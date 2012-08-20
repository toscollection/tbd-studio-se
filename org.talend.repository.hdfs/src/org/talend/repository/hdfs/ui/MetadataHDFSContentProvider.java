package org.talend.repository.hdfs.ui;

import org.talend.repository.hdfs.node.HDFSRepositoryNodeType;
import org.talend.repository.metadata.content.AbstractMetadataContentProvider;
import org.talend.repository.model.ProjectRepositoryNode;
import org.talend.repository.model.RepositoryNode;

/**
 * DOC ycbai class global comment. Detailled comment
 */
public class MetadataHDFSContentProvider extends AbstractMetadataContentProvider {

    protected RepositoryNode getTopLevelNodeFromProjectRepositoryNode(ProjectRepositoryNode projectNode) {
        return projectNode.getRootRepositoryNode(HDFSRepositoryNodeType.HDFS);
    }

}
