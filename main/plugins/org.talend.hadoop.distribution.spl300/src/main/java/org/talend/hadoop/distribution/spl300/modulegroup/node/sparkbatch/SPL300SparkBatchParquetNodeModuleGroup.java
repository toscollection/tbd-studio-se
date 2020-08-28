package org.talend.hadoop.distribution.spl300.modulegroup.node.sparkbatch;

import java.util.HashSet;
import java.util.Set;

import org.talend.hadoop.distribution.DistributionModuleGroup;
import org.talend.hadoop.distribution.ESparkVersion;
import org.talend.hadoop.distribution.condition.common.SparkBatchLinkedNodeCondition;

public class SPL300SparkBatchParquetNodeModuleGroup {

    public static final String SPARK_PARQUET_GROUP_NAME = "SPARKBATCH-PARQUET-SPL300"; //$NON-NLS-1$

    public static Set<DistributionModuleGroup> getModuleGroups() {
        Set<DistributionModuleGroup> hs = new HashSet<>();
        DistributionModuleGroup dmg = new DistributionModuleGroup(SPARK_PARQUET_GROUP_NAME, false,
                new SparkBatchLinkedNodeCondition(ESparkVersion.SPARK_3_0).getCondition());
        hs.add(dmg);
        return hs;
    }    
    
}
