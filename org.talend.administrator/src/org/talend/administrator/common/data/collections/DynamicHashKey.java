// ============================================================================
//
// Talend Community Edition
//
// Copyright (C) 2006 Talend - www.talend.com
//
// This library is free software; you can redistribute it and/or
// modify it under the terms of the GNU Lesser General Public
// License as published by the Free Software Foundation; either
// version 2.1 of the License, or (at your option) any later version.
//
// This library is distributed in the hope that it will be useful,
// but WITHOUT ANY WARRANTY; without even the implied warranty of
// MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU
// Lesser General Public License for more details.
//
// You should have received a copy of the GNU General Public License
// along with this program; if not, write to the Free Software
// Foundation, Inc., 675 Mass Ave, Cambridge, MA 02139, USA.
//
// ============================================================================
package org.talend.administrator.common.data.collections;

import java.util.Arrays;
import java.util.Set;

import org.talend.administrator.common.util.reflect.BeanAccessorsInvoker;

/**
 * DOC mhirt class global comment. Detailled comment <br/>
 * 
 * $Id$
 * 
 */
public class DynamicHashKey extends AbstractHashKey implements IHashKeyBeanContainer {

    private Object[] values;

    private String[] accessors;

    private Object bean;

    private BeanAccessorsInvoker beanAccessorsInvoker = new BeanAccessorsInvoker();

    public DynamicHashKey(Object bean, String... accessors) {
        if (bean == null) {
            throw new NullPointerException("Bean can't be null");
        }
        this.bean = bean;
        this.accessors = accessors;

        if (accessors == null || accessors.length == 0) {
            values = new Object[1];
        } else {
            values = new Object[accessors.length];
        }
    }

    public DynamicHashKey(Object bean, Set<String> accessors) {
        this(bean, accessors == null ? null : accessors.toArray(new String[0]));
    }

    @Override
    protected Object[] getValues() {
        if (accessors == null || accessors.length == 0) {
            values[0] = bean;
        } else {
            for (int i = 0; i < accessors.length; i++) {
                String currentAccessor = accessors[i];
                if (currentAccessor == null) {
                    throw new NullPointerException("Accessos contain null properites: " + Arrays.asList(accessors));
                }
                values[i] = beanAccessorsInvoker.getBeanPropertyValue(bean, currentAccessor);
            }
        }
        return values;
    }

    public Object getBean() {
        return bean;
    }
}
