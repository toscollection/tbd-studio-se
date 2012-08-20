package org.talend.repository.hdfs.action;

import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.jface.viewers.TreeViewer;
import org.talend.commons.ui.runtime.image.EImage;
import org.talend.commons.ui.runtime.image.ImageProvider;
import org.talend.repository.hdfs.i18n.Messages;
import org.talend.repository.hdfs.node.HDFSRepositoryNodeType;
import org.talend.repository.model.ERepositoryStatus;
import org.talend.repository.model.RepositoryNode;
import org.talend.repository.ui.actions.EditPropertiesAction;

public class EditHDFSPropertiesAction extends EditPropertiesAction {

    public EditHDFSPropertiesAction() {
        super();
        this.setText(Messages.getString("EditHDFSPropertiesAction.editLabel")); //$NON-NLS-1$
        this.setToolTipText(Messages.getString("EditHDFSPropertiesAction.tooltip")); //$NON-NLS-1$
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
                    if (node.getObjectType() == HDFSRepositoryNodeType.HDFS) {
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
