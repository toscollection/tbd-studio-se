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

import org.talend.core.runtime.dynamic.IDynamicConfiguration;
import org.talend.core.runtime.dynamic.IDynamicExtension;

/**
 * DOC cmeng  class global comment. Detailled comment
 */
public abstract class DynamicExtensionAdapter extends AbstractDynamicAdapter {

    public static final String ATTR_ID = "id"; //$NON-NLS-1$

    public DynamicExtensionAdapter(IDynamicExtension dynamicExtension, String id) {
        super(dynamicExtension, id);
    }

    public String getNewExtensionId() {
        return getNewValueByTemplate(ATTR_ID);
    }

    public IDynamicExtension getDynamicExtension() {
        return (IDynamicExtension) getDynamicAttribute();
    }

    @Override
    public void adapt() {
        IDynamicExtension dynamicExtension = getDynamicExtension();

        dynamicExtension.setExtensionId(getNewExtensionId());

        List<IDynamicConfiguration> configurations = dynamicExtension.getConfigurations();
        if (configurations != null && !configurations.isEmpty()) {
            for (IDynamicConfiguration configuration : configurations) {
                AbstractDynamicAdapter adapter = DynamicAdapterFactory.getInstance().create(configuration.getTagName(), configuration,
                        getDynamicId());
                adapter.adapt();
            }
        }
    }

}
