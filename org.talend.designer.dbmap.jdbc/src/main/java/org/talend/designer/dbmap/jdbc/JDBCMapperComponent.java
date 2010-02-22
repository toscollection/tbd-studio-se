package org.talend.designer.dbmap.jdbc;

import org.talend.designer.dbmap.DbMapComponent;
import org.talend.designer.dbmap.jdbc.language.JDBCGenerationManager;
import org.talend.designer.dbmap.language.generation.DbGenerationManager;

public class JDBCMapperComponent extends DbMapComponent {
	
	private DbGenerationManager generationManager = new JDBCGenerationManager();

	public JDBCMapperComponent() {
		super();
		// TODO Auto-generated constructor stub
	}
	
    public DbGenerationManager getGenerationManager() {
        return this.generationManager;
    }
}
