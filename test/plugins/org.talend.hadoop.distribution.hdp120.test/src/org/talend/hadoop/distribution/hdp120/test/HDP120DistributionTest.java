// ============================================================================
//
// Copyright (C) 2006-2015 Talend Inc. - www.talend.com
//
// This source code is available under agreement available at
// %InstallDIR%\features\org.talend.rcp.branding.%PRODUCTNAME%\%PRODUCTNAME%license.txt
//
// You should have received a copy of the agreement
// along with this program; if not, write to Talend SA
// 9 rue Pages 92150 Suresnes, France
//
// ============================================================================
package org.talend.hadoop.distribution.hdp120.test;

import static org.junit.Assert.*;

import org.junit.Test;
import org.talend.core.hadoop.version.EHadoopDistributions;
import org.talend.core.hadoop.version.EHadoopVersion4Drivers;
import org.talend.hadoop.distribution.EHadoopVersion;
import org.talend.hadoop.distribution.component.HBaseComponent;
import org.talend.hadoop.distribution.component.HCatalogComponent;
import org.talend.hadoop.distribution.component.HDFSComponent;
import org.talend.hadoop.distribution.component.HadoopComponent;
import org.talend.hadoop.distribution.component.HiveComponent;
import org.talend.hadoop.distribution.component.ImpalaComponent;
import org.talend.hadoop.distribution.component.MRComponent;
import org.talend.hadoop.distribution.component.PigComponent;
import org.talend.hadoop.distribution.component.SparkBatchComponent;
import org.talend.hadoop.distribution.component.SparkStreamingComponent;
import org.talend.hadoop.distribution.component.SqoopComponent;
import org.talend.hadoop.distribution.hdp120.HDP120Distribution;

/**
 * Test class for the {@link HDP120Distribution} distribution.
 *
 */
public class HDP120DistributionTest {

    private static final String EMPTY = ""; //$NON-NLS-1$

    @Test
    public void testHDP120Distribution() throws Exception {
        HadoopComponent hdp120 = new HDP120Distribution();
        assertNotNull(hdp120.getDistributionName());
        assertNotNull(hdp120.getVersionName(null));
        assertEquals(EHadoopDistributions.HORTONWORKS.getName(), hdp120.getDistribution());
        assertEquals(EHadoopVersion4Drivers.HDP_1_2.getVersionValue(), hdp120.getVersion());
        assertEquals(EHadoopVersion.HADOOP_1, hdp120.getHadoopVersion());
        assertTrue(hdp120.doSupportKerberos());
        assertTrue(hdp120.doSupportUseDatanodeHostname());
        assertFalse(hdp120.doSupportGroup());
        assertFalse(((HDFSComponent) hdp120).doSupportSequenceFileShortType());
        assertFalse(((MRComponent) hdp120).isExecutedThroughWebHCat());
        assertFalse(((MRComponent) hdp120).doSupportCrossPlatformSubmission());
        assertTrue(((MRComponent) hdp120).doSupportImpersonation());
        assertEquals(EMPTY, ((MRComponent) hdp120).getYarnApplicationClasspath());
        assertFalse(((HBaseComponent) hdp120).doSupportNewHBaseAPI());
        assertFalse(((SqoopComponent) hdp120).doJavaAPISupportStorePasswordInFile());
        assertFalse(((SqoopComponent) hdp120).doJavaAPISqoopImportSupportDeleteTargetDir());
        assertFalse(((SqoopComponent) hdp120).doJavaAPISqoopImportAllTablesSupportExcludeTable());
        assertTrue(((PigComponent) hdp120).doSupportHCatalog());
        assertTrue(((PigComponent) hdp120).pigVersionPriorTo_0_12());
        assertTrue(((PigComponent) hdp120).doSupportHBase());
        assertFalse(((PigComponent) hdp120).doSupportTezForPig());
        assertTrue(((HiveComponent) hdp120).doSupportEmbeddedMode());
        assertTrue(((HiveComponent) hdp120).doSupportStandaloneMode());
        assertTrue(((HiveComponent) hdp120).doSupportHive1());
        assertTrue(((HiveComponent) hdp120).doSupportHive2());
        assertFalse(((HiveComponent) hdp120).doSupportTezForHive());
        assertTrue(((HiveComponent) hdp120).doSupportHBaseForHive());
        assertFalse(((HiveComponent) hdp120).doSupportSSL());
        assertFalse(((HiveComponent) hdp120).doSupportORCFormat());
        assertTrue(((HiveComponent) hdp120).doSupportAvroFormat());
        assertTrue(((HiveComponent) hdp120).doSupportParquetFormat());
        assertFalse(((HiveComponent) hdp120).doSupportStoreAsParquet());
        assertFalse(((HiveComponent) hdp120).doSupportClouderaNavigator());
        assertFalse(hdp120 instanceof SparkBatchComponent);
        assertFalse(hdp120 instanceof SparkStreamingComponent);
        assertTrue(hdp120 instanceof HCatalogComponent);
        assertFalse(hdp120 instanceof ImpalaComponent);
    }

}
