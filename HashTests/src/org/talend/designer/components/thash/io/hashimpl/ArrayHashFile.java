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
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.io.Serializable;
import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.talend.designer.components.thash.io.HashFilesBenchs;
import org.talend.designer.components.thash.io.IMapHashFile;
import org.talend.designer.components.thash.io.beans.KeyForMap;

/**
 * 
 * DOC slanglois class global comment. Detailled comment <br/>
 * 
 * 
 * 11687 milliseconds for 1000000 beans to STORE using ArrayHashFile. 85000 items/s. 17 bytes per item in storage. 
 * 20578 milliseconds for 1000000 beans to STORE using SimpleHashFile. 48000 items/s. 137 bytes per item in storage. 
 * 14406 milliseconds for 1000000 big beans to STORE using ArrayHashFile. 69000 items/s. 72 bytes per item in storage. 
 * 34547 milliseconds for 1000000 beans to STORE using SimpleHashFile. 28000 items/s. 329 bytes per item in storage.
 * 
 * 
 */
class ArrayHashFile implements IMapHashFile {

    private static ArrayHashFile instance;

    private ArrayHashFile() {
    }

    /**
     * getInstance.
     * 
     * @return the instance if this project handler
     */
    public static synchronized ArrayHashFile getInstance() {
        if (instance == null) {
            instance = new ArrayHashFile();
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

    public Object get(String container, long cursorPosition, int hashcode) throws IOException, ClassNotFoundException {
        if (cursorPosition != lastRetrievedCursorPosition) {
            ra.seek(cursorPosition);
            byte[] byteArray = new byte[ra.readInt()];
            ra.read(byteArray);
            lastRetrievedObject = byteArray;
            lastRetrievedCursorPosition = cursorPosition;
        }
        return lastRetrievedObject;
    }

    public long put(String container, Object object) throws IOException {

        byte[] byteArray = (byte[]) object;
        int sizeBytes = byteArray.length;
        if (!readonly) {
            bw.writeInt(sizeBytes);
            bw.write(byteArray);
        }

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

    public void initGet(String container) throws FileNotFoundException {
        ra = new RandomAccessFile(container, "r");
    }

    public void endGet(String container) throws IOException {
        if (!readonly) {
            if (ra != null) {
                ra.close();
            }
            File file = new File(container);
            file.delete();
        }
    }

    public static void main(String[] args) throws IOException, IllegalAccessException, InvocationTargetException,
            NoSuchMethodException, ClassNotFoundException, InstantiationException {
        int loop = 1000000;

        String folder = "/tmp/";

        // test bean write using ArrayHashFile
        ArrayHashFile nihf = ArrayHashFile.getInstance();
        List<Long> cursors = new ArrayList<Long>();
        long start = System.currentTimeMillis();

        nihf.initPut(folder + "cache0");
        for (int i = 1; i < loop; i++) {
            InternalSmallBean bean = new InternalSmallBean(i, String.valueOf(i));
            cursors.add(nihf.put("", bean.toByteArray()));
        }
        nihf.endPut();

        long end = System.currentTimeMillis();
        long deltaTime = (end - start);
        System.out.print(deltaTime + " milliseconds for " + loop + " beans to STORE using ArrayHashFile. "
                + (loop / deltaTime * 1000) + " items/s. ");
        File file = new File(folder + "cache0");
        System.out.println((file.length() / loop) + " bytes per item in storage.");

        // nihf.initGet(folder + "cache0");
        //
        // for (int i = 0; i < cursors.size(); i++) {
        // InternalSmallBean be = new InternalSmallBean((byte[]) nihf.get("", cursors.get(i), -1));
        // System.out.println(be.primitiveInt + ": " + be.name);
        // }
        // nihf.endGet(folder + "cache0");

        // test bean write using SimpleHashFile
        SimpleHashFile shf = SimpleHashFile.getInstance();
        cursors = new ArrayList<Long>();
        start = System.currentTimeMillis();

        shf.initPut(folder + "cache1");
        for (int i = 1; i < loop; i++) {
            InternalSmallBean bean = new InternalSmallBean(i, String.valueOf(i));
            cursors.add(shf.put("", bean));
        }
        shf.endPut();

        end = System.currentTimeMillis();
        deltaTime = (end - start);
        System.out.print(deltaTime + " milliseconds for " + loop + " beans to STORE using SimpleHashFile. "
                + (loop / deltaTime * 1000) + " items/s. ");
        file = new File(folder + "cache1");
        System.out.println((file.length() / loop) + " bytes per item in storage.");

        // test big bean write using ArrayHashFile
        nihf = ArrayHashFile.getInstance();
        cursors = new ArrayList<Long>();
        start = System.currentTimeMillis();

        nihf.initPut(folder + "cache2");
        for (int i = 1; i < loop; i++) {
            InternalBigBean bean = new InternalBigBean(i, String.valueOf(i));
            cursors.add(nihf.put("", bean.toByteArray()));
        }
        nihf.endPut();

        end = System.currentTimeMillis();
        deltaTime = (end - start);
        System.out.print(deltaTime + " milliseconds for " + loop + " big beans to STORE using ArrayHashFile. "
                + (loop / deltaTime * 1000) + " items/s. ");
        file = new File(folder + "cache2");
        System.out.println((file.length() / loop) + " bytes per item in storage.");

        // nihf.initGet(folder + "cache2");
        //
        // for (int i = 0; i < cursors.size(); i++) {
        // InternalBigBean be = new InternalBigBean((byte[]) nihf.get("", cursors.get(i), -1));
        // System.out.println(be.primitiveInt + ": " + be.name + "#" + be.address + "#" + be.price + "#" + be.date + "#"
        // + be.date2 + "#" + be.b.toString() + "#" + be.flag);
        // }
        // nihf.endGet(folder + "cache2");

        // test big bean write using SimpleHashFile
        shf = SimpleHashFile.getInstance();
        cursors = new ArrayList<Long>();
        start = System.currentTimeMillis();

        shf.initPut(folder + "cache3");
        for (int i = 1; i < loop; i++) {
            InternalBigBean bean = new InternalBigBean(i, String.valueOf(i));
            cursors.add(shf.put("", bean));
        }
        shf.endPut();

        end = System.currentTimeMillis();
        deltaTime = (end - start);
        System.out.print(deltaTime + " milliseconds for " + loop + " beans to STORE using SimpleHashFile. "
                + (loop / deltaTime * 1000) + " items/s. ");
        file = new File(folder + "cache3");
        System.out.println((file.length() / loop) + " bytes per item in storage.");
    }

    /* (non-Javadoc)
     * @see org.talend.designer.components.thash.io.MapHashFile#getTotalSize()
     */
    public long getTotalSize() {
        // TODO Auto-generated method stub
        return 0;
    }

    
}

