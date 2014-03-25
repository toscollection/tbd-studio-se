// ============================================================================
//
// Copyright (C) 2006-2014 Talend Inc. - www.talend.com
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
import org.talend.designer.pigmap.model.emf.pigmap.AbstractInOutTable;
import org.talend.designer.pigmap.model.emf.pigmap.AbstractNode;
import org.talend.designer.pigmap.model.emf.pigmap.Connection;
import org.talend.designer.pigmap.model.emf.pigmap.FilterConnection;
import org.talend.designer.pigmap.model.emf.pigmap.IConnection;
import org.talend.designer.pigmap.model.emf.pigmap.InputTable;
import org.talend.designer.pigmap.model.emf.pigmap.LookupConnection;
import org.talend.designer.pigmap.model.emf.pigmap.PigMapData;
import org.talend.designer.pigmap.model.emf.pigmap.PigmapFactory;
import org.talend.designer.pigmap.model.emf.pigmap.TableNode;
import org.talend.designer.pigmap.ui.expressionutil.PigMapExpressionManager;
import org.talend.designer.pigmap.ui.expressionutil.TableEntryLocation;

/**
 * 
 * DOC hcyi class global comment. Detailled comment
 */
public class PigMapUtil {

    /********************/

    public static boolean isExpressionEditable(TableNode tableNode) {
        //
        return true;
    }

    public static PigMapData getPigMapData(AbstractNode tableNode) {
        AbstractNode rootNode = null;
        if (tableNode instanceof TableNode) {
            rootNode = tableNode;
        }
        if (rootNode != null && rootNode.eContainer() != null && rootNode.eContainer().eContainer() instanceof PigMapData) {
            return (PigMapData) rootNode.eContainer().eContainer();
        }
        return null;
    }

    public static AbstractInOutTable getAbstractInOutTable(TableNode model) {
        if (model.eContainer() instanceof AbstractInOutTable) {
            return (AbstractInOutTable) model.eContainer();
        } else if (model.eContainer() instanceof TableNode) {
            return getAbstractInOutTable((TableNode) model.eContainer());
        }
        return null;
    }

    /**** detach ****************/

    public static void detachNodeConnections(AbstractNode abstractNode, PigMapData mapData) {
        detachConnectionsSouce(abstractNode, mapData);
        detachConnectionsTarget(abstractNode, mapData);
        detachFilterTarget(abstractNode, mapData);
        if (abstractNode instanceof TableNode) {
            detachLookupSource((TableNode) abstractNode, mapData);
            detachLookupTarget((TableNode) abstractNode, mapData);
        }
    }

    public static void detachConnectionsSouce(AbstractNode tableNode, PigMapData mapData) {
        for (Connection connection : tableNode.getIncomingConnections()) {
            AbstractNode source = connection.getSource();
            if (source != null && source.getOutgoingConnections().contains(connection)) {
                source.getOutgoingConnections().remove(connection);
                mapData.getConnections().remove(connection);
            }
        }
        tableNode.getIncomingConnections().clear();
        tableNode.setExpression("");
    }

    public static void detachConnectionsTarget(AbstractNode tableNode, PigMapData mapData) {
        for (Connection connection : tableNode.getOutgoingConnections()) {
            AbstractNode target = connection.getTarget();
            if (target != null && target.getIncomingConnections().contains(connection)) {
                target.getIncomingConnections().remove(connection);
                mapData.getConnections().remove(connection);
            }
        }
        tableNode.getOutgoingConnections().clear();
    }

    public static void detachFilterSource(AbstractInOutTable table, PigMapData mapData) {
        for (FilterConnection connection : table.getFilterIncomingConnections()) {
            if (connection.getSource() != null) {
                if (connection.getSource().getFilterOutGoingConnections().contains(connection)) {
                    connection.getSource().getFilterOutGoingConnections().remove(connection);
                    mapData.getConnections().remove(connection);
                }
            }
        }
        table.getFilterIncomingConnections().clear();
    }

    public static void detachFilterTarget(AbstractNode abstractNode, PigMapData mapData) {
        for (FilterConnection connection : abstractNode.getFilterOutGoingConnections()) {
            AbstractInOutTable target = connection.getTarget();
            if (target != null && target.getFilterIncomingConnections().contains(connection)) {
                target.getFilterIncomingConnections().remove(connection);
                mapData.getConnections().remove(connection);
            }
        }
        abstractNode.getFilterOutGoingConnections().clear();
    }

    public static void detachLookupSource(TableNode tableNode, PigMapData mapData) {
        for (LookupConnection connection : tableNode.getLookupIncomingConnections()) {
            TableNode source = (TableNode) connection.getSource();
            if (source != null && source.getLookupOutgoingConnections().contains(connection)) {
                source.getLookupOutgoingConnections().remove(connection);
                mapData.getConnections().remove(connection);
            }
        }
        tableNode.getLookupIncomingConnections().clear();
    }

