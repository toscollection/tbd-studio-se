// ============================================================================
//
// Copyright (C) 2006-2007 Talend Inc. - www.talend.com
//
// This source code is available under agreement available at
// %InstallDIR%\features\org.talend.rcp.branding.%PRODUCTNAME%\%PRODUCTNAME%license.txt
//
// You should have received a copy of the agreement
// along with this program; if not, write to Talend SA
// 9 rue Pages 92150 Suresnes, France
//
// ============================================================================
package org.talend.designer.components.ecosystem.ui.views;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.List;

import org.eclipse.jface.action.IAction;
import org.eclipse.swt.SWT;
import org.eclipse.swt.custom.TableEditor;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.layout.FillLayout;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Table;
import org.eclipse.swt.widgets.TableItem;
import org.talend.commons.ui.swt.tableviewer.TableViewerCreator;
import org.talend.commons.ui.swt.tableviewer.TableViewerCreatorColumn;
import org.talend.commons.ui.swt.tableviewer.TableViewerCreator.LAYOUT_MODE;
import org.talend.commons.ui.swt.tableviewer.TableViewerCreator.SORT;
import org.talend.commons.utils.data.bean.IBeanPropertyAccessors;
import org.talend.designer.components.ecosystem.EcosystemConstants;
import org.talend.designer.components.ecosystem.model.ComponentExtension;
import org.talend.designer.components.ecosystem.model.util.ActionHelper;

/**
 * Composite that contains a table viewer and display in the ecosystem view.
 */
public class EcosystemViewComposite extends Composite {

    private static DateFormat dateFormatter = new SimpleDateFormat("yyyy-MM-dd");

    private static final IBeanPropertyAccessors<ComponentExtension, String> DESCRIPTION_ACCESSOR;

    private static final IBeanPropertyAccessors<ComponentExtension, String> NAME_ACCESSOR;

    private static final IBeanPropertyAccessors<ComponentExtension, String> AUTHOR_ACCESSOR;

    private static final IBeanPropertyAccessors<ComponentExtension, String> STATUS_ACCESSOR;

    private static final IBeanPropertyAccessors<ComponentExtension, String> REVISION_ACCESSOR;

    private static final IBeanPropertyAccessors<ComponentExtension, String> DATE_ACCESSOR;

    private TableViewerCreator<ComponentExtension> fTableViewerCreator;

    private TableViewerCreatorColumn<ComponentExtension, String> fNameColumn;

    static {
        DESCRIPTION_ACCESSOR = new BeanPropertyAccessorsAdapter<ComponentExtension, String>() {

            @Override
            public String get(ComponentExtension bean) {
                return bean.getDescription();
            }

        };

        NAME_ACCESSOR = new BeanPropertyAccessorsAdapter<ComponentExtension, String>() {

            @Override
            public String get(ComponentExtension bean) {
                return bean.getName();
            }

        };

        AUTHOR_ACCESSOR = new BeanPropertyAccessorsAdapter<ComponentExtension, String>() {

            @Override
            public String get(ComponentExtension bean) {
                return bean.getAuthor();
            }

        };

        STATUS_ACCESSOR = new BeanPropertyAccessorsAdapter<ComponentExtension, String>() {

            @Override
            public String get(ComponentExtension bean) {
                if (bean.getInstalledLocation() == null) {
                    return EcosystemConstants.STATUS_NOT_INSTALLED;
                } else if (!bean.getLatestRevision().getName().equals(bean.getInstalledRevision().getName())) {
                    return EcosystemConstants.STATUS_DEPRECATED;
                } else {
                    return EcosystemConstants.STATUS_INSTALLED;
                }
            }

        };

        REVISION_ACCESSOR = new BeanPropertyAccessorsAdapter<ComponentExtension, String>() {

            @Override
            public String get(ComponentExtension bean) {
                return bean.getLatestRevision().getName();
            }

        };

        DATE_ACCESSOR = new BeanPropertyAccessorsAdapter<ComponentExtension, String>() {

            @Override
            public String get(ComponentExtension bean) {
                return dateFormatter.format(bean.getLatestRevision().getDate());
            }

        };
    }

    /**
     * EcosystemViewComposite constructor.
     * 
     * @param parent
     * @param style
     */
    public EcosystemViewComposite(Composite parent) {
        super(parent, SWT.NONE);
        setLayout(clearGridLayoutSpace(new GridLayout()));

        Composite tableComposite = new Composite(this, SWT.NONE);
        tableComposite.setLayoutData(new GridData(GridData.FILL_BOTH));
        tableComposite.setLayout(new FillLayout());

        createTable(tableComposite);
    }

    private GridLayout clearGridLayoutSpace(GridLayout layout) {
        layout.horizontalSpacing = 0;
        layout.verticalSpacing = 0;
        layout.marginWidth = 0;
        layout.marginHeight = 0;
        return layout;
    }

