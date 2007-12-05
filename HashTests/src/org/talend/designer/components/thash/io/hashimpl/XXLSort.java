// ============================================================================
//
// Copyright (C) 2006-2007 Talend Inc. - www.talend.com
//
// This source code is available under agreement available at
// %InstallDIR%\features\org.talend.rcp.branding.%PRODUCTNAME%\%PRODUCTNAME%license.txt
//
// You should have received a copy of the  agreement
// along with this program; if not, write to Talend SA
// 9 rue Pages 92150 Suresnes, France
//
// ============================================================================
package org.talend.designer.components.thash.io.hashimpl;

import java.util.Iterator;

import xxl.core.collections.queues.ListQueue;
import xxl.core.cursors.sorters.MergeSorter;
import xxl.core.functions.Function;


/**
 * DOC amaumont  class global comment. Detailled comment
 */
public class XXLSort {

    public static void main(String[] args) {
        /*********************************************************************/
        /*                            Example 1                              */
        /*********************************************************************/
        
        final int nbItems = 5000000;
        final int[] countIter = new int[]{-1};
        
        Iterator<Data> iterator = new Iterator<Data>() {

            public boolean hasNext() {
                return countIter[0] < nbItems;
            }

            public Data next() {
                countIter[0]++;
                return new Data("name" + countIter[0], countIter[0], 0);
            }

            public void remove() {
                throw new UnsupportedOperationException();
            }
            
        };
        
        
        MergeSorter sorter = new MergeSorter(
                iterator,
            12,
            12*4096,
            0.0,
            0.0,
            0.0,
            4*4096,
            0.0,
            new Function() {
                public Object invoke(Object function1, Object function2) {
                    return new ListQueue();
                }
            },
            true
        );
        
        sorter.open();
        
        int count = 0;
        for (Data old = null; sorter.hasNext(); count++) {
            if (old != null && old.compareTo((Data) sorter.peek()) > 0)
                throw new RuntimeException("Fehler: Wert " + sorter.peek() + " ist groesser!");
            old = (Data)sorter.next();
        }
        System.out.println("Objects: " + count);
        
        sorter.close();

    }
    
}
