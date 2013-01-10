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

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.collections.map.MultiKeyMap;
import org.apache.log4j.Logger;
import org.eclipse.core.resources.IProject;
import org.eclipse.core.runtime.NullProgressMonitor;
import org.talend.commons.ui.runtime.exception.ExceptionHandler;
import org.talend.core.GlobalServiceRegister;
import org.talend.core.ILibraryManagerService;
import org.talend.core.model.general.Project;
import org.talend.core.model.metadata.builder.database.HotClassLoader;
import org.talend.core.repository.model.ResourceModelUtils;
import org.talend.designer.hdfsbrowse.model.HDFSConnectionBean;
import org.talend.repository.ProjectManager;

/**
 * DOC ycbai class global comment. Detailled comment
 */
public class HadoopClassLoaderFactory {

    protected static Logger log = Logger.getLogger(HadoopClassLoaderFactory.class.getName());

    private final static String PATH_SEPARATOR = "/"; //$NON-NLS-1$

    private static MultiKeyMap classLoadersMap = new MultiKeyMap();

    public static ClassLoader getClassLoader(HDFSConnectionBean connectionBean) {
        HotClassLoader loader = null;
        if (connectionBean == null) {
            return loader;
        }

        String distribution = connectionBean.getDistribution();
        String version = connectionBean.getDfVersion();
        if (distribution == null || version == null) {
            return loader;
        }

        loader = (HotClassLoader) classLoadersMap.get(distribution, version);
        if (loader == null) {
            loader = new HotClassLoader();
            String drivers = connectionBean.getDfDrivers();
            String[] driversArray = drivers.split(";"); //$NON-NLS-1$
            List<String> jarPathList = retrieveJarPaths(driversArray);
            for (String jarPath : jarPathList) {
                loader.addPath(jarPath);
            }
            classLoadersMap.put(distribution, version, loader);
        }

        return loader;
    }

    private static List<String> retrieveJarPaths(String[] driversArray) {
        List<String> jarPathList = new ArrayList<String>();
        if (driversArray == null || driversArray.length == 0) {
            return jarPathList;
        }

        ILibraryManagerService librairesManagerService = (ILibraryManagerService) GlobalServiceRegister.getDefault().getService(
                ILibraryManagerService.class);
        String libPath = getLibPath();
        for (String driverName : driversArray) {
            String jarPath = libPath + driverName;
            if (!new File(jarPath).exists()) {
                librairesManagerService.retrieve(driverName, libPath, new NullProgressMonitor());
            }
            jarPathList.add(jarPath);
        }

        return jarPathList;
    }

    private static String getLibPath() {
        Project project = ProjectManager.getInstance().getCurrentProject();
        IProject physProject;
        String tmpFolder = System.getProperty("user.dir"); //$NON-NLS-1$
        try {
            physProject = ResourceModelUtils.getProject(project);
            tmpFolder = physProject.getFolder("temp").getLocation().toPortableString(); //$NON-NLS-1$
        } catch (Exception e) {
            ExceptionHandler.process(e);
        }
        tmpFolder = tmpFolder + "/hadoop"; //$NON-NLS-1$
        File file = new File(tmpFolder);
        if (!file.exists()) {
            file.mkdirs();
        }
        return tmpFolder + PATH_SEPARATOR;
    }

}
