package org.talend.designer.bigdata.di.components.iceberg;

import org.talend.core.model.process.EConnectionType;
import org.talend.core.model.process.INode;
import org.talend.designer.bigdata.di.components.DesignerDIComponent;
import org.talend.designer.codegen.config.CodeGeneratorArgument;

public class TIcebergOutput implements DesignerDIComponent.WithOneInput, DesignerDIComponent.WithSchema, DesignerDIComponent.WithConnection {

    protected final CodeGeneratorArgument codeGeneratorArgument;

    public boolean hasMainFlow() {
        INode node = (INode) codeGeneratorArgument().getArgument();
        return node.getIncomingConnections(EConnectionType.FLOW_MAIN)
                .stream()
                .findFirst()
                .isPresent();
    }

    public TIcebergOutput(CodeGeneratorArgument arg) {
        this.codeGeneratorArgument = arg;
    }

    @Override
    public CodeGeneratorArgument codeGeneratorArgument() {
        return this.codeGeneratorArgument;
    }

    public String getTable() {
        return getParameter("__TABLE__", "");
    }

    public String getSourceTable() {
        return getParameter("__SOURCE_TABLE_NAME__", "");
    }

    public String getCustomQuery() {
        return getParameter("__CUSTOM_QUERY__", "");
    }

    public boolean useBatch() {
        return getBooleanParameter("__USE_BATCH__", false);
    }

    public boolean useInsertOverwrite() {
        return getBooleanParameter("__USE_INSERT_OVERWRITE__", false);
    }

    public boolean useSourceTable() {
        return getBooleanParameter("__ALL_ROWS_FROM_SOURCE_TABLE__", false);
    }

    public boolean useCustomQuery() {
        return getBooleanParameter("__USE_A_CUSTOM_QUERY__", false);
    }

    public int batchSize() {
        return Integer.parseInt(getParameter("__BATCH_SIZE__", "10"));
    }
}
