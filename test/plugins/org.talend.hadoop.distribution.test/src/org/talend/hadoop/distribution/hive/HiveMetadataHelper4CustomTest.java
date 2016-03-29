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
import org.talend.hadoop.distribution.constants.custom.ICustomDistribution;

/**
 * DOC ggu class global comment. Detailled comment
 */
public class HiveMetadataHelper4CustomTest extends AbstractTest4HiveMetadataHelper {

    @Override
    protected String getDistribution() {
        return ICustomDistribution.DISTRIBUTION_NAME;
    }

    @Override
    protected String getDistributionDisplay() {
        return ICustomDistribution.DISTRIBUTION_DISPLAY_NAME;
    }

    @Test
    public void testHiveMode_Custom() {
        doTestGetHiveModesDisplay(null, HIVE_MODE_DISPLAY_EMBEDDED_ONLY);
    }

    @Test
    public void testHiveServer_Custom() {
        doTestGetHiveServersDisplay(null, HiveModeInfo.EMBEDDED.getName(), HIVE_SERVER_DISPLAY_SERVER2_ONLY);
        doTestGetHiveServersDisplay(null, HiveModeInfo.STANDALONE.getName(), HIVE_SERVER_DISPLAY_SERVER2_ONLY);
    }

}
