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
package org.talend.hadoop.distribution.hdinsight320.test;

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
import org.talend.hadoop.distribution.hdinsight320.HDInsight32Distribution;

/**
 * Test class for the {@link HDInsight32Distribution} distribution.
 *
 */
public class HDInsight32DistributionTest {

    private final static String DEFAULT_YARN_APPLICATION_CLASSPATH = "$HADOOP_CONF_DIR,$HADOOP_COMMON_HOME/*,$HADOOP_COMMON_HOME/lib/*,$HADOOP_HDFS_HOME/*,$HADOOP_HDFS_HOME/lib/*,$HADOOP_MAPRED_HOME/*,$HADOOP_MAPRED_HOME/lib/*,$YARN_HOME/*,$YARN_HOME/lib/*,$HADOOP_YARN_HOME/*,$HADOOP_YARN_HOME/lib/*,$HADOOP_COMMON_HOME/share/hadoop/common/*,$HADOOP_COMMON_HOME/share/hadoop/common/lib/*,$HADOOP_HDFS_HOME/share/hadoop/hdfs/*,$HADOOP_HDFS_HOME/share/hadoop/hdfs/lib/*,$HADOOP_YARN_HOME/share/hadoop/yarn/*,$HADOOP_YARN_HOME/share/hadoop/yarn/lib/*"; //$NON-NLS-1$

    @Test
    public void testHDInsight32Distribution() throws Exception {
        HadoopComponent hdinsight320 = new HDInsight32Distribution();
        assertNotNull(hdinsight320.getDistributionName());
        assertNotNull(hdinsight320.getVersionName(null));
        assertEquals(EHadoopDistributions.MICROSOFT_HD_INSIGHT.getName(), hdinsight320.getDistribution());
        assertEquals(EHadoopVersion4Drivers.MICROSOFT_HD_INSIGHT_3_2.getVersionValue(), hdinsight320.getVersion());
        assertEquals(EHadoopVersion.HADOOP_2, hdinsight320.getHadoopVersion());
        assertFalse(hdinsight320.doSupportKerberos());
        assertFalse(hdinsight320.doSupportUseDatanodeHostname());
        assertFalse(hdinsight320.doSupportGroup());
        assertFalse(hdinsight320 instanceof HDFSComponent);
        assertTrue(((MRComponent) hdinsight320).isExecutedThroughWebHCat());
        assertFalse(((MRComponent) hdinsight320).doSupportCrossPlatformSubmission());
        assertFalse(((MRComponent) hdinsight320).doSupportImpersonation());
        assertEquals(DEFAULT_YARN_APPLICATION_CLASSPATH, ((MRComponent) hdinsight320).getYarnApplicationClasspath());
        assertFalse(hdinsight320 instanceof HBaseComponent);
        assertFalse(hdinsight320 instanceof SqoopComponent);
        assertTrue(((PigComponent) hdinsight320).doSupportHCatalog());
        assertFalse(((PigComponent) hdinsight320).pigVersionPriorTo_0_12());
        assertFalse(((PigComponent) hdinsight320).doSupportHBase());
        assertFalse(((PigComponent) hdinsight320).doSupportTezForPig());
        assertFalse(((HiveComponent) hdinsight320).doSupportEmbeddedMode());
        assertFalse(((HiveComponent) hdinsight320).doSupportStandaloneMode());
        assertFalse(((HiveComponent) hdinsight320).doSupportHive1());
        assertFalse(((HiveComponent) hdinsight320).doSupportHive2());
        assertFalse(((HiveComponent) hdinsight320).doSupportTezForHive());
        assertFalse(((HiveComponent) hdinsight320).doSupportHBaseForHive());
        assertFalse(((HiveComponent) hdinsight320).doSupportSSL());
        assertTrue(((HiveComponent) hdinsight320).doSupportORCFormat());
        assertTrue(((HiveComponent) hdinsight320).doSupportAvroFormat());
        assertTrue(((HiveComponent) hdinsight320).doSupportParquetFormat());
        assertFalse(((SparkBatchComponent) hdinsight320).isSpark14());
        assertFalse(((SparkBatchComponent) hdinsight320).doSupportDynamicMemoryAllocation());
        assertTrue(((SparkBatchComponent) hdinsight320).isExecutedThroughSparkJobServer());
        assertTrue(((SparkBatchComponent) hdinsight320).doSupportSparkStandaloneMode());
        assertFalse(((SparkBatchComponent) hdinsight320).doSupportSparkYarnClientMode());
        assertFalse(((SparkStreamingComponent) hdinsight320).isSpark14());
        assertFalse(((SparkStreamingComponent) hdinsight320).doSupportDynamicMemoryAllocation());
        assertTrue(((SparkStreamingComponent) hdinsight320).isExecutedThroughSparkJobServer());
        assertFalse(((SparkStreamingComponent) hdinsight320).doSupportCheckpointing());
        assertTrue(((SparkStreamingComponent) hdinsight320).doSupportSparkStandaloneMode());
        assertFalse(((SparkStreamingComponent) hdinsight320).doSupportSparkYarnClientMode());
        assertFalse(hdinsight320 instanceof HCatalogComponent);
        assertFalse(hdinsight320 instanceof ImpalaComponent);
    }

}
