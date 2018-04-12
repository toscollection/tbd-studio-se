package com.talend.tuj.generator.utils;

import org.w3c.dom.Document;
import org.w3c.dom.Node;

import java.nio.file.Path;
import java.util.Optional;

public class Context extends TalendDocument{
    private String contextID;

    public Context(Document properties, Document item, Optional<Path> jobPath) {
        super(properties, item, jobPath);
        writeContextId();
    }

    private void writeContextId() {
        this.contextID = this.properties.getElementsByTagName("TalendProperties:Property").item(0).getAttributes().getNamedItem("id").getNodeValue();
    }

    public void replaceId(String newID){
        Node contextTypeNode = this.properties.getElementsByTagName("TalendProperties:Property").item(0);
        contextTypeNode.getAttributes().getNamedItem("id").setNodeValue(newID);
    }

    public String getContextID() {
        return contextID;
    }
}
