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

import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.ArrayUtils;
import org.apache.commons.lang.StringUtils;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.jface.operation.IRunnableWithProgress;
import org.eclipse.jface.window.Window;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.KeyAdapter;
import org.eclipse.swt.events.KeyEvent;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Link;
import org.eclipse.swt.widgets.TableItem;
import org.eclipse.swt.widgets.Text;
import org.eclipse.ui.PlatformUI;
import org.eclipse.ui.part.ViewPart;
import org.talend.commons.exception.ExceptionHandler;
import org.talend.designer.components.ecosystem.EcosystemConstants;
import org.talend.designer.components.ecosystem.EcosystemUtils;
import org.talend.designer.components.ecosystem.jobs.ComponentSearcher;
import org.talend.designer.components.ecosystem.model.ComponentExtension;
import org.talend.designer.components.ecosystem.model.EcosystemPackage;
import org.talend.designer.components.ecosystem.model.Revision;
import org.talend.designer.components.ecosystem.ui.actions.RefreshJob;

/**
 * View part for ecosystem.
 */
public class EcosystemView extends ViewPart {

    private static final String[] AVAILABLE_FILTERS = new String[] { "Name", "Description" }; //$NON-NLS-1$ //$NON-NLS-2$

    private static final Map<String, Integer> FILTER_MAP = new HashMap<String, Integer>();

    private EcosystemViewComposite fEcosystemViewComposite;

    private List<ComponentExtension> fAvailableExtensions;

    private Map<String, ComponentExtension> fInstalledExtensions = new HashMap<String, ComponentExtension>();

    private String[] fFilters = AVAILABLE_FILTERS;

    private Text fFilterText;

    public static final String VIEW_ID = "org.talend.designer.components.ecosystem.ui.views.EcosystemView";

    static {
        FILTER_MAP.put(AVAILABLE_FILTERS[0], EcosystemPackage.COMPONENT_EXTENSION__NAME);
        FILTER_MAP.put(AVAILABLE_FILTERS[1], EcosystemPackage.COMPONENT_EXTENSION__DESCRIPTION);
    }

