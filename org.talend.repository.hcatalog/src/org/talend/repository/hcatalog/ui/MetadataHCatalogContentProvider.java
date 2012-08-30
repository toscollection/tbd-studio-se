package org.talend.repository.hcatalog.ui;

import org.talend.repository.hcatalog.node.HCatalogRepositoryNodeType;
import org.talend.repository.metadata.content.AbstractMetadataContentProvider;
import org.talend.repository.model.ProjectRepositoryNode;
import org.talend.repository.model.RepositoryNode;

/**
 * DOC ycbai class global comment. Detailled comment
 */
public class MetadataHCatalogContentProvider extends AbstractMetadataContentProvider {

    protected RepositoryNode getTopLevelNodeFromProjectRepositoryNode(ProjectRepositoryNode projectNode) {
        return projectNode.getRootRepositoryNode(HCatalogRepositoryNodeType.HCATALOG);
    }

}
