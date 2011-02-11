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
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import org.eclipse.emf.common.util.EList;
import org.talend.commons.exception.ExceptionHandler;
import org.talend.core.model.codegen.ICodeGenerationService;
import org.talend.core.model.components.EParameterName;
import org.talend.core.model.metadata.IMetadataTable;
import org.talend.core.model.process.IConnection;
import org.talend.core.model.process.IContext;
import org.talend.core.model.process.IContextManager;
import org.talend.core.model.process.INode;
import org.talend.core.model.process.IProcess;
import org.talend.designer.core.model.utils.emf.talendfile.LogsType;
import org.talend.designer.core.model.utils.emf.talendfile.NodeType;
import org.talend.designer.core.model.utils.emf.talendfile.ParametersType;
import org.talend.designer.core.model.utils.emf.talendfile.ProcessType;
import org.talend.designer.core.model.utils.emf.talendfile.RequiredType;
import org.talend.designer.runprocess.IProcessor;

/**
 * DOC Administrator class global comment. Detailled comment
 */
public abstract class AbstractProcess extends AbstractElement implements ProcessType, IProcess {

    protected String id;

    protected String version;

    protected String label;

    protected IContext lastRunContext;

    protected boolean duplicate = false;

    protected boolean needRegenerateCode;

    protected boolean activate = true;

    protected String repositoryId;

    protected IProcessor processor;

    protected Boolean lastVersion;

    protected final String name = new String("Job"); //$NON-NLS-1$

    protected IContextManager contextManager;

    protected byte[] content;

    protected byte[] screenshot = null;

    protected List<NodeType> unloadedNode = null;

    protected boolean processModified = true;

    // list where is stored each unique name for the connections
    protected final List<String> uniqueConnectionNameList = new ArrayList<String>();

    // list where is stored each unique name for the nodes
    protected final List<String> uniqueNodeNameList = new ArrayList<String>();

    Object generatingProcess = null;

    /**
     * Getter for id.
     * 
     * @return the id
     */
    public String getId() {
        return this.id;
    }

    /**
     * Sets the id.
     * 
     * @param id the id to set
     */
    public void setId(String id) {
        this.id = id;
    }

    /**
     * Getter for version.
     * 
     * @return the version
     */
    public String getVersion() {
        return this.version;
    }

    /**
     * Sets the version.
     * 
     * @param version the version to set
     */
    public void setVersion(String version) {
        this.version = version;
    }

    /**
     * Getter for label.
     * 
     * @return the label
     */
    public String getLabel() {
        return this.label;
    }

    /**
     * Sets the label.
     * 
     * @param label the label to set
     */
    public void setLabel(String label) {
        this.label = label;
    }

    /*
     * (non-Javadoc)
     * 
     * @see org.talend.core.model.process.IProcess#getLastRunContext()
     */
    public IContext getLastRunContext() {
        return lastRunContext;
    }

    /*
     * (non-Javadoc)
     * 
     * @see org.talend.core.model.process.IProcess#setLastRunContext(org.talend.core.model.process.IContext)
     */
    public void setLastRunContext(IContext context) {
        this.lastRunContext = context;

    }

    /**
     * Getter for duplicate.
     * 
     * @return the duplicate
     */
    public boolean isDuplicate() {
        return this.duplicate;
    }

    /**
     * Sets the duplicate.
     * 
     * @param duplicate the duplicate to set
     */
    public void setDuplicate(boolean duplicate) {
        this.duplicate = duplicate;
    }

    public boolean isActivate() {
        return activate;
    }

    public void setActivate(boolean activate) {
        this.activate = activate;
    }

    /*
     * (non-Javadoc)
     * 
     * @see org.talend.designer.core.ui.editor.Element#getElementName()
     */
    @Override
    public String getElementName() {
        return name;
    }

    public String getRepositoryId() {
        return repositoryId;
    }

    public void setRepositoryId(String repositoryId) {
        this.repositoryId = repositoryId;
    }

