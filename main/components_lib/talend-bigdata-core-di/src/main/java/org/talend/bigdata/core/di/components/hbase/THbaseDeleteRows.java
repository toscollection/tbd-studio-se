package org.talend.bigdata.core.di.components.hbase;

import org.apache.hadoop.hbase.TableName;
import org.apache.hadoop.hbase.client.*;
import org.apache.hadoop.hbase.util.Bytes;
import org.apache.logging.log4j.Logger;
import org.immutables.value.Value;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Value.Immutable
@Value.Style(visibility = Value.Style.ImplementationVisibility.PUBLIC)
public abstract class THbaseDeleteRows {
    private static final Logger LOG = org.apache.logging.log4j.LogManager.getLogger(THbaseDeleteRows.class);
    abstract Connection connection();
    abstract String namespaceName();
    abstract String tableName();
    abstract String rowKeys();
    abstract boolean dieOnError();
    abstract boolean isLog4jEnabled();
    abstract String cid();

    public void doDeleteRows() throws IOException {
        try {
            String tableNameString = tableName();
            if (!"".equals(namespaceName())) {
                tableNameString = namespaceName() + ":" + tableName();
            }
           Table table = connection().getTable(TableName.valueOf(tableNameString));
           List<String> rowKeyString = new ArrayList<>(Arrays.asList(rowKeys().split(",")));
           List<Delete> listOfBatchDelete = new ArrayList<>();
           for(String key: rowKeyString){
                listOfBatchDelete.add(new Delete(Bytes.toBytes(key.trim())));
           }
           table.delete(listOfBatchDelete);

        } catch(java.lang.Exception e){

            if(dieOnError()){
                throw(e);
            } else {
                System.out.println(e.getMessage());
                if(isLog4jEnabled()) {
                    LOG.error(cid()+" - " + e.getMessage());
                }
            }
        }
    }

}
