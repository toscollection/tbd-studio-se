package org.talend.designer.components.persistent;

import java.io.BufferedInputStream;
import java.io.DataInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;

import routines.system.IPersistableLookupRow;


public abstract class AbstractOrderedBeanLookup<B extends Comparable<B> & IPersistableLookupRow<B>> implements ILookupManagerUnit<B> {

    protected static final int MARK_READ_LIMIT = 256 * 1024 * 1024;

    protected static final int KEYS_SIZE_PLUS_VALUES_SIZE = 8;

    protected DataInputStream keysDataStream;

    protected InputStream valuesDataStream;

    protected long cursorPosition;

    protected long length;

    protected B lookupInstance;

    protected int currentValuesSize;

    protected int skipValuesSize;

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

    private int globalValuesSize;

    private int globalSkippedValues;

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
        
        this.cursorPosition = 0;
        this.keysDataStream = new DataInputStream(new BufferedInputStream(new FileInputStream(keysDataFile)));
        this.valuesDataStream = new BufferedInputStream(new FileInputStream(valuesFilePath));
        this.lookupInstance = rowProvider.createInstance();
        this.previousAskedKey = rowProvider.createInstance();
        this.rowProvider = rowProvider;

    }

    /* (non-Javadoc)
     * @see org.talend.designer.components.persistent.ILookupManager#lookup(B)
     */
    public abstract void lookup(B key) throws IOException;

    /* (non-Javadoc)
     * @see org.talend.designer.components.persistent.ILookupManager#hasNext()
     */
    public abstract boolean hasNext() throws IOException;

    /* (non-Javadoc)
     * @see org.talend.designer.components.persistent.ILookupManager#next()
     */
    public abstract B next() throws IOException;

    protected void loadDataKeys(B lookupInstance) throws IOException {
        atLeastOneLoadkeys = true;
        int keysDataLength = keysDataStream.readInt();
        byte[] bytes = new byte[keysDataLength];
        keysDataStream.read(bytes);
        lookupInstance.loadKeysData(bytes);
        currentValuesSize = keysDataStream.readInt();
        cursorPosition += (keysDataLength + KEYS_SIZE_PLUS_VALUES_SIZE);
    }

    protected void loadDataValues(B lookupInstance, int valuesSize) throws IOException {
        if (skipValuesSize > 0) {
            skipValuesSize += remainingSkip;
            valuesDataStream.skip(skipValuesSize);
            globalSkippedValues += skipValuesSize;
            //System.out.println("Data skipped:" + skipValuesSize);
            remainingSkip = 0;
            skipValuesSize = 0;
        }
        byte[] bytes = new byte[valuesSize];
        valuesDataStream.read(bytes);
        lookupInstance.loadValuesData(bytes);
        globalValuesSize += valuesSize;
        //System.out.println("Data read:" + valuesSize);
        
        //System.out.println("cursorPosition:" + (globalValuesSize + globalSkippedValues));
        
        
    }


    /* (non-Javadoc)
     * @see org.talend.designer.components.persistent.ILookupManager#close()
     */
    public void close() throws IOException {
        if (keysDataStream != null) {
            keysDataStream.close();
        }
        if (valuesDataStream != null) {
            valuesDataStream.close();
        }
    }


}
