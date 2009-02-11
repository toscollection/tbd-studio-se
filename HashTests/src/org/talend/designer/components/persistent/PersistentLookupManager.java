package org.talend.designer.components.persistent;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

import org.talend.designer.components.commons.AdvancedLookup.MATCHING_MODE;
import org.talend.designer.components.persistent.utils.FileUtils;

import routines.system.IPersistableRow;

public class PersistentLookupManager<B extends IPersistableRow<B>> implements IPersistentLookupManager<B>, Cloneable {

    private String container;

    private ObjectOutputStream objectOutStream;

    private BufferedInputStream bufferedInStream;
    private ObjectInputStream objectInStream;

    private IRowCreator<B> rowCreator;

    private B dataInstance;

    private MATCHING_MODE matchingMode;


    /**
     * DOC amaumont PersistentLookupManager constructor comment.
     * 
     * @param container
     * @throws IOException
     */
    public PersistentLookupManager(MATCHING_MODE matchingMode, String container, IRowCreator<B> rowCreator) throws IOException {
        super();
        this.matchingMode = matchingMode;
        this.container = container;
        this.rowCreator = rowCreator;
        FileUtils.createParentFolderIfNotExists(container);
    }

    public void initPut() throws IOException {

        objectOutStream = new ObjectOutputStream(new BufferedOutputStream(new FileOutputStream(buildDataFilePath())));
        this.dataInstance = this.rowCreator.createRowInstance();

    }

    private String buildDataFilePath() {
        return container + "_Data.bin";
    }

    public void put(B bean) throws IOException {
        bean.writeData(objectOutStream);
    }

    public void endPut() throws IOException {

        objectOutStream.close();

    }

    public void initGet() throws IOException {

        initDataIn();
        this.dataInstance = this.rowCreator.createRowInstance();

    }

    public void lookup(B key) throws IOException {

        if (this.objectInStream != null) {
            this.objectInStream.close();
        }

        initDataIn();

    }

    private void initDataIn() throws IOException {
        this.bufferedInStream = new BufferedInputStream(new FileInputStream(buildDataFilePath()));
        this.objectInStream = new ObjectInputStream(bufferedInStream);
    }

    public B getNextFreeRow() {
        return this.dataInstance;
    }

    public boolean hasNext() throws IOException {
        return this.bufferedInStream.available() > 0;
    }

    public B next() throws IOException {
        dataInstance.readData(this.objectInStream);
        return dataInstance;
    }

    public void endGet() throws IOException {

        this.objectInStream.close();

        File file = new File(buildDataFilePath());
        file.delete();

    }

    public void clear() throws IOException {

    }

    @Override
    public Object clone() throws CloneNotSupportedException {
        return super.clone();
    }

}
