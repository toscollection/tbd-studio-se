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
import java.util.HashMap;

/**
 * DOC mhirt class global comment. Detailled comment <br/>
 * 
 * $Id$
 * 
 */
public class BeanAccessorsCacheContainer {

    private HashMap<Class, BeanAccessorsCache> classesToBeanAccessorsCache = new HashMap<Class, BeanAccessorsCache>();

    public Method getPropertyGetter(Object obj, String bean) {
        return getPropertyGetter(obj.getClass(), bean);
    }

    public Method getPropertyGetter(Class cls, String bean) {
        BeanAccessorsCache accessorCache = findClassRepresenter(cls);
        return accessorCache.getPropertyGetter(bean);
    }

    public Method getPropertySetter(Object obj, String property, Class propertyClass) {
        return getPropertySetter(obj.getClass(), property, propertyClass);
    }

    public Method getPropertySetter(Class cls, String property, Class propertyClass) {
        BeanAccessorsCache accessorCache = findClassRepresenter(cls);
        return accessorCache.getPropertySetter(property, propertyClass);
    }

    private BeanAccessorsCache findClassRepresenter(Class cls) {
        BeanAccessorsCache accessorCache = classesToBeanAccessorsCache.get(cls);
        if (accessorCache == null) {
            accessorCache = new BeanAccessorsCache(cls);
            classesToBeanAccessorsCache.put(cls, accessorCache);
        }
        return accessorCache;
    }
}
