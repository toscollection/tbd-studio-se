// ============================================================================
//
// Copyright (C) 2006-2011 Talend Inc. - www.talend.com
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

/**
 * DOC hcw class global comment. Detailled comment
 */
public class SearchProviderFactory {

    private static SearchProviderFactory sInstance = new SearchProviderFactory();

    private SearchProviderFactory() {
    }

    public static SearchProviderFactory getInstance() {
        return sInstance;
    }

    public <T> ISearchProvider<T> getSearchProvider() {
        return new SimpleSearchProvider<T>();
        // return new LuceneSearchProvider<T>();
    }

}
