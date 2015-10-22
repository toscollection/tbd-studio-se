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
package org.talend.hadoop.distribution.cdh500mr2.test;

import static org.junit.Assert.*;

import org.junit.Test;
import org.talend.core.hadoop.version.EHadoopDistributions;
import org.talend.core.hadoop.version.EHadoopVersion4Drivers;
import org.talend.hadoop.distribution.EHadoopVersion;
import org.talend.hadoop.distribution.cdh500mr2.CDH500MR2Distribution;
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
public class CDH500MR2DistributionTest {

    private final static String DEFAULT_YARN_APPLICATION_CLASSPATH = "$HADOOP_CONF_DIR,$HADOOP_COMMON_HOME/*,$HADOOP_COMMON_HOME/lib/*,$HADOOP_HDFS_HOME/*,$HADOOP_HDFS_HOME/lib/*,$HADOOP_MAPRED_HOME/*,$HADOOP_MAPRED_HOME/lib/*,$YARN_HOME/*,$YARN_HOME/lib/*,$HADOOP_YARN_HOME/*,$HADOOP_YARN_HOME/lib/*,$HADOOP_COMMON_HOME/share/hadoop/common/*,$HADOOP_COMMON_HOME/share/hadoop/common/lib/*,$HADOOP_HDFS_HOME/share/hadoop/hdfs/*,$HADOOP_HDFS_HOME/share/hadoop/hdfs/lib/*,$HADOOP_YARN_HOME/share/hadoop/yarn/*,$HADOOP_YARN_HOME/share/hadoop/yarn/lib/*"; //$NON-NLS-1$

    @Test
    public void testCDH500MR2Distribution() throws Exception {
        HadoopComponent cdh500 = new CDH500MR2Distribution();
        assertNotNull(cdh500.getDistributionName());
        assertNotNull(cdh500.getVersionName(null));
        assertEquals(EHadoopDistributions.CLOUDERA.getName(), cdh500.getDistribution());
        assertEquals(EHadoopVersion4Drivers.CLOUDERA_CDH5.getVersionValue(), cdh500.getVersion());
        assertEquals(EHadoopVersion.HADOOP_2, cdh500.getHadoopVersion());
        assertTrue(cdh500.doSupportKerberos());
        assertTrue(cdh500.doSupportUseDatanodeHostname());
        assertFalse(cdh500.doSupportGroup());
        assertTrue(((HDFSComponent) cdh500).doSupportSequenceFileShortType());
        assertFalse(((MRComponent) cdh500).isExecutedThroughWebHCat());
        assertTrue(((MRComponent) cdh500).doSupportCrossPlatformSubmission());
        assertTrue(((MRComponent) cdh500).doSupportImpersonation());
        assertEquals(DEFAULT_YARN_APPLICATION_CLASSPATH, ((MRComponent) cdh500).getYarnApplicationClasspath());
        assertTrue(((HBaseComponent) cdh500).doSupportNewHBaseAPI());
        assertTrue(((SqoopComponent) cdh500).doJavaAPISupportStorePasswordInFile());
        assertTrue(((SqoopComponent) cdh500).doJavaAPISqoopImportSupportDeleteTargetDir());
        assertTrue(((SqoopComponent) cdh500).doJavaAPISqoopImportAllTablesSupportExcludeTable());
        assertTrue(((PigComponent) cdh500).doSupportHCatalog());
        assertFalse(((PigComponent) cdh500).pigVersionPriorTo_0_12());
        assertTrue(((PigComponent) cdh500).doSupportHBase());
        assertFalse(((PigComponent) cdh500).doSupportTezForPig());
        assertTrue(((HiveComponent) cdh500).doSupportEmbeddedMode());
        assertTrue(((HiveComponent) cdh500).doSupportStandaloneMode());
        assertTrue(((HiveComponent) cdh500).doSupportHive1());
        assertTrue(((HiveComponent) cdh500).doSupportHive2());
        assertFalse(((HiveComponent) cdh500).doSupportTezForHive());
        assertTrue(((HiveComponent) cdh500).doSupportHBaseForHive());
        assertTrue(((HiveComponent) cdh500).doSupportSSL());
        assertTrue(((HiveComponent) cdh500).doSupportORCFormat());
        assertTrue(((HiveComponent) cdh500).doSupportAvroFormat());
        assertTrue(((HiveComponent) cdh500).doSupportParquetFormat());
        assertFalse(cdh500 instanceof SparkBatchComponent);
        assertFalse(cdh500 instanceof SparkStreamingComponent);
        assertTrue(cdh500 instanceof HCatalogComponent);
        assertFalse(cdh500 instanceof ImpalaComponent);
    }
}
