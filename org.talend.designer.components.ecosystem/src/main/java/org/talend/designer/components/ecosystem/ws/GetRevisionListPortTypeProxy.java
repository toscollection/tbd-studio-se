package org.talend.designer.components.ecosystem.ws;

public class GetRevisionListPortTypeProxy implements org.talend.designer.components.ecosystem.ws.GetRevisionListPortType {

    private String _endpoint = null;

    private org.talend.designer.components.ecosystem.ws.GetRevisionListPortType getRevisionListPortType = null;

    public GetRevisionListPortTypeProxy() {
        _initGetRevisionListPortTypeProxy();
    }

    public GetRevisionListPortTypeProxy(String endpoint) {
        _endpoint = endpoint;
        _initGetRevisionListPortTypeProxy();
    }

    private void _initGetRevisionListPortTypeProxy() {
        try {
            getRevisionListPortType = (new org.talend.designer.components.ecosystem.ws.GetRevisionListLocator())
                    .getgetRevisionListPort();
            if (getRevisionListPortType != null) {
                if (_endpoint != null) {
                    ((javax.xml.rpc.Stub) getRevisionListPortType)._setProperty("javax.xml.rpc.service.endpoint.address", //$NON-NLS-1$
                            _endpoint);
                } else {
                    _endpoint = (String) ((javax.xml.rpc.Stub) getRevisionListPortType)
                            ._getProperty("javax.xml.rpc.service.endpoint.address"); //$NON-NLS-1$
                }
            }

        } catch (javax.xml.rpc.ServiceException serviceException) {
        }
    }

    public String getEndpoint() {
        return _endpoint;
    }

    public void setEndpoint(String endpoint) {
        _endpoint = endpoint;
        if (getRevisionListPortType != null) {
            ((javax.xml.rpc.Stub) getRevisionListPortType)._setProperty("javax.xml.rpc.service.endpoint.address", _endpoint); //$NON-NLS-1$
        }

    }

    public org.talend.designer.components.ecosystem.ws.GetRevisionListPortType getGetRevisionListPortType() {
        if (getRevisionListPortType == null) {
            _initGetRevisionListPortTypeProxy();
        }
        return getRevisionListPortType;
    }

    public java.lang.String getVersionList() throws java.rmi.RemoteException {
        if (getRevisionListPortType == null) {
            _initGetRevisionListPortTypeProxy();
        }
        return getRevisionListPortType.getVersionList();
    }

    public org.talend.designer.components.ecosystem.ws.Revision[] get_revision_list(java.lang.String version, int category_id)
            throws java.rmi.RemoteException {
        if (getRevisionListPortType == null) {
            _initGetRevisionListPortTypeProxy();
        }
        return getRevisionListPortType.get_revision_list(version, category_id);
    }

}
