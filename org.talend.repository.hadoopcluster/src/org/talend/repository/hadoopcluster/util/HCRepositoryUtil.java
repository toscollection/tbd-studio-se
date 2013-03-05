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
package org.talend.repository.hadoopcluster.util;

import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

import org.eclipse.emf.common.util.EList;
import org.talend.commons.exception.PersistenceException;
import org.talend.commons.ui.runtime.exception.ExceptionHandler;
import org.talend.core.model.properties.Item;
import org.talend.core.model.repository.IRepositoryViewObject;
import org.talend.core.repository.model.ProxyRepositoryFactory;
import org.talend.repository.hadoopcluster.node.HadoopFolderRepositoryNode;
import org.talend.repository.hadoopcluster.node.model.HadoopClusterRepositoryNodeType;
import org.talend.repository.model.IProxyRepositoryFactory;
import org.talend.repository.model.IRepositoryNode;
import org.talend.repository.model.IRepositoryNode.ENodeType;
import org.talend.repository.model.IRepositoryNode.EProperties;
import org.talend.repository.model.hadoopcluster.HadoopClusterConnection;
import org.talend.repository.model.hadoopcluster.HadoopClusterConnectionItem;
import org.talend.repository.model.hadoopcluster.HadoopClusterPackage;
import org.talend.repository.model.hadoopcluster.HadoopSubConnection;
import org.talend.repository.model.hadoopcluster.HadoopSubConnectionItem;

/**
 * created by ycbai on 2013-1-22 Detailled comment
 * 
 */
public class HCRepositoryUtil {

    /**
     * DOC ycbai Comment method "isHadoopClusterItem".
     * 
     * Estimate whether or not the item is a hadoop cluster item.
     * 
     * @param item
     * @return
     */
    public static boolean isHadoopClusterItem(Item item) {
        return item.eClass() == HadoopClusterPackage.Literals.HADOOP_CLUSTER_CONNECTION_ITEM;
    }

    /**
     * DOC ycbai Comment method "setupConnectionToHadoopCluster".
     * 
     * Setup the relation from hadoop subnode to hadoop cluster.
     * 
     * @param node
     * @param connectionID
     * @throws PersistenceException
     */
    public static void setupConnectionToHadoopCluster(IRepositoryNode node, String connectionID) throws PersistenceException {
        HadoopClusterConnectionItem hcConnectionItem = getHCConnectionItemFromRepositoryNode(node);
        setupConnectionToHadoopCluster(hcConnectionItem, connectionID);
    }

    public static void setupConnectionToHadoopCluster(HadoopClusterConnectionItem hcConnectionItem, String connectionID)
            throws PersistenceException {
        if (hcConnectionItem != null) {
            HadoopClusterConnection clusterConnection = (HadoopClusterConnection) hcConnectionItem.getConnection();
            clusterConnection.getConnectionList().add(connectionID);
            ProxyRepositoryFactory.getInstance().save(hcConnectionItem);
        }
    }

    /**
     * DOC ycbai Comment method "removeFromHadoopCluster".
     * 
     * Remove the connection from hadoop cluster.
     * 
     * @param clusterConnectionItem
     * @param connectionID
     * @throws PersistenceException
     */
    public static void removeFromHadoopCluster(HadoopClusterConnectionItem clusterConnectionItem, String connectionID)
            throws PersistenceException {
        boolean updated = false;
        IProxyRepositoryFactory factory = ProxyRepositoryFactory.getInstance();
        List<String> connectionList = ((HadoopClusterConnection) clusterConnectionItem.getConnection()).getConnectionList();
        Iterator<String> connsIter = connectionList.iterator();
        while (connsIter.hasNext()) {
            String id = connsIter.next();
            if (id != null && id.equals(connectionID)) {
                connsIter.remove();
                updated = true;
            }
        }
        if (updated) {
            factory.save(clusterConnectionItem);
        }
    }

    /**
     * DOC ycbai Comment method "getHCConnectionItemFromRepositoryNode".
     * 
     * Get the HadoopClusterConnectionItem from RepositoryNode recursively.
     * 
     * @param node
     * @return
     */
    public static HadoopClusterConnectionItem getHCConnectionItemFromRepositoryNode(IRepositoryNode node) {
        IRepositoryViewObject viewObject = node.getObject();
        if (viewObject != null && viewObject.getProperty() != null) {
            Item item = viewObject.getProperty().getItem();
            if (item instanceof HadoopClusterConnectionItem) {
                return (HadoopClusterConnectionItem) item;
            } else if (item instanceof HadoopSubConnectionItem) {
                return getRelativeHadoopClusterItem((HadoopSubConnectionItem) item);
            }
        } else if (isHadoopFolderNode(node)) {
            return getHCConnectionItemFromRepositoryNode(node.getParent());
        }

        return null;
    }

    /**
     * DOC ycbai Comment method "getRelativeHadoopClusterConnection".
     * 
     * Get the HadoopClusterConnection from HadoopConnection.
     * 
     * @param hadoopConnection
     * @return
     */
    public static HadoopClusterConnection getRelativeHadoopClusterConnection(HadoopSubConnection hadoopSubConnection) {
        if (hadoopSubConnection == null) {
            return null;
        }

        return getRelativeHadoopClusterConnection(hadoopSubConnection.getRelativeHadoopClusterId());
    }

