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
package org.talend.designer.hdfsbrowse.manager;

import java.util.Set;

import org.talend.core.classloader.ClassLoaderFactory;
import org.talend.core.hadoop.version.custom.ECustomVersionGroup;
import org.talend.designer.hdfsbrowse.model.HDFSConnectionBean;

/**
 * DOC ycbai class global comment. Detailled comment
 */
public class HadoopClassLoaderFactory {

    public static ClassLoader getClassLoader(HDFSConnectionBean connectionBean) {
        ClassLoader loader = null;
        if (connectionBean == null) {
            return loader;
        }

        if (connectionBean.isUseCustomVersion()) {
            return getCustomClassLoader(connectionBean);
        }

        String distribution = connectionBean.getDistribution();
        String version = connectionBean.getDfVersion();
        boolean enableKerberos = connectionBean.isEnableKerberos();
        if (distribution == null || version == null) {
            return loader;
        }

        return getClassLoader(distribution, version, enableKerberos);
    }

    public static ClassLoader getClassLoader(String distribution, String version) {
        return getClassLoader(distribution, version, false);
    }

    public static ClassLoader getClassLoader(String distribution, String version, boolean enableKerberos) {
        String index = "HDFS" + ":" + distribution + ":" + version; //$NON-NLS-1$  //$NON-NLS-2$  //$NON-NLS-3$
        if (enableKerberos) {
            index += "?USE_KRB"; //$NON-NLS-1$
        }
        ClassLoader loader = ClassLoaderFactory.getClassLoader(index);
        if (loader == null) {
            loader = HadoopClassLoaderFactory.class.getClassLoader();
        }

        return loader;
    }

    public static ClassLoader getCustomClassLoader(HDFSConnectionBean connectionBean) {
        String hcId = connectionBean.getRelativeHadoopClusterId();
        String index = "HadoopCustomVersion:" + hcId; //$NON-NLS-1$ 
        return ClassLoaderFactory.getCustomClassLoader(index,
                (Set<String>) connectionBean.getAdditionalProperties().get(ECustomVersionGroup.COMMON.getName()));
    }

}
