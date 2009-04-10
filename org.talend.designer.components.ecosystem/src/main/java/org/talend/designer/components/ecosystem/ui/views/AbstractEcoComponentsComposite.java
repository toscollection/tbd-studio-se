// ============================================================================
//
// Copyright (C) 2006-2009 Talend Inc. - www.talend.com
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

import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.eclipse.jface.action.IAction;
import org.eclipse.jface.util.IPropertyChangeListener;
import org.eclipse.jface.util.PropertyChangeEvent;
import org.eclipse.swt.SWT;
import org.eclipse.swt.custom.TableEditor;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.events.SelectionListener;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Combo;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.Table;
import org.eclipse.swt.widgets.TableColumn;
import org.talend.commons.exception.ExceptionHandler;
import org.talend.commons.ui.swt.tableviewer.TableViewerCreator;
import org.talend.commons.ui.swt.tableviewer.TableViewerCreatorColumn;
import org.talend.commons.ui.swt.tableviewer.TableViewerCreator.SORT;
import org.talend.commons.ui.swt.tableviewer.sort.IColumnSortedListener;
import org.talend.commons.utils.data.bean.IBeanPropertyAccessors;
import org.talend.designer.components.ecosystem.EcosystemConstants;
import org.talend.designer.components.ecosystem.EcosystemPlugin;
import org.talend.designer.components.ecosystem.EcosystemUtils;
import org.talend.designer.components.ecosystem.i18n.Messages;
import org.talend.designer.components.ecosystem.model.ComponentExtension;
import org.talend.designer.components.ecosystem.model.Revision;

/**
 * DOC chuang class global comment. Detailled comment
 */
public abstract class AbstractEcoComponentsComposite extends Composite {

    protected static final int ICON_HEIGHT = 22;

    protected static final int ICON_WIDTH = 22;

    Map<String, ComponentExtension> fInstalledExtensions = new HashMap<String, ComponentExtension>();

    protected static DateFormat dateFormatter = new SimpleDateFormat("yyyy-MM-dd");

    protected IBeanPropertyAccessors<ComponentExtension, String> DESCRIPTION_ACCESSOR;

    protected IBeanPropertyAccessors<ComponentExtension, String> NAME_ACCESSOR;

    protected IBeanPropertyAccessors<ComponentExtension, String> AUTHOR_ACCESSOR;

    protected IBeanPropertyAccessors<ComponentExtension, String> STATUS_ACCESSOR;

    protected IBeanPropertyAccessors<ComponentExtension, String> LATEST_REVISION_ACCESSOR;

    protected IBeanPropertyAccessors<ComponentExtension, String> INSTALLED_REVISION_ACCESSOR;

    // protected IBeanPropertyAccessors<ComponentExtension, String>
    // DATE_ACCESSOR;

    protected TableViewerCreator<ComponentExtension> fTableViewerCreator;

    protected Combo versionCombo;

    protected Shell shell;

    protected List<TableViewerCreatorColumn> sortableColumns = new ArrayList<TableViewerCreatorColumn>();

    protected TableViewerCreatorColumn<ComponentExtension, String> fNameColumn;

    /**
     * DOC chuang AbstractEcoComponentsComposite constructor comment.
     */
    AbstractEcoComponentsComposite(Composite parent, Shell shell) {
        super(parent, SWT.NONE);
        this.shell = shell;
        initialColumnModel();
    }

