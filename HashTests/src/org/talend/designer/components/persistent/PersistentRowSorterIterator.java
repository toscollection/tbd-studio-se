// ============================================================================
//
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
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.RandomAccessFile;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;

import org.talend.designer.components.persistent.utils.FileUtils;

import routines.system.IPersistableRow;

/**
 * 
 * DOC amaumont class global comment. Detailled comment <br/>
 * 
 * @param <V> object value to sort
 */
public abstract class PersistentRowSorterIterator<V extends IPersistableRow> implements IPersistentRowManager<V>, Iterator<V> {

    int INIT_BUFFER_INDEX = -1;

    private static final float MARGIN_MAX = 0.35f;

    int[] bwPositionArray = null;

    boolean readonly;

    RandomAccessFile[] raArray = null;

    Object[] lastRetrievedObjectArray = null;

    long[] lastRetrievedCursorPositionArray = null;

    int countUniqueGet;

    private int bufferSize = 10000000;

    // private int bufferSize = 100;

    // private int bufferSize = 20;

    private int bufferBeanIndex = INIT_BUFFER_INDEX;

    private V[] buffer;

    IPersistableRow<V> persistableRow = null;// Change this based on the Bean class;

    private int beansCount;

    private ObjectInputStream[] diss;

    private ArrayList<File> files = new ArrayList<File>();

    private String container;

    private int count = 0;

    private boolean someFileStillHasRows = false;

    private V[] datas;

    private V currentObject;

    private boolean isFirstNext = true;

    private int bufferMarkLimit = -1;

    private boolean bufferIsMarked;

    private boolean firstUnsifficientMemory = true;

    private boolean waitingHeapException;

    private boolean nextFreeRowCalled;

    /**
     * DOC amaumont SortedMultipleHashFile constructor comment.
     * 
     * @throws IOException
     */
    public PersistentRowSorterIterator(String container) throws IOException {
        super();
        this.container = container;
        FileUtils.createParentFolderIfNotExists(this.container);
    }

    /**
     * DOC amaumont SortedMultipleHashFile constructor comment.
     * 
     * @throws IOException
     */
    public PersistentRowSorterIterator(String container, int bufferSize) throws IOException {
        this(container);
        this.bufferSize = bufferSize;
    }

    protected abstract V createRowInstance();

    // ///////////////////////

