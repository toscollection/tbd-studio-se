package org.talend.designer.components.thash.io.hashimpl;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.Serializable;
import java.util.Date;

import org.talend.designer.components.thash.io.HashFilesBenchs;
import org.talend.designer.components.thash.io.beans.KeyForMap;

/**
 * 
 * DOC amaumont  class global comment. Detailled comment
 */
public class InternalSmallBean implements Serializable {

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
