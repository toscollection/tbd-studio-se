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

import org.talend.administrator.common.data.ArraysComparator;

/**
 * DOC mhirt class global comment. Detailled comment <br/>
 * 
 * $Id$
 * 
 */
public abstract class AbstractHashKey {
    
    protected static final int HASH_CODE_INITIAL_VALUE = 1;

    private int oldHashCode = HASH_CODE_INITIAL_VALUE;

    private Object[] oldValues;

    protected abstract Object[] getValues();

    @Override
    public int hashCode() {
        Object[] values = getValues();
        // attempt to avoid recalculating
        if (oldHashCode != HASH_CODE_INITIAL_VALUE && oldValues == values) {
            if (oldValues != null && values != null) {
                boolean stillTheSameValues = true;
                for (int i = 0; i < values.length; i++) {
                    if (values[i] != oldValues[i]) {
                        stillTheSameValues = false;
                        break;
                    }
                }
                if (stillTheSameValues) {
                    return oldHashCode;
                }
            }
        }
        return recalculateHashCode(values);
    }

    private int recalculateHashCode(Object[] values) {
        oldHashCode = 1;
        for (Object current : values) {
            oldHashCode *= current != null ? current.hashCode() : 1;
        }
        return oldHashCode;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj instanceof AbstractHashKey) {
            return ArraysComparator.areEqual(getValues(), ((AbstractHashKey) obj).getValues());
        }
        return false;
    }

    @Override
    public String toString() {
        return getClass().getSimpleName() + "(" + Arrays.asList(getValues()) + ")";
    }
}
