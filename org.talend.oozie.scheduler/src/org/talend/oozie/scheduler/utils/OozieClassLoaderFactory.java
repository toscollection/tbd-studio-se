// ============================================================================
//
// Copyright (C) 2006-2013 Talend Inc. - www.talend.com
//
// This source code is available under agreement available at
// %InstallDIR%\features\org.talend.rcp.branding.%PRODUCTNAME%\%PRODUCTNAME%license.txt
//
// You should have received a copy of the agreement
// along with this program; if not, write to Talend SA
// 9 rue Pages 92150 Suresnes, France
//
// ============================================================================
package org.talend.oozie.scheduler.utils;

import org.apache.commons.lang.StringUtils;
import org.talend.core.classloader.ClassLoaderFactory;

/**
 * DOC ycbai class global comment. Detailled comment
 */
public class OozieClassLoaderFactory {

    public static ClassLoader getClassLoader() {
        ClassLoader classLoader = null;

        // TODO: Try to get distribution and version from user configuration after oozie supports multi-distributions.
        String distribution = "HORTONWORKS"; //$NON-NLS-1$ 
        String version = "HDP_1_0"; //$NON-NLS-1$ 
        if (StringUtils.isNotEmpty(distribution) && StringUtils.isNotEmpty(version)) {
            String index = "OOZIE" + ":" + distribution + ":" + version; //$NON-NLS-1$  //$NON-NLS-2$  //$NON-NLS-3$
            classLoader = ClassLoaderFactory.getClassLoader(index);
        }
        if (classLoader == null) {
            classLoader = OozieClassLoaderFactory.class.getClassLoader();
        }

        return classLoader;
    }

}
