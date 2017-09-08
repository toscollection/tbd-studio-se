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

import org.apache.commons.lang.StringUtils;
import org.talend.hadoop.distribution.dynamic.DynamicPluginAdapter;

/**
 * DOC cmeng  class global comment. Detailled comment
 */
public abstract class AbstractModuleGroup {

    private DynamicPluginAdapter pluginAdapter;

    public AbstractModuleGroup(DynamicPluginAdapter pluginAdapter) {
        this.pluginAdapter = pluginAdapter;
    }


    public DynamicPluginAdapter getPluginAdapter() {
        return this.pluginAdapter;
    }

    public void setPluginAdapter(DynamicPluginAdapter pluginAdapter) {
        this.pluginAdapter = pluginAdapter;
    }

    protected void checkRuntimeId(String id) throws Exception {
        if (StringUtils.isEmpty(id)) {
            throw new Exception("Can't find runtime id for " + id);
        }
    }
}
