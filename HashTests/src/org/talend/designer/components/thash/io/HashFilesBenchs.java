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

import gnu.trove.THashMap;
import gnu.trove.TObjectHashingStrategy;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.NumberFormat;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;

import org.talend.designer.components.thash.Sizeof;
import org.talend.designer.components.thash.io.beans.Bean;
import org.talend.designer.components.thash.io.beans.KeyForMap;
import org.talend.designer.components.thash.io.hashimpl.BerkeleyDBHashByCRC;
import org.talend.designer.components.thash.io.hashimpl.BerkeleyDBHashById;
import org.talend.designer.components.thash.io.hashimpl.MultiPointersMultiHashFiles;
import org.talend.designer.components.thash.io.hashimpl.SortedMultipleHashFile;
import org.talend.designer.components.thash.io.hashimpl.SqliteDBHash;

/**
 * DOC amaumont class global comment. Detailled comment <br/>
 * 
 * 
 * 
 * 
 * 
 * 
 */
public class HashFilesBenchs {

    public static IMapHashFile hashFile;

    public static void main(String[] args) throws Exception {

        new HashFilesBenchs();

    }

    boolean randomWrite = true;

    boolean randomRead = false;

    boolean readonly = false;

    boolean readAfterStore = true;

    String filePath = "/home/amaumont/hash_benchs/talend_hash";

    String folderStatsPath = "/home/amaumont/hash_benchs/";

    String fileHashBenchsBaseName = "HashBenchs4";

    private Map hashMap;

    private long time1;

    private long time2;

    private FileOutputStream fileData;

    private int beanReads;

    /**
     * DOC amaumont HashFilesBenchs class global comment. Detailled comment
     */
    enum PERSISTENT_METHOD {
        SORTED_MULTIPLE_HASH("Sorted multiple hash", "/home/amaumont/hash_benchs/external_sort/tmp_"),
        FIXED_AREA_POINTERS("Fixed area pointers", "/home/amaumont/hash_benchs/talend_hash_perfs_beans_n"),
        TURNING_POINTERS_NEAREST("Turning pointers nearest", "/home/amaumont/hash_benchs/talend_hash_perfs_beans_0"),
        TURNING_POINTERS_NEXT("Turning pointers next", "/home/amaumont/hash_benchs/talend_hash_perfs_beans_0"),
        SQL_LITE_DB("Sql lite", "/home/amaumont/hash_benchs/sql_lite_bench.db"),
        BERKELEY_DB_BY_ID("Berkeley DB by id", "hash_benchs_by_id"),
        BERKELEY_DB_BY_CRC("Berkeley DB by CEC", "hash_benchs_by_crc"),

        ;

        private String filePath;

        private String label;

        PERSISTENT_METHOD(String label, String filePath) {
            this.label = label;
            this.filePath = filePath;
        }

        /**
         * Getter for filePath.
         * 
         * @return the filePath
         */
        public String getFilePath() {
            return this.filePath;
        }

        /**
         * Sets the filePath.
         * 
         * @param filePath the filePath to set
         */
        public void setFilePath(String filePath) {
            this.filePath = filePath;
        }

        /**
         * Getter for label.
         * 
         * @return the label
         */
        public String getLabel() {
            return this.label;
        }

        /**
         * Sets the label.
         * 
         * @param label the label to set
         */
        public void setLabel(String label) {
            this.label = label;
        }

    }

