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
import java.util.Arrays;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;

import org.talend.commons.utils.generation.CodeGenerationUtils;
import org.talend.core.language.ECodeLanguage;
import org.talend.core.language.ICodeProblemsChecker;
import org.talend.core.model.components.IODataComponent;
import org.talend.core.model.components.IODataComponentContainer;
import org.talend.core.model.metadata.IMetadataTable;
import org.talend.core.model.process.AbstractConnection;
import org.talend.core.model.process.Element;
import org.talend.core.model.process.IConnection;
import org.talend.core.model.process.IExternalNode;
import org.talend.core.model.process.INode;
import org.talend.core.model.process.Problem;
import org.talend.core.model.process.Problem.ProblemStatus;
import org.talend.designer.codegen.IAloneProcessNodeConfigurer;
import org.talend.designer.dbmap.language.IDbLanguage;
import org.talend.designer.dbmap.language.IDbOperator;
import org.talend.designer.dbmap.language.IDbOperatorManager;
import org.talend.designer.dbmap.model.tableentry.FilterTableEntry;
import org.talend.designer.dbmap.model.tableentry.IColumnEntry;
import org.talend.designer.dbmap.model.tableentry.ITableEntry;
import org.talend.designer.dbmap.model.tableentry.InputColumnTableEntry;
import org.talend.designer.dbmap.model.tableentry.OutputColumnTableEntry;
import org.talend.designer.dbmap.ui.visualmap.table.DataMapTableView;
import org.talend.designer.dbmap.ui.visualmap.zone.Zone;

/**
 * DOC amaumont class global comment. Detailled comment <br/>
 * 
 * $Id$
 * 
 */
public class ProblemsManager {

    private static final String EMPTY_STRING = ""; //$NON-NLS-1$

    private MapperManager mapperManager;

    private ICodeProblemsChecker codeChecker;

    private ECodeLanguage codeLanguage;

    private IAloneProcessNodeConfigurer nodeConfigurer;

    /**
     * DOC amaumont ProblemsManager constructor comment.
     */
    private ProblemsManager() {
        super();
    }

    /**
     * DOC amaumont ProblemsManager constructor comment.
     * 
     * @param manager
     */
    public ProblemsManager(final MapperManager mapperManager) {
        this.mapperManager = mapperManager;
        IDbLanguage currentLanguage = mapperManager.getCurrentLanguage();
        codeChecker = currentLanguage.getCodeChecker();
        this.nodeConfigurer = new IAloneProcessNodeConfigurer() {

            public void configure(INode processNode) {

                IExternalNode mapperNode = mapperManager.getComponent();
                if (processNode.getUniqueName().equals(mapperNode.getUniqueName())) {

                    IExternalNode processExternalNode = (IExternalNode) processNode;
                    processExternalNode.setExternalData(mapperNode.getExternalData());

                    IODataComponentContainer dataComponents = mapperNode.getIODataComponents();

                    List<IODataComponent> mapperInputsDataComponent = (List<IODataComponent>) dataComponents.getInputs();
                    HashMap<String, IMetadataTable> connectionNameToInputMetadataTable = new HashMap<String, IMetadataTable>();
                    for (IODataComponent dataComponent : mapperInputsDataComponent) {
                        connectionNameToInputMetadataTable.put(dataComponent.getConnection().getName(), dataComponent.getTable());
                    }
                    List<IConnection> processIncomingConnections = (List<IConnection>) processExternalNode.getIncomingConnections();
                    for (IConnection connection : processIncomingConnections) {
                        if (connection instanceof AbstractConnection) {
                            IMetadataTable metadataTable = connectionNameToInputMetadataTable.get(connection.getName());
                            ((AbstractConnection) connection).setMetadataTable(metadataTable);
                        }
                    }

                    List<IMetadataTable> metadataListOut = new ArrayList<IMetadataTable>();

                    List<IODataComponent> mapperOutputsDataComponent = (List<IODataComponent>) dataComponents.getOuputs();
                    HashMap<String, IMetadataTable> connectionNameToOutputMetadataTable = new HashMap<String, IMetadataTable>();
                    for (IODataComponent dataComponent : mapperOutputsDataComponent) {
                        connectionNameToOutputMetadataTable.put(dataComponent.getConnection().getName(), dataComponent.getTable());
                    }
                    List<IConnection> processOutgoingConnections = (List<IConnection>) processExternalNode.getOutgoingConnections();
                    for (IConnection connection : processOutgoingConnections) {
                        if (connection instanceof AbstractConnection) {
                            IMetadataTable metadataTable = connectionNameToOutputMetadataTable.get(connection.getName());
                            ((AbstractConnection) connection).setMetadataTable(metadataTable);
                            metadataListOut.add(metadataTable);
                        }
                    }
                    processExternalNode.setMetadataList(metadataListOut);

                } else {
                    throw new IllegalArgumentException("Should be the same node..."); //$NON-NLS-1$
                }

            }

        };
    }

