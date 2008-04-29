/**
 * GetRevisionListPortType.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */

package org.talend.designer.components.ecosystem.ws;

public interface GetRevisionListPortType extends java.rmi.Remote {
    public org.talend.designer.components.ecosystem.ws.Revision[] getRevisionList(java.lang.String version, int category_id) throws java.rmi.RemoteException;
}
