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
package org.talend.designer.components.thash.io;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.RandomAccessFile;

/**
 * 
 * DOC amaumont class global comment. Detailled comment <br/>
 * 
 */
class MultiplePointerSimpleHashFile implements IMapHashFile {

    private static MultiplePointerSimpleHashFile instance;

    private MultiplePointerSimpleHashFile() {
    }

    /**
     * getInstance.
     * 
     * @return the instance if this project handler
     */
    public static synchronized MultiplePointerSimpleHashFile getInstance() {
        if (instance == null) {
            instance = new MultiplePointerSimpleHashFile();
        }
        return instance;
    }

    
    RandomAccessFile bw = null;

    boolean readonly;

    static final int START_POSITION = 0;

    long position;

    RandomAccessFile ra = null;

    private FileOutputStream fis;

    Object lastRetrievedObject;

    long lastRetrievedCursorPosition = -1;

    private int count;
    
    boolean threaded = true;
    
    long totalGetTime = 0;
    
    int readPointersNumber = 1000;

    private RandomAccessFile[] raArray;

    private long fileSize;

    private int offsetBetweenPointer;

    public Object get(String container, long cursorPosition, int hashcode) throws IOException, ClassNotFoundException {
        if (cursorPosition != lastRetrievedCursorPosition) {
            long timeBefore = System.currentTimeMillis();
            RandomAccessFile ra = getPointer(cursorPosition);
            ra.seek(cursorPosition);
            byte[] byteArray = new byte[ra.readInt()];
            ra.read(byteArray);
            totalGetTime += System.currentTimeMillis() - timeBefore;
            lastRetrievedObject = new ObjectInputStream(new ByteArrayInputStream(byteArray)).readObject();
            lastRetrievedCursorPosition = cursorPosition;
//            if((++count + 1) % 10000 == 0) {
//                System.out.println("totalGetTime from disk=" + totalGetTime + " ms");
//            }
            if((++count + 1) % 1000 == 0) {
                System.out.println("Total time to get 1000 items from disk=" + totalGetTime + " ms");
                totalGetTime = 0;
            }
        }
        return lastRetrievedObject;
    }

    public long put(String container, Object bean) throws IOException {

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
            bw.writeInt(sizeBytes);
            bw.write(byteArrayOutputStream.toByteArray());
        }

        byteArrayOutputStream.close();

        long returnPosition = position;

        position += (4 + sizeBytes);

        return returnPosition;
    }

    public RandomAccessFile getPointer(long cursorPosition) {
        int index = (int) (cursorPosition/offsetBetweenPointer);
//        System.out.println(index);
        if(index >= readPointersNumber) {
        	index = readPointersNumber - 1;
        }
        return raArray[index];
    }
    
    public void initPut(String container) throws IOException {
        if (!readonly) {
            File file = new File(container);
            file.delete();
            position = START_POSITION;
            bw = new RandomAccessFile(container, "rw");
        }
    }

    public void endPut() throws IOException {
        if (!readonly) {
            bw.close();
        }
    }

    public void initGet(String container) throws IOException {
        raArray = new RandomAccessFile[readPointersNumber];
        File file = new File(container);
        fileSize = file.length();
        
        offsetBetweenPointer = (int)((float)fileSize / (float)(readPointersNumber));
        
        for (int i = 0; i < readPointersNumber; i++) {
            raArray[i] = new RandomAccessFile(container, "r");
            raArray[i].seek((long)(offsetBetweenPointer) * (i + 1) - (long)offsetBetweenPointer / 2);
        }
    }

    public void endGet(String container) throws IOException {
        if (!readonly) {
            for (int i = 0; i < readPointersNumber; i++) {
                RandomAccessFile ra = raArray[i];
                if (ra != null) {
                    ra.close();
                }
            }
            File file = new File(container);
//            file.delete();
        }
    }

    /* (non-Javadoc)
     * @see org.talend.designer.components.thash.io.MapHashFile#getTotalSize()
     */
    public long getTotalSize() {
        // TODO Auto-generated method stub
        return 0;
    }

}
