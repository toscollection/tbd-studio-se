/**
 * GetRevisionListLocator.java
 * 
 * This file was auto-generated from WSDL by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */

package org.talend.designer.components.ecosystem.ws;

public class GetRevisionListLocator extends org.apache.axis.client.Service implements
        org.talend.designer.components.ecosystem.ws.GetRevisionList {

    public GetRevisionListLocator() {
    }

    public GetRevisionListLocator(org.apache.axis.EngineConfiguration config) {
        super(config);
    }

    public GetRevisionListLocator(java.lang.String wsdlLoc, javax.xml.namespace.QName sName)
            throws javax.xml.rpc.ServiceException {
        super(wsdlLoc, sName);
    }

    // Use to get a proxy class for getRevisionListPort
    private java.lang.String getRevisionListPort_address = "http://talendforge.org/ext/soap_server.php"; //$NON-NLS-1$

    public java.lang.String getgetRevisionListPortAddress() {
        return getRevisionListPort_address;
    }

    // The WSDD service name defaults to the port name.
    private java.lang.String getRevisionListPortWSDDServiceName = "getRevisionListPort"; //$NON-NLS-1$

    public java.lang.String getgetRevisionListPortWSDDServiceName() {
        return getRevisionListPortWSDDServiceName;
    }

    public void setgetRevisionListPortWSDDServiceName(java.lang.String name) {
        getRevisionListPortWSDDServiceName = name;
    }

    public org.talend.designer.components.ecosystem.ws.GetRevisionListPortType getgetRevisionListPort()
            throws javax.xml.rpc.ServiceException {
        java.net.URL endpoint;
        try {
            endpoint = new java.net.URL(getRevisionListPort_address);
        } catch (java.net.MalformedURLException e) {
            throw new javax.xml.rpc.ServiceException(e);
        }
        return getgetRevisionListPort(endpoint);
    }

    public org.talend.designer.components.ecosystem.ws.GetRevisionListPortType getgetRevisionListPort(java.net.URL portAddress)
            throws javax.xml.rpc.ServiceException {
        try {
            org.talend.designer.components.ecosystem.ws.GetRevisionListBindingStub _stub = new org.talend.designer.components.ecosystem.ws.GetRevisionListBindingStub(
                    portAddress, this);
            _stub.setPortName(getgetRevisionListPortWSDDServiceName());
            return _stub;
        } catch (org.apache.axis.AxisFault e) {
            return null;
        }
    }

    public void setgetRevisionListPortEndpointAddress(java.lang.String address) {
        getRevisionListPort_address = address;
    }

    /**
     * For the given interface, get the stub implementation. If this service has no port for the given interface, then
     * ServiceException is thrown.
     */
    @Override
    public java.rmi.Remote getPort(Class serviceEndpointInterface) throws javax.xml.rpc.ServiceException {
        try {
            if (org.talend.designer.components.ecosystem.ws.GetRevisionListPortType.class
                    .isAssignableFrom(serviceEndpointInterface)) {
                org.talend.designer.components.ecosystem.ws.GetRevisionListBindingStub _stub = new org.talend.designer.components.ecosystem.ws.GetRevisionListBindingStub(
                        new java.net.URL(getRevisionListPort_address), this);
                _stub.setPortName(getgetRevisionListPortWSDDServiceName());
                return _stub;
            }
        } catch (java.lang.Throwable t) {
            throw new javax.xml.rpc.ServiceException(t);
        }
        throw new javax.xml.rpc.ServiceException("There is no stub implementation for the interface:  " //$NON-NLS-1$
                + (serviceEndpointInterface == null ? "null" : serviceEndpointInterface.getName())); //$NON-NLS-1$
    }

    /**
     * For the given interface, get the stub implementation. If this service has no port for the given interface, then
     * ServiceException is thrown.
     */
    @Override
    public java.rmi.Remote getPort(javax.xml.namespace.QName portName, Class serviceEndpointInterface)
            throws javax.xml.rpc.ServiceException {
        if (portName == null) {
            return getPort(serviceEndpointInterface);
        }
        java.lang.String inputPortName = portName.getLocalPart();
        if ("getRevisionListPort".equals(inputPortName)) { //$NON-NLS-1$
            return getgetRevisionListPort();
        } else {
            java.rmi.Remote _stub = getPort(serviceEndpointInterface);
            ((org.apache.axis.client.Stub) _stub).setPortName(portName);
            return _stub;
        }
    }

    @Override
    public javax.xml.namespace.QName getServiceName() {
        return new javax.xml.namespace.QName("http://talendforge.org/ext/wsdl", "getRevisionList"); //$NON-NLS-1$ //$NON-NLS-2$
    }

    private java.util.HashSet ports = null;

    @Override
    public java.util.Iterator getPorts() {
        if (ports == null) {
            ports = new java.util.HashSet();
            ports.add(new javax.xml.namespace.QName("http://talendforge.org/ext/wsdl", "getRevisionListPort")); //$NON-NLS-1$ //$NON-NLS-2$
        }
        return ports.iterator();
    }

    /**
     * Set the endpoint address for the specified port name.
     */
    public void setEndpointAddress(java.lang.String portName, java.lang.String address) throws javax.xml.rpc.ServiceException {

        if ("getRevisionListPort".equals(portName)) { //$NON-NLS-1$
            setgetRevisionListPortEndpointAddress(address);
        } else { // Unknown Port Name
            throw new javax.xml.rpc.ServiceException(" Cannot set Endpoint Address for Unknown Port" + portName); //$NON-NLS-1$
        }
    }

    /**
     * Set the endpoint address for the specified port name.
     */
    public void setEndpointAddress(javax.xml.namespace.QName portName, java.lang.String address)
            throws javax.xml.rpc.ServiceException {
        setEndpointAddress(portName.getLocalPart(), address);
    }

}
