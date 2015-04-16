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
package org.talend.repository.nosql.service;

import org.talend.core.model.repository.ERepositoryObjectType;
import org.talend.core.service.INOSQLService;
import org.talend.repository.nosql.ui.node.NoSQLRepositoryNodeType;

/**
 * created by hwang on Apr 15, 2015 Detailled comment
 *
 */
public class NOSQLService implements INOSQLService {

    /*
     * (non-Javadoc)
     * 
     * @see org.talend.core.service.INOSQLService#getNOSQLRepositoryType()
     */
    @Override
    public ERepositoryObjectType getNOSQLRepositoryType() {
        return NoSQLRepositoryNodeType.METADATA_NOSQL_CONNECTIONS;
    }

}
