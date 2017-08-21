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
package org.talend.hadoop.distribution.dynamic;

import org.talend.core.runtime.dynamic.IDynamicExtension;

/**
 * DOC cmeng  class global comment. Detailled comment
 */
public class DynamicLibraryNeededExtensionAdaper extends DynamicExtensionAdapter {

    public static final String TAG_NAME = "extension"; //$NON-NLS-1$

    public static final String ATTR_POINT = "org.talend.core.runtime.librariesNeeded"; //$NON-NLS-1$

    public DynamicLibraryNeededExtensionAdaper(IDynamicExtension dynamicExtension, String id) {
        super(dynamicExtension, id);
        if (!TAG_NAME.equals(dynamicExtension.getTagName()) && !ATTR_POINT.equals(dynamicExtension.getExtensionPoint())) {
            throw new RuntimeException("The input configuration is not an instance of " + TAG_NAME + ", " + ATTR_POINT); //$NON-NLS-1$
        }
    }

}
