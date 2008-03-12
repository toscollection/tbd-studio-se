package org.talend.designer.components.sort.oettrans;

import org.talend.designer.components.sort.SortAlgorithm;

/*
 * @(#)OETransSortAlgorithm.java 95/11/22 Andrew Kitchen
 * 
 */

/**
 * An Odd-Even Transposition sort demonstration algorithm
 * 
 * @author Andrew Kitchen
 * @version 22 Nov 1995
 */
class OETransSortAlgorithm extends SortAlgorithm {

    protected void sort(int a[]) throws Exception {
        pause(0, a.length - 1);
        for (int i = 0; i < a.length / 2; i++) {
            if (stopRequested) {
                return;
            }
            for (int j = 0; j + 1 < a.length; j += 2)
                if (a[j] > a[j + 1]) {
                    int T = a[j];
                    a[j] = a[j + 1];
                    a[j + 1] = T;
                }
            pause();
            pause();
            for (int j = 1; j + 1 < a.length; j += 2)
                if (a[j] > a[j + 1]) {
                    int T = a[j];
                    a[j] = a[j + 1];
                    a[j + 1] = T;
                }
            pause();
            pause();
        }
        pause(-1, -1);
    }
}
