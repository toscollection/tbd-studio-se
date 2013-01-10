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
package org.talend.oozie.scheduler.jobdeployer;

import java.io.File;
import java.io.FileFilter;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.Path;
import org.eclipse.core.runtime.SubProgressMonitor;
import org.talend.core.CorePlugin;
import org.talend.core.model.process.IProcess2;
import org.talend.core.model.properties.ProcessItem;
import org.talend.core.prefs.ITalendCorePrefConstants;
import org.talend.designer.runprocess.IProcessor;
import org.talend.designer.runprocess.ProcessorException;
import org.talend.designer.runprocess.ProcessorUtilities;
import org.talend.oozie.scheduler.exceptions.OozieJobDeployException;
import org.talend.repository.documentation.ArchiveFileExportOperationFullPath;
import org.talend.repository.documentation.ExportFileResource;
import org.talend.repository.ui.utils.ZipToFile;
import org.talend.repository.ui.wizards.exportjob.JavaJobScriptsExportWSWizardPage.JobExportType;
import org.talend.repository.ui.wizards.exportjob.scriptsmanager.JobScriptsManager;
import org.talend.repository.ui.wizards.exportjob.scriptsmanager.JobScriptsManager.ExportChoice;
import org.talend.repository.ui.wizards.exportjob.scriptsmanager.JobScriptsManagerFactory;

/**
 */
public class OozieJobDeployer {

    public static void deployJob(IProcess2 process, IProgressMonitor progressMonitor) throws OozieJobDeployException {
        String archive;
        try {
            archive = buildExportZip(process, progressMonitor);
        } catch (ProcessorException e) {
            throw new OozieJobDeployException("Can not build a zip file!", e);
        }
        unzipAndUploadProcess(process, archive);
    }

    /**
     * 
     * @param archive
     * @throws OozieJobDeployException
     * @throws Exception
     */
    private static void unzipAndUploadProcess(IProcess2 process, String archiveZipFileStr) throws OozieJobDeployException {
        String tempUnzipFolder = unzipProcess(process, archiveZipFileStr);
        uploadProcess(process, tempUnzipFolder);
        deleteProcess(tempUnzipFolder);
    }

    private static String unzipProcess(IProcess2 process, String archiveZipFileStr) throws OozieJobDeployException {
        String jobName = process.getLabel();
        String tempFolder = null;
        if (archiveZipFileStr != null && !"".equals(archiveZipFileStr)) {
            File file = new File(archiveZipFileStr);
            tempFolder = file.getParentFile().getPath() + File.separator + jobName;
            try {
                ZipToFile.unZipFile(archiveZipFileStr, tempFolder);
            } catch (Exception e) {
                throw new OozieJobDeployException("Can not unzip a file!", e);
            }
        }
        return tempFolder;
    }

    private static void uploadProcess(IProcess2 process, String unzipDir) throws OozieJobDeployException {
        String appPathOnHDFSParent = (String) process.getElementParameter("HADOOP_APP_PATH").getValue();

        org.apache.hadoop.fs.FileSystem fs = null;

        org.apache.hadoop.conf.Configuration config = new org.apache.hadoop.conf.Configuration();

        config.set(
                "fs.default.name",
                CorePlugin.getDefault().getPreferenceStore()
                        .getString(ITalendCorePrefConstants.OOZIE_SHCEDULER_NAME_NODE_ENDPOINT));

        String userName = CorePlugin.getDefault().getPreferenceStore()
                .getString(ITalendCorePrefConstants.OOZIE_SCHEDULER_USER_NAME);
        if (StringUtils.isEmpty(userName)) {
            try {
                fs = org.apache.hadoop.fs.FileSystem.get(config);
            } catch (IOException e) {
                throw new OozieJobDeployException("Can not acess Hadoop File System with default name!", e);
            }
        } else {
            try {
                fs = org.apache.hadoop.fs.FileSystem.get(new java.net.URI(config.get("fs.default.name")), config, userName);
            } catch (IOException e) {
                throw new OozieJobDeployException("Can not access Hadoop File System with user "+userName+"!", e);
            } catch (InterruptedException e) {
                throw new OozieJobDeployException("Can not access Hadoop File System!", e);
            } catch (URISyntaxException e) {
                throw new OozieJobDeployException("The name node end point is not valid!", e);
            }
        }
        

        // /user/hdp/etl/talend/MarvinJob_0.1/MarvinJob
        try {
            // if something has been uploaded already before in the same folder, delete the old content
            if (fs.exists(new org.apache.hadoop.fs.Path(appPathOnHDFSParent + "/lib"))) {
                fs.delete(new org.apache.hadoop.fs.Path(appPathOnHDFSParent + "/lib"), true);
            }
            
            fs.mkdirs(new org.apache.hadoop.fs.Path(appPathOnHDFSParent + "/lib"));
        } catch (IOException e) {
            throw new OozieJobDeployException("Can not make a directory in HDFS!", e);
        }

        File unzipDirFile = new File(unzipDir);
        File[] files = unzipDirFile.listFiles(new FileFilter() {

            @Override
            public boolean accept(File file) {
                if (file != null && file.isDirectory()) {
                    return true;
                }
                return false;
            }
        });

        if (files != null && files.length > 0) {
            for (int i = 0; i < files.length; i++) {
                File file = files[i];
                File[] tempFiles = file.listFiles();
                if (tempFiles != null && tempFiles.length > 0) {
                    for (int j = 0; j < tempFiles.length; j++) {
                        File tempFile = tempFiles[j];
                        org.apache.hadoop.fs.Path srcPath = new org.apache.hadoop.fs.Path(tempFile.getAbsolutePath());
                        org.apache.hadoop.fs.Path distpath = new org.apache.hadoop.fs.Path(appPathOnHDFSParent + "/lib");
                        try {
                            fs.copyFromLocalFile(false, true, srcPath, distpath);
                        } catch (IOException e) {
                            throw new OozieJobDeployException("The local file can not upload to Hadoop HDFS!", e);
                        }
                    }
                }
            }
        }
    }

