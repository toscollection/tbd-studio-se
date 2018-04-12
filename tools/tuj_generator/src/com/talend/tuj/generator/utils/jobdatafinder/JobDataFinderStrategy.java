package com.talend.tuj.generator.utils.jobdatafinder;

import com.talend.tuj.generator.utils.Job;
import com.talend.tuj.generator.utils.JobFramework;
import com.talend.tuj.generator.utils.JobType;

public interface JobDataFinderStrategy {
    JobFramework findJobFramework(Job job);
    JobType findJobType(Job job);
}
