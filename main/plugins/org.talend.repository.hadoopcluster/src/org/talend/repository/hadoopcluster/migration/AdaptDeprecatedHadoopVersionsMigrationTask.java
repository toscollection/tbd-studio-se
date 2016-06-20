package org.talend.repository.hadoopcluster.migration;

import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;

import org.talend.commons.exception.ExceptionHandler;
import org.talend.commons.exception.PersistenceException;
import org.talend.core.hadoop.version.EHadoopDistributions;
import org.talend.core.hadoop.version.EHadoopVersion4Drivers;
import org.talend.core.model.migration.AbstractItemMigrationTask;
import org.talend.core.model.properties.Item;
import org.talend.core.repository.model.ProxyRepositoryFactory;
import org.talend.repository.model.hadoopcluster.HadoopClusterConnection;
import org.talend.repository.model.hadoopcluster.HadoopClusterConnectionItem;

/**
 * 
 * created by ycbai on 2016年6月20日 Detailled comment
 *
 */
public class AdaptDeprecatedHadoopVersionsMigrationTask extends AbstractItemMigrationTask {

    @Override
    public Date getOrder() {
        GregorianCalendar gc = new GregorianCalendar(2016, 6, 20, 12, 0, 0);
        return gc.getTime();
    }

    @Override
    public ExecutionResult execute(Item item) {
        if (item instanceof HadoopClusterConnectionItem) {
            boolean modified = false;
            HadoopClusterConnectionItem hcItem = (HadoopClusterConnectionItem) item;
            HadoopClusterConnection hcConnection = (HadoopClusterConnection) hcItem.getConnection();
            String distribution = hcConnection.getDistribution();
            EHadoopDistributions distri = EHadoopDistributions.getDistributionByName(distribution, false);
            if (distri == null) {
                distri = EHadoopDistributions.HORTONWORKS;
                hcConnection.setDistribution(distri.getName());
                modified = true;
            }
            String version = hcConnection.getDfVersion();
            EHadoopVersion4Drivers ver = EHadoopVersion4Drivers.indexOfByVersion(version);
            if (ver == null) {
                List<EHadoopVersion4Drivers> versions = EHadoopVersion4Drivers.indexOfByDistribution(distri);
                if (versions.size() > 0) {
                    hcConnection.setDfVersion(versions.get(0).getVersionValue());
                    modified = true;
                }
            }
            if (modified) {
                try {
                    ProxyRepositoryFactory.getInstance().save(hcItem, true);
                    return ExecutionResult.SUCCESS_NO_ALERT;
                } catch (PersistenceException e) {
                    ExceptionHandler.process(e);
                    return ExecutionResult.FAILURE;
                }
            }
        }
        return ExecutionResult.NOTHING_TO_DO;
    }

}