    @SuppressWarnings("unchecked")
    public void initPut() {
        // System.out.println("bufferSize="+bufferSize +" objects");
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
                    // System.out.println("Buffer marked at index (1-Row) " + bufferMarkLimit);
                    bufferIsMarked = true;
                }
            }
        }

        if (nextFreeRowCalled) {
            nextFreeRowCalled = false;
        } else {
            bufferBeanIndex++;
        }

        buffer[bufferBeanIndex] = bean;

        if (bufferBeanIndex + 1 == bufferSize || bufferIsMarked && bufferBeanIndex == bufferMarkLimit) {// buffer is
            // full do
            // sort and write.
            // sort
            writeBuffer();
            if (!bufferIsMarked) {
                bufferMarkLimit = bufferBeanIndex;
                // System.out.println("Buffer marked at index (2-Row) " + bufferMarkLimit);
                bufferIsMarked = true;
            }
            bufferBeanIndex = INIT_BUFFER_INDEX;
        }

        someFileStillHasRows = true;

        beansCount++;
    }

    public void endPut() throws IOException {
        if (bufferBeanIndex > INIT_BUFFER_INDEX) {
            writeBuffer();
        }
        buffer = null;
    }

    /**
     * sort list and then use light serialization to store Data
     * 
     * @param list
     * @throws FileNotFoundException
     * @throws IOException
     */
    public void writeBuffer() throws IOException {
        // long time1 = System.currentTimeMillis();
        // System.out.println("Sorting buffer...");

        Arrays.sort(buffer, 0, bufferBeanIndex + 1);

        // long time2 = System.currentTimeMillis();
        // long deltaTimeSort = (time2 - time1);
        // int length = bufferBeanIndex + 1;
        // int itemsPerSecSort = (int) ((float) length / (float) deltaTimeSort * 1000f);
        // System.out.println(deltaTimeSort + " milliseconds for " + length + " objects to sort in memory. " +
        // itemsPerSecSort
        // + " items/s ");

        // time1 = System.currentTimeMillis();
        // System.out.println("Writing ordered buffer in file...");

        File file = new File(buildFilePath());
        count++;
        ObjectOutputStream rw = new ObjectOutputStream(new BufferedOutputStream(new FileOutputStream(file)));
        // System.out.println("Start write buffer ");
        for (int i = 0; i < bufferBeanIndex + 1; i++) {
            buffer[i].writeData(rw);
            // System.out.println(buffer[i]);
        }
        rw.close();
        files.add(file);

        // time2 = System.currentTimeMillis();
        // long deltaTimeWrite = (time2 - time1);
        // int itemsPerSecWrite = (int) ((float) length / (float) deltaTimeWrite * 1000f);
        // System.out.println(deltaTimeWrite + " milliseconds for " + length + " objects to write in file. " +
        // itemsPerSecWrite
        // + " items/s ");

    }

    private String buildFilePath() {
        return container + "_TEMP_" + count + ".bin";
    }

    public void initGet() {
        bufferBeanIndex = INIT_BUFFER_INDEX;
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
     * 
     * @throws IOException
     * 
     * @throws IOException
     */
    private void beforeLoopFind() throws IOException {
        // File file = new File(workDirectory + "TEMP_" + count);

        // DataOutputStream dos = new DataOutputStream(new BufferedOutputStream(new FileOutputStream(file)));
        int numFiles = files.size();
        List<V> datasList = new ArrayList<V>();
        List<ObjectInputStream> dissList = new ArrayList<ObjectInputStream>();

        boolean someFileStillHasRows = false;

        bufferBeanIndex = INIT_BUFFER_INDEX;

        for (int i = 0; i < numFiles; i++) {
            ObjectInputStream dis = new ObjectInputStream(new BufferedInputStream(new FileInputStream(files.get(i))));
            dissList.add(dis);
            // V bean = getNextFreeRow();
            V bean = createRowInstance();
            bean.readData(dis);
            if (!someFileStillHasRows) {
                someFileStillHasRows = true;
            }
            datasList.add(bean);
        }

        int size = datasList.size();
        datas = (V[]) datasList.toArray(new IPersistableRow[size]);
        diss = (ObjectInputStream[]) dissList.toArray(new ObjectInputStream[size]);

    }

    /**
     * DOC amaumont Comment method "findNextData".
     * 
     * @throws IOException
     * 
     * @throws IOException
     */
    private void findNextData() throws IOException {
        V min = null;
        int minIndex = 0;

        if (datas.length == 0) {
            currentObject = null;
            return;
        }

        V bean = datas[0];

        if (bean != null) {
            min = bean;
            minIndex = 0;
        } else {
            min = null;
            minIndex = -1;
        }

        // check which one is min
        for (int i = 1; i < datas.length; i++) {
            bean = datas[i];

            if (min != null) {
                if (bean != null && ((Comparable) (bean)).compareTo(min) < 0) {
                    minIndex = i;
                    min = bean;
                }
            } else {
                if (bean != null) {
                    min = bean;
                    minIndex = i;
                }
            }
        }

        if (minIndex < 0) {
            someFileStillHasRows = false;
        } else {

            currentObject = min;

            // get another data from the file
            ObjectInputStream dis = diss[minIndex];
            if (dis.available() > 0) {
                // bean = getNextFreeRow();
                bean = createRowInstance();
                bean.readData(dis);
                datas[minIndex] = bean;
            } else {
                datas[minIndex] = null;
            }

            // check if one still has data
            someFileStillHasRows = false;
            for (int i = 0; i < datas.length; i++) {
                if (datas[i] != null) {
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

        buffer = null;

        if (diss != null) {
            for (int i = 0; i < diss.length; i++) {
                try {
                    diss[i].close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        // delete files
        for (int i = 0; i < files.size(); i++) {
            files.get(i).delete();
        }

    }

    public V getNextFreeRow() {

        if (buffer.length > 0 && bufferBeanIndex + 1 < buffer.length) {
            bufferBeanIndex++;
            nextFreeRowCalled = true;
            V nextBean = (V) buffer[bufferBeanIndex];
            if (nextBean == null) {
                return createRowInstance();
            } else {
                return nextBean;
            }
        } else {
            return createRowInstance();
        }
    }

    /*
     * (non-Javadoc)
     * 
     * @see org.talend.designer.components.thash.io.IMapHashFile#endGet(java.lang.String)
     */
    public void endGet() {
        afterLoopFind();
    }

    public static void main(String[] args) throws IOException {
        new PersistentRowSorterIterator<IPersistableRow>("/home/amaumont/data/dev/projets/Talend/hashfile/sort") {

            protected IPersistableRow createRowInstance() {
                return null;
            }

        };
    }

}
