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
package org.talend.designer.mapper.oracle.model.metadata;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import org.talend.core.model.components.IODataComponent;
import org.talend.core.model.metadata.IMetadataColumn;
import org.talend.core.model.metadata.IMetadataTable;
import org.talend.core.model.metadata.MetadataColumn;
import org.talend.core.model.metadata.MetadataTable;
import org.talend.core.model.process.AbstractConnection;
import org.talend.core.model.process.AbstractNode;
import org.talend.core.model.process.EConnectionType;
import org.talend.core.model.process.IConnection;
import org.talend.core.model.process.INodeReturn;
import org.talend.designer.dbmap.external.data.ExternalDbMapData;
import org.talend.designer.dbmap.external.data.ExternalDbMapEntry;
import org.talend.designer.dbmap.external.data.ExternalDbMapTable;
import org.talend.designer.dbmap.language.generation.DbGenerationManager;
import org.talend.designer.dbmap.language.generation.TableType;
import org.talend.designer.dbmap.model.table.VarsTable;

/**
 * DOC amaumont class global comment. Detailled comment <br/>
 * 
 * $Id: MapperDataTestGenerator.java 2077 2007-02-15 11:16:46Z amaumont $
 * 
 */
public class MysqlDbMapTestGenerator {

    private static final int N_TABLES = 4;

    private static final int N_FIELDS = 2;

    private boolean fixedData = true;

    private static final String COLUMN_NAME = "column"; //$NON-NLS-1$

    private DbGenerationManager gen;

    /**
     * 
     * DOC amaumont MapperDataTestGenerator class global comment. Detailled comment <br/>
     * 
     * $Id: MapperDataTestGenerator.java 2077 2007-02-15 11:16:46Z amaumont $
     * 
     */
    enum TEST {
        ONE_INNER_JOIN_AND_NOT_ANY_REJECT_AND_ONE_OUT_TABLE_WITHOUT_FILTER,
        ONE_INNER_JOIN_AND_ONLY_ONE_INNER_JOIN_REJECT_AND_ONE_OUT_TABLE_WITHOUT_FILTER,
        NO_INNER_JOIN_AND_ONLY_ONE_INNER_JOIN_REJECT_AND_ONE_OUT_TABLE_WITHOUT_FILTER,
        ONE_INNER_JOIN_AND_A_TABLE_WITH_REJECT_AND_INNER_JOIN_REJECT_AND_ONE_OUT_TABLE_WITHOUT_FILTER,
        NO_INNER_JOIN_AND_ONE_INNER_JOIN_REJECT_AND_2_REJECT_TABLE_WITHOUT_FILTER,
        NO_INNER_JOIN_AND_A_TABLE_WITH_FILTER_AND_A_TABLE_WITH_REJECT,
        ONE_INNER_JOIN_AND_2_TABLES_WITH_FILTER_AND_OTHER_TABLE_WITH_REJECT_AND_REJECT_INNER_JOIN,
        ONE_INNER_JOIN_AND_A_TABLE_WITH_FILTER_AND_A_TABLE_WITH_REJECT_AND_A_TABLE_WITH_REJECT_INNER_JOIN,
        MAIN_CONNECTION_WITH_INNER_JOIN_AND_A_TABLE_WITH_FILTER_AND_A_TABLE_WITH_REJECT_AND_A_TABLE_WITH_REJECT_INNER_JOIN,
        ONE_INNER_JOIN_AND_A_TABLE_WITH_FILTER_AND_A_TABLE_WITH_REJECT,
        ONE_INNER_JOIN_AND_A_TABLE_WITH_INNER_JOIN_REJECT_WITHOUT_REGULAR_TABLE,
    };

    TEST currentTest = TEST.ONE_INNER_JOIN_AND_2_TABLES_WITH_FILTER_AND_OTHER_TABLE_WITH_REJECT_AND_REJECT_INNER_JOIN;

    public MysqlDbMapTestGenerator(DbGenerationManager generationManager, boolean random) {
        super();

        this.random = random;

        gen = generationManager;

        generateConnectionListIn();
        generateExternalData();
        // generateMetadataListOut();
    }

    public final Random rand = new Random();

    private static final String[] FIELDS = new String[] { "id", "name", "street", "address", "city", "country", "zip" }; //$NON-NLS-1$ //$NON-NLS-2$ //$NON-NLS-3$ //$NON-NLS-4$ //$NON-NLS-5$ //$NON-NLS-6$ //$NON-NLS-7$

    private int inputsCounter; // remove finally

    private int outputsCounter; // remove finally

    private boolean random;

    private ArrayList<IConnection> connectionList;

    private boolean useConnectionsToGenerateExternalTables = true;

    private ExternalDbMapData externalData;

    private ArrayList<IMetadataTable> metadataListOut;

    public void resetCounters() {
        inputsCounter = 0; // remove finally

        outputsCounter = 0; // remove finally

    }

    public List<IMetadataTable> generateMedataTables(int nb, int j) {

        ArrayList<IMetadataTable> list = new ArrayList<IMetadataTable>();

        for (int i = 0; i < nb; i++) {
            IMetadataTable metadataTable = null;
            if (j == 1) {
                metadataTable = initInputsDataMapTable();
            }
            if (j == 2) {
                metadataTable = initVarsDataMapTable();
            }
            if (j == 3) {
                metadataTable = initOutputsDataMapTable();
            }

            list.add(metadataTable);
        }
        return list;
    }

    /**
     * DOC amaumont Comment method "initProcessDataMapTable".
     * 
     * @return
     */
    private IMetadataTable initVarsDataMapTable() {
        MetadataTable metadataTable = new MetadataTable();
        metadataTable.setTableName(VarsTable.PREFIX_VARS_TABLE_NAME);

        ArrayList<IMetadataColumn> metadatColumns = new ArrayList<IMetadataColumn>();

        MetadataColumn metadataColumn = new MetadataColumn();
        metadataColumn.setLabel("id"); //$NON-NLS-1$
        metadataColumn.setKey(true);
        // metadataColumn.setType(EMetadataType.INTEGER.toString());
        metadatColumns.add(metadataColumn);

        metadataColumn = new MetadataColumn();
        metadataColumn.setLabel("name"); //$NON-NLS-1$
        metadataColumn.setKey(false);
        // metadataColumn.setType(EMetadataType.STRING.toString());
        metadatColumns.add(metadataColumn);

        metadataTable.setListColumns(metadatColumns);
        return metadataTable;
    }

