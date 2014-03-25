// ============================================================================
//
// Copyright (C) 2006-2014 Talend Inc. - www.talend.com
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
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.apache.commons.lang.StringUtils;
import org.eclipse.core.runtime.IPath;
import org.eclipse.emf.common.util.EMap;
import org.talend.commons.exception.BusinessException;
import org.talend.commons.exception.ExceptionHandler;
import org.talend.commons.exception.PersistenceException;
import org.talend.core.database.conn.ConnParameterKeys;
import org.talend.core.hadoop.IHadoopClusterService;
import org.talend.core.model.metadata.builder.connection.Connection;
import org.talend.core.model.metadata.builder.connection.DatabaseConnection;
import org.talend.core.model.process.IProcess;
import org.talend.core.model.properties.DatabaseConnectionItem;
import org.talend.core.model.properties.Item;
import org.talend.core.model.repository.ERepositoryObjectType;
import org.talend.core.model.repository.IRepositoryTypeProcessor;
import org.talend.core.repository.model.ProxyRepositoryFactory;
import org.talend.repository.hadoopcluster.node.model.HadoopClusterRepositoryNodeType;
import org.talend.repository.hadoopcluster.ui.viewer.HadoopSubMultiRepTypeProcessor;
import org.talend.repository.hadoopcluster.ui.viewer.HadoopSubnodeRepositoryContentManager;
import org.talend.repository.hadoopcluster.ui.viewer.handler.IHadoopSubnodeRepositoryContentHandler;
import org.talend.repository.hadoopcluster.util.HCRepositoryUtil;
import org.talend.repository.model.IProxyRepositoryFactory;
import org.talend.repository.model.IRepositoryNode;
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
    public boolean isHadoopClusterNode(IRepositoryNode node) {
        return HCRepositoryUtil.isHadoopClusterNode(node);
    }

    @Override
    public boolean isHadoopSubnode(IRepositoryNode node) {
        return HCRepositoryUtil.isHadoopSubnode(node);
    }

    @Override
    public boolean isHadoopFolderNode(IRepositoryNode node) {
        return HCRepositoryUtil.isHadoopFolderNode(node);
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

    @Override
    public Item getHadoopClusterBySubitemId(String subItemId) {
        return HCRepositoryUtil.getHadoopClusterItemBySubitemId(subItemId);
    }

    @Override
    public void refreshCluster(String clusterId) {
        HadoopClusterConnectionItem clusterItem = HCRepositoryUtil.getRelativeHadoopClusterItem(clusterId);
        if (clusterItem != null) {
            try {
                ProxyRepositoryFactory.getInstance().save(clusterItem.getProperty());
            } catch (PersistenceException e) {
                ExceptionHandler.process(e);
            }
        }
    }

    @Override
    public Map<String, String> getHadoopDbParameters(String clusterId) {
        return HCRepositoryUtil.getHadoopDbParameters(clusterId);
    }

    @Override
    public void removeHadoopDbParameters(DatabaseConnection connection) {
        HCRepositoryUtil.removeHadoopDbParameters(connection);
    }

    @Override
    public void copyHadoopCluster(final Item sourceItem, final IPath path) throws PersistenceException, BusinessException {
        copyHadoopCluster(sourceItem, path, null);
    }

    @Override
    public void copyHadoopCluster(final Item sourceItem, final IPath path, String newName) throws PersistenceException,
            BusinessException {
        if (isHadoopClusterItem(sourceItem)) {
            IProxyRepositoryFactory factory = ProxyRepositoryFactory.getInstance();
            HadoopClusterConnectionItem sourceClusterItem = (HadoopClusterConnectionItem) sourceItem;
            HadoopClusterConnectionItem targetClusterItem = null;
            if (StringUtils.isNotBlank(newName)) {
                targetClusterItem = (HadoopClusterConnectionItem) factory.copy(sourceClusterItem, path, newName);
            } else {
                targetClusterItem = (HadoopClusterConnectionItem) factory.copy(sourceClusterItem, path, true);
            }
            HadoopClusterConnection targetClusterConnection = (HadoopClusterConnection) targetClusterItem.getConnection();
            targetClusterConnection.getConnectionList().clear();
            String targetClusterId = targetClusterItem.getProperty().getId();
            Set<Item> sourceSubitems = HCRepositoryUtil.getSubitemsOfHadoopCluster(sourceClusterItem);
            for (Item subitem : sourceSubitems) {
                Item newSubitem = factory.copy(subitem, path, true);
                if (newSubitem instanceof HadoopSubConnectionItem) {
                    ((HadoopSubConnection) ((HadoopSubConnectionItem) newSubitem).getConnection())
                            .setRelativeHadoopClusterId(targetClusterId);
                    targetClusterConnection.getConnectionList().add(newSubitem.getProperty().getId());
                } else if (subitem instanceof DatabaseConnectionItem) {
                    ((DatabaseConnection) ((DatabaseConnectionItem) newSubitem).getConnection()).getParameters().put(
                            ConnParameterKeys.CONN_PARA_KEY_HADOOP_CLUSTER_ID, targetClusterId);
                }
                factory.save(newSubitem);
            }
            factory.save(targetClusterItem);
        }
    }

    /*
     * (non-Javadoc)
     * 
     * @see org.talend.core.hadoop.IHadoopClusterService#getHadoopCustomLibraries()
     */
    @Override
    public Map<String, String> getHadoopCustomLibraries(String clusterId) {
        Map<String, String> customLibraries = new HashMap<String, String>();
        HadoopClusterConnection hadoopClusterConnection = HCRepositoryUtil.getRelativeHadoopClusterConnection(clusterId);
        if (hadoopClusterConnection != null) {
            EMap<String, String> parameters = hadoopClusterConnection.getParameters();
            for (String key : parameters.keySet()) {
                customLibraries.put(key, parameters.get(key));
            }
        }
        return customLibraries;
    }

    @Override
    public IRepositoryTypeProcessor getHadoopSubMultiRepTypeProcessor(String[] repositoryTypes) {
        return new HadoopSubMultiRepTypeProcessor(repositoryTypes);
    }

    public boolean hasDiffsFromClusterToProcess(Connection hcConnection, IProcess process) {
        if (hcConnection == null || process == null) {
            return false;
        }

        if (hcConnection instanceof HadoopClusterConnection) {
            return HCRepositoryUtil.detectClusterChangeOfProcess((HadoopClusterConnection) hcConnection, process);
        }

        return false;
    }

}
