// ============================================================================
//
// Copyright (C) 2006-2015 Talend Inc. - www.talend.com
//
// This source code is available under agreement available at
// %InstallDIR%\features\org.talend.rcp.branding.%PRODUCTNAME%\%PRODUCTNAME%license.txt
//
// You should have received a copy of the agreement
// along with this program; if not, write to Talend SA
// 9 rue Pages 92150 Suresnes, France
//
// ============================================================================
package org.talend.cloudera.navigator.api;

import static org.junit.Assert.*;

import org.junit.Test;

/**
 * created by pbailly on 21 Oct 2015 Detailled comment
 *
 */
public class GeneratorIDTest {

    @Test
    public void test() {
        assertEquals("27cef6c51402deedbe850e73ef529a84", GeneratorID.generateDatasetID("job", "component"));
        assertEquals("494b69782152d6685642976d2c25a54e", GeneratorID.generateNodeID("job", "component"));
    }
}
