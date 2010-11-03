// ============================================================================
//
// Copyright (C) 2006-2010 Talend Inc. - www.talend.com
//
// This source code is available under agreement available at
// %InstallDIR%\features\org.talend.rcp.branding.%PRODUCTNAME%\%PRODUCTNAME%license.txt
//
// You should have received a copy of the agreement
// along with this program; if not, write to Talend SA
// 9 rue Pages 92150 Suresnes, France
//
// ============================================================================
package org.talend.designer.core.model;

import java.util.Map;

import org.eclipse.emf.common.util.EList;
import org.talend.core.model.components.EParameterName;
import org.talend.designer.core.model.utils.emf.talendfile.ConnectionType;

/**
 * DOC Administrator class global comment. Detailled comment
 */
public class AbstractConnection extends AbstractElement implements ConnectionType {

    protected String metaName;

    protected String uniqueName;

    // true if this connection is activated.
    protected boolean activate = true;

    protected boolean readOnly = false;

    protected Map<String, String> traceData;

    protected String connectorName;

    protected String name;

    protected boolean monitorConnection = false;

    protected int order = -1;

    public int getOrder() {
        return this.order;
    }

    public void setOrder(int order) {
        this.order = order;
    }

    public boolean isActivate() {
        return this.activate;
    }

    protected void setActivate(boolean activate) {
        this.activate = activate;
        firePropertyChange(EParameterName.ACTIVATE.getName(), null, null);
    }

    /**
     * Tells if this connection has a subjob source or not instead of a node.
     */
    protected boolean isSubjobConnection;

    /**
     * Getter for isSubjobConnection.
     * 
     * @return the isSubjobConnection
     */
    public boolean isSubjobConnection() {
        return this.isSubjobConnection;
    }

    /**
     * Sets the isSubjobConnection.
     * 
     * @param isSubjobConnection the isSubjobConnection to set
     */
    public void setSubjobConnection(boolean isSubjobConnection) {
        this.isSubjobConnection = isSubjobConnection;
    }

    public void setMetaName(String metaName) {
        this.metaName = metaName;
    }

    public String getMetaName() {
        return metaName;
    }

    /**
     * 
     * Only works for FLOW_MAIN, FLOW_REF or TABLE link.
     * 
     * @return
     */
    public String getUniqueName() {
        // if (source != null) {
        // if (source.getConnectorFromType(lineStyle).isBuiltIn()) {
        // return metaName;
        // }
        // }
        return uniqueName;
    }

    public void setUniqueName(String uniqueName) {
        this.uniqueName = uniqueName;
    }

    public boolean isReadOnly() {
        return this.readOnly;
    }

    public void setReadOnly(boolean readOnly) {
        this.readOnly = readOnly;
    }

    /**
     * Getter for connectionType.
     * 
     * @return the connectionType
     */
    public String getConnectorName() {
        return connectorName;
    }

    public Map<String, String> getTraceData() {
        return this.traceData;
    }

    /**
     * Getter for monitorConnection.
     * 
     * @return the monitorConnection
     */
    public boolean isMonitorConnection() {
        return this.monitorConnection;
    }

    /**
     * Sets the monitorConnection.
     * 
     * @param monitorConnection the monitorConnection to set
     */
    public void setMonitorConnection(boolean monitorConnection) {
        this.monitorConnection = monitorConnection;
        firePropertyChange(EParameterName.MONITOR_CONNECTION.getName(), null, name);
    }

    /*
     * (non-Javadoc)
     * 
     * @see org.talend.designer.core.model.AbstractElement#getElementName()
     */
    @Override
    public String getElementName() {
        // TODO Auto-generated method stub
        return null;
    }

    /*
     * (non-Javadoc)
     * 
     * @see org.talend.designer.core.model.utils.emf.talendfile.ConnectionType#getConnectionStyle()
     */
    public int getConnectionStyle() {
        // TODO Auto-generated method stub
        return 0;
    }

    /*
     * (non-Javadoc)
     * 
     * @see org.talend.designer.core.model.utils.emf.talendfile.ConnectionType#getElementParameter()
     */
    public EList getElementParameter() {
        // TODO Auto-generated method stub
        return null;
    }

    /*
     * (non-Javadoc)
     * 
     * @see org.talend.designer.core.model.utils.emf.talendfile.ConnectionType#getLabel()
     */
    public String getLabel() {
        // TODO Auto-generated method stub
        return null;
    }

    /*
     * (non-Javadoc)
     * 
     * @see org.talend.designer.core.model.utils.emf.talendfile.ConnectionType#getMergeOrder()
     */
    public int getMergeOrder() {
        // TODO Auto-generated method stub
        return 0;
    }

    /*
     * (non-Javadoc)
     * 
     * @see org.talend.designer.core.model.utils.emf.talendfile.ConnectionType#getMetaname()
     */
    public String getMetaname() {
        // TODO Auto-generated method stub
        return null;
    }

    /*
     * (non-Javadoc)
     * 
     * @see org.talend.designer.core.model.utils.emf.talendfile.ConnectionType#getOffsetLabelX()
     */
    public int getOffsetLabelX() {
        // TODO Auto-generated method stub
        return 0;
    }

    /*
     * (non-Javadoc)
     * 
     * @see org.talend.designer.core.model.utils.emf.talendfile.ConnectionType#getOffsetLabelY()
     */
    public int getOffsetLabelY() {
        // TODO Auto-generated method stub
        return 0;
    }

    /*
     * (non-Javadoc)
     * 
     * @see org.talend.designer.core.model.utils.emf.talendfile.ConnectionType#getOutputId()
     */
    public int getOutputId() {
        // TODO Auto-generated method stub
        return 0;
    }

