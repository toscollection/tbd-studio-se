package org.talend.spark.distribution.spark311.modulegroup.node;

import java.util.HashSet;
import java.util.Set;

import org.talend.hadoop.distribution.DistributionModuleGroup;
import org.talend.hadoop.distribution.ESparkVersion;

public class Spark311NodeModuleGroup {

    public static Set<DistributionModuleGroup> getModuleGroup(String moduleGroupName, String sparkConfigLinkedParameter,
            ESparkVersion sparkLocalVersion) {
        Set<DistributionModuleGroup> hs = new HashSet<>();
        DistributionModuleGroup dmg = new DistributionModuleGroup(moduleGroupName, false);
        hs.add(dmg);
        return hs;
    }

}
