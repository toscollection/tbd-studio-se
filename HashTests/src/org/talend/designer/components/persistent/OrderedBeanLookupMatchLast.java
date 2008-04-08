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

package org.talend.designer.components.persistent;

import java.io.IOException;
import java.util.NoSuchElementException;

/**
 * DOC amaumont class global comment. Detailled comment <br/>
 * 
 * $Id$
 * 
 */
public class OrderedBeanLookupMatchLast<B extends Comparable<B> & IPersistableLookupRow<B>> extends AbstractOrderedBeanLookup<B> {

    public OrderedBeanLookupMatchLast(String baseDirectory, int fileIndex, IRowProvider<B> rowProvider) throws IOException {
        super(baseDirectory, fileIndex, rowProvider);
        lookupInstance = rowProvider.createInstance();
        previousLookupInstance = rowProvider.createInstance();
    }

    /*
     * (non-Javadoc)
     * 
     * @see org.talend.designer.components.persistent.TestA#lookup(B)
     */
    public void lookup(B key) throws IOException {

        currentSearchedKey = key;

        nextDirty = true;

//        if (previousAskedKey != null && previousAskedKey.compareTo(key) == 0) {
//            startWithNewKey = true;
//        } else {
//            startWithNewKey = true;
//        }
      startWithNewKey = true;

//        previousAskedKey = key;

    }

    /*
     * (non-Javadoc)
     * 
     * @see org.talend.designer.components.persistent.TestA#hasNext()
     */
    public boolean hasNext() throws IOException {

        if (currentSearchedKey == null) {
            return false;
        }

        if (nextDirty) {
            if (cursorPosition >= length) {
                noMoreNext = true;
                return false;
            }

            int compareResult = -1;

            int localSkip = 0;

            if (atLeastOneLoadkeys) {
                compareResult = lookupInstance.compareTo(currentSearchedKey);
                if (compareResult == 0) {
//                    swapInstances();
                    if(startWithNewKey && !previousCompareResultMatch) {
                        localSkip += currentValuesSize;
                        compareResult = -1;
                    } else {
                        nextWithPreviousLookup = true;
                        if (previousCompareResultMatch) {
                            remainingSkip = 0;
                        }
                    }
                } else if (compareResult < 0) {
                    localSkip += currentValuesSize;
                }
            }
            startWithNewKey = false;

            int previousValuesSize = 0;

            if (compareResult < 0 || !atLeastOneLoadkeys) {

                boolean searchingNextNotMatch = false;
                do {

                    loadDataKeys();
                    compareResult = lookupInstance.compareTo(currentSearchedKey);
                    if (compareResult > 0) {
                        // remainingSkip = currentValuesSize;
                        // localSkip += currentValuesSize;
                    }
                    if (compareResult >= 0 || cursorPosition >= length) {
                        if (!searchingNextNotMatch && compareResult == 0) {
                            searchingNextNotMatch = true;
                        } else if (compareResult > 0 || cursorPosition >= length) {
                            // localSkip -= currentValuesSize;
                            remainingSkip = 0;
                            sizeDataToRead = previousValuesSize;
                            if(searchingNextNotMatch) {
                                localSkip -= previousValuesSize;
                                swapInstances();
                                compareResult = 0;
                            } else {
                                localSkip += previousValuesSize;
//                                previousValuesSize = currentValuesSize;
                            }
                            break;
                        }
                        swapInstances();
                        localSkip += currentValuesSize;
                        previousValuesSize = currentValuesSize;
                    }
                    if (compareResult < 0 && !searchingNextNotMatch) {
                        localSkip += currentValuesSize;
                    }
                } while (true);
            }
            if (compareResult == 0) {
                previousCompareResultMatch = true;
                // skipValuesSize -= remainingSkip;
                skipValuesSize += localSkip;
                hasNext = true;
                noMoreNext = false;
                nextDirty = false;
                return true;
            } else if (compareResult < 0) {
                previousCompareResultMatch = false;
                skipValuesSize += localSkip;
                nextDirty = true;
                noMoreNext = true;
                hasNext = false;
                return false;
            } else {
                previousCompareResultMatch = false;
                skipValuesSize += localSkip;
                nextDirty = true;
                noMoreNext = false;
                hasNext = false;
                return false;
            }
        } else {
            return hasNext;
        }
    }

    private void swapInstances() {
        B temp = previousLookupInstance;
        previousLookupInstance = lookupInstance;
        lookupInstance = temp;
    }

    /*
     * (non-Javadoc)
     * 
     * @see org.talend.designer.components.persistent.TestA#next()
     */
    public B next() throws IOException {

        previousCompareResultMatch = true;

        if (noMoreNext || nextDirty) {
            throw new NoSuchElementException();
        }

        nextDirty = true;
        B row = null;
        if (nextWithPreviousLookup) {
            nextWithPreviousLookup = false;
            row = lookupInstance;
//            swapInstances();
        } else {
            loadDataValues(sizeDataToRead);
            row = lookupInstance;
        }



        return row;
    }

}
