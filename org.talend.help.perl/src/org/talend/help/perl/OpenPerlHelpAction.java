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
package org.talend.help.perl;

import org.eclipse.jface.action.Action;
import org.eclipse.jface.action.IAction;
import org.eclipse.jface.viewers.ISelection;
import org.eclipse.swt.widgets.Display;
import org.eclipse.ui.IWorkbenchWindow;
import org.eclipse.ui.IWorkbenchWindowActionDelegate;
import org.eclipse.ui.PlatformUI;

/**
 * OpenPerlHelpAction.java.
 * 
 */
public class OpenPerlHelpAction extends Action implements IWorkbenchWindowActionDelegate {

    public OpenPerlHelpAction() {
        super();
    }

    public void run() {
        Display workbenchDisplay = PlatformUI.getWorkbench().getDisplay();
        PerlHelpDialog dialog = new PerlHelpDialog(workbenchDisplay.getActiveShell());
        dialog.open();
    }

    public void selectionChanged(IAction action, ISelection selection) {
    }

    public void dispose() {
    }

    public void run(IAction action) {
        run();
    }

    public void init(IWorkbenchWindow window) {
    }

}
