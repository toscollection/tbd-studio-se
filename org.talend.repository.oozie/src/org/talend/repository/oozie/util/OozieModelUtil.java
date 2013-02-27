package org.talend.repository.oozie.util;

import org.apache.commons.beanutils.BeanUtils;
import org.apache.log4j.Logger;
import org.talend.designer.hdfsbrowse.model.HDFSConnectionBean;
import org.talend.repository.hadoopcluster.util.HCRepositoryUtil;
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
            }
            bean.setUserName(connection.getUserName());
        } catch (Exception e) {
            log.error("Convert failure from OozieConnection to HDFSConnectionBean", e);
        }

        return bean;
    }
}
