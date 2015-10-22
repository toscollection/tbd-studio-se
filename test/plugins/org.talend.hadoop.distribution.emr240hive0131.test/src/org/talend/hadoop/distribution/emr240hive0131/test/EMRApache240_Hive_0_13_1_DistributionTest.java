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
package org.talend.hadoop.distribution.emr240hive0131.test;

import static org.junit.Assert.*;

import org.junit.Test;
import org.talend.core.hadoop.version.EHadoopDistributions;
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
import org.talend.hadoop.distribution.emr240hive0131.EMRApache240_Hive_0_13_1_Distribution;

/**
 * Test class for the {@link EMRApache240_Hive_0_13_1_Distribution} distribution.
 *
 */
public class EMRApache240_Hive_0_13_1_DistributionTest {

    private final static String DEFAULT_YARN_APPLICATION_CLASSPATH = "$HADOOP_CONF_DIR,$HADOOP_COMMON_HOME/share/hadoop/common/*,$HADOOP_COMMON_HOME/share/hadoop/common/lib/*,$HADOOP_HDFS_HOME/share/hadoop/hdfs/*,$HADOOP_HDFS_HOME/share/hadoop/hdfs/lib/*,$HADOOP_YARN_HOME/share/hadoop/yarn/*,$HADOOP_YARN_HOME/share/hadoop/yarn/lib/*,/usr/share/aws/emr/emr-fs/lib/*,/usr/share/aws/emr/lib/*"; //$NON-NLS-1$ {

    @Test
    public void testEMRApache240_Hive_0_13_1_Distribution() throws Exception {
        HadoopComponent emr240hive0131 = new EMRApache240_Hive_0_13_1_Distribution();
        assertNotNull(emr240hive0131.getDistributionName());
        assertNotNull(emr240hive0131.getVersionName(null));
        assertEquals(EHadoopDistributions.AMAZON_EMR.getName(), emr240hive0131.getDistribution());
        assertEquals("APACHE_2_4_0_EMR_0_13_1", emr240hive0131.getVersion()); //$NON-NLS-1$
        assertEquals(EHadoopVersion.HADOOP_2, emr240hive0131.getHadoopVersion());
        assertFalse(emr240hive0131.doSupportKerberos());
        assertTrue(emr240hive0131.doSupportUseDatanodeHostname());
        assertFalse(emr240hive0131.doSupportGroup());
        assertFalse(emr240hive0131 instanceof HDFSComponent);
        assertFalse(((MRComponent) emr240hive0131).isExecutedThroughWebHCat());
        assertTrue(((MRComponent) emr240hive0131).doSupportCrossPlatformSubmission());
        assertTrue(((MRComponent) emr240hive0131).doSupportImpersonation());
        assertEquals(((MRComponent) emr240hive0131).getYarnApplicationClasspath(), DEFAULT_YARN_APPLICATION_CLASSPATH);
        assertFalse(emr240hive0131 instanceof HBaseComponent);
        assertFalse(emr240hive0131 instanceof SqoopComponent);
        assertFalse(emr240hive0131 instanceof PigComponent);
        assertFalse(((HiveComponent) emr240hive0131).doSupportEmbeddedMode());
        assertTrue(((HiveComponent) emr240hive0131).doSupportStandaloneMode());
        assertFalse(((HiveComponent) emr240hive0131).doSupportHive1());
        assertTrue(((HiveComponent) emr240hive0131).doSupportHive2());
        assertFalse(((HiveComponent) emr240hive0131).doSupportTezForHive());
        assertTrue(((HiveComponent) emr240hive0131).doSupportHBaseForHive());
        assertFalse(((HiveComponent) emr240hive0131).doSupportSSL());
        assertFalse(((HiveComponent) emr240hive0131).doSupportORCFormat());
        assertFalse(((HiveComponent) emr240hive0131).doSupportAvroFormat());
        assertTrue(((HiveComponent) emr240hive0131).doSupportParquetFormat());
        assertFalse(emr240hive0131 instanceof SparkBatchComponent);
        assertFalse(emr240hive0131 instanceof SparkStreamingComponent);
        assertFalse(emr240hive0131 instanceof HCatalogComponent);
        assertFalse(emr240hive0131 instanceof ImpalaComponent);
    }

}
