package org.talend.designer.dbmap.postgresql;

import org.talend.designer.dbmap.DbMapComponent;
import org.talend.designer.dbmap.language.generation.DbGenerationManager;
import org.talend.designer.dbmap.postgresql.language.PostgresqlGenerationManager;

public class PostgresqlMapperComponent extends DbMapComponent {
	
	private DbGenerationManager generationManager = new PostgresqlGenerationManager();

	public PostgresqlMapperComponent() {
		super();
		// TODO Auto-generated constructor stub
	}
	
    public DbGenerationManager getGenerationManager() {
        return this.generationManager;
    }
}
