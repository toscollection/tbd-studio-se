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
package org.talend.hadoop.distribution.condition.common;

import org.talend.hadoop.distribution.ComponentType;
import org.talend.hadoop.distribution.condition.BooleanOperator;
import org.talend.hadoop.distribution.condition.ComponentCondition;
import org.talend.hadoop.distribution.condition.EqualityOperator;
import org.talend.hadoop.distribution.condition.LinkedNodeExpression;
import org.talend.hadoop.distribution.condition.MultiComponentCondition;
import org.talend.hadoop.distribution.condition.NestedComponentCondition;
import org.talend.hadoop.distribution.condition.SimpleComponentCondition;
import org.talend.hadoop.distribution.constants.SparkBatchConstant;

/**
 * created by rdubois on 16 nov. 2015 Detailled comment
 *
 */
public class SparkBatchLinkedNodeCondition {

    private MultiComponentCondition mCondition = null;

    public SparkBatchLinkedNodeCondition(String distribution, String version) {
        this(distribution, version, SparkBatchConstant.SPARK_BATCH_SPARKCONFIGURATION_LINKEDPARAMETER);
    }

    public SparkBatchLinkedNodeCondition(String distribution, String version, String linkedParameter) {
        final ComponentCondition isCurrentDistribution = new NestedComponentCondition(new MultiComponentCondition(
                new SimpleComponentCondition(new LinkedNodeExpression(linkedParameter,
                        ComponentType.SPARKBATCH.getDistributionParameter(), distribution, EqualityOperator.EQ)),
                new SimpleComponentCondition(new LinkedNodeExpression(linkedParameter, ComponentType.SPARKBATCH
                        .getVersionParameter(), version, EqualityOperator.EQ)), BooleanOperator.AND));

        final ComponentCondition isNotLocal = new SimpleComponentCondition(new LinkedNodeExpression(linkedParameter,
                SparkBatchConstant.SPARKCONFIGURATION_IS_LOCAL_MODE_PARAMETER, "false", EqualityOperator.EQ)); //$NON-NLS-1$

        this.mCondition = new MultiComponentCondition(isCurrentDistribution, isNotLocal, BooleanOperator.AND);
    }

    public MultiComponentCondition getCondition() {
        return this.mCondition;
    }
}
