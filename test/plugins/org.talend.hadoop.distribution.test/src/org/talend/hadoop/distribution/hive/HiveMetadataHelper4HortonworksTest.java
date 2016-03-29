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
import org.talend.core.model.metadata.connection.hive.HiveServerVersionInfo;
import org.talend.hadoop.distribution.constants.hdp.IHortonworksDistribution;

/**
 * DOC ggu class global comment. Detailled comment
 */
public class HiveMetadataHelper4HortonworksTest extends AbstractTest4HiveMetadataHelper {

    @Override
    protected String getDistribution() {
        return IHortonworksDistribution.DISTRIBUTION_NAME;
    }

    @Override
    protected String getDistributionDisplay() {
        return IHortonworksDistribution.DISTRIBUTION_DISPLAY_NAME;
    }

    @Test
    public void testHiveMode_HDP120() {
        doTestGetHiveModesDisplay(IHortonworksDistribution.VERSION_12, HIVE_MODE_DISPLAY_EMBEDDED_ONLY);
    }

    @Test
    public void testHiveServer_HDP120() {
        doTestGetHiveServersDisplay(IHortonworksDistribution.VERSION_12, HiveModeInfo.EMBEDDED.getName(), HIVE_SERVER_DISPLAY_ALL);
        doTestGetHiveServersDisplay(IHortonworksDistribution.VERSION_12, HiveModeInfo.STANDALONE.getName(),
                HIVE_SERVER_DISPLAY_ALL);
    }

    @Test
    public void testHiveMode_HDP130() {
        doTestGetHiveModesDisplay(IHortonworksDistribution.VERSION_13, HIVE_MODE_DISPLAY_EMBEDDED_ONLY);
    }

    @Test
    public void testHiveServer_HDP130() {
        doTestGetHiveServersDisplay(IHortonworksDistribution.VERSION_13, HiveModeInfo.EMBEDDED.getName(), HIVE_SERVER_DISPLAY_ALL);
        doTestGetHiveServersDisplay(IHortonworksDistribution.VERSION_13, HiveModeInfo.STANDALONE.getName(),
                HIVE_SERVER_DISPLAY_ALL);
    }

    @Test
    public void testHiveMode_HDP200() {
        doTestGetHiveModesDisplay(IHortonworksDistribution.VERSION_20, HIVE_MODE_DISPLAY_EMBEDDED_ONLY);
    }

    @Test
    public void testHiveServer_HDP200() {
        doTestGetHiveServersDisplay(IHortonworksDistribution.VERSION_20, HiveModeInfo.EMBEDDED.getName(), HIVE_SERVER_DISPLAY_ALL);
        doTestGetHiveServersDisplay(IHortonworksDistribution.VERSION_20, HiveModeInfo.STANDALONE.getName(),
                HIVE_SERVER_DISPLAY_ALL);
    }

    @Test
    public void testHiveMode_HDP210() {
        doTestGetHiveModesDisplay(IHortonworksDistribution.VERSION_21, HIVE_MODE_DISPLAY_ALL);
    }

    @Test
    public void testHiveServer_HDP210() {
        doTestGetHiveServersDisplay(IHortonworksDistribution.VERSION_21, HiveModeInfo.EMBEDDED.getName(), HIVE_SERVER_DISPLAY_ALL);
        doTestGetHiveServersDisplay(IHortonworksDistribution.VERSION_21, HiveModeInfo.STANDALONE.getName(),
                HIVE_SERVER_DISPLAY_ALL);
    }

    @Test
    public void testHiveMode_HDP220() {
        doTestGetHiveModesDisplay(IHortonworksDistribution.VERSION_22, HIVE_MODE_DISPLAY_ALL);
    }

    @Test
    public void testHiveServer_HDP220() {
        doTestGetHiveServersDisplay(IHortonworksDistribution.VERSION_22, HiveModeInfo.EMBEDDED.getName(), HIVE_SERVER_DISPLAY_ALL);
        doTestGetHiveServersDisplay(IHortonworksDistribution.VERSION_22, HiveModeInfo.STANDALONE.getName(),
                HIVE_SERVER_DISPLAY_ALL);
    }

    @Test
    public void testHiveMode_HDP230() {
        doTestGetHiveModesDisplay(IHortonworksDistribution.VERSION_23, HIVE_MODE_DISPLAY_STANDALONE_ONLY);
    }

    @Test
    public void testHiveServer_HDP230() {
        String[] hiveServer2Only = new String[] { HiveServerVersionInfo.HIVE_SERVER_2.getDisplayName() };
        doTestGetHiveServersDisplay(IHortonworksDistribution.VERSION_23, HiveModeInfo.EMBEDDED.getName(), hiveServer2Only);
        doTestGetHiveServersDisplay(IHortonworksDistribution.VERSION_23, HiveModeInfo.STANDALONE.getName(), hiveServer2Only);
    }
}
