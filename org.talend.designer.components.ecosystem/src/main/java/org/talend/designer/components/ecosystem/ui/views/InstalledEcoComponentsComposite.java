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
package org.talend.designer.components.ecosystem.ui.views;

import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.ArrayUtils;
import org.apache.commons.lang.StringUtils;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.Status;
import org.eclipse.core.runtime.jobs.Job;
import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.jface.dialogs.ProgressMonitorDialog;
import org.eclipse.jface.operation.IRunnableWithProgress;
import org.eclipse.jface.preference.IPreferenceStore;
import org.eclipse.jface.viewers.Viewer;
import org.eclipse.jface.viewers.ViewerFilter;
import org.eclipse.jface.window.Window;
import org.eclipse.swt.SWT;
import org.eclipse.swt.custom.TableEditor;
import org.eclipse.swt.events.KeyAdapter;
import org.eclipse.swt.events.KeyEvent;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.events.SelectionListener;
import org.eclipse.swt.graphics.Point;
import org.eclipse.swt.layout.FillLayout;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Combo;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Event;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Link;
import org.eclipse.swt.widgets.Listener;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.Table;
import org.eclipse.swt.widgets.TableItem;
import org.eclipse.swt.widgets.Text;
import org.eclipse.ui.PlatformUI;
import org.talend.commons.exception.ExceptionHandler;
import org.talend.commons.ui.swt.tableviewer.TableViewerCreator;
import org.talend.commons.ui.swt.tableviewer.TableViewerCreatorColumn;
import org.talend.commons.ui.swt.tableviewer.TableViewerCreator.LAYOUT_MODE;
import org.talend.commons.ui.swt.tableviewer.TableViewerCreator.SORT;
import org.talend.commons.ui.ws.WindowSystem;
import org.talend.designer.components.ecosystem.EcosystemConstants;
import org.talend.designer.components.ecosystem.EcosystemPlugin;
import org.talend.designer.components.ecosystem.EcosystemUtils;
import org.talend.designer.components.ecosystem.i18n.Messages;
import org.talend.designer.components.ecosystem.jobs.ComponentSearcher;
import org.talend.designer.components.ecosystem.model.ComponentExtension;
import org.talend.designer.components.ecosystem.model.EcosystemPackage;
import org.talend.designer.components.ecosystem.ui.actions.UpdateComponenentsUtil;

/**
 * DOC chuang class global comment. Detailled comment
 */
public class InstalledEcoComponentsComposite extends AbstractEcoComponentsComposite {

    static final int UPDATE_BUTTON_COLUMN = 0;

    static final int REMOVE_BUTTON_COLUMN = 1;

    List<TableEditor> updateEditors = new ArrayList<TableEditor>();

    List<TableEditor> removeEditors = new ArrayList<TableEditor>();

    List<ComponentExtension> compatible;

    private Text fFilterText;

    private Button filterbutton;

    private String[] fFilters = AVAILABLE_FILTERS;

    public ComponentExtension curExtensionItem;

    private static final String[] AVAILABLE_FILTERS = new String[] { "Name", "Description" }; //$NON-NLS-1$ //$NON-NLS-2$

    private static final Map<String, Integer> FILTER_MAP = new HashMap<String, Integer>();

    private static final String REFRESH_COMPONENTS = "refreshComponents"; //$NON-NLS-1$

    private String UPDATEKEY = "UPDATEKEY"; //$NON-NLS-1$

    String value;

    ViewerFilter filter = new ViewerFilter() {

        @Override
        public boolean select(Viewer viewer, Object parentElement, Object element) {
            // ComponentExtension ext = (ComponentExtension) element;
            return true;
        }

    };

    static {
        FILTER_MAP.put(AVAILABLE_FILTERS[0], EcosystemPackage.COMPONENT_EXTENSION__NAME);
        FILTER_MAP.put(AVAILABLE_FILTERS[1], EcosystemPackage.COMPONENT_EXTENSION__DESCRIPTION);
    }

    /**
     * DOC chuang InstalledEcoComponentsComposite constructor comment.
     */
    InstalledEcoComponentsComposite(Composite parent, Shell shell) {
        super(parent, shell);
        createControls(this);
        init();
    }

    /**
     * DOC chuang Comment method "init".
     */
    public void init() {
        // load installed component information from file
        loadFromFile();
        initTable(new ArrayList<ComponentExtension>(fInstalledExtensions.values()));
        refresh();
    }