    private static void deleteProcess(String zipFile) {
        new File(zipFile).delete();
    }

    private static String buildExportZip(IProcess2 process, IProgressMonitor progressMonitor) throws ProcessorException {
        Map<ExportChoice, Object> exportChoiceMap = JobScriptsManagerFactory.getDefaultExportChoiceMap();
        exportChoiceMap.put(ExportChoice.needLauncher, false);
        exportChoiceMap.put(ExportChoice.needJobItem, false);
        exportChoiceMap.put(ExportChoice.needJobScript, true);
        exportChoiceMap.put(ExportChoice.needSourceCode, false);
        exportChoiceMap.put(ExportChoice.needContext, true);

        // IProcess2 process = findProcessFromRepository(processItem.getProperty().getId(),
        // processItem.getProperty().getVersion());

        ProcessItem processItem = (ProcessItem) process.getProperty().getItem();

        String processName = processItem.getProperty().getLabel();

        ExportFileResource fileResource = new ExportFileResource(processItem, processName);

        ExportFileResource[] exportFileResources = new ExportFileResource[] { fileResource };

        if (progressMonitor.isCanceled()) {
            throw new ProcessorException(new InterruptedException());
        }
        JobScriptsManager jobScriptsManager = JobScriptsManagerFactory.createManagerInstance(exportChoiceMap, processItem
                .getProcess().getDefaultContext(), JobScriptsManager.ALL_ENVIRONMENTS, IProcessor.NO_STATISTICS,
                IProcessor.NO_TRACES, JobExportType.POJO);
        String codeOptions = null;
        List<ExportFileResource> exportResources = jobScriptsManager.getExportResources(exportFileResources, codeOptions);

        if (progressMonitor.isCanceled()) {
            throw new ProcessorException(new InterruptedException());
        }

        final String archiveFilePath = Path.fromOSString(CorePlugin.getDefault().getPreferenceStore()
                .getString(ITalendCorePrefConstants.FILE_PATH_TEMP))
                + "/export.zip"; //$NON-NLS-1$
        final ArchiveFileExportOperationFullPath exporterOperation = new ArchiveFileExportOperationFullPath(exportResources,
                archiveFilePath);
        exporterOperation.setCreateLeadupStructure(true);
        exporterOperation.setUseCompression(true);

        final IProgressMonitor subProgressMonitor = new SubProgressMonitor(progressMonitor, 1);

        if (progressMonitor.isCanceled()) {
            throw new ProcessorException(new InterruptedException());
        }

        try {
            exporterOperation.run(subProgressMonitor);
        } catch (InvocationTargetException e) {
            throw new ProcessorException(e);
        } catch (InterruptedException e) {
            throw new ProcessorException(e);
        }

        // path can like name/name
        jobScriptsManager.deleteTempFiles();
        ProcessorUtilities.resetExportConfig();
        return archiveFilePath;
    }
}
