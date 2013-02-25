package org.talend.repository.hadoopcluster.node;

import org.talend.commons.ui.runtime.image.IImage;
import org.talend.core.repository.IExtendRepositoryNode;
import org.talend.repository.hadoopcluster.util.EHadoopClusterImage;
import org.talend.repository.model.RepositoryNode;

/**
 * created by ycbai on 2013-1-21 Detailled comment
 * 
 */
public class HadoopClusterExtendNode implements IExtendRepositoryNode {

    @Override
    public IImage getNodeImage() {
        return EHadoopClusterImage.HADOOPCLUSTER_RESOURCE_ICON;
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
