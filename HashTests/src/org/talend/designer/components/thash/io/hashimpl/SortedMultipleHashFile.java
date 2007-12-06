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
import gnu.trove.TObjectHashingStrategy;

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
import java.util.Map;

import org.talend.designer.components.thash.io.IMapHashFile;
import org.talend.designer.components.thash.io.beans.ILightSerializable;
import org.talend.designer.components.thash.io.beans.KeyForMap;

/**
 * 
 * DOC amaumont class global comment. Detailled comment <br/>
 * 
 */
public class SortedMultipleHashFile implements IMapHashFile {

    private static SortedMultipleHashFile instance;

    private SortedMultipleHashFile() {
    }

    /**
     * getInstance.
     * 
     * @return the instance if this project handler
     */
    public static synchronized SortedMultipleHashFile getInstance() {
        if (instance == null) {
            instance = new SortedMultipleHashFile();
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
    //private int bufferSize = 5000000;
     private int bufferSize = 10000000;

    private int bufferCount = 0;

    private int itemCountInBuffer = 0;

    private ILightSerializable[] buffer;

    private String container = null;

    ILightSerializable iLightSerializable = null;// Change this based on the Bean class;

    private Map map;

    private int beansCount;

    private int replacedObjectsCount;

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
            
//            System.out.println("array for buffer " + bufferCount + " : " + Arrays.toString(buffer));;

            // write
            DataOutputStream[] doss = new DataOutputStream[numberFiles];
            for (int i = 0; i < numberFiles; i++) {
                doss[i] = new DataOutputStream(new BufferedOutputStream(new FileOutputStream(
                        new File(getFilePath(container, i, bufferCount)))));
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
                doss[i] = new DataOutputStream(new BufferedOutputStream(new FileOutputStream(
                        new File(getFilePath(container, i, bufferCount)))));
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

        TObjectHashingStrategy objectHashingStrategy = new TObjectHashingStrategy() {

            public int computeHashCode(Object arg0) {
                return arg0 == null ? 0 : arg0.hashCode();
            }

            public boolean equals(Object arg0, Object arg1) {
                return arg1 == null ? arg0 == null : arg1.equals(arg0);
            }

        };
	
	System.gc();

        map = new THashMap<KeyForMap, KeyForMap>(beansCount, 1.0f, objectHashingStrategy);

        for (int iFinalHashFile = 0; iFinalHashFile < numberFiles; iFinalHashFile++) {
//            System.out.println(">> iFinalHashFile = " + iFinalHashFile);
            DataOutputStream dos = new DataOutputStream(new BufferedOutputStream(new FileOutputStream(new File(container + iFinalHashFile))));
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

//            System.out.println("datasSameHashcodeValue=" + datasSameHashcodeValue.toString());
            
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
                Object replacedObject = map.put(keyForMap, keyForMap);
                
                if(replacedObject != null) {
                    replacedObjectsCount++;
                }
                
                cursorPosition += (4 + bytes.length);

                //System.out.println(map.size());
                
//                System.out.println("min=" + min + " -> " + keyForMap.toString() + "  ->  " + bytes.length);

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
            dis.close();

            // delete files
            for (int k = 0; k < files.size(); k++) {
                files.get(k).delete();
            }

        }
        
        System.out.println("replacedObjectsCount = " + replacedObjectsCount);

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

        
//        System.out.println("GET cursorPosition="+cursorPosition + " hashcode="+hashcode);
        
        int fileNumber = getFileNumber(hashcode);

//        System.out.println(fileNumber);
        
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
//        System.out.println("Found:" + lastRetrievedObjectArray[fileNumber]);
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

//        System.out.println("countUniqueGet = " + countUniqueGet);
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
    
    public void setILightSerializable(ILightSerializable ils){
        this.iLightSerializable = ils;
    }

    public Map<KeyForMap, KeyForMap> getMap() {
        return map;
    }

    
                ///////////////
                //tips for use//
                ///////////////

    /////before put//////
//        SortedMultipleHashFile smh = SortedMultipleHashFile.getInstance();
//        smh.setBufferSize(10000000);//setBufferSize
//        smh.setILightSerializable(new Bean());//set an Instance of proccessed Bean;
//        String container = "D:/temp/test";
//        smh.initPut(container);

    //////do put//////
//            smh.put(container, data);

    //////endput to merge files and gain the map//////
//        smh.endPut();

    //////get the result map, it is a THashMap instance.//////
//        Map map = smh.getMap();
        
    //////the get proccess will be the same as other IMapHashFile.//////
//       ......


}
