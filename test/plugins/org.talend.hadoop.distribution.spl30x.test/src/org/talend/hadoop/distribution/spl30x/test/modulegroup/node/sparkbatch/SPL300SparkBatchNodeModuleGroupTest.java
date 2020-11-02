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
package org.talend.hadoop.distribution.spl30x.test.modulegroup.node.sparkbatch;

import static org.junit.Assert.assertEquals;

import org.junit.Test;
import org.talend.hadoop.distribution.ESparkVersion;
import org.talend.hadoop.distribution.constants.SparkBatchConstant;
import org.talend.hadoop.distribution.spl30x.SPL30xConstant;
import org.talend.hadoop.distribution.spl30x.modulegroup.node.sparkbatch.SPL30xSparkBatchNodeModuleGroup;

public class SPL300SparkBatchNodeModuleGroupTest {

    @Test
    public void testModuleGroups() throws Exception {
        assertEquals(1, SPL30xSparkBatchNodeModuleGroup.getModuleGroup(SPL30xConstant.SPARK_BATCH_PARQUET_MODULE_GROUP.getModuleName(), SparkBatchConstant.SPARK_BATCH_SPARKCONFIGURATION_LINKEDPARAMETER, ESparkVersion.SPARK_3_0).size());
    }
}
