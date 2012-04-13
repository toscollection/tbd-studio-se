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

import java.io.IOException;
import java.net.URISyntaxException;
import java.util.Date;
import java.util.Properties;

import org.apache.oozie.client.OozieClient;
import org.apache.oozie.client.OozieClientException;
import org.apache.oozie.client.WorkflowJob;
import org.eclipse.core.runtime.NullProgressMonitor;
import org.eclipse.gef.commands.CommandStack;
import org.eclipse.jface.window.Window;
import org.eclipse.swt.widgets.Shell;
import org.talend.core.CorePlugin;
import org.talend.core.model.process.IProcess2;
import org.talend.core.model.utils.JavaResourcesHelper;
import org.talend.core.prefs.ITalendCorePrefConstants;
import org.talend.designer.core.ui.AbstractMultiPageTalendEditor;
import org.talend.designer.core.ui.editor.cmd.PropertyChangeCommand;
import org.talend.oozie.scheduler.constants.JobSubmissionType;
import org.talend.oozie.scheduler.constants.OutputMessages;
import org.talend.oozie.scheduler.constants.SchedulerForHadoopConstants;
import org.talend.oozie.scheduler.constants.WidgetStatusType;
import org.talend.oozie.scheduler.i18n.Messages;
import org.talend.oozie.scheduler.jobdeployer.OozieJobDeployer;
import org.talend.oozie.scheduler.ui.ExecuteJobComposite;
import org.talend.oozie.scheduler.ui.OozieShcedulerSettingDialog;
import org.talend.oozie.scheduler.ui.SchedulingDialog;
import org.talend.oozie.scheduler.utils.OozieSchedulerStringUtils;

import com.hortonworks.etl.talend.JobContext;
import com.hortonworks.etl.talend.JobContext.Timeunit;
import com.hortonworks.etl.talend.JobSubmissionException;
import com.hortonworks.etl.talend.oozie.JavaAction;
import com.hortonworks.etl.talend.oozie.RemoteJobSubmission;
import com.hortonworks.etl.talend.oozie.ScheduledJobSubmission;
import com.hortonworks.etl.talend.oozie.Workflow;

/**
 * Created by Marvin Wang on Mar. 31, 2012 for doing some action from the widgets of
 * {@link ExecuteJobCompositeController}, all businesses are handled here.
 */
public class ExecuteJobCompositeController {

    private ExecuteJobComposite executeJobComposite;

    private SchedulingDialog schedulingDialog;

    private OozieShcedulerSettingDialog settingDialog;

    private boolean isSettingDone = false;// To check if the values of setting are done.

    private OozieClient oozieClient;

    private String jobIdInOozie;

    private AbstractMultiPageTalendEditor multiPageTalendEditor;

    // public static final String JOB_FQ_CN_NAME = "marvinproject.marvinjob_0_1.MarvinJob";

    public ExecuteJobCompositeController(ExecuteJobComposite executeJobComposite) {
        this.executeJobComposite = executeJobComposite;
    }

    public void init() {
        if (multiPageTalendEditor != null) {
            IProcess2 process = multiPageTalendEditor.getProcess();
            jobIdInOozie = (String) process.getElementParameter("JOBID_FOR_OOZIE").getValue();
        }
    }

    /**
     * When clicking the "Schedule" button, this action will open a dialog to provide some configurations for
     * scheduling. If <code>Window.OK == Dialog.open</code>, will invoke the method
     */
    public void doScheduleAction() {
        Shell shell = executeJobComposite.getShell();
        schedulingDialog = new SchedulingDialog(shell);
        initScheduling(schedulingDialog);
        if (Window.OK == schedulingDialog.open()) {
            JobContext jobContext = initJobCotextForOozie(JobSubmissionType.SCHEDULED);
            OozieClient oozieClient = new OozieClient(getOozieFromPreference());
            jobIdInOozie = doScheduleJob(jobContext);
            outputScheduleJobLogs(oozieClient, jobIdInOozie);
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
                ScheduledJobSubmission scheduledJobSubmission = new ScheduledJobSubmission();
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
                    while (oozieClient.getJobInfo(jobIdFromOozie).getStatus() == WorkflowJob.Status.RUNNING) {
                        output.append(oozieClient.getJobInfo(jobIdFromOozie));
                        output.append(OutputMessages.LINE_BREAK_CHAR);
                        updateOutputTextContents(output.toString());
                        Thread.sleep(1000 * 2);
                    }
                    output.append(Messages.getString("MSG_output_complete", new Object[] { jobIdFromOozie,
                            oozieClient.getJobInfo(jobIdFromOozie).getStatus() }));
                    output.append(OutputMessages.LINE_BREAK_CHAR);
                    updateOutputTextContents(output.toString());
                } catch (OozieClientException e) {
                    e.printStackTrace();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }.start();
    }

