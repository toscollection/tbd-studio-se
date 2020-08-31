package org.talend.hadoop.distribution.spl300.modulegroup.node.sparkbatch;

import java.util.HashSet;
import java.util.Set;

import org.talend.hadoop.distribution.DistributionModuleGroup;
import org.talend.hadoop.distribution.ESparkVersion;
import org.talend.hadoop.distribution.condition.common.SparkBatchLocalCondition;
import org.talend.hadoop.distribution.spl300.SPL300Constant;

public class SPL300SparkBatchParquetNodeModuleGroup {

    public static Set<DistributionModuleGroup> getModuleGroups() {
        Set<DistributionModuleGroup> hs = new HashSet<>();
        DistributionModuleGroup dmg = new DistributionModuleGroup(SPL300Constant.SPARK_BATCH_PARQUET_MODULE_GROUP.getModuleName(), false,
                new SparkBatchLocalCondition(ESparkVersion.SPARK_3_0));
        hs.add(dmg);
        return hs;
    }    
    
}
