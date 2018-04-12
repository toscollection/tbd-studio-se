package com.talend.tuj.generator.utils.jobdatafinder;

import com.talend.tuj.generator.utils.Job;
import com.talend.tuj.generator.utils.JobFramework;
import com.talend.tuj.generator.utils.JobType;

import java.util.NoSuchElementException;

public class JobDataFinderPathStrategy implements JobDataFinderStrategy {
    @Override
    public JobFramework findJobFramework(Job job) {
        return JobFramework.NONE;
    }

    @Override
    public JobType findJobType(Job job) {
        try{
            if(job.getOriginalPath().toString().contains("process_mr")) return JobType.BIG_DATA_BATCH;
            else if(job.getOriginalPath().toString().contains("process_storm")) return JobType.BIG_DATA_STREAMING;
            else if(job.getOriginalPath().toString().contains("process")) return JobType.STANDARD;
        } catch (NoSuchElementException ignored){
        }
        return JobType.NONE;
    }
}
