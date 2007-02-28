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
package org.talend.designer.dbmap.model.table;

import org.talend.core.model.metadata.IMetadataColumn;
import org.talend.core.model.metadata.IMetadataTable;
import org.talend.core.model.process.EConnectionType;
import org.talend.designer.dbmap.external.connection.IOConnection;
import org.talend.designer.dbmap.external.data.ExternalDbMapTable;
import org.talend.designer.dbmap.language.IJoinType;
import org.talend.designer.dbmap.managers.MapperManager;
import org.talend.designer.dbmap.model.tableentry.AbstractInOutTableEntry;
import org.talend.designer.dbmap.model.tableentry.InputColumnTableEntry;

/**
 * DOC amaumont class global comment. Detailled comment <br/>
 * 
 * $Id: InputTable.java 1601 2007-01-25 16:46:15Z amaumont $
 * 
 */
public class InputTable extends AbstractInOutTable {

    private IOConnection connection;

    /**
     */
    private IJoinType joinType;

    private String alias;

    private String tableName;
    
//    private boolean joinWithPrevious;

    /**
     * DOC amaumont InputTable constructor comment.
     * 
     * @param mapperManager
     * 
     * @param metadataTable
     * @param externalMapperTable can be null
     * @param mainConnection
     */
    public InputTable(MapperManager mapperManager, IOConnection connection, String name) {
        super(mapperManager, connection.getTable(), name);
        this.connection = connection;
    }

    /**
     * DOC amaumont InputTable constructor comment.
     * 
     * @param manager
     * @param metadataTable
     * @param alias2
     */
    public InputTable(MapperManager mapperManager, IMetadataTable metadataTable, String name) {
        super(mapperManager, metadataTable, name);
    }

    /*
     * (non-Javadoc)
     * 
     * @see org.talend.designer.dbmap.model.table.AbstractInOutTable#initFromExternalData(org.talend.designer.dbmap.external.data.ExternalMapperTable)
     */
    @Override
    public void initFromExternalData(ExternalDbMapTable externalMapperTable) {
        super.initFromExternalData(externalMapperTable);
        if (externalMapperTable != null) {
            joinType = mapperManager.getCurrentLanguage().getJoin(externalMapperTable.getJoinType());
            if (joinType == null) {
                joinType = mapperManager.getCurrentLanguage().getAvailableJoins()[0];
            }
            alias = externalMapperTable.getAlias();
            tableName = externalMapperTable.getTableName() != null ? externalMapperTable.getTableName() : connection.getName();
//            joinWithPrevious = externalMapperTable.isJoinWithPrevious();
        }
    }

    /*
     * (non-Javadoc)
     * 
     * @see org.talend.designer.dbmap.model.table.DataMapTable#getNewTableEntry(org.talend.core.model.metadata.IMetadataColumn)
     */
    @Override
    protected AbstractInOutTableEntry getNewTableEntry(IMetadataColumn column) {
        return new InputColumnTableEntry(this, column);
    }

    public boolean isMainConnection() {
        return false;
    }

    /**
     * Getter for joinType.
     * 
     * @return the joinType
     */
    public IJoinType getJoinType() {
        if (this.joinType == null) {
            this.joinType = mapperManager.getCurrentLanguage().getJoin(null);
        }
        return this.joinType;
    }

    /**
     * Sets the joinType.
     * 
     * @param joinType the joinType to set
     */
    public void setJoinType(IJoinType joinType) {
        this.joinType = joinType;
    }

    /*
     * (non-Javadoc)
     * 
     * @see org.talend.designer.dbmap.model.table.AbstractDataMapTable#getName()
     */
    @Override
    public String getName() {
        if (alias != null) {
            return alias;
        }
        if (tableName != null) {
            return tableName;
        }
        return super.getName();
    }

    /**
     * Getter for alias.
     * 
     * @return the alias
     */
    public String getAlias() {
        return this.alias;
    }

    /**
     * Sets the alias.
     * 
     * @param alias the alias to set
     */
    public void setAlias(String alias) {
        this.alias = alias;
    }

    /**
     * Getter for tableName.
     * 
     * @return the tableName
     */
    public String getTableName() {
        return this.tableName;
    }

    /**
     * Sets the tableName.
     * 
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

    
//    /**
//     * Sets the joinWithPrevious.
//     * @param joinWithPrevious the joinWithPrevious to set
//     */
//    public void setJoinWithPrevious(boolean joinWithPrevious) {
//        this.joinWithPrevious = joinWithPrevious;
//    }

    
    
    
}
