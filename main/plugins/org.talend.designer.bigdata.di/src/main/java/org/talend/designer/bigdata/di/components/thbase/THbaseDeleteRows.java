package org.talend.designer.bigdata.di.components.thbase;

import org.talend.designer.codegen.config.CodeGeneratorArgument;

import java.util.Map;

public class THbaseDeleteRows extends THbase {
    private final THBaseConfiguration<THbaseDeleteRows> thBaseConfiguration;
    public THbaseDeleteRows(CodeGeneratorArgument codeGeneratorArgument) {
        super(codeGeneratorArgument);
        this.thBaseConfiguration = new THBaseConfiguration<>();
    }
    public String getDeleteRows(){
        return BigDataDIComponent.getParameter(node,"__ROW_KEY__","");
    }
    public Map<String, String> getConnectionConfiguration() {
        return thBaseConfiguration.getConnectionConfiguration(this);
    }
    @Override
    public String getKeytab() {
        return thBaseConfiguration.getKeytab(this);
    }
}
