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
package org.talend.designer.dbmap.managers;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.eclipse.gef.commands.Command;
import org.eclipse.gef.commands.CommandStack;
import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.jface.viewers.TableViewer;
import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Table;
import org.eclipse.swt.widgets.TableItem;
import org.talend.commons.ui.swt.tableviewer.TableViewerCreator;
import org.talend.core.model.metadata.IMetadataColumn;
import org.talend.core.model.metadata.MetadataTable;
import org.talend.core.model.metadata.editor.MetadataTableEditor;
import org.talend.core.model.process.IConnection;
import org.talend.core.model.process.IElementParameter;
import org.talend.core.model.process.IProcess;
import org.talend.core.ui.metadata.editor.MetadataTableEditorView;
import org.talend.designer.core.model.components.EParameterName;
import org.talend.designer.dbmap.AbstractDbMapComponent;
import org.talend.designer.dbmap.ActivatorForDbMap;
import org.talend.designer.dbmap.i18n.Messages;
import org.talend.designer.dbmap.language.IDbLanguage;
import org.talend.designer.dbmap.model.table.AbstractDataMapTable;
import org.talend.designer.dbmap.model.table.InputTable;
import org.talend.designer.dbmap.model.table.OutputTable;
import org.talend.designer.dbmap.model.table.VarsTable;
import org.talend.designer.dbmap.model.tableentry.FilterTableEntry;
import org.talend.designer.dbmap.model.tableentry.IColumnEntry;
import org.talend.designer.dbmap.model.tableentry.ITableEntry;
import org.talend.designer.dbmap.model.tableentry.InputColumnTableEntry;
import org.talend.designer.dbmap.model.tableentry.OutputColumnTableEntry;
import org.talend.designer.dbmap.model.tableentry.TableEntryLocation;
import org.talend.designer.dbmap.model.tableentry.VarTableEntry;
import org.talend.designer.dbmap.oracle.Activator;
import org.talend.designer.dbmap.ui.automap.AutoMapper;
import org.talend.designer.dbmap.ui.commands.AddVarEntryCommand;
import org.talend.designer.dbmap.ui.dialog.AliasDialog;
import org.talend.designer.dbmap.ui.visualmap.TableEntryProperties;
import org.talend.designer.dbmap.ui.visualmap.link.IMapperLink;
import org.talend.designer.dbmap.ui.visualmap.link.LinkState;
import org.talend.designer.dbmap.ui.visualmap.table.DataMapTableView;
import org.talend.designer.dbmap.ui.visualmap.table.EntryState;
import org.talend.designer.dbmap.ui.visualmap.table.InputDataMapTableView;
import org.talend.designer.dbmap.ui.visualmap.table.OutputDataMapTableView;
import org.talend.designer.dbmap.ui.visualmap.zone.Zone;
import org.talend.designer.dbmap.ui.visualmap.zone.scrollable.TablesZoneView;
import org.talend.designer.dbmap.utils.DataMapExpressionParser;
import org.talend.repository.model.IRepositoryService;
import org.talend.repository.model.RepositoryConstants;

/**
 * DOC amaumont class global comment. Detailled comment <br/>
 * 
 * $Id: MapperManager.java 2077 2007-02-15 11:16:46Z amaumont $
 * 
 */
public class MapperManager {

    public static final String MAPPER_MODEL_DATA = "MAPPER_MODEL_DATA"; //$NON-NLS-1$

    private TableEntriesManager tableEntriesManager;

    private TableManager tableManager;

    private LinkManager linkManager;

    UIManager uiManager;

    private AbstractDbMapComponent mapperComponent;

    private CommandStack commandStack;

    private ProblemsManager problemsManager;

    public MapperManager(AbstractDbMapComponent mapperComponent) {
        super();
        this.mapperComponent = mapperComponent;
        tableEntriesManager = new TableEntriesManager(this);
        tableManager = new TableManager();
        linkManager = new LinkManager();
        uiManager = new UIManager(this, tableManager);
        problemsManager = new ProblemsManager(this);
    }

    public void addTablePair(DataMapTableView view, AbstractDataMapTable data) {
        tableManager.addTable(view, data);
        tableEntriesManager.addAll(data.getColumnEntries());
        if (data instanceof OutputTable) {
            tableEntriesManager.addAll(((OutputTable) data).getFilterEntries());
        }
    }

