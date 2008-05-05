package org.talend.designer.components.persistent;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

import org.talend.designer.components.commons.AdvancedLookup.MATCHING_MODE;

import routines.system.IPersistableRow;


public class PersistentLookupManager <B extends IPersistableRow<B>> implements IPersistentLookupManager<B> {


    private String container;
    private DataOutputStream dataOut;
    private DataInputStream dataIn;
    private IRowCreator<B> rowCreator;
    private B dataInstance;
    private MATCHING_MODE matchingMode;

    /**
     * DOC amaumont PersistentLookupManager constructor comment.
     * @param container
     */
    public PersistentLookupManager(MATCHING_MODE matchingMode, String container, IRowCreator<B> rowCreator) {
        super();
        this.matchingMode = matchingMode;
        this.container = container;
        this.rowCreator = rowCreator;
        this.dataInstance = this.rowCreator.createRowInstance();
    }

    public void initPut() throws IOException {

        dataOut = new DataOutputStream(new BufferedOutputStream(new FileOutputStream(buildDataFilePath())));
        
    }
    
    private String buildDataFilePath() {
        return container + "_Data.bin";
    }


    public void put(B bean) throws IOException {
        bean.writeData(dataOut);
    }
    
    public void endPut() throws IOException {
        
        dataOut.close();
        
    }
    
    public void initGet() throws IOException {
        
        initDataIn();

    }
    
    public void lookup(B key) throws IOException {

        if(this.dataIn != null) {
            this.dataIn.close();
        }
        
        initDataIn();

    }

    private void initDataIn() throws FileNotFoundException {
        this.dataIn = new DataInputStream(new BufferedInputStream(new FileInputStream(buildDataFilePath())));
    }

    public B getNextFreeRow() {
        return this.dataInstance;
    }
    
    public boolean hasNext() throws IOException {
        return this.dataIn.available() > 0;
    }
    
    public B next() throws IOException {
        dataInstance.readData(this.dataIn);
        return dataInstance;
    }
    
    public void endGet() throws IOException {
        
        this.dataIn.close();
     
        File file = new File(buildDataFilePath());
        file.delete();
        
    }

    public void clear() throws IOException {
        
    }
    

}
