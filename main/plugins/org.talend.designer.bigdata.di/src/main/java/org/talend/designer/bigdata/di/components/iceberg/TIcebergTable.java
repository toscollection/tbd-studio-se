package org.talend.designer.bigdata.di.components.iceberg;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

import org.apache.commons.lang3.StringUtils;
import org.talend.core.model.metadata.IMetadataTable;
import org.talend.core.model.process.INode;
import org.talend.core.model.utils.NodeUtil;
import org.talend.designer.bigdata.di.components.DesignerDIComponent.WithSchema;
import org.talend.designer.bigdata.di.components.Schema;
import org.talend.designer.bigdata.di.components.Schema.Field;
import org.talend.designer.codegen.config.CodeGeneratorArgument;

public class TIcebergTable implements WithSchema {

    protected final CodeGeneratorArgument codeGeneratorArgument;

    private final static Map<String, String> tableActionToMethod = new HashMap<String, String>();

    static {
        tableActionToMethod.put("CHANGE_COLUMNS", "changeColumn()");
        tableActionToMethod.put("CREATE_TABLE", "create()");
        tableActionToMethod.put("CREATE_TABLE_IF_NOT_EXISTS", "create(true)");
        tableActionToMethod.put("DROP_COLUMNS", "dropColumns()");
        tableActionToMethod.put("DROP_TABLE", "drop()");
        tableActionToMethod.put("DROP_TABLE_IF_EXISTS", "drop(true)");
        tableActionToMethod.put("TRUNCATE", "truncate()");
        tableActionToMethod.put("SET_PARTITIONS", "alterPartitions()");
        tableActionToMethod.put("ADD_COLUMNS", "addColumns()");
    }

    public TIcebergTable(CodeGeneratorArgument arg) {
        this.codeGeneratorArgument = arg;
    }

    public boolean isChangeColumnAction() {
        return "CHANGE_COLUMNS".equals(getActionName());
    }

    @Override
    public List<Field> getFields() {
        if (isChangeColumnAction()) {
            ArrayList<Map<String, String>> mappingTable = getColumnsMappingTable();
            INode node = (INode) codeGeneratorArgument().getArgument();
            return node.getMetadataList()
                    .stream()
                    .findFirst()
                    .get()
                    .getListColumns()
                    .stream()
                    .filter(field -> {
                        for (Map<String, String> tableItem : mappingTable) {
                            if (field.getLabel().equals(tableItem.get("TARGET_COLUMN"))) {
                                return true;
                            }
                        }
                        return false;
                    })
                    .map(c -> new Schema.Field(c.getLabel(), Schema.StudioToJavaType(c.getTalendType())))
                    .collect(Collectors.toList());
        } else {
            return WithSchema.super.getFields();
        }
    }

    protected ArrayList<Map<String, String>> getColumnsMappingTable() {
        ArrayList<Map<String, String>> mappingTable =
                getParameter("__COLUMN_MAPPING__", new ArrayList<Map<String, String>>());
        return mappingTable;
    }

    @Override
    public CodeGeneratorArgument codeGeneratorArgument() {
        return this.codeGeneratorArgument;
    }

    public String connectionComponentName() {
        return getParameter("__CONNECTION__", "");
    }

    public String table() {
        return getParameter("__TABLE__", "");
    }

    public String getActionName() {
        String actionName = getParameter("__TABLE_ACTION__", "CREATE_TABLE");
        if ("ALTER".equals(actionName)) {
            return getParameter("__ALTER_TABLE_ACTION__", "ADD_COLUMNS");
        }
        return actionName;
    }

    public String actionMethod() {
        return tableActionToMethod.get(getActionName());
    }

    public boolean externalTable() {
        return getBooleanParameter("__EXTERNAL__", false);
    }

    public String format() {
        return getParameter("__FORMAT__", "PARQUET");
    }

    public boolean setFormat() {
        return getBooleanParameter("__SET_FORMAT__", false);
    }

    public boolean createLike() {
        return getBooleanParameter("__CREATE_LIKE__", false);
    }

    public String createLikeSourceTable() {
        return getParameter("__SOURCE_TABLE__", "");
    }

    public boolean createAsSelect() {
        return getBooleanParameter("__CREATE_AS_SELECT__", false);
    }

    public String createAsSelectQuery() {
        return NodeUtil.replaceCRLFInMEMO_SQL(getParameter("__AS_SELECT_QUERY__", ""));
    }

    public List<Schema.Field> partitions() {
        INode node = (INode) codeGeneratorArgument().getArgument();
        Optional<IMetadataTable> partitions = node.getMetadataList().stream().filter(m -> "PARTITIONS".equals(m.getTableName())).findFirst();
        return !createAsSelect() && partitions.isPresent()
                ? partitions.get().getListColumns().stream().map(c -> new Schema.Field(c.getLabel(), Schema.StudioToJavaType(c.getTalendType()))).collect(Collectors.toList())
                : Collections.emptyList();
    }

    public List<String> newPartitions() {
        return getParameter("__NEW_PARTITIONS__", Collections.<Map<String, String>> emptyList()).stream().map(p -> p.get("PARTITION")).collect(Collectors.toList());
    }

    public Map<String, String> tableProperties() {
        Map<String, String> tableProperties = new HashMap<String, String>();
        getParameter("__TABLE_PROPERTIES__", Collections.<Map<String, String>> emptyList()).stream().forEach(p -> tableProperties.put(p.get("PROPERTY"), p.get("VALUE")));
        return tableProperties;
    }

    public boolean setLocation() {
        return getBooleanParameter("__SET_LOCATION__", false);
    }

    public String location() {
        return getParameter("__LOCATION__", "");
    }

    public boolean purge() {
        return getBooleanParameter("__PURGE__", false);
    }

    public String getRenameCol(Schema.Field targetField) {
        ArrayList<Map<String, String>> mappingTable = getColumnsMappingTable();
        for (Map<String, String> tableItem : mappingTable) {
            if (targetField.getName().equals(tableItem.get("TARGET_COLUMN"))) {
                // toLowerCasse rename value to avoid same with keyword of iceberg
                return StringUtils.isEmpty(tableItem.get("RENAME")) ? targetField.getName().toLowerCase()
                        : tableItem.get("RENAME").toLowerCase();
            }

        }
        return targetField.getName().toLowerCase();
    }

    public String getType(Schema.Field targetField) {
        ArrayList<Map<String, String>> mappingTable = getColumnsMappingTable();
        for (Map<String, String> tableItem : mappingTable) {
            if (targetField.getName().equals(tableItem.get("TARGET_COLUMN"))) {
                return StringUtils.isEmpty(tableItem.get("TYPE")) ? targetField.getType() : tableItem.get("TYPE");
            }

        }
        return targetField.getType();
    }

    public String getAfterCol(Schema.Field targetField) {
        ArrayList<Map<String, String>> mappingTable = getColumnsMappingTable();
        for (Map<String, String> tableItem : mappingTable) {
            if (targetField.getName().equals(tableItem.get("TARGET_COLUMN"))) {
                return tableItem.get("AFTER");
            }

        }
        return "";
    }

}
