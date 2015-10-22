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
package org.talend.hadoop.distribution.emr400.test;

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
import org.talend.hadoop.distribution.emr400.EMR400Distribution;

/**
 * Test class for the {@link EMR400Distribution} distribution.
 *
 */
public class EMR400DistributionTest {

    private final static String DEFAULT_YARN_APPLICATION_CLASSPATH = "$HADOOP_CONF_DIR,$HADOOP_COMMON_HOME/*,$HADOOP_COMMON_HOME/lib/*,$HADOOP_HDFS_HOME/*,$HADOOP_HDFS_HOME/lib/*,$HADOOP_MAPRED_HOME/*,$HADOOP_MAPRED_HOME/lib/*,$HADOOP_YARN_HOME/*,$HADOOP_YARN_HOME/lib/*,/usr/lib/hadoop-lzo/lib/*,/usr/share/aws/emr/emrfs/conf, /usr/share/aws/emr/emrfs/lib/*,/usr/share/aws/emr/emrfs/auxlib/*,/usr/share/aws/emr/lib/*,/usr/share/aws/emr/ddb/lib/emr-ddb-hadoop.jar, /usr/share/aws/emr/goodies/lib/emr-hadoop-goodies.jar,/usr/share/aws/emr/kinesis/lib/emr-kinesis-hadoop.jar,/usr/lib/spark/yarn/lib/datanucleus-api-jdo.jar,/usr/lib/spark/yarn/lib/datanucleus-core.jar,/usr/lib/spark/yarn/lib/datanucleus-rdbms.jar,/usr/share/aws/emr/cloudwatch-sink/lib/*"; //$NON-NLS-1$

    @Test
    public void testEMR400Distribution() throws Exception {
        HadoopComponent emr400 = new EMR400Distribution();
        assertNotNull(emr400.getDistributionName());
        assertNotNull(emr400.getVersionName(null));
        assertEquals(EHadoopDistributions.AMAZON_EMR.getName(), emr400.getDistribution());
        assertEquals(EHadoopVersion4Drivers.EMR_4_0_0.getVersionValue(), emr400.getVersion());
        assertEquals(EHadoopVersion.HADOOP_2, emr400.getHadoopVersion());
        assertFalse(emr400.doSupportKerberos());
        assertTrue(emr400.doSupportUseDatanodeHostname());
        assertFalse(emr400.doSupportGroup());
        assertTrue(((HDFSComponent) emr400).doSupportSequenceFileShortType());
        assertFalse(((MRComponent) emr400).isExecutedThroughWebHCat());
        assertTrue(((MRComponent) emr400).doSupportCrossPlatformSubmission());
        assertTrue(((MRComponent) emr400).doSupportImpersonation());
        assertEquals(((MRComponent) emr400).getYarnApplicationClasspath(), DEFAULT_YARN_APPLICATION_CLASSPATH);
        assertFalse(emr400 instanceof HBaseComponent);
        assertFalse(emr400 instanceof SqoopComponent);
        assertFalse(((PigComponent) emr400).doSupportHCatalog());
        assertFalse(((PigComponent) emr400).pigVersionPriorTo_0_12());
        assertFalse(((PigComponent) emr400).doSupportHBase());
        assertFalse(((PigComponent) emr400).doSupportTezForPig());
        assertFalse(((HiveComponent) emr400).doSupportEmbeddedMode());
        assertTrue(((HiveComponent) emr400).doSupportStandaloneMode());
        assertFalse(((HiveComponent) emr400).doSupportHive1());
        assertTrue(((HiveComponent) emr400).doSupportHive2());
        assertFalse(((HiveComponent) emr400).doSupportTezForHive());
        assertFalse(((HiveComponent) emr400).doSupportHBaseForHive());
        assertTrue(((HiveComponent) emr400).doSupportSSL());
        assertTrue(((HiveComponent) emr400).doSupportORCFormat());
        assertTrue(((HiveComponent) emr400).doSupportAvroFormat());
        assertTrue(((HiveComponent) emr400).doSupportParquetFormat());
        assertTrue(((SparkBatchComponent) emr400).isSpark14());
        assertTrue(((SparkBatchComponent) emr400).doSupportDynamicMemoryAllocation());
        assertFalse(((SparkBatchComponent) emr400).isExecutedThroughSparkJobServer());
        assertFalse(((SparkBatchComponent) emr400).doSupportSparkStandaloneMode());
        assertTrue(((SparkBatchComponent) emr400).doSupportSparkYarnClientMode());
        assertTrue(((SparkStreamingComponent) emr400).isSpark14());
        assertTrue(((SparkStreamingComponent) emr400).doSupportDynamicMemoryAllocation());
        assertFalse(((SparkStreamingComponent) emr400).isExecutedThroughSparkJobServer());
        assertTrue(((SparkStreamingComponent) emr400).doSupportCheckpointing());
        assertFalse(((SparkStreamingComponent) emr400).doSupportSparkStandaloneMode());
        assertTrue(((SparkStreamingComponent) emr400).doSupportSparkYarnClientMode());
        assertFalse(emr400 instanceof HCatalogComponent);
        assertFalse(emr400 instanceof ImpalaComponent);
    }

}
