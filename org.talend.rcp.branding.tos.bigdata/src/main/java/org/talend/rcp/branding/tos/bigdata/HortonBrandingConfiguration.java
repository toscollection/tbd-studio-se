// ============================================================================
//
// Copyright (C) 2006-2014 Talend Inc. - www.talend.com
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

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.eclipse.jface.action.ICoolBarManager;
import org.eclipse.jface.action.IMenuManager;
import org.eclipse.jface.preference.PreferenceManager;
import org.eclipse.ui.IFolderLayout;
import org.eclipse.ui.IPageLayout;
import org.eclipse.ui.PlatformUI;
import org.talend.core.language.ECodeLanguage;
import org.talend.core.model.repository.ERepositoryObjectType;
import org.talend.core.ui.branding.DefaultBrandingConfiguration;
import org.talend.core.ui.branding.IBrandingConfiguration;
import org.talend.repository.model.IRepositoryNode;
import org.talend.repository.model.IRepositoryNode.ENodeType;
import org.talend.repository.model.IRepositoryNode.EProperties;
import org.talend.repository.model.RepositoryNode;
import org.talend.repository.ui.views.IRepositoryView;

/**
 * DOC hwang class global comment. Detailled comment
 */
public class HortonBrandingConfiguration extends DefaultBrandingConfiguration {

    /*
     * (non-Jsdoc)
     * 
     * @see org.talend.core.ui.branding.IActionBarHelper#fillMenuBar(org.eclipse.jface.action.IMenuManager)
     */
    public void fillMenuBar(IMenuManager menuBar) {
    }

    /*
     * (non-Jsdoc)
     * 
     * @see org.talend.core.ui.branding.IActionBarHelper#fillCoolBar(org.eclipse.jface.action.ICoolBarManager)
     */
    public void fillCoolBar(ICoolBarManager coolBar) {
        coolBar.removeAll();
    }

    /*
     * (non-Jsdoc)
     * 
     * @see org.talend.core.ui.branding.IBrandingConfiguration#getAvailableComponents()
     */
    public String[] getAvailableComponents() {
        return null;
    }

    /*
     * (non-Jsdoc)
     * 
     * @see org.talend.core.ui.branding.IBrandingConfiguration#getAvailableLanguages()
     */
    public String[] getAvailableLanguages() {
        return new String[] { ECodeLanguage.JAVA.getName() };
    }

    /*
     * (non-Jsdoc)
     * 
     * @see org.talend.core.ui.branding.IBrandingConfiguration#getJobEditorSettings()
     */
    public Map<String, Object> getJobEditorSettings() {
        return new HashMap<String, Object>();
    }

    /*
     * (non-Jsdoc)
     * 
     * @see org.talend.core.ui.branding.IBrandingConfiguration#isUseMailLoginCheck()
     */
    public boolean isUseMailLoginCheck() {
        return true;
    }

    /*
     * (non-Jsdoc)
     * 
     * @see org.talend.core.ui.branding.IBrandingConfiguration#isUseProductRegistration()
     */
    public boolean isUseProductRegistration() {
        return true;
    }

    /*
     * (non-Jsdoc)
     * 
     * @see org.talend.core.ui.branding.IBrandingConfiguration#isAllowDebugMode()
     */
    public boolean isAllowDebugMode() {
        return true;
    }

    /*
     * (non-Jsdoc)
     * 
     * @see org.talend.core.ui.branding.IBrandingConfiguration#isUseDemoProjects()
     */
    public boolean isUseDemoProjects() {
        return false;
    }

    /*
     * (non-Jsdoc)
     * 
     * @see org.talend.core.ui.branding.IBrandingConfiguration#getAdditionalTitle()
     */
    public String getAdditionalTitle() {
        return "";
    }

    /*
     * (non-Jsdoc)
     * 
     * @see org.talend.core.ui.branding.IBrandingConfiguration#setAdditionalTitle(java.lang.String)
     */
    public void setAdditionalTitle(String title) {

    }

