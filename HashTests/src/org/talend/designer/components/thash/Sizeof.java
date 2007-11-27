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
package org.talend.designer.components.thash;

/**
 * 
 * DOC amaumont  class global comment. Detailled comment
 * <br/>
 *
 */
public class Sizeof {

    public void main2(String[] args) throws Exception {
        // Warm up all classes/methods we will use
        runGC();
        usedMemory();
        // Array to keep strong references to allocated objects
        final int count = 100000;
        Object[] objects = new Object[count];

        long heap1 = 0;
        // Allocate count+1 objects, discard the first one
        for (int i = -1; i < count; ++i) {
            Object object = null;

            // Instantiate your data here and assign it to object

            object = new Object();
            // object = new Integer (i);
            // object = new Long (i);
            // object = new String ();
            // object = new byte [128][1]

            if (i >= 0)
                objects[i] = object;
            else {
                object = null; // Discard the warm up object
                runGC();
                heap1 = usedMemory(); // Take a before heap snapshot
            }
        }
        runGC();
        long heap2 = usedMemory(); // Take an after heap snapshot:

        final int size = Math.round(((float) (heap2 - heap1)) / count);
        System.out.println("'before' heap: " + heap1 + ", 'after' heap: " + heap2);
        System.out.println("heap delta: " + (heap2 - heap1) + ", {" + objects[0].getClass() + "} size = " + size
                + " bytes");
        for (int i = 0; i < count; ++i)
            objects[i] = null;
        objects = null;
    }

    public static void runGC() throws Exception {
        // It helps to call Runtime.gc()
        // using several method calls:
        for (int r = 0; r < 4; ++r)
            runGCSub();
    }

    public static void runGCSub() throws Exception {
        long usedMem1 = usedMemory(), usedMem2 = Long.MAX_VALUE;
        for (int i = 0; (usedMem1 < usedMem2) && (i < 500); ++i) {
            S_RUNTIME.runFinalization();
            S_RUNTIME.gc();
            Thread.currentThread().yield();

            usedMem2 = usedMem1;
            usedMem1 = usedMemory();
        }
    }

    public static long usedMemory() {
        return S_RUNTIME.totalMemory() - S_RUNTIME.freeMemory();
    }

    private static final Runtime S_RUNTIME = Runtime.getRuntime();
} // End of class
