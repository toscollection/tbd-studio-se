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
package org.talend.designer.dbmap.external.connection;

import org.talend.core.model.components.IODataComponent;
import org.talend.core.model.metadata.IMetadataTable;
import org.talend.core.model.process.EConnectionType;
import org.talend.core.model.process.IConnection;

/**
 * DOC amaumont class global comment. Detailled comment <br/>
 * 
 * $Id: IOConnection.java 968 2006-12-12 10:59:26Z amaumont $
 * 
 */
public class IOConnection {

    private String name;

    private IMetadataTable table;

    private EConnectionType connectionType;

    /**
     * DOC amaumont MapperConnection constructor comment.
     * 
     * @param connection
     * @param ioDataComponent
     */
    public IOConnection(IConnection connection) {
        super();
        this.name = connection.getName();
        this.table = connection.getMetadataTable();
        this.connectionType = connection.getLineStyle();

    }

    /**
     * DOC amaumont MapperConnection constructor comment.
     * 
     * @param connection
     * @param ioDataComponent
     */
    public IOConnection(IODataComponent ioDataComponent) {
        super();
        this.name = ioDataComponent.getName();
        this.table = ioDataComponent.getTable();
        this.connectionType = ioDataComponent.getConnectionType();
    }

    public String getName() {
        return name;
    }

    public EConnectionType getConnectionType() {
        return connectionType;
    }

    public IMetadataTable getTable() {
        return table;
    }

}
