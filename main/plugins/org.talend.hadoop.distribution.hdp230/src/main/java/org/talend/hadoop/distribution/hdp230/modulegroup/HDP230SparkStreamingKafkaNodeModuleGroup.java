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
package org.talend.hadoop.distribution.hdp230.modulegroup;

import java.util.HashSet;
import java.util.Set;

import org.talend.core.hadoop.version.EHadoopDistributions;
import org.talend.core.hadoop.version.EHadoopVersion4Drivers;
import org.talend.hadoop.distribution.ComponentType;
import org.talend.hadoop.distribution.DistributionModuleGroup;
import org.talend.hadoop.distribution.condition.BooleanOperator;
import org.talend.hadoop.distribution.condition.ComponentCondition;
import org.talend.hadoop.distribution.condition.EqualityOperator;
import org.talend.hadoop.distribution.condition.LinkedNodeExpression;
import org.talend.hadoop.distribution.condition.MultiComponentCondition;
import org.talend.hadoop.distribution.constants.SparkStreamingConstant;

public class HDP230SparkStreamingKafkaNodeModuleGroup {

    public static final String MODULE_GROUP_NAME = "SPARK-STREAMING-KAFKA-LIB-HDP_2_3"; //$NON-NLS-1$

    private static final ComponentCondition condition = new MultiComponentCondition( //
            new LinkedNodeExpression(SparkStreamingConstant.KAFKA_SPARKCONFIGURATION_LINKEDPARAMETER,
                    SparkStreamingConstant.SPARKCONFIGURATION_IS_LOCAL_MODE_PARAMETER, EqualityOperator.EQ, "false"), //
            BooleanOperator.AND,//
            new MultiComponentCondition( //
                    new LinkedNodeExpression(SparkStreamingConstant.KAFKA_SPARKCONFIGURATION_LINKEDPARAMETER,
                            ComponentType.SPARKSTREAMING.getDistributionParameter(), //
                            EqualityOperator.EQ, //
                            EHadoopDistributions.HORTONWORKS.getName()), //
                    BooleanOperator.AND,//
                    new LinkedNodeExpression(SparkStreamingConstant.KAFKA_SPARKCONFIGURATION_LINKEDPARAMETER,
                            ComponentType.SPARKSTREAMING.getVersionParameter(), //
                            EqualityOperator.EQ, //
                            EHadoopVersion4Drivers.HDP_2_3.getVersionValue())));

    public static Set<DistributionModuleGroup> getModuleGroups() {
        Set<DistributionModuleGroup> hs = new HashSet<>();
        DistributionModuleGroup dmg = new DistributionModuleGroup(MODULE_GROUP_NAME, true, condition);
        hs.add(dmg);
        return hs;
    }

}
