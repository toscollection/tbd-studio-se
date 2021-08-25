// ============================================================================
//
// Copyright (C) 2006-2021 Talend Inc. - www.talend.com
//
// This source code is available under agreement available at
// %InstallDIR%\features\org.talend.rcp.branding.%PRODUCTNAME%\%PRODUCTNAME%license.txt
//
// You should have received a copy of the agreement
// along with this program; if not, write to Talend SA
// 9 rue Pages 92150 Suresnes, France
//
// ============================================================================
package org.talend.hadoop.distribution.mapr611.modulegroup;

import java.util.HashSet;
import java.util.Set;

import org.talend.hadoop.distribution.DistributionModuleGroup;
import org.talend.hadoop.distribution.mapr611.MapR611Constant;

public class MapR611SparkBatchModuleGroup {

    public static Set<DistributionModuleGroup> getModuleGroups() {
        Set<DistributionModuleGroup> hs = new HashSet<>();
        hs.add(new DistributionModuleGroup(MapR611Constant.SPARK_MODULE_GROUP.getModuleName(), false));
        // can be used for parquet, need to check
        // hs.add(new DistributionModuleGroup(MapR510Constant.SPARK_MRREQUIRED_MODULE_GROUP.getModuleName(), true,
        // condition));
        hs.add(new DistributionModuleGroup(MapR611Constant.SPARK_YARN_CLUSTER_MRREQUIRED_MODULE_GROUP.getModuleName(), true));
        hs.add(new DistributionModuleGroup(MapR611Constant.HDFS_MODULE_GROUP.getModuleName(), false));
        hs.add(new DistributionModuleGroup(MapR611Constant.MAPREDUCE_MODULE_GROUP.getModuleName(), false));
        return hs;
    }

}
