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
package org.talend.designer.dbmap.ui.visualmap.table;

import java.util.List;

import org.eclipse.jface.viewers.ComboBoxCellEditor;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.jface.viewers.TextCellEditor;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.events.SelectionListener;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Table;
import org.eclipse.swt.widgets.ToolItem;
import org.talend.commons.ui.image.EImage;
import org.talend.commons.ui.image.ImageProvider;
import org.talend.commons.ui.swt.advanced.dataeditor.commands.ExtendedTableMoveCommand;
import org.talend.commons.ui.swt.extended.table.AbstractExtendedTableViewer;
import org.talend.commons.ui.swt.tableviewer.TableViewerCreator;
import org.talend.commons.ui.swt.tableviewer.TableViewerCreatorColumn;
import org.talend.commons.ui.swt.tableviewer.TableViewerCreator.CELL_EDITOR_STATE;
import org.talend.commons.ui.swt.tableviewer.behavior.CellEditorValueAdapter;
import org.talend.commons.ui.swt.tableviewer.celleditor.DialogErrorForCellEditorListener;
import org.talend.commons.ui.swt.tableviewer.data.ModifiedObjectInfo;
import org.talend.commons.ui.swt.tableviewer.selection.ILineSelectionListener;
import org.talend.commons.ui.swt.tableviewer.selection.LineSelectionEvent;
import org.talend.commons.ui.swt.tableviewer.tableeditor.CheckboxTableEditorContent;
import org.talend.commons.utils.data.bean.IBeanPropertyAccessors;
import org.talend.commons.utils.data.list.IListenableListListener;
import org.talend.commons.utils.data.list.ListenableListEvent;
import org.talend.core.language.ECodeLanguage;
import org.talend.core.model.metadata.MetadataTalendType;
import org.talend.core.model.metadata.types.JavaTypesManager;
import org.talend.designer.core.ui.celleditor.JavaTypeComboValueAdapter;
import org.talend.designer.dbmap.i18n.Messages;
import org.talend.designer.dbmap.managers.MapperManager;
import org.talend.designer.dbmap.managers.UIManager;
import org.talend.designer.dbmap.model.table.AbstractDataMapTable;
import org.talend.designer.dbmap.model.table.VarsTable;
import org.talend.designer.dbmap.model.tableentry.ITableEntry;
import org.talend.designer.dbmap.model.tableentry.VarTableEntry;
import org.talend.designer.dbmap.ui.visualmap.zone.Zone;

/**
 * DOC amaumont class global comment. Detailled comment <br/>
 * 
 * $Id: VarsDataMapTableView.java 2010 2007-02-12 13:18:38Z amaumont $
 * 
 */
public class VarsDataMapTableView extends DataMapTableView {

    protected ToolItem removeEntryItem;

    protected ToolItem moveUpEntryItem;

    protected ToolItem moveDownEntryItem;

    public VarsDataMapTableView(Composite parent, int style, AbstractDataMapTable abstractDataMapTable, MapperManager mapperManager) {
        super(parent, style, abstractDataMapTable, mapperManager);
    }

    @Override
    protected void addListeners() {
        super.addListeners();
        tableViewerCreatorForColumns.getSelectionHelper().addAfterSelectionListener(new ILineSelectionListener() {

            public void handle(LineSelectionEvent e) {
                boolean atLeastOneItemIsSelected = tableViewerCreatorForColumns.getTable().getSelectionCount() > 0;
                removeEntryItem.setEnabled(atLeastOneItemIsSelected);
                moveUpEntryItem.setEnabled(atLeastOneItemIsSelected);
                moveDownEntryItem.setEnabled(atLeastOneItemIsSelected);
            }

        });

        getExtendedTableViewerForColumns().getExtendedTableModel().addAfterOperationListListener(new IListenableListListener() {

            public void handleEvent(ListenableListEvent event) {
                if (event.type == ListenableListEvent.TYPE.SWAPED) {
                    DataMapTableView varsDataMapTableView = mapperManager.retrieveDataMapTableView(getExtendedTableViewerForColumns()
                            .getTable());
                    UIManager uiManager = mapperManager.getUiManager();
                    uiManager.parseAllExpressions(varsDataMapTableView, false);
                    mapperManager.getProblemsManager().checkProblemsForAllEntries(varsDataMapTableView, true);
                    uiManager.refreshBackground(true, false);
                    List<ITableEntry> list = uiManager.extractSelectedTableEntries(varsDataMapTableView.getTableViewerCreatorForColumns()
                            .getTableViewer().getSelection());

                    uiManager.selectLinks(varsDataMapTableView, list, false, false);
                }
            }

        });

    }

