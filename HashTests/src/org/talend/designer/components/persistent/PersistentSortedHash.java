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
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.NoSuchElementException;

import org.talend.designer.components.persistent.IPersistableHash.KEYS_MANAGEMENT;

import routines.system.IPersistableLookupRow;

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

    private int bufferSize = 10000000;

    // private int bufferSize = 100;
    // private int bufferSize = 3;

    private IPersistableLookupRow<B>[] buffer = null;

    private int fileIndex = 0;

    // ////////////////////////////////////////
    private int bufferBeanIndex = 0;

    private int lookupIndex = 0;

    AbstractOrderedBeanLookup<B> currLookup;

    private int lookupListSize;

    private boolean waitingNext;

    private B lookupKey;

    private boolean noMoreNext;

    private B previousResult;

    private boolean nextIsPreviousResult;

    private IRowCreator<B> rowCreator;

    private boolean lookupKeyIsInitialized;

    private boolean previousResultRetrieved;

    private int bufferMarkLimit = -1;

    private boolean bufferIsMarked;

    public PersistentSortedHash(KEYS_MANAGEMENT keysManagement, String container, IRowCreator<B> rowCreator) {
        this.keysManagement = keysManagement;
        this.container = container;
        this.rowCreator = rowCreator;
        this.lookupKey = rowCreator.createRowInstance();
    }

    public void initPut() throws IOException {
        buffer = new IPersistableLookupRow[bufferSize];
        bufferBeanIndex = 0;
    }

    public void put(B bean) throws IOException {

        if (bufferBeanIndex > 1000 && !bufferIsMarked && !MemoryHelper.hasFreeMemory(0.20f)) {
            bufferMarkLimit = bufferBeanIndex;
            bufferIsMarked = true;
        }

        if (bufferBeanIndex == bufferSize || bufferIsMarked && bufferBeanIndex == bufferMarkLimit) {
            writeBuffer();
        }
        buffer[bufferBeanIndex++] = bean;
    }

    public void endPut() throws IOException {

        if (bufferBeanIndex > 0) {
            writeBuffer();
        }

        //Arrays.fill(buffer, null);

        buffer = null;

        MemoryHelper.gc();

    }

    private void writeBuffer() throws FileNotFoundException, IOException {
        Arrays.sort(buffer, 0, bufferBeanIndex);
        File keysDataFile = new File(buildKeysFilePath(fileIndex));
        File valuesDataFile = new File(buildValuesFilePath(fileIndex));
        DataOutputStream keysDataOutputStream = new DataOutputStream(new BufferedOutputStream(new FileOutputStream(keysDataFile)));
        BufferedOutputStream valuesDataOutputStream = new BufferedOutputStream(new FileOutputStream(valuesDataFile));
        byte[] keysData = null;
        byte[] valuesData = null;

        System.out.println("-------------------------------------------------------------");
        System.out.println("Writing LOOKUP buffer " + fileIndex + "... ");

        for (int i = 0; i < bufferBeanIndex; i++) {

            IPersistableLookupRow<B> curBean = buffer[i];
            valuesData = curBean.toValuesData();
            keysData = curBean.toKeysData();

            keysDataOutputStream.writeInt(keysData.length);
            keysDataOutputStream.write(keysData);
            keysDataOutputStream.writeInt(valuesData.length);

            valuesDataOutputStream.write(valuesData);

            // System.out.println(curBean);
        }
        System.out.println("Write ended LOOKUP buffer " + fileIndex);
        keysDataOutputStream.close();
        valuesDataOutputStream.close();
        fileIndex++;
        bufferBeanIndex = 0;
    }

    private String buildValuesFilePath(int i) {
        return container + "ValuesData_" + i + ".bin";
    }

    private String buildKeysFilePath(int i) {
        return container + "KeysData_" + i + ".bin";
    }

    public void initGet() throws IOException {
        lookupList = new ArrayList<AbstractOrderedBeanLookup<B>>();
        for (int i = 0; i < fileIndex; i++) {
            RowProvider<B> rowProvider = new RowProvider<B>(rowCreator);
            lookupList.add(getOrderedBeanLoohupInstance(buildKeysFilePath(i), buildValuesFilePath(i), i, rowProvider,
                    this.keysManagement));
        }
        lookupListSize = fileIndex;
    }

    private AbstractOrderedBeanLookup<B> getOrderedBeanLoohupInstance(String keysFilePath, String valuesFilePath, int i,
            RowProvider<B> rowProvider, KEYS_MANAGEMENT keysManagement) throws IOException {
        switch (keysManagement) {
        case KEEP_FIRST:
            throw new UnsupportedOperationException();
            // return new OrderedBeanLookupMatchLast<B>(container, i, rowProvider);

        case KEEP_LAST:

            return new OrderedBeanLookupMatchLast<B>(keysFilePath, valuesFilePath, i, rowProvider);

        case KEEP_ALL:

            return new OrderedBeanLookupMatchAll<B>(keysFilePath, valuesFilePath, i, rowProvider);

        default:
            throw new IllegalArgumentException();
        }
    }

    public void lookup(B key) throws IOException {

        waitingNext = false;
        if (keysManagement == KEYS_MANAGEMENT.KEEP_ALL) {
            lookupIndex = 0;
            for (int lookupIndexLocal = 0; lookupIndexLocal < lookupListSize; lookupIndexLocal++) {
                AbstractOrderedBeanLookup<B> tempLookup = lookupList.get(lookupIndexLocal);
                tempLookup.lookup(key);
            }
        } else {
            try {
                if (lookupKey.compareTo(key) == 0 && previousResultRetrieved) {
                    nextIsPreviousResult = true;
                }
            } catch (NullPointerException e) {
                // nothing
            }
            noMoreNext = false;
        }
        key.copyDataTo(lookupKey);
        lookupKeyIsInitialized = true;
    }

    public boolean hasNext() throws IOException {

        if (waitingNext || nextIsPreviousResult) {
            return true;
        }

        if (!lookupKeyIsInitialized || noMoreNext) {
            return false;
        }

        if (keysManagement == KEYS_MANAGEMENT.KEEP_LAST) {
            for (int lookupIndexLocal = lookupListSize - 1; lookupIndexLocal >= 0; lookupIndexLocal--) {
                AbstractOrderedBeanLookup<B> tempLookup = lookupList.get(lookupIndexLocal);
                // System.out.println("########################################");
                // System.out.println(lookupKey);
                // System.out.println("lookupIndexLocal=" + lookupIndexLocal);
                tempLookup.lookup(lookupKey);
                if (tempLookup.hasNext()) {
                    // System.out.println("Found in " + lookupIndexLocal);
                    currLookup = tempLookup;
                    waitingNext = true;
                    noMoreNext = true;
                    previousResultRetrieved = false;
                    return true;
                }
            }
            noMoreNext = true;
            return false;

        } else if (keysManagement == KEYS_MANAGEMENT.KEEP_ALL) {
            for (; lookupIndex < lookupListSize; lookupIndex++) {
                AbstractOrderedBeanLookup<B> tempLookup = lookupList.get(lookupIndex);
                if (tempLookup.hasNext()) {
                    currLookup = tempLookup;
                    waitingNext = true;
                    return true;
                }
            }
            return false;

        } else {

            if (currLookup.hasNext()) {
                waitingNext = true;
                return true;
            }
            lookupIndex++;
            return false;

        }

    }

    public B next() throws IOException {

        if (nextIsPreviousResult) {
            nextIsPreviousResult = false;
            noMoreNext = true;
            return previousResult;
        }

        if (waitingNext) {
            waitingNext = false;
            previousResult = currLookup.next();

            if (keysManagement == KEYS_MANAGEMENT.KEEP_LAST || keysManagement == KEYS_MANAGEMENT.KEEP_FIRST) {
                previousResultRetrieved = true;
                noMoreNext = true;
            }

            return previousResult;
        } else {
            throw new NoSuchElementException();
        }
    }

    public void endGet() throws IOException {
        for (AbstractOrderedBeanLookup<B> orderedBeanLookup : lookupList) {
            orderedBeanLookup.close();
        }
        lookupList = null;
    }

    public void clear() throws IOException {
        for (int i = 0; i < fileIndex; i++) {
            (new File(buildKeysFilePath(i))).delete();
            (new File(buildValuesFilePath(i))).delete();
        }
    }

    public B getNextFreeRow() {
        if (buffer.length > 0 && bufferBeanIndex != buffer.length) {
            B nextBean = (B) buffer[bufferBeanIndex];
            if (nextBean == null) {
                return this.rowCreator.createRowInstance();
            } else {
                return nextBean;
            }
        } else {
            return this.rowCreator.createRowInstance();
        }
    }

}
