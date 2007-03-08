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

import org.eclipse.jface.viewers.ICellEditorListener;
import org.eclipse.swt.SWT;
import org.eclipse.swt.custom.CCombo;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.graphics.Color;
import org.eclipse.swt.graphics.Point;
import org.eclipse.swt.graphics.Rectangle;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Menu;
import org.eclipse.swt.widgets.MenuItem;
import org.eclipse.swt.widgets.ToolBar;
import org.eclipse.swt.widgets.ToolItem;
import org.talend.commons.ui.swt.celleditor.ComboxCellEditorImproved;
import org.talend.commons.ui.swt.tableviewer.CellEditorValueAdapterFactory;
import org.talend.commons.ui.swt.tableviewer.TableViewerCreator;
import org.talend.commons.ui.swt.tableviewer.TableViewerCreatorColumn;
import org.talend.commons.ui.swt.tableviewer.TableViewerCreatorColumn.ALIGNMENT;
import org.talend.commons.ui.swt.tableviewer.behavior.DefaultCellModifier;
import org.talend.commons.ui.swt.tableviewer.behavior.IColumnColorProvider;
import org.talend.commons.ui.swt.tableviewer.data.ModifiedObjectInfo;
import org.talend.commons.ui.swt.tableviewer.tableeditor.CheckboxTableEditorContent;
import org.talend.commons.utils.data.bean.IBeanPropertyAccessors;
import org.talend.designer.dbmap.i18n.Messages;
import org.talend.designer.dbmap.language.IDbOperator;
import org.talend.designer.dbmap.language.IDbOperatorManager;
import org.talend.designer.dbmap.language.IJoinType;
import org.talend.designer.dbmap.managers.MapperManager;
import org.talend.designer.dbmap.model.table.InputTable;
import org.talend.designer.dbmap.model.tableentry.ITableEntry;
import org.talend.designer.dbmap.model.tableentry.InputColumnTableEntry;
import org.talend.designer.dbmap.ui.image.ImageInfo;
import org.talend.designer.dbmap.ui.image.ImageProviderMapper;
import org.talend.designer.dbmap.ui.visualmap.zone.Zone;

/**
 * DOC amaumont class global comment. Detailled comment <br/>
 * 
 * $Id: InputDataMapTableView.java 1782 2007-02-03 07:57:38Z bqian $
 * 
 */
public class InputDataMapTableView extends DataMapTableView {

    public static final Color READONLY_CELL_BG_COLOR = Display.getCurrent().getSystemColor(SWT.COLOR_GRAY);

    public static final String ID_OPERATOR_COLUMN = "ID_OPERATOR_COLUMN";

    private ToolItem dropDownItem;

    private InputTableCellModifier cellModifier;

    // private ToolItem joinWithPreviousJoinCheck;

    public InputDataMapTableView(Composite parent, int style, InputTable inputTable, MapperManager mapperManager) {
        super(parent, style, inputTable, mapperManager);
    }

