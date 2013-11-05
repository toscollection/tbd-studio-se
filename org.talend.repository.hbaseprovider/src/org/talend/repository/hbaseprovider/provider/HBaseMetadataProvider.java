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
package org.talend.repository.hbaseprovider.provider;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;

import org.apache.commons.lang.StringUtils;
import org.eclipse.jface.viewers.Viewer;
import org.eclipse.swt.graphics.Image;
import org.talend.commons.exception.ExceptionHandler;
import org.talend.core.GlobalServiceRegister;
import org.talend.core.database.conn.ConnParameterKeys;
import org.talend.core.hadoop.repository.HadoopRepositoryUtil;
import org.talend.core.model.metadata.IMetadataConnection;
import org.talend.core.model.metadata.builder.connection.DatabaseConnection;
import org.talend.core.model.metadata.builder.connection.MetadataColumn;
import org.talend.core.model.metadata.builder.connection.MetadataTable;
import org.talend.core.model.metadata.builder.database.ExtractMetaDataFromDataBase.ETableTypes;
import org.talend.core.model.metadata.builder.database.TableNode;
import org.talend.core.repository.AbstractMetadataExtractorViewProvider;
import org.talend.core.repository.ConnectionStatus;
import org.talend.core.repository.IDBMetadataProvider;
import org.talend.core.repository.model.ProxyRepositoryFactory;
import org.talend.core.utils.ReflectionUtils;
import org.talend.cwm.helper.CatalogHelper;
import org.talend.cwm.helper.ConnectionHelper;
import org.talend.cwm.helper.PackageHelper;
import org.talend.cwm.helper.TaggedValueHelper;
import org.talend.cwm.relational.RelationalFactory;
import org.talend.cwm.relational.TdColumn;
import org.talend.cwm.relational.TdTable;
import org.talend.designer.core.IDesignerCoreService;
import org.talend.repository.hbaseprovider.util.HBaseClassLoaderFactory;
import org.talend.repository.model.IProxyRepositoryFactory;
import org.talend.repository.ui.wizards.metadata.table.database.SelectorTreeViewerProvider;
import orgomg.cwm.objectmodel.core.TaggedValue;
import orgomg.cwm.resource.relational.Catalog;

/**
 * DOC hywang class global comment. Detailled comment
 */
public class HBaseMetadataProvider implements IDBMetadataProvider {

    private static String CATALOG_NAME = "HBase"; //$NON-NLS-1$

    private static String COLUMN_FAMILY = "COLUMN FAMILY"; //$NON-NLS-1$

    private static ClassLoader classLoader;

    private static Map<IMetadataConnection, Object> adminMap = new HashMap<IMetadataConnection, Object>();

    @Override
    public ConnectionStatus testConnection(IMetadataConnection metadataConnection) {
        classLoader = HBaseClassLoaderFactory.getClassLoader(metadataConnection);
        ConnectionStatus connectionStatus = new ConnectionStatus();
        connectionStatus.setResult(false);
        ClassLoader oldClassLoaderLoader = Thread.currentThread().getContextClassLoader();
        try {
            Thread.currentThread().setContextClassLoader(classLoader);
            Object config = ReflectionUtils.invokeStaticMethod("org.apache.hadoop.hbase.HBaseConfiguration", classLoader,
                    "create", new Object[0]);
            ReflectionUtils.invokeMethod(config, "set",
                    new Object[] { "hbase.zookeeper.quorum", metadataConnection.getServerName() });
            ReflectionUtils.invokeMethod(config, "set",
                    new Object[] { "hbase.zookeeper.property.clientPort", metadataConnection.getPort() });
            ReflectionUtils.invokeMethod(config, "set", new Object[] { "zookeeper.recovery.retry", "0" });

            updateHadoopProperties(config, metadataConnection);

            Callable<Object> callable = checkHBaseAvailable(config);
            ExecutorService executor = Executors.newSingleThreadExecutor();
            Future<Object> future = executor.submit(callable);
            try {
                int timeout = 15;
                if (GlobalServiceRegister.getDefault().isServiceRegistered(IDesignerCoreService.class)) {
                    IDesignerCoreService designerService = (IDesignerCoreService) GlobalServiceRegister.getDefault().getService(
                            IDesignerCoreService.class);
                    timeout = designerService.getDBConnectionTimeout();
                }
                future.get(timeout, TimeUnit.SECONDS);
                connectionStatus.setResult(true);
            } catch (Exception e) {
                future.cancel(true);
                connectionStatus.setResult(false);
                connectionStatus.setMessageException("Connection timeout!");
            }
        } catch (Exception e) {
            ExceptionHandler.process(e);
            connectionStatus.setResult(false);
            connectionStatus.setMessageException(e.getMessage());
        } finally {
            Thread.currentThread().setContextClassLoader(oldClassLoaderLoader);
        }
        return connectionStatus;
    }

