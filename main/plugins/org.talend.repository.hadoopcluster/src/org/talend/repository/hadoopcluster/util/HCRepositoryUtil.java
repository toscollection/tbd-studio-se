// ============================================================================
//
// Copyright (C) 2006-2021 Talend Inc. - www.talend.com
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

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.common.util.EMap;
import org.talend.commons.exception.ExceptionHandler;
import org.talend.commons.exception.PersistenceException;
import org.talend.core.GlobalServiceRegister;
import org.talend.core.database.conn.ConnParameterKeys;
import org.talend.core.hadoop.IHadoopDistributionService;
import org.talend.core.hadoop.conf.EHadoopProperties;
import org.talend.core.model.general.Project;
import org.talend.core.model.metadata.builder.connection.Connection;
import org.talend.core.model.metadata.builder.connection.DatabaseConnection;
import org.talend.core.model.process.IProcess;
import org.talend.core.model.properties.ConnectionItem;
import org.talend.core.model.properties.DatabaseConnectionItem;
import org.talend.core.model.properties.Item;
import org.talend.core.model.properties.Property;
import org.talend.core.model.repository.ERepositoryObjectType;
import org.talend.core.model.repository.IRepositoryViewObject;
import org.talend.core.repository.model.ProjectRepositoryNode;
import org.talend.core.repository.model.ProxyRepositoryFactory;
import org.talend.core.runtime.hd.IHDistribution;
import org.talend.core.runtime.hd.IHDistributionVersion;
import org.talend.designer.hdfsbrowse.manager.HadoopParameterUtil;
import org.talend.hadoop.distribution.constants.apache.ISparkDistribution;
import org.talend.metadata.managment.ui.utils.ConnectionContextHelper;
import org.talend.repository.ProjectManager;
import org.talend.repository.hadoopcluster.node.HadoopFolderRepositoryNode;
import org.talend.repository.hadoopcluster.node.model.HadoopClusterRepositoryNodeType;
import org.talend.repository.hadoopcluster.tester.HadoopClusterMetadataNodeTester;
import org.talend.repository.hadoopcluster.ui.viewer.HadoopSubnodeRepositoryContentManager;
import org.talend.repository.hadoopcluster.ui.viewer.handler.IHadoopSubnodeRepositoryContentHandler;
import org.talend.repository.model.IProxyRepositoryFactory;
import org.talend.repository.model.IRepositoryNode;
import org.talend.repository.model.IRepositoryNode.ENodeType;
import org.talend.repository.model.IRepositoryNode.EProperties;
import org.talend.repository.model.RepositoryNode;
import org.talend.repository.model.hadoopcluster.HadoopClusterConnection;
import org.talend.repository.model.hadoopcluster.HadoopClusterConnectionItem;
import org.talend.repository.model.hadoopcluster.HadoopClusterPackage;
import org.talend.repository.model.hadoopcluster.HadoopSubConnection;
import org.talend.repository.model.hadoopcluster.HadoopSubConnectionItem;
import org.talend.repository.model.hadoopcluster.util.EncryptionUtil;

/**
 * created by ycbai on 2013-1-22 Detailled comment
 *
 */
public class HCRepositoryUtil {

    private static List<String> hadoopDbParameters = null;

    private static HadoopClusterMetadataNodeTester hcTester = null;

    static {
        hadoopDbParameters = new ArrayList<String>();
        hadoopDbParameters.add(ConnParameterKeys.CONN_PARA_KEY_HADOOP_CLUSTER_ID);
        hadoopDbParameters.add(ConnParameterKeys.CONN_PARA_KEY_HADOOP_CUSTOM_JARS);
        hadoopDbParameters.add(ConnParameterKeys.CONN_PARA_KEY_NAME_NODE_URL);
        hadoopDbParameters.add(ConnParameterKeys.CONN_PARA_KEY_JOB_TRACKER_URL);
        hadoopDbParameters.add(ConnParameterKeys.CONN_PARA_KEY_USE_KRB);
        hadoopDbParameters.add(ConnParameterKeys.CONN_PARA_KEY_NAME_NODE_PRINCIPAL);
        hadoopDbParameters.add(ConnParameterKeys.CONN_PARA_KEY_JOB_TRACKER_PRINCIPAL);
        hadoopDbParameters.add(ConnParameterKeys.CONN_PARA_KEY_JOB_HISTORY_PRINCIPAL);
        hadoopDbParameters.add(ConnParameterKeys.CONN_PARA_KEY_DB_SERVER);
        hadoopDbParameters.add(ConnParameterKeys.CONN_PARA_KEY_USERNAME);
        hadoopDbParameters.add(ConnParameterKeys.CONN_PARA_KEY_HIVE_DISTRIBUTION);
        hadoopDbParameters.add(ConnParameterKeys.CONN_PARA_KEY_HIVE_VERSION);
        hadoopDbParameters.add(ConnParameterKeys.CONN_PARA_KEY_HBASE_DISTRIBUTION);
        hadoopDbParameters.add(ConnParameterKeys.CONN_PARA_KEY_HBASE_VERSION);
        hadoopDbParameters.add(ConnParameterKeys.CONN_PARA_KEY_USEKEYTAB);
        hadoopDbParameters.add(ConnParameterKeys.CONN_PARA_KEY_KEYTAB_PRINCIPAL);
        hadoopDbParameters.add(ConnParameterKeys.CONN_PARA_KEY_KEYTAB);
        hadoopDbParameters.add(ConnParameterKeys.CONN_PARA_KEY_HBASE_AUTHENTICATION_USE_MAPRTICKET);
        hadoopDbParameters.add(ConnParameterKeys.CONN_PARA_KEY_HBASE_AUTHENTICATION_USERNAME);
        hadoopDbParameters.add(ConnParameterKeys.CONN_PARA_KEY_HBASE_AUTHENTICATION_MAPRTICKET_PASSWORD);
        hadoopDbParameters.add(ConnParameterKeys.CONN_PARA_KEY_HBASE_AUTHENTICATION_MAPRTICKET_CLUSTER);
        hadoopDbParameters.add(ConnParameterKeys.CONN_PARA_KEY_HBASE_AUTHENTICATION_MAPRTICKET_DURATION);
        hadoopDbParameters.add(ConnParameterKeys.CONN_PARA_KEY_HIVE_AUTHENTICATION_USE_MAPRTICKET);
        hadoopDbParameters.add(ConnParameterKeys.CONN_PARA_KEY_HIVE_AUTHENTICATION_USERNAME);
        hadoopDbParameters.add(ConnParameterKeys.CONN_PARA_KEY_HIVE_AUTHENTICATION_MAPRTICKET_PASSWORD);
        hadoopDbParameters.add(ConnParameterKeys.CONN_PARA_KEY_HIVE_AUTHENTICATION_MAPRTICKET_CLUSTER);
        hadoopDbParameters.add(ConnParameterKeys.CONN_PARA_KEY_HIVE_AUTHENTICATION_MAPRTICKET_DURATION);
        hadoopDbParameters.add(ConnParameterKeys.CONN_PARA_KEY_MAPRTICKET_SETMAPRHOMEDIR);
        hadoopDbParameters.add(ConnParameterKeys.CONN_PARA_KEY_MAPRTICKET_MAPRHOMEDIR);
        hadoopDbParameters.add(ConnParameterKeys.CONN_PARA_KEY_MAPRTICKET_SETMAPRHADOOPLOGIN);
        hadoopDbParameters.add(ConnParameterKeys.CONN_PARA_KEY_MAPRTICKET_MAPRHADOOPLOGIN);
        hadoopDbParameters.add(ConnParameterKeys.CONN_PARA_KEY_MAPRDB_DISTRIBUTION);
        hadoopDbParameters.add(ConnParameterKeys.CONN_PARA_KEY_MAPRDB_VERSION);
        hadoopDbParameters.add(ConnParameterKeys.CONN_PARA_KEY_MAPRDB_AUTHENTICATION_USE_MAPRTICKET);
        hadoopDbParameters.add(ConnParameterKeys.CONN_PARA_KEY_MAPRDB_AUTHENTICATION_USERNAME);
        hadoopDbParameters.add(ConnParameterKeys.CONN_PARA_KEY_MAPRDB_AUTHENTICATION_MAPRTICKET_PASSWORD);
        hadoopDbParameters.add(ConnParameterKeys.CONN_PARA_KEY_MAPRDB_AUTHENTICATION_MAPRTICKET_CLUSTER);
        hadoopDbParameters.add(ConnParameterKeys.CONN_PARA_KEY_MAPRDB_AUTHENTICATION_MAPRTICKET_DURATION);
        hadoopDbParameters.add(ConnParameterKeys.CONN_PARA_KEY_HIVE_GOOGLE_PROJECT_ID);
        hadoopDbParameters.add(ConnParameterKeys.CONN_PARA_KEY_HIVE_GOOGLE_CLUSTER_ID);
        hadoopDbParameters.add(ConnParameterKeys.CONN_PARA_KEY_HIVE_GOOGLE_REGION);
        hadoopDbParameters.add(ConnParameterKeys.CONN_PARA_KEY_HIVE_GOOGLE_JARS_BUCKET);
        hadoopDbParameters.add(ConnParameterKeys.CONN_PARA_KEY_HIVE_AUTHENTICATION_DEFINE_PATH_TO_GOOGLE_CREDENTIALS);
        hadoopDbParameters.add(ConnParameterKeys.CONN_PARA_KEY_HIVE_AUTHENTICATION_PATH_TO_GOOGLE_CREDENTIALS);
    }

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
        ERepositoryObjectType repositoryObjectType = viewObject != null ? viewObject.getRepositoryObjectType() : null;
        if (viewObject != null
                && repositoryObjectType != null
                && (repositoryObjectType.equals(ERepositoryObjectType.METADATA_CONNECTIONS)
                        || repositoryObjectType.equals(HadoopClusterRepositoryNodeType.HADOOPCLUSTER) || isHadoopSubItem(
                            repositoryObjectType, node))) {
            final Property property = viewObject.getProperty();
            if (property != null) {
                Item item = property.getItem();
                if (item instanceof HadoopClusterConnectionItem) {
                    return (HadoopClusterConnectionItem) item;
                } else if (item instanceof ConnectionItem) {
                    return getRelativeHadoopClusterItem(item);
                }
            }
        } else if (isHadoopFolderNode(node)) {
            return getHCConnectionItemFromRepositoryNode(node.getParent());
        }

