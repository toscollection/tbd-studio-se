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

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.talend.commons.exception.ExceptionHandler;
import org.talend.core.model.general.ModuleNeeded;
import org.talend.core.runtime.dynamic.IDynamicConfiguration;
import org.talend.librariesmanager.model.ModulesNeededProvider;

/**
 * DOC cmeng  class global comment. Detailled comment
 */
public class DynamicLibraryAdapter extends AbstractDynamicAdapter {

    public static final String TAG_NAME = "library"; //$NON-NLS-1$

    public static final String ATTR_ID = "id"; //$NON-NLS-1$

    private static Map<String, ModuleNeeded> existingModuleMap = new HashMap<>();

    static {
        try {
            List<ModuleNeeded> modules = ModulesNeededProvider.getModulesNeededForApplication();
            if (modules != null && !modules.isEmpty()) {
                for (ModuleNeeded module : modules) {
                    String id = module.getId();
                    if (id != null && !id.isEmpty()) {
                        existingModuleMap.put(id, module);
                    }
                }
            }
        } catch (Exception e) {
            ExceptionHandler.process(e);
        }
    }

    private static ModuleNeeded getExistingModule(String id) {
        return existingModuleMap.get(id);
    }

    public DynamicLibraryAdapter(IDynamicConfiguration dynamicConfiguration, String id) {
        super(dynamicConfiguration, id);
        if (!TAG_NAME.equals(dynamicConfiguration.getTagName())) {
            throw new RuntimeException("The input configuration is not an instance of " + TAG_NAME); //$NON-NLS-1$
        }
    }

    public IDynamicConfiguration getDynamicConfiguration() {
        return (IDynamicConfiguration) getDynamicAttribute();
    }

    @Override
    public void adapt() {

        IDynamicConfiguration dynamicLibraryNeeded = getDynamicConfiguration();

        String id = null;

        Object defaultKeyObj = getAttributeDefault(ATTR_ID);
        if (defaultKeyObj != null) {
            String defaultKey = defaultKeyObj.toString();
            ModuleNeeded existingModule = getExistingModule(defaultKey);
            if (existingModule != null) {
                id = existingModule.getId();
            }
        }
        if (id == null) {
            id = getNewValueByTemplate(ATTR_ID);
        }

        dynamicLibraryNeeded.setAttribute(ATTR_ID, id);

        List<IDynamicConfiguration> childConfigurations = dynamicLibraryNeeded.getChildConfigurations();
        if (childConfigurations != null && !childConfigurations.isEmpty()) {
            for (IDynamicConfiguration config : childConfigurations) {
                AbstractDynamicAdapter adapter = DynamicAdapterFactory.getInstance().create(config.getTagName(), config, getDynamicId());
                adapter.adapt();
            }
        }

        super.adapt();

    }

}
