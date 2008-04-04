// ============================================================================
//
// Talend Community Edition
//
// Copyright (C) 2006 Talend Inc. - www.talend.com
//
// This library is free software; you can redistribute it and/or
// modify it under the terms of the GNU Lesser General Public
// License as published by the Free Software Foundation; either
// version 2.1 of the License, or (at your option) any later version.
//
// This library is distributed in the hope that it will be useful,
// but WITHOUT ANY WARRANTY; without even the implied warranty of
// MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU
// Lesser General Public License for more details.
//
// You should have received a copy of the GNU General Public License
// along with this program; if not, write to the Free Software
// Foundation, Inc., 675 Mass Ave, Cambridge, MA 02139, USA.
//
// ============================================================================

package org.talend.designer.components.persistent;

import java.io.BufferedOutputStream;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * DOC amaumont class global comment. Detailled comment <br/>
 * 
 * 
 */
public class PersistentSortedHash<B extends Comparable<B> & IPersistableLookupRow> implements IPersistableHash<B> {

    private String container;

    private KEYS_MANAGEMENT keysManagement;

    //
    private List<OrderedBeanLookup<B>> lookupList;

    private int bufferSize = 100000;

    private IPersistableLookupRow[] buffer = null;

    private int fileIndex = 0;

    // ////////////////////////////////////////
    private int bufferIndex = 0;

    private int lookupIndex = 0;

    private B keyForLookup;

    private boolean hasGet;

    private B result;

    OrderedBeanLookup<B> currLookup;

    private B internalKeyInstance;

    public PersistentSortedHash(KEYS_MANAGEMENT keysManagement, String container, B internalKeyInstance) {
        this.keysManagement = keysManagement;
        this.container = container;
        this.internalKeyInstance = internalKeyInstance;
    }

    public void initPut() throws IOException {
        buffer = new IPersistableLookupRow[bufferSize];
        bufferIndex = 0;
    }

    public void put(B bean) throws IOException {
        if (bufferIndex == bufferSize) {
            Arrays.sort(buffer);
            File keysDataFile = new File(container + "/KeysData_" + fileIndex + ".bin");
            File valuesDataFile = new File(container + "/ValuesData_" + fileIndex + ".bin");
            DataOutputStream keysDataOutputStream = new DataOutputStream(new BufferedOutputStream(new FileOutputStream(
                    keysDataFile)));
            BufferedOutputStream valuesDataOutputStream = new BufferedOutputStream(new FileOutputStream(valuesDataFile));
            byte[] keysData = null;
            byte[] valuesData = null;
            for (IPersistableLookupRow curBean : buffer) {
                valuesData = curBean.toValuesData();
                keysData = curBean.toKeysData();

                keysDataOutputStream.writeInt(keysData.length);
                keysDataOutputStream.write(keysData);
                keysDataOutputStream.writeInt(valuesData.length);

                valuesDataOutputStream.write(valuesData);
            }
            keysDataOutputStream.close();
            valuesDataOutputStream.close();
            fileIndex++;
            // Arrays.fill(buffer, null);
            bufferIndex = 0;

        }
        buffer[bufferIndex] = bean;
        bufferIndex++;
    }

    public void endPut() throws IOException {
        Arrays.sort(buffer, 0, bufferIndex);
        File keysDataFile = new File(container + "/KeysData_" + fileIndex + ".bin");
        File valuesDataFile = new File(container + "/ValuesData_" + fileIndex + ".bin");
        DataOutputStream keysDataOutputStream = new DataOutputStream(new BufferedOutputStream(new FileOutputStream(keysDataFile)));
        BufferedOutputStream valuesDataOutputStream = new BufferedOutputStream(new FileOutputStream(valuesDataFile));
        byte[] keysData = null;
        byte[] valuesData = null;
        for (int i = 0; i < bufferIndex; i++) {
            IPersistableLookupRow curBean = buffer[i];
            valuesData = curBean.toValuesData();
            keysData = curBean.toKeysData();

            keysDataOutputStream.writeInt(keysData.length);
            keysDataOutputStream.write(keysData);
            keysDataOutputStream.writeInt(valuesData.length);

            valuesDataOutputStream.write(valuesData);
        }
        keysDataOutputStream.close();
        valuesDataOutputStream.close();
        fileIndex++;

    }

    public void initGet() throws IOException {
        lookupList = new ArrayList<OrderedBeanLookup<B>>();
        for (int i = 0; i < fileIndex; i++) {
            lookupList.add(new OrderedBeanLookup<B>(container, i, internalKeyInstance, this.keysManagement));
        }
    }

    public void lookup(B key) throws IOException {
        keyForLookup = key;
        lookupIndex = 0;
        currLookup = lookupList.get(lookupIndex);
        hasGet = false;
        int size = lookupList.size();
        for (lookupIndex = 0; lookupIndex < size; lookupIndex++) {
            OrderedBeanLookup<B> tempLookup = lookupList.get(lookupIndex);
            tempLookup.lookup(key);
            if(keysManagement == KEYS_MANAGEMENT.KEEP_ALL && tempLookup.hasNext()) {
                break;
            }
        }
    }

    public boolean hasNext() throws IOException {
        if (keysManagement == KEYS_MANAGEMENT.KEEP_FIRST || keysManagement == KEYS_MANAGEMENT.KEEP_LAST) {
            if (hasGet) {
                return false;
            } else {
                if (keysManagement == KEYS_MANAGEMENT.KEEP_FIRST) {
                    for (OrderedBeanLookup<B> tempLookup : lookupList) {
                        if (tempLookup.hasNext()) {
                            hasGet = false;
                            return true;
                        }
                    }
                    return false;
                } else {
                    B temp = null;
                    for (OrderedBeanLookup<B> tempLookup : lookupList) {
                        while (tempLookup.hasNext()) {
                            temp = tempLookup.next();
                        }
                    }
                    if (temp != null) {
                        result = temp;
                        hasGet = true;
                        return true;
                    } else {
                        return false;
                    }
                }
            }
        } else {
            hasGet = false;
            int size = lookupList.size();
            for (; lookupIndex < size; lookupIndex++) {
                OrderedBeanLookup<B> tempLookup = lookupList.get(lookupIndex);
                if (tempLookup.hasNext()) {
                    return true;
                }
            }
            result = null;
            return false;
        }
    }

    public B next() throws IOException {
        if(!hasGet) {
            hasGet = true;
            return currLookup.next();
        } else {
            return result;
        }
    }

    public void endGet() throws IOException {
        for (OrderedBeanLookup<B> orderedBeanLookup : lookupList) {
            orderedBeanLookup.close();
        }
        lookupList = null;
        buffer = null;
    }

    public void clear() throws IOException {
        for (int i = 0; i < fileIndex; i++) {
            (new File(container + "/KeysData_" + i + ".bin")).delete();
            (new File(container + "/ValuesData_" + i + ".bin")).delete();
        }
    }

    public boolean hasFreeBean() {
        return buffer.length > 0 && bufferIndex != buffer.length && buffer[bufferIndex] != null;
    }

    public B nextFreeBean() {
        return (B) buffer[bufferIndex];
    }

}
