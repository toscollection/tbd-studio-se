package org.talend.designer.components.persistent.memorygc;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
import java.util.Date;

import org.jfree.data.category.DefaultCategoryDataset;
import org.jfree.data.xy.DefaultXYDataset;
import org.talend.designer.components.persistent.MemoryHelper;
import org.talend.designer.components.thash.io.PersistentDiskBenchsChart;

import routines.system.IPersistableRow;

public class GCTester {

    private static final int INFO_ECART = 400000;

    private ArrayList<Measure> measures;

    public static void main(String[] args) {

        GCTester tester = new GCTester();
        tester.init();

    }

    public GCTester() {
        super();
    }

    private void init() {

        measures = new ArrayList<Measure>();

        // double[] xTime = new double[x1.size()];
        // double[] y11 = new double[y1.size()];
        // for (int iValue = 0; iValue < x1.size(); iValue++) {
        // x11[iValue] = x1.get(iValue);
        // y11[iValue] = y1.get(iValue);
        // }
        //
        // double[][] data1 = new double[][] { x11, y11 };
        // dataset.addSeries(testCase.label, data1);

        int count = 3800000;

        ROW1Struct[] array1 = new ROW1Struct[count];

        writeMemoryInfos("Start");
        for (int i = 0; i < count; i++) {
            ROW1Struct struct = new ROW1Struct();
            struct.ID_MAIN = i;
            struct.LABEL_MAIN = "Label" + i;
            array1[i] = struct;
            if (i % INFO_ECART == 0) {
                writeMemoryInfos("loop 1 index=" + i);
            }
        }

        array1 = null;

        writeMemoryInfos("before gc 1");
        MemoryHelper.gc();
        writeMemoryInfos("after gc 1");

        ROW2Struct[] array2 = new ROW2Struct[count];

        for (int i = 0; i < count; i++) {
            ROW2Struct struct = new ROW2Struct();
            struct.ID_MAIN = i;
            struct.LABEL_MAIN = "Label" + i;
            array2[i] = struct;
            if (i % INFO_ECART == 0) {
                writeMemoryInfos("loop 2 index=" + i);
            }
        }

        array2 = null;

        writeMemoryInfos("before gc 2");
        MemoryHelper.gc();
        writeMemoryInfos("after gc 2");

        int measuresListSize = measures.size();

        DefaultCategoryDataset dataset = new DefaultCategoryDataset();
        for (int i = 0; i < measuresListSize; i++) {
            Measure measure = measures.get(i);
            String contextMeasure = measure.contextMeasure;
            dataset.addValue(measure.usedMemory, "Used Memory", contextMeasure);
            dataset.addValue(measure.totalMemory, "Total Memory", contextMeasure);
            dataset.addValue(measure.maxMemory, "Max Memory", contextMeasure);
            dataset.addValue(measure.freeMemory, "Free Memory", contextMeasure);
        }

        new MemoryGcChart("Memory GC", dataset, "Memory GC", "Time", "bytes",
                "/home/amaumont/hash_benchs/charts/MemoryGC_" + count + "rows.png", 1024, 800).createDemoPanel(dataset);
    }

    private void writeMemoryInfos(String contextInfo) {

        measures.add(new Measure(contextInfo, new Date(), MemoryHelper.usedMemory(), MemoryHelper.freeMemory(),
                MemoryHelper.totalMemory(), MemoryHelper.maxMemory(), MemoryHelper.hasFreeMemory(0.10f)));

        System.out.println(contextInfo + ";" + MemoryHelper.maxMemory() + ";" + MemoryHelper.freeMemory() + ";"
                + MemoryHelper.totalMemory() + ";" + MemoryHelper.usedMemory() + ";"
                + MemoryHelper.hasFreeMemory(0.10f));

    }

    public static class ROW1Struct {

        Integer ID_MAIN;

        String LABEL_MAIN;

    }

    public static class ROW2Struct {

        Integer ID_MAIN;

        String LABEL_MAIN;

    }

    class Measure {

        String contextMeasure;

        Date date;

        long usedMemory;

        long freeMemory;

        long totalMemory;

        long maxMemory;

        boolean hasFreeMemory;

        public Measure(String contextMeasure, Date date, long usedMemory, long freeMemory, long totalMemory,
                long maxMemory, boolean hasFreeMemory) {
            super();
            this.contextMeasure = contextMeasure;
            this.date = date;
            this.usedMemory = usedMemory;
            this.freeMemory = freeMemory;
            this.totalMemory = totalMemory;
            this.maxMemory = maxMemory;
            this.hasFreeMemory = hasFreeMemory;
        }

    }

}
