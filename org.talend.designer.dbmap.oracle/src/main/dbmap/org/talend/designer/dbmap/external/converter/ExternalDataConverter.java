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
package org.talend.designer.dbmap.external.converter;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.talend.core.model.metadata.IMetadataTable;
import org.talend.core.model.process.EConnectionType;
import org.talend.designer.dbmap.external.connection.IOConnection;
import org.talend.designer.dbmap.external.data.ExternalDbMapData;
import org.talend.designer.dbmap.external.data.ExternalDbMapEntry;
import org.talend.designer.dbmap.external.data.ExternalDbMapTable;
import org.talend.designer.dbmap.external.data.ExternalDbMapUiProperties;
import org.talend.designer.dbmap.managers.MapperManager;
import org.talend.designer.dbmap.model.MapperModel;
import org.talend.designer.dbmap.model.table.AbstractDataMapTable;
import org.talend.designer.dbmap.model.table.InputTable;
import org.talend.designer.dbmap.model.table.OutputTable;
import org.talend.designer.dbmap.model.table.VarsTable;
import org.talend.designer.dbmap.model.tableentry.FilterTableEntry;
import org.talend.designer.dbmap.model.tableentry.ITableEntry;
import org.talend.designer.dbmap.model.tableentry.InputColumnTableEntry;

/**
 * Convert external data to internal data and conversely.
 * 
 * $Id: ExternalDataConverter.java 1744 2007-01-31 13:05:30Z amaumont $
 * 
 */
public class ExternalDataConverter {

    private ArrayList<ExternalDbMapTable> inputTables;

    private ArrayList<ExternalDbMapTable> outputTables;

    private ArrayList<ExternalDbMapTable> varsTables;

    private MapperManager mapperManager;

    /**
     * DOC amaumont ExternalDataConverter constructor comment.
     * 
     * @param main
     */
    public ExternalDataConverter(MapperManager mapperManager) {
        super();
        this.mapperManager = mapperManager;
    }

    /**
     * Prepare internal data from connections and external data.
     * 
     * @param inputConnections
     * @param outputConnections
     * @param outputMetadataTables
     * @param externalData
     * @param checkProblems
     */
    public MapperModel prepareModel(List<IOConnection> inputs, List<IOConnection> outputs, List<IMetadataTable> outputMetadataTables,
            ExternalDbMapData externalData, boolean checkProblems) {

        if (checkProblems) {
            mapperManager.getProblemsManager().checkProblems();
        }

        ArrayList<InputTable> inputDataMapTables = prepareInputTables(inputs, externalData);

        ArrayList<OutputTable> outputDataMapTables = prepareOutputTables(outputs, outputMetadataTables, externalData);

        List<VarsTable> varsTablesList = prepareVarsTables(externalData);

        return new MapperModel(inputDataMapTables, outputDataMapTables, varsTablesList);

    }

    private List<VarsTable> prepareVarsTables(ExternalDbMapData externalData) {
        List<VarsTable> varsTablesList = new ArrayList<VarsTable>();
        if (externalData != null) {
            List<ExternalDbMapTable> varsExternalTables = externalData.getVarsTables();
            for (ExternalDbMapTable persistentTable : varsExternalTables) {
                VarsTable varsTable = new VarsTable(this.mapperManager, persistentTable.getName());
                varsTable.initFromExternalData(persistentTable);
                varsTablesList.add(varsTable);
            }
        }
        if (varsTablesList.size() == 0) {
            VarsTable varsTable = new VarsTable(this.mapperManager, VarsTable.PREFIX_VARS_TABLE_NAME);
            varsTable.setMinimized(true);
            varsTablesList.add(varsTable);
        }
        return varsTablesList;
    }

