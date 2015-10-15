package org.talend.cloudera.navigator.api;

import java.util.HashMap;
import java.util.Map;

import com.cloudera.nav.sdk.client.NavigatorPlugin;
import com.cloudera.nav.sdk.client.PluginConfigurationFactory;
import com.cloudera.nav.sdk.client.writer.ResultSet;
import com.cloudera.nav.sdk.model.Source;
import com.cloudera.nav.sdk.model.SourceType;
import com.cloudera.nav.sdk.model.entities.EntityType;
import com.cloudera.nav.sdk.model.entities.FileFormat;
import com.cloudera.nav.sdk.model.entities.HdfsEntity;

public class SchemaCreator {

    private Source fileSystem;

    private NavigatorPlugin plugin;

    public SchemaCreator(String clientApplicationUrl, String navigatorUrl, String metadataUri, String username, String password) {
        this(clientApplicationUrl, navigatorUrl, metadataUri, username, password, false);
    }

    public SchemaCreator(String clientApplicationUrl, String navigatorUrl, String metadataUri, String username, String password,
            Boolean autoCommit) {
        Map<String, Object> configurationMap = new HashMap<String, Object>();
        configurationMap.put(PluginConfigurationFactory.APP_URL, clientApplicationUrl);
        configurationMap.put(PluginConfigurationFactory.NAV_URL, navigatorUrl);
        configurationMap.put(PluginConfigurationFactory.METADATA_URI, metadataUri);
        configurationMap.put(PluginConfigurationFactory.USERNAME, username);
        configurationMap.put(PluginConfigurationFactory.PASSWORD, password);
        // File Format allow only JSON for the moment
        configurationMap.put(PluginConfigurationFactory.FILE_FORMAT, "JSON"); //$NON-NLS-1$
        configurationMap.put(PluginConfigurationFactory.NAMESPACE, "Talend"); //$NON-NLS-1$

        if (autoCommit) {
            configurationMap.put("autocommit", "true");
        }

        this.plugin = NavigatorPlugin.fromConfigMap(configurationMap);
        this.fileSystem = plugin.getClient().getOnlySource(SourceType.HDFS);
    }

    /*
     * Constructor without required external dependency of the navigator SDK. Need a correctly instanciated map of
     * system configuration
     */
    public SchemaCreator(Map<String, Object> configurationMap) {
        this(NavigatorPlugin.fromConfigMap(configurationMap));
    }

    /*
     * Constructor without required external dependency of the navigator SDK.
     */
    public SchemaCreator(String configurationFile) {
        this(NavigatorPlugin.fromConfigFile(configurationFile));
    }

    public SchemaCreator(NavigatorPlugin plugin) {
        this(plugin, SourceType.HDFS);
    }

    public SchemaCreator(NavigatorPlugin plugin, SourceType source) {
        this.plugin = plugin;
        this.fileSystem = plugin.getClient().getOnlySource(source);
    }

    public void InstanciateSchema(Map<String, String> columns, String datasetName, String fileSystemPath) {
        InstanciateSchema(columns, datasetName, fileSystemPath, FileFormat.CSV);
    }

    public void InstanciateSchema(Map<String, String> columns, String datasetName, String fileSystemPath, FileFormat fileFormat) {
        TalendDataset dataset = new TalendDataset();
        dataset.setName(datasetName);
        HdfsEntity container = new HdfsEntity(fileSystemPath, EntityType.DIRECTORY, fileSystem.getIdentity());
        dataset.setDataContainer(container);
        dataset.setFileFormat(fileFormat);

        dataset.setFields(ClouderaFieldConvertor.ConvertToTalendField(columns));

        // Write metadata
        ResultSet results = plugin.write(dataset);
        if (results.hasErrors()) {
            throw new RuntimeException(results.toString());
        }
    }
}
