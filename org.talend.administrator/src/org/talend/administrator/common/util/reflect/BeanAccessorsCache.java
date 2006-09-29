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
package org.talend.administrator.common.util.reflect;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * DOC mhirt class global comment. Detailled comment <br/>
 * 
 * $Id$
 * 
 */
public class BeanAccessorsCache {

    private static final Object NOT_FOUND = new Object();

    private Class[] noArg = new Class[0];

    private Class beansClass;

    private HashMap<String, Object> beanToGetter = new HashMap<String, Object>();

    private HashMap<String, Object> beanToSetter = new HashMap<String, Object>();

    private boolean rememberUnfoundBeans;

    public BeanAccessorsCache(Class toExplore) {
        this(toExplore, false);
    }

    public BeanAccessorsCache(Class toExplore, boolean newRememberUnfoundBeans) {
        beansClass = toExplore;
        rememberUnfoundBeans = newRememberUnfoundBeans;
    }

    public Method getPropertyGetter(String bean) {
        Object found = beanToGetter.get(bean);
        if (found == NOT_FOUND) {
            return null;
        }
        String beanGetter = "get" + bean.substring(0, 1).toUpperCase() + bean.substring(1);

        try {
            found = beansClass.getMethod(beanGetter, noArg);
            beanToGetter.put(bean, found);

            return (Method) found;
        } catch (NoSuchMethodException e) {
            if (rememberUnfoundBeans) {
                beanToGetter.put(bean, NOT_FOUND);
            }
            return null;
        }
    }

    public Method getPropertySetter(String property, Class propertyClass) {
        Object found = beanToSetter.get(property);
        if (found == NOT_FOUND) {
            return null;
        }
        String beanSetter = "set" + property.substring(0, 1).toUpperCase() + property.substring(1);

        try {
            if (propertyClass == null) {
                Method[] methods = beansClass.getMethods();
                List<Method> matching = new ArrayList<Method>();
                for (int i = 0; i < methods.length; i++) {
                    Method current = methods[i];
                    // see if can use equals instead
                    if (current.getName().endsWith(beanSetter)) {
                        matching.add(current);
                    }
                }
                if (matching.size() == 1) {
                    found = matching.get(0);
                } else if (matching.size() > 1) {
                    throw new UnsupportedOperationException("Method " + beansClass.getName() + "." + beanSetter
                            + "() is ambigious for a call with null.");
                }
            } else {
                found = beansClass.getMethod(beanSetter, new Class[] { propertyClass });
            }
        } catch (NoSuchMethodException e) {
            if (rememberUnfoundBeans) {
                beanToSetter.put(property, NOT_FOUND);
            }

            return null;
        }

        beanToGetter.put(property, found);
        return (Method) found;
    }
}
