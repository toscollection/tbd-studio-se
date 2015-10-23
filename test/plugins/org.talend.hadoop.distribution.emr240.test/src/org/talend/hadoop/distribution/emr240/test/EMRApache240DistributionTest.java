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
package org.talend.hadoop.distribution.emr240.test;

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
import org.talend.hadoop.distribution.emr240.EMRApache240Distribution;

/**
 * Test class for the {@link EMRApache240Distribution} distribution.
 *
 */
public class EMRApache240DistributionTest {

    private final static String DEFAULT_YARN_APPLICATION_CLASSPATH = "$HADOOP_CONF_DIR,$HADOOP_COMMON_HOME/share/hadoop/common/*,$HADOOP_COMMON_HOME/share/hadoop/common/lib/*,$HADOOP_HDFS_HOME/share/hadoop/hdfs/*,$HADOOP_HDFS_HOME/share/hadoop/hdfs/lib/*,$HADOOP_YARN_HOME/share/hadoop/yarn/*,$HADOOP_YARN_HOME/share/hadoop/yarn/lib/*,/usr/share/aws/emr/emr-fs/lib/*,/usr/share/aws/emr/lib/*"; //$NON-NLS-1$

    @Test
    public void testEMRApache240Distribution() throws Exception {
        HadoopComponent emr240 = new EMRApache240Distribution();
        assertNotNull(emr240.getDistributionName());
        assertNotNull(emr240.getVersionName(null));
        assertEquals(EHadoopDistributions.AMAZON_EMR.getName(), emr240.getDistribution());
        assertEquals(EHadoopVersion4Drivers.APACHE_2_4_0_EMR.getVersionValue(), emr240.getVersion());
        assertEquals(EHadoopVersion.HADOOP_2, emr240.getHadoopVersion());
        assertFalse(emr240.doSupportKerberos());
        assertTrue(emr240.doSupportUseDatanodeHostname());
        assertFalse(emr240.doSupportGroup());
        assertTrue(((HDFSComponent) emr240).doSupportSequenceFileShortType());
        assertFalse(((MRComponent) emr240).isExecutedThroughWebHCat());
        assertTrue(((MRComponent) emr240).doSupportCrossPlatformSubmission());
        assertTrue(((MRComponent) emr240).doSupportImpersonation());
        assertEquals(((MRComponent) emr240).getYarnApplicationClasspath(), DEFAULT_YARN_APPLICATION_CLASSPATH);
        assertFalse(((HBaseComponent) emr240).doSupportNewHBaseAPI());
        assertTrue(((SqoopComponent) emr240).doJavaAPISupportStorePasswordInFile());
        assertTrue(((SqoopComponent) emr240).doJavaAPISqoopImportSupportDeleteTargetDir());
        assertFalse(((SqoopComponent) emr240).doJavaAPISqoopImportAllTablesSupportExcludeTable());
        assertFalse(((PigComponent) emr240).doSupportHCatalog());
        assertFalse(((PigComponent) emr240).pigVersionPriorTo_0_12());
        assertTrue(((PigComponent) emr240).doSupportHBase());
        assertFalse(((PigComponent) emr240).doSupportTezForPig());
        assertTrue(((HiveComponent) emr240).doSupportEmbeddedMode());
        assertTrue(((HiveComponent) emr240).doSupportStandaloneMode());
        assertFalse(((HiveComponent) emr240).doSupportHive1());
        assertTrue(((HiveComponent) emr240).doSupportHive2());
        assertFalse(((HiveComponent) emr240).doSupportTezForHive());
        assertTrue(((HiveComponent) emr240).doSupportHBaseForHive());
        assertFalse(((HiveComponent) emr240).doSupportSSL());
        assertFalse(((HiveComponent) emr240).doSupportORCFormat());
        assertFalse(((HiveComponent) emr240).doSupportAvroFormat());
        assertTrue(((HiveComponent) emr240).doSupportParquetFormat());
        assertFalse(((HiveComponent) emr240).doSupportStoreAsParquet());
        assertFalse(((HiveComponent) emr240).doSupportClouderaNavigator());
        assertFalse(emr240 instanceof SparkBatchComponent);
        assertFalse(emr240 instanceof SparkStreamingComponent);
        assertFalse(emr240 instanceof HCatalogComponent);
        assertFalse(emr240 instanceof ImpalaComponent);
    }

}