    /**
     * DOC amaumont Comment method "initMetadataTable".
     * 
     * @return
     */
    private IMetadataTable initInputsDataMapTable() {
        MetadataTable metadataTable = new MetadataTable();
        metadataTable.setTableName("in" + ++inputsCounter); //$NON-NLS-1$

        ArrayList<IMetadataColumn> metadatColumns = new ArrayList<IMetadataColumn>();

        MetadataColumn metadataColumn = new MetadataColumn();
        metadataColumn.setLabel("id"); //$NON-NLS-1$
        metadataColumn.setKey(true);
        // metadataColumn.setType(EMetadataType.INTEGER.toString());
        metadatColumns.add(metadataColumn);

        metadataColumn = new MetadataColumn();
        metadataColumn.setLabel("name"); //$NON-NLS-1$
        metadataColumn.setKey(false);
        metadataColumn.setType("int"); //$NON-NLS-1$
        metadatColumns.add(metadataColumn);
        /*
         * metadataColumn = new MetadataColumn(); metadataColumn.setLabel("name2"); metadataColumn.setKey(false);
         * metadataColumn.setType(EMetadataType.STRING); metadatColumns.add(metadataColumn);
         * 
         * metadataColumn = new MetadataColumn(); metadataColumn.setLabel("name3"); metadataColumn.setKey(false);
         * metadataColumn.setType(EMetadataType.STRING); metadatColumns.add(metadataColumn);
         * 
         * metadataColumn = new MetadataColumn(); metadataColumn.setLabel("name4"); metadataColumn.setKey(false);
         * metadataColumn.setType(EMetadataType.STRING); metadatColumns.add(metadataColumn);
         * 
         * metadataColumn = new MetadataColumn(); metadataColumn.setLabel("name5"); metadataColumn.setKey(false);
         * metadataColumn.setType(EMetadataType.STRING); metadatColumns.add(metadataColumn);
         * 
         * metadataColumn = new MetadataColumn(); metadataColumn.setLabel("name6"); metadataColumn.setKey(false);
         * metadataColumn.setType(EMetadataType.STRING); metadatColumns.add(metadataColumn);
         */
        metadataTable.setListColumns(metadatColumns);

        return metadataTable;

    }

    /**
     * DOC amaumont Comment method "initMetadataTable".
     * 
     * @return
     */
    private IMetadataTable initOutputsDataMapTable() {
        MetadataTable metadataTable = new MetadataTable();
        metadataTable.setTableName("out" + ++outputsCounter); //$NON-NLS-1$

        ArrayList<IMetadataColumn> metadatColumns = new ArrayList<IMetadataColumn>();

        MetadataColumn metadataColumn = new MetadataColumn();
        metadataColumn.setLabel("newId"); //$NON-NLS-1$
        metadataColumn.setKey(true);
        // metadataColumn.setType(EMetadataType.INTEGER.toString());
        metadatColumns.add(metadataColumn);

        metadataColumn = new MetadataColumn();
        metadataColumn.setLabel("newName"); //$NON-NLS-1$
        metadataColumn.setKey(false);
        metadataColumn.setType("int"); //$NON-NLS-1$
        metadatColumns.add(metadataColumn);

        metadataTable.setListColumns(metadatColumns);

        return metadataTable;

    }

    /**
     * DOC amaumont Comment method "generateMedataPersistentData".
     * 
     * @param random TODO
     * 
     * @return
     */
    public Object generateExternalData() {

        if (random) {
            return generateRandomExternalData();
        } else {
            return generateConstantExternalData();
        }

    }

    /**
     * DOC amaumont Comment method "generateRandomPersistentData".
     * 
     * @return
     */
    private Object generateRandomExternalData() {
        externalData = new ExternalDbMapData();

        externalData.setInputTables(generateExternalTables(TableType.INPUT, new TableType[] { TableType.INPUT }, 5, 5));
        externalData.setVarsTables(generateExternalTables(TableType.VARS, new TableType[] { TableType.INPUT }, 20));
        externalData.setOutputTables(generateExternalTables(TableType.OUTPUT, new TableType[] { TableType.INPUT,
                TableType.VARS }, 20));

        return externalData;
    }

    private List<ExternalDbMapTable> generateExternalTables(TableType tableType, TableType[] tableTypes,
            int nExpressionsMax) {
        return generateExternalTables(tableType, tableTypes, nExpressionsMax, null);
    }

