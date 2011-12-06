// ============================================================================
//
// Copyright (C) 2006-2011 Talend Inc. - www.talend.com
//
// This source code is available under agreement available at
// %InstallDIR%\features\org.talend.rcp.branding.%PRODUCTNAME%\%PRODUCTNAME%license.txt
//
// You should have received a copy of the agreement
// along with this program; if not, write to Talend SA
// 9 rue Pages 92150 Suresnes, France
//
// ============================================================================
package org.talend.repository.hbaseprovider.provider;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.hbase.HBaseConfiguration;
import org.apache.hadoop.hbase.HColumnDescriptor;
import org.apache.hadoop.hbase.HTableDescriptor;
import org.apache.hadoop.hbase.KeyValue;
import org.apache.hadoop.hbase.MasterNotRunningException;
import org.apache.hadoop.hbase.ZooKeeperConnectionException;
import org.apache.hadoop.hbase.client.HBaseAdmin;
import org.apache.hadoop.hbase.client.Result;
import org.apache.hadoop.hbase.client.ResultScanner;
import org.apache.hadoop.hbase.util.Bytes;
import org.eclipse.jface.viewers.Viewer;
import org.eclipse.swt.graphics.Image;
import org.talend.commons.ui.runtime.exception.ExceptionHandler;
import org.talend.core.model.metadata.IMetadataConnection;
import org.talend.core.model.metadata.builder.connection.DatabaseConnection;
import org.talend.core.model.metadata.builder.connection.MetadataColumn;
import org.talend.core.model.metadata.builder.connection.MetadataTable;
import org.talend.core.model.metadata.builder.database.ExtractMetaDataFromDataBase.ETableTypes;
import org.talend.core.model.metadata.builder.database.TableNode;
import org.talend.core.repository.AbstractMetadataExtractorViewProvider;
import org.talend.core.repository.ConnectionStatus;
import org.talend.core.repository.IDBMetadataProvider;
import org.talend.cwm.helper.CatalogHelper;
import org.talend.cwm.helper.ConnectionHelper;
import org.talend.cwm.helper.PackageHelper;
import org.talend.cwm.helper.TaggedValueHelper;
import org.talend.cwm.relational.RelationalFactory;
import org.talend.cwm.relational.TdColumn;
import org.talend.cwm.relational.TdTable;
import org.talend.repository.ui.wizards.metadata.table.database.SelectorTreeViewerProvider;
import orgomg.cwm.objectmodel.core.TaggedValue;
import orgomg.cwm.resource.relational.Catalog;

/**
 * DOC hywang class global comment. Detailled comment
 */
public class HBaseMetadataProvider implements IDBMetadataProvider {

    private static String CATALOG_NAME = "HBase"; //$NON-NLS-N$

    private static String COLUMN_FAMILY = "COLUMN FAMILY"; //$NON-NLS-N$

    private static Map<IMetadataConnection, HBaseAdmin> adminMap = new HashMap<IMetadataConnection, HBaseAdmin>();

    public ConnectionStatus testConnection(String dbType, String url, String username, String pwd, String schema, String server,
            String port, String driverClassName, String driverJarPath, String dbVersionString, String additionalParam) {
        ConnectionStatus connectionStatus = new ConnectionStatus();
        connectionStatus.setResult(false);
        Configuration config = HBaseConfiguration.create();
        config.set("hbase.zookeeper.quorum", server);
        config.set("hbase.zookeeper.property.clientPort", port);
        try {
            HBaseAdmin.checkHBaseAvailable(config);
            connectionStatus.setResult(true);
        } catch (MasterNotRunningException e) {
            ExceptionHandler.process(e);
            connectionStatus.setMessageException(e.getMessage());
        } catch (ZooKeeperConnectionException e) {
            ExceptionHandler.process(e);
            connectionStatus.setMessageException(e.getMessage());
        } finally {
            return connectionStatus;
        }
    }

    public void updatePackage(IMetadataConnection metadataConnection) {

    }

    public List getTableNodeInfo(IMetadataConnection metadataConnection) {
        List<TableNode> tableNodes = new ArrayList<TableNode>();
        try {
            HBaseAdmin hAdmin = getAdmin(metadataConnection);
            HTableDescriptor[] allTables = hAdmin.listTables();
            for (HTableDescriptor td : allTables) {
                TdTable tb = RelationalFactory.eINSTANCE.createTdTable();
                tb.setLabel(td.getNameAsString());
                tb.setName(td.getNameAsString());
                tb.setTableType(ETableTypes.TABLETYPE_TABLE.getName());
                TableNode tableNode = new TableNode();
                tableNode.setType(TableNode.TABLE);
                tableNode.setValue(tb.getLabel());
                tableNode.setItemType(tb.getTableType());
                tableNode.setMetadataConn(metadataConnection);
                tableNode.setTable(tb);
                tableNodes.add(tableNode);
            }
        } catch (MasterNotRunningException e) {
            ExceptionHandler.process(e);
        } catch (ZooKeeperConnectionException e) {
            ExceptionHandler.process(e);
        } catch (IOException e) {
            ExceptionHandler.process(e);
        }

        return tableNodes;
    }

