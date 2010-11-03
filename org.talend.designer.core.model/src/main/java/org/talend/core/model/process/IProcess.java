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
package org.talend.core.model.process;

import java.util.List;
import java.util.Set;

import org.talend.core.model.metadata.IMetadataTable;
import org.talend.designer.runprocess.IProcessor;

/**
 * DOC nrousseau class global comment. Detailled comment <br/>
 * 
 * $Id: IProcess.java 38013 2010-03-05 14:21:59Z mhirt $
 * 
 */
public interface IProcess extends IElement {

    public static final String SCREEN_OFFSET_X = "SCREEN_OFFSET_X"; //$NON-NLS-1$

    public static final String SCREEN_OFFSET_Y = "SCREEN_OFFSET_Y"; //$NON-NLS-1$

    public String getLabel();

    public String getVersion();

    public String getId();

    // list of nodes that are in the designer
    public List<? extends INode> getGraphicalNodes();

    // list of nodes that will be used to generated the code
    // this list is slightly different from the designer nodes
    public List<? extends INode> getGeneratingNodes();

    public String generateUniqueConnectionName(String baseName);

    public void addUniqueConnectionName(String uniqueConnectionName);

    public void removeUniqueConnectionName(String uniqueConnectionName);

    public boolean checkValidConnectionName(String connectionName);

    public boolean checkValidConnectionName(String connectionName, boolean checkExists);

    public IContextManager getContextManager();

    public List<? extends INode> getNodesOfType(String componentName);

    public void setProcessor(IProcessor processor);

    public IProcessor getProcessor();

    /**
     * Comment method "getAllConnections".
     * 
     * @param filter only return the filter matched connections
     * @return
     */
    public IConnection[] getAllConnections(String filter);

    public Set<String> getNeededLibraries(boolean withChildrens);

    public int getMergelinkOrder(final INode node);

    public boolean isThereLinkWithHash(final INode node);

    boolean disableRunJobView();

    public List<INode> getNodesWithImport();

    public IContext getLastRunContext();

    public void setLastRunContext(IContext context);

    /**
     * if need to regenerate the code for the current process.
     * 
     * @return
     */
    public boolean isNeedRegenerateCode();

    public void setNeedRegenerateCode(boolean regenerateCode);

    public IMetadataTable getOutputMetadataTable();
}
