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
package org.talend.designer.dbmap.ui.dnd;

import org.talend.core.model.metadata.IMetadataColumn;
import org.talend.designer.dbmap.model.tableentry.ITableEntry;
import org.talend.designer.dbmap.ui.visualmap.zone.Zone;

/**
 * DOC amaumont class global comment. Detailled comment <br/>
 * 
 * $Id: TransferableEntry.java 898 2006-12-07 11:06:17Z amaumont $
 * 
 */
public class TransferableEntry {

    private IMetadataColumn metadataColumn;

    private ITableEntry tableEntrySource;

    private Zone zoneSourceEntry;

    public TransferableEntry(ITableEntry tableEntryLocation, IMetadataColumn metadataColumn, Zone zoneSourceEntry) {
        super();
        this.tableEntrySource = tableEntryLocation;
        this.metadataColumn = metadataColumn;
        this.zoneSourceEntry = zoneSourceEntry;
    }

    public void setTableEntryLocationSource(ITableEntry tableEntryLocation) {
        this.tableEntrySource = tableEntryLocation;
    }

    public ITableEntry getTableEntrySource() {
        return this.tableEntrySource;
    }

    public IMetadataColumn getMetadataColumn() {
        return this.metadataColumn;
    }

    public void setMetadataColumn(IMetadataColumn metadataColumn) {
        this.metadataColumn = metadataColumn;
    }

    public Zone getZoneSourceEntry() {
        return this.zoneSourceEntry;
    }

    public void setZoneSourceEntry(Zone zone) {
        this.zoneSourceEntry = zone;
    }

}
