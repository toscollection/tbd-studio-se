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
package org.talend.repository.hcatalog.ui;

import org.apache.log4j.Logger;
import org.eclipse.core.runtime.Path;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.ui.IWorkbench;
import org.eclipse.ui.IWorkbenchWizard;
import org.talend.commons.ui.runtime.image.ImageProvider;
import org.talend.commons.ui.swt.dialogs.ErrorDialogWidthDetailArea;
import org.talend.commons.utils.VersionUtils;
import org.talend.core.GlobalServiceRegister;
import org.talend.core.context.Context;
import org.talend.core.context.RepositoryContext;
import org.talend.core.model.properties.ConnectionItem;
import org.talend.core.model.properties.PropertiesFactory;
import org.talend.core.model.properties.Property;
import org.talend.core.model.repository.ERepositoryObjectType;
import org.talend.core.repository.model.ProxyRepositoryFactory;
import org.talend.core.runtime.CoreRuntimePlugin;
import org.talend.designer.core.IDesignerCoreService;
import org.talend.repository.hcatalog.Activator;
import org.talend.repository.hcatalog.i18n.Messages;
import org.talend.repository.hcatalog.update.HCatalogUpdateManager;
import org.talend.repository.hcatalog.util.EHCatalogImage;
import org.talend.repository.model.IProxyRepositoryFactory;
import org.talend.repository.model.RepositoryNode;
import org.talend.repository.model.RepositoryNodeUtilities;
import org.talend.repository.model.hcatalog.HCatalogConnection;
import org.talend.repository.model.hcatalog.HCatalogFactory;
import org.talend.repository.ui.utils.ConnectionContextHelper;
import org.talend.repository.ui.wizards.CheckLastVersionRepositoryWizard;
import org.talend.repository.ui.wizards.metadata.connection.Step0WizardPage;

/**
 * DOC ycbai class global comment. Detailled comment
 */
public class HCatalogWizard extends CheckLastVersionRepositoryWizard {

    private static Logger log = Logger.getLogger(HCatalogWizard.class);

    private Step0WizardPage propertiesPage;

    private HCatalogWizardPage mainPage;

    private HCatalogConnection connection;

    private Property connectionProperty;

    private String originalLabel;

    private String originalVersion;

    private String originalPurpose;

    private String originalDescription;

    private String originalStatus;

    private boolean isToolBar;

    public HCatalogWizard(IWorkbench workbench, boolean creation, RepositoryNode node, String[] existingNames) {
        super(workbench, creation);
        this.existingNames = existingNames;
        setNeedsProgressMonitor(true);
        switch (node.getType()) {
        case SIMPLE_FOLDER:
        case REPOSITORY_ELEMENT:
            pathToSave = RepositoryNodeUtilities.getPath(node);
            break;
        case SYSTEM_FOLDER:
            pathToSave = new Path(""); //$NON-NLS-1$
            break;
        }

        switch (node.getType()) {
        case SIMPLE_FOLDER:
        case SYSTEM_FOLDER:
            connection = HCatalogFactory.eINSTANCE.createHCatalogConnection();
            connectionProperty = PropertiesFactory.eINSTANCE.createProperty();
            connectionProperty.setAuthor(((RepositoryContext) CoreRuntimePlugin.getInstance().getContext()
                    .getProperty(Context.REPOSITORY_CONTEXT_KEY)).getUser());
            connectionProperty.setVersion(VersionUtils.DEFAULT_VERSION);
            connectionProperty.setStatusCode(""); //$NON-NLS-1$

            connectionItem = HCatalogFactory.eINSTANCE.createHCatalogConnectionItem();
            connectionItem.setProperty(connectionProperty);
            connectionItem.setConnection(connection);
            break;

        case REPOSITORY_ELEMENT:
            connection = (HCatalogConnection) ((ConnectionItem) node.getObject().getProperty().getItem()).getConnection();
            connectionProperty = node.getObject().getProperty();
            connectionItem = (ConnectionItem) node.getObject().getProperty().getItem();
            // set the repositoryObject, lock and set isRepositoryObjectEditable
            setRepositoryObject(node.getObject());
            isRepositoryObjectEditable();
            initLockStrategy();
            break;
        }
        if (!creation) {
            this.originalLabel = this.connectionItem.getProperty().getDisplayName();
            this.originalVersion = this.connectionItem.getProperty().getVersion();
            this.originalDescription = this.connectionItem.getProperty().getDescription();
            this.originalPurpose = this.connectionItem.getProperty().getPurpose();
            this.originalStatus = this.connectionItem.getProperty().getStatusCode();
        }
        // initialize the context mode
        ConnectionContextHelper.checkContextMode(connectionItem);
    }

    public void setToolBar(boolean isToolbar) {
        this.isToolBar = isToolbar;
    }

