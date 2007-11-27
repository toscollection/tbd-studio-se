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

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.RandomAccessFile;
import java.io.Serializable;
import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

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
    @Override
    public long getTotalSize() {
        // TODO Auto-generated method stub
        return 0;
    }

}

/**
 * 
 * DOC amaumont  class global comment. Detailled comment
 */
class InternalSmallBean implements Serializable {

    int primitiveInt;

    String name;

    transient int hashcode = -1;

    /**
     * DOC amaumont Bean constructor comment.
     * 
     * @param primitiveInt
     * @param name
     */
    public InternalSmallBean(int primitiveInt, String name) {
        super();
        this.primitiveInt = primitiveInt;
        this.name = name;
    }

    public InternalSmallBean() {
        super();
    }

    public InternalSmallBean(byte[] bytes) throws IOException {
        super();
        ByteArrayInputStream bai = new ByteArrayInputStream(bytes);
        DataInputStream dis = new DataInputStream(bai);
        this.primitiveInt = dis.readInt();
        byte[] byteArray = null;
        int length = dis.readInt();
        if (length == -1) {
            this.name = null;
        } else {
            byteArray = new byte[length];
            dis.read(byteArray);
            this.name = new String(byteArray);
        }
    }

    /*
     * (non-Javadoc)
     * 
     * @see java.lang.Object#hashCode()
     */
    @Override
    public int hashCode() {
        if (hashcode == -1) {
            final int prime = 31;
            int result = 1;
            result = prime * result + ((this.name == null) ? 0 : this.name.hashCode());
            result = prime * result + this.primitiveInt;
            hashcode = result;
        }
        return hashcode;
    }

    /*
     * (non-Javadoc)
     * 
     * @see java.lang.Object#equals(java.lang.Object)
     */
    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null)
            return false;
        final KeyForMap other = (KeyForMap) obj;

        if (this.hashCode() != other.hashcode) {
            return false;
        }

        Object o = null;
        try {
            o = HashFilesBenchs.hashFile.get("", (long)other.cursorPosition, -1);
        } catch (Exception e) {
            e.printStackTrace();
        }
        if (o == null) {
            return false;
        }
        InternalSmallBean bean = (InternalSmallBean) o;

        if (this.name == null) {
            if ((String) bean.name != null)
                return false;
        } else if (!this.name.equals((String) bean.name))
            return false;
        if (this.primitiveInt != bean.primitiveInt)
            return false;
        return true;
    }

    public int getPrimitiveInt() {
        return primitiveInt;
    }

    public void setPrimitiveInt(int primitiveInt) {
        this.primitiveInt = primitiveInt;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public byte[] toByteArray() throws IOException {
        ByteArrayOutputStream bao = new ByteArrayOutputStream();
        DataOutputStream dos = new DataOutputStream(bao);
        dos.writeInt(this.primitiveInt);
        byte[] bytes = null;
        if (this.name == null) {
            dos.writeInt(-1);
        } else {
            bytes = this.name.getBytes();
            dos.writeInt(bytes.length);
            dos.write(bytes);
        }
        dos.close();
        bytes = bao.toByteArray();
        bao.close();
        return bytes;
    }

}

/**
 * 
 * DOC amaumont  class global comment. Detailled comment
 */
class InternalBigBean implements Serializable {

    int primitiveInt;

    String name;

    String address;

    float price;

    Date date = null;

    Date date2 = null;

    byte[] b = null;

    boolean flag;

    transient int hashcode = -1;

    /**
     * DOC amaumont Bean constructor comment.
     * 
     * @param primitiveInt
     * @param name
     */
    public InternalBigBean(int primitiveInt, String name) {
        super();
        this.primitiveInt = primitiveInt;
        this.name = name;
        this.address = "Address" + name;
        this.b = address.getBytes();
        this.date = new Date();
        this.date2 = new Date();
        this.price = (float) primitiveInt;
        this.flag = true;
    }

    public InternalBigBean() {
        super();
    }

