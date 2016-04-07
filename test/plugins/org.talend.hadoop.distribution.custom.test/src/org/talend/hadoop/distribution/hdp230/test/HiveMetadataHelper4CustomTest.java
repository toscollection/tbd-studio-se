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
package org.talend.hadoop.distribution.hdp230.test;

import org.junit.Test;
import org.talend.hadoop.distribution.constants.custom.ICustomDistribution;
import org.talend.hadoop.distribution.test.hive.AbstractVersionTest4HiveMetadataHelper;

/**
 * DOC ggu class global comment. Detailled comment
 */
public class HiveMetadataHelper4CustomTest extends AbstractVersionTest4HiveMetadataHelper {

    @Override
    protected String getDistribution() {
        return ICustomDistribution.DISTRIBUTION_NAME;
    }

    @Override
    protected String getDistributionVersion() {
        return null;
    }

    @Test
    public void testHiveMode_Custom_All() {
        doTestGetHiveModesDisplay(getDistributionVersion(), HIVE_MODE_DISPLAY_ALL);
    }

    @Test
    public void testHiveServer_Custom_All() {
        doTestGetHiveServersDisplay(getDistributionVersion(), HIVE_SERVER_DISPLAY_ALL);
    }

}
