// ============================================================================
//
// Copyright (C) 2006-2011 Talend Inc. - www.talend.com
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

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.StringReader;
import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;
import org.talend.core.model.components.EParameterName;
import org.talend.core.model.components.IComponent;
import org.talend.core.model.components.IMultipleComponentManager;
import org.talend.core.model.components.IODataComponent;
import org.talend.core.model.metadata.IMetadataTable;
import org.talend.core.model.process.BlockCode;
import org.talend.core.model.process.EConnectionType;
import org.talend.core.model.process.EParameterFieldType;
import org.talend.core.model.process.IConnection;
import org.talend.core.model.process.IConnectionCategory;
import org.talend.core.model.process.IElementParameter;
import org.talend.core.model.process.IExternalData;
import org.talend.core.model.process.IExternalNode;
import org.talend.core.model.process.INodeConnector;
import org.talend.designer.core.model.i18n.Messages;
import org.talend.designer.core.model.utils.emf.talendfile.NodeType;

/**
 * DOC Administrator class global comment. Detailled comment
 */
public abstract class AbstractNode extends AbstractElement implements NodeType {

    private static Logger log = Logger.getLogger(AbstractNode.class);

    protected boolean template = false;

    protected boolean schemaSynchronized = true;

    protected String name, label;

    public static final String UPDATE_STATUS = "addStatus"; //$NON-NLS-1$

    // true if this node is set as a start node.
    protected boolean start;

    protected String pluginFullName;

    protected String errorInfo;

    protected boolean errorFlag;

    protected List<? extends INodeConnector> listConnector;

    protected boolean compareFlag;

    protected boolean dummy;

    protected boolean showHint;

    // true if this node is activated.
    protected boolean activate = true;

    protected int currentStatus, oldStatus = 0;

    protected String showHintText;

    protected String connectionName;

    protected IComponent component;

    protected final List<IConnection> outputs = new ArrayList<IConnection>();

    protected final List<IConnection> inputs = new ArrayList<IConnection>();

    protected IExternalData externalData = null;

    protected IExternalNode externalNode = null;

    public static final String INPUTS = "inputs"; //$NON-NLS-1$

    public static final String OUTPUTS = "outputs"; //$NON-NLS-1$

    protected String performanceData;

    protected List<String> errorList = new ArrayList<String>(), warningList = new ArrayList<String>();

    protected List<IMetadataTable> metadataList;

    public List<IMetadataTable> getMetadataList() {
        return this.metadataList;
    }

    public void setMetadataList(final List<IMetadataTable> metaDataList) {
        this.metadataList = metaDataList;
    }

    protected boolean readOnly = false;

    public boolean isReadOnly() {
        return this.readOnly;
    }

    public void setReadOnly(boolean readOnly) {
        this.readOnly = readOnly;
    }

    /**
     * DOC nrousseau Comment method "isDummy".
     * 
     * @return
     */
    public boolean isDummy() {
        return dummy;
    }

    /**
     * DOC nrousseau Comment method "setDummy".
     * 
     * @param value
     */
    public void setDummy(Boolean value) {
        dummy = value;
    }

    public boolean isCompareFlag() {
        return this.compareFlag;
    }

    public void setCompareFlag(boolean compareFlag) {
        this.compareFlag = compareFlag;
    }

    public boolean isErrorFlag() {
        return this.errorFlag;
    }

    public void setErrorFlag(boolean errorFlag) {
        this.errorFlag = errorFlag;
    }

    public String getErrorInfo() {
        return this.errorInfo;
    }

    public void setErrorInfo(String errorInfo) {
        this.errorInfo = errorInfo;
    }

    public String getPluginFullName() {
        return pluginFullName;
    }

    public void setPluginFullName(String pluginFullName) {
        this.pluginFullName = pluginFullName;
    }

    /**
     * Gives the label of the node.
     * 
     * @return
     */
    public String getLabel() {
        return label;
    }

    public boolean isTemplate() {
        return this.template;
    }

    /**
     * Set this node as the start of the diagram.
     * 
     * @param start boolean that will give the status
     */
    public void setStart(final boolean start) {
        IElementParameter param = getElementParameter(EParameterName.START.getName());
        if (param == null) {
            return;
        }
        param.setValue(new Boolean(start));
        this.start = start;
        firePropertyChange(EParameterName.START.getName(), null, null);

    }

