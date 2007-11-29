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
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * 
 * DOC amaumont  class global comment. Detailled comment
 */
class DoubleHashFile implements IMapHashFile {

    /**
     * 
     */
    private static final int INTEGER_BYTES_SIZE = 4;

    private static final int LONG_BYTES_SIZE = 8;

    private static final String SUFFIX_FILE_INDICES = ".idx";

    private static final String SUFFIX_FILE_DATA = ".data";

    private static final boolean THREADED = false;

    private ExecutorService executor = Executors.newSingleThreadExecutor();

    private static DoubleHashFile instance;

    private DoubleHashFile() {
    }

    /**
     * getInstance.
     * 
     * @return the instance if this project handler
     */
    public synchronized static DoubleHashFile getInstance() {
        if (instance == null) {
            instance = new DoubleHashFile();
        }
        return instance;
    }

    RandomAccessFile fhPutIdx = null;

    RandomAccessFile fhPutData = null;

    boolean readonly;

    static final int START_POSITION = 0;

    long positionData;

    int positionIdx;

    RandomAccessFile fhGetIdx = null;

    RandomAccessFile fhGetData = null;

    private FileOutputStream fis;

    private Object lastRetrievedObject;

    private int lastRetrievedCursorPosition = -1;

    public Object get(String container, long positionIdx, int hashcode) throws IOException, ClassNotFoundException {
        if (positionIdx != lastRetrievedCursorPosition) {
            fhGetIdx.seek(positionIdx);
            long positionGetData = fhGetIdx.readLong();
            fhGetData.seek(positionGetData);
            byte[] byteArray = new byte[fhGetData.readInt()];
            fhGetData.read(byteArray);
            lastRetrievedObject = new ObjectInputStream(new ByteArrayInputStream(byteArray)).readObject();
            lastRetrievedCursorPosition = (int) positionIdx;
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

        final int dataSizeBytes = byteArrayOutputStream.size();

        final boolean[] writeEnded = new boolean[1];

        if (THREADED) {
            executor.execute(new Runnable() {

                /*
                 * (non-Javadoc)
                 * 
                 * @see java.lang.Runnable#run()
                 */
                public void run() {
                    try {
                        if (!readonly) {
                            try {
                                fhPutIdx.writeLong(positionData);
                            } catch (IOException e) {
                                e.printStackTrace();
                            }
                        }
                        positionData += (INTEGER_BYTES_SIZE + dataSizeBytes);
                    } catch (RuntimeException e) {
                        // TODO Auto-generated catch block
                        e.printStackTrace();
                    } finally {
                        writeEnded[0] = true;
                    }
                }

            });
        } else {
            if (!readonly) {
                fhPutIdx.writeLong(positionData);
            }
            positionData += (INTEGER_BYTES_SIZE + dataSizeBytes);
        }

        if (!readonly) {
            fhPutData.writeInt(dataSizeBytes);
            fhPutData.write(byteArrayOutputStream.toByteArray());
        }

        byteArrayOutputStream.close();

        int returnPositionIdx = positionIdx;

        positionIdx += LONG_BYTES_SIZE;

        if (THREADED) {
            try {
                while (!writeEnded[0]) {
                    Thread.sleep(5);
                }
            } catch (InterruptedException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
        }
        return returnPositionIdx;
    }

    public void initPut(String container) throws IOException {
        if (!readonly) {
            File file = new File(container + SUFFIX_FILE_INDICES);
            file.delete();
            file = new File(container + SUFFIX_FILE_DATA);
            file.delete();
            positionData = START_POSITION;
            fhPutIdx = new RandomAccessFile(container + SUFFIX_FILE_INDICES, "rw");
            fhPutData = new RandomAccessFile(container + SUFFIX_FILE_DATA, "rw");
        }
    }

    public void endPut() throws IOException {
        if (!readonly) {
            fhPutIdx.close();
            fhPutData.close();
        }
    }

    public void initGet(String container) throws FileNotFoundException {
        fhGetIdx = new RandomAccessFile(container + SUFFIX_FILE_INDICES, "r");
        fhGetData = new RandomAccessFile(container + SUFFIX_FILE_DATA, "r");
    }

    public void endGet(String container) throws IOException {
        if (!readonly) {
            if (fhGetIdx != null) {
                fhGetIdx.close();
            }
            File file = new File(container + SUFFIX_FILE_INDICES);
            file.delete();
            if (fhGetData != null) {
                fhGetData.close();
            }
            file = new File(container + SUFFIX_FILE_DATA);
            file.delete();
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
