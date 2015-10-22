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
package org.talend.hadoop.distribution.piv101.test;

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
import org.talend.hadoop.distribution.piv101.Pivotal101Distribution;

/**
 * Test class for the {@link Pivotal101Distribution} distribution.
 *
 */
public class Pivotal101DistributionTest {

    private final static String DEFAULT_YARN_APPLICATION_CLASSPATH = "$HADOOP_CONF_DIR,$HADOOP_COMMON_HOME/*,$HADOOP_COMMON_HOME/lib/*,$HADOOP_HDFS_HOME/*,$HADOOP_HDFS_HOME/lib/*,$HADOOP_MAPRED_HOME/*,$HADOOP_MAPRED_HOME/lib/*,$YARN_HOME/*,$YARN_HOME/lib/*,$HADOOP_YARN_HOME/*,$HADOOP_YARN_HOME/lib/*,$HADOOP_COMMON_HOME/share/hadoop/common/*,$HADOOP_COMMON_HOME/share/hadoop/common/lib/*,$HADOOP_HDFS_HOME/share/hadoop/hdfs/*,$HADOOP_HDFS_HOME/share/hadoop/hdfs/lib/*,$HADOOP_YARN_HOME/share/hadoop/yarn/*,$HADOOP_YARN_HOME/share/hadoop/yarn/lib/*"; //$NON-NLS-1$

    @Test
    public void testPivotal101Distribution() throws Exception {
        HadoopComponent piv101 = new Pivotal101Distribution();
        assertNotNull(piv101.getDistributionName());
        assertNotNull(piv101.getVersionName(null));
        assertEquals(EHadoopDistributions.PIVOTAL_HD.getName(), piv101.getDistribution());
        assertEquals(EHadoopVersion4Drivers.PIVOTAL_HD_1_0_1.getVersionValue(), piv101.getVersion());
        assertEquals(EHadoopVersion.HADOOP_2, piv101.getHadoopVersion());
        assertFalse(piv101.doSupportKerberos());
        assertTrue(piv101.doSupportUseDatanodeHostname());
        assertFalse(piv101.doSupportGroup());
        assertTrue(((HDFSComponent) piv101).doSupportSequenceFileShortType());
        assertFalse(((MRComponent) piv101).isExecutedThroughWebHCat());
        assertFalse(((MRComponent) piv101).doSupportCrossPlatformSubmission());
        assertTrue(((MRComponent) piv101).doSupportImpersonation());
        assertEquals(DEFAULT_YARN_APPLICATION_CLASSPATH, ((MRComponent) piv101).getYarnApplicationClasspath());
        assertFalse(((HBaseComponent) piv101).doSupportNewHBaseAPI());
        assertFalse(((SqoopComponent) piv101).doJavaAPISupportStorePasswordInFile());
        assertFalse(((SqoopComponent) piv101).doJavaAPISqoopImportSupportDeleteTargetDir());
        assertFalse(((SqoopComponent) piv101).doJavaAPISqoopImportAllTablesSupportExcludeTable());
        assertFalse(((PigComponent) piv101).doSupportHCatalog());
        assertFalse(((PigComponent) piv101).pigVersionPriorTo_0_12());
        assertTrue(((PigComponent) piv101).doSupportHBase());
        assertFalse(((PigComponent) piv101).doSupportTezForPig());
        assertTrue(((HiveComponent) piv101).doSupportEmbeddedMode());
        assertTrue(((HiveComponent) piv101).doSupportStandaloneMode());
        assertTrue(((HiveComponent) piv101).doSupportHive1());
        assertFalse(((HiveComponent) piv101).doSupportHive2());
        assertFalse(((HiveComponent) piv101).doSupportTezForHive());
        assertTrue(((HiveComponent) piv101).doSupportHBaseForHive());
        assertFalse(((HiveComponent) piv101).doSupportSSL());
        assertFalse(((HiveComponent) piv101).doSupportORCFormat());
        assertFalse(((HiveComponent) piv101).doSupportAvroFormat());
        assertFalse(((HiveComponent) piv101).doSupportParquetFormat());
        assertFalse(piv101 instanceof SparkBatchComponent);
        assertFalse(piv101 instanceof SparkStreamingComponent);
        assertFalse(piv101 instanceof HCatalogComponent);
        assertFalse(piv101 instanceof ImpalaComponent);
    }

}
