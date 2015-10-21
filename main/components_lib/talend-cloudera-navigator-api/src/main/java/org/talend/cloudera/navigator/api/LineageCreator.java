package org.talend.cloudera.navigator.api;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.cloudera.nav.sdk.client.NavigatorPlugin;
import com.cloudera.nav.sdk.client.writer.ResultSet;
import com.cloudera.nav.sdk.model.Source;
import com.cloudera.nav.sdk.model.SourceType;
import com.cloudera.nav.sdk.model.entities.Entity;
import com.cloudera.nav.sdk.model.entities.EntityType;
import com.cloudera.nav.sdk.model.entities.FileFormat;
import com.cloudera.nav.sdk.model.entities.HdfsEntity;
import com.google.common.collect.ImmutableList;

public class LineageCreator {

    // TODO : the PluginConfigurationAPI is going to change its name on version 1.1,
    // I keep the variable here for compatibility
    public static final String APP_URL = "application_url"; //$NON-NLS-1$

    public static final String FILE_FORMAT = "file_format"; //$NON-NLS-1$

    public static final String METADATA_URI = "metadata_parent_uri"; //$NON-NLS-1$

    public static final String NAMESPACE = "namespace"; //$NON-NLS-1$

    public static final String NAV_URL = "navigator_url"; //$NON-NLS-1$

    public static final String USERNAME = "username"; //$NON-NLS-1$

    public static final String PASSWORD = "password"; //$NON-NLS-1$

    public static final String AUTOCOMMIT = "autocommit"; //$NON-NLS-1$

    public static final String DISABLE_SSL_VALIDATION = "disable_ssl_validation"; //$NON-NLS-1$

    private static org.apache.log4j.Logger LOG = org.apache.log4j.Logger.getLogger(LineageCreator.class);

    private String jobName;

    private String projectName;

    private Source fileSystem;

    private NavigatorPlugin plugin;

    private List<NavigatorNode> inputNavigatorNodes = new ArrayList<NavigatorNode>();

    private List<Entity> datasets = new ArrayList<Entity>();

    public LineageCreator(String clientApplicationUrl, String navigatorUrl, String metadataUri, String username, String password,
            String jobName, String projectName) {
        this(clientApplicationUrl, navigatorUrl, metadataUri, username, password, jobName, projectName, false);
    }

    public LineageCreator(String clientApplicationUrl, String navigatorUrl, String metadataUri, String username, String password,
            String jobName, String projectName, Boolean autoCommit) {
        this(clientApplicationUrl, navigatorUrl, metadataUri, username, password, jobName, projectName, autoCommit, false);
    }

    public LineageCreator(String clientApplicationUrl, String navigatorUrl, String metadataUri, String username, String password,
            String jobName, String projectName, Boolean autoCommit, Boolean disableValidationSSL) {
        Map<String, Object> configurationMap = new HashMap<String, Object>();
        configurationMap.put(LineageCreator.APP_URL, clientApplicationUrl);
        configurationMap.put(LineageCreator.NAV_URL, navigatorUrl);
        configurationMap.put(LineageCreator.METADATA_URI, metadataUri);
        configurationMap.put(LineageCreator.USERNAME, username);
        configurationMap.put(LineageCreator.PASSWORD, password);
        // File Format allow only JSON for the moment
        configurationMap.put(LineageCreator.FILE_FORMAT, "JSON"); //$NON-NLS-1$
        configurationMap.put(LineageCreator.NAMESPACE, "Talend"); //$NON-NLS-1$

        // TODO the next two options are not fully integrated on the PluginConfigurationFactory API.
        // They will be soon.
        if (disableValidationSSL) {
            configurationMap.put(LineageCreator.DISABLE_SSL_VALIDATION, "true"); //$NON-NLS-1$
        }
        if (autoCommit) {
            configurationMap.put(LineageCreator.AUTOCOMMIT, "true"); //$NON-NLS-1$
        }

        this.plugin = NavigatorPlugin.fromConfigMap(configurationMap);
        this.fileSystem = this.plugin.getClient().getOnlySource(SourceType.HDFS);
        this.jobName = jobName;
        this.projectName = projectName;
    }

    public void addNodeToLineage(String name, Map<String, String> schema, List<String> inputNodes, List<String> outputNodes) {
        this.inputNavigatorNodes.add(new NavigatorNode(name, schema, inputNodes, outputNodes));
    }

    public void instanciateSchema(Map<String, String> columns, String componentName, String fileSystemPath, String fileFormat) {
        instanciateSchema(columns, componentName, fileSystemPath, FileFormat.valueOf(fileFormat));
    }

    public void instanciateSchema(Map<String, String> columns, String componentName, String fileSystemPath, FileFormat fileFormat) {
        HdfsEntity container = new HdfsEntity(fileSystemPath, EntityType.DIRECTORY, this.fileSystem.getIdentity());

        TalendDataset dataset = new TalendDataset(ClouderaAPIUtil.getDatasetName(fileSystemPath), componentName, this.jobName
                + this.projectName);
        dataset.setDataContainer(container);
        dataset.setFileFormat(fileFormat);
        dataset.setFields(ClouderaFieldConvertor.convertToTalendField(columns));
        dataset.addTags(ImmutableList.of("Talend", this.jobName)); //$NON-NLS-1$

        this.datasets.add(dataset);
    }

    public void sendToNavigator() {
        sendToNavigator(false);
    }

    public void sendToNavigator(Boolean dieOnError) {
        try {
            ResultSet results = this.plugin.write(this.datasets);
            if (results.hasErrors()) {
                throw new RuntimeException(results.toString());
            }

            // Mapper
            TalendEntityMapper tem = new TalendEntityMapper(this.inputNavigatorNodes, this.jobName + this.projectName);
            tem.addTag(this.jobName);

            if (LOG.isDebugEnabled()) {
                for (NavigatorNode nn : this.inputNavigatorNodes) {
                    LOG.debug(nn);
                }
            }

            List<Entity> entities = tem.map();
            LOG.debug(tem.toString());

            results = this.plugin.write(entities);
            if (results.hasErrors()) {
                throw new RuntimeException(results.toString());
            }
        } catch (RuntimeException e) {
            if (dieOnError) {
                throw new RuntimeException(e);
            } else {
                LOG.error(e);
            }
        }

    }
}
