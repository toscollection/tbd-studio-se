package org.talend.cloudera.navigator.api;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.cloudera.nav.sdk.client.NavigatorPlugin;
import com.cloudera.nav.sdk.client.PluginConfigurationFactory;
import com.cloudera.nav.sdk.client.writer.ResultSet;
import com.cloudera.nav.sdk.model.entities.Entity;

public class LinageCreator {

    private String jobName;

    private NavigatorPlugin plugin;

    private List<NavigatorNode> inputNavigatorNodes = new ArrayList<NavigatorNode>();

    private List<Entity> outputEntries = new ArrayList<Entity>();

    public LinageCreator(String clientApplicationUrl, String navigatorUrl, String metadataUri, String username, String password,
            String jobName) {
        this(clientApplicationUrl, navigatorUrl, metadataUri, username, password, jobName, false);
    }

    public LinageCreator(String clientApplicationUrl, String navigatorUrl, String metadataUri, String username, String password,
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
        this.jobName = jobName;
    }

    public void addNodeToLineage(String name, Map<String, String> schema, List<String> inputNodes, List<String> outputNodes) {
        inputNavigatorNodes.add(new NavigatorNode(name, schema, inputNodes, outputNodes));
    }

    public void sendToNavigator() {
        // TODO: outputEntries = dosomthing(inputNavigatorNodes);
        // Write metadata
        ResultSet results = plugin.write(outputEntries);
        if (results.hasErrors()) {
            throw new RuntimeException(results.toString());
        }
    }
}
