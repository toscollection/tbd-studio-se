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
import java.util.List;

import org.talend.designer.components.thash.io.IMapHashFile;
import org.talend.designer.components.thash.io.beans.ILightSerializable;
import org.talend.designer.components.thash.io.beans.KeyForMap;

/**
 * 
 * DOC slanglois class global comment. Detailled comment <br/>
 * 
 */
public class PersistentAdvancedLookup extends NewAdvancedLookup<Object> implements IMapHashFile {

    /*
     * (non-Javadoc)
     * 
     * @see org.talend.designer.components.thash.io.hashimpl.NewAdvancedLookup#getResultArray()
     */
    public Object[] getResultArray() {
        return getResultList().toArray();
    }

    /*
     * (non-Javadoc)
     * 
     * @see org.talend.designer.components.thash.io.hashimpl.NewAdvancedLookup#getResultList()
     */
    public List<Object> getResultList() {
        List<Object> keyForMaps = super.getResultList();
        List<Object> resultList = new ArrayList<Object>();
        for (int index = 0; index < keyForMaps.size(); index++) {
            KeyForMap keyForMap = (KeyForMap) keyForMaps.get(index);
            try {
                resultList.add(this.get(container, keyForMap.cursorPosition, keyForMap.hashcode));
            } catch (IOException e) {
                e.printStackTrace();
            } catch (ClassNotFoundException e) {
                e.printStackTrace();
            }
        }
        return resultList;
    }

