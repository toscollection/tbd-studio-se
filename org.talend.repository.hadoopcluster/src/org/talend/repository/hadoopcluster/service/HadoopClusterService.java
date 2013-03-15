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
package org.talend.repository.hadoopcluster.service;

import java.util.ArrayList;
import java.util.List;

import org.talend.core.hadoop.IHadoopClusterService;
import org.talend.core.model.metadata.builder.connection.Connection;
import org.talend.core.model.properties.Item;
import org.talend.core.model.repository.ERepositoryObjectType;
import org.talend.repository.hadoopcluster.node.model.HadoopClusterRepositoryNodeType;
import org.talend.repository.hadoopcluster.ui.viewer.HadoopSubnodeRepositoryContentManager;
import org.talend.repository.hadoopcluster.ui.viewer.handler.IHadoopSubnodeRepositoryContentHandler;
import org.talend.repository.hadoopcluster.util.HCRepositoryUtil;
import org.talend.repository.model.RepositoryNode;
import org.talend.repository.model.hadoopcluster.HadoopClusterConnection;
import org.talend.repository.model.hadoopcluster.HadoopClusterConnectionItem;
import org.talend.repository.model.hadoopcluster.HadoopClusterPackage;
import org.talend.repository.model.hadoopcluster.HadoopSubConnection;
import org.talend.repository.model.hadoopcluster.HadoopSubConnectionItem;

/**
 * created by ycbai on 2013-1-28 Detailled comment
 * 
 */
public class HadoopClusterService implements IHadoopClusterService {

    @Override
    public boolean containedByCluster(Connection hadoopClusterConnection, Connection hadoopSubConnection) {
        if (hadoopClusterConnection != null && hadoopSubConnection != null
                && hadoopClusterConnection instanceof HadoopClusterConnection
                && hadoopSubConnection instanceof HadoopSubConnection) {
            HadoopClusterConnection hcConnection = (HadoopClusterConnection) hadoopClusterConnection;
            HadoopSubConnection hConnection = (HadoopSubConnection) hadoopSubConnection;
            HadoopClusterConnection relativeHCConnection = HCRepositoryUtil.getRelativeHadoopClusterConnection(hConnection);
            if (hcConnection.equals(relativeHCConnection)) {
                return true;
            }
        }

        return false;
    }

    @Override
    public ERepositoryObjectType getHadoopClusterType() {
        return HadoopClusterRepositoryNodeType.HADOOPCLUSTER;
    }

    @Override
    public boolean isHadoopClusterNode(RepositoryNode node) {
        return HCRepositoryUtil.isHadoopClusterNode(node);
    }

    @Override
    public boolean isHadoopSubnode(RepositoryNode node) {
        return HCRepositoryUtil.isHadoopSubnode(node);
    }

    @Override
    public boolean isHadoopClusterItem(Item item) {
        return item.eClass() == HadoopClusterPackage.Literals.HADOOP_CLUSTER_CONNECTION_ITEM;
    }

    @Override
    public boolean isHadoopSubItem(Item item) {
        if (isHadoopClusterItem(item)) {
            return false;
        }
        for (IHadoopSubnodeRepositoryContentHandler handler : HadoopSubnodeRepositoryContentManager.getHandlers()) {
            if (handler.isProcess(item)) {
                return true;
            }
        }

        return false;
    }

    @Override
    public boolean isValidHadoopSubItem(Item item) {
        if (isHadoopSubItem(item)) {
            HadoopSubConnectionItem hadoopSubConnectionItem = (HadoopSubConnectionItem) item;
            Connection connection = hadoopSubConnectionItem.getConnection();
            if (connection instanceof HadoopSubConnection) {
                return ((HadoopSubConnection) connection).getRelativeHadoopClusterId() != null;
            }
        }

        return false;
    }

    @Override
    public List<String> getSubitemIdsOfHadoopCluster(Item item) {
        if (!isHadoopClusterItem(item)) {
            return new ArrayList<String>();
        }
        HadoopClusterConnectionItem clusterConnectionItem = (HadoopClusterConnectionItem) item;
        HadoopClusterConnection clusterConnection = (HadoopClusterConnection) clusterConnectionItem.getConnection();

        return clusterConnection.getConnectionList();
    }

}
