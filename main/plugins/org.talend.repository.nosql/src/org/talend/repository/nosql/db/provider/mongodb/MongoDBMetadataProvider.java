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
package org.talend.repository.nosql.db.provider.mongodb;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import org.talend.core.model.metadata.MetadataToolHelper;
import org.talend.core.model.metadata.builder.connection.ConnectionFactory;
import org.talend.core.model.metadata.builder.connection.MetadataColumn;
import org.talend.core.model.metadata.types.JavaType;
import org.talend.core.model.metadata.types.JavaTypesManager;
import org.talend.repository.model.nosql.NoSQLConnection;
import org.talend.repository.nosql.db.common.mongodb.IMongoConstants;
import org.talend.repository.nosql.db.common.mongodb.IMongoDBAttributes;
import org.talend.repository.nosql.db.util.mongodb.MongoDBConnectionUtil;
import org.talend.repository.nosql.exceptions.NoSQLExtractSchemaException;
import org.talend.repository.nosql.exceptions.NoSQLServerException;
import org.talend.repository.nosql.metadata.AbstractMetadataProvider;
import org.talend.repository.nosql.metadata.NoSQLSchemaUtil;
import org.talend.repository.nosql.model.INoSQLSchemaNode;
import org.talend.repository.nosql.reflection.NoSQLReflection;

public class MongoDBMetadataProvider extends AbstractMetadataProvider {

    @Override
    public List<MetadataColumn> extractColumns(NoSQLConnection connection, INoSQLSchemaNode node)
            throws NoSQLExtractSchemaException {
        List<MetadataColumn> metadataColumns = new ArrayList<MetadataColumn>();
        if (connection == null || node == null) {
            return metadataColumns;
        }
        try {
            if (IMongoConstants.COLLECTION.equals(node.getNodeType())) {
                String dbName = null;
                INoSQLSchemaNode parent = node.getParent();
                if (parent != null && IMongoConstants.DATABASE.equals(parent.getNodeType())) {
                    dbName = parent.getName();
                } else {
                    dbName = connection.getAttributes().get(IMongoDBAttributes.DATABASE);
                }
                if (dbName == null) {
                    return metadataColumns;
                }
                String collectionName = node.getName();
                metadataColumns.addAll(extractTheColumns(connection, dbName, collectionName));
            }
        } catch (Exception e) {
            throw new NoSQLExtractSchemaException(e);
        }

        return metadataColumns;
    }

    @Override
    public List<MetadataColumn> extractColumns(NoSQLConnection connection, String tableName) throws NoSQLExtractSchemaException {
        List<MetadataColumn> metadataColumns = new ArrayList<MetadataColumn>();
        if (connection == null || tableName == null) {
            return metadataColumns;
        }
        try {
            String dbName = NoSQLSchemaUtil.getSchemaNameByTableLabel(connection, tableName);
            metadataColumns.addAll(extractTheColumns(connection, dbName, tableName));
        } catch (Exception e) {
            throw new NoSQLExtractSchemaException(e);
        }

        return metadataColumns;
    }

    private List<MetadataColumn> extractTheColumns(NoSQLConnection connection, String dbName, String collectionName)
            throws NoSQLExtractSchemaException {
        List<MetadataColumn> metadataColumns = new ArrayList<MetadataColumn>();
        try {
            Object db = MongoDBConnectionUtil.getDB(connection, dbName);
            if (db == null) {
                return metadataColumns;
            }
            Object dbCollection = NoSQLReflection.invokeMethod(db, "getCollection", new Object[] { collectionName }); //$NON-NLS-1$
            Object dbCursor = NoSQLReflection.invokeMethod(dbCollection, "find"); //$NON-NLS-1$
            Boolean hasNext = (Boolean) NoSQLReflection.invokeMethod(dbCursor, "hasNext"); //$NON-NLS-1$
            if (hasNext) {
                Object dbObject = NoSQLReflection.invokeMethod(dbCursor, "next"); //$NON-NLS-1$
                Set<String> columnNames = (Set<String>) NoSQLReflection.invokeMethod(dbObject, "keySet"); //$NON-NLS-1$
                for (String colName : columnNames) {
                    MetadataColumn column = ConnectionFactory.eINSTANCE.createMetadataColumn();
                    colName = MetadataToolHelper.validateValue(colName);
                    column.setName(colName);
                    column.setLabel(colName);
                    Object value = NoSQLReflection.invokeMethod(dbObject, "get", new Object[] { colName }); //$NON-NLS-1$
                    JavaType javaType = JavaTypesManager.getJavaTypeFromName(value.getClass().getSimpleName());
                    if (javaType == null) {
                        javaType = JavaTypesManager.STRING;
                    }
                    column.setTalendType(javaType.getId());
                    metadataColumns.add(column);
                }
            }
        } catch (Exception e) {
            throw new NoSQLExtractSchemaException(e);
        }

        return metadataColumns;
    }

    /*
     * (non-Javadoc)
     * 
     * @see org.talend.repository.nosql.metadata.IMetadataProvider#checkConnection(org.talend.repository.model.nosql.
     * NoSQLConnection)
     */
    @Override
    public boolean checkConnection(NoSQLConnection connection) throws NoSQLServerException {
        return MongoDBConnectionUtil.checkConnection(connection);
    }
}
