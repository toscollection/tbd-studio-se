package org.talend.repository.hdfs.action;

import org.eclipse.jface.wizard.WizardDialog;
import org.eclipse.swt.widgets.Display;
import org.eclipse.ui.PlatformUI;
import org.talend.commons.ui.runtime.image.ImageProvider;
import org.talend.core.model.repository.ERepositoryObjectType;
import org.talend.core.repository.model.ProxyRepositoryFactory;
import org.talend.core.repository.ui.actions.metadata.AbstractCreateAction;
import org.talend.repository.ProjectManager;
import org.talend.repository.hdfs.i18n.Messages;
import org.talend.repository.hdfs.node.HDFSRepositoryNodeType;
import org.talend.repository.hdfs.ui.HDFSWizard;
import org.talend.repository.hdfs.util.EHDFSImage;
import org.talend.repository.model.IProxyRepositoryFactory;
import org.talend.repository.model.IRepositoryNode.EProperties;
import org.talend.repository.model.RepositoryNode;
import org.talend.repository.model.hdfs.HDFSConnectionItem;

/**
 * DOC ycbai class global comment. Detailled comment
 */
public class CreateHDFSAction extends AbstractCreateAction {

    private static final String CREATE_LABEL = Messages.getString("CreateHDFSAction.createLabel"); //$NON-NLS-1$

    private static final String EDIT_LABEL = Messages.getString("CreateHDFSAction.editLabel"); //$NON-NLS-1$

    private static final String OPEN_LABEL = Messages.getString("CreateHDFSAction.openLabel"); //$NON-NLS-1$

    protected static final int WIZARD_WIDTH = 700;

    protected static final int WIZARD_HEIGHT = 400;

    private boolean creation = true;

    public CreateHDFSAction() {
        super();
        this.setText(CREATE_LABEL);
        this.setToolTipText(CREATE_LABEL);
        this.setImageDescriptor(ImageProvider.getImageDesc(EHDFSImage.HDFS_RESOURCE_ICON));
    }

    public CreateHDFSAction(boolean isToolbar) {
        this();
        setToolbar(isToolbar);
    }

    protected void doRun() {
        if (repositoryNode == null) {
            repositoryNode = getCurrentRepositoryNode();
        }

        if (isToolbar()) {
            if (repositoryNode != null && repositoryNode.getContentType() != HDFSRepositoryNodeType.HDFS) {
                repositoryNode = null;
            }
            if (repositoryNode == null) {
                repositoryNode = getRepositoryNodeForDefault(HDFSRepositoryNodeType.HDFS);
            }

        }

        WizardDialog wizardDialog = null;
        if (isToolbar()) {
            init(repositoryNode);
            HDFSWizard hdfs = new HDFSWizard(PlatformUI.getWorkbench(), creation, repositoryNode, getExistingNames());
            hdfs.setToolBar(true);
            wizardDialog = new WizardDialog(Display.getCurrent().getActiveShell(), hdfs);
        } else {
            wizardDialog = new WizardDialog(Display.getCurrent().getActiveShell(), new HDFSWizard(PlatformUI.getWorkbench(),
                    creation, repositoryNode, getExistingNames()));
        }
        wizardDialog.setPageSize(WIZARD_WIDTH, WIZARD_HEIGHT);
        wizardDialog.create();
        wizardDialog.open();
    }

    protected void init(RepositoryNode node) {
        ERepositoryObjectType nodeType = (ERepositoryObjectType) node.getProperties(EProperties.CONTENT_TYPE);
        if (!HDFSRepositoryNodeType.HDFS.equals(nodeType)) {
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
        return HDFSConnectionItem.class;
    }

}
