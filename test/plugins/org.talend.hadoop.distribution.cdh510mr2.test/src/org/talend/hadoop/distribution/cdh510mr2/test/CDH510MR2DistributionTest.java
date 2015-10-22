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
package org.talend.hadoop.distribution.cdh510mr2.test;

import static org.junit.Assert.*;

import org.junit.Test;
import org.talend.core.hadoop.version.EHadoopDistributions;
import org.talend.core.hadoop.version.EHadoopVersion4Drivers;
import org.talend.hadoop.distribution.EHadoopVersion;
import org.talend.hadoop.distribution.cdh510mr2.CDH510MR2Distribution;
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
 * Test class for the {@link CDH540Distribution} distribution.
 *
 */
public class CDH510MR2DistributionTest {

    private final static String DEFAULT_YARN_APPLICATION_CLASSPATH = "$HADOOP_CONF_DIR,$HADOOP_COMMON_HOME/*,$HADOOP_COMMON_HOME/lib/*,$HADOOP_HDFS_HOME/*,$HADOOP_HDFS_HOME/lib/*,$HADOOP_MAPRED_HOME/*,$HADOOP_MAPRED_HOME/lib/*,$YARN_HOME/*,$YARN_HOME/lib/*,$HADOOP_YARN_HOME/*,$HADOOP_YARN_HOME/lib/*,$HADOOP_COMMON_HOME/share/hadoop/common/*,$HADOOP_COMMON_HOME/share/hadoop/common/lib/*,$HADOOP_HDFS_HOME/share/hadoop/hdfs/*,$HADOOP_HDFS_HOME/share/hadoop/hdfs/lib/*,$HADOOP_YARN_HOME/share/hadoop/yarn/*,$HADOOP_YARN_HOME/share/hadoop/yarn/lib/*"; //$NON-NLS-1$

    @Test
    public void testCDH540Distribution() throws Exception {
        HadoopComponent cdh510mr2 = new CDH510MR2Distribution();
        assertNotNull(cdh510mr2.getDistributionName());
        assertNotNull(cdh510mr2.getVersionName(null));
        assertEquals(EHadoopDistributions.CLOUDERA.getName(), cdh510mr2.getDistribution());
        assertEquals(EHadoopVersion4Drivers.CLOUDERA_CDH5_1.getVersionValue(), cdh510mr2.getVersion());
        assertEquals(EHadoopVersion.HADOOP_2, cdh510mr2.getHadoopVersion());
        assertTrue(cdh510mr2.doSupportKerberos());
        assertTrue(cdh510mr2.doSupportUseDatanodeHostname());
        assertFalse(cdh510mr2.doSupportGroup());
        assertTrue(((HDFSComponent) cdh510mr2).doSupportSequenceFileShortType());
        assertFalse(((MRComponent) cdh510mr2).isExecutedThroughWebHCat());
        assertTrue(((MRComponent) cdh510mr2).doSupportCrossPlatformSubmission());
        assertTrue(((MRComponent) cdh510mr2).doSupportImpersonation());
        assertEquals(DEFAULT_YARN_APPLICATION_CLASSPATH, ((MRComponent) cdh510mr2).getYarnApplicationClasspath());
        assertTrue(((HBaseComponent) cdh510mr2).doSupportNewHBaseAPI());
        assertTrue(((SqoopComponent) cdh510mr2).doJavaAPISupportStorePasswordInFile());
        assertTrue(((SqoopComponent) cdh510mr2).doJavaAPISqoopImportSupportDeleteTargetDir());
        assertTrue(((SqoopComponent) cdh510mr2).doJavaAPISqoopImportAllTablesSupportExcludeTable());
        assertTrue(((PigComponent) cdh510mr2).doSupportHCatalog());
        assertFalse(((PigComponent) cdh510mr2).pigVersionPriorTo_0_12());
        assertTrue(((PigComponent) cdh510mr2).doSupportHBase());
        assertFalse(((PigComponent) cdh510mr2).doSupportTezForPig());
        assertTrue(((HiveComponent) cdh510mr2).doSupportEmbeddedMode());
        assertTrue(((HiveComponent) cdh510mr2).doSupportStandaloneMode());
        assertTrue(((HiveComponent) cdh510mr2).doSupportHive1());
        assertTrue(((HiveComponent) cdh510mr2).doSupportHive2());
        assertFalse(((HiveComponent) cdh510mr2).doSupportTezForHive());
        assertTrue(((HiveComponent) cdh510mr2).doSupportHBaseForHive());
        assertTrue(((HiveComponent) cdh510mr2).doSupportSSL());
        assertTrue(((HiveComponent) cdh510mr2).doSupportORCFormat());
        assertTrue(((HiveComponent) cdh510mr2).doSupportAvroFormat());
        assertTrue(((HiveComponent) cdh510mr2).doSupportParquetFormat());
        assertFalse(((SparkBatchComponent) cdh510mr2).isSpark14());
        assertFalse(((SparkBatchComponent) cdh510mr2).doSupportDynamicMemoryAllocation());
        assertFalse(((SparkBatchComponent) cdh510mr2).isExecutedThroughSparkJobServer());
        assertTrue(((SparkBatchComponent) cdh510mr2).doSupportSparkStandaloneMode());
        assertTrue(((SparkBatchComponent) cdh510mr2).doSupportSparkYarnClientMode());
        assertFalse(((SparkStreamingComponent) cdh510mr2).isSpark14());
        assertFalse(((SparkStreamingComponent) cdh510mr2).doSupportDynamicMemoryAllocation());
        assertFalse(((SparkStreamingComponent) cdh510mr2).isExecutedThroughSparkJobServer());
        assertTrue(((SparkStreamingComponent) cdh510mr2).doSupportCheckpointing());
        assertTrue(((SparkStreamingComponent) cdh510mr2).doSupportSparkStandaloneMode());
        assertTrue(((SparkStreamingComponent) cdh510mr2).doSupportSparkYarnClientMode());
        assertTrue(cdh510mr2 instanceof HCatalogComponent);
        assertTrue(cdh510mr2 instanceof ImpalaComponent);
    }
}
