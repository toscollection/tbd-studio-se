package org.talend.designer.components.persistent;

import java.io.IOException;

public interface IPersistentLookupManager<B> {

    public void initPut() throws IOException;

    public void put(B bean) throws IOException;

    public void endPut() throws IOException;

    public void initGet() throws IOException;

    public void lookup(B key) throws IOException;

    public boolean hasNext() throws IOException;

    public B next() throws IOException;

    public void endGet() throws IOException;

    public void clear() throws IOException;

    public B getNextFreeRow();

}
