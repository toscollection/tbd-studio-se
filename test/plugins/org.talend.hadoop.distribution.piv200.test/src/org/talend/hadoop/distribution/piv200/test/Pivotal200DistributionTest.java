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
package org.talend.hadoop.distribution.piv200.test;

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
import org.talend.hadoop.distribution.piv200.Pivotal200Distribution;

/**
 * Test class for the {@link Pivotal200Distribution} distribution.
 *
 */
public class Pivotal200DistributionTest {

    private final static String DEFAULT_YARN_APPLICATION_CLASSPATH = "$HADOOP_CONF_DIR,$HADOOP_COMMON_HOME/*,$HADOOP_COMMON_HOME/lib/*,$HADOOP_HDFS_HOME/*,$HADOOP_HDFS_HOME/lib/*,$HADOOP_MAPRED_HOME/*,$HADOOP_MAPRED_HOME/lib/*,$YARN_HOME/*,$YARN_HOME/lib/*,$HADOOP_YARN_HOME/*,$HADOOP_YARN_HOME/lib/*,$HADOOP_COMMON_HOME/share/hadoop/common/*,$HADOOP_COMMON_HOME/share/hadoop/common/lib/*,$HADOOP_HDFS_HOME/share/hadoop/hdfs/*,$HADOOP_HDFS_HOME/share/hadoop/hdfs/lib/*,$HADOOP_YARN_HOME/share/hadoop/yarn/*,$HADOOP_YARN_HOME/share/hadoop/yarn/lib/*"; //$NON-NLS-1$

    @Test
    public void testPivotal200Distribution() throws Exception {
        HadoopComponent piv200 = new Pivotal200Distribution();
        assertNotNull(piv200.getDistributionName());
        assertNotNull(piv200.getVersionName(null));
        assertEquals(EHadoopDistributions.PIVOTAL_HD.getName(), piv200.getDistribution());
        assertEquals(EHadoopVersion4Drivers.PIVOTAL_HD_2_0.getVersionValue(), piv200.getVersion());
        assertEquals(EHadoopVersion.HADOOP_2, piv200.getHadoopVersion());
        assertTrue(piv200.doSupportKerberos());
        assertTrue(piv200.doSupportUseDatanodeHostname());
        assertFalse(piv200.doSupportGroup());
        assertTrue(((HDFSComponent) piv200).doSupportSequenceFileShortType());
        assertFalse(((MRComponent) piv200).isExecutedThroughWebHCat());
        assertFalse(((MRComponent) piv200).doSupportCrossPlatformSubmission());
        assertTrue(((MRComponent) piv200).doSupportImpersonation());
        assertEquals(DEFAULT_YARN_APPLICATION_CLASSPATH, ((MRComponent) piv200).getYarnApplicationClasspath());
        assertTrue(((HBaseComponent) piv200).doSupportNewHBaseAPI());
        assertTrue(((SqoopComponent) piv200).doJavaAPISupportStorePasswordInFile());
        assertTrue(((SqoopComponent) piv200).doJavaAPISqoopImportSupportDeleteTargetDir());
        assertTrue(((SqoopComponent) piv200).doJavaAPISqoopImportAllTablesSupportExcludeTable());
        assertFalse(((PigComponent) piv200).doSupportHCatalog());
        assertFalse(((PigComponent) piv200).pigVersionPriorTo_0_12());
        assertFalse(((PigComponent) piv200).doSupportHBase());
        assertFalse(((PigComponent) piv200).doSupportTezForPig());
        assertTrue(((HiveComponent) piv200).doSupportEmbeddedMode());
        assertTrue(((HiveComponent) piv200).doSupportStandaloneMode());
        assertTrue(((HiveComponent) piv200).doSupportHive1());
        assertTrue(((HiveComponent) piv200).doSupportHive2());
        assertFalse(((HiveComponent) piv200).doSupportTezForHive());
        assertTrue(((HiveComponent) piv200).doSupportHBaseForHive());
        assertTrue(((HiveComponent) piv200).doSupportSSL());
        assertTrue(((HiveComponent) piv200).doSupportORCFormat());
        assertTrue(((HiveComponent) piv200).doSupportAvroFormat());
        assertTrue(((HiveComponent) piv200).doSupportParquetFormat());
        assertFalse(((HiveComponent) piv200).doSupportStoreAsParquet());
        assertFalse(((HiveComponent) piv200).doSupportClouderaNavigator());
        assertFalse(piv200 instanceof SparkBatchComponent);
        assertFalse(piv200 instanceof SparkStreamingComponent);
        assertFalse(piv200 instanceof HCatalogComponent);
        assertFalse(piv200 instanceof ImpalaComponent);
    }

}