    public AbstractMetadataExtractorViewProvider getMetadataViewProvider() {
        HBaseMetadataViewProvider provider = new HBaseMetadataViewProvider();
        return provider;
    }

    class HBaseMetadataViewProvider extends SelectorTreeViewerProvider {

        public void inputChanged(Viewer viewer, Object oldInput, Object newInput) {

        }

        public Object[] getElements(Object inputElement) {
            List list = (List) inputElement;
            return list.toArray();
        }

        public Object[] getChildren(Object parentElement) {
            TableNode tableNode = (TableNode) parentElement;
            List<TableNode> child = tableNode.getChildren();
            boolean extended = false;
            if (!child.isEmpty()) {
                for (TableNode node : child) {
                    if (node.getType() == TableNode.COLUMN_FAMILY) {
                        extended = true;
                        break;
                    }
                }
            }
            // if extended is true, means table already got,no need to get again.
            if (extended) {
                return child.toArray();
            }

            /* use hbase api to get analyse all nodes rather than using jdbc */

            try {
                IMetadataConnection metadataConnection = tableNode.getMetadataConn();
                HBaseAdmin hAdmin = getAdmin(metadataConnection);
                if (tableNode.getType() == TableNode.TABLE) {
                    Configuration config = HBaseConfiguration.create();
                    config.set("hbase.zookeeper.quorum", metadataConnection.getServerName());
                    config.set("hbase.zookeeper.property.clientPort", metadataConnection.getPort());
                    HTableDescriptor td = hAdmin.getTableDescriptor(tableNode.getValue().getBytes());

                    List<String> familyNames = new ArrayList<String>();
                    for (HColumnDescriptor columnDescripter : td.getColumnFamilies()) {
                        String nameAsString = columnDescripter.getNameAsString();
                        familyNames.add(nameAsString);
                    }

                    String columnFamily = metadataConnection.getSchema();
                    if (columnFamily == null || "".equals(columnFamily)) {
                        for (String columnFamilyToAdd : familyNames) {
                            TableNode columnFamilyNode = new TableNode();
                            columnFamilyNode.setParent(tableNode);
                            columnFamilyNode.setType(TableNode.COLUMN_FAMILY);
                            columnFamilyNode.setValue(columnFamilyToAdd);
                            columnFamilyNode.setItemType(COLUMN_FAMILY); //$NON-NLS-N$
                            columnFamilyNode.setTable(tableNode.getTable());
                            columnFamilyNode.setMetadataConn(metadataConnection);
                            tableNode.getChildren().add(columnFamilyNode);
                        }
                    }

                    else if (columnFamily != null && familyNames.contains(columnFamily)) {
                        TableNode columnFamilyNode = new TableNode();
                        columnFamilyNode.setParent(tableNode);
                        columnFamilyNode.setType(TableNode.COLUMN_FAMILY);
                        columnFamilyNode.setValue(columnFamily);
                        columnFamilyNode.setItemType(COLUMN_FAMILY);
                        columnFamilyNode.setTable(tableNode.getTable());
                        columnFamilyNode.setMetadataConn(metadataConnection);
                        tableNode.getChildren().add(columnFamilyNode);
                    }

                }
                // retrieve columns from column family
                if (tableNode.getType() == TableNode.COLUMN_FAMILY) {
                    Configuration config = null;
                    config = hAdmin.getConfiguration();
                    TableNode parent = tableNode.getParent();
                    String tableName = null;
                    if (parent != null) {
                        tableName = parent.getValue();
                    }
                    org.apache.hadoop.hbase.client.Scan scan = new org.apache.hadoop.hbase.client.Scan();
                    org.apache.hadoop.hbase.client.HTable table = new org.apache.hadoop.hbase.client.HTable(config, tableName);
                    scan.addFamily(tableNode.getValue().getBytes());
                    ResultScanner resultSetscanner = table.getScanner(scan);

                    while (resultSetscanner.iterator().hasNext()) {
                        Result result = resultSetscanner.iterator().next();
                        if (result != null) {
                            List<KeyValue> list = result.list();
                            if (list != null) {
                                /**
                                 * in hbase data will stored as keyValue,but the kv.getKeyString() which equal
                                 * "kv.getFamily()+kv.getQualifier()+kv.getRow()" can specific a metadata,so need to
                                 * trim the duplicated kv.getQualifier()+kv.getRow()
                                 **/
                                List<String> columnExsit = new ArrayList<String>();
                                for (KeyValue kv : list) {
                                    String columnName = Bytes.toStringBinary(kv.getQualifier());
                                    String familyName = Bytes.toStringBinary(kv.getFamily());
                                    String exsistColumn = columnName + "_" + familyName;
                                    if (!columnExsit.contains(exsistColumn)) {
                                        TableNode columnNode = new TableNode();
                                        columnNode.setParent(tableNode);
                                        columnNode.setType(TableNode.COLUMN);
                                        columnNode.setValue(Bytes.toStringBinary(kv.getQualifier()));
                                        columnNode.setItemType("COLUMN");
                                        columnNode.setTable(tableNode.getTable());
                                        tableNode.getChildren().add(columnNode);
                                        columnExsit.add(exsistColumn);
                                    }
                                }
                                columnExsit = null;
                            }
                        }
                    }
                }

            } catch (MasterNotRunningException e) {
                e.printStackTrace();
            } catch (ZooKeeperConnectionException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
            List<TableNode> children = tableNode.getChildren();
            return children.toArray();
        }

        public Object getParent(Object element) {
            TableNode tableNode = (TableNode) element;
            return tableNode.getParent();
        }

        public boolean hasChildren(Object element) {
            TableNode tableNode = (TableNode) element;
            int type = tableNode.getType();
            if (type == TableNode.COLUMN_FAMILY || type == TableNode.TABLE) {
                return true;
            }
            return !tableNode.getChildren().isEmpty();
        }

        public Image getColumnImage(Object element, int columnIndex) {
            return null;
        }

        public String getColumnText(Object element, int columnIndex) {
            TableNode tableNode = (TableNode) element;
            int type = tableNode.getType();
            switch (columnIndex) {
            case 0:
                String value = tableNode.getValue();
                if (value == null) {
                    return "";
                }
                return value;
            case 1:
                if (type == TableNode.CATALOG) {
                    return "CATALOG";
                } else if (type == TableNode.SCHEMA) {
                    return "SCHEMA";
                } else {
                    return tableNode.getItemType();
                }
            default:
                return "";
            }
        }

    }

