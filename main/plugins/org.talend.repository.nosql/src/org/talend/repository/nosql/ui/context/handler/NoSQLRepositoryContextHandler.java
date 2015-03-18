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
package org.talend.repository.nosql.ui.context.handler;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.talend.core.model.metadata.builder.connection.Connection;
import org.talend.core.model.metadata.types.JavaType;
import org.talend.core.model.metadata.types.JavaTypesManager;
import org.talend.core.model.process.IContextParameter;
import org.talend.core.model.properties.ContextItem;
import org.talend.core.model.utils.ContextParameterUtils;
import org.talend.core.ui.context.model.table.ConectionAdaptContextVariableModel;
import org.talend.designer.core.model.utils.emf.talendfile.ContextType;
import org.talend.metadata.managment.ui.model.IConnParamName;
import org.talend.metadata.managment.ui.utils.ConnectionContextHelper;
import org.talend.metadata.managment.ui.utils.ExtendedNodeConnectionContextUtils.EHadoopParamName;
import org.talend.metadata.managment.ui.wizard.context.AbstractRepositoryContextHandler;
import org.talend.repository.model.nosql.NoSQLConnection;
import org.talend.repository.nosql.constants.INoSQLCommonAttributes;
import org.talend.repository.nosql.db.common.neo4j.INeo4jAttributes;

/**
 * created by ldong on Dec 18, 2014 Detailled comment
 * 
 */
public class NoSQLRepositoryContextHandler extends AbstractRepositoryContextHandler {

    @Override
    public boolean isRepositoryConType(Connection connection) {
        return connection instanceof NoSQLConnection;
    }

    @Override
    public List<IContextParameter> createContextParameters(String prefixName, Connection connection, Set<IConnParamName> paramSet) {
        List<IContextParameter> varList = new ArrayList<IContextParameter>();
        if (connection instanceof NoSQLConnection) {
            NoSQLConnection conn = (NoSQLConnection) connection;
            JavaType javaType = null;
            if (conn == null || prefixName == null || paramSet == null || paramSet.isEmpty()) {
                return Collections.emptyList();
            }
            for (Map.Entry<String, String> attr : ((NoSQLConnection) connection).getAttributes()) {
                if (INoSQLCommonAttributes.PASSWORD.equals(attr)) {
                    javaType = JavaTypesManager.PASSWORD;
                }
            }

            String paramPrefix = prefixName + ConnectionContextHelper.LINE;
            String paramName = null;
            for (IConnParamName param : paramSet) {
                if (param instanceof EHadoopParamName) {
                    EHadoopParamName noSqlParam = (EHadoopParamName) param;
                    paramName = paramPrefix + noSqlParam;
                    switch (noSqlParam) {
                    case Server:
                        ConnectionContextHelper.createParameters(varList, paramName,
                                conn.getAttributes().get(INoSQLCommonAttributes.HOST), javaType);
                        break;
                    case Port:
                        ConnectionContextHelper.createParameters(varList, paramName,
                                conn.getAttributes().get(INoSQLCommonAttributes.PORT), javaType);
                        break;
                    case Keyspace:
                        ConnectionContextHelper.createParameters(varList, paramName,
                                conn.getAttributes().get(INoSQLCommonAttributes.DATABASE), javaType);
                        break;
                    case Database:
                        ConnectionContextHelper.createParameters(varList, paramName,
                                conn.getAttributes().get(INoSQLCommonAttributes.DATABASE), javaType);
                        break;
                    case Databasepath:
                        ConnectionContextHelper.createParameters(varList, paramName,
                                conn.getAttributes().get(INeo4jAttributes.DATABASE_PATH), javaType);
                        break;
                    case ServerUrl:
                        ConnectionContextHelper.createParameters(varList, paramName,
                                conn.getAttributes().get(INeo4jAttributes.SERVER_URL), javaType);
                        break;
                    case UserName:
                        ConnectionContextHelper.createParameters(varList, paramName,
                                conn.getAttributes().get(INoSQLCommonAttributes.USERNAME), javaType);
                        break;
                    case Password:
                        ConnectionContextHelper.createParameters(varList, paramName,
                                conn.getAttributes().get(INoSQLCommonAttributes.PASSWORD), JavaTypesManager.PASSWORD);
                        break;
                    default:
                    }
                }
            }

        }
        return varList;
    }

