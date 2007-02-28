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
import org.talend.designer.dbmap.managers.TableEntriesManager;
import org.talend.designer.dbmap.model.tableentry.VarTableEntry;

/**
 * DOC amaumont class global comment. Detailled comment <br/>
 * 
 * $Id$
 * 
 */
public class AddVarEntryCommand extends Command {

    private VarTableEntry varTableEntry;

    private TableEntriesManager tableEntriesManager;

    private Integer index;

    /**
     * DOC amaumont AddTableEntryCommand constructor comment.
     * 
     * @param tableEntriesManager
     */
    public AddVarEntryCommand(TableEntriesManager tableEntriesManager, VarTableEntry tableEntry, Integer index) {
        super();
        this.varTableEntry = tableEntry;
        this.tableEntriesManager = tableEntriesManager;
        this.index = index;
    }

    /**
     * DOC amaumont AddTableEntryCommand constructor comment.
     * 
     * @param label
     */
    public AddVarEntryCommand(String label) {
        super(label);
    }

    /*
     * (non-Javadoc)
     * 
     * @see org.eclipse.gef.commands.Command#execute()
     */
    @Override
    public void execute() {

        tableEntriesManager.addTableEntry(varTableEntry, index);

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
     * @see org.eclipse.gef.commands.Command#redo()
     */
    @Override
    public void redo() {
        execute();
    }

    /*
     * (non-Javadoc)
     * 
     * @see org.eclipse.gef.commands.Command#undo()
     */
    @Override
    public void undo() {
        tableEntriesManager.remove(varTableEntry);
    }

}