    private Callable<Object> checkHBaseAvailable(final Object config) {
        return new Callable<Object>() {

            @Override
            public Object call() throws Exception {
                return ReflectionUtils.invokeStaticMethod("org.apache.hadoop.hbase.client.HBaseAdmin", classLoader,
                        "checkHBaseAvailable", new Object[] { config });
            }
        };

    }

    private void updateHadoopProperties(Object hbaseConfiguration, IMetadataConnection metadataConnection) throws Exception {
        String hadoopProperties = (String) metadataConnection.getParameter(ConnParameterKeys.CONN_PARA_KEY_HBASE_PROPERTIES);
        List<HashMap<String, Object>> hadoopPropertiesList = HadoopRepositoryUtil.getHadoopPropertiesList(hadoopProperties);
        for (HashMap<String, Object> hadoopPros : hadoopPropertiesList) {
            ReflectionUtils.invokeMethod(hbaseConfiguration, "set",
                    new Object[] { hadoopPros.get("PROPERTY"), hadoopPros.get("VALUE") });
        }
    }

    @Override
    public void updatePackage(IMetadataConnection metadataConnection) {

    }

    @Override
    public List getTableNodeInfo(IMetadataConnection metadataConnection) {
        List<TableNode> tableNodes = new ArrayList<TableNode>();
        ClassLoader oldClassLoaderLoader = Thread.currentThread().getContextClassLoader();
        try {
            Thread.currentThread().setContextClassLoader(classLoader);
            Object hAdmin = getAdmin(metadataConnection);
            Object[] allTables = (Object[]) ReflectionUtils.invokeMethod(hAdmin, "listTables", new Object[0]);
            for (Object td : allTables) {
                String htabName = (String) ReflectionUtils.invokeMethod(td, "getNameAsString", new Object[0]);
                TdTable tb = getTableFromConnection(metadataConnection, htabName);
                if (tb == null) {
                    tb = RelationalFactory.eINSTANCE.createTdTable();
                    tb.setLabel(htabName);
                    tb.setName(htabName);
                    tb.setTableType(ETableTypes.TABLETYPE_TABLE.getName());
                }
                TableNode tableNode = new TableNode();
                tableNode.setType(TableNode.TABLE);
                tableNode.setValue(tb.getLabel());
                tableNode.setItemType(tb.getTableType());
                tableNode.setMetadataConn(metadataConnection);
                tableNode.setTable(tb);
                tableNodes.add(tableNode);
            }
        } catch (Exception e) {
            ExceptionHandler.process(e);
        } finally {
            Thread.currentThread().setContextClassLoader(oldClassLoaderLoader);
        }

        return tableNodes;
    }

    private TdTable getTableFromConnection(IMetadataConnection metadataConnection, String tableName) {
        List<Catalog> catalogs = CatalogHelper.getCatalogs(((DatabaseConnection) metadataConnection.getCurrentConnection())
                .getDataPackage());
        Catalog catalog = null;
        for (Catalog c : catalogs) {
            if (c.getName() != null && c.getName().equals(getDefaultCatalogName())) {
                catalog = c;
                break;
            }
        }
        if (catalog != null) {
            List<TdTable> exsitTables = CatalogHelper.getTables(catalog);
            for (TdTable tb : exsitTables) {
                if (tableName != null && tb.getLabel().equals(tableName)) {
                    return tb;
                }
            }
        }

        return null;
    }

    @Override
    public AbstractMetadataExtractorViewProvider getMetadataViewProvider() {
        HBaseMetadataViewProvider provider = new HBaseMetadataViewProvider();
        return provider;
    }

    class HBaseMetadataViewProvider extends SelectorTreeViewerProvider {

