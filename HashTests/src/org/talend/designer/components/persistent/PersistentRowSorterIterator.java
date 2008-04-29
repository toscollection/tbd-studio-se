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
import java.io.BufferedOutputStream;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;

import routines.system.IPersistableRow;

/**
 * 
 * DOC amaumont class global comment. Detailled comment <br/>
 * 
 * @param <V> object value to sort
 */
public abstract class PersistentRowSorterIterator<V extends IPersistableRow> implements IPersistentRowManager<V>, Iterator<V> {

    private static final float MARGIN_MAX = 0.35f;

    int[] bwPositionArray = null;

    boolean readonly;

    RandomAccessFile[] raArray = null;

    Object[] lastRetrievedObjectArray = null;

    long[] lastRetrievedCursorPositionArray = null;

    int countUniqueGet;

    private int bufferSize = 10000000;

    private int bufferBeanIndex = 0;

    private V[] buffer;

    IPersistableRow<V> persistableRow = null;// Change this based on the Bean class;

    private int beansCount;

    private ArrayList<Long> fileLengths;

    private ArrayList<DataInputStream> diss = new ArrayList<DataInputStream>();

    private ArrayList<File> files = new ArrayList<File>();
    
    private String container;

    private int count = 0;

    private boolean someFileStillHasRows = false;

    private ArrayList<DataContainer<V>> datas;

    private V currentObject;

    private boolean isFirstNext = true;

    private int bufferMarkLimit = - 1;

    private boolean bufferIsMarked;
    
    private boolean firstUnsifficientMemory = true;

    private boolean waitingHeapException;


    /**
     * DOC amaumont SortedMultipleHashFile constructor comment.
     */
    public PersistentRowSorterIterator(String container) {
        super();
        this.container = container;
    }
    
    protected abstract V createRowInstance();

    // ///////////////////////

    @SuppressWarnings("unchecked")
    public void initPut() {
        //System.out.println("bufferSize="+bufferSize +" objects");
        buffer = (V[]) new IPersistableRow[bufferSize];
    }

    public void put(V bean) throws IOException {

        if (!MemoryHelper.hasFreeMemory(MARGIN_MAX)) {
            if (!bufferIsMarked) {
                if (firstUnsifficientMemory) {
                    firstUnsifficientMemory = false;
                    MemoryHelper.gc();
                    if (bufferBeanIndex == 0) {
                        waitingHeapException = true;
                    }
                }
                if (!waitingHeapException && !MemoryHelper.hasFreeMemory(MARGIN_MAX)) {
                    float _10P = ((float) bufferSize) * 0.1f;
                    if ((float) bufferBeanIndex >= _10P) {
                        bufferMarkLimit = bufferBeanIndex;
                    } else {
                        bufferMarkLimit = (int) _10P;
                    }
                    //System.out.println("Buffer marked at index " + bufferMarkLimit);
                    bufferIsMarked = true;
                }
            }
        }

        if (bufferBeanIndex == bufferSize || bufferIsMarked && bufferBeanIndex == bufferMarkLimit) {// buffer is full do sort and write.
            // sort
            writeBuffer();

            bufferBeanIndex = 0;
        }
        
        buffer[bufferBeanIndex++] = bean;

        someFileStillHasRows = true;
        
        beansCount++;
    }

    public void endPut() throws IOException {
        writeRemainingData();
    }

    /**
     * DOC amaumont Comment method "writeRemainingData".
     * 
     * @throws IOException
     */
    private void writeRemainingData() throws IOException {
        if (bufferBeanIndex > 0) {
            writeBuffer();
        }
//        Arrays.fill(buffer, null);
        buffer = null;
        MemoryHelper.gc();
    }

    /**
     * sort list and then use light serialization to store Data
     * 
     * @param list
     * @throws FileNotFoundException
     * @throws IOException
     */
    public void writeBuffer() throws IOException {
        long time1 = System.currentTimeMillis();
//        System.out.println("Sorting buffer...");

        Arrays.sort(buffer, 0, bufferBeanIndex);

        long time2 = System.currentTimeMillis();
        long deltaTimeSort = (time2 - time1);
        int length = bufferBeanIndex + 1;
        int itemsPerSecSort = (int) ((float) length / (float) deltaTimeSort * 1000f);
//        System.out.println(deltaTimeSort + " milliseconds for " + length + " objects to sort in memory. " + itemsPerSecSort
//                + "  items/s ");

        time1 = System.currentTimeMillis();
//        System.out.println("Writing ordered buffer in file...");

        File file = new File(buildFilePath());
        count++;
        DataOutputStream rw = new DataOutputStream(new BufferedOutputStream(new FileOutputStream(file)));
        byte[] bytes = null;
//        System.out.println("Start write buffer ");
        for (int i = 0; i < bufferBeanIndex; i++) {
            bytes = buffer[i].toData();
            rw.writeInt(bytes.length);
            rw.write(bytes);
            //System.out.println(buffer[i]); 
        }
        rw.close();
        files.add(file);

        time2 = System.currentTimeMillis();
        long deltaTimeWrite = (time2 - time1);
        int itemsPerSecWrite = (int) ((float) length / (float) deltaTimeWrite * 1000f);
//        System.out.println(deltaTimeWrite + " milliseconds for " + length + " objects to write in file. " + itemsPerSecWrite
//                + "  items/s ");

    }

