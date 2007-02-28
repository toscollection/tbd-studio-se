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
package org.talend.designer.dbmap.model.tableentry;

/**
 * DOC amaumont class global comment. Detailled comment <br/>
 * 
 * $Id: TableEntryLocation.java 1782 2007-02-03 07:57:38Z bqian $
 * 
 */
public class TableEntryLocation {

    public String tableName;

    public String columnName;

    public TableEntryLocation() {
        super();
    }

    /**
     * DOC amaumont Couple constructor comment.
     * 
     * @param tableName
     * @param columnName
     */
    public TableEntryLocation(String tableName, String columnName) {
        this.tableName = tableName;
        this.columnName = columnName;
    }

    /**
     * DOC amaumont Couple constructor comment.
     * 
     * @param tableName
     * @param columnName
     */
    public TableEntryLocation(TableEntryLocation tableEntryLocation) {
        this.tableName = tableEntryLocation.tableName;
        this.columnName = tableEntryLocation.columnName;
    }

    public String toString() {
        return "{tableName=" + this.tableName + ", columnName=" + this.columnName + "}"; //$NON-NLS-1$ //$NON-NLS-2$ //$NON-NLS-3$
    }

    public static TableEntryLocation getNewInstance(ITableEntry dataMapTableEntry) {
        return new TableEntryLocation(dataMapTableEntry.getParent().getName(), dataMapTableEntry.getName());
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((this.columnName == null) ? 0 : this.columnName.hashCode());
        result = prime * result + ((this.tableName == null) ? 0 : this.tableName.hashCode());
        return result;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final TableEntryLocation other = (TableEntryLocation) obj;
        if (this.columnName == null) {
            if (other.columnName != null) {
                return false;
            }
        } else if (!this.columnName.equals(other.columnName)) {
            return false;
        }
        if (this.tableName == null) {
            if (other.tableName != null) {
                return false;
            }
        } else if (!this.tableName.equals(other.tableName)) {
            return false;
        }
        return true;
    }

}
