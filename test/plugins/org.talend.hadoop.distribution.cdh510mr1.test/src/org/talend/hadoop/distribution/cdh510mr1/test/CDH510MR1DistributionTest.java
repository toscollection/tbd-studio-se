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
package org.talend.hadoop.distribution.cdh510mr1.test;

import static org.junit.Assert.*;

import org.junit.Test;
import org.talend.core.hadoop.version.EHadoopDistributions;
import org.talend.core.hadoop.version.EHadoopVersion4Drivers;
import org.talend.hadoop.distribution.EHadoopVersion;
import org.talend.hadoop.distribution.cdh510mr1.CDH510MR1Distribution;
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

/**
 * Test class for the {@link CDH510MR1Distribution} distribution.
 *
 */
public class CDH510MR1DistributionTest {

    private static final String EMPTY = ""; //$NON-NLS-1$

    @Test
    public void testCDH510MR1Distribution() throws Exception {
        HadoopComponent cdh510mr1 = new CDH510MR1Distribution();
        assertNotNull(cdh510mr1.getDistributionName());
        assertNotNull(cdh510mr1.getVersionName(null));
        assertEquals(EHadoopDistributions.CLOUDERA.getName(), cdh510mr1.getDistribution());
        assertEquals(EHadoopVersion4Drivers.CLOUDERA_CDH5_1_MR1.getVersionValue(), cdh510mr1.getVersion());
        assertEquals(EHadoopVersion.HADOOP_1, cdh510mr1.getHadoopVersion());
        assertTrue(cdh510mr1.doSupportKerberos());
        assertFalse(cdh510mr1.doSupportUseDatanodeHostname());
        assertFalse(cdh510mr1.doSupportGroup());
        assertTrue(((HDFSComponent) cdh510mr1).doSupportSequenceFileShortType());
        assertFalse(((MRComponent) cdh510mr1).isExecutedThroughWebHCat());
        assertFalse(((MRComponent) cdh510mr1).doSupportCrossPlatformSubmission());
        assertTrue(((MRComponent) cdh510mr1).doSupportImpersonation());
        assertEquals(EMPTY, ((MRComponent) cdh510mr1).getYarnApplicationClasspath());
        assertTrue(((HBaseComponent) cdh510mr1).doSupportNewHBaseAPI());
        assertFalse(((SqoopComponent) cdh510mr1).doJavaAPISupportStorePasswordInFile());
        assertTrue(((SqoopComponent) cdh510mr1).doJavaAPISqoopImportSupportDeleteTargetDir());
        assertFalse(((SqoopComponent) cdh510mr1).doJavaAPISqoopImportAllTablesSupportExcludeTable());
        assertTrue(((PigComponent) cdh510mr1).doSupportHCatalog());
        assertFalse(((PigComponent) cdh510mr1).pigVersionPriorTo_0_12());
        assertTrue(((PigComponent) cdh510mr1).doSupportHBase());
        assertFalse(((PigComponent) cdh510mr1).doSupportTezForPig());
        assertTrue(((HiveComponent) cdh510mr1).doSupportEmbeddedMode());
        assertTrue(((HiveComponent) cdh510mr1).doSupportStandaloneMode());
        assertTrue(((HiveComponent) cdh510mr1).doSupportHive1());
        assertTrue(((HiveComponent) cdh510mr1).doSupportHive2());
        assertFalse(((HiveComponent) cdh510mr1).doSupportTezForHive());
        assertTrue(((HiveComponent) cdh510mr1).doSupportHBaseForHive());
        assertTrue(((HiveComponent) cdh510mr1).doSupportSSL());
        assertTrue(((HiveComponent) cdh510mr1).doSupportORCFormat());
        assertTrue(((HiveComponent) cdh510mr1).doSupportAvroFormat());
        assertTrue(((HiveComponent) cdh510mr1).doSupportParquetFormat());
        assertFalse(cdh510mr1 instanceof SparkBatchComponent);
        assertFalse(cdh510mr1 instanceof SparkStreamingComponent);
        assertTrue(cdh510mr1 instanceof HCatalogComponent);
        assertFalse(cdh510mr1 instanceof ImpalaComponent);
    }
}
