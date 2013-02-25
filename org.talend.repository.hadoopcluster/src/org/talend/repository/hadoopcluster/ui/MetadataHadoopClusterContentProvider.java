package org.talend.repository.hadoopcluster.ui;

import org.eclipse.core.runtime.IPath;
import org.eclipse.core.runtime.Path;
import org.talend.repository.hadoopcluster.node.model.HadoopClusterRepositoryNodeType;
import org.talend.repository.metadata.content.AbstractMetadataContentProvider;
import org.talend.repository.model.ProjectRepositoryNode;
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

}
