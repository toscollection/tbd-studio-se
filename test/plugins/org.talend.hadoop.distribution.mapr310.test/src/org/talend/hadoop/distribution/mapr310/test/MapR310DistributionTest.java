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
package org.talend.hadoop.distribution.mapr310.test;

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
import org.talend.hadoop.distribution.mapr310.MapR310Distribution;

/**
 * Test class for the {@link MapR310Distribution} distribution.
 *
 */
public class MapR310DistributionTest {

    private final static String EMPTY = ""; //$NON-NLS-1$

    @Test
    public void testMapR310Distribution() throws Exception {
        HadoopComponent mapr310 = new MapR310Distribution();
        assertNotNull(mapr310.getDistributionName());
        assertNotNull(mapr310.getVersionName(null));
        assertEquals(EHadoopDistributions.MAPR.getName(), mapr310.getDistribution());
        assertEquals(EHadoopVersion4Drivers.MAPR310.getVersionValue(), mapr310.getVersion());
        assertEquals(EHadoopVersion.HADOOP_1, mapr310.getHadoopVersion());
        assertFalse(mapr310.doSupportKerberos());
        assertFalse(mapr310.doSupportUseDatanodeHostname());
        assertTrue(mapr310.doSupportGroup());
        assertFalse(((HDFSComponent) mapr310).doSupportSequenceFileShortType());
        assertFalse(((MRComponent) mapr310).isExecutedThroughWebHCat());
        assertFalse(((MRComponent) mapr310).doSupportCrossPlatformSubmission());
        assertFalse(((MRComponent) mapr310).doSupportImpersonation());
        assertEquals(EMPTY, ((MRComponent) mapr310).getYarnApplicationClasspath());
        assertFalse(((HBaseComponent) mapr310).doSupportNewHBaseAPI());
        assertFalse(((SqoopComponent) mapr310).doJavaAPISupportStorePasswordInFile());
        assertFalse(((SqoopComponent) mapr310).doJavaAPISqoopImportSupportDeleteTargetDir());
        assertFalse(((SqoopComponent) mapr310).doJavaAPISqoopImportAllTablesSupportExcludeTable());
        assertTrue(((PigComponent) mapr310).doSupportHCatalog());
        assertTrue(((PigComponent) mapr310).pigVersionPriorTo_0_12());
        assertTrue(((PigComponent) mapr310).doSupportHBase());
        assertFalse(((PigComponent) mapr310).doSupportTezForPig());
        assertTrue(((HiveComponent) mapr310).doSupportEmbeddedMode());
        assertTrue(((HiveComponent) mapr310).doSupportStandaloneMode());
        assertTrue(((HiveComponent) mapr310).doSupportHive1());
        assertTrue(((HiveComponent) mapr310).doSupportHive2());
        assertFalse(((HiveComponent) mapr310).doSupportTezForHive());
        assertTrue(((HiveComponent) mapr310).doSupportHBaseForHive());
        assertFalse(((HiveComponent) mapr310).doSupportSSL());
        assertFalse(((HiveComponent) mapr310).doSupportORCFormat());
        assertFalse(((HiveComponent) mapr310).doSupportAvroFormat());
        assertTrue(((HiveComponent) mapr310).doSupportParquetFormat());
        assertFalse(mapr310 instanceof SparkBatchComponent);
        assertFalse(mapr310 instanceof SparkStreamingComponent);
        assertTrue(mapr310 instanceof HCatalogComponent);
        assertFalse(mapr310 instanceof ImpalaComponent);
    }

}