    /**
     * 
     * Remove the <code>DataMapTableView</code>-<code>DataMapTable</code> pair.
     * 
     * @param view
     */
    public void removeTablePair(DataMapTableView view) {
        AbstractDataMapTable dataTable = tableManager.getData(view);
        List<IColumnEntry> dataMapTableEntries = dataTable.getColumnEntries();
        tableEntriesManager.removeAll(dataMapTableEntries);
        if (dataTable instanceof OutputTable) {
            List<FilterTableEntry> constraintEntries = ((OutputTable) dataTable).getFilterEntries();
            tableEntriesManager.removeAll(constraintEntries);
        }
        tableManager.removeTable(view);
    }

    /**
     * 
     * Remove the <code>DataMapTable</code>-<code>DataMapTableView</code> pair.
     * 
     * @param view
     */
    public void removeTablePair(AbstractDataMapTable dataTable) {
        List<IColumnEntry> dataMapTableEntries = dataTable.getColumnEntries();
        tableEntriesManager.removeAll(dataMapTableEntries);
        if (dataTable instanceof OutputTable) {
            List<FilterTableEntry> constraintEntries = ((OutputTable) dataTable).getFilterEntries();
            tableEntriesManager.removeAll(constraintEntries);
        }
        tableManager.removeTable(dataTable);
    }

    /**
     * DOC amaumont Comment method "getDataMapTable".
     */
    public AbstractDataMapTable retrieveAbstractDataMapTable(DataMapTableView dataMapTableView) {
        return tableManager.getData(dataMapTableView);
    }

    /**
     * DOC amaumont Comment method "getDataMapTableView".
     */
    public DataMapTableView retrieveAbstractDataMapTableView(AbstractDataMapTable abstractDataMapTable) {
        return tableManager.getView(abstractDataMapTable);
    }

    public ITableEntry retrieveTableEntry(TableEntryLocation location) {
        return tableEntriesManager.retrieveTableEntry(location);
    }

    public DataMapTableView retrieveDataMapTableView(ITableEntry dataMapTableEntry) {
        return tableManager.getView(dataMapTableEntry.getParent());
    }

    /**
     * DOC amaumont Comment method "getDataMapTable".
     */
    public DataMapTableView retrieveDataMapTableView(TableEntryLocation names) {
        ITableEntry dataMapTableEntry = retrieveTableEntry(names);
        if (dataMapTableEntry == null) {
            return null;
        }
        return retrieveDataMapTableView(dataMapTableEntry);
    }

    public DataMapTableView retrieveDataMapTableView(Table swtTable) {
        return this.tableManager.getView(swtTable);
    }

    /**
     * DOC amaumont Comment method "getDataMapTable".
     */
    public AbstractDataMapTable retrieveAbstractDataMapTable(TableEntryLocation names) {
        ITableEntry dataMapTableEntry = retrieveTableEntry(names);
        if (dataMapTableEntry == null) {
            return null;
        }
        return dataMapTableEntry.getParent();
    }

    /**
     * DOC amaumont Comment method "clearLinks".
     */
    public void clearLinks() {
        linkManager.clearLinks();

    }

    /**
     * DOC amaumont Comment method "addLink".
     * 
     * @param link
     */
    public void addLink(IMapperLink link) {
        linkManager.addLink(link);
        changeDependentSourcesAndTargetEntriesState(link.getPointLinkDescriptor2().getTableEntry(), link, false);

        if (link.getPointLinkDescriptor2().getTableEntry() instanceof InputColumnTableEntry && linkManager.getCountOfInputLevels() > 4) {
            uiManager.enlargeLeftMarginForInputTables(linkManager.getCountOfInputLevels());
        }

    }

    /**
     * DOC amaumont Comment method "removeLink".
     * 
     * @param link
     */
    public void removeLink(IMapperLink link, ITableEntry entryCauseOfRemove) {
        changeDependentSourcesAndTargetEntriesState(entryCauseOfRemove, link, true);
        linkManager.removeLink(link);
    }

