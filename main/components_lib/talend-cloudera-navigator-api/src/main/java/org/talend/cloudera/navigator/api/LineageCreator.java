package org.talend.cloudera.navigator.api;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.cloudera.nav.sdk.client.NavigatorPlugin;
import com.cloudera.nav.sdk.client.PluginConfigurationFactory;
import com.cloudera.nav.sdk.client.writer.ResultSet;
import com.cloudera.nav.sdk.model.Source;
import com.cloudera.nav.sdk.model.SourceType;
import com.cloudera.nav.sdk.model.entities.Entity;
import com.cloudera.nav.sdk.model.entities.EntityType;
import com.cloudera.nav.sdk.model.entities.FileFormat;
import com.cloudera.nav.sdk.model.entities.HdfsEntity;
import com.google.common.collect.ImmutableList;

public class LineageCreator {

    private static org.apache.log4j.Logger LOG = org.apache.log4j.Logger.getLogger(LineageCreator.class);

    private String jobName;

    private Source fileSystem;

    private NavigatorPlugin plugin;

    private List<NavigatorNode> inputNavigatorNodes = new ArrayList<NavigatorNode>();

    private List<Entity> datasets = new ArrayList<Entity>();

    private String jobId = "" + System.currentTimeMillis();

    public LineageCreator(String clientApplicationUrl, String navigatorUrl, String metadataUri, String username, String password,
            String jobName) {
        this(clientApplicationUrl, navigatorUrl, metadataUri, username, password, jobName, false);
    }

    public LineageCreator(String clientApplicationUrl, String navigatorUrl, String metadataUri, String username, String password,
            String jobName, Boolean autoCommit) {
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
            configurationMap.put("autocommit", "true"); //$NON-NLS-1$ //$NON-NLS-2$
        }

        this.plugin = NavigatorPlugin.fromConfigMap(configurationMap);
        this.fileSystem = this.plugin.getClient().getOnlySource(SourceType.HDFS);
        this.jobName = jobName;
    }

    public void addNodeToLineage(String name, Map<String, String> schema, List<String> inputNodes, List<String> outputNodes) {
        this.inputNavigatorNodes.add(new NavigatorNode(name, schema, inputNodes, outputNodes));
    }

    public void instanciateSchema(Map<String, String> columns, String componentName, String fileSystemPath, String fileFormat) {
        instanciateSchema(columns, componentName, fileSystemPath, FileFormat.valueOf(fileFormat));
    }

    public void instanciateSchema(Map<String, String> columns, String componentName, String fileSystemPath, FileFormat fileFormat) {
        HdfsEntity container = new HdfsEntity(fileSystemPath, EntityType.DIRECTORY, this.fileSystem.getIdentity());

        TalendDataset dataset = new TalendDataset(ClouderaAPIUtil.getDatasetName(fileSystemPath), componentName, this.jobId);
        dataset.setDataContainer(container);
        dataset.setFileFormat(fileFormat);
        dataset.setFields(ClouderaFieldConvertor.convertToTalendField(columns));
        dataset.addTags(ImmutableList.of("Talend", this.jobName)); //$NON-NLS-1$

        this.datasets.add(dataset);
    }

    public void sendToNavigator() {
        ResultSet results = this.plugin.write(this.datasets);
        if (results.hasErrors()) {
            throw new RuntimeException(results.toString());
        }

        // Mapper
        TalendEntityMapper tem = new TalendEntityMapper(this.inputNavigatorNodes, jobId);

        if (LOG.isDebugEnabled()) {
            for (NavigatorNode nn : this.inputNavigatorNodes) {
                LOG.debug(nn);
            }
        }

        List<Entity> entities = tem.map();
        LOG.debug(entities);

        results = this.plugin.write(entities);
        if (results.hasErrors()) {
            throw new RuntimeException(results.toString());
        }
    }
}
