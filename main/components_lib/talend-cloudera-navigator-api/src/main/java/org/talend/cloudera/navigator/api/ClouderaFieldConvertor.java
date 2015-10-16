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

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

/**
 * created by pbailly on 14 Oct 2015 Detailled comment
 *
 */
public class ClouderaFieldConvertor {

    public static List<TalendField> convertToTalendField(Map<String, String> columns) {
        List<TalendField> talendFields = new ArrayList<TalendField>();
        for (Entry<String, String> column : columns.entrySet()) {
            talendFields.add(new TalendField(column.getKey(), column.getValue()));
        }
        return talendFields;
    }
}