    /*
     * (non-Javadoc)
     * 
     * @see org.talend.designer.core.model.utils.emf.talendfile.ConnectionType#getSourceName()
     */
    public String getSourceName() {
        // TODO Auto-generated method stub
        return null;
    }

    /*
     * (non-Javadoc)
     * 
     * @see org.talend.designer.core.model.utils.emf.talendfile.ConnectionType#getTargetName()
     */
    public String getTargetName() {
        // TODO Auto-generated method stub
        return null;
    }

    /*
     * (non-Javadoc)
     * 
     * @see org.talend.designer.core.model.utils.emf.talendfile.ConnectionType#isSetConnectionStyle()
     */
    public boolean isSetConnectionStyle() {
        // TODO Auto-generated method stub
        return false;
    }

    /*
     * (non-Javadoc)
     * 
     * @see org.talend.designer.core.model.utils.emf.talendfile.ConnectionType#isSetMergeOrder()
     */
    public boolean isSetMergeOrder() {
        // TODO Auto-generated method stub
        return false;
    }

    /*
     * (non-Javadoc)
     * 
     * @see org.talend.designer.core.model.utils.emf.talendfile.ConnectionType#isSetOffsetLabelX()
     */
    public boolean isSetOffsetLabelX() {
        // TODO Auto-generated method stub
        return false;
    }

    /*
     * (non-Javadoc)
     * 
     * @see org.talend.designer.core.model.utils.emf.talendfile.ConnectionType#isSetOffsetLabelY()
     */
    public boolean isSetOffsetLabelY() {
        // TODO Auto-generated method stub
        return false;
    }

    /*
     * (non-Javadoc)
     * 
     * @see org.talend.designer.core.model.utils.emf.talendfile.ConnectionType#isSetOutputId()
     */
    public boolean isSetOutputId() {
        // TODO Auto-generated method stub
        return false;
    }

    /*
     * (non-Javadoc)
     * 
     * @see org.talend.designer.core.model.utils.emf.talendfile.ConnectionType#setConnectionStyle(int)
     */
    public void setConnectionStyle(int value) {
        // TODO Auto-generated method stub

    }

    /*
     * (non-Javadoc)
     * 
     * @see org.talend.designer.core.model.utils.emf.talendfile.ConnectionType#setConnectorName(java.lang.String)
     */
    public void setConnectorName(String value) {
        // TODO Auto-generated method stub

    }

    /*
     * (non-Javadoc)
     * 
     * @see org.talend.designer.core.model.utils.emf.talendfile.ConnectionType#setLabel(java.lang.String)
     */
    public void setLabel(String value) {
        // TODO Auto-generated method stub

    }

    /*
     * (non-Javadoc)
     * 
     * @see org.talend.designer.core.model.utils.emf.talendfile.ConnectionType#setMergeOrder(int)
     */
    public void setMergeOrder(int value) {
        // TODO Auto-generated method stub

    }

    /*
     * (non-Javadoc)
     * 
     * @see org.talend.designer.core.model.utils.emf.talendfile.ConnectionType#setMetaname(java.lang.String)
     */
    public void setMetaname(String value) {
        // TODO Auto-generated method stub

    }

    /*
     * (non-Javadoc)
     * 
     * @see org.talend.designer.core.model.utils.emf.talendfile.ConnectionType#setOffsetLabelX(int)
     */
    public void setOffsetLabelX(int value) {
        // TODO Auto-generated method stub

    }

    /*
     * (non-Javadoc)
     * 
     * @see org.talend.designer.core.model.utils.emf.talendfile.ConnectionType#setOffsetLabelY(int)
     */
    public void setOffsetLabelY(int value) {
        // TODO Auto-generated method stub

    }

    /*
     * (non-Javadoc)
     * 
     * @see org.talend.designer.core.model.utils.emf.talendfile.ConnectionType#setOutputId(int)
     */
    public void setOutputId(int value) {
        // TODO Auto-generated method stub

    }

    /*
     * (non-Javadoc)
     * 
     * @see org.talend.designer.core.model.utils.emf.talendfile.ConnectionType#setSourceName(java.lang.String)
     */
    public void setSourceName(String value) {
        // TODO Auto-generated method stub

    }

    /*
     * (non-Javadoc)
     * 
     * @see org.talend.designer.core.model.utils.emf.talendfile.ConnectionType#setTargetName(java.lang.String)
     */
    public void setTargetName(String value) {
        // TODO Auto-generated method stub

    }

    /*
     * (non-Javadoc)
     * 
     * @see org.talend.designer.core.model.utils.emf.talendfile.ConnectionType#unsetConnectionStyle()
     */
    public void unsetConnectionStyle() {
        // TODO Auto-generated method stub

    }

    /*
     * (non-Javadoc)
     * 
     * @see org.talend.designer.core.model.utils.emf.talendfile.ConnectionType#unsetMergeOrder()
     */
    public void unsetMergeOrder() {
        // TODO Auto-generated method stub

    }

    /*
     * (non-Javadoc)
     * 
     * @see org.talend.designer.core.model.utils.emf.talendfile.ConnectionType#unsetOffsetLabelX()
     */
    public void unsetOffsetLabelX() {
        // TODO Auto-generated method stub

    }

    /*
     * (non-Javadoc)
     * 
     * @see org.talend.designer.core.model.utils.emf.talendfile.ConnectionType#unsetOffsetLabelY()
     */
    public void unsetOffsetLabelY() {
        // TODO Auto-generated method stub

    }

    /*
     * (non-Javadoc)
     * 
     * @see org.talend.designer.core.model.utils.emf.talendfile.ConnectionType#unsetOutputId()
     */
    public void unsetOutputId() {
        // TODO Auto-generated method stub

    }

}
