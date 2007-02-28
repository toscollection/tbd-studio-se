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
 * $Id: ITableEntry.java 1601 2007-01-25 16:46:15Z amaumont $
 * 
 */
public interface ITableEntry {

    public String getName();

    public void setName(String name);

    public String getParentName();

    public AbstractDataMapTable getParent();

    public String getExpression();

    public void setExpression(String expression);

    public List<Problem> getProblems();

    public void setProblems(List<Problem> errorMessage);

}
