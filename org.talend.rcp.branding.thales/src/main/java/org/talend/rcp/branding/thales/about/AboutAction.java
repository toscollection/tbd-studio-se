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
package org.talend.rcp.branding.thales.about;

import org.eclipse.core.runtime.IProduct;
import org.eclipse.core.runtime.Platform;
import org.eclipse.jface.action.Action;
import org.eclipse.osgi.util.NLS;
import org.eclipse.ui.IWorkbenchWindow;
import org.eclipse.ui.PlatformUI;
import org.eclipse.ui.actions.ActionFactory;
import org.eclipse.ui.internal.IWorkbenchHelpContextIds;
import org.eclipse.ui.internal.WorkbenchMessages;

/**
 * DOC nrousseau class global comment. Detailled comment
 */
public class AboutAction extends Action implements ActionFactory.IWorkbenchAction {

    private static final String ACTION_ID = "org.talend.rcp.branding.thales.about.aboutAction"; //$NON-NLS-1$

    /**
     * Constructs a new ShowViewAction.
     */
    public AboutAction() {
        super(NLS.bind(WorkbenchMessages.AboutAction_text, Platform.getProduct() == null ? "" : Platform.getProduct().getName())); //$NON-NLS-1$
        setId(ACTION_ID);

        IProduct product = Platform.getProduct();
        String productName = null;
        if (product != null) {
            productName = product.getName();
        }
        if (productName == null) {
            productName = ""; //$NON-NLS-1$
        }
        workbenchWindow = PlatformUI.getWorkbench().getActiveWorkbenchWindow();
        setText(NLS.bind(WorkbenchMessages.AboutAction_text, productName));
        setToolTipText(NLS.bind(WorkbenchMessages.AboutAction_toolTip, productName));
        setId("about"); //$NON-NLS-1$
        setActionDefinitionId("org.eclipse.ui.help.aboutAction"); //$NON-NLS-1$
        PlatformUI.getWorkbench().getHelpSystem().setHelp(this, IWorkbenchHelpContextIds.ABOUT_ACTION);
    }

    /**
     * The workbench window; or <code>null</code> if this action has been <code>dispose</code>d.
     */
    private IWorkbenchWindow workbenchWindow;

    /**
     * Creates a new <code>AboutAction</code>.
     * 
     * @param window the window
     */
    public AboutAction(IWorkbenchWindow window) {
        if (window == null) {
            throw new IllegalArgumentException();
        }

        this.workbenchWindow = window;

        // use message with no fill-in
        IProduct product = Platform.getProduct();
        String productName = null;
        if (product != null) {
            productName = product.getName();
        }
        if (productName == null) {
            productName = ""; //$NON-NLS-1$
        }
        setText(NLS.bind(WorkbenchMessages.AboutAction_text, productName));
        setToolTipText(NLS.bind(WorkbenchMessages.AboutAction_toolTip, productName));
        setId("about"); //$NON-NLS-1$
        setActionDefinitionId("org.eclipse.ui.help.aboutAction"); //$NON-NLS-1$
        window.getWorkbench().getHelpSystem().setHelp(this, IWorkbenchHelpContextIds.ABOUT_ACTION);
    }

    /*
     * (non-Javadoc) Method declared on IAction.
     */
    public void run() {
        if (workbenchWindow == null) {
            workbenchWindow = PlatformUI.getWorkbench().getActiveWorkbenchWindow();
        }
        // make sure action is not disposed
        if (workbenchWindow != null) {
            new AboutDialog(workbenchWindow.getShell()).open();
        }
    }

    /*
     * (non-Javadoc) Method declared on ActionFactory.IWorkbenchAction.
     */
    public void dispose() {
        workbenchWindow = null;
    }
}