    /**
     * 
     * DOC amaumont Comment method "changeDependentSourcesAndTargetEntriesState".
     * 
     * @param entryCauseOfChange
     * @param currentLink
     * @param removedLink
     */
    private void changeDependentSourcesAndTargetEntriesState(ITableEntry entryCauseOfChange, IMapperLink currentLink, boolean removedLink) {

        boolean sourceIsCauseOfChange = false;
        if (currentLink.getPointLinkDescriptor1().getTableEntry() == entryCauseOfChange) {
            sourceIsCauseOfChange = true;
        } else if (currentLink.getPointLinkDescriptor2().getTableEntry() == entryCauseOfChange) {
            sourceIsCauseOfChange = false;
        } else {
            throw new IllegalArgumentException(Messages.getString("MapperManager.exceptionMessage.mustBeSourceOrTarget")); //$NON-NLS-1$
        }

        if (sourceIsCauseOfChange) {
            Set<IMapperLink> dependentLinks = linkManager.getLinksFromSource(entryCauseOfChange);
            for (IMapperLink dependentLink : dependentLinks) {
                changeDependentEntriesState(currentLink, dependentLink.getPointLinkDescriptor2().getTableEntry(), removedLink);
            }
        } else {
            Set<IMapperLink> dependentLinks = linkManager.getLinksFromTarget(entryCauseOfChange);
            for (IMapperLink dependentLink : dependentLinks) {
                changeDependentEntriesState(currentLink, dependentLink.getPointLinkDescriptor1().getTableEntry(), removedLink);
            }
        }
        changeDependentEntriesState(currentLink, entryCauseOfChange, removedLink);
    }

    /**
     * 
     * DOC amaumont Comment method "changeDependentEntriesState".
     * 
     * @param link
     * @param currentEntry
     * @param removedLink
     */
    private void changeDependentEntriesState(IMapperLink link, ITableEntry currentEntry, boolean removedLink) {
        Set<IMapperLink> dependentLinks = linkManager.getLinksFromSource(currentEntry);
        dependentLinks.addAll(linkManager.getLinksFromTarget(currentEntry));
        boolean hasSelectedLink = false;
        for (IMapperLink dependentLink : dependentLinks) {
            if (dependentLink.getState() == LinkState.SELECTED && dependentLink != link) {
                hasSelectedLink = true;
                break;
            }
        }
        if (!hasSelectedLink && link.getState() == LinkState.UNSELECTED || removedLink) {
            uiManager.setEntryState(this, EntryState.NONE, currentEntry);
        } else {
            uiManager.setEntryState(this, EntryState.HIGHLIGHT, currentEntry);
        }
    }

    /**
     * DOC amaumont Comment method "removeLink".
     * 
     * @param link
     */
    public Set<ITableEntry> getSourcesForTarget(ITableEntry dataMapTableEntry) {
        return linkManager.getSourcesForTarget(dataMapTableEntry);
    }

    /**
     * DOC amaumont Comment method "removeLink".
     * 
     * @param link
     */
    public Set<IMapperLink> getGraphicalLinksFromSource(ITableEntry dataMapTableEntry) {
        return linkManager.getLinksFromSource(dataMapTableEntry);
    }

    public Set<IMapperLink> getGraphicalLinksFromTarget(ITableEntry dataMapTableEntry) {
        return linkManager.getLinksFromTarget(dataMapTableEntry);
    }

    public List<IMapperLink> getLinks() {
        return linkManager.getLinks();
    }

    /**
     * DOC amaumont Comment method "retrieveTableFromTableEntry".
     * 
     * @param dataMapTableEntry
     * @return
     */
    public Table retrieveTable(ITableEntry dataMapTableEntry) {
        return tableEntriesManager.retrieveTable(dataMapTableEntry);
    }

    public TableItem retrieveTableItem(ITableEntry dataMapTableEntry) {
        return tableEntriesManager.retrieveTableItem(dataMapTableEntry);
    }

    public Collection<DataMapTableView> getTablesView() {
        return tableManager.getTablesView();
    }

    /**
     * Return all table data. Order is not assured. DOC amaumont Comment method "getTablesData".
     * 
     * @return
     */
    public Collection<AbstractDataMapTable> getTablesData() {
        return tableManager.getTablesData();
    }

    public List<InputTable> getInputTables() {
        return this.tableManager.getInputTables();
    }

    public List<OutputTable> getOutputTables() {
        return this.tableManager.getOutputTables();
    }

    public List<VarsTable> getVarsTables() {
        return this.tableManager.getVarsTables();
    }

