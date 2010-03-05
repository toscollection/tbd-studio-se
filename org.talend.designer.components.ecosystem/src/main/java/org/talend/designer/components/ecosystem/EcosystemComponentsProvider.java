// ============================================================================
//
// Copyright (C) 2006-2010 Talend Inc. - www.talend.com
//
// This source code is available under agreement available at
// %InstallDIR%\features\org.talend.rcp.branding.%PRODUCTNAME%\%PRODUCTNAME%license.txt
//
// You should have received a copy of the agreement
// along with this program; if not, write to Talend SA
// 9 rue Pages 92150 Suresnes, France
//
// ============================================================================
package org.talend.designer.components.ecosystem;

import java.io.File;
import java.io.FileFilter;
import java.io.IOException;

import org.apache.commons.io.FileUtils;
import org.talend.commons.exception.ExceptionHandler;
import org.talend.commons.utils.io.FilesUtils;
import org.talend.core.model.components.AbstractComponentsProvider;

/**
 * Components provider for ecosystem.
 */
public class EcosystemComponentsProvider extends AbstractComponentsProvider {

    /**
     * EcosystemComponentsProvider constructor.
     */
    public EcosystemComponentsProvider() {
    }

    @Override
    public void preComponentsLoad() throws IOException {
        File installFolder = getInstallationFolder();

        // clean folder
        if (installFolder.exists()) {
            FilesUtils.removeFolder(installFolder, true);
        }
        FilesUtils.createFoldersIfNotExists(installFolder.getAbsolutePath(), false);

        File externalComponentsLocation = getExternalComponentsLocation();
        if (externalComponentsLocation != null) {
            if (externalComponentsLocation.exists()) {
                try {
                    copyFolder(externalComponentsLocation, installFolder);
                } catch (Throwable e) {
                    ExceptionHandler.process(e);
                }
            }
        }
    }

    /**
     * DOC hcw Comment method "copyFolder".
     * 
     * @param externalComponentsLocation
     * @param installFolder
     * @throws IOException
     */
    private void copyFolder(File externalComponentsLocation, File installFolder) throws IOException {
        FileFilter folderFilter = new FileFilter() {

            public boolean accept(File file) {
                return file.isDirectory();
            }
        };
        for (File folder : externalComponentsLocation.listFiles(folderFilter)) {
            /*
             * Some components have: <componentName>/<files> . some other have: <folder>/<componentName>/<files> .
             */
            File componentFolder = searchComponentFolder(folder);
            // if we cannot find the component folder, just ignore it
            if (componentFolder != null) {
                File target = new File(installFolder, componentFolder.getName());
                FileUtils.copyDirectory(componentFolder, target);
            }
        }
    }

    /**
     * DOC hcw Comment method "searchComponentFolder".
     * 
     * @param folder
     * @return
     */
    public static File searchComponentFolder(File folder) {
        File componentFolder = null;
        File[] files = folder.listFiles();
        if (files != null) {
            for (File file : files) {
                if (file.isDirectory()) {
                    componentFolder = searchComponentFolder(file);
                    if (componentFolder != null) {
                        break;
                    }
                } else {
                    // assumes that if the folder contains properties file, it is the root folder of component
                    if (file.getName().endsWith(".properties")) { //$NON-NLS-1$
                        componentFolder = folder;
                        break;
                    }
                }
            }
        }
        return componentFolder;
    }

    @Override
    protected File getExternalComponentsLocation() {
        return EcosystemUtils.getComponentFolder();
    }

}
