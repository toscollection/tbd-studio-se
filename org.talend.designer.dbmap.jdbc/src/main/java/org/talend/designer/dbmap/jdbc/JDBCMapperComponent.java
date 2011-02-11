// ============================================================================
//
// Copyright (C) 2006-2011 Talend Inc. - www.talend.com
//
// This source code is available under agreement available at
// %InstallDIR%\features\org.talend.rcp.branding.%PRODUCTNAME%\%PRODUCTNAME%license.txt
//
// You should have received a copy of the agreement
// along with this program; if not, write to Talend SA
// 9 rue Pages 92150 Suresnes, France
//
// ============================================================================
package org.talend.designer.dbmap.jdbc;

import org.talend.designer.dbmap.DbMapComponent;
import org.talend.designer.dbmap.jdbc.language.JDBCGenerationManager;
import org.talend.designer.dbmap.language.generation.DbGenerationManager;

public class JDBCMapperComponent extends DbMapComponent {

    private DbGenerationManager generationManager = new JDBCGenerationManager();

    public JDBCMapperComponent() {
        super();
    }

    public DbGenerationManager getGenerationManager() {
        return this.generationManager;
    }
}
