package org.talend.designer.components.sort;

public class ShellSort {

    public static void shellsort(int[] a, int l, int r) {
        int i, j, h, v;
        for (h = 1; h <= (r - l) / 9; h = 3 * h + 1)
            ;
        for (; h > 0; h /= 3)
            for (i = l + h; i <= r; i++) {
                j = i;
                v = a[i];
                while (j >= l + h && v < a[j - h]) {
                    a[j] = a[j - h];
                    j -= h;
                }
                a[j] = v;
            }
    }

}
