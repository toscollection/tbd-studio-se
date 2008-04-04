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

    private B internalInstance;

    private int currentValuesSize;

    private int skipValuesSize;

    private boolean nextDirty = true;

    private boolean noMoreNext;

    private Comparable<B> previousAskedKey;

    private long markCursorPosition;

    private KEYS_MANAGEMENT keysManagement;

    private B currentKey;

    private boolean hasNext;

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
    public OrderedBeanLookup(String baseDirectory, int fileIndex, B internalKeyInstance, KEYS_MANAGEMENT keysManagement)
            throws IOException {
        File keysDataFile = new File(baseDirectory + "/KeysData_" + fileIndex + ".bin");
        this.length = keysDataFile.length();

        this.cursorPosition = 0;
        this.keysDataStream = new DataInputStream(new BufferedInputStream(new FileInputStream(keysDataFile)));
        this.valuesDataStream = new BufferedInputStream(new FileInputStream(baseDirectory + "/ValuesData_" + fileIndex + ".bin"));
        this.internalInstance = internalKeyInstance;
        this.keysManagement = keysManagement;
    }

    public void lookup(B key) throws IOException {

        currentKey = key;

        nextDirty = true;
        
        if (keysManagement != KEYS_MANAGEMENT.KEEP_ALL && previousAskedKey != null && previousAskedKey.compareTo(key) == 0) {
            valuesDataStream.reset();
            cursorPosition = markCursorPosition;
        } else {
            valuesDataStream.mark(MARK_READ_LIMIT);
            markCursorPosition = cursorPosition;
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

        if (nextDirty) {
            if (cursorPosition >= length) {
                noMoreNext = true;
                return false;
            }

            currentValuesSize = 0;

            int compareResult;
            do {
                skipValuesSize += currentValuesSize;
                loadDataKeys();
            } while ((compareResult = internalInstance.compareTo(currentKey)) < 0 && cursorPosition < length);

            if (compareResult == 0) {
                hasNext = true;
                noMoreNext = false;
                nextDirty = false;
                return true;
            } else if (compareResult < 0) {
                noMoreNext = true;
                hasNext = false;
                return false;
            } else {
                noMoreNext = false;
                nextDirty = false;
                hasNext = false;
                return false;
            }
        } else {
            return hasNext;
        }
    }

    private void loadDataKeys() throws IOException {
        int keysDataLength = keysDataStream.readInt();
        byte[] bytes = new byte[keysDataLength];
        keysDataStream.read(bytes);
        internalInstance.loadKeysData(bytes);
        currentValuesSize = keysDataStream.readInt();
        cursorPosition += (keysDataLength + KEYS_SIZE_PLUS_VALUES_SIZE);
    }

    private void loadDataValues(int valuesSize) throws IOException {
        if (skipValuesSize > 0) {
            valuesDataStream.skip(skipValuesSize);
            skipValuesSize = 0;
        }
        byte[] bytes = new byte[valuesSize];
        valuesDataStream.read(bytes);
        internalInstance.loadValuesData(bytes);
    }

    /**
     * DOC slanglois Comment method "next".
     * 
     * @return
     * @throws IOException
     */
    public B next() throws IOException {

        if (noMoreNext || nextDirty) {
            throw new NoSuchElementException();
        }

        loadDataValues(currentValuesSize);

        nextDirty = true;
        return internalInstance;
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
