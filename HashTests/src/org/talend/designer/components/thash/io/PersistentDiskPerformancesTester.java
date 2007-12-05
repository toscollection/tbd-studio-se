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
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.text.NumberFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.Random;

import org.jfree.data.xy.DefaultXYDataset;
import org.talend.designer.components.thash.io.hashimpl.HashFileReader;
import org.talend.designer.components.thash.io.hashimpl.MultiPointersMultiHashFiles;
import org.talend.designer.components.thash.io.hashimpl.MultiPointersMultiHashFiles.MultiReadPointersFileHandler;

/**
 * DOC amaumont class global comment. Detailled comment <br/>
 * 
 * $Id: talend.epf 1 2006-09-29 17:06:40Z nrousseau $
 * 
 */
public class PersistentDiskPerformancesTester {

    String folderStatsPath = "/home/amaumont/hash_benchs/";

    String fileHashBenchsBaseName = "PerfDisk";

    private int numberOfFiles;

    private int numberOfPointers;

    private int fileLength;

    private MultiPointersMultiHashFiles mpfh;

    private HashFileReader hfr;

    private int numberOfReads;

    private int bytesToRead;

    private boolean randomized;

    /**
     * DOC amaumont PersistentDiskPerformancesTester constructor comment.
     * 
     * @throws IOException
     */
    public PersistentDiskPerformancesTester() throws IOException {
        super();
    }

    public static void main(String[] args) throws IOException {
        // new PersistentDiskPerformancesTester().run();
        // new PersistentDiskPerformancesTester().run2();
        new PersistentDiskPerformancesTester().run();
    }

    /**
     * 
     * DOC amaumont PersistentDiskPerformancesTester class global comment. Detailled comment
     */
    enum TEST_CASE {
        // FIXED_AREA_POINTERS("Fixed area pointers", "/home/amaumont/hash_benchs/talend_hash_perfs"),
        FIXED_AREA_POINTERS("Fixed area pointers", "/home/amaumont/hash_benchs/talend_hash_perfs_n"),
        TURNING_POINTERS_NEAREST("Turning pointers nearest", "/home/amaumont/hash_benchs/talend_hash_perfs0"),
        TURNING_POINTERS_NEXT("Turning pointers next", "/home/amaumont/hash_benchs/talend_hash_perfs0"), ;

        private String filePath;

        private String label;

        TEST_CASE(String label, String filePath) {
            this.label = label;
            this.filePath = filePath;
        }

    }

