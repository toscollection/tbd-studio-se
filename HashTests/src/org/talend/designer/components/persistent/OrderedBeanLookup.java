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


/**
 * DOC slanglois class global comment. Detailled comment
 */
class OrderedBeanLookup {

    DataInputStream keysDataStream;

    InputStream valuesDataStream;

    ILookupBean[] buffer;

    int[] valuesDataLengths;

    long cursorPosition;

    long length;

    int size;

    int index;

    boolean noDataInStream;

    /**
     * DOC slanglois OrderedBeanLookup constructor comment.
     * 
     * @param baseDirectory
     * @param fileIndex
     * @param buffer
     * @param comparator
     * @throws IOException
     * @throws InstantiationException
     * @throws IllegalAccessException
     */
    public OrderedBeanLookup(String baseDirectory, int fileIndex, ILookupBean[] buffer) throws IOException,
            InstantiationException, IllegalAccessException {
        if (buffer == null || buffer.length < 1) {
            throw new IllegalArgumentException();
        }
        File keysDataFile = new File(baseDirectory + "/KeysData_" + fileIndex + ".bin");
        length = keysDataFile.length();
        cursorPosition = 0;
        keysDataStream = new DataInputStream(new BufferedInputStream(new FileInputStream(keysDataFile)));
        valuesDataStream = new BufferedInputStream(new FileInputStream(baseDirectory + "/ValuesData_" + fileIndex + ".bin"));

        this.buffer = buffer;
        valuesDataLengths = new int[buffer.length];

        loadBuffer();
    }

    /**
     * DOC slanglois Comment method "hasNext".
     * 
     * @return
     */
    public boolean hasNext(Comparable key) {
        if (index == size) {
            if (cursorPosition < length) {
                try {
                    loadBuffer();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            } else {
                return false;
            }
        }

        int compareResult = key.compareTo(buffer[index]);

        if (compareResult == 0) {
            byte[] bytes = new byte[valuesDataLengths[index]];
            try {
                valuesDataStream.read(bytes);
            } catch (IOException e) {
                e.printStackTrace();
            }
            buffer[index].loadValuesData(bytes);
            return true;
        } else if (compareResult < 0) {
            return false;
        } else {
            try {
                valuesDataStream.skip(valuesDataLengths[index]);
            } catch (IOException e) {
                e.printStackTrace();
            }
            index++;
            return hasNext(key);
        }
    }

    /**
     * DOC slanglois Comment method "next".
     * 
     * @return
     */
    public ILookupBean next() {
        return buffer[index++];
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
        buffer = null;
    }

    /**
     * DOC slanglois Comment method "hasMoreBean".
     * 
     * @return
     */
    public boolean hasMoreBean() {
        return (cursorPosition < length) || (index < size);
    }

    /**
     * DOC slanglois Comment method "loadBuffer".
     * 
     * @throws IOException
     */
    private void loadBuffer() throws IOException {
        int i = 0;
        for (; i < buffer.length && cursorPosition < length; i++) {
            int keysDataLength = keysDataStream.readInt();
            byte[] bytes = new byte[keysDataLength];
            keysDataStream.read(bytes);
            buffer[i].loadKeysData(bytes);
            valuesDataLengths[i] = keysDataStream.readInt();
            cursorPosition += (keysDataLength + 8);
        }
        size = i;
        index = 0;
    }
}
