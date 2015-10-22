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
package org.talend.hadoop.distribution.mapr410.test;

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
import org.talend.hadoop.distribution.mapr410.MapR410Distribution;

/**
 * Test class for the {@link MapR410Distribution} distribution.
 *
 */
public class MapR410DistributionTest {

    private final static String DEFAULT_YARN_APPLICATION_CLASSPATH = "$HADOOP_CONF_DIR,$HADOOP_COMMON_HOME/*,$HADOOP_COMMON_HOME/lib/*,$HADOOP_HDFS_HOME/*,$HADOOP_HDFS_HOME/lib/*,$HADOOP_MAPRED_HOME/*,$HADOOP_MAPRED_HOME/lib/*,$YARN_HOME/*,$YARN_HOME/lib/*,$HADOOP_YARN_HOME/*,$HADOOP_YARN_HOME/lib/*,$HADOOP_COMMON_HOME/share/hadoop/common/*,$HADOOP_COMMON_HOME/share/hadoop/common/lib/*,$HADOOP_HDFS_HOME/share/hadoop/hdfs/*,$HADOOP_HDFS_HOME/share/hadoop/hdfs/lib/*,$HADOOP_YARN_HOME/share/hadoop/yarn/*,$HADOOP_YARN_HOME/share/hadoop/yarn/lib/*"; //$NON-NLS-1$

    @Test
    public void testMapR410Distribution() throws Exception {
        HadoopComponent mapr410 = new MapR410Distribution();
        assertNotNull(mapr410.getDistributionName());
        assertNotNull(mapr410.getVersionName(null));
        assertEquals(EHadoopDistributions.MAPR.getName(), mapr410.getDistribution());
        assertEquals(EHadoopVersion4Drivers.MAPR410.getVersionValue(), mapr410.getVersion());
        assertEquals(EHadoopVersion.HADOOP_2, mapr410.getHadoopVersion());
        assertFalse(mapr410.doSupportKerberos());
        assertFalse(mapr410.doSupportUseDatanodeHostname());
        assertTrue(mapr410.doSupportGroup());
        assertTrue(((HDFSComponent) mapr410).doSupportSequenceFileShortType());
        assertFalse(((MRComponent) mapr410).isExecutedThroughWebHCat());
        assertTrue(((MRComponent) mapr410).doSupportCrossPlatformSubmission());
        assertFalse(((MRComponent) mapr410).doSupportImpersonation());
        assertEquals(DEFAULT_YARN_APPLICATION_CLASSPATH, ((MRComponent) mapr410).getYarnApplicationClasspath());
        assertTrue(((HBaseComponent) mapr410).doSupportNewHBaseAPI());
        assertFalse(((SqoopComponent) mapr410).doJavaAPISupportStorePasswordInFile());
        assertFalse(((SqoopComponent) mapr410).doJavaAPISqoopImportSupportDeleteTargetDir());
        assertFalse(((SqoopComponent) mapr410).doJavaAPISqoopImportAllTablesSupportExcludeTable());
        assertTrue(((PigComponent) mapr410).doSupportHCatalog());
        assertFalse(((PigComponent) mapr410).pigVersionPriorTo_0_12());
        assertTrue(((PigComponent) mapr410).doSupportHBase());
        assertTrue(((PigComponent) mapr410).doSupportTezForPig());
        assertTrue(((HiveComponent) mapr410).doSupportEmbeddedMode());
        assertTrue(((HiveComponent) mapr410).doSupportStandaloneMode());
        assertFalse(((HiveComponent) mapr410).doSupportHive1());
        assertTrue(((HiveComponent) mapr410).doSupportHive2());
        assertTrue(((HiveComponent) mapr410).doSupportTezForHive());
        assertTrue(((HiveComponent) mapr410).doSupportHBaseForHive());
        assertFalse(((HiveComponent) mapr410).doSupportSSL());
        assertTrue(((HiveComponent) mapr410).doSupportORCFormat());
        assertTrue(((HiveComponent) mapr410).doSupportAvroFormat());
        assertTrue(((HiveComponent) mapr410).doSupportParquetFormat());
        assertFalse(((SparkBatchComponent) mapr410).isSpark14());
        assertFalse(((SparkBatchComponent) mapr410).doSupportDynamicMemoryAllocation());
        assertFalse(((SparkBatchComponent) mapr410).isExecutedThroughSparkJobServer());
        assertTrue(((SparkBatchComponent) mapr410).doSupportSparkStandaloneMode());
        assertTrue(((SparkBatchComponent) mapr410).doSupportSparkYarnClientMode());
        assertFalse(mapr410 instanceof SparkStreamingComponent);
        assertTrue(mapr410 instanceof HCatalogComponent);
        assertFalse(mapr410 instanceof ImpalaComponent);
    }

}
