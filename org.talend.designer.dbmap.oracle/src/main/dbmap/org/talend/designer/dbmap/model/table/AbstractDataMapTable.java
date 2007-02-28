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
package org.talend.designer.dbmap.model.table;

import java.util.ArrayList;
import java.util.List;

import org.talend.commons.ui.swt.extended.table.ExtendedTableModel;
import org.talend.designer.dbmap.external.data.ExternalDbMapTable;
import org.talend.designer.dbmap.managers.MapperManager;
import org.talend.designer.dbmap.model.tableentry.IColumnEntry;

/**
 * DOC amaumont class global comment. Detailled comment <br/>
 * 
 * $Id: AbstractDataMapTable.java 1782 2007-02-03 07:57:38Z bqian $
 * 
 */
public abstract class AbstractDataMapTable {

    protected List<IColumnEntry> dataMapTableEntries = new ArrayList<IColumnEntry>();

    protected boolean minimized;

    private String name;

    private ExtendedTableModel<IColumnEntry> tableColumnsEntriesModel;

    protected MapperManager mapperManager;

    
    
    /**
     * DOC amaumont DataMapTable constructor comment.
     * 
     * @param persistentTable
     * 
     * @param metadataTable
     * @param externalMapperTable
     */
    public AbstractDataMapTable(MapperManager mapperManager, String name) {
        this.mapperManager = mapperManager;
        this.name = name;
        if (name == null) {
            throw new IllegalArgumentException("Name's AbstractDataMapTable can't be null"); //$NON-NLS-1$
        }
        this.tableColumnsEntriesModel = new ExtendedTableModel<IColumnEntry>(name + " : model for Columns", dataMapTableEntries); //$NON-NLS-1$

    }

    /**
     * DOC amaumont Comment method "init".
     */
    protected void initFromExternalData(ExternalDbMapTable externalMapperTable) {
        if (externalMapperTable != null) {
            this.minimized = externalMapperTable.isMinimized();
        }
    }

    /**
     * 
     * @return a <code>List</code> of entries.
     */
    public List<IColumnEntry> getColumnEntries() {
        return this.tableColumnsEntriesModel.getBeansList();
    }

    public void removeColumnEntry(IColumnEntry dataMapTableEntry) {
        this.tableColumnsEntriesModel.remove(dataMapTableEntry);
    }

    public void addColumnEntry(IColumnEntry dataMapTableEntry) {
        this.tableColumnsEntriesModel.add(dataMapTableEntry);
    }

    public void addColumnEntry(IColumnEntry dataMapTableEntry, int index) {
        this.tableColumnsEntriesModel.add(dataMapTableEntry, index);
    }

    public String getName() {
        return this.name;
    }

    public boolean isMinimized() {
        return this.minimized;
    }

    public void setMinimized(boolean minimized) {
        this.minimized = minimized;
    }

    /**
     * Getter for columnsEntriesModel.
     * 
     * @return the columnsEntriesModel
     */
    public ExtendedTableModel<IColumnEntry> getTableColumnsEntriesModel() {
        return this.tableColumnsEntriesModel;
    }

    /**
     * DOC amaumont Comment method "swapElements".
     * 
     * @param indicesOrigin
     * @param listIndexTarget
     */
    public void swapColumnElements(List<Integer> indicesOrigin, List<Integer> listIndexTarget) {
        this.tableColumnsEntriesModel.swapElements(indicesOrigin, listIndexTarget);
    }

    
    /**
     * Getter for mapperManager.
     * @return the mapperManager
     */
    public MapperManager getMapperManager() {
        return this.mapperManager;
    }

    
}
