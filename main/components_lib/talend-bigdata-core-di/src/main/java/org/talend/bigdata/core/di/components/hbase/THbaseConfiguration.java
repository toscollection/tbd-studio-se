package org.talend.bigdata.core.di.components.hbase;

import org.immutables.value.Value;
import org.apache.hadoop.conf.Configuration;

import java.util.Map;

@Value.Immutable
@Value.Style(visibility = Value.Style.ImplementationVisibility.PUBLIC)
public abstract class THbaseConfiguration {
    abstract boolean isUseExistingConnection();
    abstract Map<String, Object> globalMap();
    abstract Map<String, String> hBaseConfigurationOptions();
    abstract String existingConnectionName();

    public Configuration createAndGetConfiguration(){
        Configuration configuration=null;
        if (!isUseExistingConnection()){
            configuration = org.apache.hadoop.hbase.HBaseConfiguration.create();
            configuration.clear();
            for (Map.Entry<String, String> entry : hBaseConfigurationOptions().entrySet()) {
                configuration.set(entry.getKey(),entry.getValue());
            }
        } else {
            String connString = "conn_" + existingConnectionName();
            configuration = (org.apache.hadoop.conf.Configuration)globalMap().get(connString);
            if(configuration == null){
                throw new RuntimeException(connString+"'s connection is null!");
            }
        }
        return configuration;
    }

}
