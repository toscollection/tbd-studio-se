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
package org.talend.oozie.scheduler.controller;

import java.net.MalformedURLException;
import java.net.URISyntaxException;
import java.net.URL;
import java.util.Date;
import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.apache.oozie.client.OozieClient;
import org.apache.oozie.client.OozieClientException;
import org.apache.oozie.client.WorkflowAction;
import org.apache.oozie.client.WorkflowJob;
import org.eclipse.core.runtime.IPath;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.NullProgressMonitor;
import org.eclipse.core.runtime.Path;
import org.eclipse.core.runtime.Status;
import org.eclipse.core.runtime.jobs.Job;
import org.eclipse.gef.commands.CommandStack;
import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.jface.window.Window;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.Text;
import org.eclipse.ui.IViewReference;
import org.eclipse.ui.PartInitException;
import org.eclipse.ui.PlatformUI;
import org.eclipse.ui.browser.IWebBrowser;
import org.eclipse.ui.browser.IWorkbenchBrowserSupport;
import org.talend.commons.ui.runtime.exception.ExceptionHandler;
import org.talend.core.CorePlugin;
import org.talend.core.model.process.IContext;
import org.talend.core.model.process.IProcess2;
import org.talend.core.model.utils.JavaResourcesHelper;
import org.talend.core.prefs.ITalendCorePrefConstants;
import org.talend.designer.core.ui.AbstractMultiPageTalendEditor;
import org.talend.oozie.scheduler.constants.JobSubmissionType;
import org.talend.oozie.scheduler.constants.OozieJobProcessStatus;
import org.talend.oozie.scheduler.constants.OutputMessages;
import org.talend.oozie.scheduler.constants.SchedulerForHadoopConstants;
import org.talend.oozie.scheduler.exceptions.OozieJobDeployException;
import org.talend.oozie.scheduler.exceptions.OozieJobException;
import org.talend.oozie.scheduler.jobdeployer.OozieJobDeployer;
import org.talend.oozie.scheduler.ui.ExecuteJobComposite;
import org.talend.oozie.scheduler.ui.OozieShcedulerSettingDialog;
import org.talend.oozie.scheduler.ui.SchedulingDialog;
import org.talend.oozie.scheduler.utils.OozieSchedulerCommonUtils;
import org.talend.oozie.scheduler.utils.OozieSchedulerDateUtils;
import org.talend.oozie.scheduler.utils.OozieSchedulerStringUtils;
import org.talend.oozie.scheduler.views.OozieJobTrackerListener;
import org.talend.oozie.scheduler.views.OozieSchedulerView;
import org.talend.repository.ui.wizards.documentation.LinkUtils;

import com.hortonworks.etl.talend.JobContext;
import com.hortonworks.etl.talend.JobContext.Timeunit;
import com.hortonworks.etl.talend.JobSubmission;
import com.hortonworks.etl.talend.JobSubmissionException;
import com.hortonworks.etl.talend.oozie.RemoteJobSubmission;
import com.hortonworks.etl.talend.oozie.ScheduledJobSubmission;

/**
 * Created by Marvin Wang on Mar. 31, 2012 for doing some action from the widgets of
 * {@link ExecuteJobCompositeController}, all businesses are handled here.
 */
public class ExecuteJobCompositeController {

    private ExecuteJobComposite executeJobComposite;

    private SchedulingDialog schedulingDialog;

    private OozieShcedulerSettingDialog settingDialog;

    private boolean isSettingDone = false;// To check if the values of setting are done.

    // private OozieClient oozieClient;

    // private String jobIdInOozie;

    boolean isRunBtnEnabled = false;// Identify if the "Run" button is enabled.

    boolean isScheduleBtnEnabled = false;// Identify if the "Schedule" button is enabled.

    boolean isKillBtnEnabled = false;// Identify if the "Kill" button is enabled.

    // public static final String JOB_FQ_CN_NAME = "marvinproject.marvinjob_0_1.MarvinJob";

    private RemoteJobSubmission remoteJobSubmission;

    private ScheduledJobSubmission scheduledJobSubmission;

    OozieJobTraceManager traceManager;

    // private Map<String, String> tracesForOozie;// Key is job id, and value is result of logging.

    public ExecuteJobCompositeController(ExecuteJobComposite executeJobComposite) {
        // tracesForOozie = new HashMap<String, String>();
        traceManager = OozieJobTraceManager.getInstance();
        this.executeJobComposite = executeJobComposite;
    }

    public void initValues() {
        Text pathText = executeJobComposite.getPathText();
        Text outputText = executeJobComposite.getOutputTxt();
        if (!pathText.isDisposed() && !outputText.isDisposed()) {
            final IProcess2 process = OozieJobTrackerListener.getProcess();
            if (process != null) {

                String jobIdInOozie = CorePlugin.getDefault().getPreferenceStore().getString("oozie-" + process.getLabel());
                String appPath = (String) process.getElementParameter("HADOOP_APP_PATH").getValue();
                pathText.setText(appPath);
                // update output value for each editor.

                // String outputValue = tracesForOozie.get(jobIdInOozie);
                String outputValue = traceManager.getTrace(jobIdInOozie);
                updateOutputTextContents(outputValue == null ? "" : outputValue, process.getLabel());

                updatePathTxtEnabledOrNot();
                updateOutputTxtEnabledOrNot();
                updateAllEnabledOrNot();
                // updateOutputTxtValue();
            } else {
                pathText.setText("");
                outputText.setText("");
                updatePathTxtEnabledOrNot();
                updateOutputTxtEnabledOrNot();
            }
        }
    }

