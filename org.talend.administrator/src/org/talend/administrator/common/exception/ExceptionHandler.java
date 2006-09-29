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
import java.util.List;
import java.util.MissingResourceException;
import java.util.logging.Logger;

import org.talend.administrator.common.logging.LoggerFactory;

/**
 * DOC mhirt class global comment. Detailled comment <br/>
 * 
 * $Id$
 * 
 */
public final class ExceptionHandler {
    
    private ExceptionHandler() {
        
    }

    public static final boolean PRINT_STACK_TRACE_TO_SYS_OUT = false;

    private static Logger log = LoggerFactory.getLogger(ExceptionHandler.class);

    public static final List<Class> EXCEPTIONS_TO_SKIP_STACK_ON = Arrays
            .asList(new Class[] { MissingResourceException.class });

    public static void process(Throwable ex) {
        process(ExceptionHandler.class, "process", ex);
    }

    public static void process(Class clazz, String method, Throwable ex) {
        process(clazz, method, ex, null);
    }

    public static void process(String clazz, String method, Throwable ex) {
        process(clazz, method, ex, null);
    }

    public static void process(Class clazz, String method, Throwable ex, String warningMsg) {
        process(clazz.getName(), method, ex, warningMsg);
    }

    public static void process(String clazz, String method, Throwable ex, String warningMsg) {
        if (PRINT_STACK_TRACE_TO_SYS_OUT) {
            ex.printStackTrace();
        }

        if (warningMsg != null && warningMsg.length() != 0) {
            log.warning(warningMsg);
        }

        if (!EXCEPTIONS_TO_SKIP_STACK_ON.contains(ex.getCause())) {
            ex.printStackTrace();
            log.throwing(clazz, method, ex); // SML Log in finer level
        }
    }
}
