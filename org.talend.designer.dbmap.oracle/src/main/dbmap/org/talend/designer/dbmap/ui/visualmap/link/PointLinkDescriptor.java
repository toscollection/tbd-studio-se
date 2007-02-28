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
package org.talend.designer.dbmap.ui.visualmap.link;

import org.talend.designer.dbmap.model.tableentry.ITableEntry;
import org.talend.designer.dbmap.ui.visualmap.zone.Zone;

/**
 * DOC amaumont class global comment. Detailled comment <br/>
 * 
 * $Id: PointLinkDescriptor.java 898 2006-12-07 11:06:17Z amaumont $
 * 
 */
public class PointLinkDescriptor {

    private ITableEntry tableEntry;

    private Zone zone;

    public PointLinkDescriptor(ITableEntry dataMapTableEntry, Zone zone) {
        super();
        this.tableEntry = dataMapTableEntry;
        this.zone = zone;
    }

    public ITableEntry getTableEntry() {
        return this.tableEntry;
    }

    public void setTableEntry(ITableEntry dataMapTableEntry) {
        this.tableEntry = dataMapTableEntry;
    }

    public Zone getZone() {
        return this.zone;
    }

    public void setZone(Zone zone) {
        this.zone = zone;
    }

}