    /**
     * 
     * Check all problems and save in cache for Java only.
     */
    public void checkProblems() {
        if (codeLanguage == ECodeLanguage.JAVA) {
            codeChecker.checkProblems(nodeConfigurer);
        }
    }

    /**
     * DOC amaumont Comment method "checkExpressionSyntax".
     * 
     * @param expression
     */
    public List<Problem> checkExpressionSyntax(String expression) {
        ICodeProblemsChecker codeChecker = mapperManager.getCurrentLanguage().getCodeChecker();
        return codeChecker.checkProblemsForExpression(expression);
    }

    /**
     * DOC amaumont Comment method "checkProblemsForAllEntries".
     * 
     * @param forceRefreshData TODO
     */
    public void checkProblemsForAllEntriesOfAllTables(boolean forceRefreshData) {
        List<DataMapTableView> tablesView = mapperManager.getUiManager().getInputsTablesView();
        tablesView.addAll(mapperManager.getUiManager().getVarsTablesView());
        tablesView.addAll(mapperManager.getUiManager().getOutputsTablesView());
        if (forceRefreshData) {
            mapperManager.getComponent().refreshMapperConnectorData();
            checkProblems();
        }
        for (DataMapTableView view : tablesView) {
            checkProblemsForAllEntries(view, false);
        }
    }

    /**
     * DOC amaumont Comment method "processAllExpressions".
     * 
     * @param forceRefreshData TODO
     */
    @SuppressWarnings("unchecked")//$NON-NLS-1$
    public void checkProblemsForAllEntries(DataMapTableView dataMapTableView, boolean forceRefreshData) {
        if (forceRefreshData) {
            mapperManager.getComponent().refreshMapperConnectorData();
            checkProblems();
        }
        List<IColumnEntry> columnsEntriesList = dataMapTableView.getDataMapTable().getColumnEntries();
        if (checkProblemsForAllEntries(columnsEntriesList)) {
            dataMapTableView.getTableViewerCreatorForColumns().getTableViewer().refresh(true);
        }
        if (dataMapTableView.getZone() == Zone.OUTPUTS) {
            List<ITableEntry> constraintEntriesList = dataMapTableView.getTableViewerCreatorForFilters().getInputList();
            if (checkProblemsForAllEntries(constraintEntriesList)) {
                dataMapTableView.getTableViewerCreatorForFilters().getTableViewer().refresh(true);
            }
        }
    }

    private boolean checkProblemsForAllEntries(List<? extends ITableEntry> entriesList) {
        boolean errorsHasChanged = false;
        for (ITableEntry entry : entriesList) {
            boolean haveProblemsBefore = entry.getProblems() != null;
            mapperManager.getProblemsManager().checkProblemsForTableEntry(entry, false);
            boolean haveProblemsAfter = entry.getProblems() != null;
            if (haveProblemsBefore != haveProblemsAfter) {
                errorsHasChanged = true;
            }
        }
        return errorsHasChanged;
    }

    public void checkProblemsForTableEntry(ITableEntry tableEntry, boolean forceRefreshData) {

        // if (forceRefreshData) {
        // mapperManager.getComponent().refreshMapperConnectorData();
        // checkProblems();
        // }

        List<Problem> problems = null;
        String expression = tableEntry.getExpression();
        if (tableEntry instanceof InputColumnTableEntry) {
            InputColumnTableEntry inputEntry = (InputColumnTableEntry) tableEntry;
            IDbOperatorManager operatorsManager = mapperManager.getCurrentLanguage().getOperatorsManager();
            IDbOperator dbOperator = operatorsManager.getOperatorFromValue(inputEntry.getOperator());

            boolean operatorIsSet = dbOperator != null;
            boolean expressionIsSet = expression != null && expression.trim().length() > 0;

            Problem problem = null;

            String errorMessage = null;
            if (!operatorIsSet && expressionIsSet) {
                errorMessage = "Operator of input entry '" + inputEntry.getName() + "' is not set";
            }
            if (operatorIsSet && !expressionIsSet && !dbOperator.isMonoOperand()) {
                errorMessage = "Expression of input entry '" + inputEntry.getParentName() + "." + inputEntry.getName() + "' is not set";
            }
            if (errorMessage != null) {
                problem = new Problem(null, errorMessage, ProblemStatus.ERROR);
                problems = Arrays.asList(problem);
            }
        } else if (tableEntry instanceof OutputColumnTableEntry) {
            String errorMessage = null;
            Problem problem = null;
            if (expression == null || EMPTY_STRING.equals(expression.trim())) {
                errorMessage = "Expression of output entry '" + tableEntry.getParentName() + "." + tableEntry.getName() + "' is not set";
            }
            if (errorMessage != null) {
                problem = new Problem(null, errorMessage, ProblemStatus.ERROR);
                problems = Arrays.asList(problem);
            }

        }
        tableEntry.setProblems(problems);

    }
}
