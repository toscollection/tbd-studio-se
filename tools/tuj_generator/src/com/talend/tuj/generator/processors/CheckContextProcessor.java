package com.talend.tuj.generator.processors;

import com.talend.tuj.generator.elements.IElement;
import com.talend.tuj.generator.elements.JobElement;
import com.talend.tuj.generator.utils.*;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class CheckContextProcessor extends IProcessor {
    @Override
    public boolean shouldBeProcessed(IElement component) {
        if(component.isOfClass(ComponentClass.Job)){
            JobElement jobComponent = (JobElement)component;
            return (jobComponent.isJobOfFramework(JobFramework.MAPREDUCE) || jobComponent.isJobOfFramework(JobFramework.SPARK_STREAMING) || jobComponent.isJobOfFramework(JobFramework.MAPREDUCE)) &&
                jobComponent.isOfType(NodeType.JOBCONFIG);
        }
        return false;
    }

    @Override
    public void process(IElement component) {
        JobElement jobConfig = (JobElement)component;

        Map<String, String> newContextVariables = new HashMap<>();
        Map<String, String> newJobParameters = new HashMap<>();

        Map<String, String> jobParameters = jobConfig.getAllParameters();

        if(!jobParameters.get("RESOURCE_MANAGER").equals("context.resourcemanager")){
            newContextVariables.put("resourcemanager","null");
            newJobParameters.put("RESOURCE_MANAGER", "context.resourcemanager");
        }

        if(!jobParameters.get("RESOURCEMANAGER_SCHEDULER_ADDRESS").equals("context.scheduler")){
            newContextVariables.put("scheduler","null");
            newJobParameters.put("RESOURCEMANAGER_SCHEDULER_ADDRESS", "context.scheduler");
        }

        if(!jobParameters.get("JOBHISTORY_ADDRESS").equals("context.jobhistory")){
            newContextVariables.put("jobhistory","null");
            newJobParameters.put("JOBHISTORY_ADDRESS", "context.jobhistory");
        }

        jobConfig.replaceParameters(newJobParameters);
        jobConfig.getParentJob().addContextParameters(newContextVariables);
        jobConfig.getParentJob().getTuj().ifPresent(
                tuj -> tuj.getStarterJob().addContextParameters(newContextVariables)
        );
    }
}
