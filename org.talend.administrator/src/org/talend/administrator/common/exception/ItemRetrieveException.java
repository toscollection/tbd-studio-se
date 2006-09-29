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

import java.util.Arrays;

/**
 * DOC mhirt class global comment. Detailled comment <br/>
 * 
 * $Id$
 * 
 */
public class ItemRetrieveException extends BusinessException {

    private static final long serialVersionUID = 1L;

    private Object persistent;

    private String[] matchProperties;

    public ItemRetrieveException(Throwable cause, String key, Object... args) {
        super(cause, key, args);
    }

    public ItemRetrieveException(String key, Object[] args) {
        super(key, args);
    }

    public ItemRetrieveException(String key, Throwable cause) {
        super(key, cause);
    }

    public ItemRetrieveException(String key) {
        super(key);
    }

    public ItemRetrieveException(Throwable cause) {
        super(cause);
    }

    public ItemRetrieveException(Object persistent, String... properties) {
        this.persistent = persistent;
        matchProperties = properties;
    }

    public ItemRetrieveException() {
        super();
    }

    @Override
    public String getMessage() {
        if (persistent == null) {
            return super.getMessage();
        }

        String props = "";
        if (matchProperties != null) {
            props = Arrays.asList(matchProperties).toString();
        }
        return "Object " + persistent.getClass().getName() + ": " + persistent
                + "can't be found with the matching properties: " + props;
    }
}
