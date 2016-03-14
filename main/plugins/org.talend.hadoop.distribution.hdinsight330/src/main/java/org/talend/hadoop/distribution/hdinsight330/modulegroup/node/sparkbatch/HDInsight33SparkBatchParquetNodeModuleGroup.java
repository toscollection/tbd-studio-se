// ============================================================================
//
// Copyright (C) 2006-2016 Talend Inc. - www.talend.com
//
// This source code is available under agreement available at
// %InstallDIR%\features\org.talend.rcp.branding.%PRODUCTNAME%\%PRODUCTNAME%license.txt
//
// You should have received a copy of the agreement
// along with this program; if not, write to Talend SA
// 9 rue Pages 92150 Suresnes, France
//
// ============================================================================
package org.talend.hadoop.distribution.hdinsight330.modulegroup.node.sparkbatch;

import java.util.HashSet;
import java.util.Set;

import org.talend.core.hadoop.version.EHadoopDistributions;
import org.talend.hadoop.distribution.DistributionModuleGroup;
import org.talend.hadoop.distribution.condition.common.SparkBatchLinkedNodeCondition;
import org.talend.hadoop.distribution.hdinsight330.HDInsight33Constant;
import org.talend.hadoop.distribution.hdinsight330.HDInsight33Distribution;

public class HDInsight33SparkBatchParquetNodeModuleGroup {

    public static Set<DistributionModuleGroup> getModuleGroups() {
        Set<DistributionModuleGroup> hs = new HashSet<>();
        DistributionModuleGroup dmg = new DistributionModuleGroup(
                HDInsight33Constant.SPARK_PARQUET_MRREQUIRED_MODULE_GROUP.getModuleName(), true,
                new SparkBatchLinkedNodeCondition(EHadoopDistributions.MICROSOFT_HD_INSIGHT.getName(),
                        HDInsight33Distribution.VERSION).getCondition());
        hs.add(dmg);
        return hs;
    }
}