    /*
     * (non-Javadoc)
     * 
     * @see org.talend.designer.components.thash.io.hashimpl.NewAdvancedLookup#getResultObject()
     */
    public Object getResultObject() {
        Object o = super.getResultObject();
        if (o == null) {
            return null;
        }

        KeyForMap keyForMap = (KeyForMap) o;

        Object resultObject = null;
        try {
            resultObject = this.get(container, keyForMap.cursorPosition, keyForMap.hashcode);
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return resultObject;
    }

    private PersistentAdvancedLookup(MATCHING_MODE matchingMode, boolean keepAllValues, boolean countValuesForEachKey) {
        super(matchingMode, keepAllValues, countValuesForEachKey);
    }

    private static PersistentAdvancedLookup instance;

    /**
     * getInstance.
     * 
     * @return the instance if this project handler
     */
    public static synchronized PersistentAdvancedLookup getInstance(MATCHING_MODE matchingMode, boolean keepAllValues,
            boolean countValuesForEachKey) {
        if (instance == null) {
            instance = new PersistentAdvancedLookup(matchingMode, keepAllValues, countValuesForEachKey);
        }
        return instance;
    }

    int[] bwPositionArray = null;

    boolean readonly;

    int numberFiles = 10;

    RandomAccessFile[] raArray = null;

    Object[] lastRetrievedObjectArray = null;

    long[] lastRetrievedCursorPositionArray = null;

    int countUniqueGet;

    // ////////////////////////
    // private int bufferSize = 5000000;
    private int bufferSize = 10000000;

    private int bufferCount = 0;

    private int itemCountInBuffer = 0;

    private ILightSerializable[] buffer;

    private String container = null;

    ILightSerializable iLightSerializable = null;// Change this based on the Bean class;

    private int beansCount;

    // private int replacedObjectsCount;

    // ///////////////////////

    /**
     * DOC amaumont Comment method "getFileNumber".
     * 
     * @param hashcode
     * @return
     */
    private int getFileNumber(int hashcode) {
        return Math.abs(hashcode) % numberFiles;
    }

    /**
     * DOC amaumont Comment method "getFilePath".
     * 
     * @param container
     * @param i
     * @param j
     * @return
     */
    private String getFilePath(String container, int i, int j) {
        return container + "_" + i + "_" + j;
    }

    public void initPut(String container) throws IOException {
        this.container = container;
        buffer = new ILightSerializable[bufferSize];
    }

    public long put(String container, Object bean) throws IOException {
        ILightSerializable item = (ILightSerializable) bean;

        if (itemCountInBuffer >= bufferSize) {// buffer is full do sort and write.
            // sort
            Arrays.sort(buffer, 0, itemCountInBuffer);

            // System.out.println("array for buffer " + bufferCount + " : " + Arrays.toString(buffer));;

            // write
            DataOutputStream[] doss = new DataOutputStream[numberFiles];
            for (int i = 0; i < numberFiles; i++) {
                doss[i] = new DataOutputStream(new BufferedOutputStream(new FileOutputStream(new File(getFilePath(container, i,
                        bufferCount)))));
            }

            int fileNumber = 0;
            byte[] bytes = null;
            for (int i = 0; i < itemCountInBuffer; i++) {
                fileNumber = getFileNumber(buffer[i].hashCode());
                bytes = buffer[i].toByteArray();
                doss[fileNumber].writeInt(bytes.length);
                doss[fileNumber].write(bytes);
            }

            for (int i = 0; i < numberFiles; i++) {
                doss[i].close();
            }

            bufferCount++;

            // clear buffer
            Arrays.fill(buffer, 0, itemCountInBuffer, null);

            System.gc();

            itemCountInBuffer = 0;
        }
        buffer[itemCountInBuffer++] = item;

        beansCount++;

        return -1;

    }

    /*
     * (non-Javadoc)
     * 
     * @see org.talend.designer.components.thash.io.IMapHashFile#endPut()
     */
    public void endPut() throws IOException {
        if (itemCountInBuffer > 0) {
            // sort
            Arrays.sort(buffer, 0, itemCountInBuffer);

            // write
            DataOutputStream[] doss = new DataOutputStream[numberFiles];
            for (int i = 0; i < numberFiles; i++) {
                doss[i] = new DataOutputStream(new BufferedOutputStream(new FileOutputStream(new File(getFilePath(container, i,
                        bufferCount)))));
            }

            int fileNumber = 0;
            byte[] bytes = null;
            for (int i = 0; i < itemCountInBuffer; i++) {
                fileNumber = getFileNumber(buffer[i].hashCode());
                bytes = buffer[i].toByteArray();
                doss[fileNumber].writeInt(bytes.length);
                doss[fileNumber].write(bytes);
            }

            for (int i = 0; i < numberFiles; i++) {
                doss[i].close();
            }

            bufferCount++;
        }
        buffer = null;

        System.gc();

        for (int iFinalHashFile = 0; iFinalHashFile < numberFiles; iFinalHashFile++) {
            // System.out.println(">> iFinalHashFile = " + iFinalHashFile);
            DataOutputStream dos = new DataOutputStream(new BufferedOutputStream(new FileOutputStream(new File(container
                    + iFinalHashFile))));
            int cursorPosition = 0;

            List<File> files = new ArrayList<File>();
            for (int iDivHashFile = 0; iDivHashFile < bufferCount; iDivHashFile++) {
                files.add(new File(getFilePath(container, iFinalHashFile, iDivHashFile)));
            }

            int numFiles = files.size();
            List<DataInputStream> diss = new ArrayList<DataInputStream>();
            List<ILightSerializable> datasSameHashcodeValue = new ArrayList<ILightSerializable>();
            List<Long> positions = new ArrayList<Long>();
            List<Long> fileLengths = new ArrayList<Long>();

            byte[] bytes = null;
            DataInputStream dis = null;
            int fileCount = 0;
            for (int iDivHashFile = 0; iDivHashFile < numFiles; iDivHashFile++) {
                dis = new DataInputStream(new BufferedInputStream(new FileInputStream(files.get(iDivHashFile))));
                long fileLength = files.get(iDivHashFile).length();
                if (0 < fileLength) {
                    bytes = new byte[dis.readInt()];
                    dis.read(bytes);
                    datasSameHashcodeValue.add(iLightSerializable.createInstance(bytes));
                    diss.add(dis);
                    fileLengths.add(fileLength);
                    positions.add((long) (4 + bytes.length));
                    fileCount++;
                }
            }

            // System.out.println("datasSameHashcodeValue=" + datasSameHashcodeValue.toString());

            bytes = null;
            dis = null;
            ILightSerializable dc = null;
            long position = -1;

            while (fileCount > 0) {
                ILightSerializable min = null;
                int minIndex = 0;
                min = datasSameHashcodeValue.get(0);

                // check which one is min
                for (int k = 1; k < fileCount; k++) {
                    dc = datasSameHashcodeValue.get(k);

                    if (dc.compareTo(min) < 0) {
                        minIndex = k;
                        min = dc;
                    }
                }

                // write to the sorted file
                bytes = min.toByteArray();
                dos.writeInt(bytes.length);
                dos.write(bytes);
                KeyForMap keyForMap = new KeyForMap(cursorPosition, min.hashCode());
                System.out.println(keyForMap);
                super.put(keyForMap);

                cursorPosition += (4 + bytes.length);

                // System.out.println(map.size());

                // System.out.println("min=" + min + " -> " + keyForMap.toString() + " -> " + bytes.length);

                bytes = null;

                // get another data from the file
                position = positions.get(minIndex);
                dis = diss.get(minIndex);
                if (position < fileLengths.get(minIndex)) {
                    bytes = new byte[dis.readInt()];
                    dis.read(bytes);
                    datasSameHashcodeValue.set(minIndex, iLightSerializable.createInstance(bytes));
                    positions.set(minIndex, position + 4 + bytes.length);
                    bytes = null;
                } else {
                    dis.close();
                    diss.remove(minIndex);
                    datasSameHashcodeValue.remove(minIndex);
                    positions.remove(minIndex);
                    fileLengths.remove(minIndex);
                    fileCount--;
                }
            }

            // close all the streams
            dos.close();
            if (dis != null)
                dis.close();

            // delete files
            for (int k = 0; k < files.size(); k++) {
                files.get(k).delete();
            }

        }

    }

    public void initGet(String container) throws FileNotFoundException {
        raArray = new RandomAccessFile[numberFiles];
        lastRetrievedCursorPositionArray = new long[numberFiles];
        lastRetrievedObjectArray = new Object[numberFiles];
        for (int i = 0; i < numberFiles; i++) {
            raArray[i] = new RandomAccessFile(container + i, "r");
            lastRetrievedCursorPositionArray[i] = -1;
        }
    }

    public Object get(String container, long cursorPosition, int hashcode) throws IOException, ClassNotFoundException {

        // System.out.println("GET cursorPosition="+cursorPosition + " hashcode="+hashcode);

        int fileNumber = getFileNumber(hashcode);

        // System.out.println(fileNumber);

        RandomAccessFile ra = raArray[fileNumber];

        if (cursorPosition != lastRetrievedCursorPositionArray[fileNumber]) {
            ++countUniqueGet;
            ra.seek(cursorPosition);
            int readInt = ra.readInt();
            byte[] byteArray = new byte[readInt];
            ra.read(byteArray);

            lastRetrievedObjectArray[fileNumber] = iLightSerializable.createInstance(byteArray);
            lastRetrievedCursorPositionArray[fileNumber] = cursorPosition;
        }
        // System.out.println("Found:" + lastRetrievedObjectArray[fileNumber]);
        return lastRetrievedObjectArray[fileNumber];
    }

    public void endGet(String container) throws IOException {
        if (!readonly) {
            for (int i = 0; i < numberFiles; i++) {
                RandomAccessFile ra = raArray[i];
                if (ra != null) {
                    ra.close();
                }
                File file = new File(container + i);
                // file.delete();
            }
        }

        // System.out.println("countUniqueGet = " + countUniqueGet);
    }

    /*
     * (non-Javadoc)
     * 
     * @see org.talend.designer.components.thash.io.MapHashFile#getTotalSize()
     */
    public long getTotalSize() {
        // TODO Auto-generated method stub
        return 0;
    }

    public int getBufferSize() {
        return bufferSize;
    }

    public void setBufferSize(int bufferSize) {
        this.bufferSize = bufferSize;
    }

    public void setILightSerializable(ILightSerializable ils) {
        this.iLightSerializable = ils;
    }

}
