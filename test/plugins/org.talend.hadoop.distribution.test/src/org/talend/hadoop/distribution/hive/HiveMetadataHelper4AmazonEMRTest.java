// ============================================================================
//
// Copyright (C) 2006-2016 Talend Inc. - www.talend.com
//
// This source code is available under agreement available at
// %InstallDIR%\features\org.talend.rcp.branding.%PRODUCTNAME%\%PRODUCTNAME%license.txt
//
// You should have received a copy of the agreement
// along with this program; if not, write to Talend SA
// 9 rue Pages 92150 Suresnes, France
//
// ============================================================================
package org.talend.hadoop.distribution.hive;

import org.junit.Test;
import org.talend.hadoop.distribution.constants.emr.IAmazonEMRDistribution;

/**
 * DOC ggu class global comment. Detailled comment
 */
public class HiveMetadataHelper4AmazonEMRTest extends AbstractTest4HiveMetadataHelper {

    @Override
    protected String getDistribution() {
        return IAmazonEMRDistribution.DISTRIBUTION_NAME;
    }

    @Override
    protected String getDistributionDisplay() {
        return IAmazonEMRDistribution.DISTRIBUTION_DISPLAY_NAME;
    }

    @Override
    protected String[] getDistributionVersionsDisplay() {
        return new String[] { IAmazonEMRDistribution.VERSION_400_HIVE_DISPLAY,
                IAmazonEMRDistribution.VERSION_APACHE_240_HIVE_0131_DISPLAY,
                IAmazonEMRDistribution.VERSION_APACHE_240_HIVE_DISPLAY, IAmazonEMRDistribution.VERSION_APACHE_103_HIVE_DISPLAY };
    }

    @Test
    public void testHiveMode_EMR400_Standalone_Only() {
        doTestGetHiveModesDisplay(IAmazonEMRDistribution.VERSION_400, HIVE_MODE_DISPLAY_STANDALONE_ONLY);
    }

    @Test
    public void testHiveServer_EMR400_Server2_Only() {
        doTestGetHiveServersDisplay(IAmazonEMRDistribution.VERSION_400, HIVE_SERVER_DISPLAY_SERVER2_ONLY);
    }

    @Test
    public void testHiveMode_EMRApache103_WithAll() {
        doTestGetHiveModesDisplay(IAmazonEMRDistribution.VERSION_APACHE_103, HIVE_MODE_DISPLAY_ALL);
    }

    @Test
    public void testHiveServer_EMRApache103_Server1_Only() {
        doTestGetHiveServersDisplay(IAmazonEMRDistribution.VERSION_APACHE_103, HIVE_SERVER_DISPLAY_SERVER1_ONLY);
    }

    @Test
    public void testHiveMode_EMRApache240Hive0131_Standalone_Only() {
        doTestGetHiveModesDisplay(IAmazonEMRDistribution.VERSION_APACHE_240_HIVE_0131, HIVE_MODE_DISPLAY_STANDALONE_ONLY);
    }

    @Test
    public void testHiveServer_EMRApache240Hive0131_Server2_Only() {
        doTestGetHiveServersDisplay(IAmazonEMRDistribution.VERSION_APACHE_240_HIVE_0131, HIVE_SERVER_DISPLAY_SERVER2_ONLY);
    }

    @Test
    public void testHiveMode_EMRApache240_WithAll() {
        doTestGetHiveModesDisplay(IAmazonEMRDistribution.VERSION_APACHE_240, HIVE_MODE_DISPLAY_ALL);
    }

    @Test
    public void testHiveServer_EMRApache240_Server2_Only() {
        doTestGetHiveServersDisplay(IAmazonEMRDistribution.VERSION_APACHE_240, HIVE_SERVER_DISPLAY_SERVER2_ONLY);
    }

}
