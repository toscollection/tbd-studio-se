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
package org.talend.designer.dbmap.postgresql;

import org.talend.designer.dbmap.DbMapComponent;
import org.talend.designer.dbmap.language.generation.DbGenerationManager;
import org.talend.designer.dbmap.postgresql.language.PostgresqlGenerationManager;

public class PostgresqlMapperComponent extends DbMapComponent {

    private DbGenerationManager generationManager = new PostgresqlGenerationManager();

    public PostgresqlMapperComponent() {
        super();
    }

    public DbGenerationManager getGenerationManager() {
        return this.generationManager;
    }
}
