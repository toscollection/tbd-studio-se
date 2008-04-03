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
 */
public class PersistentSortedHash<K extends Comparable<V>, V extends IPersistableLookupRow> implements IPersistableHash<K, V> {

    private String container;

    private KEYS_MANAGEMENT keysManagement;

    //
    private List<OrderedBeanLookup> lookupList;

    private int bufferSize = 100000;

    private IPersistableLookupRow[] buffer = null;

    private int fileIndex = 0;

    // ////////////////////////////////////////
    private int bufferIndex = 0;

    private int lookupIndex = 0;

    private K keyForLookup;

    private boolean hasGet;

    private V result;

    OrderedBeanLookup currLookup;

    public PersistentSortedHash(KEYS_MANAGEMENT keysManagement, String container) {
        this.keysManagement = keysManagement;
        this.container = container;
    }

    public void initPut() throws IOException {
        buffer = new IPersistableLookupRow[bufferSize];
    }

    public void put(V bean) throws IOException {
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
        lookupList = new ArrayList<OrderedBeanLookup>();
        int bufferSize = 0;
        if (fileIndex > 1) {
            // Caution: if fileIndex > bufferLength, the reuse of bean instance doesn't work.
            bufferSize = buffer.length / fileIndex;
        } else {
            bufferSize = bufferIndex;
        }
        // bufferSize = 10;
        int j = 0;
        for (int i = 0; i < fileIndex; i++) {
            IPersistableLookupRow[] reusedBuffer = ((Object) ((Class<IPersistableLookupRow[]>) buffer.getClass()) == (Object) Object[].class) ? (IPersistableLookupRow[]) new Object[bufferSize]
                    : (IPersistableLookupRow[]) Array
                            .newInstance(((Class<IPersistableLookupRow[]>) buffer.getClass()).getComponentType(), bufferSize);
            System.arraycopy(buffer, j, reusedBuffer, 0, Math.min(buffer.length - j, bufferSize));

            j += bufferSize;
            try {
                lookupList.add(new OrderedBeanLookup(container, i, reusedBuffer));
            } catch (InstantiationException e) {
                e.printStackTrace();
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            }
        }
        buffer = null;
    }

    public void lookup(K key) throws IOException {
        keyForLookup = key;
        lookupIndex = 0;
        currLookup = lookupList.get(lookupIndex);
        if (keysManagement == KEYS_MANAGEMENT.KEEP_FIRST || keysManagement == KEYS_MANAGEMENT.KEEP_LAST) {
            hasGet = false;
        }
    }

    public boolean hasNext() throws IOException {
        if (keysManagement == KEYS_MANAGEMENT.KEEP_FIRST || keysManagement == KEYS_MANAGEMENT.KEEP_LAST) {
            if (hasGet) {
                return false;
            } else {
                if (keysManagement == KEYS_MANAGEMENT.KEEP_FIRST) {
                    for (OrderedBeanLookup tempLookup : lookupList) {
                        if (tempLookup.hasNext(keyForLookup)) {
                            result = (V) tempLookup.next();
                            hasGet = true;
                            return true;
                        }
                    }
                    return false;
                } else {
                    V temp = null;
                    for (OrderedBeanLookup tempLookup : lookupList) {
                        while (tempLookup.hasNext(keyForLookup)) {
                            temp = (V) tempLookup.next();
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
            return hasNextInKeepAllMode();
        }
    }

    private boolean hasNextInKeepAllMode() {
        if (currLookup == null) {
            return false;
        }
        boolean tempHasNext = currLookup.hasNext(keyForLookup);
        if (tempHasNext) {
            result = (V) currLookup.next();
            return true;
        }
        lookupIndex++;
        if (lookupIndex < lookupList.size()) {
            currLookup = lookupList.get(lookupIndex);
        } else {
            currLookup = null;
        }
        return hasNextInKeepAllMode();
    }

    public V next() throws IOException {
        return result;
    }

    public void endGet() throws IOException {
        for (OrderedBeanLookup orderedBeanLookup : lookupList) {
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
        return fileIndex > 0;
    }

    public V nextFreeBean() {
        return (V) buffer[bufferIndex];
    }

}
