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

import org.talend.oozie.scheduler.i18n.Messages;

/**
 */
public class SchedulerForHadoopConstants {

    public static final String TIME_UNIT_TYPE_MINUTE = "minute";

    public static final String TIME_UNIT_TYPE_HOUR = "hour";

    public static final String TIME_UNIT_TYPE_DAY = "day";

    public static final String TIME_UNIT_TYPE_WEEK = "week";

    public static final String TIME_UNIT_TYPE_MONTH = "month";

    public static final String TIME_UNIT_TYPE_ENDOFDAY = "end of day";

    public static final String TIME_UNIT_TYPE_ENDOFMONTH = "end of month";

    public static final String[] TIME_UNIT_VALUES = new String[] { TIME_UNIT_TYPE_MINUTE, TIME_UNIT_TYPE_HOUR,
            TIME_UNIT_TYPE_DAY, TIME_UNIT_TYPE_MONTH, TIME_UNIT_TYPE_ENDOFDAY, TIME_UNIT_TYPE_ENDOFMONTH };

    public static final String NAME_NODE_URI = "nameNodeURI";

    public static final String JOB_TRACKER_URI = "jobTrackerURI";

    public static final String TOS_JOB_FQCN = "tosJobFQCN";

    public static final String WF_APP_PATH = "wfAppPath";

    public static final String OS_WINDOWS_PREFIX = Messages.getString("OS_windows_name_prefix");
}
