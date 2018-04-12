package com.talend.tuj.generator.elements;

import com.talend.tuj.generator.utils.Context;
import com.talend.tuj.generator.utils.Job;
import org.w3c.dom.Node;

import java.util.Optional;

public class ElementFactory {
    private static Optional<ElementFactory> instance = Optional.empty();

    private ElementFactory() {
    }

    public static ElementFactory getInstance() {
        if (!instance.isPresent()) {
            instance = Optional.of(new ElementFactory());
        }
        return instance.get();
    }

    public IElement createJobElement(Node node, Job job) {
        return new JobElement(node, job);
    }

    public IElement createContextElement(Node node, Context context) {
        return new ContextElement(node, context);
    }
}