        return null;
    }

    private static boolean isHadoopSubItem(ERepositoryObjectType repType, IRepositoryNode node) {

        if (ERepositoryObjectType.METADATA_CON_TABLE.equals(repType) || ERepositoryObjectType.METADATA_CON_COLUMN.equals(repType)) {
            List<ERepositoryObjectType> allLinkedTypes = getAllLinkedTypes(node);
            if (allLinkedTypes.contains(ERepositoryObjectType.METADATA_CONNECTIONS)
                    || allLinkedTypes.contains(HadoopClusterRepositoryNodeType.HADOOPCLUSTER)) {
                return true;
            }
        }

        /**
         * Don't use the ERepositoryObjectType.findParentType, just to improve the performance.
         */
        ERepositoryObjectType[] parentTypes = repType.getParentTypesArray();
        if (parentTypes != null && 0 < parentTypes.length) {
            for (ERepositoryObjectType parentType : parentTypes) {
                if (HadoopClusterRepositoryNodeType.HADOOPCLUSTER.equals(parentType)) {
                    return true;
                }
            }
        }

        if (node instanceof RepositoryNode) {
            RepositoryNode repositoryNode = (RepositoryNode) node;
            HadoopClusterMetadataNodeTester hcNodeTester = getHadoopClusterMetadataNodeTester();
            if (hcNodeTester.isHadoopSubNodeSchema(repositoryNode)) {
                return true;
            }
            if (hcNodeTester.isHadoopSubNodeSchemaColumn(repositoryNode)) {
                return true;
            }
        }

        return false;
    }

    private static HadoopClusterMetadataNodeTester getHadoopClusterMetadataNodeTester() {
        if (hcTester == null) {
            hcTester = new HadoopClusterMetadataNodeTester();
        }
        return hcTester;
    }

    private static List<ERepositoryObjectType> getAllLinkedTypes(IRepositoryNode node) {
        List<ERepositoryObjectType> objTypes = new ArrayList<ERepositoryObjectType>();

        IRepositoryNode curNode = node;
        Set<IRepositoryNode> checkedNodeSet = new HashSet<IRepositoryNode>();
        while (curNode != null) {
            if (curNode instanceof ProjectRepositoryNode) {
                break;
            }
            if (checkedNodeSet.contains(curNode)) {
                break;
            } else {
                checkedNodeSet.add(curNode);
            }

            ERepositoryObjectType curType = curNode.getObjectType();
            if (curType != null) {
                objTypes.add(curType);
            }
            curNode = curNode.getParent();
        }

        return objTypes;
    }

    /**
     * DOC ycbai Comment method "getRelativeHadoopClusterConnection".
     *
     * Get the HadoopClusterConnection from HadoopConnection.
     *
     * @param hadoopConnection
     * @return
     */
    public static HadoopClusterConnection getRelativeHadoopClusterConnection(Connection hadoopSubConnection) {
        if (hadoopSubConnection == null) {
            return null;
        }
        if (hadoopSubConnection instanceof HadoopSubConnection) {
            return getRelativeHadoopClusterConnection(((HadoopSubConnection) hadoopSubConnection).getRelativeHadoopClusterId());
        } else if (hadoopSubConnection instanceof DatabaseConnection) {
            DatabaseConnection dbConnection = (DatabaseConnection) hadoopSubConnection;
            String hcId = dbConnection.getParameters().get(ConnParameterKeys.CONN_PARA_KEY_HADOOP_CLUSTER_ID);
            return getRelativeHadoopClusterConnection(hcId);
        }

        return null;
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

        return getRelativeHadoopClusterConnection(hadoopSubConnectionItem.getConnection());
    }

    public static HadoopClusterConnectionItem getRelativeHadoopClusterItem(Item subConnectionItem) {
        if (subConnectionItem == null) {
            return null;
        }

        String hcId = null;
        if (subConnectionItem instanceof HadoopSubConnectionItem) {
            hcId = ((HadoopSubConnection) ((HadoopSubConnectionItem) subConnectionItem).getConnection())
                    .getRelativeHadoopClusterId();
        } else if (subConnectionItem instanceof DatabaseConnectionItem) {
            Connection connection = ((DatabaseConnectionItem) subConnectionItem).getConnection();
            if (connection != null && connection instanceof DatabaseConnection) {
                hcId = ((DatabaseConnection) connection).getParameters().get(ConnParameterKeys.CONN_PARA_KEY_HADOOP_CLUSTER_ID);
            }
        }

        return getRelativeHadoopClusterItem(hcId);
    }

    public static HadoopClusterConnectionItem getHadoopClusterItemBySubitemId(String subitemId) {
        ProjectManager projectManager = ProjectManager.getInstance();
        HadoopClusterConnectionItem item = getHadoopClusterItemBySubitemId(projectManager.getCurrentProject(), subitemId);
        if (item == null) {
            // if not exist in current project, try to find it from reference projects
            List<Project> allReferencedProjects = projectManager.getAllReferencedProjects();
            for (Project referencedProject : allReferencedProjects) {
                item = getHadoopClusterItemBySubitemId(referencedProject, subitemId);
                if (item != null) {
                    break;
                }
            }
        }
        return item;
    }

    public static HadoopClusterConnectionItem getHadoopClusterItemBySubitemId(Project project, String subitemId) {
        if (subitemId == null) {
            return null;
        }

        IRepositoryViewObject repObj = null;
        try {
            repObj = ProxyRepositoryFactory.getInstance().getLastVersion(project, subitemId);
        } catch (PersistenceException e) {
            ExceptionHandler.process(e);
        }
        if (repObj != null && repObj.getProperty() != null) {
            Item item = repObj.getProperty().getItem();
            if (item != null) {
                return getRelativeHadoopClusterItem(item);
            }
        }

        return null;
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

    public static Item getRelativeItem(String id) {
        if (id != null) {
            IRepositoryViewObject repObj = null;
            try {
                repObj = ProxyRepositoryFactory.getInstance().getLastVersion(id);
            } catch (PersistenceException e) {
                ExceptionHandler.process(e);
            }
            if (repObj != null && repObj.getProperty() != null) {
                return repObj.getProperty().getItem();
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
     * @throws PersistenceException
     */
    public static Set<Item> getSubitemsOfHadoopCluster(Item item) throws PersistenceException {
        Set<Item> subItems = new HashSet<Item>();
        if (item.eClass() != HadoopClusterPackage.Literals.HADOOP_CLUSTER_CONNECTION_ITEM) {
            return subItems;
        }

        Project project = new Project(ProjectManager.getInstance().getProject(item.getProperty()));
        HadoopClusterConnectionItem clusterConnectionItem = (HadoopClusterConnectionItem) item;
        HadoopClusterConnection clusterConnection = (HadoopClusterConnection) clusterConnectionItem.getConnection();
        EList<String> connectionList = clusterConnection.getConnectionList();
        for (String connId : connectionList) {
            if (connId != null) {
                IRepositoryViewObject repObj = ProxyRepositoryFactory.getInstance().getLastVersion(project, connId);
                if (repObj != null && repObj.getProperty() != null) {
                    Item subItem = repObj.getProperty().getItem();
                    if (subItem != null) {
                        subItems.add(subItem);
                    }
                }
            }
        }
        String clusterId = clusterConnectionItem.getProperty().getId();
        List<IRepositoryViewObject> repObjs = ProxyRepositoryFactory.getInstance().getAll(project,
                ERepositoryObjectType.METADATA_CONNECTIONS);
        for (IRepositoryViewObject repObj : repObjs) {
            if (repObj != null && repObj.getProperty() != null) {
                DatabaseConnectionItem dbItem = (DatabaseConnectionItem) repObj.getProperty().getItem();
                DatabaseConnection dbConnection = (DatabaseConnection) dbItem.getConnection();
                String hcId = dbConnection.getParameters().get(ConnParameterKeys.CONN_PARA_KEY_HADOOP_CLUSTER_ID);
                if (clusterId.equals(hcId)) {
                    subItems.add(dbItem);
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

    public static List<DatabaseConnectionItem> getHadoopRelatedDbConnectionItems(String clusterId) {
        List<DatabaseConnectionItem> dbConnItems = new ArrayList<DatabaseConnectionItem>();
        try {
            List<IRepositoryViewObject> repObjs = ProxyRepositoryFactory.getInstance().getAll(
                    ERepositoryObjectType.METADATA_CONNECTIONS);
            for (IRepositoryViewObject repObj : repObjs) {
                if (repObj != null && repObj.getProperty() != null) {
                    DatabaseConnectionItem item = (DatabaseConnectionItem) repObj.getProperty().getItem();
                    DatabaseConnection connection = (DatabaseConnection) item.getConnection();
                    String hcId = connection.getParameters().get(ConnParameterKeys.CONN_PARA_KEY_HADOOP_CLUSTER_ID);
                    if (hcId != null && hcId.equals(clusterId) && !dbConnItems.contains(connection)) {
                        dbConnItems.add(item);
                    }
                }
            }
        } catch (PersistenceException e) {
            ExceptionHandler.process(e);
        }

        return dbConnItems;
    }

    /**
     * DOC ycbai Comment method "getHadoopDbParameters".
     *
     * Get db connection(like hbase, hive) parameters by hadoop cluster.
     *
     * @param clusterId
     * @return
     */
    public static Map<String, String> getHadoopDbParameters(String clusterId) {
        HadoopClusterConnectionItem clusterItem = HCRepositoryUtil.getRelativeHadoopClusterItem(clusterId);
        return getHadoopDbParameters(clusterItem);
    }

    public static Map<String, String> getHadoopDbParameters(HadoopClusterConnectionItem clusterItem) {
        Map<String, String> parameters = new HashMap<>();
        if (clusterItem == null) {
            return parameters;
        }
        HadoopClusterConnection hcConnection = (HadoopClusterConnection) clusterItem.getConnection();
        parameters.put(ConnParameterKeys.CONN_PARA_KEY_HADOOP_CLUSTER_ID, clusterItem.getProperty().getId());
        parameters.put(ConnParameterKeys.CONN_PARA_KEY_NAME_NODE_URL, hcConnection.getNameNodeURI());
        parameters.put(ConnParameterKeys.CONN_PARA_KEY_JOB_TRACKER_URL, hcConnection.getJobTrackerURI());
        parameters.put(ConnParameterKeys.CONN_PARA_KEY_USE_YARN, String.valueOf(hcConnection.isUseYarn()));
        parameters.put(ConnParameterKeys.CONN_PARA_KEY_USE_CUSTOM_CONFS, String.valueOf(hcConnection.isUseCustomConfs()));
        parameters.put(ConnParameterKeys.CONN_PARA_KEY_USE_KRB, String.valueOf(hcConnection.isEnableKerberos()));
        parameters.put(ConnParameterKeys.CONN_PARA_KEY_NAME_NODE_PRINCIPAL, hcConnection.getPrincipal());
        parameters.put(ConnParameterKeys.CONN_PARA_KEY_JOB_TRACKER_PRINCIPAL, hcConnection.getJtOrRmPrincipal());
        parameters.put(ConnParameterKeys.CONN_PARA_KEY_JOB_HISTORY_PRINCIPAL, hcConnection.getJobHistoryPrincipal());
        parameters.put(ConnParameterKeys.CONN_PARA_KEY_RESOURCEMANAGER_SCHEDULER_ADDRESS, hcConnection.getRmScheduler());
        parameters.put(ConnParameterKeys.CONN_PARA_KEY_JOBHISTORY_ADDRESS, hcConnection.getJobHistory());
        parameters.put(ConnParameterKeys.CONN_PARA_KEY_STAGING_DIRECTORY, hcConnection.getStagingDirectory());
        parameters.put(ConnParameterKeys.CONN_PARA_KEY_USE_DATANODE_HOSTNAME, String.valueOf(hcConnection.isUseDNHost()));
        parameters.put(ConnParameterKeys.CONN_PARA_KEY_DB_SERVER, HadoopParameterUtil
                .getHostNameFromNameNodeURI(ConnectionContextHelper.getParamValueOffContext(hcConnection,
                        hcConnection.getNameNodeURI())));
        parameters.put(ConnParameterKeys.CONN_PARA_KEY_USERNAME, hcConnection.getUserName());
        parameters.put(ConnParameterKeys.CONN_PARA_KEY_HIVE_DISTRIBUTION, hcConnection.getDistribution());
        parameters.put(ConnParameterKeys.CONN_PARA_KEY_HIVE_VERSION, hcConnection.getDfVersion());
        parameters.put(ConnParameterKeys.CONN_PARA_KEY_HBASE_DISTRIBUTION, hcConnection.getDistribution());
        parameters.put(ConnParameterKeys.CONN_PARA_KEY_HBASE_VERSION, hcConnection.getDfVersion());
        parameters.put(ConnParameterKeys.CONN_PARA_KEY_MAPRDB_DISTRIBUTION, hcConnection.getDistribution());
        parameters.put(ConnParameterKeys.CONN_PARA_KEY_MAPRDB_VERSION, hcConnection.getDfVersion());
        //
        parameters.put(ConnParameterKeys.CONN_PARA_KEY_HIVE_GOOGLE_PROJECT_ID,
                hcConnection.getParameters().get(ConnParameterKeys.CONN_PARA_KEY_GOOGLE_PROJECT_ID));
        parameters.put(ConnParameterKeys.CONN_PARA_KEY_HIVE_GOOGLE_CLUSTER_ID,
                hcConnection.getParameters().get(ConnParameterKeys.CONN_PARA_KEY_GOOGLE_CLUSTER_ID));
        parameters.put(ConnParameterKeys.CONN_PARA_KEY_HIVE_GOOGLE_REGION,
                hcConnection.getParameters().get(ConnParameterKeys.CONN_PARA_KEY_GOOGLE_REGION));
        parameters.put(ConnParameterKeys.CONN_PARA_KEY_HIVE_GOOGLE_JARS_BUCKET,
                hcConnection.getParameters().get(ConnParameterKeys.CONN_PARA_KEY_GOOGLE_JARS_BUCKET));
        parameters.put(ConnParameterKeys.CONN_PARA_KEY_HIVE_AUTHENTICATION_DEFINE_PATH_TO_GOOGLE_CREDENTIALS,
                hcConnection.getParameters().get(ConnParameterKeys.CONN_PARA_KEY_DEFINE_PATH_TO_GOOGLE_CREDENTIALS));
        parameters.put(ConnParameterKeys.CONN_PARA_KEY_HIVE_AUTHENTICATION_PATH_TO_GOOGLE_CREDENTIALS,
                hcConnection.getParameters().get(ConnParameterKeys.CONN_PARA_KEY_PATH_TO_GOOGLE_CREDENTIALS));
        if (hcConnection.isEnableKerberos()) {
            parameters.put(ConnParameterKeys.CONN_PARA_KEY_USEKEYTAB, String.valueOf(hcConnection.isUseKeytab()));
            if (hcConnection.isUseKeytab()) {
                parameters.put(ConnParameterKeys.CONN_PARA_KEY_KEYTAB_PRINCIPAL, hcConnection.getKeytabPrincipal());
                parameters.put(ConnParameterKeys.CONN_PARA_KEY_KEYTAB, hcConnection.getKeytab());
            }
        }
        if (hcConnection.isUseWebHDFSSSL()) {
            parameters.put(ConnParameterKeys.CONN_PARA_KEY_USE_WEBHDFS_SSL, String.valueOf(hcConnection.isUseWebHDFSSSL()));
            parameters.put(ConnParameterKeys.CONN_PARA_KEY_WEBHDFS_SSL_TRUST_STORE_PATH,
                    hcConnection.getWebHDFSSSLTrustStorePath());
            parameters.put(ConnParameterKeys.CONN_PARA_KEY_WEBHDFS_SSL_TRUST_STORE_PASSWORD,
                    hcConnection.getWebHDFSSSLTrustStorePassword());
        }
        // hbase/hive/maprdb
        parameters.put(ConnParameterKeys.CONN_PARA_KEY_HBASE_AUTHENTICATION_USE_MAPRTICKET,
                String.valueOf(hcConnection.isEnableMaprT()));
        parameters.put(ConnParameterKeys.CONN_PARA_KEY_MAPRDB_AUTHENTICATION_USE_MAPRTICKET,
                String.valueOf(hcConnection.isEnableMaprT()));
        parameters.put(ConnParameterKeys.CONN_PARA_KEY_HIVE_AUTHENTICATION_USE_MAPRTICKET,
                String.valueOf(hcConnection.isEnableMaprT()));
        if (hcConnection.isEnableMaprT()) {
            parameters.put(ConnParameterKeys.CONN_PARA_KEY_HBASE_AUTHENTICATION_USERNAME, hcConnection.getUserName());
            parameters.put(ConnParameterKeys.CONN_PARA_KEY_HBASE_AUTHENTICATION_MAPRTICKET_PASSWORD,
                    hcConnection.getMaprTPassword());
            parameters.put(ConnParameterKeys.CONN_PARA_KEY_HBASE_AUTHENTICATION_MAPRTICKET_CLUSTER,
                    hcConnection.getMaprTCluster());
            parameters.put(ConnParameterKeys.CONN_PARA_KEY_HBASE_AUTHENTICATION_MAPRTICKET_DURATION,
                    hcConnection.getMaprTDuration());
            parameters.put(ConnParameterKeys.CONN_PARA_KEY_MAPRDB_AUTHENTICATION_USERNAME, hcConnection.getUserName());
            parameters.put(ConnParameterKeys.CONN_PARA_KEY_MAPRDB_AUTHENTICATION_MAPRTICKET_PASSWORD,
                    hcConnection.getMaprTPassword());
            parameters.put(ConnParameterKeys.CONN_PARA_KEY_MAPRDB_AUTHENTICATION_MAPRTICKET_CLUSTER,
                    hcConnection.getMaprTCluster());
            parameters.put(ConnParameterKeys.CONN_PARA_KEY_MAPRDB_AUTHENTICATION_MAPRTICKET_DURATION,
                    hcConnection.getMaprTDuration());
            parameters.put(ConnParameterKeys.CONN_PARA_KEY_HIVE_AUTHENTICATION_USERNAME, hcConnection.getUserName());
            parameters.put(ConnParameterKeys.CONN_PARA_KEY_HIVE_AUTHENTICATION_MAPRTICKET_PASSWORD,
                    hcConnection.getMaprTPassword());
            parameters
                    .put(ConnParameterKeys.CONN_PARA_KEY_HIVE_AUTHENTICATION_MAPRTICKET_CLUSTER, hcConnection.getMaprTCluster());
            parameters.put(ConnParameterKeys.CONN_PARA_KEY_HIVE_AUTHENTICATION_MAPRTICKET_DURATION,
                    hcConnection.getMaprTDuration());
            parameters.put(ConnParameterKeys.CONN_PARA_KEY_MAPRTICKET_SETMAPRHOMEDIR,
                    String.valueOf(hcConnection.isSetMaprTHomeDir()));
            parameters.put(ConnParameterKeys.CONN_PARA_KEY_MAPRTICKET_MAPRHOMEDIR,
                    ConnectionContextHelper.getParamValueOffContext(hcConnection, hcConnection.getMaprTHomeDir()));
            parameters.put(ConnParameterKeys.CONN_PARA_KEY_MAPRTICKET_SETMAPRHADOOPLOGIN,
                    String.valueOf(hcConnection.isSetHadoopLogin()));
            parameters.put(ConnParameterKeys.CONN_PARA_KEY_MAPRTICKET_MAPRHADOOPLOGIN,
                    ConnectionContextHelper.getParamValueOffContext(hcConnection, hcConnection.getMaprTHadoopLogin()));
        }
        return parameters;
    }

    /**
     * DOC ycbai Comment method "removeHadoopDbParameters".
     *
     * Remove all db connection related parameters.
     *
     * @param connection
     */
    public static void removeHadoopDbParameters(DatabaseConnection connection) {
        EMap<String, String> parameters = connection.getParameters();
        Iterator<Entry<String, String>> iterator = parameters.entrySet().iterator();
        while (iterator.hasNext()) {
            Map.Entry<String, String> entry = iterator.next();
            if (hadoopDbParameters.contains(entry.getKey())) {
                iterator.remove();
            }
        }
    }

    /**
     * DOC ycbai Comment method "detectClusterChangeOfMRProcess".
     *
     * <p>
     * Detect the changes of hadoop related parameters from Process to Hadoop cluster.
     * </p>
     *
     * @param hcConnection
     * @param process
     * @return true if there are some changes from them, otherwise return false.
     */
    public static boolean detectClusterChangeOfProcess(HadoopClusterConnection hcConnection, IProcess process) {
        String distribution = hcConnection.getDistribution();
        String version = hcConnection.getDfVersion();
        String customJars = hcConnection.getParameters().get(ConnParameterKeys.CONN_PARA_KEY_HADOOP_CLUSTER_ID);
        String namenode = hcConnection.getNameNodeURI();
        String jobTracker = hcConnection.getJobTrackerURI();
        boolean useKrb = hcConnection.isEnableKerberos();
        String nnPrincipal = hcConnection.getPrincipal();
        String userName = hcConnection.getUserName();

        String distributionPr = HCParameterUtil.getParameterRealValue(process, process,
                EHadoopRepositoryToMapReduce.DISTRIBUTION.getParameterName());
        String versionPr = HCParameterUtil.getParameterRealValue(process, process,
                EHadoopRepositoryToMapReduce.VERSION.getParameterName());
        String customJarsPr = HCParameterUtil.getParameterRealValue(process, process,
                EHadoopRepositoryToMapReduce.CUSTOM_JARS.getParameterName());
        String namenodePr = HCParameterUtil.getParameterRealValue(process, process,
                EHadoopRepositoryToMapReduce.NAMENODE.getParameterName());
        String jobTrackerPr = HCParameterUtil.getParameterRealValue(process, process,
                EHadoopRepositoryToMapReduce.JOB_TRACKER.getParameterName());
        String useKrbPr = HCParameterUtil.getParameterRealValue(process, process,
                EHadoopRepositoryToMapReduce.USE_KRB.getParameterName());
        String nnPrincipalPr = HCParameterUtil.getParameterRealValue(process, process,
                EHadoopRepositoryToMapReduce.NN_PRINCIPAL.getParameterName());
        String userNamePr = HCParameterUtil.getParameterRealValue(process, process,
                EHadoopRepositoryToMapReduce.USERNAME.getParameterName());

        if (distribution != null && !distribution.equals(distributionPr) || version != null && !version.equals(versionPr)
                || customJars != null && !customJars.equals(customJarsPr) || namenode != null && !namenode.equals(namenodePr)
                || jobTracker != null && !jobTracker.equals(jobTrackerPr) || useKrb != Boolean.valueOf(useKrbPr)
                || nnPrincipal != null && !nnPrincipal.equals(nnPrincipalPr) || userName != null && !userName.equals(userNamePr)) {
            return true;
        }

        return false;
    }

    public static void fillDefaultValuesOfHadoopCluster(HadoopClusterConnection connection) {
        String distribution = connection.getDistribution();
        String version = connection.getDfVersion();
        String sparkMode  = connection.getParameters().get(ConnParameterKeys.CONN_PARA_KEY_SPARK_MODE);
        
        if (distribution == null) {
            return;
        }
        IHadoopDistributionService hadoopDistributionService = null;
        if (GlobalServiceRegister.getDefault().isServiceRegistered(IHadoopDistributionService.class)) {
            hadoopDistributionService = GlobalServiceRegister.getDefault().getService(
                    IHadoopDistributionService.class);
        }
        if (hadoopDistributionService == null) {
            return;
        }
        IHDistribution hiveDistribution = hadoopDistributionService.getHadoopDistribution(distribution, false);
        if (hiveDistribution == null) {
            return;
        }
        IHDistributionVersion hiveVersion = hiveDistribution.getHDVersion(version, false);
        if (hiveVersion == null) {
            return;
        }
        boolean isYarn = connection.isUseYarn();

        String defaultJTORRM = null;
        String defaultJTORRMPrincipal = null;
        if (isYarn) {
            defaultJTORRM = hiveVersion.getDefaultConfig(distribution, EHadoopProperties.RESOURCE_MANAGER.getName());
            defaultJTORRMPrincipal = hiveVersion.getDefaultConfig(distribution,
                    EHadoopProperties.RESOURCE_MANAGER_PRINCIPAL.getName());
        } else {
            defaultJTORRM = hiveVersion.getDefaultConfig(distribution, EHadoopProperties.JOBTRACKER.getName());
            defaultJTORRMPrincipal = hiveVersion.getDefaultConfig(distribution, EHadoopProperties.JOBTRACKER_PRINCIPAL.getName());
        }
        if (defaultJTORRM != null) {
            connection.setJobTrackerURI(defaultJTORRM);
        }
        String defaultRMS = hiveVersion.getDefaultConfig(distribution,
                EHadoopProperties.RESOURCEMANAGER_SCHEDULER_ADDRESS.getName());
        if (defaultRMS != null) {
            connection.setRmScheduler(defaultRMS);
        }
        if (defaultJTORRMPrincipal != null) {
            connection.setJtOrRmPrincipal(defaultJTORRMPrincipal);
        }
        String defaultNN = hiveVersion.getDefaultConfig(distribution, EHadoopProperties.NAMENODE_URI.getName());
        if (defaultNN != null) {
            connection.setNameNodeURI(defaultNN);
        }
        String defaultNNP = hiveVersion.getDefaultConfig(distribution, EHadoopProperties.NAMENODE_PRINCIPAL.getName());
        if (defaultNNP != null) {
            connection.setPrincipal(defaultNNP);
        }
        String defaultJobHistoryPrincipal = hiveVersion.getDefaultConfig(distribution,
                EHadoopProperties.JOBHISTORY_PRINCIPAL.getName());
        if (defaultJobHistoryPrincipal != null) {
            connection.setJobHistoryPrincipal(defaultJobHistoryPrincipal);
        }
        String defaultJH = hiveVersion.getDefaultConfig(distribution, EHadoopProperties.JOBHISTORY_ADDRESS.getName());
        if (defaultJH != null) {
            connection.setJobHistory(defaultJH);
        }
        String defaultSD = hiveVersion.getDefaultConfig(distribution, EHadoopProperties.STAGING_DIRECTORY.getName());
        if (defaultSD != null) {
            connection.setStagingDirectory(defaultSD);
        }

        String defaultClouderaUserName = hiveVersion.getDefaultConfig(distribution,
                EHadoopProperties.CLOUDERA_NAVIGATOR_USERNAME.getName());
        if (defaultClouderaUserName != null) {
            connection.setClouderaNaviUserName(defaultClouderaUserName);
        }
        String defaultClouderaPassword = hiveVersion.getDefaultConfig(distribution,
                EHadoopProperties.CLOUDERA_NAVIGATOR_PASSWORD.getName());
        if (defaultClouderaPassword != null) {
            connection.setClouderaNaviPassword(defaultClouderaPassword);
        }
        String defaultClouderaUrl = hiveVersion
                .getDefaultConfig(distribution, EHadoopProperties.CLOUDERA_NAVIGATOR_URL.getName());
        if (defaultClouderaUrl != null) {
            connection.setClouderaNaviUrl(defaultClouderaUrl);
        }
        String defaultClouderaMetUrl = hiveVersion.getDefaultConfig(distribution,
                EHadoopProperties.CLOUDERA_NAVIGATOR_METADATA_URL.getName());
        if (defaultClouderaMetUrl != null) {
            connection.setClouderaNaviMetadataUrl(defaultClouderaMetUrl);
        }
        String defaultClouderaClientUrl = hiveVersion.getDefaultConfig(distribution,
                EHadoopProperties.CLOUDERA_NAVIGATOR_CLIENT_URL.getName());
        if (defaultClouderaClientUrl != null) {
            connection.setClouderaNaviClientUrl(defaultClouderaClientUrl);
        }

        String defaultMaprticket_Cluster = hiveVersion.getDefaultConfig(distribution,
                EHadoopProperties.MAPRTICKET_CLUSTER.getName());
        if (defaultMaprticket_Cluster != null) {
            connection.setMaprTCluster(defaultMaprticket_Cluster);
        }

        String defaultMaprticket_Duration = hiveVersion.getDefaultConfig(distribution,
                EHadoopProperties.MAPRTICKET_DURATION.getName());
        if (defaultMaprticket_Duration != null) {
            connection.setMaprTDuration(defaultMaprticket_Duration);
        }

        String defaultMapr_Home_Dir = hiveVersion.getDefaultConfig(distribution, EHadoopProperties.MAPR_HOME_DIR.getName());
        if (defaultMapr_Home_Dir != null) {
            connection.setMaprTHomeDir(defaultMapr_Home_Dir);
        }

        String defaultHadoop_Login = hiveVersion.getDefaultConfig(distribution, EHadoopProperties.HADOOP_LOGIN.getName());
        if (defaultHadoop_Login != null) {
            connection.setMaprTHadoopLogin(defaultHadoop_Login);
        }
        connection.setPreloadAuthentification(true);

        String defaultGoogleProjectId = hiveVersion.getDefaultConfig(distribution, EHadoopProperties.GOOGLE_PROJECT_ID.getName());
        if (defaultGoogleProjectId != null) {
            connection.getParameters().put(ConnParameterKeys.CONN_PARA_KEY_GOOGLE_PROJECT_ID, defaultGoogleProjectId);
        }

        String defaultGoogleClusterId = hiveVersion.getDefaultConfig(distribution, EHadoopProperties.GOOGLE_CLUSTER_ID.getName());
        if (defaultGoogleClusterId != null) {
            connection.getParameters().put(ConnParameterKeys.CONN_PARA_KEY_GOOGLE_CLUSTER_ID, defaultGoogleClusterId);
        }

        String defaultGoogleRegion = hiveVersion.getDefaultConfig(distribution, EHadoopProperties.GOOGLE_REGION.getName());
        if (defaultGoogleRegion != null) {
            connection.getParameters().put(ConnParameterKeys.CONN_PARA_KEY_GOOGLE_REGION, defaultGoogleRegion);
        }
        String defaultGoogleJarsBucket = hiveVersion.getDefaultConfig(distribution, EHadoopProperties.GOOGLE_JARS_BUCKET.getName());
        if (defaultGoogleJarsBucket != null) {
            connection.getParameters().put(ConnParameterKeys.CONN_PARA_KEY_GOOGLE_JARS_BUCKET, defaultGoogleJarsBucket);
        }
        
        String defaultGoogleAuthMode =  hiveVersion.getDefaultConfig(distribution, EHadoopProperties.AUTH_MODE.getName());
        if (defaultGoogleAuthMode != null) {
            connection.getParameters().put(ConnParameterKeys.CONN_PARA_KEY_AUTH_MODE, defaultGoogleAuthMode);
        }
        
        String defaultPathToGoogleCredentials = hiveVersion.getDefaultConfig(distribution, EHadoopProperties.PATH_TO_GOOGLE_CREDENTIALS.getName());
        if (defaultPathToGoogleCredentials != null) {
            connection.getParameters().put(ConnParameterKeys.CONN_PARA_KEY_PATH_TO_GOOGLE_CREDENTIALS, defaultPathToGoogleCredentials);
        }
        
        String defaultGoogleOauthToken = hiveVersion.getDefaultConfig(distribution, EHadoopProperties.OAUTH_ACCESS_TOKEN.getName());
        if (defaultGoogleOauthToken != null) {
            connection.getParameters().put(ConnParameterKeys.CONN_PARA_OAUTH2_TOKEN_TO_GOOGLE_CREDENTIALS, defaultGoogleOauthToken);
        }
        String defaultWebHCatHostName = hiveVersion.getDefaultConfig(distribution,
                EHadoopProperties.HD_WEBHCAT_HOSTNAME.getName());
        if (defaultWebHCatHostName != null) {
            connection.getParameters().put(ConnParameterKeys.CONN_PARA_KEY_WEB_HCAT_HOSTNAME, defaultWebHCatHostName);
        }

        String defaultWebHCatPort = hiveVersion.getDefaultConfig(distribution, EHadoopProperties.HD_WEBHCAT_PORT.getName());
        if (defaultWebHCatPort != null) {
            connection.getParameters().put(ConnParameterKeys.CONN_PARA_KEY_WEB_HCAT_PORT, defaultWebHCatPort);
        }

        String defaultWebHCatUserName = hiveVersion.getDefaultConfig(distribution,
                EHadoopProperties.HD_WEBHCAT_USERNAME.getName());
        if (defaultWebHCatUserName != null) {
            connection.getParameters().put(ConnParameterKeys.CONN_PARA_KEY_WEB_HCAT_USERNAME, defaultWebHCatUserName);
        }
	//HD Insight default values
        String defaultHDInsightUserName = hiveVersion.getDefaultConfig(distribution,
                EHadoopProperties.HD_INSIGHT_USERNAME.getName());
        if (defaultHDInsightUserName != null) {
            connection.getParameters().put(ConnParameterKeys.CONN_PARA_KEY_HDI_USERNAME, defaultHDInsightUserName);
        }

        String defaultAzureHostName = hiveVersion.getDefaultConfig(distribution, EHadoopProperties.HD_AZURE_HOSTNAME.getName());
        if (defaultAzureHostName != null) {
            connection.getParameters().put(ConnParameterKeys.CONN_PARA_KEY_AZURE_HOSTNAME, defaultAzureHostName);
        }

        String defaultAzureContainer = hiveVersion.getDefaultConfig(distribution, EHadoopProperties.HD_AZURE_CONTAINER.getName());
        if (defaultAzureContainer != null) {
            connection.getParameters().put(ConnParameterKeys.CONN_PARA_KEY_AZURE_CONTAINER, defaultAzureContainer);
        }
	
	String hdiAuthMode = hiveVersion.getDefaultConfig(distribution, EHadoopProperties.HD_ADLSGEN2AUTH.getName());
        if (hdiAuthMode != null) {
            connection.getParameters().put(ConnParameterKeys.CONN_PARA_KEY_HDI_AUTH_MODE, hdiAuthMode);
        }
        
        String defaultAzureUserName = hiveVersion.getDefaultConfig(distribution, EHadoopProperties.HD_AZURE_USERNAME.getName());
        if (defaultAzureUserName != null) {
            connection.getParameters().put(ConnParameterKeys.CONN_PARA_KEY_AZURE_USERNAME, defaultAzureUserName);
        }
        
        String hdiclientId =  hiveVersion.getDefaultConfig(distribution, EHadoopProperties.HD_APPLICATION_ID.getName());
        if (hdiclientId != null) {
        	connection.getParameters().put(ConnParameterKeys.CONN_PARA_KEY_HDI_APPLICATION_ID, hdiclientId);
        }
    
        String hdidirectoryId =  hiveVersion.getDefaultConfig(distribution, EHadoopProperties.HD_DIRECTORY_ID.getName());
        if (hdidirectoryId != null) {
        	connection.getParameters().put(ConnParameterKeys.CONN_PARA_KEY_HDI_DIRECTORY_ID, hdidirectoryId);
        }
        
        String hdiclientKey = hiveVersion.getDefaultConfig(distribution, EHadoopProperties.HD_CLIENT_KEY.getName());
        if (hdiclientKey != null) {
            connection.getParameters().put(ConnParameterKeys.CONN_PARA_KEY_HDI_CLIENT_KEY, hdiclientKey);
        }

        String defaultAzureDeployBlob = hiveVersion.getDefaultConfig(distribution,
                EHadoopProperties.HD_AZURE_DEPLOYBOLB.getName());
        if (defaultAzureDeployBlob != null) {
            connection.getParameters().put(ConnParameterKeys.CONN_PARA_KEY_AZURE_DEPLOY_BLOB, defaultAzureDeployBlob);
        }

        String defaultJobResultFolder = hiveVersion.getDefaultConfig(distribution,
                EHadoopProperties.HD_JOB_RESULT_FOLDER.getName());
        if (defaultJobResultFolder != null) {
            connection.getParameters().put(ConnParameterKeys.CONN_PARA_KEY_WEB_HCAT_JOB_RESULT_FOLDER, defaultJobResultFolder);
        }
        //Synapse default values
        String synapseHostName = hiveVersion.getDefaultConfig(distribution,
                EHadoopProperties.SYNAPSE_ENDPOINT.getName());
        if (synapseHostName != null) {
            connection.getParameters().put(ConnParameterKeys.CONN_PARA_KEY_SYNAPSE_HOST, synapseHostName);
        }
        String synapseAuthToken = hiveVersion.getDefaultConfig(distribution,
        		EHadoopProperties.SYNAPSE_TOKEN.getName());
        if (synapseAuthToken != null) {
            connection.getParameters().put(ConnParameterKeys.CONN_PARA_KEY_SYNAPSE_AUTH_TOKEN, synapseAuthToken);
        }
        String synapseSparkPools = hiveVersion.getDefaultConfig(distribution,
                EHadoopProperties.SPARK_POOL_NAME.getName());
        if (synapseSparkPools != null) {
            connection.getParameters().put(ConnParameterKeys.CONN_PARA_KEY_SYNAPSE_SPARK_POOLS, synapseSparkPools);
        } 
        String synapseFsHostname = hiveVersion.getDefaultConfig(distribution,
                EHadoopProperties.SYNAPSE_STORAGE_HOST.getName());
        if (synapseFsHostname != null) {
            connection.getParameters().put(ConnParameterKeys.CONN_PARA_KEY_SYNAPSE_FS_HOSTNAME, synapseFsHostname);
        }
        String synapseFsContainer = hiveVersion.getDefaultConfig(distribution,
                EHadoopProperties.SYNAPSE_STORAGE_CONTAINER.getName());
        if (synapseFsContainer != null) {
            connection.getParameters().put(ConnParameterKeys.CONN_PARA_KEY_SYNAPSE_FS_CONTAINER, synapseFsContainer);
        }
        String synapseAuthMode = hiveVersion.getDefaultConfig(distribution,
                EHadoopProperties.ADLSGEN2AUTH.getName());
        if (synapseAuthMode != null) {
            connection.getParameters().put(ConnParameterKeys.CONN_PARA_KEY_SYNAPSE_AUTH_MODE, synapseAuthMode);
        }
        String synapseFsUsername =  hiveVersion.getDefaultConfig(distribution,
        		EHadoopProperties.SYNAPSE_STORAGE_USERNAME.getName());
        if (synapseFsUsername != null) {
        	connection.getParameters().put(ConnParameterKeys.CONN_PARA_KEY_SYNAPSE_FS_USERNAME, synapseFsUsername);
        }
        String synapseFsPassword = hiveVersion.getDefaultConfig(distribution,
                EHadoopProperties.SYNAPSE_STORAGE_PASSWORD.getName());
        if (synapseFsPassword != null) {
            connection.getParameters().put(ConnParameterKeys.CONN_PARA_KEY_SYNAPSE_FS_PASSWORD, synapseFsPassword);
        }
        String synapseclientId =  hiveVersion.getDefaultConfig(distribution,
        		EHadoopProperties.SYNAPSE_APPLICATION_ID.getName());
        if (synapseclientId != null) {
        	connection.getParameters().put(ConnParameterKeys.CONN_PARA_KEY_SYNAPSE_APPLICATION_ID, synapseclientId);
        }
        String synapsedirectoryId =  hiveVersion.getDefaultConfig(distribution,
        		EHadoopProperties.SYNAPSE_DIRECTORY_ID.getName());
        if (synapseclientId != null) {
        	connection.getParameters().put(ConnParameterKeys.CONN_PARA_KEY_SYNAPSE_DIRECTORY_ID, synapsedirectoryId);
        }
        String synapseclientKey = hiveVersion.getDefaultConfig(distribution,
                EHadoopProperties.SYNAPSE_CLIENT_KEY.getName());
        if (synapseclientKey != null) {
            connection.getParameters().put(ConnParameterKeys.CONN_PARA_KEY_SYNAPSE_CLIENT_KEY, synapseclientKey);
        }
        String synapseclientCertificate = hiveVersion.getDefaultConfig(distribution,
                EHadoopProperties.SYNAPSE_CLIENT_CERTIFICATE.getName());
        if (synapseclientCertificate != null) {
            connection.getParameters().put(ConnParameterKeys.CONN_PARA_KEY_SYNAPSE_CLIENT_CERTIFICATE, synapseclientCertificate);
        }
        String synapseDeployBlob = hiveVersion.getDefaultConfig(distribution,
                EHadoopProperties.DEPLOY_FOLDER.getName());
        if (synapseDeployBlob != null) {
            connection.getParameters().put(ConnParameterKeys.CONN_PARA_KEY_SYNAPSE_DEPLOY_BLOB, synapseDeployBlob);
        }
        String synapseDriverMemory = hiveVersion.getDefaultConfig(distribution,
                EHadoopProperties.SPARK_DRIVER_MEM.getName());
        if (synapseDriverMemory != null) {
            connection.getParameters().put(ConnParameterKeys.CONN_PARA_KEY_DRIVER_MEMORY, synapseDriverMemory);
        }
        String synapseDriverCores = hiveVersion.getDefaultConfig(distribution,
                EHadoopProperties.SPARK_DRIVER_CORES.getName());
        if (synapseDriverCores != null) {
            connection.getParameters().put(ConnParameterKeys.CONN_PARA_KEY_DRIVER_CORES, synapseDriverCores);
        }
        String synapseExecutorMemory = hiveVersion.getDefaultConfig(distribution,
                EHadoopProperties.SPARK_EXECUTOR_MEMORY.getName());
        if (synapseExecutorMemory != null) {
            connection.getParameters().put(ConnParameterKeys.CONN_PARA_KEY_EXECUTOR_MEMORY, synapseExecutorMemory);
        }
        String synapseExecutorCores = hiveVersion.getDefaultConfig(distribution,
                EHadoopProperties.SPARK_EXECUTOR_CORES.getName());
        if (synapseExecutorMemory != null) {
            connection.getParameters().put(ConnParameterKeys.CONN_PARA_KEY_EXECUTOR_CORES, synapseExecutorCores);
        }
        String univDistributionString = ISparkDistribution.DISTRIBUTION_NAME;
        IHDistribution univDistribution = hadoopDistributionService.getHadoopDistribution(univDistributionString, false);
        if (hiveDistribution == null) {
            return;
        }
        IHDistributionVersion univDefaultVersion = univDistribution.getHDVersion("SPARK_3_1_x", false);
        if (hiveVersion == null) {
            return;
        }
        
        String dataBricksEndpoint = univDefaultVersion.getDefaultConfig(univDistributionString, EHadoopProperties.DATABRICKS_ENDPOINT.getName());
        if (dataBricksEndpoint != null) {
            connection.getParameters().put(ConnParameterKeys.CONN_PARA_KEY_DATABRICKS_ENDPOINT, dataBricksEndpoint);
        }
        String dataBricksCloudProvider = univDefaultVersion.getDefaultConfig(univDistributionString,
                EHadoopProperties.DATABRICKS_CLOUD_PROVIDER.getName());
        if (dataBricksCloudProvider != null) {
            connection.getParameters().put(ConnParameterKeys.CONN_PARA_KEY_DATABRICKS_CLOUD_PROVIDER, dataBricksCloudProvider);
        }
        String dataBricksSubmitMode = univDefaultVersion.getDefaultConfig(univDistributionString,
                EHadoopProperties.DATABRICKS_RUN_MODE.getName());
        if (dataBricksSubmitMode != null) {
            connection.getParameters().put(ConnParameterKeys.CONN_PARA_KEY_DATABRICKS_RUN_MODE, dataBricksSubmitMode);
        }
        String dataBricksClusterID = univDefaultVersion.getDefaultConfig(univDistributionString,
                EHadoopProperties.DATABRICKS_CLUSTER_ID.getName());
        if (dataBricksClusterID != null) {
            connection.getParameters().put(ConnParameterKeys.CONN_PARA_KEY_DATABRICKS_CLUSTER_ID, dataBricksClusterID);
        }
        String databricksToken = univDefaultVersion.getDefaultConfig(univDistributionString, EHadoopProperties.DATABRICKS_TOKEN.getName());
        if (databricksToken != null) {
            connection.getParameters().put(ConnParameterKeys.CONN_PARA_KEY_DATABRICKS_TOKEN, EncryptionUtil.getValue(databricksToken, true));
        }
        String tempsK8sValue = univDefaultVersion.getDefaultConfig(univDistributionString,
                EHadoopProperties.DATABRICKS_DBFS_DEP_FOLDER.getName());
        if (tempsK8sValue != null) {
            connection.getParameters().put(ConnParameterKeys.CONN_PARA_KEY_DATABRICKS_DBFS_DEP_FOLDER, tempsK8sValue);
        }
        String tempK8sValue = univDefaultVersion.getDefaultConfig(univDistributionString,
                EHadoopProperties.K8S_SUBMIT_MODE.getName());
        if (tempsK8sValue != null) {
            connection.getParameters().put(ConnParameterKeys.CONN_PARA_KEY_K8S_SUBMIT_MODE, tempK8sValue);
        }
        tempK8sValue = univDefaultVersion.getDefaultConfig(univDistributionString,
                EHadoopProperties.K8S_MASTER.getName());
        if (tempsK8sValue != null) {
            connection.getParameters().put(ConnParameterKeys.CONN_PARA_KEY_K8S_MASTER, tempK8sValue);
        }
        tempK8sValue = univDefaultVersion.getDefaultConfig(univDistributionString,
                EHadoopProperties.K8S_INSTANCES.getName());
        if (tempsK8sValue != null) {
            connection.getParameters().put(ConnParameterKeys.CONN_PARA_KEY_K8S_INSTANCES, tempK8sValue);
        }
        tempK8sValue = univDefaultVersion.getDefaultConfig(univDistributionString,
                EHadoopProperties.K8S_REGISTRYSECRET.getName());
        if (tempsK8sValue != null) {
            connection.getParameters().put(ConnParameterKeys.CONN_PARA_KEY_K8S_REGISTRYSECRET, tempK8sValue);
        }
        tempK8sValue = univDefaultVersion.getDefaultConfig(univDistributionString,
                EHadoopProperties.K8S_IMAGE.getName());
        if (tempsK8sValue != null) {
            connection.getParameters().put(ConnParameterKeys.CONN_PARA_KEY_K8S_IMAGE, tempK8sValue);
        }
        tempK8sValue = univDefaultVersion.getDefaultConfig(univDistributionString,
                EHadoopProperties.K8S_NAMESPACE.getName());
        if (tempsK8sValue != null) {
            connection.getParameters().put(ConnParameterKeys.CONN_PARA_KEY_K8S_NAMESPACE, tempK8sValue);
        }
        tempK8sValue = univDefaultVersion.getDefaultConfig(univDistributionString,
                EHadoopProperties.K8S_SERVICEACCOUNT.getName());
        if (tempsK8sValue != null) {
            connection.getParameters().put(ConnParameterKeys.CONN_PARA_KEY_K8S_SERVICEACCOUNT, tempK8sValue);
        }
        tempK8sValue = univDefaultVersion.getDefaultConfig(univDistributionString,
                EHadoopProperties.K8S_DISTUPLOAD.getName());
        if (tempsK8sValue != null) {
            connection.getParameters().put(ConnParameterKeys.CONN_PARA_KEY_K8S_DISTUPLOAD, tempK8sValue);
        }
        tempK8sValue = univDefaultVersion.getDefaultConfig(univDistributionString,
                EHadoopProperties.K8S_S3BUCKET.getName());
        if (tempsK8sValue != null) {
            connection.getParameters().put(ConnParameterKeys.CONN_PARA_KEY_K8S_S3BUCKET, tempK8sValue);
        }
        tempK8sValue = univDefaultVersion.getDefaultConfig(univDistributionString,
                EHadoopProperties.K8S_S3FOLDER.getName());
        if (tempsK8sValue != null) {
            connection.getParameters().put(ConnParameterKeys.CONN_PARA_KEY_K8S_S3FOLDER, tempK8sValue);
        }
        tempK8sValue = univDefaultVersion.getDefaultConfig(univDistributionString,
                EHadoopProperties.K8S_S3CREDENTIALS.getName());
        if (tempsK8sValue != null) {
            connection.getParameters().put(ConnParameterKeys.CONN_PARA_KEY_K8S_S3CREDENTIALS, tempK8sValue);
        }
        tempK8sValue = univDefaultVersion.getDefaultConfig(univDistributionString,
                EHadoopProperties.K8S_S3ACCESSKEY.getName());
        if (tempsK8sValue != null) {
            connection.getParameters().put(ConnParameterKeys.CONN_PARA_KEY_K8S_S3ACCESSKEY, tempK8sValue);
        }
        tempK8sValue = univDefaultVersion.getDefaultConfig(univDistributionString,
                EHadoopProperties.K8S_S3SECRETKEY.getName());
        if (tempsK8sValue != null) {
            connection.getParameters().put(ConnParameterKeys.CONN_PARA_KEY_K8S_S3SECRETKEY, EncryptionUtil.getValue(tempK8sValue, true));
        }
        tempK8sValue = univDefaultVersion.getDefaultConfig(univDistributionString,
                EHadoopProperties.K8S_BLOBACCOUNT.getName());
        if (tempsK8sValue != null) {
            connection.getParameters().put(ConnParameterKeys.CONN_PARA_KEY_K8S_BLOBACCOUNT, tempK8sValue);
        }
        tempK8sValue = univDefaultVersion.getDefaultConfig(univDistributionString,
                EHadoopProperties.K8S_BLOBCONTAINER.getName());
        if (tempsK8sValue != null) {
            connection.getParameters().put(ConnParameterKeys.CONN_PARA_KEY_K8S_BLOBCONTAINER, tempK8sValue);
        }
        tempK8sValue = univDefaultVersion.getDefaultConfig(univDistributionString,
                EHadoopProperties.K8S_BLOBSECRETKEY.getName());
        if (tempsK8sValue != null) {
            connection.getParameters().put(ConnParameterKeys.CONN_PARA_KEY_K8S_BLOBSECRETKEY, EncryptionUtil.getValue(tempK8sValue, true));
        }
        tempK8sValue = univDefaultVersion.getDefaultConfig(univDistributionString,
                EHadoopProperties.K8S_AZUREACCOUNT.getName());
        if (tempsK8sValue != null) {
            connection.getParameters().put(ConnParameterKeys.CONN_PARA_KEY_K8S_AZUREACCOUNT, tempK8sValue);
        }
        tempK8sValue = univDefaultVersion.getDefaultConfig(univDistributionString,
                EHadoopProperties.K8S_AZURECREDENTIALS.getName());
        if (tempsK8sValue != null) {
            connection.getParameters().put(ConnParameterKeys.CONN_PARA_KEY_K8S_AZURECREDENTIALS, tempK8sValue);
        }
        tempK8sValue = univDefaultVersion.getDefaultConfig(univDistributionString,
                EHadoopProperties.K8S_AZURECONTAINER.getName());
        if (tempsK8sValue != null) {
            connection.getParameters().put(ConnParameterKeys.CONN_PARA_KEY_K8S_AZURECONTAINER, tempK8sValue);
        }
        tempK8sValue = univDefaultVersion.getDefaultConfig(univDistributionString,
                EHadoopProperties.K8S_AZURESECRETKEY.getName());
        if (tempsK8sValue != null) {
            connection.getParameters().put(ConnParameterKeys.CONN_PARA_KEY_K8S_AZURESECRETKEY, EncryptionUtil.getValue(tempK8sValue, true));
        }
        tempK8sValue = univDefaultVersion.getDefaultConfig(univDistributionString,
                EHadoopProperties.K8S_AZUREAADKEY.getName());
        if (tempsK8sValue != null) {
            connection.getParameters().put(ConnParameterKeys.CONN_PARA_KEY_K8S_AZUREAADKEY, EncryptionUtil.getValue(tempK8sValue, true));
        }
        tempK8sValue = univDefaultVersion.getDefaultConfig(univDistributionString,
                EHadoopProperties.K8S_AZUREAADCLIENTID.getName());
        if (tempsK8sValue != null) {
            connection.getParameters().put(ConnParameterKeys.CONN_PARA_KEY_K8S_AZUREAADCLIENTID, tempK8sValue);
        }
        tempK8sValue = univDefaultVersion.getDefaultConfig(univDistributionString,
                EHadoopProperties.K8S_AZUREAADDIRECTORYID.getName());
        if (tempsK8sValue != null) {
            connection.getParameters().put(ConnParameterKeys.CONN_PARA_KEY_K8S_AZUREAADDIRECTORYID, tempK8sValue);
        }
        String univStandaloneMaster = hiveVersion.getDefaultConfig(distribution,
                EHadoopProperties.UNIV_STANDALONE_MASTER.getName());
        if (univStandaloneMaster != null) {
            connection.getParameters().put(ConnParameterKeys.CONN_PARA_KEY_UNIV_STANDALONE_MASTER, univStandaloneMaster);
        }
        String univStandaloneExecCore = hiveVersion.getDefaultConfig(distribution,
                EHadoopProperties.UNIV_STANDALONE_EXEC_CORE.getName());
        if (univStandaloneExecCore != null) {
            connection.getParameters().put(ConnParameterKeys.CONN_PARA_KEY_UNIV_STANDALONE_EXEC_CORE, univStandaloneExecCore);
        }
        String univStandaloneExecMemory = hiveVersion.getDefaultConfig(distribution,
                EHadoopProperties.UNIV_STANDALONE_EXEC_MEMORY.getName());
        if (univStandaloneExecMemory != null) {
            connection.getParameters().put(ConnParameterKeys.CONN_PARA_KEY_UNIV_STANDALONE_EXEC_MEMORY, univStandaloneExecMemory);
        }
        
        String univSparkSubmitScriptHome = hiveVersion.getDefaultConfig(distribution,
                EHadoopProperties.UNIV_SPARK_SUBMIT_SCRIPT_HOME.getName());
        if (univStandaloneExecMemory != null) {
            connection.getParameters().put(ConnParameterKeys.CONN_PARA_KEY_UNIV_SPARK_SUBMIT_SCRIPT_HOME, univSparkSubmitScriptHome);
        }
    }

    public static String getRepositoryTypeOfHadoopSubItem(Item item) {
        if (item instanceof DatabaseConnectionItem) {
            DatabaseConnectionItem dbItem = (DatabaseConnectionItem) item;
            DatabaseConnection dbConnection = (DatabaseConnection) dbItem.getConnection();
            return dbConnection.getDatabaseType().toUpperCase();
        }

        for (IHadoopSubnodeRepositoryContentHandler handler : HadoopSubnodeRepositoryContentManager.getHandlers()) {
            if (handler.isProcess(item) && handler.getProcessType() != null) {
                return handler.getProcessType().getType().toUpperCase();
            }
        }

        return null;
    }

}