    /*
     * (non-Jsdoc)
     * 
     * @see org.talend.core.ui.branding.IBrandingConfiguration#getInitialWindowPerspectiveId()
     */
    public String getInitialWindowPerspectiveId() {
        PreferenceManager pm = PlatformUI.getWorkbench().getPreferenceManager();
        pm.find("org.talend.core.prefs").remove("org.talend.repository.prefs.documentation");
        pm.find("org.talend.core.prefs").findSubNode("org.talend.designer.core.ui.preferences.SpecificSettingPreferencePage")
                .remove("org.talend.sqlbuilder.ui.prefs.sqlbuilder");
        pm.find("org.talend.core.prefs").remove("org.talend.designer.core.ui.preferences.PerformancePreferencePage");
        return IBrandingConfiguration.PERSPECTIVE_DI_ID;
    }

    /*
     * (non-Jsdoc)
     * 
     * @see org.talend.core.ui.branding.IBrandingConfiguration#setUseMailLoginCheck(boolean)
     */
    public void setUseMailLoginCheck(boolean useMainLoginCheck) {

    }

    /*
     * (non-Jsdoc)
     * 
     * @see org.talend.core.ui.branding.IBrandingConfiguration#setUseProductRegistration(boolean)
     */
    public void setUseProductRegistration(boolean useProductRegistration) {

    }

    @Override
    public List<IRepositoryNode> getHiddenRepositoryCategory(IRepositoryNode nodeParent, String type) {
        RepositoryNode parent = (RepositoryNode) nodeParent;

        List<IRepositoryNode> nodes = super.getHiddenRepositoryCategory(parent, type);

        // RepositoryNode businessProcessNode = new RepositoryNode(null, parent, ENodeType.SYSTEM_FOLDER);
        // businessProcessNode.setProperties(EProperties.LABEL, ERepositoryObjectType.BUSINESS_PROCESS);
        // businessProcessNode.setProperties(EProperties.CONTENT_TYPE, ERepositoryObjectType.BUSINESS_PROCESS);
        // nodes.add(businessProcessNode);
        //
        // RepositoryNode docNode = new RepositoryNode(null, parent, ENodeType.SYSTEM_FOLDER);
        // docNode.setProperties(EProperties.LABEL, ERepositoryObjectType.DOCUMENTATION);
        // docNode.setProperties(EProperties.CONTENT_TYPE, ERepositoryObjectType.DOCUMENTATION);
        // nodes.add(docNode);
        //
        // RepositoryNode metadataNode = new RepositoryNode(null, parent, ENodeType.STABLE_SYSTEM_FOLDER);
        // metadataNode.setProperties(EProperties.LABEL, ERepositoryObjectType.METADATA);
        // metadataNode.setProperties(EProperties.CONTENT_TYPE, ERepositoryObjectType.METADATA);
        // nodes.add(metadataNode);

        RepositoryNode sqlTemplates = new RepositoryNode(null, parent, ENodeType.SIMPLE_FOLDER);
        sqlTemplates.setProperties(EProperties.LABEL, "Generic");
        sqlTemplates.setProperties(EProperties.CONTENT_TYPE, ERepositoryObjectType.SQLPATTERNS);
        nodes.add(sqlTemplates);
        sqlTemplates = new RepositoryNode(null, parent, ENodeType.SIMPLE_FOLDER);
        sqlTemplates.setProperties(EProperties.LABEL, "MySQL");
        sqlTemplates.setProperties(EProperties.CONTENT_TYPE, ERepositoryObjectType.SQLPATTERNS);
        nodes.add(sqlTemplates);
        sqlTemplates = new RepositoryNode(null, parent, ENodeType.SIMPLE_FOLDER);
        sqlTemplates.setProperties(EProperties.LABEL, "Netezza");
        sqlTemplates.setProperties(EProperties.CONTENT_TYPE, ERepositoryObjectType.SQLPATTERNS);
        nodes.add(sqlTemplates);
        sqlTemplates = new RepositoryNode(null, parent, ENodeType.SIMPLE_FOLDER);
        sqlTemplates.setProperties(EProperties.LABEL, "Oracle");
        sqlTemplates.setProperties(EProperties.CONTENT_TYPE, ERepositoryObjectType.SQLPATTERNS);
        nodes.add(sqlTemplates);
        sqlTemplates = new RepositoryNode(null, parent, ENodeType.SIMPLE_FOLDER);
        sqlTemplates.setProperties(EProperties.LABEL, "ParAccel");
        sqlTemplates.setProperties(EProperties.CONTENT_TYPE, ERepositoryObjectType.SQLPATTERNS);
        nodes.add(sqlTemplates);
        sqlTemplates = new RepositoryNode(null, parent, ENodeType.SIMPLE_FOLDER);
        sqlTemplates.setProperties(EProperties.LABEL, "Teradata");
        sqlTemplates.setProperties(EProperties.CONTENT_TYPE, ERepositoryObjectType.SQLPATTERNS);
        nodes.add(sqlTemplates);

        return nodes;
    }

