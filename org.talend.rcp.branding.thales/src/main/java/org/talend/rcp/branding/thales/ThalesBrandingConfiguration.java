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

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Properties;

import org.eclipse.core.runtime.FileLocator;
import org.eclipse.ui.IFolderLayout;
import org.eclipse.ui.IPageLayout;
import org.talend.commons.exception.MessageBoxExceptionHandler;
import org.talend.core.GlobalServiceRegister;
import org.talend.core.language.ECodeLanguage;
import org.talend.core.model.repository.ERepositoryObjectType;
import org.talend.core.ui.branding.DefaultBrandingConfiguration;
import org.talend.core.ui.branding.IBrandingService;
import org.talend.core.ui.images.ECoreImage;
import org.talend.repository.model.RepositoryNode;
import org.talend.repository.model.StableRepositoryNode;
import org.talend.repository.model.RepositoryNode.ENodeType;
import org.talend.repository.model.RepositoryNode.EProperties;

/**
 * DOC aiming class global comment. Detailled comment
 */
public class ThalesBrandingConfiguration extends DefaultBrandingConfiguration {

    private Properties brandingProperties;

    public ThalesBrandingConfiguration() {
        brandingProperties = new Properties();

        IBrandingService brandingService = (IBrandingService) GlobalServiceRegister.getDefault().getService(
                IBrandingService.class);
        try {
            File setupFile = new File(FileLocator.resolve(Activator.BUNDLE.getEntry("resources/setup.properties")).getFile());
            FileInputStream fileReader = new FileInputStream(setupFile);
            brandingProperties.load(fileReader);
            fileReader.close();
        } catch (Exception e) {
            MessageBoxExceptionHandler.process(new Exception(brandingService.getFullProductName() + " setup file not found !"));
        }

    }

    /*
     * (non-Javadoc)
     * 
     * @see org.talend.core.ui.branding.IBrandingConfiguration#getHiddenRepositoryCategory()
     */
    public List<RepositoryNode> getHiddenRepositoryCategory(RepositoryNode parent) {
        List<RepositoryNode> nodes = super.getHiddenRepositoryCategory(parent);

        String currentUser = (String) brandingProperties.get("currentUser");
        if (currentUser == null || !currentUser.equals("TC")) {
            // Code
            StableRepositoryNode codeNode = new StableRepositoryNode(parent, "Code", ECoreImage.CODE_ICON); //$NON-NLS-1$
            codeNode.setProperties(EProperties.CONTENT_TYPE, ERepositoryObjectType.ROUTINES);
            nodes.add(codeNode);
        }

        String displayDbConnection = (String) brandingProperties.get("displayDbConnection");
        if (displayDbConnection == null || !displayDbConnection.equals("true")) {
            // 7.1. Metadata connections
            RepositoryNode metadataConNode = new RepositoryNode(null, parent, ENodeType.SYSTEM_FOLDER);
            metadataConNode.setProperties(EProperties.LABEL, ERepositoryObjectType.METADATA_CONNECTIONS);
            metadataConNode.setProperties(EProperties.CONTENT_TYPE, ERepositoryObjectType.METADATA_CONNECTIONS);
            nodes.add(metadataConNode);
        }

        // 1. Business process
        RepositoryNode businessProcessNode = new RepositoryNode(null, parent, ENodeType.SYSTEM_FOLDER);
        businessProcessNode.setProperties(EProperties.LABEL, ERepositoryObjectType.BUSINESS_PROCESS);
        businessProcessNode.setProperties(EProperties.CONTENT_TYPE, ERepositoryObjectType.BUSINESS_PROCESS);
        nodes.add(businessProcessNode);

        // 5. Sql patterns
        RepositoryNode sqlPatternNode = new RepositoryNode(null, parent, ENodeType.SYSTEM_FOLDER);
        sqlPatternNode.setProperties(EProperties.LABEL, ERepositoryObjectType.SQLPATTERNS);
        sqlPatternNode.setProperties(EProperties.CONTENT_TYPE, ERepositoryObjectType.SQLPATTERNS);
        nodes.add(sqlPatternNode);

        // 7.6. Metadata file ldif
        RepositoryNode metadataFileLdifNode = new RepositoryNode(null, parent, ENodeType.SYSTEM_FOLDER);
        metadataFileLdifNode.setProperties(EProperties.LABEL, ERepositoryObjectType.METADATA_FILE_LDIF);
        metadataFileLdifNode.setProperties(EProperties.CONTENT_TYPE, ERepositoryObjectType.METADATA_FILE_LDIF);
        nodes.add(metadataFileLdifNode);

        // 7.8. LDAP schemas
        RepositoryNode metadataLDAPSchemaNode = new RepositoryNode(null, parent, ENodeType.SYSTEM_FOLDER);
        metadataLDAPSchemaNode.setProperties(EProperties.LABEL, ERepositoryObjectType.METADATA_LDAP_SCHEMA);
        metadataLDAPSchemaNode.setProperties(EProperties.CONTENT_TYPE, ERepositoryObjectType.METADATA_LDAP_SCHEMA);
        nodes.add(metadataLDAPSchemaNode);

        // 7.9. Generic schemas
        RepositoryNode metadataGenericSchemaNode = new RepositoryNode(null, parent, ENodeType.SYSTEM_FOLDER);
        metadataGenericSchemaNode.setProperties(EProperties.LABEL, ERepositoryObjectType.METADATA_GENERIC_SCHEMA);
        metadataGenericSchemaNode.setProperties(EProperties.CONTENT_TYPE, ERepositoryObjectType.METADATA_GENERIC_SCHEMA);
        nodes.add(metadataGenericSchemaNode);

        // 7.10 WSDL
        RepositoryNode metadataWSDLSchemaNode = new RepositoryNode(null, parent, ENodeType.SYSTEM_FOLDER);
        metadataWSDLSchemaNode.setProperties(EProperties.LABEL, ERepositoryObjectType.METADATA_WSDL_SCHEMA);
        metadataWSDLSchemaNode.setProperties(EProperties.CONTENT_TYPE, ERepositoryObjectType.METADATA_WSDL_SCHEMA);
        nodes.add(metadataWSDLSchemaNode);

        // 7.11 Salesforce
        RepositoryNode metadataSalesforceSchemaNode = new RepositoryNode(null, parent, ENodeType.SYSTEM_FOLDER);
        metadataSalesforceSchemaNode.setProperties(EProperties.LABEL, ERepositoryObjectType.METADATA_SALESFORCE_SCHEMA);
        metadataSalesforceSchemaNode.setProperties(EProperties.CONTENT_TYPE, ERepositoryObjectType.METADATA_SALESFORCE_SCHEMA);
        nodes.add(metadataSalesforceSchemaNode);
        return nodes;
    }

