package org.talend.designer.bigdata.di.components.thbase;

import org.talend.core.model.process.ElementParameterParser;
import org.talend.designer.codegen.config.CodeGeneratorArgument;

import java.util.List;
import java.util.Map;

public class THbaseTable extends THbase {
    public THbaseTable(CodeGeneratorArgument codeGeneratorArgument) {
        super(codeGeneratorArgument);
    }

    public String getTableAction(){
        return BigDataDIComponent.getParameter(node, "__TABLE_ACTION__", "NONE");
    }

    public List<Map<String,String>> getFamilyParameters(){
        return ElementParameterParser.getTableValue(node,"__FAMILY_PARAMETERS__");
    }

}
