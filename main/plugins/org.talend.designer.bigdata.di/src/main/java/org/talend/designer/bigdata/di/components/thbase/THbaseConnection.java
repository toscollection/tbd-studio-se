package org.talend.designer.bigdata.di.components.thbase;

import org.talend.core.model.process.INode;
import org.talend.designer.bigdata.di.components.DesignerDIComponent;
import org.talend.designer.codegen.config.CodeGeneratorArgument;

import java.util.Map;

public class THbaseConnection implements THbase, DesignerDIComponent.WithDieOnErrorOption {
    private final CodeGeneratorArgument codeGeneratorArgument;
    private final INode node;
    private final INode connNode;
    private final THBaseConfiguration tHBaseConfiguration;

    public THbaseConnection(CodeGeneratorArgument codeGeneratorArgument) {
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
    public boolean isSetTableNsMapping() {
        return false;
    }

    @Override
    public String getTableNsMapping() {
        return null;
    }

    @Override
    public boolean useExistingConnection() {
        return false;
    }

    @Override
    public boolean isConfigureFromClassPath() {
        return BigDataDIComponent.getBooleanParameter(node, "__CONFIGURATIONS_FROM_CLASSPATH__");
    }

    @Override
    public INode getNode() {
        return null;
    }
}