    /**
     * Return the start status of this node.
     * 
     * @return
     */
    public boolean isStart() {
        return start;
    }

    public void setTemplate(boolean template) {
        this.template = template;
    }

    public static final String SIZE = "nodeSize"; //$NON-NLS-1$

    public void setData(byte[] bytesData, String stringData) {
        ByteArrayInputStream inputStream = null;
        StringReader reader = null;

        if (externalNode == null) {
            return;
        }
        if (bytesData != null) {
            inputStream = new ByteArrayInputStream(bytesData);
        }
        if (stringData != null) {
            reader = new StringReader(stringData);
        }

        try {
            externalNode.loadDataIn(inputStream, reader);
        } catch (IOException e) {
            throw new RuntimeException(e);
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
        setExternalData(externalNode.getExternalData());
    }

    public IExternalData getExternalData() {
        return this.externalData;
    }

    public void setExternalData(final IExternalData persistantData) {
        this.externalData = persistantData;
    }

    public void setShowHint(final Boolean showHint) {
        this.showHint = showHint;
        firePropertyChange(EParameterName.HINT.getName(), null, null);

        IElementParameter param = getElementParameter(EParameterName.SHOW_HINT.getName());
        param.setValue(new Boolean(showHint));
    }

    public boolean isSetShowHint() {
        return showHint;
    }

    public void setShowHintText(final String showHintText) {
        this.showHintText = showHintText;

        firePropertyChange(EParameterName.HINT.getName(), null, null);
    }

    public String getShowHintText() {
        return showHintText;
    }

    // hywang add this method for feature 8221
    private boolean isCheckMultiSchemaForMSField() {
        boolean needMultiSchema = false;
        if (this.getElementParameter(EParameterName.COMPONENT_NAME.getName()).getValue().toString().equals(
                "tFileInputMSFieldDelimited") && this.getElementParameter("USE_MUL_SCHEMAS") != null) { //$NON-NLS-1$ //$NON-NLS-2$
            if (Boolean.parseBoolean(this.getElementParameter("USE_MUL_SCHEMAS").getValue().toString())) { //$NON-NLS-1$
                needMultiSchema = true;
            }
        }
        return needMultiSchema;
    }

    /*
     * (non-Javadoc)
     * 
     * @see org.talend.core.model.process.INode#isUseLoopOnConditionalOutput(java.lang.String)
     */
    public boolean isUseLoopOnConditionalOutput(String outputName) {
        return false;
    }

    /*
     * (non-Javadoc)
     * 
     * @see org.talend.core.model.process.INode#getUniqueShortName()
     */
    public String getUniqueShortName() {
        // should't be call from here, should be called from something extends AbstractNode (DataNode, ExternalNode...).
        return null;
    }

    /*
     * (non-Javadoc)
     * 
     * @see org.talend.core.model.process.INode#isGeneratedAsVirtualComponent()
     */
    public boolean isGeneratedAsVirtualComponent() {
        List<IMultipleComponentManager> multipleComponentManagers = getComponent().getMultipleComponentManagers();
        return multipleComponentManagers.size() > 0;
    }

    /*
     * return false is ok, becase all nodes generated from virtual component are DataNode.
     * 
     * @see org.talend.core.model.process.INode#isVirtualGenerateNode()
     */
    public boolean isVirtualGenerateNode() {
        return false;
    }

    /**
     * yzhang Comment method "setConnectionName".
     * 
     * @param name
     */
    public void setConnectionName(String name) {
        this.connectionName = name.replaceAll("\"", "").replaceAll(" ", ""); //$NON-NLS-1$//$NON-NLS-2$ //$NON-NLS-3$ //$NON-NLS-4$
        firePropertyChange(EParameterName.CONNECTION_FORMAT.getName(), null, this.connectionName);
    }

    /**
     * yzhang Comment method "getConnectionName".
     * 
     * @param name
     * @return
     */
    public String getConnectionName() {
        return this.connectionName;
    }

    /**
     * 
     * DOC amaumont Node class global comment. Detailled comment <br/>
     * 
     * $Id: Node.java 49534 2010-10-08 06:02:46Z nrousseau $
     * 
     */
    public class Data {

        byte[] bytesData = new byte[0];

        String stringData = null;

        /**
         * DOC amaumont Data constructor comment.
         */
        public Data() {
            super();
        }

        /**
         * DOC amaumont Data constructor comment.
         * 
         * @param bytesData
         * @param stringData
         */
        public Data(byte[] bytesData, String stringData) {
            super();
            this.bytesData = bytesData;
            this.stringData = stringData;
        }

        public byte[] getBytesData() {
            return this.bytesData;
        }

        public void setBytesData(byte[] bytesData) {
            this.bytesData = bytesData;
        }

        public String getStringData() {
            return this.stringData;
        }

        public void setStringData(String stringData) {
            this.stringData = stringData;
        }
    }

    public boolean isSchemaSynchronized() {
        return schemaSynchronized;
    }

    public List<BlockCode> getBlocksCodeToClose() {
        return null;
    }

    /**
     * Getter for performanceData.
     * 
     * @return the performanceData
     */
    public String getPerformanceData() {
        return this.performanceData;
    }

    /*
     * (non-Javadoc)
     * 
     * @see org.talend.designer.core.ui.editor.Element#getElementName()
     */
    @Override
    public String getElementName() {
        return getUniqueName();
    }

    /**
     * Gives the unique name of the node.
     * 
     * @return unique name
     */
    public String getUniqueName() {
        String uniqueName = null;
        IElementParameter param = getElementParameter(EParameterName.UNIQUE_NAME.getName());
        if (param != null) {
            uniqueName = (String) param.getValue();
        }
        return uniqueName;
    }

    public boolean isExternalNode() {
        if (externalNode != null) {
            return true;
        }
        return false;
    }

    public void setExternalNode(final IExternalNode externalNode) {
        this.externalNode = externalNode;
    }

    public boolean isActivate() {
        return this.activate;
    }

    @Override
    protected Object clone() throws CloneNotSupportedException {
        return super.clone();
    }

    /**
     * Gives all incoming connections (only).
     * 
     * @return List of Connection
     */
    public List<? extends IConnection> getIncomingConnections() {
        return this.inputs;
    }

    public void setIncomingConnections(List<? extends IConnection> connections) {
        this.inputs.clear();
        this.inputs.addAll(connections);
    }

    public void setOutgoingConnections(List<? extends IConnection> connections) {
        this.outputs.clear();
        this.outputs.addAll(connections);
    }

    /**
     * Gives all outgoing connections (only).
     * 
     * @return List of Connection
     */
    public List<? extends IConnection> getOutgoingConnections() {
        return this.outputs;
    }

    /**
     * Force connection draw update. This is only used when the subjobs are collapsed or uncollapsed
     */
    public void forceConnectionsUpdate() {
        fireStructureChange(OUTPUTS, null);
    }

    /*
     * (non-Javadoc)
     * 
     * @see org.talend.designer.core.ui.editor.Element#getPropertyValue(java.lang.Object)
     */
    @Override
    public Object getPropertyValue(final String id) {
        if (id.equals(EParameterName.UNIQUE_NAME.getName())) {
            return getUniqueName();
        }

        return super.getPropertyValue(id);
    }

    public void setErrorInfoChange(final String id, Object value) {
        if (id.equals("ERRORINFO")) { //$NON-NLS-1$
            firePropertyChange(UPDATE_STATUS, null, null);
        } else if (id.equals("COMPAREINFO")) { //$NON-NLS-1$
            firePropertyChange(UPDATE_STATUS, null, null);
        }
    }

    public boolean isSubProcessStart() {
        IConnection connec;
        if (isActivate()) {
            if (!isELTComponent()) {
                for (int j = 0; j < getIncomingConnections().size(); j++) {
                    connec = getIncomingConnections().get(j);
                    if (connec.isActivate()) {
                        if (connec.getLineStyle().hasConnectionCategory(IConnectionCategory.MAIN)) {
                            return false;
                        }
                    }
                }
            } else {
                if (!(Boolean) getPropertyValue(EParameterName.STARTABLE.getName())) {
                    return false;
                }
            }
        }
        if (isDummy()) {
            return false;
        }
        return true;
    }

    public IMetadataTable getMetadataTable(String metaName) {
        for (int i = 0; i < metadataList.size(); i++) {
            if (metadataList.get(i).getTableName().equals(metaName)) {
                return metadataList.get(i);
            }
        }
        return null;
    }

    public IMetadataTable getMetadataFromConnector(String connector) {
        for (IMetadataTable table : metadataList) {
            if (table.getAttachedConnector() != null) {
                if (table.getAttachedConnector().equals(connector)) {
                    return table;
                }
            }
        }
        return null;
    }

    public boolean isELTComponent() {
        return getComponent().getOriginalFamilyName().startsWith("ELT"); //$NON-NLS-1$
    }

    public boolean isFileScaleComponent() {
        return getComponent().getOriginalFamilyName().equals("FileScale"); //$NON-NLS-1$
    }

    public boolean isHL7Output() {
        return getComponent().getName().equals("tHL7Output"); //$NON-NLS-1$
    }

    public IComponent getComponent() {
        return this.component;
    }

    public void setComponent(IComponent component) {
        this.component = component;
    }

    public int getStatus() {
        return currentStatus;
    }

    public int getCurrentActiveLinksNbInput(EConnectionType type) {
        int nb = 0;
        for (IConnection connection : inputs) {
            if (connection.isActivate() && connection.getLineStyle().equals(type)) {
                nb++;
            }
        }
        return nb;
    }

    public int getCurrentActiveLinksNbInput(int connCategory) {
        int nb = 0;
        for (IConnection connection : inputs) {
            if (connection.isActivate() && connection.getLineStyle().hasConnectionCategory(connCategory)) {
                nb++;
            }
        }
        return nb;
    }

    public int getCurrentActiveLinksNbOutput(EConnectionType type) {
        int nb = 0;
        for (IConnection connection : outputs) {
            if (connection.isActivate() && connection.getLineStyle().equals(type)) {
                nb++;
            }
        }
        return nb;
    }

    public int getCurrentActiveLinksNbOutput(int connCategory) {
        int nb = 0;
        for (IConnection connection : outputs) {
            if (connection.isActivate() && connection.getLineStyle().hasConnectionCategory(connCategory)) {
                nb++;
            }
        }
        return nb;
    }

    public boolean canModifySchema() {
        boolean canModifySchema = false;
        List<? extends IElementParameter> listParam = this.getElementParameters();
        for (int i = 0; i < listParam.size(); i++) {
            IElementParameter param = listParam.get(i);
            if (param.isShow(listParam)) {
                if (param.getField().equals(EParameterFieldType.SCHEMA_TYPE)) {
                    canModifySchema = true;
                }
            }
        }
        return canModifySchema;
    }

    @Override
    public String toString() {
        StringBuffer buff = new StringBuffer();
        buff.append(getUniqueName() + " - "); //$NON-NLS-1$
        buff.append(" status(start=" + isStart() + ", subProcessStart=" + isSubProcessStart() + ")"); //$NON-NLS-1$ //$NON-NLS-2$ //$NON-NLS-3$
        buff.append(Messages.getString("Node.input")); //$NON-NLS-1$
        for (int i = 0; i < inputs.size(); i++) {
            buff.append(inputs.get(i).getName());
            if (i < (inputs.size() - 1)) {
                buff.append(","); //$NON-NLS-1$
            }
        }
        buff.append(") "); //$NON-NLS-1$
        buff.append(Messages.getString("Node.output")); //$NON-NLS-1$
        for (int i = 0; i < outputs.size(); i++) {
            buff.append(outputs.get(i).getName());
            if (i < (outputs.size() - 1)) {
                buff.append(","); //$NON-NLS-1$
            }
        }
        buff.append(")"); //$NON-NLS-1$
        return buff.toString();
    }

    /*
     * (non-Javadoc)
     * 
     * @see org.talend.core.model.process.INode#renameMetadataColumnName(java.lang.String, java.lang.String,
     * java.lang.String)
     */
    public void metadataInputChanged(IODataComponent dataComponent, String connectionToApply) {
        log.trace("InputChanged : Node=" + this + ", IOData=[" + dataComponent + "] on " + connectionToApply); //$NON-NLS-1$ //$NON-NLS-2$ //$NON-NLS-3$
        if (externalNode != null) {
            externalNode.metadataInputChanged(dataComponent, connectionToApply);
        }
    }

    public void metadataOutputChanged(IODataComponent dataComponent, String connectionToApply) {
        log.trace("OutputChanged : Node=" + this + ", IOData=[" + dataComponent + "] on " + connectionToApply); //$NON-NLS-1$ //$NON-NLS-2$ //$NON-NLS-3$
        if (externalNode != null) {
            externalNode.metadataOutputChanged(dataComponent, connectionToApply);
        }
    }

    /**
     * Getter for listConnector.
     * 
     * @return the listConnector
     */
    public List<? extends INodeConnector> getListConnector() {
        return listConnector;
    }

}
