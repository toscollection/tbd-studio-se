package org.talend.designer.components.persistent;

import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;


public class RowProvider<B> implements IRowProvider<B> {

    private List<B> cache = new ArrayList<B>();
    
    private int currentFreeIndex = 0;

    private int currentGetIndex = 0;

    private IRowCreator<B> rowCreator;
    
    public RowProvider(IRowCreator<B> rowCreator) {
        this.rowCreator = rowCreator;
    }

    public B getFreeInstance() {
        B row = null;
        if(currentFreeIndex >= cache.size()) {
            row = this.rowCreator.createRowInstance();
            cache.add(row);
        } else {
            row = cache.get(currentFreeIndex);
        }
        currentFreeIndex++;
        return row;
    }

    public boolean hasNext() {
        return currentGetIndex < currentFreeIndex - 1;
    }

    public B next() {
        if(currentGetIndex > currentFreeIndex - 1) {
            throw new NoSuchElementException();
        }
        B row = cache.get(currentGetIndex);
        currentGetIndex++;
        return row;
    }

    public B createInstance() {
        return this.rowCreator.createRowInstance();
    }

    public void resetFreeIndex() {
        currentFreeIndex = 0;
    }

    public void resetInstanceIndex() {
        currentGetIndex = 0;
    }

}
