package org.talend.designer.components.sort.radixsort;

import org.talend.designer.components.sort.SortAlgorithm;

/*
 * @!RadixSortAlgorithm.java By: Alvin Raj Date: 12 August 2002 Algorithm adapted from: C++ Data Structures, Nell Dale
 * Additional Comments added by Jason Harrison <harrison@cs.ubc.ca>
 * 
 * Algorithm comments from http://ciips.ee.uwa.edu.au/~morris/Year2/PLDS210/radixsort.html
 * 
 */
public class RadixSortAlgorithm extends SortAlgorithm {

    // for each of the 10 digits
    private LinkedQueue[] Q = { new LinkedQueue(), // 0
            new LinkedQueue(), // 1
            new LinkedQueue(), // 2
            new LinkedQueue(), // 3
            new LinkedQueue(), // 4
            new LinkedQueue(), // 5
            new LinkedQueue(), // 6
            new LinkedQueue(), // 7
            new LinkedQueue(), // 8
            new LinkedQueue() }; // 9

    // Assumes all positive integers
    // numbDigits is the number of digits in the largest number
    void sort(int a[], int numDigits) throws Exception {
        int arrayPos;
        // i is the radix
        for (int i = 1; i <= numDigits; i++) {
            if (stopRequested) {
                return;
            }
            arrayPos = 0;
            // Put values into queues according to radix
            // least significant digit first, then second,...
            // first pass sorts on least significant digit
            for (int j = 0; j < a.length; j++) {
                Q[getRadix(a[j], i)].enqueue(a[j]);
                pause(-1, j); // JH
            }
            // Collect the queues and put them back into the array
            // queues contain partially sorted lists after first
            // pass -- moving to next significant digit will
            // maintain this ordering.
            for (int j = 0; j < Q.length; j++) {
                while (!Q[j].isEmpty()) {
                    a[arrayPos] = Q[j].dequeue();
                    arrayPos++;
                }
                pause(-1, arrayPos);
            }
        }
    }

    protected void sort(int a[]) throws Exception {
        // Find the largest entry in a[]
        int max = 0;
        int maxIndex = 0; // JH
        for (int i = 0; i < a.length; i++) {
            if (max < a[i]) {
                max = a[i];
                maxIndex = i; // JH
                pause(maxIndex, i); // JH
            }
        }

        int numDigits = 1;
        int temp = 10;
        // compute the max number of digits
        // JH: this could be more easily be computed by using
        // numDigits = (int) log(base10, max)
        while (true) {
            if (max >= temp) {
                numDigits++;
                temp *= 10;
            } else
                break;
        }

        // call the sort
        sort(a, numDigits);
    }

    // returns the radix of the given number
    // EG:
    // 2nd radix of 79981 is 8
    // 1st radix of 79981 is 1
    public static int getRadix(int number, int radix) {
        return (int) (number / Math.pow(10, radix - 1)) % 10;
    }

}
