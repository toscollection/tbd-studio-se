package org.talend.designer.bigdata.di.components.thbase;

import org.talend.core.model.process.ElementParameterParser;
import org.talend.designer.codegen.config.CodeGeneratorArgument;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class THbaseTable extends THbase {
    public THbaseTable(CodeGeneratorArgument codeGeneratorArgument) {
        super(codeGeneratorArgument);
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
        if (string.isEmpty()) {
            return "";
        } else if ((string.charAt(0) != '\"') && (string.charAt(string.length()-1) == '\"')) {
            return "\""+string+"\"";
        }
        return string;
    }
}
