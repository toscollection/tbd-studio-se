package org.talend.designer.components.thash.io.hashimpl;

import gnu.trove.THashMap;
import gnu.trove.TObjectHashingStrategy;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Map;

import org.apache.commons.collections.list.GrowthList;
import org.talend.commons.utils.data.map.MultiLazyValuesMap;
import org.talend.designer.components.commons.AdvancedLookup;
import org.talend.designer.components.thash.io.beans.ILightSerializable;
import org.talend.designer.components.thash.io.beans.IPersistentBean;
import org.talend.designer.components.thash.io.beans.KeyForMap;

public class PersistentAdvancedLookup<V> extends AdvancedLookup<V> {

    private MultiLazyValuesMap mapOfCol;

    private Map<KeyForMap, KeyForMap> uniqueHash;

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

    SortedMultipleHashFile hashFile;

    private String container = "/home/amaumont/hash_benchs/external_sort/tmp_";

    private boolean firstGet = true;

    private boolean firstPut = true;

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
    public PersistentAdvancedLookup(MATCHING_MODE matchingMode, boolean keepAllValues, boolean countValuesForEachKey) {
        super();
        this.keepAllValues = keepAllValues;
        this.matchingMode = matchingMode == null ? MATCHING_MODE.UNIQUE_MATCH : matchingMode;
        this.countValuesForEachKey = countValuesForEachKey || this.matchingMode == MATCHING_MODE.UNIQUE_MATCH;

        hashFile = new SortedMultipleHashFile() {

            /*
             * (non-Javadoc)
             * 
             * @see org.talend.designer.components.thash.io.hashimpl.SortedMultipleHashFile#processData(int,
             * org.talend.designer.components.thash.io.beans.ILightSerializable)
             */
            @Override
            public void processData(int cursorPosition, ILightSerializable data) {
                KeyForMap keyForMap = new KeyForMap(cursorPosition, data.hashCode());
                uniqueHash.put(keyForMap, keyForMap);

            }

        };

    }

    public static <V> PersistentAdvancedLookup<V> getLookup(MATCHING_MODE matchingMode) {
        return new PersistentAdvancedLookup<V>(matchingMode, false, false);
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

    /**
     * @param container
     * @throws IOException
     * @see org.talend.designer.components.thash.io.hashimpl.SortedMultipleHashFile#initGet(java.lang.String)
     */
    public void initGet() {
        initKeyContainers(hashFile.getObjectsCount());
        try {
            this.hashFile.initGet(container);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }


    public void get(V key) {
        if (matchingMode == MATCHING_MODE.UNIQUE_MATCH) {
            KeyForMap keyForMap = uniqueHash.get(key);
            if (keyForMap != null) {
                try {
                    objectResult = (V) hashFile.get(container, keyForMap.cursorPosition, keyForMap.hashcode);
                } catch (IOException e) {
                    objectResult = null;
                    e.printStackTrace();
                } catch (ClassNotFoundException e) {
                    objectResult = null;
                    e.printStackTrace();
                }
            } else {
                objectResult = null;
            }

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

    /**
     * @param container
     * @throws IOException 
     * @throws IOException
     * @see org.talend.designer.components.thash.io.hashimpl.SortedMultipleHashFile#endGet(java.lang.String)
     */
    public void endGet(String container) throws IOException {
        try {
            this.hashFile.endGet(container);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }


    /**
     * DOC amaumont Comment method "initKeyContainers".
     * 
     * @param objectsCount
     */
    private void initKeyContainers(int objectsCount) {
        if (matchingMode != MATCHING_MODE.ALL_ROWS) {
            TObjectHashingStrategy objectHashingStrategy = new TObjectHashingStrategy() {

                public int computeHashCode(Object arg0) {
                    return arg0 == null ? 0 : arg0.hashCode();
                }

                public boolean equals(Object arg0, Object arg1) {
                    return arg1 == null ? arg0 == null : arg1.equals(arg0);
                }

            };

            if (this.countValuesForEachKey) {
                counterHash = new THashMap<V, Integer>(objectsCount, 1.0f, objectHashingStrategy);
            }

            if (matchingMode == MATCHING_MODE.UNIQUE_MATCH && !keepAllValues) {
                uniqueHash = new THashMap<KeyForMap, KeyForMap>(objectsCount, 1.0f, objectHashingStrategy);
            } else {
                mapOfCol = new MultiLazyValuesMap(new THashMap(objectsCount, 1.0f, objectHashingStrategy)) {

                    @Override
                    public Collection instanciateNewCollection() {
                        return new GrowthList(3);
                    }

                };
            }
        }

    }

    public boolean resultIsObject() {
        return objectResult != null;
    }

    public boolean resultIsList() {
        return listResult != null;
    }

    /**
     * @param container
     * @throws IOException
     * @see org.talend.designer.components.thash.io.hashimpl.SortedMultipleHashFile#initPut(java.lang.String)
     */
    public void initPut() {
        try {
            this.hashFile.initPut(container);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public V put(V value) {

        if (firstPut) {
            hashFile.setILightSerializable((ILightSerializable)value);// set an Instance
            ((IPersistentBean) value).setHashFile(hashFile);
            firstPut = false;
        }

        if (value != null) {
            if (matchingMode == MATCHING_MODE.UNIQUE_MATCH && !keepAllValues) {
                // V previousValue = uniqueHash.put(value, value);
                // incrementCountValues(value, previousValue);
                try {
                    hashFile.put(container, value);
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
                // return previousValue;
                return null;
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
     * @throws IOException
     * @see org.talend.designer.components.thash.io.hashimpl.SortedMultipleHashFile#endPut()
     */
    public void endPut() {
        try {
            this.hashFile.endPut();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
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
        try {
            hashFile.endGet(container);
        } catch (IOException e) {
            throw new RuntimeException(e);
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

//    public static void main(String[] args) {
//
//        PersistentAdvancedLookup<Bean> lookup = PersistentAdvancedLookup.<Bean> getLookup(MATCHING_MODE.UNIQUE_MATCH);
//
//        final int nbItems = 100;
//
//        int[] randomArray = null;
//
//        boolean randomWrite = true;
//
//        if (randomWrite) {
//            randomArray = new int[nbItems];
//            for (int i = 0; i < randomArray.length; i++) {
//                randomArray[i] = i;
//            }
//            int j = 0;
//            Random rand = new Random(System.currentTimeMillis());
//            // shiffle unique values
//            for (int i = 0; i < randomArray.length; i++) {
//                j = rand.nextInt(nbItems);
//                int vj = randomArray[j];
//                randomArray[j] = randomArray[i];
//                randomArray[i] = vj;
//            }
//        }
//
//        for (int i = 0; i < nbItems; i++) {
//            int v = i;
//
//            if (randomWrite) {
//                v = randomArray[i];
//                // System.out.println("reandom value =" + v);
//            }
//            Bean bean = new Bean(v, String.valueOf(v));
//            lookup.put(bean);
//        }
//
//        for (int i = 0; i < nbItems; i++) {
//            Bean bean = new Bean(i, String.valueOf(i));
//            lookup.get(bean);
//            Bean persistentBean = lookup.getResultObject();
//
//            if (persistentBean != null) {
//                if (!persistentBean.name.equals(bean.name) || persistentBean.primitiveInt != bean.primitiveInt) {
//                    throw new RuntimeException("Bean data does not match " + i);
//                }
//            } else {
//                throw new NullPointerException("persistentBean should'nt be null");
//            }
//
//        }
//
//    }
}
