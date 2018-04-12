package com.talend.tuj.generator.processors;

import com.talend.tuj.generator.elements.IElement;
import com.talend.tuj.generator.utils.Context;

import java.util.List;

public abstract class IProcessor {

    public IProcessor withContexts(List<Context> contexts){return this;}

    public abstract boolean shouldBeProcessed(IElement component);

    public abstract void process(IElement component);
}