    /**
     * DOC amaumont ReliabilityHashMapFileTest constructor comment.
     * 
     * @throws Exception
     * @throws ClassNotFoundException
     * @throws IOException
     */
    public HashFilesBenchs() throws IOException, ClassNotFoundException, Exception {
        super();

        int[] nbItemsArray = new int[] {

//         100, // 100
        // 10000, // 10 k
        // 100000, // 100 k
//        1000000, // 1 M
        // 10000000, // 10 M
        // 20000000, // 20 M
        // 25000000, // 25 M
        // 30000000, // 30 M
        // 55000000, // 55 M
         60000000, // 60 M
        // 100000000, // 100 M
        };

        int[] nbFilesArray = new int[] { 1,
        // 10,
        // 20,
        // 40,
        // 60,
        // 80,
        // 100,
        // 150,
        };// , };

        int[] pointersByFileArray = new int[] { 1,
        // 10,
        // 20,
        // 40,
        // 60,
        // 80,
        // 100,
        // 200,
        // 400,
        // 800,
        // 1000

        };// , };

        PERSISTENT_METHOD[] testCases = new PERSISTENT_METHOD[] {
        //
        PERSISTENT_METHOD.SORTED_MULTIPLE_HASH,
        // PERSISTENT_METHOD.FIXED_AREA_POINTERS,
        // PERSISTENT_METHOD.TURNING_POINTERS_NEAREST,
        // PERSISTENT_METHOD.TURNING_POINTERS_NEXT,
        // PERSISTENT_METHOD.SQL_LITE_DB,
        // PERSISTENT_METHOD.BERKELEY_DB_BY_ID,
        // PERSISTENT_METHOD.BERKELEY_DB_BY_CRC,

        };

        StringBuilder sbPreview = new StringBuilder();

        NumberFormat nf = NumberFormat.getInstance();

        sbPreview.append("nbItems list: ");
        for (int i = 0; i < nbItemsArray.length; i++) {
            int j = nbItemsArray[i];
            sbPreview.append(nf.format(j));
            sbPreview.append("  |  ");
        }
        sbPreview.append("\n");

        sbPreview.append("nbFiles list: ");
        for (int i = 0; i < nbFilesArray.length; i++) {
            int j = nbFilesArray[i];
            sbPreview.append(nf.format(j));
            sbPreview.append("  |  ");
        }
        sbPreview.append("\n");

        sbPreview.append("pointersByFile list: ");
        for (int i = 0; i < pointersByFileArray.length; i++) {
            int j = pointersByFileArray[i];
            sbPreview.append(nf.format(j));
            sbPreview.append("  |  ");
        }
        sbPreview.append("\n");

        System.out.println(sbPreview.toString());
        System.out.println("randomRead=" + randomRead);

        openDataFile();

        try {

            for (int i = 0; i < testCases.length; i++) {
                PERSISTENT_METHOD persistMethod = testCases[i];
                System.out.println("Current method: " + persistMethod.label + "(" + persistMethod.toString() + ")");

                for (int nbItemsIndex = 0; nbItemsIndex < nbItemsArray.length; nbItemsIndex++) {
                    int nbItems = nbItemsArray[nbItemsIndex];

                    System.out.println("Current items number: " + nf.format(nbItems));

                    for (int j = 0; j < nbFilesArray.length; j++) {
                        int nbFiles = -1;

                        switch (persistMethod) {
                        case SORTED_MULTIPLE_HASH:
                            nbFiles = nbFilesArray[j];
                            // hashFile = new MultiPointersMultiHashFiles(filePath, nbFiles);
                            hashFile = SortedMultipleHashFile.getInstance();
                            // ((SortedMultipleHashFile) hashFile).setBufferSize(nbItems / 10);// setBufferSize
                            ((SortedMultipleHashFile) hashFile).setILightSerializable(new Bean());// set an Instance
                            // of proccessed
                            // Bean;
                            break;

                        case FIXED_AREA_POINTERS:
                            nbFiles = nbFilesArray[j];
                            hashFile = new MultiPointersMultiHashFiles(filePath, nbFiles);
                            break;

                        case TURNING_POINTERS_NEAREST:
                        case TURNING_POINTERS_NEXT:
                            nbFiles = 1;
                            throw new UnsupportedOperationException();
                            // break;

                        case SQL_LITE_DB:
                            nbFiles = 1;
                            hashFile = SqliteDBHash.getInstance();
                            break;

                        case BERKELEY_DB_BY_ID:
                            nbFiles = 1;
                            hashFile = new BerkeleyDBHashById(persistMethod.filePath);
                            break;

                        case BERKELEY_DB_BY_CRC:
                            nbFiles = 1;
                            hashFile = new BerkeleyDBHashByCRC(persistMethod.filePath);
                            break;

                        default:
                            break;
                        }

                        System.out.println("Current files number: " + nf.format(nbFiles));
                        DataBench dataWrite = new DataBench();

                        dataWrite.setPersistentMethod(persistMethod);
                        dataWrite.setNbFiles(nbFiles);
                        if (!readonly && persistMethod != PERSISTENT_METHOD.BERKELEY_DB_BY_CRC) {
                            try {
                                launchWriteBenchs(persistMethod, nbItems, nbFiles, dataWrite);
                                dataWrite.setWriteEndedWithSuccess(true);
                            } catch (Throwable e) {
                                e.printStackTrace();
                                dataWrite.setWriteError(e.getMessage() + ":" + e.getStackTrace()[0]);
                                writeData(dataWrite);
                                continue;
                            }
                        }

                        for (int pointersByFileIdx = 0; pointersByFileIdx < pointersByFileArray.length; pointersByFileIdx++) {
                            DataBench dataReadWrite = (DataBench) dataWrite.clone();
                            int pointersByFile = pointersByFileArray[pointersByFileIdx];
                            System.out.println("Current items number: " + nf.format(nbItems));
                            System.out.println("Current files number: " + nf.format(nbFiles));
                            System.out.println("Current pointersByFile number: " + nf.format(pointersByFile));
                            try {
                                launchReadBenchs(persistMethod, nbItems, nbFiles, pointersByFile, dataReadWrite);
                                dataReadWrite.setReadEndedWithSuccess(true);
                            } catch (Throwable e) {
                                e.printStackTrace();
                                dataReadWrite.setReadError(e.getMessage() + ":" + e.getStackTrace()[0]);
                                continue;
                            } finally {
                                writeData(dataReadWrite);
                            }
                        }

                        hashMap = null;

                        // System.out.println("countReturnFalse1=" + Bean.countReturnFalse1);
                        // System.out.println("waiting for garbage collector...");
                        // Sizeof.runGC();
                        // long heap2 = Sizeof.usedMemory(); // Take a before heap snapshot
                        //
                        // final int size = Math.round(((float) (heap2 - heap1)) / loop);
                        // System.out.println("'before' heap: " + heap1 + " bytes, 'after' heap: " + heap2 + " bytes ");
                        // //
                        // not
                        // needed
                        // // here
                        // System.out.println("heap delta: " + (heap2 - heap1) + " bytes ");
                        // System.out.println("size by item: " + size + " bytes ");
                        // System.out.println("Number of loops: " + loop);
                        // System.out.println("Number of items: " + hashMap.size());
                        // System.out.println("Read randomized: " + randomRead);
                        // System.out.println("Hash class used: " + hashFile.getClass().getName());
                        // if (loop != hashMap.size()) {
                        // System.out.println("WARNING: loops number is different of items number !");
                        // }
                        // System.out.println("Time: " + ((time2 - time1) / 1000) + " s");

                        boolean breakForFiles = false;

                        switch (persistMethod) {
                        case FIXED_AREA_POINTERS:
                            break;

                        case TURNING_POINTERS_NEAREST:
                        case TURNING_POINTERS_NEXT:
                        case SQL_LITE_DB:
                        case BERKELEY_DB_BY_ID:

                            breakForFiles = true;

                            break;

                        default:
                            break;
                        }

                        if (breakForFiles) {
                            break;
                        }

                    }

                }
            }
        } finally {
            closeDataFile();
        }

    }

