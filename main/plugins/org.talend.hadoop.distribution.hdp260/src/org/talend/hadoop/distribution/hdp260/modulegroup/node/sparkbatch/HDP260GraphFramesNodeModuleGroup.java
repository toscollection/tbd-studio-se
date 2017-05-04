// ============================================================================
package org.talend.hadoop.distribution.hdp260.modulegroup.node.sparkbatch;

import java.util.HashSet;
import java.util.Set;

import org.talend.core.hadoop.version.EHadoopDistributions;
import org.talend.hadoop.distribution.DistributionModuleGroup;
import org.talend.hadoop.distribution.condition.common.SparkBatchLinkedNodeCondition;
import org.talend.hadoop.distribution.constants.SparkBatchConstant;
import org.talend.hadoop.distribution.hdp260.HDP260Constant;
import org.talend.hadoop.distribution.hdp260.HDP260Distribution;

public class HDP260GraphFramesNodeModuleGroup {

    public static Set<DistributionModuleGroup> getModuleGroups() {
        Set<DistributionModuleGroup> hs = new HashSet<>();
        DistributionModuleGroup dmg = new DistributionModuleGroup(
                HDP260Constant.GRAPHFRAMES_MRREQUIRED_MODULE_GROUP.getModuleName(), true, new SparkBatchLinkedNodeCondition(
                        EHadoopDistributions.HORTONWORKS.getName(), HDP260Distribution.VERSION,
                        SparkBatchConstant.SPARK_BATCH_SPARKCONFIGURATION_LINKEDPARAMETER).getCondition());
        hs.add(dmg);
        return hs;
    }
}