    /**
     * When clicking the "Schedule" button, this action will open a dialog to provide some configurations for
     * scheduling. If <code>Window.OK == Dialog.open</code>, will invoke the method.
     */
    public void doScheduleAction(IContext context) {
        Shell shell = executeJobComposite.getShell();
        schedulingDialog = new SchedulingDialog(shell);
        initScheduling(schedulingDialog);
        if (Window.OK == schedulingDialog.open()) {
            String jobName = null;
            try {
                JobContext jobContext = initJobContextForOozie(JobSubmissionType.SCHEDULED, context);
                jobName = jobContext.getJobName();
                OozieJobDeployer.deployJob(OozieJobTrackerListener.getProcess(), new NullProgressMonitor());
                updateAllEnabledOrNot(OozieJobProcessStatus.PREP, OozieJobTrackerListener.getProcess().getLabel());
                doScheduleJob(jobContext);
            } catch (Exception e) {
                StringBuffer output = new StringBuffer();
                output.append(e.getMessage());
                output.append(OutputMessages.LINE_BREAK_CHAR);
                output.append(e.getCause().getMessage());
                output.append(OutputMessages.LINE_BREAK_CHAR);
                updateOutputTextContents(output.toString(), jobName);

            }
        }
    }

    protected void initScheduling(SchedulingDialog schedulingDialog) {
        schedulingDialog.setFrequencyValue("1");
        schedulingDialog.setTimeUnitItemValues(SchedulerForHadoopConstants.TIME_UNIT_VALUES);
    }

    private void doScheduleJob(final JobContext jobContext) {
        new Runnable() {

            @Override
            public void run() {
                scheduledJobSubmission = new ScheduledJobSubmission();
                try {
                    OozieClient oozieClient = new OozieClient(getOozieFromPreference());
                    String jobIdInOozie = scheduledJobSubmission.submit(jobContext);
                    CorePlugin.getDefault().getPreferenceStore().putValue("oozie-" + jobContext.getJobName(), jobIdInOozie);
                    outputScheduleJobLogs(oozieClient, jobIdInOozie, jobContext);
                } catch (JobSubmissionException e) {
                    ExceptionHandler.process(e);
                } catch (InterruptedException e) {
                    ExceptionHandler.process(e);
                } catch (URISyntaxException e) {
                    ExceptionHandler.process(e);
                }
            }
        }.run();
    }

    protected void checkJobStatus(String jobIdInOozie, JobSubmission jobSubmission, StringBuffer output, JobContext jobContext) {
        output.append(outputCurrentDate());
        try {
            switch (jobSubmission.status(jobIdInOozie, getOozieFromPreference())) {
            case RUNNING:
                output.append(OutputMessages.MSG_OUTPUT_RUNNING);
                output.append(OutputMessages.LINE_BREAK_CHAR);
                break;
            case SUCCEEDED:
                output.append(OutputMessages.MSG_OUTPUT_JOB_COMPLETE);
                output.append(OutputMessages.LINE_BREAK_CHAR);
                traceManager.putTrace(jobIdInOozie, output.toString());
                // tracesForOozie.put(jobIdInOozie, output.toString());
                updateOutputTextContents(output.toString(), jobContext.getJobName());
                break;
            case KILLED:
                output.append(OutputMessages.MSG_OUTPUT_JOB_KILL);
                output.append(OutputMessages.LINE_BREAK_CHAR);
                traceManager.putTrace(jobIdInOozie, output.toString());
                // tracesForOozie.put(jobIdInOozie, output.toString());
                afterDoKillAction(jobIdInOozie, output);
                updateOutputTextContents(output.toString(), jobContext.getJobName());
                break;
            case FAILED:
                output.append(OutputMessages.MSG_OUTPUT_JOB_FAILD);
                output.append(OutputMessages.LINE_BREAK_CHAR);
                break;
            case PREP:
                output.append(OutputMessages.MSG_OUTPUT_JOB_PREPARE);
                output.append(OutputMessages.LINE_BREAK_CHAR);
                break;
            case SUSPENDED:
                output.append(OutputMessages.MSG_OUTPUT_JOB_SUSPENDED);
                output.append(OutputMessages.LINE_BREAK_CHAR);
                break;
            }

        } catch (JobSubmissionException e) {
            ExceptionHandler.process(e);
        } catch (OozieJobException e) {
            ExceptionHandler.process(e);
        }
    }