    private ArrayList<OutputTable> prepareOutputTables(List<IOConnection> outputConnections, List<IMetadataTable> outputMetadataTables,
            ExternalDbMapData externalData) {
        Map<String, ExternalDbMapTable> nameToOutpuPersistentTable = new HashMap<String, ExternalDbMapTable>();
        if (externalData != null) {
            for (ExternalDbMapTable persistentTable : externalData.getOutputTables()) {
                nameToOutpuPersistentTable.put(persistentTable.getName(), persistentTable);
            }
        }
        Map<String, IOConnection> nameMetadataToOutpuConn = new HashMap<String, IOConnection>();
        if (outputConnections != null) {
            for (IOConnection connection : outputConnections) {
                if (connection.getConnectionType().equals(EConnectionType.FLOW_MAIN)
                        || connection.getConnectionType().equals(EConnectionType.FLOW_REF)) {
                    nameMetadataToOutpuConn.put(connection.getTable().getTableName(), connection);
                }
            }
        }
        ArrayList<OutputTable> outputDataMapTables = new ArrayList<OutputTable>();
        for (IMetadataTable table : outputMetadataTables) {
            IOConnection connection = nameMetadataToOutpuConn.get(table.getTableName());
            OutputTable outputTable = null;
            if (connection != null) {
                ExternalDbMapTable persistentTable = nameToOutpuPersistentTable.get(connection.getName());
                outputTable = new OutputTable(this.mapperManager, connection.getTable(), connection.getName());
                outputTable.initFromExternalData(persistentTable);
            } else {
                ExternalDbMapTable persistentTable = nameToOutpuPersistentTable.get(table.getTableName());
                outputTable = new OutputTable(this.mapperManager, table, table.getTableName());
                outputTable.initFromExternalData(persistentTable);
            }
            outputDataMapTables.add(outputTable);
        }
        return outputDataMapTables;
    }

    public ArrayList<InputTable> prepareInputTables(List<IOConnection> inputConnections, ExternalDbMapData externalData) {
        // Map<String, ExternalMapperTable> nameToInputPersistentTable = new HashMap<String, ExternalMapperTable>();
        // if (externalData != null) {
        // for (ExternalMapperTable persistentTable : externalData.getInputTables()) {
        // nameToInputPersistentTable.put(persistentTable.getName(), persistentTable);
        // }
        // }

        Map<String, IOConnection> nameToConnection = new HashMap<String, IOConnection>();
        for (IOConnection connection : inputConnections) {
            nameToConnection.put(connection.getName(), connection);
        }

        ArrayList<InputTable> inputDataMapTables = new ArrayList<InputTable>();
        if (externalData == null || externalData.getInputTables().size() == 0) {
            for (IOConnection connection : inputConnections) {
                InputTable inputTable = new InputTable(this.mapperManager, connection, connection.getName());
                inputTable.initFromExternalData(null);
                inputDataMapTables.add(inputTable);
            }

        } else {
            for (ExternalDbMapTable persistentTable : externalData.getInputTables()) {
                String name = persistentTable.getTableName() != null ? persistentTable.getTableName() : persistentTable.getName();
                IOConnection connection = nameToConnection.get(name);
                if (connection != null) {
                    InputTable inputTable = new InputTable(this.mapperManager, connection, connection.getName());
                    inputTable.initFromExternalData(persistentTable);
                    inputDataMapTables.add(inputTable);
                }
            }

        }

//        for (IOConnection connection : remainingConnections) {
//            inputDataMapTables.add(new InputTable(this.mapperManager, connection, connection.getName()));
//        }

        // sort for put table with main connection at top position of the list
        Collections.sort(inputDataMapTables, new Comparator<InputTable>() {

            public int compare(InputTable o1, InputTable o2) {
                if (o1.isMainConnection()) {
                    return -1;
                } else if (o2.isMainConnection()) {
                    return 1;
                }
                return 0;
            }
        });
        return inputDataMapTables;
    }

    /**
     * 
     * Prepare ExternalMapperData object from internal data.
     * 
     * @param mapperModel
     * @return
     */
    public ExternalDbMapData prepareExternalData(MapperModel mapperModel, ExternalDbMapUiProperties uiProperties) {
        ExternalDbMapData externalData = new ExternalDbMapData();
        inputTables = new ArrayList<ExternalDbMapTable>();
        externalData.setInputTables(inputTables);
        outputTables = new ArrayList<ExternalDbMapTable>();
        externalData.setOutputTables(outputTables);
        varsTables = new ArrayList<ExternalDbMapTable>();
        externalData.setVarsTables(varsTables);
        loadInExternalData(mapperModel.getInputDataMapTables());
        loadInExternalData(mapperModel.getVarsDataMapTables());
        loadInExternalData(mapperModel.getOutputDataMapTables());
        externalData.setUiProperties(uiProperties);
        return externalData;
    }

