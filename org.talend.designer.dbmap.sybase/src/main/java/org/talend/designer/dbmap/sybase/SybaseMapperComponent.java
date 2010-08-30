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
package org.talend.designer.dbmap.sybase;

import org.talend.designer.dbmap.DbMapComponent;
import org.talend.designer.dbmap.language.generation.DbGenerationManager;
import org.talend.designer.dbmap.sybase.language.SybaseGenerationManager;

/**
 * DOC amaumont class global comment. Detailled comment <br/>
 * 
 * $Id: MapperComponent.java 1782 2007-02-03 07:57:38Z bqian $
 * 
 */
public class SybaseMapperComponent extends DbMapComponent {

    DbGenerationManager generationManager = new SybaseGenerationManager();

    /**
     * DOC amaumont OracleMapperComponent constructor comment.
     */
    public SybaseMapperComponent() {
        super();
    }

    /**
     * Getter for generationManager.
     * 
     * @return the generationManager
     */
    public DbGenerationManager getGenerationManager() {
        return this.generationManager;
    }

}