    /*
     * (non-Javadoc)
     * 
     * @see org.talend.core.ui.branding.DefaultBrandingConfiguration#getAvailableComponents()
     */
    @Override
    public String[] getAvailableComponents() {
        List<String> componentList = new ArrayList<String>();
        File setupFile;
        try {
            setupFile = new File(FileLocator.resolve(
                    Activator.BUNDLE.getEntry("resources/" + brandingProperties.getProperty("componentFile"))).getFile());

            FileReader fileReader = new FileReader(setupFile);

            BufferedReader bufReader = new BufferedReader(fileReader);
            String curComponent = bufReader.readLine();
            while (curComponent != null) {
                componentList.add(curComponent);
                curComponent = bufReader.readLine();
            }
            fileReader.close();
        } catch (IOException e) {
            if (componentList.isEmpty()) {
                return null;
            }
        }
        return componentList.toArray(new String[] {});
    }

    /*
     * (non-Javadoc)
     * 
     * @see org.talend.core.ui.branding.DefaultBrandingConfiguration#getAvailableLanguages()
     */
    @Override
    public String[] getAvailableLanguages() {
        String[] languages = { ECodeLanguage.JAVA.getName() };

        return languages;
    }

    /*
     * (non-Javadoc)
     * 
     * @see org.talend.core.ui.branding.DefaultBrandingConfiguration#getJobEditorSettings()
     */
    @Override
    public Map<String, Object> getJobEditorSettings() {
        Map<String, Object> map = super.getJobEditorSettings();
        map.put(DISPLAY_CODE_VIEW, Boolean.FALSE);
        return map;
    }

    /*
     * (non-Javadoc)
     * 
     * @see org.talend.core.ui.branding.DefaultBrandingConfiguration#initPerspective(org.eclipse.ui.IPageLayout)
     */
    @Override
    public void initPerspective(IPageLayout layout) {
        String componentSettingViewerId = "org.talend.designer.core.ui.views.properties.ComponentSettingsView";//$NON-NLS-1$
        String repositoryId = "org.talend.repository.views.repository"; //$NON-NLS-1$

        String runProcessViewId = "org.talend.designer.runprocess.ui.views.processview"; //$NON-NLS-1$
        String problemsViewId = "org.talend.designer.core.ui.views.ProblemsView"; //$NON-NLS-1$
        String contextsViewId = "org.talend.designer.core.ui.views.ContextsView"; //$NON-NLS-1$
        String gefPaletteViewId = "org.eclipse.gef.ui.palette_view"; //$NON-NLS-1$
        String jobSettingsViewId = "org.talend.designer.core.ui.views.jobsettings.JobSettingsView"; //$NON-NLS-1$

        // leftTopLayout
        IFolderLayout leftTopLayout = layout.createFolder("navigatorLayout", IPageLayout.LEFT, new Float(0.3), //$NON-NLS-1$
                IPageLayout.ID_EDITOR_AREA);
        leftTopLayout.addView(repositoryId);

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
        bottomLayout.addView(problemsViewId);
    }

    /*
     * (non-Javadoc)
     * 
     * @see org.talend.core.ui.branding.DefaultBrandingConfiguration#isUseMailLoginCheck()
     */
    @Override
    public boolean isUseMailLoginCheck() {
        return false;
    }

    /*
     * (non-Javadoc)
     * 
     * @see org.talend.core.ui.branding.DefaultBrandingConfiguration#isUseProductRegistration()
     */
    @Override
    public boolean isUseProductRegistration() {
        return false;
    }

    /*
     * (non-Javadoc)
     * 
     * @see org.talend.core.ui.branding.DefaultBrandingConfiguration#isAllowDebugMode()
     */
    @Override
    public boolean isAllowDebugMode() {
        return false;
    }

    /*
     * (non-Javadoc)
     * 
     * @see org.talend.core.ui.branding.DefaultBrandingConfiguration#isUseDemoProjects()
     */
    @Override
    public boolean isUseDemoProjects() {
        return false;
    }
}
