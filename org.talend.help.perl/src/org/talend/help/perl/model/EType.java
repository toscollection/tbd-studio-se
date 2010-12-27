// ============================================================================
//
// Copyright (C) 2006-2010 Talend Inc. - www.talend.com
//
// This source code is available under agreement available at
// %InstallDIR%\features\org.talend.rcp.branding.%PRODUCTNAME%\%PRODUCTNAME%license.txt
//
// You should have received a copy of the agreement
// along with this program; if not, write to Talend SA
// 9 rue Pages 92150 Suresnes, France
//
// ============================================================================
package org.talend.help.perl.model;

/**
 * EType.java.
 * 
 */
public enum EType {
    ROOT("help"), //$NON-NLS-1$
    FAMILY("family"), //$NON-NLS-1$
    CATEGORY("category"), //$NON-NLS-1$
    FUNCTION("function"), //$NON-NLS-1$
    ANCHOR("anchor"); //$NON-NLS-1$

    private final String elementName;

    EType(String name) {
        elementName = name;
    }

    public String getElementName() {
        return elementName;
    }

    public static final EType find(String name) {
        if (name.equals(ROOT.elementName)) {
            return ROOT;
        }
        if (name.equals(FAMILY.elementName)) {
            return FAMILY;
        }
        if (name.equals(CATEGORY.elementName)) {
            return CATEGORY;
        }
        if (name.equals(FUNCTION.elementName)) {
            return FUNCTION;
        }
        if (name.equals(ANCHOR.elementName)) {
            return ANCHOR;
        }
        return null;
    }
}
