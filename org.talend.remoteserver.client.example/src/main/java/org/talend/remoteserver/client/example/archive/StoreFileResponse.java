// ============================================================================
//
// Talend Community Edition
//
// Copyright (C) 2006 Talend - www.talend.com
//
// This library is free software; you can redistribute it and/or
// modify it under the terms of the GNU Lesser General Public
// License as published by the Free Software Foundation; either
// version 2.1 of the License, or (at your option) any later version.
//
// This library is distributed in the hope that it will be useful,
// but WITHOUT ANY WARRANTY; without even the implied warranty of
// MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU
// Lesser General Public License for more details.
//
// You should have received a copy of the GNU General Public License
// along with this program; if not, write to the Free Software
// Foundation, Inc., 675 Mass Ave, Cambridge, MA 02139, USA.
//
// ============================================================================

package org.talend.remoteserver.client.example.archive;

import java.io.Serializable;

import javax.xml.namespace.QName;

import org.apache.axis.description.ElementDesc;
import org.apache.axis.description.TypeDesc;
import org.apache.axis.encoding.Deserializer;
import org.apache.axis.encoding.Serializer;
import org.apache.axis.encoding.ser.BeanDeserializer;
import org.apache.axis.encoding.ser.BeanSerializer;

/**
 * DOC mhirt class global comment. Detailled comment <br/>
 * 
 * $Id$
 * 
 */
public class StoreFileResponse implements Serializable {

    /**
     * 
     */
    private static final long serialVersionUID = 3409771995096856215L;

    private String storeFileReturn;

    public StoreFileResponse() {
    }

    public StoreFileResponse(String storeFileReturn) {
        this.storeFileReturn = storeFileReturn;
    }

    /**
     * Gets the storeFileReturn value for this StoreFileResponse.
     * 
     * @return storeFileReturn
     */
    public String getStoreFileReturn() {
        return storeFileReturn;
    }

    /**
     * Sets the storeFileReturn value for this StoreFileResponse.
     * 
     * @param storeFileReturn
     */
    public void setStoreFileReturn(String storeFileReturn) {
        this.storeFileReturn = storeFileReturn;
    }

    private Object equalsCalc = null;

    public synchronized boolean equals(Object obj) {
        if (!(obj instanceof StoreFileResponse)) {
            return false;
        }
        StoreFileResponse other = (StoreFileResponse) obj;
        if (obj == null) {
            return false;
        }
        if (this == obj) {
            return true;
        }
        if (equalsCalc != null) {
            return (equalsCalc == obj);
        }
        equalsCalc = obj;
        boolean equals;
        equals = true && ((this.storeFileReturn == null && other.getStoreFileReturn() == null) || 
                (this.storeFileReturn != null && this.storeFileReturn.equals(other.getStoreFileReturn())));
        equalsCalc = null;
        return equals;
    }

    private boolean hashCodeCalc = false;

    public synchronized int hashCode() {
        if (hashCodeCalc) {
            return 0;
        }
        hashCodeCalc = true;
        int hashCode = 1;
        if (getStoreFileReturn() != null) {
            hashCode += getStoreFileReturn().hashCode();
        }
        hashCodeCalc = false;
        return hashCode;
    }

    // Type metadata
    private static TypeDesc typeDesc = new TypeDesc(
            StoreFileResponse.class, true);

    static {
        typeDesc.setXmlType(new QName("http://business.archive.remoterver.talend.org",
                ">StoreFileResponse"));
        ElementDesc elemField = new ElementDesc();
        elemField.setFieldName("storeFileReturn");
        elemField.setXmlName(new QName("http://business.archive.remoterver.talend.org",
                "StoreFileReturn"));
        elemField.setXmlType(new QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
    }

    /**
     * Return type metadata object.
     */
    public static TypeDesc getTypeDesc() {
        return typeDesc;
    }

    /**
     * Get Custom Serializer.
     */
    public static Serializer getSerializer(String mechType, Class javaType, QName xmlType) {
        return new BeanSerializer(javaType, xmlType, typeDesc);
    }

    /**
     * Get Custom Deserializer.
     */
    public static Deserializer getDeserializer(String mechType, Class javaType, QName xmlType) {
        return new BeanDeserializer(javaType, xmlType, typeDesc);
    }

}
