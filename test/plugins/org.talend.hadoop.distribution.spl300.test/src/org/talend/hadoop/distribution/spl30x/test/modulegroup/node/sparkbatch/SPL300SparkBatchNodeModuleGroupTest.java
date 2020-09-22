// ============================================================================
//
// Copyright (C) 2006-2019 Talend Inc. - www.talend.com
//
// This source code is available under agreement available at
// %InstallDIR%\features\org.talend.rcp.branding.%PRODUCTNAME%\%PRODUCTNAME%license.txt
//
// You should have received a copy of the agreement
// along with this program; if not, write to Talend SA
// 9 rue Pages 92150 Suresnes, France
//
// ============================================================================
package org.talend.hadoop.distribution.spl300.test.modulegroup.node.sparkbatch;

import static org.junit.Assert.assertEquals;

import org.junit.Test;
import org.talend.hadoop.distribution.ESparkVersion;
import org.talend.hadoop.distribution.constants.SparkBatchConstant;
import org.talend.hadoop.distribution.spl300.SPL300Constant;
import org.talend.hadoop.distribution.spl300.modulegroup.node.sparkbatch.SPL300SparkBatchNodeModuleGroup;

public class SPL300SparkBatchNodeModuleGroupTest {

    @Test
    public void testModuleGroups() throws Exception {
        assertEquals(1, SPL300SparkBatchNodeModuleGroup.getModuleGroup(SPL300Constant.SPARK_BATCH_PARQUET_MODULE_GROUP.getModuleName(), SparkBatchConstant.SPARK_BATCH_SPARKCONFIGURATION_LINKEDPARAMETER, ESparkVersion.SPARK_3_0).size());
    }
}
