package org.talend.repository.hadoopcluster.ui;

import org.eclipse.core.runtime.IPath;
import org.eclipse.core.runtime.Path;
import org.talend.core.model.metadata.builder.connection.impl.DatabaseConnectionImpl;
import org.talend.core.model.properties.DatabaseConnectionItem;
import org.talend.core.model.properties.Item;
import org.talend.core.repository.model.ProjectRepositoryNode;
import org.talend.repository.hadoopcluster.node.model.HadoopClusterRepositoryNodeType;
import org.talend.repository.metadata.content.AbstractMetadataContentProvider;
import org.talend.repository.model.RepositoryNode;
import org.talend.repository.model.nodes.IProjectRepositoryNode;

/**
 * 
 * created by ycbai on 2013-1-21 Detailled comment
 * 
 */
public class MetadataHadoopClusterContentProvider extends AbstractMetadataContentProvider {

    @Override
    protected RepositoryNode getTopLevelNodeFromProjectRepositoryNode(ProjectRepositoryNode projectNode) {
        return projectNode.getRootRepositoryNode(HadoopClusterRepositoryNodeType.HADOOPCLUSTER);
    }

    @Override
    protected IPath getWorkspaceTopNodePath(RepositoryNode topLevelNode) {
        IPath workspaceRelativePath = null;
        IProjectRepositoryNode root = topLevelNode.getRoot();
        if (root != null) {
            String projectName = root.getProject().getTechnicalLabel();
            if (projectName != null) {
                workspaceRelativePath = Path.fromPortableString('/' + projectName).append("/metadata/hadoop"); //$NON-NLS-1$ 
            }
        }
        return workspaceRelativePath;
    }

    /*
     * (non-Javadoc)
     * 
     * @see
     * org.talend.repository.viewer.content.FolderListenerSingleTopContentProvider#isLinkedTopNode(org.talend.repository
     * .model.RepositoryNode, org.talend.core.model.properties.Item)
     */
    @Override
    protected boolean isLinkedTopNode(RepositoryNode topLevelNode, Item item) {
        if (item instanceof DatabaseConnectionItem) {
            DatabaseConnectionImpl connection = (DatabaseConnectionImpl) ((DatabaseConnectionItem) item).getConnection();
            String hadoopClusterId = connection.getParameters().get("CONN_PARA_KEY_HADOOP_CLUSTER_ID");
            if (!"".equals(hadoopClusterId) && hadoopClusterId != null) {
                return true;
            }
        }
        return false;
    }

}
