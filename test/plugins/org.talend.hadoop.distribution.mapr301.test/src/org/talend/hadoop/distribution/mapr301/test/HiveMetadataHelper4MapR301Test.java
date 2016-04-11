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
package org.talend.hadoop.distribution.mapr301.test;

import org.junit.Test;
import org.talend.hadoop.distribution.mapr301.MapR301Distribution;
import org.talend.hadoop.distribution.test.hive.AbstractVersionTest4HiveMetadataHelper;

/**
 * DOC ggu class global comment. Detailled comment
 */
public class HiveMetadataHelper4MapR301Test extends AbstractVersionTest4HiveMetadataHelper {

    @Override
    protected String getDistribution() {
        return MapR301Distribution.DISTRIBUTION_NAME;
    }

    @Override
    protected String getDistributionVersion() {
        return MapR301Distribution.VERSION;
    }

    @Test
    public void testHiveMode_MapR301_WitAll() {
        doTestGetHiveModesDisplay(getDistributionVersion(), HIVE_MODE_DISPLAY_ALL);
    }

    @Test
    public void testHiveServer_MapR301_WithAll() {
        doTestGetHiveServersDisplay(getDistributionVersion(), HIVE_SERVER_DISPLAY_ALL);
    }

}
