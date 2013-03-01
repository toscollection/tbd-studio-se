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

import org.talend.repository.hadoopcluster.node.model.HadoopClusterRepositoryNodeType;
import org.talend.repository.model.RepositoryNode;
import org.talend.repository.tester.AbstractNodeTester;

/**
 * 
 * created by ycbai on 2013-1-21 Detailled comment
 * 
 */
public class HadoopClusterMetadataNodeTester extends AbstractNodeTester {

    private static final String IS_HADOOP_CLUSTER_CONNECTION = "isHadoopClusterConnection"; //$NON-NLS-1$

    private static final String IS_HADOOP_SUBCONNECTION = "isHadoopSubconnection"; //$NON-NLS-1$

    private static final String IS_HADOOP_SUBCONNECTION_SCHEMA = "isHadoopSubconnectionSchema"; //$NON-NLS-1$

    private static final String IS_HADOOP_SUBCONNECTION_SCHEMA_COLUMN = "isHadoopSubconnectionSchemaColumn"; //$NON-NLS-1$

    @Override
    protected Boolean testProperty(Object receiver, String property, Object[] args, Object expectedValue) {
        if (receiver instanceof RepositoryNode) {
            RepositoryNode repositoryNode = (RepositoryNode) receiver;
            if (IS_HADOOP_CLUSTER_CONNECTION.equals(property)) {
                return isHadoopClusterNode(repositoryNode);
            } else if (IS_HADOOP_SUBCONNECTION.equals(property)) {
                return isHadoopSubNode(repositoryNode);
            } else if (IS_HADOOP_SUBCONNECTION_SCHEMA.equals(property)) {
                return isHadoopSubNodeSchema(repositoryNode);
            } else if (IS_HADOOP_SUBCONNECTION_SCHEMA_COLUMN.equals(property)) {
                return isHadoopSubNodeSchemaColumn(repositoryNode);
            }
        }

        return null;
    }

    private boolean isHadoopClusterNode(RepositoryNode repositoryNode) {
        if (repositoryNode == null) {
            return false;
        }
        return isTypeNode(repositoryNode, HadoopClusterRepositoryNodeType.HADOOPCLUSTER);
    }

    private boolean isHadoopSubNode(RepositoryNode repositoryNode) {
        if (repositoryNode == null) {
            return false;
        }
        return HadoopClusterRepositoryNodeType.HADOOPCLUSTER.isParentTypeOf(getNodeContentType(repositoryNode));
    }

    private boolean isHadoopSubNodeSchema(RepositoryNode repositoryNode) {
        if (repositoryNode == null) {
            return false;
        }
        return isHadoopSubNode(repositoryNode.getParent());
    }

    private boolean isHadoopSubNodeSchemaColumn(RepositoryNode repositoryNode) {
        if (repositoryNode == null) {
            return false;
        }
        RepositoryNode parentNode = repositoryNode.getParent();
        if (parentNode == null) {
            return false;
        }
        return isHadoopSubNodeSchema(parentNode.getParent());
    }

}
