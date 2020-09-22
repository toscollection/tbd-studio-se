package org.talend.hadoop.distribution.spl30x.modulegroup.node.sparkbatch;

import java.util.HashSet;
import java.util.Set;

import org.talend.hadoop.distribution.DistributionModuleGroup;
import org.talend.hadoop.distribution.ESparkVersion;
import org.talend.hadoop.distribution.condition.common.SparkBatchLocalCondition;
import org.talend.hadoop.distribution.spl30x.SPL300Constant;

public class SPL300SparkBatchNodeModuleGroup {

    public static Set<DistributionModuleGroup> getModuleGroup(String moduleGroupName, String sparkConfigLinkedParameter, ESparkVersion sparkLocalVersion) {
        Set<DistributionModuleGroup> hs = new HashSet<>();
        DistributionModuleGroup dmg = new DistributionModuleGroup(moduleGroupName, true,
                new SparkBatchLocalCondition(sparkConfigLinkedParameter, sparkLocalVersion));
        hs.add(dmg);
        return hs;
    }    
    
}
