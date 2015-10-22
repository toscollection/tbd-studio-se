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
package org.talend.hadoop.distribution.mapr500.test;

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
import org.talend.hadoop.distribution.mapr500.MapR500Distribution;

/**
 * Test class for the {@link MapR500Distribution} distribution.
 *
 */
public class MapR500DistributionTest {

    private final static String DEFAULT_YARN_APPLICATION_CLASSPATH = "$HADOOP_CONF_DIR,$HADOOP_COMMON_HOME/*,$HADOOP_COMMON_HOME/lib/*,$HADOOP_HDFS_HOME/*,$HADOOP_HDFS_HOME/lib/*,$HADOOP_MAPRED_HOME/*,$HADOOP_MAPRED_HOME/lib/*,$YARN_HOME/*,$YARN_HOME/lib/*,$HADOOP_YARN_HOME/*,$HADOOP_YARN_HOME/lib/*,$HADOOP_COMMON_HOME/share/hadoop/common/*,$HADOOP_COMMON_HOME/share/hadoop/common/lib/*,$HADOOP_HDFS_HOME/share/hadoop/hdfs/*,$HADOOP_HDFS_HOME/share/hadoop/hdfs/lib/*,$HADOOP_YARN_HOME/share/hadoop/yarn/*,$HADOOP_YARN_HOME/share/hadoop/yarn/lib/*"; //$NON-NLS-1$

    @Test
    public void testMapR500Distribution() throws Exception {
        HadoopComponent mapr500 = new MapR500Distribution();
        assertNotNull(mapr500.getDistributionName());
        assertNotNull(mapr500.getVersionName(null));
        assertEquals(EHadoopDistributions.MAPR.getName(), mapr500.getDistribution());
        assertEquals(EHadoopVersion4Drivers.MAPR500.getVersionValue(), mapr500.getVersion());
        assertEquals(EHadoopVersion.HADOOP_2, mapr500.getHadoopVersion());
        assertTrue(mapr500.doSupportKerberos());
        assertFalse(mapr500.doSupportUseDatanodeHostname());
        assertTrue(mapr500.doSupportGroup());
        assertTrue(((HDFSComponent) mapr500).doSupportSequenceFileShortType());
        assertFalse(((MRComponent) mapr500).isExecutedThroughWebHCat());
        assertTrue(((MRComponent) mapr500).doSupportCrossPlatformSubmission());
        assertFalse(((MRComponent) mapr500).doSupportImpersonation());
        assertEquals(DEFAULT_YARN_APPLICATION_CLASSPATH, ((MRComponent) mapr500).getYarnApplicationClasspath());
        assertTrue(((HBaseComponent) mapr500).doSupportNewHBaseAPI());
        assertFalse(((SqoopComponent) mapr500).doJavaAPISupportStorePasswordInFile());
        assertFalse(((SqoopComponent) mapr500).doJavaAPISqoopImportSupportDeleteTargetDir());
        assertFalse(((SqoopComponent) mapr500).doJavaAPISqoopImportAllTablesSupportExcludeTable());
        assertTrue(((PigComponent) mapr500).doSupportHCatalog());
        assertFalse(((PigComponent) mapr500).pigVersionPriorTo_0_12());
        assertTrue(((PigComponent) mapr500).doSupportHBase());
        assertFalse(((PigComponent) mapr500).doSupportTezForPig());
        assertFalse(((HiveComponent) mapr500).doSupportEmbeddedMode());
        assertTrue(((HiveComponent) mapr500).doSupportStandaloneMode());
        assertFalse(((HiveComponent) mapr500).doSupportHive1());
        assertTrue(((HiveComponent) mapr500).doSupportHive2());
        assertFalse(((HiveComponent) mapr500).doSupportTezForHive());
        assertTrue(((HiveComponent) mapr500).doSupportHBaseForHive());
        assertFalse(((HiveComponent) mapr500).doSupportSSL());
        assertTrue(((HiveComponent) mapr500).doSupportORCFormat());
        assertTrue(((HiveComponent) mapr500).doSupportAvroFormat());
        assertTrue(((HiveComponent) mapr500).doSupportParquetFormat());
        assertFalse(((HiveComponent) mapr500).doSupportStoreAsParquet());
        assertFalse(((HiveComponent) mapr500).doSupportClouderaNavigator());
        assertFalse(((SparkBatchComponent) mapr500).isSpark14());
        assertFalse(((SparkBatchComponent) mapr500).doSupportDynamicMemoryAllocation());
        assertFalse(((SparkBatchComponent) mapr500).isExecutedThroughSparkJobServer());
        assertTrue(((SparkBatchComponent) mapr500).doSupportSparkStandaloneMode());
        assertTrue(((SparkBatchComponent) mapr500).doSupportSparkYarnClientMode());
        assertTrue(mapr500 instanceof SparkStreamingComponent);
        assertTrue(((SparkStreamingComponent) mapr500).doSupportSparkStandaloneMode());
        assertTrue(((SparkStreamingComponent) mapr500).doSupportSparkYarnClientMode());
        assertTrue(mapr500 instanceof HCatalogComponent);
        assertFalse(mapr500 instanceof ImpalaComponent);
        assertTrue(((SparkStreamingComponent) mapr500).doSupportCheckpointing());
    }

}