    private void outputScheduleJobLogs(final OozieClient oozieClient, final String jobIdInOozie, final JobContext jobContext) {
        new Thread() {

            public void run() {
                StringBuffer output = new StringBuffer(OutputMessages.MSG_OUTPUT_RUNNING);
                output.append(OutputMessages.LINE_BREAK_CHAR);
                try {
                    // JobSubmission.Status status = scheduledJobSubmission.status(scheduledJobHandle,
                    // jobContext.getOozieEndPoint());
                    while (JobSubmission.Status.PREP == scheduledJobSubmission.status(jobIdInOozie, getOozieFromPreference())) {
                        output.append("Job is preparing to run...");
                        output.append(OutputMessages.LINE_BREAK_CHAR);
                        output.append(oozieClient.getCoordJobInfo(jobIdInOozie));
                        output.append(OutputMessages.LINE_BREAK_CHAR);
                        updateOutputTextContents(output.toString(), jobContext.getJobName());
                        Thread.sleep(1000 * 2);
                    }

                    while (JobSubmission.Status.RUNNING == scheduledJobSubmission.status(jobIdInOozie, getOozieFromPreference())) {
                        output.append("Job is running...");
                        output.append(OutputMessages.LINE_BREAK_CHAR);
                        output.append(oozieClient.getJobInfo(jobIdInOozie));
                        output.append(OutputMessages.LINE_BREAK_CHAR);
                        updateOutputTextContents(output.toString(), jobContext.getJobName());
                        Thread.sleep(1000 * 2);
                    }

                    switch (scheduledJobSubmission.status(jobIdInOozie, getOozieFromPreference())) {
                    case SUCCEEDED:
                        output.append(OutputMessages.MSG_OUTPUT_JOB_COMPLETE);
                        output.append(OutputMessages.LINE_BREAK_CHAR);
                        break;
                    case KILLED:
                        output.append(OutputMessages.MSG_OUTPUT_JOB_KILL);
                        output.append(OutputMessages.LINE_BREAK_CHAR);
                        break;
                    case FAILED:
                        output.append(OutputMessages.MSG_OUTPUT_JOB_FAILD);
                        output.append(OutputMessages.LINE_BREAK_CHAR);
                        break;
                    case PREP:
                        output.append(OutputMessages.MSG_OUTPUT_JOB_PREPARE);
                        output.append(OutputMessages.LINE_BREAK_CHAR);
                        break;
                    case SUSPENDED:
                        output.append(OutputMessages.MSG_OUTPUT_JOB_SUSPENDED);
                        output.append(OutputMessages.LINE_BREAK_CHAR);
                        break;
                    }

                    // output.append(Messages.getString("MSG_output_complete", new Object[] { jobIdFromOozie,
                    // oozieClient.getJobInfo(jobIdFromOozie).getStatus() }));
                    // output.append(OutputMessages.LINE_BREAK_CHAR);
                    updateAllEnabledOrNot(OozieJobProcessStatus.SUCCEEDED, jobContext.getJobName());
                    updateOutputTextContents(output.toString(), jobContext.getJobName());
                } catch (OozieClientException e) {
                    ExceptionHandler.process(e);
                } catch (InterruptedException e) {
                    ExceptionHandler.process(e);
                } catch (JobSubmissionException e) {
                    updateAllEnabledOrNot(OozieJobProcessStatus.KILLED, jobContext.getJobName());
                    ExceptionHandler.process(e);
                }
            }
        }.start();
    }

    /**
     * When clicking the "Run" button, this action will be invoked.<br>
     * Workflow job state valid transitions: <li>--> PREP <li>PREP --> RUNNING | KILLED <li>RUNNING --> SUSPENDED |
     * SUCCEEDED | KILLED | FAILED <li>SUSPENDED --> RUNNING | KILLED
     * 
     * @param iContext
     */
    public void doRunAction(IContext iContext) {
        try {
            JobContext jobContext = initJobContextForOozie(JobSubmissionType.REMOTE, iContext);
            String jobIdInOozie = CorePlugin.getDefault().getPreferenceStore().getString("oozie-" + jobContext.getJobName());
            if (jobIdInOozie != null)
                // tracesForOozie.remove(jobIdInOozie);
                traceManager.removeTrace(jobIdInOozie);
            // updateAllEnabledOrNot(OozieJobProcessStatus.INIT);
            doRunJob(jobContext);
        } catch (Exception e) {
            ExceptionHandler.process(e);
        }
    }

    private String doRunJob(final JobContext jobContext) {
        Display.getDefault().asyncExec(new Runnable() {

            public void run() {
                Job runJob = new Job("Job is running remotely...") {

                    @Override
                    protected IStatus run(IProgressMonitor monitor) {
                        StringBuffer output = new StringBuffer("");
                        IStatus status = Status.OK_STATUS;
                        try {
                            monitor.beginTask(OutputMessages.MSG_OUTPUT_STARTUP, 100);
                            // startupRemoteJob(output);
                            monitor.worked(10);

                            monitor.subTask(OutputMessages.MSG_OUTPUT_DEPLOYING);
                            updateAllEnabledOrNot(OozieJobProcessStatus.DEPLOYING, jobContext.getJobName());
                            deployJobOnHadoop(output, jobContext);
                            monitor.worked(30);

                            status = runRemoteJob(monitor, jobContext, output);

                            return status;
                        } catch (InterruptedException e) {
                            updateAllEnabledOrNot(OozieJobProcessStatus.FAILED, jobContext.getJobName());
                            output.append(outputCurrentDate());
                            output.append(e.getMessage());
                            output.append(OutputMessages.LINE_BREAK_CHAR);
                            updateOutputTextContents(output.toString(), jobContext.getJobName());
                            ExceptionHandler.process(e);
                        } catch (JobSubmissionException e) {
                            updateAllEnabledOrNot(OozieJobProcessStatus.FAILED, jobContext.getJobName());
                            output.append(outputCurrentDate());
                            output.append(e.getMessage());
                            output.append(OutputMessages.LINE_BREAK_CHAR);
                            output.append(e.getCause().getMessage());
                            output.append(OutputMessages.LINE_BREAK_CHAR);
                            updateOutputTextContents(output.toString(), jobContext.getJobName());
                            ExceptionHandler.process(e);
                        } catch (OozieClientException e) {
                            updateAllEnabledOrNot(OozieJobProcessStatus.FAILED, jobContext.getJobName());
                            output.append(outputCurrentDate());
                            output.append(e.getMessage());
                            output.append(OutputMessages.LINE_BREAK_CHAR);
                            updateOutputTextContents(output.toString(), jobContext.getJobName());
                            ExceptionHandler.process(e);
                        } catch (URISyntaxException e) {
                            updateAllEnabledOrNot(OozieJobProcessStatus.FAILED, jobContext.getJobName());
                            output.append(outputCurrentDate());
                            output.append(e.getMessage());
                            output.append(OutputMessages.LINE_BREAK_CHAR);
                            updateOutputTextContents(output.toString(), jobContext.getJobName());
                            ExceptionHandler.process(e);
                        } catch (OozieJobDeployException e) {
                            updateAllEnabledOrNot(OozieJobProcessStatus.FAILED, jobContext.getJobName());
                            output.append(outputCurrentDate());
                            output.append(e.getMessage());
                            output.append(OutputMessages.LINE_BREAK_CHAR);
                            updateOutputTextContents(output.toString(), jobContext.getJobName());
                            ExceptionHandler.process(e);
                        }
                        return Status.OK_STATUS;
                    }
                };
                runJob.setUser(true);
                runJob.schedule();
            }
        });
        return null;
    }

