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
package org.talend.libraries;

import java.text.MessageFormat;

import org.apache.log4j.AppenderSkeleton;
import org.apache.log4j.Level;
import org.apache.log4j.spi.LoggingEvent;
import org.eclipse.core.runtime.ILog;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.Platform;
import org.eclipse.core.runtime.Status;
import org.osgi.framework.Bundle;

/**
 * Define the log stategy of application. <br/>
 * 
 * $Id$
 * 
 */
public class PluginLogAppender extends AppenderSkeleton {

    private String symbolicName;

    /*
     * (non-Javadoc)
     * 
     * @see org.apache.log4j.AppenderSkeleton#append(org.apache.log4j.spi.LoggingEvent)
     */
    protected void append(LoggingEvent event) {

        // don't go any further if event is not severe enough.
        if (!isAsSevereAsThreshold(event.getLevel())) {
            return;
        }

        ILog log = getBundleILog();
        if (log == null) {
            return;
        }

        // if throwable information is available, extract it.
        Throwable t = null;
        if (event.getThrowableInformation() != null && layout.ignoresThrowable()) {
            t = event.getThrowableInformation().getThrowable();
        }

        // build an Eclipse Status record, map severity and code from Event.
        Status s = new Status(getSeverity(event), getSymbolicName(), getCode(event), layout.format(event), t);

        log.log(s);
    }

    /**
     * map LoggingEvent's level to Status severity
     * 
     * @param ev
     * @return
     */
    private int getSeverity(LoggingEvent ev) {

        Level level = ev.getLevel();
        if (level == Level.FATAL || level == Level.ERROR)
            return IStatus.ERROR;
        else if (level == Level.WARN)
            return IStatus.WARNING;
        else if (level == Level.INFO)
            return IStatus.INFO;
        else
            // debug, trace and custom levels
            return IStatus.OK;
    }

    /**
     * Returns the pluginId under which the messages will be logged.
     * 
     * @return the symbolicName
     */
    public String getSymbolicName() {
        return this.symbolicName;
    }

    /**
     * Sets the symbolicName.
     * 
     * @param symbolicName the symbolicName to set
     */
    public void setSymbolicName(String symbolicName) {
        this.symbolicName = symbolicName;
    }

    /**
     * map LoggingEvent to Status code.
     * 
     * @param ev
     * @return
     */
    private int getCode(LoggingEvent ev) {
        return 0;
    }

    private ILog getBundleILog() {
        // get the bundle for a plug-in
        Bundle b = Platform.getBundle(getSymbolicName());
        if (b == null) {
            String m = MessageFormat.format("Plugin: {0} not found in {1}.", new Object[] { getSymbolicName(), this.name });
            this.errorHandler.error(m);
            return null;
        }

        return Platform.getLog(b);

    }

    public void close() {
        // nothing to close
    }

    public boolean requiresLayout() {
        return true;
    }

}
