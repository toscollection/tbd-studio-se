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
package org.talend.hadoop.distribution.cdh540.test;

import static org.junit.Assert.*;

import org.junit.Test;
import org.talend.core.hadoop.version.EHadoopDistributions;
import org.talend.core.hadoop.version.EHadoopVersion4Drivers;
import org.talend.hadoop.distribution.EHadoopVersion;
import org.talend.hadoop.distribution.cdh540.CDH540Distribution;
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
public class CDH540DistributionTest {

    private final static String DEFAULT_YARN_APPLICATION_CLASSPATH = "$HADOOP_CONF_DIR,$HADOOP_COMMON_HOME/*,$HADOOP_COMMON_HOME/lib/*,$HADOOP_HDFS_HOME/*,$HADOOP_HDFS_HOME/lib/*,$HADOOP_MAPRED_HOME/*,$HADOOP_MAPRED_HOME/lib/*,$YARN_HOME/*,$YARN_HOME/lib/*,$HADOOP_YARN_HOME/*,$HADOOP_YARN_HOME/lib/*,$HADOOP_COMMON_HOME/share/hadoop/common/*,$HADOOP_COMMON_HOME/share/hadoop/common/lib/*,$HADOOP_HDFS_HOME/share/hadoop/hdfs/*,$HADOOP_HDFS_HOME/share/hadoop/hdfs/lib/*,$HADOOP_YARN_HOME/share/hadoop/yarn/*,$HADOOP_YARN_HOME/share/hadoop/yarn/lib/*"; //$NON-NLS-1$

    @Test
    public void testCDH540Distribution() throws Exception {
        HadoopComponent cdh540 = new CDH540Distribution();
        assertNotNull(cdh540.getDistributionName());
        assertNotNull(cdh540.getVersionName(null));
        assertEquals(EHadoopDistributions.CLOUDERA.getName(), cdh540.getDistribution());
        assertEquals(EHadoopVersion4Drivers.CLOUDERA_CDH5_4.getVersionValue(), cdh540.getVersion());
        assertEquals(EHadoopVersion.HADOOP_2, cdh540.getHadoopVersion());
        assertTrue(cdh540.doSupportKerberos());
        assertTrue(cdh540.doSupportUseDatanodeHostname());
        assertFalse(cdh540.doSupportGroup());
        assertTrue(((HDFSComponent) cdh540).doSupportSequenceFileShortType());
        assertFalse(((MRComponent) cdh540).isExecutedThroughWebHCat());
        assertTrue(((MRComponent) cdh540).doSupportCrossPlatformSubmission());
        assertTrue(((MRComponent) cdh540).doSupportImpersonation());
        assertEquals(DEFAULT_YARN_APPLICATION_CLASSPATH, ((MRComponent) cdh540).getYarnApplicationClasspath());
        assertTrue(((HBaseComponent) cdh540).doSupportNewHBaseAPI());
        assertTrue(((SqoopComponent) cdh540).doJavaAPISupportStorePasswordInFile());
        assertTrue(((SqoopComponent) cdh540).doJavaAPISqoopImportSupportDeleteTargetDir());
        assertTrue(((SqoopComponent) cdh540).doJavaAPISqoopImportAllTablesSupportExcludeTable());
        assertTrue(((PigComponent) cdh540).doSupportHCatalog());
        assertFalse(((PigComponent) cdh540).pigVersionPriorTo_0_12());
        assertTrue(((PigComponent) cdh540).doSupportHBase());
        assertFalse(((PigComponent) cdh540).doSupportTezForPig());
        assertTrue(((HiveComponent) cdh540).doSupportEmbeddedMode());
        assertTrue(((HiveComponent) cdh540).doSupportStandaloneMode());
        assertFalse(((HiveComponent) cdh540).doSupportHive1());
        assertTrue(((HiveComponent) cdh540).doSupportHive2());
        assertFalse(((HiveComponent) cdh540).doSupportTezForHive());
        assertTrue(((HiveComponent) cdh540).doSupportHBaseForHive());
        assertTrue(((HiveComponent) cdh540).doSupportSSL());
        assertTrue(((HiveComponent) cdh540).doSupportORCFormat());
        assertTrue(((HiveComponent) cdh540).doSupportAvroFormat());
        assertTrue(((HiveComponent) cdh540).doSupportParquetFormat());
        assertFalse(((HiveComponent) cdh540).doSupportStoreAsParquet());
        assertFalse(((HiveComponent) cdh540).doSupportClouderaNavigator());
        assertFalse(((SparkBatchComponent) cdh540).isSpark14());
        assertTrue(((SparkBatchComponent) cdh540).doSupportDynamicMemoryAllocation());
        assertFalse(((SparkBatchComponent) cdh540).isExecutedThroughSparkJobServer());
        assertTrue(((SparkBatchComponent) cdh540).doSupportSparkStandaloneMode());
        assertTrue(((SparkBatchComponent) cdh540).doSupportSparkYarnClientMode());
        assertFalse(((SparkStreamingComponent) cdh540).isSpark14());
        assertTrue(((SparkStreamingComponent) cdh540).doSupportDynamicMemoryAllocation());
        assertFalse(((SparkStreamingComponent) cdh540).isExecutedThroughSparkJobServer());
        assertTrue(((SparkStreamingComponent) cdh540).doSupportCheckpointing());
        assertTrue(((SparkStreamingComponent) cdh540).doSupportSparkStandaloneMode());
        assertTrue(((SparkStreamingComponent) cdh540).doSupportSparkYarnClientMode());
        assertTrue(((SparkStreamingComponent) cdh540).doSupportSparkYarnClientMode());

        assertTrue(cdh540 instanceof HCatalogComponent);
        assertTrue(cdh540 instanceof ImpalaComponent);

    }
}
