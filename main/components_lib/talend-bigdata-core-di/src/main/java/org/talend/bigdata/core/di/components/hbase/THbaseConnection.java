package org.talend.bigdata.core.di.components.hbase;

import org.apache.hadoop.hbase.client.Connection;
import org.apache.hadoop.hbase.client.ConnectionFactory;
import org.immutables.value.Value;
import org.apache.hadoop.conf.Configuration;

import java.io.IOException;
import java.util.Map;

@Value.Immutable
@Value.Style(visibility = Value.Style.ImplementationVisibility.PUBLIC)
public abstract class THbaseConnection {
    abstract boolean isUseExistingConnection();
    abstract Map<String, Object> globalMap();
    abstract Map<String, String> hBaseConfigurationOptions();
    abstract String existingConnectionName();
    private Configuration configuration;
    private Connection connection = null;

    public void createConfiguration(){
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
    }

    public Configuration getConfiguration(){
        return configuration;
    }
    public void createConnection() throws IOException {
        connection = ConnectionFactory.createConnection(configuration);
    }
    public Connection getConnection(){
        return connection;
    }

    public void closeConnection() throws IOException {
        if(!connection.isClosed()){
            connection.close();
        }
    }
}
