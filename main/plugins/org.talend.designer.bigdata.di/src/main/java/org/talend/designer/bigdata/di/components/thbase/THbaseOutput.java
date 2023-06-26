package org.talend.designer.bigdata.di.components.thbase;

import org.talend.designer.codegen.config.CodeGeneratorArgument;

import java.util.*;

public class THbaseOutput extends THbase {
    private final THBaseConfiguration<THbaseOutput> thBaseConfiguration;
    private THbaseTable tHbaseTable;

    public THbaseOutput(CodeGeneratorArgument codeGeneratorArgument) {
        super(codeGeneratorArgument);
        tHbaseTable = new THbaseTable(codeGeneratorArgument);
        thBaseConfiguration = new THBaseConfiguration<>();
    }
    public String getTableAction() {
        return tHbaseTable.getTableAction();
    }
    public List<Map<String, String>> getFamilyParameters() {
        return tHbaseTable.getFamilyParameters();
    }
    @Override
    public boolean isSetTableNsMapping() {
        return BigDataDIComponent.getBooleanParameter(node, "__SET_TABLE_NS_MAPPING__");
    }
    @Override
    public String getTableNsMapping() {
        return BigDataDIComponent.getParameter(node, "__TABLE_NS_MAPPING__", "");
    }
    public Map<String, String> getConnectionConfiguration() {
        return thBaseConfiguration.getConnectionConfiguration(this);
    }
    @Override
    public String getKeytab() {
        return thBaseConfiguration.getKeytab(this);
    }

    public boolean isCustomRowKey() {
        return BigDataDIComponent.getBooleanParameter(node, "__CUSTOM_ROW_KEY__");
    }
//    public String getRowKey() {
//        if (isCustomRowKey()) return BigDataDIComponent.getParameter(node, "__ROW_KEY__", "");
//        //TODO: change this
//        return "myRow_" + javaVariable.name();
//    }
    public boolean isUseCustomTimestampColumn() {
        return BigDataDIComponent.getBooleanParameter(node, "__CUSTOM_TIMESTAMP_COLUMN__");
    }
    public String getCustomTimestampColumn() {
        return BigDataDIComponent.getParameter(node, "__TIMESTAMP_COLUMN__", "");
    }
    public boolean isUseBatchMode(){
        return BigDataDIComponent.getBooleanParameter(node,"__USE_BATCH_MODE__");
    }
    public String getBatchSize(){
        return BigDataDIComponent.getParameter(node,"__BATCH_SIZE__","");
    }
    public String getTableNameWithNamespace(){
        return tHbaseTable.getTableNameWithNamespace();
    }
}
