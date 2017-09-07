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
package org.talend.hadoop.distribution.dynamic;

import org.eclipse.core.runtime.IProgressMonitor;
import org.talend.core.runtime.dynamic.DynamicFactory;
import org.talend.core.runtime.dynamic.IDynamicPluginConfiguration;
import org.talend.hadoop.distribution.dynamic.adapter.TemplateBean;

/**
 * DOC cmeng  class global comment. Detailled comment
 */
public class DynamicDistriConfigAdapter extends AbstractDynamicAdapter {

    public DynamicDistriConfigAdapter(TemplateBean templateBean, DynamicConfiguration configuration) {
        super(templateBean, configuration);
    }

    public IDynamicPluginConfiguration adapt(IProgressMonitor monitor) throws Exception {
        IDynamicPluginConfiguration pluginConfiguration = DynamicFactory.getInstance().createDynamicPluginConfiguration();

        TemplateBean templateBean = getTemplateBean();
        DynamicConfiguration configuration = getConfiguration();

        String id = templateBean.getId();
        String name = templateBean.getName();
        String description = templateBean.getDescription();
        String distribution = templateBean.getDistribution();
        String repository = templateBean.getRepository();

        pluginConfiguration.setId(id);
        pluginConfiguration.setName(name);
        pluginConfiguration.setDescription(description);
        pluginConfiguration.setDistribution(distribution);
        pluginConfiguration.setRepository(repository);
        pluginConfiguration.setVersion(configuration.getVersion());

        return pluginConfiguration;
    }

    @Override
    protected void resolve() throws Exception {
        // nothing to do
    }

}
