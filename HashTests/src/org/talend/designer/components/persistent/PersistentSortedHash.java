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
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.talend.designer.components.persistent.IPersistableHash.KEYS_MANAGEMENT;

/**
 * DOC amaumont class global comment. Detailled comment <br/>
 * 
 * 
 */
public class PersistentSortedHash<B extends Comparable<B> & IPersistableLookupRow<B>> implements IPersistableHash<B> {

    private String container;

    private KEYS_MANAGEMENT keysManagement;

    //
    private List<AbstractOrderedBeanLookup<B>> lookupList;

    private int bufferSize = 100000;

    private IPersistableLookupRow[] buffer = null;

    private int fileIndex = 0;

    // ////////////////////////////////////////
    private int bufferIndex = 0;

    private int lookupIndex = 0;

    private B keyForLookup;

    private boolean hasGet;

    private B result;

    AbstractOrderedBeanLookup<B> currLookup;

    private boolean hasNextCanBeEvaluated;

    private IRowCreator<B> rowCreator;

    private RowProvider<B> rowProvider;

    public PersistentSortedHash(KEYS_MANAGEMENT keysManagement, String container, IRowCreator<B> rowCreator) {
        this.keysManagement = keysManagement;
        this.container = container;
        this.rowCreator = rowCreator;
        this.rowProvider = new RowProvider<B>(rowCreator);
        
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
        lookupList = new ArrayList<AbstractOrderedBeanLookup<B>>();
        for (int i = 0; i < fileIndex; i++) {
            lookupList.add(getOrderedBeanLoohupInstance(container, i, rowProvider, this.keysManagement));
        }
    }

    private AbstractOrderedBeanLookup<B> getOrderedBeanLoohupInstance(String container, int i, RowProvider<B> rowProvider,
            KEYS_MANAGEMENT keysManagement) throws IOException {
        switch (keysManagement) {
        case KEEP_FIRST:
            throw new UnsupportedOperationException();
            //return new OrderedBeanLookupMatchLast<B>(container, i, rowProvider);

        case KEEP_LAST:
            
            return new OrderedBeanLookupMatchLast<B>(container, i, rowProvider);
            
        case KEEP_ALL:
            
            return new OrderedBeanLookupMatchAll<B>(container, i, rowProvider);
            
        default:
            throw new IllegalArgumentException();
        }
    }

    public void lookup(B key) throws IOException {
        hasNextCanBeEvaluated = true;
        keyForLookup = key;
        lookupIndex = 0;
        currLookup = lookupList.get(lookupIndex);
        hasGet = false;
        int size = lookupList.size();
        for (lookupIndex = 0; lookupIndex < size; lookupIndex++) {
            AbstractOrderedBeanLookup<B> tempLookup = lookupList.get(lookupIndex);
            tempLookup.lookup(key);
            if(keysManagement != KEYS_MANAGEMENT.KEEP_ALL || keysManagement == KEYS_MANAGEMENT.KEEP_ALL && tempLookup.hasNext()) {
                return;
            }
        }
        hasNextCanBeEvaluated = false;
    }

    public boolean hasNext() throws IOException {
        
        if(!hasNextCanBeEvaluated) {
            return false;
        }
        
        if (keysManagement == KEYS_MANAGEMENT.KEEP_FIRST || keysManagement == KEYS_MANAGEMENT.KEEP_LAST) {
            if (hasGet) {
                return false;
            } else {
                if (keysManagement == KEYS_MANAGEMENT.KEEP_FIRST) {
                    for (AbstractOrderedBeanLookup<B> tempLookup : lookupList) {
                        if (tempLookup.hasNext()) {
                            hasGet = false;
                            return true;
                        }
                    }
                    return false;
                } else {
                    B temp = null;
                    for (AbstractOrderedBeanLookup<B> tempLookup : lookupList) {
                        if (tempLookup.hasNext()) {
                            temp = tempLookup.next();
                            break;
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
            if(lookupIndex >= size) {
                lookupIndex = 0;
            }
            for (; lookupIndex < size; lookupIndex++) {
                AbstractOrderedBeanLookup<B> tempLookup = lookupList.get(lookupIndex);
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
        for (AbstractOrderedBeanLookup<B> orderedBeanLookup : lookupList) {
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
