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
package org.talend.designer.dbmap.ui.automap;

import java.util.HashMap;
import java.util.List;

import org.talend.designer.dbmap.language.IDbLanguage;
import org.talend.designer.dbmap.managers.MapperManager;
import org.talend.designer.dbmap.model.table.InputTable;
import org.talend.designer.dbmap.model.table.OutputTable;
import org.talend.designer.dbmap.model.tableentry.IColumnEntry;
import org.talend.designer.dbmap.ui.visualmap.table.DataMapTableView;

/**
 * DOC amaumont class global comment. Detailled comment <br/>
 * 
 * $Id$
 * 
 */
public class AutoMapper {

    private MapperManager mapperManager;

    /**
     * DOC amaumont AutoMapper constructor comment.
     * 
     * @param manager
     */
    public AutoMapper(MapperManager mapperManager) {
        this.mapperManager = mapperManager;
    }

    /**
     * DOC amaumont Comment method "map".
     */
    public void map() {
        List<InputTable> inputTables = mapperManager.getInputTables();
        List<OutputTable> outputTables = mapperManager.getOutputTables();

        IDbLanguage currentLanguage = mapperManager.getCurrentLanguage();

        HashMap<String, InputTable> nameToInputTable = new HashMap<String, InputTable>(inputTables.size());

        for (InputTable inputTable : inputTables) {
            nameToInputTable.put(inputTable.getName(), inputTable);
        }

        // output tables are the references
        for (OutputTable outputTable : outputTables) {

            List<IColumnEntry> outputEntries = outputTable.getColumnEntries();
            boolean mapFound = false;
            for (IColumnEntry outputEntry : outputEntries) {

                mapFound = false;

                if (mapperManager.checkEntryHasEmptyExpression(outputEntry)) {

                    String outputColumnName = outputEntry.getName();

                    for (InputTable inputTable : inputTables) {

                        List<IColumnEntry> inputColumnEntries = inputTable.getColumnEntries();
                        for (IColumnEntry inputEntry : inputColumnEntries) {
                            if (inputEntry.getName().equalsIgnoreCase(outputColumnName)) {
                                outputEntry.setExpression(currentLanguage.getLocation(inputTable.getName(), inputEntry.getName()));
                                mapFound = true;
                                break;
                            }
                        }
                        if (mapFound) {
                            break;
                        }

                    }

                }

            }
            DataMapTableView view = mapperManager.retrieveAbstractDataMapTableView(outputTable);
            view.getTableViewerCreatorForColumns().getTableViewer().refresh();

        }

        mapperManager.getProblemsManager().checkProblems();

        List<DataMapTableView> outputsTablesView = mapperManager.getUiManager().getOutputsTablesView();
        for (DataMapTableView view : outputsTablesView) {
            mapperManager.getUiManager().parseAllExpressions(view, true);
            mapperManager.getProblemsManager().checkProblemsForAllEntries(view, true);
        }
        mapperManager.getUiManager().refreshBackground(true, false);

    }

}
