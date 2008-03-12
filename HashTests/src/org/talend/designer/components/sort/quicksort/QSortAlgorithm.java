package org.talend.designer.components.sort.quicksort;

import org.talend.designer.components.sort.SortAlgorithm;

/*
 * @(#)QSortAlgorithm.java 1.6f 95/01/31 James Gosling
 * 
 * Copyright (c) 1994-1995 Sun Microsystems, Inc. All Rights Reserved.
 * 
 * Permission to use, copy, modify, and distribute this software and its documentation for NON-COMMERCIAL or COMMERCIAL
 * purposes and without fee is hereby granted. Please refer to the file http://java.sun.com/copy_trademarks.html for
 * further important copyright and trademark information and to http://java.sun.com/licensing.html for further important
 * licensing information for the Java (tm) Technology.
 * 
 * SUN MAKES NO REPRESENTATIONS OR WARRANTIES ABOUT THE SUITABILITY OF THE SOFTWARE, EITHER EXPRESS OR IMPLIED,
 * INCLUDING BUT NOT LIMITED TO THE IMPLIED WARRANTIES OF MERCHANTABILITY, FITNESS FOR A PARTICULAR PURPOSE, OR
 * NON-INFRINGEMENT. SUN SHALL NOT BE LIABLE FOR ANY DAMAGES SUFFERED BY LICENSEE AS A RESULT OF USING, MODIFYING OR
 * DISTRIBUTING THIS SOFTWARE OR ITS DERIVATIVES.
 * 
 * THIS SOFTWARE IS NOT DESIGNED OR INTENDED FOR USE OR RESALE AS ON-LINE CONTROL EQUIPMENT IN HAZARDOUS ENVIRONMENTS
 * REQUIRING FAIL-SAFE PERFORMANCE, SUCH AS IN THE OPERATION OF NUCLEAR FACILITIES, AIRCRAFT NAVIGATION OR COMMUNICATION
 * SYSTEMS, AIR TRAFFIC CONTROL, DIRECT LIFE SUPPORT MACHINES, OR WEAPONS SYSTEMS, IN WHICH THE FAILURE OF THE SOFTWARE
 * COULD LEAD DIRECTLY TO DEATH, PERSONAL INJURY, OR SEVERE PHYSICAL OR ENVIRONMENTAL DAMAGE ("HIGH RISK ACTIVITIES").
 * SUN SPECIFICALLY DISCLAIMS ANY EXPRESS OR IMPLIED WARRANTY OF FITNESS FOR HIGH RISK ACTIVITIES.
 */

/**
 * A quick sort demonstration algorithm SortAlgorithm.java, Thu Oct 27 10:32:35 1994
 * 
 * @author James Gosling
 * @version 1.6f, 31 Jan 1995
 */
class QSortAlgorithm extends SortAlgorithm {

    protected void sort(int a[], int lo0, int hi0) throws Exception {
        int lo = lo0;
        int hi = hi0;
        pause(lo, hi);
        if (lo >= hi) {
            return;
        }
        int mid = a[(lo + hi) / 2];
        while (lo < hi) {
            while (lo < hi && a[lo] < mid) {
                lo++;
            }
            while (lo < hi && a[hi] > mid) {
                hi--;
            }
            if (lo < hi) {
                int T = a[lo];
                a[lo] = a[hi];
                a[hi] = T;
                pause();
            }
        }
        if (hi < lo) {
            int T = hi;
            hi = lo;
            lo = T;
        }
        sort(a, lo0, lo);
        sort(a, lo == lo0 ? lo + 1 : lo, hi0);
    }

    protected void sort(int a[]) throws Exception {
        sort(a, 0, a.length - 1);
        pause(-1, -1);
    }
}
