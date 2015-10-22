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
package org.talend.hadoop.distribution.mapr200.test;

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
import org.talend.hadoop.distribution.mapr200.MapR200Distribution;

/**
 * Test class for the {@link MapR200Distribution} distribution.
 *
 */
public class MapR200DistributionTest {

    private final static String EMPTY = ""; //$NON-NLS-1$

    @Test
    public void testMapR200Distribution() throws Exception {
        HadoopComponent mapr200 = new MapR200Distribution();
        assertNotNull(mapr200.getDistributionName());
        assertNotNull(mapr200.getVersionName(null));
        assertEquals(EHadoopDistributions.MAPR.getName(), mapr200.getDistribution());
        assertEquals(EHadoopVersion4Drivers.MAPR2.getVersionValue(), mapr200.getVersion());
        assertEquals(EHadoopVersion.HADOOP_1, mapr200.getHadoopVersion());
        assertFalse(mapr200.doSupportKerberos());
        assertFalse(mapr200.doSupportUseDatanodeHostname());
        assertFalse(((HDFSComponent) mapr200).doSupportSequenceFileShortType());
        assertFalse(((MRComponent) mapr200).isExecutedThroughWebHCat());
        assertFalse(((MRComponent) mapr200).doSupportCrossPlatformSubmission());
        assertFalse(((MRComponent) mapr200).doSupportImpersonation());
        assertEquals(EMPTY, ((MRComponent) mapr200).getYarnApplicationClasspath());
        assertFalse(((HBaseComponent) mapr200).doSupportNewHBaseAPI());
        assertFalse(((SqoopComponent) mapr200).doJavaAPISupportStorePasswordInFile());
        assertFalse(((SqoopComponent) mapr200).doJavaAPISqoopImportSupportDeleteTargetDir());
        assertFalse(((SqoopComponent) mapr200).doJavaAPISqoopImportAllTablesSupportExcludeTable());
        assertFalse(((PigComponent) mapr200).doSupportHCatalog());
        assertFalse(((PigComponent) mapr200).pigVersionPriorTo_0_12());
        assertTrue(((PigComponent) mapr200).doSupportHBase());
        assertFalse(((PigComponent) mapr200).doSupportTezForPig());
        assertTrue(((HiveComponent) mapr200).doSupportEmbeddedMode());
        assertTrue(((HiveComponent) mapr200).doSupportStandaloneMode());
        assertTrue(((HiveComponent) mapr200).doSupportHive1());
        assertFalse(((HiveComponent) mapr200).doSupportHive2());
        assertFalse(((HiveComponent) mapr200).doSupportTezForHive());
        assertFalse(((HiveComponent) mapr200).doSupportHBaseForHive());
        assertFalse(((HiveComponent) mapr200).doSupportSSL());
        assertFalse(((HiveComponent) mapr200).doSupportORCFormat());
        assertFalse(((HiveComponent) mapr200).doSupportAvroFormat());
        assertFalse(((HiveComponent) mapr200).doSupportParquetFormat());
        assertFalse(mapr200 instanceof SparkBatchComponent);
        assertFalse(mapr200 instanceof SparkStreamingComponent);
        assertFalse(mapr200 instanceof HCatalogComponent);
        assertFalse(mapr200 instanceof ImpalaComponent);
    }

}