    /**
     * DOC amaumont Comment method "openDataFile".
     * 
     * @throws IOException
     */
    private void openDataFile() throws IOException {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy_MM_dd_HH'h'mm'm'ss's'");

        String filePath = folderStatsPath
        // + sdf.format(new Date())
                + "_" + fileHashBenchsBaseName + ".csv";

        File file = new File(filePath);
        boolean exists = file.exists();

        try {
            this.fileData = new FileOutputStream(filePath, true);
            if (!exists) {
                this.fileData.write(DataBench.getFileHeader().getBytes());
                this.fileData.write('\n');
            }
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        }

    }

    /**
     * DOC amaumont Comment method "closeDataFile".
     */
    private void closeDataFile() {
        try {
            this.fileData.close();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * DOC amaumont Comment method "writeInFile".
     * 
     * @param dataReadWrite
     * @throws IOException
     */
    private void writeData(DataBench dataReadWrite) throws IOException {
        this.fileData.write(dataReadWrite.toFileRow().getBytes());
        this.fileData.write('\n');
    }

    private void launchWriteBenchs(PERSISTENT_METHOD persistMethod, int nbItems, int nbFiles, DataBench dataWrite)
            throws Exception, IOException, ClassNotFoundException {

        System.out.println("Write step");

        int[] randomArray = null;
        
        if(randomWrite) {
            randomArray = new int[nbItems];
            for (int i = 0; i < randomArray.length; i++) {
                randomArray[i] = i;
            }
            int j = 0;
            Random rand = new Random(System.currentTimeMillis());
            // shiffle unique values
            for (int i = 0; i < randomArray.length; i++) {
                j = rand.nextInt(nbItems);
                int vj = randomArray[j];
                randomArray[j] = randomArray[i];
                randomArray[i] = vj;
            }
        }
        
        
        Sizeof.runGC();
        long heap1 = Sizeof.usedMemory(); // Take a before heap snapshot

        long start = System.currentTimeMillis();
        dataWrite.setStartWriteDate(new Date(start));

        // time1 = System.currentTimeMillis();

        TObjectHashingStrategy objectHashingStrategy = new TObjectHashingStrategy() {

            /*
             * (non-Javadoc)
             * 
             * @see gnu.trove.TObjectHashingStrategy#computeHashCode(java.lang.Object)
             */
            public int computeHashCode(Object o) {
                return o == null ? 0 : o.hashCode();
            }

            /*
             * (non-Javadoc)
             * 
             * @see gnu.trove.TObjectHashingStrategy#equals(java.lang.Object, java.lang.Object)
             */
            public boolean equals(Object o1, Object o2) {
                return o2 == null ? o1 == null : o2.equals(o1);
            }

        };

        dataWrite.setNbItems(nbItems);
        dataWrite.setNbFiles(nbFiles);

        if (persistMethod == PERSISTENT_METHOD.BERKELEY_DB_BY_CRC || persistMethod == PERSISTENT_METHOD.SORTED_MULTIPLE_HASH) {

            // nothing

        } else {

            // ################################################################################
            // Do not compare bytes number in this class with bytes number MemoryHashMapTest,
            // the purpose of this test class is not to measure heap memory !
            // ################################################################################

            // Map hashMap = new HashMap();
            hashMap = new HashMap(nbItems, 1f);
            // Map hashMap = new HashMap(10000, 1f);
            // Map hashMap = new THashMap(objectHashingStrategy);
            // Map hashMap = new THashMap(loop, 0.1f, objectHashingStrategy);
            // Map hashMap = new THashMap(loop, 1f, objectHashingStrategy);
            // Map hashMap = new THashMap(loop + 10000, 1.0f, objectHashingStrategy);
            // Map hashMap = new THashMap(loop + 10000, 0.99f, objectHashingStrategy);
            // Map hashMap = new THashMap(10000, 1.0f, objectHashingStrategy);

            // Data in Memory, 119 bytes , all=34 s
            // Data in Sqlite DB, ?? bytes , all=?? s, write=2550 s (42 min), read=???? s (?? min)
            // Data in File, ?? bytes , all=?? s, write=?? s (?? min), read=???? s (?? min)

            // hashMap = new THashMap(nbItems, 1.0f, objectHashingStrategy); // ??
        }
        // Map hashMap = new THashMap(loop + (int)((float)loop * 0.1f), 0.1f, objectHashingStrategy);

        dataWrite.setInitialCapacityMap(nbItems);
        dataWrite.setLoadFactorMap(1.0f);

        Map localHashMap = hashMap;

        switch (persistMethod) {
        case FIXED_AREA_POINTERS:
            ((MultiPointersMultiHashFiles) hashFile).readonly = readonly;
            break;

        default:
            break;
        }

        try {
            hashFile.initPut(persistMethod.filePath);
            
            for (int i = 0; i < nbItems; i++) {
                int v = i;

                if (randomWrite) {
                    v = randomArray[i];
                    // System.out.println("reandom value =" + v);
                }

                // => bean from tFileInput for example...
                Bean bean = new Bean(v, String.valueOf(v));

                if (persistMethod == PERSISTENT_METHOD.BERKELEY_DB_BY_CRC
                        || persistMethod == PERSISTENT_METHOD.SORTED_MULTIPLE_HASH) {
                    hashFile.put(persistMethod.filePath, bean);
                } else {
                    KeyForMap keyForMap = null;
                    // => THash storing step
                    if (readAfterStore) {
                        keyForMap = new KeyForMap((int) hashFile.put("buffer", bean), bean.hashCode());
                    } else {
                        keyForMap = new KeyForMap(v, bean.hashCode());
                    }
                    localHashMap.put(keyForMap, keyForMap);
                }

                if (i % 100000 == 0) {
                    System.out.println("Writing " + i);
                }

            }

            dataWrite.setWriteEndedWithSuccess(true);

        } catch (Throwable e) {
            e.printStackTrace();
        } finally {
            hashFile.endPut();
        }

        Sizeof.runGC();
        long heap2 = Sizeof.usedMemory(); // Take a before heap snapshot

        long memoryHeap = heap2 - heap1;

        dataWrite.setHeapMemoryWrite(memoryHeap);

        final int bytesPerItems = Math.round(((float) memoryHeap) / nbItems);
        System.out.println("'before' heap: " + heap1 + " bytes, 'after' heap: " + heap2 + " bytes "); //

        dataWrite.setBytesPerItemWrite(bytesPerItems);

        long end = System.currentTimeMillis();

        dataWrite.setEndWriteDate(new Date(end));

        long deltaTime = (end - start);

        dataWrite.setTimeWrite(deltaTime);

        int itemsPerSec = (int) ((float) nbItems / (float) deltaTime * 1000f);

        dataWrite.setItemsPerSecWrite(itemsPerSec);

        System.out.println(deltaTime + " milliseconds for " + nbItems + " objects to STORE. " + itemsPerSec + " items/s ");

        dataWrite.setTotalFilesSize(hashFile.getTotalSize());
    }

    /**
     * DOC amaumont Comment method "launchBenchs".
     * 
     * @param persistMethod
     * 
     * @param pointersByFile
     * @param nbFiles
     * @param dataReadWrite
     * 
     * @throws Exception
     * @throws IOException
     * @throws ClassNotFoundException
     */
    private void launchReadBenchs(PERSISTENT_METHOD persistMethod, int nbItems, int nbFiles, int pointersByFile,
            DataBench dataReadWrite) throws Exception, IOException, ClassNotFoundException {

        System.out.println("Starting with " + nbItems + " items ");

        Sizeof.runGC();
        long heap1 = Sizeof.usedMemory(); // Take a before heap snapshot

        long start = System.currentTimeMillis();
        dataReadWrite.setStartReadDate(new Date(start));

        Random rand = new Random(System.currentTimeMillis());

        switch (persistMethod) {
        case FIXED_AREA_POINTERS:
            ((MultiPointersMultiHashFiles) hashFile).setFilePointersNumber(pointersByFile);
            break;

        case TURNING_POINTERS_NEAREST:
        case TURNING_POINTERS_NEXT:
            break;

        case SQL_LITE_DB:
            break;

        case BERKELEY_DB_BY_ID:
            break;

        default:

            break;
        }

        dataReadWrite.setPointersByFile(pointersByFile);

        Map localHashMap = hashMap;

        Bean.getDataCountRequested = 0;

        if (persistMethod == PERSISTENT_METHOD.SORTED_MULTIPLE_HASH) {
            localHashMap = ((SortedMultipleHashFile) hashFile).getMap();
        }

        System.out.println("Items written in hash: " + localHashMap.size());

        if (readAfterStore) {
            System.out.println("Read step");
            long lastTime = start;
            long timeOut = 300000;

            try {
                hashFile.initGet(persistMethod.filePath);

                for (int i = 0; i < nbItems; i++) {
                    // System.out.println(i);

                    Bean bean = null;
                    // => bean from main flow in tMap for example...
                    if (randomRead) {
                        int v = rand.nextInt(nbItems);
                        bean = new Bean(v, String.valueOf(v));
                    } else {
                        bean = new Bean(i, String.valueOf(i));
                    }

                    Bean persistentBean = null;

                    if (persistMethod == PERSISTENT_METHOD.BERKELEY_DB_BY_CRC) {
                        persistentBean = (Bean) hashFile.get("buffer", -1, bean.hashCode());
                        // validity test
                        if (persistentBean == null) {
                            // throw new RuntimeException("Bean not found with id " + i);
                            new RuntimeException("Bean not found with id " + i).printStackTrace();
                        }
                        // validity test
                        // if (!persistentBean.name.equals(bean.name) || persistentBean.primitiveInt !=
                        // bean.primitiveInt) {
                        // throw new RuntimeException("Values of beans are different with id " + i);
                        // }
                    } else {
                        // => search properties of bean in lookup for example...
                        KeyForMap keyForMap = (KeyForMap) localHashMap.get(bean);

                        // validity test
                        if (keyForMap == null && persistMethod != PERSISTENT_METHOD.SORTED_MULTIPLE_HASH) {
                            throw new RuntimeException("keyForMap not found with id " + i);
                        }

                        if (keyForMap != null) {
                            // => bean found from DB
                            persistentBean = (Bean) hashFile.get(persistMethod.filePath, keyForMap.cursorPosition,
                                    keyForMap.hashcode);
                            // validity test
                            if (persistentBean == null) {
                                throw new RuntimeException("Bean not found with cursorPosition " + keyForMap.cursorPosition);
                            }
                            // validity test
                            if (!persistentBean.name.equals(bean.name) || persistentBean.primitiveInt != bean.primitiveInt) {
                                throw new RuntimeException("Values of beans are different with cursorPosition "
                                        + keyForMap.cursorPosition);
                            }

                            beanReads++;

                        }
                    }

                    if (false && System.currentTimeMillis() - lastTime > timeOut) {
                        int timeRead = (int) (((double) (System.currentTimeMillis() - lastTime) * nbItems) / (double) i);
                        dataReadWrite.setTimeRead(timeRead);
                        dataReadWrite.setItemsPerSecRead(1000 * nbItems / timeRead);
                        throw new RuntimeException("Timeout, read is too long !");
                    }

                    if (i % 100000 == 0) {
                        long currentTimeMillis = System.currentTimeMillis();
                        System.out.println("Reading " + i + ", time since last display: "
                                + (int) ((float) (currentTimeMillis - lastTime)) + " ms");

                        System.out.println("Bean.getDataCountRequested =" + Bean.getDataCountRequested);
                        lastTime = currentTimeMillis;
                    }

                }
                dataReadWrite.setReadEndedWithSuccess(true);

            } catch (FileNotFoundException e) {
                throw e;
            } finally {
                hashFile.endGet(persistMethod.filePath);
            }
            long end = System.currentTimeMillis();

            Sizeof.runGC();
            long heap2 = Sizeof.usedMemory(); // Take a before heap snapshot

            long memoryHeap = heap2 - heap1;

            System.out.println("beanReads=" + beanReads);

            dataReadWrite.setHeapMemoryRead(memoryHeap);

            final int bytesPerItems = Math.round(((float) (heap2 - heap1)) / nbItems);
            System.out.println("'before' heap: " + heap1 + " bytes, 'after' heap: " + heap2 + " bytes "); //

            dataReadWrite.setBytesPerItemRead(bytesPerItems);

            dataReadWrite.setEndReadDate(new Date(end));

            long deltaTime = (end - start);

            dataReadWrite.setTimeRead(deltaTime);

            int itemsPerSec = (int) ((float) nbItems / (float) deltaTime * 1000f);

            dataReadWrite.setItemsPerSecRead(itemsPerSec);

            System.out.println(deltaTime + " milliseconds for " + nbItems + " objects to READ. " + itemsPerSec + "  items/s ");

        }

        time2 = System.currentTimeMillis();

    }

}