    /**
     * DOC amaumont Comment method "run".
     * 
     * @throws FileNotFoundException
     * @throws IOException
     */
    private void run() throws IOException {

        /*
         * numberOfFiles = 1; numberOfPointers = 100; mpfh 2Go loop = 10000 => 42 items/s
         * 
         * numberOfFiles = 1; numberOfPointers = 200; mpfh 2Go loop = 10000 => ?? items/s
         * 
         * numberOfFiles = 1; numberOfPointers = 100; hfr - nearest - 2Go loop = 10000 => 80 items/s
         * 
         * numberOfFiles = 1; numberOfPointers = 100; hfr - next greater than - 2Go loop = 10000 => 100 items/s
         * 
         * numberOfFiles = 1; numberOfPointers = 200; hfr - next greater than - 2Go loop = 10000 => 130 items/s
         * 
         */

        TEST_CASE[] testCases = new TEST_CASE[] { TEST_CASE.FIXED_AREA_POINTERS,
        // TEST_CASE.TURNING_POINTERS_NEAREST,
        // TEST_CASE.TURNING_POINTERS_NEXT,
        };

        int[] randomValuesSteps = new int[] {
        //
                // 10000, // 10 ko
                // //
                // 100000, // 100 ko
                // //
                // 1000000, // 1 Mo
                // //
                // 10000000, // 10 Mo
                // //

                10000000, // 10 Mo
                //
                20000000, // 20 Mo
                //
                30000000, // 30 Mo
                //
                40000000, // 40 Mo
                //
                50000000, // 50 Mo
                //
                60000000, // 60 Mo
                //
                70000000, // 70 Mo
                //
                80000000, // 80 Mo
                //
                90000000, // 90 Mo
                //
                100000000, // 100 Mo
                //
                110000000, // 110 Mo
                //
                120000000, // 120 Mo

        // 50000000, // 50 Mo
        // //
        // 60000000, // 50 Mo
        // //
        // 75000000, // 750 Mo
        // //
        // 100000000, // 100 Mo
        // //
        // 150000000, // 150 Mo
        // //
        // 200000000, // 200 Mo
        // //
        // 250000000, // 250 Mo
        // //
        // 500000000, // 500 Mo
        // //
        // 750000000, // 750 Mo
        // //
        // 1000000000, // 1 Go
        // //
        // 1250000000, // 1,25 Go
        // //
        // 1500000000, // 1,5 Go
        // //
        // 1750000000, // 1,75 Go
        // //
        // 2000000000, // 2 Go
        //
        };

        // int[] nbFilesArray = new int[] {
        // 1,
        // 10,
        // 20,
        // 40,
        // 60,
        // 80,
        // 100,
        // 150,
        // };

        int[] pointersByFileArray = new int[] { 1, 10,
        // 20,
                // 40,
                50,
                // 60,
                // 80,
                100,
                150,
                200,

        };

        bytesToRead = 20;

        numberOfReads = 10000000;

        StringBuilder sbPreview = new StringBuilder();

        NumberFormat nf = NumberFormat.getInstance();

        sbPreview.append("nbItems list: ");
        for (int i = 0; i < randomValuesSteps.length; i++) {
            int j = randomValuesSteps[i];
            sbPreview.append(nf.format(j));
            sbPreview.append("  |  ");
        }
        sbPreview.append("\n");

        // sbPreview.append("nbFiles list: ");
        // for (int i = 0; i < nbFilesArray.length; i++) {
        // int j = nbFilesArray[i];
        // sbPreview.append(nf.format(j));
        // sbPreview.append(" | ");
        // }
        // sbPreview.append("\n");

        sbPreview.append("pointersByFile list: ");
        for (int i = 0; i < pointersByFileArray.length; i++) {
            int j = pointersByFileArray[i];
            sbPreview.append(nf.format(j));
            sbPreview.append("  |  ");
        }
        sbPreview.append("\n");

        System.out.println(sbPreview.toString());

        DefaultXYDataset dataset = new DefaultXYDataset();

        for (int i = 0; i < testCases.length; i++) {
            TEST_CASE testCase = testCases[i];

            numberOfFiles = 10;

            numberOfPointers = 10;

            int testsCount = 5;

            long time = 0;

            byte[] buffer = new byte[bytesToRead];

            Random random = new Random(System.currentTimeMillis());

            randomized = false;

            List<Double> x1 = new ArrayList<Double>();
            List<Double> y1 = new ArrayList<Double>();

            // System.out.println("run2");

            long timeOut = 350000;
            long lastTime = System.currentTimeMillis();

            // fileLength = 2000000000; // 2 Go
            fileLength = 120000000; // 120 Mo

            openDataFile();

            // double[] timeValues = new double[testsCount];

            for (int iRandomValueStep = 0; iRandomValueStep < randomValuesSteps.length; iRandomValueStep++) {

                int randomValue = randomValuesSteps[iRandomValueStep];

                if (testCase == TEST_CASE.FIXED_AREA_POINTERS) {
                    mpfh = new MultiPointersMultiHashFiles(TEST_CASE.FIXED_AREA_POINTERS.filePath, numberOfFiles);
                    mpfh.setFilePointersNumber(numberOfPointers);
                    mpfh.initGet(testCase.filePath);
                    mpfh.setLimitEndOfRead(randomValue - 1000);
                } else if (testCase == TEST_CASE.TURNING_POINTERS_NEAREST || testCase == TEST_CASE.TURNING_POINTERS_NEXT) {
                    hfr = new HashFileReader(testCase.filePath, numberOfPointers);
                    hfr.setBytesToRead(bytesToRead);
                    hfr.init();
                    hfr.setNearest(testCase == TEST_CASE.TURNING_POINTERS_NEAREST);
                    hfr.setLimitEndOfRead(randomValue - 1000);
                }

                long seekTime = 0;

                System.out.println("randomValue=" + randomValue);
                System.out.println("loop=" + numberOfReads);
                for (int iRead = 0; iRead < numberOfReads; iRead++) {

                    time = System.currentTimeMillis();
                    int cursorPosition = iRead;
                    if (randomized) {
                        cursorPosition = random.nextInt(randomValue);
                    }

                    if (testCase == TEST_CASE.FIXED_AREA_POINTERS) {
                        int fileNumber = 0;
                        if (numberOfFiles > 1) {
                            fileNumber = mpfh.getFileNumber(cursorPosition);
                        }
                        MultiReadPointersFileHandler fh = mpfh.getFileHandler(fileNumber);
                        RandomAccessFile ra = fh.getPointer(cursorPosition);
                        // System.out.println(nextInt);
                        ra.seek(cursorPosition);
                        ra.read(buffer);
                    } else if (testCase == TEST_CASE.TURNING_POINTERS_NEAREST || testCase == TEST_CASE.TURNING_POINTERS_NEXT) {
                        hfr.read(cursorPosition);
                    }
                    // System.out.println(Arrays.toString(buffer));
                    seekTime += System.currentTimeMillis() - time;

                }

                if (testCase == TEST_CASE.FIXED_AREA_POINTERS) {
                    mpfh.endGet(testCase.filePath);
                } else if (testCase == TEST_CASE.TURNING_POINTERS_NEAREST || testCase == TEST_CASE.TURNING_POINTERS_NEXT) {
                    hfr.close();
                }

                // long seekTimeAverage = (long) average(timeValues);

                double readsBySecond = (double) (((double) numberOfReads) / ((double) seekTime / 1000d));

                System.out.println("################################################################");
                System.out.println(seekTime + " ms");
                System.out.println((double) (((double) numberOfReads / (double) seekTime)) + " items/ms");
                System.out.println(readsBySecond + " items/s");
                System.out.println("################################################################");
                DataDiskBench data = new DataDiskBench(numberOfFiles, numberOfPointers, numberOfReads, fileLength, randomValue,
                        (int) readsBySecond);
                writeData(data);
                x1.add((double) randomValue);
                y1.add((double) readsBySecond);

                if (System.currentTimeMillis() - lastTime > timeOut) {
                    break;
                }
                lastTime = System.currentTimeMillis();

            }

            double[] x11 = new double[x1.size()];
            double[] y11 = new double[y1.size()];
            for (int iValue = 0; iValue < x1.size(); iValue++) {
                x11[iValue] = x1.get(iValue);
                y11[iValue] = y1.get(iValue);
            }

            double[][] data1 = new double[][] { x11, y11 };
            dataset.addSeries(testCase.label, data1);

            closeDataFile();
        }

        // System.out.println(seekStart);
        // System.out.println(seekMedium);
        // System.out.println(seekEnd);

        NumberFormat integerInstance = NumberFormat.getIntegerInstance(Locale.FRANCE);

        new PersistentDiskBenchsChart("Disk Bench", dataset, "Persistent Hash Benchs for " + numberOfFiles + " file "
                + integerInstance.format(fileLength) + " bytes, " + numberOfReads + " reads by test, " + numberOfPointers
                + " pointers by file" + (hfr != null ? ", with turning pointers " : ""), "Max random value for cursor position",
                "Reads/s", getFilePathBaseName() + ".png", 1024, 800).createDemoPanel(dataset);
    }

