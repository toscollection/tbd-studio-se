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
package org.talend.hadoop.distribution.emr103.test;

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
import org.talend.hadoop.distribution.emr103.EMRApache103Distribution;

/**
 * Test class for the {@link EMRApache103Distribution} distribution.
 *
 */
public class EMRApache103DistributionTest {

    private static final String EMPTY = ""; //$NON-NLS-1$

    @Test
    public void testEMRApache103Distribution() throws Exception {
        HadoopComponent emr103 = new EMRApache103Distribution();
        assertNotNull(emr103.getDistributionName());
        assertNotNull(emr103.getVersionName(null));
        assertEquals(EHadoopDistributions.AMAZON_EMR.getName(), emr103.getDistribution());
        assertEquals(EHadoopVersion4Drivers.APACHE_1_0_3_EMR.getVersionValue(), emr103.getVersion());
        assertEquals(EHadoopVersion.HADOOP_1, emr103.getHadoopVersion());
        assertTrue(emr103.doSupportKerberos());
        assertFalse(emr103.doSupportUseDatanodeHostname());
        assertFalse(emr103.doSupportGroup());
        assertFalse(((HDFSComponent) emr103).doSupportSequenceFileShortType());
        assertFalse(((MRComponent) emr103).isExecutedThroughWebHCat());
        assertFalse(((MRComponent) emr103).doSupportCrossPlatformSubmission());
        assertFalse(((MRComponent) emr103).doSupportImpersonation());
        assertEquals(EMPTY, ((MRComponent) emr103).getYarnApplicationClasspath());
        assertFalse(((HBaseComponent) emr103).doSupportNewHBaseAPI());
        assertFalse(emr103 instanceof SqoopComponent);
        assertFalse(((PigComponent) emr103).doSupportHCatalog());
        assertFalse(((PigComponent) emr103).pigVersionPriorTo_0_12());
        assertTrue(((PigComponent) emr103).doSupportHBase());
        assertFalse(((PigComponent) emr103).doSupportTezForPig());
        assertTrue(((HiveComponent) emr103).doSupportEmbeddedMode());
        assertTrue(((HiveComponent) emr103).doSupportStandaloneMode());
        assertTrue(((HiveComponent) emr103).doSupportHive1());
        assertFalse(((HiveComponent) emr103).doSupportHive2());
        assertFalse(((HiveComponent) emr103).doSupportTezForHive());
        assertTrue(((HiveComponent) emr103).doSupportHBaseForHive());
        assertFalse(((HiveComponent) emr103).doSupportSSL());
        assertFalse(((HiveComponent) emr103).doSupportORCFormat());
        assertFalse(((HiveComponent) emr103).doSupportAvroFormat());
        assertTrue(((HiveComponent) emr103).doSupportParquetFormat());
        assertFalse(((HiveComponent) emr103).doSupportStoreAsParquet());
        assertFalse(((HiveComponent) emr103).doSupportClouderaNavigator());
        assertFalse(emr103 instanceof SparkBatchComponent);
        assertFalse(emr103 instanceof SparkStreamingComponent);
        assertFalse(emr103 instanceof HCatalogComponent);
        assertFalse(emr103 instanceof ImpalaComponent);
    }

}
