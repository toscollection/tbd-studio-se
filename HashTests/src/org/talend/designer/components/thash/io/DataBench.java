// ============================================================================
//
// Copyright (C) 2006-2007 Talend Inc. - www.talend.com
//
// This source code is available under agreement available at
// %InstallDIR%\features\org.talend.rcp.branding.%PRODUCTNAME%\%PRODUCTNAME%license.txt
//
// You should have received a copy of the agreement
// along with this program; if not, write to Talend SA
// 9 rue Pages 92150 Suresnes, France
//
// ============================================================================
package org.talend.designer.components.thash.io;

import java.text.SimpleDateFormat;
import java.util.Date;

import org.apache.commons.beanutils.PropertyUtils;
import org.talend.designer.components.thash.io.HashFilesBenchs.PERSISTENT_METHOD;

/**
 * 
 * DOC amaumont  class global comment. Detailled comment
 * <br/>
 *
 */
public class DataBench implements Cloneable {

    private final static String[] PROPERTIES = new String[] {
        "persistentMethod",
        "nbItems",
        "nbFiles",
        "pointersByFile",
        "initialCapacityMap",
        "loadFactorMap",
        "startWriteDate",
        "endWriteDate",
        "timeWrite",
        "heapMemoryWrite",
        "bytesPerItemWrite",
        "itemsPerSecWrite",
        "writeEndedWithSuccess",
        "writeError",
        "totalFilesSize",
        "startReadDate",
        "endReadDate",
        "timeRead",
        "heapMemoryRead",
        "bytesPerItemRead",
        "itemsPerSecRead",
        "readEndedWithSuccess",
        "readError",
            };

    private int nbItems;

    private int nbFiles;

    private int pointersByFile;

    private long heapMemoryWrite; // bytes

    private long heapMemoryRead; // bytes

    private long totalFilesSize; // bytes

    private long timeWrite; // ms

    private long timeRead; // ms

    private int bytesPerItemWrite; // bytes

    private int averageBytesPerItemRead; // bytes

    private float loadFactorMap;

    private int initialCapacityMap;

    private boolean writeEndedWithSuccess;

    private String writeError;

    private boolean readEndedWithSuccess;

    private String readError;

    private int itemsPerSecWrite;

    private int itemsPerSecRead;

    private Date startWriteDate;

    private Date endWriteDate;

    private Date startReadDate;

    private Date endReadDate;

    private SimpleDateFormat sdf = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");

    private PERSISTENT_METHOD persistentMethod;

    /*
     * (non-Javadoc)
     * 
     * @see java.lang.Object#clone()
     */
    @Override
    protected Object clone() throws CloneNotSupportedException {
        return super.clone();
    }

    /**
     * Getter for nbItems.
     * 
     * @return the nbItems
     */
    public int getNbItems() {
        return this.nbItems;
    }

    /**
     * Sets the nbItems.
     * 
     * @param nbItems the nbItems to set
     */
    public void setNbItems(int nbItems) {
        this.nbItems = nbItems;
    }

    /**
     * Getter for nbFiles.
     * 
     * @return the nbFiles
     */
    public int getNbFiles() {
        return this.nbFiles;
    }

    /**
     * Sets the nbFiles.
     * 
     * @param nbFiles the nbFiles to set
     */
    public void setNbFiles(int nbFiles) {
        this.nbFiles = nbFiles;
    }

    /**
     * Getter for pointersByFile.
     * 
     * @return the pointersByFile
     */
    public int getPointersByFile() {
        return this.pointersByFile;
    }

    /**
     * Sets the pointersByFile.
     * 
     * @param pointersByFile the pointersByFile to set
     */
    public void setPointersByFile(int pointersByFile) {
        this.pointersByFile = pointersByFile;
    }

    /**
     * Getter for heapMemoryWrite.
     * 
     * @return the heapMemoryWrite
     */
    public long getHeapMemoryWrite() {
        return this.heapMemoryWrite;
    }

    /**
     * Sets the heapMemoryWrite.
     * 
     * @param heapMemoryWrite the heapMemoryWrite to set
     */
    public void setHeapMemoryWrite(long heapMemoryWrite) {
        this.heapMemoryWrite = heapMemoryWrite;
    }

    /**
     * Getter for heapMemoryRead.
     * 
     * @return the heapMemoryRead
     */
    public long getHeapMemoryRead() {
        return this.heapMemoryRead;
    }

    /**
     * Sets the heapMemoryRead.
     * 
     * @param heapMemoryRead the heapMemoryRead to set
     */
    public void setHeapMemoryRead(long heapMemoryRead) {
        this.heapMemoryRead = heapMemoryRead;
    }

