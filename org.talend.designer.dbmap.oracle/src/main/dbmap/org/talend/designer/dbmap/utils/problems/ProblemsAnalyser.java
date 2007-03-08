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
package org.talend.designer.dbmap.utils.problems;

import java.util.ArrayList;
import java.util.List;

import org.talend.core.language.ECodeLanguage;
import org.talend.core.language.ICodeProblemsChecker;
import org.talend.core.model.process.IConnection;
import org.talend.core.model.process.Problem;
import org.talend.core.model.process.Problem.ProblemStatus;
import org.talend.designer.dbmap.MapperMain;
import org.talend.designer.dbmap.external.connection.IOConnection;
import org.talend.designer.dbmap.external.converter.ExternalDataConverter;
import org.talend.designer.dbmap.external.data.ExternalDbMapData;
import org.talend.designer.dbmap.external.data.ExternalDbMapEntry;
import org.talend.designer.dbmap.external.data.ExternalDbMapTable;
import org.talend.designer.dbmap.language.IDbLanguage;
import org.talend.designer.dbmap.managers.MapperManager;
import org.talend.designer.dbmap.model.table.InputTable;
import org.talend.designer.dbmap.model.tableentry.IColumnEntry;
import org.talend.designer.dbmap.model.tableentry.InputColumnTableEntry;

/**
 * DOC amaumont class global comment. Detailled comment <br/>
 * 
 * $Id: ProblemsAnalyser.java 1877 2007-02-06 17:16:43Z amaumont $
 * 
 */
public class ProblemsAnalyser {

    private MapperManager mapperManager;

    private List<Problem> problems = new ArrayList<Problem>();

    /**
     * DOC amaumont ProblemsAnalyser constructor comment.
     */
    public ProblemsAnalyser(MapperManager mapperManager) {
        super();
        this.mapperManager = mapperManager;
    }

    public List<Problem> checkProblems(ExternalDbMapData externalData) {

        problems.clear();

        return null;
        
//        if (externalData != null) {
//
//            List<ExternalDbMapTable> extInputTables = new ArrayList<ExternalDbMapTable>(externalData.getInputTables());
//            List<ExternalDbMapTable> extVarTables = new ArrayList<ExternalDbMapTable>(externalData.getVarsTables());
//            List<ExternalDbMapTable> extOutputTables = new ArrayList<ExternalDbMapTable>(externalData.getOutputTables());
//            // loop on all tables
//
//            ICodeProblemsChecker codeChecker = this.mapperManager.getCurrentLanguage().getCodeChecker();
//            IDbLanguage currentLanguage = this.mapperManager.getCurrentLanguage();
//            if (currentLanguage.getCodeLanguage() == ECodeLanguage.JAVA) {
//                codeChecker.checkProblems(null);
//            }
//
//            checkExpressionSyntaxProblems(extInputTables, codeChecker);
//            checkExpressionSyntaxProblems(extVarTables, codeChecker);
//            checkExpressionSyntaxProblems(extOutputTables, codeChecker);
//
//            List<? extends IConnection> incomingConnections = new ArrayList<IConnection>(this.mapperManager.getComponent()
//                    .getIncomingConnections());
//            ExternalDataConverter converter = new ExternalDataConverter(mapperManager);
//            MapperMain mapperMain = mapperManager.getComponent().getMapperMain();
//            ArrayList<IOConnection> inputsIOConnections = mapperMain.createIOConnections(incomingConnections);
//            ArrayList<InputTable> inputTables = converter.prepareInputTables(inputsIOConnections, externalData);
//
//            checkKeysProblems(inputTables);
//
//            checkLookupTablesUnusedProblems(inputTables);
//
//        }
//
//        return getProblems();
    }

