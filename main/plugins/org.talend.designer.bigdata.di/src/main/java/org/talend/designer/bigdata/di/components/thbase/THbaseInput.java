package org.talend.designer.bigdata.di.components.thbase;

import org.talend.designer.codegen.config.CodeGeneratorArgument;

import java.util.Map;

public class THbaseInput extends THbase{
    private final THBaseConfiguration<THbaseInput> thBaseConfiguration;
    private THbaseTable tHbaseTable;

    public THbaseInput(CodeGeneratorArgument codeGeneratorArgument) {
        super(codeGeneratorArgument);
        thBaseConfiguration = new THBaseConfiguration<>();
        tHbaseTable = new THbaseTable(codeGeneratorArgument);
    }
    @Override
    public Map<String, String> getConnectionConfiguration() {
        return thBaseConfiguration.getConnectionConfiguration(this);
    }
    @Override
    String getKeytab() {
        return thBaseConfiguration.getKeytab(this);
    }

    public String getTableNameWithNamespace(){
       return tHbaseTable.getTableNameWithNamespace();
    }
}
