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

import java.util.List;

import org.talend.core.runtime.dynamic.IDynamicExtension;
import org.talend.core.runtime.dynamic.IDynamicPlugin;

/**
 * DOC cmeng  class global comment. Detailled comment
 */
public class DynamicPluginAdapter extends AbstractDynamicAdapter {

    public static final String TAG_NAME = "plugin"; //$NON-NLS-1$

    public DynamicPluginAdapter(IDynamicPlugin dynamicPlugin, String id) {
        super(dynamicPlugin, id);
        if (!TAG_NAME.equals(dynamicPlugin.getTagName())) {
            throw new RuntimeException("The input configuration is not an instance of " + TAG_NAME); //$NON-NLS-1$
        }
    }

    public IDynamicPlugin getDynamicPlugin() {
        return (IDynamicPlugin) getDynamicAttribute();
    }

    @Override
    public void adapt() {

        IDynamicPlugin dynamicPlugin = getDynamicPlugin();

        List<IDynamicExtension> allExtensions = dynamicPlugin.getAllExtensions();
        if (allExtensions != null && !allExtensions.isEmpty()) {
            for (IDynamicExtension dynamicExtension : allExtensions) {
                AbstractDynamicAdapter adapter = DynamicAdapterFactory.getInstance().create(dynamicExtension.getTagName(),
                        dynamicExtension, getDynamicId());
                adapter.adapt();
            }
        }

    }

}
