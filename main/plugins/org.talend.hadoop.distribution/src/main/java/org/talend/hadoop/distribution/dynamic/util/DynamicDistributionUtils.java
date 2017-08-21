// ============================================================================
//
// Copyright (C) 2006-2016 Talend Inc. - www.talend.com
//
// This source code is available under agreement available at
// %InstallDIR%\features\org.talend.rcp.branding.%PRODUCTNAME%\%PRODUCTNAME%license.txt
//
// You should have received a copy of the agreement
// along with this program; if not, write to Talend SA
// 9 rue Pages 92150 Suresnes, France
//
// ============================================================================
package org.talend.hadoop.distribution.dynamic.util;


/**
 * DOC cmeng  class global comment. Detailled comment
 */
public class DynamicDistributionUtils {

    public static String fillTemplate(String templateString, String... args) {
        String fullString = templateString;
        if (args != null && 0 < args.length) {
            for (int i = 0; i < args.length; ++i) {
                String arg = args[i];
                fullString = fullString.replaceAll("\\{" + i + "\\}", arg); //$NON-NLS-1$ //$NON-NLS-2$
            }
        }
        return fullString;
    }

}
