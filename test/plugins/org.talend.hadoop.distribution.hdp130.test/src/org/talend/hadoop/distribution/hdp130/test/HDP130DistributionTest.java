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
package org.talend.hadoop.distribution.hdp130.test;

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
import org.talend.hadoop.distribution.hdp130.HDP130Distribution;

/**
 * Test class for the {@link HDP130Distribution} distribution.
 *
 */
public class HDP130DistributionTest {

    private final static String EMPTY = ""; //$NON-NLS-1$

    @Test
    public void testHDP130Distribution() throws Exception {
        HadoopComponent hdp130 = new HDP130Distribution();
        assertNotNull(hdp130.getDistributionName());
        assertNotNull(hdp130.getVersionName(null));
        assertEquals(EHadoopDistributions.HORTONWORKS.getName(), hdp130.getDistribution());
        assertEquals(EHadoopVersion4Drivers.HDP_1_3.getVersionValue(), hdp130.getVersion());
        assertEquals(EHadoopVersion.HADOOP_1, hdp130.getHadoopVersion());
        assertTrue(hdp130.doSupportKerberos());
        assertTrue(hdp130.doSupportUseDatanodeHostname());
        assertFalse(hdp130.doSupportGroup());
        assertFalse(((HDFSComponent) hdp130).doSupportSequenceFileShortType());
        assertFalse(((MRComponent) hdp130).isExecutedThroughWebHCat());
        assertFalse(((MRComponent) hdp130).doSupportCrossPlatformSubmission());
        assertTrue(((MRComponent) hdp130).doSupportImpersonation());
        assertEquals(EMPTY, ((MRComponent) hdp130).getYarnApplicationClasspath());
        assertFalse(((HBaseComponent) hdp130).doSupportNewHBaseAPI());
        assertFalse(((SqoopComponent) hdp130).doJavaAPISupportStorePasswordInFile());
        assertFalse(((SqoopComponent) hdp130).doJavaAPISqoopImportSupportDeleteTargetDir());
        assertFalse(((SqoopComponent) hdp130).doJavaAPISqoopImportAllTablesSupportExcludeTable());
        assertTrue(((PigComponent) hdp130).doSupportHCatalog());
        assertTrue(((PigComponent) hdp130).pigVersionPriorTo_0_12());
        assertTrue(((PigComponent) hdp130).doSupportHBase());
        assertFalse(((PigComponent) hdp130).doSupportTezForPig());
        assertTrue(((HiveComponent) hdp130).doSupportEmbeddedMode());
        assertTrue(((HiveComponent) hdp130).doSupportStandaloneMode());
        assertTrue(((HiveComponent) hdp130).doSupportHive1());
        assertTrue(((HiveComponent) hdp130).doSupportHive2());
        assertFalse(((HiveComponent) hdp130).doSupportTezForHive());
        assertTrue(((HiveComponent) hdp130).doSupportHBaseForHive());
        assertFalse(((HiveComponent) hdp130).doSupportSSL());
        assertTrue(((HiveComponent) hdp130).doSupportORCFormat());
        assertTrue(((HiveComponent) hdp130).doSupportAvroFormat());
        assertTrue(((HiveComponent) hdp130).doSupportParquetFormat());
        assertFalse(hdp130 instanceof SparkBatchComponent);
        assertFalse(hdp130 instanceof SparkStreamingComponent);
        assertTrue(hdp130 instanceof HCatalogComponent);
        assertFalse(hdp130 instanceof ImpalaComponent);
    }

}
