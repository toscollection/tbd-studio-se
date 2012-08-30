package org.talend.repository.hcatalog.action;

import org.eclipse.jface.wizard.WizardDialog;
import org.eclipse.swt.widgets.Display;
import org.eclipse.ui.PlatformUI;
import org.talend.commons.ui.runtime.image.ImageProvider;
import org.talend.core.model.repository.ERepositoryObjectType;
import org.talend.core.repository.model.ProxyRepositoryFactory;
import org.talend.core.repository.ui.actions.metadata.AbstractCreateAction;
import org.talend.repository.ProjectManager;
import org.talend.repository.hcatalog.i18n.Messages;
import org.talend.repository.hcatalog.node.HCatalogRepositoryNodeType;
import org.talend.repository.hcatalog.ui.HCatalogWizard;
import org.talend.repository.hcatalog.util.EHCatalogImage;
import org.talend.repository.model.IProxyRepositoryFactory;
import org.talend.repository.model.IRepositoryNode.EProperties;
import org.talend.repository.model.RepositoryNode;
import org.talend.repository.model.hcatalog.HCatalogConnectionItem;

/**
 * DOC ycbai class global comment. Detailled comment
 */
public class CreateHCatalogAction extends AbstractCreateAction {

    private static final String CREATE_LABEL = Messages.getString("CreateHCatalogAction.createLabel"); //$NON-NLS-1$

    private static final String EDIT_LABEL = Messages.getString("CreateHCatalogAction.editLabel"); //$NON-NLS-1$

    private static final String OPEN_LABEL = Messages.getString("CreateHCatalogAction.openLabel"); //$NON-NLS-1$

    protected static final int WIZARD_WIDTH = 700;

    protected static final int WIZARD_HEIGHT = 400;

    private boolean creation = true;

    public CreateHCatalogAction() {
        super();
        this.setText(CREATE_LABEL);
        this.setToolTipText(CREATE_LABEL);
        this.setImageDescriptor(ImageProvider.getImageDesc(EHCatalogImage.HCATALOG_RESOURCE_ICON));
    }

    public CreateHCatalogAction(boolean isToolbar) {
        this();
        setToolbar(isToolbar);
    }

    protected void doRun() {
        if (repositoryNode == null) {
            repositoryNode = getCurrentRepositoryNode();
        }

        if (isToolbar()) {
            if (repositoryNode != null && repositoryNode.getContentType() != HCatalogRepositoryNodeType.HCATALOG) {
                repositoryNode = null;
            }
            if (repositoryNode == null) {
                repositoryNode = getRepositoryNodeForDefault(HCatalogRepositoryNodeType.HCATALOG);
            }

        }

        WizardDialog wizardDialog = null;
        if (isToolbar()) {
            init(repositoryNode);
            HCatalogWizard hdfs = new HCatalogWizard(PlatformUI.getWorkbench(), creation, repositoryNode, getExistingNames());
            hdfs.setToolBar(true);
            wizardDialog = new WizardDialog(Display.getCurrent().getActiveShell(), hdfs);
        } else {
            wizardDialog = new WizardDialog(Display.getCurrent().getActiveShell(), new HCatalogWizard(PlatformUI.getWorkbench(),
                    creation, repositoryNode, getExistingNames()));
        }
        wizardDialog.setPageSize(WIZARD_WIDTH, WIZARD_HEIGHT);
        wizardDialog.create();
        wizardDialog.open();
    }

    protected void init(RepositoryNode node) {
        ERepositoryObjectType nodeType = (ERepositoryObjectType) node.getProperties(EProperties.CONTENT_TYPE);
        if (!HCatalogRepositoryNodeType.HCATALOG.equals(nodeType)) {
            return;
        }
        IProxyRepositoryFactory factory = ProxyRepositoryFactory.getInstance();
        switch (node.getType()) {
        case SIMPLE_FOLDER:
            if (node.getObject() != null && node.getObject().getProperty().getItem().getState().isDeleted()) {
                setEnabled(false);
                return;
            }
        case SYSTEM_FOLDER:
            if (factory.isUserReadOnlyOnCurrentProject() || !ProjectManager.getInstance().isInCurrentMainProject(node)) {
                setEnabled(false);
                return;
            }
            this.setText(CREATE_LABEL);
            collectChildNames(node);
            creation = true;
            break;
        case REPOSITORY_ELEMENT:
            if (factory.isPotentiallyEditable(node.getObject())) {
                this.setText(EDIT_LABEL);
                collectSiblingNames(node);
            } else {
                this.setText(OPEN_LABEL);
            }
            collectSiblingNames(node);
            creation = false;
            break;
        default:
            return;
        }
        setEnabled(true);
    }

    public Class getClassForDoubleClick() {
        return HCatalogConnectionItem.class;
    }

}
