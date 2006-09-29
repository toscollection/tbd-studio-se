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
package org.talend.administrator.common.logging;

import java.text.MessageFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.logging.Formatter;
import java.util.logging.Level;
import java.util.logging.LogRecord;

/**
 * DOC mhirt class global comment. Detailled comment <br/>
 * 
 * $Id$
 * 
 */
public class LogFormatter extends Formatter {

    public static final Level EXCEPTION_LEVEL = Level.SEVERE;

    private SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy.MM.dd HH:mm:ss");

    private String lineSeparator = System.getProperty("line.separator");

    public LogFormatter() {
        System.err.println("***************** LogFormatter created!");
    }

    @Override
    public synchronized String format(LogRecord record) {
        StringBuffer toReturn = new StringBuffer("zied: ");
        toReturn.append(dateFormat.format(new Date(record.getMillis())));
        toReturn.append(" ");
        toReturn.append(record.getLevel().toString() + " ");
        int size = 28;
        while (toReturn.length() < size) {
            toReturn.append(" ");
        }
        toReturn.append(record.getSourceClassName());
        toReturn.append(".");
        toReturn.append(record.getSourceMethodName());
        toReturn.append("() ");
        toReturn.append(formatMessage(record));
        toReturn.append(lineSeparator);
        return toReturn.toString();
    }

    @Override
    public synchronized String formatMessage(LogRecord record) {
        String message = record.getMessage();
        Object[] parameters = record.getParameters();
        if (parameters == null || parameters.length == 0) {
            Throwable throwable = record.getThrown();
            if (throwable == null) {
                return message;
            }

            StringBuffer message2 = new StringBuffer(throwable.getMessage());
            message2.append(lineSeparator);
            StackTraceElement[] trace = throwable.getStackTrace();
            for (int i = 0; i < trace.length; i++) {
                StackTraceElement element = trace[i];
                message2.append("\t");
                message2.append(element.getClassName());
                message2.append(".");
                message2.append(element.getMethodName());
                message2.append("()");
                message2.append("[" + element.getLineNumber() + "]");
                message2.append(lineSeparator);
            }
            return message2.toString();
        }
        return MessageFormat.format(message, parameters);
    }
}
