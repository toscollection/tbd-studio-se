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
package org.talend.hadoop.distribution.cdh4mr1.test;

import static org.junit.Assert.*;

import org.junit.Test;
import org.talend.core.hadoop.version.EHadoopDistributions;
import org.talend.core.hadoop.version.EHadoopVersion4Drivers;
import org.talend.hadoop.distribution.EHadoopVersion;
import org.talend.hadoop.distribution.cdh4mr1.CDH4MR1Distribution;
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
public class CDH4MR1DistributionTest {

    private static final String EMPTY = ""; //$NON-NLS-1$

    @Test
    public void testCDH4MR1Distribution() throws Exception {
        HadoopComponent cdh400mr1 = new CDH4MR1Distribution();
        assertNotNull(cdh400mr1.getDistributionName());
        assertNotNull(cdh400mr1.getVersionName(null));
        assertEquals(EHadoopDistributions.CLOUDERA.getName(), cdh400mr1.getDistribution());
        assertEquals(EHadoopVersion4Drivers.CLOUDERA_CDH4.getVersionValue(), cdh400mr1.getVersion());
        assertEquals(EHadoopVersion.HADOOP_1, cdh400mr1.getHadoopVersion());
        assertTrue(cdh400mr1.doSupportKerberos());
        assertFalse(cdh400mr1.doSupportUseDatanodeHostname());
        assertFalse(cdh400mr1.doSupportGroup());
        assertTrue(((HDFSComponent) cdh400mr1).doSupportSequenceFileShortType());
        assertFalse(((MRComponent) cdh400mr1).isExecutedThroughWebHCat());
        assertFalse(((MRComponent) cdh400mr1).doSupportCrossPlatformSubmission());
        assertTrue(((MRComponent) cdh400mr1).doSupportImpersonation());
        assertEquals(EMPTY, ((MRComponent) cdh400mr1).getYarnApplicationClasspath());
        assertFalse(((HBaseComponent) cdh400mr1).doSupportNewHBaseAPI());
        assertFalse(((SqoopComponent) cdh400mr1).doJavaAPISupportStorePasswordInFile());
        assertFalse(((SqoopComponent) cdh400mr1).doJavaAPISqoopImportSupportDeleteTargetDir());
        assertFalse(((SqoopComponent) cdh400mr1).doJavaAPISqoopImportAllTablesSupportExcludeTable());
        assertFalse(((PigComponent) cdh400mr1).doSupportHCatalog());
        assertFalse(((PigComponent) cdh400mr1).pigVersionPriorTo_0_12());
        assertTrue(((PigComponent) cdh400mr1).doSupportHBase());
        assertFalse(((PigComponent) cdh400mr1).doSupportTezForPig());
        assertTrue(((HiveComponent) cdh400mr1).doSupportEmbeddedMode());
        assertTrue(((HiveComponent) cdh400mr1).doSupportStandaloneMode());
        assertTrue(((HiveComponent) cdh400mr1).doSupportHive1());
        assertTrue(((HiveComponent) cdh400mr1).doSupportHive2());
        assertFalse(((HiveComponent) cdh400mr1).doSupportTezForHive());
        assertTrue(((HiveComponent) cdh400mr1).doSupportHBaseForHive());
        assertTrue(((HiveComponent) cdh400mr1).doSupportSSL());
        assertTrue(((HiveComponent) cdh400mr1).doSupportHive1Standalone());
        assertFalse(((HiveComponent) cdh400mr1).doSupportORCFormat());
        assertTrue(((HiveComponent) cdh400mr1).doSupportAvroFormat());
        assertFalse(((HiveComponent) cdh400mr1).doSupportParquetFormat());
        assertFalse(((HiveComponent) cdh400mr1).doSupportStoreAsParquet());
        assertFalse(((HiveComponent) cdh400mr1).doSupportClouderaNavigator());
        assertFalse(cdh400mr1 instanceof SparkBatchComponent);
        assertFalse(cdh400mr1 instanceof SparkStreamingComponent);
        assertFalse(cdh400mr1 instanceof HCatalogComponent);
        assertFalse(cdh400mr1 instanceof ImpalaComponent);
    }
}
