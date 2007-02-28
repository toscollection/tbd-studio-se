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
import org.talend.core.model.metadata.IMetadataColumn;
import org.talend.core.model.metadata.IMetadataTable;
import org.talend.designer.dbmap.external.data.ExternalDbMapEntry;
import org.talend.designer.dbmap.external.data.ExternalDbMapTable;
import org.talend.designer.dbmap.managers.MapperManager;
import org.talend.designer.dbmap.model.tableentry.AbstractInOutTableEntry;
import org.talend.designer.dbmap.model.tableentry.FilterTableEntry;
import org.talend.designer.dbmap.model.tableentry.OutputColumnTableEntry;

/**
 * DOC amaumont class global comment. Detailled comment <br/>
 * 
 * $Id: OutputTable.java 1795 2007-02-04 04:15:56Z bqian $
 * 
 */
public class OutputTable extends AbstractInOutTable {

    protected List<FilterTableEntry> filterTableEntries = new ArrayList<FilterTableEntry>(0);

    private boolean reject;

    private boolean rejectInnerJoin;

    private ExtendedTableModel<FilterTableEntry> tableFiltersEntriesModel;

    public OutputTable(MapperManager mapperManager, IMetadataTable metadataTable, String name) {
        super(mapperManager, metadataTable, name);
        this.tableFiltersEntriesModel = new ExtendedTableModel<FilterTableEntry>(name + " : model for Filters", filterTableEntries); //$NON-NLS-1$
    }

    public void initFromExternalData(ExternalDbMapTable externalMapperTable) {
        super.initFromExternalData(externalMapperTable);
        if (externalMapperTable != null) {
            this.reject = externalMapperTable.isReject();
            this.rejectInnerJoin = externalMapperTable.isRejectInnerJoin();
            List<ExternalDbMapEntry> externalConstraintTableEntries = externalMapperTable.getCustomConditionsEntries();
            if (externalConstraintTableEntries != null) {
                for (ExternalDbMapEntry entry : externalConstraintTableEntries) {
                    FilterTableEntry filterTableEntry = new FilterTableEntry(this, entry.getName(), entry.getExpression());
//                    mapperManager.getProblemsManager().checkProblemsForTableEntry(filterTableEntry, false);
                    addFilterEntry(filterTableEntry);
                }
            }
        }
    }

    @Override
    protected AbstractInOutTableEntry getNewTableEntry(IMetadataColumn metadataColumn) {
        return new OutputColumnTableEntry(this, metadataColumn);
    }

    public boolean isReject() {
        return this.reject;
    }

    public void setReject(boolean reject) {
        this.reject = reject;
    }

    public void addFilterEntry(FilterTableEntry constraintTableEntry) {
        this.tableFiltersEntriesModel.add(constraintTableEntry);
    }

    public void addFilterEntry(FilterTableEntry constraintTableEntry, Integer index) {
        this.tableFiltersEntriesModel.add(constraintTableEntry, index);
    }

    public void removeFilterEntry(FilterTableEntry constraintTableEntry) {
        this.tableFiltersEntriesModel.remove(constraintTableEntry);
    }

    public List<FilterTableEntry> getFilterEntries() {
        return this.tableFiltersEntriesModel.getBeansList();
    }

    /**
     * Getter for rejectInnerJoin.
     * 
     * @return the rejectInnerJoin
     */
    public boolean isRejectInnerJoin() {
        return this.rejectInnerJoin;
    }

    /**
     * Sets the rejectInnerJoin.
     * 
     * @param rejectInnerJoin the rejectInnerJoin to set
     */
    public void setRejectInnerJoin(boolean rejectInnerJoin) {
        this.rejectInnerJoin = rejectInnerJoin;
    }

    /**
     * Getter for tableFiltersEntriesModel.
     * 
     * @return the tableFiltersEntriesModel
     */
    public ExtendedTableModel<FilterTableEntry> getTableFiltersEntriesModel() {
        return this.tableFiltersEntriesModel;
    }

}
