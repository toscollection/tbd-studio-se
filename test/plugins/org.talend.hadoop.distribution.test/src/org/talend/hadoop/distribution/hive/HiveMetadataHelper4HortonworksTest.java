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
    public void testHiveMode_HDP120_Server1WithEmbedded() {
        if (notSupportForTOP()) {
            String[] hiveModesDisplay = HiveMetadataHelper.getHiveModesDisplay(getDistribution(),
                    IHortonworksDistribution.VERSION_12, HiveServerVersionInfo.HIVE_SERVER_1.getKey(), false);
            assertTrue(NOT_SUPPORT_TOP_MESSAGE, hiveModesDisplay == null || hiveModesDisplay.length == 0);

            // because fasle for doSupportHive1Standalone in 1.2.0
            doTestGetHiveModesDisplay(IHortonworksDistribution.VERSION_12, HiveServerVersionInfo.HIVE_SERVER_1.getKey(),
                    HIVE_MODE_DISPLAY_EMBEDDED_ONLY);
        }
    }

    @Test
    public void testHiveMode_HDP120_Server2WithAll() {
        if (notSupportForTOP()) {
            String[] hiveModesDisplay = HiveMetadataHelper.getHiveModesDisplay(getDistribution(),
                    IHortonworksDistribution.VERSION_12, HiveServerVersionInfo.HIVE_SERVER_2.getKey(), false);
            assertTrue(NOT_SUPPORT_TOP_MESSAGE, hiveModesDisplay == null || hiveModesDisplay.length == 0);
        } else {
            doTestGetHiveModesDisplay(IHortonworksDistribution.VERSION_12, HiveServerVersionInfo.HIVE_SERVER_2.getKey(),
                    HIVE_MODE_DISPLAY_ALL);
        }
    }

    @Test
    public void testHiveServer_HDP120_WithAll() {
        if (notSupportForTOP()) {
            String[] hiveServersDisplay = HiveMetadataHelper.getHiveServersDisplay(getDistribution(),
                    IHortonworksDistribution.VERSION_12, false);
            assertTrue(NOT_SUPPORT_TOP_MESSAGE, hiveServersDisplay == null || hiveServersDisplay.length == 0);

        } else {
            doTestGetHiveServersDisplay(IHortonworksDistribution.VERSION_12, HIVE_SERVER_DISPLAY_ALL);
        }
    }

    @Test
    public void testHiveMode_HDP130_Server1WithEmbedded() {
        if (notSupportForTOP()) {
            String[] hiveModesDisplay = HiveMetadataHelper.getHiveModesDisplay(getDistribution(),
                    IHortonworksDistribution.VERSION_13, HiveServerVersionInfo.HIVE_SERVER_1.getKey(), false);
            assertTrue(NOT_SUPPORT_TOP_MESSAGE, hiveModesDisplay == null || hiveModesDisplay.length == 0);

        } else {
            doTestGetHiveModesDisplay(IHortonworksDistribution.VERSION_13, HiveServerVersionInfo.HIVE_SERVER_1.getKey(),
                    HIVE_MODE_DISPLAY_EMBEDDED_ONLY);
        }
    }

    @Test
    public void testHiveMode_HDP130_Server2WithAll() {
        if (notSupportForTOP()) {
            String[] hiveModesDisplay = HiveMetadataHelper.getHiveModesDisplay(getDistribution(),
                    IHortonworksDistribution.VERSION_13, HiveServerVersionInfo.HIVE_SERVER_2.getKey(), false);
            assertTrue(NOT_SUPPORT_TOP_MESSAGE, hiveModesDisplay == null || hiveModesDisplay.length == 0);
        } else {
            doTestGetHiveModesDisplay(IHortonworksDistribution.VERSION_13, HiveServerVersionInfo.HIVE_SERVER_2.getKey(),
                    HIVE_MODE_DISPLAY_ALL);
        }
    }

    @Test
    public void testHiveServer_HDP130_WithAll() {
        if (notSupportForTOP()) {
            String[] hiveServersDisplay = HiveMetadataHelper.getHiveServersDisplay(getDistribution(),
                    IHortonworksDistribution.VERSION_13, false);
            assertTrue(NOT_SUPPORT_TOP_MESSAGE, hiveServersDisplay == null || hiveServersDisplay.length == 0);

        } else {
            doTestGetHiveServersDisplay(IHortonworksDistribution.VERSION_13, HIVE_SERVER_DISPLAY_ALL);
        }
    }

    @Test
    public void testHiveMode_HDP200_Server1WithEmbedded() {
        if (notSupportForTOP()) {
            String[] hiveModesDisplay = HiveMetadataHelper.getHiveModesDisplay(getDistribution(),
                    IHortonworksDistribution.VERSION_20, HiveServerVersionInfo.HIVE_SERVER_1.getKey(), false);
            assertTrue(NOT_SUPPORT_TOP_MESSAGE, hiveModesDisplay == null || hiveModesDisplay.length == 0);

        } else {
            doTestGetHiveModesDisplay(IHortonworksDistribution.VERSION_20, HiveServerVersionInfo.HIVE_SERVER_1.getKey(),
                    HIVE_MODE_DISPLAY_EMBEDDED_ONLY);
        }
    }

    @Test
    public void testHiveMode_HDP200_Server2WithAll() {
        if (notSupportForTOP()) {
            String[] hiveModesDisplay = HiveMetadataHelper.getHiveModesDisplay(getDistribution(),
                    IHortonworksDistribution.VERSION_20, HiveServerVersionInfo.HIVE_SERVER_2.getKey(), false);
            assertTrue(NOT_SUPPORT_TOP_MESSAGE, hiveModesDisplay == null || hiveModesDisplay.length == 0);
        } else {
            doTestGetHiveModesDisplay(IHortonworksDistribution.VERSION_20, HiveServerVersionInfo.HIVE_SERVER_2.getKey(),
                    HIVE_MODE_DISPLAY_ALL);
        }
    }

    @Test
    public void testHiveServer_HDP200_WithAll() {
        if (notSupportForTOP()) {
            String[] hiveServersDisplay = HiveMetadataHelper.getHiveServersDisplay(getDistribution(),
                    IHortonworksDistribution.VERSION_20, false);
            assertTrue(NOT_SUPPORT_TOP_MESSAGE, hiveServersDisplay == null || hiveServersDisplay.length == 0);

        } else {
            doTestGetHiveServersDisplay(IHortonworksDistribution.VERSION_20, HIVE_SERVER_DISPLAY_ALL);
        }
    }

    @Test
    public void testHiveMode_HDP210_Server1WithEmbedded() {
        if (notSupportForTOP()) {
            String[] hiveModesDisplay = HiveMetadataHelper.getHiveModesDisplay(getDistribution(),
                    IHortonworksDistribution.VERSION_21, HiveServerVersionInfo.HIVE_SERVER_1.getKey(), false);
            assertTrue(NOT_SUPPORT_TOP_MESSAGE, hiveModesDisplay == null || hiveModesDisplay.length == 0);

        } else {
            doTestGetHiveModesDisplay(IHortonworksDistribution.VERSION_21, HiveServerVersionInfo.HIVE_SERVER_1.getKey(),
                    HIVE_MODE_DISPLAY_EMBEDDED_ONLY);
        }
    }

    @Test
    public void testHiveMode_HDP210_Server2WithAll() {
        if (notSupportForTOP()) {
            String[] hiveModesDisplay = HiveMetadataHelper.getHiveModesDisplay(getDistribution(),
                    IHortonworksDistribution.VERSION_21, HiveServerVersionInfo.HIVE_SERVER_2.getKey(), false);
            assertTrue(NOT_SUPPORT_TOP_MESSAGE, hiveModesDisplay == null || hiveModesDisplay.length == 0);
        } else {
            doTestGetHiveModesDisplay(IHortonworksDistribution.VERSION_21, HiveServerVersionInfo.HIVE_SERVER_2.getKey(),
                    HIVE_MODE_DISPLAY_ALL);
        }
    }

    @Test
    public void testHiveServer_HDP210_WithAll() {
        if (notSupportForTOP()) {
            String[] hiveServersDisplay = HiveMetadataHelper.getHiveServersDisplay(getDistribution(),
                    IHortonworksDistribution.VERSION_21, false);
            assertTrue(NOT_SUPPORT_TOP_MESSAGE, hiveServersDisplay == null || hiveServersDisplay.length == 0);

        } else {
            doTestGetHiveServersDisplay(IHortonworksDistribution.VERSION_21, HIVE_SERVER_DISPLAY_ALL);
        }
    }

    @Test
    public void testHiveMode_HDP220_Server1WithEmbedded() {
        if (notSupportForTOP()) {
            String[] hiveModesDisplay = HiveMetadataHelper.getHiveModesDisplay(getDistribution(),
                    IHortonworksDistribution.VERSION_22, HiveServerVersionInfo.HIVE_SERVER_1.getKey(), false);
            assertTrue(NOT_SUPPORT_TOP_MESSAGE, hiveModesDisplay == null || hiveModesDisplay.length == 0);

        } else {
            doTestGetHiveModesDisplay(IHortonworksDistribution.VERSION_22, HiveServerVersionInfo.HIVE_SERVER_1.getKey(),
                    HIVE_MODE_DISPLAY_EMBEDDED_ONLY);
        }
    }

    @Test
    public void testHiveMode_HDP220_Server2WithAll() {
        if (notSupportForTOP()) {
            String[] hiveModesDisplay = HiveMetadataHelper.getHiveModesDisplay(getDistribution(),
                    IHortonworksDistribution.VERSION_22, HiveServerVersionInfo.HIVE_SERVER_2.getKey(), false);
            assertTrue(NOT_SUPPORT_TOP_MESSAGE, hiveModesDisplay == null || hiveModesDisplay.length == 0);
        } else {
            doTestGetHiveModesDisplay(IHortonworksDistribution.VERSION_22, HiveServerVersionInfo.HIVE_SERVER_2.getKey(),
                    HIVE_MODE_DISPLAY_ALL);
        }
    }

    @Test
    public void testHiveServer_HDP220_WithAll() {
        if (notSupportForTOP()) {
            String[] hiveServersDisplay = HiveMetadataHelper.getHiveServersDisplay(getDistribution(),
                    IHortonworksDistribution.VERSION_22, false);
            assertTrue(NOT_SUPPORT_TOP_MESSAGE, hiveServersDisplay == null || hiveServersDisplay.length == 0);

        } else {
            doTestGetHiveServersDisplay(IHortonworksDistribution.VERSION_22, HIVE_SERVER_DISPLAY_ALL);
        }
    }

    @Test
    public void testHiveMode_HDP230_Server1NotSupport() {
        if (notSupportForTOP()) {
            String[] hiveModesDisplay = HiveMetadataHelper.getHiveModesDisplay(getDistribution(),
                    IHortonworksDistribution.VERSION_23, HiveServerVersionInfo.HIVE_SERVER_1.getKey(), false);
            assertTrue(NOT_SUPPORT_TOP_MESSAGE, hiveModesDisplay == null || hiveModesDisplay.length == 0);

        } else {
            // don't support any hive model in server 1
            doTestGetHiveModesDisplay(IHortonworksDistribution.VERSION_23, HiveServerVersionInfo.HIVE_SERVER_1.getKey(),
                    new String[] {});
        }
    }

    @Test
    public void testHiveMode_HDP230_Server2WithStandalone() {
        if (notSupportForTOP()) {
            String[] hiveModesDisplay = HiveMetadataHelper.getHiveModesDisplay(getDistribution(),
                    IHortonworksDistribution.VERSION_23, HiveServerVersionInfo.HIVE_SERVER_2.getKey(), false);
            assertTrue(NOT_SUPPORT_TOP_MESSAGE, hiveModesDisplay == null || hiveModesDisplay.length == 0);
        } else {
            doTestGetHiveModesDisplay(IHortonworksDistribution.VERSION_23, HiveServerVersionInfo.HIVE_SERVER_2.getKey(),
                    HIVE_MODE_DISPLAY_STANDALONE_ONLY);
        }
    }

    @Test
    public void testHiveServer_HDP230_Sever2_Only() {
        if (notSupportForTOP()) {
            String[] hiveServersDisplay = HiveMetadataHelper.getHiveServersDisplay(getDistribution(),
                    IHortonworksDistribution.VERSION_23, false);
            assertTrue(NOT_SUPPORT_TOP_MESSAGE, hiveServersDisplay == null || hiveServersDisplay.length == 0);

        } else {
            doTestGetHiveServersDisplay(IHortonworksDistribution.VERSION_23, HIVE_SERVER_DISPLAY_SERVER2_ONLY);
        }
    }
}