    /**
     * Create the table viewer to display component information.
     * 
     * @param tableComposite
     */
    private void createTable(Composite tableComposite) {
        fTableViewerCreator = new TableViewerCreator<ComponentExtension>(tableComposite);
        fTableViewerCreator.setCheckboxInFirstColumn(false);
        fTableViewerCreator.setColumnsResizableByDefault(true);
        fTableViewerCreator.setLayoutMode(LAYOUT_MODE.FILL_HORIZONTAL);
        fTableViewerCreator.setHorizontalScroll(true);
        fTableViewerCreator.setVerticalScroll(true);
        fTableViewerCreator.createTable();

        // Status | Component Name | Author | Revision | Released Date | Description

        createInstallActionColumn();

        TableViewerCreatorColumn<ComponentExtension, String> statusColumn = createTableColumn(EcosystemConstants.STATUS_TITLE,
                true, false, 4, STATUS_ACCESSOR);
        statusColumn.setImageProvider(new StatusImageProvider());

        fNameColumn = createTableColumn(EcosystemConstants.COMPONENT_NAME_TITLE, true, false, 4, NAME_ACCESSOR);
        createTableColumn(EcosystemConstants.AUTHOR_TITLE, true, false, 4, AUTHOR_ACCESSOR); // authorColumn
        createTableColumn(EcosystemConstants.REVISION_TITLE, true, false, 2, REVISION_ACCESSOR); // revisionColumn
        createTableColumn(EcosystemConstants.RELEASED_DATE_TITLE, true, false, 4, DATE_ACCESSOR); // dateColumn
        TableViewerCreatorColumn<ComponentExtension, String> descriptionColumn = createTableColumn(
                EcosystemConstants.DESCRIPTION_TITLE, true, false, 18, DESCRIPTION_ACCESSOR); // descriptionColumn
        descriptionColumn.setMinimumWidth(1300);
    }

    /**
     * DOC YeXiaowei Comment method "createInstallActionColumn".
     */
    private void createInstallActionColumn() {
        TableViewerCreatorColumn<ComponentExtension, String> installcolumn = createTableColumn(
                EcosystemConstants.INSTALL_ACTION_TITLE, false, false, 3, null);
        installcolumn.setResizable(false);
    }

    /**
     * Initialize the content of table and sort by name.
     * 
     * @param extensions
     */
    public void initTable(List<ComponentExtension> extensions) {
        fTableViewerCreator.init(extensions);
        fTableViewerCreator.setSort(fNameColumn, SORT.ASC);
        // Add install button to first column
        addInstallButtonToFirstColumn();
    }

    /**
     * 
     * update the content of table.
     * 
     * @param extensions
     */
    public void updateTable(List<ComponentExtension> extensions) {
        fTableViewerCreator.setInputList(extensions);
        fTableViewerCreator.setSort(fNameColumn, SORT.ASC);
        // Add install button to first column
        addInstallButtonToFirstColumn();
    }

    /**
     * 
     * DOC YeXiaowei Comment method "addInstallButtonToFirstColumn".
     */
    private void addInstallButtonToFirstColumn() {
        final Table table = fTableViewerCreator.getTable();
        for (final TableItem item : table.getItems()) {
            TableEditor editor = new TableEditor(table);
            final Button button = new Button(table, SWT.FLAT);
            button.setImage(StatusImageProvider.getDownloadImage());
            button.setData(item);
            button.addSelectionListener(new SelectionAdapter() {

                /*
                 * (non-Javadoc)
                 * 
                 * @see org.eclipse.swt.events.SelectionAdapter#widgetSelected(org.eclipse.swt.events.SelectionEvent)
                 */
                @Override
                public void widgetSelected(SelectionEvent e) {
                    table.select(table.indexOf(item));
                    IAction action = ActionHelper.getDownloadAction();
                    if (action != null) {
                        action.run();
                    }
                }

            });
            editor.setEditor(button, item, 0);
            editor.grabHorizontal = true;
            button.pack();
        }
    }

    private TableViewerCreatorColumn<ComponentExtension, String> createTableColumn(String title, boolean sortable,
            boolean modifiable, int weight, IBeanPropertyAccessors<ComponentExtension, String> accessor) {
        TableViewerCreatorColumn<ComponentExtension, String> column = new TableViewerCreatorColumn<ComponentExtension, String>(
                fTableViewerCreator);
        column.setTitle(title);
        column.setSortable(sortable);
        column.setModifiable(modifiable);
        column.setWeight(weight);
        column.setBeanPropertyAccessors(accessor);
        return column;
    }

    public void refresh() {
        fTableViewerCreator.getTableViewer().refresh();
    }

    public TableItem[] getSelectedItems() {
        return fTableViewerCreator.getTable().getSelection();
    }

    public TableItem[] getAllItems() {
        return fTableViewerCreator.getTable().getItems();
    }

    static class BeanPropertyAccessorsAdapter<B, V> implements IBeanPropertyAccessors<B, V> {

        /*
         * (non-Javadoc)
         * 
         * @see org.talend.commons.utils.data.bean.IGetterPropertyAccessor#get(java.lang.Object)
         */
        public V get(B bean) {
            return null;
        }

        /*
         * (non-Javadoc)
         * 
         * @see org.talend.commons.utils.data.bean.ISetterPropertyAccessor#set(java.lang.Object, java.lang.Object)
         */
        public void set(B bean, V value) {
        }
    }
}
