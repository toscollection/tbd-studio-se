/**
 * Revision.java
 * 
 * This file was auto-generated from WSDL by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */

package org.talend.designer.components.ecosystem.ws;

public class Revision implements java.io.Serializable {

    private int revision_id;

    private java.lang.String extension_name;

    private java.lang.String extension_author;

    private java.lang.String extension_description;

    private java.lang.String revision_name;

    private java.lang.String revision_date;

    private java.lang.String file_url;

    private java.lang.String download_url;

    private java.lang.String filename;

    private java.lang.String revision_description;

    public Revision() {
    }

    public Revision(int revision_id, java.lang.String extension_name, java.lang.String extension_author,
            java.lang.String extension_description, java.lang.String revision_name, java.lang.String revision_date,
            java.lang.String file_url, java.lang.String download_url, java.lang.String filename,
            java.lang.String revision_description) {
        this.revision_id = revision_id;
        this.extension_name = extension_name;
        this.extension_author = extension_author;
        this.extension_description = extension_description;
        this.revision_name = revision_name;
        this.revision_date = revision_date;
        this.file_url = file_url;
        this.download_url = download_url;
        this.filename = filename;
        this.revision_description = revision_description;
    }

    /**
     * Gets the revision_id value for this Revision.
     * 
     * @return revision_id
     */
    public int getRevision_id() {
        return revision_id;
    }

    /**
     * Sets the revision_id value for this Revision.
     * 
     * @param revision_id
     */
    public void setRevision_id(int revision_id) {
        this.revision_id = revision_id;
    }

    /**
     * Gets the extension_name value for this Revision.
     * 
     * @return extension_name
     */
    public java.lang.String getExtension_name() {
        return extension_name;
    }

    /**
     * Sets the extension_name value for this Revision.
     * 
     * @param extension_name
     */
    public void setExtension_name(java.lang.String extension_name) {
        this.extension_name = extension_name;
    }

    /**
     * Gets the extension_author value for this Revision.
     * 
     * @return extension_author
     */
    public java.lang.String getExtension_author() {
        return extension_author;
    }

    /**
     * Sets the extension_author value for this Revision.
     * 
     * @param extension_author
     */
    public void setExtension_author(java.lang.String extension_author) {
        this.extension_author = extension_author;
    }

    /**
     * Gets the extension_description value for this Revision.
     * 
     * @return extension_description
     */
    public java.lang.String getExtension_description() {
        return extension_description;
    }

    /**
     * Sets the extension_description value for this Revision.
     * 
     * @param extension_description
     */
    public void setExtension_description(java.lang.String extension_description) {
        this.extension_description = extension_description;
    }

    /**
     * Gets the revision_name value for this Revision.
     * 
     * @return revision_name
     */
    public java.lang.String getRevision_name() {
        return revision_name;
    }

    /**
     * Sets the revision_name value for this Revision.
     * 
     * @param revision_name
     */
    public void setRevision_name(java.lang.String revision_name) {
        this.revision_name = revision_name;
    }

    /**
     * Gets the revision_date value for this Revision.
     * 
     * @return revision_date
     */
    public java.lang.String getRevision_date() {
        return revision_date;
    }

    /**
     * Sets the revision_date value for this Revision.
     * 
     * @param revision_date
     */
    public void setRevision_date(java.lang.String revision_date) {
        this.revision_date = revision_date;
    }

    /**
     * Gets the file_url value for this Revision.
     * 
     * @return file_url
     */
    public java.lang.String getFile_url() {
        return file_url;
    }

    /**
     * Sets the file_url value for this Revision.
     * 
     * @param file_url
     */
    public void setFile_url(java.lang.String file_url) {
        this.file_url = file_url;
    }

    /**
     * Gets the download_url value for this Revision.
     * 
     * @return download_url
     */
    public java.lang.String getDownload_url() {
        return download_url;
    }

    /**
     * Sets the download_url value for this Revision.
     * 
     * @param download_url
     */
    public void setDownload_url(java.lang.String download_url) {
        this.download_url = download_url;
    }

    /**
     * Gets the filename value for this Revision.
     * 
     * @return filename
     */
    public java.lang.String getFilename() {
        return filename;
    }

    /**
     * Sets the filename value for this Revision.
     * 
     * @param filename
     */
    public void setFilename(java.lang.String filename) {
        this.filename = filename;
    }

    /**
     * Gets the revision_description value for this Revision.
     * 
     * @return revision_description
     */
    public java.lang.String getRevision_description() {
        return revision_description;
    }

    /**
     * Sets the revision_description value for this Revision.
     * 
     * @param revision_description
     */
    public void setRevision_description(java.lang.String revision_description) {
        this.revision_description = revision_description;
    }

    private java.lang.Object __equalsCalc = null;

