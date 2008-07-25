// ============================================================================
//
// Copyright (C) 2006-2007 Talend Inc. - www.talend.com
//
// This source code is available under agreement available at
// %InstallDIR%\features\org.talend.rcp.branding.%PRODUCTNAME%\%PRODUCTNAME%license.txt
//
// You should have received a copy of the agreement
// along with this program; if not, write to Talend SA
// 9 rue Pages 92150 Suresnes, France
//
// ============================================================================
package org.talend.designer.components.ecosystem.ui.actions;

import java.io.File;
import java.net.URL;
import java.util.List;

import org.apache.commons.io.FileUtils;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.Status;
import org.eclipse.core.runtime.SubMonitor;
import org.eclipse.core.runtime.jobs.IJobChangeEvent;
import org.eclipse.core.runtime.jobs.Job;
import org.eclipse.core.runtime.jobs.JobChangeAdapter;
import org.eclipse.jface.action.IAction;
import org.eclipse.jface.viewers.ISelection;
import org.eclipse.swt.widgets.Display;
import org.eclipse.ui.IViewActionDelegate;
import org.eclipse.ui.IViewPart;
import org.talend.commons.exception.ExceptionHandler;
import org.talend.core.GlobalServiceRegister;
import org.talend.designer.codegen.ICodeGeneratorService;
import org.talend.designer.components.ecosystem.EcosystemConstants;
import org.talend.designer.components.ecosystem.EcosystemUtils;
import org.talend.designer.components.ecosystem.jobs.ComponentDownloader;
import org.talend.designer.components.ecosystem.jobs.ComponentInstaller;
import org.talend.designer.components.ecosystem.jobs.DownloadListener;
import org.talend.designer.components.ecosystem.model.ComponentExtension;
import org.talend.designer.components.ecosystem.ui.views.EcosystemView;

/**
 * View action for downloading components.
 */
public class DownloadComponenentsAction implements IViewActionDelegate {

    // private static final String SET_FOLDER_TITLE =
    // Messages.getString("DownloadComponenentsAction.SetUserFolder.Title"); //$NON-NLS-1$

    // private static final String SET_FOLDER_MESSAGE =
    // Messages.getString("DownloadComponenentsAction.SetUserFolder.Message"); //$NON-NLS-1$

    private EcosystemView fView;

    private int fExtensionDownloaded;

    public void init(IViewPart view) {
        fView = (EcosystemView) view;
    }

    public void run(final IAction action) {
        // check if user have set the folder in preference page
        // if (EcosystemUtils.getUserComponentFolder() == null) {
        // MessageDialog.openError(fView.getSite().getShell(), SET_FOLDER_TITLE, SET_FOLDER_MESSAGE);
        // EcosystemUtils.showComponentPreferencePage(fView.getSite().getShell());
        // return;
        // }

        // avoid starting multiple action at the same time.
        action.setEnabled(false);
        try {
            Job job = new DownloadJob(fView.getSelectedExtensions());
            fExtensionDownloaded = 0;
            job.addJobChangeListener(new JobChangeAdapter() {

                @Override
                public void done(final IJobChangeEvent event) {

                    Display.getDefault().syncExec(new Runnable() {

                        public void run() {
                            updateUI(action, event);
                        }
                    });
                }
            });
            EcosystemUtils.scheduleUserJob(job);

        } catch (Throwable e) {
            ExceptionHandler.process(e);
        }
    }

    /**
     * Update ui after job finished.
     * 
     * @param action
     * @param event
     */
    private void updateUI(final IAction action, final IJobChangeEvent event) {
        // activate action again after job finished
        action.setEnabled(true);
        if (fExtensionDownloaded > 0) {
            fView.refresh(); // refresh table
            EcosystemUtils.reloadComponents(); // refresh palette
            fView.saveToFile();

            // Start Code Generation Init
            ICodeGeneratorService codeGenService = (ICodeGeneratorService) GlobalServiceRegister.getDefault().getService(
                    ICodeGeneratorService.class);
            codeGenService.initializeTemplates();
        }
    }

    /**
     * Notify after download complete.
     * 
     * @param extension
     */
    void extensionDownloadCompleted(ComponentExtension extension) {
        fView.addInstalledExtension(extension);
        fExtensionDownloaded++;
    }

