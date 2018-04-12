package com.talend.tuj.generator.utils.jobdatafinder;

import com.talend.tuj.generator.utils.Job;
import com.talend.tuj.generator.utils.JobFramework;
import com.talend.tuj.generator.utils.JobType;

public class JobDataFinderImplicitStrategy implements JobDataFinderStrategy {
    @Override
    public JobFramework findJobFramework(Job job) {
        return JobFramework.NONE;
    }

    @Override
    public JobType findJobType(Job job) {
        switch (job.getFramework()){
            case MAPREDUCE:
            case SPARK:
                return JobType.BIG_DATA_BATCH;
            case SPARK_STREAMING:
                return JobType.BIG_DATA_STREAMING;
            default:
                return JobType.NONE;
        }
    }
}
