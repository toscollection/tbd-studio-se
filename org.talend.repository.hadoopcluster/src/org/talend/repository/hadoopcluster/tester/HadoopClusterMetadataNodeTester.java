// ============================================================================
//
// Copyright (C) 2006-2013 Talend Inc. - www.talend.com
//
// This source code is available under agreement available at
// %InstallDIR%\features\org.talend.rcp.branding.%PRODUCTNAME%\%PRODUCTNAME%license.txt
//
// You should have received a copy of the agreement
// along with this program; if not, write to Talend SA
// 9 rue Pages 92150 Suresnes, France
//
// ============================================================================
package org.talend.repository.hadoopcluster.tester;

import org.talend.core.model.repository.ERepositoryObjectType;
import org.talend.repository.hadoopcluster.node.model.HadoopClusterRepositoryNodeType;
import org.talend.repository.hadoopcluster.util.HCRepositoryUtil;
import org.talend.repository.metadata.tester.CoMetadataNodeTester;
import org.talend.repository.model.IRepositoryNode.EProperties;
import org.talend.repository.model.RepositoryNode;

/**
 * 
 * created by ycbai on 2013-1-21 Detailled comment
 * 
 */
public class HadoopClusterMetadataNodeTester extends CoMetadataNodeTester {

    private static final String IS_HADOOP_CLUSTER_CONNECTION = "isHadoopClusterConnection"; //$NON-NLS-1$

    @Override
    protected ERepositoryObjectType findType(String property) {
        if (property != null) {
            if (IS_HADOOP_CLUSTER_CONNECTION.equals(property)) {
                return HadoopClusterRepositoryNodeType.HADOOPCLUSTER;
            }
        }
        return null;
    }

    @Override
    public boolean isTypeNode(RepositoryNode repositoryNode, ERepositoryObjectType type) {
        ERepositoryObjectType contentType = (ERepositoryObjectType) repositoryNode.getProperties(EProperties.CONTENT_TYPE);
        if (contentType != null && contentType.equals(type)) {
            return true;
        }

        return HCRepositoryUtil.isHadoopNode(repositoryNode);
    }

}