    /*
     * (non-Javadoc)
     * 
     * @see org.talend.core.ui.ILastVersionChecker#setLastVersion(java.lang.Boolean)
     */
    public void setLastVersion(Boolean lastVersion) {
        this.lastVersion = lastVersion;
    }

    /**
     * Sets the contextManager.
     * 
     * @param contextManager the contextManager to set
     */
    public void setContextManager(IContextManager contextManager) {
        this.contextManager = contextManager;
    }

    public IContextManager getContextManager() {
        return contextManager;
    }

    @Override
    public String toString() {
        return "Process:" + getLabel(); //$NON-NLS-1$
    }

    /*
     * (non-Javadoc)
     * 
     * @see org.talend.core.model.temp.IXmlSerializable#getXmlStream()
     */
    public InputStream getXmlStream() {
        return new ByteArrayInputStream(content);
    }

    /*
     * (non-Javadoc)
     * 
     * @see org.talend.core.model.temp.IXmlSerializable#setXmlStream(java.io.InputStream)
     */
    public void setXmlStream(InputStream xmlStream) {
        ByteArrayOutputStream st = new ByteArrayOutputStream();

        int byteLu;
        try {
            while ((byteLu = xmlStream.read()) != -1) {
                st.write(byteLu);
            }
        } catch (IOException e) {
            // TODO SML Auto-generated catch block
            // e.printStackTrace();
            ExceptionHandler.process(e);
        } finally {
            try {
                xmlStream.close();
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
        this.content = st.toByteArray();
    }

    public byte[] getScreenshot() {
        return this.screenshot;
    }

    public void setScreenshot(byte[] imagedata) {
        this.screenshot = imagedata;
    }

    public List<NodeType> getUnloadedNode() {
        return this.unloadedNode;
    }

    /**
     * This function will take a unique name and update the list with the given name. This function should be private
     * only and should be called only when the xml file is loaded.
     * 
     * @param uniqueName
     */
    public void addUniqueNodeName(final String uniqueName) {
        if (!uniqueNodeNameList.contains(uniqueName)) {
            uniqueNodeNameList.add(uniqueName);
        }
    }

    public void removeUniqueNodeName(final String uniqueName) {
        if (uniqueName != null && !uniqueName.equals("")) { //$NON-NLS-1$
            uniqueNodeNameList.remove(uniqueName);
        }
    }

    public void addUniqueConnectionName(String uniqueConnectionName) {
        if (uniqueConnectionName != null) {
            if (checkValidConnectionName(uniqueConnectionName)) {
                uniqueConnectionNameList.add(uniqueConnectionName);
            } else {
                throw new IllegalArgumentException("The name of the connection is not valid: " + uniqueConnectionName); //$NON-NLS-1$
            }
        }
    }

    public void removeUniqueConnectionName(String uniqueConnectionName) {
        if (uniqueConnectionName != null) {
            uniqueConnectionNameList.remove(uniqueConnectionName);
        }
    }

    /**
     * Sets the processModified.
     * 
     * @param processModified the processModified to set
     */
    public void setProcessModified(boolean processModified) {
        this.processModified = processModified;
    }

    public String generateUniqueConnectionName(String baseName, String tableName) {
        if (baseName == null || tableName == null) {
            throw new IllegalArgumentException("baseName or tableName can't be null"); //$NON-NLS-1$
        }
        String uniqueName = baseName + 1;

        int counter = 1;
        boolean exists = true;
        String fullName = "";
        while (exists) {
            fullName = uniqueName + "_" + tableName;
            exists = !checkValidConnectionName(fullName);
            if (!exists) {
                break;
            }
            uniqueName = baseName + counter++;
        }
        return fullName;
    }

    // hshen
    // qli modified to fix the bug "7312".
    public boolean checkIgnoreCase(String connectionName) {
        if (connectionName.equals("")) {//$NON-NLS-1$
            return true;
        }
        if (uniqueConnectionNameList != null) {
            for (String value : uniqueConnectionNameList) {
                if (value.equalsIgnoreCase(connectionName)) {
                    return true;
                }
            }
        }

        return false;
    }

    /**
     * Check if the given name will be unique in the process. If another link already exists with that name, false will
     * be returned.
     * 
     * @param uniqueName
     * @return true if the name is unique
     */
    public boolean checkValidConnectionName(String connectionName) {
        return checkValidConnectionName(connectionName, true);
    }

    @Override
    public void setPropertyValue(String id, Object value) {
        if (id.equals(EParameterName.SCHEMA_TYPE.getName()) || id.equals(EParameterName.QUERYSTORE_TYPE.getName())
                || id.equals(EParameterName.PROPERTY_TYPE.getName())
                // || id.equals(JobSettingsConstants.getExtraParameterName(EParameterName.PROPERTY_TYPE.getName()))
                || id.equals(EParameterName.PROCESS_TYPE_PROCESS.getName())) {
            String updataComponentParamName = null;
            // if (JobSettingsConstants.isExtraParameter(id)) {
            // updataComponentParamName =
            // JobSettingsConstants.getExtraParameterName(EParameterName.UPDATE_COMPONENTS.getName());
            // } else {
            updataComponentParamName = EParameterName.UPDATE_COMPONENTS.getName();
            // }
            setPropertyValue(updataComponentParamName, Boolean.TRUE);
        }

        super.setPropertyValue(id, value);
    }

    /**
     * 
     * DOC yexiaowei Comment method "sortNodes".
     * 
     * @param nodes
     * @return
     */
    protected List<INode> sortNodes(List<INode> nodes) {

        if (nodes == null || nodes.size() <= 1) {
            return nodes;
        }

        List<INode> res = new ArrayList<INode>();

        List<List<INode>> mainStart = new ArrayList<List<INode>>();

        List<List<INode>> notMainStart = new ArrayList<List<INode>>();

        List<INode> starts = new ArrayList<INode>();

        for (INode node : nodes) {
            if (node.isStart() || node.isSubProcessStart()) {
                starts.add(node);
            }
        }

        for (INode node : starts) {
            List<INode> branch = new ArrayList<INode>();
            branch.add(node);
            findTargetAll(branch, node);
            if (node.isStart() && node.isSubProcessStart()) {
                mainStart.add(branch);
            } else {
                notMainStart.add(branch);
            }

        }

        // Must sort the mainStart first...
        List<List<INode>> tempStart = new ArrayList<List<INode>>();
        tempStart.addAll(mainStart);
        for (List<INode> preview : mainStart) {
            for (List<INode> now : mainStart) {
                if (!preview.equals(now) && now.contains(preview.get(0))) {
                    tempStart.remove(preview);
                    tempStart.add(tempStart.indexOf(now) + 1, preview);
                }
            }
        }

        for (List<INode> branch : tempStart) {
            for (INode n : branch) {
                if (!res.contains(n)) {
                    res.add(n);
                }
            }

            for (List<INode> ns : notMainStart) {

                for (INode node : ns) {
                    if (branch.contains(node)) {
                        for (INode nodeadd : ns) {
                            if (!res.contains(nodeadd)) {
                                res.add(nodeadd);
                            }
                            break;
                        }
                    }
                }

            }
        }
        return res;
    }

    private void findTargetAll(List<INode> res, INode current) {

        List conns = current.getOutgoingConnections();

        if (conns == null || conns.size() == 0) {
            return;
        } else {
            for (Object obj : conns) {
                IConnection con = (IConnection) obj;
                INode target = (INode) con.getTarget();
                if (!res.contains(target)) {
                    res.add(target);
                    findTargetAll(res, target);
                }
            }
        }
    }

    /*
     * (non-Javadoc)
     * 
     * @see org.talend.core.model.process.IProcess#getGeneratingNodes()
     */
    public List<? extends INode> getGeneratingNodes() {
        ICodeGenerationService generatorService; // retrieve service

        if (generatingProcess == null) {
            generatingProcess = generatorService.getDataProcess(this); // new DataProcess(this);
        }
        List<INode> generatedNodeList = generatorService.getNodeList(generatingProcess);

        List<INode> nodes = (List<INode>) getGraphicalNodes();
        List<INode> sortedFlow = sortNodes(nodes);
        if (sortedFlow.size() != nodes.size()) {
            sortedFlow = nodes;
        }
        generatorService.buildFromGraphicalProcess(generatingProcess, sortedFlow);
        generatedNodeList = generatorService.getNodeList(generatingProcess);
        processModified = false;
        return generatedNodeList;
    }

    /*
     * (non-Javadoc)
     * 
     * @see org.talend.core.model.process.IProcess#getGraphicalNodes()
     */
    public List<? extends INode> getGraphicalNodes() {
        return getNode();
    }

    /*
     * (non-Javadoc)
     * 
     * @see org.talend.designer.core.model.utils.emf.talendfile.ProcessType#getAuthor()
     */
    public String getAuthor() {
        // TODO Auto-generated method stub
        return null;
    }

    /*
     * (non-Javadoc)
     * 
     * @see org.talend.designer.core.model.utils.emf.talendfile.ProcessType#getComment()
     */
    public String getComment() {
        // TODO Auto-generated method stub
        return null;
    }

    /*
     * (non-Javadoc)
     * 
     * @see org.talend.designer.core.model.utils.emf.talendfile.ProcessType#getConnection()
     */
    public EList getConnection() {
        // TODO Auto-generated method stub
        return null;
    }

    /*
     * (non-Javadoc)
     * 
     * @see org.talend.designer.core.model.utils.emf.talendfile.ProcessType#getContext()
     */
    public EList getContext() {
        // TODO Auto-generated method stub
        return null;
    }

    /*
     * (non-Javadoc)
     * 
     * @see org.talend.designer.core.model.utils.emf.talendfile.ProcessType#getDefaultContext()
     */
    public String getDefaultContext() {
        // TODO Auto-generated method stub
        return null;
    }

    /*
     * (non-Javadoc)
     * 
     * @see org.talend.designer.core.model.utils.emf.talendfile.ProcessType#getDescription()
     */
    public String getDescription() {
        // TODO Auto-generated method stub
        return null;
    }

    /*
     * (non-Javadoc)
     * 
     * @see org.talend.designer.core.model.utils.emf.talendfile.ProcessType#getLogs()
     */
    public LogsType getLogs() {
        // TODO Auto-generated method stub
        return null;
    }

    /*
     * (non-Javadoc)
     * 
     * @see org.talend.designer.core.model.utils.emf.talendfile.ProcessType#getName()
     */
    public String getName() {
        // TODO Auto-generated method stub
        return null;
    }

    /*
     * (non-Javadoc)
     * 
     * @see org.talend.designer.core.model.utils.emf.talendfile.ProcessType#getNode()
     */
    public EList getNode() {
        // TODO Auto-generated method stub
        return null;
    }

    /*
     * (non-Javadoc)
     * 
     * @see org.talend.designer.core.model.utils.emf.talendfile.ProcessType#getNote()
     */
    public EList getNote() {
        // TODO Auto-generated method stub
        return null;
    }

    /*
     * (non-Javadoc)
     * 
     * @see org.talend.designer.core.model.utils.emf.talendfile.ProcessType#getParameters()
     */
    public ParametersType getParameters() {
        // TODO Auto-generated method stub
        return null;
    }

    /*
     * (non-Javadoc)
     * 
     * @see org.talend.designer.core.model.utils.emf.talendfile.ProcessType#getPurpose()
     */
    public String getPurpose() {
        // TODO Auto-generated method stub
        return null;
    }

    /*
     * (non-Javadoc)
     * 
     * @see org.talend.designer.core.model.utils.emf.talendfile.ProcessType#getRepositoryContextId()
     */
    public String getRepositoryContextId() {
        // TODO Auto-generated method stub
        return null;
    }

    /*
     * (non-Javadoc)
     * 
     * @see org.talend.designer.core.model.utils.emf.talendfile.ProcessType#getRequired()
     */
    public RequiredType getRequired() {
        // TODO Auto-generated method stub
        return null;
    }

    /*
     * (non-Javadoc)
     * 
     * @see org.talend.designer.core.model.utils.emf.talendfile.ProcessType#getRoutinesDependencies()
     */
    public EList getRoutinesDependencies() {
        // TODO Auto-generated method stub
        return null;
    }

    /*
     * (non-Javadoc)
     * 
     * @see org.talend.designer.core.model.utils.emf.talendfile.ProcessType#getStatus()
     */
    public String getStatus() {
        // TODO Auto-generated method stub
        return null;
    }

    /*
     * (non-Javadoc)
     * 
     * @see org.talend.designer.core.model.utils.emf.talendfile.ProcessType#getSubjob()
     */
    public EList getSubjob() {
        // TODO Auto-generated method stub
        return null;
    }

    /*
     * (non-Javadoc)
     * 
     * @see org.talend.designer.core.model.utils.emf.talendfile.ProcessType#setAuthor(java.lang.String)
     */
    public void setAuthor(String value) {
        // TODO Auto-generated method stub

    }

    /*
     * (non-Javadoc)
     * 
     * @see org.talend.designer.core.model.utils.emf.talendfile.ProcessType#setComment(java.lang.String)
     */
    public void setComment(String value) {
        // TODO Auto-generated method stub

    }

    /*
     * (non-Javadoc)
     * 
     * @see org.talend.designer.core.model.utils.emf.talendfile.ProcessType#setDefaultContext(java.lang.String)
     */
    public void setDefaultContext(String value) {
        // TODO Auto-generated method stub

    }

    /*
     * (non-Javadoc)
     * 
     * @see org.talend.designer.core.model.utils.emf.talendfile.ProcessType#setDescription(java.lang.String)
     */
    public void setDescription(String value) {
        // TODO Auto-generated method stub

    }

    /*
     * (non-Javadoc)
     * 
     * @see
     * org.talend.designer.core.model.utils.emf.talendfile.ProcessType#setLogs(org.talend.designer.core.model.utils.
     * emf.talendfile.LogsType)
     */
    public void setLogs(LogsType value) {
        // TODO Auto-generated method stub

    }

    /*
     * (non-Javadoc)
     * 
     * @see org.talend.designer.core.model.utils.emf.talendfile.ProcessType#setName(java.lang.String)
     */
    public void setName(String value) {
        // TODO Auto-generated method stub

    }

    /*
     * (non-Javadoc)
     * 
     * @see
     * org.talend.designer.core.model.utils.emf.talendfile.ProcessType#setParameters(org.talend.designer.core.model.
     * utils.emf.talendfile.ParametersType)
     */
    public void setParameters(ParametersType value) {
        // TODO Auto-generated method stub

    }

    /*
     * (non-Javadoc)
     * 
     * @see org.talend.designer.core.model.utils.emf.talendfile.ProcessType#setPurpose(java.lang.String)
     */
    public void setPurpose(String value) {
        // TODO Auto-generated method stub

    }

    /*
     * (non-Javadoc)
     * 
     * @see org.talend.designer.core.model.utils.emf.talendfile.ProcessType#setRepositoryContextId(java.lang.String)
     */
    public void setRepositoryContextId(String value) {
        // TODO Auto-generated method stub

    }

    /*
     * (non-Javadoc)
     * 
     * @see
     * org.talend.designer.core.model.utils.emf.talendfile.ProcessType#setRequired(org.talend.designer.core.model.utils
     * .emf.talendfile.RequiredType)
     */
    public void setRequired(RequiredType value) {
        // TODO Auto-generated method stub

    }

    /*
     * (non-Javadoc)
     * 
     * @see org.talend.designer.core.model.utils.emf.talendfile.ProcessType#setStatus(java.lang.String)
     */
    public void setStatus(String value) {
        // TODO Auto-generated method stub

    }

    /*
     * (non-Javadoc)
     * 
     * @see org.talend.core.model.process.IProcess#checkValidConnectionName(java.lang.String, boolean)
     */
    public boolean checkValidConnectionName(String connectionName, boolean checkExists) {
        // TODO Auto-generated method stub
        return false;
    }

    /*
     * (non-Javadoc)
     * 
     * @see org.talend.core.model.process.IProcess#disableRunJobView()
     */
    public boolean disableRunJobView() {
        // TODO Auto-generated method stub
        return false;
    }

    /*
     * (non-Javadoc)
     * 
     * @see org.talend.core.model.process.IProcess#generateUniqueConnectionName(java.lang.String)
     */
    public String generateUniqueConnectionName(String baseName) {
        // TODO Auto-generated method stub
        return null;
    }

    /*
     * (non-Javadoc)
     * 
     * @see org.talend.core.model.process.IProcess#getAllConnections(java.lang.String)
     */
    public IConnection[] getAllConnections(String filter) {
        // TODO Auto-generated method stub
        return null;
    }

    /*
     * (non-Javadoc)
     * 
     * @see org.talend.core.model.process.IProcess#getMergelinkOrder(org.talend.core.model.process.INode)
     */
    public int getMergelinkOrder(INode node) {
        // TODO Auto-generated method stub
        return 0;
    }

    /*
     * (non-Javadoc)
     * 
     * @see org.talend.core.model.process.IProcess#getNeededLibraries(boolean)
     */
    public Set<String> getNeededLibraries(boolean withChildrens) {
        // TODO Auto-generated method stub
        return null;
    }

    /*
     * (non-Javadoc)
     * 
     * @see org.talend.core.model.process.IProcess#getNodesOfType(java.lang.String)
     */
    public List<? extends INode> getNodesOfType(String componentName) {
        // TODO Auto-generated method stub
        return null;
    }

    /*
     * (non-Javadoc)
     * 
     * @see org.talend.core.model.process.IProcess#getNodesWithImport()
     */
    public List<INode> getNodesWithImport() {
        // TODO Auto-generated method stub
        return null;
    }

    /*
     * (non-Javadoc)
     * 
     * @see org.talend.core.model.process.IProcess#getOutputMetadataTable()
     */
    public IMetadataTable getOutputMetadataTable() {
        // TODO Auto-generated method stub
        return null;
    }

    /*
     * (non-Javadoc)
     * 
     * @see org.talend.core.model.process.IProcess#getProcessor()
     */
    public IProcessor getProcessor() {
        // TODO Auto-generated method stub
        return null;
    }

    /*
     * (non-Javadoc)
     * 
     * @see org.talend.core.model.process.IProcess#isNeedRegenerateCode()
     */
    public boolean isNeedRegenerateCode() {
        // TODO Auto-generated method stub
        return false;
    }

    /*
     * (non-Javadoc)
     * 
     * @see org.talend.core.model.process.IProcess#isThereLinkWithHash(org.talend.core.model.process.INode)
     */
    public boolean isThereLinkWithHash(INode node) {
        // TODO Auto-generated method stub
        return false;
    }

    /*
     * (non-Javadoc)
     * 
     * @see org.talend.core.model.process.IProcess#setNeedRegenerateCode(boolean)
     */
    public void setNeedRegenerateCode(boolean regenerateCode) {
        // TODO Auto-generated method stub

    }

    /*
     * (non-Javadoc)
     * 
     * @see org.talend.core.model.process.IProcess#setProcessor(org.talend.designer.runprocess.IProcessor)
     */
    public void setProcessor(IProcessor processor) {
        // TODO Auto-generated method stub

    }
}
