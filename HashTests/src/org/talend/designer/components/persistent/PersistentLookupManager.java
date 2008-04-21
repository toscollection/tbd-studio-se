package org.talend.designer.components.persistent;

import java.io.IOException;

import routines.system.IPersistableLookupRow;


public class PersistentLookupManager <B extends Comparable<B> & IPersistableLookupRow<B>> extends 
AbstractPersistentLookup<B> implements IPersistentLookupManager<B> {

    public void initPut() throws IOException {
        
    }
    
    public void put(B bean) throws IOException {
        
    }
    
    public void endPut() throws IOException {
        
    }
    
    public void initGet() throws IOException {
        
    }
    
    public void lookup(B key) throws IOException {
        
    }

    public B getNextFreeRow() {
        return null;
    }
    
    public boolean hasNext() throws IOException {
        return false;
    }
    
    public B next() throws IOException {
        return null;
    }
    
    public void endGet() throws IOException {
        
    }

    public void clear() throws IOException {
        
    }
    

}
