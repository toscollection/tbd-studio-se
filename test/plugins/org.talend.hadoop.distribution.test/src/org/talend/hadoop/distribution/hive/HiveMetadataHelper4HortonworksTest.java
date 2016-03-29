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

import static org.junit.Assert.assertTrue;

import org.junit.Test;
import org.talend.commons.utils.platform.PluginChecker;
import org.talend.core.model.metadata.connection.hive.HiveModeInfo;
import org.talend.core.model.metadata.connection.hive.HiveServerVersionInfo;
import org.talend.core.runtime.hd.hive.HiveMetadataHelper;
import org.talend.hadoop.distribution.constants.hdp.IHortonworksDistribution;

/**
 * DOC ggu class global comment. Detailled comment
 */
public class HiveMetadataHelper4HortonworksTest extends AbstractTest4HiveMetadataHelper {

    private static final String NOT_SUPPORT_TOP_MESSAGE = "Shouldn't support for hive in TOS for DQ product";

    @Override
    protected String getDistribution() {
        return IHortonworksDistribution.DISTRIBUTION_NAME;
    }

    @Override
    protected String getDistributionDisplay() {
        return IHortonworksDistribution.DISTRIBUTION_DISPLAY_NAME;
    }

    @Override
    protected String[] getDistributionVersionsDisplay() {
        return new String[] { IHortonworksDistribution.VERSION_23_DISPLAY, IHortonworksDistribution.VERSION_22_DISPLAY,
                IHortonworksDistribution.VERSION_21_DISPLAY, IHortonworksDistribution.VERSION_20_DISPLAY,
                IHortonworksDistribution.VERSION_13_DISPLAY, IHortonworksDistribution.VERSION_12_DISPLAY, };
    }

    private boolean notSupportForTOP() {
        return PluginChecker.isOnlyTopLoaded();
    }

    @Test
    public void testHiveMode_HDP120() {
        if (notSupportForTOP()) {
            String[] hiveModesDisplay = HiveMetadataHelper.getHiveModesDisplay(getDistribution(),
                    IHortonworksDistribution.VERSION_12, false);
            assertTrue(NOT_SUPPORT_TOP_MESSAGE, hiveModesDisplay == null || hiveModesDisplay.length == 0);
        } else {
            doTestGetHiveModesDisplay(IHortonworksDistribution.VERSION_12, HIVE_MODE_DISPLAY_ALL);
        }
    }

    @Test
    public void testHiveServer_HDP120() {
        if (notSupportForTOP()) {
            String[] hiveServersDisplay = HiveMetadataHelper.getHiveServersDisplay(getDistribution(),
                    IHortonworksDistribution.VERSION_12, HiveModeInfo.EMBEDDED.getName(), false);
            assertTrue(NOT_SUPPORT_TOP_MESSAGE, hiveServersDisplay == null || hiveServersDisplay.length == 0);

            hiveServersDisplay = HiveMetadataHelper.getHiveServersDisplay(getDistribution(), IHortonworksDistribution.VERSION_12,
                    HiveModeInfo.STANDALONE.getName(), false);
            assertTrue(NOT_SUPPORT_TOP_MESSAGE, hiveServersDisplay == null || hiveServersDisplay.length == 0);
        } else {
            doTestGetHiveServersDisplay(IHortonworksDistribution.VERSION_12, HiveModeInfo.EMBEDDED.getName(),
                    HIVE_SERVER_DISPLAY_ALL);
            doTestGetHiveServersDisplay(IHortonworksDistribution.VERSION_12, HiveModeInfo.STANDALONE.getName(),
                    HIVE_SERVER_DISPLAY_ALL);
        }
    }

    @Test
    public void testHiveMode_HDP130() {
        if (notSupportForTOP()) {
            String[] hiveModesDisplay = HiveMetadataHelper.getHiveModesDisplay(getDistribution(),
                    IHortonworksDistribution.VERSION_13, false);
            assertTrue(NOT_SUPPORT_TOP_MESSAGE, hiveModesDisplay == null || hiveModesDisplay.length == 0);
        } else {
            doTestGetHiveModesDisplay(IHortonworksDistribution.VERSION_13, HIVE_MODE_DISPLAY_ALL);
        }
    }

