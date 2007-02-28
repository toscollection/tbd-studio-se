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
package org.talend.designer.dbmap.ui.dnd;

import org.eclipse.swt.dnd.ByteArrayTransfer;
import org.eclipse.swt.dnd.TransferData;

/**
 * DOC amaumont class global comment. Detailled comment <br/>
 * 
 * $Id: TableEntriesTransfer.java 1782 2007-02-03 07:57:38Z bqian $
 * 
 */
public class TableEntriesTransfer extends ByteArrayTransfer {

    private DraggedData draggedData;

    private static final String MAPPER_TABLE_ENTRIES_TYPE_NAME = "MAPPER_TABLE_ENTRIES"; //$NON-NLS-1$

    private static final int MAPPER_TABLE_ENTRIES_ID = registerType(MAPPER_TABLE_ENTRIES_TYPE_NAME);

    private static final TableEntriesTransfer INSTANCE = new TableEntriesTransfer();

    public static TableEntriesTransfer getInstance() {
        return INSTANCE;
    }

    /*
     * (non-Javadoc)
     * 
     * @see org.eclipse.swt.dnd.Transfer#getTypeIds()
     */
    @Override
    protected int[] getTypeIds() {
        return new int[] { MAPPER_TABLE_ENTRIES_ID };
    }

    /*
     * (non-Javadoc)
     * 
     * @see org.eclipse.swt.dnd.Transfer#getTypeNames()
     */
    @Override
    protected String[] getTypeNames() {
        return new String[] { MAPPER_TABLE_ENTRIES_TYPE_NAME };
    }

    @Override
    protected void javaToNative(Object object, TransferData transferData) {
    }

    @Override
    protected Object nativeToJava(TransferData transferData) {
        return new byte[0];
    }

    protected DraggedData getDraggedData() {
        return draggedData;
    }

    protected void setDraggedData(DraggedData draggedData) {
        this.draggedData = draggedData;
    }

}
