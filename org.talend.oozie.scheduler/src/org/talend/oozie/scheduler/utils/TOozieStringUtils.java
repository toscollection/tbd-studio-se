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

import org.talend.oozie.scheduler.constants.TOozieOutputMessages;

/**
 */
public class TOozieStringUtils {

    public static final String REGEX_PATH = "";

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

    /**
     * Outputs log as a line beginning with date like "[yyyy-MM-dd HH:mm:ss]Starting job remote running..."
     * 
     * @param log
     * @return
     */
    public static String outputLogWithPrefixDate(String log) {
        StringBuffer sb = new StringBuffer("");
        String currentDate = TOozieDateUtils.fetchCurrentDate();
        sb.append(formatDateLog(currentDate));
        sb.append(log == null ? "" : log);
        sb.append(TOozieOutputMessages.MSG_OUTPUT_LINE_BREAK_CHAR);
        return sb.toString();
    }

    /**
     * 
     * @param path
     * @return
     */
    public static boolean isValidPath(String path) {
        boolean isValid = false;

        return isValid;
    }

}
