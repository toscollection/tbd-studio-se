package org.talend.bigdata.core.di.components.hbase;

import org.apache.hadoop.hbase.HColumnDescriptor;
import org.apache.hadoop.hbase.TableName;
import org.apache.hadoop.hbase.client.*;
import org.apache.hadoop.hbase.io.compress.Compression;
import org.apache.hadoop.hbase.regionserver.BloomType;
import org.immutables.value.Value;
import java.io.IOException;
import java.util.*;

@Value.Immutable
@Value.Style(visibility = Value.Style.ImplementationVisibility.PUBLIC)
public interface THbaseTable {
    Connection connection();
    boolean isSpecifyNamespace();
    String namespaceName();
    String tableName();
    String tableAction();
    List<Map<String, String>> familyParameters();
    CreateTableFunction createTableFunction();

    default void doTableAction() throws IOException {
        if ("".equals(tableName())) throw new RuntimeException("Table name can not be empty");

        Admin admin = connection().getAdmin();

        String tableNameString = tableName();
        if (isSpecifyNamespace() && !"".equals(namespaceName())) {
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
    }

    default HColumnDescriptor getColumnFamily() {
        HColumnDescriptor family = null;
        for (Map<String, String> map : familyParameters()) {
            String family_name = map.get("FAMILY_NAME");
            family = new HColumnDescriptor(family_name);

            String family_inmemory = map.get("FAMILY_INMEMORY");
            String family_blockcacheenabled = map.get("FAMILY_BLOCKCACHEENABLED");
            String family_bloomfiltertype = map.get("FAMILY_BLOOMFILTERTYPE");
            String family_blocksize = map.get("FAMILY_BLOCKSIZE");
            String family_compactioncompressiontype = map.get("FAMILY_COMPACTIONCOMPRESSIONTYPE");
            String family_compressiontype = map.get("FAMILY_COMPRESSIONTYPE");
            String family_maxversions = map.get("FAMILY_MAXVERSIONS");
            String family_scope = map.get("FAMILY_SCOPE");
            String family_timetolive = map.get("FAMILY_TIMETOLIVE");

            if(family_inmemory!=null && !"".equals(family_inmemory)){
                family.setInMemory(Boolean.parseBoolean(family_inmemory));
            }
            if(family_blockcacheenabled !=null && !"".equals(family_blockcacheenabled)){
                family.setBlockCacheEnabled(Boolean.parseBoolean(family_blockcacheenabled));
            }
            if(family_bloomfiltertype!=null && !"".equals(family_bloomfiltertype)){
                family.setBloomFilterType(BloomType.valueOf(family_bloomfiltertype));
            }
            if(family_blocksize!=null && !"".equals(family_blocksize)){
                family.setBlocksize(Integer.parseInt(family_blocksize));
            }
            if(family_compactioncompressiontype!=null && !"".equals(family_compactioncompressiontype)){
                family.setCompactionCompressionType(Compression.Algorithm.valueOf(family_compactioncompressiontype));
            }
            if(family_compressiontype!=null && !"".equals(family_compressiontype)){
                family.setCompressionType(Compression.Algorithm.valueOf(family_compressiontype));
            }
            if(family_maxversions!=null && !"".equals(family_maxversions)){
                family.setMaxVersions(Integer.parseInt(family_maxversions));
            }
            if(family_scope!=null && !"".equals(family_scope)){
                family.setScope(Integer.parseInt(family_scope));
            }
            if(family_timetolive!=null && !"".equals(family_timetolive)){
                family.setTimeToLive(Integer.parseInt(family_timetolive));
            }
        }
        return family;
    }

     default void deleteTable(TableName tableName, Admin admin) throws IOException {
         if(!admin.isTableDisabled(tableName)){
             admin.disableTable(tableName);
         }
        admin.deleteTable(tableName);
    }

    interface CreateTableFunction {
        void doCreateTable(TableName tableName, Admin admin, HColumnDescriptor columnFamily) throws IOException;
    }

}
