package com.talend.tuj.generator.processors;

import com.talend.tuj.generator.elements.IElement;
import com.talend.tuj.generator.elements.JobElement;
import com.talend.tuj.generator.utils.ComponentClass;
import com.talend.tuj.generator.utils.JobFramework;
import com.talend.tuj.generator.utils.NodeType;

import java.util.Optional;

public class SparkConfigurationDistributionProcessor extends IProcessor {
    private String distribution_name, distribution_version;
    private Optional<String> spark_version = Optional.empty();

    SparkConfigurationDistributionProcessor(String distribution_name, String distribution_version) {
        this.distribution_name = distribution_name;
        this.distribution_version = distribution_version;
    }

    SparkConfigurationDistributionProcessor(String distribution_name, String distribution_version, String spark_version) {
        this(distribution_name, distribution_version);
        this.spark_version = Optional.of(spark_version);
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
            component.replaceParameter("SPARK_LOCAL_MODE", "false");
            component.replaceParameter("DISTRIBUTION", distribution_name);
            component.replaceParameter("SPARK_VERSION", distribution_version);
            spark_version.ifPresent(version -> component.replaceParameter("SUPPORTED_SPARK_VERSION", version));
        } catch (NullPointerException ignored) {
        }
    }
}
