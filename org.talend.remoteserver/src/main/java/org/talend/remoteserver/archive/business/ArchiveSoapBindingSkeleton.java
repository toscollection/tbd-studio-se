/**
 * ArchiveSoapBindingSkeleton.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.3 Oct 05, 2005 (05:23:37 EDT) WSDL2Java emitter.
 */

package org.talend.remoteserver.archive.business;

public class ArchiveSoapBindingSkeleton implements org.talend.remoteserver.archive.business.Archive, org.apache.axis.wsdl.Skeleton {
    private org.talend.remoteserver.archive.business.Archive impl;
    private static java.util.Map _myOperations = new java.util.Hashtable();
    private static java.util.Collection _myOperationsList = new java.util.ArrayList();

    /**
    * Returns List of OperationDesc objects with this name
    */
    public static java.util.List getOperationDescByName(java.lang.String methodName) {
        return (java.util.List)_myOperations.get(methodName);
    }

    /**
    * Returns Collection of OperationDescs
    */
    public static java.util.Collection getOperationDescs() {
        return _myOperationsList;
    }

    static {
        org.apache.axis.description.OperationDesc _oper;
        org.apache.axis.description.FaultDesc _fault;
        org.apache.axis.description.ParameterDesc [] _params;
        _params = new org.apache.axis.description.ParameterDesc [] {
            new org.apache.axis.description.ParameterDesc(new javax.xml.namespace.QName("http://business.archive.remoteserver.talend.org", "filename"), org.apache.axis.description.ParameterDesc.IN, new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"), java.lang.String.class, false, false), 
            new org.apache.axis.description.ParameterDesc(new javax.xml.namespace.QName("http://business.archive.remoteserver.talend.org", "dh"), org.apache.axis.description.ParameterDesc.IN, new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "anyType"), java.lang.Object.class, false, false), 
        };
        _oper = new org.apache.axis.description.OperationDesc("storeFile", _params, new javax.xml.namespace.QName("http://business.archive.remoteserver.talend.org", "StoreFileReturn"));
        _oper.setReturnType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        _oper.setElementQName(new javax.xml.namespace.QName("http://business.archive.remoteserver.talend.org", "StoreFile"));
        _oper.setSoapAction("");
        _myOperationsList.add(_oper);
        if (_myOperations.get("storeFile") == null) {
            _myOperations.put("storeFile", new java.util.ArrayList());
        }
        ((java.util.List)_myOperations.get("storeFile")).add(_oper);
    }

    public ArchiveSoapBindingSkeleton() {
        this.impl = new org.talend.remoteserver.archive.business.ArchiveSoapBindingImpl();
    }

    public ArchiveSoapBindingSkeleton(org.talend.remoteserver.archive.business.Archive impl) {
        this.impl = impl;
    }
    public java.lang.String storeFile(java.lang.String filename, java.lang.Object dh) throws java.rmi.RemoteException
    {
        java.lang.String ret = impl.storeFile(filename, dh);
        return ret;
    }

}
