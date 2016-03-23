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
package org.talend.hadoop.distribution.helper;

import org.talend.core.runtime.hd.IHDistributionVersion;
import org.talend.hadoop.distribution.component.HadoopComponent;
import org.talend.hadoop.distribution.model.DistributionVersion;

/**
 * DOC ggu class global comment. Detailled comment
 */
public final class DistributionHelper {

    public static boolean doSupportService(IHDistributionVersion distributionVersion, String service) {
        if (distributionVersion instanceof DistributionVersion && service != null) {
            HadoopComponent dadoopComponent = ((DistributionVersion) distributionVersion).hadoopComponent;
            if (dadoopComponent != null) {
                try {
                    final Class<?> clazz = Class.forName(service, false, HadoopComponent.class.getClassLoader());
                    return clazz.isInstance(dadoopComponent);
                } catch (ClassNotFoundException e) {
                    // if not found, nothing to do
                }
            }
        }
        return false;
    }
}
