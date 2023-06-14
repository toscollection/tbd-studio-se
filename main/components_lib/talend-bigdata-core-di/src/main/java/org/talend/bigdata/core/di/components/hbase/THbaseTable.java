package org.talend.bigdata.core.di.components.hbase;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.hbase.NamespaceDescriptor;
import org.apache.hadoop.hbase.TableName;
import org.apache.hadoop.hbase.client.*;
import org.immutables.value.Value;

import java.io.IOException;
import java.util.*;

import static org.apache.hadoop.hbase.util.Bytes.toBytes;

@Value.Immutable
@Value.Style(visibility = Value.Style.ImplementationVisibility.PUBLIC)
public abstract class THbaseTable {
    abstract Configuration configuration();
    abstract String namespaceName();
    abstract String tableName();

    abstract String tableAction();
    abstract List<Map<String, String>> familyParameters();
    private Admin admin;

    public void doTableAction() throws IOException {
        if ("".equals(tableName())) throw new RuntimeException("Table should be entered");

        Connection connection = ConnectionFactory.createConnection(configuration());
        admin = connection.getAdmin();

        String tableNameString = tableName();
        if (!"".equals(namespaceName())) {
            NamespaceDescriptor nd = admin.getNamespaceDescriptor(namespaceName());
            tableNameString = namespaceName() + ":" + tableName();
        }
        TableName tableName = TableName.valueOf(tableNameString);
        switch (tableAction()) {
            case "CREATE_IF_NOT_EXISTS":
                if (!admin.tableExists(tableName)) {
                    createTable(tableName);
                }
                break;
            case "CREATE":
                createTable(tableName);
                break;
            case "DROP_CREATE":
                deleteTable(tableName);
                createTable(tableName);
                break;
            case "DROP_IF_EXISTS_AND_CREATE":
                if (admin.tableExists(tableName)) {
                    deleteTable(tableName);
                    createTable(tableName);
                }
                break;
            case "DROP":
                deleteTable(tableName);
                break;
        }
        connection.close();

    }

    private ColumnFamilyDescriptor getColumnFamily() {
        ColumnFamilyDescriptor family = null;
        for (Map<String, String> map : familyParameters()) {
            String family_name = map.get("FAMILY_NAME");
            family = new ColumnFamilyDescriptorBuilder.ModifyableColumnFamilyDescriptor(toBytes(family_name));
        }
        return family;
    }

    private void createTable(TableName tableName) throws IOException {
        TableDescriptorBuilder tableDesBuilder = TableDescriptorBuilder.newBuilder(tableName);
        ColumnFamilyDescriptor family = getColumnFamily();
        tableDesBuilder.setColumnFamily(family);
        org.apache.hadoop.hbase.client.TableDescriptor tableDes = tableDesBuilder.build();
        admin.createTable(tableDes);
    }

    private void deleteTable(TableName tableName) throws IOException {
        admin.disableTable(tableName);
        admin.deleteTable(tableName);
    }
}
