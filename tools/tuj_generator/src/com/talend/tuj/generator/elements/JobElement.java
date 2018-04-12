package com.talend.tuj.generator.elements;

import com.talend.tuj.generator.utils.ComponentClass;
import com.talend.tuj.generator.utils.Job;
import com.talend.tuj.generator.utils.JobFramework;
import com.talend.tuj.generator.utils.JobType;
import org.w3c.dom.Node;

public class JobElement extends AbstractElement {
    protected Job job;

    public JobElement(Node node, Job job) {
        super(node);
        this.job = job;
    }

    public boolean isJobOfType(JobType type) {
        return job.getType().equals(type);
    }

    public boolean isJobOfFramework(JobFramework type) {
        return job.getFramework().equals(type);
    }

    public Job getParentJob() {
        return job;
    }

    @Override
    public boolean isOfClass(ComponentClass compClass) {
        return compClass.equals(ComponentClass.Job);
    }
}
