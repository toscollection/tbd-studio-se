package org.talend.designer.components.persistent;

import java.io.BufferedInputStream;
import java.io.DataInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.ObjectInputStream;

import routines.system.IPersistableLookupRow;

public abstract class AbstractOrderedBeanLookup<B extends Comparable<B> & IPersistableLookupRow<B>> implements
        ILookupManagerUnit<B> {

    protected static final int MARK_READ_LIMIT = 256 * 1024 * 1024;

    protected static final int KEYS_SIZE_PLUS_VALUES_SIZE = 8;

    protected BufferedInputStream keysBufferedInStream;
    
    protected ObjectInputStream keysObjectInStream;

    protected DataInputStream valuesDataInStream;
    
    protected ObjectInputStream valuesObjectInStream;

    protected long length;

    protected B lookupInstance;

    protected int currentValuesSize;

    protected long skipValuesSize;

    protected boolean nextDirty = true;

    protected boolean noMoreNext;

    protected B previousAskedKey;

    protected long markCursorPosition = -1;

    protected B currentSearchedKey;

    protected boolean hasNext;

    protected boolean atLeastOneLoadkeys = false;

    protected boolean startWithNewKey;

    protected IRowProvider<B> rowProvider;

    protected boolean nextWithPreviousLookup;

    protected int remainingSkip;

    protected boolean previousCompareResultMatch;

    protected B previousLookupInstance;

    protected int sizeDataToRead;

    protected B resultLookupInstance;

    protected int fileIndex;


    /**
     * 
     * DOC amaumont OrderedBeanLookup constructor comment.
     * 
     * @param keysFilePath
     * @param valuesFilePath
     * @param fileIndex
     * @param internalKeyInstance
     * @param keys_management
     * @throws IOException
     */
    public AbstractOrderedBeanLookup(String keysFilePath, String valuesFilePath, int fileIndex, IRowProvider<B> rowProvider)
            throws IOException {
        File keysDataFile = new File(keysFilePath);
        this.length = keysDataFile.length();

        this.fileIndex = fileIndex;

        this.keysBufferedInStream = new BufferedInputStream(new FileInputStream(keysDataFile));
        this.keysObjectInStream = new ObjectInputStream(keysBufferedInStream);
        this.valuesDataInStream = new DataInputStream(new BufferedInputStream(new FileInputStream(valuesFilePath)));
        this.valuesObjectInStream = new ObjectInputStream(this.valuesDataInStream);
        this.lookupInstance = rowProvider.createInstance();
        this.previousAskedKey = rowProvider.createInstance();
        this.rowProvider = rowProvider;

    }

    /*
     * (non-Javadoc)
     * 
     * @see org.talend.designer.components.persistent.ILookupManager#lookup(B)
     */
    public abstract void lookup(B key) throws IOException;

    /*
     * (non-Javadoc)
     * 
     * @see org.talend.designer.components.persistent.ILookupManager#hasNext()
     */
    public abstract boolean hasNext() throws IOException;

    /*
     * (non-Javadoc)
     * 
     * @see org.talend.designer.components.persistent.ILookupManager#next()
     */
    public abstract B next() throws IOException;

    protected void loadDataKeys(B lookupInstance) throws IOException {
        atLeastOneLoadkeys = true;
        lookupInstance.readKeysData(keysObjectInStream);
        currentValuesSize = keysObjectInStream.readInt();
    }

    protected boolean isEndOfKeysFile() throws IOException {
        return !(keysObjectInStream.available() > 0 || keysBufferedInStream.available() > 0);
    }

    protected void loadDataValues(B lookupInstance, int valuesSize) throws IOException {
        if (skipValuesSize > 0) {
            skipValuesSize += remainingSkip;

            int currentSkipped = 0;
            while (skipValuesSize != (currentSkipped += valuesDataInStream.skip(skipValuesSize - currentSkipped))) {
            }
            // System.out.println("Data skipped:" + skipValuesSize);
            remainingSkip = 0;
            skipValuesSize = 0;
        }
        lookupInstance.readValuesData(valuesDataInStream, valuesObjectInStream);

    }

    /*
     * (non-Javadoc)
     * 
     * @see org.talend.designer.components.persistent.ILookupManager#close()
     */
    public void close() throws IOException {
        if (keysObjectInStream != null) {
            keysObjectInStream.close();
        }
        if (keysBufferedInStream != null) {
            keysBufferedInStream.close();
        }
        if (valuesDataInStream != null) {
            valuesObjectInStream.close();
        }
    }

}
