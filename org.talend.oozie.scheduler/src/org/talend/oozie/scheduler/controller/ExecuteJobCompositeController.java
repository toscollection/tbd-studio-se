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
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

import org.apache.oozie.client.OozieClient;
import org.apache.oozie.client.OozieClientException;
import org.apache.oozie.client.WorkflowJob;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.NullProgressMonitor;
import org.eclipse.core.runtime.Status;
import org.eclipse.core.runtime.jobs.Job;
import org.eclipse.gef.commands.CommandStack;
import org.eclipse.jface.window.Window;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.Text;
import org.eclipse.ui.PartInitException;
import org.eclipse.ui.PlatformUI;
import org.eclipse.ui.browser.IWebBrowser;
import org.eclipse.ui.browser.IWorkbenchBrowserSupport;
import org.talend.core.CorePlugin;
import org.talend.core.model.process.IProcess2;
import org.talend.core.model.utils.JavaResourcesHelper;
import org.talend.core.prefs.ITalendCorePrefConstants;
import org.talend.designer.core.ui.AbstractMultiPageTalendEditor;
import org.talend.designer.core.ui.editor.cmd.PropertyChangeCommand;
import org.talend.oozie.scheduler.constants.JobSubmissionType;
import org.talend.oozie.scheduler.constants.OutputMessages;
import org.talend.oozie.scheduler.constants.SchedulerForHadoopConstants;
import org.talend.oozie.scheduler.exceptions.OozieJobDeployException;
import org.talend.oozie.scheduler.i18n.Messages;
import org.talend.oozie.scheduler.jobdeployer.OozieJobDeployer;
import org.talend.oozie.scheduler.ui.ExecuteJobComposite;
import org.talend.oozie.scheduler.ui.OozieShcedulerSettingDialog;
import org.talend.oozie.scheduler.ui.SchedulingDialog;
import org.talend.oozie.scheduler.utils.OozieSchedulerStringUtils;

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

    private String jobIdInOozie;

    private AbstractMultiPageTalendEditor multiPageTalendEditor;

    boolean isRunBtnEnabled = false;// Identify if the "Run" button is enabled.

    boolean isScheduleBtnEnabled = false;// Identify if the "Schedule" button is enabled.

    boolean isKillBtnEnabled = false;// Identify if the "Kill" button is enabled.

    // public static final String JOB_FQ_CN_NAME = "marvinproject.marvinjob_0_1.MarvinJob";

    private RemoteJobSubmission remoteJobSubmission;

    private ScheduledJobSubmission scheduledJobSubmission;

    private Map<String, String> tracesForOozie;// Key is job id, and value is result of logging.

    public ExecuteJobCompositeController(ExecuteJobComposite executeJobComposite) {
        tracesForOozie = new HashMap<String, String>();
        this.executeJobComposite = executeJobComposite;
    }

    public void initValues() {
        Text pathText = executeJobComposite.getPathText();
        Text outputText = executeJobComposite.getOutputTxt();
        if (!pathText.isDisposed() && !outputText.isDisposed()) {
            if (multiPageTalendEditor != null) {
                IProcess2 process = multiPageTalendEditor.getProcess();
                jobIdInOozie = (String) process.getElementParameter("JOBID_FOR_OOZIE").getValue();

                String appPath = (String) process.getElementParameter("HADOOP_APP_PATH").getValue();
                pathText.setText(appPath);
                // update output value for each editor.
                String outputValue = tracesForOozie.get(process.getId());
                outputText.setText(outputValue == null ? "" : outputValue);
                updatePathTxtEnabledOrNot();
                updateOutputTxtEnabledOrNot();
                updateOutputTxtValue();
            } else {
                pathText.setText("");
                outputText.setText("");
                updatePathTxtEnabledOrNot();
                updateOutputTxtEnabledOrNot();
            }
        }
    }

    protected void updateOutputTxtValue() {
        Text outputText = executeJobComposite.getOutputTxt();
        if (jobIdInOozie != null && !"".equals(jobIdInOozie)) {
            StringBuffer trace = new StringBuffer("");
            if (remoteJobSubmission == null)
                remoteJobSubmission = new RemoteJobSubmission();
            try {
                OozieClient oozieClient = new OozieClient(getOozieFromPreference());
                JobSubmission.Status status = remoteJobSubmission.status(jobIdInOozie, getOozieFromPreference());
                switch (status) {
                case RUNNING:
                    trace.append("Job is running...");
                    trace.append("\n");
                    trace.append(oozieClient.getJobInfo(jobIdInOozie));
                    tracesForOozie.put(multiPageTalendEditor.getProcess().getId(), trace.toString());
                    outputText.setText(trace.toString());
                    break;
                default:
                    tracesForOozie.remove(multiPageTalendEditor.getProcess().getId());
                    outputText.setText("");
                }
            } catch (JobSubmissionException e) {
                e.printStackTrace();
            } catch (OozieClientException e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * When clicking the "Schedule" button, this action will open a dialog to provide some configurations for
     * scheduling. If <code>Window.OK == Dialog.open</code>, will invoke the method.
     */
    public void doScheduleAction() {
        Shell shell = executeJobComposite.getShell();
        schedulingDialog = new SchedulingDialog(shell);
        initScheduling(schedulingDialog);
        if (Window.OK == schedulingDialog.open()) {
            try {
                OozieJobDeployer.deployJob(multiPageTalendEditor.getProcess(), new NullProgressMonitor());
                JobContext jobContext = initJobCotextForOozie(JobSubmissionType.SCHEDULED);
                OozieClient oozieClient = new OozieClient(getOozieFromPreference());

                updateAllEnabledOrNot(JobSubmission.Status.PREP);
                jobIdInOozie = doScheduleJob(jobContext);
                outputScheduleJobLogs(oozieClient, jobIdInOozie);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    protected void initScheduling(SchedulingDialog schedulingDialog) {
        schedulingDialog.setFrequencyValue("1");
        schedulingDialog.setTimeUnitItemValues(SchedulerForHadoopConstants.TIME_UNIT_VALUES);
    }

    private String doScheduleJob(final JobContext jobContext) {
        new Runnable() {

            @Override
            public void run() {
                scheduledJobSubmission = new ScheduledJobSubmission();
                try {
                    jobIdInOozie = scheduledJobSubmission.submit(jobContext);
                } catch (JobSubmissionException e) {
                    e.printStackTrace();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                } catch (URISyntaxException e) {
                    e.printStackTrace();
                }
            }
        }.run();
        return jobIdInOozie;
    }

    private void outputScheduleJobLogs(final OozieClient oozieClient, final String jobIdFromOozie) {
        new Thread() {

            public void run() {
                StringBuffer output = new StringBuffer(OutputMessages.MSG_OUTPUT_RUNNING);
                output.append(OutputMessages.LINE_BREAK_CHAR);
                try {
                    // JobSubmission.Status status = scheduledJobSubmission.status(scheduledJobHandle,
                    // jobContext.getOozieEndPoint());
                    while (JobSubmission.Status.PREP == scheduledJobSubmission.status(jobIdFromOozie, getOozieFromPreference())) {
                        output.append("Job is preparing to run...");
                        output.append(OutputMessages.LINE_BREAK_CHAR);
                        output.append(oozieClient.getCoordJobInfo(jobIdFromOozie));
                        output.append(OutputMessages.LINE_BREAK_CHAR);
                        updateOutputTextContents(output.toString());
                        Thread.sleep(1000 * 2);
                    }

                    while (JobSubmission.Status.RUNNING == scheduledJobSubmission
                            .status(jobIdFromOozie, getOozieFromPreference())) {
                        output.append("Job is running...");
                        output.append(OutputMessages.LINE_BREAK_CHAR);
                        output.append(oozieClient.getJobInfo(jobIdFromOozie));
                        output.append(OutputMessages.LINE_BREAK_CHAR);
                        updateOutputTextContents(output.toString());
                        Thread.sleep(1000 * 2);
                    }
                    output.append(Messages.getString("MSG_output_complete", new Object[] { jobIdFromOozie,
                            oozieClient.getJobInfo(jobIdFromOozie).getStatus() }));
                    output.append(OutputMessages.LINE_BREAK_CHAR);
                    updateAllEnabledOrNot(JobSubmission.Status.SUCCEEDED);
                    updateOutputTextContents(output.toString());
                } catch (OozieClientException e) {
                    e.printStackTrace();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                } catch (JobSubmissionException e) {
                    updateAllEnabledOrNot(JobSubmission.Status.KILLED);
                    e.printStackTrace();
                }
            }
        }.start();
    }

    /**
     * When clicking the "Run" button, this action will be invoked.<br>
     * Workflow job state valid transitions: <li>--> PREP <li>PREP --> RUNNING | KILLED <li>RUNNING --> SUSPENDED |
     * SUCCEEDED | KILLED | FAILED <li>SUSPENDED --> RUNNING | KILLED
     */
    public void doRunAction() {
        try {
            // OozieJobDeployer.deployJob(multiPageTalendEditor.getProcess(), new NullProgressMonitor());
            // JobContext jobContext = initJobCotextForOozie(JobSubmissionType.REMOTE);
            // updateAllEnabledOrNot(JobSubmission.Status.RUNNING);
            // jobIdInOozie = doRunJob(jobContext);
            // outputLogs(oozieClient, jobIdInOozie);
            JobContext jobContext = initJobCotextForOozie(JobSubmissionType.REMOTE);
            updateAllEnabledOrNot(JobSubmission.Status.RUNNING);
            doRunJob(jobContext);
        } catch (Exception e) {
            e.printStackTrace();
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
                            startupRemoteJob(output);
                            monitor.worked(10);

                            monitor.subTask(OutputMessages.MSG_OUTPUT_DEPLOYING);
                            deployJobOnHadoop(output);
                            monitor.worked(30);

                            status = runRemoteJob(monitor, jobContext, output);

                            return status;
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        } catch (JobSubmissionException e) {
                            e.printStackTrace();
                        } catch (OozieClientException e) {
                            e.printStackTrace();
                        } catch (URISyntaxException e) {
                            e.printStackTrace();
                        } catch (OozieJobDeployException e) {
                            e.printStackTrace();
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

    private void startupRemoteJob(StringBuffer output) {
        output.append(OutputMessages.MSG_OUTPUT_STARTUP);
        output.append(OutputMessages.LINE_BREAK_CHAR);
        updateOutputTextContents(output.toString());
    }

    private void deployJobOnHadoop(StringBuffer output) throws OozieJobDeployException {
        output.append(OutputMessages.MSG_OUTPUT_DEPLOYING);
        output.append(OutputMessages.LINE_BREAK_CHAR);
        try {
            OozieJobDeployer.deployJob(multiPageTalendEditor.getProcess(), new NullProgressMonitor());
            output.append(OutputMessages.MSG_OUTPUT_DEPLOY_COMPLETE);
            output.append(OutputMessages.LINE_BREAK_CHAR);
        } catch (OozieJobDeployException e) {
            output.append(OutputMessages.MSG_OUTPUT_DEPLOY_FAILED);
            output.append(OutputMessages.LINE_BREAK_CHAR);
            throw e;
        }
        updateOutputTextContents(output.toString());
    }

    private IStatus runRemoteJob(IProgressMonitor monitor, JobContext jobContext, StringBuffer output)
            throws JobSubmissionException, InterruptedException, URISyntaxException, OozieClientException {
        // OozieClient oozieClient = new OozieClient(getOozieFromPreference());
        monitor.subTask(OutputMessages.MSG_OUTPUT_RUNNING);

        output.append(OutputMessages.MSG_OUTPUT_RUNNING);
        output.append(OutputMessages.LINE_BREAK_CHAR);
        updateOutputTextContents(output.toString());

        remoteJobSubmission = new RemoteJobSubmission();
        jobIdInOozie = remoteJobSubmission.submit(jobContext);

        remoteJobSubmission = new RemoteJobSubmission();
        jobIdInOozie = remoteJobSubmission.submit(jobContext);
        if (monitor.isCanceled()) {
            output.append(">> The running job is canceled!");
            updateOutputTextContents(output.toString());
            return Status.CANCEL_STATUS;
        }
        monitor.worked(10);
        monitor.subTask("Logging...");
        // outputLogs(oozieClient, jobIdInOozie);
        while (JobSubmission.Status.RUNNING == remoteJobSubmission.status(jobIdInOozie, getOozieFromPreference())) {
            // output.append(oozieClient.getJobInfo(jobIdInOozie));
            // output.append(OutputMessages.LINE_BREAK_CHAR);
            // if (multiPageTalendEditor != null)
            // updateOutputTextContents(output.toString());
            Thread.sleep(1000 * 2);
        }
        switch (remoteJobSubmission.status(jobIdInOozie, getOozieFromPreference())) {
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
        updateJobIdInOozieForJob(null);
        updateAllEnabledOrNot(remoteJobSubmission.status(jobIdInOozie, getOozieFromPreference()));
        if (multiPageTalendEditor != null) {
            tracesForOozie.put(multiPageTalendEditor.getProcess().getId(), output.toString());
            updateOutputTextContents(output.toString());
        }
        monitor.worked(50);
        return Status.OK_STATUS;
    }

    @SuppressWarnings("unused")
    private String doRunJob_Copy(final JobContext jobContext) {
        new Runnable() {

            @Override
            public void run() {
                remoteJobSubmission = new RemoteJobSubmission();
                try {
                    jobIdInOozie = remoteJobSubmission.submit(jobContext);

                    // updateAllEnabledOrNot();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                } catch (URISyntaxException e) {
                    e.printStackTrace();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }.run();
        return jobIdInOozie;
    }

    @SuppressWarnings("unused")
    private void outputLogs(final OozieClient oozieClient, final String jobIdFromOozie) {
        new Thread() {

            public void run() {
                StringBuffer output = new StringBuffer(OutputMessages.MSG_OUTPUT_RUNNING);
                output.append(OutputMessages.LINE_BREAK_CHAR);
                try {
                    while (JobSubmission.Status.RUNNING == remoteJobSubmission.status(jobIdFromOozie, getOozieFromPreference())) {
                        output.append(oozieClient.getJobInfo(jobIdFromOozie));
                        output.append(OutputMessages.LINE_BREAK_CHAR);
                        if (multiPageTalendEditor != null)
                            updateOutputTextContents(output.toString());
                        Thread.sleep(1000 * 2);
                    }
                    updateJobIdInOozieForJob(null);
                    output.append(Messages.getString("MSG_output_complete", new Object[] { jobIdFromOozie,
                            oozieClient.getJobInfo(jobIdFromOozie).getStatus() }));
                    output.append(OutputMessages.LINE_BREAK_CHAR);
                    updateAllEnabledOrNot(remoteJobSubmission.status(jobIdFromOozie, getOozieFromPreference()));
                    if (multiPageTalendEditor != null) {
                        tracesForOozie.put(multiPageTalendEditor.getProcess().getId(), output.toString());
                        updateOutputTextContents(output.toString());
                    }
                } catch (OozieClientException e) {
                    e.printStackTrace();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                } catch (JobSubmissionException e) {
                    e.printStackTrace();
                }
            }
        }.start();
    }

    /**
     * Updates the contents of the widget "Output" using logs and status.
     * 
     * @param output
     */
    private void updateOutputTextContents(final String output) {
        if (multiPageTalendEditor != null)
            executeJobComposite.getDisplay().asyncExec(new Runnable() {

                @Override
                public void run() {
                    Text outputTxt = executeJobComposite.getOutputTxt();
                    outputTxt.setText("");
                    outputTxt.setText(output);
                    int lines = outputTxt.getLineCount();
                    outputTxt.setTopIndex(lines);
                }

            });
    }

    /**
     * Workflow job state valid transitions: <li>--> PREP <li>PREP --> RUNNING | KILLED <li>RUNNING --> SUSPENDED |
     * SUCCEEDED | KILLED | FAILED <li>SUSPENDED --> RUNNING | KILLED
     * 
     * @param status
     */
    protected void updateAllEnabledOrNot(final JobSubmission.Status status) {
        executeJobComposite.getDisplay().asyncExec(new Runnable() {

            public void run() {
                Button runBtn = executeJobComposite.getRunBtn();
                Button scheduleBtn = executeJobComposite.getScheduleBtn();
                Button killBtn = executeJobComposite.getKillBtn();
                Text pathTxt = executeJobComposite.getPathText();
                Text outputTxt = executeJobComposite.getOutputTxt();
                switch (status) {
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
        });
    }

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
            if (oozieClient.getJobInfo(jobIdInOozie).getStatus() == WorkflowJob.Status.RUNNING) {
                output.append(executeJobComposite.getOutputTxt().getText());
                oozieClient.kill(jobIdInOozie);
                updateJobIdInOozieForJob(null);

                // output.append(OutputMessages.MSG_OUTPUT_KILL);
                // output.append(OutputMessages.LINE_BREAK_CHAR);
                executeJobComposite.getKillBtn().setEnabled(false);
                // if (multiPageTalendEditor != null)
                // updateOutputTextContents(output.toString());
            } else if (oozieClient.getJobInfo(jobIdInOozie).getStatus() == WorkflowJob.Status.SUCCEEDED) {
                executeJobComposite.getRunBtn().setEnabled(true);
                executeJobComposite.getKillBtn().setEnabled(false);
                // todo add schedule
            } else if (oozieClient.getJobInfo(jobIdInOozie).getStatus() == WorkflowJob.Status.KILLED) {

            }
        } catch (OozieClientException e) {
            e.printStackTrace();
        }

    }

    public CommandStack getCommandStack() {
        return multiPageTalendEditor == null ? null : (CommandStack) (multiPageTalendEditor.getTalendEditor()
                .getAdapter(CommandStack.class));
    }

    protected void updateJobIdInOozieForJob(final String jobIdFromOozie) {
        executeJobComposite.getDisplay().asyncExec(new Runnable() {

            @Override
            public void run() {
                if (multiPageTalendEditor != null) {
                    IProcess2 process = multiPageTalendEditor.getProcess();
                    getCommandStack().execute(
                            new PropertyChangeCommand(process, "JOBID_FOR_OOZIE", jobIdFromOozie == null ? "" : jobIdFromOozie));
                }
            }
        });
    }

    protected String getJobIdInOozie() {
        if (multiPageTalendEditor != null) {
            IProcess2 process = multiPageTalendEditor.getProcess();
            jobIdInOozie = (String) process.getElementParameter("JOBID_FOR_OOZIE").getValue();
        }
        return jobIdInOozie;
    }

    @SuppressWarnings("unused")
    private String runOozieClient(final OozieClient oozieClient) throws OozieClientException {
        oozieClient.setDebugMode(1);
        // create a workflow job configuration and set the workflow application path
        final Properties configuration = oozieClient.createConfiguration();
        IProcess2 process = multiPageTalendEditor.getProcess();
        String appPath = (String) process.getElementParameter("HADOOP_APP_PATH").getValue();

        configuration.setProperty(OozieClient.APP_PATH,
                new org.eclipse.core.runtime.Path(getNameNodeFromPreference()).append(appPath).toPortableString());
        configuration.setProperty(OozieClient.USER_NAME, "hdp");
        // submit and start the workflow job

        // try {
        // jobIdInOozie = oozieClient.run(configuration);
        // System.out.println("Workflow job submitted: " + jobIdInOozie);
        // } catch (OozieClientException e) {
        // e.printStackTrace();
        // }

        return jobIdInOozie;
    }

    /**
     * 
     * @return
     */
    private JobContext initJobCotextForOozie(JobSubmissionType jobSubType) {
        JobContext jobContext = new JobContext();
        // Job name.
        String jobName = multiPageTalendEditor.getProcess().getLabel();
        // String jobName = "MavinJob";
        jobContext.setJobName(jobName);

        // Job path on HDFS
        String path = executeJobComposite.getPathValue();
        // IProcess2 process = multiPageTalendEditor.getProcess();
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
        // String wfAppPathValue = getAppPathFromPreference();

        // User Name for acessing hadoop
        String userNameValue = getUserNameForHadoopFromPreference();
        jobContext.set(OozieClient.USER_NAME, userNameValue);

        // TOS job
        String tosJobFQCN = getTOSJobFQCNValue();
        jobContext.setJobFQClassName(tosJobFQCN);

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

    @SuppressWarnings("unused")
    private String getAppPathFromPreference() {
        String path = CorePlugin.getDefault().getPreferenceStore().getString(ITalendCorePrefConstants.OOZIE_SCHEDULER_PATH);
        return path;
    }

    private String getUserNameForHadoopFromPreference() {
        String userNameValue = CorePlugin.getDefault().getPreferenceStore()
                .getString(ITalendCorePrefConstants.OOZIE_SCHEDULER_USER_NAME);
        return userNameValue;
    }

    private String getTOSJobFQCNValue() {
        IProcess2 process = multiPageTalendEditor.getProcess();
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
            updateAllEnabledOrNot();
            // To update the values of Oozie preference page
            updateOoziePreferencePageValues();
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
        if (multiPageTalendEditor != null) {
            String path = executeJobComposite.getPathValue();
            IProcess2 process = multiPageTalendEditor.getProcess();
            process.getElementParameter("HADOOP_APP_PATH").setValue(path);
        }
        // checkWidgetsStatus();
        updateAllEnabledOrNot();
    }

    /**
     * When clicking the button named "Monitoring", this method will be invoked.
     */
    public void doMonitoringBtnAction() {
        try {
            IWorkbenchBrowserSupport support = PlatformUI.getWorkbench().getBrowserSupport();

            IWebBrowser browser = support.getExternalBrowser();
            // Open SWT brower in Eclipse.
            // IWebBrowser browser = support.createBrowser("id");
            browser.openURL(new URL(getOozieFromPreference()));
        } catch (PartInitException e) {
        } catch (MalformedURLException e) {
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
                    e.printStackTrace();
                }
            }
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
                    e.printStackTrace();
                }
            }
            isScheduleBtnEnabled = true;
        }
        return isScheduleBtnEnabled;
    }

    public boolean checkIfKillBtnEnabled() {
        if (jobIdInOozie != null && !"".equals(jobIdInOozie)) {
            try {
                JobSubmission.Status status = checkJobSubmissionStaus(jobIdInOozie);
                switch (status) {
                case PREP:
                case RUNNING:
                case SUSPENDED:
                    isScheduleBtnEnabled = false;
                    break;
                case SUCCEEDED:
                case FAILED:
                    isScheduleBtnEnabled = true;
                    break;
                }
            } catch (JobSubmissionException e) {
                isScheduleBtnEnabled = true;
                e.printStackTrace();
            }
        }
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
        if (multiPageTalendEditor == null)
            runBtn.setEnabled(false);
        else
            runBtn.setEnabled(checkIfRunBtnEnabled());
    }

    protected void updateScheduleBtnEnabledOrNot() {
        Button scheduleBtn = executeJobComposite.getScheduleBtn();
        if (scheduleBtn.isDisposed())
            return;
        if (multiPageTalendEditor == null)
            scheduleBtn.setEnabled(false);
        else
            scheduleBtn.setEnabled(checkIfScheduleBtnEnabled());
    }

    protected void updateKillBtnEnabledOrNot() {
        Button killBtn = executeJobComposite.getKillBtn();
        if (killBtn.isDisposed())
            return;
        if (multiPageTalendEditor == null)
            killBtn.setEnabled(false);
        else
            killBtn.setEnabled(checkIfKillBtnEnabled());
    }

    protected void updatePathTxtEnabledOrNot() {
        Text pathTxt = executeJobComposite.getPathText();
        if (pathTxt.isDisposed())
            return;
        if (multiPageTalendEditor == null) {
            pathTxt.setEnabled(false);
        } else {
            pathTxt.setEnabled(true);
        }

    }

    protected void updateOutputTxtEnabledOrNot() {
        Text outputTxt = executeJobComposite.getOutputTxt();
        if (outputTxt.isDisposed())
            return;
        if (multiPageTalendEditor == null) {
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

    public AbstractMultiPageTalendEditor getMultiPageTalendEditor() {
        return this.multiPageTalendEditor;
    }

    public void setMultiPageTalendEditor(AbstractMultiPageTalendEditor multiPageTalendEditor) {
        this.multiPageTalendEditor = multiPageTalendEditor;
        if (multiPageTalendEditor != null) {
            IProcess2 process = multiPageTalendEditor.getProcess();
            String appPath = (String) process.getElementParameter("HADOOP_APP_PATH").getValue();
            executeJobComposite.setPathValue(appPath); // "/user/hdp/etl/talend/MarvinJob_0.1");
        }
    }

    /**
     * This method is called when job editor is closed.
     */
    public void removeTrace(String jobId) {
        if (tracesForOozie != null)
            tracesForOozie.remove(jobId);
    }

    public void clearAllTraces() {
        if (tracesForOozie != null)
            tracesForOozie.clear();
    }
}