    /**
     * DOC amaumont Comment method "renameProcessColumnName".
     * 
     * @param currentModifiedTableEntry
     * @param newColumnName
     * @param newColumnName2
     */
    public void changeColumnName(ITableEntry dataMapTableEntry, String previousColumnName, String newColumnName) {
        tableEntriesManager.renameEntryName(dataMapTableEntry, previousColumnName, newColumnName);
    }

    public void removeTableEntry(ITableEntry dataMapTableEntry) {
        tableEntriesManager.remove(dataMapTableEntry);
    }

    /**
     * DOC amaumont Comment method "removeLinksFrom".
     * 
     * @param dataMapTableEntry
     */
    public void removeLinksOf(ITableEntry dataMapTableEntry) {
        Set<IMapperLink> links = linkManager.getLinksFromSource(dataMapTableEntry);
        links.addAll(linkManager.getLinksFromTarget(dataMapTableEntry));
        for (IMapperLink link : links) {
            removeLink(link, dataMapTableEntry);
        }
    }

    public UIManager getUiManager() {
        return this.uiManager;
    }

    /**
     * DOC amaumont Comment method "getTableEntryProperties".
     * 
     * @param dataMapTableEntry
     * @return
     */
    public TableEntryProperties getTableEntryProperties(ITableEntry dataMapTableEntry) {
        return tableEntriesManager.getTableEntryProperties(dataMapTableEntry);
    }

    /**
     * This method is called when "addMetadataTableEditorEntry" is called (event on list of MetadataEditor) , so if you
     * want keep synchronisation between inputs/outputs DataMaps and MetadataEditors don't call this method.
     * 
     * @param dataMapTableView
     * @param metadataColumn, can be null if added in VarsTable
     * @param index
     */
    public IColumnEntry addNewColumnEntry(DataMapTableView dataMapTableView, IMetadataColumn metadataColumn, Integer index) {
        AbstractDataMapTable abstractDataMapTable = dataMapTableView.getDataMapTable();
        IColumnEntry dataMapTableEntry = null;
        if (dataMapTableView.getZone() == Zone.INPUTS) {
            dataMapTableEntry = new InputColumnTableEntry(abstractDataMapTable, metadataColumn);
        } else if (dataMapTableView.getZone() == Zone.OUTPUTS) {
            dataMapTableEntry = new OutputColumnTableEntry(abstractDataMapTable, metadataColumn);
        } else {
            throw new IllegalArgumentException(Messages.getString("MapperManager.exceptionMessage.useOtherSignature")); //$NON-NLS-1$
        }
        tableEntriesManager.addTableEntry(dataMapTableEntry, index);
        return dataMapTableEntry;
    }

    /**
     * This method is called when "addMetadataTableEditorEntry" is called (event on list of MetadataEditor) , so if you
     * want keep synchronisation between inputs/outputs DataMaps and MetadataEditors don't call this method.
     * 
     * For other uses such as add an entry to VarsTable or add entries to inputs or outputs DataMaps when
     * MetadataEditors are not active, call it.
     * 
     * @param dataMapTableView
     * @param index
     * @param type TODO
     * @param metadataColumn, can be null if added in VarsTable
     */
    public VarTableEntry addNewVarEntry(DataMapTableView dataMapTableView, String name, Integer index, String type) {
        AbstractDataMapTable abstractDataMapTable = dataMapTableView.getDataMapTable();
        VarTableEntry dataMapTableEntry = null;
        if (dataMapTableView.getZone() == Zone.VARS) {
            dataMapTableEntry = new VarTableEntry(abstractDataMapTable, name, null, type);
        } else {
            throw new IllegalArgumentException(Messages.getString("MapperManager.exceptionMessage.useOtherSignature")); //$NON-NLS-1$
        }

        AddVarEntryCommand varEntryCommand = new AddVarEntryCommand(tableEntriesManager, dataMapTableEntry, index);
        executeCommand(varEntryCommand);

        return dataMapTableEntry;
    }

    /**
     * DOC amaumont Comment method "addTableEntry".
     * 
     * @param dataMapTableEntry
     * @param index
     */
    public void addMetadataTableEditorEntry(MetadataTableEditorView metadataTableEditorView, IMetadataColumn metadataColumn, Integer index) {
        MetadataTableEditor metadataTableEditor = metadataTableEditorView.getMetadataTableEditor();
        metadataTableEditor.add(metadataColumn, index);
    }