    @Override
    public void initColumnsOfTableColumns(final TableViewerCreator tableViewerCreatorForColumns) {
        TableViewerCreatorColumn column = null;

        String useInJoinTitle = "Use in join";
        column = new TableViewerCreatorColumn(tableViewerCreatorForColumns);
        column.setTitle(useInJoinTitle);
        column.setBeanPropertyAccessors(new IBeanPropertyAccessors<InputColumnTableEntry, Boolean>() {

            public Boolean get(InputColumnTableEntry bean) {
                return bean.isJoin();
            }

            public void set(InputColumnTableEntry bean, Boolean value) {
                bean.setJoin(value);
            }

        });
        column.setModifiable(true);
        // column.setWidth(12);
        column.setWidth(65);
        column.setDisplayedValue("");
        // column.setResizable(false);
        CheckboxTableEditorContent checkboxTableEditorContent = new CheckboxTableEditorContent();
        checkboxTableEditorContent.setToolTipText(useInJoinTitle);
        column.setTableEditorContent(checkboxTableEditorContent);
        column.setToolTipHeader(useInJoinTitle);

        column = new TableViewerCreatorColumn(tableViewerCreatorForColumns);
        column.setTitle(DataMapTableView.COLUMN_NAME);
        column.setId(DataMapTableView.ID_NAME_COLUMN);
        column.setBeanPropertyAccessors(new IBeanPropertyAccessors<InputColumnTableEntry, String>() {

            public String get(InputColumnTableEntry bean) {
                return bean.getMetadataColumn().getLabel();
            }

            public void set(InputColumnTableEntry bean, String value) {
                bean.getMetadataColumn().setLabel(value);
            }

        });
        column.setWeight(COLUMN_NAME_SIZE_WEIGHT);

        final TableViewerCreatorColumn columnOperator = new TableViewerCreatorColumn(tableViewerCreatorForColumns);
        columnOperator.setTitle("Operator");
        columnOperator.setId(InputDataMapTableView.ID_OPERATOR_COLUMN);
        columnOperator.setToolTipHeader("Operator");
        columnOperator.setBeanPropertyAccessors(new IBeanPropertyAccessors<InputColumnTableEntry, String>() {

            public String get(InputColumnTableEntry bean) {
                return bean.getOperator();
            }

            public void set(InputColumnTableEntry bean, String value) {
                bean.setOperator(value);
                mapperManager.getProblemsManager().checkProblemsForTableEntry(bean, true);
            }

        });
        columnOperator.setModifiable(true);
        columnOperator.setWidth(85);
        final IDbOperatorManager operatorsManager = mapperManager.getCurrentLanguage().getOperatorsManager();
        IDbOperator[] operators = operatorsManager.getOperators();
        String[] arrayOperators = new String[operators.length + 1];
        arrayOperators[0] = "";
        for (int i = 0; i < operators.length; i++) {
            arrayOperators[i + 1] = operators[i].getOperator();
        }
        final ComboxCellEditorImproved typeComboEditor = new ComboxCellEditorImproved(tableViewerCreatorForColumns.getTable(),
                arrayOperators, SWT.NONE);
        typeComboEditor.addListener(new ICellEditorListener() {

            public void applyEditorValue() {
                ModifiedObjectInfo modifiedObjectInfo = tableViewerCreatorForColumns.getModifiedObjectInfo();
                InputColumnTableEntry currentInputEntry = (InputColumnTableEntry) modifiedObjectInfo.getCurrentModifiedBean();
                currentInputEntry.setOriginalExpression(null);
                CCombo combo = (CCombo) typeComboEditor.getControl();
                String selectedText = combo.getText();
                IDbOperator operatorFromValue = operatorsManager.getOperatorFromValue(selectedText);
                if (operatorFromValue != null && operatorFromValue.isMonoOperand()) {
                    currentInputEntry.setExpression("");
                }
            }

            public void cancelEditor() {
                ModifiedObjectInfo modifiedObjectInfo = tableViewerCreatorForColumns.getModifiedObjectInfo();
                InputColumnTableEntry currentInputEntry = (InputColumnTableEntry) modifiedObjectInfo.getCurrentModifiedBean();
                // currentInputEntry.setExpression(currentInputEntry.getOriginalExpression());
            }

            public void editorValueChanged(boolean oldValidState, boolean newValidState) {
                ModifiedObjectInfo modifiedObjectInfo = tableViewerCreatorForColumns.getModifiedObjectInfo();
                InputColumnTableEntry currentInputEntry = (InputColumnTableEntry) modifiedObjectInfo.getCurrentModifiedBean();
                if (modifiedObjectInfo.getCurrentModifiedColumn() == columnOperator) {

                    if (currentInputEntry != modifiedObjectInfo.getPreviousModifiedBean()) {
                        currentInputEntry.setOriginalExpression(currentInputEntry.getExpression());
                    }
                    CCombo combo = (CCombo) typeComboEditor.getControl();
                    String selectedText = combo.getText();
                    if (!selectedText.equals("")
                            && (currentInputEntry.getExpression() == null || currentInputEntry.getExpression().trim().length() == 0)) {
                        IDbOperator operatorFromValue = operatorsManager.getOperatorFromValue(selectedText);
                        if (operatorFromValue.getAssociatedExpression() != null) {
                            currentInputEntry.setExpression(operatorFromValue.getAssociatedExpression());
                        }
                    }

                }
            }

        });
        CCombo typeCombo = (CCombo) typeComboEditor.getControl();
        typeCombo.setEditable(true);
        columnOperator.setCellEditor(typeComboEditor, CellEditorValueAdapterFactory.getComboAdapterForComboCellEditorImproved());
        columnOperator.setAlignment(ALIGNMENT.CENTER);

        final TableViewerCreatorColumn columnExpression = new TableViewerCreatorColumn(tableViewerCreatorForColumns);
        columnExpression.setTitle(Messages.getString("InputDataMapTableView.columnTitle.Expr")); //$NON-NLS-1$
        columnExpression.setId(DataMapTableView.ID_EXPRESSION_COLUMN);
        columnExpression.setBeanPropertyAccessors(new IBeanPropertyAccessors<InputColumnTableEntry, String>() {

            public String get(InputColumnTableEntry bean) {
                return bean.getExpression();
            }

            public void set(InputColumnTableEntry bean, String value) {
                bean.setExpression(value);
//                mapperManager.getProblemsManager().checkProblemsForTableEntry(bean, true);
            }

        });
        columnExpression.setModifiable(true);
        columnExpression.setDefaultInternalValue(""); //$NON-NLS-1$
        createExpressionCellEditor(tableViewerCreatorForColumns, columnExpression, new Zone[] { Zone.INPUTS }, false);
        columnExpression.setWeight(COLUMN_EXPRESSION_SIZE_WEIGHT);
        columnExpression.setColorProvider(new IColumnColorProvider() {

            public Color getBackgroundColor(Object bean) {
                if (!cellModifier.canModify(bean, columnExpression.getId())) {
                    return READONLY_CELL_BG_COLOR;
                }
                return null;
            }

            public Color getForegroundColor(Object bean) {
                return null;
            }

        });

        configureCellModifier(tableViewerCreatorForColumns);
    }

