package org.talend.repository.oozie.util;

import java.util.Iterator;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

import org.apache.commons.beanutils.BeanUtils;
import org.apache.log4j.Logger;
import org.talend.designer.hdfsbrowse.model.HDFSConnectionBean;
import org.talend.repository.hadoopcluster.util.HCRepositoryUtil;
import org.talend.repository.hadoopcluster.util.HCVersionUtil;
import org.talend.repository.model.hadoopcluster.HadoopClusterConnection;
import org.talend.repository.model.oozie.OozieConnection;

public class OozieModelUtil {

    private static Logger log = Logger.getLogger(OozieModelUtil.class);

    public static HDFSConnectionBean convert2HDFSConnectionBean(OozieConnection connection) {
        HDFSConnectionBean bean = new HDFSConnectionBean();
        try {
            HadoopClusterConnection hcConnection = HCRepositoryUtil.getRelativeHadoopClusterConnection(connection);
            if (hcConnection != null) {
                BeanUtils.copyProperties(bean, hcConnection);
                Map<String, Object> properties = bean.getAdditionalProperties();
                Map<String, Set<String>> customVersionMap = HCVersionUtil.getCustomVersionMap(hcConnection);
                Iterator<Entry<String, Set<String>>> iter = customVersionMap.entrySet().iterator();
                while (iter.hasNext()) {
                    Map.Entry<String, Set<String>> entry = iter.next();
                    String groupName = entry.getKey();
                    Set<String> jars = entry.getValue();
                    if (jars != null && jars.size() > 0) {
                        properties.put(groupName, jars);
                    }
                }
            }
            bean.setUserName(connection.getUserName());
            bean.setRelativeHadoopClusterId(connection.getRelativeHadoopClusterId());
        } catch (Exception e) {
            log.error("Convert failure from OozieConnection to HDFSConnectionBean", e);
        }

        return bean;
    }
}
