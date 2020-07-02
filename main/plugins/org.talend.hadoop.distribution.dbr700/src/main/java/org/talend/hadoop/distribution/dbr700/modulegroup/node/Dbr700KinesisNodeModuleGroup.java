// ============================================================================
//
// Copyright (C) 2006-2020 Talend Inc. - www.talend.com
//
// This source code is available under agreement available at
// %InstallDIR%\features\org.talend.rcp.branding.%PRODUCTNAME%\%PRODUCTNAME%license.txt
//
// You should have received a copy of the agreement
// along with this program; if not, write to Talend SA
// 9 rue Pages 92150 Suresnes, France
//
// ============================================================================
package org.talend.hadoop.distribution.dbr700.modulegroup.node;

import java.util.HashSet;
import java.util.Set;

import org.talend.hadoop.distribution.DistributionModuleGroup;
import org.talend.hadoop.distribution.dbr700.Dbr700Constant;
import org.talend.hadoop.distribution.utils.ModuleGroupsUtils;

public class Dbr700KinesisNodeModuleGroup {

    public static Set<DistributionModuleGroup> getModuleGroups(String distribution, String version) {
        Set<DistributionModuleGroup> moduleGroups = new HashSet<>();
        moduleGroups.add(new DistributionModuleGroup(Dbr700Constant.SPARK_STREAMING_LIB_KINESIS_DBR700.getModuleName(),
                true,
                null));
        return moduleGroups;
    }
}

