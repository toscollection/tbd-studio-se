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
 * created by pbailly on 16 Oct 2015 Detailled comment
 *
 */
public class ClouderaAPIUtilTest {

    @Test
    public void testGetDatasetName() {
        assertEquals("my", ClouderaAPIUtil.getDatasetName("/my"));
        assertEquals("path", ClouderaAPIUtil.getDatasetName("/my/path"));
        assertEquals("path", ClouderaAPIUtil.getDatasetName("/my/path/"));
        assertEquals("path", ClouderaAPIUtil.getDatasetName("/my/long/path"));
        assertEquals("path", ClouderaAPIUtil.getDatasetName("this/is/my/long//path"));
        assertEquals("path", ClouderaAPIUtil.getDatasetName("path/"));
        assertEquals("path", ClouderaAPIUtil.getDatasetName("path////"));
        assertEquals("UnamedTalendDataset", ClouderaAPIUtil.getDatasetName("/"));
        assertEquals("UnamedTalendDataset", ClouderaAPIUtil.getDatasetName(""));
        assertEquals("UnamedTalendDataset", ClouderaAPIUtil.getDatasetName(null));
    }
}
