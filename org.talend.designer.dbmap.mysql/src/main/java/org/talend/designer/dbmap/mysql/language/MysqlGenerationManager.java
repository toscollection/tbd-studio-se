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
package org.talend.designer.dbmap.mysql.language;

import java.util.HashSet;
import java.util.Set;

import org.talend.designer.dbmap.DbMapComponent;
import org.talend.designer.dbmap.language.generation.DbGenerationManager;

/**
 * DOC amaumont class global comment. Detailled comment <br/>
 * 
 * $Id: GenerationManager.java 1299 2007-01-05 14:53:10Z amaumont $
 * 
 */
public class MysqlGenerationManager extends DbGenerationManager {

    private Set<String> aliasAlreadyDeclared = new HashSet<String>();

    public MysqlGenerationManager() {
        super(new MysqlLanguage());
    }

    // /**
    // * DOC amaumont Comment method "buildConditions".
    // *
    // * @param gm
    // *
    // * @param constraintTableEntries
    // * @param expressionParser
    // * @return
    // */
    // public String buildConditions(List<ExternalDbMapEntry> constraintTableEntries, DataMapExpressionParser
    // expressionParser) {
    // int lstSize = constraintTableEntries.size();
    // StringBuilder stringBuilder = new StringBuilder();
    // String and = null;
    // for (int i = 0; i < lstSize; i++) {
    //
    // String constraintExpression = ((ExternalDbMapEntry) constraintTableEntries.get(i)).getExpression();
    // if (constraintExpression == null) {
    // continue;
    // }
    // if (and != null && constraintExpression.trim().length() > 0) {
    // stringBuilder.append(and);
    // }
    //
    // if (lstSize > 1) {
    // stringBuilder.append(DbMapSqlConstants.SPACE);
    // stringBuilder.append(DbMapSqlConstants.LEFT_BRACKET);
    // stringBuilder.append(DbMapSqlConstants.SPACE);
    //
    // stringBuilder.append(constraintExpression);
    //
    // stringBuilder.append(DbMapSqlConstants.SPACE);
    // stringBuilder.append(DbMapSqlConstants.RIGHT_BRACKET);
    // stringBuilder.append(DbMapSqlConstants.SPACE);
    //
    // } else {
    // stringBuilder.append(constraintExpression);
    // }
    //
    // if (and == null) {
    // and = language.getAndCondition();
    // }
    // }
    // return stringBuilder.toString();
    // }

    @Override
    public String buildSqlSelect(DbMapComponent component, String outputTableName) {
        return super.buildSqlSelect(component, outputTableName);
    }

}
