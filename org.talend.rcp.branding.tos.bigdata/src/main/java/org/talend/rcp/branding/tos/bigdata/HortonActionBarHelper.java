// ============================================================================
//
// Copyright (C) 2006-2012 Talend Inc. - www.talend.com
//
// This source code is available under agreement available at
// %InstallDIR%\features\org.talend.rcp.branding.%PRODUCTNAME%\%PRODUCTNAME%license.txt
//
// You should have received a copy of the agreement
// along with this program; if not, write to Talend SA
// 9 rue Pages 92150 Suresnes, France
//
// ============================================================================
package org.talend.rcp.branding.tos.bigdata;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang.ArrayUtils;
import org.eclipse.jface.preference.IPreferenceNode;
import org.eclipse.ui.IPerspectiveDescriptor;
import org.eclipse.ui.internal.registry.PerspectiveDescriptor;
import org.eclipse.ui.internal.registry.PerspectiveRegistry;
import org.eclipse.ui.internal.registry.ViewDescriptor;
import org.eclipse.ui.internal.registry.ViewRegistry;
import org.eclipse.ui.views.IViewDescriptor;
import org.talend.rcp.intro.ActionBarBuildHelper;

/**
 * DOC hwang class global comment. Detailled comment
 */
public class HortonActionBarHelper extends ActionBarBuildHelper {

    @Override
    public void postWindowOpen() {
        super.postWindowOpen();
        String[] removeIds = { "perspective" /* , "org.talend.rcp.intro.ShowViewAction" */};
        for (String id : removeIds) {
            windowMenu.remove(id);
        }

        String[] removeHelpIds = { "org.eclipse.core.internal.registry.ConfigurationElementHandle@1a8a",
                "org.eclipse.ui.actionSet.keyBindings" };
        for (String id : removeHelpIds) {
            helpMenu.remove(id);
        }

        String[] perspectivesId = { "org.eclipse.jdt.ui.JavaPerspective", "org.eclipse.team.ui.TeamSynchronizingPerspective",
                "org.eclipse.ui.resourcePerspective", "org.eclipse.pde.ui.PDEPerspective" };

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

        String[] viewsId = { "org.eclipse.ant.ui.views.AntView", "org.eclipse.pde.runtime.RegistryBrowser",
                "org.eclipse.pde.ui.DependenciesView", "org.eclipse.pde.ui.PluginsView", "org.eclipse.search.SearchResultView",
                "org.eclipse.search.ui.views.SearchView", "org.eclipse.team.sync.views.SynchronizeView",
                "org.eclipse.team.ui.GenericHistoryView", "org.eclipse.ui.browser.view",
                "org.eclipse.ui.cheatsheets.views.CheatSheetView", "org.eclipse.ui.texteditor.TemplatesView",
                "org.eclipse.ui.views.AllMarkersView", "org.eclipse.ui.views.BookmarkView",
                "org.eclipse.team.sync.views.SynchronizeView", "org.eclipse.ui.views.ProblemView",
                "org.eclipse.ui.views.PropertySheet", "org.eclipse.ui.views.TaskList",
                "org.eclipse.ui.navigator.ProjectExplorer", "org.eclipse.wst.common.snippets.internal.ui.SnippetsView" };

        List<IViewDescriptor> viewsToDelete = new ArrayList<IViewDescriptor>();

        for (IViewDescriptor desc : window.getWorkbench().getViewRegistry().getViews()) {
            if (ArrayUtils.contains(viewsId, desc.getId())) {
                viewsToDelete.add(desc);
            }
        }

        for (IViewDescriptor desc : viewsToDelete) {
            ViewDescriptor viewDesc = (ViewDescriptor) desc;
            ViewRegistry registry = (ViewRegistry) window.getWorkbench().getViewRegistry();
            ViewDescriptor[] descriptors = { viewDesc };
            registry.removeExtension(viewDesc.getConfigurationElement().getDeclaringExtension(), descriptors);
        }

        String[] prefsId = { "org.eclipse.ant.ui.AntPreferencePage",
                "org.eclipse.datatools.connectivity.internal.ui.preferences.DataToolsMainPage",
                "org.eclipse.debug.ui.DebugPreferencePage", "org.eclipse.team.ui.TeamPreferences",
                "org.eclipse.pde.ui.MainPreferencePage" };
        List<IPreferenceNode> prefsToDelete = new ArrayList<IPreferenceNode>();
        for (IPreferenceNode node : window.getWorkbench().getPreferenceManager().getRootSubNodes()) {
            if (ArrayUtils.contains(prefsId, node.getId())) {
                prefsToDelete.add(node);
            }
        }
        for (IPreferenceNode node : prefsToDelete) {
            window.getWorkbench().getPreferenceManager().remove(node);
        }
    }

}
