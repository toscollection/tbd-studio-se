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
package org.talend.hadoop.distribution.hdp220.test;

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
import org.talend.hadoop.distribution.hdp220.HDP220Distribution;

/**
 * Test class for the {@link HDP220Distribution} distribution.
 *
 */
public class HDP220DistributionTest {

    private final static String YARN_APPLICATION_CLASSPATH = "$HADOOP_CONF_DIR,/usr/hdp/current/hadoop-client/*,/usr/hdp/current/hadoop-client/lib/*,/usr/hdp/current/hadoop-hdfs-client/*,/usr/hdp/current/hadoop-hdfs-client/lib/*,/usr/hdp/current/hadoop-mapreduce-client/*,/usr/hdp/current/hadoop-mapreduce-client/lib/*,/usr/hdp/current/hadoop-yarn-client/*,/usr/hdp/current/hadoop-yarn-client/lib/*"; //$NON-NLS-1$

    @Test
    public void testHDP220Distribution() throws Exception {
        HadoopComponent hdp220 = new HDP220Distribution();
        assertNotNull(hdp220.getDistributionName());
        assertNotNull(hdp220.getVersionName(null));
        assertEquals(EHadoopDistributions.HORTONWORKS.getName(), hdp220.getDistribution());
        assertEquals(EHadoopVersion4Drivers.HDP_2_2.getVersionValue(), hdp220.getVersion());
        assertEquals(EHadoopVersion.HADOOP_2, hdp220.getHadoopVersion());
        assertTrue(hdp220.doSupportKerberos());
        assertTrue(hdp220.doSupportUseDatanodeHostname());
        assertFalse(hdp220.doSupportGroup());
        assertTrue(((HDFSComponent) hdp220).doSupportSequenceFileShortType());
        assertFalse(((MRComponent) hdp220).isExecutedThroughWebHCat());
        assertTrue(((MRComponent) hdp220).doSupportCrossPlatformSubmission());
        assertTrue(((MRComponent) hdp220).doSupportImpersonation());
        assertEquals(((MRComponent) hdp220).getYarnApplicationClasspath(), YARN_APPLICATION_CLASSPATH);
        assertTrue(((HBaseComponent) hdp220).doSupportNewHBaseAPI());
        assertTrue(((SqoopComponent) hdp220).doJavaAPISupportStorePasswordInFile());
        assertTrue(((SqoopComponent) hdp220).doJavaAPISqoopImportSupportDeleteTargetDir());
        assertTrue(((SqoopComponent) hdp220).doJavaAPISqoopImportAllTablesSupportExcludeTable());
        assertTrue(((PigComponent) hdp220).doSupportHCatalog());
        assertFalse(((PigComponent) hdp220).pigVersionPriorTo_0_12());
        assertTrue(((PigComponent) hdp220).doSupportHBase());
        assertTrue(((PigComponent) hdp220).doSupportTezForPig());
        assertTrue(((HiveComponent) hdp220).doSupportEmbeddedMode());
        assertTrue(((HiveComponent) hdp220).doSupportStandaloneMode());
        assertTrue(((HiveComponent) hdp220).doSupportHive1());
        assertTrue(((HiveComponent) hdp220).doSupportHive2());
        assertTrue(((HiveComponent) hdp220).doSupportTezForHive());
        assertTrue(((HiveComponent) hdp220).doSupportHBaseForHive());
        assertTrue(((HiveComponent) hdp220).doSupportSSL());
        assertTrue(((HiveComponent) hdp220).doSupportORCFormat());
        assertTrue(((HiveComponent) hdp220).doSupportAvroFormat());
        assertTrue(((HiveComponent) hdp220).doSupportParquetFormat());
        assertFalse(((HiveComponent) hdp220).doSupportStoreAsParquet());
        assertFalse(((HiveComponent) hdp220).doSupportClouderaNavigator());

        assertFalse(hdp220 instanceof SparkBatchComponent);
        assertFalse(hdp220 instanceof SparkStreamingComponent);
        assertTrue(hdp220 instanceof HCatalogComponent);
        assertFalse(hdp220 instanceof ImpalaComponent);
    }

}
