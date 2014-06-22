// ============================================================================
//
// Copyright (C) 2006-2012 Talend Inc. - www.talend.com
//
// This source code is available under agreement available at
// %InstallDIR%\features\org.talend.rcp.branding.%PRODUCTNAME%\%PRODUCTNAME%license.txt
//
// You should have received a copy of the agreement
// along with this program; if not, write to Talend SA
// 9 rue Pages 92150 Suresnes, France
//
// ============================================================================
package org.talend.oozie.scheduler.constants;

import com.hortonworks.etl.talend.JobContext.Timeunit;

/**
 * Created by Marvin Wang on May 6, 2012. The enum provides the time unit for Oozie Coordinator. All the time units are
 * mapping to {@link Timeunit} as follows:
 * 
 * <pre>
 * <li>{@link TOozieTimeUnit#MINUTE}
 * <li>{@link TOozieTimeUnit#HOUR}
 * <li>{@link TOozieTimeUnit#DAY}
 * <li>{@link TOozieTimeUnit#WEEK}
 * <li>{@link TOozieTimeUnit#MONTH}
 * <li>{@link TOozieTimeUnit#END_OF_DAY}
 * <li>{@link TOozieTimeUnit#END_OF_MONTH}
 * <li>{@link TOozieTimeUnit#NONE}
 */
public enum TOozieTimeUnit {

    MINUTE("minute", Timeunit.MINUTE),
    HOUR("hour", Timeunit.HOUR),
    DAY("day", Timeunit.DAY),
    WEEK("week", Timeunit.WEEK),
    MONTH("month", Timeunit.MONTH),
    END_OF_DAY("end of day", Timeunit.END_OF_DAY),
    END_OF_MONTH("end of month", Timeunit.END_OF_MONTH),
    NONE("none", Timeunit.NONE);

    private String displayName;

    private Timeunit timeUnit;

    private TOozieTimeUnit(String displayName, Timeunit timeUnit) {
        this.displayName = displayName;
        this.timeUnit = timeUnit;
    }

    public String getDisplayName() {
        return this.displayName;
    }

    public Timeunit getTimeUnit() {
        return this.timeUnit;
    }

}
