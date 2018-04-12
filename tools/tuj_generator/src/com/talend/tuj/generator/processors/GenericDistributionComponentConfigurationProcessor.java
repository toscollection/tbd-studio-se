package com.talend.tuj.generator.processors;

import com.talend.tuj.generator.elements.IElement;
import com.talend.tuj.generator.utils.NodeType;

public class GenericDistributionComponentConfigurationProcessor extends IProcessor {
    private String distribution_name;
    private String distribution_version;

    public GenericDistributionComponentConfigurationProcessor(String distribution_name, String distribution_version) {
        this.distribution_version = distribution_version;
        this.distribution_name = distribution_name;
    }

    @Override
    public boolean shouldBeProcessed(IElement component) {
        return component.isOfType(NodeType.COMPONENT) &&
            (
                component.getParameter("USE_EXISTING_CONNECTION").orElse("").equals("false") ||
                component.getAttribute("componentName").orElse("").endsWith("Connection") ||
                component.getAttribute("componentName").orElse("").endsWith("Configuration") ||
                component.getAttribute("componentName").orElse("").startsWith("tSqoop")
            );
    }

    @Override
    public void process(IElement component) {
        component.replaceParameter("DISTRIBUTION", distribution_name);
        String componentName = component.getAttribute("componentName").orElse("");
        if (componentName.startsWith("tHDFS") || componentName.startsWith("tSqoop")) {
            component.replaceParameter("DB_VERSION", distribution_version);
        } else if (componentName.startsWith("tHBase")) {
            component.replaceParameter("HBASE_VERSION", distribution_version);
        } else if (componentName.startsWith("tPig")) {
            component.replaceParameter("PIG_VERSION", distribution_version);
        } else if (componentName.startsWith("tHive")) {
            component.replaceParameter("HIVE_VERSION", distribution_version);
        } else if (componentName.startsWith("tHCatalog")) {
            component.replaceParameter("HCAT_VERSION", distribution_version);
        } else if (componentName.startsWith("tImpala")) {
            component.replaceParameter("IMPALA_VERSION", distribution_version);
        } else if (componentName.startsWith("tKafka")) {
            component.replaceParameter("SPARK_VERSION", distribution_version);
        } else if (componentName.startsWith("tRedshift") || componentName.startsWith("tDynamoDB") || componentName.startsWith("tS3") || componentName.startsWith("tAzureFS") || componentName.startsWith("tMysql")) {

        } else { // TODO add others unsupported components
            System.err.println("Distribution configuration is not supported in this component : " + componentName);
        }
    }
}
