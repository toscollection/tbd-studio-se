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
package org.talend.hadoop.distribution.mapr212.test;

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
import org.talend.hadoop.distribution.mapr212.MapR212Distribution;

/**
 * Test class for the {@link MapR212Distribution} distribution.
 *
 */
public class MapR212DistributionTest {

    private final static String EMPTY = ""; //$NON-NLS-1$

    @Test
    public void testMapR212Distribution() throws Exception {
        HadoopComponent mapr212 = new MapR212Distribution();
        assertNotNull(mapr212.getDistributionName());
        assertNotNull(mapr212.getVersionName(null));
        assertEquals(EHadoopDistributions.MAPR.getName(), mapr212.getDistribution());
        assertEquals(EHadoopVersion4Drivers.MAPR212.getVersionValue(), mapr212.getVersion());
        assertEquals(EHadoopVersion.HADOOP_1, mapr212.getHadoopVersion());
        assertFalse(mapr212.doSupportKerberos());
        assertFalse(mapr212.doSupportUseDatanodeHostname());
        assertTrue(mapr212.doSupportGroup());
        assertFalse(((HDFSComponent) mapr212).doSupportSequenceFileShortType());
        assertFalse(((MRComponent) mapr212).isExecutedThroughWebHCat());
        assertFalse(((MRComponent) mapr212).doSupportCrossPlatformSubmission());
        assertFalse(((MRComponent) mapr212).doSupportImpersonation());
        assertEquals(EMPTY, ((MRComponent) mapr212).getYarnApplicationClasspath());
        assertFalse(((HBaseComponent) mapr212).doSupportNewHBaseAPI());
        assertFalse(((SqoopComponent) mapr212).doJavaAPISupportStorePasswordInFile());
        assertFalse(((SqoopComponent) mapr212).doJavaAPISqoopImportSupportDeleteTargetDir());
        assertFalse(((SqoopComponent) mapr212).doJavaAPISqoopImportAllTablesSupportExcludeTable());
        assertFalse(((PigComponent) mapr212).doSupportHCatalog());
        assertFalse(((PigComponent) mapr212).pigVersionPriorTo_0_12());
        assertTrue(((PigComponent) mapr212).doSupportHBase());
        assertFalse(((PigComponent) mapr212).doSupportTezForPig());
        assertTrue(((HiveComponent) mapr212).doSupportEmbeddedMode());
        assertTrue(((HiveComponent) mapr212).doSupportStandaloneMode());
        assertTrue(((HiveComponent) mapr212).doSupportHive1());
        assertFalse(((HiveComponent) mapr212).doSupportHive2());
        assertFalse(((HiveComponent) mapr212).doSupportTezForHive());
        assertTrue(((HiveComponent) mapr212).doSupportHBaseForHive());
        assertFalse(((HiveComponent) mapr212).doSupportSSL());
        assertFalse(((HiveComponent) mapr212).doSupportORCFormat());
        assertTrue(((HiveComponent) mapr212).doSupportAvroFormat());
        assertTrue(((HiveComponent) mapr212).doSupportParquetFormat());

        assertFalse(((HiveComponent) mapr212).doSupportStoreAsParquet());
        assertFalse(((HiveComponent) mapr212).doSupportClouderaNavigator());
        assertFalse(mapr212 instanceof SparkBatchComponent);
        assertFalse(mapr212 instanceof SparkStreamingComponent);
        assertFalse(mapr212 instanceof HCatalogComponent);
        assertFalse(mapr212 instanceof ImpalaComponent);
    }

}
