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
package org.talend.repository.remoteprovider.wsclient.pingclient;

import java.net.ConnectException;
import java.net.UnknownHostException;
import java.rmi.RemoteException;

import org.talend.commons.exception.BusinessException;
import org.talend.repository.remoteprovider.i18n.Messages;
import org.talend.repository.remoteprovider.wsclient.pingclient.proxy.PingServerProxy;

/**
 * DOC mhirt class global comment. Detailled comment <br/>
 * 
 * $Id$
 * 
 */
public class ServerManagement {

    private static String message = "";

    public static boolean ping(String server, String context, int port) throws BusinessException {
        PingServerProxy proxy = new PingServerProxy();
        // proxy.setEndpoint("http://localhost:8080/org.talend.remoteserver/services/PingServer");
        proxy.setEndpoint("http://" + server + ":" + port + "/" + context + ((context != "") ? "/" : "")
                + "org.talend.remoteserver/services/PingServer");
        try {
            return proxy.isRunning();
        } catch (RemoteException e) {
            if (e.getCause() != null) {
                if (e.getCause() instanceof UnknownHostException) {
                    setMessage(Messages.getString("webServiceClient.serverManagement.unreachableServer"));
                } else if (e.getCause() instanceof ConnectException) {
                    setMessage(Messages.getString("webServiceClient.serverManagement.refusedConnection"));
                } else {
                    setMessage(e.getCause().getLocalizedMessage());
                }
            } else {
                setMessage(Messages.getString("webServiceClient.serverManagement.refusedContext"));
            }
            throw new BusinessException(e);
        }
    }

    /**
     * Getter for message.
     * 
     * @return the message
     */
    public static String getMessage() {
        return ServerManagement.message;
    }

    /**
     * Sets the message.
     * 
     * @param message the message to set
     */
    public static void setMessage(String message) {
        ServerManagement.message = message;
    }
}
