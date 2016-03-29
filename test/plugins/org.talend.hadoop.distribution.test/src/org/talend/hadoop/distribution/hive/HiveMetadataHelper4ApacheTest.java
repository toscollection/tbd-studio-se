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
import org.talend.hadoop.distribution.constants.apache.IApacheDistribution;
import org.talend.hadoop.distribution.constants.hdp.IHortonworksDistribution;

/**
 * DOC ggu class global comment. Detailled comment
 */
public class HiveMetadataHelper4ApacheTest extends AbstractTest4HiveMetadataHelper {

    @Override
    protected String getDistribution() {
        return IApacheDistribution.DISTRIBUTION_NAME;
    }

    @Override
    protected String getDistributionDisplay() {
        return IApacheDistribution.DISTRIBUTION_DISPLAY_NAME;
    }

    @Test
    public void testHiveMode_Apache100() {
        doTestGetHiveModesDisplay(IApacheDistribution.VERSION_100, HIVE_MODE_DISPLAY_EMBEDDED_ONLY);
    }

    @Test
    public void testHiveServer_Apache100() {
        doTestGetHiveServersDisplay(IHortonworksDistribution.VERSION_12, HiveModeInfo.EMBEDDED.getName(),
                HIVE_SERVER_DISPLAY_SERVER2_ONLY);
        doTestGetHiveServersDisplay(IHortonworksDistribution.VERSION_12, HiveModeInfo.STANDALONE.getName(),
                HIVE_SERVER_DISPLAY_SERVER2_ONLY);
    }

}
