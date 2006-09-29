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
package org.talend.administrator.common.constants;

/**
 * DOC mhirt class global comment. Detailled comment <br/>
 * 
 * $Id$
 * 
 */
public final class Constants {
    
    // Fields Validating constant
    
    public static final String DATE_PATTERN = "conf.pattern.DATE";

    public static final String TIME_PATTERN = "conf.pattern.TIME";

    public static final String PRECISETIME_PATTERN = "conf.pattern.TIMEPRECISE";

    public static final String PATTERN_SEPARATOR = " ";
    
    // Action Forward Constant

    public static final String LIST = "list";

    public static final String LOAD = "load";

    public static final String FORM = "form";

    public static final String CONF_DELETE = "confDelete";

    public static final String ERROR_LIST = "errorList";

    public static final String ERROR_FORM = "errorForm";

    public static final String ERROR = "error";

    public static final String CRASH_SESSION = "crashSession";

    public static final String FAILURE = "failure";

    public static final String SUCCESS = "success";

    public static final String READ_MODE = "readMode";

    public static final String USER_LIST = "userList";
    
    // ActrionConstants

    public static final String USER = "user";
    
    public static final String USER_ROLE = "userRole";

    /**
     * DOC mhirt Constants class global comment. Detailled comment <br/>
     * 
     * $Id$
     * 
     */
    public enum Forward {
        LIST(Constants.LIST),
        FORM(Constants.FORM),
        CONF_DELETE(Constants.CONF_DELETE),
        ERROR_LIST(Constants.ERROR_LIST),
        ERROR_FORM(Constants.ERROR_FORM),
        ERROR(Constants.ERROR),
        CRASH_SESSION(Constants.CRASH_SESSION),
        FAILURE(Constants.ERROR_LIST),
        SUCCESS(Constants.SUCCESS);

        String forwardKey;

        Forward(String forwardKey) {
            this.forwardKey = forwardKey;
        }

        @Override
        public String toString() {
            return forwardKey;
        }
    }

    /**
     * DOC mhirt Constants class global comment. Detailled comment <br/>
     * 
     * $Id$
     * 
     */
    public enum Action {
        SUBMIT,
        UPDATE,
        ADD,
        DELETE,
        CONFIRMDELETE,
        LIST;
        
        @Override
        public String toString() {
            return super.toString().toLowerCase();
        }
    }

    /**
     * DOC mhirt Constants class global comment. Detailled comment <br/>
     * 
     * $Id$
     * 
     */
    public enum ReadMode {
        READ_ONLY,
        READ_WRITE;
    }

    /**
     * DOC mhirt Constants class global comment. Detailled comment <br/>
     * 
     * $Id$
     * 
     */
    public enum Event {
        LEVEL("changeLevel"),
        UNIT("changeUnit"),
        SCAN("scan");

        private Event(String tostr) {
            this.tostr = tostr;
        }

        public String toString() {
            return tostr;
        }

        private String tostr;
    }
}
