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
package org.talend.rcp.branding.thales;

import org.eclipse.jface.action.IMenuManager;
import org.talend.rcp.intro.ActionBarBuildHelper;

/**
 * DOC aiming class global comment. Detailled comment
 */
public class ThalesActionBarHelper extends ActionBarBuildHelper {

    /*
     * (non-Javadoc)
     * 
     * @see org.talend.rcp.intro.ActionBarBuildHelper#fillMenuBar(org.eclipse.jface.action.IMenuManager)
     */
    @Override
    public void fillMenuBar(IMenuManager menuBar) {
        // TODO Auto-generated method stub
        super.fillMenuBar(menuBar);
    }

    /*
     * (non-Javadoc)
     * 
     * @see org.talend.rcp.intro.ActionBarBuildHelper#hideActions()
     */
    @Override
    public void hideActions() {
        super.hideActions();
        String[] removeIds = { "perspective" };// 
        for (String id : removeIds) {
            windowMenu.remove(id);
        }
    }
}
