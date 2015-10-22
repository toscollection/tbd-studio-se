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
package org.talend.hadoop.distribution.hdinsight310.test;

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
import org.talend.hadoop.distribution.hdinsight310.HDInsight31Distribution;

/**
 * Test class for the {@link HDInsight31Distribution} distribution.
 *
 */
public class HDInsight31DistributionTest {

    private final static String DEFAULT_YARN_APPLICATION_CLASSPATH = "$HADOOP_CONF_DIR,$HADOOP_COMMON_HOME/*,$HADOOP_COMMON_HOME/lib/*,$HADOOP_HDFS_HOME/*,$HADOOP_HDFS_HOME/lib/*,$HADOOP_MAPRED_HOME/*,$HADOOP_MAPRED_HOME/lib/*,$YARN_HOME/*,$YARN_HOME/lib/*,$HADOOP_YARN_HOME/*,$HADOOP_YARN_HOME/lib/*,$HADOOP_COMMON_HOME/share/hadoop/common/*,$HADOOP_COMMON_HOME/share/hadoop/common/lib/*,$HADOOP_HDFS_HOME/share/hadoop/hdfs/*,$HADOOP_HDFS_HOME/share/hadoop/hdfs/lib/*,$HADOOP_YARN_HOME/share/hadoop/yarn/*,$HADOOP_YARN_HOME/share/hadoop/yarn/lib/*"; //$NON-NLS-1$

    @Test
    public void testHDInsight31Distribution() throws Exception {
        HadoopComponent hdinsight310 = new HDInsight31Distribution();
        assertNotNull(hdinsight310.getDistributionName());
        assertNotNull(hdinsight310.getVersionName(null));
        assertEquals(EHadoopDistributions.MICROSOFT_HD_INSIGHT.getName(), hdinsight310.getDistribution());
        assertEquals(EHadoopVersion4Drivers.MICROSOFT_HD_INSIGHT_3_1.getVersionValue(), hdinsight310.getVersion());
        assertEquals(EHadoopVersion.HADOOP_2, hdinsight310.getHadoopVersion());
        assertFalse(hdinsight310.doSupportKerberos());
        assertFalse(hdinsight310.doSupportUseDatanodeHostname());
        assertFalse(hdinsight310.doSupportGroup());
        assertFalse(hdinsight310 instanceof HDFSComponent);
        assertTrue(((MRComponent) hdinsight310).isExecutedThroughWebHCat());
        assertFalse(((MRComponent) hdinsight310).doSupportCrossPlatformSubmission());
        assertFalse(((MRComponent) hdinsight310).doSupportImpersonation());
        assertEquals(DEFAULT_YARN_APPLICATION_CLASSPATH, ((MRComponent) hdinsight310).getYarnApplicationClasspath());
        assertFalse(hdinsight310 instanceof HBaseComponent);
        assertFalse(hdinsight310 instanceof SqoopComponent);
        assertTrue(((PigComponent) hdinsight310).doSupportHCatalog());
        assertFalse(((PigComponent) hdinsight310).pigVersionPriorTo_0_12());
        assertFalse(((PigComponent) hdinsight310).doSupportHBase());
        assertFalse(((PigComponent) hdinsight310).doSupportTezForPig());
        assertFalse(((HiveComponent) hdinsight310).doSupportEmbeddedMode());
        assertFalse(((HiveComponent) hdinsight310).doSupportStandaloneMode());
        assertFalse(((HiveComponent) hdinsight310).doSupportHive1());
        assertFalse(((HiveComponent) hdinsight310).doSupportHive2());
        assertFalse(((HiveComponent) hdinsight310).doSupportTezForHive());
        assertFalse(((HiveComponent) hdinsight310).doSupportHBaseForHive());
        assertFalse(((HiveComponent) hdinsight310).doSupportSSL());
        assertTrue(((HiveComponent) hdinsight310).doSupportORCFormat());
        assertTrue(((HiveComponent) hdinsight310).doSupportAvroFormat());
        assertTrue(((HiveComponent) hdinsight310).doSupportParquetFormat());
        assertFalse(hdinsight310 instanceof SparkBatchComponent);
        assertFalse(hdinsight310 instanceof SparkStreamingComponent);
        assertFalse(hdinsight310 instanceof HCatalogComponent);
        assertFalse(hdinsight310 instanceof ImpalaComponent);
    }

}