    private List<ExternalDbMapTable> generateExternalTables(TableType tableType, TableType[] tableTypes,
            int nFieldsMaxInExpression, Integer nExpressionsMax) {
        List<ExternalDbMapTable> tables = new ArrayList<ExternalDbMapTable>();
        if (this.useConnectionsToGenerateExternalTables && tableType == TableType.INPUT) {

            for (IConnection connection : connectionList) {

                ExternalDbMapTable mapperTable = new ExternalDbMapTable();

                mapperTable.setName(connection.getName());

                IMetadataTable metadataTable = connection.getMetadataTable();

                List<ExternalDbMapEntry> tableEntries = new ArrayList<ExternalDbMapEntry>();
                List<IMetadataColumn> listColumns = metadataTable.getListColumns();
                int nExpressions = 0;
                for (IMetadataColumn column : listColumns) {
                    ExternalDbMapEntry mapperTableEntry = new ExternalDbMapEntry();
                    mapperTableEntry.setName(column.getLabel());
                    if (!fixedData && rand.nextBoolean()) {
                        if (nExpressionsMax == null || nExpressions <= nExpressionsMax) {
                            mapperTableEntry.setExpression(generateExpression(tableTypes, N_FIELDS, rand
                                    .nextInt(nFieldsMaxInExpression), nExpressions));
                            nExpressions++;
                        }
                    }
                    tableEntries.add(mapperTableEntry);
                }
                mapperTable.setMetadataTableEntries(tableEntries);

                tables.add(mapperTable);

            }

        } else {

            for (int i = 1; i <= tableType.getNTables(); i++) {

                ExternalDbMapTable mapperTable = new ExternalDbMapTable();

                String tableName = tableType.getBaseTableName();
                if (tableType != TableType.VARS) {
                    tableName += i;
                }
                mapperTable.setName(tableName);

                List<ExternalDbMapEntry> tableEntries = new ArrayList<ExternalDbMapEntry>();

                for (int j = 1; j <= N_FIELDS; j++) {
                    ExternalDbMapEntry mapperTableEntry = new ExternalDbMapEntry();
                    String baseColumnName = FIELDS[rand.nextInt(FIELDS.length)];
                    if (fixedData) {
                        baseColumnName = COLUMN_NAME;
                    }
                    mapperTableEntry.setName(baseColumnName + j);
                    mapperTableEntry.setExpression(generateExpression(tableTypes, N_FIELDS, rand
                            .nextInt(nFieldsMaxInExpression), j));
                    tableEntries.add(mapperTableEntry);
                }

                mapperTable.setMetadataTableEntries(tableEntries);

                tables.add(mapperTable);
            }
        }
        return tables;

    }

    /**
     * DOC amaumont Comment method "generateExpression".
     * 
     * @param nExpressions
     * 
     * @param string
     * @param i
     * @return
     */
    private String generateExpression(TableType[] tables2, int nFields, int nExpressions, int currentIndex) {
        String expression = ""; //$NON-NLS-1$
        if (fixedData) {
            for (int iTable = 0; iTable < N_TABLES; iTable++) {
                // for (int iField = 0; iField < tables2.length; iField++) {
                for (int i = 0; i < tables2.length; i++) {
                    TableType tableType = tables2[i];
                    expression += gen.getTableColumnVariable(tableType.getBaseTableName() + (iTable + 1), COLUMN_NAME
                            + (currentIndex));
                }
                // }
            }
        } else {
            for (int j = 0; j < nExpressions; j++) {
                TableType tableType = tables2[rand.nextInt(tables2.length)];
                expression += (rand.nextInt(4) == 0 ? "\n" : "") //$NON-NLS-1$ //$NON-NLS-2$
                        + (rand.nextBoolean() ? " + " : " - ") //$NON-NLS-1$ //$NON-NLS-2$
                        + gen.getTableColumnVariable(tableType.getBaseTableName()
                                + (tableType != TableType.VARS ? (rand.nextInt(tableType.getNTables()) + 1) : ""), //$NON-NLS-1$
                                FIELDS[rand.nextInt(FIELDS.length)] + (rand.nextInt(nFields) + 1))
                        + (rand.nextInt(4) == 0 ? (rand.nextBoolean() ? " + " : " - ") + "$array_var" //$NON-NLS-1$ //$NON-NLS-2$ //$NON-NLS-3$
                                + rand.nextInt(10) + "[test_var]" : "") //$NON-NLS-1$ //$NON-NLS-2$
                        + (rand.nextInt(4) == 0 ? (rand.nextBoolean() ? " + " : " - ") + "$hash_var" + rand.nextInt(10) //$NON-NLS-1$ //$NON-NLS-2$ //$NON-NLS-3$
                                + "{test_var}" : "") //$NON-NLS-1$ //$NON-NLS-2$
                        + (rand.nextInt(4) == 0 ? (rand.nextBoolean() ? " + " : " - ") + "$var" + rand.nextInt(10) : ""); //$NON-NLS-1$ //$NON-NLS-2$ //$NON-NLS-3$ //$NON-NLS-4$
            }
        }
        return expression;
    }

