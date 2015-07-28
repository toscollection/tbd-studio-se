// ============================================================================
//
// Copyright (C) 2006-2015 Talend Inc. - www.talend.com
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
import java.lang.reflect.InvocationTargetException;
import java.util.List;
import java.util.Map;

import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.Path;
import org.eclipse.core.runtime.SubProgressMonitor;
import org.talend.core.CorePlugin;
import org.talend.core.model.process.IProcess2;
import org.talend.core.model.properties.ProcessItem;
import org.talend.core.prefs.ITalendCorePrefConstants;
import org.talend.core.ui.export.ArchiveFileExportOperationFullPath;
import org.talend.designer.core.model.components.EOozieParameterName;
import org.talend.designer.hdfsbrowse.manager.HadoopServerUtil;
import org.talend.designer.hdfsbrowse.model.HDFSConnectionBean;
import org.talend.designer.hdfsbrowse.reflection.HadoopClassConstants;
import org.talend.designer.hdfsbrowse.reflection.HadoopReflection;
import org.talend.designer.runprocess.IProcessor;
import org.talend.designer.runprocess.ProcessorException;
import org.talend.designer.runprocess.ProcessorUtilities;
import org.talend.oozie.scheduler.exceptions.OozieJobDeployException;
import org.talend.oozie.scheduler.utils.OozieClassLoaderFactory;
import org.talend.oozie.scheduler.utils.TOozieParamUtils;
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
        String appPathOnHDFSParent = (String) process.getElementParameter(EOozieParameterName.HADOOP_APP_PATH.getName())
                .getValue();
        // Object fs = null;
        ClassLoader oldClassLoaderLoader = Thread.currentThread().getContextClassLoader();
        HDFSConnectionBean connectionBean = TOozieParamUtils.getHDFSConnectionBean();
        try {
            ClassLoader classLoader = OozieClassLoaderFactory.getClassLoader(connectionBean);
            Thread.currentThread().setContextClassLoader(classLoader);

            // Object config = HadoopReflection.newInstance(HadoopClassConstants.CONFIGURATION, classLoader);
            //
            // HadoopReflection.invokeMethod(
            // config,
            // "set",
            // new Object[] { "fs.default.name",
            // TOozieParamUtils.getParamValue(ITalendCorePrefConstants.OOZIE_SHCEDULER_NAME_NODE_ENDPOINT) });
            //
            // String userName = TOozieParamUtils.getUserNameForHadoop();
            // if (StringUtils.isEmpty(userName)) {
            // HadoopReflection.invokeStaticMethod(HadoopClassConstants.FILESYSTEM, "get", new Object[] { config },
            // classLoader);
            // } else {
            // String nnURI = (String) HadoopReflection.invokeMethod(config, "get", new Object[] { "fs.default.name" });
            // fs = HadoopReflection.invokeStaticMethod(HadoopClassConstants.FILESYSTEM, "get", new Object[] {
            // new java.net.URI(nnURI), config, userName }, classLoader);
            // }

            Object fs = HadoopServerUtil.getDFS(connectionBean, classLoader);

            // if something has been uploaded already before in the same folder, delete the old content
            Object libPath = HadoopReflection.newInstance(HadoopClassConstants.PATH,
                    new Object[] { appPathOnHDFSParent + "/lib" }, classLoader);
            if ((Boolean) HadoopReflection.invokeMethod(fs, "exists", new Object[] { libPath })) {
                HadoopReflection.invokeMethod(fs, "delete", new Object[] { libPath });
            }
            HadoopReflection.invokeMethod(fs, "mkdirs", new Object[] { libPath });

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
                for (File file : files) {
                    File[] tempFiles = file.listFiles();
                    if (tempFiles != null && tempFiles.length > 0) {
                        for (File tempFile : tempFiles) {
                            String destPath = appPathOnHDFSParent + "/lib/" + tempFile.getName(); //$NON-NLS-1$
                            HadoopServerUtil.upload(tempFile, destPath, fs, classLoader);
                        }
                    }
                }
            }
        } catch (Exception e) {
            throw new OozieJobDeployException("The local file can not upload to Hadoop HDFS!", e);
        } finally {
            Thread.currentThread().setContextClassLoader(oldClassLoaderLoader);
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
