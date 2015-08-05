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
package org.talend.designer.pigmap.dnd;

public enum DropType {
    DROP_FILTER("Drop to update tree filter"),
    DROP_EXPRESSION("Drop to update node filter"),
    DROP_INSERT_INPUT("Drop to insert a new input column"),
    DROP_INSERT_OUTPUT("Drop to insert a new output column");

    DropType(String str) {

    }
}