        @Override
        public void inputChanged(Viewer viewer, Object oldInput, Object newInput) {

        }

        @Override
        public Object[] getElements(Object inputElement) {
            List list = (List) inputElement;
            return list.toArray();
        }

        @Override
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
            ClassLoader oldClassLoaderLoader = Thread.currentThread().getContextClassLoader();
            try {
                Thread.currentThread().setContextClassLoader(classLoader);
                IMetadataConnection metadataConnection = tableNode.getMetadataConn();
                Object hAdmin = getAdmin(metadataConnection);
                if (tableNode.getType() == TableNode.TABLE) {
                    // Configuration config = HBaseConfiguration.create();
                    // config.set("hbase.zookeeper.quorum", metadataConnection.getServerName());
                    // config.set("hbase.zookeeper.property.clientPort", metadataConnection.getPort());
                    Object td = ReflectionUtils.invokeMethod(hAdmin, "getTableDescriptor", new Object[] { tableNode.getValue()
                            .getBytes() });

                    List<String> familyNames = new ArrayList<String>();
                    Object[] columnFamilies = (Object[]) ReflectionUtils.invokeMethod(td, "getColumnFamilies", new Object[0]);
                    for (Object columnDescripter : columnFamilies) {
                        String nameAsString = (String) ReflectionUtils.invokeMethod(columnDescripter, "getNameAsString",
                                new Object[0]);
                        familyNames.add(nameAsString);
                    }

                    String columnFamily = metadataConnection.getSchema();
                    if (columnFamily == null || "".equals(columnFamily)) {
                        for (String columnFamilyToAdd : familyNames) {
                            TableNode columnFamilyNode = new TableNode();
                            columnFamilyNode.setParent(tableNode);
                            columnFamilyNode.setType(TableNode.COLUMN_FAMILY);
                            columnFamilyNode.setValue(columnFamilyToAdd);
                            columnFamilyNode.setItemType(COLUMN_FAMILY);
                            columnFamilyNode.setTable(tableNode.getTable());
                            columnFamilyNode.setMetadataConn(metadataConnection);
                            tableNode.getChildren().add(columnFamilyNode);
                        }
                    } else if (columnFamily != null && familyNames.contains(columnFamily)) {
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
                    Object config = ReflectionUtils.invokeMethod(hAdmin, "getConfiguration", new Object[0]);
                    TableNode parent = tableNode.getParent();
                    String tableName = null;
                    if (parent != null) {
                        tableName = parent.getValue();
                    }
                    Object scan = Class.forName("org.apache.hadoop.hbase.client.Scan", true, classLoader).newInstance();
                    Object table = ReflectionUtils.newInstance("org.apache.hadoop.hbase.client.HTable", classLoader,
                            new Object[] { config, tableName });

                    ReflectionUtils.invokeMethod(scan, "addFamily", new Object[] { tableNode.getValue().getBytes() });

                    Object resultSetscanner = ReflectionUtils.invokeMethod(table, "getScanner", new Object[] { scan });
                    List<String> columnExsit = new ArrayList<String>();
                    Object result = ReflectionUtils.invokeMethod(resultSetscanner, "next", new Object[0]);
                    while (result != null) {
                        List<Object> list = (List<Object>) ReflectionUtils.invokeMethod(result, "list", new Object[0]);
                        if (list != null) {
                            /**
                             * in hbase data will stored as keyValue,but the kv.getKeyString() which equal
                             * "kv.getFamily()+kv.getQualifier()+kv.getRow()" can specific a metadata,so need to trim
                             * the duplicated kv.getQualifier()+kv.getRow()
                             **/
                            for (Object kv : list) {
                                byte[] qualifier = (byte[]) ReflectionUtils.invokeMethod(kv, "getQualifier", new Object[0]);
                                String columnName = (String) ReflectionUtils.invokeStaticMethod(
                                        "org.apache.hadoop.hbase.util.Bytes", classLoader, "toStringBinary",
                                        new Object[] { qualifier });

                                if (StringUtils.isEmpty(columnName)) {
                                    continue;
                                }
                                byte[] family = (byte[]) ReflectionUtils.invokeMethod(kv, "getFamily", new Object[0]);
                                String familyName = (String) ReflectionUtils.invokeStaticMethod(
                                        "org.apache.hadoop.hbase.util.Bytes", classLoader, "toStringBinary",
                                        new Object[] { family });
                                String exsistColumn = columnName + "_" + familyName;
                                if (!columnExsit.contains(exsistColumn)) {
                                    TableNode columnNode = new TableNode();
                                    columnNode.setParent(tableNode);
                                    columnNode.setType(TableNode.COLUMN);
                                    columnNode.setValue(columnName);
                                    columnNode.setItemType("COLUMN");
                                    columnNode.setTable(tableNode.getTable());
                                    tableNode.getChildren().add(columnNode);
                                    columnExsit.add(exsistColumn);
                                }
                            }
                        }
                        result = ReflectionUtils.invokeMethod(resultSetscanner, "next", new Object[0]);
                    }
                }
            } catch (Exception e) {
                ExceptionHandler.process(e);
            } finally {
                Thread.currentThread().setContextClassLoader(oldClassLoaderLoader);
            }
            List<TableNode> children = tableNode.getChildren();
            return children.toArray();
        }

