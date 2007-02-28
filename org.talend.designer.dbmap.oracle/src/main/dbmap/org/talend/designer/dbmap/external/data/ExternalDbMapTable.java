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
import java.util.List;

/**
 * DOC amaumont class global comment. Detailled comment <br/>
 * 
 * $Id: ExternalMapperTable.java 898 2006-12-07 11:06:17Z amaumont $
 * 
 */
public class ExternalDbMapTable implements Serializable {

    /**
     * 
     */
    private static final long serialVersionUID = 8768004929161302382L;

    private List<ExternalDbMapEntry> customConditionsEntries;

    private List<ExternalDbMapEntry> metadataTableEntries;

    private String name;

    private boolean minimized;

    /**
     * Used only for outputs.
     */
    private boolean reject;

    /**
     * Used only for outputs.
     */
    private boolean rejectInnerJoin;

    /**
     * Used only for lookup inputs. Reject main row if this lookup row doesn't exist.
     */
    private String joinType;

    /**
     * Used only for inputs.
     */
    private String alias;
    
    /**
     * Used only for inputs.
     */
    private String tableName;
    
    /**
     * Used only for inputs.
     */
//    private boolean joinWithPrevious;
    
    public String getName() {
        return this.name;
    }

    public void setName(String medataTableName) {
        this.name = medataTableName;
    }

    public boolean isMinimized() {
        return this.minimized;
    }

    public void setMinimized(boolean minimized) {
        this.minimized = minimized;
    }

    public List<ExternalDbMapEntry> getMetadataTableEntries() {
        return this.metadataTableEntries;
    }

    public void setMetadataTableEntries(List<ExternalDbMapEntry> tableEntries) {
        this.metadataTableEntries = tableEntries;
    }

    public List<ExternalDbMapEntry> getCustomConditionsEntries() {
        return this.customConditionsEntries;
    }

    public void setCustomConditionsEntries(List<ExternalDbMapEntry> constraintTableEntries) {
        this.customConditionsEntries = constraintTableEntries;
    }

    /**
     * 
     * used only for outputs.
     * 
     * @return
     */
    public boolean isReject() {
        return this.reject;
    }

    /**
     * 
     * used only for outputs.
     * 
     * @param reject
     */
    public void setReject(boolean reject) {
        this.reject = reject;
    }

    /**
     * Getter for rejectInnerJoin.
     * 
     * @return the rejectInnerJoin
     */
    public boolean isRejectInnerJoin() {
        return this.rejectInnerJoin;
    }

    /**
     * Sets the rejectInnerJoin.
     * 
     * @param rejectInnerJoin the rejectInnerJoin to set
     */
    public void setRejectInnerJoin(boolean rejectInnerJoin) {
        this.rejectInnerJoin = rejectInnerJoin;
    }

    
    /**
     * Getter for joinType.
     * @return the joinType
     */
    public String getJoinType() {
        return this.joinType;
    }

    
    /**
     * Sets the joinType.
     * @param joinType the joinType to set
     */
    public void setJoinType(String joinType) {
        this.joinType = joinType;
    }

    
    /**
     * Getter for alias.
     * @return the alias
     */
    public String getAlias() {
        return this.alias;
    }

    
    /**
     * Sets the alias.
     * @param alias the alias to set
     */
    public void setAlias(String alias) {
        this.alias = alias;
    }

    
    /**
     * Getter for tableName.
     * @return the tableName
     */
    public String getTableName() {
        return this.tableName;
    }

    
    /**
     * Sets the tableName.
     * @param tableName the tableName to set
     */
    public void setTableName(String tableName) {
        this.tableName = tableName;
    }

    
//    /**
//     * Getter for joinWithPrevious.
//     * @return the joinWithPrevious
//     */
//    public boolean isJoinWithPrevious() {
//        return this.joinWithPrevious;
//    }
//
//    
//    /**
//     * Sets the joinWithPrevious.
//     * @param joinWithPrevious the joinWithPrevious to set
//     */
//    public void setJoinWithPrevious(boolean joinWithPrevious) {
//        this.joinWithPrevious = joinWithPrevious;
//    }

}
