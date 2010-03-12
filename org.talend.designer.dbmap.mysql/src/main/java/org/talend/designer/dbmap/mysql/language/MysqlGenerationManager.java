// ============================================================================
//
// Copyright (C) 2006-2010 Talend Inc. - www.talend.com
//
// This source code is available under agreement available at
// %InstallDIR%\features\org.talend.rcp.branding.%PRODUCTNAME%\%PRODUCTNAME%license.txt
//
// You should have received a copy of the agreement
// along with this program; if not, write to Talend SA
// 9 rue Pages 92150 Suresnes, France
//
// ============================================================================
package org.talend.designer.dbmap.mysql.language;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.talend.core.model.process.IConnection;
import org.talend.designer.dbmap.DbMapComponent;
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
    public String buildConditions(List<ExternalDbMapEntry> constraintTableEntries, DataMapExpressionParser expressionParser) {
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
    public String buildSqlSelect(DbMapComponent component, String outputTableName) {

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
            // if (connection != null) {
            // outputTable = removeUnmatchingEntriesWithColumnsOfMetadataTable(outputTable, connection
            // .getMetadataTable());
            // }

            sb.append("SELECT\n"); //$NON-NLS-1$

            List<ExternalDbMapEntry> metadataTableEntries = outputTable.getMetadataTableEntries();
            if (metadataTableEntries != null) {
                int lstSizeOutTableEntries = metadataTableEntries.size();
                for (int i = 0; i < lstSizeOutTableEntries; i++) {
                    ExternalDbMapEntry dbMapEntry = metadataTableEntries.get(i);
                    String expression = dbMapEntry.getExpression();
                    if (i > 0) {
                        sb.append(", "); //$NON-NLS-1$
                    }
                    if (expression != null && expression.trim().length() > 0) {
                        sb.append(dbMapEntry.getExpression());
                    } else {
                        sb.append("/* Expression of output entry '" + outputTable.getName() + "." //$NON-NLS-1$ //$NON-NLS-2$
                                + dbMapEntry.getName() + "' is not set */"); //$NON-NLS-1$
                    }
                }
            }

            List<ExternalDbMapTable> inputTables = data.getInputTables();
            sb.append("\nFROM"); //$NON-NLS-1$

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
                    break;
                }

            }
            // bug 11758
            List<ExternalDbMapTable> appendTableList = new ArrayList<ExternalDbMapTable>(inputTables);
            List<ExternalDbMapTable> tmpTableList = new ArrayList<ExternalDbMapTable>();

            Iterator<ExternalDbMapTable> iterator = appendTableList.iterator();
            while (iterator.hasNext()) {
                ExternalDbMapTable inputTable = iterator.next();
                IJoinType joinType = language.getJoin(inputTable.getJoinType());
                ExternalDbMapTable joinTable = getExternalDbMapTable(inputTables, inputTable);
                if (joinType != AbstractDbLanguage.JOIN.NO_JOIN || joinTable != inputTable) {
                    iterator.remove();
                }
                if (joinTable != inputTable) { // related
                    tmpTableList.add(joinTable);
                }
            }
            appendTableList.removeAll(tmpTableList);

            StringBuilder sbWhere = new StringBuilder();
            boolean isFirstClause = true;
            for (int i = 0; i < lstSizeInputTables; i++) {
                ExternalDbMapTable inputTable = inputTables.get(i);
                if (buildConditions(sbWhere, inputTable, false, isFirstClause)) {
                    isFirstClause = false;
                }
            }

            sb.append("\n"); //$NON-NLS-1$

            IJoinType previousJoinType = null;
            boolean hasToAppend = true;
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
                    ExternalDbMapTable joinTable = getExternalDbMapTable(inputTables, inputTable);
                    if (hasToAppend) {// 11758
                        for (int j = 0; j < appendTableList.size(); j++) {
                            ExternalDbMapTable appendTable = appendTableList.get(j);
                            if (joinTable == appendTable) {
                                continue;
                            }
                            IJoinType appendType = language.getJoin(appendTable.getJoinType());
                            if (appendType == AbstractDbLanguage.JOIN.NO_JOIN && explicitJoin && i > 0) {
                                buildTableDeclaration(sb, appendTable, commaCouldBeAdded, crCouldBeAdded, false);
                                sb.append(", ");
                                hasToAppend = false;
                            }
                        }
                    }
                    if (i > 0) {
                        if (previousJoinType == null) {

                            if (joinTable == null) {
                                joinTable = inputTables.get(i - 1);
                            }
                            buildTableDeclaration(sb, joinTable, commaCouldBeAdded, crCouldBeAdded, true);
                            previousJoinType = joinType;
                        } else {
                            sb.append("\n"); //$NON-NLS-1$
                        }
                        sb.append(" "); //$NON-NLS-1$
                    }
                    String labelJoinType = joinType.getLabel();
                    sb.append(labelJoinType).append(" "); //$NON-NLS-1$
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
                        sb.append(" "); //$NON-NLS-1$
                        sb.append("ON( "); //$NON-NLS-1$
                        if (!buildConditions(sb, inputTable, true, true)) {
                            sb.append("/* Conditions of joint are not set */"); //$NON-NLS-1$
                        }
                        sb.append(" )"); //$NON-NLS-1$
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
                                sbAddClauses.append("\n AND "); //$NON-NLS-1$
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
                sb.append("\nWHERE "); //$NON-NLS-1$
                sb.append(whereClauses);
                if (whereClauses.trim().length() > 0 && addClauses.trim().length() > 0) {
                    sb.append("\n AND "); //$NON-NLS-1$
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
    private boolean buildConditions(StringBuilder sb, ExternalDbMapTable inputTable, boolean writeForJoin, boolean isFirstClause) {
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

    private ExternalDbMapTable getExternalDbMapTable(List<ExternalDbMapTable> inputTables, ExternalDbMapTable inputTable) {
        ExternalDbMapTable joinTable = null;
        List<ExternalDbMapEntry> inputEntries = inputTable.getMetadataTableEntries();
        for (int j = 0; j < inputEntries.size(); j++) {
            ExternalDbMapEntry dbMapEntry = inputEntries.get(j);
            for (ExternalDbMapTable table : inputTables) {
                if (dbMapEntry.isJoin() || language.getJoin(inputTable.getJoinType()) != AbstractDbLanguage.JOIN.NO_JOIN) {
                    String joinTabelName = dbMapEntry.getExpression();
                    if (joinTabelName != null) {
                        int indexOf = joinTabelName.indexOf(".");
                        if (indexOf > -1) { // found
                            joinTabelName = joinTabelName.substring(0, indexOf);
                            if (table.getName().equals(joinTabelName)) {
                                joinTable = table;
                                return joinTable;
                            }
                        }
                    }
                }
            }
        }
        return inputTable;
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
                sbWhere.append("\n "); //$NON-NLS-1$
            }
            if (!isFirstClause) {
                sbWhere.append(" AND "); //$NON-NLS-1$
            }
            String locationInputEntry = language.getLocation(table.getName(), dbMapEntry.getName());
            sbWhere.append(" "); //$NON-NLS-1$
            sbWhere.append(locationInputEntry);
            sbWhere.append(" "); //$NON-NLS-1$
            if (operatorIsSet) {
                sbWhere.append(dbOperator.getOperator()).append(" "); //$NON-NLS-1$
            } else if (!operatorIsSet && expressionIsSet) {
                sbWhere.append("/* Operator of input entry '" + dbMapEntry.getName() + "' is not set */ "); //$NON-NLS-1$ //$NON-NLS-2$
            }
            if (operatorIsSet && !expressionIsSet && !dbOperator.isMonoOperand()) {
                sbWhere.append("/* Expression of input entry '" + table.getName() + "." + dbMapEntry.getName() //$NON-NLS-1$ //$NON-NLS-2$
                        + "' is not set */"); //$NON-NLS-1$
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
        sb.append(" "); //$NON-NLS-1$
        String alias = inputTable.getAlias();
        if (alias != null) {
            if (!aliasAlreadyDeclared.contains(inputTable.getName())) {
                if (crCouldBeAdded) {
                    sb.append("\n"); //$NON-NLS-1$
                }
                if (commaCouldBeAdded) {
                    sb.append(", "); //$NON-NLS-1$
                }
                sb.append(inputTable.getTableName());
                sb.append(" "); //$NON-NLS-1$
                sb.append(alias);
                aliasAlreadyDeclared.add(alias);
            } else {
                if (writingInJoin) {
                    sb.append(inputTable.getName());
                }
            }
        } else {
            if (crCouldBeAdded) {
                sb.append("\n"); //$NON-NLS-1$
            }
            if (commaCouldBeAdded) {
                sb.append(", "); //$NON-NLS-1$
            }
            sb.append(inputTable.getName());
        }
    }

    public static void main(String[] args) {

        // OracleGenerationManager manager = new OracleGenerationManager(SqlLanguageProvider.getJavaLanguage());
        // System.out.println(manager.insertFieldKey("table:column", "\nligne1\nligne2\nligne3"));
    }

}
