package org.talend.designer.bigdata.di.components.thbase;

import org.talend.designer.codegen.config.CodeGeneratorArgument;

import java.util.Map;

public class THbaseConnection extends THbase {
    private final THBaseConfiguration<THbaseConnection> thBaseConfiguration;
    public THbaseConnection(CodeGeneratorArgument codeGeneratorArgument) {
        super(codeGeneratorArgument);
        this.thBaseConfiguration = new THBaseConfiguration<>();
    }
    public boolean isUseExistingConnection(){
        return false;
    }
    @Override
    public Map<String, String> getConnectionConfiguration() {
        return thBaseConfiguration.getConnectionConfiguration(this);
    }
    @Override
    public String getKeytab() {
        return thBaseConfiguration.getKeytab(this);
    }

}
