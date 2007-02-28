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

import java.util.List;

import org.talend.core.model.process.Problem;
import org.talend.designer.dbmap.model.table.AbstractDataMapTable;

/**
 * DOC amaumont class global comment. Detailled comment <br/>
 * 
 * $Id: TableEntry.java 1877 2007-02-06 17:16:43Z amaumont $
 * 
 */
public abstract class TableEntry implements ITableEntry {

    private String expression;

    private AbstractDataMapTable parent;

    private String name;

    private List<Problem> problems;

    public TableEntry(AbstractDataMapTable abstractDataMapTable, String expression) {
        super();
        this.parent = abstractDataMapTable;
        this.expression = expression;
    }

    public TableEntry(AbstractDataMapTable abstractDataMapTable) {
        this(abstractDataMapTable, null);
    }

    /**
     * DOC amaumont ITableEntry constructor comment.
     * 
     * @param abstractDataMapTable
     * @param name, can't be null
     */
    public TableEntry(AbstractDataMapTable abstractDataMapTable, String name, String expression) {
        super();
        this.parent = abstractDataMapTable;
        this.name = name;
        if (this.name == null) {
            throw new IllegalArgumentException("Name of the TableEntry must not be null !"); //$NON-NLS-1$
        }
        this.expression = expression;
    }

    public String getExpression() {
        return this.expression;
    }

    public void setExpression(String expression) {
        this.expression = expression;
    }

    public AbstractDataMapTable getParent() {
        return this.parent;
    }

    public void setParent(AbstractDataMapTable parent) {
        this.parent = parent;
    }

    public String getName() {
        return this.name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getParentName() {
        if (parent != null) {
            return parent.getName();
        } else {
            throw new IllegalStateException("parent null"); //$NON-NLS-1$
        }
    }

    public List<Problem> getProblems() {
        return this.problems;
    }

    public void setProblems(List<Problem> problems) {
        this.problems = problems;
    }

}
