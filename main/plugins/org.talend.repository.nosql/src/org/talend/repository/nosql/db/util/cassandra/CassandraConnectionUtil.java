// ============================================================================
//
// Copyright (C) 2006-2016 Talend Inc. - www.talend.com
//
// This source code is available under agreement available at
// %InstallDIR%\features\org.talend.rcp.branding.%PRODUCTNAME%\%PRODUCTNAME%license.txt
//
// You should have received a copy of the agreement
// along with this program; if not, write to Talend SA
// 9 rue Pages 92150 Suresnes, France
//
// ============================================================================
package org.talend.repository.nosql.db.util.cassandra;

import org.talend.commons.exception.ExceptionHandler;
import org.talend.repository.model.nosql.NoSQLConnection;
import org.talend.repository.nosql.constants.INoSQLCommonAttributes;
import org.talend.repository.nosql.db.common.cassandra.ICassandraConstants;
import org.talend.repository.nosql.db.handler.cassandra.CassandraMetadataHandler;
import org.talend.repository.nosql.db.handler.cassandra.CassandraOldVersionMetadataHandler;
import org.talend.repository.nosql.db.handler.cassandra.ICassandraMetadataHandler;

/**
 * 
 * created by ycbai on 2014年11月26日 Detailled comment
 *
 */
public class CassandraConnectionUtil {

    public static ICassandraMetadataHandler getMetadataHandler(NoSQLConnection connection) {
        if (isOldVersion(connection)) {
            return CassandraOldVersionMetadataHandler.getInstance();
        }if(isUpgradeVersion(connection)){
               return CassandraMetadataHandler.getInstanceForUpgradeDataStax();
        } else {
            boolean isDatastaxApiType = ICassandraConstants.API_TYPE_DATASTAX.equals(connection.getAttributes().get(
                    INoSQLCommonAttributes.API_TYPE));
            if (isDatastaxApiType) {
                return CassandraMetadataHandler.getInstanceForDataStax();
            } else {
                return CassandraMetadataHandler.getInstance();
            }
        }
    }

    public static boolean isOldVersion(NoSQLConnection connection) {
        String dbVersion = connection.getAttributes().get(INoSQLCommonAttributes.DB_VERSION);
        return "CASSANDRA_1_1_2".equals(dbVersion); //$NON-NLS-1$
    }
    
    public static boolean isUpgradeVersion(NoSQLConnection connection) {
        String dbVersion = connection.getAttributes().get(INoSQLCommonAttributes.DB_VERSION);
        try{
            if(dbVersion.startsWith("CASSANDRA_")&&dbVersion.length()>=11){//$NON-NLS-1$
                String vf =dbVersion.substring(10,11);
                Integer vi = Integer.parseInt(vf);
                if(vi>2){
                    return true;
                }else if(vi<2){
                    return false;
                }else if(dbVersion.startsWith("CASSANDRA_2_")&&dbVersion.length()>=13){//$NON-NLS-1$
                    String vs = dbVersion.substring(12,13);
                    vi = Integer.parseInt(vs);
                    if(vi<2){
                        return false;
                    }else{
                        return true;
                    }
                }
            }
        } catch (Exception ex) {
            //do nothing
        }
        return false; 
    }

}
