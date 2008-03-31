package org.talend.designer.components.persistent;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

import org.talend.designer.components.commons.AdvancedLookup.MATCHING_MODE;
import org.talend.designer.components.thash.io.hashimpl.IAdvancedLookup;
import org.talend.designer.components.thash.io.hashimpl.SortedMultipleHashFile;

public class PersistentSortedAdvancedLookup<K, V> implements IAdvancedLookup<K, V> {

    List<V> list = new ArrayList<V>();

    Object[] arrayValues;

    boolean arrayIsDirty = true;

    List<V> listResult;

    V objectResult;

    MATCHING_MODE matchingMode;

    static final int ZERO = 0;

    static final int ONE = 1;

    IPersistentHash<K, V> persistentSortedHash;

    String container;

    private Comparator<Object> keyValueComparator;

    public PersistentSortedAdvancedLookup(MATCHING_MODE matchingMode, String container, Comparator<Object> keyValueComparator) {
        if (container == null) {
            throw new IllegalArgumentException("container can't be null");
        }
        if (keyValueComparator == null) {
            throw new IllegalArgumentException("keyValueComparator can't be null");
        }
        this.container = container;
        this.keyValueComparator = keyValueComparator;

        KEYS_MANAGEMENT keysManagement = null;
        switch (matchingMode) {
        case FIRST_MATCH:
            keysManagement = KEYS_MANAGEMENT.KEEP_FIRST;
            break;
        case LAST_MATCH:
        case UNIQUE_MATCH:
            keysManagement = KEYS_MANAGEMENT.KEEP_LAST;
            break;
        case ALL_MATCHES:
        case ALL_ROWS:
            keysManagement = KEYS_MANAGEMENT.KEEP_ALL;
            break;

        default:
            throw new IllegalArgumentException("matchingMode unknown");
        }

        persistentSortedHash = new PersistentSortedHash(keysManagement, container, keyValueComparator);
        
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
        try {
            this.persistentSortedHash.initGet();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public void lookup(K key) {
        if (matchingMode == MATCHING_MODE.UNIQUE_MATCH) {

            persistentSortedHash.lookup(key);

            try {

                if (persistentSortedHash.hasNext()) {
                    V current = (V) persistentSortedHash.next();
                    objectResult = (V) current;
                }

            } catch (IOException e) {
                objectResult = null;
                e.printStackTrace();
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
            this.persistentSortedHash.endGet();
        } catch (IOException e) {
            throw new RuntimeException(e);
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
            this.persistentSortedHash.initPut();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public V put(V value) {

        if (value != null) {
            if (matchingMode == MATCHING_MODE.UNIQUE_MATCH && !keepAllValues) {
                // V previousValue = uniqueHash.put(value, value);
                // incrementCountValues(value, previousValue);
                try {
                    persistentSortedHash.put(value);
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
                    return null;
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
            this.persistentSortedHash.endPut();
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
    void incrementCountValues(V value, V previousValue) {
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
            persistentSortedHash.endGet(container);
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

}
