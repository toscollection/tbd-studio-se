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

import org.talend.designer.dbmap.language.IDbOperator;
import org.talend.designer.dbmap.language.IDbOperatorManager;

/**
 * DOC amaumont class global comment. Detailled comment <br/>
 * 
 * $Id$
 * 
 */
public class MysqlOperatorsManager implements IDbOperatorManager {

    /**
     * 
     * DOC amaumont OracleOperatorsManager class global comment. Detailled comment <br/>
     * 
     * $Id$
     * 
     */
    public enum DB_OPERATOR implements IDbOperator {
        EQUAL("="),
        DIFFERENT("<>"),
        STRICTLY_INFERIOR("<"),
        INFERIOR_OR_EQUAL("<="),
        SUPERIOR(">"),
        SUPERIOR_OR_EQUAL(">="),

        IS_NULL("IS NULL", null, true),
        IS_NOT_NULL("IS NOT NULL", null, true),
        BETWEEN("BETWEEN", " AND ", false),
        NOT_BETWEEN("NOT BETWEEN", " AND ", false),
        IN("IN", "( , )", false),
        NOT_IN("NOT IN", "( , )", false),
        LIKE("LIKE", "'%'", false),
        NOT_LIKE("NOT LIKE", "'%'", false),
        EXISTS("EXISTS", "(SELECT * FROM MyTable)", false), ;

        private String operator;

        private String associatedExpression;

        private boolean monoOperand;

        /**
         * DOC amaumont DbOperator constructor comment.
         * 
         * @param operator
         * @param expressionPattern
         * @param monoOperand
         */
        private DB_OPERATOR(String operator, String expressionPattern, boolean monoOperand) {
            this.operator = operator;
            this.associatedExpression = expressionPattern;
            this.monoOperand = monoOperand;
        }

        /**
         * DOC amaumont DbOperator constructor comment.
         * 
         * @param operator
         * @param associatedExpression
         * @param monoOperand
         */
        private DB_OPERATOR(String operator) {
            this.operator = operator;
        }

        /*
         * (non-Javadoc)
         * 
         * @see org.talend.designer.dbmap.language.IDbOperator#getValue()
         */
        public String getOperator() {
            return operator;
        }

        /*
         * (non-Javadoc)
         * 
         * @see org.talend.designer.dbmap.language.IDbOperator#getAssociatedExpression()
         */
        public String getAssociatedExpression() {
            return associatedExpression;
        }

        /*
         * (non-Javadoc)
         * 
         * @see org.talend.designer.dbmap.language.IDbOperator#isMonoOperand()
         */
        public boolean isMonoOperand() {
            return monoOperand;
        }
    };

    /*
     * (non-Javadoc)
     * 
     * @see org.talend.designer.dbmap.language.IDbOperatorManager#getOperators()
     */
    public IDbOperator[] getOperators() {
        return DB_OPERATOR.values();
    }

    /*
     * (non-Javadoc)
     * 
     * @see org.talend.designer.dbmap.language.IDbOperatorManager#getOperatorFromValue(java.lang.String)
     */
    public IDbOperator getOperatorFromValue(String operatorValue) {
        IDbOperator[] operators = getOperators();
        for (int i = 0; i < operators.length; i++) {
            if (operators[i].getOperator().equals(operatorValue)) {
                return operators[i];
            }
        }
        return null;
    }

    /* (non-Javadoc)
     * @see org.talend.designer.dbmap.language.IDbOperatorManager#getDefaultEqualOperator()
     */
    public IDbOperator getDefaultEqualOperator() {
        return DB_OPERATOR.EQUAL;
    }

}