    private String outputCurrentDate() {
        return OozieSchedulerStringUtils.formatDateLog(OozieSchedulerDateUtils.fetchCurrentDate());
    }

    @SuppressWarnings("unused")
    private void startupRemoteJob(StringBuffer output) {
        output.append(outputCurrentDate());
        output.append(OutputMessages.MSG_OUTPUT_STARTUP);
        output.append(OutputMessages.LINE_BREAK_CHAR);
        // updateOutputTextContents(output.toString());
    }

    private void deployJobOnHadoop(StringBuffer output, JobContext jobContext) throws OozieJobDeployException {
        output.append(outputCurrentDate());
        output.append(OutputMessages.MSG_OUTPUT_DEPLOYING + OutputMessages.LINE_BREAK_CHAR);
        updateOutputTextContents(output.toString(), jobContext.getJobName());
        try {
            OozieJobDeployer.deployJob(OozieJobTrackerListener.getProcess(), new NullProgressMonitor());
            output.append(outputCurrentDate());
            output.append(OutputMessages.MSG_OUTPUT_DEPLOY_COMPLETE);
            output.append(OutputMessages.LINE_BREAK_CHAR);
        } catch (OozieJobDeployException e) {
            output.append(outputCurrentDate());
            output.append(OutputMessages.MSG_OUTPUT_DEPLOY_FAILED);
            output.append(OutputMessages.LINE_BREAK_CHAR);
            output.append(outputCurrentDate());
            output.append(e.getMessage());
            output.append(OutputMessages.LINE_BREAK_CHAR);
            output.append(outputCurrentDate());
            output.append(e.getCause().getMessage());
            output.append(OutputMessages.LINE_BREAK_CHAR);
            updateOutputTextContents(output.toString(), jobContext.getJobName());
            throw e;
        }
        updateOutputTextContents(output.toString(), jobContext.getJobName());
    }

    private IStatus runRemoteJob(IProgressMonitor monitor, JobContext jobContext, StringBuffer output)
            throws JobSubmissionException, InterruptedException, URISyntaxException, OozieClientException {
        // OozieClient oozieClient = new OozieClient(getOozieFromPreference());
        remoteJobSubmission = new RemoteJobSubmission();
        String jobIdInOozie = remoteJobSubmission.submit(jobContext);
        monitor.subTask(OutputMessages.MSG_OUTPUT_RUNNING);

        output.append(outputCurrentDate());
        output.append(OutputMessages.MSG_OUTPUT_RUNNING);
        output.append(OutputMessages.LINE_BREAK_CHAR);
        updateOutputTextContents(output.toString(), jobContext.getJobName());

        updateAllEnabledOrNot(OozieJobProcessStatus.RUNNING, jobContext.getJobName());
        CorePlugin.getDefault().getPreferenceStore().putValue("oozie-" + jobContext.getJobName(), jobIdInOozie);
        if (monitor.isCanceled()) {
            output.append(">> The running job is canceled!");
            updateOutputTextContents(output.toString(), jobContext.getJobName());
            return Status.CANCEL_STATUS;
        }
        monitor.worked(10);
        monitor.subTask("Logging...");

        // outputLogs(oozieClient, jobIdInOozie);
        // tracesForOozie.put(jobIdInOozie, output.toString());
        traceManager.putTrace(jobIdInOozie, output.toString());
        while (jobIdInOozie != null && !"".equals(jobIdInOozie)
                && JobSubmission.Status.RUNNING == remoteJobSubmission.status(jobIdInOozie, getOozieFromPreference())) {

            Thread.sleep(1000 * 2);
        }
        checkJobStatus(jobIdInOozie, remoteJobSubmission, output, jobContext);
        updateAllEnabledOrNot(OozieSchedulerCommonUtils.convertToOozieJobProcessStatus(remoteJobSubmission.status(jobIdInOozie,
                getOozieFromPreference())), jobContext.getJobName());
        monitor.worked(50);
        return Status.OK_STATUS;
    }

    /**
     * Updates the contents of the widget "Output" using logs and status.
     * 
     * @param output
     */
    private void updateOutputTextContents(final String output, final String oozieJobName) {
        if (OozieJobTrackerListener.getProcess() != null) {
            Display.getDefault().asyncExec(new Runnable() {

                @Override
                public void run() {
                    IViewReference[] viewRef = PlatformUI.getWorkbench().getActiveWorkbenchWindow().getActivePage()
                            .getViewReferences();
                    if (viewRef != null && viewRef.length > 0) {
                        for (int i = 0; i < viewRef.length; i++) {
                            if (viewRef[i].getView(true) instanceof OozieSchedulerView) {
                                if (OozieJobTrackerListener.getProcess() != null
                                        && OozieJobTrackerListener.getProcess().getLabel().equals(oozieJobName)) {
                                    OozieSchedulerView view = (OozieSchedulerView) viewRef[i].getView(true);
                                    executeJobComposite = view.getExecuteJobComposite();
                                    Text outputTxt = executeJobComposite.getOutputTxt();
                                    if (!outputTxt.isDisposed()) {
                                        outputTxt.setText("");
                                        outputTxt.setText(output);
                                        int lines = outputTxt.getLineCount();
                                        outputTxt.setTopIndex(lines);
                                    }
                                }
                            }
                        }
                    }
                }

            });
        }
    }

