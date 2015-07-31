// ============================================================================
//
// Copyright (C) 2006-2015 Talend Inc. - www.talend.com
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
import org.talend.core.database.conn.ConnParameterKeys;
import org.talend.core.model.general.Project;
import org.talend.core.model.metadata.builder.connection.Connection;
import org.talend.core.model.metadata.builder.connection.DatabaseConnection;
import org.talend.core.model.process.IProcess;
import org.talend.core.model.properties.ConnectionItem;
import org.talend.core.model.properties.DatabaseConnectionItem;
import org.talend.core.model.properties.Item;
import org.talend.core.model.repository.ERepositoryObjectType;
import org.talend.core.model.repository.IRepositoryViewObject;
import org.talend.core.model.utils.ContextParameterUtils;
import org.talend.core.repository.model.ProxyRepositoryFactory;
import org.talend.core.utils.TalendQuoteUtils;
import org.talend.designer.core.model.utils.emf.talendfile.ContextType;
import org.talend.designer.hdfsbrowse.manager.HadoopParameterUtil;
import org.talend.metadata.managment.ui.utils.ConnectionContextHelper;
import org.talend.repository.ProjectManager;
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

    private static List<String> hadoopDbParameters = null;

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
        if (viewObject != null && viewObject.getProperty() != null) {
            Item item = viewObject.getProperty().getItem();
            if (item instanceof HadoopClusterConnectionItem) {
                return (HadoopClusterConnectionItem) item;
            } else if (item instanceof ConnectionItem) {
                return getRelativeHadoopClusterItem(item);
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
            hcId = ((DatabaseConnection) ((DatabaseConnectionItem) subConnectionItem).getConnection()).getParameters().get(
                    ConnParameterKeys.CONN_PARA_KEY_HADOOP_CLUSTER_ID);
        }

        return getRelativeHadoopClusterItem(hcId);
    }

    public static HadoopClusterConnectionItem getHadoopClusterItemBySubitemId(String subitemId) {
        if (subitemId == null) {
            return null;
        }

        IRepositoryViewObject repObj = null;
        try {
            repObj = ProxyRepositoryFactory.getInstance().getLastVersion(subitemId);
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
        Map<String, String> map = new HashMap<String, String>();
        HadoopClusterConnectionItem clusterItem = HCRepositoryUtil.getRelativeHadoopClusterItem(clusterId);
        if (clusterItem != null) {
            HadoopClusterConnection hcConnection = (HadoopClusterConnection) clusterItem.getConnection();
            map.put(ConnParameterKeys.CONN_PARA_KEY_HADOOP_CLUSTER_ID, clusterItem.getProperty().getId());
            map.put(ConnParameterKeys.CONN_PARA_KEY_NAME_NODE_URL, getRealValue(hcConnection, hcConnection.getNameNodeURI()));
            map.put(ConnParameterKeys.CONN_PARA_KEY_JOB_TRACKER_URL, getRealValue(hcConnection, hcConnection.getJobTrackerURI()));
            map.put(ConnParameterKeys.CONN_PARA_KEY_USE_YARN, String.valueOf(hcConnection.isUseYarn()));
            map.put(ConnParameterKeys.CONN_PARA_KEY_USE_CUSTOM_CONFS, String.valueOf(hcConnection.isUseCustomConfs()));
            map.put(ConnParameterKeys.CONN_PARA_KEY_USE_KRB, String.valueOf(hcConnection.isEnableKerberos()));
            map.put(ConnParameterKeys.CONN_PARA_KEY_NAME_NODE_PRINCIPAL, getRealValue(hcConnection, hcConnection.getPrincipal()));
            map.put(ConnParameterKeys.CONN_PARA_KEY_JOB_TRACKER_PRINCIPAL,
                    getRealValue(hcConnection, hcConnection.getJtOrRmPrincipal()));
            map.put(ConnParameterKeys.CONN_PARA_KEY_JOB_HISTORY_PRINCIPAL,
                    getRealValue(hcConnection, hcConnection.getJobHistoryPrincipal()));
            map.put(ConnParameterKeys.CONN_PARA_KEY_RESOURCEMANAGER_SCHEDULER_ADDRESS,
                    getRealValue(hcConnection, hcConnection.getRmScheduler()));
            map.put(ConnParameterKeys.CONN_PARA_KEY_JOBHISTORY_ADDRESS, getRealValue(hcConnection, hcConnection.getJobHistory()));
            map.put(ConnParameterKeys.CONN_PARA_KEY_STAGING_DIRECTORY,
                    getRealValue(hcConnection, hcConnection.getStagingDirectory()));
            map.put(ConnParameterKeys.CONN_PARA_KEY_USE_DATANODE_HOSTNAME, String.valueOf(hcConnection.isUseDNHost()));
            map.put(ConnParameterKeys.CONN_PARA_KEY_DB_SERVER,
                    HadoopParameterUtil.getHostNameFromNameNodeURI(getRealValue(hcConnection, hcConnection.getNameNodeURI())));
            map.put(ConnParameterKeys.CONN_PARA_KEY_USERNAME, getRealValue(hcConnection, hcConnection.getUserName()));
            map.put(ConnParameterKeys.CONN_PARA_KEY_HIVE_DISTRIBUTION, hcConnection.getDistribution());
            map.put(ConnParameterKeys.CONN_PARA_KEY_HIVE_VERSION, hcConnection.getDfVersion());
            map.put(ConnParameterKeys.CONN_PARA_KEY_HBASE_DISTRIBUTION, hcConnection.getDistribution());
            map.put(ConnParameterKeys.CONN_PARA_KEY_HBASE_VERSION, hcConnection.getDfVersion());
            if (hcConnection.isEnableKerberos()) {
                map.put(ConnParameterKeys.CONN_PARA_KEY_USEKEYTAB, String.valueOf(hcConnection.isUseKeytab()));
                if (hcConnection.isUseKeytab()) {
                    map.put(ConnParameterKeys.CONN_PARA_KEY_KEYTAB_PRINCIPAL,
                            getRealValue(hcConnection, hcConnection.getKeytabPrincipal()));
                    map.put(ConnParameterKeys.CONN_PARA_KEY_KEYTAB, getRealValue(hcConnection, hcConnection.getKeytab()));
                }
            }
        }

        return map;
    }

    private static String getRealValue(Connection connection, String value) {
        String realValue = value;
        if (connection.isContextMode()) {
            ContextType contextType = ConnectionContextHelper.getContextTypeForContextMode(connection,
                    connection.getContextName());
            realValue = TalendQuoteUtils.removeQuotes(ContextParameterUtils.getOriginalValue(contextType, value));
        }
        return realValue;
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

}
