// ============================================================================
//
// Copyright (C) 2006-2007 Talend Inc. - www.talend.com
//
// This source code is available under agreement available at
// %InstallDIR%\features\org.talend.rcp.branding.%PRODUCTNAME%\%PRODUCTNAME%license.txt
//
// You should have received a copy of the agreement
// along with this program; if not, write to Talend SA
// 9 rue Pages 92150 Suresnes, France
//
// ============================================================================
package org.talend.designer.dbmap.oracle.language;

import org.talend.designer.dbmap.language.IDbOperator;
import org.talend.designer.dbmap.language.IDbOperatorManager;

/**
 * DOC amaumont class global comment. Detailled comment <br/>
 * 
 * $Id$
 * 
 */
public class OracleOperatorsManager implements IDbOperatorManager {

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
        EXISTS("EXISTS", "(SELECT * FROM MyTable)", false),

        EQUAL_ANY("=", "( , )", false),
        DIFFERENT_ANY("<> ANY", "( , )", false),
        STRICTLY_INFERIOR_ANY("< ANY", "( , )", false),
        INFERIOR_OR_EQUAL_ANY("<= ANY", "( , )", false),
        SUPERIOR_ANY("> ANY", "( , )", false),
        SUPERIOR_OR_EQUAL_ANY(">= ANY", "( , )", false),

        EQUAL_SOME("= SOME", "( , )", false),
        DIFFERENT_SOME("<> SOME", "( , )", false),
        STRICTLY_INFERIOR_SOME("< SOME", "( , )", false),
        INFERIOR_OR_EQUAL_SOME("<= SOME", "( , )", false),
        SUPERIOR_SOME("> SOME", "( , )", false),
        SUPERIOR_OR_EQUAL_SOME(">= SOME", "( , )", false),

        EQUAL_ALL("= ALL", "( , )", false),
        DIFFERENT_ALL("<> ALL", "( , )", false),
        STRICTLY_INFERIOR_ALL("< ALL", "( , )", false),
        INFERIOR_OR_EQUAL_ALL("<= ALL", "( , )", false),
        SUPERIOR_ALL("> ALL", "( , )", false),
        SUPERIOR_OR_EQUAL_ALL(">= ALL", "( , )", false),

        ;

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
