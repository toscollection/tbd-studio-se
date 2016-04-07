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
package org.talend.hadoop.distribution.piv200.test;

import org.junit.Test;
import org.talend.hadoop.distribution.piv200.Pivotal200Distribution;
import org.talend.hadoop.distribution.test.hive.AbstractVersionTest4HiveMetadataHelper;

/**
 * DOC ggu class global comment. Detailled comment
 */
public class HiveMetadataHelper4PivotalHD200Test extends AbstractVersionTest4HiveMetadataHelper {

    @Override
    protected String getDistribution() {
        return Pivotal200Distribution.DISTRIBUTION_NAME;
    }

    @Override
    protected String getDistributionVersion() {
        return Pivotal200Distribution.VERSION;
    }

    @Test
    public void testHiveMode_Pivotal200_WithAll() {
        doTestGetHiveModesDisplay(getDistributionVersion(), HIVE_MODE_DISPLAY_ALL);
    }

    @Test
    public void testHiveServer_Pivotal200_WithAll() {
        doTestGetHiveServersDisplay(getDistributionVersion(), HIVE_SERVER_DISPLAY_ALL);
    }
}
