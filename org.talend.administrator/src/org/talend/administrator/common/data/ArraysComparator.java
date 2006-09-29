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

/**
 * DOC mhirt class global comment. Detailled comment <br/>
 * 
 * $Id$
 * 
 */
public final class ArraysComparator {
    private ArraysComparator() {
        
    }

    public static boolean areEqual(Object[] array1, Object[] array2) {
        if (array1 == array2) {
            return true;
        }

        if (array1 == null || array2 == null) {
            return array1 == null && array2 == null;
        }

        if (array1.length != array2.length) {
            return false;
        }

        if (!array1.getClass().equals(array2.getClass())) {
            return false;
        }

        for (int i = 0; i < array1.length; i++) {
            if ((array1[i] == null) || (array2[i] == null)) {
                if (array1[i] != array2[i]) {
                    return false;
                }
            } else {
                if (!array1[i].equals(array2[i])) {
                    return false;
                }
            }
        }
        return true;
    }
}