    protected void updateAllEnabledOrNot(final OozieJobProcessStatus status, final String oozieJobName) {
        if (OozieJobTrackerListener.getProcess() != null) {
            Display.getDefault().asyncExec(new Runnable() {

                public void run() {
                    IViewReference[] viewRef = PlatformUI.getWorkbench().getActiveWorkbenchWindow().getActivePage()
                            .getViewReferences();
                    if (viewRef != null && viewRef.length > 0) {
                        for (int i = 0; i < viewRef.length; i++) {
                            if (viewRef[i].getView(true) instanceof OozieSchedulerView
                                    && OozieJobTrackerListener.getProcess().getLabel().equals(oozieJobName)) {
                                OozieSchedulerView view = (OozieSchedulerView) viewRef[i].getView(true);
                                executeJobComposite = view.getExecuteJobComposite();
                                Button runBtn = executeJobComposite.getRunBtn();
                                Button scheduleBtn = executeJobComposite.getScheduleBtn();
                                Button killBtn = executeJobComposite.getKillBtn();
                                Text pathTxt = executeJobComposite.getPathText();
                                Text outputTxt = executeJobComposite.getOutputTxt();
                                switch (status) {
                                case INIT:
                                    break;
                                case DEPLOYING:
                                    runBtn.setEnabled(false);
                                    scheduleBtn.setEnabled(false);
                                    killBtn.setEnabled(false);
                                    pathTxt.setEnabled(false);
                                    outputTxt.setEnabled(true);
                                    break;
                                case PREP:
                                    runBtn.setEnabled(false);
                                    scheduleBtn.setEnabled(false);
                                    killBtn.setEnabled(true);
                                    pathTxt.setEnabled(false);
                                    outputTxt.setEnabled(true);
                                    break;
                                case RUNNING:
                                    runBtn.setEnabled(false);
                                    scheduleBtn.setEnabled(false);
                                    killBtn.setEnabled(true);
                                    pathTxt.setEnabled(false);
                                    outputTxt.setEnabled(true);
                                    break;
                                case SUCCEEDED:
                                    runBtn.setEnabled(true);
                                    scheduleBtn.setEnabled(true);
                                    killBtn.setEnabled(false);
                                    pathTxt.setEnabled(true);
                                    outputTxt.setEnabled(true);
                                    break;
                                case KILLED:
                                    runBtn.setEnabled(true);
                                    scheduleBtn.setEnabled(true);
                                    killBtn.setEnabled(false);
                                    pathTxt.setEnabled(true);
                                    outputTxt.setEnabled(true);
                                    break;
                                case FAILED:
                                    runBtn.setEnabled(true);
                                    scheduleBtn.setEnabled(true);
                                    killBtn.setEnabled(false);
                                    pathTxt.setEnabled(true);
                                    outputTxt.setEnabled(true);
                                    break;
                                case SUSPENDED:
                                    runBtn.setEnabled(true);
                                    scheduleBtn.setEnabled(false);
                                    killBtn.setEnabled(true);
                                    pathTxt.setEnabled(false);
                                    outputTxt.setEnabled(true);
                                    break;
                                }

                            }
                        }
                    }
                }
            });
        }
    }

    /**
     * Workflow job state valid transitions: <li>--> PREP <li>PREP --> RUNNING | KILLED <li>RUNNING --> SUSPENDED |
     * SUCCEEDED | KILLED | FAILED <li>SUSPENDED --> RUNNING | KILLED
     * 
     * @param status
     */
    // protected void updateAllEnabledOrNot(final OozieJobProcessStatus status) {
    //
    // if (!executeJobComposite.isDisposed())
    // return;
    //
    // }

    public void doKillAction() {
        Button runBtn = executeJobComposite.getRunBtn();
        Button scheduleBtn = executeJobComposite.getScheduleBtn();
        Button killBtn = executeJobComposite.getKillBtn();
        Text pathTxt = executeJobComposite.getPathText();

        runBtn.setEnabled(true);
        scheduleBtn.setEnabled(true);
        pathTxt.setEnabled(true);
        killBtn.setEnabled(false);
        try {
            OozieClient oozieClient = new OozieClient(getOozieFromPreference());
            StringBuffer output = new StringBuffer("");
            String jobIdInOozie = CorePlugin.getDefault().getPreferenceStore()
                    .getString("oozie-" + OozieJobTrackerListener.getProcess().getLabel());
            if (!StringUtils.isEmpty(jobIdInOozie)) {
                if (oozieClient.getJobInfo(jobIdInOozie).getStatus() == WorkflowJob.Status.RUNNING) {
                    output.append(executeJobComposite.getOutputTxt().getText());
                    oozieClient.kill(jobIdInOozie);
                    // updateJobIdInOozieForJob(null);
                    // System.out.println("Kill Job!");
                    // output.append(OutputMessages.MSG_OUTPUT_KILL);
                    // output.append(OutputMessages.LINE_BREAK_CHAR);
                    executeJobComposite.getKillBtn().setEnabled(false);
                    // if (multiPageTalendEditor != null)
                    // updateOutputTextContents(output.toString());
                } else if (oozieClient.getJobInfo(jobIdInOozie).getStatus() == WorkflowJob.Status.SUCCEEDED) {
                    System.out.println("Job is succeeded!");
                    executeJobComposite.getRunBtn().setEnabled(true);
                    executeJobComposite.getKillBtn().setEnabled(false);
                    // todo add schedule
                } else if (oozieClient.getJobInfo(jobIdInOozie).getStatus() == WorkflowJob.Status.KILLED) {

                }
                // afterDoKillAction(jobIdInOozie);
            }
        } catch (OozieClientException e) {
            ExceptionHandler.process(e);
        }
    }

    private void afterDoKillAction(String killedJobId, StringBuffer output) throws OozieJobException {

        OozieClient oozieClient = new OozieClient(getOozieFromPreference());
        oozieClient.setDebugMode(0);
        try {
            List<WorkflowAction> actions = oozieClient.getJobInfo(killedJobId).getActions();
            if (actions != null && actions.size() > 0) {
                for (WorkflowAction action : actions) {
                    if (action.getErrorMessage() != null) {
                        output.append(action.getErrorMessage() + OutputMessages.LINE_BREAK_CHAR);
                    }
                }
            }
        } catch (OozieClientException e) {
            throw new OozieJobException("Job info can not be retrieved!", e);
        }
    }

