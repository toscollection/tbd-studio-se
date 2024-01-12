package org.talend.designer.bigdata.di.components.iceberg;

import org.talend.designer.bigdata.di.components.DesignerDIComponent;
import org.talend.designer.codegen.config.CodeGeneratorArgument;

public class TIcebergMerge implements DesignerDIComponent.WithConnection {

    protected final CodeGeneratorArgument codeGeneratorArgument;

    public TIcebergMerge(CodeGeneratorArgument arg) {
        this.codeGeneratorArgument = arg;
    }

    @Override
    public CodeGeneratorArgument codeGeneratorArgument() {
        return this.codeGeneratorArgument;
    }

    public String getMergeOnCondition() {
        return getParameter("__MERGE_ON_CONDITION__", "");
    }

    private String getTableSourceExpression() {
        String sourceAlias = getParameter("__SOURCE_ALIAS__", "");
        String sourceAliasSql = sourceAlias.isEmpty() || "\"\"".equals(sourceAlias) ? "" : "+\" AS \"+" + sourceAlias;
        return getParameter("__SOURCE_TABLE__", "") + sourceAliasSql;
    }

    private String getSqlSourceExpression() {
        return "\"(\" + " + getParameter("__SOURCE_SELECT_QUERY__", "") + " + \") AS \" + " + getParameter("__SOURCE_ALIAS__", "");
    }

    public String getSourceExpression() {
        return "TABLE".equals(getParameter("__SOURCE__", "TABLE")) ? getTableSourceExpression() : getSqlSourceExpression();
    }

    public String getTargetExpression() {
        String targetAlias = getParameter("__TARGET_ALIAS__", "");
        String targetAliasSql = targetAlias.isEmpty() || "\"\"".equals(targetAlias) ? "" : "+\" AS \"+" + targetAlias;
        return getParameter("__TARGET_TABLE__", "") + targetAliasSql;
    }

    public boolean whenMatchedDeleteChecked() {
        return getBooleanParameter("__WHEN_MATCHED_DELETE_CHECK__", false);
    }

    public String getWhenMatchedDeleteCondition() {
        return getParameter("__WHEN_MATCHED_DELETE_CONDITION__", "");
    }

    public boolean whenMatchedDeleteConditionChecked() {
        return getBooleanParameter("__WHEN_MATCHED_DELETE_CONDITION_CHECK__", false);
    }

    public boolean whenNotMatchedInsertChecked() {
        return getBooleanParameter("__WHEN_NOT_MATCHED_INSERT_CHECK__", false);
    }

    public String getWhenNotMatchedInsertCondition() {
        return getParameter("__WHEN_NOT_MATCHED_INSERT_CONDITION__", "");
    }

    public boolean whenNotMatchedInsertConditionChecked() {
        return getBooleanParameter("__WHEN_NOT_MATCHED_INSERT_CONDITION_CHECK__", false);
    }

    public String getWhenNotMatchedInsertValues() {
        return getParameter("__WHEN_NOT_MATCHED_INSERT_VALUES__", "");
    }

    public String getWhenMatchedUpdateCondition() {
        return getParameter("__WHEN_MATCHED_UPDATE_CONDITION__", "");
    }

    public boolean whenMatchedUpdateConditionChecked() {
        return getParameter("__WHEN_MATCHED_UPDATE_CONDITION_CHECK__", false);
    }

    public boolean whenMatchedUpdateChecked() {
        return getBooleanParameter("__WHEN_MATCHED_UPDATE_CHECK__", false);
    }

    public String getWhenMatchedUpdateClause() {
        return getParameter("__WHEN_MATCHED_UPDATE_CLAUSE__", "");
    }
}
