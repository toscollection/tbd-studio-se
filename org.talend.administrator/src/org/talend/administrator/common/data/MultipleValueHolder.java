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

import java.util.ArrayList;
import java.util.Arrays;
import java.util.StringTokenizer;

/**
 * DOC mhirt class global comment. Detailled comment <br/>
 * 
 * $Id$
 * 
 */
public class MultipleValueHolder implements Comparable<MultipleValueHolder> {
    
    public static final String SEPARATOR = ";";

    private ArrayList<String> values = new ArrayList<String>();

    public MultipleValueHolder() {
    }

    @SuppressWarnings("unchecked")
    public MultipleValueHolder(String concatenated) {
        values = MutipleValueCombiner.split(concatenated);
    }

    public MultipleValueHolder(String[] values) {
        this.values.addAll(Arrays.asList(values));
    }

    public String[] getStringArray() {
        return (String[]) values.toArray(new String[0]);
    }

    public void addValue(String value) {
        values.add(value);
    }

    public void appendValue(int index, String toAppend) {
        String val = (String) values.get(index);
        values.set(index, val == null ? toAppend.toUpperCase() : (val + toAppend).toUpperCase());
    }

    public int size() {
        return values.size();
    }

    /*
     * (non-Javadoc)
     * 
     * @see java.lang.Object#toString()
     */
    public String toString() {
        return MutipleValueCombiner.combine(getStringArray());
    }

    /*
     * (non-Javadoc)
     * 
     * @see java.lang.Comparable#compareTo(java.lang.Object)
     */
    public int compareTo(MultipleValueHolder o) {
        if (this.equals(o)) {
            return 0;
        }
        return 2;
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((this.values == null) ? 0 : this.values.hashCode());
        return result;
    }

    @Override
    public boolean equals(Object toCompare) {
        MultipleValueHolder mvhToCompare = (MultipleValueHolder) toCompare;
        if (mvhToCompare == null) {
            return false;
        }
        return Arrays.equals(getStringArray(), mvhToCompare.getStringArray());
    }
    
    

    /**
     * DOC mhirt MultipleValueHolder class global comment. Detailled comment <br/>
     * 
     * $Id$
     * 
     */
    public static final class MutipleValueCombiner {
        private MutipleValueCombiner() {
            
        }

        static String combine(String[] values) {
            StringBuffer buff = new StringBuffer();
            boolean allNull = true;
            for (int i = 0; i < values.length; i++) {
                if (values[i] != null) {
                    allNull = false;
                    buff.append(values[i] + SEPARATOR);
                }
            }
            if (allNull) {
                return null;
            }
            if (buff.length() == 0) {
                return "";
            }
            return buff.toString().substring(0, buff.length() - 1);
        }

        @SuppressWarnings("unchecked")
        static String[] splitToArray(String str) {
            return (String[]) split(str).toArray(new String[0]);
        }

        @SuppressWarnings("unchecked")
        private static ArrayList split(String str) {
            StringTokenizer strTokz = new StringTokenizer(str, SEPARATOR);
            ArrayList toReturn = new ArrayList();
            while (strTokz.hasMoreElements()) {
                toReturn.add(strTokz.nextToken());
            }
            return toReturn;
        }
    }
}