    public static void detachLookupTarget(TableNode tableNode, PigMapData mapData) {
        for (LookupConnection connection : tableNode.getLookupOutgoingConnections()) {
            if (connection.getTarget() instanceof TableNode) {
                TableNode target = (TableNode) connection.getTarget();
                if (target != null && target.getLookupIncomingConnections().contains(connection)) {
                    target.getLookupIncomingConnections().remove(connection);
                    mapData.getConnections().remove(connection);
                }
            }
        }
        tableNode.getLookupOutgoingConnections().clear();
    }

    /**** create ****************/

    public static void createConnection(AbstractNode sourceNode, AbstractNode targetNode, PigMapData mapData) {
        Connection conn = PigmapFactory.eINSTANCE.createConnection();
        conn.setSource(sourceNode);
        conn.setTarget(targetNode);
        if (targetNode != null && targetNode.getIncomingConnections() != null) {
            targetNode.getIncomingConnections().add(conn);
        }
        if (sourceNode != null && sourceNode.getOutgoingConnections() != null) {
            sourceNode.getOutgoingConnections().add(conn);
        }
        if (mapData != null) {
            mapData.getConnections().add(conn);
        }
    }

    public static void createLookupConnection(TableNode sourceNode, TableNode targetNode, PigMapData mapData) {
        LookupConnection conn = PigmapFactory.eINSTANCE.createLookupConnection();
        conn.setSource(sourceNode);
        conn.setTarget(targetNode);
        if (targetNode != null && targetNode.getLookupIncomingConnections() != null) {
            targetNode.getLookupIncomingConnections().add(conn);
        }
        if (sourceNode != null && sourceNode.getLookupOutgoingConnections() != null) {
            sourceNode.getLookupOutgoingConnections().add(conn);
        }
        if (mapData != null) {
            mapData.getConnections().add(conn);
        }
    }

    /********************/

    public static List<IConnection> getAllNodeLookConnections(AbstractInOutTable abstractTable) {
        List<IConnection> connections = new ArrayList<IConnection>();
        if (abstractTable != null && abstractTable instanceof InputTable) {
            for (TableNode node : ((InputTable) abstractTable).getNodes()) {
                EList<LookupConnection> outgoingConnections = node.getLookupOutgoingConnections();
                connections.addAll(outgoingConnections);
            }
        }
        return connections;
    }

    public static List<IConnection> getInputTableFilterConnections(AbstractInOutTable abstractTable) {
        List<IConnection> connections = new ArrayList<IConnection>();
        if (abstractTable != null && abstractTable instanceof InputTable) {
            for (TableNode node : ((InputTable) abstractTable).getNodes()) {
                EList<FilterConnection> outgoingConnections = node.getFilterOutGoingConnections();
                for (FilterConnection conn : outgoingConnections) {
                    if (conn.getTarget() != null && conn.getTarget() instanceof InputTable) {
                        connections.add(conn);
                    }
                }
            }
        }
        return connections;
    }

    /**
     * DOC talend2 Comment method "updateExpression".
     * @param oldName
     * @param newName
     * @param externalEmfData 
     * @param expressionManager 
     */
    public static void updateExpression(String oldName, String newName, PigMapData externalEmfData, PigMapExpressionManager expressionManager) {
        List<AbstractInOutTable> tables = new ArrayList<AbstractInOutTable>(externalEmfData.getInputTables());
        tables.addAll(new ArrayList<AbstractInOutTable>(externalEmfData.getOutputTables()));
        
        for(AbstractInOutTable table : tables) {
            
            String newExpressionFilter = replaceExpression(oldName, newName, table.getExpressionFilter(), expressionManager);
            
            if (newExpressionFilter != null) {
                table.setExpressionFilter(newExpressionFilter);
            }
            
            List<TableNode>  nodes = table.getNodes();
            if(nodes!=null) {
                for(TableNode node : nodes) {
                    String newExpression = replaceExpression(oldName, newName, node.getExpression(), expressionManager);
                    if (newExpression != null) {
                        node.setExpression(newExpression);
                    }
                }
            }
            
        }
    }

    /**
     * DOC talend2 Comment method "replaceExpression".
     * @param oldName
     * @param newName
     * @param expressionFilter
     * @param expressionManager
     * @return
     */
    private static String replaceExpression(String oldName, String newName, String currentExpression,
            PigMapExpressionManager expressionManager) {
        
        if (currentExpression == null || currentExpression.trim().length() == 0) {
            return null;
        }
        
        List<TableEntryLocation> tableEntryLocations = expressionManager.parseTableEntryLocation(currentExpression);
        
        TableEntryLocation oldLocation = new TableEntryLocation(oldName);
        TableEntryLocation newLocation = new TableEntryLocation(newName);
        
        for (int i = 0; i < tableEntryLocations.size(); i++) {
            TableEntryLocation currentLocation = tableEntryLocations.get(i);
            if (oldLocation.getTableName().equals(currentLocation.getTableName())) {
                oldLocation.setColumnValue(currentLocation.getColumnValue());
                newLocation.setColumnValue(currentLocation.getColumnValue());
            }
            
            if (currentLocation.equals(oldLocation)) {
                currentExpression = expressionManager.replaceExpression(currentExpression, currentLocation, newLocation);
            }
        }
        
        return currentExpression;
        
    }
    
}