    private void loadInExternalData(Collection<? extends AbstractDataMapTable> tables) {
        for (AbstractDataMapTable table : tables) {
            ExternalDbMapTable externalMapperTable = new ExternalDbMapTable();
            fillExternalTable(table, externalMapperTable);
            ArrayList<ExternalDbMapEntry> perTableEntries = new ArrayList<ExternalDbMapEntry>();
            boolean isInputTable = table instanceof InputTable;
            for (ITableEntry dataMapTableEntry : table.getColumnEntries()) {
                ExternalDbMapEntry externalMapperTableEntry = new ExternalDbMapEntry();
                externalMapperTableEntry.setExpression(dataMapTableEntry.getExpression());
                externalMapperTableEntry.setName(dataMapTableEntry.getName());
                if (isInputTable) {
                    externalMapperTableEntry.setOperator(((InputColumnTableEntry) dataMapTableEntry).getOperator());
                    externalMapperTableEntry.setJoin(((InputColumnTableEntry) dataMapTableEntry).isJoin());
                }
                perTableEntries.add(externalMapperTableEntry);
            }
            externalMapperTable.setMetadataTableEntries(perTableEntries);

        }
    }

    /**
     * DOC amaumont Comment method "processToExternal".
     * 
     * @param table
     * @param externalMapperTable
     */
    private void fillExternalTable(AbstractDataMapTable table, ExternalDbMapTable externalMapperTable) {
        if (table instanceof InputTable) {
            fillExternalTable((InputTable) table, externalMapperTable);
        } else if (table instanceof VarsTable) {
            fillExternalTable((VarsTable) table, externalMapperTable);
        } else if (table instanceof OutputTable) {
            fillExternalTable((OutputTable) table, externalMapperTable);
        }
    }

    /**
     * 
     * DOC amaumont Comment method "fillExternalTable".
     * 
     * @param table
     * @param externalMapperTable
     */
    private void fillExternalTable(InputTable table, ExternalDbMapTable externalMapperTable) {
        externalMapperTable.setJoinType(table.getJoinType().toString());
        externalMapperTable.setAlias(table.getAlias());
        externalMapperTable.setTableName(table.getTableName());
//        externalMapperTable.setJoinWithPrevious(table.isJoinWithPrevious());
        fillExternalTableWithCommonsData(table, externalMapperTable);
        inputTables.add(externalMapperTable);
    }

    /**
     * DOC amaumont Comment method "fillCommonsDataForExternalTable".
     * 
     * @param table
     * @param externalMapperTable
     */
    private void fillExternalTableWithCommonsData(AbstractDataMapTable table, ExternalDbMapTable externalMapperTable) {
        externalMapperTable.setName(table.getName());
        externalMapperTable.setMinimized(table.isMinimized());
    }

    /**
     * DOC amaumont Comment method "process".
     * 
     * @param externalMapperTable
     * @param table
     */
    private void fillExternalTable(OutputTable table, ExternalDbMapTable externalMapperTable) {
        fillExternalTableWithCommonsData(table, externalMapperTable);
        externalMapperTable.setReject(table.isReject());
        externalMapperTable.setRejectInnerJoin(table.isRejectInnerJoin());
        ArrayList<ExternalDbMapEntry> constraintTableEntries = new ArrayList<ExternalDbMapEntry>();
        for (FilterTableEntry constraintTableEntry : table.getFilterEntries()) {
            ExternalDbMapEntry externalMapperTableEntry = new ExternalDbMapEntry();
            externalMapperTableEntry.setExpression(constraintTableEntry.getExpression());
            externalMapperTableEntry.setName(constraintTableEntry.getName());
            constraintTableEntries.add(externalMapperTableEntry);
        }
        externalMapperTable.setCustomConditionsEntries(constraintTableEntries);
        outputTables.add(externalMapperTable);
    }

    /**
     * 
     * DOC amaumont Comment method "fillExternalTable".
     * 
     * @param table
     * @param externalMapperTable
     */
    private void fillExternalTable(VarsTable table, ExternalDbMapTable externalMapperTable) {
        fillExternalTableWithCommonsData(table, externalMapperTable);
        varsTables.add(externalMapperTable);
    }

}
