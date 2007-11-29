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
import java.util.Date;
import java.util.List;

/**
 * 
 * DOC slanglois class global comment. Detailled comment <br/>
 *
 * 
 * 
71922 milliseconds for 1000000 objects to STORE using BigBeanIntrospectionHashFile. 13000 items/s. 67 bytes per item in storage.
82172 milliseconds for 1000000 objects to STORE using IntrospectionHashFile. 12000 items/s. 68 bytes per item in storage.
30438 milliseconds for 1000000 objects to STORE using SimpleHashFile. 32000 items/s. 321 bytes per item in storage.
44406 milliseconds for 1000000 objects to STORE using DoubleHashFile. 22000 items/s. 
 * 
 */
class BigBeanIntrospectionHashFile implements IMapHashFile {

    private static BigBeanIntrospectionHashFile instance;

    private BigBeanIntrospectionHashFile() {
    }

    /**
     * getInstance.
     * 
     * @return the instance if this project handler
     */
    public static synchronized BigBeanIntrospectionHashFile getInstance() {
        if (instance == null) {
            instance = new BigBeanIntrospectionHashFile();
        }
        return instance;
    }

    RandomAccessFile bw = null;

    boolean readonly;

    RandomAccessFile ra = null;

    InternalBigBean lastRetrievedObject;

    long lastRetrievedCursorPosition = -1;

    public Object get(String container, long cursorPosition, int hashcode) throws IOException {
        if (cursorPosition != lastRetrievedCursorPosition) {
            lastRetrievedObject = new InternalBigBean();
            ra.seek(cursorPosition);
            lastRetrievedObject.primitiveInt = ra.readInt();
            byte[] bytes = new byte[ra.readInt()];
            ra.read(bytes);
            lastRetrievedObject.name = new String(bytes);
            bytes = new byte[ra.readInt()];
            ra.read(bytes);
            lastRetrievedObject.address = new String(bytes);
            lastRetrievedObject.price = ra.readFloat();
            long date = ra.readLong();
            if(date == 0){
                lastRetrievedObject.date = null;
            }else{
                lastRetrievedObject.date = new Date(date);
            }
            date = ra.readLong();
            if(date == 0){
                lastRetrievedObject.date2 = null;
            }else{
                lastRetrievedObject.date2 = new Date(date);
            }
            bytes = new byte[ra.readInt()];
            ra.read(bytes);
            lastRetrievedObject.b = bytes;
            lastRetrievedObject.flag = ra.readBoolean();
            
            lastRetrievedCursorPosition = cursorPosition;
        }
        return lastRetrievedObject;

    }

    /**
     * 
     */
    public long put(String container, Object bigBean) throws IOException {
        long returnPosition = bw.getFilePointer();
        if (!readonly) {
            lastRetrievedObject = (InternalBigBean)bigBean;
            bw.writeInt(lastRetrievedObject.primitiveInt);
            byte[] bytes = null;
            if(lastRetrievedObject.name == null){
                bytes = new byte[0];
            }else{
                bytes = lastRetrievedObject.name.getBytes();
            }
            bw.writeInt(bytes.length);
            bw.write(bytes);
            if(lastRetrievedObject.address == null){
                bytes = new byte[0];
            }else{
                bytes = lastRetrievedObject.address.getBytes();
            }
            bw.writeInt(bytes.length);
            bw.write(bytes);
            bw.writeFloat(lastRetrievedObject.price);
            if(lastRetrievedObject.date == null){
                bw.writeLong((long)0);
            }else{
                bw.writeLong(lastRetrievedObject.date.getTime());
            }
            if(lastRetrievedObject.date2 == null){
                bw.writeLong((long)0);
            }else{
                bw.writeLong(lastRetrievedObject.date2.getTime());
            }
            if(lastRetrievedObject.b == null){
                bytes = new byte[0];
            }else{
                bytes = lastRetrievedObject.b;
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
        BigBeanIntrospectionHashFile nihf = BigBeanIntrospectionHashFile.getInstance();
        List<Long> cursors = new ArrayList<Long>();
        long start = System.currentTimeMillis();

        String folder = "/tmp/";
        
        nihf.initPut(folder + "cache0");
        for (int i = 1; i < loop; i++) {
            InternalBigBean bean = new InternalBigBean(i, String.valueOf(i));
            nihf.put("", bean);
        }
        nihf.endPut();

        long end = System.currentTimeMillis();
        long deltaTime = (end - start);
        System.out.print(deltaTime + " milliseconds for " + loop + " objects to STORE using BigBeanIntrospectionHashFile. "
                + (loop / deltaTime * 1000) + " items/s. ");
        File file = new File(folder + "cache0");
        System.out.println((file.length() / loop) + " bytes per item in storage.");

        
        IntrospectionHashFile ihf = IntrospectionHashFile.getInstance();
        start = System.currentTimeMillis();

        ihf.init(InternalBigBean.class);
        ihf.initPut(folder + "cache1");
        for (int i = 1; i < loop; i++) {
            InternalBigBean bean = new InternalBigBean(i, String.valueOf(i));
            ihf.put("", bean);
        }
        ihf.endPut();

        end = System.currentTimeMillis();
        deltaTime = (end - start);
        System.out.print(deltaTime + " milliseconds for " + loop + " objects to STORE using IntrospectionHashFile. "
                + (loop / deltaTime * 1000) + " items/s. ");
        file = new File(folder + "cache1");
        System.out.println((file.length() / loop) + " bytes per item in storage.");
        
        
         SimpleHashFile shf = SimpleHashFile.getInstance();
         start = System.currentTimeMillis();
        
         shf.initPut(folder + "cache2");
         for (int i = 0; i < loop; i++) {
             InternalBigBean bean = new InternalBigBean(i, String.valueOf(i));
         shf.put("", bean);
         }
         ihf.endPut();
        
         end = System.currentTimeMillis();
         deltaTime = (end - start);
         System.out.print(deltaTime + " milliseconds for " + loop + " objects to STORE using SimpleHashFile. "
         + (loop / deltaTime * 1000) + " items/s. ");
         file = new File(folder + "cache2");
         System.out.println((file.length() / loop) + " bytes per item in storage.");
        
         DoubleHashFile dhf = DoubleHashFile.getInstance();
         start = System.currentTimeMillis();
        
         dhf.initPut(folder + "cache3");
         for (int i = 0; i < loop; i++) {
             InternalBigBean bean = new InternalBigBean(i, String.valueOf(i));
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
