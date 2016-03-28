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

import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Map;

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

    public static Map<String, Boolean> doSupportMethods(IHDistributionVersion distributionVersion, String... methods) {
        Map<String, Boolean> resultsMap = new HashMap<String, Boolean>();
        if (distributionVersion instanceof DistributionVersion && methods != null && methods.length > 0) {
            HadoopComponent dadoopComponent = ((DistributionVersion) distributionVersion).hadoopComponent;
            if (dadoopComponent != null) {
                for (String m : methods) {
                    try {
                        Method declaredMethod = dadoopComponent.getClass().getDeclaredMethod(m);
                        declaredMethod.setAccessible(true);
                        Object is = declaredMethod.invoke(dadoopComponent);
                        if (is instanceof Boolean) {
                            resultsMap.put(m, ((Boolean) is));
                        }

                    } catch (Exception e) {
                        // ignore
                    }
                }
            }
        }
        return resultsMap;
    }
}
