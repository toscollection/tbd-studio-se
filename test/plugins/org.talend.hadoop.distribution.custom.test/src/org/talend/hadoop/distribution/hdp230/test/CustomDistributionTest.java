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
package org.talend.hadoop.distribution.hdp230.test;

import static org.junit.Assert.*;

import org.junit.Test;
import org.talend.core.hadoop.version.EHadoopDistributions;
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
 * Test class for the {@link HDP230Distribution} distribution.
 *
 */
public class CustomDistributionTest {

    private final static String DEFAULT_YARN_APPLICATION_CLASSPATH = "$HADOOP_CONF_DIR,$HADOOP_COMMON_HOME/*,$HADOOP_COMMON_HOME/lib/*,$HADOOP_HDFS_HOME/*,$HADOOP_HDFS_HOME/lib/*,$HADOOP_MAPRED_HOME/*,$HADOOP_MAPRED_HOME/lib/*,$YARN_HOME/*,$YARN_HOME/lib/*,$HADOOP_YARN_HOME/*,$HADOOP_YARN_HOME/lib/*,$HADOOP_COMMON_HOME/share/hadoop/common/*,$HADOOP_COMMON_HOME/share/hadoop/common/lib/*,$HADOOP_HDFS_HOME/share/hadoop/hdfs/*,$HADOOP_HDFS_HOME/share/hadoop/hdfs/lib/*,$HADOOP_YARN_HOME/share/hadoop/yarn/*,$HADOOP_YARN_HOME/share/hadoop/yarn/lib/*"; //$NON-NLS-1$

    @Test
    public void testCustomDistribution() throws Exception {
        HadoopComponent custom = new org.talend.hadoop.distribution.custom.CustomDistribution();
        assertNotNull(custom.getDistributionName());
        assertNull(custom.getVersionName(null));
        assertEquals(EHadoopDistributions.CUSTOM.getName(), custom.getDistribution());
        assertNull(custom.getVersion());
        assertNull(custom.getHadoopVersion());
        assertFalse(custom.doSupportKerberos());
        assertTrue(custom.doSupportUseDatanodeHostname());
        assertFalse(custom.doSupportGroup());
        assertTrue(((HDFSComponent) custom).doSupportSequenceFileShortType());
        assertFalse(((MRComponent) custom).isExecutedThroughWebHCat());
        assertFalse(((MRComponent) custom).doSupportCrossPlatformSubmission());
        assertTrue(((MRComponent) custom).doSupportImpersonation());
        assertEquals(DEFAULT_YARN_APPLICATION_CLASSPATH, ((MRComponent) custom).getYarnApplicationClasspath());
        assertFalse(((HBaseComponent) custom).doSupportNewHBaseAPI());
        assertTrue(((SqoopComponent) custom).doJavaAPISupportStorePasswordInFile());
        assertFalse(((SqoopComponent) custom).doJavaAPISqoopImportSupportDeleteTargetDir());
        assertTrue(((SqoopComponent) custom).doJavaAPISqoopImportAllTablesSupportExcludeTable());
        assertTrue(((PigComponent) custom).doSupportHCatalog());
        assertFalse(((PigComponent) custom).pigVersionPriorTo_0_12());
        assertTrue(((PigComponent) custom).doSupportHBase());
        assertTrue(((PigComponent) custom).doSupportTezForPig());
        assertTrue(((HiveComponent) custom).doSupportEmbeddedMode());
        assertTrue(((HiveComponent) custom).doSupportStandaloneMode());
        assertTrue(((HiveComponent) custom).doSupportHive1());
        assertTrue(((HiveComponent) custom).doSupportHive2());
        assertTrue(((HiveComponent) custom).doSupportTezForHive());
        assertTrue(((HiveComponent) custom).doSupportHBaseForHive());
        assertTrue(((HiveComponent) custom).doSupportSSL());
        assertTrue(((HiveComponent) custom).doSupportORCFormat());
        assertTrue(((HiveComponent) custom).doSupportAvroFormat());
        assertTrue(((HiveComponent) custom).doSupportParquetFormat());
        assertFalse(((SparkBatchComponent) custom).isSpark14());
        assertTrue(((SparkBatchComponent) custom).doSupportDynamicMemoryAllocation());
        assertFalse(((SparkBatchComponent) custom).isExecutedThroughSparkJobServer());
        assertTrue(((SparkBatchComponent) custom).doSupportSparkStandaloneMode());
        assertTrue(((SparkBatchComponent) custom).doSupportSparkYarnClientMode());
        assertFalse(((SparkStreamingComponent) custom).isSpark14());
        assertTrue(((SparkStreamingComponent) custom).doSupportDynamicMemoryAllocation());
        assertFalse(((SparkStreamingComponent) custom).isExecutedThroughSparkJobServer());
        assertTrue(((SparkStreamingComponent) custom).doSupportCheckpointing());
        assertTrue(((SparkStreamingComponent) custom).doSupportSparkStandaloneMode());
        assertTrue(((SparkStreamingComponent) custom).doSupportSparkYarnClientMode());
        assertFalse(((HiveComponent) custom).doSupportStoreAsParquet());
        assertFalse(((HiveComponent) custom).doSupportClouderaNavigator());
        assertTrue(custom instanceof HCatalogComponent);
        assertTrue(custom instanceof ImpalaComponent);
    }

}
