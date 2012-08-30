package org.talend.repository.hcatalog.action;

import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.jface.viewers.TreeViewer;
import org.talend.commons.ui.runtime.image.EImage;
import org.talend.commons.ui.runtime.image.ImageProvider;
import org.talend.repository.hcatalog.i18n.Messages;
import org.talend.repository.hcatalog.node.HCatalogRepositoryNodeType;
import org.talend.repository.model.ERepositoryStatus;
import org.talend.repository.model.RepositoryNode;
import org.talend.repository.ui.actions.EditPropertiesAction;

public class EditHCatalogPropertiesAction extends EditPropertiesAction {

    public EditHCatalogPropertiesAction() {
        super();
        this.setText(Messages.getString("EditHCatalogPropertiesAction.editLabel")); //$NON-NLS-1$
        this.setToolTipText(Messages.getString("EditHCatalogPropertiesAction.tooltip")); //$NON-NLS-1$
        this.setImageDescriptor(ImageProvider.getImageDesc(EImage.EDIT_ICON));
    }

    public void init(TreeViewer viewer, IStructuredSelection selection) {
        boolean canWork = selection.size() == 1;
        if (canWork) {
            Object o = ((IStructuredSelection) selection).getFirstElement();
            if (o instanceof RepositoryNode) {
                RepositoryNode node = (RepositoryNode) o;
                switch (node.getType()) {
                case REPOSITORY_ELEMENT:
                    if (node.getObjectType() == HCatalogRepositoryNodeType.HCATALOG) {
                        canWork = true;
                    } else {
                        canWork = false;
                    }
                    break;
                default:
                    canWork = false;
                    break;
                }
                if (canWork) {
                    canWork = (node.getObject().getRepositoryStatus() != ERepositoryStatus.DELETED);
                }
                if (canWork) {
                    canWork = isLastVersion(node);
                }
            }
        }
        setEnabled(canWork);
    }

}
