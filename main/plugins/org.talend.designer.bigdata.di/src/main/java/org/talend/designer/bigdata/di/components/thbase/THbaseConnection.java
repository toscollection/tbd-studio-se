package org.talend.designer.bigdata.di.components.thbase;

import org.talend.designer.codegen.config.CodeGeneratorArgument;

public class THbaseConnection extends THbase{
    public THbaseConnection(CodeGeneratorArgument codeGeneratorArgument) {
        super(codeGeneratorArgument);
    }
    public boolean isUseExistingConnection(){
        return false;
    }
}
