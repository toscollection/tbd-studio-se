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
package org.talend.administrator.common.exception;

/**
 * DOC mhirt class global comment. Detailled comment <br/>
 * 
 * $Id$
 * 
 */
public class ItemNotFoundException extends ItemRetrieveException {

    private static final long serialVersionUID = 1L;

    public ItemNotFoundException(Object item, String... properties) {
        super(item, properties);
    }

    public ItemNotFoundException() {
        super();
        // TODO Auto-generated constructor stub
    }

    public ItemNotFoundException(String arg0) {
        super(arg0);
        // TODO Auto-generated constructor stub
    }

    public ItemNotFoundException(String arg0, Throwable arg1) {
        super(arg0, arg1);
        // TODO Auto-generated constructor stub
    }

    public ItemNotFoundException(Throwable arg0) {
        super(arg0);
        // TODO Auto-generated constructor stub
    }

}