    /*
     * (non-Javadoc)
     * 
     * @see org.talend.designer.dbmap.ui.visualmap.table.DataMapTableView#initTableConstraints()
     */
    @Override
    protected void initTableFilters(Composite parent) {
        // nothing
    }

    /*
     * (non-Javadoc)
     * 
     * @see org.talend.designer.dbmap.ui.visualmap.table.DataMapTableView#addEntriesActionsComponents()
     */
    @Override
    protected boolean addToolItems() {

        // // JOIN WITH PREVIOUS JOIN
        // joinWithPreviousJoinCheck = new ToolItem(toolBarActions, SWT.CHECK);
        // joinWithPreviousJoinCheck.setToolTipText(Messages.getString("InputDataMapTableView.widgetTooltip.joinWithPrevious"));
        // //$NON-NLS-1$
        // boolean isInnerJoin = getInputTable().isJoinWithPrevious();
        // // Image image = ImageProviderMapper.getImage(isInnerJoin ? ImageInfo.CHECKED_ICON :
        // // ImageInfo.UNCHECKED_ICON);
        // Image image = ImageProviderMapper.getImage(isInnerJoin ? ImageInfo.CHECKED_ICON : ImageInfo.UNCHECKED_ICON);
        // if (WindowSystem.isGTK()) {
        // joinWithPreviousJoinCheck.setImage(image);
        // joinWithPreviousJoinCheck.setHotImage(image);
        // } else {
        // joinWithPreviousJoinCheck.setImage(ImageProviderMapper.getImage(ImageInfo.UNCHECKED_ICON));
        // joinWithPreviousJoinCheck.setHotImage(image);
        // }
        // joinWithPreviousJoinCheck.setSelection(isInnerJoin);
        // joinWithPreviousJoinCheck.setText(Messages.getString("InputDataMapTableView.widgetTooltip.joinWithPrevious"));
        // //$NON-NLS-1$
        // if (getInputTable().getJoinType() == AbstractDbLanguage.JOIN.NO_JOIN) {
        // joinWithPreviousJoinCheck.setEnabled(false);
        // } else {
        // joinWithPreviousJoinCheck.setEnabled(true);
        // }
        // joinWithPreviousJoinCheck.addSelectionListener(new SelectionListener() {
        //
        // public void widgetDefaultSelected(SelectionEvent e) {
        // }
        //
        // public void widgetSelected(SelectionEvent e) {
        // Image image = null;
        // if (joinWithPreviousJoinCheck.getSelection()) {
        // getInputTable().setJoinWithPrevious(true);
        // image = ImageProviderMapper.getImage(ImageInfo.CHECKED_ICON);
        // } else {
        // getInputTable().setJoinWithPrevious(false);
        // image = ImageProviderMapper.getImage(ImageInfo.UNCHECKED_ICON);
        // }
        // if (WindowSystem.isGTK()) {
        // joinWithPreviousJoinCheck.setImage(image);
        // joinWithPreviousJoinCheck.setHotImage(image);
        // } else {
        // joinWithPreviousJoinCheck.setHotImage(image);
        // }
        // mapperManager.getUiManager().refreshSqlExpression();
        // }
        //
        // });

        // DROP DOWN
        dropDownItem = new ToolItem(toolBarActions, SWT.DROP_DOWN | SWT.BORDER);
        refreshLabelForJoinDropDown();
        dropDownItem.addSelectionListener(new DropDownSelectionListener());

        return true;
    }

