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
package org.talend.administrator.common.data;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

import org.talend.administrator.common.data.collections.DynamicHashKey;
import org.talend.administrator.common.data.collections.IHashKeyBeanContainer;

/**
 * DOC mhirt class global comment. Detailled comment <br/>
 * 
 * $Id$
 * 
 */
public final class SetComparator {
    
    private SetComparator() {
        
    }

    public static <T, U> Map<T, U> intersection(Collection<T> set1, Collection<U> set2, Set<String> accessors) {
        return intersection(set1, accessors, set2, accessors);
    }

    public static <T, U> Map<T, U> intersection(Collection<T> set1, Set<String> set1Accessors, Collection<U> set2,
            Set<String> set2Accessors) {
        Map<? extends IHashKeyBeanContainer, T> set1HashObjects = createDynamicBeanComparators(set1, set1Accessors);
        Map<? extends IHashKeyBeanContainer, U> set2HashObjects = createDynamicBeanComparators(set2, set2Accessors);
        return intersection(set1HashObjects, set2HashObjects);
    }

    protected static <T, U> Map<T, U> intersection(Map<? extends IHashKeyBeanContainer, T> set1,
            Map<? extends IHashKeyBeanContainer, U> set2) {
        Map<T, U> toReturn = new HashMap<T, U>();
        for (IHashKeyBeanContainer key : set1.keySet()) {
            U pairValue = set2.get(key);
            if (pairValue != null) {
                toReturn.put(set1.get(key), pairValue);
            }
        }
        return toReturn;
    }

    private static <T> Map<DynamicHashKey, T> createDynamicBeanComparators(Collection<T> set1, Set<String> set1Accessors) {
        if (set1 == null) {
            return null;
        }
        Map<DynamicHashKey, T> toReturn = new HashMap<DynamicHashKey, T>();
        for (T object : set1) {
            DynamicHashKey key = new DynamicHashKey(object, set1Accessors);
            toReturn.put(key, object);
        }
        return toReturn;
    }

}
