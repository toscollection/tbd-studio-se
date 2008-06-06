package org.talend.designer.components.thash.io.hashimpl;

import java.io.IOException;
import java.util.List;

public interface IAdvancedLookup<K, V> {

    public Object[] getResultArray();

    public List<V> getResultList();

    public V getResultObject();

    /**
     * @param container
     * @throws IOException
     * @see org.talend.designer.components.thash.io.hashimpl.SortedMultipleHashFile#initGet(java.lang.String)
     */
    public void initGet();

    public void lookup(K key);

    /**
     * @param container
     * @throws IOException
     * @throws IOException
     * @see org.talend.designer.components.thash.io.hashimpl.SortedMultipleHashFile#endGet(java.lang.String)
     */
    public void endGet(String container) throws IOException;

    public boolean resultIsObject();

    public boolean resultIsList();

    /**
     * @param container
     * @throws IOException
     * @see org.talend.designer.components.thash.io.hashimpl.SortedMultipleHashFile#initPut(java.lang.String)
     */
    public void initPut();

    public V put(V value);

    /**
     * @throws IOException
     * @see org.talend.designer.components.thash.io.hashimpl.SortedMultipleHashFile#endPut()
     */
    public void endPut();

    public void clear();

    /**
     * DOC amaumont Comment method "hasResult".
     * 
     * @return
     */
    public boolean hasResult();

    /**
     * Getter for hasHashKeys.
     * 
     * @return the hasHashKeys
     */
    public boolean isUseHashKeys();

    /**
     * Getter for countValuesForEachKey.
     * 
     * @return the countValuesForEachKey
     */
    public boolean isCountValuesForEachKey();

    /**
     * Getter for keepAllValues.
     * 
     * @return the keepAllValues
     */
    public boolean isKeepAllValues();

    /**
     * Getter for uniqueMatch.
     * 
     * @return the uniqueMatch
     */
    public boolean isUniqueMatch();

    /**
     * Getter for uniqueMatch.
     * 
     * @return the uniqueMatch
     */
    public boolean isOnlyOneMatchResult();

}
