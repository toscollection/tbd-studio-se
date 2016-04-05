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
package org.talend.hadoop.distribution.test.hive;

import static org.junit.Assert.assertArrayEquals;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import java.util.Arrays;

import org.apache.commons.lang3.ArrayUtils;
import org.talend.commons.utils.platform.PluginChecker;
import org.talend.core.model.metadata.connection.hive.HiveModeInfo;
import org.talend.core.model.metadata.connection.hive.HiveServerVersionInfo;
import org.talend.core.runtime.hd.hive.HiveMetadataHelper;

/**
 * DOC ggu class global comment. Detailled comment
 */
public abstract class AbstractVersionTest4HiveMetadataHelper extends AbstractTest4HiveMetadataHelper {

    protected final static String[] HIVE_MODE_DISPLAY_ALL = new String[] { HiveModeInfo.EMBEDDED.getDisplayName(),
            HiveModeInfo.STANDALONE.getDisplayName() };

    protected final static String[] HIVE_MODE_DISPLAY_EMBEDDED_ONLY = new String[] { HiveModeInfo.EMBEDDED.getDisplayName() };

    protected final static String[] HIVE_MODE_DISPLAY_STANDALONE_ONLY = new String[] { HiveModeInfo.STANDALONE.getDisplayName() };

    protected final static String[] HIVE_SERVER_DISPLAY_ALL = new String[] {
            HiveServerVersionInfo.HIVE_SERVER_1.getDisplayName(), HiveServerVersionInfo.HIVE_SERVER_2.getDisplayName() };

    protected final static String[] HIVE_SERVER_DISPLAY_SERVER1_ONLY = new String[] { HiveServerVersionInfo.HIVE_SERVER_1
            .getDisplayName() };

    protected final static String[] HIVE_SERVER_DISPLAY_SERVER2_ONLY = new String[] { HiveServerVersionInfo.HIVE_SERVER_2
            .getDisplayName() };

    protected abstract String getDistribution();

    protected abstract String getDistributionVersion();

    protected void doTestGetHiveModesDisplay(String hiveVersion, String[] modeArr) {
        doTestGetHiveModesDisplay(hiveVersion, HiveServerVersionInfo.HIVE_SERVER_1.getKey(), modeArr);
        doTestGetHiveModesDisplay(hiveVersion, HiveServerVersionInfo.HIVE_SERVER_2.getKey(), modeArr);
    }

    protected void doTestGetHiveModesDisplay(String hiveVersion, String hiveServer, String[] modeArr) {
        if (PluginChecker.isOnlyTopLoaded() && ArrayUtils.contains(modeArr, HiveModeInfo.EMBEDDED.getDisplayName())) {
            modeArr = ArrayUtils.removeElement(modeArr, HiveModeInfo.EMBEDDED.getDisplayName());
        }
        String[] hiveModesDisplay = HiveMetadataHelper.getHiveModesDisplay(getDistribution(), hiveVersion, hiveServer, false);
        doTestArray("Modes are different", modeArr, hiveModesDisplay);
    }

    protected void doTestGetHiveServersDisplay(String hiveVersion, String[] serverArr) {
        String[] hiveServersDisplay = HiveMetadataHelper.getHiveServersDisplay(getDistribution(), hiveVersion, false);
        doTestArray("Server Versions are different", serverArr, hiveServersDisplay);
    }

    protected void doTestArray(String baseMessages, String[] expecteds, String[] actuals) {
        assertNotNull(expecteds);
        assertNotNull(actuals);
        if (expecteds.length == actuals.length) {
            assertArrayEquals(baseMessages, expecteds, actuals);
        } else {
            assertEquals(baseMessages + " , " + Arrays.asList(expecteds) + "<==>" + Arrays.asList(actuals), expecteds.length,
                    actuals.length);
        }
    }
}
