package com.talend.tuj.generator.processors;

import com.talend.tuj.generator.elements.ContextElement;
import com.talend.tuj.generator.elements.IElement;
import com.talend.tuj.generator.elements.JobElement;
import com.talend.tuj.generator.utils.*;
import javafx.util.Pair;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import java.nio.file.Paths;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

public class FileNameSubstitutionProcessor extends IProcessor {

    private List<Pair<String, String>> substitutions;

    public FileNameSubstitutionProcessor(List<Pair<String, String>> substitutions) {
        this.substitutions = substitutions;
    }

    @Override
    public boolean shouldBeProcessed(IElement component) {
        return component.isOfType(NodeType.TPProperty) || component.isOfType(NodeType.TPProcessItem) || component.isOfType(NodeType.TPContextItem) || component.isOfType(NodeType.TPItemState) ||
                (component.isOfType(NodeType.COMPONENT) && component.getAttribute("componentName").orElse("").equals("tRunJob"));
    }

    @Override
    public void process(IElement component) {
        if(component.isOfClass(ComponentClass.Job)){
            JobElement jobComponent = (JobElement)component;

            if (jobComponent.isOfType(NodeType.TPProperty)) {
                Job parentJob = jobComponent.getParentJob();

                parentJob.setName(applySubstitutions(parentJob.getName()));
                parentJob.getFsPath().ifPresent(path -> parentJob.setFsPath(Optional.of(Paths.get(applySubstitutions(path.toString())))));

                if (parentJob.getTuj().isPresent() && parentJob.getTuj().get().getStarterJob().equals(parentJob)) {
                    TUJ parentTUJ = parentJob.getTuj().get();
                    parentTUJ.setName(applySubstitutions(parentTUJ.getName()));
                    parentTUJ.getProjectName().ifPresent(
                            name -> parentTUJ.setProjectName(applySubstitutions(name))
                    );
                }

                jobComponent.replaceAttribute("label", applySubstitutions(jobComponent.getAttribute("label").orElse("")));
                jobComponent.replaceAttribute("displayName", applySubstitutions(jobComponent.getAttribute("displayName").orElse("")));
            } else if (jobComponent.isOfType(NodeType.TPItemState)) {
                jobComponent.replaceAttribute("path", applySubstitutions(jobComponent.getAttribute("path").orElse("")));
            } else if (jobComponent.isOfType(NodeType.TPProcessItem)) {
                NodeList nodes = jobComponent.getRawNode().getChildNodes();
                for(int i = 0 ; i<nodes.getLength() ; i++){
                    Node node = nodes.item(i);
                    if(node.getNodeName().contains("process")){
                        node = node.getAttributes().getNamedItem("href");
                        node.setNodeValue(applySubstitutions(node.getNodeValue()));
                    }
                }
            } else if (jobComponent.isOfType(NodeType.COMPONENT)) {
                jobComponent.replaceParameter("PROCESS", applySubstitutions(jobComponent.getParameter("PROCESS").orElse("")));
            }
        } else if(component.isOfClass(ComponentClass.Context)){
            ContextElement contextComponent = (ContextElement)component;

            if (contextComponent.isOfType(NodeType.TPProperty)) {
                contextComponent.replaceAttribute("label", applySubstitutions(contextComponent.getAttribute("label").orElse("")));
                contextComponent.replaceAttribute("displayName", applySubstitutions(contextComponent.getAttribute("displayName").orElse("")));

                contextComponent.getParentContext().getFsPath().ifPresent(
                        path -> contextComponent.getParentContext().setFsPath(Optional.of(Paths.get(applySubstitutions(path.toString()))))
                );
                contextComponent.getParentContext().setName(applySubstitutions(contextComponent.getParentContext().getName()));
            } else if (contextComponent.isOfType(NodeType.TPItemState)) {
                contextComponent.replaceAttribute("path", applySubstitutions(contextComponent.getAttribute("path").orElse("")));
            } else if (contextComponent.isOfType(NodeType.TPContextItem)) {
                NodeList nodes = contextComponent.getRawNode().getChildNodes();
                for(int i = 0 ; i<nodes.getLength() ; i++){
                    Node node = nodes.item(i);
                    if(node.getNodeName().contains("context")){
                        node = node.getAttributes().getNamedItem("href");
                        node.setNodeValue(applySubstitutions(node.getNodeValue()));
                    }
                }
            }
        }

    }

    private String applySubstitutions(String str) {
        String newStr = str;
        for (Pair<String, String> entry : substitutions) {
            newStr = newStr.replaceAll(entry.getKey(), entry.getValue());

        }
        return newStr;
    }
}