    /**
     * DOC chuang Comment method "createControls".
     * 
     * @param installedEcoComponentsComposite gcui add a filter
     * 
     * gcui add a checkbox
     */
    private void createControls(Composite parent) {
        setLayout(clearGridLayoutSpace(new GridLayout(5, false)));

        Label filterLabel = new Label(parent, SWT.NONE);
        filterLabel.setText(EcosystemConstants.getFilterLabelText());
        filterLabel.setLayoutData(new GridData());

        fFilterText = new Text(parent, SWT.BORDER);
        GridData gd = new GridData(SWT.FILL, SWT.CENTER, false, false);
        gd.widthHint = 200;
        fFilterText.setLayoutData(gd);
        fFilterText.addKeyListener(new KeyAdapter() {

            @Override
            public void keyPressed(KeyEvent e) {
                if (e.keyCode == EcosystemConstants.KEY_ENTER) {
                    // press enter key
                    applyFilters();
                }
            }

        });

        final Link filterLink = new Link(parent, SWT.NONE);
        filterLink.setText("<a href=\"\">" + EcosystemConstants.getFilterLinkTextLable() + "</a>"); //$NON-NLS-1$ //$NON-NLS-2$
        filterLink.addSelectionListener(new SelectionAdapter() {

            @Override
            public void widgetSelected(final SelectionEvent e) {
                openFilterDialog();
            }
        });

        creatTosVersionFilter(parent, false);
        versionCombo.setEnabled(false);

        filterbutton = new Button(parent, SWT.CHECK);
        filterbutton.setText(Messages.getString("InstalledEcoComponentsComposite.useVersionFilter")); //$NON-NLS-1$
        filterbutton.setSelection(false);
        filterbutton.addSelectionListener(new SelectionListener() {

            public void widgetSelected(SelectionEvent e) {
                boolean isTosFilterSelect = filterbutton.getSelection();
                versionCombo.setEnabled(isTosFilterSelect);
                if (isTosFilterSelect == false) {
                    updateTable(new ArrayList<ComponentExtension>(fInstalledExtensions.values()));
                }
                if (isTosFilterSelect == true) {
                    value = versionCombo.getText();
                    selectInstalledVersion();
                }
            }

            public void widgetDefaultSelected(SelectionEvent e) {
                widgetSelected(e);

            }
        });

        Composite tableComposite = new Composite(this, SWT.NONE);
        tableComposite.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, true, 5, 1));
        tableComposite.setLayout(new FillLayout());
        createTable(tableComposite);
    }

    /*
     * gcui applyFilters when user press enter
     */

    private void applyFilters() {
        if (ArrayUtils.isEmpty(fFilters) || StringUtils.isEmpty(fFilterText.getText())) {
            updateTable(new ArrayList<ComponentExtension>(fInstalledExtensions.values()));
            return;
        }
        int[] featureId = new int[fFilters.length];
        for (int i = 0; i < fFilters.length; i++) {
            featureId[i] = FILTER_MAP.get(fFilters[i]);
        }
        List<ComponentExtension> found;
        try {
            found = ComponentSearcher.filterComponentExtensions(new ArrayList<ComponentExtension>(fInstalledExtensions.values()),
                    fFilterText.getText(), featureId);
            updateTable(found);
        } catch (Exception e) {
            ExceptionHandler.process(e);
        }
    }

    public void updateTable(List<ComponentExtension> extensions) {

        fTableViewerCreator.setInputList(extensions);
        fTableViewerCreator.setSort(fNameColumn, SORT.ASC);

        addButtons();
    }

    /**
     * Open a dialog for user to select filters.
     */
    protected void openFilterDialog() {
        FilterDialog dialog = new FilterDialog(shell, AVAILABLE_FILTERS, fFilters);
        if (dialog.open() == Window.OK) {
            Object[] result = dialog.getResult();
            fFilters = new String[result.length];
            for (int i = 0; i < result.length; i++) {
                fFilters[i] = result[i].toString();
            }
        }
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
        // fTableViewerCreator.getTableViewer().addFilter(filter);

        // install / update / remove actions, see 0005053: [ecosystem view]
        // update and remove components
        int width = ICON_WIDTH;
        if (WindowSystem.isGTK()) {
            // fix button icon size in linux
            width = 32;
        }
        createActionColumn(width + 6);
        createActionColumn(width + 4);

        fNameColumn = createTableColumn(EcosystemConstants.getComponentNameTitleLable(), true, false, 100, NAME_ACCESSOR);
        createTableColumn(EcosystemConstants.getAuthorTitleLable(), true, false, 70, AUTHOR_ACCESSOR); // authorColumn

        // install revision column
        createTableColumn(Messages.getString("EcosystemViewComposite.InstalledRevision.Title"), true, false, 110, //$NON-NLS-1$
                INSTALLED_REVISION_ACCESSOR);
        TableViewerCreatorColumn<ComponentExtension, String> descriptionColumn = createTableColumn(EcosystemConstants
                .getDescriptionTitleLable(), true, false, 1300, DESCRIPTION_ACCESSOR); // descriptionColumn
        // descriptionColumn.setMinimumWidth(1300);
        fTableViewerCreator.setDefaultSort(fNameColumn, SORT.ASC);

        final Table table = fTableViewerCreator.getTable();
        table.addListener(SWT.MeasureItem, new Listener() {

            public void handleEvent(Event event) {
                TableItem item = (TableItem) event.item;
                String text = item.getText(event.index);
                Point size = event.gc.textExtent(text);
                if (event.index < 2) {
                    // event.width = 40;
                    if (WindowSystem.isGTK()) {
                        event.height = ICON_HEIGHT + 10;
                    } else {
                        event.height = ICON_HEIGHT + 6;
                    }
                }
            }
        });
    }

    @Override
    void addButtons() {
        fTableViewerCreator.getTableViewer().getControl().setRedraw(false);

        addUpdateButtons();
        addRemoveButtons();
        fTableViewerCreator.getTableViewer().getTable().layout();
        fTableViewerCreator.getTableViewer().refresh(true);

        fTableViewerCreator.getTableViewer().getControl().setRedraw(true);
    }

    /**
     * DOC chuang Comment method "addRemoveButtons".
     */
    private void addRemoveButtons() {
        disposeEditors(removeEditors);
        final Table table = fTableViewerCreator.getTable();
        for (final TableItem item : table.getItems()) {
            TableEditor editor = new TableEditor(table);
            removeEditors.add(editor);
            final Button button = new Button(table, SWT.FLAT);
            button.setImage(StatusImageProvider.getRemoveImage((ComponentExtension) item.getData()));
            button.setToolTipText(Messages.getString("InstalledEcoComponentsComposite.removeComponent")); //$NON-NLS-1$
            button.setData(item);
            if (button.getImage() == StatusImageProvider.REMOVE_ICON) {
                button.addSelectionListener(new SelectionAdapter() {

                    /*
                     * (non-Javadoc)
                     * 
                     * @see org.eclipse.swt.events.SelectionAdapter#widgetSelected
                     * (org.eclipse.swt.events.SelectionEvent)
                     */
                    @Override
                    public void widgetSelected(SelectionEvent e) {
                        button.setEnabled(false);
                        ComponentExtension component = (ComponentExtension) item.getData();

                        onRemoveButtonClick(button, component);
                    }

                });
            }
            // button.pack();
            editor.grabHorizontal = true;
            editor.minimumHeight = ICON_HEIGHT;
            editor.setEditor(button, item, REMOVE_BUTTON_COLUMN);
            editor.layout();
        }

    }

    /**
     * DOC chuang Comment method "onRemoveButtonClick".
     * 
     * @param button
     * @param component
     */
    private void onRemoveButtonClick(final Button button, final ComponentExtension component) {

        IRunnableWithProgress op = new IRunnableWithProgress() {

            public void run(IProgressMonitor monitor) throws InvocationTargetException, InterruptedException {
                monitor.beginTask(
                        Messages.getString("InstalledEcoComponentsComposite.removeComponentName", component.getName()), 100); //$NON-NLS-1$
                monitor.worked(10);
                EcosystemUtils.deleteComponent(component);
                monitor.worked(70);
                // update view
                EcosystemView view = EcosystemUtils.getEcosystemView();
                view.removeInstalledExtension(component);
                monitor.worked(10);
                saveToFile();
                button.setEnabled(true);
                view.refresh();
                monitor.done();

            }

        };
        try {
            new ProgressMonitorDialog(getShell()).run(false, false, op);
        } catch (Exception e) {
            // ignore me
        }
        StringBuilder message = new StringBuilder();
        message.append("Component ").append(component.getName()).append(" is successfully removed."); //$NON-NLS-1$ //$NON-NLS-2$
        MessageDialog.openInformation(getShell(),
                Messages.getString("InstalledEcoComponentsComposite.removeEcosystem"), message.toString()); //$NON-NLS-1$

    }

    public void removeInstalledExtension(ComponentExtension extension) {
        fInstalledExtensions.remove(extension.getName());
    }

    /**
     * DOC chuang Comment method "refresh".
     */
    protected void refresh() {
        fTableViewerCreator.setInputList(new ArrayList<ComponentExtension>(fInstalledExtensions.values()));
        // fTableViewerCreator.getTableViewer().refresh();
        addButtons();
    }

    /**
     * DOC chuang Comment method "addUpdateButtons".
     */
    private void addUpdateButtons() {
        disposeEditors(updateEditors);
        final Table table = fTableViewerCreator.getTable();
        for (final TableItem item : table.getItems()) {
            TableEditor editor = new TableEditor(table);
            updateEditors.add(editor);

            final Button button = new Button(table, SWT.FLAT);
            button.setImage(StatusImageProvider.getUpdateImage((ComponentExtension) item.getData()));
            button.setToolTipText(Messages.getString("InstalledEcoComponentsComposite.updateComponent")); //$NON-NLS-1$
            button.setData(item);
            if (button.getImage() == StatusImageProvider.UPDATE_ICON) {

                button.addSelectionListener(new SelectionAdapter() {

                    /*
                     * (non-Javadoc)
                     * 
                     * @see org.eclipse.swt.events.SelectionAdapter#widgetSelected
                     * (org.eclipse.swt.events.SelectionEvent)
                     */
                    @Override
                    public void widgetSelected(SelectionEvent e) {
                        button.setEnabled(false);
                        List<ComponentExtension> curExtensionItems = new ArrayList<ComponentExtension>();
                        curExtensionItems.add((ComponentExtension) item.getData());
                        UpdateComponenentsUtil update = new UpdateComponenentsUtil();
                        update.init(EcosystemUtils.getEcosystemView());
                        update.run(curExtensionItems);
                        button.setEnabled(true);

                    }

                });
            }
            // button.pack();
            editor.grabHorizontal = true;
            editor.minimumHeight = ICON_HEIGHT;
            editor.setEditor(button, item, UPDATE_BUTTON_COLUMN);
            editor.layout();
        }

    }

    /*
     * (non-Javadoc)
     * 
     * @seeorg.talend.designer.components.ecosystem.ui.views. AbstractEcoComponentsComposite
     * #onVersionFilterChanged(org.eclipse.swt.events.SelectionEvent)
     */
    @Override
    protected void onVersionFilterChanged(SelectionEvent e) {
        Combo comboControl = (Combo) e.getSource();
        value = comboControl.getText();
        IPreferenceStore preferenceStore = EcosystemPlugin.getDefault().getPreferenceStore();
        preferenceStore.setValue(EcosystemView.TOS_VERSION_FILTER, value);

        selectInstalledVersion();

    }

    private void selectInstalledVersion() {

        // versionCombo.setEnabled(false);
        Job job = new Job("refresh Installed Components") { //$NON-NLS-1$

            public IStatus run(IProgressMonitor monitor) {

                monitor.beginTask(
                        Messages.getString("InstalledEcoComponentsComposite.refreshComponent"), IProgressMonitor.UNKNOWN); //$NON-NLS-1$
                try {

                    compatible = ComponentSearcher.getAvailableComponentExtensions(value, EcosystemUtils.getCurrentLanguage());

                    final List<ComponentExtension> compatibleAndInstalled = new ArrayList<ComponentExtension>();
                    for (ComponentExtension installed : fInstalledExtensions.values()) {
                        // check if in compatible list
                        for (ComponentExtension curComp : compatible) {
                            if (curComp.getName().equals(installed.getName())) {
                                // if current version was latest
                                if (installed.getInstalledRevision() != null && curComp.getLatestRevision() != null
                                        && installed.getInstalledRevision().getName() != null
                                        && curComp.getLatestRevision().getName() != null) {
                                    double installedVersion = toDoubleversion(installed.getInstalledRevision().getName());
                                    double curCompVerision = toDoubleversion(curComp.getLatestRevision().getName());
                                    if (installedVersion <= curCompVerision) {
                                        installed.setLatestRevision(curComp.getLatestRevision());
                                    }
                                }
                                compatibleAndInstalled.add(installed);
                                break;
                            }
                        }
                    }

                    Runnable runnable = new Runnable() {

                        public void run() {
                            updateTable(compatibleAndInstalled);
                        }

                    };
                    Display display = PlatformUI.getWorkbench().getDisplay();
                    display.syncExec(runnable);

                } catch (Exception exc) {
                    // exc.printStackTrace();
                    ExceptionHandler.process(exc);
                } finally {
                    monitor.done();
                }
                return Status.OK_STATUS;
            }

        };

        job.setUser(true);
        job.schedule();
        // versionCombo.setEnabled(true);

    }

    private double toDoubleversion(String versinonString) {
        String[] VersionArray = versinonString.replaceAll("^\\B", "").split("\\."); //$NON-NLS-1$ //$NON-NLS-2$ //$NON-NLS-3$
        String newVersionString = VersionArray[0] + "."; //$NON-NLS-1$
        for (int i = 1; i < VersionArray.length; i++) {
            newVersionString = newVersionString + VersionArray[i];
        }

        double versionDouble = new Double(newVersionString);
        return versionDouble;
    }

}
