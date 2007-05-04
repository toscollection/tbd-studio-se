// ============================================================================
//
// Talend Community Edition
//
// Copyright (C) 2006-2007 Talend - www.talend.com
//
// This library is free software; you can redistribute it and/or
// modify it under the terms of the GNU Lesser General Public
// License as published by the Free Software Foundation; either
// version 2.1 of the License.
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

package org.talend.designer.dbmap.mysql.language;

import org.talend.designer.dbmap.language.AbstractDbLanguage;
import org.talend.designer.dbmap.language.IDbOperatorManager;

/**
 * DOC amaumont class global comment. Detailled comment <br/>
 * 
 * $Id: JavaLanguage.java 1877 2007-02-06 17:16:43Z amaumont $
 * 
 */
public class MysqlLanguage extends AbstractDbLanguage {

    private static final String DOUBLE_ESCAPE = "\\"; //$NON-NLS-1$

    private static final String PREFIX_VARIABLE_NAME = ""; //$NON-NLS-1$

    private static final String AND_CONDITION = "AND"; //$NON-NLS-1$

    private static final String PREFIX_FIELD_NAME = "."; //$NON-NLS-1$

    private static final String SUFFIX_FIELD_NAME = ""; //$NON-NLS-1$

    private static final String PREFIX_TABLE_NAME = PREFIX_VARIABLE_NAME;

    private static final String PREFIX_TABLE_NAME_REGEXP = ""; //$NON-NLS-1$

    private static final String SUFFIX_TABLE_NAME = ""; //$NON-NLS-1$

    private static final String SUFFIX_TABLE_NAME_REGEXP = SUFFIX_TABLE_NAME;

    private static final String PREFIX_FIELD_NAME_REGEXP = DOUBLE_ESCAPE + PREFIX_FIELD_NAME;

    private static final String SUFFIX_FIELD_NAME_REGEXP = SUFFIX_FIELD_NAME;

    private static final String LOCATION_PATTERN = PREFIX_TABLE_NAME_REGEXP
            + "\\s*(\\w+)\\s*" + PREFIX_FIELD_NAME_REGEXP //$NON-NLS-1$
            + "\\s*(\\w+)\\s*" + SUFFIX_FIELD_NAME_REGEXP; //$NON-NLS-1$

    /**
     * {0} and {1} must be replaced respectively by the table name and the column name.
     */
    private static final String SUBST_PATTERN_FOR_PREFIX_COLUMN_NAME = PREFIX_TABLE_NAME_REGEXP + "\\s*({0})\\s*" //$NON-NLS-1$
            + PREFIX_FIELD_NAME_REGEXP + "\\s*({1})\\s*" + SUFFIX_FIELD_NAME_REGEXP; //$NON-NLS-1$

    /**
     * {0} and {1} must be replaced respectively by the table name and the column name.
     */
    private static final String SUBST_PATTERN_FOR_REPLACE_LOCATION = PREFIX_TABLE_NAME_REGEXP
            + "(\\s*){0}(\\s*)" //$NON-NLS-1$
            + SUFFIX_TABLE_NAME_REGEXP
            + "(\\s*)" + PREFIX_FIELD_NAME_REGEXP + "(\\s*){1}(\\s*)" + SUFFIX_FIELD_NAME_REGEXP; //$NON-NLS-1$ //$NON-NLS-2$

    /**
     * {0} and {1} must be replaced respectively by the table name and the column name.
     */
    private static final String TEMPLATE_TABLE_VARIABLE = PREFIX_TABLE_NAME + "{0}"; //$NON-NLS-1$

    /**
     * {0} and {1} must be replaced respectively by the table name and the column name.
     */
    private static final String TEMPLATE_TABLE_COLUMN_VARIABLE = TEMPLATE_TABLE_VARIABLE + PREFIX_FIELD_NAME
            + "{1}" + SUFFIX_FIELD_NAME; //$NON-NLS-1$

    /**
     * {0} and {1} must be replaced respectively by the table name and the column name.
     */
    private static final String TEMPLATE_GENERATED_CODE_TABLE_COLUMN_VARIABLE = PREFIX_TABLE_NAME
            + "{0}" + PREFIX_FIELD_NAME + "{1}" //$NON-NLS-1$ //$NON-NLS-2$
            + SUFFIX_FIELD_NAME;

    /**
     * {0} and {1} must be replaced respectively by the table name and the column name.
     */
    private static final String TEMPLATE_VARS_COLUMN_VARIABLE = PREFIX_VARIABLE_NAME + "{0}"; //$NON-NLS-1$

    private MysqlOperatorsManager operatorsManager;

    /**
     * DOC amaumont PerlLanguage constructor comment.
     */
    public MysqlLanguage() {
        super();
        this.operatorsManager = new MysqlOperatorsManager();
    }

