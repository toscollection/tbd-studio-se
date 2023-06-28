package org.talend.designer.bigdata.di.components.thbase;

import org.talend.core.model.process.INode;
import org.talend.designer.bigdata.di.components.DesignerDIComponent;
import org.talend.designer.codegen.config.CodeGeneratorArgument;

import java.util.*;

public class THbaseOutput implements THbase, DesignerDIComponent.WithDieOnErrorOption {
    private final CodeGeneratorArgument codeGeneratorArgument;
    private final INode node;
    private final INode connNode;
    private final THBaseConfiguration tHBaseConfiguration;
    private final THbaseTable tHbaseTable;

    public THbaseOutput(CodeGeneratorArgument codeGeneratorArgument) {
        this.codeGeneratorArgument = codeGeneratorArgument;
        this.node = (INode) codeGeneratorArgument().getArgument();
        this.connNode = setTargetNode();
        tHbaseTable = new THbaseTable(codeGeneratorArgument);
        tHBaseConfiguration = new THBaseConfiguration();
    }

    @Override
    public CodeGeneratorArgument codeGeneratorArgument() {
        return this.codeGeneratorArgument;
    }

    @Override
    public INode node() {
        return this.node;
    }

    @Override
    public INode connNode() {
        return this.connNode;
    }

    @Override
    public Map<String, String> getConnectionConfiguration() {
        return this.tHBaseConfiguration.getConnectionConfiguration(this);
    }

    @Override
    public String getKeytab() {
        return this.tHBaseConfiguration.getKeytab(this);
    }

    @Override
    public boolean isConfigureFromClassPath() {
        return false;
    }

    @Override
    public boolean isSetTableNsMapping() {
        return BigDataDIComponent.getBooleanParameter(node, "__SET_TABLE_NS_MAPPING__");
    }

    @Override
    public String getTableNsMapping() {
        return BigDataDIComponent.getParameter(node, "__TABLE_NS_MAPPING__", "");
    }

    @Override
    public INode getNode() {
        return this.node;
    }

    public String getTableAction() {
        return tHbaseTable.getTableAction();
    }

    public List<Map<String, String>> getFamilyParameters() {
        return tHbaseTable.getFamilyParameters();
    }

    public boolean isCustomRowKey() {
        return BigDataDIComponent.getBooleanParameter(node, "__CUSTOM_ROW_KEY__");
    }

    public boolean isUseCustomTimestampColumn() {
        return BigDataDIComponent.getBooleanParameter(node, "__CUSTOM_TIMESTAMP_COLUMN__");
    }

    public String getCustomTimestampColumn() {
        return BigDataDIComponent.getParameter(node, "__TIMESTAMP_COLUMN__", "");
    }

    public boolean isUseBatchMode() {
        return BigDataDIComponent.getBooleanParameter(node, "__USE_BATCH_MODE__");
    }

    public String getBatchSize() {
        return BigDataDIComponent.getParameter(node, "__BATCH_SIZE__", "");
    }
}
