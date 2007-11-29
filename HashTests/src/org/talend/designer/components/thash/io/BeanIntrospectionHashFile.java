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

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.List;

/**
 * 
 * DOC slanglois class global comment. Detailled comment <br/>
 * 
 * 
18000 milliseconds for 1000000 objects to STORE using BeanIntrospectionHashFile. 55000 items/s. 13 bytes per item in storage.
20406 milliseconds for 1000000 objects to STORE using IntrospectionHashFile. 49000 items/s. 13 bytes per item in storage.
19485 milliseconds for 1000000 objects to STORE using SimpleHashFile. 51000 items/s. 124 bytes per item in storage.
30609 milliseconds for 1000000 objects to STORE using DoubleHashFile. 32000 items/s. 
 * 
 */
class BeanIntrospectionHashFile  implements IMapHashFile {

    private static BeanIntrospectionHashFile instance;

    private BeanIntrospectionHashFile() {
    }

    /**
     * getInstance.
     * 
     * @return the instance if this project handler
     */
    public static synchronized BeanIntrospectionHashFile getInstance() {
        if (instance == null) {
            instance = new BeanIntrospectionHashFile();
        }
        return instance;
    }

    RandomAccessFile bw = null;

    boolean readonly;

    RandomAccessFile ra = null;

    InternalSmallBean lastRetrievedObject;

    long lastRetrievedCursorPosition = -1;

    public Object get(String container, long cursorPosition, int hashcode) throws IOException {
        if (cursorPosition != lastRetrievedCursorPosition) {
            lastRetrievedObject = new InternalSmallBean();
            ra.seek(cursorPosition);
            lastRetrievedObject.primitiveInt = ra.readInt();
            byte[] bytes = new byte[ra.readInt()];
            ra.read(bytes);
            lastRetrievedObject.name = new String(bytes);
            lastRetrievedCursorPosition = cursorPosition;
        }
        return lastRetrievedObject;

    }

    public long put(String container, Object bean) throws IOException {
        long returnPosition = bw.getFilePointer();
        if (!readonly) {
            lastRetrievedObject = (InternalSmallBean)bean;
            bw.writeInt(lastRetrievedObject.primitiveInt);
            byte[] bytes = null;
            if(lastRetrievedObject.name == null){
                bytes = new byte[0];
            }else{
                bytes = lastRetrievedObject.name.getBytes();
            }
            bw.writeInt(bytes.length);
            bw.write(bytes);
        }
        return returnPosition;
    }

    public void initPut(String container) throws IOException {
        if (!readonly) {
            File file = new File(container);
            file.delete();
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

    public static void main(String[] args) throws IOException, IllegalAccessException, InvocationTargetException, NoSuchMethodException {
        int loop = 1000000;
        BeanIntrospectionHashFile nihf = BeanIntrospectionHashFile.getInstance();
        List<Long> cursors = new ArrayList<Long>();
        long start = System.currentTimeMillis();

        nihf.initPut("D:/cache0");
        for (int i = 1; i < loop; i++) {
            InternalSmallBean bean = new InternalSmallBean(i, String.valueOf(i));
            nihf.put("", bean);
        }
        nihf.endPut();

        long end = System.currentTimeMillis();
        long deltaTime = (end - start);
        System.out.print(deltaTime + " milliseconds for " + loop + " objects to STORE using BeanIntrospectionHashFile. "
                + (loop / deltaTime * 1000) + " items/s. ");
        File file = new File("D:/cache0");
        System.out.println((file.length() / loop) + " bytes per item in storage.");

        
        IntrospectionHashFile ihf = IntrospectionHashFile.getInstance();
        start = System.currentTimeMillis();

        ihf.init(InternalSmallBean.class);
        ihf.initPut("D:/cache1");
        for (int i = 1; i < loop; i++) {
            InternalSmallBean bean = new InternalSmallBean(i, String.valueOf(i));
            ihf.put("", bean);
        }
        ihf.endPut();

        end = System.currentTimeMillis();
        deltaTime = (end - start);
        System.out.print(deltaTime + " milliseconds for " + loop + " objects to STORE using IntrospectionHashFile. "
                + (loop / deltaTime * 1000) + " items/s. ");
        file = new File("D:/cache1");
        System.out.println((file.length() / loop) + " bytes per item in storage.");
        
        
         SimpleHashFile shf = SimpleHashFile.getInstance();
         start = System.currentTimeMillis();
        
         shf.initPut("D:/cache2");
         for (int i = 0; i < loop; i++) {
         InternalSmallBean bean = new InternalSmallBean(i, String.valueOf(i));
         shf.put("", bean);
         }
         ihf.endPut();
        
         end = System.currentTimeMillis();
         deltaTime = (end - start);
         System.out.print(deltaTime + " milliseconds for " + loop + " objects to STORE using SimpleHashFile. "
         + (loop / deltaTime * 1000) + " items/s. ");
         file = new File("D:/cache2");
         System.out.println((file.length() / loop) + " bytes per item in storage.");
        
         DoubleHashFile dhf = DoubleHashFile.getInstance();
         start = System.currentTimeMillis();
        
         dhf.initPut("D:/cache3");
         for (int i = 0; i < loop; i++) {
         InternalSmallBean bean = new InternalSmallBean(i, String.valueOf(i));
         dhf.put("", bean);
         }
         dhf.endPut();
        
         end = System.currentTimeMillis();
         deltaTime = (end - start);
         System.out.print(deltaTime + " milliseconds for " + loop + " objects to STORE using DoubleHashFile. "
         + (loop / deltaTime * 1000) + " items/s. ");
    }

    /* (non-Javadoc)
     * @see org.talend.designer.components.thash.io.MapHashFile#getTotalSize()
     */
    public long getTotalSize() {
        // TODO Auto-generated method stub
        return 0;
    }

}