    private AbstractMultiPageTalendEditor getEditor() {
        if (OozieJobTrackerListener.getProcess() == null) {
            return null;
        }
        return (AbstractMultiPageTalendEditor) OozieJobTrackerListener.getProcess().getEditor();
    }

    public CommandStack getCommandStack() {
        return getEditor() == null ? null : (CommandStack) (getEditor().getTalendEditor().getAdapter(CommandStack.class));
    }

    protected void updateJobIdInOozieForJob(final String jobIdFromOozie) {
        // executeJobComposite.getDisplay().asyncExec(new Runnable() {
        //
        // @Override
        // public void run() {
        // if (multiPageTalendEditor != null) {
        // IProcess2 process = OozieJobTrackerListener.getProcess();
        // getCommandStack().execute(
        // new PropertyChangeCommand(process, "JOBID_FOR_OOZIE", jobIdFromOozie == null ? "" : jobIdFromOozie));
        // }
        // }
        // });
    }

    protected String getJobIdInOozie() {
        String jobIdInOozie = null;
        if (OozieJobTrackerListener.getProcess() != null) {
            IProcess2 process = OozieJobTrackerListener.getProcess();
            jobIdInOozie = CorePlugin.getDefault().getPreferenceStore().getString("oozie-" + process.getLabel());
        }
        return jobIdInOozie;
    }

    /**
     * 
     * @return
     */
    private JobContext initJobContextForOozie(JobSubmissionType jobSubType, IContext context) {
        JobContext jobContext = new JobContext();

        IProcess2 process = OozieJobTrackerListener.getProcess();
        // Job name.
        String jobName = process.getLabel();
        // String jobName = "MavinJob";
        jobContext.setJobName(jobName);

        // Job path on HDFS
        String path = executeJobComposite.getPathValue();
        // IProcess2 process = OozieJobTrackerListener.getProcess();
        // String name = JavaResourcesHelper.getJobFolderName(process.getLabel(), process.getVersion()) + "." +
        // process.getLabel();
        // jobContext.setJobPathOnHDFS(path + "/" + name);
        jobContext.setJobPathOnHDFS(path);

        // Oozie end point
        String oozieEPValue = getOozieFromPreference();
        jobContext.setOozieEndPoint(oozieEPValue);

        // Name node end point
        String nameNodeEPValue = getNameNodeFromPreference();
        jobContext.setNameNodeEndPoint(nameNodeEPValue);

        // Job tracker end point
        String jobTrackerEPValue = getJobTrackerFromPreference();
        jobContext.setJobTrackerEndPoint(jobTrackerEPValue);

        // APP path
        // IPath appPath = new Path(nameNodeEPValue).append(path);
        // mhirt : bug fix on linux Path changes // to /. FIXME : Do a better coding for 5.1.1
        String appPath = (nameNodeEPValue!=null)?nameNodeEPValue.concat(path):path;
        jobContext.set(OozieClient.APP_PATH, appPath);

        // User Name for acessing hadoop
        String userNameValue = getUserNameForHadoopFromPreference();
        jobContext.set(OozieClient.USER_NAME, userNameValue);
        
        // TOS job
        String tosJobFQCN = getTOSJobFQCNValue();
        jobContext.setJobFQClassName(tosJobFQCN);
        jobContext.setTosContextPath(context.getName());

        switch (jobSubType) {
        case LOCAL:
            break;
        case REMOTE:
            break;
        case SCHEDULED:
            // Frequency
            int fequency = getFrequencyValue();
            jobContext.setFrequency(fequency);
            // Time unit
            Timeunit timeUnit = getTimeUnitValue();
            jobContext.setTimeUnit(timeUnit);
            // Start Time
            Date startTime = getStartTimeValue();
            jobContext.setStartTime(startTime);
            // End Time
            Date endTime = getEndTimeValue();
            jobContext.setEndTime(endTime);
            break;
        default:
        }

        return jobContext;
    }

    private Timeunit getTimeUnitValue() {
        int selectIndex = schedulingDialog.getSelectedTimeUnitIndex();
        return OozieSchedulerStringUtils.convertTimeUnit(selectIndex);
        // return Timeunit.MINUTE;
    }

    private int getFrequencyValue() {
        String frequencyValue = schedulingDialog.getFrequencyValue();
        int intValue = Integer.valueOf(frequencyValue).intValue();
        return intValue;
    }

    private Date getStartTimeValue() {
        // Calendar instance = Calendar.getInstance();
        // Date start = instance.getTime();
        Date startDate = schedulingDialog.getStartDate();
        return startDate;
    }

    private Date getEndTimeValue() {
        // Calendar instance = Calendar.getInstance();
        // instance.roll(3, 1);
        // Date end = instance.getTime();
        // return end;
        Date endDate = schedulingDialog.getEndDate();
        return endDate;
    }

    /**
     * Gets the value of name node end point from preference store.
     * 
     * @return
     */
    private String getNameNodeFromPreference() {
        String nameNodeEPValue = CorePlugin.getDefault().getPreferenceStore()
                .getString(ITalendCorePrefConstants.OOZIE_SHCEDULER_NAME_NODE_ENDPOINT);
        return nameNodeEPValue;
    }

    private String getJobTrackerFromPreference() {
        String jobTrackerEPValue = CorePlugin.getDefault().getPreferenceStore()
                .getString(ITalendCorePrefConstants.OOZIE_SHCEDULER_JOB_TRACKER_ENDPOINT);
        return jobTrackerEPValue;
    }

    private String getOozieFromPreference() {
        String oozieEPValue = CorePlugin.getDefault().getPreferenceStore()
                .getString(ITalendCorePrefConstants.OOZIE_SHCEDULER_OOZIE_ENDPOINT);
        return oozieEPValue;
    }

