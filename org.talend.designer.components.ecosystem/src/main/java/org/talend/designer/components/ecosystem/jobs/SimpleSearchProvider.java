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
package org.talend.designer.components.ecosystem.jobs;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

/**
 * Use string indexof to find a match.
 */
public class SimpleSearchProvider<T> implements ISearchProvider<T> {

    private Map<T, String[]> fRecords;

    /*
     * (non-Javadoc)
     * 
     * @see org.talend.designer.components.ecosystem.jobs.ISearchProvider#addRecord(java.lang.Object, java.lang.String,
     * java.lang.String[])
     */
    public void addRecord(T bean, String key, String... fields) throws Exception {
        if (fields == null || fields.length == 0) {
            return;
        }
        String[] texts = new String[fields.length];
        for (int i = 0; i < fields.length; i++) {
            texts[i] = fields[i].toLowerCase();
        }
        fRecords.put(bean, texts);
    }

    /*
     * (non-Javadoc)
     * 
     * @see org.talend.designer.components.ecosystem.jobs.ISearchProvider#init()
     */
    public void init() throws Exception {
        fRecords = new HashMap<T, String[]>();
    }

    /*
     * (non-Javadoc)
     * 
     * @see org.talend.designer.components.ecosystem.jobs.ISearchProvider#search(java.lang.String)
     */
    public List<T> search(String queryString) throws Exception {
        List<T> matched = new ArrayList<T>();
        queryString = queryString.toLowerCase();
        for (Entry<T, String[]> entry : fRecords.entrySet()) {
            T bean = entry.getKey();

            for (String text : entry.getValue()) {
                if (text.indexOf(queryString) > -1) {
                    matched.add(bean);
                    break;
                }
            }

        }
        return matched;
    }

}
