package com.talend.tuj.generator.processors;

import com.talend.tuj.generator.elements.IElement;
import com.talend.tuj.generator.utils.NodeType;

public class PrintProcessor extends IProcessor {
    @Override
    public boolean shouldBeProcessed(IElement component) {
        return component.isOfType(NodeType.COMPONENT);
    }

    @Override
    public void process(IElement component) {
        System.out.println("PrintProcessor: " + component.getAttribute("componentName").orElse("component has no name"));
    }
}
