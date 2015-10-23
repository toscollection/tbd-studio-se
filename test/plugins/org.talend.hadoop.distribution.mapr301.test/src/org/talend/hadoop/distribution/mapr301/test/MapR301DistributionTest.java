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
package org.talend.hadoop.distribution.mapr301.test;

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
import org.talend.hadoop.distribution.mapr301.MapR301Distribution;

/**
 * Test class for the {@link MapR301Distribution} distribution.
 *
 */
public class MapR301DistributionTest {

    private final static String EMPTY = ""; //$NON-NLS-1$

    @Test
    public void testMapR301Distribution() throws Exception {
        HadoopComponent mapr301 = new MapR301Distribution();
        assertNotNull(mapr301.getDistributionName());
        assertNotNull(mapr301.getVersionName(null));
        assertEquals(EHadoopDistributions.MAPR.getName(), mapr301.getDistribution());
        assertEquals(EHadoopVersion4Drivers.MAPR301.getVersionValue(), mapr301.getVersion());
        assertEquals(EHadoopVersion.HADOOP_1, mapr301.getHadoopVersion());
        assertFalse(mapr301.doSupportKerberos());
        assertFalse(mapr301.doSupportUseDatanodeHostname());
        assertTrue(mapr301.doSupportGroup());
        assertFalse(((HDFSComponent) mapr301).doSupportSequenceFileShortType());
        assertFalse(((MRComponent) mapr301).isExecutedThroughWebHCat());
        assertFalse(((MRComponent) mapr301).doSupportCrossPlatformSubmission());
        assertFalse(((MRComponent) mapr301).doSupportImpersonation());
        assertEquals(EMPTY, ((MRComponent) mapr301).getYarnApplicationClasspath());
        assertFalse(((HBaseComponent) mapr301).doSupportNewHBaseAPI());
        assertFalse(((SqoopComponent) mapr301).doJavaAPISupportStorePasswordInFile());
        assertFalse(((SqoopComponent) mapr301).doJavaAPISqoopImportSupportDeleteTargetDir());
        assertFalse(((SqoopComponent) mapr301).doJavaAPISqoopImportAllTablesSupportExcludeTable());
        assertFalse(((PigComponent) mapr301).doSupportHCatalog());
        assertFalse(((PigComponent) mapr301).pigVersionPriorTo_0_12());
        assertFalse(((PigComponent) mapr301).doSupportTezForPig());
        assertTrue(((PigComponent) mapr301).doSupportHBase());
        assertTrue(((HiveComponent) mapr301).doSupportEmbeddedMode());
        assertTrue(((HiveComponent) mapr301).doSupportStandaloneMode());
        assertTrue(((HiveComponent) mapr301).doSupportHive1());
        assertTrue(((HiveComponent) mapr301).doSupportHive2());
        assertFalse(((HiveComponent) mapr301).doSupportTezForHive());
        assertTrue(((HiveComponent) mapr301).doSupportHBaseForHive());
        assertFalse(((HiveComponent) mapr301).doSupportSSL());
        assertFalse(((HiveComponent) mapr301).doSupportORCFormat());
        assertFalse(((HiveComponent) mapr301).doSupportAvroFormat());
        assertTrue(((HiveComponent) mapr301).doSupportParquetFormat());
        assertFalse(((HiveComponent) mapr301).doSupportStoreAsParquet());
        assertFalse(((HiveComponent) mapr301).doSupportClouderaNavigator());
        assertFalse(mapr301 instanceof SparkBatchComponent);
        assertFalse(mapr301 instanceof SparkStreamingComponent);
        assertFalse(mapr301 instanceof HCatalogComponent);
        assertFalse(mapr301 instanceof ImpalaComponent);
    }

}
