package org.talend.repository.hdfs.action;

import org.eclipse.jface.wizard.IWizard;
import org.eclipse.ui.IWorkbench;
import org.talend.commons.ui.runtime.image.IImage;
import org.talend.core.model.repository.ERepositoryObjectType;
import org.talend.repository.hadoopcluster.action.common.CreateHadoopNodeAction;
import org.talend.repository.hdfs.node.model.HDFSRepositoryNodeType;
import org.talend.repository.hdfs.ui.HDFSWizard;
import org.talend.repository.hdfs.util.EHDFSImage;
import org.talend.repository.model.RepositoryNode;
import org.talend.repository.model.hdfs.HDFSConnectionItem;

/**
 * DOC ycbai class global comment. Detailled comment
 */
public class CreateHDFSAction extends CreateHadoopNodeAction {

    @Override
    protected ERepositoryObjectType getNodeType() {
        return HDFSRepositoryNodeType.HDFS;
    }

    @Override
    protected IImage getNodeImage() {
        return EHDFSImage.HDFS_RESOURCE_ICON;
    }

    @Override
    protected IWizard getWizard(IWorkbench workbench, boolean isCreate, RepositoryNode node, String[] existingNames) {
        return new HDFSWizard(workbench, isCreate, node, existingNames);
    }

    @Override
    public Class getClassForDoubleClick() {
        return HDFSConnectionItem.class;
    }

}