    public void addPages() {
        setWindowTitle(Messages.getString("HCatalogWizard.windowTitle")); //$NON-NLS-1$
        setDefaultPageImageDescriptor(ImageProvider.getImageDesc(EHCatalogImage.HCATALOG_WIZ));
        if (isToolBar) {
            pathToSave = null;
        }
        propertiesPage = new Step0WizardPage(connectionProperty, pathToSave, ERepositoryObjectType.METADATA_CONNECTIONS,
                !isRepositoryObjectEditable(), creation);
        mainPage = new HCatalogWizardPage(connectionItem, isRepositoryObjectEditable(), existingNames);

        if (creation) {
            propertiesPage.setTitle(Messages.getString("HCatalogWizardPage.titleCreate.Step1")); //$NON-NLS-1$
            propertiesPage.setDescription(Messages.getString("HCatalogWizardPage.descriptionCreate.Step1")); //$NON-NLS-1$
            propertiesPage.setPageComplete(false);

            mainPage.setTitle(Messages.getString("HCatalogWizardPage.titleCreate.Step2")); //$NON-NLS-1$
            mainPage.setDescription(Messages.getString("HCatalogWizardPage.descriptionCreate.Step2")); //$NON-NLS-1$
            mainPage.setPageComplete(false);
        } else {
            propertiesPage.setTitle(Messages.getString("HCatalogWizardPage.titleUpdate.Step1")); //$NON-NLS-1$
            propertiesPage.setDescription(Messages.getString("HCatalogWizardPage.descriptionUpdate.Step1")); //$NON-NLS-1$
            propertiesPage.setPageComplete(isRepositoryObjectEditable());

            mainPage.setTitle(Messages.getString("HCatalogWizardPage.titleUpdate.Step2")); //$NON-NLS-1$
            mainPage.setDescription(Messages.getString("HCatalogWizardPage.descriptionUpdate.Step2")); //$NON-NLS-1$
            mainPage.setPageComplete(isRepositoryObjectEditable());
        }
        addPage(propertiesPage);
        addPage(mainPage);
    }

    @Override
    public boolean performFinish() {
        if (mainPage.isPageComplete()) {
            final IProxyRepositoryFactory factory = ProxyRepositoryFactory.getInstance();
            String displayName = connectionProperty.getDisplayName();
            connectionProperty.setLabel(displayName);
            this.connection.setName(displayName);
            this.connection.setLabel(displayName);
            try {
                if (creation) {
                    String nextId = factory.getNextId();
                    connectionProperty.setId(nextId);
                    factory.create(connectionItem, propertiesPage.getDestinationPath());
                } else {
                    HCatalogUpdateManager.updateHCatalogConnection(connectionItem);
                    updateConnectionItem();

                    boolean isModified = propertiesPage.isNameModifiedByUser();
                    if (isModified) {
                        if (GlobalServiceRegister.getDefault().isServiceRegistered(IDesignerCoreService.class)) {
                            IDesignerCoreService service = (IDesignerCoreService) GlobalServiceRegister.getDefault().getService(
                                    IDesignerCoreService.class);
                            if (service != null) {
                                service.refreshComponentView(connectionItem);
                            }
                        }
                    }

                }
            } catch (Exception e) {
                String detailError = e.toString();
                new ErrorDialogWidthDetailArea(getShell(), Activator.PLUGIN_ID,
                        Messages.getString("HCatalogWizard.persistenceException"), //$NON-NLS-1$
                        detailError);
                log.error(Messages.getString("HCatalogWizard.persistenceException") + "\n" + detailError); //$NON-NLS-1$ //$NON-NLS-2$
                return false;
            }
            return true;
        } else {
            return false;
        }
    }

    @Override
    public boolean performCancel() {
        if (!creation) {
            connectionItem.getProperty().setVersion(this.originalVersion);
            connectionItem.getProperty().setDisplayName(this.originalLabel);
            connectionItem.getProperty().setDescription(this.originalDescription);
            connectionItem.getProperty().setPurpose(this.originalPurpose);
            connectionItem.getProperty().setStatusCode(this.originalStatus);
        }
        return super.performCancel();
    }

    /**
     * We will accept the selection in the workbench to see if we can initialize from it.
     * 
     * @see IWorkbenchWizard#init(IWorkbench, IStructuredSelection)
     */
    public void init(final IWorkbench workbench, final IStructuredSelection selection2) {
        super.setWorkbench(workbench);
        this.selection = selection2;
    }

    /*
     * (non-Javadoc)
     * 
     * @see org.talend.repository.ui.wizards.RepositoryWizard#getConnectionItem()
     */
    public ConnectionItem getConnectionItem() {
        return this.connectionItem;
    }

}