    public double average(double[] values) {

        double total = 0;
        for (int i = 0; i < values.length; i++) {
            total += values[i];
        }
        return total / values.length;

    }

    private FileOutputStream fileData;

    /**
     * DOC amaumont Comment method "openDataFile".
     * 
     * @throws IOException
     */
    private void openDataFile() throws IOException {

        String filePath = getFilePathBaseName() + ".csv";

        File file = new File(filePath);
        boolean exists = file.exists();

        try {
            this.fileData = new FileOutputStream(filePath, true);
            if (!exists) {
                this.fileData.write(DataDiskBench.getFileHeader().getBytes());
                this.fileData.write('\n');
            }
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        }

    }

    /**
     * DOC amaumont Comment method "getFilePathBaseName".
     * 
     * @return
     */
    private String getFilePathBaseName() {
        NumberFormat integerInstance = NumberFormat.getIntegerInstance(Locale.FRANCE);

        SimpleDateFormat sdf = new SimpleDateFormat("yyyy_MM_dd_HH'h'mm'm'ss's'");

        return folderStatsPath + sdf.format(new Date()) + "_" + numberOfFiles + "_files_"
                + integerInstance.format(fileLength).replace(' ', '_') + "_bytes_" + numberOfReads + "_reads_by_test_"
                + numberOfPointers + "_pointer_by_file" + (hfr != null ? "_turning_pointers" : "");
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
    private void writeData(DataDiskBench dataReadWrite) throws IOException {
        this.fileData.write(dataReadWrite.toFileRow().getBytes());
        this.fileData.write('\n');
    }

    /**
     * 
     * DOC amaumont PersistentDiskPerformancesTester class global comment. Detailled comment
     */
    static class DataDiskBench {

        int numberOfFiles;

        int numberOfPointers;

        int readLoopNumber;

        int fileLength;

        int randomValue;

        int time;

        /**
         * DOC amaumont DataDiskBench constructor comment.
         * 
         * @param numberOfFiles
         * @param numberOfPointers
         * @param readLoopNumber
         * @param fileLength
         * @param randomValue
         * @param time
         */
        public DataDiskBench(int numberOfFiles, int numberOfPointers, int readLoopNumber, int fileLength, int randomValue,
                int time) {
            super();
            this.numberOfFiles = numberOfFiles;
            this.numberOfPointers = numberOfPointers;
            this.readLoopNumber = readLoopNumber;
            this.fileLength = fileLength;
            this.randomValue = randomValue;
            this.time = time;
        }

        /**
         * DOC amaumont Comment method "getFileHeader".
         * 
         * @return
         */
        public static String getFileHeader() {
            return "numberOfFiles" + ";" + "numberOfPointers" + ";" + "readLoopNumber" + ";" + "fileLength" + ";" + "randomValue"
                    + ";" + "time";
        }

        /**
         * DOC amaumont Comment method "toFileRow".
         * 
         * @return
         */
        public String toFileRow() {
            return numberOfFiles + ";" + numberOfPointers + ";" + readLoopNumber + ";" + fileLength + ";" + randomValue + ";"
                    + time;
        }

    }

}
