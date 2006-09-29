/**
 * ArchiveService.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.3 Oct 05, 2005 (05:23:37 EDT) WSDL2Java emitter.
 */

package org.talend.remoteserver.archive.business;

public interface ArchiveService extends javax.xml.rpc.Service {
    public java.lang.String getArchiveAddress();

    public org.talend.remoteserver.archive.business.Archive getArchive() throws javax.xml.rpc.ServiceException;

    public org.talend.remoteserver.archive.business.Archive getArchive(java.net.URL portAddress) throws javax.xml.rpc.ServiceException;
}
