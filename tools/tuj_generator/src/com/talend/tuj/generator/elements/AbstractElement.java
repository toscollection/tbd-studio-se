package com.talend.tuj.generator.elements;

import com.talend.tuj.generator.utils.Job;
import com.talend.tuj.generator.utils.JobFramework;
import com.talend.tuj.generator.utils.JobType;
import com.talend.tuj.generator.utils.NodeType;
import org.w3c.dom.NamedNodeMap;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

public abstract class AbstractElement implements IElement {
    protected Node xmlNode;


    public AbstractElement(Node node) {
        this.xmlNode = node;
    }

    @Override
    public boolean isOfType(NodeType type) {
        return xmlNode.getNodeName().equals(type.getXmlNodeName());
    }

    @Override
    public Optional<String> getAttribute(String attribute) {
        try {
            return Optional.of(xmlNode.getAttributes().getNamedItem(attribute).getNodeValue());
        } catch (NullPointerException e) {
            return Optional.empty();
        }
    }

    @Override
    public void replaceAttribute(String attribute, String value) {
        xmlNode.getAttributes().getNamedItem(attribute).setNodeValue(value);
    }

    @Override
    public Optional<String> getParameter(String parameter) {
        if (xmlNode.hasChildNodes()) {
            NodeList childs = xmlNode.getChildNodes();
            for (int nodeIndex = 0; nodeIndex < childs.getLength(); nodeIndex++) {
                Node childNode = childs.item(nodeIndex);
                try {
                    if (childNode.getAttributes().getNamedItem("name").getNodeValue().equals(parameter)) {
                        return Optional.of(childNode.getAttributes().getNamedItem("value").getNodeValue());
                    }
                } catch (NullPointerException ignored) {
                }
            }
        }
        return Optional.empty();
    }

    @Override
    public void replaceParameter(String parameter, String value) {
        if (xmlNode.hasChildNodes()) {
            NodeList childs = xmlNode.getChildNodes();
            for (int nodeIndex = 0; nodeIndex < childs.getLength(); nodeIndex++) {
                Node childNode = childs.item(nodeIndex);
                try {
                    if (childNode.getAttributes().getNamedItem("name").getNodeValue().equals(parameter)) {
                        childNode.getAttributes().getNamedItem("value").setNodeValue(value);
                    }
                } catch (NullPointerException ignored) {
                }
            }
        }
    }

    @Override
    public Map<String, String> getAllParameters() {
        Map<String, String> parameters = new HashMap<>();

        NodeList childs = xmlNode.getChildNodes();
        for (int nodeIndex = 0; nodeIndex < childs.getLength(); nodeIndex++) {
            NamedNodeMap childAttributes = childs.item(nodeIndex).getAttributes();
            if (childAttributes == null) continue;
            try{
                parameters.put(childAttributes.getNamedItem("name").getNodeValue(), childAttributes.getNamedItem("value").getNodeValue());
            } catch (NullPointerException ignored){}
        }

        return parameters;
    }

    @Override
    public void replaceParameters(Map<String, String> newParameters) {
        NodeList childs = xmlNode.getChildNodes();
        for (int nodeIndex = 0; nodeIndex < childs.getLength(); nodeIndex++) {
            NamedNodeMap childAttributes = childs.item(nodeIndex).getAttributes();
            if (childAttributes == null) continue;
            String attributeName = childAttributes.getNamedItem("name").getNodeValue();
            if (newParameters.containsKey(attributeName)) {
                replaceParameter(attributeName, newParameters.get(attributeName));
            }
        }
    }

    @Override
    public Node getRawNode() {
        return xmlNode;
    }
}
