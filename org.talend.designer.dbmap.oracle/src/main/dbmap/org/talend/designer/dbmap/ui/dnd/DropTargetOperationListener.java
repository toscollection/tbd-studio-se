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

import org.eclipse.swt.dnd.DND;
import org.eclipse.swt.dnd.DropTarget;
import org.eclipse.swt.dnd.DropTargetListener;
import org.eclipse.swt.dnd.Transfer;
import org.eclipse.swt.widgets.Control;
import org.talend.designer.dbmap.managers.MapperManager;

/**
 * DOC amaumont class global comment. Detailled comment <br/>
 * 
 * $Id: DropTargetOperationListener.java 898 2006-12-07 11:06:17Z amaumont $
 * 
 */
public class DropTargetOperationListener {

    int authorizedOperations = DND.DROP_DEFAULT | DND.DROP_COPY | DND.DROP_MOVE | DND.DROP_LINK;

    Transfer[] authorizedTransfers = new Transfer[] { TableEntriesTransfer.getInstance() };

    private DropTargetListener dropTargetListener;

    public DropTargetOperationListener(final MapperManager mapperManager) {
        super();
        dropTargetListener = new DefaultDropTargetListener(mapperManager);
    }

    /**
     * DOC amaumont Comment method "addControl".
     * 
     * @param outputTablesZoneView
     */
    public void addControl(Control control) {
        DropTarget dropTarget = new DropTarget(control, authorizedOperations);
        dropTarget.setTransfer(authorizedTransfers);
        dropTarget.addDropListener(dropTargetListener);
    }

}