    public FilterTableEntry addNewFilterEntry(DataMapTableView dataMapTableView, String name, Integer index) {
        AbstractDataMapTable abstractDataMapTable = dataMapTableView.getDataMapTable();
        FilterTableEntry constraintEntry = new FilterTableEntry(abstractDataMapTable, name, null);
        tableEntriesManager.addTableEntry(constraintEntry, index);
        return constraintEntry;
    }

    /**
     * DOC amaumont Comment method "addOutput".
     */
    public void addOutput() {

        String tableName = uiManager.openNewOutputCreationDialog();
        if (tableName == null) {
            return;
        }

        IProcess process = mapperComponent.getProcess();
        process.addUniqueConnectionName(tableName);

        MetadataTable metadataTable = new MetadataTable();
        metadataTable.setTableName(tableName);

        List<DataMapTableView> outputsTablesView = getUiManager().getOutputsTablesView();
        int sizeOutputsView = outputsTablesView.size();
        Control lastChild = null;
        if (sizeOutputsView - 1 >= 0) {
            lastChild = outputsTablesView.get(sizeOutputsView - 1);
        }

        AbstractDataMapTable abstractDataMapTable = new OutputTable(this, metadataTable, tableName);

        TablesZoneView tablesZoneViewOutputs = uiManager.getTablesZoneViewOutputs();
        DataMapTableView dataMapTableView = uiManager.createNewOutputTableView(lastChild, abstractDataMapTable, tablesZoneViewOutputs);
        tablesZoneViewOutputs.setSize(tablesZoneViewOutputs.computeSize(SWT.DEFAULT, SWT.DEFAULT));
        tablesZoneViewOutputs.layout();
        uiManager.moveOutputScrollBarZoneToMax();
        uiManager.refreshBackground(true, false);
        tablesZoneViewOutputs.layout();
        uiManager.selectDataMapTableView(dataMapTableView, true, false);
    }

    public void removeSelectedAliasInputTable() {
        InputDataMapTableView currentSelectedDataMapTableView = uiManager.getCurrentSelectedInputTableView();

        if (currentSelectedDataMapTableView != null) {
            String tableName = currentSelectedDataMapTableView.getDataMapTable().getName();
            if (MessageDialog.openConfirm(currentSelectedDataMapTableView.getShell(), Messages
                    .getString("MapperManager.removeOutputTableTitle"), //$NON-NLS-1$
                    Messages.getString("MapperManager.removeOutputTableTitleMessage") + tableName + "' ?")) { //$NON-NLS-1$ //$NON-NLS-2$
                IProcess process = mapperComponent.getProcess();
                uiManager.removeInputTableView(currentSelectedDataMapTableView);
                uiManager.updateToolbarButtonsStates(Zone.OUTPUTS);
                process.removeUniqueConnectionName(currentSelectedDataMapTableView.getDataMapTable().getName());
            }
        }
    }

    public void removeSelectedOutputTable() {
        OutputDataMapTableView currentSelectedDataMapTableView = uiManager.getCurrentSelectedOutputTableView();

        if (currentSelectedDataMapTableView != null) {
            String tableName = currentSelectedDataMapTableView.getDataMapTable().getName();
            if (MessageDialog.openConfirm(currentSelectedDataMapTableView.getShell(), Messages
                    .getString("MapperManager.removeOutputTableTitle"), //$NON-NLS-1$
                    Messages.getString("MapperManager.removeOutputTableTitleMessage") + tableName + "' ?")) { //$NON-NLS-1$ //$NON-NLS-2$
                IProcess process = mapperComponent.getProcess();
                uiManager.removeOutputTableView(currentSelectedDataMapTableView);
                uiManager.updateToolbarButtonsStates(Zone.OUTPUTS);
                process.removeUniqueConnectionName(currentSelectedDataMapTableView.getDataMapTable().getName());
            }
        }
    }

    /**
     * DOC amaumont Comment method "isTableOfInputMetadataEditor".
     * 
     * @param table
     * @return
     */
    public boolean isTableOfInputMetadataEditor(Table table) {
        MetadataTableEditorView inputEditorView = uiManager.getInputMetaEditorView();
        Table tableEditorView = inputEditorView.getTable();
        return tableEditorView == table;
    }

