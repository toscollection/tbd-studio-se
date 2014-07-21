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
package org.talend.repository.nosql.db.util.neo4j;

import java.util.Iterator;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.talend.core.model.utils.ContextParameterUtils;
import org.talend.designer.core.model.utils.emf.talendfile.ContextType;
import org.talend.repository.model.nosql.NoSQLConnection;
import org.talend.repository.nosql.db.common.neo4j.INeo4jAttributes;
import org.talend.repository.nosql.exceptions.NoSQLReflectionException;
import org.talend.repository.nosql.exceptions.NoSQLServerException;
import org.talend.repository.nosql.factory.NoSQLClassLoaderFactory;
import org.talend.repository.nosql.i18n.Messages;
import org.talend.repository.nosql.reflection.NoSQLReflection;
import org.talend.repository.ui.utils.ConnectionContextHelper;

/**
 * 
 * created by ycbai on Jul 22, 2014 Detailled comment
 * 
 */
public class Neo4jConnectionUtil {

    private static Object graphDb;

    private static Object queryEngine;

    private static Object executionEngine;

    public static synchronized boolean checkConnection(NoSQLConnection connection) throws NoSQLServerException {
        boolean canConnect = true;
        try {
            Object db = getDB(connection);
            NoSQLReflection.invokeMethod(db, "getAllNodes", //$NON-NLS-1$
                    new Object[0]);
        } catch (Exception e) {
            canConnect = false;
            resetAll();
            throw new NoSQLServerException(Messages.getString("Neo4jConnectionUtil.cannotConnectDatabase"), e); //$NON-NLS-1$
        }

        return canConnect;
    }

    public static synchronized Object getDB(NoSQLConnection connection) throws NoSQLServerException {
        Object db = graphDb;
        if (db != null) {
            return db;
        }

        ClassLoader classLoader = NoSQLClassLoaderFactory.getClassLoader(connection);
        try {
            String isRemoteAttr = connection.getAttributes().get(INeo4jAttributes.REMOTE_SERVER);
            boolean isRemote = isRemoteAttr == null ? false : Boolean.valueOf(isRemoteAttr);
            if (isRemote) {
                String serverUrl = StringUtils.trimToEmpty(connection.getAttributes().get(INeo4jAttributes.SERVER_URL));
                if (connection.isContextMode()) {
                    ContextType contextType = ConnectionContextHelper.getContextTypeForContextMode(connection);
                    serverUrl = ContextParameterUtils.getOriginalValue(contextType, serverUrl);
                }
                db = NoSQLReflection.newInstance("org.neo4j.rest.graphdb.RestGraphDatabase", new Object[] { serverUrl }, //$NON-NLS-1$
                        classLoader);
            } else {
                String dbPath = StringUtils.trimToEmpty(connection.getAttributes().get(INeo4jAttributes.DATABASE_PATH));
                if (connection.isContextMode()) {
                    ContextType contextType = ConnectionContextHelper.getContextTypeForContextMode(connection);
                    dbPath = ContextParameterUtils.getOriginalValue(contextType, dbPath);
                }
                Object dbFactory = NoSQLReflection.newInstance("org.neo4j.graphdb.factory.GraphDatabaseFactory", new Object[0], //$NON-NLS-1$
                        classLoader);
                db = NoSQLReflection.invokeMethod(dbFactory, "newEmbeddedDatabase", //$NON-NLS-1$
                        new Object[] { dbPath });
            }
            registerShutdownHook(db);
        } catch (NoSQLReflectionException e) {
            throw new NoSQLServerException(e);
        }

        return graphDb = db;
    }

    private static void registerShutdownHook(final Object db) {
        // Registers a shutdown hook for the Neo4j instance so that it
        // shuts down nicely when the VM exits (even if you "Ctrl-C" the
        // running application).
        Runtime.getRuntime().addShutdownHook(new Thread() {

            @Override
            public void run() {
                try {
                    NoSQLReflection.invokeMethod(db, "shutdown", new Object[0]); //$NON-NLS-1$
                } catch (NoSQLReflectionException e) {
                    // Nothing we can do here.
                    e.printStackTrace();
                }
            }
        });
    }

    public static synchronized Iterator<Map<String, Object>> getResultIterator(NoSQLConnection connection, String cypher)
            throws NoSQLServerException {
        Iterator<Map<String, Object>> resultIterator = null;
        Object db = getDB(connection);
        ClassLoader classLoader = NoSQLClassLoaderFactory.getClassLoader(connection);
        String isRemoteAttr = connection.getAttributes().get(INeo4jAttributes.REMOTE_SERVER);
        boolean isRemote = isRemoteAttr == null ? false : Boolean.valueOf(isRemoteAttr);
        try {
            if (isRemote) {
                Object queryResult = NoSQLReflection.invokeMethod(getQueryEngine(db, classLoader),
                        "query", new Object[] { cypher, null }, String.class, Map.class); //$NON-NLS-1$
                resultIterator = (Iterator<Map<String, Object>>) NoSQLReflection.invokeMethod(queryResult, "iterator", //$NON-NLS-1$
                        new Object[0]);
            } else {
                Object executionResult = NoSQLReflection.invokeMethod(getExecutionEngine(db, classLoader),
                        "execute", new Object[] { cypher }); //$NON-NLS-1$
                resultIterator = (Iterator<Map<String, Object>>) NoSQLReflection.invokeMethod(executionResult, "javaIterator", //$NON-NLS-1$
                        new Object[0]);
            }
        } catch (NoSQLReflectionException e) {
            throw new NoSQLServerException(e);
        }

        return resultIterator;
    }

    private static Object getQueryEngine(Object db, ClassLoader classLoader) throws NoSQLReflectionException {
        Object qe = queryEngine;
        if (qe != null) {
            return qe;
        }

        Object restAPI = NoSQLReflection.invokeMethod(db, "getRestAPI", new Object[0]); //$NON-NLS-1$
        try {
            qe = NoSQLReflection.newInstance("org.neo4j.rest.graphdb.query.RestCypherQueryEngine", //$NON-NLS-1$
                    new Object[] { restAPI }, classLoader, Class.forName("org.neo4j.rest.graphdb.RestAPI", true, classLoader)); //$NON-NLS-1$
        } catch (ClassNotFoundException e) {
            throw new NoSQLReflectionException(e);
        }

        return queryEngine = qe;
    }

    private static Object getExecutionEngine(Object db, ClassLoader classLoader) throws NoSQLReflectionException {
        Object ee = executionEngine;
        if (ee != null) {
            return ee;
        }

        try {
            ee = NoSQLReflection.newInstance("org.neo4j.cypher.ExecutionEngine", new Object[] { db }, //$NON-NLS-1$
                    classLoader, Class.forName("org.neo4j.graphdb.GraphDatabaseService", true, classLoader)); //$NON-NLS-1$
        } catch (ClassNotFoundException e) {
            throw new NoSQLReflectionException(e);
        }

        return executionEngine = ee;
    }

    public static synchronized void closeConnections() throws NoSQLServerException {
        if (graphDb != null) {
            try {
                NoSQLReflection.invokeMethod(graphDb, "shutdown", new Object[0]); //$NON-NLS-1$
            } catch (NoSQLReflectionException e) {
                throw new NoSQLServerException(e);
            }
        }
        resetAll();
    }

    public static void resetAll() {
        graphDb = null;
        queryEngine = null;
        executionEngine = null;
    }

}
