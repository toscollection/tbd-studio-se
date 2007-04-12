// ============================================================================
//
// Talend Community Edition
//
// Copyright (C) 2006-2007 Talend - www.talend.com
//
// This library is free software; you can redistribute it and/or
// modify it under the terms of the GNU Lesser General Public
// License as published by the Free Software Foundation; either
// version 2.1 of the License.
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
package org.talend.designer.dbmap.mysql.language;

import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.talend.core.model.process.IConnection;
import org.talend.designer.dbmap.AbstractDbMapComponent;
import org.talend.designer.dbmap.external.data.ExternalDbMapData;
import org.talend.designer.dbmap.external.data.ExternalDbMapEntry;
import org.talend.designer.dbmap.external.data.ExternalDbMapTable;
import org.talend.designer.dbmap.language.AbstractDbLanguage;
import org.talend.designer.dbmap.language.IDbOperator;
import org.talend.designer.dbmap.language.IJoinType;
import org.talend.designer.dbmap.language.generation.DbGenerationManager;
import org.talend.designer.dbmap.utils.DataMapExpressionParser;

/**
 * DOC amaumont class global comment. Detailled comment <br/>
 * 
 * $Id: GenerationManager.java 1299 2007-01-05 14:53:10Z amaumont $
 * 
 */
public class MysqlGenerationManager extends DbGenerationManager {

    private Set<String> aliasAlreadyDeclared = new HashSet<String>();

    public MysqlGenerationManager() {
        super(new MysqlLanguage());
    }

    /**
     * DOC amaumont Comment method "buildConditions".
     * 
     * @param gm
     * 
     * @param constraintTableEntries
     * @param expressionParser
     * @return
     */
    public String buildConditions(List<ExternalDbMapEntry> constraintTableEntries,
            DataMapExpressionParser expressionParser) {
        int lstSize = constraintTableEntries.size();
        StringBuilder stringBuilder = new StringBuilder();
        String and = null;
        for (int i = 0; i < lstSize; i++) {

            String constraintExpression = ((ExternalDbMapEntry) constraintTableEntries.get(i)).getExpression();
            if (constraintExpression == null) {
                continue;
            }
            if (and != null && constraintExpression.trim().length() > 0) {
                stringBuilder.append(and);
            }

            if (lstSize > 1) {
                stringBuilder.append(" ( " + constraintExpression + " ) "); //$NON-NLS-1$ //$NON-NLS-2$
            } else {
                stringBuilder.append(constraintExpression);
            }

            if (and == null) {
                and = language.getAndCondition();
            }
        }
        return stringBuilder.toString();
    }

