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

import org.talend.administrator.common.util.messages.MessageHandler;

/**
 * DOC mhirt class global comment. Detailled comment <br/>
 * 
 * $Id$
 * 
 */
public class DBException extends SystemException {

    private static final long serialVersionUID = 1L;

    private String key;

    private Object[] args;

    public DBException() {
        super();
    }

    public DBException(String key, Object... args) {
        this(null, key, args);
    }

    public DBException(String key) {
        this(key, new Object[0]);
    }

    public DBException(Throwable cause) {
        super(cause);
    }

    public DBException(String key, Throwable cause) {
        this(cause, key, new Object[0]);
    }

    public DBException(Throwable cause, String key, Object... args) {
        super(MessageHandler.getMessage(key), cause);
        this.key = key;
        this.args = args;
    }

    public Object[] getArgs() {
        return args;
    }

    public String getKey() {
        return key;
    }
}