    /**
     * DOC amaumont Comment method "isTableOfOutputMetadataEditor".
     * 
     * @param table
     * @return
     */
    public boolean isTableOfOutputMetadataEditor(Table table) {
        MetadataTableEditorView outputEditorView = uiManager.getOutputMetaEditorView();
        Table tableEditorView = outputEditorView.getTable();
        return tableEditorView == table;
    }

    public TableEntryLocation findUniqueLocation(final TableEntryLocation proposedLocation, String[] columnsBeingCreated) {
        TableEntryLocation tableEntryLocation = new TableEntryLocation(proposedLocation);
        int counter = 1;
        boolean exists = true;
        while (exists) {
            exists = retrieveTableEntry(tableEntryLocation) != null;
            if (!exists) {
                for (int i = 0; i < columnsBeingCreated.length; i++) {
                    String columnBeingCreated = columnsBeingCreated[i];
                    if (columnBeingCreated.equals(tableEntryLocation.columnName)) {
                        exists = true;
                        break;
                    }
                }
            }
            if (!exists) {
                break;
            }
            tableEntryLocation.columnName = proposedLocation.columnName + "_" + counter++; //$NON-NLS-1$
        }
        return tableEntryLocation;
    }

    /**
     * DOC amaumont Comment method "orderLinks".
     */
    public void orderLinks() {
        linkManager.orderLinks();
    }

    /**
     * DOC amaumont Comment method "changeEntryExpression".
     * 
     * @param currentEntry
     * @param text
     */
    public void changeEntryExpression(ITableEntry currentEntry, String text) {
        currentEntry.setExpression(text);
        getProblemsManager().checkProblemsForTableEntry(currentEntry, true);
        DataMapTableView dataMapTableView = retrieveDataMapTableView(currentEntry);
        TableViewer tableViewer = null;
        if (currentEntry instanceof IColumnEntry) {
            tableViewer = dataMapTableView.getTableViewerCreatorForColumns().getTableViewer();
        } else if (currentEntry instanceof FilterTableEntry) {
            tableViewer = dataMapTableView.getTableViewerCreatorForFilters().getTableViewer();
        }
        if (currentEntry.getProblems() != null) {
            tableViewer.getTable().deselectAll();
        }
        tableViewer.refresh(currentEntry);

        uiManager.parseNewExpression(text, currentEntry, false);
    }

    public AbstractDbMapComponent getComponent() {
        return this.mapperComponent;
    }

    /**
     * DOC amaumont Comment method "checkLocationIsValid".
     * 
     * @param couple
     * @param currentModifiedITableEntry
     * @return
     */
    public boolean checkSourceLocationIsValid(TableEntryLocation locationSource, ITableEntry entryTarget) {
        return checkSourceLocationIsValid(retrieveTableEntry(locationSource), entryTarget);
    }

    public boolean checkSourceLocationIsValid(ITableEntry entrySource, ITableEntry entryTarget) {

        if (entrySource instanceof VarTableEntry && entrySource.getParent() == entryTarget.getParent()) {
            List<IColumnEntry> columnEntries = entrySource.getParent().getColumnEntries();
            if (columnEntries.indexOf(entrySource) < columnEntries.indexOf(entryTarget)) {
                return true;
            }
        } else if (entrySource instanceof InputColumnTableEntry && entryTarget instanceof InputColumnTableEntry
                && entrySource.getParent() != entryTarget.getParent()) {
            // List<InputTable> inputTables = getInputTables();
            // int indexTableSource = inputTables.indexOf(entrySource.getParent());
            // int indexTableTarget = inputTables.indexOf(entryTarget.getParent());
            // if (indexTableSource < indexTableTarget) {
            return true;
            // }
        } else if (entryTarget instanceof VarTableEntry || entryTarget instanceof OutputColumnTableEntry
                || entryTarget instanceof FilterTableEntry) {
            if (entrySource instanceof InputColumnTableEntry || entrySource instanceof VarTableEntry) {
                return true;
            }
        }
        return false;
    }

    /**
     * DOC amaumont Comment method "getPreviewPath".
     * 
     * @return
     */
    public String getPreviewFilePath() {
        IRepositoryService service = Activator.getDefault().getRepositoryService();
        return service.getPathFileName(RepositoryConstants.IMG_DIRECTORY, getPreviewFileName()).toString();
    }

