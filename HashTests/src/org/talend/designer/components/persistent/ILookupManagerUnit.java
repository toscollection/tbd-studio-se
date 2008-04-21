package org.talend.designer.components.persistent;

import java.io.IOException;

import routines.system.IPersistableLookupRow;

public interface ILookupManagerUnit<B extends Comparable<B> & IPersistableLookupRow<B>> {

    public abstract void lookup(B key) throws IOException;

    /**
     * DOC slanglois Comment method "hasNext".
     * 
     * @return
     * @throws IOException
     */
    public abstract boolean hasNext() throws IOException;

    /**
     * DOC slanglois Comment method "next".
     * 
     * @return
     * @throws IOException
     */
    public abstract B next() throws IOException;

    /**
     * DOC slanglois Comment method "close".
     * 
     * @throws IOException
     */
    public abstract void close() throws IOException;

}
