// ============================================================================
//
// Copyright (C) 2006-2019 Talend Inc. - www.talend.com
//
// This source code is available under agreement available at
// %InstallDIR%\features\org.talend.rcp.branding.%PRODUCTNAME%\%PRODUCTNAME%license.txt
//
// You should have received a copy of the agreement
// along with this program; if not, write to Talend SA
// 9 rue Pages 92150 Suresnes, France
//
// ============================================================================
package org.talend.repository.nosql.db.common.mongodb;

import org.talend.repository.nosql.constants.INoSQLCommonAttributes;

/**
 * DOC PLV class global comment. Detailled comment
 */
public interface IMongoDBAttributes extends INoSQLCommonAttributes {

    public final static String MONGODB = "MONGODB"; //$NON-NLS-1$

    public final static String USE_REPLICA_SET = "USE_REPLICA_SET"; //$NON-NLS-1$

    public final static String REPLICA_SET = "REPLICA_SET"; //$NON-NLS-1$

    public final static String COLLECTION = "COLLECTION"; //$NON-NLS-1$

    public final static String REQUIRED_ENCRYPTION = "REQUIRED_ENCRYPTION"; //$NON-NLS-1$
    
    public static final String SET_AUTHENTICATION_DATABASE = "SET_AUTHENTICATION_DATABASE"; //$NON-NLS-1$
    
    public static final String AUTHENTICATION_DATABASE = "AUTHENTICATION_DATABASE"; //$NON-NLS-1$
    
    public static final String AUTHENTICATION_MECHANISM = "AUTHENTICATION_MECHANISM"; //$NON-NLS-1$
    
    public static final String KRB_USER_PRINCIPAL = "KRB_USER_PRINCIPAL"; //$NON-NLS-1$
    
    public static final String KRB_REALM = "KRB_REALM"; //$NON-NLS-1$
    
    public static final String KRB_KDC = "KRB_KDC"; //$NON-NLS-1$
    
}
