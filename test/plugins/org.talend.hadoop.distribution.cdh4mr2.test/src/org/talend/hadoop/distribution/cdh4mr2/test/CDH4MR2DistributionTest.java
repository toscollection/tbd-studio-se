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
package org.talend.hadoop.distribution.cdh4mr2.test;

import static org.junit.Assert.*;

import org.junit.Test;
import org.talend.core.hadoop.version.EHadoopDistributions;
import org.talend.core.hadoop.version.EHadoopVersion4Drivers;
import org.talend.hadoop.distribution.EHadoopVersion;
import org.talend.hadoop.distribution.cdh4mr2.CDH4MR2Distribution;
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
 * Test class for the {@link CDH510MR1Distribution} distribution.
 *
 */
public class CDH4MR2DistributionTest {

    private final static String DEFAULT_YARN_APPLICATION_CLASSPATH = "$HADOOP_CONF_DIR,$HADOOP_COMMON_HOME/*,$HADOOP_COMMON_HOME/lib/*,$HADOOP_HDFS_HOME/*,$HADOOP_HDFS_HOME/lib/*,$HADOOP_MAPRED_HOME/*,$HADOOP_MAPRED_HOME/lib/*,$YARN_HOME/*,$YARN_HOME/lib/*,$HADOOP_YARN_HOME/*,$HADOOP_YARN_HOME/lib/*,$HADOOP_COMMON_HOME/share/hadoop/common/*,$HADOOP_COMMON_HOME/share/hadoop/common/lib/*,$HADOOP_HDFS_HOME/share/hadoop/hdfs/*,$HADOOP_HDFS_HOME/share/hadoop/hdfs/lib/*,$HADOOP_YARN_HOME/share/hadoop/yarn/*,$HADOOP_YARN_HOME/share/hadoop/yarn/lib/*"; //$NON-NLS-1$

    @Test
    public void testCDH4MR2Distribution() throws Exception {
        HadoopComponent cdh400mr2 = new CDH4MR2Distribution();
        assertNotNull(cdh400mr2.getDistributionName());
        assertNotNull(cdh400mr2.getVersionName(null));
        assertEquals(EHadoopDistributions.CLOUDERA.getName(), cdh400mr2.getDistribution());
        assertEquals(EHadoopVersion4Drivers.CLOUDERA_CDH4_YARN.getVersionValue(), cdh400mr2.getVersion());
        assertEquals(EHadoopVersion.HADOOP_2, cdh400mr2.getHadoopVersion());
        assertTrue(cdh400mr2.doSupportKerberos());
        assertTrue(cdh400mr2.doSupportUseDatanodeHostname());
        assertFalse(cdh400mr2.doSupportGroup());
        assertTrue(((HDFSComponent) cdh400mr2).doSupportSequenceFileShortType());
        assertFalse(((MRComponent) cdh400mr2).isExecutedThroughWebHCat());
        assertFalse(((MRComponent) cdh400mr2).doSupportCrossPlatformSubmission());
        assertTrue(((MRComponent) cdh400mr2).doSupportImpersonation());
        assertEquals(DEFAULT_YARN_APPLICATION_CLASSPATH, ((MRComponent) cdh400mr2).getYarnApplicationClasspath());
        assertFalse(((HBaseComponent) cdh400mr2).doSupportNewHBaseAPI());
        assertFalse(((SqoopComponent) cdh400mr2).doJavaAPISupportStorePasswordInFile());
        assertTrue(((SqoopComponent) cdh400mr2).doJavaAPISqoopImportSupportDeleteTargetDir());
        assertTrue(((SqoopComponent) cdh400mr2).doJavaAPISqoopImportAllTablesSupportExcludeTable());
        assertFalse(((PigComponent) cdh400mr2).doSupportHCatalog());
        assertFalse(((PigComponent) cdh400mr2).pigVersionPriorTo_0_12());
        assertTrue(((PigComponent) cdh400mr2).doSupportHBase());
        assertFalse(((PigComponent) cdh400mr2).doSupportTezForPig());
        assertTrue(((HiveComponent) cdh400mr2).doSupportEmbeddedMode());
        assertTrue(((HiveComponent) cdh400mr2).doSupportStandaloneMode());
        assertTrue(((HiveComponent) cdh400mr2).doSupportHive1());
        assertTrue(((HiveComponent) cdh400mr2).doSupportHive2());
        assertFalse(((HiveComponent) cdh400mr2).doSupportTezForHive());
        assertTrue(((HiveComponent) cdh400mr2).doSupportHBaseForHive());
        assertTrue(((HiveComponent) cdh400mr2).doSupportSSL());
        assertFalse(((HiveComponent) cdh400mr2).doSupportORCFormat());
        assertTrue(((HiveComponent) cdh400mr2).doSupportAvroFormat());
        assertTrue(((HiveComponent) cdh400mr2).doSupportParquetFormat());
        assertFalse(cdh400mr2 instanceof SparkBatchComponent);
        assertFalse(cdh400mr2 instanceof SparkStreamingComponent);
        assertFalse(cdh400mr2 instanceof HCatalogComponent);
        assertFalse(cdh400mr2 instanceof ImpalaComponent);
    }
}
