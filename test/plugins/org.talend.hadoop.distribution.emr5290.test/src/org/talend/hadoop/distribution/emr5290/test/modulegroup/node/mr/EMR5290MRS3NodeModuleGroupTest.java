// ============================================================================
//
// Copyright (C) 2006-2019 Talend Inc. - www.talend.com
//
// This source code is available under agreement available at
// %InstallDIR%\features\org.talend.rcp.branding.%PRODUCTNAME%\%PRODUCTNAME%license.txt
//
// You should have received a copy of the agreement
// along with this program; if not, write to Talend SA
// 9 rue Pages 92150 Suresnes, France
//
// ============================================================================
package org.talend.hadoop.distribution.emr5290.test.modulegroup.node.mr;

import static org.junit.Assert.*;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

import org.junit.Test;
import org.talend.hadoop.distribution.DistributionModuleGroup;
import org.talend.hadoop.distribution.emr5290.EMR5290Constant;
import org.talend.hadoop.distribution.emr5290.EMR5290Distribution;
import org.talend.hadoop.distribution.emr5290.modulegroup.node.mr.EMR5290MRS3NodeModuleGroup;

public class EMR5290MRS3NodeModuleGroupTest {

    @Test
    public void testModuleGroups() throws Exception {
        Map<String, String> results = new HashMap<>();

        results
                .put(EMR5290Constant.S3_MODULE_GROUP.getModuleName(),
                        "((#LINK@NODE.MR_CONFIGURATION.DISTRIBUTION=='AMAZON_EMR') AND (#LINK@NODE.MR_CONFIGURATION.MR_VERSION=='EMR_5_29_0'))"); //$NON-NLS-1$

        Set<DistributionModuleGroup> moduleGroups =
                EMR5290MRS3NodeModuleGroup.getModuleGroups(EMR5290Distribution.DISTRIBUTION_NAME,
                        EMR5290Distribution.VERSION);
        assertEquals(results.size(), moduleGroups.size());
        moduleGroups.iterator();
        for (DistributionModuleGroup module : moduleGroups) {
            assertTrue("Should contain module " + module.getModuleName(), results.containsKey(module.getModuleName())); //$NON-NLS-1$
            if (results.get(module.getModuleName()) == null) {
                assertTrue("The condition of the module " + module.getModuleName() + " is not null.", //$NON-NLS-1$ //$NON-NLS-2$
                        results.get(module.getModuleName()) == null);
            } else {
                assertTrue("The condition of the module " + module.getModuleName() + " is null, but it should be " //$NON-NLS-1$ //$NON-NLS-2$
                        + results.get(module.getModuleName()) + ".", results.get(module.getModuleName()) != null); //$NON-NLS-1$
                assertEquals(results.get(module.getModuleName()), module.getRequiredIf().getConditionString());
            }
        }
    }

}
