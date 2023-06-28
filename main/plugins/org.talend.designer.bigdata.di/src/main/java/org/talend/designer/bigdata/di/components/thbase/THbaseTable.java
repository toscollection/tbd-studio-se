package org.talend.designer.bigdata.di.components.thbase;

import org.talend.core.model.process.INode;
import org.talend.designer.codegen.config.CodeGeneratorArgument;

import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class THbaseTable implements THbase {
    private final CodeGeneratorArgument codeGeneratorArgument;
    private final INode node;
    private final INode connNode;
    private final THBaseConfiguration tHBaseConfiguration;


    public THbaseTable(CodeGeneratorArgument codeGeneratorArgument) {
        this.codeGeneratorArgument = codeGeneratorArgument;
        this.node = (INode) codeGeneratorArgument().getArgument();
        this.connNode = setTargetNode();
        this.tHBaseConfiguration = new THBaseConfiguration();
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
        return tHBaseConfiguration.getConnectionConfiguration(this);
    }

    @Override
    public String getKeytab() {
        return tHBaseConfiguration.getKeytab(this);
    }

    @Override
    public boolean isConfigureFromClassPath() {
        return false;
    }

    @Override
    public boolean isSetTableNsMapping() {
        return false;
    }

    @Override
    public String getTableNsMapping() {
        return null;
    }

    @Override
    public INode getNode() {
        return node;
    }

    public String getTableAction() {
        return BigDataDIComponent.getParameter(node, "__TABLE_ACTION__", "NONE");
    }

    public List<Map<String, String>> getFamilyParameters() {
        return BigDataDIComponent.tableParameter(node, "__FAMILY_PARAMETERS__", Collections.emptyList())
                .stream()
                .map(m -> m.entrySet().stream()
                        .collect(Collectors.toMap(Map.Entry::getKey, p -> THbaseUtils.trimQuotes(p.getValue()))))
                .collect(Collectors.toList());
    }
}
