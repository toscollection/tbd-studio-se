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

import java.util.regex.Pattern;

import org.talend.oozie.scheduler.constants.OozieJobProcessStatus;
import org.talend.oozie.scheduler.constants.SchedulerForHadoopConstants;

import com.hortonworks.etl.talend.JobSubmission;

/**
 * Created by Marvin Wang on Apr. 24, 2012
 */
public class OozieSchedulerCommonUtils {

    /**
     * If the current OS is Windows, return <code>true</code>, otherwise return <code>false</code>
     * 
     * @return
     */
    public static boolean isWindowsOS() {
        boolean isWindowsOS = false;
        String osName = System.getProperty("os.name");
        if (osName.trim().startsWith(SchedulerForHadoopConstants.OS_WINDOWS_PREFIX))
            isWindowsOS = true;
        return isWindowsOS;
    }

    public static boolean isValidURL(String URL) {
        boolean isValid = false;
        Pattern.matches("^(http|ftp|file)://.* ", URL);

        return isValid;
    }

    public static OozieJobProcessStatus convertToOozieJobProcessStatus(JobSubmission.Status status) {
        switch (status) {
        case PREP:
            return OozieJobProcessStatus.PREP;
        case RUNNING:
            return OozieJobProcessStatus.RUNNING;
        case SUCCEEDED:
            return OozieJobProcessStatus.SUCCEEDED;
        case KILLED:
            return OozieJobProcessStatus.KILLED;
        case FAILED:
            return OozieJobProcessStatus.FAILED;
        case SUSPENDED:
            return OozieJobProcessStatus.SUSPENDED;
        default:
            return OozieJobProcessStatus.INIT;
        }
    }
}