    /*
     * (non-Javadoc)
     * 
     * @see org.talend.designer.dbmap.ui.visualmap.table.DataMapTableView#getZone()
     */
    @Override
    public Zone getZone() {
        return Zone.INPUTS;
    }

    public InputTable getInputTable() {
        return (InputTable) abstractDataMapTable;
    }

    /*
     * (non-Javadoc)
     * 
     * @see org.talend.designer.dbmap.ui.visualmap.table.DataMapTableView#toolbarNeededToBeRightStyle()
     */
    @Override
    public boolean toolbarNeedToHaveRightStyle() {
        return true;
    }

    /**
     * Listens to widgetSelected() events on SWT.DROP_DOWN type ToolItems and opens/closes a menu when appropriate.
     */
    class DropDownSelectionListener extends SelectionAdapter {

        private Menu menu = null;

        private boolean visible = false;

        public void widgetSelected(SelectionEvent event) {
            // Create the menu if it has not already been created
            if (menu == null) {
                // Lazy create the menu.
                menu = new Menu(getShell());

                IJoinType[] availableJoins = mapperManager.getCurrentLanguage().getAvailableJoins();

                for (int i = 0; i < availableJoins.length; ++i) {
                    final String text = availableJoins[i].getLabel();
                    if (text.length() != 0) {
                        MenuItem menuItem = new MenuItem(menu, SWT.NONE);
                        menuItem.setData(availableJoins[i]);
                        menuItem.setText(text);
                        if (availableJoins[i] == getInputTable().getJoinType()) {
                            menuItem.setImage(ImageProviderMapper.getImage(ImageInfo.CHECKED_ICON));
                        }

                        /*
                         * Add a menu selection listener so that the menu is hidden when the user selects an item from
                         * the drop down menu.
                         */
                        menuItem.addSelectionListener(new SelectionAdapter() {

                            public void widgetSelected(SelectionEvent e) {
                                MenuItem menuItem = (MenuItem) e.widget;
                                MenuItem[] menuItems = menu.getItems();
                                for (int j = 0; j < menuItems.length; j++) {
                                    menuItems[j].setImage(null);
                                }
                                menuItem.setImage(ImageProviderMapper.getImage(ImageInfo.CHECKED_ICON));
                                IJoinType joinType = (IJoinType) menuItem.getData();
                                getInputTable().setJoinType(joinType);
                                // if (joinType == AbstractDbLanguage.JOIN.NO_JOIN) {
                                // joinWithPreviousJoinCheck.setEnabled(false);
                                // } else {
                                // joinWithPreviousJoinCheck.setEnabled(true);
                                // }
                                setMenuVisible(false);
                                refreshLabelForJoinDropDown();
                                mapperManager.getUiManager().refreshSqlExpression();
                            }

                        });
                    } else {
                        new MenuItem(menu, SWT.SEPARATOR);
                    }
                }
            }

            /**
             * A selection event will be fired when a drop down tool item is selected in the main area and in the drop
             * down arrow. Examine the event detail to determine where the widget was selected.
             */

            /*
             * The drop down arrow was selected.
             */
            if (visible) {
                // Hide the menu to give the Arrow the appearance of being a toggle button.
                setMenuVisible(false);
            } else {
                // Position the menu below and vertically aligned with the the drop down tool button.
                final ToolItem toolItem = (ToolItem) event.widget;
                final ToolBar toolBar = toolItem.getParent();

                Rectangle toolItemBounds = toolItem.getBounds();
                Point point = toolBar.toDisplay(new Point(toolItemBounds.x, toolItemBounds.y));
                menu.setLocation(point.x, point.y + toolItemBounds.height);
                setMenuVisible(true);
            }
        }

        private void setMenuVisible(boolean visible) {
            menu.setVisible(visible);
            this.visible = visible;
        }
    }

    /*
     * (non-Javadoc)
     * 
     * @see org.talend.designer.dbmap.ui.visualmap.table.DataMapTableView#hasDropDownToolBarItem()
     */
    @Override
    public boolean hasDropDownToolBarItem() {
        return true;
    }

