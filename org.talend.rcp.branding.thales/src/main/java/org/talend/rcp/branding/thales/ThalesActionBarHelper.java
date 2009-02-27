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

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang.ArrayUtils;
import org.eclipse.jface.action.IMenuManager;
import org.eclipse.ui.IPerspectiveDescriptor;
import org.eclipse.ui.application.IWorkbenchWindowConfigurer;
import org.eclipse.ui.internal.registry.PerspectiveDescriptor;
import org.eclipse.ui.internal.registry.PerspectiveRegistry;
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
        super.fillMenuBar(menuBar);
    }

    /*
     * (non-Javadoc)
     * 
     * @see
     * org.talend.rcp.intro.ActionBarBuildHelper#preWindowOpen(org.eclipse.ui.application.IWorkbenchWindowConfigurer)
     */
    @Override
    public void preWindowOpen(IWorkbenchWindowConfigurer configurer) {
        super.preWindowOpen(configurer);
        configurer.setShowPerspectiveBar(false);
    }

    /*
     * (non-Javadoc)
     * 
     * @see org.talend.rcp.intro.ActionBarBuildHelper#postWindowOpen()
     */
    @Override
    public void postWindowOpen() {
        super.postWindowOpen();
        String[] removeIds = { "perspective", "org.talend.rcp.intro.ShowViewAction" };
        for (String id : removeIds) {
            windowMenu.remove(id);
        }

        String[] removeHelpIds = { "org.eclipse.core.internal.registry.ConfigurationElementHandle@1a8a", "org.talend.help.perl",
                "org.eclipse.ui.actionSet.keyBindings", "org.talend.help.perl.OpenPerlHelpAction" };
        for (String id : removeHelpIds) {
            helpMenu.remove(id);
        }

        String[] perspectivesId = { "org.eclipse.debug.ui.DebugPerspective", "org.eclipse.jdt.ui.JavaPerspective",
                "org.eclipse.jdt.ui.JavaHierarchyPerspective", "org.eclipse.jdt.ui.JavaBrowsingPerspective",
                "org.eclipse.team.ui.TeamSynchronizingPerspective", "org.eclipse.ui.resourcePerspective",
                "org.epic.core.Perspective", "org.eclipse.pde.ui.PDEPerspective" };

        List<IPerspectiveDescriptor> perspectivesToDelete = new ArrayList<IPerspectiveDescriptor>();

        for (IPerspectiveDescriptor desc : window.getWorkbench().getPerspectiveRegistry().getPerspectives()) {
            if (ArrayUtils.contains(perspectivesId, desc.getId())) {
                perspectivesToDelete.add(desc);
            }
        }

        for (IPerspectiveDescriptor desc : perspectivesToDelete) {
            PerspectiveDescriptor perspDesc = (PerspectiveDescriptor) desc;
            PerspectiveRegistry registry = (PerspectiveRegistry) window.getWorkbench().getPerspectiveRegistry();
            PerspectiveDescriptor[] descriptors = { perspDesc };
            registry.removeExtension(perspDesc.getConfigElement().getDeclaringExtension(), descriptors);
        }
    }
}
