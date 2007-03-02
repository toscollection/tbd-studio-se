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
package org.talend.designer.dbmap.oracle.language;

import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.talend.designer.dbmap.AbstractDbMapComponent;
import org.talend.designer.dbmap.external.data.ExternalDbMapData;
import org.talend.designer.dbmap.external.data.ExternalDbMapEntry;
import org.talend.designer.dbmap.external.data.ExternalDbMapTable;
import org.talend.designer.dbmap.language.AbstractDbLanguage;
import org.talend.designer.dbmap.language.IJoinType;
import org.talend.designer.dbmap.language.generation.DbGenerationManager;
import org.talend.designer.dbmap.model.table.InputTable;
import org.talend.designer.dbmap.model.table.OutputTable;
import org.talend.designer.dbmap.model.tableentry.TableEntryLocation;
import org.talend.designer.dbmap.oracle.OracleMapperComponent;
import org.talend.designer.dbmap.utils.DataMapExpressionParser;

/**
 * DOC amaumont class global comment. Detailled comment <br/>
 * 
 * $Id: GenerationManager.java 1299 2007-01-05 14:53:10Z amaumont $
 * 
 */
public class OracleGenerationManager extends DbGenerationManager {

    private Set<String> aliasAlreadyDeclared = new HashSet<String>();

