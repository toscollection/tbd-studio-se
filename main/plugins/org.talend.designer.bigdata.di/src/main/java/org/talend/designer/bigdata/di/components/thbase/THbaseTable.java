package org.talend.designer.bigdata.di.components.thbase;

import org.talend.designer.codegen.config.CodeGeneratorArgument;

public class THbaseTable extends THbase {
    public THbaseTable(CodeGeneratorArgument codeGeneratorArgument) {
        super(codeGeneratorArgument);
    }

    public String getNamespace(){
        return BigDataDIComponent.getParameter(node, "__NAMESPACE__", "");
    }

    public String getTableName(){
        return BigDataDIComponent.getParameter(node, "__TABLE_NAME__", "");
    }

    public String getTableAction(){
        return BigDataDIComponent.getParameter(node, "__TABLE_ACTION__", "NONE");
    }


}
