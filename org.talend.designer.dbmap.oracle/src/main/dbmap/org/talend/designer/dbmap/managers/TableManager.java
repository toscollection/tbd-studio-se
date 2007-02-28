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
package org.talend.designer.dbmap.managers;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.eclipse.swt.widgets.Table;
import org.talend.commons.utils.data.list.ListUtils;
import org.talend.core.model.metadata.IMetadataTable;
import org.talend.designer.dbmap.model.table.AbstractDataMapTable;
import org.talend.designer.dbmap.model.table.AbstractInOutTable;
import org.talend.designer.dbmap.model.table.InputTable;
import org.talend.designer.dbmap.model.table.OutputTable;
import org.talend.designer.dbmap.model.table.VarsTable;
import org.talend.designer.dbmap.ui.visualmap.table.DataMapTableView;

/**
 * DOC amaumont class global comment. Detailled comment <br/>
 * 
 * $Id: TableManager.java 1782 2007-02-03 07:57:38Z bqian $
 * 
 */
public class TableManager {

    private Map<AbstractDataMapTable, DataMapTableView> abstractDataMapTableToView = new HashMap<AbstractDataMapTable, DataMapTableView>();

    private Map<Table, DataMapTableView> swtTableToView = new HashMap<Table, DataMapTableView>();

    private ArrayList<OutputTable> listOutputsTables = new ArrayList<OutputTable>();

    private ArrayList<InputTable> listInputsTables = new ArrayList<InputTable>();

    private ArrayList<VarsTable> listVarsTables = new ArrayList<VarsTable>();

    private Map<IMetadataTable, AbstractInOutTable> metadataTableToAbstractDataMapTable = new HashMap<IMetadataTable, AbstractInOutTable>();

    private HashMap<String, InputTable> aliasToInputTable = new HashMap<String, InputTable>();

    
    public TableManager() {
        super();
    }

    /**
     * DOC amaumont Comment method "addTable".
     * 
     * @param view
     * @param tableData
     */
    @SuppressWarnings("unchecked") //$NON-NLS-1$
    void addTable(DataMapTableView view, AbstractDataMapTable tableData) {

        if (tableData instanceof AbstractInOutTable) {
            AbstractInOutTable data = (AbstractInOutTable) tableData;
            metadataTableToAbstractDataMapTable.put(data.getMetadataTable(), data);
        }
        getMatchedList(tableData).add(tableData);
        swtTableToView.put(view.getTableViewerCreatorForColumns().getTable(), view);
        if (view.getTableViewerCreatorForFilters() != null) {
            swtTableToView.put(view.getTableViewerCreatorForFilters().getTable(), view);
        }
        abstractDataMapTableToView.put(tableData, view);
    }

    /**
     * DOC amaumont Comment method "getTable".
     */
    AbstractDataMapTable getData(DataMapTableView view) {
        return view.getDataMapTable();
    }

    /**
     * DOC amaumont Comment method "getTable".
     */
    DataMapTableView getView(AbstractDataMapTable data) {
        return abstractDataMapTableToView.get(data);
    }

    /**
     * DOC amaumont Comment method "getTable".
     */
    DataMapTableView getView(Table swtTable) {
        return swtTableToView.get(swtTable);
    }

    /**
     * DOC amaumont Comment method "removeTable".
     * 
     * @param view
     */
    AbstractDataMapTable removeTable(DataMapTableView view) {
        AbstractDataMapTable abstractDataMapTable = getData(view);
        removeTable(abstractDataMapTable);
        return abstractDataMapTable;
    }

    /**
     * DOC amaumont Comment method "removeTable".
     * 
     * @param data
     */
    Object removeTable(AbstractDataMapTable data) {
        getMatchedList(data).remove(data);
        DataMapTableView view = abstractDataMapTableToView.remove(data);
        swtTableToView.remove(view.getTableViewerCreatorForColumns().getTable());
        if (view.getTableViewerCreatorForFilters() != null) {
            swtTableToView.remove(view.getTableViewerCreatorForFilters().getTable());
        }
        return view;
    }

