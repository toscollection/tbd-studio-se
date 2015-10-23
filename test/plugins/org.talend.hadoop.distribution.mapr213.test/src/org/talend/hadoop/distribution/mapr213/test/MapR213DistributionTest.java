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
package org.talend.hadoop.distribution.mapr213.test;

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
import org.talend.hadoop.distribution.mapr213.MapR213Distribution;

/**
 * Test class for the {@link MapR213Distribution} distribution.
 *
 */
public class MapR213DistributionTest {

    private final static String EMPTY = ""; //$NON-NLS-1$

    @Test
    public void testMapR213Distribution() throws Exception {
        HadoopComponent mapr213 = new MapR213Distribution();
        assertNotNull(mapr213.getDistributionName());
        assertNotNull(mapr213.getVersionName(null));
        assertEquals(EHadoopDistributions.MAPR.getName(), mapr213.getDistribution());
        assertEquals(EHadoopVersion4Drivers.MAPR213.getVersionValue(), mapr213.getVersion());
        assertEquals(EHadoopVersion.HADOOP_1, mapr213.getHadoopVersion());
        assertFalse(mapr213.doSupportKerberos());
        assertFalse(mapr213.doSupportUseDatanodeHostname());
        assertTrue(mapr213.doSupportGroup());
        assertFalse(((HDFSComponent) mapr213).doSupportSequenceFileShortType());
        assertFalse(((MRComponent) mapr213).isExecutedThroughWebHCat());
        assertFalse(((MRComponent) mapr213).doSupportCrossPlatformSubmission());
        assertFalse(((MRComponent) mapr213).doSupportImpersonation());
        assertEquals(EMPTY, ((MRComponent) mapr213).getYarnApplicationClasspath());
        assertFalse(((HBaseComponent) mapr213).doSupportNewHBaseAPI());
        assertFalse(((SqoopComponent) mapr213).doJavaAPISupportStorePasswordInFile());
        assertFalse(((SqoopComponent) mapr213).doJavaAPISqoopImportSupportDeleteTargetDir());
        assertFalse(((SqoopComponent) mapr213).doJavaAPISqoopImportAllTablesSupportExcludeTable());
        assertFalse(((PigComponent) mapr213).doSupportHCatalog());
        assertFalse(((PigComponent) mapr213).pigVersionPriorTo_0_12());
        assertTrue(((PigComponent) mapr213).doSupportHBase());
        assertFalse(((PigComponent) mapr213).doSupportTezForPig());
        assertTrue(((HiveComponent) mapr213).doSupportEmbeddedMode());
        assertTrue(((HiveComponent) mapr213).doSupportStandaloneMode());
        assertTrue(((HiveComponent) mapr213).doSupportHive1());
        assertTrue(((HiveComponent) mapr213).doSupportHive2());
        assertFalse(((HiveComponent) mapr213).doSupportTezForHive());
        assertTrue(((HiveComponent) mapr213).doSupportHBaseForHive());
        assertFalse(((HiveComponent) mapr213).doSupportSSL());
        assertTrue(((HiveComponent) mapr213).doSupportORCFormat());
        assertTrue(((HiveComponent) mapr213).doSupportAvroFormat());
        assertTrue(((HiveComponent) mapr213).doSupportParquetFormat());
        assertFalse(((HiveComponent) mapr213).doSupportStoreAsParquet());
        assertFalse(((HiveComponent) mapr213).doSupportClouderaNavigator());
        assertFalse(mapr213 instanceof SparkBatchComponent);
        assertFalse(mapr213 instanceof SparkStreamingComponent);
        assertFalse(mapr213 instanceof HCatalogComponent);
        assertFalse(mapr213 instanceof ImpalaComponent);
    }

}