    /**
     * DOC amaumont Comment method "getPreviewFileName".
     * 
     * @return
     */
    private String getPreviewFileName() {
        return mapperComponent.getProcess().getId() + "-" + mapperComponent.getUniqueName() + "-" + EParameterName.PREVIEW.getName() //$NON-NLS-1$ //$NON-NLS-2$
                + ".bmp"; //$NON-NLS-1$
    }

    public void updateEmfParameters(String... parametersToUpdate) {

        HashSet<String> hParametersToUpdate = new HashSet<String>();
        for (int i = 0; i < parametersToUpdate.length; i++) {
            hParametersToUpdate.add(parametersToUpdate[i]);
        }

        List<? extends IElementParameter> elementParameters = mapperComponent.getElementParameters();
        for (IElementParameter parameter : elementParameters) {
            if (hParametersToUpdate.contains(parameter.getName())) {
                // set preview path to PREVIEW parameter
                if (EParameterName.PREVIEW.getName().equals(parameter.getName())) {
                    String previewFileName = getPreviewFileName();
                    parameter.setValue(previewFileName == null ? "" : previewFileName); //$NON-NLS-1$
                }
            }
        }
    }

    public Object getElementParameterValue(String parameterName) {

        List<? extends IElementParameter> elementParameters = mapperComponent.getElementParameters();
        for (IElementParameter parameter : elementParameters) {
            if (parameter.getName().equals(parameterName)) {
                return parameter.getValue();
            }
        }
        return null;
    }

    /**
     * DOC amaumont Comment method "replacePreviousLocationInAllExpressions".
     */
    public void replacePreviousLocationInAllExpressions(final TableEntryLocation previousLocation, final TableEntryLocation newLocation) {

        DataMapExpressionParser dataMapExpressionParser = new DataMapExpressionParser(getCurrentLanguage());
        Collection<AbstractDataMapTable> tablesData = getTablesData();
        for (AbstractDataMapTable table : tablesData) {
            List<IColumnEntry> columnEntries = table.getColumnEntries();
            for (IColumnEntry entry : columnEntries) {
                replaceLocation(previousLocation, newLocation, dataMapExpressionParser, table, entry);
            }
            if (table instanceof OutputTable) {
                List<FilterTableEntry> constraintEntries = ((OutputTable) table).getFilterEntries();
                for (FilterTableEntry entry : constraintEntries) {
                    replaceLocation(previousLocation, newLocation, dataMapExpressionParser, table, entry);
                }
            }

        }
        uiManager.refreshBackground(false, false);
    }

    /**
     * DOC amaumont Comment method "getCurrentLanguage".
     * 
     * @return
     */
    public IDbLanguage getCurrentLanguage() {
        return getComponent().getGenerationManager().getLanguage();
    }

    /**
     * 
     * DOC amaumont Comment method "replaceLocation".
     * 
     * @param previousLocation
     * @param newLocation
     * @param dataMapExpressionParser
     * @param table
     * @param entry
     * @return true if expression of entry has changed
     */
    private boolean replaceLocation(final TableEntryLocation previousLocation, final TableEntryLocation newLocation,
            DataMapExpressionParser dataMapExpressionParser, AbstractDataMapTable table, ITableEntry entry) {
        boolean expressionHasChanged = false;
        String currentExpression = entry.getExpression();
        TableEntryLocation[] tableEntryLocations = dataMapExpressionParser.parseTableEntryLocations(currentExpression);
        // loop on all locations of current expression
        for (int i = 0; i < tableEntryLocations.length; i++) {
            TableEntryLocation currentLocation = tableEntryLocations[i];
            if (currentLocation.equals(previousLocation)) {
                currentExpression = dataMapExpressionParser.replaceLocation(currentExpression, previousLocation, newLocation);
                expressionHasChanged = true;
            }
        }
        if (expressionHasChanged) {
            entry.setExpression(currentExpression);
            DataMapTableView dataMapTableView = retrieveAbstractDataMapTableView(table);
            TableViewerCreator tableViewerCreator = null;
            if (entry instanceof IColumnEntry) {
                tableViewerCreator = dataMapTableView.getTableViewerCreatorForColumns();
            } else if (entry instanceof FilterTableEntry) {
                tableViewerCreator = dataMapTableView.getTableViewerCreatorForFilters();
            }
            tableViewerCreator.getTableViewer().refresh(entry);
            uiManager.parseExpression(currentExpression, entry, false, true, false);
            return true;
        }
        return false;
    }