    @Override
    public String buildSqlSelect(AbstractDbMapComponent component, String outputTableName) {

        aliasAlreadyDeclared.clear();

        List<IConnection> outputConnections = (List<IConnection>) component.getOutgoingConnections();

        Map<String, IConnection> nameToOutputConnection = new HashMap<String, IConnection>();
        for (IConnection connection : outputConnections) {
            nameToOutputConnection.put(connection.getUniqueName(), connection);
        }

        ExternalDbMapData data = (ExternalDbMapData) component.getExternalData();

        StringBuilder sb = new StringBuilder();

        List<ExternalDbMapTable> outputTables = data.getOutputTables();
        int lstOutputTablesSize = outputTables.size();
        ExternalDbMapTable outputTable = null;
        for (int i = 0; i < lstOutputTablesSize; i++) {
            ExternalDbMapTable temp = outputTables.get(i);
            if (outputTableName.equals(temp.getName())) {
                outputTable = temp;
                break;
            }
        }

        if (outputTable != null) {

            IConnection connection = nameToOutputConnection.get(outputTable.getName());
            if (connection != null) {
                outputTable = removeUnmatchingEntriesWithColumnsOfMetadataTable(outputTable, connection
                        .getMetadataTable());
            }

            sb.append("SELECT\n");

            List<ExternalDbMapEntry> metadataTableEntries = outputTable.getMetadataTableEntries();
            if (metadataTableEntries != null) {
                int lstSizeOutTableEntries = metadataTableEntries.size();
                for (int i = 0; i < lstSizeOutTableEntries; i++) {
                    ExternalDbMapEntry dbMapEntry = metadataTableEntries.get(i);
                    String expression = dbMapEntry.getExpression();
                    if (i > 0) {
                        sb.append(", ");
                    }
                    if (expression != null && expression.trim().length() > 0) {
                        sb.append(dbMapEntry.getExpression());
                    } else {
                        sb.append("/* Expression of output entry '" + outputTable.getName() + "."
                                + dbMapEntry.getName() + "' is not set */");
                    }
                }
            }

            List<ExternalDbMapTable> inputTables = data.getInputTables();
            sb.append("\nFROM");

            // load input table in hash
            boolean explicitJoin = false;
            int lstSizeInputTables = inputTables.size();
            Map<String, ExternalDbMapTable> nameToInputTable = new HashMap<String, ExternalDbMapTable>();
            for (int i = 0; i < lstSizeInputTables; i++) {
                ExternalDbMapTable inputTable = inputTables.get(i);
                nameToInputTable.put(inputTable.getName(), inputTable);
                IJoinType joinType = language.getJoin(inputTable.getJoinType());
                if (joinType != AbstractDbLanguage.JOIN.NO_JOIN && i > 0) {
                    explicitJoin = true;
                }

            }

            StringBuilder sbWhere = new StringBuilder();
            boolean isFirstClause = true;
            for (int i = 0; i < lstSizeInputTables; i++) {
                ExternalDbMapTable inputTable = inputTables.get(i);
                if (buildConditions(sbWhere, inputTable, false, isFirstClause)) {
                    isFirstClause = false;
                }
            }

            sb.append("\n");

            IJoinType previousJoinType = null;

            for (int i = 0; i < lstSizeInputTables; i++) {
                ExternalDbMapTable inputTable = inputTables.get(i);
                IJoinType joinType = null;
                if (i == 0) {
                    joinType = AbstractDbLanguage.JOIN.NO_JOIN;
                } else {
                    joinType = language.getJoin(inputTable.getJoinType());
                }
                boolean commaCouldBeAdded = !explicitJoin && i > 0;
                boolean crCouldBeAdded = false;
                if (joinType == AbstractDbLanguage.JOIN.NO_JOIN && !explicitJoin) {
                    buildTableDeclaration(sb, inputTable, commaCouldBeAdded, crCouldBeAdded, false);

                } else if (joinType != AbstractDbLanguage.JOIN.NO_JOIN && explicitJoin) {
                    if (i > 0) {
                        if (previousJoinType == null) {
                            buildTableDeclaration(sb, inputTables.get(i - 1), commaCouldBeAdded, crCouldBeAdded, true);
                            previousJoinType = joinType;
                        } else {
                            sb.append("\n");
                        }
                        sb.append(" ");
                    }
                    String labelJoinType = joinType.getLabel();
                    sb.append(labelJoinType).append(" ");
                    if (joinType == AbstractDbLanguage.JOIN.CROSS_JOIN) {
                        ExternalDbMapTable nextTable = null;
                        if (i < lstSizeInputTables) {
                            nextTable = inputTables.get(i);
                            buildTableDeclaration(sb, nextTable, false, false, true);
                        }

                    } else {

                        // ExternalDbMapTable rightTable = joinLeftToJoinRightTables.get(inputTable.getName());
                        buildTableDeclaration(sb, inputTable, false, false, true);
                        // if (rightTable != null) {
                        // } else {
                        // sb.append(" <!! NO JOIN CLAUSES FOR '" + inputTable.getName() + "' !!> ");
                        // }
                        sb.append(" ");
                        sb.append("ON( ");
                        if (!buildConditions(sb, inputTable, true, true)) {
                            sb.append("/* Conditions of joint are not set */");
                        }
                        sb.append(" )");
                    }

                }
            }

            StringBuilder sbAddClauses = new StringBuilder();
            if (outputTable != null) {
                List<ExternalDbMapEntry> customConditionsEntries = outputTable.getCustomConditionsEntries();
                if (customConditionsEntries != null) {
                    lstSizeInputTables = customConditionsEntries.size();
                    isFirstClause = true;
                    for (int i = 0; i < lstSizeInputTables; i++) {
                        ExternalDbMapEntry dbMapEntry = customConditionsEntries.get(i);
                        if (dbMapEntry.getExpression() != null) {
                            if (!isFirstClause) {
                                sbAddClauses.append("\n AND ");
                            }
                            sbAddClauses.append(dbMapEntry.getExpression());
                            isFirstClause = false;
                        }
                    }
                }
            }

            String whereClauses = sbWhere.toString();
            String addClauses = sbAddClauses.toString();

            if (whereClauses.trim().length() > 0 || addClauses.trim().length() > 0) {
                sb.append("\nWHERE ");
                sb.append(whereClauses);
                if (whereClauses.trim().length() > 0 && addClauses.trim().length() > 0) {
                    sb.append("\n AND ");
                }

                sb.append(addClauses);
            }
        }
        return sb.toString();
    }

