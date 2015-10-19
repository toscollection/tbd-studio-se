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

import com.cloudera.nav.sdk.model.entities.FileFormat;

/**
 * created by pbailly on 16 Oct 2015 Detailled comment
 *
 */
public class DatasetCreatorTest {

    @Test
    public void test() {
        assertEquals(FileFormat.AVRO, FileFormat.valueOf("AVRO"));
        assertEquals(FileFormat.JSON, FileFormat.valueOf("JSON"));
    }

}
