// ============================================================================
//
// Copyright (C) 2006-2009 Talend Inc. - www.talend.com
//
// This source code is available under agreement available at
// %InstallDIR%\features\org.talend.rcp.branding.%PRODUCTNAME%\%PRODUCTNAME%license.txt
//
// You should have received a copy of the agreement
// along with this program; if not, write to Talend SA
// 9 rue Pages 92150 Suresnes, France
//
// ============================================================================
package org.talend.designer.components.ecosystem.ui.views;

import org.talend.commons.utils.data.bean.IBeanPropertyAccessors;

/**
 * DOC chuang class global comment. Detailled comment
 */
public class BeanPropertyAccessorsAdapter<B, V> implements IBeanPropertyAccessors<B, V> {

    /*
     * (non-Javadoc)
     * 
     * @see org.talend.commons.utils.data.bean.IGetterPropertyAccessor#get(java.lang.Object)
     */
    public V get(B bean) {
        return null;
    }

    /*
     * (non-Javadoc)
     * 
     * @see org.talend.commons.utils.data.bean.ISetterPropertyAccessor#set(java.lang.Object, java.lang.Object)
     */
    public void set(B bean, V value) {
    }
}
