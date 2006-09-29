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
public class ArchiveProxy implements org.talend.remoteserver.client.example.archive.IArchive {

    private String endpoint = null;

    private org.talend.remoteserver.client.example.archive.IArchive archive = null;

    public ArchiveProxy() {
        initArchiveProxy();
    }

    private void initArchiveProxy() {
        try {
            archive = (new org.talend.remoteserver.client.example.archive.ArchiveServiceLocator()).getArchive();
            if (archive != null) {
                if (endpoint != null) {
                    ((javax.xml.rpc.Stub) archive)._setProperty("javax.xml.rpc.service.endpoint.address", endpoint);
                } else {
                    endpoint = (String) ((javax.xml.rpc.Stub) archive)
                            ._getProperty("javax.xml.rpc.service.endpoint.address");
                }
            }

        } catch (javax.xml.rpc.ServiceException serviceException) {
            // do nothing
        }
    }

    public String getEndpoint() {
        return endpoint;
    }

    public void setEndpoint(String endpoint) {
        this.endpoint = endpoint;
        if (archive != null) {
            ((javax.xml.rpc.Stub) archive)._setProperty("javax.xml.rpc.service.endpoint.address", endpoint);
        }

    }

    public org.talend.remoteserver.client.example.archive.IArchive getArchive() {
        if (archive == null) {
            initArchiveProxy();
        }
        return archive;
    }

    public java.lang.String storeFile(java.lang.String filename, java.lang.Object dh) throws java.rmi.RemoteException {
        if (archive == null) {
            initArchiveProxy();
        }
        return archive.storeFile(filename, dh);
    }

}