    @Override
    public synchronized boolean equals(java.lang.Object obj) {
        if (!(obj instanceof Revision)) {
            return false;
        }
        Revision other = (Revision) obj;
        if (obj == null) {
            return false;
        }
        if (this == obj) {
            return true;
        }
        if (__equalsCalc != null) {
            return (__equalsCalc == obj);
        }
        __equalsCalc = obj;
        boolean _equals;
        _equals = true
                && this.revision_id == other.getRevision_id()
                && ((this.extension_name == null && other.getExtension_name() == null) || (this.extension_name != null && this.extension_name
                        .equals(other.getExtension_name())))
                && ((this.extension_author == null && other.getExtension_author() == null) || (this.extension_author != null && this.extension_author
                        .equals(other.getExtension_author())))
                && ((this.extension_description == null && other.getExtension_description() == null) || (this.extension_description != null && this.extension_description
                        .equals(other.getExtension_description())))
                && ((this.revision_name == null && other.getRevision_name() == null) || (this.revision_name != null && this.revision_name
                        .equals(other.getRevision_name())))
                && ((this.revision_date == null && other.getRevision_date() == null) || (this.revision_date != null && this.revision_date
                        .equals(other.getRevision_date())))
                && ((this.file_url == null && other.getFile_url() == null) || (this.file_url != null && this.file_url
                        .equals(other.getFile_url())))
                && ((this.download_url == null && other.getDownload_url() == null) || (this.download_url != null && this.download_url
                        .equals(other.getDownload_url())))
                && ((this.filename == null && other.getFilename() == null) || (this.filename != null && this.filename
                        .equals(other.getFilename())))
                && ((this.revision_description == null && other.getRevision_description() == null) || (this.revision_description != null && this.revision_description
                        .equals(other.getRevision_description())));
        __equalsCalc = null;
        return _equals;
    }

    private boolean __hashCodeCalc = false;

    @Override
    public synchronized int hashCode() {
        if (__hashCodeCalc) {
            return 0;
        }
        __hashCodeCalc = true;
        int _hashCode = 1;
        _hashCode += getRevision_id();
        if (getExtension_name() != null) {
            _hashCode += getExtension_name().hashCode();
        }
        if (getExtension_author() != null) {
            _hashCode += getExtension_author().hashCode();
        }
        if (getExtension_description() != null) {
            _hashCode += getExtension_description().hashCode();
        }
        if (getRevision_name() != null) {
            _hashCode += getRevision_name().hashCode();
        }
        if (getRevision_date() != null) {
            _hashCode += getRevision_date().hashCode();
        }
        if (getFile_url() != null) {
            _hashCode += getFile_url().hashCode();
        }
        if (getDownload_url() != null) {
            _hashCode += getDownload_url().hashCode();
        }
        if (getFilename() != null) {
            _hashCode += getFilename().hashCode();
        }
        if (getRevision_description() != null) {
            _hashCode += getRevision_description().hashCode();
        }
        __hashCodeCalc = false;
        return _hashCode;
    }

    // Type metadata
    private static org.apache.axis.description.TypeDesc typeDesc = new org.apache.axis.description.TypeDesc(Revision.class, true);

    static {
        typeDesc.setXmlType(new javax.xml.namespace.QName("http://talendforge.org/ext/wsdl", "revision"));
        org.apache.axis.description.ElementDesc elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("revision_id");
        elemField.setXmlName(new javax.xml.namespace.QName("", "revision_id"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "int"));
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("extension_name");
        elemField.setXmlName(new javax.xml.namespace.QName("", "extension_name"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("extension_author");
        elemField.setXmlName(new javax.xml.namespace.QName("", "extension_author"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("extension_description");
        elemField.setXmlName(new javax.xml.namespace.QName("", "extension_description"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("revision_name");
        elemField.setXmlName(new javax.xml.namespace.QName("", "revision_name"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("revision_date");
        elemField.setXmlName(new javax.xml.namespace.QName("", "revision_date"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("file_url");
        elemField.setXmlName(new javax.xml.namespace.QName("", "file_url"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("download_url");
        elemField.setXmlName(new javax.xml.namespace.QName("", "download_url"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("filename");
        elemField.setXmlName(new javax.xml.namespace.QName("", "filename"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("revision_description");
        elemField.setXmlName(new javax.xml.namespace.QName("", "revision_description"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
    }

    /**
     * Return type metadata object
     */
    public static org.apache.axis.description.TypeDesc getTypeDesc() {
        return typeDesc;
    }

    /**
     * Get Custom Serializer
     */
    public static org.apache.axis.encoding.Serializer getSerializer(java.lang.String mechType, java.lang.Class _javaType,
            javax.xml.namespace.QName _xmlType) {
        return new org.apache.axis.encoding.ser.BeanSerializer(_javaType, _xmlType, typeDesc);
    }

    /**
     * Get Custom Deserializer
     */
    public static org.apache.axis.encoding.Deserializer getDeserializer(java.lang.String mechType, java.lang.Class _javaType,
            javax.xml.namespace.QName _xmlType) {
        return new org.apache.axis.encoding.ser.BeanDeserializer(_javaType, _xmlType, typeDesc);
    }

}
