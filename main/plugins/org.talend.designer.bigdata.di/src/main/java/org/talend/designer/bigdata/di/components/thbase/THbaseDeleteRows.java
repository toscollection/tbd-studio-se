package org.talend.designer.bigdata.di.components.thbase;

import org.talend.designer.codegen.config.CodeGeneratorArgument;

public class THbaseDeleteRows extends THbase {

    public THbaseDeleteRows(CodeGeneratorArgument codeGeneratorArgument) {
        super(codeGeneratorArgument);
    }

    public String getDeleteRows(){
        return BigDataDIComponent.getParameter(node,"__ROW_KEY__","");
    }
}
