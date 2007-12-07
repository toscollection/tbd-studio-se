package org.talend.designer.components.thash.io.hashimpl;

import gnu.trove.THashMap;
import gnu.trove.TObjectHashingStrategy;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Map;

import org.apache.commons.collections.list.GrowthList;
import org.talend.commons.utils.data.map.MultiLazyValuesMap;
import org.talend.core.model.process.IMatchingMode;

public class NewAdvancedLookup<V> {

    private MultiLazyValuesMap mapOfCol;

    private Map<V, V> uniqueHash;

    private boolean countValuesForEachKey;

    private Map<V, Integer> counterHash;

    private List<V> list = new ArrayList<V>();

    private Object[] arrayValues;

    private boolean arrayIsDirty = true;

    private List<V> listResult;

    private V objectResult;

    private boolean keepAllValues;

    private MATCHING_MODE matchingMode;

    private static final int ZERO = 0;

    private static final int ONE = 1;
    
    

    /**
     * 
     * DOC amaumont AdvancedLookup class global comment. Detailled comment <br/>
     * 
     */
    public enum MATCHING_MODE implements IMatchingMode {
        ALL_ROWS,
        ALL_MATCHES,
        FIRST_MATCH,
        LAST_MATCH,
        UNIQUE_MATCH, ;

        public static MATCHING_MODE parse(String matchingMode) {
            MATCHING_MODE multipleMatchingModeResult = null;
            MATCHING_MODE[] multipleMatchingModes = values();
            for (MATCHING_MODE multipleMatchingMode : multipleMatchingModes) {
                if (multipleMatchingMode.toString().equals(matchingMode)) {
                    multipleMatchingModeResult = multipleMatchingMode;
                    break;
                }
            }
            return multipleMatchingModeResult;
        }

    }

    /**
     * 
     * <code>AdvancedLookup</code> can be configured to store values in different modes.
     * 
     * @param useHashKeys use <code>equals()</code> and <code>hashCode()</code> methods by storing objects in hash
     * maps
     * @param matchingMode to optimize storing and searching, and to specify which matching mode should used
     * @param uniqueMatch keep in the lookup only the last put object, but store the current number of same values for
     * each key
     * @param keepAllValues keep all identical values (with same key values) in each list of each key
     * @param countValuesForEachKey force internal count of values
     */
    public NewAdvancedLookup(MATCHING_MODE matchingMode, boolean keepAllValues, boolean countValuesForEachKey) {
        super();
        this.keepAllValues = keepAllValues;
        this.matchingMode = matchingMode == null ? MATCHING_MODE.UNIQUE_MATCH : matchingMode;
        this.countValuesForEachKey = countValuesForEachKey || this.matchingMode == MATCHING_MODE.UNIQUE_MATCH;
        if (matchingMode != MATCHING_MODE.ALL_ROWS) {
            TObjectHashingStrategy objectHashingStrategy = new TObjectHashingStrategy() {

                public int computeHashCode(Object arg0) {
                    return arg0 == null ? 0 : arg0.hashCode();
                }

                public boolean equals(Object arg0, Object arg1) {
                    return arg1 == null ? arg0 == null : arg1.equals(arg0);
                }

            };
            if (matchingMode == MATCHING_MODE.UNIQUE_MATCH && !keepAllValues) {

                uniqueHash = new THashMap<V, V>(10000, 1.0f, objectHashingStrategy);
            }
            if (this.countValuesForEachKey) {
                counterHash = new THashMap<V, Integer>(10000, 1.0f, objectHashingStrategy);
            }

            mapOfCol = new MultiLazyValuesMap(new THashMap(10000, 1.0f, objectHashingStrategy)) {

                @Override
                public Collection instanciateNewCollection() {
                    return new GrowthList(3);
                }

            };
        }
    }

    public static <V> NewAdvancedLookup<V> getLookup(MATCHING_MODE matchingMode) {
        return new NewAdvancedLookup<V>(matchingMode, false, false);
    }

    public Object[] getResultArray() {
        if (matchingMode == MATCHING_MODE.ALL_ROWS) {
            if (listResult == null) {
                listResult = list;
            }
            if (arrayIsDirty) {
                arrayValues = listResult.toArray();
                arrayIsDirty = false;
            }
            return arrayValues;
        } else {
            return listResult.toArray();
        }
    }

    public List<V> getResultList() {
        return listResult;
    }

    public V getResultObject() {
        return objectResult;
    }

