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
import java.util.zip.DataFormatException;
import java.util.zip.Deflater;
import java.util.zip.Inflater;

/**
 * 
 * DOC amaumont class global comment. Detailled comment <br/>
 * 
 */
class DoubleHashFileWithCompressor implements IMapHashFile {

    /**
     * 
     */
    private static final int INTEGER_BYTES_SIZE = 4;

    private static final int LONG_BYTES_SIZE = 8;

    private static final String SUFFIX_FILE_INDICES = ".idx";

    private static final String SUFFIX_FILE_DATA = ".data";

    private static final boolean THREADED = false;

    private ExecutorService executor = Executors.newSingleThreadExecutor();

    private Deflater compressor;

    private Inflater decompressor;

    private static DoubleHashFileWithCompressor instance;

    private DoubleHashFileWithCompressor() {
        if(compress) {
            // Compressor with highest level of compression
            compressor = new Deflater();
            compressor.setLevel(Deflater.BEST_SPEED);
            compressor.setStrategy(Deflater.DEFAULT_STRATEGY);
            decompressor = new Inflater();
        }

    }

    /**
     * getInstance.
     * 
     * @return the instance if this project handler
     */
    public synchronized static DoubleHashFileWithCompressor getInstance() {
        if (instance == null) {
            instance = new DoubleHashFileWithCompressor();
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

    boolean compress = false;

    public Object get(String container, long positionIdx, int hashcode) throws IOException, ClassNotFoundException {
        if (positionIdx != lastRetrievedCursorPosition) {
            fhGetIdx.seek(positionIdx);
            long positionGetData = fhGetIdx.readLong();
            fhGetData.seek(positionGetData);
            byte[] byteArray = new byte[fhGetData.readInt()];
            fhGetData.read(byteArray);

            if (compress) {
                decompressor.setInput(byteArray);
                byte[] result = new byte[byteArray.length];
                try {
                    decompressor.inflate(result);
                } catch (DataFormatException e) {
                    throw new RuntimeException(e);
                }
                decompressor.reset();
                lastRetrievedObject = new ObjectInputStream(new ByteArrayInputStream(result)).readObject();
            } else {
                lastRetrievedObject = new ObjectInputStream(new ByteArrayInputStream(byteArray)).readObject();
            }

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

        if (compress) {

            byte[] input = byteArrayOutputStream.toByteArray();

            // Give the compressor the data to compress
            compressor.setInput(input);
            compressor.finish();

            // Create an expandable byte array to hold the compressed data.
            // It is not necessary that the compressed data will be smaller than
            // the uncompressed data.
            byteArrayOutputStream = new ByteArrayOutputStream(input.length);

            // Compress the data
            byte[] buf = new byte[1024];
            while (!compressor.finished()) {
                byteArrayOutputStream.write(buf, 0, compressor.deflate(buf));
            }
            try {
                byteArrayOutputStream.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
            compressor.reset();

        }

        int dataSizeBytes = byteArrayOutputStream.size();

        final boolean[] writeEnded = new boolean[1];

        if (THREADED) {
            final int dataSizeBytesFinal = dataSizeBytes;
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
                        positionData += (INTEGER_BYTES_SIZE + dataSizeBytesFinal);
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
        if (compress) {
            compressor.end();
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
            long size1 = file.length();
            System.out.println("File " + container + SUFFIX_FILE_INDICES + ":" + size1 + " bytes");
            file.delete();
            if (fhGetData != null) {
                fhGetData.close();
            }
            file = new File(container + SUFFIX_FILE_DATA);
            long size2 = file.length();
            System.out.println("File " + container + SUFFIX_FILE_DATA + ":" + size2 + " bytes");
            System.out.println("Total size on disk : " + (size1 + size2) + " bytes");
            file.delete();
        }
        if (compress) {
            decompressor.end();
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


/*

Without compression

6030 milliseconds for 100000 objects to STORE. 16583 items/s 
Read step
Reading 0, time since last display0 s
File /tmp/talend_hash.idx:800000 bytes
File /tmp/talend_hash.data:12388890 bytes
Total size on disk : 13188890
4537 milliseconds for 100000 objects to READ. 22040  items/s 
waiting for garbage collector...
'before' heap: 1276456 bytes, 'after' heap: 3689648 bytes 
heap delta: 2413192 bytes 
size by item: 24 bytes 
Number of loops: 100000
Number of items: 100000
Time: 10 s



compressor.setLevel(Deflater.BEST_COMPRESSION);
compressor.setStrategy(Deflater.HUFFMAN_ONLY);

9987 milliseconds for 100000 objects to STORE. 10013 items/s 
Read step
Reading 0, time since last display0 s
File /tmp/talend_hash.idx:800000 bytes
File /tmp/talend_hash.data:13248205 bytes
Total size on disk : 14048205 bytes
5063 milliseconds for 100000 objects to READ. 19751  items/s 
waiting for garbage collector...
'before' heap: 1276456 bytes, 'after' heap: 3689616 bytes 
heap delta: 2413160 bytes 
size by item: 24 bytes 
Number of loops: 100000
Number of items: 100000
Time: 15 s



compressor.setLevel(Deflater.BEST_COMPRESSION);
compressor.setStrategy(Deflater.FILTERED);

9970 milliseconds for 100000 objects to STORE. 10030 items/s 
Read step
Reading 0, time since last display0 s
File /tmp/talend_hash.idx:800000 bytes
File /tmp/talend_hash.data:13248205 bytes
Total size on disk : 14048205 bytes
4932 milliseconds for 100000 objects to READ. 20275  items/s 
waiting for garbage collector...
'before' heap: 1276456 bytes, 'after' heap: 3689616 bytes 
heap delta: 2413160 bytes 
size by item: 24 bytes 
Number of loops: 100000
Number of items: 100000
Time: 14 s

*/