    /**
     * @return
     * @see org.talend.designer.dbmap.managers.LinkManager#getCurrentNumberLinks()
     */
    public int getCurrentNumberLinks() {
        return this.linkManager.getCurrentNumberLinks();
    }

    /**
     * @return
     * @see org.talend.designer.dbmap.ui.MapperUI#getCommandStack()
     */
    public CommandStack getCommandStack() {
        return this.commandStack;
    }

    /**
     * Sets the commandStack.
     * 
     * @param commandStack the commandStack to set
     */
    public void setCommandStack(CommandStack commandStack) {
        this.commandStack = commandStack;
    }

    public void executeCommand(Command command) {
        if (this.commandStack != null) {
            this.commandStack.execute(command);
        } else {
            command.execute();
        }
    }

    /**
     * DOC amaumont Comment method "checkEntryHasValidKey".
     * 
     * @param inputEntry
     */
    public boolean checkEntryHasInvalidCheckedKey(InputColumnTableEntry inputEntry) {
        return inputEntry.getMetadataColumn().isKey() && checkEntryHasEmptyExpression(inputEntry);
    }

    /**
     * DOC amaumont Comment method "checkEntryHasValidKey".
     * 
     * @param inputEntry
     */
    public boolean checkEntryHasInvalidUncheckedKey(InputColumnTableEntry inputEntry) {
        return !inputEntry.getMetadataColumn().isKey() && inputEntry.getExpression() != null
                && inputEntry.getExpression().trim().length() > 0;
    }

    public boolean checkEntryHasEmptyExpression(ITableEntry entry) {
        return entry.getExpression() == null || entry.getExpression().trim().length() == 0;
    }

    /**
     * DOC amaumont Comment method "mapAutomaticallly".
     */
    public void mapAutomaticallly() {
        AutoMapper autoMapper = new AutoMapper(this);
        autoMapper.map();
    }

    /**
     * Getter for problemsManager.
     * 
     * @return the problemsManager
     */
    public ProblemsManager getProblemsManager() {
        return this.problemsManager;
    }

    /**
     * DOC amaumont Comment method "addAlias".
     */
    public void addAliasForInputTable() {

        ArrayList<String> tables = new ArrayList<String>();
        List<IConnection> incomingConnections = (List<IConnection>) getComponent().getIncomingConnections();
        for (IConnection connection : incomingConnections) {
            tables.add(connection.getName());
        }

        AliasDialog aliasDialog = new AliasDialog(this, tables.toArray(new String[0]));
        if (!aliasDialog.open()) {
            return;
        }

        incomingConnections = (List<IConnection>) getComponent().getIncomingConnections();
        IConnection connectionFound = null;
        for (IConnection connection : incomingConnections) {
            if (connection.getName().equals(aliasDialog.getTableName())) {
                connectionFound = connection;
                break;
            }
        }

        List<DataMapTableView> inputsTablesView = getUiManager().getInputsTablesView();
        int sizeOutputsView = inputsTablesView.size();
        Control lastChild = null;
        if (sizeOutputsView - 1 >= 0) {
            lastChild = inputsTablesView.get(sizeOutputsView - 1);
        }

        InputTable inputTable = new InputTable(this, connectionFound.getMetadataTable(), aliasDialog.getAlias());
        inputTable.setAlias(aliasDialog.getAlias());
        inputTable.setTableName(aliasDialog.getTableName());
        inputTable.initFromExternalData(null);

        TablesZoneView tablesZoneViewInputs = uiManager.getTablesZoneViewInputs();
        DataMapTableView dataMapTableView = uiManager.createNewInputTableView(lastChild, inputTable, tablesZoneViewInputs);
        tablesZoneViewInputs.setSize(tablesZoneViewInputs.computeSize(SWT.DEFAULT, SWT.DEFAULT));
        tablesZoneViewInputs.layout(true, true);
        uiManager.moveInputScrollBarZoneToMax();
        uiManager.refreshBackground(true, false);
        tablesZoneViewInputs.layout();
        uiManager.selectDataMapTableView(dataMapTableView, true, false);

    }

}