    @Override
    public boolean isAllowChengeVersion() {
        return false;
    }

    @Override
    public void initPerspective(IPageLayout layout) {
        String componentSettingViewerId = "org.talend.designer.core.ui.views.properties.ComponentSettingsView";//$NON-NLS-1$
        //        String navigatorId = "org.eclipse.ui.views.ResourceNavigator"; //$NON-NLS-1$
        String outlineId = "org.eclipse.ui.views.ContentOutline"; //$NON-NLS-1$
        String codeId = "org.talend.designer.core.codeView"; //$NON-NLS-1$
        String repositoryId = IRepositoryView.VIEW_ID;

        String runProcessViewId = "org.talend.designer.runprocess.ui.views.processview"; //$NON-NLS-1$
        //        String problemsViewId = "org.talend.designer.core.ui.views.ProblemsView"; //$NON-NLS-1$
        String modulesViewId = "org.talend.designer.codegen.perlmodule.ModulesView"; //$NON-NLS-1$
        //String ecosystemViewId = "org.talend.designer.components.ecosystem.ui.views.EcosystemView"; //$NON-NLS-1$
        //        String schedulerViewId = "org.talend.scheduler.views.Scheduler"; //$NON-NLS-1$
        String contextsViewId = "org.talend.designer.core.ui.views.ContextsView"; //$NON-NLS-1$
        String gefPaletteViewId = "org.eclipse.gef.ui.palette_view"; //$NON-NLS-1$
        String jobSettingsViewId = "org.talend.designer.core.ui.views.jobsettings.JobSettingsView"; //$NON-NLS-1$
        //        String jobHierarchyViewId = "org.talend.designer.core.ui.hierarchy.JobHierarchyViewPart"; //$NON-NLS-1$
        //            String exchangeViewId = "org.talend.designer.components.exchange.ui.views.ExchangeView"; //$NON-NLS-1$

        // leftTopLayout
        IFolderLayout leftTopLayout = layout.createFolder("navigatorLayout", IPageLayout.LEFT, new Float(0.3), //$NON-NLS-1$
                IPageLayout.ID_EDITOR_AREA);
        leftTopLayout.addView(repositoryId);
        // leftTopLayout.addView(navigatorId);

        // leftBottomLayout
        IFolderLayout leftBottomLayout = layout.createFolder("outlineCodeLayout", IPageLayout.BOTTOM, new Float(0.6), //$NON-NLS-1$
                repositoryId);
        leftBottomLayout.addView(outlineId);
        leftBottomLayout.addView(codeId);
        // leftBottomLayout.addView(jobHierarchyViewId);

        IFolderLayout rightTopLayout = layout.createFolder("paletteLayout", IPageLayout.RIGHT, new Float(0.8), //$NON-NLS-1$
                IPageLayout.ID_EDITOR_AREA);
        rightTopLayout.addView(gefPaletteViewId);

        // bottomLayout
        IFolderLayout bottomLayout = layout.createFolder("bottomLayout", IPageLayout.BOTTOM, new Float(0.6), //$NON-NLS-1$
                IPageLayout.ID_EDITOR_AREA);
        // bottomLayout.addView(propertyId);
        bottomLayout.addView(jobSettingsViewId);
        bottomLayout.addView(contextsViewId);
        bottomLayout.addView(componentSettingViewerId);

        bottomLayout.addView(runProcessViewId);
        bottomLayout.addView("org.talend.oozie.scheduler.views.OozieSchedulerView");
        // bottomLayout.addView(problemsViewId);
        bottomLayout.addView(modulesViewId);
        // bottomLayout.addView(ecosystemViewId);
        // bottomLayout.addView(exchangeViewId);
        //
        // bottomLayout.addView(schedulerViewId);

        bottomLayout.addPlaceholder("*");
    }

}