    public static HadoopClusterConnection getRelativeHadoopClusterConnection(String hadoopClusterId) {
        HadoopClusterConnectionItem hadoopClusterItem = getRelativeHadoopClusterItem(hadoopClusterId);
        if (hadoopClusterItem != null) {
            return (HadoopClusterConnection) hadoopClusterItem.getConnection();
        }

        return null;
    }

    public static HadoopClusterConnection getRelativeHadoopClusterConnection(HadoopSubConnectionItem hadoopSubConnectionItem) {
        if (hadoopSubConnectionItem == null) {
            return null;
        }

        return getRelativeHadoopClusterConnection((HadoopSubConnection) hadoopSubConnectionItem.getConnection());
    }

    public static HadoopClusterConnectionItem getRelativeHadoopClusterItem(HadoopSubConnectionItem hadoopSubConnectionItem) {
        if (hadoopSubConnectionItem == null) {
            return null;
        }

        return getRelativeHadoopClusterItem(((HadoopSubConnection) hadoopSubConnectionItem.getConnection())
                .getRelativeHadoopClusterId());
    }

    public static HadoopClusterConnectionItem getRelativeHadoopClusterItem(String hadoopClusterId) {
        if (hadoopClusterId != null) {
            IRepositoryViewObject repObj = null;
            try {
                repObj = ProxyRepositoryFactory.getInstance().getLastVersion(hadoopClusterId);
            } catch (PersistenceException e) {
                ExceptionHandler.process(e);
            }
            if (repObj != null && repObj.getProperty() != null) {
                Item item = repObj.getProperty().getItem();
                if (isHadoopClusterItem(item)) {
                    return (HadoopClusterConnectionItem) item;
                }
            }
        }

        return null;
    }

    /**
     * DOC ycbai Comment method "getSubitemsOfHadoopCluster".
     * 
     * Get subitems of hadoop cluster like hdfs, hcatalog, hive etc.
     * 
     * @param item
     * @return
     */
    public static Set<Item> getSubitemsOfHadoopCluster(Item item) {
        Set<Item> subItems = new HashSet<Item>();
        if (item.eClass() != HadoopClusterPackage.Literals.HADOOP_CLUSTER_CONNECTION_ITEM) {
            return subItems;
        }
        HadoopClusterConnectionItem clusterConnectionItem = (HadoopClusterConnectionItem) item;
        HadoopClusterConnection clusterConnection = (HadoopClusterConnection) clusterConnectionItem.getConnection();
        EList<String> connectionList = clusterConnection.getConnectionList();
        for (String connId : connectionList) {
            if (connId != null) {
                IRepositoryViewObject repObj = null;
                try {
                    repObj = ProxyRepositoryFactory.getInstance().getLastVersion(connId);
                } catch (PersistenceException e) {
                    ExceptionHandler.process(e);
                }
                if (repObj != null && repObj.getProperty() != null) {
                    Item subItem = repObj.getProperty().getItem();
                    if (subItem != null) {
                        subItems.add(subItem);
                    }
                }
            }
        }

        return subItems;
    }

    /**
     * DOC ycbai Comment method "isHadoopNode".
     * 
     * Estimate whether or not the node belong to hadoop cluster.
     * 
     * @param node
     * @return
     */
    public static boolean isHadoopNode(IRepositoryNode node) {
        return getHCConnectionItemFromRepositoryNode(node) != null;
    }

    /**
     * DOC ycbai Comment method "isHadoopSubnode".
     * 
     * Estimate whether or not the node is a subnode of a hadoop cluster.
     * 
     * @param node
     * @return
     */
    public static boolean isHadoopSubnode(IRepositoryNode node) {
        return !isHadoopClusterNode(node) && isHadoopNode(node);
    }

    /**
     * DOC ycbai Comment method "isHadoopFolderNode".
     * 
     * Estimate whether or not the folder node belong to hadoop cluster.
     * 
     * @param node
     * @return
     */
    public static boolean isHadoopFolderNode(IRepositoryNode node) {
        return node instanceof HadoopFolderRepositoryNode;
    }

    /**
     * DOC ycbai Comment method "isHadoopContainerNode".
     * 
     * Estimate whether or not the node can be a container.
     * 
     * @param node
     * @return
     */
    public static boolean isHadoopContainerNode(IRepositoryNode node) {
        return isHadoopClusterNode(node) || isHadoopFolderNode(node);
    }

    /**
     * DOC ycbai Comment method "isHadoopClusterNode".
     * 
     * Estimate whether or not the type of node is HadoopClusterRepositoryNodeType.HADOOPCLUSTER.
     * 
     * @param node
     * @return
     */
    public static boolean isHadoopClusterNode(IRepositoryNode node) {
        if (node == null || node.getObject() == null || !ENodeType.REPOSITORY_ELEMENT.equals(node.getType())) {
            return false;
        }

        return HadoopClusterRepositoryNodeType.HADOOPCLUSTER.equals(node.getProperties(EProperties.CONTENT_TYPE));
    }

    public static boolean isHadoopClusterNode(IRepositoryViewObject repObj) {
        if (repObj == null) {
            return false;
        }
        IRepositoryNode repNode = repObj.getRepositoryNode();
        if (repNode == null) {
            return false;
        }

        return isHadoopClusterNode(repNode);
    }

}