    @Override
    public void initColumnsOfTableColumns(final TableViewerCreator tableViewerCreatorForColumns) {

        TableViewerCreatorColumn column = new TableViewerCreatorColumn(tableViewerCreatorForColumns);
        column.setTitle(Messages.getString("VarsDataMapTableView.columnTitle.expression")); //$NON-NLS-1$
        column.setId(DataMapTableView.ID_EXPRESSION_COLUMN);
        column.setBeanPropertyAccessors(new IBeanPropertyAccessors<VarTableEntry, String>() {

            public String get(VarTableEntry bean) {
                return bean.getExpression();
            }

            public void set(VarTableEntry bean, String value) {
                bean.setExpression(value);
                mapperManager.getProblemsManager().checkProblemsForTableEntry(bean, true);
            }

        });
        column.setModifiable(true);
        column.setDefaultInternalValue(""); //$NON-NLS-1$
        createExpressionCellEditor(tableViewerCreatorForColumns, column, new Zone[] { Zone.INPUTS, Zone.VARS }, false);
        column.setWeight(40);

        column = new TableViewerCreatorColumn(tableViewerCreatorForColumns);
        column.setTitle(Messages.getString("VarsDataMapTableView.columnTitle.variable")); //$NON-NLS-1$
        column.setId(DataMapTableView.ID_NAME_COLUMN);
        column.setBeanPropertyAccessors(new IBeanPropertyAccessors<VarTableEntry, String>() {

            public String get(VarTableEntry bean) {
                return bean.getName();
            }

            public void set(VarTableEntry bean, String value) {
                bean.setName(value);
            }

        });
        column.setModifiable(true);
        column.setWeight(25);
        
        final TextCellEditor cellEditor = new TextCellEditor(tableViewerCreatorForColumns.getTable());
        cellEditor.addListener(new DialogErrorForCellEditorListener(cellEditor, column) {

            @Override
            public void newValidValueTyped(int itemIndex, Object previousValue, Object newValue, CELL_EDITOR_STATE state) {
                if (state == CELL_EDITOR_STATE.APPLYING) {
                    ModifiedObjectInfo modifiedObjectInfo = tableViewerCreatorForColumns.getModifiedObjectInfo();
                    String originalValue = (String) modifiedObjectInfo.getOriginalPropertyBeanValue();
                    Object currentModifiedBean = modifiedObjectInfo.getCurrentModifiedBean();
                    mapperManager.getUiManager().processColumnNameChanged(originalValue.toString(), newValue.toString(),
                            VarsDataMapTableView.this, (ITableEntry) currentModifiedBean);
                }
            }

            @Override
            public String validateValue(String newValue, int beanPosition) {
                return ((VarsTable) getDataMapTable()).validateColumnName(newValue, beanPosition);
            }

        });
        column.setCellEditor(cellEditor);

    }

    /*
     * (non-Javadoc)
     * 
     * @see org.talend.designer.dbmap.ui.visualmap.table.DataMapTableView#initTableConstraints()
     */
    @Override
    protected void initTableFilters(Composite parent) {
        // no table constraint
    }

    /*
     * (non-Javadoc)
     * 
     * @see org.talend.designer.dbmap.ui.visualmap.table.DataMapTableView#addEntriesActionsComponents()
     */
    @Override
    protected boolean addToolItems() {
        createToolItems();
        return true;
    }

    /*
     * (non-Javadoc)
     * 
     * @see org.talend.designer.dbmap.ui.visualmap.table.DataMapTableView#getZone()
     */
    @Override
    public Zone getZone() {
        return Zone.VARS;
    }

    @Override
    public void unselectAllColumnEntries() {
        super.unselectAllColumnEntries();
        if (removeEntryItem != null) {
            removeEntryItem.setEnabled(false);
            moveUpEntryItem.setEnabled(false);
            moveDownEntryItem.setEnabled(false);
        }
    }

    /*
     * (non-Javadoc)
     * 
     * @see org.talend.designer.dbmap.ui.visualmap.table.DataMapTableView#toolbarNeededToBeRightStyle()
     */
    @Override
    public boolean toolbarNeedToHaveRightStyle() {
        return false;
    }

