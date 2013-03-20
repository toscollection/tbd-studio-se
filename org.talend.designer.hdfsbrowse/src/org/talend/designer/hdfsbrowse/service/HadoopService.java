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
package org.talend.designer.hdfsbrowse.service;

import java.util.HashSet;
import java.util.Set;

import org.talend.core.classloader.DynamicClassLoader;
import org.talend.core.hadoop.IHadoopService;
import org.talend.designer.hdfsbrowse.manager.HadoopClassLoaderFactory;

/**
 * created by ycbai on 2013-3-14 Detailled comment
 * 
 */
public class HadoopService implements IHadoopService {

    /*
     * (non-Javadoc)
     * 
     * @see org.talend.core.hadoop.IHadoopService#getHadoopJars(java.lang.String, java.lang.String)
     */
    @Override
    public Set<String> getHadoopLibraries(String distribution, String version) {
        Set<String> set = new HashSet<String>();

        ClassLoader classLoader = HadoopClassLoaderFactory.getClassLoader(distribution, version);
        if (classLoader instanceof DynamicClassLoader) {
            set.addAll(((DynamicClassLoader) classLoader).getLibraries());
        }

        return set;
    }

}