    @Test
    public void testHiveServer_HDP130() {
        if (notSupportForTOP()) {
            String[] hiveServersDisplay = HiveMetadataHelper.getHiveServersDisplay(getDistribution(),
                    IHortonworksDistribution.VERSION_13, HiveModeInfo.EMBEDDED.getName(), false);
            assertTrue(NOT_SUPPORT_TOP_MESSAGE, hiveServersDisplay == null || hiveServersDisplay.length == 0);

            hiveServersDisplay = HiveMetadataHelper.getHiveServersDisplay(getDistribution(), IHortonworksDistribution.VERSION_13,
                    HiveModeInfo.STANDALONE.getName(), false);
            assertTrue(NOT_SUPPORT_TOP_MESSAGE, hiveServersDisplay == null || hiveServersDisplay.length == 0);
        } else {
            doTestGetHiveServersDisplay(IHortonworksDistribution.VERSION_13, HiveModeInfo.EMBEDDED.getName(),
                    HIVE_SERVER_DISPLAY_ALL);
            doTestGetHiveServersDisplay(IHortonworksDistribution.VERSION_13, HiveModeInfo.STANDALONE.getName(),
                    HIVE_SERVER_DISPLAY_ALL);
        }
    }

    @Test
    public void testHiveMode_HDP200() {
        if (notSupportForTOP()) {
            String[] hiveModesDisplay = HiveMetadataHelper.getHiveModesDisplay(getDistribution(),
                    IHortonworksDistribution.VERSION_20, false);
            assertTrue(NOT_SUPPORT_TOP_MESSAGE, hiveModesDisplay == null || hiveModesDisplay.length == 0);
        } else {
            doTestGetHiveModesDisplay(IHortonworksDistribution.VERSION_20, HIVE_MODE_DISPLAY_ALL);
        }
    }

    @Test
    public void testHiveServer_HDP200() {
        if (notSupportForTOP()) {
            String[] hiveServersDisplay = HiveMetadataHelper.getHiveServersDisplay(getDistribution(),
                    IHortonworksDistribution.VERSION_20, HiveModeInfo.EMBEDDED.getName(), false);
            assertTrue(NOT_SUPPORT_TOP_MESSAGE, hiveServersDisplay == null || hiveServersDisplay.length == 0);

            hiveServersDisplay = HiveMetadataHelper.getHiveServersDisplay(getDistribution(), IHortonworksDistribution.VERSION_20,
                    HiveModeInfo.STANDALONE.getName(), false);
            assertTrue(NOT_SUPPORT_TOP_MESSAGE, hiveServersDisplay == null || hiveServersDisplay.length == 0);
        } else {
            doTestGetHiveServersDisplay(IHortonworksDistribution.VERSION_20, HiveModeInfo.EMBEDDED.getName(),
                    HIVE_SERVER_DISPLAY_ALL);
            doTestGetHiveServersDisplay(IHortonworksDistribution.VERSION_20, HiveModeInfo.STANDALONE.getName(),
                    HIVE_SERVER_DISPLAY_ALL);
        }
    }

    @Test
    public void testHiveMode_HDP210() {
        if (notSupportForTOP()) {
            String[] hiveModesDisplay = HiveMetadataHelper.getHiveModesDisplay(getDistribution(),
                    IHortonworksDistribution.VERSION_21, false);
            assertTrue(NOT_SUPPORT_TOP_MESSAGE, hiveModesDisplay == null || hiveModesDisplay.length == 0);
        } else {
            doTestGetHiveModesDisplay(IHortonworksDistribution.VERSION_21, HIVE_MODE_DISPLAY_ALL);
        }
    }

    @Test
    public void testHiveServer_HDP210() {
        if (notSupportForTOP()) {
            String[] hiveServersDisplay = HiveMetadataHelper.getHiveServersDisplay(getDistribution(),
                    IHortonworksDistribution.VERSION_21, HiveModeInfo.EMBEDDED.getName(), false);
            assertTrue(NOT_SUPPORT_TOP_MESSAGE, hiveServersDisplay == null || hiveServersDisplay.length == 0);

            hiveServersDisplay = HiveMetadataHelper.getHiveServersDisplay(getDistribution(), IHortonworksDistribution.VERSION_21,
                    HiveModeInfo.STANDALONE.getName(), false);
            assertTrue(NOT_SUPPORT_TOP_MESSAGE, hiveServersDisplay == null || hiveServersDisplay.length == 0);
        } else {
            doTestGetHiveServersDisplay(IHortonworksDistribution.VERSION_21, HiveModeInfo.EMBEDDED.getName(),
                    HIVE_SERVER_DISPLAY_ALL);
            doTestGetHiveServersDisplay(IHortonworksDistribution.VERSION_21, HiveModeInfo.STANDALONE.getName(),
                    HIVE_SERVER_DISPLAY_ALL);
        }
    }

