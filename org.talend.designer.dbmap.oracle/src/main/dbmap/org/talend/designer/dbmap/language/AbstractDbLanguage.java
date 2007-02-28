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

import org.talend.commons.utils.data.text.StringHelper;
import org.talend.core.language.ICodeProblemsChecker;

/**
 * DOC amaumont class global comment. Detailled comment <br/>
 * 
 * $Id: AbstractLanguage.java 1877 2007-02-06 17:16:43Z amaumont $
 * 
 */
public abstract class AbstractDbLanguage implements IDbLanguage {

    public static final String CARRIAGE_RETURN = "\n"; //$NON-NLS-1$

    protected ICodeProblemsChecker codeChecker;

    /**
     * 
     * DOC amaumont OracleLanguage class global comment. Detailled comment <br/>
     * 
     * $Id$
     * 
     */
    public static enum JOIN implements IJoinType {
        NO_JOIN("NO JOIN"),
        INNER_JOIN("INNER JOIN"),
        LEFT_OUTER_JOIN("LEFT OUTER JOIN"),
        RIGHT_OUTER_JOIN("RIGHT OUTER JOIN"),
        FULL_OUTER_JOIN("FULL OUTER JOIN"), 
        CROSS_JOIN("CROSS JOIN");

        String label;

        JOIN(String label) {
            this.label = label;
        }

        /**
         * Getter for label.
         * 
         * @return the label
         */
        public String getLabel() {
            return this.label;
        }

        public static IJoinType getJoin(String joinType) {
            if (joinType == null) {
                return NO_JOIN;
            }
            return valueOf(joinType);
        }

    };

    /**
     * 
     */
    public AbstractDbLanguage() {
    }

    /*
     * (non-Javadoc)
     * 
     * @see org.talend.designer.dbmap.language.IDbLanguage#getLocation(org.talend.designer.dbmap.model.tableentry.TableEntryLocation)
     */
    public String getLocation(String tableName, String columnName) {
        return StringHelper.replacePrms(getTemplateTableColumnVariable(), new Object[] { tableName, columnName });
    }

    /*
     * (non-Javadoc)
     * 
     * @see org.talend.designer.dbmap.language.IDbLanguage#getLocation(java.lang.String)
     */
    public String getLocation(String tableName) {
        return StringHelper.replacePrms(getTemplateTableVariable(), new Object[] { tableName });
    }

    /*
     * (non-Javadoc)
     * 
     * @see org.talend.designer.dbmap.language.IDbLanguage#getCodeChecker()
     */
    public ICodeProblemsChecker getCodeChecker() {
        return codeChecker;
    }

    /*
     * (non-Javadoc)
     * 
     * @see org.talend.designer.dbmap.language.IDbLanguage#getAvailableJoins()
     */
    public IJoinType[] getAvailableJoins() {
        return JOIN.values();
    }

    /*
     * (non-Javadoc)
     * 
     * @see org.talend.designer.dbmap.language.IDbLanguage#getJoin(java.lang.String)
     */
    public IJoinType getJoin(String joinType) {
        return JOIN.getJoin(joinType);
    }

}
