/**
 * ArchiveServiceLocator.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.3 Oct 05, 2005 (05:23:37 EDT) WSDL2Java emitter.
 */

package org.talend.remoteserver.archive.business;

public class ArchiveServiceLocator extends org.apache.axis.client.Service implements org.talend.remoteserver.archive.business.ArchiveService {

    public ArchiveServiceLocator() {
    }


    public ArchiveServiceLocator(org.apache.axis.EngineConfiguration config) {
        super(config);
    }

    public ArchiveServiceLocator(java.lang.String wsdlLoc, javax.xml.namespace.QName sName) throws javax.xml.rpc.ServiceException {
        super(wsdlLoc, sName);
    }

    // Use to get a proxy class for Archive
    private java.lang.String Archive_address = "http://localhost:8080/org.talend.remoteserver/services/Archive";

    public java.lang.String getArchiveAddress() {
        return Archive_address;
    }

    // The WSDD service name defaults to the port name.
    private java.lang.String ArchiveWSDDServiceName = "Archive";

    public java.lang.String getArchiveWSDDServiceName() {
        return ArchiveWSDDServiceName;
    }

    public void setArchiveWSDDServiceName(java.lang.String name) {
        ArchiveWSDDServiceName = name;
    }

    public org.talend.remoteserver.archive.business.Archive getArchive() throws javax.xml.rpc.ServiceException {
       java.net.URL endpoint;
        try {
            endpoint = new java.net.URL(Archive_address);
        }
        catch (java.net.MalformedURLException e) {
            throw new javax.xml.rpc.ServiceException(e);
        }
        return getArchive(endpoint);
    }

    public org.talend.remoteserver.archive.business.Archive getArchive(java.net.URL portAddress) throws javax.xml.rpc.ServiceException {
        try {
            org.talend.remoteserver.archive.business.ArchiveSoapBindingStub _stub = new org.talend.remoteserver.archive.business.ArchiveSoapBindingStub(portAddress, this);
            _stub.setPortName(getArchiveWSDDServiceName());
            return _stub;
        }
        catch (org.apache.axis.AxisFault e) {
            return null;
        }
    }

    public void setArchiveEndpointAddress(java.lang.String address) {
        Archive_address = address;
    }

    /**
     * For the given interface, get the stub implementation.
     * If this service has no port for the given interface,
     * then ServiceException is thrown.
     */
    public java.rmi.Remote getPort(Class serviceEndpointInterface) throws javax.xml.rpc.ServiceException {
        try {
            if (org.talend.remoteserver.archive.business.Archive.class.isAssignableFrom(serviceEndpointInterface)) {
                org.talend.remoteserver.archive.business.ArchiveSoapBindingStub _stub = new org.talend.remoteserver.archive.business.ArchiveSoapBindingStub(new java.net.URL(Archive_address), this);
                _stub.setPortName(getArchiveWSDDServiceName());
                return _stub;
            }
        }
        catch (java.lang.Throwable t) {
            throw new javax.xml.rpc.ServiceException(t);
        }
        throw new javax.xml.rpc.ServiceException("There is no stub implementation for the interface:  " + (serviceEndpointInterface == null ? "null" : serviceEndpointInterface.getName()));
    }

    /**
     * For the given interface, get the stub implementation.
     * If this service has no port for the given interface,
     * then ServiceException is thrown.
     */
    public java.rmi.Remote getPort(javax.xml.namespace.QName portName, Class serviceEndpointInterface) throws javax.xml.rpc.ServiceException {
        if (portName == null) {
            return getPort(serviceEndpointInterface);
        }
        java.lang.String inputPortName = portName.getLocalPart();
        if ("Archive".equals(inputPortName)) {
            return getArchive();
        }
        else  {
            java.rmi.Remote _stub = getPort(serviceEndpointInterface);
            ((org.apache.axis.client.Stub) _stub).setPortName(portName);
            return _stub;
        }
    }

    public javax.xml.namespace.QName getServiceName() {
        return new javax.xml.namespace.QName("http://business.archive.remoteserver.talend.org", "ArchiveService");
    }

    private java.util.HashSet ports = null;

    public java.util.Iterator getPorts() {
        if (ports == null) {
            ports = new java.util.HashSet();
            ports.add(new javax.xml.namespace.QName("http://business.archive.remoteserver.talend.org", "Archive"));
        }
        return ports.iterator();
    }

    /**
    * Set the endpoint address for the specified port name.
    */
    public void setEndpointAddress(java.lang.String portName, java.lang.String address) throws javax.xml.rpc.ServiceException {
        
if ("Archive".equals(portName)) {
            setArchiveEndpointAddress(address);
        }
        else 
{ // Unknown Port Name
            throw new javax.xml.rpc.ServiceException(" Cannot set Endpoint Address for Unknown Port" + portName);
        }
    }

    /**
    * Set the endpoint address for the specified port name.
    */
    public void setEndpointAddress(javax.xml.namespace.QName portName, java.lang.String address) throws javax.xml.rpc.ServiceException {
        setEndpointAddress(portName.getLocalPart(), address);
    }

}
