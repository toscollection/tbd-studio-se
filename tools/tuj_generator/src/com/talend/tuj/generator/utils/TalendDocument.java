package com.talend.tuj.generator.utils;

import org.w3c.dom.Document;
import org.w3c.dom.NamedNodeMap;
import org.w3c.dom.Node;

import java.nio.file.Path;
import java.util.Optional;

public abstract class TalendDocument {
    protected Document properties;
    protected Document item;
    protected String id;
    protected String name;
    protected String version;

    protected Optional<Path> fsPath;

    public TalendDocument(Document properties, Document item, Optional<Path> jobPath) {
        this.properties = properties;
        this.item = item;
        this.fsPath = jobPath;
        writeId();
        writeNameAndVersion();
    }

    private void writeId() {
        this.id = Optional.of(properties.getElementsByTagName("TalendProperties:Property").item(0).getAttributes().getNamedItem("id").getNodeValue()).orElse("null");
    }

    public String getId() {
        return id;
    }

    public Document getItem() {
        return item;
    }

    public Document getProperties() {
        return properties;
    }

    public Optional<Path> getFsPath() {
        return fsPath;
    }

    public void setFsPath(Optional<Path> fsPath) {
        this.fsPath = fsPath;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getVersion() {
        return version;
    }

    private void writeNameAndVersion() {
        try {
            NamedNodeMap attributes = this.properties.getElementsByTagName("TalendProperties:Property").item(0).getAttributes();
            this.name = attributes.getNamedItem("label").getNodeValue();
            this.version = attributes.getNamedItem("version").getNodeValue();
        } catch (NullPointerException ignored) {
        }
    }
}