    /**
     * DOC amaumont Comment method "generateConstantPeristentData".
     * 
     * @return
     */
    private Object generateConstantExternalData() {
        externalData = new ExternalDbMapData();

        List<ExternalDbMapTable> tables = new ArrayList<ExternalDbMapTable>();

        // ///////////////////////////////////////////
        // INPUTS

        ExternalDbMapTable mapperTable = new ExternalDbMapTable();

        mapperTable.setName("book"); //$NON-NLS-1$

        List<ExternalDbMapEntry> tableEntries = new ArrayList<ExternalDbMapEntry>();

        ExternalDbMapEntry mapperTableEntry = new ExternalDbMapEntry();
        mapperTableEntry.setName("id"); //$NON-NLS-1$
        mapperTableEntry.setExpression("test"); //$NON-NLS-1$
        mapperTableEntry.setOperator("int"); //$NON-NLS-1$
        tableEntries.add(mapperTableEntry);

        mapperTableEntry = new ExternalDbMapEntry();
        mapperTableEntry.setName("name"); //$NON-NLS-1$
        mapperTableEntry.setExpression(""); //$NON-NLS-1$
        mapperTableEntry.setOperator("String"); //$NON-NLS-1$
        tableEntries.add(mapperTableEntry);

        mapperTableEntry = new ExternalDbMapEntry();
        mapperTableEntry.setName("nb_pages"); //$NON-NLS-1$
        mapperTableEntry.setExpression(""); //$NON-NLS-1$
        mapperTableEntry.setOperator("Integer"); //$NON-NLS-1$
        tableEntries.add(mapperTableEntry);

        mapperTable.setMetadataTableEntries(tableEntries);

        tables.add(mapperTable);

        // #########################################################################

        mapperTable = new ExternalDbMapTable();
        mapperTable.setName("page"); //$NON-NLS-1$

        tableEntries = new ArrayList<ExternalDbMapEntry>();

        mapperTableEntry = new ExternalDbMapEntry();
        mapperTableEntry.setName("id_page"); //$NON-NLS-1$
        mapperTableEntry.setExpression(""); //$NON-NLS-1$
        mapperTableEntry.setOperator("Integer"); //$NON-NLS-1$
        tableEntries.add(mapperTableEntry);

        mapperTableEntry = new ExternalDbMapEntry();
        mapperTableEntry.setName("id_book"); //$NON-NLS-1$
        mapperTableEntry.setExpression(gen.getTableColumnVariable("book", "id")); //$NON-NLS-1$ //$NON-NLS-2$
        mapperTableEntry.setOperator("String"); //$NON-NLS-1$
        tableEntries.add(mapperTableEntry);

        mapperTableEntry = new ExternalDbMapEntry();
        mapperTableEntry.setName("content"); //$NON-NLS-1$
        mapperTableEntry.setExpression(gen.getTableColumnVariable("book", "name")); //$NON-NLS-1$ //$NON-NLS-2$
        mapperTableEntry.setOperator("int"); //$NON-NLS-1$
        tableEntries.add(mapperTableEntry);

        mapperTable.setMetadataTableEntries(tableEntries);

        if (currentTest == TEST.ONE_INNER_JOIN_AND_A_TABLE_WITH_INNER_JOIN_REJECT_WITHOUT_REGULAR_TABLE) {
            // nothing
        } else {
            tables.add(mapperTable);
        }

        // #########################################################################

        mapperTable = new ExternalDbMapTable();
        mapperTable.setName("userInput"); //$NON-NLS-1$

        tableEntries = new ArrayList<ExternalDbMapEntry>();

        mapperTableEntry = new ExternalDbMapEntry();
        mapperTableEntry.setName("id"); //$NON-NLS-1$
        mapperTableEntry.setExpression(""); //$NON-NLS-1$
        mapperTableEntry.setOperator("String"); //$NON-NLS-1$
        tableEntries.add(mapperTableEntry);

        mapperTableEntry = new ExternalDbMapEntry();
        mapperTableEntry.setName("id_book"); //$NON-NLS-1$
        mapperTableEntry.setExpression(gen.getTableColumnVariable("book", "id")); //$NON-NLS-1$ //$NON-NLS-2$
        mapperTableEntry.setOperator("String"); //$NON-NLS-1$
        tableEntries.add(mapperTableEntry);

        if (currentTest == TEST.ONE_INNER_JOIN_AND_A_TABLE_WITH_INNER_JOIN_REJECT_WITHOUT_REGULAR_TABLE) {
            // nothing
        } else {
            mapperTableEntry = new ExternalDbMapEntry();
            mapperTableEntry.setName("id_page"); //$NON-NLS-1$
            mapperTableEntry.setOperator("String"); //$NON-NLS-1$
            mapperTableEntry.setExpression(gen.getTableColumnVariable("page", "id_page") + " . " //$NON-NLS-1$ //$NON-NLS-2$ //$NON-NLS-3$
                    + gen.getTableColumnVariable("book", "name")); //$NON-NLS-1$ //$NON-NLS-2$
            tableEntries.add(mapperTableEntry);
        }

        mapperTable.setMetadataTableEntries(tableEntries);

        tables.add(mapperTable);

        // #########################################################################
        // #########################################################################

        externalData.setInputTables(tables);

        // INPUTS
        // ///////////////////////////////////////////

        // ///////////////////////////////////////////
        // VARS

        tables = new ArrayList<ExternalDbMapTable>();

        // #########################################################################
        // #########################################################################

        mapperTable = new ExternalDbMapTable();
        mapperTable.setName(VarsTable.PREFIX_VARS_TABLE_NAME);

        tableEntries = new ArrayList<ExternalDbMapEntry>();

        mapperTableEntry = new ExternalDbMapEntry();
        mapperTableEntry.setName("upperCaseContent"); //$NON-NLS-1$
        mapperTableEntry.setOperator("String"); //$NON-NLS-1$
        mapperTableEntry.setExpression("uc " + gen.getTableColumnVariable("page", "content") + " + " //$NON-NLS-1$ //$NON-NLS-2$ //$NON-NLS-3$ //$NON-NLS-4$
                + gen.getTableColumnVariable("book", "id_book") + " - 2 * " //$NON-NLS-1$ //$NON-NLS-2$ //$NON-NLS-3$
                + gen.getTableColumnVariable("book", "id_book")); //$NON-NLS-1$ //$NON-NLS-2$
        tableEntries.add(mapperTableEntry);

        mapperTableEntry = new ExternalDbMapEntry();
        mapperTableEntry.setName("newId"); //$NON-NLS-1$
        mapperTableEntry.setExpression(gen.getTableColumnVariable("book", "id_book") + " + 1"); //$NON-NLS-1$ //$NON-NLS-2$ //$NON-NLS-3$
        mapperTableEntry.setOperator("String"); //$NON-NLS-1$
        tableEntries.add(mapperTableEntry);

        mapperTable.setMetadataTableEntries(tableEntries);

        tables.add(mapperTable);

        // #########################################################################
        // #########################################################################

        externalData.setVarsTables(tables);

        // VARS
        // ///////////////////////////////////////////

        // ///////////////////////////////////////////
        // OUTPUTS

        tables = new ArrayList<ExternalDbMapTable>();

        // #########################################################################
        // #########################################################################

        mapperTable = new ExternalDbMapTable();
        mapperTable.setName("user"); //$NON-NLS-1$

        tableEntries = new ArrayList<ExternalDbMapEntry>();

        mapperTableEntry = new ExternalDbMapEntry();
        mapperTableEntry.setName("idUser"); //$NON-NLS-1$
        mapperTableEntry.setExpression(gen.getTableColumnVariable(VarsTable.PREFIX_VARS_TABLE_NAME, "newId")); //$NON-NLS-1$
        mapperTableEntry.setOperator("String"); //$NON-NLS-1$
        tableEntries.add(mapperTableEntry);

        mapperTableEntry = new ExternalDbMapEntry();
        mapperTableEntry.setName("idBook"); //$NON-NLS-1$
        mapperTableEntry.setExpression(gen.getTableColumnVariable("book", "id_book")); //$NON-NLS-1$ //$NON-NLS-2$
        mapperTableEntry.setOperator("String"); //$NON-NLS-1$
        tableEntries.add(mapperTableEntry);

        mapperTableEntry = new ExternalDbMapEntry();
        mapperTableEntry.setName("content"); //$NON-NLS-1$
        mapperTableEntry.setExpression(""); //$NON-NLS-1$
        mapperTableEntry.setOperator("String"); //$NON-NLS-1$
        tableEntries.add(mapperTableEntry);

        mapperTable.setMetadataTableEntries(tableEntries);

        // CONSTRAINTS

        tableEntries = new ArrayList<ExternalDbMapEntry>();

        mapperTableEntry = new ExternalDbMapEntry();
        mapperTableEntry.setExpression(" $book[id] == 1"); //$NON-NLS-1$
        mapperTableEntry.setExpression(" " + gen.getTableColumnVariable("book", "id") + " == 1"); //$NON-NLS-1$ //$NON-NLS-2$ //$NON-NLS-3$ //$NON-NLS-4$
        tableEntries.add(mapperTableEntry);

        mapperTable.setCustomConditionsEntries(tableEntries);

        if (currentTest == TEST.ONE_INNER_JOIN_AND_A_TABLE_WITH_INNER_JOIN_REJECT_WITHOUT_REGULAR_TABLE) {
            // nothing
        } else {
            tables.add(mapperTable);
        }

        // #########################################################################
        // #########################################################################

        mapperTable = new ExternalDbMapTable();
        mapperTable.setName("newBook"); //$NON-NLS-1$

        tableEntries = new ArrayList<ExternalDbMapEntry>();

        mapperTableEntry = new ExternalDbMapEntry();
        mapperTableEntry.setName("newIdBook"); //$NON-NLS-1$
        mapperTableEntry.setExpression(gen.getTableColumnVariable(VarsTable.PREFIX_VARS_TABLE_NAME, "newId")); //$NON-NLS-1$
        mapperTableEntry.setOperator("String"); //$NON-NLS-1$
        tableEntries.add(mapperTableEntry);

        mapperTableEntry = new ExternalDbMapEntry();
        mapperTableEntry.setName("id_book"); //$NON-NLS-1$
        mapperTableEntry.setExpression(gen.getTableColumnVariable("book", "id_book")); //$NON-NLS-1$ //$NON-NLS-2$
        mapperTableEntry.setOperator("String"); //$NON-NLS-1$
        tableEntries.add(mapperTableEntry);

        mapperTableEntry = new ExternalDbMapEntry();
        mapperTableEntry.setName("content"); //$NON-NLS-1$
        mapperTableEntry.setOperator("String"); //$NON-NLS-1$
        mapperTableEntry.setExpression(""); //$NON-NLS-1$
        tableEntries.add(mapperTableEntry);

        mapperTable.setMetadataTableEntries(tableEntries);

        tableEntries = new ArrayList<ExternalDbMapEntry>();

        // #########################################################################
        // CONSTRAINTS newBook

        mapperTableEntry = new ExternalDbMapEntry();
        mapperTableEntry.setExpression(" test == 3 "); //$NON-NLS-1$
        mapperTableEntry.setOperator("String"); //$NON-NLS-1$
        tableEntries.add(mapperTableEntry);

        // mapperTableEntry = new ExternalMapperTableEntry();
        // mapperTableEntry.setExpression(gen.getTableColumnVariable(VarsTable.PREFIX_VARS_TABLE_NAME, "newId") + " ==
        // 1");
        // tableEntries.add(mapperTableEntry);
        //
        // mapperTableEntry = new ExternalMapperTableEntry();
        // mapperTableEntry.setExpression(gen.getTableColumnVariable(VarsTable.PREFIX_VARS_TABLE_NAME, "newId") + " ==
        // 2");
        // tableEntries.add(mapperTableEntry);
        //
        // mapperTableEntry = new ExternalMapperTableEntry();
        // mapperTableEntry.setExpression(gen.getTableColumnVariable(VarsTable.PREFIX_VARS_TABLE_NAME, "newId") + " ==
        // 3");
        // tableEntries.add(mapperTableEntry);
        //
        mapperTable.setCustomConditionsEntries(tableEntries);

        if (currentTest == TEST.NO_INNER_JOIN_AND_A_TABLE_WITH_FILTER_AND_A_TABLE_WITH_REJECT
                || currentTest == TEST.NO_INNER_JOIN_AND_A_TABLE_WITH_FILTER_AND_A_TABLE_WITH_REJECT
                || currentTest == TEST.ONE_INNER_JOIN_AND_A_TABLE_WITH_INNER_JOIN_REJECT_WITHOUT_REGULAR_TABLE

        ) {

        } else {
            tables.add(mapperTable);
        }

        // #########################################################################
        // #########################################################################

        // REJECTS

        // #########################################################################
        // #########################################################################

        mapperTable = new ExternalDbMapTable();
        mapperTable.setName("newPageRejected"); //$NON-NLS-1$

        tableEntries = new ArrayList<ExternalDbMapEntry>();

        mapperTableEntry = new ExternalDbMapEntry();
        mapperTableEntry.setName("id_page"); //$NON-NLS-1$
        mapperTableEntry.setExpression(""); //$NON-NLS-1$
        mapperTableEntry.setOperator("int"); //$NON-NLS-1$
        tableEntries.add(mapperTableEntry);

        mapperTableEntry = new ExternalDbMapEntry();
        mapperTableEntry.setName("id_book"); //$NON-NLS-1$
        mapperTableEntry.setExpression(gen.getTableColumnVariable("page", "id_book")); //$NON-NLS-1$ //$NON-NLS-2$
        mapperTableEntry.setOperator("String"); //$NON-NLS-1$
        tableEntries.add(mapperTableEntry);

        mapperTableEntry = new ExternalDbMapEntry();
        mapperTableEntry.setName("content"); //$NON-NLS-1$
        mapperTableEntry
                .setExpression(gen.getTableColumnVariable(VarsTable.PREFIX_VARS_TABLE_NAME, "upperCaseContent")); //$NON-NLS-1$
        mapperTableEntry.setOperator("String"); //$NON-NLS-1$
        tableEntries.add(mapperTableEntry);

        mapperTable.setMetadataTableEntries(tableEntries);

        // #########################################################################

        // FILTERS

        tableEntries = new ArrayList<ExternalDbMapEntry>();

        mapperTableEntry = new ExternalDbMapEntry();
        if (currentTest == TEST.ONE_INNER_JOIN_AND_NOT_ANY_REJECT_AND_ONE_OUT_TABLE_WITHOUT_FILTER
                || currentTest == TEST.ONE_INNER_JOIN_AND_ONLY_ONE_INNER_JOIN_REJECT_AND_ONE_OUT_TABLE_WITHOUT_FILTER
                || currentTest == TEST.NO_INNER_JOIN_AND_ONLY_ONE_INNER_JOIN_REJECT_AND_ONE_OUT_TABLE_WITHOUT_FILTER
                || currentTest == TEST.ONE_INNER_JOIN_AND_A_TABLE_WITH_REJECT_AND_INNER_JOIN_REJECT_AND_ONE_OUT_TABLE_WITHOUT_FILTER
                || currentTest == TEST.NO_INNER_JOIN_AND_ONE_INNER_JOIN_REJECT_AND_2_REJECT_TABLE_WITHOUT_FILTER
                || currentTest == TEST.NO_INNER_JOIN_AND_A_TABLE_WITH_FILTER_AND_A_TABLE_WITH_REJECT
                || currentTest == TEST.ONE_INNER_JOIN_AND_A_TABLE_WITH_INNER_JOIN_REJECT_WITHOUT_REGULAR_TABLE) {
            // nothing
        } else {
            mapperTableEntry.setExpression(gen.getTableColumnVariable("page", "newId") + " == 1"); //$NON-NLS-1$ //$NON-NLS-2$ //$NON-NLS-3$
            tableEntries.add(mapperTableEntry);
        }

        mapperTable.setCustomConditionsEntries(tableEntries);

        tables.add(mapperTable);

        // #########################################################################
        // #########################################################################

        mapperTable = new ExternalDbMapTable();
        mapperTable.setName("newPageRejected2"); //$NON-NLS-1$

        tableEntries = new ArrayList<ExternalDbMapEntry>();

        mapperTableEntry = new ExternalDbMapEntry();
        mapperTableEntry.setName("id_page"); //$NON-NLS-1$
        mapperTableEntry.setExpression(""); //$NON-NLS-1$
        mapperTableEntry.setOperator("String"); //$NON-NLS-1$
        tableEntries.add(mapperTableEntry);

        mapperTableEntry = new ExternalDbMapEntry();
        mapperTableEntry.setName("id_book"); //$NON-NLS-1$
        mapperTableEntry.setExpression(gen.getTableColumnVariable("page", "id_book")); //$NON-NLS-1$ //$NON-NLS-2$
        mapperTableEntry.setOperator("String"); //$NON-NLS-1$
        tableEntries.add(mapperTableEntry);

        mapperTableEntry = new ExternalDbMapEntry();
        mapperTableEntry.setName("content"); //$NON-NLS-1$
        mapperTableEntry
                .setExpression(gen.getTableColumnVariable(VarsTable.PREFIX_VARS_TABLE_NAME, "upperCaseContent")); //$NON-NLS-1$
        mapperTableEntry.setOperator("int"); //$NON-NLS-1$
        tableEntries.add(mapperTableEntry);

        mapperTable.setMetadataTableEntries(tableEntries);

        if (currentTest == TEST.NO_INNER_JOIN_AND_A_TABLE_WITH_FILTER_AND_A_TABLE_WITH_REJECT
                || currentTest == TEST.ONE_INNER_JOIN_AND_2_TABLES_WITH_FILTER_AND_OTHER_TABLE_WITH_REJECT_AND_REJECT_INNER_JOIN
                || currentTest == TEST.ONE_INNER_JOIN_AND_A_TABLE_WITH_FILTER_AND_A_TABLE_WITH_REJECT
                || currentTest == TEST.ONE_INNER_JOIN_AND_A_TABLE_WITH_REJECT_AND_INNER_JOIN_REJECT_AND_ONE_OUT_TABLE_WITHOUT_FILTER
                || currentTest == TEST.ONE_INNER_JOIN_AND_A_TABLE_WITH_INNER_JOIN_REJECT_WITHOUT_REGULAR_TABLE) {
            // nothing
        } else if (currentTest == TEST.ONE_INNER_JOIN_AND_A_TABLE_WITH_FILTER_AND_A_TABLE_WITH_REJECT_AND_A_TABLE_WITH_REJECT_INNER_JOIN) {
            tables.add(mapperTable);
        } else {
            tables.add(mapperTable);
        }

        // #########################################################################
        // #########################################################################

        externalData.setOutputTables(tables);
        // OUTPUTS
        // ///////////////////////////////////////////

        // #########################################################################
        // #########################################################################

        return externalData;
    }