    /*
     * (non-Javadoc)
     * 
     * @see org.eclipse.ui.part.WorkbenchPart#createPartControl(org.eclipse.swt.widgets.Composite)
     */
    @Override
    public void createPartControl(Composite parent) {

        parent.setLayout(clearGridLayoutSpace(new GridLayout(3, false)));

        Label filterLabel = new Label(parent, SWT.NONE);
        filterLabel.setText(EcosystemConstants.FILTER_LABEL_TEXT);
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
        filterLink.setText("<a href=\"\">" + EcosystemConstants.FILTER_LINK_TEXT + "</a>");
        filterLink.addSelectionListener(new SelectionAdapter() {

            @Override
            public void widgetSelected(final SelectionEvent e) {
                openFilterDialog();
            }
        });

        fEcosystemViewComposite = new EcosystemViewComposite(parent);
        fEcosystemViewComposite.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, true, 3, 1));

        init();
    }

    /**
     * Initialize the contents that will display on this view.
     */
    private void init() {
        // load installed component information from file
        loadFromFile();
        findAvailableComponentExtensions();
        refresh();
    }

    /**
     * Search components according to the user input and selected filters.
     */
    private void applyFilters() {
        if (ArrayUtils.isEmpty(fFilters) || StringUtils.isEmpty(fFilterText.getText())) {
            fEcosystemViewComposite.updateTable(fAvailableExtensions);
            return;
        }
        int[] featureId = new int[fFilters.length];
        for (int i = 0; i < fFilters.length; i++) {
            featureId[i] = FILTER_MAP.get(fFilters[i]);
        }
        List<ComponentExtension> found;
        try {
            found = ComponentSearcher.filterComponentExtensions(fAvailableExtensions, fFilterText.getText(), featureId);
            fEcosystemViewComposite.updateTable(found);
        } catch (Exception e) {
            ExceptionHandler.process(e);
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
     * Load installed components information from file.
     */
    public void loadFromFile() {
        try {
            List<ComponentExtension> extensions = EcosystemUtils.loadInstallComponents(EcosystemConstants.COMPONENT_MODEL_FILE);
            for (ComponentExtension ext : extensions) {
                fInstalledExtensions.put(ext.getName(), ext);
            }
        } catch (Throwable e) {
            // do nothing, the file may not exist because this is the first time we use this view and we haven't
            // installed any extensions
        }
    }

    /**
     * Open a dialog for user to select filters.
     */
    protected void openFilterDialog() {
        FilterDialog dialog = new FilterDialog(this.getSite().getShell(), AVAILABLE_FILTERS, fFilters);
        if (dialog.open() == Window.OK) {
            Object[] result = dialog.getResult();
            fFilters = new String[result.length];
            for (int i = 0; i < result.length; i++) {
                fFilters[i] = result[i].toString();
            }
        }
    }

    private GridLayout clearGridLayoutSpace(GridLayout layout) {
        layout.horizontalSpacing = 3;
        layout.verticalSpacing = 0;
        layout.marginWidth = 0;
        layout.marginHeight = 0;
        return layout;
    }

    private void findAvailableComponentExtensions() {
        final RefreshJob job = new RefreshJob();
        try {
            PlatformUI.getWorkbench().getProgressService().busyCursorWhile(new IRunnableWithProgress() {

                public void run(IProgressMonitor monitor) throws InvocationTargetException, InterruptedException {
                    job.run(monitor);
                }

            });
        } catch (Exception e) {
            ExceptionHandler.process(e);
        }

        fAvailableExtensions = job.getAvailableExtensions();
        // update status of installed extensions
        checkInstalledExtensions();
        fEcosystemViewComposite.initTable(fAvailableExtensions);
    }

    /*
     * (non-Javadoc)
     * 
     * @see org.eclipse.ui.part.WorkbenchPart#setFocus()
     */
    @Override
    public void setFocus() {
        if (fEcosystemViewComposite != null) {
            fEcosystemViewComposite.setFocus();
        }
    }

    /**
     * Refresh the table in this view, as the component status may have changed.
     */
    public void refresh() {
        fEcosystemViewComposite.refresh();
    }

    public List<ComponentExtension> getSelectedExtensions() {
        return getExtensionsFromTableItems(fEcosystemViewComposite.getSelectedItems());
    }

    /**
     * Get selected components from table items.
     * 
     * @return
     */
    private List<ComponentExtension> getExtensionsFromTableItems(TableItem[] items) {
        List<ComponentExtension> selected = new ArrayList<ComponentExtension>();
        for (TableItem item : items) {
            selected.add((ComponentExtension) item.getData());
        }
        return selected;
    }

    public void updateAvailableExtensions(List<ComponentExtension> extensions) {
        fAvailableExtensions = extensions;
        // update status of installed extensions
        checkInstalledExtensions();
        fEcosystemViewComposite.updateTable(fAvailableExtensions);
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

    public void removeInstalledExtension(ComponentExtension extension) {
        fInstalledExtensions.remove(extension.getName());
        int pos = fAvailableExtensions.indexOf(extension);
        fAvailableExtensions.get(pos).setInstalledRevision(null);
        fAvailableExtensions.get(pos).setInstalledLocation(null);
    }

    /**
     * Update the component status if we have already installed.
     */
    private void checkInstalledExtensions() {
        if (fInstalledExtensions == null || fAvailableExtensions == null) {
            return;
        }

        for (ComponentExtension available : fAvailableExtensions) {
            ComponentExtension installed = fInstalledExtensions.get(available.getName());
            if (installed != null) {
                // find installed revision in revisions list
                for (Revision rev : available.getRevisions()) {
                    if (rev.getName().equals(installed.getInstalledRevision().getName())) {
                        available.setInstalledRevision(rev);
                        available.setInstalledLocation(installed.getInstalledLocation());
                        break;
                    }
                }
            }
        }
    }

}
