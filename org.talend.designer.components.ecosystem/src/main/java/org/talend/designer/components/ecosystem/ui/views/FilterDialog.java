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

import java.util.Arrays;

import org.eclipse.jface.viewers.ArrayContentProvider;
import org.eclipse.jface.viewers.CheckboxTableViewer;
import org.eclipse.jface.viewers.LabelProvider;
import org.eclipse.swt.SWT;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.ui.dialogs.SelectionDialog;
import org.talend.designer.components.ecosystem.EcosystemConstants;

/**
 * A dialog for user to select filters that will be used to search components.
 */
public class FilterDialog extends SelectionDialog {

    private String[] fSelectedFilters;

    private String[] fAvailableFilters;

    private CheckboxTableViewer fViewer;

    /**
     * FilterDialog constructor.
     * 
     * @param parentShell
     */
    protected FilterDialog(Shell parentShell, String[] availableFilters, String[] selectedFilters) {
        super(parentShell);
        fAvailableFilters = availableFilters;
        fSelectedFilters = selectedFilters;
        setShellStyle(getShellStyle() | SWT.RESIZE);
    }

    @Override
    protected void configureShell(Shell shell) {
        setTitle(EcosystemConstants.FILTER_DIALOG_TITLE);
        super.configureShell(shell);
    }

    /**
     * Create a table viewer with checkbox.
     * 
     * @see org.eclipse.jface.dialogs.Dialog#createDialogArea(Composite)
     */
    @Override
    protected Control createDialogArea(Composite parent) {
        fViewer = CheckboxTableViewer.newCheckList(parent, SWT.BORDER);
        fViewer.getTable().setLayoutData(new GridData(GridData.FILL_BOTH));
        fViewer.setContentProvider(new ArrayContentProvider());
        fViewer.setLabelProvider(new LabelProvider());
        fViewer.setInput(fAvailableFilters);
        fViewer.setCheckedElements(fSelectedFilters);

        return parent;
    }

    @Override
    protected void okPressed() {
        setResult(Arrays.asList(fViewer.getCheckedElements()));
        super.okPressed();
    }

}
