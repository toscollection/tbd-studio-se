package com.talend.tuj.generator.utils.jobdatafinder;

import com.talend.tuj.generator.utils.Job;
import com.talend.tuj.generator.utils.JobFramework;
import com.talend.tuj.generator.utils.JobType;
import org.w3c.dom.NamedNodeMap;

public class JobDataFinder70Strategy implements JobDataFinderStrategy {
    @Override
    public JobFramework findJobFramework(Job job) {
        try {
            NamedNodeMap attributes = job.getItem().getElementsByTagName("talendfile:ProcessType").item(0).getAttributes();
            return JobFramework.valueOf(attributes.getNamedItem("framework").getNodeValue().toUpperCase());
        } catch (NullPointerException e) {
            return JobFramework.NONE;
        }
    }

    @Override
    public JobType findJobType(Job job) {
        try {
            NamedNodeMap attributes = job.getItem().getElementsByTagName("talendfile:ProcessType").item(0).getAttributes();
            return JobType.valueOf(attributes.getNamedItem("jobType").getNodeValue().toUpperCase());
        } catch (NullPointerException e) {
            return JobType.NONE;
        }
    }
}
