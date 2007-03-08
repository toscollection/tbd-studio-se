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
package org.talend.designer.dbmap.model.tableentry;

import org.talend.core.model.metadata.IMetadataColumn;
import org.talend.designer.dbmap.model.table.AbstractDataMapTable;

/**
 * DOC amaumont class global comment. Detailled comment <br/>
 * 
 * $Id: InputColumnTableEntry.java 898 2006-12-07 11:06:17Z amaumont $
 * 
 */
public class InputColumnTableEntry extends AbstractInOutTableEntry {

    private String operator;
    
    private boolean join;
    
    private String originalExpression;
    
    public InputColumnTableEntry(AbstractDataMapTable abstractDataMapTable, IMetadataColumn metadataColumn, String expression) {
        super(abstractDataMapTable, metadataColumn, expression);
    }

    public InputColumnTableEntry(AbstractDataMapTable abstractDataMapTable, IMetadataColumn metadataColumn) {
        super(abstractDataMapTable, metadataColumn);
    }

    
    /**
     * Getter for operator.
     * @return the operator
     */
    public String getOperator() {
        return this.operator;
    }
    
    /**
     * Sets the operator.
     * @param operator the operator to set
     */
    public void setOperator(String operator) {
        this.operator = operator;
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

    
    /**
     * Getter for originalExpression.
     * @return the originalExpression
     */
    public String getOriginalExpression() {
        return this.originalExpression;
    }

    
    /**
     * Sets the originalExpression.
     * Used to save the original expression while operator is not accepted, so expression will be restored if operator has not changed.
     * @param expressionBackup the originalExpression to set
     */
    public void setOriginalExpression(String originalExpression) {
        this.originalExpression = originalExpression;
    }

    
    
    
}
