package org.talend.bigdata.core.di.components.hbase;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.hbase.HColumnDescriptor;
import org.apache.hadoop.hbase.TableName;
import org.apache.hadoop.hbase.client.*;
import org.immutables.value.Value;
import java.io.IOException;
import java.util.*;

@Value.Immutable
@Value.Style(visibility = Value.Style.ImplementationVisibility.PUBLIC)
public interface THbaseTable {
    Configuration configuration();
    boolean isSpecifyNamespace();
    String namespaceName();
    String tableName();
    String tableAction();
    List<Map<String, String>> familyParameters();
    CreateTableFunction createTableFunction();

    default void doTableAction() throws IOException {
        if ("".equals(tableName())) throw new RuntimeException("Table name can not be empty");

        Connection connection = ConnectionFactory.createConnection(configuration());
        Admin admin = connection.getAdmin();

        String tableNameString = tableName();
        if (!"".equals(namespaceName())) {
            tableNameString = namespaceName() + ":" + tableName();
        }
        TableName tableName = TableName.valueOf(tableNameString);

        switch (tableAction()) {
            case "CREATE_IF_NOT_EXISTS":
                if (!admin.tableExists(tableName)) {
                    createTableFunction().doCreateTable(tableName, admin, getColumnFamily());
                }
                break;
            case "CREATE":
                createTableFunction().doCreateTable(tableName, admin,getColumnFamily());
                break;
            case "DROP_CREATE":
                deleteTable(tableName, admin);
                createTableFunction().doCreateTable(tableName, admin,getColumnFamily());
                break;
            case "DROP_IF_EXISTS_AND_CREATE":
                if (admin.tableExists(tableName)) {
                    deleteTable(tableName, admin);
                }
                createTableFunction().doCreateTable(tableName, admin,getColumnFamily());
                break;
            case "DROP":
                deleteTable(tableName, admin);
                break;
        }
        connection.close();

    }

    default HColumnDescriptor getColumnFamily() {
        HColumnDescriptor family = null;
        for (Map<String, String> map : familyParameters()) {
            String family_name = map.get("FAMILY_NAME");
            family = new HColumnDescriptor(family_name);
        }
        return family;
    }

     default void deleteTable(TableName tableName, Admin admin) throws IOException {
        admin.disableTable(tableName);
        admin.deleteTable(tableName);
    }

    interface CreateTableFunction {
        void doCreateTable(TableName tableName, Admin admin, HColumnDescriptor columnFamily) throws IOException;
    }

}
