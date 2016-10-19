// ============================================================================
package org.talend.hadoop.distribution.emr500.modulegroup.node.sparkbatch;

import java.util.HashSet;
import java.util.Set;

import org.talend.hadoop.distribution.DistributionModuleGroup;
import org.talend.hadoop.distribution.condition.common.SparkBatchLinkedNodeCondition;

public class EMR500GraphFramesNodeModuleGroup {

    public static final String GRAPHFRAMES_GROUP_NAME = "GRAPHFRAMES-LIB-EMR_5_0_0_LATEST"; //$NON-NLS-1$
    
    public static Set<DistributionModuleGroup> getModuleGroups(String distribution, String version) {
        Set<DistributionModuleGroup> hs = new HashSet<>();
        DistributionModuleGroup dmg = new DistributionModuleGroup(GRAPHFRAMES_GROUP_NAME, true,
                new SparkBatchLinkedNodeCondition(distribution, version).getCondition());
        hs.add(dmg);
        return hs;
    }
}