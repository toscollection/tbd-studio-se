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
package org.talend.designer.components.thash.io.beans;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.Serializable;

import org.talend.designer.components.thash.io.HashFilesBenchs;

/**
 * 
 * DOC amaumont class global comment. Detailled comment <br/>
 * 
 */
public class Bean implements Serializable, ILightSerializable {

    public int primitiveInt;

    public String name;

    transient int hashcode = -1;

    public static int countReturnFalse1 = 0;

    public static int getDataCountRequested = 0;

    /**
     * DOC amaumont Bean constructor comment.
     * 
     * @param primitiveInt
     * @param name
     */
    public Bean(int primitiveInt, String name) {
        super();
        this.primitiveInt = primitiveInt;
        this.name = name;
    }

    public Bean() {
        super();
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

        if (this.hashCode() != obj.hashCode()) {
            countReturnFalse1++;
            return false;
        }
        final KeyForMap other = (KeyForMap) obj;

        Object o = null;
        try {
            getDataCountRequested++;
            o = HashFilesBenchs.hashFile.get("buffer", (long) other.cursorPosition, hashcode);
        } catch (Exception e) {
            e.printStackTrace();
        }
        if (o == null) {
            return false;
        }
        Bean bean = (Bean) o;

        if (this.name == null) {
            if ((String) bean.name != null) {
                // System.out.println("return false 2");
                return false;
            }
        } else if (!this.name.equals((String) bean.name)) {
            // System.out.println("return false 3");
            return false;
        }
        if (this.primitiveInt != bean.primitiveInt) {
            // System.out.println("return false 4");
            return false;
        }
        return true;
    }

    @Override
    public ILightSerializable createInstance(byte[] bytes) {
        Bean result = new Bean();
        ByteArrayInputStream bai = null;
        DataInputStream dis = null;
        try {
            bai = new ByteArrayInputStream(bytes);
            dis = new DataInputStream(bai);
            result.primitiveInt = dis.readInt();
            byte[] byteArray = null;
            int length = dis.readInt();
            if (length == -1) {
                result.name = null;
            } else {
                byteArray = new byte[length];
                dis.read(byteArray);
                result.name = new String(byteArray);
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (dis != null) {
                try {
                    dis.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        return result;
    }

    @Override
    public byte[] toByteArray() {
        ByteArrayOutputStream bao = null;
        DataOutputStream dos = null;
        byte[] bytes = null;
        try {
            bao = new ByteArrayOutputStream();
            dos = new DataOutputStream(bao);
            dos.writeInt(this.primitiveInt);
            if (this.name == null) {
                dos.writeInt(-1);
            } else {
                bytes = this.name.getBytes();
                dos.writeInt(bytes.length);
                dos.write(bytes);
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (dos != null) {
                try {
                    dos.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        bytes = bao.toByteArray();
        return bytes;
    }

    @Override
    public int compareTo(Object o) {
        Bean bean = (Bean) o;
        if (this.primitiveInt != bean.primitiveInt) {
            return this.primitiveInt - bean.primitiveInt;
        }
        if (this.name == null) {
            if (bean.name == null) {
                return 0;
            } else {
                return -1;
            }
        } else {
            if (bean.name == null) {
                return 1;
            } else {
                return this.name.compareTo(bean.name);
            }
        }
    }
    
    public String toString(){
        return primitiveInt+"   "+name;
    }
}
