package org.talend.designer.components.sort;

/*
 * Copyright (c) 2008 the authors listed at the following URL, and/or the authors of referenced articles or incorporated
 * external code: http://en.literateprograms.org/Merge_sort_(Java)?action=history&offset=20080109204500
 * 
 * Permission is hereby granted, free of charge, to any person obtaining a copy of this software and associated
 * documentation files (the "Software"), to deal in the Software without restriction, including without limitation the
 * rights to use, copy, modify, merge, publish, distribute, sublicense, and/or sell copies of the Software, and to
 * permit persons to whom the Software is furnished to do so, subject to the following conditions:
 * 
 * The above copyright notice and this permission notice shall be included in all copies or substantial portions of the
 * Software.
 * 
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR IMPLIED, INCLUDING BUT NOT LIMITED TO THE
 * WARRANTIES OF MERCHANTABILITY, FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE AUTHORS OR
 * COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR
 * OTHERWISE, ARISING FROM, OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE SOFTWARE.
 * 
 * Retrieved from: http://en.literateprograms.org/Merge_sort_(Java)?oldid=12004
 */

class MergeSortArray {

    private static final int minMergeSortListSize = 32;

    public static void mergeSortArray(int[] a, int start, int end) {
        int[] temp = new int[end];
        int i1, i2, tempi;

        if ((end - start) < minMergeSortListSize) {
            /* Use insertion sort for small datasets. */
            for (int i = start; i < end; i++) {
                int j, v = a[i];
                for (j = i - 1; j >= 0; j--) {
                    if (a[j] <= v)
                        break;
                    a[j + 1] = a[j];
                }
                a[j + 1] = v;
            }
            return;
        }

        mergeSortArray(a, start, start + ((end - start) / 2));
        mergeSortArray(a, start + ((end - start) / 2), end);
        i1 = 0;
        i2 = end / 2;
        tempi = 0;
        while (i1 < end / 2 && i2 < end) {
            if (a[i1] < a[i2])
                temp[tempi++] = a[i1++];
            else
                temp[tempi++] = a[i2++];
        }

        while (i1 < end / 2) {
            temp[tempi++] = a[i1++];
        }
        while (i2 < end) {
            temp[tempi++] = a[i2++];
        }

        System.arraycopy(a, start, temp, start, end - start);
    }

    public static void main(String[] argv) {
        int size = Integer.parseInt(argv[0]);
        int[] a = new int[size];
        java.util.Random rand = new java.util.Random(System.currentTimeMillis());
        for (int i = 0; i < size; i++) {
            a[i] = rand.nextInt() % size;
        }
        mergeSortArray(a, 0, size);
        for (int i = 1; i < size; i++) {
            if (!(a[i - 1] <= a[i])) {
                System.out.println("ERROR");
                System.exit(-1);
            }
        }
        System.out.println("SUCCESS");
        System.exit(0);
    }
}
