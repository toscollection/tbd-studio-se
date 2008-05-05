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

import java.util.List;

import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.Status;
import org.eclipse.core.runtime.jobs.IJobChangeEvent;
import org.eclipse.core.runtime.jobs.Job;
import org.eclipse.core.runtime.jobs.JobChangeAdapter;
import org.eclipse.jface.action.IAction;
import org.eclipse.jface.viewers.ISelection;
import org.eclipse.swt.widgets.Display;
import org.eclipse.ui.IViewActionDelegate;
import org.eclipse.ui.IViewPart;
import org.talend.commons.exception.ExceptionHandler;
import org.talend.designer.components.ecosystem.EcosystemUtils;
import org.talend.designer.components.ecosystem.jobs.ComponentSearcher;
import org.talend.designer.components.ecosystem.model.ComponentExtension;
import org.talend.designer.components.ecosystem.ui.views.EcosystemView;

/**
 * View action for refresh components lists in table viewer.
 */
public class RefreshComponenentsAction implements IViewActionDelegate {

    private EcosystemView fView;

    public void init(IViewPart view) {
        fView = (EcosystemView) view;
    }

    public void run(final IAction action) {
        // avoid starting multiple action at the same time
        action.setEnabled(false);

        try {
            final RefreshJob job = new RefreshJob();
            job.addJobChangeListener(new JobChangeAdapter() {

                @Override
                public void done(final IJobChangeEvent event) {

                    Display.getDefault().syncExec(new Runnable() {

                        public void run() {
                            updateUI(action, job, event);
                        }
                    });
                }
            });
            EcosystemUtils.scheduleUserJob(job);

        } catch (Exception e) {
            ExceptionHandler.process(e);

        }

    }

    public void selectionChanged(IAction action, ISelection selection) {
    }

    /**
     * Update ui after job finished.
     * 
     * @param action
     * @param job
     * @param event
     */
    private void updateUI(final IAction action, final RefreshJob job, final IJobChangeEvent event) {
        // activate action again after job finished
        action.setEnabled(true);

        if (event.getResult().isOK()) {
            // update ecosystem view
            List<ComponentExtension> extensions = job.getAvailableExtensions();
            fView.updateAvailableExtensions(extensions);
        }
    }

    class RefreshJob extends Job {

        public RefreshJob() {
            super(EcosystemView.FIND_EXTENSIONS_MSG);
        }

        private List<ComponentExtension> fAvailableExtensions;

        @Override
        protected IStatus run(IProgressMonitor monitor) {
            if (monitor.isCanceled()) {
                return Status.CANCEL_STATUS;
            }

            monitor.beginTask(EcosystemView.FIND_EXTENSIONS_MSG, IProgressMonitor.UNKNOWN);

            try {
                fAvailableExtensions = ComponentSearcher.getAvailableComponentExtensions(EcosystemUtils.getCurrentTosVersion(),
                        EcosystemUtils.getCurrentLanguage());
            } catch (Throwable e) {
                ExceptionHandler.process(e);
                return Status.CANCEL_STATUS;
            } finally {
                monitor.done();
            }
            return Status.OK_STATUS;
        }

        public List<ComponentExtension> getAvailableExtensions() {
            return fAvailableExtensions;
        }

    }

}
