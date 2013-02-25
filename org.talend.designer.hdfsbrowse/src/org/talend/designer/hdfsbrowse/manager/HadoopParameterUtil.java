// ============================================================================
//
// Copyright (C) 2006-2013 Talend Inc. - www.talend.com
//
// This source code is available under agreement available at
// %InstallDIR%\features\org.talend.rcp.branding.%PRODUCTNAME%\%PRODUCTNAME%license.txt
//
// You should have received a copy of the agreement
// along with this program; if not, write to Talend SA
// 9 rue Pages 92150 Suresnes, France
//
// ============================================================================
package org.talend.designer.hdfsbrowse.manager;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.talend.designer.hdfsbrowse.constants.HadoopPatternConstants;

/**
 * created by ycbai on 2013-2-1 Detailled comment
 * 
 */
public class HadoopParameterUtil {

    /**
     * DOC ycbai Comment method "isValidNameNodeURI".
     * 
     * Check whether the name node uri is valid.
     * 
     * @param nameNodeURI
     * @return
     */
    public static boolean isValidNameNodeURI(String nameNodeURI) {
        Pattern p = Pattern.compile(HadoopPatternConstants.NAME_NODE_URI);
        Matcher m = p.matcher(nameNodeURI);

        return m.matches();
    }

    /**
     * DOC ycbai Comment method "getHostNameFromNameNodeURI".
     * 
     * Get host name from name node uri.
     * 
     * @param nameNodeURI
     * @return
     */
    public static String getHostNameFromNameNodeURI(String nameNodeURI) {
        String hostName = ""; //$NON-NLS-1$
        if (nameNodeURI == null) {
            return hostName;
        }

        String[] array = nameNodeURI.split(":"); //$NON-NLS-1$
        if (array.length > 1) {
            hostName = array[1];
        }
        if (hostName.startsWith("//")) { //$NON-NLS-1$
            hostName = hostName.substring(hostName.indexOf("//") + 2); //$NON-NLS-1$
        }

        return hostName;
    }

}