    private String getUserNameForHadoopFromPreference() {
        String userNameValue = CorePlugin.getDefault().getPreferenceStore()
                .getString(ITalendCorePrefConstants.OOZIE_SCHEDULER_USER_NAME);
        return userNameValue;
    }

    private String getTOSJobFQCNValue() {
        IProcess2 process = OozieJobTrackerListener.getProcess();
        return JavaResourcesHelper.getCurrentProjectName() + "."
                + JavaResourcesHelper.getJobFolderName(process.getLabel(), process.getVersion()) + "." + process.getLabel();
    }

    /**
     * Handles the action when clicking the button "Setting".
     */
    public void doSettingAction() {
        Shell shell = executeJobComposite.getShell();
        settingDialog = new OozieShcedulerSettingDialog(shell);
        initPreferenceSettingForJob(settingDialog);
        if (Window.OK == settingDialog.open()) {
            // To update the values of Oozie preference page
            updateOoziePreferencePageValues();
            updateAllEnabledOrNot();
        }
    }

    /**
     * Initializes the setup before opening scheduler setting dialog. Sets back the job setting when a job is opened.
     */
    protected void initPreferenceSettingForJob(OozieShcedulerSettingDialog settingDialog) {
        String nameNodeEPValue = getNameNodeFromPreference();
        String jobTrackerEPValue = getJobTrackerFromPreference();
        String oozieEPValue = getOozieFromPreference();
        String userNameValue = getUserNameForHadoopFromPreference();

        settingDialog.setNameNodeEndPointValue(nameNodeEPValue);
        settingDialog.setJobTrackerEndPointValue(jobTrackerEPValue);
        settingDialog.setOozieEndPointValue(oozieEPValue);
        settingDialog.setUserNameValue(userNameValue);
    }

    public void doModifyPathAction() {
        if (OozieJobTrackerListener.getProcess() != null) {

            if (isJobReadOnly()) {

            } else {
                String path = executeJobComposite.getPathValue();
                IProcess2 process = OozieJobTrackerListener.getProcess();
                process.getElementParameter("HADOOP_APP_PATH").setValue(path);
            }
        }
        // checkWidgetsStatus();
        updateAllEnabledOrNot();
    }

    private boolean isJobReadOnly() {
        boolean isReadOnly = false;
        isReadOnly = OozieJobTrackerListener.getProcess().isReadOnly();
        return isReadOnly;
    }

    /**
     * When clicking the button named "Monitoring", this method will be invoked.
     */
    public void doMonitoringBtnAction() {
        String oozieURL = getOozieFromPreference();
        if (oozieURL == null || "".equals(oozieURL) || !LinkUtils.isRemoteFile(oozieURL)) {
            MessageDialog.openWarning(executeJobComposite.getShell(), OutputMessages.MSG_WARNING_URL_TITLE,
                    OutputMessages.MSG_WARNING_URL_NOTVALID);
        } else {
            try {
                IWorkbenchBrowserSupport support = PlatformUI.getWorkbench().getBrowserSupport();

                IWebBrowser browser = support.getExternalBrowser();
                // Open SWT brower in Eclipse.
                // IWebBrowser browser = support.createBrowser("id");
                browser.openURL(new URL(oozieURL));
            } catch (PartInitException e) {
            } catch (MalformedURLException e) {
            }
        }
    }

    /**
     * To store all values in Oozie preference, values include "Name node end point", "Job tracker end point",
     * "Oozie end point", and "User name for hadoop".
     */
    private void updateOoziePreferencePageValues() {
        String nameNodeEPValue = settingDialog.getNameNodeEndPointValue();
        String jobTrackerEPValue = settingDialog.getJobTrackerEndPointValue();
        String oozieEPValue = settingDialog.getOozieEndPointValue();
        String userNameValue = settingDialog.getUserNameValue();

        CorePlugin.getDefault().getPreferenceStore()
                .setValue(ITalendCorePrefConstants.OOZIE_SHCEDULER_NAME_NODE_ENDPOINT, nameNodeEPValue);
        CorePlugin.getDefault().getPreferenceStore()
                .setValue(ITalendCorePrefConstants.OOZIE_SHCEDULER_JOB_TRACKER_ENDPOINT, jobTrackerEPValue);
        CorePlugin.getDefault().getPreferenceStore()
                .setValue(ITalendCorePrefConstants.OOZIE_SHCEDULER_OOZIE_ENDPOINT, oozieEPValue);

        CorePlugin.getDefault().getPreferenceStore().setValue(ITalendCorePrefConstants.OOZIE_SCHEDULER_USER_NAME, userNameValue);
    }

    /**
     * Checks if the "Run" button is enabled. Return <code>true</code> if "Path" value is not <code>null</code> and
     * empty, and all preference values are done, and job is not running on hadoop. Otherwise, return <code>false</code>
     * .
     * 
     * @return
     */
    public boolean checkIfRunBtnEnabled() {
        String jobIdInOozie = getJobIdInOozie();
        isRunBtnEnabled = false;
        String pathValue = executeJobComposite.getPathValue();
        if (pathValue != null && !"".equals(pathValue) && isSettingDoneFromPreferencePage()) {
            if (jobIdInOozie != null && !"".equals(jobIdInOozie)) {
                try {
                    JobSubmission.Status status = checkJobSubmissionStaus(jobIdInOozie);
                    switch (status) {
                    case RUNNING:
                        isRunBtnEnabled = false;
                        break;
                    case PREP:
                    case SUCCEEDED:
                    case KILLED:
                    case FAILED:
                    case SUSPENDED:
                        isRunBtnEnabled = true;
                    }
                } catch (JobSubmissionException e) {
                    isRunBtnEnabled = true;
                    // ExceptionHandler.process(e);
                }
            } else
                isRunBtnEnabled = true;
        }

        return isRunBtnEnabled;
    }

