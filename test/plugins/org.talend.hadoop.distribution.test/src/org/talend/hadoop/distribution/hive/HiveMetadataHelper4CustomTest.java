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

import org.junit.Ignore;
import org.junit.Test;
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

    @Override
    protected String[] getDistributionVersionsDisplay() {
        return null;
    }

    @Override
    @Ignore
    public void testGetDistributionVersionsDisplay() {
        //
    }

    @Test
    public void testHiveMode_Custom_All() {
        doTestGetHiveModesDisplay(null, HIVE_MODE_DISPLAY_ALL);
    }

    @Test
    public void testHiveServer_Custom_All() {
        doTestGetHiveServersDisplay(null, HIVE_SERVER_DISPLAY_ALL);
    }

}
