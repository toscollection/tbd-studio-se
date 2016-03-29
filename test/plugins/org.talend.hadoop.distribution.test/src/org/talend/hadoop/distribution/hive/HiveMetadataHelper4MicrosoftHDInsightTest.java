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

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;

import org.apache.commons.lang3.ArrayUtils;
import org.junit.Ignore;
import org.junit.Test;
import org.talend.core.model.metadata.connection.hive.HiveModeInfo;
import org.talend.core.runtime.hd.IHDistribution;
import org.talend.core.runtime.hd.hive.HiveMetadataHelper;
import org.talend.hadoop.distribution.constants.hdinsight.IMicrosoftHDInsightDistribution;

/**
 * DOC ggu class global comment. Detailled comment
 */
public class HiveMetadataHelper4MicrosoftHDInsightTest extends AbstractTest4HiveMetadataHelper {

    @Override
    protected String getDistribution() {
        return IMicrosoftHDInsightDistribution.DISTRIBUTION_NAME;
    }

    @Override
    protected String getDistributionDisplay() {
        return IMicrosoftHDInsightDistribution.DISTRIBUTION_DISPLAY_NAME;
    }

    @Override
    protected String[] getDistributionVersionsDisplay() {
        return null;
    }

    @Override
    @Test
    public void testGetDistributionsDisplay_FindCurrentOne() {
        String[] distributionsDisplay = HiveMetadataHelper.getDistributionsDisplay();
        assertFalse("Can't find hive distributions", distributionsDisplay == null || distributionsDisplay.length <= 0);
        assertFalse("Shouldn't support for Hive" + getDistributionDisplay(),
                ArrayUtils.contains(distributionsDisplay, getDistributionDisplay()));
    }

    @Override
    @Test
    public void testGetDistribution_Name() {
        IHDistribution distribution = HiveMetadataHelper.getDistribution(getDistribution(), false);
        assertNull("Shouldn't support for Hive", distribution);
    }

    @Override
    public void testGetDistribution_Display() {
        IHDistribution distribution = HiveMetadataHelper.getDistribution(getDistributionDisplay(), true);
        assertNull("Shouldn't support for Hive", distribution);
    }

    @Override
    @Ignore
    public void testGetDistribution_Default() {
        //
    }

    @Override
    @Ignore
    public void testGetDistributionVersionsDisplay() {
        //
    }

    @Test
    public void testHiveMode_HDInsight31() {
        String[] hiveModesDisplay = HiveMetadataHelper.getHiveModesDisplay(getDistribution(),
                IMicrosoftHDInsightDistribution.VERSION_31, false);
        assertTrue("Don't support for Hive", hiveModesDisplay == null || hiveModesDisplay.length == 0);
    }

    @Test
    public void testHiveServer_HDInsight31() {
        String[] hiveServersDisplay = HiveMetadataHelper.getHiveServersDisplay(getDistribution(),
                IMicrosoftHDInsightDistribution.VERSION_31, HiveModeInfo.EMBEDDED.getName(), false);
        assertTrue("Don't support for Hive", hiveServersDisplay == null || hiveServersDisplay.length == 0);

        hiveServersDisplay = HiveMetadataHelper.getHiveServersDisplay(getDistribution(),
                IMicrosoftHDInsightDistribution.VERSION_31, HiveModeInfo.STANDALONE.getName(), false);
        assertTrue("Don't support for Hive", hiveServersDisplay == null || hiveServersDisplay.length == 0);
    }

    @Test
    public void testHiveMode_HDInsight32() {
        String[] hiveModesDisplay = HiveMetadataHelper.getHiveModesDisplay(getDistribution(),
                IMicrosoftHDInsightDistribution.VERSION_32, false);
        assertTrue("Don't support for Hive", hiveModesDisplay == null || hiveModesDisplay.length == 0);
    }

    @Test
    public void testHiveServer_HDInsight32() {
        String[] hiveServersDisplay = HiveMetadataHelper.getHiveServersDisplay(getDistribution(),
                IMicrosoftHDInsightDistribution.VERSION_32, HiveModeInfo.EMBEDDED.getName(), false);
        assertTrue("Don't support for Hive", hiveServersDisplay == null || hiveServersDisplay.length == 0);

        hiveServersDisplay = HiveMetadataHelper.getHiveServersDisplay(getDistribution(),
                IMicrosoftHDInsightDistribution.VERSION_32, HiveModeInfo.STANDALONE.getName(), false);
        assertTrue("Don't support for Hive", hiveServersDisplay == null || hiveServersDisplay.length == 0);
    }
}
