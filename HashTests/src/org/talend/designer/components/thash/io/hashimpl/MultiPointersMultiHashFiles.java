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
package org.talend.designer.components.thash.io.hashimpl;

import gnu.trove.THashMap;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.RandomAccessFile;

import org.talend.designer.components.thash.io.IMapHashFile;

/**
 * 
 * DOC amaumont class global comment. Detailled comment <br/>
 * 
 */
public class MultiPointersMultiHashFiles implements IMapHashFile {

    /**
     * 
     */
    private static final int INTEGER_BYTES_SIZE = 4;

    private int filePointersNumberPrm = -1;

    private RandomAccessFile[] bwArray = null;

    private int[] bwPositionArray = null;

    public boolean readonly;

    private int hashFilesNumber;

    private MultiReadPointersFileHandler[] fileHandlersArray = null;

    private Object[] lastRetrievedObjectArray = null;

    private long[] lastRetrievedCursorPositionArray = null;

    private int countUniqueGet;

    private String filePath;

    private int maxSizeData;

    private byte[] readBuffer;

    private int cacheLimit = 1000000;

    private boolean cacheEnabled;
    
    private THashMap<Integer, Object> cache = (cacheEnabled ? new THashMap<Integer, Object>(cacheLimit) : null);

    private long limitEndOfRead;
    

    public MultiPointersMultiHashFiles(String filePath, int hashFilesNumber) {
        super();
        this.filePath = filePath;
        this.hashFilesNumber = hashFilesNumber;
    }

    /**
     * DOC amaumont Comment method "getFileNumber".
     * 
     * @param hashcode
     * @return
     */
    public int getFileNumber(int hashcode) {
        int index = Math.abs(hashcode) % hashFilesNumber;
//        System.out.println("read file "+ index);
        return index;
    }

    public void initPut(String container) throws IOException {
        if (!readonly) {
            bwArray = new RandomAccessFile[hashFilesNumber];
            bwPositionArray = new int[hashFilesNumber];
            for (int i = 0; i < hashFilesNumber; i++) {
                File file = new File(this.filePath + i);
                file.delete();
                bwArray[i] = new RandomAccessFile(this.filePath + i, "rw");
            }
        }
    }

