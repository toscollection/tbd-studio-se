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
import org.talend.core.model.metadata.connection.hive.HiveModeInfo;
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

    @Test
    public void testHiveMode_EMR400() {
        doTestGetHiveModesDisplay(IAmazonEMRDistribution.VERSION_400, HIVE_MODE_DISPLAY_STANDALONE_ONLY);
    }

    @Test
    public void testHiveServer_EMR400() {
        doTestGetHiveServersDisplay(IAmazonEMRDistribution.VERSION_400, HiveModeInfo.EMBEDDED.getName(),
                HIVE_SERVER_DISPLAY_SERVER2_ONLY);
        doTestGetHiveServersDisplay(IAmazonEMRDistribution.VERSION_400, HiveModeInfo.STANDALONE.getName(),
                HIVE_SERVER_DISPLAY_SERVER2_ONLY);
    }

    @Test
    public void testHiveMode_EMRApache103() {
        doTestGetHiveModesDisplay(IAmazonEMRDistribution.VERSION_APACHE_103, HIVE_MODE_DISPLAY_ALL);
    }

    @Test
    public void testHiveServer_EMRApache103() {
        // TODO not sure.
        String[] serverArr = new String[] { "Not sure" };
        doTestGetHiveServersDisplay(IAmazonEMRDistribution.VERSION_APACHE_103, HiveModeInfo.EMBEDDED.getName(), serverArr);
        doTestGetHiveServersDisplay(IAmazonEMRDistribution.VERSION_APACHE_103, HiveModeInfo.STANDALONE.getName(), serverArr);
    }

    @Test
    public void testHiveMode_EMRApache240Hive0131() {
        doTestGetHiveModesDisplay(IAmazonEMRDistribution.VERSION_APACHE_240_HIVE_0131, HIVE_MODE_DISPLAY_STANDALONE_ONLY);
    }

    @Test
    public void testHiveServer_EMRApache240Hive0131() {
        // TODO not sure.
        String[] serverArr = new String[] { "Not sure" };
        doTestGetHiveServersDisplay(IAmazonEMRDistribution.VERSION_APACHE_240_HIVE_0131, HiveModeInfo.EMBEDDED.getName(),
                serverArr);
        doTestGetHiveServersDisplay(IAmazonEMRDistribution.VERSION_APACHE_240_HIVE_0131, HiveModeInfo.STANDALONE.getName(),
                serverArr);
    }

    @Test
    public void testHiveMode_EMRApache240() {
        doTestGetHiveModesDisplay(IAmazonEMRDistribution.VERSION_APACHE_240_HIVE_0131, HIVE_MODE_DISPLAY_ALL);
    }

    @Test
    public void testHiveServer_EMRApache240() {
        // TODO not sure.
        String[] serverArr = new String[] { "Not sure" };
        doTestGetHiveServersDisplay(IAmazonEMRDistribution.VERSION_APACHE_240, HiveModeInfo.EMBEDDED.getName(), serverArr);
        doTestGetHiveServersDisplay(IAmazonEMRDistribution.VERSION_APACHE_240, HiveModeInfo.STANDALONE.getName(), serverArr);
    }

}
