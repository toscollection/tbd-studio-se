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

import java.util.List;

import org.eclipse.swt.SWT;
import org.eclipse.swt.custom.CTabFolder;
import org.eclipse.swt.custom.CTabItem;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.events.SelectionListener;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.ui.IViewPart;
import org.eclipse.ui.IWorkbenchPage;
import org.eclipse.ui.IWorkbenchWindow;
import org.eclipse.ui.PlatformUI;
import org.eclipse.ui.part.ViewPart;
import org.talend.designer.components.ecosystem.model.ComponentExtension;
import org.talend.designer.core.ui.views.IEcosystemView;

/**
 * View part for ecosystem.
 */
public class EcosystemView extends ViewPart implements IEcosystemView {

    private CompatibleEcoComponentsComposite fEcosystemViewComposite;

    public static final String TOS_VERSION_FILTER = "TOS_VERSION_FILTER"; //$NON-NLS-1$

    private CTabFolder tab;

    private InstalledEcoComponentsComposite fInstalledComposite;

    /*
     * (non-Javadoc)
     * 
     * @see org.eclipse.ui.part.WorkbenchPart#createPartControl(org.eclipse.swt.widgets .Composite)
     */
    @Override
    public void createPartControl(Composite parent) {

        parent.setLayout(clearGridLayoutSpace(new GridLayout()));

        tab = new CTabFolder(parent, SWT.FLAT | SWT.BORDER);
        // tab.setBackground(parent.getBackground());
        tab.setLayoutData(new GridData(GridData.FILL_BOTH));
        CTabItem compatibleTab = new CTabItem(tab, SWT.NONE);
        compatibleTab.setText("compatible components");

        final CTabItem installedTab = new CTabItem(tab, SWT.NONE);
        installedTab.setText("installed components");

        Shell shell = this.getViewSite().getShell();
        fEcosystemViewComposite = new CompatibleEcoComponentsComposite(tab, shell);
        fEcosystemViewComposite.setLayoutData(new GridData(GridData.FILL_BOTH));

        fInstalledComposite = new InstalledEcoComponentsComposite(tab, shell);
        fInstalledComposite.setLayoutData(new GridData(GridData.FILL_BOTH));

        compatibleTab.setControl(fEcosystemViewComposite);
        installedTab.setControl(fInstalledComposite);
        tab.setSelection(compatibleTab);

        SelectionListener tabListener = new SelectionListener() {

            public void widgetDefaultSelected(SelectionEvent e) {
                widgetSelected(e);
            }

            public void widgetSelected(SelectionEvent e) {
                onTabChanged(installedTab);
            }

        };
        tab.addSelectionListener(tabListener);

    }

    /**
     * DOC chuang Comment method "onTabChanged".
     * 
     * @param installedTab
     */
    private void onTabChanged(final CTabItem installedTab) {
        if (tab.getSelection() == installedTab) {
            // fInstalledComposite.init();
            // EcosystemUtils.findViewAction(DownloadComponenentsAction.ID)
            // .setEnabled(false);
            // EcosystemUtils.findViewAction(RefreshComponenentsAction.ID)
            // .setEnabled(false);
        } else {
            // EcosystemUtils.findViewAction(DownloadComponenentsAction.ID)
            // .setEnabled(true);
            // EcosystemUtils.findViewAction(RefreshComponenentsAction.ID)
            // .setEnabled(true);

        }
    }

    private GridLayout clearGridLayoutSpace(GridLayout layout) {
        layout.horizontalSpacing = 3;
        layout.verticalSpacing = 0;
        layout.marginWidth = 0;
        layout.marginHeight = 0;
        return layout;
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
        fInstalledComposite.refresh();
    }

    public List<ComponentExtension> getSelectedExtensions() {
        return fEcosystemViewComposite.getSelectedExtensions();
    }

    public void updateAvailableExtensions(List<ComponentExtension> extensions) {
        fEcosystemViewComposite.updateAvailableExtensions(extensions);
    }

    /**
     * @return the installedExtensions
     */
    public List<ComponentExtension> getInstalledExtensions() {
        return fEcosystemViewComposite.getInstalledExtensions();
    }

    public void addInstalledExtension(ComponentExtension extension) {
        fEcosystemViewComposite.addInstalledExtension(extension);
        fInstalledComposite.addInstalledExtension(extension);
    }

    public void removeInstalledExtension(ComponentExtension extension) {
        fEcosystemViewComposite.removeInstalledExtension(extension);
        fInstalledComposite.removeInstalledExtension(extension);
    }

    public void refreshVersions() {
        fEcosystemViewComposite.initTosVersionFilter();
        fInstalledComposite.initTosVersionFilter();
    }

    public static IEcosystemView getEcosystemView() {
        IWorkbenchWindow activeWorkbenchWindow = PlatformUI.getWorkbench().getActiveWorkbenchWindow();
        if (activeWorkbenchWindow != null) {
            IWorkbenchPage activePage = activeWorkbenchWindow.getActivePage();
            if (activePage != null) {
                IViewPart findView = activePage.findView(IEcosystemView.VIEW_ID);
                if (findView instanceof IEcosystemView) {
                    return (IEcosystemView) findView;
                }
            }
        }
        return null;
    }
}
