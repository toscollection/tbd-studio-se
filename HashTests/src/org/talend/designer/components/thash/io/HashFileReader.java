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

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.text.NumberFormat;
import java.util.Arrays;

import org.apache.commons.lang.ArrayUtils;

/**
 * DOC slanglois class global comment. Detailled comment
 */
public class HashFileReader {

    RandomAccessFileWithPosition[] array = null;

    long[] initPositions = null;

    int pointers = 1;

    long fileSize;

    long resetAfterThisPosition;

    boolean threaded = true;

    private byte[] buffer = null;

    private int bytesToRead = 130;

    private boolean nearest = true;

    private String container;

    private long limitEndOfRead;

    public HashFileReader(String container, int pointers) throws IOException {

        this.container = container;
        this.pointers = pointers;
    }

    /**
     * DOC amaumont Comment method "init".
     * 
     * @param container
     * @param pointers
     * @throws FileNotFoundException
     * @throws IOException
     */
    public void init() throws FileNotFoundException, IOException {

        buffer = new byte[bytesToRead];

        array = new RandomAccessFileWithPosition[pointers];
        initPositions = new long[pointers];

        File file = new File(container);
        fileSize = file.length();

        if (limitEndOfRead != 0) {
            fileSize = limitEndOfRead;
        }

        long offsetBetweenPointer = (long) ((float) fileSize / (float) (pointers));

        for (int i = 0; i < pointers; i++) {

            if (i == 0 || i == pointers - 1) {
                System.out.println("Pointer " + i + " on '" + container + "'");
            } else if (i == 1) {
                System.out.println("...");
            }

            RandomAccessFile raf = new RandomAccessFile(container, "r");
            long position = (long) (offsetBetweenPointer) * (i + 1) - (long) offsetBetweenPointer / 2;
            initPositions[i] = position;
            raf.seek(position);
            array[i] = new RandomAccessFileWithPosition(raf, position);
        }

        resetAfterThisPosition = fileSize - fileSize / (2 * pointers);
    }

    public byte[] read(long position) throws IOException {
        RandomAccessFileWithPosition key = new RandomAccessFileWithPosition(null, position);

        int index = Arrays.binarySearch(array, key);

        NumberFormat numberFormat = NumberFormat.getInstance();

        boolean display = false;
        if (display) {

            System.out.println("#################################################################");
            ;
            System.out.println(ArrayUtils.toString(array));
            ;

            System.out.println("Value searched: " + numberFormat.format(position));

            System.out.println("index 1: " + index);
        }

        // here get the nearest pointer and seek to the position, index >= 0 indicate that the index's position is the
        // key's position.
        if (index < 0) {
            index = -1 - index;
            if (index == pointers) {
                index = pointers - 1;
            } else if (!nearest && array[index].position > position) {
                index--;
                if (index == -1) {
                    index = array.length - 1;
                }
            } else if (nearest && index > 0 && array[index].position - position > position - array[index - 1].position) {
                index = index - 1;
            }
            array[index].raf.seek(position);
            if (display) {
                System.out.println("index 2: " + index);
            }
        }

        byte[] result = null;

        if (bytesToRead != 0) {
            result = buffer;
        } else {
            result = new byte[array[index].raf.readInt()];
        }

        if (display) {
            System.out.println("index used " + numberFormat.format(array[index].position));
        }

        array[index].raf.read(result);
        array[index].position = position + result.length + 4;

        // determine the from index and to index for sorting.
        if (array[index].position > resetAfterThisPosition && index != pointers - 1) {
            // array[index].raf.seek(0);
            array[index].position = 0;
            Arrays.sort(array, 0, index + 1);
        } else {
            int fromIndex = index;
            int toIndex = index + 1;
            while (fromIndex > 0 && array[fromIndex - 1].position > array[index].position) {
                fromIndex--;
            }
            while (toIndex < pointers && array[toIndex].position < array[index].position) {
                toIndex++;
            }
            Arrays.sort(array, fromIndex, toIndex);
        }

        return result;

    }

    public void close() throws IOException {
        for (int i = 0; i < pointers; i++) {
            array[i].close();
        }
    }

    /**
     * DOC slanglois Comment method "scatterPointers". Scatter pointers to their initial positions.
     * 
     * @throws IOException
     */
    public void scatterPointers() throws IOException {
        for (int i = 0; i < pointers; i++) {
            array[i].raf.seek(initPositions[i]);
            array[i].position = initPositions[i];
        }
    }

    /*
     * (non-Javadoc)
     * 
     * @see java.lang.Object#toString()
     */
    public String toString() {
        String result = "";
        for (int i = 0; i < pointers; i++) {
            result += array[i].position + "    ";
        }
        return result;
    }

    /**
     * Getter for nearest.
     * 
     * @return the nearest
     */
    public boolean isNearest() {
        return this.nearest;
    }

    /**
     * Sets the nearest.
     * 
     * @param nearest the nearest to set
     */
    public void setNearest(boolean nearest) {
        this.nearest = nearest;
    }

    /**
     * DOC amaumont Comment method "setLimitSize".
     * 
     * @param i
     */
    public void setLimitEndOfRead(long limitEndOfRead) {
        this.limitEndOfRead = limitEndOfRead;
    }


    /**
     * Sets the bytesToRead.
     * 
     * @param bytesToRead the bytesToRead to set
     */
    public void setBytesToRead(int bytesToRead) {
        this.bytesToRead = bytesToRead;
    }

    
    /**
     * Getter for readFixedSizeData.
     * @return the readFixedSizeData
     */
    public boolean isReadFixedSizeData() {
        return bytesToRead != 0;
    }

    
    /**
     * Sets the readFixedSizeData.
     * @param readFixedSizeData the readFixedSizeData to set
     */
    public void setReadFixedSizeData(int bytesToRead) {
        this.bytesToRead = bytesToRead;
    }
    
    

}

/**
 * DOC slanglois class global comment. Detailled comment
 * 
 */
class RandomAccessFileWithPosition implements Comparable<RandomAccessFileWithPosition> {

    long position;

    RandomAccessFile raf = null;

    RandomAccessFileWithPosition(RandomAccessFile raf, long position) {
        super();
        this.raf = raf;
        this.position = position;
    }

    public int compareTo(RandomAccessFileWithPosition o) {
        return (int) (this.position - o.position);
    }

    void close() throws IOException {
        if (raf != null) {
            raf.close();
        }

    }

    /*
     * (non-Javadoc)
     * 
     * @see java.lang.Object#toString()
     */
    @Override
    public String toString() {
        NumberFormat instance = NumberFormat.getInstance();
        // return "raf=" + this.raf.toString() + ", position=" + this.position;
        return " " + instance.format(this.position);
    }

}
