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
import java.util.Comparator;
import java.util.List;

import test.prototype.main.ILookupBean;

/**
 * DOC amaumont class global comment. Detailled comment <br/>
 * 
 */
public class PersistentSortedHash<K> implements IPersistentHash<K, ILookupBean> {

    private Comparator<Object> keyValueComparator;

    private String container;

    private KEYS_MANAGEMENT keysManagement;

    //
    private List<OrderedBeanLookup> lookupList;

    private int bufferSize = 100000;

    private ILookupBean[] buffer = null;

    private int fileIndex = 0;

    // ////////////////////////////////////////
    private int bufferIndex = 0;

    private int lookupIndex = 0;

    private K keyForLookup;

    private boolean hasGet;

    private ILookupBean result;

    OrderedBeanLookup currLookup;

    public PersistentSortedHash(KEYS_MANAGEMENT keysManagement, String container, Comparator<Object> keyValueComparator) {
        this.keysManagement = keysManagement;
        this.container = container;
        this.keyValueComparator = keyValueComparator;

    }

    public void initPut() throws IOException {
        buffer = new ILookupBean[bufferSize];
    }

    public void put(ILookupBean bean) throws IOException {
        if (bufferIndex == bufferSize) {
            Arrays.sort(buffer, keyValueComparator);
            File keysDataFile = new File(container + "/KeysData_" + fileIndex + ".bin");
            File valuesDataFile = new File(container + "/ValuesData_" + fileIndex + ".bin");
            DataOutputStream keysDataOutputStream = new DataOutputStream(new BufferedOutputStream(new FileOutputStream(
                    keysDataFile)));
            BufferedOutputStream valuesDataOutputStream = new BufferedOutputStream(new FileOutputStream(valuesDataFile));
            byte[] keysData = null;
            byte[] valuesData = null;
            for (ILookupBean curBean : buffer) {
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
        Arrays.sort(buffer, 0, bufferIndex, keyValueComparator);
        File keysDataFile = new File(container + "/KeysData_" + fileIndex + ".bin");
        File valuesDataFile = new File(container + "/ValuesData_" + fileIndex + ".bin");
        DataOutputStream keysDataOutputStream = new DataOutputStream(new BufferedOutputStream(new FileOutputStream(keysDataFile)));
        BufferedOutputStream valuesDataOutputStream = new BufferedOutputStream(new FileOutputStream(valuesDataFile));
        byte[] keysData = null;
        byte[] valuesData = null;
        for (int i = 0; i < bufferIndex; i++) {
            ILookupBean curBean = buffer[i];
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
            ILookupBean[] reusedBuffer = Arrays.copyOfRange(buffer, j, bufferSize + j);
            j += bufferSize;
            try {
                lookupList.add(new OrderedBeanLookup(container, i, reusedBuffer, keyValueComparator));
            } catch (InstantiationException e) {
                e.printStackTrace();
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            }
        }
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
                            result = tempLookup.next();
                            hasGet = true;
                            return true;
                        }
                    }
                    return false;
                } else {
                    ILookupBean temp = null;
                    for (OrderedBeanLookup tempLookup : lookupList) {
                        while (tempLookup.hasNext(keyForLookup)) {
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
            return hasNextInKeepAllMode();
        }
    }

    private boolean hasNextInKeepAllMode() {
        if (currLookup == null) {
            return false;
        }
        boolean tempHasNext = currLookup.hasNext(keyForLookup);
        if (tempHasNext) {
            result = currLookup.next();
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

    public ILookupBean next() throws IOException {
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

}
