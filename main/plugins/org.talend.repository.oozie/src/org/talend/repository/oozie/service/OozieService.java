package org.talend.repository.oozie.service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.talend.core.hadoop.IOozieService;
import org.talend.core.hadoop.version.custom.ECustomVersionGroup;
import org.talend.core.model.metadata.builder.connection.Connection;
import org.talend.core.prefs.ITalendCorePrefConstants;
import org.talend.repository.hadoopcluster.util.HCRepositoryUtil;
import org.talend.repository.model.IRepositoryNode.ENodeType;
import org.talend.repository.model.IRepositoryNode.EProperties;
import org.talend.repository.model.RepositoryNode;
import org.talend.repository.model.hadoopcluster.HadoopClusterConnection;
import org.talend.repository.model.oozie.OozieConnection;
import org.talend.repository.oozie.node.model.OozieRepositoryNodeType;
import org.talend.utils.json.JSONArray;
import org.talend.utils.json.JSONException;
import org.talend.utils.json.JSONObject;

public class OozieService implements IOozieService {

    @Override
    public Map<String, Object> getOozieParamFromConnection(Connection connection) {
        if (connection instanceof OozieConnection) {
            OozieConnection oozieConnection = (OozieConnection) connection;
            Map<String, Object> oozieParam = new HashMap<String, Object>();

            HadoopClusterConnection hcConnection = HCRepositoryUtil.getRelativeHadoopClusterConnection(oozieConnection);
            oozieParam.put(ITalendCorePrefConstants.OOZIE_SCHEDULER_USER_NAME, oozieConnection.getUserName());
            oozieParam.put(ITalendCorePrefConstants.OOZIE_SCHEDULER_GROUP, hcConnection.getGroup());
            oozieParam.put(ITalendCorePrefConstants.OOZIE_SHCEDULER_OOZIE_ENDPOINT, oozieConnection.getOozieEndPoind());
            oozieParam.put(ITalendCorePrefConstants.OOZIE_SHCEDULER_JOB_TRACKER_ENDPOINT, hcConnection.getJobTrackerURI());
            oozieParam.put(ITalendCorePrefConstants.OOZIE_SHCEDULER_NAME_NODE_ENDPOINT, hcConnection.getNameNodeURI());
            oozieParam.put(ITalendCorePrefConstants.OOZIE_SHCEDULER_HADOOP_DISTRIBUTION, hcConnection.getDistribution());
            oozieParam.put(ITalendCorePrefConstants.OOZIE_SHCEDULER_HADOOP_VERSION, hcConnection.getDfVersion());
            oozieParam.put(ITalendCorePrefConstants.OOZIE_SCHEDULER_HADOOP_CUSTOM_JARS,
                    hcConnection.getParameters().get(ECustomVersionGroup.COMMON.getName()));
            oozieParam.put(ITalendCorePrefConstants.OOZIE_SCHEDULER_HADOOP_PRINCIPAL, hcConnection.getPrincipal());
            oozieParam.put(ITalendCorePrefConstants.OOZIE_SCHEDULER_HADOOP_KERBEROS, hcConnection.isEnableKerberos());
            oozieParam.put(ITalendCorePrefConstants.OOZIE_SCHEDULER_HADOOP_USE_KEYTAB, hcConnection.isUseKeytab());
            oozieParam.put(ITalendCorePrefConstants.OOZIE_SCHEDULER_HADOOP_USE_YARN, hcConnection.isUseYarn());
            oozieParam.put(ITalendCorePrefConstants.OOZIE_SCHEDULER_HADOOP_KEYTAB_PRINCIPAL, hcConnection.getKeytabPrincipal());
            oozieParam.put(ITalendCorePrefConstants.OOZIE_SCHEDULER_HADOOP_KEYTAB_PATH, hcConnection.getKeytab());
            oozieParam.put(ITalendCorePrefConstants.OOZIE_SCHEDULER_AUTH_MODE, hcConnection.getAuthMode());
            oozieParam.put(ITalendCorePrefConstants.OOZIE_SCHEDULER_OOZIE_KERBEROS, oozieConnection.isEnableKerberos());
            oozieParam.put(ITalendCorePrefConstants.OOZIE_SCHEDULER_RELATIVE_HADOOP_CLUSTER_ID,
                    oozieConnection.getRelativeHadoopClusterId());

            return oozieParam;
        }
        return null;
    }

    @Override
    public boolean isOozieNode(RepositoryNode node) {
        if (node == null || node.getObject() == null || !ENodeType.REPOSITORY_ELEMENT.equals(node.getType())) {
            return false;
        }
        return OozieRepositoryNodeType.OOZIE.equals(node.getProperties(EProperties.CONTENT_TYPE));
    }

    @Override
    public List<HashMap<String, Object>> getHadoopProperties(Connection connection) {
        if (connection instanceof OozieConnection) {
            OozieConnection oozieConnection = (OozieConnection) connection;
            return initHadoopProperties(oozieConnection.getHadoopProperties());
        }
        return null;
    }

    private List<HashMap<String, Object>> initHadoopProperties(String hadoopProperties) {
        List<HashMap<String, Object>> properties = new ArrayList<HashMap<String, Object>>();
        try {
            if (StringUtils.isNotEmpty(hadoopProperties)) {
                JSONArray jsonArr = new JSONArray(hadoopProperties);
                for (int i = 0; i < jsonArr.length(); i++) {
                    HashMap<String, Object> map = new HashMap();
                    JSONObject object = jsonArr.getJSONObject(i);
                    Iterator it = object.keys();
                    while (it.hasNext()) {
                        String key = (String) it.next();
                        map.put(key, object.get(key));
                    }
                    properties.add(map);
                }
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return properties;
    }

}
