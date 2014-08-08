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
package org.talend.repository.nosql.db.provider.neo4j;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import org.apache.commons.lang.StringUtils;
import org.eclipse.emf.common.util.EList;
import org.talend.core.model.metadata.MetadataToolHelper;
import org.talend.core.model.metadata.builder.connection.ConnectionFactory;
import org.talend.core.model.metadata.builder.connection.MetadataColumn;
import org.talend.core.model.metadata.types.JavaType;
import org.talend.core.model.metadata.types.JavaTypesManager;
import org.talend.repository.model.nosql.NoSQLConnection;
import org.talend.repository.nosql.db.common.neo4j.INeo4jConstants;
import org.talend.repository.nosql.db.util.neo4j.Neo4jConnectionUtil;
import org.talend.repository.nosql.exceptions.NoSQLExtractSchemaException;
import org.talend.repository.nosql.exceptions.NoSQLServerException;
import org.talend.repository.nosql.metadata.AbstractMetadataProvider;
import orgomg.cwm.objectmodel.core.CoreFactory;
import orgomg.cwm.objectmodel.core.TaggedValue;

/**
 * 
 * created by ycbai on Jul 22, 2014 Detailled comment
 * 
 */
public class Neo4jMetadataProvider extends AbstractMetadataProvider {

    private static final String DOT = "."; //$NON-NLS-1$

    private static final int COUNT_ROWS = 50;

    @Override
    public List<MetadataColumn> extractColumns(NoSQLConnection connection, String cypher) throws NoSQLExtractSchemaException {
        List<MetadataColumn> metadataColumns = new ArrayList<MetadataColumn>();
        if (connection == null || cypher == null) {
            return metadataColumns;
        }
        try {
            metadataColumns.addAll(extractTheColumns(connection, cypher));
        } catch (Exception e) {
            throw new NoSQLExtractSchemaException(e);
        }

        return metadataColumns;
    }

    private List<MetadataColumn> extractTheColumns(NoSQLConnection connection, String cypher) throws NoSQLExtractSchemaException {
        List<MetadataColumn> metadataColumns = new ArrayList<MetadataColumn>();
        try {
            Object db = Neo4jConnectionUtil.getDB(connection);
            if (db == null) {
                return metadataColumns;
            }
            Iterator<Map<String, Object>> resultIterator = Neo4jConnectionUtil.getResultIterator(connection, cypher);
            if (resultIterator == null) {
                return metadataColumns;
            }
            List<String> columnLabels = new ArrayList<String>();
            int rowNum = 0;
            while (resultIterator.hasNext()) {
                if (rowNum > COUNT_ROWS) {
                    break;
                }
                rowNum++;
                int columnIndex = 0;
                Map<String, Object> row = resultIterator.next();
                for (Entry<String, Object> column : row.entrySet()) {
                    String key = column.getKey();
                    Object value = column.getValue();
                    if (StringUtils.isEmpty(key) || value == null) {
                        continue;
                    }
                    String formalColumnName = getFormalColumnName(key, columnIndex, columnLabels);
                    MetadataColumn metaColumn = ConnectionFactory.eINSTANCE.createMetadataColumn();
                    metaColumn.setName(formalColumnName);
                    metaColumn.setLabel(formalColumnName);
                    JavaType javaType = JavaTypesManager.getJavaTypeFromName(value.getClass().getSimpleName());
                    if (javaType == null) {
                        javaType = JavaTypesManager.STRING;
                    }
                    metaColumn.setTalendType(javaType.getId());
                    EList<TaggedValue> keyTaggedValue = metaColumn.getTaggedValue();
                    TaggedValue keyTV = CoreFactory.eINSTANCE.createTaggedValue();
                    keyTV.setTag(INeo4jConstants.RETURN_PARAMETER);
                    keyTV.setValue(key);
                    keyTaggedValue.add(keyTV);
                    metadataColumns.add(metaColumn);
                    columnIndex++;
                }
            }
        } catch (Exception e) {
            throw new NoSQLExtractSchemaException(e);
        }

        return metadataColumns;
    }

    private String getFormalColumnName(String columnName, int columnIndex, List<String> columnLabels) {
        String formalColumnName = columnName;
        if (formalColumnName.indexOf(DOT) != -1) {
            formalColumnName = formalColumnName.substring(formalColumnName.indexOf(DOT) + 1);
        }
        formalColumnName = MetadataToolHelper.validateColumnName(formalColumnName, columnIndex, columnLabels);

        return formalColumnName;
    }

    /*
     * (non-Javadoc)
     * 
     * @see org.talend.repository.nosql.metadata.IMetadataProvider#checkConnection(org.talend.repository.model.nosql.
     * NoSQLConnection)
     */
    @Override
    public boolean checkConnection(NoSQLConnection connection) throws NoSQLServerException {
        return Neo4jConnectionUtil.checkConnection(connection);
    }
}
