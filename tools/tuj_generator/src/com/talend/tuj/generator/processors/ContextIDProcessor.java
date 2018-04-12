package com.talend.tuj.generator.processors;

import com.talend.tuj.generator.elements.ContextElement;
import com.talend.tuj.generator.elements.IElement;
import com.talend.tuj.generator.elements.JobElement;
import com.talend.tuj.generator.utils.*;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import java.util.*;
import java.util.stream.Collectors;

public class ContextIDProcessor extends IProcessor {

    private Map<String, String> oldNewIdRelation = new HashMap<>();

    public ContextIDProcessor() {}

    @Override
    public boolean shouldBeProcessed(IElement component) {
        return (component.isOfClass(ComponentClass.Job) && component.isOfType(NodeType.JOBCONTEXT)) ||
                (component.isOfClass(ComponentClass.Context) && component.isOfType(NodeType.TPProperty));
    }

    @Override
    public void process(IElement component) {
        if(component.isOfClass(ComponentClass.Job)){
            JobElement jobComponent = (JobElement)component;
            List<Context> contexts = jobComponent.getParentJob().getTuj().get().getContexts();
            Optional<Context> matchingContext = Optional.empty();
            NodeList contextParameters = jobComponent.getRawNode().getChildNodes();


            for(int i = 0 ; i < contextParameters.getLength() ; i++){
                Node contextParameter = contextParameters.item(i);
                try{
                    if(contextParameter.getNodeName().equals("contextParameter")){
                        String componentContextID = contextParameter.getAttributes().getNamedItem("repositoryContextId").getNodeValue();

                        if(!oldNewIdRelation.containsKey(componentContextID)){
                            oldNewIdRelation.put(componentContextID, JobID.generateJobID());
                        }
                        contextParameter.getAttributes().getNamedItem("repositoryContextId").setNodeValue(oldNewIdRelation.get(componentContextID));
                    }
                } catch(NullPointerException ignored){
                    // Built-in values
                }
            }

            /*
            if(componentContextID.equals("")) return;

            for (Context context : contexts){
                if(componentContextID.equals(context.getContextID())){
                    matchingContext = Optional.of(context);
                    break;
                }
            }

            matchingContext.ifPresent(
                    context -> {
                        if(!oldNewIdRelation.containsKey(context.getContextID())){
                            oldNewIdRelation.put(context.getContextID(), JobID.generateJobID());
                        }

                        // Replace context parameters ID
                        for(int i = 0 ; i < contextParameters.getLength() ; i++){
                            Node contextParameter = contextParameters.item(i);
                            try{
                                if(contextParameter.getNodeName().equals("contextParameter"))
                                    contextParameter.getAttributes().getNamedItem("repositoryContextId").setNodeValue(oldNewIdRelation.get(context.getContextID()));
                            } catch(NullPointerException ignored){
                                // Built-in values
                            }
                        }

                        // Update matching context file
                        context.replaceId(oldNewIdRelation.get(context.getContextID()));
                    }
            );
            */
        } else if (component.isOfClass(ComponentClass.Context)){
            ContextElement contextComponent = (ContextElement) component;
            component.getAttribute("id").ifPresent(
                    oldId -> {
                        if (!oldNewIdRelation.containsKey(oldId)) {
                            oldNewIdRelation.put(oldId, JobID.generateJobID());
                        }
                        String newID = oldNewIdRelation.get(oldId);
                        contextComponent.replaceAttribute("id", oldNewIdRelation.get(oldId));
                    }
            );
        }


    }
}