    public long put(String container, Object bean) throws IOException {

        int fileNumber = getFileNumber(bean.hashCode());

        ObjectOutputStream objectOutputStream = null;
        ByteArrayOutputStream byteArrayOutputStream = null;
        try {
            byteArrayOutputStream = new ByteArrayOutputStream();
            objectOutputStream = new ObjectOutputStream(byteArrayOutputStream);
            objectOutputStream.writeObject(bean);
            objectOutputStream.flush();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (objectOutputStream != null) {
                try {
                    objectOutputStream.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }

        int sizeBytes = byteArrayOutputStream.size();

        if (!readonly) {
            RandomAccessFile bw = bwArray[fileNumber];
            // bw.wr
            bw.writeInt(sizeBytes);
            bw.write(byteArrayOutputStream.toByteArray());
        }

        if (maxSizeData < sizeBytes) {
            maxSizeData = sizeBytes;
        }

        byteArrayOutputStream.close();

        long returnPosition = bwPositionArray[fileNumber];

        //putInCache((int)returnPosition, bean);

        bwPositionArray[fileNumber] += (INTEGER_BYTES_SIZE + sizeBytes);

        return returnPosition;
    }

    /**
     * DOC amaumont Comment method "putInCache".
     * 
     * @param bean
     * @param returnPosition
     */
    private void putInCache(int returnPosition, Object bean) {
        if (bean.hashCode() % 10 == 0 && cache.size() < cacheLimit) {
            cache.put((int) returnPosition, bean);
        }
    }

    public void endPut() {
        if (!readonly) {
            for (int i = 0; i < hashFilesNumber; i++) {
                try {
                    if (bwArray[i] != null) {
                        bwArray[i].close();
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    public void initGet(String container) throws IOException {
        if(filePointersNumberPrm == -1) {
            throw new IllegalStateException("filePointersNumberPrm must be set before call initGet");
        }
        
        fileHandlersArray = new MultiReadPointersFileHandler[hashFilesNumber];
        lastRetrievedCursorPositionArray = new long[hashFilesNumber];
        lastRetrievedObjectArray = new Object[hashFilesNumber];
        for (int i = 0; i < hashFilesNumber; i++) {
            fileHandlersArray[i] = new MultiReadPointersFileHandler(this.filePath + i, filePointersNumberPrm);
            fileHandlersArray[i].init();
            lastRetrievedCursorPositionArray[i] = -1;
        }
        readBuffer = new byte[INTEGER_BYTES_SIZE + maxSizeData];
    }

    public Object get(String container, long cursorPosition, int hashcode) throws IOException, ClassNotFoundException {

        int fileNumber = getFileNumber(hashcode);

        MultiReadPointersFileHandler fh = fileHandlersArray[fileNumber];
        RandomAccessFile ra = fh.getPointer(cursorPosition);

        if (cursorPosition != lastRetrievedCursorPositionArray[fileNumber]) {
            if(hashFilesNumber == 1 && cacheEnabled) {
                Object object = getFromChache((int) cursorPosition);
                if (object == null) {
                    ++countUniqueGet;
                    object = readData(cursorPosition, fileNumber, ra);
                    putInCache((int) cursorPosition, object);
                } else {
                    lastRetrievedObjectArray[fileNumber] = object;
                    System.out.println("Found in cache !");
                }
            } else {
                readData(cursorPosition, fileNumber, ra);
            }
        } else {
            //System.out.println("previous is good !");
        }
        return lastRetrievedObjectArray[fileNumber];
    }

    public MultiReadPointersFileHandler getFileHandler(int index) {
        return fileHandlersArray[index];
    }
    
    /**
     * DOC amaumont Comment method "readData".
     * @param cursorPosition
     * @param fileNumber
     * @param ra
     * @return
     * @throws IOException
     * @throws ClassNotFoundException
     */
    private Object readData(long cursorPosition, int fileNumber, RandomAccessFile ra) throws IOException,
            ClassNotFoundException {
        Object object;
        
        if(cursorPosition < 0) {
            cursorPosition = -1*cursorPosition + Integer.MAX_VALUE; 
        }
        
        ra.seek(cursorPosition);
        ra.read(readBuffer);
        object = new ObjectInputStream(new ByteArrayInputStream(readBuffer,
                INTEGER_BYTES_SIZE, getDataLength(readBuffer[0], readBuffer[1], readBuffer[2], readBuffer[3])))
                .readObject();
        lastRetrievedObjectArray[fileNumber] = object;
        lastRetrievedCursorPositionArray[fileNumber] = cursorPosition;
        return object;
    }

    /**
     * DOC amaumont Comment method "getFromChache".
     * 
     * @param cursorPosition
     * @return
     */
    private Object getFromChache(int cursorPosition) {
        return cache.get(cursorPosition);
    }

    /**
     * DOC amaumont Comment method "getDataLength".
     * 
     * @param readBuffer2
     * @return
     */
    private int getDataLength(int byte0, int byte1, int byte2, int byte3) {
        return ((byte0 << 24) + (byte1 << 16) + (byte2 << 8) + (byte3 << 0));
    }

    public void endGet(String container) throws IOException {
        if (!readonly) {
            for (int i = 0; i < hashFilesNumber; i++) {
                MultiReadPointersFileHandler ra = fileHandlersArray[i];
                if (ra != null) {
                    ra.close();
                }
                File file = new File(this.filePath + i);
                // file.delete();
            }
        }

        System.out.println("countUniqueGet = " + countUniqueGet);
    }

    /**
     * 
     * DOC amaumont MultiPointersMultiHashFiles class global comment. Detailled comment
     */
    public class MultiReadPointersFileHandler {

        private String containerFilePath;

        private RandomAccessFile[] pointersArray;

        private int filePointersNumber;

        private int offsetBetweenPointer;

        private long fileSize;

        /**
         * DOC amaumont FileHandler constructor comment.
         * 
         * @param filePointersNumber
         * @throws IOException
         */
        public MultiReadPointersFileHandler(String containerFilePath, int filePointersNumber) throws IOException {
            super();
            this.containerFilePath = containerFilePath;
            this.filePointersNumber = filePointersNumber;
        }

        /**
         * DOC amaumont Comment method "init".
         * 
         * @throws IOException
         */
        public void init() throws IOException {
            pointersArray = new RandomAccessFile[filePointersNumber];
            File file = new File(containerFilePath);
            fileSize = file.length();
            if(limitEndOfRead != 0) {
                fileSize = limitEndOfRead;
            }

            offsetBetweenPointer = (int) ((float) fileSize / (float) (filePointersNumber));

            for (int i = 0; i < filePointersNumber; i++) {
                if(i == 0 || i ==filePointersNumber -1) {
                    System.out.println("Pointer " + i + " on '" + containerFilePath + "'");
                } else if(i == 1) {
                    System.out.println("...");
                }
                pointersArray[i] = new RandomAccessFile(containerFilePath, "r");
                pointersArray[i].seek((offsetBetweenPointer) * (i + 1) - offsetBetweenPointer / 2);
            }

        }

        public RandomAccessFile getPointer(long cursorPosition) {
            int index = (int) (cursorPosition / offsetBetweenPointer);
            // System.out.println(index);
            if (index >= filePointersNumber) {
                index = filePointersNumber - 1;
            }
            return pointersArray[index];
        }

        public void close() throws IOException {
            for (int i = 0; i < filePointersNumber; i++) {
                RandomAccessFile ra = pointersArray[i];
                if (ra != null) {
                    ra.close();
                }
            }

        }

    }

    /**
     * Getter for filePointersNumber.
     * 
     * @return the filePointersNumber
     */
    public int getFilePointersNumber() {
        return this.filePointersNumberPrm;
    }

    /**
     * Sets the filePointersNumber.
     * 
     * @param filePointersNumber the filePointersNumber to set
     */
    public void setFilePointersNumber(int filePointersNumber) {
        this.filePointersNumberPrm = filePointersNumber;
    }

    /*
     * (non-Javadoc)
     * 
     * @see org.talend.designer.components.thash.io.MapHashFile#getTotalSize()
     */
    public long getTotalSize() {
        long size = 0;
        for (int i = 0; i < hashFilesNumber; i++) {
            File file = new File(this.filePath + i);
            size += file.length();
        }
        return size;
    }

    
    /**
     * Getter for limitEndOfRead.
     * @return the limitEndOfRead
     */
    public long getLimitEndOfRead() {
        return this.limitEndOfRead;
    }

    
    /**
     * Sets the limitEndOfRead.
     * @param limitEndOfRead the limitEndOfRead to set
     */
    public void setLimitEndOfRead(long limitEndOfRead) {
        this.limitEndOfRead = limitEndOfRead;
    }

    
    
}
