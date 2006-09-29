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

package pingclient.proxy;

/**
 * DOC mhirt  class global comment. Detailled comment
 * <br/>
 *
 * $Id$
 *
 */
public class PingServerServiceLocator extends org.apache.axis.client.Service implements IPingServerService {

    /**
     * 
     */
    private static final long serialVersionUID = 252792907806998775L;

    public PingServerServiceLocator() {
    }

    public PingServerServiceLocator(org.apache.axis.EngineConfiguration config) {
        super(config);
    }

    public PingServerServiceLocator(java.lang.String wsdlLoc, javax.xml.namespace.QName sName)
            throws javax.xml.rpc.ServiceException {
        super(wsdlLoc, sName);
    }

    // Use to get a proxy class for PingServer
    private java.lang.String pingServerAddress = "http://localhost:8080/org.talend.remoteserver/services/PingServer";

    public java.lang.String getPingServerAddress() {
        return pingServerAddress;
    }

    // The WSDD service name defaults to the port name.
    private java.lang.String pingServerWSDDServiceName = "PingServer";

    public java.lang.String getPingServerWSDDServiceName() {
        return pingServerWSDDServiceName;
    }

    public void setPingServerWSDDServiceName(java.lang.String name) {
        pingServerWSDDServiceName = name;
    }

    public IPingServer getPingServer() throws javax.xml.rpc.ServiceException {
        java.net.URL endpoint;
        try {
            endpoint = new java.net.URL(pingServerAddress);
        } catch (java.net.MalformedURLException e) {
            throw new javax.xml.rpc.ServiceException(e);
        }
        return getPingServer(endpoint);
    }

    public IPingServer getPingServer(java.net.URL portAddress) throws javax.xml.rpc.ServiceException {
        try {
            PingServerSoapBindingStub stub = new PingServerSoapBindingStub(portAddress, this);
            stub.setPortName(getPingServerWSDDServiceName());
            return stub;
        } catch (org.apache.axis.AxisFault e) {
            return null;
        }
    }

    public void setPingServerEndpointAddress(java.lang.String address) {
        pingServerAddress = address;
    }

    /**
     * For the given interface, get the stub implementation. If this service has no port for the given interface, then
     * ServiceException is thrown.
     */
    public java.rmi.Remote getPort(Class serviceEndpointInterface) throws javax.xml.rpc.ServiceException {
        try {
            if (IPingServer.class.isAssignableFrom(serviceEndpointInterface)) {
                PingServerSoapBindingStub stub = new PingServerSoapBindingStub(new java.net.URL(pingServerAddress),
                        this);
                stub.setPortName(getPingServerWSDDServiceName());
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
        if ("PingServer".equals(inputPortName)) {
            return getPingServer();
        } else {
            java.rmi.Remote stub = getPort(serviceEndpointInterface);
            ((org.apache.axis.client.Stub) stub).setPortName(portName);
            return stub;
        }
    }

    public javax.xml.namespace.QName getServiceName() {
        return new javax.xml.namespace.QName("http://servermanagement.remoteserver.talend.org", "PingServerService");
    }

    private java.util.HashSet ports = null;

    @SuppressWarnings("unchecked")
    public java.util.Iterator getPorts() {
        if (ports == null) {
            ports = new java.util.HashSet();
            ports.add(new javax.xml.namespace.QName("http://servermanagement.remoteserver.talend.org", "PingServer"));
        }
        return ports.iterator();
    }

    /**
     * Set the endpoint address for the specified port name.
     */
    public void setEndpointAddress(java.lang.String portName, java.lang.String address)
            throws javax.xml.rpc.ServiceException {

        if ("PingServer".equals(portName)) {
            setPingServerEndpointAddress(address);
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
