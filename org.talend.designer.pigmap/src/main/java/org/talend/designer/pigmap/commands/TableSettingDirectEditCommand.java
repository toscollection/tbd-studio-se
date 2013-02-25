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
import org.talend.designer.gefabstractmap.part.directedit.DirectEditType;
import org.talend.designer.pigmap.figures.tablesettings.IUILookupMode;
import org.talend.designer.pigmap.figures.tablesettings.IUIMatchingMode;
import org.talend.designer.pigmap.figures.tablesettings.PIG_MAP_LOOKUP_MODE;
import org.talend.designer.pigmap.figures.tablesettings.PIG_MAP_MATCHING_MODE;
import org.talend.designer.pigmap.figures.tablesettings.TableSettingsConstant;
import org.talend.designer.pigmap.model.emf.pigmap.AbstractInOutTable;
import org.talend.designer.pigmap.model.emf.pigmap.FilterConnection;
import org.talend.designer.pigmap.model.emf.pigmap.InputTable;
import org.talend.designer.pigmap.model.emf.pigmap.OutputTable;
import org.talend.designer.pigmap.model.emf.pigmap.PigMapData;
import org.talend.designer.pigmap.model.emf.pigmap.TableNode;
import org.talend.designer.pigmap.ui.expressionutil.TableEntryLocation;

/**
 * DOC hcyi class global comment. Detailled comment
 */
public class TableSettingDirectEditCommand extends DirectEditCommand {

    private Object model;

    private Object newValue;

    private DirectEditType type;

    public TableSettingDirectEditCommand(Object model, DirectEditType type, Object newValue) {
        this.model = model;
        this.newValue = newValue;
        this.type = type;
    }

    @Override
    public void execute() {
        try {
            if (model instanceof InputTable) {
                InputTable inputTable = (InputTable) model;
                if (type != null) {
                    switch (type) {
                    case LOOKUP_MODEL:
                        inputTable.setLookupMode(getLookupModelByLabel((String) newValue));
                        break;
                    case MATCH_MODEL:
                        inputTable.setMatchingMode(getMatchModelByLabel((String) newValue));
                        break;
                    case JOIN_MODEL:
                        if (TableSettingsConstant.INNER_JOIN.equals(newValue)) {
                            inputTable.setInnerJoin(true);
                        } else {
                            inputTable.setInnerJoin(false);
                        }
                        break;
                    case PERSISTENT_MODEL:
                        inputTable.setPersistent(Boolean.valueOf((String) newValue));
                        break;
                    case EXPRESSION_FILTER:
                        calculateFilterConnections(inputTable, (String) newValue);
                        inputTable.setExpressionFilter((String) newValue);
                        break;
                    default:
                        break;
                    }
                }
            } else if (model instanceof OutputTable) {
                OutputTable outputTable = (OutputTable) model;
                if (type != null) {
                    switch (type) {
                    case OUTPUT_REJECT:
                        outputTable.setReject(Boolean.valueOf((String) newValue));
                        break;
                    case LOOK_UP_INNER_JOIN_REJECT:
                        outputTable.setRejectInnerJoin(Boolean.valueOf((String) newValue));
                        break;
                    case EXPRESSION_FILTER:
                        calculateFilterConnections(outputTable, (String) newValue);
                        outputTable.setExpressionFilter((String) newValue);
                        break;
                    case ALL_IN_ONE:
                        outputTable.setAllInOne(Boolean.valueOf((String) newValue));
                        break;
                    case ENABLE_EMPTY_ELEMENT:
                        outputTable.setEnableEmptyElement(Boolean.valueOf((String) newValue));
                    }
                }
            }

        } catch (PatternSyntaxException ex) {
            // Syntax error in the regular expression
        }
    }

    private void calculateFilterConnections(AbstractInOutTable abstractTable, String newValue) {
        PigMapData mapperData = (PigMapData) abstractTable.eContainer();

        List<TableEntryLocation> matchedLocations = expressionManager.parseTableEntryLocation((String) newValue);
        EList<FilterConnection> connections = abstractTable.getFilterIncomingConnections();

        List usefullConnections = new ArrayList();

        if (!matchedLocations.isEmpty()) {
            for (int i = 0; i < matchedLocations.size(); i++) {
                TableEntryLocation currentLocation = matchedLocations.get(i);
                boolean found = false;
                for (FilterConnection conn : connections) {
                    TableEntryLocation sourceLocation = null;
                    if (conn.getSource() instanceof TableNode) {

                    }
                    if (currentLocation.equals(sourceLocation)) {
                        found = true;
                        usefullConnections.add(conn);
                        break;
                    }
                }
                if (!found) {
                    //
                }
            }

            List<FilterConnection> copyOfConnections = new ArrayList<FilterConnection>(connections);
            copyOfConnections.removeAll(usefullConnections);
            for (FilterConnection connection : copyOfConnections) {
                if (connection.getSource() != null) {
                    if (connection.getSource().getFilterOutGoingConnections().contains(connection)) {
                        connection.getSource().getFilterOutGoingConnections().remove(connection);
                        mapperData.getConnections().remove(connection);
                    }
                }
            }
            abstractTable.getFilterIncomingConnections().removeAll(copyOfConnections);

        } else if (!connections.isEmpty()) {

            for (FilterConnection connection : connections) {
                if (connection.getSource() != null) {
                    if (connection.getSource().getFilterOutGoingConnections().contains(connection)) {
                        connection.getSource().getFilterOutGoingConnections().remove(connection);
                        mapperData.getConnections().remove(connection);
                    }
                }
            }

            abstractTable.getFilterIncomingConnections().removeAll(connections);
        }
    }

    private String getLookupModelByLabel(String label) {
        IUILookupMode[] availableLookups = { PIG_MAP_LOOKUP_MODE.LOAD_ONCE, PIG_MAP_LOOKUP_MODE.RELOAD,
                PIG_MAP_LOOKUP_MODE.CACHE_OR_RELOAD };
        for (IUILookupMode mode : availableLookups) {
            if (mode.getLabel().equals(label.trim())) {
                return mode.toString();
            }
        }
        return "";
    }

    private String getMatchModelByLabel(String label) {
        IUIMatchingMode[] allMatchingModel = PIG_MAP_MATCHING_MODE.values();
        for (IUIMatchingMode mode : allMatchingModel) {
            if (mode.getLabel().equals(label)) {
                return mode.toString();
            }
        }
        return "";
    }

}
