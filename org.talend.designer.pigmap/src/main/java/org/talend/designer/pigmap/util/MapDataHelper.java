// ============================================================================
//
// Copyright (C) 2006-2013 Talend Inc. - www.talend.com
//
// This source code is available under agreement available at
// %InstallDIR%\features\org.talend.rcp.branding.%PRODUCTNAME%\%PRODUCTNAME%license.txt
//
// You should have received a copy of the agreement
// along with this program; if not, write to Talend SA
// 9 rue Pages 92150 Suresnes, France
//
// ============================================================================
package org.talend.designer.pigmap.util;

import java.util.ArrayList;
import java.util.List;

import org.eclipse.emf.common.util.EList;
import org.talend.core.model.metadata.IMetadataColumn;
import org.talend.core.model.metadata.IMetadataTable;
import org.talend.core.model.process.EConnectionType;
import org.talend.core.model.process.IConnection;
import org.talend.designer.pigmap.PigMapComponent;
import org.talend.designer.pigmap.figures.tablesettings.PIG_MAP_JOIN_OPTIMIZATION;
import org.talend.designer.pigmap.figures.tablesettings.TableSettingsConstant;
import org.talend.designer.pigmap.model.emf.pigmap.InputTable;
import org.talend.designer.pigmap.model.emf.pigmap.OutputTable;
import org.talend.designer.pigmap.model.emf.pigmap.PigMapData;
import org.talend.designer.pigmap.model.emf.pigmap.PigmapFactory;
import org.talend.designer.pigmap.model.emf.pigmap.TableNode;

/**
 * DOC hcyi class global comment. Detailled comment
 */
public class MapDataHelper {

    /**
     * 
     * DOC hcyi Comment method "rebuildXmlMapData".
     * 
     * @param mapData
     * @param pigMapComponent
     */
    public void rebuildXmlMapData(PigMapData mapData, PigMapComponent pigMapComponent) {
        if (pigMapComponent != null) {
            rebuildModelInputs(pigMapComponent.getIncomingConnections(), mapData);
            rebuildModelOutputs(pigMapComponent.getMetadataList(), mapData);
        }
    }

    public void rebuildModelInputs(List<? extends IConnection> inputConn, PigMapData mapData) {
        // remove no used input table
        if (mapData.getInputTables().size() != inputConn.size()) {
            List tableToRemove = new ArrayList();
            for (InputTable inputTable : mapData.getInputTables()) {
                boolean found = false;
                for (IConnection connection : inputConn) {
                    if (inputTable.getName().equals(connection.getName())) {
                        found = true;
                    }
                }
                if (!found) {
                    for (TableNode tableNode : inputTable.getNodes()) {
                        PigMapUtil.detachNodeConnections(tableNode, mapData);
                    }
                    tableToRemove.add(inputTable);
                    PigMapUtil.detachFilterSource(inputTable, mapData);
                }
            }
            mapData.getInputTables().removeAll(tableToRemove);
        }

        for (IConnection inData : inputConn) {
            String name = inData.getName();
            InputTable inputTable = null;
            for (InputTable in : mapData.getInputTables()) {
                if (in.getName() != null && in.getName().equals(name)) {
                    inputTable = in;
                    break;
                }
            }
            if (inputTable == null) {
                inputTable = PigmapFactory.eINSTANCE.createInputTable();
                inputTable.setName(name);
                inputTable.setLookup(EConnectionType.FLOW_MAIN != inData.getLineStyle());
                mapData.getInputTables().add(inputTable.isLookup() ? mapData.getInputTables().size() : 0, inputTable);
            } else {
                inputTable.setLookup(EConnectionType.FLOW_MAIN != inData.getLineStyle());
            }
            // by default
            if (inputTable.isLookup() && inputTable.getJoinModel() == null) {
                inputTable.setJoinModel(TableSettingsConstant.LEFT_OUTER_JOIN);
            }
            if (inputTable.isLookup() && inputTable.getJoinOptimization() == null) {
                inputTable.setJoinOptimization(PIG_MAP_JOIN_OPTIMIZATION.NONE.toString());
            }
            rebuildInputTable(inputTable, inData.getMetadataTable(), mapData);
        }
    }

