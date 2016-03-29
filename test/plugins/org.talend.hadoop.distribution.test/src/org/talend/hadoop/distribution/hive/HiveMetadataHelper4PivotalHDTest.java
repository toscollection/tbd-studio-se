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
import org.talend.hadoop.distribution.constants.piv.IPivotalHDDistribution;

/**
 * DOC ggu class global comment. Detailled comment
 */
public class HiveMetadataHelper4PivotalHDTest extends AbstractTest4HiveMetadataHelper {

    @Override
    protected String getDistribution() {
        return IPivotalHDDistribution.DISTRIBUTION_NAME;
    }

    @Override
    protected String getDistributionDisplay() {
        return IPivotalHDDistribution.DISTRIBUTION_DISPLAY_NAME;
    }

    @Override
    protected String[] getDistributionVersionsDisplay() {
        return new String[] { IPivotalHDDistribution.VERSION_20_DISPLAY, IPivotalHDDistribution.VERSION_101_DISPLAY };
    }

    @Test
    public void testHiveMode_Pivotal101() {
        doTestGetHiveModesDisplay(IPivotalHDDistribution.VERSION_101, HIVE_MODE_DISPLAY_ALL);
    }

    @Test
    public void testHiveServer_Pivotal101() {
        // TODO not sure.
        String[] serverArr = new String[] { "Not sure" };
        doTestGetHiveServersDisplay(IPivotalHDDistribution.VERSION_101, HiveModeInfo.EMBEDDED.getName(), serverArr);
        doTestGetHiveServersDisplay(IPivotalHDDistribution.VERSION_101, HiveModeInfo.STANDALONE.getName(), serverArr);
    }

    @Test
    public void testHiveMode_Pivotal200() {
        doTestGetHiveModesDisplay(IPivotalHDDistribution.VERSION_20, HIVE_MODE_DISPLAY_ALL);
    }

    @Test
    public void testHiveServer_Pivotal200() {
        doTestGetHiveServersDisplay(IPivotalHDDistribution.VERSION_20, HiveModeInfo.EMBEDDED.getName(), HIVE_SERVER_DISPLAY_ALL);
        doTestGetHiveServersDisplay(IPivotalHDDistribution.VERSION_20, HiveModeInfo.STANDALONE.getName(), HIVE_SERVER_DISPLAY_ALL);
    }
}
