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

import routines.system.IPersistableLookupRow;

/**
 * DOC amaumont class global comment. Detailled comment <br/>
 * 
 * $Id$
 * 
 */
public class OrderedBeanLookupMatchLast<B extends Comparable<B> & IPersistableLookupRow<B>> extends AbstractOrderedBeanLookup<B> {

    private boolean previousKeyLoaded;

    private boolean swaped;

    private boolean usePreviousForNextLookup;

    private int previousValuesSize;

    public OrderedBeanLookupMatchLast(String baseDirectory, int fileIndex, IRowProvider<B> rowProvider) throws IOException {
        super(baseDirectory, fileIndex, rowProvider);
        lookupInstance = rowProvider.createInstance();
        previousLookupInstance = rowProvider.createInstance();
        resultLookupInstance = rowProvider.createInstance();
    }

    /*
     * (non-Javadoc)
     * 
     * @see org.talend.designer.components.persistent.TestA#lookup(B)
     */
    public void lookup(B key) throws IOException {

        currentSearchedKey = key;

        nextDirty = true;

        if (previousKeyLoaded && previousAskedKey.compareTo(key) == 0) {
            nextWithPreviousLookup = true;
            nextDirty = false;
            hasNext = true;
        } else {
            if (usePreviousForNextLookup) {
                // swapInstances();
                // skipValuesSize -= currentValuesSize;
            }
            // if (hasNext && nextWithPreviousLookup && previousLookupInstance.compareTo(currentSearchedKey) == 0 &&
            // previousLookupInstance.compareTo(previousAskedKey) > 0) {
            // swapInstances();
            // }
            hasNext = false;
            nextDirty = true;
            startWithNewKey = true;
            nextWithPreviousLookup = false;
        }

        key.copyDataTo(previousAskedKey);
        previousKeyLoaded = true;

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
            int compareResult = -1;

            int localSkip = 0;

            boolean endOfFile = cursorPosition >= length;

            if (endOfFile && !swaped) {
                swaped = true;
//                swapInstances();
            }

            if (atLeastOneLoadkeys) {
                compareResult = lookupInstance.compareTo(currentSearchedKey);
                // if(compareResult != 0) {
                // int compareResultInternal = previousLookupInstance.compareTo(currentSearchedKey);
                // if(compareResultInternal == 0) {
                // compareResult = compareResultInternal;
                // swapInstances();
                // }
                // }

                if (compareResult == 0) {
                    if(endOfFile) {
                        lookupInstance.copyKeysDataTo(resultLookupInstance);
                    } else {
                        localSkip += currentValuesSize;
                        compareResult = -1;
                    }

                    // if (startWithNewKey) {
                    // if (!endOfFile) {
                    // if (usePreviousForNextLookup) {
                    // usePreviousForNextLookup = false;
                    // } else {
                    // localSkip += currentValuesSize;
                    // compareResult = -1;
                    // }
                    // }
                    // } else {
                    // nextWithPreviousLookup = true;
                    // if (previousCompareResultMatch) {
                    // remainingSkip = 0;
                    // }
                    // }
                } else if (compareResult < 0) {
                    // if(previousLookupInstance.compareTo(currentSearchedKey) > 0) {
                    // compareResult = 1;
                    // swapInstances();
                    // } else {
                    // localSkip += currentValuesSize;
                    // }
                }
            }
            startWithNewKey = false;

            if (!endOfFile && (compareResult < 0 || !atLeastOneLoadkeys)) {

                boolean searchingNextNotMatchAfterMatchFound = false;
                do {

                    loadDataKeys(lookupInstance);
                    compareResult = lookupInstance.compareTo(currentSearchedKey);
                    if (compareResult > 0) {
                        // remainingSkip = currentValuesSize;
                        // localSkip += currentValuesSize;
                    }

                    endOfFile = cursorPosition >= length;
                    if (compareResult >= 0 || endOfFile) {

                        if (!searchingNextNotMatchAfterMatchFound && compareResult == 0 && !endOfFile) {
                            searchingNextNotMatchAfterMatchFound = true;
                        } else if (compareResult > 0 || endOfFile) {
                            // localSkip -= currentValuesSize;
                            
                            if(endOfFile && compareResult == 0) {
                                
                                System.out.println();
                                
                            } else {
                                sizeDataToRead = previousValuesSize;
                                localSkip -= previousValuesSize;
                            }
                            
                            remainingSkip = 0;
                            usePreviousForNextLookup = true;
                            previousLookupInstance.copyKeysDataTo(resultLookupInstance);
                            lookupInstance.copyKeysDataTo(previousLookupInstance);
                            compareResult = 0;
                            previousValuesSize = currentValuesSize;
                            break;
                        }
                        swapInstances();
                        localSkip += currentValuesSize;
                        previousValuesSize = currentValuesSize;
                    }
                    if (compareResult < 0 && !searchingNextNotMatchAfterMatchFound) {
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
            row = resultLookupInstance;
            // swapInstances();
        } else {
            loadDataValues(resultLookupInstance, sizeDataToRead);
            row = resultLookupInstance;

        }

        return row;
    }

}
