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

import static org.junit.Assert.assertArrayEquals;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;

import org.apache.commons.lang3.ArrayUtils;
import org.junit.Test;
import org.talend.core.model.metadata.connection.hive.HiveModeInfo;
import org.talend.core.model.metadata.connection.hive.HiveServerVersionInfo;
import org.talend.core.runtime.hd.IHDistribution;
import org.talend.core.runtime.hd.hive.HiveMetadataHelper;

/**
 * DOC ggu class global comment. Detailled comment
 */
public abstract class AbstractTest4HiveMetadataHelper {

    protected final static String[] HIVE_MODE_DISPLAY_ALL = new String[] { HiveModeInfo.EMBEDDED.getDisplayName(),
            HiveModeInfo.STANDALONE.getDisplayName() };

    protected final static String[] HIVE_MODE_DISPLAY_EMBEDDED_ONLY = new String[] { HiveModeInfo.EMBEDDED.getDisplayName() };

    protected final static String[] HIVE_MODE_DISPLAY_STANDALONE_ONLY = new String[] { HiveModeInfo.STANDALONE.getDisplayName() };

    protected final static String[] HIVE_SERVER_DISPLAY_ALL = new String[] {
            HiveServerVersionInfo.HIVE_SERVER_1.getDisplayName(), HiveServerVersionInfo.HIVE_SERVER_2.getDisplayName() };

    protected final static String[] HIVE_SERVER_DISPLAY_SERVER2_ONLY = new String[] { HiveServerVersionInfo.HIVE_SERVER_2
            .getDisplayName() };

    protected abstract String getDistribution();

    protected abstract String getDistributionDisplay();

    @Test
    public void testGetDistributionsDisplay() {
        String[] distributionsDisplay = HiveMetadataHelper.getDistributionsDisplay();
        assertFalse("Can't find hive distributions", distributionsDisplay == null || distributionsDisplay.length <= 0);
        assertTrue("Should support " + getDistributionDisplay(),
                ArrayUtils.contains(distributionsDisplay, getDistributionDisplay()));
    }

    @Test
    public void testGetDistribution() {
        IHDistribution distribution = HiveMetadataHelper.getDistribution(null, false);
        assertNull(distribution);

        distribution = HiveMetadataHelper.getDistribution("", false);
        assertNull(distribution);

        distribution = HiveMetadataHelper.getDistribution("ABC", false);
        assertNull(distribution);

        distribution = HiveMetadataHelper.getDistribution(getDistribution(), false);
        assertNotNull(distribution);
        assertEquals(getDistribution(), distribution.getName());
        assertEquals(getDistributionDisplay(), distribution.getDisplayName());

        String[] versionsDisplay = distribution.getVersionsDisplay();
        assertTrue("Must have version for " + getDistributionDisplay(), versionsDisplay != null && versionsDisplay.length > 0);
    }

    @Test
    public void testGetDistributionByDisplay() {
        IHDistribution distribution = HiveMetadataHelper.getDistribution("Abc", true);
        assertNull(distribution);

        distribution = HiveMetadataHelper.getDistribution(getDistributionDisplay(), true);
        assertNotNull(distribution);
        assertEquals(getDistribution(), distribution.getName());
        assertEquals(getDistributionDisplay(), distribution.getDisplayName());

        String[] versionsDisplay = distribution.getVersionsDisplay();
        assertTrue("Must have version for " + getDistributionDisplay(), versionsDisplay != null && versionsDisplay.length > 0);
    }

    @Test
    public void testGetDistributionWithDefault() {
        IHDistribution distribution = HiveMetadataHelper.getDistribution("Abc", true, false);
        assertNull(distribution);

        distribution = HiveMetadataHelper.getDistribution("Abc", true, true);
        assertNotNull("Should support one distribution at least", distribution);

        assertNotNull(distribution.getName());
        assertNotNull(distribution.getDisplayName());

        String[] distributionsDisplay = HiveMetadataHelper.getDistributionsDisplay();
        assertNotNull(distributionsDisplay);
        assertTrue(distributionsDisplay.length > 0);
        IHDistribution distribution2 = HiveMetadataHelper.getDistribution(distributionsDisplay[0], true);
        assertNotNull(distribution2);

        assertEquals(distribution2.getName(), distribution.getName());
        assertEquals(distribution2.getDisplayName(), distribution.getDisplayName());
    }

    protected void doTestGetHiveModesDisplay(String hiveVersion, String[] modeArr) {
        String[] hiveModesDisplay = HiveMetadataHelper.getHiveModesDisplay(getDistribution(), hiveVersion, false);
        assertNotNull(hiveModesDisplay);
        assertNotNull(modeArr);
        assertArrayEquals(modeArr, hiveModesDisplay);
        assertEquals(modeArr.length, hiveModesDisplay.length);
    }

    protected void doTestGetHiveServersDisplay(String hiveVersion, String hiveMode, String[] serverArr) {
        String[] hiveServersDisplay = HiveMetadataHelper.getHiveServersDisplay(getDistribution(), hiveVersion, hiveMode, false);
        assertNotNull(hiveServersDisplay);
        assertNotNull(serverArr);
        assertArrayEquals(serverArr, hiveServersDisplay);
        assertEquals(serverArr.length, hiveServersDisplay.length);
    }
}
