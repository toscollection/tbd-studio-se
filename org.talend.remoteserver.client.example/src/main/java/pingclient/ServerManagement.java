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
package pingclient;

import java.rmi.RemoteException;

import pingclient.proxy.PingServerProxy;

/**
 * DOC mhirt class global comment. Detailled comment <br/>
 * 
 * $Id$
 * 
 */
public class ServerManagement {

    String message = "";

    public boolean ping(String server, String context, int port) {
        if (context != "") {
            context += "/";
        }
        PingServerProxy proxy = new PingServerProxy();
        // proxy.setEndpoint("http://localhost:8080/org.talend.remoteserver/services/PingServer");
        proxy.setEndpoint("http://" + server + ":" + port + "/" + context
                + "org.talend.remoteserver/services/PingServer");
        try {
            return proxy.isRunning();
        } catch (RemoteException e) {
            setMessage(e.getMessage());
        }
        return false;
    }

    /**
     * Getter for message.
     * 
     * @return the message
     */
    public String getMessage() {
        return this.message;
    }

    /**
     * Sets the message.
     * 
     * @param message the message to set
     */
    public void setMessage(String message) {
        this.message = message;
    }
}