        @Override
        public Object getParent(Object element) {
            TableNode tableNode = (TableNode) element;
            return tableNode.getParent();
        }

        @Override
        public boolean hasChildren(Object element) {
            TableNode tableNode = (TableNode) element;
            int type = tableNode.getType();
            if (type == TableNode.COLUMN_FAMILY || type == TableNode.TABLE) {
                return true;
            }
            return !tableNode.getChildren().isEmpty();
        }

        @Override
        public Image getColumnImage(Object element, int columnIndex) {
            return null;
        }

        @Override
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
    @Override
    public int getRunnableAccessNodeType() {
        return TableNode.COLUMN;
    }

    /** run method in Runnable will execute this **/
    @Override
    public void executeInRunnable(IMetadataConnection metadataConnection, Object currentNode, DatabaseConnection dbconn) {
        Object hAdmin = getAdmin(metadataConnection);
        Object config = null;
        MetadataTable metadataTable = null;
        TableNode columnNode = null;
        ClassLoader oldClassLoaderLoader = Thread.currentThread().getContextClassLoader();
        try {
            Thread.currentThread().setContextClassLoader(classLoader);
            if (hAdmin != null) {
                config = ReflectionUtils.invokeMethod(hAdmin, "getConfiguration", new Object[0]);
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
                Object scan = Class.forName("org.apache.hadoop.hbase.client.Scan", true, classLoader).newInstance();
                Object table = ReflectionUtils.newInstance("org.apache.hadoop.hbase.client.HTable", classLoader, new Object[] {
                        config, tableName });
                ReflectionUtils.invokeMethod(scan, "addFamily", new Object[] { columnFamilyName.getBytes() });
                List<String> columnNameExsit = new ArrayList<String>();
                Object resultSetscanner = ReflectionUtils.invokeMethod(table, "getScanner", new Object[] { scan });
                Object result = ReflectionUtils.invokeMethod(resultSetscanner, "next", new Object[0]);
                while (result != null) {
                    List<Object> list = (List<Object>) ReflectionUtils.invokeMethod(result, "list", new Object[0]);
                    if (list != null) {
                        for (Object kv : list) {
                            byte[] family = (byte[]) ReflectionUtils.invokeMethod(kv, "getFamily", new Object[0]);
                            String familyName = (String) ReflectionUtils.invokeStaticMethod("org.apache.hadoop.hbase.util.Bytes",
                                    classLoader, "toStringBinary", new Object[] { family });
                            if (familyName.equals(columnFamilyNode.getValue())) {
                                byte[] qualifier = (byte[]) ReflectionUtils.invokeMethod(kv, "getQualifier", new Object[0]);
                                String columnName = (String) ReflectionUtils.invokeStaticMethod(
                                        "org.apache.hadoop.hbase.util.Bytes", classLoader, "toStringBinary",
                                        new Object[] { qualifier });
                                if (columnName != null && columnName.equals(columnNode.getValue())
                                        && !columnNameExsit.contains(columnName)) {
                                    TdColumn column = RelationalFactory.eINSTANCE.createTdColumn();
                                    column.setLabel(columnName);
                                    column.setName(columnName);
                                    column.setTalendType("id_String");
                                    TaggedValue tv = TaggedValueHelper.createTaggedValue(COLUMN_FAMILY, columnFamilyName);
                                    column.getTaggedValue().add(tv);
                                    List<MetadataColumn> columns = metadataTable.getColumns();
                                    columns.add(column);
                                    columnNameExsit.add(columnName);
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
                                                     * can add into column list directly because it will delete the same
                                                     * name column when deselect
                                                     */
                                                    columnsExsit.add(column);
                                                    findTable = true;
                                                }
                                            }
                                        }
                                        if (!findTable) {
                                            if (metadataTable.getId() == null) {
                                                IProxyRepositoryFactory factory = ProxyRepositoryFactory.getInstance();
                                                metadataTable.setId(factory.getNextId());
                                            }
                                            PackageHelper.addMetadataTable(metadataTable, catalogToWrite);
                                        }

                                    }
                                }
                            }
                        }
                    }
                    result = ReflectionUtils.invokeMethod(resultSetscanner, "next", new Object[0]);
                }
            }
        } catch (Exception e) {
            ExceptionHandler.process(e);
        } finally {
            Thread.currentThread().setContextClassLoader(oldClassLoaderLoader);
        }
    }

    @Override
    public Map getConnectionMap() {
        return adminMap;
    }

    @Override
    public void fillConnection(DatabaseConnection connection) {
        Catalog defaultCatalog = CatalogHelper.createCatalog(getDefaultCatalogName());
        ConnectionHelper.addPackage(defaultCatalog, connection);
        connection.setSID(getDefaultCatalogName());
    }

    @Override
    public String getDefaultCatalogName() {
        return CATALOG_NAME;
    }

    @Override
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

    @Override
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
                                            // columns.remove(column);
                                            return true;
                                        }
                                    }
                                }
                            }
                        }
                    }
                }
            }
        } else if (columnNode.getType() == TableNode.COLUMN_FAMILY) {
            String columnFamilyName = columnNode.getValue();
            String tableName = null;
            TableNode tableNode = columnNode.getParent();
            if (tableNode != null) {
                tableName = tableNode.getValue();
            }
            List<MetadataTable> tables = ConnectionHelper.getTablesWithOrders(connection);
            for (MetadataTable table : tables) {
                if (table instanceof TdTable) {
                    TdTable tdTable = (TdTable) table;
                    if (tdTable.getLabel().equals(tableName)) {
                        List<MetadataColumn> columns = tdTable.getColumns();
                        for (MetadataColumn column : columns) {
                            List<TaggedValue> tagValues = column.getTaggedValue();
                            for (TaggedValue tv : tagValues) {
                                String tag = tv.getTag();
                                String value = tv.getValue();
                                if (tag != null && tag.equals(COLUMN_FAMILY)) {
                                    if (value != null && value.equals(columnFamilyName)) {
                                        return true;
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

    @Override
    public List<TdColumn> returnMetadataColumnsFromTable(String tableName, IMetadataConnection metadataConnection) {
        List<TdColumn> toReturn = new ArrayList<TdColumn>();
        ClassLoader oldClassLoaderLoader = Thread.currentThread().getContextClassLoader();
        try {
            Thread.currentThread().setContextClassLoader(classLoader);
            Object hAdmin = getAdmin(metadataConnection);
            Object config = ReflectionUtils.invokeMethod(hAdmin, "getConfiguration", new Object[0]);
            Object scan = Class.forName("org.apache.hadoop.hbase.client.Scan", true, classLoader).newInstance();
            Object table = ReflectionUtils.newInstance("org.apache.hadoop.hbase.client.HTable", classLoader, new Object[] {
                    config, tableName });
            String schema = metadataConnection.getSchema();
            if (StringUtils.isNotEmpty(schema)) {
                ReflectionUtils.invokeMethod(scan, "addFamily", new Object[] { schema.getBytes() });
            }
            List<String> columnExsit = new ArrayList<String>();
            Object resultSetscanner = ReflectionUtils.invokeMethod(table, "getScanner", new Object[] { scan });
            Object result = ReflectionUtils.invokeMethod(resultSetscanner, "next", new Object[0]);
            while (result != null) {
                List<Object> list = (List<Object>) ReflectionUtils.invokeMethod(result, "list", new Object[0]);
                if (list != null) {
                    /**
                     * in hbase data will stored as keyValue,but the kv.getKeyString() which equal
                     * "kv.getFamily()+kv.getQualifier()+kv.getRow()" can specific a metadata,so need to trim the
                     * duplicated kv.getQualifier()+kv.getRow()
                     **/
                    for (Object kv : list) {
                        byte[] qualifier = (byte[]) ReflectionUtils.invokeMethod(kv, "getQualifier", new Object[0]);
                        String columnName = (String) ReflectionUtils.invokeStaticMethod("org.apache.hadoop.hbase.util.Bytes",
                                classLoader, "toStringBinary", new Object[] { qualifier });
                        if (StringUtils.isEmpty(columnName)) {
                            continue;
                        }
                        byte[] family = (byte[]) ReflectionUtils.invokeMethod(kv, "getFamily", new Object[0]);
                        String familyName = (String) ReflectionUtils.invokeStaticMethod("org.apache.hadoop.hbase.util.Bytes",
                                classLoader, "toStringBinary", new Object[] { family });
                        if (columnName != null) {
                            String exsistColumn = columnName + "_" + familyName;
                            if (!columnExsit.contains(exsistColumn)) {
                                TdColumn column = RelationalFactory.eINSTANCE.createTdColumn();
                                column.setLabel(columnName);
                                column.setName(columnName);
                                // hbase no type ,just byte[],need to cast the type ourself
                                column.setTalendType("id_String");
                                TaggedValue tv = TaggedValueHelper.createTaggedValue(COLUMN_FAMILY, familyName);
                                column.getTaggedValue().add(tv);
                                toReturn.add(column);
                                columnExsit.add(exsistColumn);
                            }
                        }
                    }
                }
                result = ReflectionUtils.invokeMethod(resultSetscanner, "next", new Object[0]);
            }
        } catch (Exception e) {
            ExceptionHandler.process(e);
        } finally {
            Thread.currentThread().setContextClassLoader(oldClassLoaderLoader);
        }
        return toReturn;
    }

    private Object getAdmin(IMetadataConnection metadataConnection) {
        Object hAdmin = adminMap.get(metadataConnection);
        if (hAdmin == null) {
            ClassLoader oldClassLoaderLoader = Thread.currentThread().getContextClassLoader();
            try {
                Thread.currentThread().setContextClassLoader(classLoader);
                Object config = ReflectionUtils.invokeStaticMethod("org.apache.hadoop.hbase.HBaseConfiguration", classLoader,
                        "create", new Object[0]);
                ReflectionUtils.invokeMethod(config, "set",
                        new Object[] { "hbase.zookeeper.quorum", metadataConnection.getServerName() });
                ReflectionUtils.invokeMethod(config, "set", new Object[] { "hbase.zookeeper.property.clientPort",
                        metadataConnection.getPort() });

                updateHadoopProperties(config, metadataConnection);

                hAdmin = ReflectionUtils.newInstance("org.apache.hadoop.hbase.client.HBaseAdmin", classLoader,
                        new Object[] { config });
                adminMap.put(metadataConnection, hAdmin);
            } catch (Exception e) {
                ExceptionHandler.process(e);
            } finally {
                Thread.currentThread().setContextClassLoader(oldClassLoaderLoader);
            }
        }
        return hAdmin;
    }

    @Override
    public List<String> returnTablesFormConnection(IMetadataConnection metadataConnection) {
        List<String> toReturn = new ArrayList<String>();
        ClassLoader oldClassLoaderLoader = Thread.currentThread().getContextClassLoader();
        try {
            Thread.currentThread().setContextClassLoader(classLoader);
            Object hAdmin = getAdmin(metadataConnection);
            Object[] allTables = (Object[]) ReflectionUtils.invokeMethod(hAdmin, "listTables", new Object[0]);
            for (Object td : allTables) {
                toReturn.add((String) ReflectionUtils.invokeMethod(td, "getNameAsString", new Object[0]));
            }
        } catch (Exception e) {
            ExceptionHandler.process(e);
        } finally {
            Thread.currentThread().setContextClassLoader(oldClassLoaderLoader);
        }

        return toReturn;
    }

    @Override
    public boolean isSupportGuessSchema() {
        return false;
    }

    @Override
    public boolean isSupportRetrieveSchema() {
        return true;
    }

}