    /**
     * Checks if the "Schedule" button is enabled. Return <code>true</code> if "Path" value is not <code>null</code> and
     * empty, and all preference values are done, and job is not running on hadoop. Otherwise, return <code>false</code>
     * .
     * 
     * @return
     */
    public boolean checkIfScheduleBtnEnabled() {
        String jobIdInOozie = getJobIdInOozie();
        isScheduleBtnEnabled = false;
        String pathValue = executeJobComposite.getPathValue();
        if (pathValue != null && !"".equals(pathValue) && isSettingDoneFromPreferencePage()) {
            if (jobIdInOozie != null && !"".equals(jobIdInOozie)) {
                try {
                    JobSubmission.Status status = checkJobSubmissionStaus(jobIdInOozie);
                    switch (status) {
                    case RUNNING:
                        isScheduleBtnEnabled = false;
                        break;
                    case PREP:
                    case SUCCEEDED:
                    case KILLED:
                    case FAILED:
                    case SUSPENDED:
                        isScheduleBtnEnabled = true;
                    }
                } catch (JobSubmissionException e) {
                    isScheduleBtnEnabled = true;
                    // ExceptionHandler.process(e);
                }
            } else
                isScheduleBtnEnabled = true;
        }
        return isScheduleBtnEnabled;
    }

    public boolean checkIfKillBtnEnabled() {
        String jobIdInOozie = getJobIdInOozie();
        if (jobIdInOozie != null && !"".equals(jobIdInOozie)) {
            try {
                JobSubmission.Status status = checkJobSubmissionStaus(jobIdInOozie);
                switch (status) {
                case PREP:
                case RUNNING:
                case SUSPENDED:
                    isKillBtnEnabled = true;
                    break;
                case SUCCEEDED:
                case FAILED:
                case KILLED:
                    isKillBtnEnabled = false;
                    break;
                }
            } catch (JobSubmissionException e) {
                isKillBtnEnabled = false;
                // ExceptionHandler.process(e);
            }
        } else
            isKillBtnEnabled = false;
        return isKillBtnEnabled;
    }

    /**
     * Updates all buttons status, enabled or not.
     */
    public void updateAllEnabledOrNot() {
        updateRunBtnEnabledOrNot();
        updateScheduleBtnEnabledOrNot();
        updateKillBtnEnabledOrNot();
        updatePathTxtEnabledOrNot();
        updateOutputTxtEnabledOrNot();
    }

    protected void updateRunBtnEnabledOrNot() {
        Button runBtn = executeJobComposite.getRunBtn();
        if (runBtn.isDisposed())
            return;
        if (OozieJobTrackerListener.getProcess() == null)
            runBtn.setEnabled(false);
        else
            runBtn.setEnabled(checkIfRunBtnEnabled());
    }

    protected void updateScheduleBtnEnabledOrNot() {
        Button scheduleBtn = executeJobComposite.getScheduleBtn();
        if (scheduleBtn.isDisposed())
            return;
        if (OozieJobTrackerListener.getProcess() == null)
            scheduleBtn.setEnabled(false);
        else
            scheduleBtn.setEnabled(checkIfScheduleBtnEnabled());
    }

    protected void updateKillBtnEnabledOrNot() {
        Button killBtn = executeJobComposite.getKillBtn();
        if (killBtn.isDisposed())
            return;
        if (OozieJobTrackerListener.getProcess() == null)
            killBtn.setEnabled(false);
        else
            killBtn.setEnabled(checkIfKillBtnEnabled());
    }

    protected void updatePathTxtEnabledOrNot() {
        Text pathTxt = executeJobComposite.getPathText();
        if (pathTxt.isDisposed())
            return;
        if (OozieJobTrackerListener.getProcess() == null) {
            pathTxt.setEnabled(false);
        } else {
            pathTxt.setEnabled(true);
        }

    }

    protected void updateOutputTxtEnabledOrNot() {
        Text outputTxt = executeJobComposite.getOutputTxt();
        if (outputTxt.isDisposed())
            return;
        if (OozieJobTrackerListener.getProcess() == null) {
            outputTxt.setEnabled(false);
        } else {
            outputTxt.setEnabled(true);
        }
    }

    public boolean isPathTxtEnabled() {
        boolean isEnabled = false;

        return isEnabled;
    }

    /**
     * Checks if the scheduler setting is done from preference page, returns <code>true</code> if done, otherwise,
     * returns <code>false</code> .
     * 
     * @return
     */
    protected boolean isSettingDoneFromPreferencePage() {
        // Fetch the scheduler setting infos from preference page. If all are not null, set true.
        String nameNodeEPValue = getNameNodeFromPreference();
        String jobTrackerEPValue = getJobTrackerFromPreference();
        String oozieEPValue = getOozieFromPreference();

        if ("".equals(nameNodeEPValue) || "".equals(jobTrackerEPValue) || "".equals(oozieEPValue))
            isSettingDone = false;
        else
            isSettingDone = true;
        return isSettingDone;
    }

    /**
     * 
     * @return
     * @throws JobSubmissionException
     */
    protected JobSubmission.Status checkJobSubmissionStaus(String jobId) throws JobSubmissionException {
        RemoteJobSubmission remoteJobSub = new RemoteJobSubmission();
        JobSubmission.Status jobSubStatus = remoteJobSub.status(jobId, getOozieFromPreference());

        return jobSubStatus;
    }

    /**
     * This method is called when job editor is closed.
     */
    // public void removeTrace(String jobIdInOozie) {
    // if (tracesForOozie != null)
    // tracesForOozie.remove(jobIdInOozie);
    // }
    //
    // public void clearAllTraces() {
    // if (tracesForOozie != null)
    // tracesForOozie.clear();
    // }
}
