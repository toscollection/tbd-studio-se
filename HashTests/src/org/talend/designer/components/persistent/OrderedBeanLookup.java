// ============================================================================
//
// Copyright (C) 2006-2007 Talend Inc. - www.talend.com
//
// This source code is available under agreement available at
// %InstallDIR%\features\org.talend.rcp.branding.%PRODUCTNAME%\%PRODUCTNAME%license.txt
//
// You should have received a copy of the agreement
// along with this program; if not, write to Talend SA
// 9 rue Pages 92150 Suresnes, France
//
// ============================================================================

package org.talend.designer.components.persistent;

import java.io.BufferedInputStream;
import java.io.DataInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.NoSuchElementException;

import org.talend.designer.components.persistent.IPersistableHash.KEYS_MANAGEMENT;

/**
 * DOC slanglois class global comment. Detailled comment
 */
class OrderedBeanLookup<B extends Comparable<B> & IPersistableLookupRow> {

    private static final int MARK_READ_LIMIT = 256 * 1024 * 1024;

    private static final int KEYS_SIZE_PLUS_VALUES_SIZE = 8;

    DataInputStream keysDataStream;

    InputStream valuesDataStream;

    long cursorPosition;

    long length;

    private B lookupInstance;

    private int currentValuesSize;

    private int skipValuesSize;

    private boolean nextDirty = true;

    private boolean noMoreNext;

    private Comparable<B> previousAskedKey;

    private long markCursorPosition = -1;

    private KEYS_MANAGEMENT keysManagement;

    private B currentSearchedKey;

    private boolean hasNext;

    boolean atLeastOneLoadkeys = false;

    private boolean startWithNewKey;

    private boolean marking = true;

    private IRowProvider<B> rowProvider;

    private boolean nextFromCache;

    private int remainingSkip;

    private boolean previousCompareResultMatch;

    private B previousLookupInstance;

    /**
     * 
     * DOC amaumont OrderedBeanLookup constructor comment.
     * 
     * @param baseDirectory
     * @param fileIndex
     * @param internalKeyInstance
     * @param keys_management
     * @throws IOException
     */
    public OrderedBeanLookup(String baseDirectory, int fileIndex, IRowProvider<B> rowProvider, KEYS_MANAGEMENT keysManagement)
            throws IOException {
        File keysDataFile = new File(baseDirectory + "/KeysData_" + fileIndex + ".bin");
        this.length = keysDataFile.length();

        this.cursorPosition = 0;
        this.keysDataStream = new DataInputStream(new BufferedInputStream(new FileInputStream(keysDataFile)));
        this.valuesDataStream = new BufferedInputStream(new FileInputStream(baseDirectory + "/ValuesData_" + fileIndex + ".bin"));
        this.lookupInstance = rowProvider.createInstance();
        this.rowProvider = rowProvider;
        this.keysManagement = keysManagement;
    }

    public void lookup(B key) throws IOException {

        currentSearchedKey = key;

        nextDirty = true;

        if (keysManagement == KEYS_MANAGEMENT.KEEP_ALL && previousAskedKey != null && previousAskedKey.compareTo(key) == 0) {
            startWithNewKey = false;
            nextFromCache = true;
            rowProvider.resetInstanceIndex();
        } else {
            rowProvider.resetFreeIndex();
            startWithNewKey = true;
            nextFromCache = false;
            lookupInstance = rowProvider.getFreeInstance();
        }


        previousAskedKey = key;

    }

    /**
     * DOC slanglois Comment method "hasNext".
     * 
     * @return
     * @throws IOException
     */
    public boolean hasNext() throws IOException {

        if (currentSearchedKey == null) {
            return false;
        }

        if (nextFromCache) {
            return rowProvider.hasNext();
        }

        if (nextDirty) {
            if (cursorPosition >= length) {
                noMoreNext = true;
                return false;
            }

            int compareResult = -1;

            if (atLeastOneLoadkeys && startWithNewKey) {
                compareResult = previousLookupInstance.compareTo(currentSearchedKey);
                if (compareResult == 0) {
                    lookupInstance = previousLookupInstance;
                    if (previousCompareResultMatch) {
                        remainingSkip = 0;
                    }
                }
            }
            startWithNewKey = false;

            int localSkip = 0;

            if (compareResult < 0 || !atLeastOneLoadkeys) {

                do {

                    loadDataKeys();
                    compareResult = lookupInstance.compareTo(currentSearchedKey);
                    if (compareResult > 0) {
                        marking = false;
                        remainingSkip = currentValuesSize;
                        // localSkip += currentValuesSize;
                    }
                    if (compareResult >= 0 || cursorPosition >= length) {
                        previousLookupInstance = lookupInstance;
                        break;
                    }
                    if (compareResult < 0) {
                        marking = false;
                        localSkip += currentValuesSize;
                    }
                } while (true);
            }
            previousCompareResultMatch = false;
            if (compareResult == 0) {
//                skipValuesSize -= remainingSkip;
                skipValuesSize += localSkip;
                hasNext = true;
                noMoreNext = false;
                nextDirty = false;
                return true;
            } else if (compareResult < 0) {
                skipValuesSize += localSkip;
                nextDirty = true;
                noMoreNext = true;
                hasNext = false;
                return false;
            } else {
                skipValuesSize += localSkip;
                nextDirty = true;
                noMoreNext = false;
                hasNext = false;
                return false;
            }
        } else {
            return hasNext;
        }
    }

    private void loadDataKeys() throws IOException {
        atLeastOneLoadkeys = true;
        int keysDataLength = keysDataStream.readInt();
        byte[] bytes = new byte[keysDataLength];
        keysDataStream.read(bytes);
        lookupInstance.loadKeysData(bytes);
        currentValuesSize = keysDataStream.readInt();
        cursorPosition += (keysDataLength + KEYS_SIZE_PLUS_VALUES_SIZE);
    }

    private void loadDataValues(int valuesSize) throws IOException {
        if (skipValuesSize > 0) {
            skipValuesSize += remainingSkip;
            valuesDataStream.skip(skipValuesSize);
            // valuesDataStream.mark(MARK_READ_LIMIT);
            // markCursorPosition = cursorPosition;
            remainingSkip = 0;
            skipValuesSize = 0;
            marking = true;
        }
        byte[] bytes = new byte[valuesSize];
        valuesDataStream.read(bytes);
        lookupInstance.loadValuesData(bytes);
    }

    /**
     * DOC slanglois Comment method "next".
     * 
     * @return
     * @throws IOException
     */
    public B next() throws IOException {

        previousCompareResultMatch = true;
        if (nextFromCache) {
            return rowProvider.next();
        }

        if (noMoreNext || nextDirty) {
            throw new NoSuchElementException();
        }

        
        loadDataValues(currentValuesSize);

        nextDirty = true;

        B row = lookupInstance;
        lookupInstance = rowProvider.getFreeInstance();

        atLeastOneLoadkeys = false;

        return row;
    }

    /**
     * DOC slanglois Comment method "close".
     * 
     * @throws IOException
     */
    public void close() throws IOException {
        if (keysDataStream != null) {
            keysDataStream.close();
        }
        if (valuesDataStream != null) {
            valuesDataStream.close();
        }
    }

}
