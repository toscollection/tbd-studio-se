package org.talend.hadoop.distribution.spl300.modulegroup.node.sparkstreaming;

import java.util.HashSet;
import java.util.Set;

import org.talend.hadoop.distribution.DistributionModuleGroup;
import org.talend.hadoop.distribution.spl300.SPL300Constant;
import org.talend.hadoop.distribution.utils.ModuleGroupsUtils;

public class SPL300SparkStreamingKinesisNodeModuleGroup {

    public static Set<DistributionModuleGroup> getModuleGroups() {
        Set<DistributionModuleGroup> moduleGroups = new HashSet<>();
//        moduleGroups.add(new DistributionModuleGroup(SPL300Constant.SPARK_MRREQUIRED_MODULE_GROUP.getModuleName()));
//        moduleGroups.add(new DistributionModuleGroup(SPL300Constant.SPARK_STREAMING_MRREQUIRED_MODULE_GROUP.getModuleName()));
//        moduleGroups.add(new DistributionModuleGroup(SPL300Constant.SPARK_STREAMING_KINESIS_MODULE_GROUP.getModuleName(), true, null));
        return moduleGroups;
    }

    public static Set<DistributionModuleGroup> getKinesisModuleGroups(String distribution, String version, String condition) {
        return ModuleGroupsUtils.getModuleGroups(distribution, version, condition,
        		SPL300Constant.SPARK_STREAMING_KINESIS_LIBS.getModuleName(), true);
    }
}