    public void selectionChanged(IAction action, ISelection selection) {
    }

    class DownloadJob extends Job implements DownloadListener {

        private IProgressMonitor fMonitor = null;

        private String fProgressLabel;

        private int fBytesDownloaded;

        private List<ComponentExtension> fExtensions;

        public DownloadJob(List<ComponentExtension> extensions) {
            super(EcosystemConstants.DOWNLOAD_TASK_TITLE);
            fExtensions = extensions;
        }

        @Override
        protected IStatus run(IProgressMonitor monitor) {
            SubMonitor progress = SubMonitor.convert(monitor, fExtensions.size() * 10 + 5);
            progress.setTaskName(this.getName());
            for (ComponentExtension extension : fExtensions) {
                if (progress.isCanceled()) {
                    return Status.CANCEL_STATUS;
                }
                fMonitor = progress.newChild(10);
                downloadExtension(extension, fMonitor);
            }
            progress.setTaskName(EcosystemConstants.RELOAD_PALETTE);
            // progress.done();
            return Status.OK_STATUS;
        }

        private void downloadExtension(ComponentExtension extension, IProgressMonitor monitor) {

            // get the latest revision url
            String componentUrl = extension.getLatestRevision().getUrl();
            monitor.setTaskName(EcosystemConstants.DOWNLOAD_TASK_NAME + componentUrl);
            String targetFolder = EcosystemUtils.getComponentFolder().getAbsolutePath();
            try {
                String fileName = extension.getLatestRevision().getFileName();
                // fileName = extension.getLatestRevision().getFileName();
                File localZipFile = new File(targetFolder, fileName);

                if (extension.getInstalledLocation() != null && extension.getInstalledRevision() != null) {
                    // if already install the latest revision, ignore
                    if (extension.getInstalledRevision().getName().equals(extension.getLatestRevision().getName())) {
                        if (localZipFile.exists() && checkIfExisted(extension.getInstalledLocation())) {
                            monitor.done();
                            return;
                        }
                    } else {
                        // before installing the new revision, delete the older revision that has been installed
                        FileUtils.deleteDirectory(new File(extension.getInstalledLocation()));
                        extension.setInstalledLocation(null);
                        extension.setInstalledRevision(null);
                        fView.removeInstalledExtension(extension);
                    }
                }

                URL url = new URL(componentUrl);

                monitor.setTaskName(EcosystemConstants.DOWNLOAD_TASK_NAME + url.toString());
                ComponentDownloader downloader = new ComponentDownloader();
                downloader.addDownloadListener(this);
                // block until download complete
                downloader.download(url, localZipFile);

                // check if the job is cancelled
                if (!monitor.isCanceled()) {
                    File installedLocation = ComponentInstaller.unzip(localZipFile.getAbsolutePath(), targetFolder);
                    // update extesion status
                    extension.setInstalledRevision(extension.getLatestRevision());
                    extension.setInstalledLocation(installedLocation.getAbsolutePath());
                    monitor.done();
                    extensionDownloadCompleted(extension);
                }
                // the component zip file
                // localZipFile.delete();
            } catch (Exception e) {
                ExceptionHandler.process(e);
            }
        }

        /**
         * Check if the component folder really exist, as the user may delete the folder from filesystem.
         * 
         * @param installedLocation
         * @return
         */
        private boolean checkIfExisted(String installedLocation) {
            try {
                File dir = new File(installedLocation);
                if (dir.exists()) {
                    return true;
                }
            } catch (Throwable e) {
                // do nothing;
            }
            return false;
        }

        public void downloadComplete() {
        }

        public void downloadProgress(ComponentDownloader downloader, int bytesRead) {
            if (fMonitor.isCanceled()) {
                // cancel download
                downloader.setCancel(true);
                return;
            }
            fBytesDownloaded += bytesRead;
            fMonitor.setTaskName(toKbFormat(fBytesDownloaded) + fProgressLabel);
            fMonitor.worked(bytesRead);
        }

        public void downloadStart(int totalSize) {
            fProgressLabel = "/" + toKbFormat(totalSize);
            fBytesDownloaded = 0;
            fMonitor.beginTask("0 KB" + fProgressLabel, totalSize);
        }

        private String toKbFormat(int size) {
            return String.format("%1$s KB", size >> 10); //$NON-NLS-1$
        }
    }
}
