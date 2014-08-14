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
package org.talend.repository.nosql.db.ui.cassandra;

import org.eclipse.jface.wizard.WizardPage;
import org.eclipse.swt.widgets.Composite;
import org.talend.core.model.metadata.builder.connection.MetadataTable;
import org.talend.core.model.properties.ConnectionItem;
import org.talend.repository.nosql.db.common.cassandra.ICassandraConstants;
import org.talend.repository.nosql.db.util.cassandra.CassandraConnectionUtil;
import org.talend.repository.nosql.exceptions.NoSQLGeneralException;
import org.talend.repository.nosql.exceptions.NoSQLServerException;
import org.talend.repository.nosql.ui.common.AbstractNoSQLSchemaForm;

public class CassandraSchemaForm extends AbstractNoSQLSchemaForm {

    private final String DBM_ID = "cassandra_id"; //$NON-NLS-1$

    public CassandraSchemaForm(Composite parent, ConnectionItem connectionItem, MetadataTable metadataTable, boolean creation,
            WizardPage parentWizardPage) {
        super(parent, connectionItem, metadataTable, creation, parentWizardPage);
        setShowDbTypeColumn(true);
        setDbmID(DBM_ID);
        setupForm();
    }

    @Override
    public void releaseResources() throws NoSQLGeneralException {
        super.releaseResources();
        try {
            CassandraConnectionUtil.closeConnections();
        } catch (NoSQLServerException e) {
            throw new NoSQLGeneralException(e);
        }
    }

    @Override
    protected String getTableDisplayName() {
        return ICassandraConstants.COLUMN_FAMILY;
    }

}