    /**
     * DOC amaumont Comment method "generateMetadataListIn".
     * 
     * @return
     */
    public List<IConnection> generateConnectionListIn() {

        if (random) {
            return generateRandomConnectionListIn(TableType.INPUT);
        } else {
            return generateConstantConnectionListIn();
        }

    }

    /**
     * DOC amaumont Comment method "generateMetadataListOut".
     * 
     * @param b
     * @return
     */
    public List<IMetadataTable> generateMetadataListOut() {
        if (random) {
            return generateRandomMetadataList(TableType.OUTPUT);
        } else {
            return generateConstantMetadataListOut();
        }

    }

    /**
     * DOC amaumont Comment method "generateConstantMetadataListIn".
     * 
     * @return
     */
    private List<IConnection> generateRandomConnectionListIn(TableType tableType) {
        List<IMetadataTable> metadataTableList = generateRandomMetadataList(tableType);
        connectionList = new ArrayList<IConnection>();
        int i = 0;
        int indexMain = rand.nextInt(metadataTableList.size());
        for (IMetadataTable table : metadataTableList) {
            Connection connection = new Connection();
            connection.setName(table.getTableName());
            connection.setMetadataTable(table);
            connection.setLineStyle((i == indexMain) ? EConnectionType.FLOW_MAIN : EConnectionType.FLOW_REF);
            connectionList.add(connection);
            i++;
        }
        return connectionList;
    }

