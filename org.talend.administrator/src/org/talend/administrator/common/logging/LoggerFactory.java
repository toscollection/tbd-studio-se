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

import java.io.InputStream;
import java.net.URL;
import java.util.logging.LogManager;
import java.util.logging.Logger;

import org.talend.administrator.common.exception.SystemException;

/**
 * DOC mhirt class global comment. Detailled comment <br/>
 * 
 * $Id$
 * 
 */
public final class LoggerFactory {

    private static final String LOGGER_PROPERTIES_FILE_NAME = "AdministratorLogger.properties";

    private static LoggerFactory loggerFactory;

    protected static LoggerFactory getInstance() throws SystemException {
        if (loggerFactory == null) {
            loggerFactory = new LoggerFactory();
            // getLogger( LoggerFactory.class ).fine( "Instance of LoggerFactory
            // OK !!!" );
        }
        return loggerFactory;
    }

    private LoggerFactory() throws SystemException {
        URL url = LoggerFactory.class.getClassLoader().getResource(LOGGER_PROPERTIES_FILE_NAME);
        System.out.println("---------------------------\nReading logging configuration from '" + url
                + "'\n---------------------------\n");
        try {
            InputStream inputStream = url.openStream();
            LogManager.getLogManager().readConfiguration(inputStream);
            inputStream.close();
        } catch (Exception e) {
            e.printStackTrace();
            throw new SystemException("Can't open logging properties file: '"
                    + LOGGER_PROPERTIES_FILE_NAME
                    + "'. "
                    + (url == null ? "File not found in any classpath locations.\n\t classpath="
                            + System.getProperty("java.class.path") + "\n\t" : "Can't access '" + url + "' for read."),
                    e);
        }
    }

    public static Logger getLogger(Object invoker) {
        return getLogger(invoker.getClass());
    }

    public static Logger getLogger(Class invoker) {
        String className = invoker.getName();
        return getLogger(className);
    }

    protected static Logger getLogger(String className) {
        try {
            getInstance();
        } catch (SystemException ex) {
            System.err.println("Failed reading configauration of logger. \n" + ex.getMessage()
                    + "Using default JVM configuration @ JRE_HOME/lib/logging.properties");
        }
        return Logger.getLogger(className);
    }
}
