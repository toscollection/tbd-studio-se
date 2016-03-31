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
import org.talend.hadoop.distribution.constants.cdh.IClouderaDistribution;

/**
 * DOC ggu class global comment. Detailled comment
 */
public class HiveMetadataHelper4ClouderaTest extends AbstractTest4HiveMetadataHelper {

    @Override
    protected String getDistribution() {
        return IClouderaDistribution.DISTRIBUTION_NAME;
    }

    @Override
    protected String getDistributionDisplay() {
        return IClouderaDistribution.DISTRIBUTION_DISPLAY_NAME;
    }

    @Override
    protected String[] getDistributionVersionsDisplay() {
        return new String[] { IClouderaDistribution.VERSION_CDH5_5_DISPLAY, IClouderaDistribution.VERSION_CDH5_4_DISPLAY,
                IClouderaDistribution.VERSION_CDH5_1_DISPLAY, IClouderaDistribution.VERSION_CDH5_1_MR1_DISPLAY,
                IClouderaDistribution.VERSION_CDH5_DISPLAY, IClouderaDistribution.VERSION_CDH4_DISPLAY,
                IClouderaDistribution.VERSION_CDH4_YARN_DISPLAY };
    }

    @Test
    public void testHiveMode_CDH4MR1_WithAll() {
        doTestGetHiveModesDisplay(IClouderaDistribution.VERSION_CDH4, HIVE_MODE_DISPLAY_ALL);
    }

    @Test
    public void testHiveServer_CDH4MR1_WithAll() {
        doTestGetHiveServersDisplay(IClouderaDistribution.VERSION_CDH4, HIVE_SERVER_DISPLAY_ALL);
    }

    @Test
    public void testHiveMode_CDH4MR2_WithAll() {
        doTestGetHiveModesDisplay(IClouderaDistribution.VERSION_CDH4_YARN, HIVE_MODE_DISPLAY_ALL);
    }

    @Test
    public void testHiveServer_CDH4MR2_WithAll() {
        doTestGetHiveServersDisplay(IClouderaDistribution.VERSION_CDH4_YARN, HIVE_SERVER_DISPLAY_ALL);
    }

    @Test
    public void testHiveMode_CDH500_WithAll() {
        doTestGetHiveModesDisplay(IClouderaDistribution.VERSION_CDH5, HIVE_MODE_DISPLAY_ALL);
    }

    @Test
    public void testHiveServer_CDH500_WithAll() {
        doTestGetHiveServersDisplay(IClouderaDistribution.VERSION_CDH5, HIVE_SERVER_DISPLAY_ALL);
    }

    @Test
    public void testHiveMode_CDH510MR1_WithAll() {
        doTestGetHiveModesDisplay(IClouderaDistribution.VERSION_CDH5_1_MR1, HIVE_MODE_DISPLAY_ALL);
    }

    @Test
    public void testHiveServer_CDH510MR1_WithAll() {
        doTestGetHiveServersDisplay(IClouderaDistribution.VERSION_CDH5_1_MR1, HIVE_SERVER_DISPLAY_ALL);
    }

    @Test
    public void testHiveMode_CDH510MR2_WithAll() {
        doTestGetHiveModesDisplay(IClouderaDistribution.VERSION_CDH5_1, HIVE_MODE_DISPLAY_ALL);
    }

    @Test
    public void testHiveServer_CDH510MR2_WithAll() {
        doTestGetHiveServersDisplay(IClouderaDistribution.VERSION_CDH5_1, HIVE_SERVER_DISPLAY_ALL);
    }

    @Test
    public void testHiveMode_CDH540_WithAll() {
        doTestGetHiveModesDisplay(IClouderaDistribution.VERSION_CDH5_4, HIVE_MODE_DISPLAY_ALL);
    }

    @Test
    public void testHiveServer_CDH540_Server2_Only() {
        doTestGetHiveServersDisplay(IClouderaDistribution.VERSION_CDH5_4, HIVE_SERVER_DISPLAY_SERVER2_ONLY);
    }

    @Test
    public void testHiveMode_CDH550_Standalone_Only() {
        doTestGetHiveModesDisplay(IClouderaDistribution.VERSION_CDH5_5, HIVE_MODE_DISPLAY_STANDALONE_ONLY);
    }

    @Test
    public void testHiveServer_CDH550_Server2_Only() {
        doTestGetHiveServersDisplay(IClouderaDistribution.VERSION_CDH5_5, HIVE_SERVER_DISPLAY_SERVER2_ONLY);
    }
}
