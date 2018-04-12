package com.talend.tuj.generator.utils.jobdatafinder;

import com.talend.tuj.generator.utils.Job;
import com.talend.tuj.generator.utils.JobFramework;
import com.talend.tuj.generator.utils.JobType;

public class JobDataFinderPropertyStrategy implements JobDataFinderStrategy {
    @Override
    public JobFramework findJobFramework(Job job) {
        return JobFramework.NONE;
    }

    @Override
    public JobType findJobType(Job job) {
        return JobType.NONE;
    }
}
