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
package org.talend.oozie.scheduler.jobsubmission;

import java.io.IOException;
import java.net.URISyntaxException;
import java.util.Properties;

import org.apache.oozie.client.OozieClient;
import org.apache.oozie.client.OozieClientException;
import org.apache.oozie.client.WorkflowJob;
import org.talend.oozie.scheduler.jobsubmission.model.JobContext;
import org.talend.oozie.scheduler.jobsubmission.model.JobSubmissionException;

/**
 * JobSubmission implementation that launches the ETL job on the Hadoop Cluster using Oozie workflow
 */
public class RemoteJobSubmission extends AbstractOozieJobSubmission {

    @Override
    public String submit(JobContext jobContext) throws JobSubmissionException, InterruptedException, URISyntaxException {
        try {
            createWorkflowTemplate(jobContext);

            return doSubmit(jobContext);
        } catch (IOException e) {
            throw new JobSubmissionException("Error serializing workflow xml to HDFS.", e);
        } catch (OozieClientException e) {
            throw new JobSubmissionException("Error submitting workflow job to Oozie.", e);
        }
    }

    protected String doSubmit(JobContext jobContext) throws OozieClientException {
        // FIXME: It should be use "jobContext.getDebug()" instead of "0" but dont know why cannot reflect "getInt"
        // method of org.apache.hadoop.conf.Configuration
        OozieClient oozieClient = createOozieClient(jobContext.getOozieEndPoint(), 0);

        String userName = jobContext.get(OozieClient.USER_NAME);
        String appPath = jobContext.get(OozieClient.APP_PATH);

        // create a workflow job configuration and set the workflow application path
        Properties configuration = oozieClient.createConfiguration();
        configuration.setProperty(OozieClient.APP_PATH, appPath);
        if (userName != null && !"".equals(userName))
            configuration.setProperty(OozieClient.USER_NAME, userName);

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
        try {
            OozieClient oozieClient = createOozieClient(oozieEndPoint, 0);
            WorkflowJob workflowJob = oozieClient.getJobInfo(jobHandle);
            if (workflowJob == null)
                throw new OozieClientException(OozieClientException.INVALID_INPUT, "");
            String status = oozieClient.getJobInfo(jobHandle).getStatus().name();
            return Status.valueOf(status);
        } catch (OozieClientException e) {
            throw new JobSubmissionException("Error getting status for job: " + jobHandle, e);
        }
    }
}
