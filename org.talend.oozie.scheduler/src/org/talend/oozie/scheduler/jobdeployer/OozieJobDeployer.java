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
package org.talend.oozie.scheduler.jobdeployer;

import java.io.File;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Map;

import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.Path;
import org.eclipse.core.runtime.SubProgressMonitor;
import org.talend.core.CorePlugin;
import org.talend.core.model.process.IProcess2;
import org.talend.core.model.properties.ProcessItem;
import org.talend.core.model.utils.JavaResourcesHelper;
import org.talend.core.prefs.ITalendCorePrefConstants;
import org.talend.designer.runprocess.IProcessor;
import org.talend.designer.runprocess.ProcessorException;
import org.talend.designer.runprocess.ProcessorUtilities;
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

    public static void deployJob(IProcess2 process, IProgressMonitor progressMonitor) throws Exception {
        String archive = buildExportZip(process, progressMonitor);
        unzipAndUploadProcess(process, archive);
    }

    /**
     * 
     * @param archive
     * @throws Exception
     */
    private static void unzipAndUploadProcess(IProcess2 process, String archiveZipFileStr) throws Exception {
        String tempUnzipFolder = unzipProcess(process, archiveZipFileStr);
        uploadProcess(process, tempUnzipFolder);
        deleteProcess(tempUnzipFolder);
    }

    private static String unzipProcess(IProcess2 process, String archiveZipFileStr) throws Exception {
        String jobName = process.getLabel();
        String tempFolder = null;
        if (archiveZipFileStr != null && !"".equals(archiveZipFileStr)) {
            File file = new File(archiveZipFileStr);
            tempFolder = file.getParentFile().getPath() + File.separator + jobName;
            ZipToFile.unZipFile(archiveZipFileStr, tempFolder);
        }
        return tempFolder;
    }

    private static void uploadProcess(IProcess2 process, String tempFolder) throws Exception {
        int tos_count_tHDFSPut_1 = 0;

        int nb_file_tHDFSPut_1 = 0;
        int nb_success_tHDFSPut_1 = 0;

        String appPathOnHDFSParent = (String) process.getElementParameter("HADOOP_APP_PATH").getValue();
        String jobFolderName = JavaResourcesHelper.getJobFolderName(process.getLabel(), process.getVersion()) + "."
                + process.getLabel();

        org.apache.hadoop.fs.FileSystem fs = null;

        org.apache.hadoop.conf.Configuration conf_tHDFSPut_1 = new org.apache.hadoop.conf.Configuration();

        conf_tHDFSPut_1.set(
                "fs.default.name",
                CorePlugin.getDefault().getPreferenceStore()
                        .getString(ITalendCorePrefConstants.OOZIE_SHCEDULER_NAME_NODE_ENDPOINT));

        String userName = "hdp";
        if (userName == null || "".equals(userName)) {
            fs = org.apache.hadoop.fs.FileSystem.get(conf_tHDFSPut_1);
        } else {
            fs = org.apache.hadoop.fs.FileSystem.get(new java.net.URI(conf_tHDFSPut_1.get("fs.default.name")), conf_tHDFSPut_1,
                    userName);
        }
        // /user/hdp/etl/talend/MarvinJob_0.1/MarvinJob
        fs.mkdirs(new org.apache.hadoop.fs.Path(appPathOnHDFSParent));
        java.util.List<String> msg_tHDFSPut_1 = new java.util.ArrayList<String>();
        java.util.List<java.util.Map<String, String>> list_tHDFSPut_1 = new java.util.ArrayList<java.util.Map<String, String>>();
        java.util.Map<String, String> map_tHDFSPut_1_0 = new java.util.HashMap<String, String>();
        map_tHDFSPut_1_0.put("*.*", "");
        list_tHDFSPut_1.add(map_tHDFSPut_1_0);
        String localdir = tempFolder;
        for (java.util.Map<String, String> map_tHDFSPut_1 : list_tHDFSPut_1) {

            java.util.Set<String> keySet_tHDFSPut_1 = map_tHDFSPut_1.keySet();
            for (String key_tHDFSPut_1 : keySet_tHDFSPut_1) {
                String tempdir_tHDFSPut_1 = localdir;
                String filemask_tHDFSPut_1 = key_tHDFSPut_1;
                String dir_tHDFSPut_1 = null;
                String mask_tHDFSPut_1 = filemask_tHDFSPut_1.replaceAll("\\\\", "/");
                int i_tHDFSPut_1 = mask_tHDFSPut_1.lastIndexOf('/');
                if (i_tHDFSPut_1 != -1) {
                    dir_tHDFSPut_1 = mask_tHDFSPut_1.substring(0, i_tHDFSPut_1);
                    mask_tHDFSPut_1 = mask_tHDFSPut_1.substring(i_tHDFSPut_1 + 1);
                }
                if (dir_tHDFSPut_1 != null && !"".equals(dir_tHDFSPut_1))
                    tempdir_tHDFSPut_1 = tempdir_tHDFSPut_1 + "/" + dir_tHDFSPut_1;
                mask_tHDFSPut_1 = mask_tHDFSPut_1.replaceAll("\\.", "\\\\.").replaceAll("\\*", ".*");
                final String finalMask_tHDFSPut_1 = mask_tHDFSPut_1;
                java.io.File[] listings_tHDFSPut_1 = null;
                java.io.File file_tHDFSPut_1 = new java.io.File(tempdir_tHDFSPut_1);
                if (file_tHDFSPut_1.isDirectory()) {
                    listings_tHDFSPut_1 = file_tHDFSPut_1.listFiles(new java.io.FileFilter() {

                        public boolean accept(java.io.File pathname) {
                            boolean result = false;
                            if (pathname != null && pathname.isFile()) {
                                result = java.util.regex.Pattern.compile(finalMask_tHDFSPut_1).matcher(pathname.getName()).find();
                            }
                            return true;
                        }
                    });
                }
                if (listings_tHDFSPut_1 == null || listings_tHDFSPut_1.length <= 0) {
                    System.err.println("No match file(" + key_tHDFSPut_1 + ") exist!");
                } else {
                    String localFilePath_tHDFSPut_1 = "";
                    String hdfsFilePath_tHDFSPut_1 = "";
                    for (int m_tHDFSPut_1 = 0; m_tHDFSPut_1 < listings_tHDFSPut_1.length; m_tHDFSPut_1++) {
                        String listFileName = listings_tHDFSPut_1[m_tHDFSPut_1].getName();
                        // listFileName.matches(mask_tHDFSPut_1)
                        if (true) {
                            localFilePath_tHDFSPut_1 = listings_tHDFSPut_1[m_tHDFSPut_1].getAbsolutePath();
                            // hdfsFilePath_tHDFSPut_1 = "/user/hdp/etl/talend/MarvinJob_0.1/MarvinJob" + "/"
                            // + map_tHDFSPut_1.get(key_tHDFSPut_1);
                            // hdfsFilePath_tHDFSPut_1 = appPathOnHDFSParent + "/" + jobFolderName + "/"
                            // + map_tHDFSPut_1.get(key_tHDFSPut_1);
                            hdfsFilePath_tHDFSPut_1 = appPathOnHDFSParent + "/" + jobFolderName + "/" + listFileName;
                            try {
                                fs.copyFromLocalFile(false, true, new org.apache.hadoop.fs.Path(localFilePath_tHDFSPut_1),
                                        new org.apache.hadoop.fs.Path(hdfsFilePath_tHDFSPut_1));
                                // add info to list will return
                                msg_tHDFSPut_1.add("file: " + listings_tHDFSPut_1[m_tHDFSPut_1].getAbsolutePath() + ", size: "
                                        + listings_tHDFSPut_1[m_tHDFSPut_1].length() + " bytes upload successfully");

                                nb_success_tHDFSPut_1++;
                            } catch (java.io.IOException e) {

                                throw (e);

                            }
                            nb_file_tHDFSPut_1++;
                        }
                    }
                }
            }

            tos_count_tHDFSPut_1++;
        }

    }

    private static void deleteProcess(String zipFile) throws IOException, InterruptedException, URISyntaxException {
        new File(zipFile).delete();
    }

    private static String buildExportZip(IProcess2 process, IProgressMonitor progressMonitor) throws ProcessorException {
        Map<ExportChoice, Object> exportChoiceMap = JobScriptsManagerFactory.getDefaultExportChoiceMap();
        exportChoiceMap.put(ExportChoice.needLauncher, false);
        exportChoiceMap.put(ExportChoice.needJobItem, false);
        exportChoiceMap.put(ExportChoice.needJobScript, true);
        exportChoiceMap.put(ExportChoice.needSourceCode, false);

        // disable for now, to review later
        exportChoiceMap.put(ExportChoice.needContext, false);

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

        List<ExportFileResource> exportResources = jobScriptsManager.getExportResources(exportFileResources, null);

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
