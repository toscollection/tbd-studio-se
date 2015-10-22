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
package org.talend.hadoop.distribution.hdp230.test;

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
import org.talend.hadoop.distribution.hdp230.HDP230Distribution;

/**
 * Test class for the {@link HDP230Distribution} distribution.
 *
 */
public class HDP230DistributionTest {

    private final static String YARN_APPLICATION_CLASSPATH = "$HADOOP_CONF_DIR,/usr/hdp/current/hadoop-client/*,/usr/hdp/current/hadoop-client/lib/*,/usr/hdp/current/hadoop-hdfs-client/*,/usr/hdp/current/hadoop-hdfs-client/lib/*,/usr/hdp/current/hadoop-mapreduce-client/*,/usr/hdp/current/hadoop-mapreduce-client/lib/*,/usr/hdp/current/hadoop-yarn-client/*,/usr/hdp/current/hadoop-yarn-client/lib/*"; //$NON-NLS-1$

    @Test
    public void testHDP230Distribution() throws Exception {
        HadoopComponent hdp230 = new HDP230Distribution();
        assertNotNull(hdp230.getDistributionName());
        assertNotNull(hdp230.getVersionName(null));
        assertEquals(EHadoopDistributions.HORTONWORKS.getName(), hdp230.getDistribution());
        assertEquals(EHadoopVersion4Drivers.HDP_2_3.getVersionValue(), hdp230.getVersion());
        assertEquals(EHadoopVersion.HADOOP_2, hdp230.getHadoopVersion());
        assertTrue(hdp230.doSupportKerberos());
        assertTrue(hdp230.doSupportUseDatanodeHostname());
        assertFalse(hdp230.doSupportGroup());
        assertTrue(((HDFSComponent) hdp230).doSupportSequenceFileShortType());
        assertFalse(((MRComponent) hdp230).isExecutedThroughWebHCat());
        assertTrue(((MRComponent) hdp230).doSupportCrossPlatformSubmission());
        assertTrue(((MRComponent) hdp230).doSupportImpersonation());
        assertEquals(YARN_APPLICATION_CLASSPATH, ((MRComponent) hdp230).getYarnApplicationClasspath());
        assertTrue(((HBaseComponent) hdp230).doSupportNewHBaseAPI());
        assertTrue(((SqoopComponent) hdp230).doJavaAPISupportStorePasswordInFile());
        assertTrue(((SqoopComponent) hdp230).doJavaAPISqoopImportSupportDeleteTargetDir());
        assertTrue(((SqoopComponent) hdp230).doJavaAPISqoopImportAllTablesSupportExcludeTable());
        assertTrue(((PigComponent) hdp230).doSupportHCatalog());
        assertFalse(((PigComponent) hdp230).pigVersionPriorTo_0_12());
        assertTrue(((PigComponent) hdp230).doSupportHBase());
        assertTrue(((PigComponent) hdp230).doSupportTezForPig());
        assertFalse(((HiveComponent) hdp230).doSupportEmbeddedMode());
        assertTrue(((HiveComponent) hdp230).doSupportStandaloneMode());
        assertFalse(((HiveComponent) hdp230).doSupportHive1());
        assertTrue(((HiveComponent) hdp230).doSupportHive2());
        assertTrue(((HiveComponent) hdp230).doSupportTezForHive());
        assertTrue(((HiveComponent) hdp230).doSupportHBaseForHive());
        assertTrue(((HiveComponent) hdp230).doSupportSSL());
        assertTrue(((HiveComponent) hdp230).doSupportORCFormat());
        assertTrue(((HiveComponent) hdp230).doSupportAvroFormat());
        assertTrue(((HiveComponent) hdp230).doSupportParquetFormat());
        assertTrue(hdp230 instanceof SparkBatchComponent);
        assertTrue(hdp230 instanceof SparkStreamingComponent);
        assertFalse(((SparkBatchComponent) hdp230).doSupportSparkStandaloneMode());
        assertTrue(((SparkBatchComponent) hdp230).doSupportSparkYarnClientMode());
        assertFalse(((SparkStreamingComponent) hdp230).doSupportSparkStandaloneMode());
        assertTrue(((SparkStreamingComponent) hdp230).doSupportSparkYarnClientMode());
        assertTrue(hdp230 instanceof HCatalogComponent);
        assertFalse(hdp230 instanceof ImpalaComponent);
    }

}