    /**
     * When clicking the "Run" button, this action will be invoked.
     */
    public void doRunAction() {
        try {
            OozieJobDeployer.deployJob(multiPageTalendEditor.getProcess(), new NullProgressMonitor());
            JobContext jobContext = initJobCotextForOozie(JobSubmissionType.REMOTE);
            OozieClient oozieClient = new OozieClient(getOozieFromPreference());
            executeJobComposite.checkBtnsEnabled();

            jobIdInOozie = doRunJob(jobContext);
            outputLogs(oozieClient, jobIdInOozie);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private String doRunJob(final JobContext jobContext) {
        new Runnable() {

            @Override
            public void run() {
                RemoteJobSubmission remoteJobSubmission = new RemoteJobSubmission();
                try {
                    try {
                        jobIdInOozie = remoteJobSubmission.submit(jobContext);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    } catch (URISyntaxException e) {
                        e.printStackTrace();
                    }
                } catch (JobSubmissionException e) {
                    e.printStackTrace();
                }
            }
        }.run();
        return jobIdInOozie;
    }

    private void outputLogs(final OozieClient oozieClient, final String jobIdFromOozie) {
        new Thread() {

            public void run() {
                StringBuffer output = new StringBuffer(OutputMessages.MSG_OUTPUT_RUNNING);
                output.append(OutputMessages.LINE_BREAK_CHAR);
                try {
                    while (oozieClient.getJobInfo(jobIdFromOozie).getStatus() == WorkflowJob.Status.RUNNING) {
                        output.append(oozieClient.getJobInfo(jobIdFromOozie));
                        output.append(OutputMessages.LINE_BREAK_CHAR);
                        updateOutputTextContents(output.toString());
                        Thread.sleep(1000 * 2);
                    }
                    updateJobIdInOozieForJob(null);
                    output.append(Messages.getString("MSG_output_complete", new Object[] { jobIdFromOozie,
                            oozieClient.getJobInfo(jobIdFromOozie).getStatus() }));
                    output.append(OutputMessages.LINE_BREAK_CHAR);
                    updateOutputTextContents(output.toString());
                } catch (OozieClientException e) {
                    e.printStackTrace();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }.start();
    }

    public CommandStack getCommandStack() {
        return multiPageTalendEditor == null ? null : (CommandStack) (multiPageTalendEditor.getTalendEditor()
                .getAdapter(CommandStack.class));
    }

    protected void updateJobIdInOozieForJob(String jobIdFromOozie) {
        if (multiPageTalendEditor != null) {
            IProcess2 process = multiPageTalendEditor.getProcess();
            getCommandStack().execute(
                    new PropertyChangeCommand(process, "JOBID_FOR_OOZIE", jobIdFromOozie == null ? "" : jobIdFromOozie));
        }
    }

    protected String getJobIdInOozie() {
        if (multiPageTalendEditor != null) {
            IProcess2 process = multiPageTalendEditor.getProcess();
            jobIdInOozie = (String) process.getElementParameter("JOBID_FOR_OOZIE").getValue();
        }
        return jobIdInOozie;
    }

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

    private JavaAction setupJavaAction(JobContext context) {
        JavaAction action = new JavaAction(context.getJobName(), context.getJobTrackerEndPoint(), context.getNameNodeEndPoint(),
                context.getJobFQClassName());
        // action.setConfiguration("blah", "blah");
        // action.setConfiguration("foo", "bar");

        return action;
    }

    /**
     * Deploying work flow includes creating a workfolw and storing the workflow into the workflow.xml on HDFS.
     * 
     * @param context
     * @param action
     * @return
     * @throws IOException
     * @throws URISyntaxException
     * @throws InterruptedException
     */
    private Workflow deployWorkFlow(JobContext context, JavaAction action) throws IOException, InterruptedException,
            URISyntaxException {
        Workflow workflow = new Workflow(context.getJobName(), action);

        serializeWorkflowToHDFS(workflow, context);

        return workflow;
    }

    private void serializeWorkflowToHDFS(Workflow workflow, JobContext context) throws IOException, InterruptedException,
            URISyntaxException {
        // org.apache.hadoop.conf.Configuration configuration = new org.apache.hadoop.conf.Configuration();
        // String nameNodeEPValue = context.getNameNodeEndPoint();
        // configuration.set("fs.default.name", nameNodeEPValue);
        // FileSystem fs = FileSystem.get(new java.net.URI(configuration.get("fs.default.name")), configuration, "hdp");
        //
        // Path wfFile = new Path(context.getJobPathOnHDFS() + "/workflow.xml");
        // FSDataOutputStream outputStream = null;
        // try {
        // outputStream = fs.create(wfFile);
        // outputStream.writeBytes(workflow.toXMLString());
        // } finally {
        // if (outputStream != null) {
        // outputStream.close();
        // }
        // }
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
     * Updates the contents of the widget "Output" using logs and status.
     * 
     * @param output
     */
    private void updateOutputTextContents(final String output) {
        executeJobComposite.getDisplay().asyncExec(new Runnable() {

            @Override
            public void run() {
                executeJobComposite.getOutputTxt().setText("");
                executeJobComposite.getOutputTxt().setText(output);
                executeJobComposite.getOutputTxt().update();
            }

        });
    }

    public void doKillAction() {
        try {
            oozieClient = new OozieClient(getOozieFromPreference());
            StringBuffer output = new StringBuffer("");
            if (oozieClient.getJobInfo(jobIdInOozie).getStatus() == WorkflowJob.Status.RUNNING) {
                output.append(executeJobComposite.getOutputTxt().getText());
                oozieClient.kill(jobIdInOozie);
                updateJobIdInOozieForJob(null);

                output.append(Messages.getString("MSG_output_kill"));
                output.append(OutputMessages.LINE_BREAK_CHAR);
                executeJobComposite.getKillBtn().setEnabled(true);
            } else if (oozieClient.getJobInfo(jobIdInOozie).getStatus() == WorkflowJob.Status.SUCCEEDED) {
                executeJobComposite.getRunBtn().setEnabled(true);
                executeJobComposite.getKillBtn().setEnabled(false);
                // todo add schedule
            }
        } catch (OozieClientException e) {
            e.printStackTrace();
        }

    }

    /**
     * Handles the action when clicking the button "Setting".
     */
    public void doSettingAction() {
        Shell shell = executeJobComposite.getShell();
        settingDialog = new OozieShcedulerSettingDialog(shell);
        initPreferenceSettingForJob(settingDialog);
        if (Window.OK == settingDialog.open()) {
            executeJobComposite.getRunBtn().setEnabled(isRunBtnEnabled());

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
     * Checks if the Run button is valid. Returns <code>true</code> only if meets the following conditions: <li>Path
     * value is not <code>null</code> and empty. <li>Scheduler settting is done.
     * 
     * @return
     */
    public boolean isRunBtnEnabled() {
        boolean isRunBtnValid = false;
        String pathValue = executeJobComposite.getPathValue();
        if (pathValue != null && !"".equals(pathValue) && isSettingDoneFromPreferencePage() && "".equals(jobIdInOozie))
            isRunBtnValid = true;

        return isRunBtnValid;
    }

    public boolean isScheduleBtnEnabled() {
        boolean isEnabled = false;
        String pathValue = executeJobComposite.getPathValue();
        if (pathValue != null && !"".equals(pathValue) && isSettingDoneFromPreferencePage() && "".equals(jobIdInOozie))
            isEnabled = true;
        return isEnabled;
    }

    public boolean isKillBtnEnabled() {
        boolean isKillBtnEnabled = false;
        boolean isRunBtnEnabled = isRunBtnEnabled();
        boolean isScheduleBtnEnabled = isScheduleBtnEnabled();
        if (!isRunBtnEnabled || !isScheduleBtnEnabled)
            isKillBtnEnabled = true;
        return isKillBtnEnabled;
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

    public void checkWidgetsStatus() {
        if (multiPageTalendEditor == null) {
            executeJobComposite.updateWidgetStatus(WidgetStatusType.ALL_NO_ENABLE);
        } else {
            executeJobComposite.updateWidgetStatus(WidgetStatusType.NOT_ALL_ENABLE);
        }
    }

}
