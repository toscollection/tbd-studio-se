package com.talend.tuj.generator.processors;

import com.talend.tuj.generator.elements.IElement;
import com.talend.tuj.generator.elements.JobElement;
import com.talend.tuj.generator.utils.ComponentClass;
import com.talend.tuj.generator.utils.JobFramework;
import com.talend.tuj.generator.utils.JobType;
import com.talend.tuj.generator.utils.NodeType;

public class GenericDistributionConfigurationProcessor extends IProcessor {
    private String distribution_name;
    private String distribution_version;

    public GenericDistributionConfigurationProcessor(String distribution_name, String distribution_version) {
        this.distribution_version = distribution_version;
        this.distribution_name = distribution_name;
    }

    @Override
    public boolean shouldBeProcessed(IElement component) {
        if(component.isOfClass(ComponentClass.Job)){
            JobElement jobComponent = (JobElement)component;
            return !jobComponent.isJobOfType(JobType.STANDARD) && jobComponent.isOfType(NodeType.JOBCONFIG);
        }
        return false;
    }

    @Override
    public void process(IElement component) {
        JobElement jobComponent = (JobElement)component;
        jobComponent.replaceParameter("DISTRIBUTION", distribution_name);

        if (jobComponent.isJobOfFramework(JobFramework.MAPREDUCE))
            jobComponent.replaceParameter("MR_VERSION", distribution_version);
        else jobComponent.replaceParameter("SPARK_VERSION", distribution_version);
    }
}
