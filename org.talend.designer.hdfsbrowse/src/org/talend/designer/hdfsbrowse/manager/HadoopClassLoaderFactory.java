// ============================================================================
//
// Copyright (C) 2006-2012 Talend Inc. - www.talend.com
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

import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import org.apache.commons.collections.map.MultiKeyMap;
import org.apache.log4j.Logger;
import org.eclipse.core.runtime.FileLocator;
import org.eclipse.core.runtime.Platform;
import org.eclipse.emf.common.util.EMap;
import org.osgi.framework.Bundle;
import org.talend.core.model.metadata.builder.database.HotClassLoader;
import org.talend.core.model.routines.RoutineLibraryMananger;
import org.talend.designer.hdfsbrowse.model.HDFSConnectionBean;
import org.talend.designer.hdfsbrowse.util.EHadoopVersion4Drivers;
import org.talend.librariesmanager.emf.librariesindex.LibrariesIndex;
import org.talend.librariesmanager.model.service.LibrariesIndexManager;

/**
 * DOC ycbai class global comment. Detailled comment
 */
public class HadoopClassLoaderFactory {

    protected static Logger log = Logger.getLogger(RoutineLibraryMananger.class.getName());

    private final static String PATH_SEPARATOR = "/"; //$NON-NLS-1$

    private static MultiKeyMap classLoadersMap = new MultiKeyMap();

    public static ClassLoader getClassLoader(String distribution, String version, List<String> jarPathList) {
        HotClassLoader loader = null;
        boolean supported = EHadoopVersion4Drivers.support(distribution, version);
        if (supported) {
            loader = (HotClassLoader) classLoadersMap.get(distribution, version);
        }
        if (loader == null) {
            loader = new HotClassLoader();
            for (String jarPath : jarPathList) {
                loader.addPath(jarPath);
            }
            if (supported) {
                classLoadersMap.put(distribution, version, loader);
            }
        }

        return loader;
    }

    public static ClassLoader getClassLoader(HDFSConnectionBean connectionBean) {
        if (connectionBean == null) {
            return null;
        }
        LibrariesIndex index = LibrariesIndexManager.getInstance().getIndex();
        EMap<String, String> jarsToRelativePath = index.getJarsToRelativePath();
        Set<String> jarsIndexs = jarsToRelativePath.keySet();
        List<String> jarPathList = new ArrayList<String>();
        String distribution = connectionBean.getDistribution();
        String version = connectionBean.getDfVersion();
        String drivers = connectionBean.getDfDrivers();
        String[] driversArray = drivers.split(";"); //$NON-NLS-1$
        for (String driverName : driversArray) {
            if (jarsIndexs.contains(driverName)) {
                String path = jarsToRelativePath.get(driverName);
                String bundleName = path.substring(0, path.indexOf(PATH_SEPARATOR));
                String entryPath = path.substring(path.indexOf(PATH_SEPARATOR) + 1);
                Bundle bundle = Platform.getBundle(bundleName);
                if (bundle == null) {
                    continue;
                }
                URL entry = bundle.getEntry(entryPath);
                if (entry == null) {
                    continue;
                }
                try {
                    URL fileUrl = FileLocator.toFileURL(entry);
                    jarPathList.add(fileUrl.getFile());
                } catch (Exception e) {
                    log.warn("Cannot find: " + bundleName + path);
                    continue;
                }
            }
        }
        return getClassLoader(distribution, version, jarPathList);
    }

}
