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
package org.talend.designer.dbmap.external.data;

import java.io.Serializable;

/**
 * DOC amaumont class global comment. Detailled comment <br/>
 * 
 * $Id: ExternalMapperTableEntry.java 2010 2007-02-12 13:18:38Z amaumont $
 * 
 */
public class ExternalDbMapEntry implements Serializable {

    /**
     * 
     */
    private static final long serialVersionUID = -6108773358595740424L;

    private String name;

    private String expression;

    private String operator;

    private boolean join;
    
    public ExternalDbMapEntry() {
        super();
    }

    public ExternalDbMapEntry(String expression) {
        super();
        this.expression = expression;
    }

    public ExternalDbMapEntry(String name, String expression) {
        super();
        this.name = name;
        this.expression = expression;
    }
    
    /**
     * DOC amaumont ExternalDbMapEntry constructor comment.
     * @param name
     * @param expression
     * @param operator
     */
    public ExternalDbMapEntry(String name, String expression, String operator) {
        super();
        this.name = name;
        this.expression = expression;
        this.operator = operator;
    }

    public String getExpression() {
        return this.expression;
    }

    public void setExpression(String expression) {
        this.expression = expression;
    }

    public String getName() {
        return this.name;
    }

    public void setName(String metadataName) {
        this.name = metadataName;
    }

    /**
     * Talend types, used for var entries only.
     * 
     * @return
     */
    public String getOperator() {
        return operator;
    }

    /**
     * Talend types, used for var entries only.
     * 
     * @param type the type to set
     */
    public void setOperator(String type) {
        this.operator = type;
    }

    
    /**
     * Getter for join.
     * @return the join
     */
    public boolean isJoin() {
        return this.join;
    }

    
    /**
     * Sets the join.
     * @param join the join to set
     */
    public void setJoin(boolean join) {
        this.join = join;
    }
    
    
    
}
