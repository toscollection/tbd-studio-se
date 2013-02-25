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
package org.talend.designer.pigmap.commands;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.PatternSyntaxException;

import org.eclipse.emf.common.util.EList;
import org.eclipse.gef.EditPart;
import org.eclipse.gef.commands.Command;
import org.talend.designer.gefabstractmap.part.directedit.DirectEditType;
import org.talend.designer.pigmap.model.emf.pigmap.AbstractNode;
import org.talend.designer.pigmap.model.emf.pigmap.INodeConnection;
import org.talend.designer.pigmap.model.emf.pigmap.InputTable;
import org.talend.designer.pigmap.model.emf.pigmap.PigMapData;
import org.talend.designer.pigmap.model.emf.pigmap.TableNode;
import org.talend.designer.pigmap.parts.PigMapInputTablePart;
import org.talend.designer.pigmap.parts.PigMapOutputTablePart;
import org.talend.designer.pigmap.parts.PigMapTableNodePart;
import org.talend.designer.pigmap.ui.expressionutil.PigMapExpressionManager;
import org.talend.designer.pigmap.ui.expressionutil.TableEntryLocation;
import org.talend.designer.pigmap.util.PigMapUtil;

/**
 * DOC hcyi class global comment. Detailled comment
 */
public class DirectEditCommand extends Command {

    private AbstractNode model;

    private Object newValue;

    private DirectEditType type;

    private EditPart targetEditPart;

    private PigMapTableNodePart tableNodePart;

    private PigMapData mapperData;

    protected PigMapExpressionManager expressionManager = new PigMapExpressionManager();

    public DirectEditCommand() {

    }

    public DirectEditCommand(EditPart targetEditPart, AbstractNode model, DirectEditType type, Object newValue) {
        this.targetEditPart = targetEditPart;
        this.model = model;
        this.newValue = newValue;
        this.type = type;
    }

    @Override
    public void execute() {
        try {
            if (model != null) {
                if (DirectEditType.EXPRESSION.equals(type)) {
                    List<TableEntryLocation> matchedLocations = expressionManager.parseTableEntryLocation((String) newValue);
                    EList<? extends INodeConnection> connections = null;
                    //
                    if (targetEditPart != null && targetEditPart instanceof PigMapTableNodePart) {
                        tableNodePart = (PigMapTableNodePart) targetEditPart;
                        if (tableNodePart.getParent() instanceof PigMapOutputTablePart) {
                            connections = model.getIncomingConnections();
                        } else if (tableNodePart.getParent() instanceof PigMapInputTablePart) {
                            connections = ((TableNode) model).getLookupIncomingConnections();
                        }
                    }
                    List usefullConnections = new ArrayList();
                    mapperData = PigMapUtil.getPigMapData(model);
                    if (!matchedLocations.isEmpty()) {
                        for (int i = 0; i < matchedLocations.size(); i++) {
                            TableEntryLocation currentLocation = matchedLocations.get(i);
                            for (INodeConnection conn : connections) {
                                TableEntryLocation sourceLocation = null;
                                String temp = "";
                                if (conn.getSource() != null && conn.getSource() instanceof TableNode) {
                                    TableNode tableSourceNode = (TableNode) conn.getSource();
                                    if (tableSourceNode.eContainer() != null
                                            && tableSourceNode.eContainer() instanceof InputTable) {
                                        temp = ((InputTable) tableSourceNode.eContainer()).getName() + "."
                                                + conn.getSource().getName();
                                    }
                                    sourceLocation = expressionManager.parseTableEntryLocation(temp).get(0);
                                }
                                if (currentLocation.equals(sourceLocation)) {
                                    usefullConnections.add(conn);
                                    break;
                                }
                            }
                        }
                    } else {
                        if (!connections.isEmpty()) {
                            if (tableNodePart.getParent() instanceof PigMapOutputTablePart) {
                                PigMapUtil.detachConnectionsSouce(model, mapperData);
                                model.getIncomingConnections().clear();
                            } else if (tableNodePart.getParent() instanceof PigMapInputTablePart) {
                                PigMapUtil.detachLookupSource((TableNode) model, mapperData);
                                ((TableNode) model).getLookupIncomingConnections().clear();
                            }
                        }
                    }
                    List<INodeConnection> copyOfConnections = new ArrayList<INodeConnection>(connections);
                    copyOfConnections.removeAll(usefullConnections);
                    if (tableNodePart.getParent() instanceof PigMapOutputTablePart) {
                        for (INodeConnection connection : copyOfConnections) {
                            if (connection.getSource() != null) {
                                if (connection.getSource().getOutgoingConnections().contains(connection)) {
                                    connection.getSource().getOutgoingConnections().remove(connection);
                                    mapperData.getConnections().remove(connection);
                                }
                            }
                        }
                        model.getIncomingConnections().removeAll(copyOfConnections);

                    } else if (tableNodePart.getParent() instanceof PigMapInputTablePart) {
                        for (INodeConnection connection : copyOfConnections) {
                            if (connection.getSource() != null) {
                                if (((TableNode) connection.getSource()).getLookupOutgoingConnections().contains(connection)) {
                                    ((TableNode) connection.getSource()).getLookupOutgoingConnections().remove(connection);
                                    mapperData.getConnections().remove(connection);
                                }
                            }
                        }
                        ((TableNode) model).getLookupIncomingConnections().removeAll(copyOfConnections);
                    }

                    model.setExpression((String) newValue);
                } else if (DirectEditType.NODE_NAME.equals(type)) {
                    //
                }
            }

        } catch (PatternSyntaxException ex) {
            // Syntax error in the regular expression
        }
    }

    protected AbstractNode findConnectionSource(PigMapData mapperData, TableEntryLocation matchedLocation) {
        if (mapperData == null) {
            return null;
        }
        AbstractNode node = findConnectionSource(mapperData.getInputTables(), matchedLocation);
        if (node instanceof TableNode) {

        }
        return node;
    }

    private TableNode findConnectionSource(List<InputTable> inputTables, TableEntryLocation matchedLocation) {
        for (InputTable inputTable : inputTables) {
            for (TableNode node : inputTable.getNodes()) {
                TableNode source = findConnectionSource(node, matchedLocation);
                if (source != null) {
                    return source;
                }
            }
        }

        return null;
    }

    private TableNode findConnectionSource(TableNode tableNode, TableEntryLocation matchedLocation) {
        //
        return null;
    }

}
