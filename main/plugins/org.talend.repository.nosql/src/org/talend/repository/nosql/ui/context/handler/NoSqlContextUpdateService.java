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

import java.util.Map;

import org.talend.core.IRepositoryContextUpdateService;
import org.talend.core.model.metadata.builder.connection.Connection;
import org.talend.repository.model.nosql.NoSQLConnection;

/**
 * created by ldong on Mar 5, 2015 Detailled comment
 * 
 */
public class NoSqlContextUpdateService implements IRepositoryContextUpdateService {

    @Override
    public void updateRelatedContextVariableName(Connection conn, String oldName, String newName) {
        if (conn instanceof NoSQLConnection) {
            NoSQLConnection noSqlConn = (NoSQLConnection) conn;
            for (Map.Entry<String, String> attr : noSqlConn.getAttributes()) {
                if (attr.getValue().equals(oldName)) {
                    attr.setValue(newName);
                }
            }
        }
    }

}