    /*
     * (non-Javadoc)
     * 
     * @see
     * org.talend.repository.ui.utils.IRepositoryContextHandler#setPropertiesForContextMode(org.talend.core.model.properties
     * .ContextItem, java.util.Map)
     */
    @Override
    public void setPropertiesForContextMode(String prefixName, Connection connection, Set<IConnParamName> paramSet) {
        if (connection == null) {
            return;
        }
        if (connection instanceof NoSQLConnection) {
            NoSQLConnection noSqlConn = (NoSQLConnection) connection;
            String originalVariableName = prefixName + ConnectionContextHelper.LINE;
            String noSqlVariableName = null;
            for (IConnParamName param : paramSet) {
                if (param instanceof EHadoopParamName) {
                    EHadoopParamName noSqlParam = (EHadoopParamName) param;
                    originalVariableName = prefixName + ConnectionContextHelper.LINE;
                    noSqlVariableName = originalVariableName + noSqlParam;
                    matchContextForAttribues(noSqlConn, noSqlParam, noSqlVariableName);
                }
            }
        }
    }

    @Override
    public void setPropertiesForExistContextMode(Connection connection, Set<IConnParamName> paramSet,
            Map<ContextItem, List<ConectionAdaptContextVariableModel>> adaptMap) {
        if (connection == null) {
            return;
        }
        if (connection instanceof NoSQLConnection) {
            NoSQLConnection noSqlConn = (NoSQLConnection) connection;
            ContextItem currentContext = null;
            for (IConnParamName param : paramSet) {
                if (param instanceof EHadoopParamName) {
                    String noSqlVariableName = null;
                    EHadoopParamName noSqlParam = (EHadoopParamName) param;
                    if (adaptMap != null && adaptMap.size() > 0) {
                        for (Map.Entry<ContextItem, List<ConectionAdaptContextVariableModel>> entry : adaptMap.entrySet()) {
                            currentContext = entry.getKey();
                            List<ConectionAdaptContextVariableModel> modelList = entry.getValue();
                            for (ConectionAdaptContextVariableModel model : modelList) {
                                if (model.getValue().equals(noSqlParam.name())) {
                                    noSqlVariableName = model.getName();
                                    break;
                                }
                            }
                        }
                    }
                    if (noSqlVariableName != null) {
                        noSqlVariableName = getCorrectVariableName(currentContext, noSqlVariableName, noSqlParam);
                        matchContextForAttribues(noSqlConn, noSqlParam, noSqlVariableName);
                    }
                }

            }
        }
    }

    @Override
    protected void matchContextForAttribues(Connection conn, IConnParamName param, String noSqlVariableName) {
        NoSQLConnection noSqlConn = (NoSQLConnection) conn;
        EHadoopParamName noSqlParam = (EHadoopParamName) param;
        switch (noSqlParam) {
        case Server:
            noSqlConn.getAttributes().put(INoSQLCommonAttributes.HOST,
                    ContextParameterUtils.getNewScriptCode(noSqlVariableName, LANGUAGE));
            break;
        case Port:
            noSqlConn.getAttributes().put(INoSQLCommonAttributes.PORT,
                    ContextParameterUtils.getNewScriptCode(noSqlVariableName, LANGUAGE));
            break;
        case Database:
            noSqlConn.getAttributes().put(INoSQLCommonAttributes.DATABASE,
                    ContextParameterUtils.getNewScriptCode(noSqlVariableName, LANGUAGE));
            break;
        case Keyspace:
            noSqlConn.getAttributes().put(INoSQLCommonAttributes.DATABASE,
                    ContextParameterUtils.getNewScriptCode(noSqlVariableName, LANGUAGE));
            break;
        case Databasepath:
            noSqlConn.getAttributes().put(INeo4jAttributes.DATABASE_PATH,
                    ContextParameterUtils.getNewScriptCode(noSqlVariableName, LANGUAGE));
            break;
        case ServerUrl:
            noSqlConn.getAttributes().put(INeo4jAttributes.SERVER_URL,
                    ContextParameterUtils.getNewScriptCode(noSqlVariableName, LANGUAGE));
            break;
        case UserName:
            noSqlConn.getAttributes().put(INoSQLCommonAttributes.USERNAME,
                    ContextParameterUtils.getNewScriptCode(noSqlVariableName, LANGUAGE));
            break;
        case Password:
            noSqlConn.getAttributes().put(INoSQLCommonAttributes.PASSWORD,
                    ContextParameterUtils.getNewScriptCode(noSqlVariableName, LANGUAGE));
            break;
        default:
        }

    }

    @Override
    public void revertPropertiesForContextMode(Connection connection, ContextType contextType) {

    }

    @Override
    public Set<String> getConAdditionPropertiesForContextMode(Connection conn) {
        return Collections.EMPTY_SET;
    }

    @Override
    protected void matchAdditionProperties(Connection conn, Map<ContextItem, List<ConectionAdaptContextVariableModel>> adaptMap) {
        // do nothing since nosql has no additional properties
    }

}
