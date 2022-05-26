package org.talend.hadoop.distribution.dbr73x.modulegroup.node;

import java.util.HashSet;
import java.util.Set;

import org.talend.hadoop.distribution.DistributionModuleGroup;
import org.talend.hadoop.distribution.dbr73x.DBR73xConstant;

public class DBR73xRestNodeModuleGroup {
	
	public static Set<DistributionModuleGroup> getModuleGroups(String distribution, String version) {
        Set<DistributionModuleGroup> moduleGroups = new HashSet<>();
        moduleGroups.add(new DistributionModuleGroup(
                DBR73xConstant.REST_GROUP_DBR73X.getModuleName(),
                true,
                null
        ));
        return moduleGroups;
    }
}
