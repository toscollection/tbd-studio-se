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

import org.talend.core.model.metadata.IMetadataColumn;
import org.talend.designer.dbmap.model.table.AbstractDataMapTable;

/**
 * DOC amaumont class global comment. Detailled comment <br/>
 * 
 * $Id: AbstractInOutTableEntry.java 898 2006-12-07 11:06:17Z amaumont $
 * 
 */
public class AbstractInOutTableEntry extends TableEntry implements IColumnEntry {

    private IMetadataColumn metadataColumn;

    public AbstractInOutTableEntry(AbstractDataMapTable abstractDataMapTable, IMetadataColumn metadataColumn, String expression) {
        super(abstractDataMapTable, expression);
        this.metadataColumn = metadataColumn;
    }

    public AbstractInOutTableEntry(AbstractDataMapTable abstractDataMapTable, IMetadataColumn metadataColumn) {
        super(abstractDataMapTable);
        this.metadataColumn = metadataColumn;
    }

    public AbstractInOutTableEntry(AbstractDataMapTable abstractDataMapTable, String name, String expression) {
        super(abstractDataMapTable, name, expression);
    }

    public IMetadataColumn getMetadataColumn() {
        return this.metadataColumn;
    }

    public String getName() {
        return this.metadataColumn.getLabel();
    }

    public void setName(String name) {
        this.metadataColumn.setLabel(name);
    }

}
