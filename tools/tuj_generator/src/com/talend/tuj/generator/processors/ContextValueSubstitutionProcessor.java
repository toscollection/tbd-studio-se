package com.talend.tuj.generator.processors;

import com.talend.tuj.generator.elements.IElement;
import com.talend.tuj.generator.utils.NodeType;
import javafx.util.Pair;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class ContextValueSubstitutionProcessor extends IProcessor {

    private List<Pair<String, String>> substitutions;

    public ContextValueSubstitutionProcessor(List<Pair<String, String>> substitutions) {
        this.substitutions = substitutions;
    }

    @Override
    public boolean shouldBeProcessed(IElement component) {
        return component.isOfType(NodeType.JOBCONTEXT) || component.isOfType(NodeType.CONTEXTFILE);
    }

    @Override
    public void process(IElement component) {
        component.replaceParameters(
                component.getAllParameters().entrySet().stream().filter(
                        parameter -> substitutions.stream().anyMatch(
                                sub -> sub.getKey().contains(parameter.getValue()))
                ).peek(
                        parameter -> {
                            String newParameter = parameter.getValue();
                            for (Pair<String, String> entry : substitutions) {
                                newParameter = newParameter.replaceAll(entry.getKey(), entry.getValue());
                            }
                            parameter.setValue(newParameter);
                        }
                ).collect(
                        Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue)
                )
        );
    }
}