    /**
     * Getter for totalFilesSize.
     * 
     * @return the totalFilesSize
     */
    public long getTotalFilesSize() {
        return this.totalFilesSize;
    }

    /**
     * Sets the totalFilesSize.
     * 
     * @param totalFilesSize the totalFilesSize to set
     */
    public void setTotalFilesSize(long totalFilesSize) {
        this.totalFilesSize = totalFilesSize;
    }

    /**
     * Getter for timeWrite.
     * 
     * @return the timeWrite
     */
    public long getTimeWrite() {
        return this.timeWrite;
    }

    /**
     * Sets the timeWrite.
     * 
     * @param timeWrite the timeWrite to set
     */
    public void setTimeWrite(long timeWrite) {
        this.timeWrite = timeWrite;
    }

    /**
     * Getter for timeRead.
     * 
     * @return the timeRead
     */
    public long getTimeRead() {
        return this.timeRead;
    }

    /**
     * Sets the timeRead.
     * 
     * @param timeRead the timeRead to set
     */
    public void setTimeRead(long timeRead) {
        this.timeRead = timeRead;
    }

    /**
     * Getter for averageBytesPerItemWrite.
     * 
     * @return the averageBytesPerItemWrite
     */
    public int getBytesPerItemWrite() {
        return this.bytesPerItemWrite;
    }

    /**
     * Sets the averageBytesPerItemWrite.
     * 
     * @param bytesPerItemWrite the averageBytesPerItemWrite to set
     */
    public void setBytesPerItemWrite(int bytesPerItemWrite) {
        this.bytesPerItemWrite = bytesPerItemWrite;
    }

    /**
     * Getter for averageBytesPerItemRead.
     * 
     * @return the averageBytesPerItemRead
     */
    public int getBytesPerItemRead() {
        return this.averageBytesPerItemRead;
    }

    /**
     * Sets the averageBytesPerItemRead.
     * 
     * @param bytesPerItemRead the averageBytesPerItemRead to set
     */
    public void setBytesPerItemRead(int bytesPerItemRead) {
        this.averageBytesPerItemRead = bytesPerItemRead;
    }

    /**
     * Getter for loadFactorMap.
     * 
     * @return the loadFactorMap
     */
    public float getLoadFactorMap() {
        return this.loadFactorMap;
    }

    /**
     * Sets the loadFactorMap.
     * 
     * @param loadFactorMap the loadFactorMap to set
     */
    public void setLoadFactorMap(float loadFactorMap) {
        this.loadFactorMap = loadFactorMap;
    }

    /**
     * Getter for initialCapacityMap.
     * 
     * @return the initialCapacityMap
     */
    public int getInitialCapacityMap() {
        return this.initialCapacityMap;
    }

    /**
     * Sets the initialCapacityMap.
     * 
     * @param initialCapacityMap the initialCapacityMap to set
     */
    public void setInitialCapacityMap(int initialCapacityMap) {
        this.initialCapacityMap = initialCapacityMap;
    }

    /**
     * Getter for writeEndedWithSuccess.
     * 
     * @return the writeEndedWithSuccess
     */
    public boolean isWriteEndedWithSuccess() {
        return this.writeEndedWithSuccess;
    }

    /**
     * Sets the writeEndedWithSuccess.
     * 
     * @param writeEndedWithSuccess the writeEndedWithSuccess to set
     */
    public void setWriteEndedWithSuccess(boolean writeEndedWithSuccess) {
        this.writeEndedWithSuccess = writeEndedWithSuccess;
    }

    /**
     * Getter for writeError.
     * 
     * @return the writeError
     */
    public String getWriteError() {
        return this.writeError;
    }

    /**
     * Sets the writeError.
     * 
     * @param writeError the writeError to set
     */
    public void setWriteError(String writeError) {
        this.writeError = writeError;
    }

    /**
     * Getter for readEndedWithSuccess.
     * 
     * @return the readEndedWithSuccess
     */
    public boolean isReadEndedWithSuccess() {
        return this.readEndedWithSuccess;
    }

    /**
     * Sets the readEndedWithSuccess.
     * 
     * @param readEndedWithSuccess the readEndedWithSuccess to set
     */
    public void setReadEndedWithSuccess(boolean readEndedWithSuccess) {
        this.readEndedWithSuccess = readEndedWithSuccess;
    }

    /**
     * Getter for readError.
     * 
     * @return the readError
     */
    public String getReadError() {
        return this.readError;
    }