    /**
     * 
     * Return a copy.
     * 
     * @return
     */
    Collection<DataMapTableView> getTablesView() {
        return abstractDataMapTableToView.values();
    }

    List<DataMapTableView> getInputsTablesView() {
        return getTablesView(listInputsTables);
    }

    List<DataMapTableView> getOutputsTablesView() {
        return getTablesView(listOutputsTables);
    }

    List<DataMapTableView> getVarsTablesView() {
        return getTablesView(listVarsTables);
    }

    private List<DataMapTableView> getTablesView(List<? extends AbstractDataMapTable> listAbstractDataMapTables) {
        ArrayList<DataMapTableView> list = new ArrayList<DataMapTableView>();
        for (AbstractDataMapTable data : listAbstractDataMapTables) {
            list.add(abstractDataMapTableToView.get(data));
        }
        return list;
    }

    /**
     * 
     * Return an unmodifiable collection.
     * 
     * @return
     */
    Collection<AbstractDataMapTable> getTablesData() {
        return Collections.unmodifiableCollection(abstractDataMapTableToView.keySet());
    }

    /**
     * 
     * Return an unmodifiable list.
     * 
     * @return
     */
    List<InputTable> getInputTables() {
        return Collections.unmodifiableList(listInputsTables);
    }

    /**
     * 
     * Return an unmodifiable list.
     * 
     * @return
     */
    List<OutputTable> getOutputTables() {
        return Collections.unmodifiableList(listOutputsTables);
    }

    /**
     * 
     * Return an unmodifiable list.
     * 
     * @return
     */
    List<VarsTable> getVarsTables() {
        return Collections.unmodifiableList(listVarsTables);
    }

    AbstractInOutTable getData(IMetadataTable metadataTable) {
        return metadataTableToAbstractDataMapTable.get(metadataTable);
    }

    DataMapTableView getView(IMetadataTable metadataTable) {
        AbstractDataMapTable abstractDataMapTable = getData(metadataTable);
        if (abstractDataMapTable != null) {
            return getView(abstractDataMapTable);
        }
        return null;
    }

    /**
     * DOC amaumont Comment method "swapTableInputTables".
     * 
     * @param dataMapTable
     * @param dataMapTable2
     */
    boolean swapWithNextTable(AbstractDataMapTable dataMapTable) {
        List currentList = null;
        currentList = getMatchedList(dataMapTable);
        int indexTable = currentList.indexOf(dataMapTable);
        if (indexTable + 1 <= currentList.size() - 1) {
            Object nextTable = currentList.get(indexTable + 1);
            ListUtils.swap(currentList, dataMapTable, nextTable);
            return true;
        } else {
            return false;
        }
    }

    /**
     * DOC amaumont Comment method "swapTableInputTables".
     * 
     * @param dataMapTable
     * @param dataMapTable2
     */
    boolean swapWithPreviousTable(AbstractDataMapTable dataMapTable) {
        List currentList = null;
        currentList = getMatchedList(dataMapTable);
        int indexTable = currentList.indexOf(dataMapTable);
        if (indexTable - 1 >= 0) {
            Object nextTable = currentList.get(indexTable - 1);
            ListUtils.swap(currentList, dataMapTable, nextTable);
            return true;
        } else {
            return false;
        }
    }

    private List getMatchedList(AbstractDataMapTable dataMapTable) {
        List currentList = null;
        if (dataMapTable instanceof InputTable) {
            currentList = listInputsTables;
        } else if (dataMapTable instanceof OutputTable) {
            currentList = listOutputsTables;
        } else if (dataMapTable instanceof VarsTable) {
            currentList = listVarsTables;
        }
        return currentList;
    }

    InputTable getInputTableFromAlias(String alias) {
        return aliasToInputTable.get(alias);
    }
    
    InputTable addAlias(String alias, InputTable inputTable) {
        return aliasToInputTable.put(alias, inputTable);
    }
    
    
}
