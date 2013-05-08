// ============================================================================
//
// Copyright (C) 2006-2013 Talend Inc. - www.talend.com
//
// This source code is available under agreement available at
// %InstallDIR%\features\org.talend.rcp.branding.%PRODUCTNAME%\%PRODUCTNAME%license.txt
//
// You should have received a copy of the agreement
// along with this program; if not, write to Talend SA
// 9 rue Pages 92150 Suresnes, France
//
// ============================================================================
package org.talend.designer.pigmap;

import java.util.ArrayList;
import java.util.List;

import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Shell;
import org.talend.commons.ui.runtime.exception.ExceptionHandler;
import org.talend.commons.ui.swt.cursor.CursorHelper;
import org.talend.core.model.components.IODataComponent;
import org.talend.core.model.components.IODataComponentContainer;
import org.talend.core.model.metadata.IMetadataTable;
import org.talend.core.model.process.EConnectionType;
import org.talend.core.model.process.HashConfiguration;
import org.talend.core.model.process.HashableColumn;
import org.talend.core.model.process.IComponentDocumentation;
import org.talend.core.model.process.IConnection;
import org.talend.core.model.process.IElementParameter;
import org.talend.core.model.process.IExternalData;
import org.talend.core.model.process.IHashConfiguration;
import org.talend.core.model.process.IHashableColumn;
import org.talend.core.model.process.IHashableInputConnections;
import org.talend.core.model.process.Problem;
import org.talend.core.model.process.node.MapperExternalNode;
import org.talend.core.model.utils.TalendTextUtils;
import org.talend.designer.core.model.utils.emf.talendfile.AbstractExternalData;
import org.talend.designer.pigmap.figures.tablesettings.JOIN_OPTIMIZATION;
import org.talend.designer.pigmap.model.emf.pigmap.InputTable;
import org.talend.designer.pigmap.model.emf.pigmap.OutputTable;
import org.talend.designer.pigmap.model.emf.pigmap.PigMapData;
import org.talend.designer.pigmap.model.emf.pigmap.PigmapFactory;
import org.talend.designer.pigmap.model.emf.pigmap.TableNode;
import org.talend.designer.pigmap.ui.expressionutil.PigMapExpressionManager;
import org.talend.designer.pigmap.util.PigMapUtil;

/**
 * 
 * DOC hcyi class global comment. Detailled comment
 */
public class PigMapComponent extends MapperExternalNode implements IHashableInputConnections {

    private AbstractExternalData emfMapData;

    private MapperMain mapperMain;

    private PigMapExpressionManager expressionManager;

    public PigMapComponent() {
        expressionManager = new PigMapExpressionManager();
    }

    @Override
    public boolean isGeneratedAsVirtualComponent() {
        return false;
    }

    @Override
    public int open(Display display) {
        Shell parentShell = display.getActiveShell();
        mapperMain = new MapperMain(this);
        if (parentShell != null) {
            CursorHelper.changeCursor(parentShell, SWT.CURSOR_WAIT);
        }
        Shell shell = null;
        try {
            shell = mapperMain.createUI(display);
        } finally {
            parentShell.setCursor(null);
        }
        while (shell != null && !shell.isDisposed()) {
            try {
                if (!display.readAndDispatch()) {
                    display.sleep();
                }
            } catch (Throwable e) {
                ExceptionHandler.process(e);
            }
        }

        return mapperMain.getMapperDialogResponse();
    }

    @Override
    public void initialize() {
    }

    @Override
    public int open(Composite parent) {
        return open(parent.getDisplay());
    }

    @Override
    public void setExternalData(IExternalData persistentData) {
        // TODO Auto-generated method stub
    }

    @Override
    public void renameInputConnection(String oldName, String newName) {
        PigMapData externalEmfData = (PigMapData) getExternalEmfData();
        for (InputTable inputTable : externalEmfData.getInputTables()) {
            if (inputTable.getName() != null && inputTable.getName().equals(oldName) && !oldName.equals(newName)) {
                inputTable.setName(newName);
                PigMapUtil.updateExpression(oldName, newName, externalEmfData, expressionManager);
            }
        }
    }

    @Override
    public void renameOutputConnection(String oldName, String newName) {

        PigMapData externalEmfData = (PigMapData) getExternalEmfData();
        for (OutputTable outputTable : externalEmfData.getOutputTables()) {
            if (outputTable.getName() != null && outputTable.getName().equals(oldName) && !oldName.equals(newName)) {
                outputTable.setName(newName);
            }
        }

    }

