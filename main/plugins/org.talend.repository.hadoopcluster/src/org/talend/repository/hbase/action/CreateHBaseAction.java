package org.talend.repository.hbase.action;

import java.util.Map;

import org.talend.core.database.EDatabaseTypeName;
import org.talend.core.database.conn.ConnParameterKeys;
import org.talend.core.database.conn.template.EDatabaseConnTemplate;
import org.talend.core.hadoop.version.EHadoopDistributions;
import org.talend.core.hadoop.version.EHadoopVersion4Drivers;
import org.talend.core.hadoop.version.custom.ECustomVersionGroup;
import org.talend.repository.hadoopcluster.action.common.CreateHadoopDBNodeAction;
import org.talend.repository.hadoopcluster.util.HCRepositoryUtil;
import org.talend.repository.model.RepositoryNode;
import org.talend.repository.model.hadoopcluster.HadoopClusterConnection;
import org.talend.repository.model.hadoopcluster.HadoopClusterConnectionItem;

/**
 * 
 * created by ycbai on 2013-3-20 Detailled comment
 * 
 */
public class CreateHBaseAction extends CreateHadoopDBNodeAction {

    @Override
    protected String getNodeLabel() {
        return EDatabaseConnTemplate.HBASE.getDBDisplayName();
    }

    @Override
    protected EDatabaseConnTemplate getDBType() {
        return EDatabaseConnTemplate.HBASE;
    }

    @Override
    protected void initConnectionParameters(Map<String, String> initMap, HadoopClusterConnectionItem hcConnectionItem) {
        super.initConnectionParameters(initMap, hcConnectionItem);
        HadoopClusterConnection hcConnection = (HadoopClusterConnection) hcConnectionItem.getConnection();
        initMap.put(ConnParameterKeys.CONN_PARA_KEY_HADOOP_CUSTOM_JARS,
                hcConnection.getParameters().get(ECustomVersionGroup.HBASE.getName()));
        initMap.put(ConnParameterKeys.CONN_PARA_KEY_DB_TYPE, EDatabaseConnTemplate.HBASE.getDBTypeName());
        initMap.put(ConnParameterKeys.CONN_PARA_KEY_DB_PRODUCT, EDatabaseTypeName.HBASE.getProduct());
        initMap.put(ConnParameterKeys.CONN_PARA_KEY_DB_PORT, EDatabaseConnTemplate.HBASE.getDefaultPort());
    }

    @Override
    protected boolean hideAction(RepositoryNode node) {
        HadoopClusterConnectionItem hcConnectionItem = HCRepositoryUtil.getHCConnectionItemFromRepositoryNode(node);
        if (hcConnectionItem != null) {
            HadoopClusterConnection hcConnection = (HadoopClusterConnection) hcConnectionItem.getConnection();
            EHadoopDistributions distribution = EHadoopDistributions.getDistributionByName(hcConnection.getDistribution(), false);
            EHadoopVersion4Drivers version4Drivers = EHadoopVersion4Drivers.indexOfByVersion(hcConnection.getDfVersion());
            if (EHadoopVersion4Drivers.MAPR_EMR.equals(version4Drivers) || EHadoopVersion4Drivers.MAPR1.equals(version4Drivers)
                    || EHadoopVersion4Drivers.APACHE_0_20_204.equals(version4Drivers)
                    || EHadoopVersion4Drivers.APACHE_0_20_2.equals(version4Drivers)) {
                return true;
            }
            if (distribution == EHadoopDistributions.MICROSOFT_HD_INSIGHT) {
                return true;
            }
        }

        return false;
    }

}
