package com.talend.tuj.generator.processors;

import com.talend.tuj.generator.elements.IElement;
import com.talend.tuj.generator.utils.ComponentClass;
import com.talend.tuj.generator.utils.JobID;
import com.talend.tuj.generator.utils.NodeType;

import java.util.HashMap;
import java.util.Map;

public class JobIDProcessor extends IProcessor {
    private Map<String, String> oldNewIdRelation = new HashMap<>();

    @Override
    public boolean shouldBeProcessed(IElement component) {
        if(component.isOfClass(ComponentClass.Job)){
            String componentName = component.getAttribute("componentName").orElse("");

            return (component.isOfType(NodeType.COMPONENT) && componentName.equals("tRunJob")) || (component.isOfClass(ComponentClass.Job) && component.isOfType(NodeType.TPProperty));
        }
        return false;
    }

    @Override
    public void process(IElement component) {
        if (component.isOfType(NodeType.COMPONENT)) {
            String newId = JobID.generateJobID();
            oldNewIdRelation.put(component.getParameter("PROCESS:PROCESS_TYPE_PROCESS").orElse(""), newId);
            component.replaceParameter("PROCESS:PROCESS_TYPE_PROCESS", newId);
        } else if (component.isOfType(NodeType.TPProperty)) {
            String oldId = component.getAttribute("id").orElse("");
            if (!oldNewIdRelation.containsKey(oldId)) {
                oldNewIdRelation.put(oldId, JobID.generateJobID());
            }
            String newId = oldNewIdRelation.get(oldId);
            component.replaceAttribute("id", newId);
        }


    }
}
