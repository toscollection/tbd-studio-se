// ============================================================================
//
// Copyright (C) 2006-2015 Talend Inc. - www.talend.com
//
// This source code is available under agreement available at
// %InstallDIR%\features\org.talend.rcp.branding.%PRODUCTNAME%\%PRODUCTNAME%license.txt
//
// You should have received a copy of the agreement
// along with this program; if not, write to Talend SA
// 9 rue Pages 92150 Suresnes, France
//
// ============================================================================
package org.talend.cloudera.navigator.api;

import org.apache.commons.lang.StringUtils;

/**
 * created by pbailly on 16 Oct 2015 Detailled comment
 *
 */
public class ClouderaAPIUtil {

    public static final String[] FILE_INPUT_OUTPUT_COMPONENT_PREFIXS = { "tFile", "tHDFS", "tParquet", "tAvro" };

    /**
     * Is the original Talend Studio component a FileInput/Output component ? These components need to be linked to
     * datasets
     */
    public static boolean isFileInputOutputComponent(String componentName) {
        for (String prefix : FILE_INPUT_OUTPUT_COMPONENT_PREFIXS) {
            if (componentName.toLowerCase().startsWith(prefix.toLowerCase())) {
                return true;
            }
        }
        return false;
    }

    /**
     * Retrieve the last chuck of a path, in order to define the datasetName
     */
    public static String getDatasetName(String path) {
        if (StringUtils.isNotBlank(path)) {
            String[] separatedPath = path.split("/");
            for (int i = separatedPath.length - 1; i >= 0; i--) {
                if (StringUtils.isNotBlank(separatedPath[i])) {
                    return separatedPath[i];
                }
            }
        }
        return "UnamedTalendDataset";
    }
}
