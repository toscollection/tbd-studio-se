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
import org.eclipse.core.runtime.Plugin;
import org.eclipse.jface.action.IMenuManager;
import org.eclipse.jface.preference.IPreferenceNode;
import org.eclipse.ui.IPerspectiveDescriptor;
import org.eclipse.ui.application.IWorkbenchWindowConfigurer;
import org.eclipse.ui.internal.registry.PerspectiveDescriptor;
import org.eclipse.ui.internal.registry.PerspectiveRegistry;
import org.eclipse.ui.internal.registry.ViewDescriptor;
import org.eclipse.ui.internal.registry.ViewRegistry;
import org.eclipse.ui.views.IViewDescriptor;
import org.eclipse.update.internal.core.UpdateCore;
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
        Plugin plugin = UpdateCore.getPlugin();
        plugin.getPluginPreferences().setDefault(UpdateCore.P_CHECK_SIGNATURE, false);
    }

    /*
     * (non-Javadoc)
     * 
     * @see org.talend.rcp.intro.ActionBarBuildHelper#postWindowOpen()
     */
    @Override
    public void postWindowOpen() {
        super.postWindowOpen();
        String[] removeIds = { "perspective" /* , "org.talend.rcp.intro.ShowViewAction" */};
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

        // IWorkbenchActivitySupport workbenchActivitySupport = PlatformUI.getWorkbench().getActivitySupport();
        //
        // Set<String> triggerPointsIds = workbenchActivitySupport.getTriggerPointManager().getDefinedTriggerPointIds();
        // for (String triggerId : triggerPointsIds) {
        // ITriggerPoint triggerPoint = workbenchActivitySupport.getTriggerPointManager().getTriggerPoint(triggerId);
        // // triggerPoint.
        // }

        // IExtensionPoint[] extensionPoints = Platform.getExtensionRegistry().getExtensionPoints();
        // for (IExtensionPoint extension : extensionPoints) {
        // IConfigurationElement[] configElements = extension.getConfigurationElements();
        // for (IConfigurationElement configElement : configElements) {
        // System.out.println("congigElement:" + configElement.getNamespaceIdentifier() + "/" +
        // configElement.getName());
        // }
        // }

        String[] viewsId = { "org.eclipse.ant.ui.views.AntView", "org.eclipse.debug.expressionview",
                "org.eclipse.debug.ui.BreakpointView", "org.eclipse.debug.ui.DebugView", "org.eclipse.debug.ui.ExpressionView",
                "org.eclipse.debug.ui.MemoryView", "org.eclipse.debug.ui.ModuleView", "org.eclipse.debug.ui.RegisterView",
                "org.eclipse.debug.ui.VariableView", "org.eclipse.jdt.callhierarchy.view",
                "org.eclipse.jdt.debug.ui.DisplayView", "org.eclipse.jdt.junit.ResultView", "org.eclipse.jdt.ui.JavadocView",
                "org.eclipse.jdt.ui.MembersView", "org.eclipse.jdt.ui.PackageExplorer", "org.eclipse.jdt.ui.PackagesView",
                "org.eclipse.jdt.ui.ProjectsView", "org.eclipse.jdt.ui.SourceView", "org.eclipse.jdt.debug.ui.DisplayView",
                "org.eclipse.jdt.junit.ResultView", "org.eclipse.jdt.ui.TypeHierarchy", "org.eclipse.jdt.ui.TypesView",
                "org.eclipse.pde.runtime.RegistryBrowser", "org.eclipse.pde.ui.DependenciesView",
                "org.eclipse.pde.ui.PluginsView", "org.eclipse.search.SearchResultView",
                "org.eclipse.search.ui.views.SearchView", "org.eclipse.team.sync.views.SynchronizeView",
                "org.eclipse.team.ui.GenericHistoryView", "org.eclipse.ui.browser.view",
                "org.eclipse.ui.cheatsheets.views.CheatSheetView", "org.eclipse.ui.console.ConsoleView",
                "org.eclipse.ui.texteditor.TemplatesView", "org.eclipse.ui.views.AllMarkersView",
                "org.eclipse.ui.views.BookmarkView", "org.eclipse.team.sync.views.SynchronizeView",
                "org.eclipse.ui.views.ContentOutline", "org.eclipse.ui.views.ProblemView", "org.eclipse.ui.views.PropertySheet",
                "org.eclipse.ui.console.ConsoleView", "org.eclipse.ui.views.TaskList", "org.epic.core.views.browser.BrowserView",
                "org.epic.perleditor.views.ExplainErrorsView", "org.epic.perleditor.views.PerlDocView",
                "org.epic.regexp.views.RegExpView", "org.talend.designer.core.codeView", "org.talend.scheduler.views.Scheduler",
                "org.eclipse.ui.navigator.ProjectExplorer" };

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
                "org.epic.core.preferences.PerlMainPreferencePage", "org.eclipse.pde.ui.MainPreferencePage" };
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
