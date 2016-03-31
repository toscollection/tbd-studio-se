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

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;

import org.junit.Test;
import org.talend.core.database.EDatabaseTypeName;
import org.talend.core.runtime.hd.IHDistribution;
import org.talend.core.runtime.hd.hive.HiveMetadataHelper;

/**
 * DOC ggu class global comment. Detailled comment
 */
public class HiveMetadataHelperTest {

    @Test
    public void testIsHiveEmbeddedMode() {
        assertFalse(HiveMetadataHelper.isHiveEmbeddedMode(null, null));
        assertFalse(HiveMetadataHelper.isHiveEmbeddedMode("HIVE", null));
        assertFalse(HiveMetadataHelper.isHiveEmbeddedMode("Hive", null));
        assertFalse(HiveMetadataHelper.isHiveEmbeddedMode("Hive", "EMBEDDED"));
        assertFalse(HiveMetadataHelper.isHiveEmbeddedMode("Hive", "ABC"));
        assertTrue(HiveMetadataHelper.isHiveEmbeddedMode("Hive", "Embedded"));
        assertTrue(HiveMetadataHelper.isHiveEmbeddedMode(EDatabaseTypeName.HIVE.getDisplayName(), "Embedded"));
    }

    @Test
    public void testGetDistribution_Default() {
        IHDistribution distribution = HiveMetadataHelper.getDistribution("Abc", true, false);
        assertNull(distribution);

        distribution = HiveMetadataHelper.getDistribution("Abc", true, true);
        assertNotNull("Should support one distribution at least", distribution);

        assertNotNull(distribution.getName());
        assertNotNull(distribution.getDisplayName());

        String[] distributionsDisplay = HiveMetadataHelper.getDistributionsDisplay();
        assertNotNull(distributionsDisplay);
        assertTrue(distributionsDisplay.length > 0);
        IHDistribution distribution2 = HiveMetadataHelper.getDistribution(distributionsDisplay[0], true);
        assertNotNull(distribution2);

        assertEquals(distribution2.getName(), distribution.getName());
        assertEquals(distribution2.getDisplayName(), distribution.getDisplayName());
    }
}
