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
package org.talend.hadoop.distribution.hdp210.test;

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
import org.talend.hadoop.distribution.hdp210.HDP210Distribution;

/**
 * Test class for the {@link HDP210Distribution} distribution.
 *
 */
public class HDP210DistributionTest {

    private final static String YARN_APPLICATION_CLASSPATH = "/etc/hadoop/conf,/usr/lib/hadoop/*,/usr/lib/hadoop/lib/*,/usr/lib/hadoop-hdfs/*,/usr/lib/hadoop-hdfs/lib/*,/usr/lib/hadoop-yarn/*,/usr/lib/hadoop-yarn/lib/*,/usr/lib/hadoop-mapreduce/*,/usr/lib/hadoop-mapreduce/lib/*"; //$NON-NLS-1$

    @Test
    public void testHDP210Distribution() throws Exception {
        HadoopComponent hdp210 = new HDP210Distribution();
        assertNotNull(hdp210.getDistributionName());
        assertNotNull(hdp210.getVersionName(null));
        assertEquals(EHadoopDistributions.HORTONWORKS.getName(), hdp210.getDistribution());
        assertEquals(EHadoopVersion4Drivers.HDP_2_1.getVersionValue(), hdp210.getVersion());
        assertEquals(EHadoopVersion.HADOOP_2, hdp210.getHadoopVersion());
        assertTrue(hdp210.doSupportKerberos());
        assertTrue(hdp210.doSupportUseDatanodeHostname());
        assertFalse(hdp210.doSupportGroup());
        assertTrue(((HDFSComponent) hdp210).doSupportSequenceFileShortType());
        assertFalse(((MRComponent) hdp210).isExecutedThroughWebHCat());
        assertTrue(((MRComponent) hdp210).doSupportCrossPlatformSubmission());
        assertTrue(((MRComponent) hdp210).doSupportImpersonation());
        assertEquals(((MRComponent) hdp210).getYarnApplicationClasspath(), YARN_APPLICATION_CLASSPATH);
        assertTrue(((HBaseComponent) hdp210).doSupportNewHBaseAPI());
        assertTrue(((SqoopComponent) hdp210).doJavaAPISupportStorePasswordInFile());
        assertTrue(((SqoopComponent) hdp210).doJavaAPISqoopImportSupportDeleteTargetDir());
        assertTrue(((SqoopComponent) hdp210).doJavaAPISqoopImportAllTablesSupportExcludeTable());
        assertTrue(((PigComponent) hdp210).doSupportHCatalog());
        assertFalse(((PigComponent) hdp210).pigVersionPriorTo_0_12());
        assertTrue(((PigComponent) hdp210).doSupportHBase());
        assertFalse(((PigComponent) hdp210).doSupportTezForPig());
        assertTrue(((HiveComponent) hdp210).doSupportEmbeddedMode());
        assertTrue(((HiveComponent) hdp210).doSupportStandaloneMode());
        assertTrue(((HiveComponent) hdp210).doSupportHive1());
        assertTrue(((HiveComponent) hdp210).doSupportHive2());
        assertTrue(((HiveComponent) hdp210).doSupportTezForHive());
        assertTrue(((HiveComponent) hdp210).doSupportHBaseForHive());
        assertTrue(((HiveComponent) hdp210).doSupportSSL());
        assertTrue(((HiveComponent) hdp210).doSupportORCFormat());
        assertTrue(((HiveComponent) hdp210).doSupportAvroFormat());
        assertTrue(((HiveComponent) hdp210).doSupportParquetFormat());
        assertFalse(hdp210 instanceof SparkBatchComponent);
        assertFalse(hdp210 instanceof SparkStreamingComponent);
        assertTrue(hdp210 instanceof HCatalogComponent);
        assertFalse(hdp210 instanceof ImpalaComponent);
    }

}
