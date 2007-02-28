// ============================================================================
//
// Talend Community Edition
//
// Copyright (C) 2006 Talend - www.talend.com
//
// This library is free software; you can redistribute it and/or
// modify it under the terms of the GNU Lesser General Public
// License as published by the Free Software Foundation; either
// version 2.1 of the License, or (at your option) any later version.
//
// This library is distributed in the hope that it will be useful,
// but WITHOUT ANY WARRANTY; without even the implied warranty of
// MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU
// Lesser General Public License for more details.
//
// You should have received a copy of the GNU General Public License
// along with this program; if not, write to the Free Software
// Foundation, Inc., 675 Mass Ave, Cambridge, MA 02139, USA.
//
// ============================================================================
package org.talend.designer.dbmap.language.generation;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.talend.commons.utils.StringUtils;
import org.talend.commons.utils.data.text.StringHelper;
import org.talend.designer.dbmap.AbstractDbMapComponent;
import org.talend.designer.dbmap.external.data.ExternalDbMapEntry;
import org.talend.designer.dbmap.external.data.ExternalDbMapTable;
import org.talend.designer.dbmap.language.IDbLanguage;
import org.talend.designer.dbmap.model.tableentry.TableEntryLocation;

/**
 * DOC amaumont class global comment. Detailled comment <br/>
 * 
 * $Id$
 * 
 */
public abstract class DbGenerationManager {

    private Map<String, ExternalDbMapTable> nameToInputTable;

    private HashMap<String, ExternalDbMapTable> nameToVarsTable;

    protected IDbLanguage language;

    /**
     * DOC amaumont GenerationManager constructor comment.
     * 
     * @param language2
     */
    public DbGenerationManager(IDbLanguage language) {
        super();
        this.language = language;
    }

    /**
     * DOC amaumont Comment method "setInputTables".
     * 
     * @param inputTables
     */
    public void setInputTables(List<ExternalDbMapTable> inputTables) {
        // this.inputTables = inputTables;
        nameToInputTable = new HashMap<String, ExternalDbMapTable>(inputTables.size());
        for (ExternalDbMapTable table : inputTables) {
            nameToInputTable.put(table.getName(), table);
        }
    }

    /**
     * DOC amaumont Comment method "setInputTables".
     * 
     * @param varsTables
     */
    public void setVarsTables(List<ExternalDbMapTable> varsTables) {
        // this.inputTables = varsTables;
        nameToVarsTable = new HashMap<String, ExternalDbMapTable>(varsTables.size());
        for (ExternalDbMapTable table : varsTables) {
            nameToVarsTable.put(table.getName(), table);
        }
    }

    public ExternalDbMapTable getInputTable(String tableName) {
        return nameToInputTable.get(tableName);
    }

    public ExternalDbMapTable getVarsTable(String tableName) {
        return nameToVarsTable.get(tableName);
    }

    public boolean isInputTable(String tableName) {
        return getInputTable(tableName) != null;
    }

    public boolean isVarsTable(String tableName) {
        return getVarsTable(tableName) != null;
    }

    public String getTableColumnVariable(String tableName, String columnName) {
        return StringHelper.replacePrms(this.language.getTemplateTableColumnVariable(), new Object[] { tableName, columnName });
    }

    public String getGeneratedCodeTableColumnVariable(String tableName, String columnName) {
        return StringHelper
                .replacePrms(this.language.getTemplateGeneratedCodeTableColumnVariable(), new Object[] { tableName, columnName });
    }

    public String getTableColumnVariable(TableEntryLocation location) {
        return this.language.getLocation(location.tableName, location.columnName);
    }

    public String getVarsColumnVariable(String columnName) {
        return StringHelper.replacePrms(this.language.getTemplateVarsColumnVariable(), new Object[] { columnName });
    }

    public String indent(final int i) {
        return StringUtils.repeat("  ", i); //$NON-NLS-1$
    }

    /**
     * DOC amaumont Comment method "ckeckConstraintsAreEmpty".
     * 
     * @param ExternalDbMapTable
     * @return
     */
    public boolean checkFiltersAreEmpty(ExternalDbMapTable outputTable) {
        List<ExternalDbMapEntry> constraints = outputTable.getCustomConditionsEntries();
        int lstSize = constraints.size();
        boolean oneConstraintIsNotEmpty = false;
        for (int i = 0; i < lstSize; i++) {

            String constraintExpression = ((ExternalDbMapEntry) constraints.get(i)).getExpression();
            if (constraintExpression != null && constraintExpression.trim().length() > 0) {
                oneConstraintIsNotEmpty = true;
                break;
            }
        }
        return !oneConstraintIsNotEmpty;
    }

    
    /**
     * Getter for language.
     * @return the language
     */
    public IDbLanguage getLanguage() {
        return this.language;
    }

    public abstract String buildSqlSelect(AbstractDbMapComponent component, String tableName);

    
    
}
