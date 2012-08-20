package org.talend.repository.hdfs.node;

import org.talend.commons.ui.runtime.image.IImage;
import org.talend.core.repository.IExtendRepositoryNode;
import org.talend.repository.hdfs.util.EHDFSImage;
import org.talend.repository.model.RepositoryNode;

public class HDFSExtendNode implements IExtendRepositoryNode {

    @Override
    public IImage getNodeImage() {
        return EHDFSImage.HDFS_RESOURCE_ICON;
    }

    @Override
    public int getOrdinal() {
        return 50;
    }

    @Override
    public Object[] getChildren() {
        return new RepositoryNode[0];
    }

}
