/**
 * GetRevisionListPortType.java
 * 
 * This file was auto-generated from WSDL by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */

package org.talend.designer.components.ecosystem.ws;

public interface GetRevisionListPortType extends java.rmi.Remote {

    public java.lang.String getVersionList() throws java.rmi.RemoteException;

    public org.talend.designer.components.ecosystem.ws.Revision[] get_revision_list(java.lang.String version, int category_id)
            throws java.rmi.RemoteException;
}