    /**
     * DOC amaumont Comment method "checkLookupTablesUnusedProblems".
     * 
     * @param inputTables
     */
    private void checkLookupTablesUnusedProblems(ArrayList<InputTable> inputTables) {

        for (InputTable table : inputTables) {
            if (table.isMainConnection()) {
                continue;
            }
            List<IColumnEntry> columnEntries = table.getColumnEntries();
            boolean atLeastOneExpressionFilled = false;
            for (IColumnEntry entry : columnEntries) {
                InputColumnTableEntry inputEntry = (InputColumnTableEntry) entry;
                if (!mapperManager.checkEntryHasEmptyExpression(inputEntry)) {
                    atLeastOneExpressionFilled = true;
                    break;
                }
            }

            if (!atLeastOneExpressionFilled) {
                addProblem(new Problem(null, "The lookup table '" + table.getName() + "' should have at least one expression key filled. ", //$NON-NLS-1$ //$NON-NLS-2$
                        ProblemStatus.WARNING));
            }

        }

    }

    /**
     * DOC amaumont Comment method "checkKeysProblems".
     * 
     * @param incomingConnections
     * @param inputTables
     */
    private void checkKeysProblems(ArrayList<InputTable> inputTables) {

        IDbLanguage currentLanguage = this.mapperManager.getCurrentLanguage();
        for (InputTable table : inputTables) {
            if (table.isMainConnection()) {
                continue;
            }
            String tableName = table.getName();
            List<IColumnEntry> columnEntries = table.getColumnEntries();
            for (IColumnEntry entry : columnEntries) {
                InputColumnTableEntry inputEntry = (InputColumnTableEntry) entry;
                String columnName = entry.getName();
//                addProblem(new Problem(null, description, ProblemStatus.WARNING));
            }
        }

    }

    /**
     * DOC amaumont Comment method "checkExpressionSyntaxProblems".
     * 
     * @param tables
     * @param codeChecker
     */
    private void checkExpressionSyntaxProblems(List<ExternalDbMapTable> tables, ICodeProblemsChecker codeChecker) {

        IDbLanguage currentLanguage = this.mapperManager.getCurrentLanguage();

        for (ExternalDbMapTable table : tables) {
            List<ExternalDbMapEntry> metadataTableEntries = table.getMetadataTableEntries();
            // loop on all entries of current table
            if (metadataTableEntries != null) {
                for (ExternalDbMapEntry entry : metadataTableEntries) {
                    List<Problem> problems = null;
                    problems = checkCodeProblems(entry.getExpression());
                    if (problems != null) {
                        String location = currentLanguage.getLocation(table.getName(), entry.getName());
                        String prefix = "Expression of " + location + " is invalid : "; //$NON-NLS-1$ //$NON-NLS-2$
                        for (Problem problem : problems) {
                            if (!problem.getDescription().startsWith(prefix)) {
                                String description = prefix + problem.getDescription() + "."; //$NON-NLS-1$
                                problem.setDescription(description);
                            }
                            addProblem(problem);
                        }
                    }
                } // for (ExternalMapperTableEntry entry : metadataTableEntries) {
            }
            if (table.getCustomConditionsEntries() != null) {
                String prefix = "Filter invalid in table " + table.getName() + " : "; //$NON-NLS-1$ //$NON-NLS-2$
                for (ExternalDbMapEntry entry : table.getCustomConditionsEntries()) {

                    List<Problem> problems = null;
                    problems = checkCodeProblems(entry.getExpression());

                    if (problems != null) {
                        for (Problem problem : problems) {
                            if (!problem.getDescription().startsWith(prefix)) {
                                String description = prefix + problem.getDescription() + "."; //$NON-NLS-1$
                                problem.setDescription(description);
                            }
                            addProblem(problem);
                        }
                    }

                }
            }
        } // for (ExternalMapperTable table : tables) {
    }

    /**
     * DOC amaumont Comment method "getProblems".
     * 
     * @return
     */
    public List<Problem> getProblems() {
        return new ArrayList<Problem>(problems);
    }

    /**
     * DOC amaumont Comment method "addProblem".
     * 
     * @param problem
     */
    private void addProblem(Problem problem) {
        if (problem != null) {
            problems.add(problem);
        }
    }

    private List<Problem> checkCodeProblems(String expression) {
        return mapperManager.getProblemsManager().checkExpressionSyntax(expression);
    }

}
