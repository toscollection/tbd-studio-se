// ============================================================================
//
// Copyright (C) 2006-2014 Talend Inc. - www.talend.com
//
// This source code is available under agreement available at
// %InstallDIR%\features\org.talend.rcp.branding.%PRODUCTNAME%\%PRODUCTNAME%license.txt
//
// You should have received a copy of the agreement
// along with this program; if not, write to Talend SA
// 9 rue Pages 92150 Suresnes, France
//
// ============================================================================
package org.talend.oozie.scheduler.jobsubmission;

import java.io.IOException;
import java.net.URISyntaxException;
import java.util.Properties;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;

import org.apache.oozie.client.OozieClient;
import org.apache.oozie.client.OozieClientException;
import org.apache.oozie.client.WorkflowJob;
import org.talend.commons.exception.ExceptionHandler;
import org.talend.oozie.scheduler.jobsubmission.model.JobContext;
import org.talend.oozie.scheduler.jobsubmission.model.JobSubmissionException;

/**
 * JobSubmission implementation that launches the ETL job on the Hadoop Cluster using Oozie workflow
 */
public class RemoteJobSubmission extends AbstractOozieJobSubmission {

    public static final int TIMEOUT = 30;

    @Override
    public String submit(JobContext jobContext) throws JobSubmissionException, InterruptedException, URISyntaxException {
        try {
            createWorkflowTemplate(jobContext);

            return doSubmit(jobContext);
        } catch (IOException e) {
            ExceptionHandler.process(e);
            throw new JobSubmissionException("Error serializing workflow xml to HDFS.", e);
        } catch (OozieClientException e) {
            ExceptionHandler.process(e);
            throw new JobSubmissionException("Error submitting workflow job to Oozie.", e);
        }
    }

    protected String doSubmit(JobContext jobContext) throws OozieClientException {
        OozieClient oozieClient = createOozieClient(jobContext.getOozieEndPoint(), jobContext.getDebug());

        String userName = jobContext.get(OozieClient.USER_NAME);
        String appPath = jobContext.get(OozieClient.APP_PATH);

        // create a workflow job configuration and set the workflow application path
        Properties configuration = oozieClient.createConfiguration();
        configuration.setProperty(OozieClient.APP_PATH, appPath);
        if (userName != null && !"".equals(userName)) { //$NON-NLS-1$
            configuration.setProperty(OozieClient.USER_NAME, userName);
        }

        // start the workflow job
        String jobHandle = oozieClient.run(configuration);
        // waitForJobCompletion(jobHandle, oozieClient);

        return jobHandle;
    }

    protected void waitForJobCompletion(String jobHandle, OozieClient oozieClient) throws OozieClientException {
        // wait until the workflow job finishes, printing the status every 10 seconds
        while (oozieClient.getJobInfo(jobHandle).getStatus() == WorkflowJob.Status.RUNNING) {
            try {
                Thread.sleep(10 * 1000);
            } catch (InterruptedException ignore) {
            }
        }
    }

    @Override
    public Status status(String jobHandle, String oozieEndPoint) throws JobSubmissionException {
        Status status = null;
        Callable<Status> statusCallable = null;
        statusCallable = getStatus(jobHandle, oozieEndPoint);
        ExecutorService executor = Executors.newSingleThreadExecutor();
        Future<Status> future = executor.submit(statusCallable);
        try {
            status = future.get(TIMEOUT, TimeUnit.SECONDS);
        } catch (Exception e) {
            future.cancel(true);
            throw new JobSubmissionException("Error getting status for job: " + jobHandle, e);
        }
        return status;
    }

    private Callable<Status> getStatus(final String jobHandle, final String oozieEndPoint) {
        return new Callable<Status>() {

            @Override
            public Status call() throws Exception {
                try {
                    OozieClient oozieClient = createOozieClient(oozieEndPoint, 0);
                    WorkflowJob workflowJob = oozieClient.getJobInfo(jobHandle);
                    if (workflowJob == null) {
                        throw new OozieClientException(OozieClientException.INVALID_INPUT, "");
                    }
                    String status = workflowJob.getStatus().name();
                    return Status.valueOf(status);
                } catch (OozieClientException e) {
                    throw new JobSubmissionException("Error getting status for job: " + jobHandle, e);
                }
            }
        };

    }
}
