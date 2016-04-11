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
package org.talend.hadoop.distribution.cdh510mr1.test;

import org.junit.Test;
import org.talend.hadoop.distribution.cdh510mr1.CDH510MR1Distribution;
import org.talend.hadoop.distribution.test.hive.AbstractVersionTest4HiveMetadataHelper;

/**
 * DOC ggu class global comment. Detailled comment
 */
public class HiveMetadataHelper4CDH510MR1Test extends AbstractVersionTest4HiveMetadataHelper {

    @Override
    protected String getDistribution() {
        return CDH510MR1Distribution.DISTRIBUTION_NAME;
    }

    @Override
    protected String getDistributionVersion() {
        return CDH510MR1Distribution.VERSION;
    }

    @Test
    public void testHiveMode_CDH510MR1_WithAll() {
        doTestGetHiveModesDisplay(getDistributionVersion(), HIVE_MODE_DISPLAY_ALL);
    }

    @Test
    public void testHiveServer_CDH510MR1_WithAll() {
        doTestGetHiveServersDisplay(getDistributionVersion(), HIVE_SERVER_DISPLAY_ALL);
    }

}
