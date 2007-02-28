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

import java.util.ArrayList;
import java.util.List;

import org.eclipse.swt.widgets.TableItem;
import org.talend.core.model.metadata.IMetadataColumn;
import org.talend.designer.dbmap.model.tableentry.ITableEntry;
import org.talend.designer.dbmap.ui.visualmap.table.DataMapTableView;
import org.talend.designer.dbmap.ui.visualmap.zone.Zone;

/**
 * DOC amaumont class global comment. Detailled comment <br/>
 * 
 * $Id: DraggedData.java 898 2006-12-07 11:06:17Z amaumont $
 * 
 */
public class DraggedData {

    private List<TransferableEntry> transferableEntryList = new ArrayList<TransferableEntry>();

    private DataMapTableView dataMapTableViewSource;

    private TableItem tableItemSource;

    public DraggedData() {
        super();
    }

    /**
     * DOC amaumont Comment method "addEntry".
     */
    public void addEntry(ITableEntry tableEntry, IMetadataColumn metadataColumn, Zone zoneSourceEntry) {
        transferableEntryList.add(new TransferableEntry(tableEntry, metadataColumn, zoneSourceEntry));
    }

    public List<TransferableEntry> getTransferableEntryList() {
        return this.transferableEntryList;
    }

    /**
     * DOC amaumont Comment method "setDataMapTableViewSource".
     * 
     * @param dataMapTableViewSource
     */
    public void setDataMapTableViewSource(DataMapTableView dataMapTableViewSource) {
        this.dataMapTableViewSource = dataMapTableViewSource;
    }

    public DataMapTableView getDataMapTableViewSource() {
        return this.dataMapTableViewSource;
    }

    /**
     * DOC amaumont Comment method "setTableItemSource".
     * 
     * @param tableItemSource
     */
    public void setTableItemSource(TableItem tableItemSource) {
        this.tableItemSource = tableItemSource;
    }

    public TableItem getTableItemSource() {
        return this.tableItemSource;
    }

}
