// ============================================================================
//
// Copyright (C) 2006-2020 Talend Inc. - www.talend.com
//
// This source code is available under agreement available at
// %InstallDIR%\features\org.talend.rcp.branding.%PRODUCTNAME%\%PRODUCTNAME%license.txt
//
// You should have received a copy of the agreement
// along with this program; if not, write to Talend SA
// 9 rue Pages 92150 Suresnes, France
//
// ============================================================================
package org.talend.hadoop.distribution.dbr73x.test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import org.junit.Test;
import org.talend.hadoop.distribution.EHadoopVersion;
import org.talend.hadoop.distribution.ESparkVersion;
import org.talend.hadoop.distribution.component.HadoopComponent;
import org.talend.hadoop.distribution.component.SparkBatchComponent;
import org.talend.hadoop.distribution.component.SparkStreamingComponent;

import org.talend.hadoop.distribution.dbr73x.DBR73XDistribution;

public class DBR73XDistributionTest {

    @Test
    public void testDatabricksDistribution() throws Exception {
        HadoopComponent distribution = new DBR73XDistribution();
        assertNotNull(distribution.getDistributionName());
        assertNotNull(distribution.getVersionName(null));
        assertTrue(distribution.doSupportS3());
        assertEquals(DBR73XDistribution.DISTRIBUTION_NAME, distribution.getDistribution());
        assertEquals(DBR73XDistribution.VERSION, distribution.getVersion());
        assertEquals(EHadoopVersion.HADOOP_2, distribution.getHadoopVersion());
        assertFalse(distribution.doSupportKerberos());

        assertTrue(((SparkBatchComponent) distribution).getSparkVersions().contains(ESparkVersion.SPARK_3_0));
        assertFalse(((SparkBatchComponent) distribution).getSparkVersions().contains(ESparkVersion.SPARK_2_3));
        assertFalse(((SparkBatchComponent) distribution).getSparkVersions().contains(ESparkVersion.SPARK_2_2));
        assertFalse(((SparkBatchComponent) distribution).getSparkVersions().contains(ESparkVersion.SPARK_2_0));
        assertFalse(((SparkBatchComponent) distribution).getSparkVersions().contains(ESparkVersion.SPARK_1_6));
        assertFalse(((SparkBatchComponent) distribution).getSparkVersions().contains(ESparkVersion.SPARK_1_5));
        assertFalse(((SparkBatchComponent) distribution).getSparkVersions().contains(ESparkVersion.SPARK_1_4));
        assertFalse(((SparkBatchComponent) distribution).getSparkVersions().contains(ESparkVersion.SPARK_1_3));

        assertTrue(((SparkBatchComponent) distribution).doSupportSparkStandaloneMode());
        assertTrue(((SparkBatchComponent) distribution).doSupportS3());
        assertTrue(((SparkBatchComponent) distribution).doSupportSparkYarnClientMode());
        assertTrue(((SparkBatchComponent) distribution).doSupportAzureDataLakeStorage());
        assertTrue(((SparkBatchComponent) distribution).doSupportKerberos());
        assertTrue(((SparkBatchComponent) distribution).doSupportS3V4());
        assertTrue(((SparkBatchComponent) distribution).doSupportAzureDataLakeStorageGen2());
        assertTrue(((SparkBatchComponent) distribution).doSupportDynamicMemoryAllocation());
        assertTrue(((SparkBatchComponent) distribution).doSupportCheckpointing());
        assertTrue(((SparkBatchComponent) distribution).doSupportCrossPlatformSubmission());
        assertTrue(((SparkBatchComponent) distribution).doSupportBackpressure());
        assertTrue(((SparkBatchComponent) distribution).doSupportAzureBlobStorage());
        assertTrue(((SparkBatchComponent) distribution).doSupportImpersonation());
        assertTrue(((SparkBatchComponent) distribution).doSupportUseDatanodeHostname());
    }
}