    protected void initialColumnModel() {
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
                    return Messages.getString("EcosystemViewComposite.Status.NotInstalled");
                } else if (!bean.getLatestRevision().getName().equals(bean.getInstalledRevision().getName())) {
                    return Messages.getString("EcosystemViewComposite.Status.Deprecated");
                } else {
                    return Messages.getString("EcosystemViewComposite.Status.Installed");
                }
            }

        };

        LATEST_REVISION_ACCESSOR = new BeanPropertyAccessorsAdapter<ComponentExtension, String>() {

            @Override
            public String get(ComponentExtension bean) {
                return String.format("%1$-6s%2$s", bean.getLatestRevision().getName(), " ("
                        + dateFormatter.format(bean.getLatestRevision().getDate()) + ")");
            }

        };

        INSTALLED_REVISION_ACCESSOR = new BeanPropertyAccessorsAdapter<ComponentExtension, String>() {

            @Override
            public String get(ComponentExtension bean) {
                Revision installed = bean.getInstalledRevision();
                if (installed == null) {
                    return "";
                } else {
                    return String.format("%1$-6s%2$s", installed.getName(), " (" + dateFormatter.format(installed.getDate())
                            + ")");
                }
            }

        };

        // DATE_ACCESSOR = new BeanPropertyAccessorsAdapter<ComponentExtension,
        // String>() {
        //
        // @Override
        // public String get(ComponentExtension bean) {
        // return dateFormatter.format(bean.getLatestRevision().getDate());
        // }
        //
        // };
    }

    /**
     * yzhang Comment method "creatTosVersionFilter".
     * 
     * @param parent
     */
    protected void creatTosVersionFilter(Composite parent) {
        Composite tosVersionFilterComposite = new Composite(parent, SWT.NONE);

        tosVersionFilterComposite.setLayout(new GridLayout(2, false));
        Label versionFilterLable = new Label(tosVersionFilterComposite, SWT.NONE);
        versionFilterLable.setText(EcosystemConstants.getVersionFilterLabel());
        versionCombo = new Combo(tosVersionFilterComposite, SWT.DROP_DOWN | SWT.READ_ONLY);
        String currentVersion = EcosystemPlugin.getDefault().getPreferenceStore().getString(EcosystemView.TOS_VERSION_FILTER);

        currentVersion = EcosystemUtils.getMainVersion(currentVersion);
        String versions[] = EcosystemUtils.getVersionList();

        if (versions != null) {
            int stringIndex = 0;
            for (int i = 0; i < versions.length; i++) {
                versionCombo.add(versions[i]);
                if (versions[i].equals(currentVersion)) {
                    stringIndex = i;
                }
            }
            versionCombo.select(stringIndex);
        }

        versionCombo.addSelectionListener(new SelectionListener() {

            public void widgetDefaultSelected(SelectionEvent e) {
                widgetSelected(e);
            }

            public void widgetSelected(SelectionEvent e) {

                onVersionFilterChanged(e);
            }
        });
    }

    protected abstract void onVersionFilterChanged(SelectionEvent e);

    protected GridLayout clearGridLayoutSpace(GridLayout layout) {
        layout.horizontalSpacing = 0;
        layout.verticalSpacing = 0;
        layout.marginWidth = 0;
        layout.marginHeight = 0;
        return layout;
    }

    /**
     * DOC YeXiaowei Comment method "createInstallActionColumn".
     */
    protected void createActionColumn(int width) {
        TableViewerCreatorColumn<ComponentExtension, String> actionColumn = createTableColumn("", false, false, width, null);
        actionColumn.setResizable(false);
    }

    void disposeEditors(List<TableEditor> editors) {
        for (TableEditor editor : editors) {
            Control control = editor.getEditor();
            if (control != null) {
                control.dispose();
            }
            editor.dispose();
        }
        editors.clear();
    }

    protected TableViewerCreatorColumn<ComponentExtension, String> createTableColumn(String title, boolean sortable,
            boolean modifiable, int width, IBeanPropertyAccessors<ComponentExtension, String> accessor) {
        TableViewerCreatorColumn<ComponentExtension, String> column = new TableViewerCreatorColumn<ComponentExtension, String>(
                fTableViewerCreator);
        column.setTitle(title);
        column.setSortable(sortable);
        column.setModifiable(modifiable);
        // column.setWeight(weight);
        column.setWidth(width);
        column.setBeanPropertyAccessors(accessor);
        if (sortable) {
            sortableColumns.add(column);
        }
        return column;
    }

    /**
     * Initialize the content of table and sort by name.
     * 
     * @param extensions
     */
    public void initTable(List<ComponentExtension> extensions) {
        fTableViewerCreator.init(extensions);
        fTableViewerCreator.setSort(fNameColumn, SORT.ASC);
        for (TableViewerCreatorColumn column : sortableColumns) {
            column.getTableColumnSelectionListener().addColumnSortedListener(new IColumnSortedListener() {

                public void handle() {
                    addButtons();
                }

            });
        }

        Table table = fTableViewerCreator.getTable();
        for (TableColumn col : table.getColumns()) {
            col.pack();
        }
        table.setFocus();

        addButtons();
    }

    abstract void addButtons();

    /**
     * Load installed components information from file.
     */
    public void loadFromFile() {
        try {
            List<ComponentExtension> extensions = EcosystemUtils.loadInstallComponents(EcosystemConstants.COMPONENT_MODEL_FILE);
            for (ComponentExtension ext : extensions) {
                fInstalledExtensions.put(ext.getName(), ext);
            }
        } catch (Throwable e) {
            // do nothing, the file may not exist because this is the first time
            // we use this view and we haven't
            // installed any extensions
        }
    }

    /**
     * Save installed components information to file.
     */
    public void saveToFile() {
        try {
            EcosystemUtils.saveInstallComponents(EcosystemConstants.COMPONENT_MODEL_FILE, getInstalledExtensions());
        } catch (IOException e) {
            ExceptionHandler.process(e);
        }
    }

    /**
     * @return the installedExtensions
     */
    public List<ComponentExtension> getInstalledExtensions() {
        return new ArrayList<ComponentExtension>(fInstalledExtensions.values());
    }

    public void addInstalledExtension(ComponentExtension extension) {
        fInstalledExtensions.put(extension.getName(), extension);
    }

    /**
     * DOC chuang Comment method "addButtonStateListener".
     * 
     * @param button
     * @param action
     */
    protected void addButtonStateListener(final Button button, final IAction action) {
        action.addPropertyChangeListener(new IPropertyChangeListener() {

            public void propertyChange(PropertyChangeEvent event) {
                if (!button.isDisposed()) {
                    if (event.getProperty().equals(IAction.ENABLED)) {
                        button.setEnabled((Boolean) event.getNewValue());
                    }
                } else {
                    action.removePropertyChangeListener(this);
                }
            }
        });
    }

}