    private List<IMetadataTable> generateRandomMetadataList(TableType tableType) {
        metadataListOut = new ArrayList<IMetadataTable>();
        for (int i = 1; i <= N_TABLES; i++) {
            MetadataTable metadataTable = new MetadataTable();
            String name = tableType.getBaseTableName() + i;
            metadataTable.setTableName(name);
            metadataListOut.add(metadataTable);
            ArrayList<IMetadataColumn> metadatColumns = new ArrayList<IMetadataColumn>();
            metadataTable.setListColumns(metadatColumns);
            for (int j = 1; j <= N_FIELDS; j++) {
                MetadataColumn metadataColumn = new MetadataColumn();
                // metadataColumn.setLabel(FIELD_NAME + j);
                String baseColumnName = FIELDS[rand.nextInt(FIELDS.length)];
                if (fixedData) {
                    baseColumnName = COLUMN_NAME;
                }
                metadataColumn.setLabel(baseColumnName + j);
                metadataColumn.setKey(rand.nextBoolean());
                // EMetadataType[] types = EMetadataType.values();
                // metadataColumn.setType(types[rand.nextInt(types.length - 1)].toString());
                metadataColumn.setNullable(rand.nextBoolean());
                metadatColumns.add(metadataColumn);
            }
        }

        return metadataListOut;
    }

