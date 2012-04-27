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
package org.talend.oozie.scheduler.utils;

import com.hortonworks.etl.talend.JobContext.Timeunit;

/**
 */
public class OozieSchedulerStringUtils {

    public static Timeunit convertTimeUnit(int selectionIndex) {
        switch (selectionIndex) {
        case 0:
            return Timeunit.MINUTE;
        case 1:
            return Timeunit.HOUR;
        case 2:
            return Timeunit.DAY;
        case 3:
            return Timeunit.WEEK;
        case 4:
            return Timeunit.MONTH;
        case 5:
            return Timeunit.END_OF_DAY;
        case 6:
            return Timeunit.END_OF_MONTH;
        case 7:
            return Timeunit.NONE;
        default:
            return Timeunit.NONE;
        }
    }

    /**
     * @param dateStr
     * @return
     */
    public static String formatDateLog(String dateStr) {
        StringBuffer sb = new StringBuffer("");
        sb.append("[");
        sb.append(dateStr);
        sb.append("]");
        return sb.toString();
    }

}