    /*
     * (non-Javadoc)
     * 
     * @see org.talend.designer.dbmap.model.language.ILanguage#getCouplePattern()
     */
    public String getLocationPattern() {
        return LOCATION_PATTERN;
    }

    /*
     * (non-Javadoc)
     * 
     * @see org.talend.designer.dbmap.model.language.ILanguage#getPREFIX_FIELD_NAME_REGEXP()
     */
    public String getPrefixFieldRegexp() {
        return PREFIX_FIELD_NAME_REGEXP;
    }

    /*
     * (non-Javadoc)
     * 
     * @see org.talend.designer.dbmap.model.language.ILanguage#getPREFIX_TABLE_NAME_REGEXP()
     */
    public String getPrefixTableRegexp() {
        return PREFIX_TABLE_NAME_REGEXP;
    }

    /*
     * (non-Javadoc)
     * 
     * @see org.talend.designer.dbmap.model.language.ILanguage#getSUFFIX_FIELD_NAME_REGEXP()
     */
    public String getSuffixFieldRegexp() {
        return SUFFIX_FIELD_NAME_REGEXP;
    }

    /*
     * (non-Javadoc)
     * 
     * @see org.talend.designer.dbmap.model.language.ILanguage#getSUFFIX_TABLE_NAME_REGEXP()
     */
    public String getSuffixTableRegexp() {
        return SUFFIX_TABLE_NAME_REGEXP;
    }

    /*
     * (non-Javadoc)
     * 
     * @see org.talend.designer.dbmap.model.language.ILanguage#getSubstPatternForPrefixColumnName()
     */
    public String getSubstPatternForPrefixColumnName() {
        return SUBST_PATTERN_FOR_PREFIX_COLUMN_NAME;
    }

    /*
     * (non-Javadoc)
     * 
     * @see org.talend.designer.dbmap.model.language.ILanguage#getSubstPatternForReplaceLocation()
     */
    public String getSubstPatternForReplaceLocation() {
        return SUBST_PATTERN_FOR_REPLACE_LOCATION;
    }

    /*
     * (non-Javadoc)
     * 
     * @see org.talend.designer.dbmap.model.language.ILanguage#getTEMPLATE_TABLE_COLUMN_VARIABLE()
     */
    public String getTemplateTableColumnVariable() {
        return TEMPLATE_TABLE_COLUMN_VARIABLE;
    }

    /*
     * (non-Javadoc)
     * 
     * @see org.talend.designer.dbmap.language.ILanguage#getTEMPLATE_PROCESS_COLUMN_VARIABLE()
     */
    public String getTemplateVarsColumnVariable() {
        return TEMPLATE_VARS_COLUMN_VARIABLE;
    }

    /*
     * (non-Javadoc)
     * 
     * @see org.talend.designer.dbmap.language.ILanguage#getTEMPLATE_GENERATED_CODE_TABLE_COLUMN_VARIABLE()
     */
    public String getTemplateGeneratedCodeTableColumnVariable() {
        return TEMPLATE_GENERATED_CODE_TABLE_COLUMN_VARIABLE;
    }

    /*
     * (non-Javadoc)
     * 
     * @see org.talend.designer.dbmap.language.ILanguage#getAndCondition()
     */
    public String getAndCondition() {
        return AND_CONDITION;
    }

    /*
     * (non-Javadoc)
     * 
     * @see org.talend.designer.dbmap.language.ILanguage#getPrefixField()
     */
    public String getPrefixField() {
        return PREFIX_FIELD_NAME;
    }

    /*
     * (non-Javadoc)
     * 
     * @see org.talend.designer.dbmap.language.ILanguage#getPrefixTable()
     */
    public String getPrefixTable() {
        return PREFIX_TABLE_NAME;
    }

    /*
     * (non-Javadoc)
     * 
     * @see org.talend.designer.dbmap.language.ILanguage#getSuffixField()
     */
    public String getSuffixField() {
        // TODO Auto-generated method stub
        return SUFFIX_FIELD_NAME;
    }

    /*
     * (non-Javadoc)
     * 
     * @see org.talend.designer.dbmap.language.ILanguage#getSuffixTable()
     */
    public String getSuffixTable() {
        return SUFFIX_TABLE_NAME;
    }

    /*
     * (non-Javadoc)
     * 
     * @see org.talend.designer.dbmap.language.ILanguage#getTemplateTableVariable()
     */
    public String getTemplateTableVariable() {
        return TEMPLATE_TABLE_VARIABLE;
    }

    /*
     * (non-Javadoc)
     * 
     * @see org.talend.designer.dbmap.language.IDbLanguage#getOperatorsManager()
     */
    public IDbOperatorManager getOperatorsManager() {
        return operatorsManager;
    }

}