    @Override
    public IComponentDocumentation getComponentDocumentation(String componentName, String tempFolderPath) {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public IExternalData getTMapExternalData() {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    protected void renameMetadataColumnName(String conectionName, String oldColumnName, String newColumnName) {
    }

    @Override
    public void buildExternalData(AbstractExternalData abstractData) {
        if (abstractData instanceof PigMapData) {
            this.emfMapData = abstractData;
        }
    }

    @Override
    public AbstractExternalData getExternalEmfData() {
        if (this.emfMapData == null) {
            this.emfMapData = PigmapFactory.eINSTANCE.createPigMapData();
        }

        return this.emfMapData;
    }

    @Override
    public void setExternalEmfData(AbstractExternalData emfMapData) {
        this.emfMapData = emfMapData;
    }

    @Override
    public IHashConfiguration getHashConfiguration(String connectionName) {
        IHashConfiguration hashConfigurationForMapper = null;
        PigMapData externalData = (PigMapData) getExternalEmfData();
        List<InputTable> inputTables = externalData.getInputTables();
        List<IHashableColumn> hashableColumns = new ArrayList<IHashableColumn>();
        for (InputTable inputTable : inputTables) {
            if (inputTable.getName().equals(connectionName)) {
                List<TableNode> metadataTableEntries = inputTable.getNodes();
                if (metadataTableEntries != null) {
                    int metadataTableEntriesListSize = metadataTableEntries.size();
                    for (int i = 0; i < metadataTableEntriesListSize; i++) {
                        TableNode entry = metadataTableEntries.get(i);
                        if (entry.getExpression() != null && !entry.getExpression().trim().equals("")) { //$NON-NLS-1$
                            hashableColumns.add(new HashableColumn(entry.getName(), i));
                        }
                    }
                }

                JOIN_OPTIMIZATION joinOptimization = org.talend.designer.pigmap.figures.tablesettings.JOIN_OPTIMIZATION
                        .parse(inputTable.getJoinOptimization());
                if (joinOptimization == null) {
                    joinOptimization = org.talend.designer.pigmap.figures.tablesettings.JOIN_OPTIMIZATION.NONE;
                }

                IElementParameter tempFolderElem = getElementParameter("TEMPORARY_DATA_DIRECTORY"); //$NON-NLS-1$
                String tempFolder = null;
                if (tempFolderElem != null) {
                    tempFolder = (String) tempFolderElem.getValue();
                }
                if (("").equals(tempFolder)) {
                    tempFolder = (String) this.getProcess().getElementParameter("COMP_DEFAULT_FILE_DIR").getValue() + "/temp"; //$NON-NLS-1$ //$NON-NLS-2$
                    tempFolder = TalendTextUtils.addQuotes(tempFolder);
                }

                IElementParameter rowsBufferSizeElem = getElementParameter("ROWS_BUFFER_SIZE"); //$NON-NLS-1$
                String rowsBufferSize = null;
                if (rowsBufferSizeElem != null) {
                    rowsBufferSize = (String) rowsBufferSizeElem.getValue();
                }
                hashConfigurationForMapper = new HashConfiguration(hashableColumns, null, false, tempFolder, rowsBufferSize);
                break;
            }
        }

        return hashConfigurationForMapper;
    }

    @Override
    public List<String> checkNeededRoutines(List<String> possibleRoutines, String additionalString) {
        return null;
    }

    @Override
    public List<Problem> getProblems() {
        //
        return null;
    }

    private void initMapperMain() {
        if (mapperMain == null) {
            mapperMain = new MapperMain(this);
        }
    }

    @Override
    public void connectionStatusChanged(EConnectionType newValue, String connectionToApply) {
    }

    /*
     * (non-Javadoc)
     * 
     * @see org.talend.core.model.process.AbstractNode#removeInput(org.talend.core.model.process.IConnection)
     */
    @Override
    public void removeInput(IConnection connection) {
        PigMapData externalEmfData = (PigMapData) getExternalEmfData();
        InputTable toRemove = null;
        for (InputTable inputTable : externalEmfData.getInputTables()) {
            if (inputTable.getName() != null && inputTable.getName().equals(connection.getUniqueName())) {
                toRemove = inputTable;
                break;
            }
        }
        if (toRemove != null) {
            for (TableNode tableNode : toRemove.getNodes()) {
                PigMapUtil.detachNodeConnections(tableNode, externalEmfData);
            }
            PigMapUtil.detachFilterSource(toRemove, externalEmfData);
            externalEmfData.getInputTables().remove(toRemove);
        }
    }

    @Override
    public boolean isRunRefSubProcessAtStart(String connectionName) {
        PigMapData externalEmfData = (PigMapData) getExternalEmfData();
        List<InputTable> inputTables = new ArrayList<InputTable>(externalEmfData.getInputTables());
        for (InputTable table : inputTables) {
            if (table.getName().equals(connectionName)) {
                //
            }
        }
        return true;
    }

    public PigMapExpressionManager getExpressionManager() {
        return expressionManager;
    }

    @Override
    public boolean isReadOnly() {
        return super.isReadOnly() || this.getProcess().isReadOnly();
    }

    /*
     * (non-Javadoc)
     * 
     * @see org.talend.core.model.process.AbstractExternalNode#getIODataComponents()
     */
    @Override
    public IODataComponentContainer getIODataComponents() {
        // if metadata exist in IO + metadatalist, just update the instance of the one in IO.
        // check if there is some table added / deleted as well. (correct one is in metadatalist)
        List<IODataComponent> listOutput = super.getIODataComponents().getOuputs();
        List<IMetadataTable> metadataTableList = getMetadataList();
        for (IODataComponent ioComponent : listOutput) {
            String newLabel = ioComponent.getNewMetadataTable().getLabel();
            for (IMetadataTable table : metadataTableList) {
                if (newLabel != null && newLabel.equals(table.getLabel())) {
                    ioComponent.setNewMetadataTable(table);
                }
            }
        }
        // TODO Auto-generated method stub
        return super.getIODataComponents();
    }
}
