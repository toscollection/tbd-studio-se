package com.talend.tuj.generator.processors;

import com.talend.tuj.generator.elements.IElement;
import com.talend.tuj.generator.elements.JobElement;
import com.talend.tuj.generator.utils.ComponentClass;
import com.talend.tuj.generator.utils.JobFramework;
import com.talend.tuj.generator.utils.NodeType;

public class SparkConfigurationLocalSparkProcessor extends IProcessor {
    private String distribution_version;

    public SparkConfigurationLocalSparkProcessor(String local_spark_version) {
        this.distribution_version = local_spark_version;
    }

    @Override
    public boolean shouldBeProcessed(IElement component) {
        if(component.isOfClass(ComponentClass.Job)){
            JobElement jobComponent = (JobElement)component;
            return (jobComponent.isJobOfFramework(JobFramework.SPARK) || jobComponent.isJobOfFramework(JobFramework.SPARK_STREAMING)) && jobComponent.isOfType(NodeType.JOBCONFIG);
        }
        return false;
    }

    @Override
    public void process(IElement component) {
        try {
            component.replaceParameter("SPARK_LOCAL_MODE", "true");
            component.replaceParameter("SPARK_LOCAL_VERSION", distribution_version);
        } catch (NullPointerException ignored) {
        }
    }
}
