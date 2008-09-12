// ============================================================================
//
// Copyright (C) 2006-2008 Talend Inc. - www.talend.com
//
// This source code is available under agreement available at
// %InstallDIR%\features\org.talend.rcp.branding.%PRODUCTNAME%\%PRODUCTNAME%license.txt
//
// You should have received a copy of the agreement
// along with this program; if not, write to Talend SA
// 9 rue Pages 92150 Suresnes, France
//
// ============================================================================
package org.talend.designer.components.ecosystem.model.util;

import org.eclipse.jface.action.ActionContributionItem;
import org.eclipse.jface.action.IAction;
import org.eclipse.jface.action.IContributionItem;
import org.eclipse.ui.PlatformUI;
import org.talend.designer.components.ecosystem.ui.views.EcosystemView;

/**
 * DOC YeXiaowei class global comment. Detailled comment
 */
public class ActionHelper {

    /**
     * 
     * DOC YeXiaowei Comment method "getEcosystemView".
     * 
     * @return
     */
    public static EcosystemView getEcosystemView() {
        return (EcosystemView) PlatformUI.getWorkbench().getActiveWorkbenchWindow().getActivePage().findView(
                "org.talend.designer.components.ecosystem.ui.views.EcosystemView");
    }

    /**
     * 
     * DOC YeXiaowei Comment method "getDownloadAction".
     * 
     * @return
     */
    public static IAction getDownloadAction() {
        IContributionItem item = getEcosystemView().getViewSite().getActionBars().getToolBarManager().find(
                "nts.ecosystem.ui.actions.DownloadComponenentsAction");
        IAction action = ((ActionContributionItem) item).getAction();
        return action;
    }
}