    /**
     * Sets the readError.
     * 
     * @param readError the readError to set
     */
    public void setReadError(String readError) {
        this.readError = readError;
    }

    /**
     * Getter for itemsPerSecWrite.
     * @return the itemsPerSecWrite
     */
    public int getItemsPerSecWrite() {
        return this.itemsPerSecWrite;
    }

    /**
     * Sets the itemsPerSecWrite.
     * @param itemsPerSecWrite the itemsPerSecWrite to set
     */
    public void setItemsPerSecWrite(int itemsPerSecWrite) {
        this.itemsPerSecWrite = itemsPerSecWrite;
    }

    /**
     * Getter for itemsPerSecRead.
     * @return the itemsPerSecRead
     */
    public int getItemsPerSecRead() {
        return this.itemsPerSecRead;
    }

    /**
     * Sets the itemsPerSecRead.
     * @param itemsPerSecRead the itemsPerSecRead to set
     */
    public void setItemsPerSecRead(int itemsPerSecRead) {
        this.itemsPerSecRead = itemsPerSecRead;
    }

    /**
     * Getter for startWriteDate.
     * @return the startWriteDate
     */
    public Date getStartWriteDate() {
        return this.startWriteDate;
    }

    /**
     * Sets the startWriteDate.
     * @param startWriteDate the startWriteDate to set
     */
    public void setStartWriteDate(Date startWriteDate) {
        this.startWriteDate = startWriteDate;
    }

    /**
     * Getter for endWriteDate.
     * @return the endWriteDate
     */
    public Date getEndWriteDate() {
        return this.endWriteDate;
    }

    /**
     * Sets the endWriteDate.
     * @param endWriteDate the endWriteDate to set
     */
    public void setEndWriteDate(Date endWriteDate) {
        this.endWriteDate = endWriteDate;
    }

    /**
     * Getter for startReadDate.
     * @return the startReadDate
     */
    public Date getStartReadDate() {
        return this.startReadDate;
    }

    /**
     * Sets the startReadDate.
     * @param startReadDate the startReadDate to set
     */
    public void setStartReadDate(Date startReadDate) {
        this.startReadDate = startReadDate;
    }

    /**
     * Getter for endReadDate.
     * @return the endReadDate
     */
    public Date getEndReadDate() {
        return this.endReadDate;
    }

    /**
     * Sets the endReadDate.
     * @param endReadDate the endReadDate to set
     */
    public void setEndReadDate(Date endReadDate) {
        this.endReadDate = endReadDate;
    }

    /**
     * 
     * @return
     * @author
     */
    public String toString() {
        StringBuffer buffer = new StringBuffer();
        buffer.append("DataStats[");
        try {
            for (int i = 0; i < PROPERTIES.length; i++) {
                String propertyName = PROPERTIES[i];
                buffer.append(" " + propertyName + " = ").append(PropertyUtils.getProperty(this, propertyName));
            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        buffer.append("]");
        return buffer.toString();
    }

    public String toFileRow() {
        StringBuffer buffer = new StringBuffer();
        String comma = "";
        try {
            for (int i = 0; i < PROPERTIES.length; i++) {
                if (i == 1) {
                    comma = ";";
                }
                String propertyName = PROPERTIES[i];

                Object object = PropertyUtils.getProperty(this, propertyName);
                String value = null;
                if (object == null) {
                    value = "";
                } else if (object instanceof Date) {
                    value = sdf.format((Date) object);
                } else if (object instanceof PERSISTENT_METHOD) {
                    value = ((PERSISTENT_METHOD) object).getLabel();
                } else {
                    value = String.valueOf(object);
                }
                buffer.append(comma + value);
            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        return buffer.toString();
    }

    // buffer.append\("(.*);"\);
    // ,"$1"

    public static String getFileHeader() {
        StringBuffer buffer = new StringBuffer();
        String comma = "";
        try {
            for (int i = 0; i < PROPERTIES.length; i++) {
                if (i == 1) {
                    comma = ";";
                }
                String propertyName = PROPERTIES[i];
                buffer.append(comma + propertyName);
            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        return buffer.toString();
    }

    /**
     * DOC amaumont Comment method "setPersistMethod".
     * @param persistMethod
     */
    public void setPersistentMethod(PERSISTENT_METHOD persistMethod) {
        this.persistentMethod = persistMethod;
    }

    
    /**
     * Getter for persistMethod.
     * @return the persistMethod
     */
    public PERSISTENT_METHOD getPersistentMethod() {
        return this.persistentMethod;
    }
    
    

}