    public InternalBigBean(byte[] byteArray) throws IOException, ClassNotFoundException {
        super();
        ByteArrayInputStream bai = new ByteArrayInputStream(byteArray);
        DataInputStream dis = new DataInputStream(bai);
        this.primitiveInt = dis.readInt();
        byte[] bytes = null;
        int length = dis.readInt();
        if (length == -1) {
            this.name = null;
        } else {
            bytes = new byte[length];
            dis.read(bytes);
            this.name = new String(bytes);
        }

        length = dis.readInt();
        if (length == -1) {
            this.address = null;
        } else {
            bytes = new byte[length];
            dis.read(bytes);
            this.address = new String(bytes);
        }

        this.price = dis.readFloat();

        long time = dis.readLong();
        if (time == -1) {
            this.date = null;
        } else {
            this.date = new Date(time);
        }

        time = dis.readLong();
        if (time == -1) {
            this.date2 = null;
        } else {
            this.date2 = new Date(time);
        }

        length = dis.readInt();
        if (length == -1) {
            this.b = null;
        } else {
            bytes = new byte[length];
            dis.read(bytes);
            this.b = bytes;
        }
        this.flag = dis.readBoolean();

        dis.close();
    }

    /*
     * (non-Javadoc)
     * 
     * @see java.lang.Object#hashCode()
     */
    @Override
    public int hashCode() {
        if (hashcode == -1) {
            final int prime = 31;
            int result = 1;
            result = prime * result + ((this.name == null) ? 0 : this.name.hashCode());
            result = prime * result + this.primitiveInt;
            hashcode = result;
        }
        return hashcode;
    }

    /*
     * (non-Javadoc)
     * 
     * @see java.lang.Object#equals(java.lang.Object)
     */
    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null)
            return false;
        final KeyForMap other = (KeyForMap) obj;

        if (this.hashCode() != other.hashcode) {
            return false;
        }

        Object o = null;
        try {
            o = HashFilesBenchs.hashFile.get("", (long) other.cursorPosition, -1);
        } catch (Exception e) {
            e.printStackTrace();
        }
        if (o == null) {
            return false;
        }
        InternalBigBean bean = (InternalBigBean) o;

        if (this.name == null) {
            if ((String) bean.name != null)
                return false;
        } else if (!this.name.equals((String) bean.name))
            return false;
        if (this.primitiveInt != bean.primitiveInt)
            return false;
        return true;
    }

    public int getPrimitiveInt() {
        return primitiveInt;
    }

    public void setPrimitiveInt(int primitiveInt) {
        this.primitiveInt = primitiveInt;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public byte[] getB() {
        return b;
    }

    public void setB(byte[] b) {
        this.b = b;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public float getPrice() {
        return price;
    }

    public void setPrice(float price) {
        this.price = price;
    }

    public Date getDate2() {
        return date2;
    }

    public void setDate2(Date date2) {
        this.date2 = date2;
    }

    public boolean isFlag() {
        return flag;
    }

    public void setFlag(boolean flag) {
        this.flag = flag;
    }

    public byte[] toByteArray() throws IOException {
        ByteArrayOutputStream bao = new ByteArrayOutputStream();
        DataOutputStream dos = new DataOutputStream(bao);
        dos.writeInt(this.primitiveInt);

        byte[] bytes = null;

        if (this.name == null) {
            dos.writeInt(-1);
        } else {
            bytes = this.name.getBytes();
            dos.writeInt(bytes.length);
            dos.write(bytes);
        }

        if (this.address == null) {
            dos.writeInt(-1);
        } else {
            bytes = this.address.getBytes();
            dos.writeInt(bytes.length);
            dos.write(bytes);
        }

        dos.writeFloat(this.price);

        if (this.date == null) {
            dos.writeLong(-1);
        } else {
            dos.writeLong(this.date.getTime());
        }

        if (this.date2 == null) {
            dos.writeLong(-1);
        } else {
            dos.writeLong(this.date2.getTime());
        }

        if (this.b == null) {
            dos.writeInt(-1);
        } else {
            dos.writeInt(this.b.length);
            dos.write(this.b);
        }
        dos.writeBoolean(this.flag);

        dos.close();
        bytes = bao.toByteArray();
        bao.close();
        return bytes;
    }

}
