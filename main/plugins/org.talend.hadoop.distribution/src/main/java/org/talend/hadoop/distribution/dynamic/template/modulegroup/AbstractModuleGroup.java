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
package org.talend.hadoop.distribution.dynamic.template.modulegroup;

import java.util.Collections;
import java.util.Optional;
import java.util.Set;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Level;
import org.apache.log4j.LogManager;
import org.talend.commons.CommonsPlugin;
import org.talend.commons.exception.CommonExceptionHandler;
import org.talend.core.runtime.dynamic.IDynamicPluginConfiguration;
import org.talend.hadoop.distribution.DistributionModuleGroup;
import org.talend.hadoop.distribution.dynamic.adapter.DynamicPluginAdapter;

public abstract class AbstractModuleGroup {

    private DynamicPluginAdapter pluginAdapter;

    public Set<DistributionModuleGroup> getModuleGroups() throws Exception {
        IDynamicPluginConfiguration configuration = getPluginAdapter().getPluginConfiguration();
        String distribution = configuration.getDistribution();
        String version = configuration.getId();
        return getModuleGroups(distribution, version);
    }

    public Set<DistributionModuleGroup> getModuleGroups(String distribution, String version) throws Exception {
        return Collections.emptySet();
    }

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
        if (CommonsPlugin.isDebugMode()) {
            if (StringUtils.isBlank(id)) {
                String message = this.getClass().getSimpleName()
                        + ": Can't find runtime id, please make sure that your generated json is not broken and your template has already been configured correctly.";
                Level level = LogManager.getRootLogger().getLevel();
                if (level != null) {
                    if (level.getSyslogEquivalent() < Level.INFO_INT) {
                        System.out.println(message);
                    }
                }
                CommonExceptionHandler.warn(message);
            }
        }
    }

    protected Optional<DistributionModuleGroup> optionalDistributionGroup(final DynamicModuleGroupConstant groupIdentifier) throws Exception {
        return buildDistributionGroup(groupIdentifier, false);
    }

    protected Optional<DistributionModuleGroup> requiredDistributionGroup(final DynamicModuleGroupConstant groupIdentifier) throws Exception {
        return buildDistributionGroup(groupIdentifier, true);
    }

    private Optional<DistributionModuleGroup> buildDistributionGroup(final DynamicModuleGroupConstant groupIdentifier, boolean required) throws Exception {
        String groupIdByTemplateId = pluginAdapter
                .getRuntimeModuleGroupIdByTemplateId(groupIdentifier.getModuleName());
        checkRuntimeId(groupIdByTemplateId);
        return Optional.ofNullable(groupIdByTemplateId)
                .map(id -> new DistributionModuleGroup(id, required));
    }
}
