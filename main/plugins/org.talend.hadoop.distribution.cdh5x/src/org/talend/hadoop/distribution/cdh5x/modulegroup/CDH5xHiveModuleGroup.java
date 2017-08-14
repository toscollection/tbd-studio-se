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
package org.talend.hadoop.distribution.cdh5x.modulegroup;

import java.util.HashSet;
import java.util.Set;

import org.talend.hadoop.distribution.DistributionModuleGroup;
import org.talend.hadoop.distribution.cdh5x.CDH5xConstant;
import org.talend.hadoop.distribution.condition.BasicExpression;
import org.talend.hadoop.distribution.condition.BooleanOperator;
import org.talend.hadoop.distribution.condition.ComponentCondition;
import org.talend.hadoop.distribution.condition.MultiComponentCondition;
import org.talend.hadoop.distribution.condition.ShowExpression;
import org.talend.hadoop.distribution.condition.SimpleComponentCondition;
import org.talend.hadoop.distribution.constants.HiveConstant;

public class CDH5xHiveModuleGroup extends AbstractModuleGroup {

    public CDH5xHiveModuleGroup(String id) {
        super(id);
    }

    public Set<DistributionModuleGroup> getModuleGroups() {
        Set<DistributionModuleGroup> hs = new HashSet<>();
        hs.add(new DistributionModuleGroup(CDH5xConstant.HIVE_MODULE_GROUP.getModuleName(getId())));
        hs.add(new DistributionModuleGroup(CDH5xConstant.HDFS_MODULE_GROUP.getModuleName(getId())));
        hs.add(new DistributionModuleGroup(CDH5xConstant.MAPREDUCE_MODULE_GROUP.getModuleName(getId())));

        // The following condition instance stands for:
        // (isShow[STORE_BY_HBASE] AND STORE_BY_HBASE=='true')
        ComponentCondition hbaseLoaderCondition = new MultiComponentCondition(new SimpleComponentCondition(new BasicExpression(
                HiveConstant.HIVE_CONFIGURATION_COMPONENT_HBASEPARAMETER)), //
                BooleanOperator.AND, //
                new SimpleComponentCondition(new ShowExpression(HiveConstant.HIVE_CONFIGURATION_COMPONENT_HBASEPARAMETER)));
        // The Hive components need to import some hbase libraries if the "Use HBase storage" is checked.
        hs.add(new DistributionModuleGroup(CDH5xConstant.HIVE_HBASE_MODULE_GROUP.getModuleName(getId()), false,
                hbaseLoaderCondition));

        return hs;
    }

}