    @Test
    public void testHiveMode_HDP220() {
        if (notSupportForTOP()) {
            String[] hiveModesDisplay = HiveMetadataHelper.getHiveModesDisplay(getDistribution(),
                    IHortonworksDistribution.VERSION_22, false);
            assertTrue(NOT_SUPPORT_TOP_MESSAGE, hiveModesDisplay == null || hiveModesDisplay.length == 0);
        } else {
            doTestGetHiveModesDisplay(IHortonworksDistribution.VERSION_22, HIVE_MODE_DISPLAY_ALL);
        }
    }

    @Test
    public void testHiveServer_HDP220() {
        if (notSupportForTOP()) {
            String[] hiveServersDisplay = HiveMetadataHelper.getHiveServersDisplay(getDistribution(),
                    IHortonworksDistribution.VERSION_22, HiveModeInfo.EMBEDDED.getName(), false);
            assertTrue(NOT_SUPPORT_TOP_MESSAGE, hiveServersDisplay == null || hiveServersDisplay.length == 0);

            hiveServersDisplay = HiveMetadataHelper.getHiveServersDisplay(getDistribution(), IHortonworksDistribution.VERSION_22,
                    HiveModeInfo.STANDALONE.getName(), false);
            assertTrue(NOT_SUPPORT_TOP_MESSAGE, hiveServersDisplay == null || hiveServersDisplay.length == 0);
        } else {
            doTestGetHiveServersDisplay(IHortonworksDistribution.VERSION_22, HiveModeInfo.EMBEDDED.getName(),
                    HIVE_SERVER_DISPLAY_ALL);
            doTestGetHiveServersDisplay(IHortonworksDistribution.VERSION_22, HiveModeInfo.STANDALONE.getName(),
                    HIVE_SERVER_DISPLAY_ALL);
        }
    }

    @Test
    public void testHiveMode_HDP230() {
        if (notSupportForTOP()) {
            String[] hiveModesDisplay = HiveMetadataHelper.getHiveModesDisplay(getDistribution(),
                    IHortonworksDistribution.VERSION_23, false);
            assertTrue(NOT_SUPPORT_TOP_MESSAGE, hiveModesDisplay == null || hiveModesDisplay.length == 0);
        } else {
            doTestGetHiveModesDisplay(IHortonworksDistribution.VERSION_23, HIVE_MODE_DISPLAY_STANDALONE_ONLY);
        }
    }

    @Test
    public void testHiveServer_HDP230() {
        if (notSupportForTOP()) {
            String[] hiveServersDisplay = HiveMetadataHelper.getHiveServersDisplay(getDistribution(),
                    IHortonworksDistribution.VERSION_23, HiveModeInfo.EMBEDDED.getName(), false);
            assertTrue(NOT_SUPPORT_TOP_MESSAGE, hiveServersDisplay == null || hiveServersDisplay.length == 0);

            hiveServersDisplay = HiveMetadataHelper.getHiveServersDisplay(getDistribution(), IHortonworksDistribution.VERSION_23,
                    HiveModeInfo.STANDALONE.getName(), false);
            assertTrue(NOT_SUPPORT_TOP_MESSAGE, hiveServersDisplay == null || hiveServersDisplay.length == 0);
        } else {
            String[] hiveServer2Only = new String[] { HiveServerVersionInfo.HIVE_SERVER_2.getDisplayName() };
            doTestGetHiveServersDisplay(IHortonworksDistribution.VERSION_23, HiveModeInfo.EMBEDDED.getName(), hiveServer2Only);
            doTestGetHiveServersDisplay(IHortonworksDistribution.VERSION_23, HiveModeInfo.STANDALONE.getName(), hiveServer2Only);
        }
    }
}
