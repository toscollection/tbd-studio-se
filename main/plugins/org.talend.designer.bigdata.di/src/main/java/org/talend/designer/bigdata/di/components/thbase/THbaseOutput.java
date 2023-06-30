package org.talend.designer.bigdata.di.components.thbase;

import org.immutables.value.Value;
import org.talend.core.model.metadata.types.JavaType;
import org.talend.core.model.metadata.types.JavaTypesManager;
import org.talend.core.model.process.INode;
import org.talend.designer.bigdata.di.components.DesignerDIComponent;
import org.talend.designer.codegen.config.CodeGeneratorArgument;

import java.util.*;

public class THbaseOutput implements THbase, DesignerDIComponent.WithDieOnErrorOption,
                                            DesignerDIComponent.WithOneInput {
    private final CodeGeneratorArgument codeGeneratorArgument;
    private final INode node;
    private final INode connNode;
    private final THBaseConfiguration tHBaseConfiguration;
    private final THbaseTable tHbaseTable;
   // private final JavaVariable javaVariable;

    public THbaseOutput(CodeGeneratorArgument codeGeneratorArgument) {
        this.codeGeneratorArgument = codeGeneratorArgument;
        this.node = (INode) codeGeneratorArgument().getArgument();
        this.connNode = setTargetNode();
        tHbaseTable = new THbaseTable(codeGeneratorArgument);
        tHBaseConfiguration = new THBaseConfiguration();
        //javaVariable = inputVariable();
    }

    @Override
    public CodeGeneratorArgument codeGeneratorArgument() {
        return this.codeGeneratorArgument;
    }

    @Override
    public INode node() {
        return this.node;
    }

    @Override
    public INode connNode() {
        return this.connNode;
    }

    @Override
    public Map<String, String> getConnectionConfiguration() {
        return this.tHBaseConfiguration.getConnectionConfiguration(this);
    }

    @Override
    public String getKeytab() {
        return this.tHBaseConfiguration.getKeytab(this);
    }

    @Override
    public boolean isConfigureFromClassPath() {
        return false;
    }

    @Override
    public boolean isSetTableNsMapping() {
        return BigDataDIComponent.getBooleanParameter(node, "__SET_TABLE_NS_MAPPING__");
    }

    @Override
    public String getTableNsMapping() {
        return BigDataDIComponent.getParameter(node, "__TABLE_NS_MAPPING__", "");
    }

    @Override
    public INode getNode() {
        return this.node;
    }

    public String getTableAction() {
        return tHbaseTable.getTableAction();
    }

    public List<Map<String, String>> getFamilyParameters() {
        return tHbaseTable.getFamilyParameters();
    }

    public boolean isCustomRowKey() {
        return BigDataDIComponent.getBooleanParameter(node, "__CUSTOM_ROW_KEY__");
    }

    public boolean isUseCustomTimestampColumn() {
        return BigDataDIComponent.getBooleanParameter(node, "__CUSTOM_TIMESTAMP_COLUMN__");
    }

    public String getCustomTimestampColumn() {
        return BigDataDIComponent.getParameter(node, "__TIMESTAMP_COLUMN__", "");
    }

    public boolean isUseBatchMode() {
        return BigDataDIComponent.getBooleanParameter(node, "__USE_BATCH_MODE__");
    }

    public String getBatchSize() {
        return BigDataDIComponent.getParameter(node, "__BATCH_SIZE__", "");
    }
//    public ImmutableHbaseData getData() {
//        final ImmutableHbaseData.Builder hbaseData = ImmutableHbaseData.builder();
//        final String customTimestamp = getCustomTimestampColumn();
//
//        INode node = (INode) codeGeneratorArgument().getArgument();
//        node.getMetadataList().stream()
//                .findFirst()
//                .orElseThrow(UnsupportedOperationException::new)
//                .getListColumns()
//                .forEach(iMetadataColumn -> {
//                            if (isUseCustomTimestampColumn() && iMetadataColumn.getLabel().equals(customTimestamp)) {
//                                if (JavaTypesManager.getJavaTypeFromId(iMetadataColumn.getTalendType()) == JavaTypesManager.LONG) {
//                                    hbaseData.customTimestampColumn(javaVariable.name() + "." + customTimestamp);
//                                } else {
//                                    throw new RuntimeException("Custom Timestamp Column should be long-typed");
//                                }
//                            }
//                            JavaType javaType = JavaTypesManager.getJavaTypeFromId(iMetadataColumn.getTalendType());
//                            String pattern = iMetadataColumn.getPattern() == null || iMetadataColumn.getPattern().trim().length() == 0 ? null : iMetadataColumn.getPattern();
//                            Map<String, String> mapLine = getFamilyParameters().get(0);
//                            String schema_column = mapLine.get("SCHEMA_COLUMN");
//                            String family_column = mapLine.get("FAMILY_COLUMN");
//                            if (family_column != null || family_column.trim().length() != 0) {
//                                boolean isPrimitive = JavaTypesManager.isJavaPrimitiveType( javaType, iMetadataColumn.isNullable());
//
//                                if (javaType == JavaTypesManager.DATE && pattern != null && pattern.trim().length() != 0) {
//                                    hbaseData.putColumn(iMetadataColumn.getOriginalDbColumnName(),
//                                            "FormatterUtils.format_Date("+javaVariable.name()+"."+iMetadataColumn.getLabel()+", "+pattern+")");
//                                } else if (javaType == JavaTypesManager.BYTE_ARRAY){
//                                    hbaseData.putColumn(iMetadataColumn.getOriginalDbColumnName(),
//                                            javaVariable.name()+"."+iMetadataColumn.getLabel());
//                                }else if (javaType == JavaTypesManager.BYTE){
//                                    hbaseData.putColumn(iMetadataColumn.getOriginalDbColumnName(),
//                                            javaVariable.name()+"."+iMetadataColumn.getLabel()+"+\"\"");
//                                } else if (isPrimitive) {
//                                    hbaseData.putColumn(iMetadataColumn.getOriginalDbColumnName(),
//                                            javaVariable.name()+"."+iMetadataColumn.getLabel());
//                                } else {
//                                    hbaseData.putColumn(iMetadataColumn.getOriginalDbColumnName(),
//                                            javaVariable.name()+"."+iMetadataColumn.getLabel()+".toString()");
//                                }
//
//                            }
//                            hbaseData.family(family_column);
//                        }
//                );
//
//        return hbaseData.build();
//    }

    @Value.Immutable
    @Value.Style(builderVisibility = Value.Style.BuilderVisibility.PACKAGE)
    public interface HbaseData {
        @Value.Parameter
        String family();

        @Value.Parameter
        String rowKey();

        @Value.Parameter
        Map<String, String> column();

        @Value.Parameter
        String customTimestampColumn();
    }

}
