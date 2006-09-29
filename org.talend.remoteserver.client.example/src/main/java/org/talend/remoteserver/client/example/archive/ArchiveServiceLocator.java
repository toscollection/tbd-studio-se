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

/**
 * DOC mhirt class global comment. Detailled comment <br/>
 * 
 * $Id$
 * 
 */
public class ArchiveServiceLocator extends org.apache.axis.client.Service implements
        org.talend.remoteserver.client.example.archive.IArchiveService {

    /**
     * 
     */
    private static final long serialVersionUID = -1342250552774912968L;

    public ArchiveServiceLocator() {
    }

    public ArchiveServiceLocator(org.apache.axis.EngineConfiguration config) {
        super(config);
    }

    public ArchiveServiceLocator(java.lang.String wsdlLoc, javax.xml.namespace.QName sName)
            throws javax.xml.rpc.ServiceException {
        super(wsdlLoc, sName);
    }

    // Use to get a proxy class for Archive
    private java.lang.String archiveAddress = "http://localhost:8080/org.talend.remoteserver/services/Archive";

    public java.lang.String getArchiveAddress() {
        return archiveAddress;
    }

    // The WSDD service name defaults to the port name.
    private java.lang.String archiveWSDDServiceName = "Archive";

    public java.lang.String getArchiveWSDDServiceName() {
        return archiveWSDDServiceName;
    }

    public void setArchiveWSDDServiceName(java.lang.String name) {
        archiveWSDDServiceName = name;
    }

    public org.talend.remoteserver.client.example.archive.IArchive getArchive() throws javax.xml.rpc.ServiceException {
        java.net.URL endpoint;
        try {
            endpoint = new java.net.URL(archiveAddress);
        } catch (java.net.MalformedURLException e) {
            throw new javax.xml.rpc.ServiceException(e);
        }
        return getArchive(endpoint);
    }

    public org.talend.remoteserver.client.example.archive.IArchive getArchive(java.net.URL portAddress)
            throws javax.xml.rpc.ServiceException {
        try {
            ArchiveSoapBindingStub stub = new ArchiveSoapBindingStub(portAddress, this);
            stub.setPortName(getArchiveWSDDServiceName());
            return stub;
        } catch (org.apache.axis.AxisFault e) {
            return null;
        }
    }

    public void setArchiveEndpointAddress(java.lang.String address) {
        archiveAddress = address;
    }

    /**
     * For the given interface, get the stub implementation. If this service has no port for the given interface, then
     * ServiceException is thrown.
     */
    public java.rmi.Remote getPort(Class serviceEndpointInterface) throws javax.xml.rpc.ServiceException {
        try {
            if (org.talend.remoteserver.client.example.archive.IArchive.class.isAssignableFrom(serviceEndpointInterface)) {
                ArchiveSoapBindingStub stub = new ArchiveSoapBindingStub(new java.net.URL(archiveAddress), this);
                stub.setPortName(getArchiveWSDDServiceName());
                return stub;
            }
        } catch (java.lang.Throwable t) {
            throw new javax.xml.rpc.ServiceException(t);
        }
        throw new javax.xml.rpc.ServiceException("There is no stub implementation for the interface:  "
                + (serviceEndpointInterface == null ? "null" : serviceEndpointInterface.getName()));
    }

    /**
     * For the given interface, get the stub implementation. If this service has no port for the given interface, then
     * ServiceException is thrown.
     */
    public java.rmi.Remote getPort(javax.xml.namespace.QName portName, Class serviceEndpointInterface)
            throws javax.xml.rpc.ServiceException {
        if (portName == null) {
            return getPort(serviceEndpointInterface);
        }
        java.lang.String inputPortName = portName.getLocalPart();
        if ("Archive".equals(inputPortName)) {
            return getArchive();
        } else {
            java.rmi.Remote stub = getPort(serviceEndpointInterface);
            ((org.apache.axis.client.Stub) stub).setPortName(portName);
            return stub;
        }
    }

    public javax.xml.namespace.QName getServiceName() {
        return new javax.xml.namespace.QName("http://business.archive.remoterver.talend.org", "ArchiveService");
    }

    private java.util.HashSet ports = null;

    @SuppressWarnings("unchecked")
    public java.util.Iterator getPorts() {
        if (ports == null) {
            ports = new java.util.HashSet();
            ports.add(new javax.xml.namespace.QName("http://business.archive.remoterver.talend.org", "Archive"));
        }
        return ports.iterator();
    }

    /**
     * Set the endpoint address for the specified port name.
     */
    public void setEndpointAddress(java.lang.String portName, java.lang.String address)
            throws javax.xml.rpc.ServiceException {

        if ("Archive".equals(portName)) {
            setArchiveEndpointAddress(address);
        } else { // Unknown Port Name
            throw new javax.xml.rpc.ServiceException(" Cannot set Endpoint Address for Unknown Port" + portName);
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