    /**
     * DOC amaumont Comment method "generateRandomMetadataListIn".
     * 
     * @return
     */
    private List<IConnection> generateConstantConnectionListIn() {
        connectionList = new ArrayList<IConnection>();

        Connection connection = new Connection();
        connection.setName("book"); //$NON-NLS-1$
        connection.setLineStyle(EConnectionType.FLOW_MAIN);

        MetadataTable metadataTable = new MetadataTable();
        metadataTable.setTableName("book2"); //$NON-NLS-1$

        connection.setMetadataTable(metadataTable);

        ArrayList<IMetadataColumn> metadatColumns = new ArrayList<IMetadataColumn>();

        MetadataColumn metadataColumn = new MetadataColumnTest();
        metadataColumn.setLabel("id_book"); //$NON-NLS-1$
        metadataColumn.setKey(true);
        // metadataColumn.setType(EMetadataType.INTEGER.toString());
        metadatColumns.add(metadataColumn);

        metadataColumn = new MetadataColumnTest();
        metadataColumn.setLabel("name"); //$NON-NLS-1$
        metadataColumn.setKey(false);
        // metadataColumn.setType(EMetadataType.STRING.toString());
        metadatColumns.add(metadataColumn);

        metadataColumn = new MetadataColumnTest();
        metadataColumn.setLabel("nb_pages"); //$NON-NLS-1$
        metadataColumn.setKey(false);
        // metadataColumn.setType(EMetadataType.INTEGER.toString());
        metadatColumns.add(metadataColumn);

        metadataTable.setListColumns(metadatColumns);

        connectionList.add(connection);

        connection = new Connection();
        connection.setName("page"); //$NON-NLS-1$
        connection.setLineStyle(EConnectionType.FLOW_REF);

        metadataTable = new MetadataTable();
        metadataTable.setTableName("page2"); //$NON-NLS-1$

        connection.setMetadataTable(metadataTable);

        metadatColumns = new ArrayList<IMetadataColumn>();

        metadataColumn = new MetadataColumnTest();
        metadataColumn.setLabel("id"); //$NON-NLS-1$
        // metadataColumn.setType(EMetadataType.INTEGER.toString());
        metadatColumns.add(metadataColumn);

        metadataColumn = new MetadataColumnTest();
        metadataColumn.setLabel("id_book"); //$NON-NLS-1$
        metadataColumn.setKey(true);
        // metadataColumn.setType(EMetadataType.INTEGER.toString());
        metadatColumns.add(metadataColumn);

        metadataColumn = new MetadataColumnTest();
        metadataColumn.setLabel("content"); //$NON-NLS-1$
        metadataColumn.setKey(true);
        // metadataColumn.setType(EMetadataType.STRING.toString());
        metadatColumns.add(metadataColumn);

        metadataTable.setListColumns(metadatColumns);

        connectionList.add(connection);

        connection = new Connection();
        connection.setName("userInput"); //$NON-NLS-1$
        connection.setLineStyle(EConnectionType.FLOW_REF);

        metadataTable = new MetadataTable();
        metadataTable.setTableName("user_input50"); //$NON-NLS-1$

        connection.setMetadataTable(metadataTable);

        metadatColumns = new ArrayList<IMetadataColumn>();

        metadataColumn = new MetadataColumnTest();
        metadataColumn.setLabel("id"); //$NON-NLS-1$
        // metadataColumn.setType(EMetadataType.INTEGER.toString());
        metadatColumns.add(metadataColumn);

        metadataColumn = new MetadataColumnTest();
        metadataColumn.setLabel("id_book"); //$NON-NLS-1$
        metadataColumn.setKey(true);
        // metadataColumn.setType(EMetadataType.INTEGER.toString());
        metadatColumns.add(metadataColumn);

        metadataColumn = new MetadataColumnTest();
        metadataColumn.setLabel("id_page_different"); //$NON-NLS-1$
        metadataColumn.setKey(true);
        // metadataColumn.setType(EMetadataType.STRING.toString());
        metadatColumns.add(metadataColumn);

        metadataTable.setListColumns(metadatColumns);

        connectionList.add(connection);

        return connectionList;
    }

