package org.talend.designer.bigdata.di.components.thbase;

import org.talend.designer.codegen.config.CodeGeneratorArgument;

import java.util.Map;

public class THbaseNamespace extends THbase {
    private final THBaseConfiguration<THbaseNamespace> thBaseConfiguration;

    public THbaseNamespace(CodeGeneratorArgument codeGeneratorArgument) {
        super(codeGeneratorArgument);
        this.thBaseConfiguration = new THBaseConfiguration<>();
    }
    public boolean isSpecifyNamespace(){
        return false;
    }
    public String getNamespaceAction(){
        return BigDataDIComponent.getParameter(node, "__NAMESPACE_ACTION__", "NONE");
    }
    public Map<String, String> getConnectionConfiguration() {
        return thBaseConfiguration.getConnectionConfiguration(this);
    }
    @Override
    public String getKeytab() {
        return thBaseConfiguration.getKeytab(this);
    }

}
