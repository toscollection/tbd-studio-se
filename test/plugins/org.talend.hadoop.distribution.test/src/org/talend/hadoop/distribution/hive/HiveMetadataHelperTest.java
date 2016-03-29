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

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.junit.Test;
import org.talend.core.database.EDatabaseTypeName;
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
}
