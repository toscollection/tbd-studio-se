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
public enum ActionType {
    NEXT("next"), //$NON-NLS-1$
    SEARCH("search"); //$NON-NLS-1$

    private final String elementName;

    ActionType(String name) {
        elementName = name;
    }

    public String getElementName() {
        return elementName;
    }

    public static final ActionType find(String name) {
        if (name.equals(NEXT.elementName)) {
            return NEXT;
        }
        if (name.equals(SEARCH.elementName)) {
            return SEARCH;
        }
        return null;
    }
}
