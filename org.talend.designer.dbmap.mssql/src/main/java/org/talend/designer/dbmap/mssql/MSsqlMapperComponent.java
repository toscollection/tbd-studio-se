package org.talend.designer.dbmap.mssql;

import org.talend.designer.dbmap.DbMapComponent;
import org.talend.designer.dbmap.language.generation.DbGenerationManager;
import org.talend.designer.dbmap.mssql.language.MSsqlGenerationManager;

public class MSsqlMapperComponent extends DbMapComponent {

	private DbGenerationManager generationManager = new MSsqlGenerationManager();

	public MSsqlMapperComponent() {
		super();
		// TODO Auto-generated constructor stub
	}
	
    public DbGenerationManager getGenerationManager() {
        return this.generationManager;
    }

}