    public void rebuildInputTable(InputTable inputTable, IMetadataTable metadataTable, PigMapData mapData) {
        if (metadataTable != null && metadataTable.getListColumns() != null) {
            List<IMetadataColumn> listColumns = metadataTable.getListColumns();
            EList<TableNode> nodes = inputTable.getNodes();
            for (int i = 0; i < listColumns.size(); i++) {
                IMetadataColumn column = listColumns.get(i);
                TableNode found = null;
                int j = 0;
                for (; j < nodes.size(); j++) {
                    TableNode node = nodes.get(j);
                    if (node.getName() != null && node.getName().equals(column.getLabel())) {
                        found = node;
                        break;
                    }
                }
                if (found != null) {
                    // set in case talend type changed in metadata
                    found.setType(column.getTalendType());
                    if (i != j) {
                        // do switch to keep the same sequence
                        TableNode temp = nodes.get(j);
                        nodes.remove(j);
                        nodes.add(i, temp);
                    }
                } else {
                    found = PigmapFactory.eINSTANCE.createTableNode();
                    found.setName(column.getLabel());
                    found.setType(column.getTalendType());
                    found.setNullable(column.isNullable());
                    nodes.add(i, found);
                }
            }

            if (nodes.size() > listColumns.size()) {
                List unUsed = new ArrayList();
                for (int i = listColumns.size(); i < nodes.size(); i++) {
                    PigMapUtil.detachNodeConnections(nodes.get(i), mapData);
                    unUsed.add(nodes.get(i));
                }
                nodes.removeAll(unUsed);
            }
        }

        // re-build the connections in case any unnecessary connections are created because of previous bugs and can't
        // be deleted
        if (inputTable.isLookup()) {
            rebuildInputNodesConnections(inputTable.getNodes(), mapData);
        }
    }

    private void rebuildInputNodesConnections(List<TableNode> tableNodes, PigMapData mapData) {
        for (TableNode node : tableNodes) {
            if (node != null) {
                if (!node.getLookupIncomingConnections().isEmpty()) {
                    if (!PigMapUtil.isExpressionEditable(node)) {
                        node.setExpression("");
                        PigMapUtil.detachLookupSource(node, mapData);
                    }
                }
            }
        }
    }

    public void rebuildModelOutputs(List<IMetadataTable> outputMetadataTables, PigMapData mapData) {
        for (IMetadataTable meatadataTable : outputMetadataTables) {
            String name = meatadataTable.getTableName();
            OutputTable outputTable = null;
            for (OutputTable out : mapData.getOutputTables()) {
                if (out.getName() != null && out.getName().equals(name)) {
                    outputTable = out;
                    break;
                }
            }
            if (outputTable == null) {
                outputTable = PigmapFactory.eINSTANCE.createOutputTable();
                outputTable.setName(name);
                mapData.getOutputTables().add(outputTable);
            }

            List<IMetadataColumn> listColumns = meatadataTable.getListColumns();
            if (listColumns != null) {
                EList<TableNode> nodes = outputTable.getNodes();
                for (int i = 0; i < listColumns.size(); i++) {
                    IMetadataColumn column = listColumns.get(i);
                    TableNode found = null;
                    int j = 0;
                    for (; j < nodes.size(); j++) {
                        TableNode node = nodes.get(j);
                        if (node.getName() != null && node.getName().equals(column.getLabel())) {
                            found = node;
                            break;
                        }
                    }
                    if (found != null) {
                        // set in case talend type changed in metadata
                        found.setType(column.getTalendType());
                        if (i != j) {
                            // do switch to keep the same sequence
                            TableNode temp = nodes.get(j);
                            nodes.remove(j);
                            nodes.add(i, temp);
                        }
                    } else {
                        found = PigmapFactory.eINSTANCE.createTableNode();
                        found.setName(column.getLabel());
                        found.setType(column.getTalendType());
                        found.setNullable(column.isNullable());

                        nodes.add(i, found);
                    }

                }

                if (nodes.size() > listColumns.size()) {
                    List unUsed = new ArrayList();
                    for (int i = listColumns.size(); i < nodes.size(); i++) {
                        TableNode node = nodes.get(i);
                        PigMapUtil.detachConnectionsSouce(node, mapData);
                        unUsed.add(node);
                    }
                    nodes.removeAll(unUsed);
                }

            }
            mapData.getOutputTables().add(outputTable);

            // re-build the connections in case any unnecessary connections are created because of previous bugs and
            // can't be deleted
            rebuildOutputNodesConnections(outputTable.getNodes(), mapData);
        }
    }

    private void rebuildOutputNodesConnections(List<? extends TableNode> tableNodes, PigMapData mapData) {
        for (TableNode node : tableNodes) {
            if (node != null) {
                if (!node.getIncomingConnections().isEmpty()) {
                    if (!PigMapUtil.isExpressionEditable(node)) {
                        node.setExpression("");
                        PigMapUtil.detachConnectionsSouce(node, mapData);
                    }
                }
            }
        }
    }
}
