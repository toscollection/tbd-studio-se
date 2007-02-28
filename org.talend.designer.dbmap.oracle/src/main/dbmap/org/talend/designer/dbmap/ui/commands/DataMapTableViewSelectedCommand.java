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
package org.talend.designer.dbmap.ui.commands;

import org.eclipse.gef.commands.Command;
import org.talend.designer.dbmap.managers.UIManager;
import org.talend.designer.dbmap.ui.visualmap.table.DataMapTableView;

/**
 * DOC amaumont class global comment. Detailled comment <br/>
 * 
 * $Id$
 * 
 */
public class DataMapTableViewSelectedCommand extends Command {

    UIManager uiManager;

    DataMapTableView previousSelectedTableView;

    DataMapTableView newSelectedDataMapTableView;

    /**
     * DOC amaumont DataMapTableViewSelectedCommand constructor comment.
     * 
     * @param manager
     * @param previousSelectedTableView
     * @param dataMapTableView
     */
    public DataMapTableViewSelectedCommand(UIManager uiManager, DataMapTableView previousSelectedTableView,
            DataMapTableView newSelectedDataMapTableView) {
        this.uiManager = uiManager;
        this.previousSelectedTableView = previousSelectedTableView;
        this.newSelectedDataMapTableView = newSelectedDataMapTableView;
    }

    /*
     * (non-Javadoc)
     * 
     * @see org.eclipse.gef.commands.Command#canExecute()
     */
    @Override
    public boolean canExecute() {
        return true;
    }

    /*
     * (non-Javadoc)
     * 
     * @see org.eclipse.gef.commands.Command#canUndo()
     */
    @Override
    public boolean canUndo() {
        return true;
    }

    /*
     * (non-Javadoc)
     * 
     * @see org.eclipse.gef.commands.Command#execute()
     */
    @Override
    public void execute() {
        // nothing
    }

    /*
     * (non-Javadoc)
     * 
     * @see org.eclipse.gef.commands.Command#redo()
     */
    @Override
    public void redo() {
        uiManager.selectDataMapTableView(newSelectedDataMapTableView, false, false);
    }

    /*
     * (non-Javadoc)
     * 
     * @see org.eclipse.gef.commands.Command#undo()
     */
    @Override
    public void undo() {
        if (previousSelectedTableView != null) {
            uiManager.selectDataMapTableView(previousSelectedTableView, false, false);
        }
    }

}
