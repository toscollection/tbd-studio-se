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
import org.talend.hadoop.distribution.constants.mapr.IMapRDistribution;

/**
 * DOC ggu class global comment. Detailled comment
 */
public class HiveMetadataHelper4MapRTest extends AbstractTest4HiveMetadataHelper {

    @Override
    protected String getDistribution() {
        return IMapRDistribution.DISTRIBUTION_NAME;
    }

    @Override
    protected String getDistributionDisplay() {
        return IMapRDistribution.DISTRIBUTION_DISPLAY_NAME;
    }

    @Override
    protected String[] getDistributionVersionsDisplay() {
        return new String[] { IMapRDistribution.VERSION_500_DISPLAY, IMapRDistribution.VERSION_410_DISPLAY,
                IMapRDistribution.VERSION_401_DISPLAY, IMapRDistribution.VERSION_310_DISPLAY,
                IMapRDistribution.VERSION_301_DISPLAY, IMapRDistribution.VERSION_213_DISPLAY,
                IMapRDistribution.VERSION_212_DISPLAY, IMapRDistribution.VERSION_200_DISPLAY };
    }

    @Test
    public void testHiveMode_MapR200() {
        doTestGetHiveModesDisplay(IMapRDistribution.VERSION_200, HIVE_MODE_DISPLAY_ALL);
    }

    @Test
    public void testHiveServer_MapR200() {
        doTestGetHiveServersDisplay(IMapRDistribution.VERSION_200, HiveModeInfo.EMBEDDED.getName(),
                HIVE_SERVER_DISPLAY_SERVER1_ONLY);
        doTestGetHiveServersDisplay(IMapRDistribution.VERSION_200, HiveModeInfo.STANDALONE.getName(),
                HIVE_SERVER_DISPLAY_SERVER1_ONLY);
    }

    @Test
    public void testHiveMode_MapR212() {
        doTestGetHiveModesDisplay(IMapRDistribution.VERSION_212, HIVE_MODE_DISPLAY_ALL);
    }

    @Test
    public void testHiveServer_MapR212() {
        doTestGetHiveServersDisplay(IMapRDistribution.VERSION_212, HiveModeInfo.EMBEDDED.getName(),
                HIVE_SERVER_DISPLAY_SERVER1_ONLY);
        doTestGetHiveServersDisplay(IMapRDistribution.VERSION_212, HiveModeInfo.STANDALONE.getName(),
                HIVE_SERVER_DISPLAY_SERVER1_ONLY);
    }

    @Test
    public void testHiveMode_MapR213() {
        doTestGetHiveModesDisplay(IMapRDistribution.VERSION_213, HIVE_MODE_DISPLAY_ALL);
    }

    @Test
    public void testHiveServer_MapR213() {
        doTestGetHiveServersDisplay(IMapRDistribution.VERSION_213, HiveModeInfo.EMBEDDED.getName(), HIVE_SERVER_DISPLAY_ALL);
        doTestGetHiveServersDisplay(IMapRDistribution.VERSION_213, HiveModeInfo.STANDALONE.getName(), HIVE_SERVER_DISPLAY_ALL);
    }

    @Test
    public void testHiveMode_MapR301() {
        doTestGetHiveModesDisplay(IMapRDistribution.VERSION_301, HIVE_MODE_DISPLAY_ALL);
    }

    @Test
    public void testHiveServer_MapR301() {
        doTestGetHiveServersDisplay(IMapRDistribution.VERSION_301, HiveModeInfo.EMBEDDED.getName(), HIVE_SERVER_DISPLAY_ALL);
        doTestGetHiveServersDisplay(IMapRDistribution.VERSION_301, HiveModeInfo.STANDALONE.getName(), HIVE_SERVER_DISPLAY_ALL);
    }

    @Test
    public void testHiveMode_MapR310() {
        doTestGetHiveModesDisplay(IMapRDistribution.VERSION_310, HIVE_MODE_DISPLAY_ALL);
    }

    @Test
    public void testHiveServer_MapR310() {
        doTestGetHiveServersDisplay(IMapRDistribution.VERSION_310, HiveModeInfo.EMBEDDED.getName(), HIVE_SERVER_DISPLAY_ALL);
        doTestGetHiveServersDisplay(IMapRDistribution.VERSION_310, HiveModeInfo.STANDALONE.getName(), HIVE_SERVER_DISPLAY_ALL);
    }

    @Test
    public void testHiveMode_MapR401() {
        doTestGetHiveModesDisplay(IMapRDistribution.VERSION_401, HIVE_MODE_DISPLAY_ALL);
    }

    @Test
    public void testHiveServer_MapR401() {
        doTestGetHiveServersDisplay(IMapRDistribution.VERSION_401, HiveModeInfo.EMBEDDED.getName(), HIVE_SERVER_DISPLAY_ALL);
        doTestGetHiveServersDisplay(IMapRDistribution.VERSION_401, HiveModeInfo.STANDALONE.getName(), HIVE_SERVER_DISPLAY_ALL);
    }

    @Test
    public void testHiveMode_MapR410() {
        doTestGetHiveModesDisplay(IMapRDistribution.VERSION_410, HIVE_MODE_DISPLAY_ALL);
    }

    @Test
    public void testHiveServer_MapR410() {
        doTestGetHiveServersDisplay(IMapRDistribution.VERSION_410, HiveModeInfo.EMBEDDED.getName(),
                HIVE_SERVER_DISPLAY_SERVER2_ONLY);
        doTestGetHiveServersDisplay(IMapRDistribution.VERSION_410, HiveModeInfo.STANDALONE.getName(),
                HIVE_SERVER_DISPLAY_SERVER2_ONLY);
    }

    @Test
    public void testHiveMode_MapR500() {
        doTestGetHiveModesDisplay(IMapRDistribution.VERSION_500, HIVE_MODE_DISPLAY_STANDALONE_ONLY);
    }

    @Test
    public void testHiveServer_MapR500() {
        doTestGetHiveServersDisplay(IMapRDistribution.VERSION_500, HiveModeInfo.EMBEDDED.getName(),
                HIVE_SERVER_DISPLAY_SERVER2_ONLY);
        doTestGetHiveServersDisplay(IMapRDistribution.VERSION_500, HiveModeInfo.STANDALONE.getName(),
                HIVE_SERVER_DISPLAY_SERVER2_ONLY);
    }
}
