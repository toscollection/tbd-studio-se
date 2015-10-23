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
package org.talend.hadoop.distribution.hdp200.test;

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
import org.talend.hadoop.distribution.hdp200.HDP200Distribution;

/**
 * Test class for the {@link HDP200Distribution} distribution.
 *
 */
public class HDP200DistributionTest {

    private final static String DEFAULT_YARN_APPLICATION_CLASSPATH = "$HADOOP_CONF_DIR,/usr/hdp/current/hadoop-client/*,/usr/hdp/current/hadoop-client/lib/*,/usr/hdp/current/hadoop-hdfs-client/*,/usr/hdp/current/hadoop-hdfs-client/lib/*,/usr/hdp/current/hadoop-mapreduce-client/*,/usr/hdp/current/hadoop-mapreduce-client/lib/*,/usr/hdp/current/hadoop-yarn-client/*,/usr/hdp/current/hadoop-yarn-client/lib/*"; //$NON-NLS-1$

    @Test
    public void testHDP200Distribution() throws Exception {
        HadoopComponent hdp200 = new HDP200Distribution();
        assertNotNull(hdp200.getDistributionName());
        assertNotNull(hdp200.getVersionName(null));
        assertEquals(EHadoopDistributions.HORTONWORKS.getName(), hdp200.getDistribution());
        assertEquals(EHadoopVersion4Drivers.HDP_2_0.getVersionValue(), hdp200.getVersion());
        assertEquals(EHadoopVersion.HADOOP_2, hdp200.getHadoopVersion());
        assertTrue(hdp200.doSupportKerberos());
        assertTrue(hdp200.doSupportUseDatanodeHostname());
        assertFalse(hdp200.doSupportGroup());
        assertTrue(((HDFSComponent) hdp200).doSupportSequenceFileShortType());
        assertFalse(((MRComponent) hdp200).isExecutedThroughWebHCat());
        assertFalse(((MRComponent) hdp200).doSupportCrossPlatformSubmission());
        assertTrue(((MRComponent) hdp200).doSupportImpersonation());
        assertEquals(DEFAULT_YARN_APPLICATION_CLASSPATH, ((MRComponent) hdp200).getYarnApplicationClasspath());
        assertTrue(((HBaseComponent) hdp200).doSupportNewHBaseAPI());
        assertTrue(((SqoopComponent) hdp200).doJavaAPISupportStorePasswordInFile());
        assertTrue(((SqoopComponent) hdp200).doJavaAPISqoopImportSupportDeleteTargetDir());
        assertTrue(((SqoopComponent) hdp200).doJavaAPISqoopImportAllTablesSupportExcludeTable());
        assertTrue(((PigComponent) hdp200).doSupportHCatalog());
        assertFalse(((PigComponent) hdp200).pigVersionPriorTo_0_12());
        assertFalse(((PigComponent) hdp200).doSupportHBase());
        assertFalse(((PigComponent) hdp200).doSupportTezForPig());
        assertTrue(((HiveComponent) hdp200).doSupportEmbeddedMode());
        assertTrue(((HiveComponent) hdp200).doSupportStandaloneMode());
        assertTrue(((HiveComponent) hdp200).doSupportHive1());
        assertTrue(((HiveComponent) hdp200).doSupportHive2());
        assertFalse(((HiveComponent) hdp200).doSupportTezForHive());
        assertTrue(((HiveComponent) hdp200).doSupportHBaseForHive());
        assertTrue(((HiveComponent) hdp200).doSupportSSL());
        assertTrue(((HiveComponent) hdp200).doSupportORCFormat());
        assertTrue(((HiveComponent) hdp200).doSupportAvroFormat());
        assertTrue(((HiveComponent) hdp200).doSupportParquetFormat());
        assertFalse(((HiveComponent) hdp200).doSupportStoreAsParquet());
        assertFalse(((HiveComponent) hdp200).doSupportClouderaNavigator());
        assertFalse(hdp200 instanceof SparkBatchComponent);
        assertFalse(hdp200 instanceof SparkStreamingComponent);
        assertTrue(hdp200 instanceof HCatalogComponent);
        assertFalse(hdp200 instanceof ImpalaComponent);
    }

}