    /**
     * returns the node type which will access a runnable when click the node.
     * **/
    public int getRunnableAccessNodeType() {
        return TableNode.COLUMN;
    }

    /** run method in Runnable will execute this **/
    public void executeInRunnable(IMetadataConnection metadataConnection, Object currentNode, DatabaseConnection dbconn) {
        HBaseAdmin hAdmin = getAdmin(metadataConnection);
        Configuration config = null;
        MetadataTable metadataTable = null;
        TableNode columnNode = null;
        try {
            if (hAdmin != null) {
                config = hAdmin.getConfiguration();
            }
            if (currentNode instanceof TableNode) {
                columnNode = (TableNode) currentNode;
            }
            if (columnNode == null) {
                return;
            }
            TableNode columnFamilyNode = columnNode.getParent();
            TableNode tableNode = null;
            String tableName = null;
            String columnFamilyName = null;
            if (columnFamilyNode != null) {
                columnFamilyName = columnFamilyNode.getValue();
                tableNode = columnFamilyNode.getParent();
                if (tableNode != null) {
                    tableName = tableNode.getValue();
                    metadataTable = (MetadataTable) tableNode.getTable();
                }
            }
            if (columnNode != null && columnNode.getType() == TableNode.COLUMN) {
                org.apache.hadoop.hbase.client.Scan scan = new org.apache.hadoop.hbase.client.Scan();
                org.apache.hadoop.hbase.client.HTable table = new org.apache.hadoop.hbase.client.HTable(config, tableName);
                scan.addFamily(columnFamilyName.getBytes());
                ResultScanner resultSetscanner = table.getScanner(scan);
                while (resultSetscanner.iterator().hasNext()) {
                    Result result = resultSetscanner.iterator().next();
                    if (result != null) {
                        List<KeyValue> list = result.list();
                        if (list != null) {
                            for (KeyValue kv : list) {
                                if (Bytes.toStringBinary(kv.getFamily()).equals(columnFamilyNode.getValue())) {
                                    String columnName = Bytes.toStringBinary(kv.getQualifier());
                                    if (columnName != null && columnName.equals(columnNode.getValue())) {
                                        TdColumn column = RelationalFactory.eINSTANCE.createTdColumn();
                                        column.setLabel(columnName);
                                        column.setName(columnName);
                                        column.setTalendType("id_String");
                                        TaggedValue tv = TaggedValueHelper.createTaggedValue(COLUMN_FAMILY, columnFamilyName);
                                        column.getTaggedValue().add(tv);
                                        List<MetadataColumn> columns = metadataTable.getColumns();
                                        columns.add(column);
                                        List<Catalog> catalogs = ConnectionHelper.getCatalogs(dbconn);
                                        Catalog catalogToWrite = null;
                                        for (Catalog c : catalogs) {
                                            if (c.getName() != null && c.getName().equals(getDefaultCatalogName())) {
                                                catalogToWrite = c;
                                                break;
                                            }
                                        }
                                        if (catalogToWrite != null) {
                                            boolean findTable = false;
                                            List exsitTables = CatalogHelper.getTables(catalogToWrite);

                                            for (Object obj : exsitTables) {
                                                if (obj instanceof TdTable) {
                                                    TdTable tb = (TdTable) obj;
                                                    if (tableName != null && tb.getLabel().equals(tableName)) {
                                                        List<MetadataColumn> columnsExsit = tb.getColumns();
                                                        /*
                                                         * can add into column list directly because it will delete the
                                                         * same name column when deselect
                                                         */
                                                        columnsExsit.add(column);
                                                        findTable = true;
                                                    }
                                                }
                                            }
                                            if (!findTable) {
                                                PackageHelper.addMetadataTable((MetadataTable) metadataTable, catalogToWrite);
                                            }

                                        }
                                    }
                                }
                            }
                        }
                    }
                }
            }
        } catch (MasterNotRunningException e) {
            e.printStackTrace();
        } catch (ZooKeeperConnectionException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public Map getConnectionMap() {
        return adminMap;
    }

    public void fillConnection(DatabaseConnection connection) {
        Catalog defaultCatalog = CatalogHelper.createCatalog(getDefaultCatalogName());
        ConnectionHelper.addPackage(defaultCatalog, connection);
        connection.setSID(getDefaultCatalogName());
    }

    public String getDefaultCatalogName() {
        return CATALOG_NAME;
    }

    public void deleteMetadataFromConnection(Object node, DatabaseConnection connection) {
        TableNode columnNode = null;
        if (node == null) {
            return;

        }
        if (node instanceof TableNode) {
            columnNode = (TableNode) node;
        }
        List<MetadataTable> tables = ConnectionHelper.getTablesWithOrders(connection);
        TableNode columeFamilyNode = columnNode.getParent();
        TableNode tableNode = columeFamilyNode.getParent();
        String tableName = tableNode.getValue();
        String columnFamilyName = columeFamilyNode.getValue();
        String columnName = columnNode.getValue();
        boolean columnRemoved = false;
        for (MetadataTable table : tables) {
            if (columnRemoved) {
                break;
            }
            if (table instanceof TdTable) {
                TdTable tdTable = (TdTable) table;
                if (tdTable.getLabel().equals(tableName)) {
                    List<MetadataColumn> columns = tdTable.getColumns();
                    for (MetadataColumn column : columns) {
                        if (column.getLabel().equals(columnName)) {
                            List<TaggedValue> tagValues = column.getTaggedValue();
                            for (TaggedValue tv : tagValues) {
                                String tag = tv.getTag();
                                String value = tv.getValue();
                                if (tag != null && tag.equals(COLUMN_FAMILY)) {
                                    if (value != null && value.equals(columnFamilyName)) {
                                        columns.remove(column);
                                        columnRemoved = true;
                                        break;
                                    }
                                }
                            }
                        }
                        if (columnRemoved) {
                            break;
                        }
                    }
                }
            }
        }
    }

    public boolean isMetadataExsit(Object node, DatabaseConnection connection) {
        TableNode columnNode = null;
        if (node == null) {
            return false;

        }
        if (node instanceof TableNode) {
            columnNode = (TableNode) node;
        }
        if (columnNode.getType() == TableNode.COLUMN) {
            TableNode columnFamilyNode = columnNode.getParent();
            TableNode tableNode = null;
            String tableName = null;
            String columnFamilyName = null;
            String columnName = columnNode.getValue();
            if (columnFamilyNode != null) {
                columnFamilyName = columnFamilyNode.getValue();
                tableNode = columnFamilyNode.getParent();
                if (tableNode != null) {
                    tableName = tableNode.getValue();
                }
            }

            List<MetadataTable> tables = ConnectionHelper.getTablesWithOrders(connection);
            for (MetadataTable table : tables) {
                if (table instanceof TdTable) {
                    TdTable tdTable = (TdTable) table;
                    if (tdTable.getLabel().equals(tableName)) {
                        List<MetadataColumn> columns = tdTable.getColumns();
                        for (MetadataColumn column : columns) {
                            if (column.getLabel().equals(columnName)) {
                                List<TaggedValue> tagValues = column.getTaggedValue();
                                for (TaggedValue tv : tagValues) {
                                    String tag = tv.getTag();
                                    String value = tv.getValue();
                                    if (tag != null && tag.equals(COLUMN_FAMILY)) {
                                        if (value != null && value.equals(columnFamilyName)) {
                                            columns.remove(column);
                                            return true;
                                        }
                                    }
                                }
                            }
                        }
                    }
                }
            }
        }

        return false;
    }

    public List<TdColumn> returnMetadataColumnsFromTable(String tableName, IMetadataConnection metadataConnection) {
        List<TdColumn> toReturn = new ArrayList<TdColumn>();
        try {
            HBaseAdmin hAdmin = getAdmin(metadataConnection);
            Configuration config = hAdmin.getConfiguration();
            org.apache.hadoop.hbase.client.Scan scan = new org.apache.hadoop.hbase.client.Scan();
            org.apache.hadoop.hbase.client.HTable table = new org.apache.hadoop.hbase.client.HTable(config, tableName);
            scan.addFamily(metadataConnection.getSchema().getBytes());
            ResultScanner resultSetscanner = table.getScanner(scan);
            while (resultSetscanner.iterator().hasNext()) {
                Result result = resultSetscanner.iterator().next();
                if (result != null) {
                    List<KeyValue> list = result.list();
                    if (list != null) {
                        /**
                         * in hbase data will stored as keyValue,but the kv.getKeyString() which equal
                         * "kv.getFamily()+kv.getQualifier()+kv.getRow()" can specific a metadata,so need to trim the
                         * duplicated kv.getQualifier()+kv.getRow()
                         **/
                        List<String> columnExsit = new ArrayList<String>();
                        for (KeyValue kv : list) {
                            String columnName = Bytes.toStringBinary(kv.getQualifier());
                            String familyName = Bytes.toStringBinary(kv.getFamily());
                            if (columnName != null) {
                                String exsistColumn = columnName + "_" + familyName;
                                if (!columnExsit.contains(columnExsit)) {
                                    TdColumn column = RelationalFactory.eINSTANCE.createTdColumn();
                                    column.setLabel(columnName);
                                    column.setName(columnName);
                                    // hbase no type ,just byte[],need to cast the type ourself
                                    column.setTalendType("id_String"); //$NON-NLS-N$
                                    TaggedValue tv = TaggedValueHelper.createTaggedValue(COLUMN_FAMILY,
                                            metadataConnection.getSchema());
                                    column.getTaggedValue().add(tv);
                                    toReturn.add(column);
                                    columnExsit.add(exsistColumn);
                                }
                            }
                        }
                        columnExsit = null;
                    }
                }
            }
        } catch (IOException e) {
            ExceptionHandler.process(e);
        }
        return toReturn;
    }

    private HBaseAdmin getAdmin(IMetadataConnection metadataConnection) {
        HBaseAdmin hAdmin = adminMap.get(metadataConnection);
        if (hAdmin == null) {
            Configuration config = HBaseConfiguration.create();
            config.set("hbase.zookeeper.quorum", metadataConnection.getServerName());
            config.set("hbase.zookeeper.property.clientPort", metadataConnection.getPort());
            try {
                hAdmin = new HBaseAdmin(config);
                adminMap.put(metadataConnection, hAdmin);
            } catch (MasterNotRunningException e) {
                ExceptionHandler.process(e);
            } catch (ZooKeeperConnectionException e) {
                ExceptionHandler.process(e);
            }
        }
        return hAdmin;
    }

    public List<String> returnTablesFormConnection(IMetadataConnection metadataConnection) {
        List<String> toReturn = new ArrayList<String>();
        try {
            HBaseAdmin hAdmin = getAdmin(metadataConnection);
            HTableDescriptor[] allTables = hAdmin.listTables();
            for (HTableDescriptor td : allTables) {
                toReturn.add(td.getNameAsString());
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        return toReturn;
    }

    public boolean isSupportGuessSchema() {
        return false;
    }

    public boolean isSupportRetrieveSchema() {
        return true;
    }

}
