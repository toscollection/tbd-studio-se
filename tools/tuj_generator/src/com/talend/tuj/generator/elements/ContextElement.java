package com.talend.tuj.generator.elements;

import com.talend.tuj.generator.utils.ComponentClass;
import com.talend.tuj.generator.utils.Context;
import com.talend.tuj.generator.utils.Job;
import org.w3c.dom.Node;

public class ContextElement extends AbstractElement {
    protected Context context;

    public ContextElement(Node node, Context context) {
        super(node);
        this.context = context;
    }

    @Override
    public boolean isOfClass(ComponentClass compClass) {
        return compClass.equals(ComponentClass.Context);
    }

    public Context getParentContext() {
        return context;
    }
}
