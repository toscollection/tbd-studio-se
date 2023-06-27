package org.talend.designer.bigdata.di.components.thbase;

import org.talend.core.model.process.ElementParameterParser;
import org.talend.designer.codegen.config.CodeGeneratorArgument;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class THbaseTable extends THbase {
    private final THBaseConfiguration<THbaseTable> thBaseConfiguration;

    public THbaseTable(CodeGeneratorArgument codeGeneratorArgument) {
        super(codeGeneratorArgument);
        this.thBaseConfiguration = new THBaseConfiguration<>();
    }
    public Map<String, String> getConnectionConfiguration() {
        return this.thBaseConfiguration.getConnectionConfiguration(this);
    }
    public String getKeytab(){
        return thBaseConfiguration.getKeytab(this);
    }
    public String getTableAction(){
        return BigDataDIComponent.getParameter(node, "__TABLE_ACTION__", "NONE");
    }

    public List<Map<String,String>> getFamilyParameters(){
        return ElementParameterParser.getTableValue(node,"__FAMILY_PARAMETERS__")
                .stream()
                .map(m -> m.entrySet().stream()
                        .collect(Collectors.toMap(Map.Entry::getKey, p -> updateString(p.getValue()))))
                .collect(Collectors.toList());
    }

    private String updateString(String string){
        if (string.length() < 2) return string;
        if ((string.charAt(0) == '\"') && (string.charAt(string.length()-1) == '\"')) {
            return string.substring(1,string.length()-1);
        }
        return string;
    }

    public String getTableNameWithNamespace(){
        String tableNameString = trimString(getTableName());
        if (isSpecifyNamespace() && !"".equals(getNamespace())) {
            tableNameString = trimString(getNamespace()) + ":" + tableNameString;
        }
        return "\""+tableNameString+"\"";
    }
    private String trimString(String inputString){
        if ( inputString.charAt(0) == '\"' &&  inputString.charAt(inputString.length()-1) == '\"'){
            return inputString.substring(1,inputString.length()-1);
        }
        return inputString;
    }
}