    public void get(V key) {
        if (matchingMode == MATCHING_MODE.UNIQUE_MATCH) {
            listResult = null;
            objectResult = uniqueHash.get(key);
        } else {
            if (matchingMode != MATCHING_MODE.ALL_ROWS && key != null) {
                Object v = mapOfCol.get(key);
                if (v instanceof List) {
                    List<V> localList = (List<V>) v;
                    if (matchingMode == MATCHING_MODE.ALL_MATCHES) {
                        listResult = localList;
                        objectResult = null;
                    } else if (matchingMode == MATCHING_MODE.FIRST_MATCH) {
                        objectResult = localList.get(ZERO);
                    } else if (matchingMode == MATCHING_MODE.LAST_MATCH) {
                        listResult = null;
                        objectResult = localList.get(localList.size() - ONE);
                    }
                } else {
                    objectResult = (V) v;
                    listResult = null;
                }
            } else {
                listResult = list;
                objectResult = null;
            }
        }
    }

    public boolean resultIsObject() {
        return objectResult != null;
    }

    public boolean resultIsList() {
        return listResult != null;
    }

    public V put(V value) {
        if (value != null) {
            if (matchingMode == MATCHING_MODE.UNIQUE_MATCH && !keepAllValues) {
                V previousValue = uniqueHash.put(value, value);
                incrementCountValues(value, previousValue);
                return previousValue;
            } else {
                if (matchingMode == MATCHING_MODE.ALL_ROWS) {
                    list.add(value);
                    return null;
                } else {
                    arrayIsDirty = true;
                    V previousValue = (V) mapOfCol.put(value, value);
                    incrementCountValues(value, previousValue);
                    return previousValue;
                }
            }
        }
        return null;
    }

    /**
     * DOC amaumont Comment method "incrementCountValues".
     * 
     * @param value
     * @param previousValue
     */
    private void incrementCountValues(V value, V previousValue) {
        if (countValuesForEachKey && previousValue != null) {
            Integer count = counterHash.get(value);
            if (count == null) {
                count = ONE;
            } else {
                count++;
            }
            counterHash.put(value, count);
        }
    }

    public void clear() {
        if (mapOfCol != null) {
            mapOfCol.clear();
        }
        if (uniqueHash != null) {
            uniqueHash.clear();
        }
        if (counterHash != null) {
            counterHash.clear();
        }
        arrayValues = null;
        if (list != null) {
            list.clear();
        }
        if (listResult != null) {
            listResult = null;
        }
    }

    /**
     * DOC amaumont Comment method "hasResult".
     * 
     * @return
     */
    public boolean hasResult() {
        return resultIsObject() || resultIsList();
    }

    /**
     * Getter for hasHashKeys.
     * 
     * @return the hasHashKeys
     */
    public boolean isUseHashKeys() {
        return matchingMode != MATCHING_MODE.ALL_ROWS;
    }

    /**
     * Getter for countValuesForEachKey.
     * 
     * @return the countValuesForEachKey
     */
    public boolean isCountValuesForEachKey() {
        return this.countValuesForEachKey;
    }

    /**
     * Getter for keepAllValues.
     * 
     * @return the keepAllValues
     */
    public boolean isKeepAllValues() {
        return this.keepAllValues;
    }

    /**
     * Getter for uniqueMatch.
     * 
     * @return the uniqueMatch
     */
    public boolean isUniqueMatch() {
        return matchingMode == MATCHING_MODE.UNIQUE_MATCH;
    }

    /**
     * Getter for uniqueMatch.
     * 
     * @return the uniqueMatch
     */
    public boolean isOnlyOneMatchResult() {
        return matchingMode == MATCHING_MODE.UNIQUE_MATCH || matchingMode == MATCHING_MODE.FIRST_MATCH
                || matchingMode == MATCHING_MODE.LAST_MATCH;
    }

    public int getCount(V key) {
        if (countValuesForEachKey) {
            Integer count = counterHash.get(key);
            if (count == null) {
                return ZERO;
            } else {
                return count;
            }
        } else if (matchingMode == MATCHING_MODE.UNIQUE_MATCH && !keepAllValues) {
            if (uniqueHash.get(key) != null) {
                return ONE;
            } else {
                return ZERO;
            }

        } else if (matchingMode != MATCHING_MODE.ALL_ROWS) {
            Object v = mapOfCol.get(key);
            if (v instanceof List) {
                List<V> localList = (List<V>) v;
                return localList.size();
            } else {
                if (v != null) {
                    return ONE;
                } else {
                    return ZERO;
                }
            }
        } else {
            if (list.contains(key)) {
                return 1;
            } else {
                return ZERO;
            }
        }
    }
}