    /**
     * DOC amaumont Comment method "buildConditions".
     * 
     * @param sb
     * @param inputTable
     * @param writeForJoin TODO
     * @param isFirstClause TODO
     */
    private boolean buildConditions(StringBuilder sb, ExternalDbMapTable inputTable, boolean writeForJoin,
            boolean isFirstClause) {
        List<ExternalDbMapEntry> inputEntries = inputTable.getMetadataTableEntries();
        int lstSizeEntries = inputEntries.size();
        boolean atLeastOneConditionWritten = false;
        for (int j = 0; j < lstSizeEntries; j++) {
            ExternalDbMapEntry dbMapEntry = inputEntries.get(j);
            if (writeForJoin == dbMapEntry.isJoin()) {
                boolean conditionWritten = buildCondition(sb, inputTable, isFirstClause, dbMapEntry, !writeForJoin);
                if (conditionWritten) {
                    atLeastOneConditionWritten = true;
                }
                if (isFirstClause && conditionWritten) {
                    isFirstClause = false;
                }
            }
        }
        return atLeastOneConditionWritten;
    }

    /**
     * DOC amaumont Comment method "buildCondition".
     * 
     * @param sbWhere
     * @param table
     * @param isFirstClause
     * @param dbMapEntry
     * @param writeCr TODO
     */
    private boolean buildCondition(StringBuilder sbWhere, ExternalDbMapTable table, boolean isFirstClause,
            ExternalDbMapEntry dbMapEntry, boolean writeCr) {
        String expression = dbMapEntry.getExpression();
        IDbOperator dbOperator = getOperatorsManager().getOperatorFromValue(dbMapEntry.getOperator());
        boolean operatorIsSet = dbOperator != null;
        boolean expressionIsSet = expression != null && expression.trim().length() > 0;

        boolean conditionWritten = false;

        if (operatorIsSet) {

            if (writeCr) {
                sbWhere.append("\n ");
            }
            if (!isFirstClause) {
                sbWhere.append(" AND ");
            }
            String locationInputEntry = language.getLocation(table.getName(), dbMapEntry.getName());
            sbWhere.append(" ");
            sbWhere.append(locationInputEntry);
            sbWhere.append(" ");
            if (operatorIsSet) {
                sbWhere.append(dbOperator.getOperator()).append(" ");
            } else if (!operatorIsSet && expressionIsSet) {
                sbWhere.append("/* Operator of input entry '" + dbMapEntry.getName() + "' is not set */ ");
            }
            if (operatorIsSet && !expressionIsSet && !dbOperator.isMonoOperand()) {
                sbWhere.append("/* Expression of input entry '" + table.getName() + "." + dbMapEntry.getName()
                        + "' is not set */");
            } else if (expressionIsSet) {
                sbWhere.append(dbMapEntry.getExpression());
            }
            conditionWritten = true;

        }

        return conditionWritten;
    }

    /**
     * DOC amaumont Comment method "buildFromTableDeclaration".
     * 
     * @param sb
     * @param inputTable
     * @param commaCouldBeAdded
     * @param crCouldBeAdded TODO
     * @param writingInJoin TODO
     */
    private void buildTableDeclaration(StringBuilder sb, ExternalDbMapTable inputTable, boolean commaCouldBeAdded,
            boolean crCouldBeAdded, boolean writingInJoin) {
        sb.append(" ");
        String alias = inputTable.getAlias();
        if (alias != null) {
            if (!aliasAlreadyDeclared.contains(inputTable.getName())) {
                if (crCouldBeAdded) {
                    sb.append("\n");
                }
                if (commaCouldBeAdded) {
                    sb.append(", ");
                }
                sb.append(inputTable.getTableName());
                sb.append(" ");
                sb.append(alias);
                aliasAlreadyDeclared.add(alias);
            } else {
                if (writingInJoin) {
                    sb.append(inputTable.getName());
                }
            }
        } else {
            if (crCouldBeAdded) {
                sb.append("\n");
            }
            if (commaCouldBeAdded) {
                sb.append(", ");
            }
            sb.append(inputTable.getName());
        }
    }

    public static void main(String[] args) {

        // OracleGenerationManager manager = new OracleGenerationManager(SqlLanguageProvider.getJavaLanguage());
        // System.out.println(manager.insertFieldKey("table:column", "\nligne1\nligne2\nligne3"));
    }

}