    private String buildFilePath() {
        return container + "TEMP_" + count + ".bin";
    }
    
    public void initGet() {
        
    }
    
    public boolean hasNext() {
        return someFileStillHasRows;
    }

    public V next() {

        try {
            if (isFirstNext) {
                beforeLoopFind();
                isFirstNext = false;
            }
            findNextData();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        V objectToReturn = currentObject;

        currentObject = null;

        return objectToReturn;
    }

    public void remove() {
        throw new UnsupportedOperationException();
    }

    /**
     * DOC amaumont Comment method "beforeLoopFind".
     * @throws IOException 
     * 
     * @throws IOException
     */
    private void beforeLoopFind() throws IOException {
        // File file = new File(workDirectory + "TEMP_" + count);

        // DataOutputStream dos = new DataOutputStream(new BufferedOutputStream(new FileOutputStream(file)));
        int numFiles = files.size();
        datas = new ArrayList<DataContainer<V>>();
        fileLengths = new ArrayList<Long>();

        boolean someFileStillHasRows = false;

        for (int i = 0; i < numFiles; i++) {
            diss.add(new DataInputStream(new BufferedInputStream(new FileInputStream(files.get(i)))));
            fileLengths.add(files.get(i).length());
            DataContainer<V> dc = new DataContainer<V>();
            DataInputStream dis = diss.get(i);
            byte[] bytes = new byte[dis.readInt()];
            dis.read(bytes);
            dc.object = createRowInstance();
            dc.object.loadData(bytes);
            if (!someFileStillHasRows) {
                someFileStillHasRows = true;
            }
            datas.add(dc);
        }

    }

    /**
     * DOC amaumont Comment method "findNextData".
     * @throws IOException 
     * 
     * @throws IOException
     */
    private void findNextData() throws IOException {
        DataContainer<V> min = null;
        int minIndex = 0;
        
        if(datas.size() == 0) {
            currentObject = null;
            return;
        }
        
        DataContainer<V> dataContainer = datas.get(0);

        if (dataContainer.object != null) {
            min = dataContainer;
            minIndex = 0;
        } else {
            min = null;
            minIndex = -1;
        }

        // check which one is min
        for (int i = 1; i < datas.size(); i++) {
            dataContainer = datas.get(i);

            if (min != null) {
                if (dataContainer.object != null && ((Comparable) (dataContainer.object)).compareTo(min.object) < 0) {
                    minIndex = i;
                    min = dataContainer;
                }
            } else {
                if (dataContainer.object != null) {
                    min = dataContainer;
                    minIndex = i;
                }
            }
        }

        if (minIndex < 0) {
            someFileStillHasRows = false;
        } else {
            // write to the sorted file
            // write(min.data, dos);

            currentObject = (V) min.object;

            min.reset();

            // get another data from the file
            DataInputStream dis = diss.get(minIndex);
            if (dis.available() > 0) {
                byte[] bytes = new byte[dis.readInt()];
                dis.read(bytes);
                min.object = createRowInstance();
                min.object.loadData(bytes);
                min.cursorPosition += 4 + bytes.length;
            }
            // check if one still has data
            someFileStillHasRows = false;
            for (int i = 0; i < datas.size(); i++) {
                if (datas.get(i).object != null) {
                    someFileStillHasRows = true;
                    break;
                }
            }
        }
    }

    /**
     * DOC amaumont Comment method "afterLoopFind".
     * 
     * @throws IOException
     */
    private void afterLoopFind() {
        // close all the streams
        // dos.close();
        for (int i = 0; i < diss.size(); i++) {
            try {
                diss.get(i).close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        // delete files
        for (int i = 0; i < files.size(); i++) {
            files.get(i).delete();
        }
    }

    /* (non-Javadoc)
     * @see org.talend.designer.components.thash.io.IMapHashFile#endGet(java.lang.String)
     */
    public void endGet() {
        afterLoopFind();
    }
    
    public V getNextFreeRow() {
        if(buffer.length > 0 && bufferBeanIndex != buffer.length) {
            V nextBean = (V) buffer[bufferBeanIndex];
            if(nextBean == null) {
                return createRowInstance();
            } else {
                return nextBean;
            }
        } else {
            return createRowInstance();
        }
    }

    public static void main(String[] args) {
        new PersistentRowSorterIterator<IPersistableRow>("/home/amaumont/data/dev/projets/Talend/hashfile/sort") {

            protected IPersistableRow createRowInstance() {
                return null;
            }
            
            
        };
    }
    
}