    public OracleGenerationManager() {
        super(new OracleLanguage());
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
    public String buildSqlSelect(AbstractDbMapComponent component, String tableName) {

        aliasAlreadyDeclared.clear();

        ExternalDbMapData data = (ExternalDbMapData) component.getExternalData();

        StringBuilder sb = new StringBuilder();

        List<ExternalDbMapTable> outputTables = data.getOutputTables();
        int lstOutputTablesSize = outputTables.size();
        ExternalDbMapTable outputTable = null;
        for (int i = 0; i < lstOutputTablesSize; i++) {
            ExternalDbMapTable temp = outputTables.get(i);
            if (tableName.equals(temp.getName())) {
                outputTable = temp;
                break;
            }
        }

        if (outputTable == null) {
            return "";
        }

        sb.append("SELECT\n");

        List<ExternalDbMapEntry> metadataTableEntries = outputTable.getMetadataTableEntries();
        int lstSizeOutTableEntries = metadataTableEntries.size();
        for (int i = 0; i < lstSizeOutTableEntries; i++) {
            ExternalDbMapEntry dbMapEntry = metadataTableEntries.get(i);
            if (i > 0) {
                sb.append(", ");
            }
            sb.append(dbMapEntry.getExpression());
        }

        List<ExternalDbMapTable> inputTables = data.getInputTables();
        sb.append("\nFROM");

        DataMapExpressionParser parser = new DataMapExpressionParser(language);

        // load input table in hash
        boolean explicitJoin = false;
        int lstSizeInputTables = inputTables.size();
        Map<String, ExternalDbMapTable> nameToInputTable = new HashMap<String, ExternalDbMapTable>();
        for (int i = 0; i < lstSizeInputTables; i++) {
            ExternalDbMapTable inputTable = inputTables.get(i);
            nameToInputTable.put(inputTable.getName(), inputTable);
            IJoinType joinType = language.getJoin(inputTable.getJoinType());
            if (joinType != AbstractDbLanguage.JOIN.NO_JOIN) {
                explicitJoin = true;
            }

        }

        HashMap<String, ExternalDbMapTable> joinLeftToJoinRightTables = new HashMap<String, ExternalDbMapTable>();
        StringBuilder sbWhere = new StringBuilder();
        for (int i = 0; i < lstSizeInputTables; i++) {
            ExternalDbMapTable inputTable = inputTables.get(i);
            List<ExternalDbMapEntry> inputEntries = inputTable.getMetadataTableEntries();
            int lstSizeEntries = inputEntries.size();
            boolean isFirstClause = true;
            for (int j = 0; j < lstSizeEntries; j++) {
                ExternalDbMapEntry dbMapEntry = inputEntries.get(j);
                boolean expressionIsValid = dbMapEntry.getExpression() != null && dbMapEntry.getExpression().trim().length() > 0
                        || dbMapEntry.getOperator() != null && dbMapEntry.getOperator().trim().length() > 0;
                if (expressionIsValid && !dbMapEntry.isJoin()) {
                    if (!isFirstClause) {
                        sbWhere.append(" AND ");
                    } else {
                        sbWhere.append("\n ");
                    }
                    String locationInputEntry = language.getLocation(inputTable.getName(), dbMapEntry.getName());
                    sbWhere.append(locationInputEntry);
                    sbWhere.append(" ");
                    if (dbMapEntry.getOperator() != null) {
                        sbWhere.append(dbMapEntry.getOperator()).append(" ");
                    }
                    sbWhere.append(dbMapEntry.getExpression());
                    isFirstClause = false;
                } else if (expressionIsValid && dbMapEntry.isJoin()) {
                    TableEntryLocation[] locations = parser.parseTableEntryLocations(dbMapEntry.getExpression());
                    for (int k = 0; k < 1; k++) {
                        TableEntryLocation location = locations[k];
                        joinLeftToJoinRightTables.put(inputTable.getName(), nameToInputTable.get(location.tableName));
                    }
                }
            }
        }

        sb.append("\n");

        IJoinType previousJoinType = null;

        for (int i = 0; i < lstSizeInputTables; i++) {
            ExternalDbMapTable inputTable = inputTables.get(i);
            IJoinType joinType = language.getJoin(inputTable.getJoinType());
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
                    if (i < lstSizeInputTables - 1) {
                        nextTable = inputTables.get(i + 1);
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
                    List<ExternalDbMapEntry> inputEntries = inputTable.getMetadataTableEntries();
                    int lstSizeEntries = inputEntries.size();
                    boolean isFirstClause = true;
                    for (int j = 0; j < lstSizeEntries; j++) {
                        ExternalDbMapEntry dbMapEntry = inputEntries.get(j);
                        boolean expressionIsValid = dbMapEntry.getExpression() != null && dbMapEntry.getExpression().trim().length() > 0
                                || dbMapEntry.getOperator() != null && dbMapEntry.getOperator().trim().length() > 0;
                        if (expressionIsValid && dbMapEntry.isJoin()) {
                            if (!isFirstClause) {
                                sb.append(" AND ");
                            }

                            String locationInputEntry = language.getLocation(inputTable.getName(), dbMapEntry.getName());
                            sb.append(locationInputEntry);
                            sb.append(" ");
                            if (dbMapEntry.getOperator() != null) {
                                sb.append(dbMapEntry.getOperator()).append(" ");
                            }
                            sb.append(dbMapEntry.getExpression());
                            isFirstClause = false;

                        }
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
                boolean isFirstClause = true;
                sbAddClauses.append("\n");
                for (int i = 0; i < lstSizeInputTables; i++) {
                    ExternalDbMapEntry dbMapEntry = customConditionsEntries.get(i);
                    if (dbMapEntry.getExpression() != null && dbMapEntry.getExpression().trim().length() > 0) {
                        if (!isFirstClause) {
                            sbAddClauses.append(" AND ");
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
            sb.append("\nWHERE");
            sb.append(whereClauses);
            if (whereClauses.trim().length() > 0) {
                sbAddClauses.append(" AND ");
            }

            sb.append(addClauses);
        }

        return sb.toString();
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
    private void buildTableDeclaration(StringBuilder sb, ExternalDbMapTable inputTable, boolean commaCouldBeAdded, boolean crCouldBeAdded,
            boolean writingInJoin) {
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

    /**
     * 
     * DOC amaumont MapperManager class global comment. Detailled comment <br/>
     * 
     * $Id$
     * 
     */
    public enum PROBLEM_KEY_FIELD {
        METADATA_COLUMN,
        FILTER,
    }

}
