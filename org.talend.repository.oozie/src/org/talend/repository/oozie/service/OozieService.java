package org.talend.repository.oozie.service;

import java.util.HashMap;
import java.util.Map;

import org.talend.core.hadoop.IOozieService;
import org.talend.core.model.metadata.builder.connection.Connection;
import org.talend.core.prefs.ITalendCorePrefConstants;
import org.talend.core.repository.hadoop.custom.ECustomVersionGroup;
import org.talend.repository.hadoopcluster.util.HCRepositoryUtil;
import org.talend.repository.model.IRepositoryNode.ENodeType;
import org.talend.repository.model.IRepositoryNode.EProperties;
import org.talend.repository.model.RepositoryNode;
import org.talend.repository.model.hadoopcluster.HadoopClusterConnection;
import org.talend.repository.model.oozie.OozieConnection;
import org.talend.repository.oozie.node.model.OozieRepositoryNodeType;

public class OozieService implements IOozieService {

    @Override
    public Map<String, String> getOozieParamFromConnection(Connection connection) {
        if (connection instanceof OozieConnection) {
            OozieConnection oozieConnection = (OozieConnection) connection;
            Map<String, String> oozieParam = new HashMap<String, String>();

            HadoopClusterConnection hcConnection = HCRepositoryUtil.getRelativeHadoopClusterConnection(oozieConnection);
            oozieParam.put(ITalendCorePrefConstants.OOZIE_SCHEDULER_USER_NAME, oozieConnection.getUserName());
            oozieParam.put(ITalendCorePrefConstants.OOZIE_SHCEDULER_OOZIE_ENDPOINT, oozieConnection.getOozieEndPoind());
            oozieParam.put(ITalendCorePrefConstants.OOZIE_SHCEDULER_JOB_TRACKER_ENDPOINT, hcConnection.getJobTrackerURI());
            oozieParam.put(ITalendCorePrefConstants.OOZIE_SHCEDULER_NAME_NODE_ENDPOINT, hcConnection.getNameNodeURI());
            oozieParam.put(ITalendCorePrefConstants.OOZIE_SHCEDULER_HADOOP_DISTRIBUTION, hcConnection.getDistribution());
            oozieParam.put(ITalendCorePrefConstants.OOZIE_SHCEDULER_HADOOP_VERSION, hcConnection.getDfVersion());
            oozieParam.put(ITalendCorePrefConstants.OOZIE_SCHEDULER_HADOOP_CUSTOM_JARS,
                    hcConnection.getParameters().get(ECustomVersionGroup.COMMON.getName()));

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

}
