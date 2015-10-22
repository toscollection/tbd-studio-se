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
package org.talend.hadoop.distribution.mapr401.test;

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
import org.talend.hadoop.distribution.mapr401.MapR401Distribution;

/**
 * Test class for the {@link MapR401Distribution} distribution.
 *
 */
public class MapR401DistributionTest {

    private final static String DEFAULT_YARN_APPLICATION_CLASSPATH = "$HADOOP_CONF_DIR,$HADOOP_COMMON_HOME/*,$HADOOP_COMMON_HOME/lib/*,$HADOOP_HDFS_HOME/*,$HADOOP_HDFS_HOME/lib/*,$HADOOP_MAPRED_HOME/*,$HADOOP_MAPRED_HOME/lib/*,$YARN_HOME/*,$YARN_HOME/lib/*,$HADOOP_YARN_HOME/*,$HADOOP_YARN_HOME/lib/*,$HADOOP_COMMON_HOME/share/hadoop/common/*,$HADOOP_COMMON_HOME/share/hadoop/common/lib/*,$HADOOP_HDFS_HOME/share/hadoop/hdfs/*,$HADOOP_HDFS_HOME/share/hadoop/hdfs/lib/*,$HADOOP_YARN_HOME/share/hadoop/yarn/*,$HADOOP_YARN_HOME/share/hadoop/yarn/lib/*"; //$NON-NLS-1$

    @Test
    public void testMapR401Distribution() throws Exception {
        HadoopComponent mapr401 = new MapR401Distribution();
        assertNotNull(mapr401.getDistributionName());
        assertNotNull(mapr401.getVersionName(null));
        assertEquals(EHadoopDistributions.MAPR.getName(), mapr401.getDistribution());
        assertEquals(EHadoopVersion4Drivers.MAPR401.getVersionValue(), mapr401.getVersion());
        assertEquals(EHadoopVersion.HADOOP_2, mapr401.getHadoopVersion());
        assertFalse(mapr401.doSupportKerberos());
        assertFalse(mapr401.doSupportUseDatanodeHostname());
        assertTrue(mapr401.doSupportGroup());
        assertTrue(((HDFSComponent) mapr401).doSupportSequenceFileShortType());
        assertFalse(((MRComponent) mapr401).isExecutedThroughWebHCat());
        assertTrue(((MRComponent) mapr401).doSupportCrossPlatformSubmission());
        assertFalse(((MRComponent) mapr401).doSupportImpersonation());
        assertEquals(DEFAULT_YARN_APPLICATION_CLASSPATH, ((MRComponent) mapr401).getYarnApplicationClasspath());
        assertFalse(((HBaseComponent) mapr401).doSupportNewHBaseAPI());
        assertFalse(((SqoopComponent) mapr401).doJavaAPISupportStorePasswordInFile());
        assertFalse(((SqoopComponent) mapr401).doJavaAPISqoopImportSupportDeleteTargetDir());
        assertFalse(((SqoopComponent) mapr401).doJavaAPISqoopImportAllTablesSupportExcludeTable());
        assertTrue(((PigComponent) mapr401).doSupportHCatalog());
        assertFalse(((PigComponent) mapr401).pigVersionPriorTo_0_12());
        assertTrue(((PigComponent) mapr401).doSupportHBase());
        assertFalse(((PigComponent) mapr401).doSupportTezForPig());
        assertTrue(((HiveComponent) mapr401).doSupportEmbeddedMode());
        assertTrue(((HiveComponent) mapr401).doSupportStandaloneMode());
        assertTrue(((HiveComponent) mapr401).doSupportHive1());
        assertTrue(((HiveComponent) mapr401).doSupportHive2());
        assertTrue(((HiveComponent) mapr401).doSupportTezForHive());
        assertTrue(((HiveComponent) mapr401).doSupportHBaseForHive());
        assertFalse(((HiveComponent) mapr401).doSupportSSL());
        assertTrue(((HiveComponent) mapr401).doSupportORCFormat());
        assertTrue(((HiveComponent) mapr401).doSupportAvroFormat());
        assertTrue(((HiveComponent) mapr401).doSupportParquetFormat());
        assertFalse(mapr401 instanceof SparkBatchComponent);
        assertFalse(mapr401 instanceof SparkStreamingComponent);
        assertTrue(mapr401 instanceof HCatalogComponent);
        assertFalse(mapr401 instanceof ImpalaComponent);
    }

}
