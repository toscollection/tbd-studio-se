package com.talend.tuj.generator.elements;

import com.talend.tuj.generator.utils.*;
import org.w3c.dom.Node;

import java.util.Map;
import java.util.Optional;

public interface IElement {

    boolean isOfClass(ComponentClass compClass);

    boolean isOfType(NodeType type);

    Optional<String> getAttribute(String attribute);

    void replaceAttribute(String attribute, String value);

    Optional<String> getParameter(String parameter);

    void replaceParameter(String parameter, String value);

    Map<String, String> getAllParameters();

    void replaceParameters(Map<String, String> newParameters);

    Node getRawNode();
}