    protected void createToolItems() {

        // /////////////////////////////////////////////////////////////////
        ToolItem addEntryItem = new ToolItem(toolBarActions, SWT.PUSH);
        addEntryItem.setToolTipText(Messages.getString("VarsDataMapTableView.entryItemTooltip.addVariable")); //$NON-NLS-1$
        addEntryItem.setImage(org.talend.commons.ui.image.ImageProvider.getImage(org.talend.commons.ui.image.ImageProvider
                .getImageDesc(EImage.ADD_ICON)));

        addEntryItem.addSelectionListener(new SelectionListener() {

            public void widgetDefaultSelected(SelectionEvent e) {
            }

            public void widgetSelected(SelectionEvent e) {
                Table table = getExtendedTableViewerForColumns().getTable();

                int[] indices = table.getSelectionIndices();
                int indexInsert = table.getItemCount();
                if (indices.length > 0) {
                    indexInsert = indices[indices.length - 1] + 1;
                }
                AbstractDataMapTable dataMapTable = VarsDataMapTableView.this.getDataMapTable();
                String varName = null;
                if (dataMapTable instanceof VarsTable) {
                    varName = ((VarsTable) dataMapTable).findUniqueColumnName("var"); //$NON-NLS-1$
                } else {
                    throw new UnsupportedOperationException(Messages.getString("VarsDataMapTableView.exceptionMessage.caseNotFound")); //$NON-NLS-1$
                }
                mapperManager.addNewVarEntry(VarsDataMapTableView.this, varName, indexInsert, "String"); //$NON-NLS-1$
                VarsDataMapTableView.this.changeSize(VarsDataMapTableView.this.getPreferredSize(true, true, false), true, true);
                changeMinimizeState(false);
                tableViewerCreatorForColumns.getTableViewer().refresh();
                mapperManager.getUiManager().refreshBackground(true, false);
                table.setSelection(indexInsert);
                removeEntryItem.setEnabled(true);
                moveUpEntryItem.setEnabled(true);
                moveDownEntryItem.setEnabled(true);
            }

        });
        // /////////////////////////////////////////////////////////////////

        // /////////////////////////////////////////////////////////////////
        removeEntryItem = new ToolItem(toolBarActions, SWT.PUSH);
        removeEntryItem.setEnabled(false);
        removeEntryItem.setImage(org.talend.commons.ui.image.ImageProvider.getImage(org.talend.commons.ui.image.ImageProvider
                .getImageDesc(EImage.MINUS_ICON)));
        removeEntryItem.setToolTipText(Messages.getString("VarsDataMapTableView.entryItemTooltip.removeVariable")); //$NON-NLS-1$

        removeEntryItem.addSelectionListener(new SelectionListener() {

            public void widgetDefaultSelected(SelectionEvent e) {
            }

            public void widgetSelected(SelectionEvent e) {
                IStructuredSelection selection = (IStructuredSelection) tableViewerCreatorForColumns.getTableViewer().getSelection();
                List<ITableEntry> selectedBeans = (List<ITableEntry>) selection.toList();

                for (ITableEntry entry : selectedBeans) {
                    mapperManager.removeTableEntry(entry);
                }
                if (selectedBeans.size() > 0) {
                    tableViewerCreatorForColumns.getTableViewer().refresh();
                    mapperManager.getUiManager().refreshBackground(true, false);
                    resizeAtExpandedSize();
                }
                removeEntryItem.setEnabled(false);
            }

        });
        // /////////////////////////////////////////////////////////////////

        // /////////////////////////////////////////////////////////////////
        moveUpEntryItem = new ToolItem(toolBarActions, SWT.PUSH);
        moveUpEntryItem.setEnabled(false);
        moveUpEntryItem.setImage(ImageProvider.getImage(EImage.UP_ICON));
        moveUpEntryItem.setToolTipText(Messages.getString("VarsDataMapTableView.entryItemTooltip.moveUpVariable")); //$NON-NLS-1$

        moveUpEntryItem.addSelectionListener(new SelectionListener() {

            public void widgetDefaultSelected(SelectionEvent e) {
            }

            public void widgetSelected(SelectionEvent e) {

                AbstractExtendedTableViewer viewer = (AbstractExtendedTableViewer) getExtendedTableViewerForColumns();
                ExtendedTableMoveCommand moveCommand = new ExtendedTableMoveCommand(viewer.getExtendedTableModel(), true, viewer
                        .getTableViewerCreator().getTable().getSelectionIndices());
                viewer.executeCommand(moveCommand);
            }

        });
        // /////////////////////////////////////////////////////////////////

        // /////////////////////////////////////////////////////////////////
        moveDownEntryItem = new ToolItem(toolBarActions, SWT.PUSH);
        moveDownEntryItem.setEnabled(false);
        moveDownEntryItem.setImage(ImageProvider.getImage(EImage.DOWN_ICON));
        moveDownEntryItem.setToolTipText(Messages.getString("VarsDataMapTableView.entryItemTooltip.movedownVariable")); //$NON-NLS-1$

        moveDownEntryItem.addSelectionListener(new SelectionListener() {

            public void widgetDefaultSelected(SelectionEvent e) {
            }

            public void widgetSelected(SelectionEvent e) {

                AbstractExtendedTableViewer viewer = (AbstractExtendedTableViewer) getExtendedTableViewerForColumns();
                ExtendedTableMoveCommand moveCommand = new ExtendedTableMoveCommand(viewer.getExtendedTableModel(), false, viewer
                        .getTableViewerCreator().getTable().getSelectionIndices());
                viewer.executeCommand(moveCommand);
            }

        });
        // /////////////////////////////////////////////////////////////////

    }

    /* (non-Javadoc)
     * @see org.talend.designer.dbmap.ui.visualmap.table.DataMapTableView#hasDropDownToolBarItem()
     */
    @Override
    public boolean hasDropDownToolBarItem() {
        return false;
    }

}
