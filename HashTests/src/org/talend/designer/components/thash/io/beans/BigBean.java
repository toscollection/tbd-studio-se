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

import java.io.Serializable;
import java.util.Date;

import org.talend.designer.components.thash.io.HashFilesBenchs;

/**
 * 
 * DOC slanglois class global comment. Detailled comment <br/>
 * 
 */
public class BigBean implements Serializable {

    int primitiveInt;

    String name;
    
    String address;
    
    float price;

    Date date = null;
    
    Date date2 = null;
    
    byte[] b= null;
    
    boolean flag;
    
    transient int hashcode = -1;

    /**
     * DOC amaumont Bean constructor comment.
     * 
     * @param primitiveInt
     * @param name
     */
    public BigBean(int primitiveInt, String name) {
        super();
        this.primitiveInt = primitiveInt;
        this.name = name;
        this.address = "Address" + name;
        this.b = address.getBytes();
        this.date = new Date();
        this.date2 = new Date();
        this.price = (float)primitiveInt;
        this.flag = true;
    }
    
    public BigBean(){
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
        BigBean bean = (BigBean) o;

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

}
