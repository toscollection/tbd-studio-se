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
package org.talend.hadoop.distribution.utils;

import java.util.ArrayList;
import java.util.List;

import org.talend.core.model.general.ModuleNeeded;
import org.talend.librariesmanager.model.ExtensionModuleManager;

/**
 * Utilities for distributions module groups (plugin.xml)
 */
public class ModuleGroupsUtils {

    /**
     * Get all module's libraries IDs
     * 
     * @param moduleGroupName The name of the module group
     * @return A list of libraries IDs contained in the module group
     */
    public static List<String> getModuleLibrariesIDs(String moduleGroupName) {

        List<String> moduleLibrariesIDs = new ArrayList<>();

        List<ModuleNeeded> moduleNeededList = ExtensionModuleManager.getInstance().getModuleNeeded(moduleGroupName, true);

        if (moduleNeededList != null && !moduleNeededList.isEmpty()) {
            for (ModuleNeeded moduleNeeded : moduleNeededList) {
                String id = moduleNeeded.getId();
                moduleLibrariesIDs.add(id);
            }
        }

        return moduleLibrariesIDs;
    }

}
