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

import org.eclipse.jface.viewers.ComboBoxCellEditor;
import org.eclipse.swt.SWT;
import org.eclipse.swt.custom.CCombo;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.events.SelectionListener;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.graphics.Point;
import org.eclipse.swt.graphics.Rectangle;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Menu;
import org.eclipse.swt.widgets.MenuItem;
import org.eclipse.swt.widgets.ToolBar;
import org.eclipse.swt.widgets.ToolItem;
import org.talend.commons.ui.swt.tableviewer.CellEditorValueAdapterFactory;
import org.talend.commons.ui.swt.tableviewer.TableViewerCreator;
import org.talend.commons.ui.swt.tableviewer.TableViewerCreatorColumn;
import org.talend.commons.ui.swt.tableviewer.TableViewerCreatorColumn.ALIGNMENT;
import org.talend.commons.ui.swt.tableviewer.tableeditor.CheckboxTableEditorContent;
import org.talend.commons.ui.ws.WindowSystem;
import org.talend.commons.utils.data.bean.IBeanPropertyAccessors;
import org.talend.designer.dbmap.i18n.Messages;
import org.talend.designer.dbmap.language.AbstractDbLanguage;
import org.talend.designer.dbmap.language.IJoinType;
import org.talend.designer.dbmap.managers.MapperManager;
import org.talend.designer.dbmap.model.table.InputTable;
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

    private ToolItem dropDownItem;

//    private ToolItem joinWithPreviousJoinCheck;

    public InputDataMapTableView(Composite parent, int style, InputTable inputTable, MapperManager mapperManager) {
        super(parent, style, inputTable, mapperManager);
    }

    @Override
    public void initColumnsOfTableColumns(TableViewerCreator tableViewerCreatorForColumns) {
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
        column.setWidth(12);
        column.setDisplayedValue("");
        column.setResizable(false);
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

        column = new TableViewerCreatorColumn(tableViewerCreatorForColumns);
        column.setTitle("Operator");
        column.setToolTipHeader("Operator");
        column.setBeanPropertyAccessors(new IBeanPropertyAccessors<InputColumnTableEntry, String>() {

            public String get(InputColumnTableEntry bean) {
                return bean.getOperator();
            }

            public void set(InputColumnTableEntry bean, String value) {
                bean.setOperator(value);
            }

        });
        column.setModifiable(true);
        column.setWeight(20);

        String[] arrayOperators = new String[] { "", "=", "<>", ">", "<", ">=", "<=", "BETWEEN", "IS NOT NULL", "IS NULL", "IN", "NOT IN" };

        ComboBoxCellEditor typeComboEditor = new ComboBoxCellEditor(tableViewerCreatorForColumns.getTable(), arrayOperators, SWT.NONE);
        CCombo typeCombo = (CCombo) typeComboEditor.getControl();
        typeCombo.setEditable(true);
        column.setCellEditor(typeComboEditor, CellEditorValueAdapterFactory.getComboAdapter());
        column.setAlignment(ALIGNMENT.CENTER);

        column = new TableViewerCreatorColumn(tableViewerCreatorForColumns);
        column.setTitle(Messages.getString("InputDataMapTableView.columnTitle.Expr")); //$NON-NLS-1$
        column.setId(DataMapTableView.ID_EXPRESSION_COLUMN);
        column.setBeanPropertyAccessors(new IBeanPropertyAccessors<InputColumnTableEntry, String>() {

            public String get(InputColumnTableEntry bean) {
                return bean.getExpression();
            }

            public void set(InputColumnTableEntry bean, String value) {
                bean.setExpression(value);
                mapperManager.getProblemsManager().checkProblemsForTableEntry(bean, true);
            }

        });
        column.setModifiable(true);
        column.setDefaultInternalValue(""); //$NON-NLS-1$
        createExpressionCellEditor(tableViewerCreatorForColumns, column, new Zone[] { Zone.INPUTS }, false);
        column.setWeight(COLUMN_EXPRESSION_SIZE_WEIGHT);

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

//        // JOIN WITH PREVIOUS JOIN
//        joinWithPreviousJoinCheck = new ToolItem(toolBarActions, SWT.CHECK);
//        joinWithPreviousJoinCheck.setToolTipText(Messages.getString("InputDataMapTableView.widgetTooltip.joinWithPrevious"));
//        //$NON-NLS-1$
//        boolean isInnerJoin = getInputTable().isJoinWithPrevious();
//        // Image image = ImageProviderMapper.getImage(isInnerJoin ? ImageInfo.CHECKED_ICON :
//        // ImageInfo.UNCHECKED_ICON);
//        Image image = ImageProviderMapper.getImage(isInnerJoin ? ImageInfo.CHECKED_ICON : ImageInfo.UNCHECKED_ICON);
//        if (WindowSystem.isGTK()) {
//            joinWithPreviousJoinCheck.setImage(image);
//            joinWithPreviousJoinCheck.setHotImage(image);
//        } else {
//            joinWithPreviousJoinCheck.setImage(ImageProviderMapper.getImage(ImageInfo.UNCHECKED_ICON));
//            joinWithPreviousJoinCheck.setHotImage(image);
//        }
//        joinWithPreviousJoinCheck.setSelection(isInnerJoin);
//        joinWithPreviousJoinCheck.setText(Messages.getString("InputDataMapTableView.widgetTooltip.joinWithPrevious")); //$NON-NLS-1$
//        if (getInputTable().getJoinType() == AbstractDbLanguage.JOIN.NO_JOIN) {
//            joinWithPreviousJoinCheck.setEnabled(false);
//        } else {
//            joinWithPreviousJoinCheck.setEnabled(true);
//        }
//        joinWithPreviousJoinCheck.addSelectionListener(new SelectionListener() {
//
//            public void widgetDefaultSelected(SelectionEvent e) {
//            }
//
//            public void widgetSelected(SelectionEvent e) {
//                Image image = null;
//                if (joinWithPreviousJoinCheck.getSelection()) {
//                    getInputTable().setJoinWithPrevious(true);
//                    image = ImageProviderMapper.getImage(ImageInfo.CHECKED_ICON);
//                } else {
//                    getInputTable().setJoinWithPrevious(false);
//                    image = ImageProviderMapper.getImage(ImageInfo.UNCHECKED_ICON);
//                }
//                if (WindowSystem.isGTK()) {
//                    joinWithPreviousJoinCheck.setImage(image);
//                    joinWithPreviousJoinCheck.setHotImage(image);
//                } else {
//                    joinWithPreviousJoinCheck.setHotImage(image);
//                }
//                mapperManager.getUiManager().refreshSqlExpression();
//            }
//
//        });

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
//                                if (joinType == AbstractDbLanguage.JOIN.NO_JOIN) {
//                                    joinWithPreviousJoinCheck.setEnabled(false);
//                                } else {
//                                    joinWithPreviousJoinCheck.setEnabled(true);
//                                }
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

}
