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

package org.talend.repository.remoteprovider.wsclient.pingclient.proxy;

/**
 * DOC mhirt  class global comment. Detailled comment
 * <br/>
 *
 * $Id$
 *
 */
public class PingServerProxy implements IPingServer {

    private String endpoint = null;

    private IPingServer pingServer = null;

    public PingServerProxy() {
        initPingServerProxy();
    }

    private void initPingServerProxy() {
        try {
            pingServer = (new PingServerServiceLocator()).getPingServer();
            if (pingServer != null) {
                if (endpoint != null) {
                    ((javax.xml.rpc.Stub) pingServer)._setProperty("javax.xml.rpc.service.endpoint.address", endpoint);
                } else {
                    endpoint = (String) ((javax.xml.rpc.Stub) pingServer)
                            ._getProperty("javax.xml.rpc.service.endpoint.address");
                }
            }
        } catch (Exception serviceException) {
            // do nothing
        }
    }

    public String getEndpoint() {
        return endpoint;
    }

    public void setEndpoint(String endpoint) {
        this.endpoint = endpoint;
        if (pingServer != null) {
            ((javax.xml.rpc.Stub) pingServer)._setProperty("javax.xml.rpc.service.endpoint.address", endpoint);
        }

    }

    public IPingServer getPingServer() {
        if (pingServer == null) {
            initPingServerProxy();
        }
        return pingServer;
    }

    public boolean isRunning() throws java.rmi.RemoteException {
        if (pingServer == null) {
            initPingServerProxy();
        }
        return pingServer.isRunning();
    }
}