    public void refreshLabelForJoinDropDown() {
        String text = getInputTable().getJoinType().getLabel();
        dropDownItem.setText(text);
        dropDownItem.setToolTipText(text);

        Point sizeToolBar = toolBarActions.computeSize(SWT.DEFAULT, SWT.DEFAULT);
        GridData gridData = (GridData) toolBarActions.getLayoutData();
        if (gridData != null) {
            gridData.widthHint = sizeToolBar.x;
            // gridData.widthHint -= 60;
            toolBarActions.getParent().layout();
        }

    }

    /*
     * (non-Javadoc)
     * 
     * @see org.talend.designer.dbmap.ui.visualmap.table.DataMapTableView#getTitle()
     */
    @Override
    protected String getTitle() {
        String alias = ((InputTable) abstractDataMapTable).getAlias();
        String tableName = ((InputTable) abstractDataMapTable).getTableName();
        if (alias != null) {
            return alias + " (alias of table '" + tableName + "')";
        }
        return super.getTitle();
    }

    protected void configureCellModifier(TableViewerCreator<InputColumnTableEntry> tableViewerCreator) {
        cellModifier = new InputTableCellModifier(tableViewerCreator);
        tableViewerCreator.setCellModifier(cellModifier);
    }

    /**
     * . <br/>
     * 
     * $Id: MetadataTableEditorView.java 2016 2007-02-12 15:36:11Z amaumont $
     * 
     */
    class InputTableCellModifier extends DefaultCellModifier {

        /**
         * DOC amaumont MetadataTableCellModifier constructor comment.
         * 
         * @param tableViewerCreator
         */
        public InputTableCellModifier(TableViewerCreator tableViewerCreator) {
            super(tableViewerCreator);
        }

        /*
         * (non-Javadoc)
         * 
         * @see org.talend.commons.ui.swt.tableviewer.behavior.DefaultCellModifier#canModify(java.lang.Object,
         * java.lang.String)
         */
        @Override
        public boolean canModify(Object element, String property) {
            TableViewerCreatorColumn columnOperator = getTableViewerCreator().getColumn(InputDataMapTableView.ID_OPERATOR_COLUMN);
            String operator = (String) columnOperator.getBeanPropertyAccessors().get(element);
            IDbOperatorManager operatorsManager = mapperManager.getCurrentLanguage().getOperatorsManager();
            IDbOperator operatorFromValue = operatorsManager.getOperatorFromValue(operator);
            boolean columnIsExpression = DataMapTableView.ID_EXPRESSION_COLUMN.equals(property);
            return super.canModify(element, property)
                    && (columnIsExpression && operatorFromValue != null && !operatorFromValue.isMonoOperand() || !columnIsExpression || operatorFromValue == null);
        }

    }

    /*
     * (non-Javadoc)
     * 
     * @see org.talend.designer.dbmap.ui.visualmap.table.DataMapTableView#getBackgroundCellColor(org.talend.commons.ui.swt.tableviewer.TableViewerCreator,
     * java.lang.Object, int)
     */
    @Override
    protected Color getBackgroundCellColor(TableViewerCreator tableViewerCreator, Object element, int columnIndex) {
        ITableEntry entry = (ITableEntry) element;
        TableViewerCreatorColumn column = (TableViewerCreatorColumn) tableViewerCreator.getColumns().get(columnIndex);
        if (column.getId().equals(ID_OPERATOR_COLUMN)) {
            Color backgroundColor = getExpressionColorProvider().getBackgroundColor(entry.getProblems() == null ? true : false);
            if (backgroundColor != null) {
                return backgroundColor;
            }
        }
        return super.getBackgroundCellColor(tableViewerCreator, element, columnIndex);
    }

    /*
     * (non-Javadoc)
     * 
     * @see org.talend.designer.dbmap.ui.visualmap.table.DataMapTableView#getForegroundCellColor(org.talend.commons.ui.swt.tableviewer.TableViewerCreator,
     * java.lang.Object, int)
     */
    @Override
    protected Color getForegroundCellColor(TableViewerCreator tableViewerCreator, Object element, int columnIndex) {
        ITableEntry entry = (ITableEntry) element;
        TableViewerCreatorColumn column = (TableViewerCreatorColumn) tableViewerCreator.getColumns().get(columnIndex);
        if (column.getId().equals(ID_OPERATOR_COLUMN)) {
            Color foregroundColor = getExpressionColorProvider().getForegroundColor(entry.getProblems() == null ? true : false);
            if (foregroundColor != null) {
                return foregroundColor;
            }
        }
        return super.getForegroundCellColor(tableViewerCreator, element, columnIndex);
    }

}
