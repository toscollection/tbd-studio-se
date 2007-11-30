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

package org.talend.designer.components.thash.io.hashimpl;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.RandomAccessFile;

import org.talend.designer.components.thash.io.IMapHashFile;

/**
 * DOC slanglois class global comment. Detailled comment
 */
public class NewMultiplePointerSimpleHashFile implements IMapHashFile {

    private static NewMultiplePointerSimpleHashFile instance;

    private int pointersNumber = 10;

    public NewMultiplePointerSimpleHashFile(int pointersNumber) {
        this.pointersNumber = pointersNumber;
    }

    
    
    /**
     * DOC amaumont NewMultiplePointerSimpleHashFile constructor comment.
     */
    public NewMultiplePointerSimpleHashFile() {
        super();
    }



    /**
     * getInstance.
     * 
     * @return the instance if this project handler
     */
    public static synchronized NewMultiplePointerSimpleHashFile getInstance() {
        if (instance == null) {
            instance = new NewMultiplePointerSimpleHashFile();
        }
        return instance;
    }

    RandomAccessFile bw = null;

    boolean readonly;

    static final int START_POSITION = 0;

    long position;

    RandomAccessFile ra = null;

    Object lastRetrievedObject;

    long lastRetrievedCursorPosition = -1;

    private int count;

    boolean threaded = true;

    long totalGetTime = 0;

    private HashFileReader hfr = null;

    public Object get(String container, long cursorPosition, int hashcode) throws IOException, ClassNotFoundException {
        if (cursorPosition != lastRetrievedCursorPosition) {
            long timeBefore = System.currentTimeMillis();
            byte[] byteArray = hfr.read(cursorPosition);
            totalGetTime += System.currentTimeMillis() - timeBefore;
            lastRetrievedObject = new ObjectInputStream(new ByteArrayInputStream(byteArray)).readObject();
            lastRetrievedCursorPosition = cursorPosition;
            if ((++count + 1) % 1000 == 0) {
                System.out.println("Total time to get 1000 items from disk=" + totalGetTime + " ms");
                totalGetTime = 0;
            }
            // do scatter after 10000 items was get.
            // if ((count + 1) % 10000 == 0) {
            // hfr.scatterPointers();
            // }
        }
        return lastRetrievedObject;
    }

    public long put(String container, Object bean) throws IOException {

        ObjectOutputStream objectOutputStream = null;
        ByteArrayOutputStream byteArrayOutputStream = null;
        try {
            byteArrayOutputStream = new ByteArrayOutputStream();
            objectOutputStream = new ObjectOutputStream(byteArrayOutputStream);
            objectOutputStream.writeObject(bean);
            objectOutputStream.flush();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (objectOutputStream != null) {
                try {
                    objectOutputStream.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }

        int sizeBytes = byteArrayOutputStream.size();

        if (!readonly) {
            bw.writeInt(sizeBytes);
            bw.write(byteArrayOutputStream.toByteArray());
        }

        byteArrayOutputStream.close();

        long returnPosition = position;

        position += (4 + sizeBytes);

        return returnPosition;
    }

    public void initPut(String container) throws IOException {
        if (!readonly) {
            File file = new File(container);
            file.delete();
            position = START_POSITION;
            bw = new RandomAccessFile(container, "rw");
        }
    }

    public void endPut() throws IOException {
        if (!readonly) {
            bw.close();
        }
    }

    public void initGet(String container) throws IOException {
        hfr = new HashFileReader(container, pointersNumber);
        hfr.init();

    }

    public void endGet(String container) throws IOException {
        if (!readonly) {
            hfr.close();
            File file = new File(container);
            // file.delete();
        }
    }

    /*
     * (non-Javadoc)
     * 
     * @see org.talend.designer.components.thash.io.MapHashFile#getTotalSize()
     */
    public long getTotalSize() {
        // TODO Auto-generated method stub
        return 0;
    }



    /**
     * DOC amaumont Comment method "setFilePointersNumber".
     * @param numberOfPointers
     */
    public void setFilePointersNumber(int numberOfPointers) {
        this.pointersNumber = numberOfPointers;
    }

}
