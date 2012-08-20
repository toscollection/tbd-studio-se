// ============================================================================
//
// Copyright (C) 2006-2012 Talend Inc. - www.talend.com
//
// This source code is available under agreement available at
// %InstallDIR%\features\org.talend.rcp.branding.%PRODUCTNAME%\%PRODUCTNAME%license.txt
//
// You should have received a copy of the agreement
// along with this program; if not, write to Talend SA
// 9 rue Pages 92150 Suresnes, France
//
// ============================================================================
package org.talend.repository.hdfs.model;

import java.util.ArrayList;
import java.util.List;

/**
 * DOC ycbai class global comment. Detailled comment
 */
public enum EHDFSFieldSeparator {

    SEMICOLON(";"), //$NON-NLS-1$

    COMMA(","), //$NON-NLS-1$

    SPACE(" "), //$NON-NLS-1$

    TABULATION("\\t"), //$NON-NLS-1$

    ;

    private String name;

    EHDFSFieldSeparator(String name) {
        this.name = name;
    }

    public String getName() {
        return this.name;
    }

    public static List<String> getAllFieldSeparators() {
        List<String> names = new ArrayList<String>();
        EHDFSFieldSeparator[] values = values();
        for (EHDFSFieldSeparator type : values) {
            names.add(type.getName());
        }

        return names;
    }

}
