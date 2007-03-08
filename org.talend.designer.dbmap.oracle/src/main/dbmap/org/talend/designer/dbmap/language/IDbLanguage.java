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
package org.talend.designer.dbmap.language;

import org.talend.core.language.ICodeProblemsChecker;

/**
 * DOC amaumont class global comment. Detailled comment <br/>
 * 
 * $Id: ILanguage.java 1877 2007-02-06 17:16:43Z amaumont $
 * 
 */
public interface IDbLanguage {

    public String getLocationPattern();

    public String getPrefixField();

    public String getPrefixFieldRegexp();

    public String getPrefixTable();

    public String getPrefixTableRegexp();

    public String getSuffixField();

    public String getSuffixFieldRegexp();

    public String getSuffixTable();

    public String getSuffixTableRegexp();

    public String getSubstPatternForPrefixColumnName();

    public String getSubstPatternForReplaceLocation();

    public String getTemplateTableColumnVariable();

    public String getTemplateTableVariable();

    public String getTemplateVarsColumnVariable();

    public String getTemplateGeneratedCodeTableColumnVariable();

    /**
     * DOC amaumont Comment method "getAndCondition".
     * 
     * @return
     */
    public String getAndCondition();

    public String getLocation(String tableName, String columnName);

    public String getLocation(String tableName);


    public ICodeProblemsChecker getCodeChecker();

    public abstract IJoinType[] getAvailableJoins();
    public abstract IJoinType getJoin(String joinType);

    public abstract IDbOperatorManager getOperatorsManager();
    
}
