package org.talend.hadoop.distribution.spl30x.modulegroup.node.sparkstreaming;

import java.util.HashSet;
import java.util.Set;

import org.talend.hadoop.distribution.DistributionModuleGroup;
import org.talend.hadoop.distribution.spl30x.SPL30xConstant;
import org.talend.hadoop.distribution.utils.ModuleGroupsUtils;

public class SPL30xSparkStreamingKinesisNodeModuleGroup {

    public static Set<DistributionModuleGroup> getModuleGroups() {
        Set<DistributionModuleGroup> moduleGroups = new HashSet<>();
//        moduleGroups.add(new DistributionModuleGroup(SPL30xConstant.SPARK_MRREQUIRED_MODULE_GROUP.getModuleName()));
//        moduleGroups.add(new DistributionModuleGroup(SPL30xConstant.SPARK_STREAMING_MRREQUIRED_MODULE_GROUP.getModuleName()));
//        moduleGroups.add(new DistributionModuleGroup(SPL30xConstant.SPARK_STREAMING_KINESIS_MODULE_GROUP.getModuleName(), true, null));
        return moduleGroups;
    }

    public static Set<DistributionModuleGroup> getKinesisModuleGroups(String distribution, String version, String condition) {
        return ModuleGroupsUtils.getModuleGroups(distribution, version, condition,
        		"TODO", true);
    }
}