    /**
     * DOC amaumont Comment method "generateMetadataListOut".
     * 
     * @return
     */
    public List<IMetadataTable> generateConstantMetadataListOut() {
        List<IMetadataTable> list = new ArrayList<IMetadataTable>();

        MetadataTable metadataTable = new MetadataTable();
        metadataTable.setTableName("newBook"); //$NON-NLS-1$

        ArrayList<IMetadataColumn> metadatColumns = new ArrayList<IMetadataColumn>();

        MetadataColumn metadataColumn = new MetadataColumnTest();
        metadataColumn.setLabel("newIdBook"); //$NON-NLS-1$
        metadataColumn.setKey(true);
        // metadataColumn.setType(EMetadataType.INTEGER.toString());
        metadatColumns.add(metadataColumn);

        metadataColumn = new MetadataColumnTest();
        metadataColumn.setLabel("name"); //$NON-NLS-1$
        metadataColumn.setKey(false);
        // metadataColumn.setType(EMetadataType.STRING.toString());
        metadatColumns.add(metadataColumn);

        metadataColumn = new MetadataColumnTest();
        metadataColumn.setLabel("nb_pages"); //$NON-NLS-1$
        metadataColumn.setKey(false);
        // metadataColumn.setType(EMetadataType.INTEGER.toString());
        metadatColumns.add(metadataColumn);

        metadataTable.setListColumns(metadatColumns);

        list.add(metadataTable);

        metadataTable = new MetadataTable();
        metadataTable.setTableName("user"); //$NON-NLS-1$

        metadatColumns = new ArrayList<IMetadataColumn>();

        metadataColumn = new MetadataColumnTest();
        metadataColumn.setLabel("idUser"); //$NON-NLS-1$
        metadataColumn.setKey(true);
        // metadataColumn.setType(EMetadataType.INTEGER.toString());
        metadatColumns.add(metadataColumn);

        metadataColumn = new MetadataColumnTest();
        metadataColumn.setLabel("idBook"); //$NON-NLS-1$
        metadataColumn.setKey(false);
        // metadataColumn.setType(EMetadataType.STRING.toString());
        metadatColumns.add(metadataColumn);

        metadataColumn = new MetadataColumnTest();
        metadataColumn.setLabel("content"); //$NON-NLS-1$
        metadataColumn.setKey(false);
        // metadataColumn.setType(EMetadataType.INTEGER.toString());
        metadatColumns.add(metadataColumn);

        metadataTable.setListColumns(metadatColumns);

        list.add(metadataTable);

        metadataTable = new MetadataTable();
        metadataTable.setTableName("newPageRejected"); //$NON-NLS-1$

        metadatColumns = new ArrayList<IMetadataColumn>();

        metadataColumn = new MetadataColumnTest();
        metadataColumn.setLabel("id"); //$NON-NLS-1$
        metadataColumn.setKey(true);
        // metadataColumn.setType(EMetadataType.INTEGER.toString());
        metadatColumns.add(metadataColumn);

        metadataColumn = new MetadataColumnTest();
        metadataColumn.setLabel("id_book"); //$NON-NLS-1$
        metadataColumn.setKey(false);
        // metadataColumn.setType(EMetadataType.INTEGER.toString());
        metadatColumns.add(metadataColumn);

        metadataColumn = new MetadataColumnTest();
        metadataColumn.setLabel("content"); //$NON-NLS-1$
        metadataColumn.setKey(false);
        // metadataColumn.setType(EMetadataType.STRING.toString());
        metadatColumns.add(metadataColumn);

        metadataTable.setListColumns(metadatColumns);

        list.add(metadataTable);

        metadataTable = new MetadataTable();
        metadataTable.setTableName("newPageRejected2"); //$NON-NLS-1$

        metadatColumns = new ArrayList<IMetadataColumn>();

        metadataColumn = new MetadataColumnTest();
        metadataColumn.setLabel("id"); //$NON-NLS-1$
        metadataColumn.setKey(true);
        // metadataColumn.setType(EMetadataType.INTEGER.toString());
        metadatColumns.add(metadataColumn);

        metadataColumn = new MetadataColumnTest();
        metadataColumn.setLabel("id_book"); //$NON-NLS-1$
        metadataColumn.setKey(false);
        // metadataColumn.setType(EMetadataType.INTEGER.toString());
        metadatColumns.add(metadataColumn);

        metadataColumn = new MetadataColumnTest();
        metadataColumn.setLabel("content"); //$NON-NLS-1$
        metadataColumn.setKey(false);
        // metadataColumn.setType(EMetadataType.STRING.toString());
        metadatColumns.add(metadataColumn);

        metadataTable.setListColumns(metadatColumns);

        list.add(metadataTable);

        return list;
    }

    /**
     * 
     * DOC amaumont MapperDataTestGenerator class global comment. Detailled comment <br/>
     * 
     * $Id: MapperDataTestGenerator.java 2077 2007-02-15 11:16:46Z amaumont $
     * 
     */
    class MetadataColumnTest extends MetadataColumn {

        public MetadataColumnTest() {
            super();
        }

        public MetadataColumnTest(IMetadataColumn metadataColumn) {
            super(metadataColumn);
        }

        @Override
        public String getTalendType() {
            return ""; //$NON-NLS-1$
        }

    }

    /**
     * 
     * DOC amaumont MapperDataTestGenerator class global comment. Detailled comment <br/>
     * 
     * $Id: MapperDataTestGenerator.java 2077 2007-02-15 11:16:46Z amaumont $
     * 
     */
    class Connection extends AbstractConnection {

    };

    /**
     * 
     * DOC amaumont MapperDataTestGenerator class global comment. Detailled comment <br/>
     * 
     * $Id: MapperDataTestGenerator.java 2077 2007-02-15 11:16:46Z amaumont $
     * 
     */
    class Node extends AbstractNode {

        public List<? extends INodeReturn> getReturns() {
            return new ArrayList<INodeReturn>();
        }

        public void metadataInputChanged(IODataComponent dataComponent, String connectionToApply) {
        }

        public void metadataOutputChanged(IODataComponent dataComponent, String connectionToApply) {
        }
    };

    public ArrayList<IConnection> getConnectionList() {
        return this.connectionList;
    }

    public ArrayList<IMetadataTable> getMetadataListOut() {
        return this.metadataListOut;
    }

    public ExternalDbMapData getExternalData() {
        return this.externalData;
    };

}
