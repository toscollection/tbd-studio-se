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
    public void testHiveMode_MapR200_WitAll() {
        doTestGetHiveModesDisplay(IMapRDistribution.VERSION_200, HIVE_MODE_DISPLAY_ALL);
    }

    @Test
    public void testHiveServer_MapR200_Server1_Only() {
        doTestGetHiveServersDisplay(IMapRDistribution.VERSION_200, HIVE_SERVER_DISPLAY_SERVER1_ONLY);
    }

    @Test
    public void testHiveMode_MapR212_WitAll() {
        doTestGetHiveModesDisplay(IMapRDistribution.VERSION_212, HIVE_MODE_DISPLAY_ALL);
    }

    @Test
    public void testHiveServer_MapR212_Server1_Only() {
        doTestGetHiveServersDisplay(IMapRDistribution.VERSION_212, HIVE_SERVER_DISPLAY_SERVER1_ONLY);
    }

    @Test
    public void testHiveMode_MapR213_WitAll() {
        doTestGetHiveModesDisplay(IMapRDistribution.VERSION_213, HIVE_MODE_DISPLAY_ALL);
    }

    @Test
    public void testHiveServer_MapR213_WithAll() {
        doTestGetHiveServersDisplay(IMapRDistribution.VERSION_213, HIVE_SERVER_DISPLAY_ALL);
    }

    @Test
    public void testHiveMode_MapR301_WitAll() {
        doTestGetHiveModesDisplay(IMapRDistribution.VERSION_301, HIVE_MODE_DISPLAY_ALL);
    }

    @Test
    public void testHiveServer_MapR301_WithAll() {
        doTestGetHiveServersDisplay(IMapRDistribution.VERSION_301, HIVE_SERVER_DISPLAY_ALL);
    }

    @Test
    public void testHiveMode_MapR310_WitAll() {
        doTestGetHiveModesDisplay(IMapRDistribution.VERSION_310, HIVE_MODE_DISPLAY_ALL);
    }

    @Test
    public void testHiveServer_MapR310_WithAll() {
        doTestGetHiveServersDisplay(IMapRDistribution.VERSION_310, HIVE_SERVER_DISPLAY_ALL);
    }

    @Test
    public void testHiveMode_MapR401_WitAll() {
        doTestGetHiveModesDisplay(IMapRDistribution.VERSION_401, HIVE_MODE_DISPLAY_ALL);
    }

    @Test
    public void testHiveServer_MapR401_WithAll() {
        doTestGetHiveServersDisplay(IMapRDistribution.VERSION_401, HIVE_SERVER_DISPLAY_ALL);
    }

    @Test
    public void testHiveMode_MapR410_WitAll() {
        doTestGetHiveModesDisplay(IMapRDistribution.VERSION_410, HIVE_MODE_DISPLAY_ALL);
    }

    @Test
    public void testHiveServer_MapR410_Server2_Only() {
        doTestGetHiveServersDisplay(IMapRDistribution.VERSION_410, HIVE_SERVER_DISPLAY_SERVER2_ONLY);
    }

    @Test
    public void testHiveMode_MapR500_Standalone_Only() {
        doTestGetHiveModesDisplay(IMapRDistribution.VERSION_500, HIVE_MODE_DISPLAY_STANDALONE_ONLY);
    }

    @Test
    public void testHiveServer_MapR500_Server2_Only() {
        doTestGetHiveServersDisplay(IMapRDistribution.VERSION_500, HIVE_SERVER_DISPLAY_SERVER2_ONLY);
    }
}
