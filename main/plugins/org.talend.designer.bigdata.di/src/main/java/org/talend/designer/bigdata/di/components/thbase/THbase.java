package org.talend.designer.bigdata.di.components.thbase;

import org.talend.core.model.process.ElementParameterParser;
import org.talend.core.model.process.INode;
import org.talend.designer.bigdata.di.components.DesignerDIComponent;
import org.talend.hadoop.distribution.DistributionFactory;
import org.talend.hadoop.distribution.component.HBaseComponent;

import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public interface THbase extends DesignerDIComponent.BigDataDIComponent {
    INode node();

    INode connNode();

    Map<String, String> getConnectionConfiguration();

    String getKeytab();

    boolean isConfigureFromClassPath();

    boolean isSetTableNsMapping();

    String getTableNsMapping();

    INode getNode();


    default INode setTargetNode() {
        if (useExistingConnection()) {
            return ElementParameterParser.getLinkedNodeValue(node(), "__CONNECTION__");
        }
        return node();
    }

    default String getHbaseVersion() {
        return DesignerDIComponent.BigDataDIComponent.getParameter(connNode(), "__HBASE_VERSION__", "");
    }

    default HBaseComponent getHbaseDistrib() {
        try {
            return (HBaseComponent) DistributionFactory.buildDistribution(getDistribution(), getHbaseVersion());
        } catch (Exception e) {
            return null;
        }
    }

    default String getDistribution() {
        return DesignerDIComponent.BigDataDIComponent.getParameter(connNode(), "__DISTRIBUTION__", "");
    }

    default String getZookeeperUrl() {
        return DesignerDIComponent.BigDataDIComponent.getParameter(node(), "__ZOOKEEPER_QUORUM__", "");
    }

    default String getZookeeperPort() {
        return DesignerDIComponent.BigDataDIComponent.getParameter(node(), "__ZOOKEEPER_CLIENT_PORT__", "");
    }

    default boolean isHBase2x() {
        if (getHbaseDistrib() != null) {
            return getHbaseDistrib().doSupportHBase2x();
        }
        return DesignerDIComponent.BigDataDIComponent.getParameter(node(), "__HBASE_API_VERSION__", "").equals("HBASE_2");
    }

    default boolean isCustom() {
        if (getDistribution() != null) {
            return getHbaseDistrib() instanceof org.talend.hadoop.distribution.custom.CustomDistribution;
        }
        return false;
    }

    default boolean isHadoop2() {
        return DesignerDIComponent.BigDataDIComponent.getBooleanParameter(node(), "__HADOOP_CUSTOM_VERSION__");
    }

    default boolean isZNodeParentSet() {
        return DesignerDIComponent.BigDataDIComponent.getBooleanParameter(node(), "__SET_ZNODE_PARENT__");
    }

    default String getZNodeParent() {
        return DesignerDIComponent.BigDataDIComponent.getParameter(node(), "__ZNODE_PARENT__", "");
    }

    default String getExistingConnection() {
        return DesignerDIComponent.BigDataDIComponent.getParameter(node(), "__CONNECTION__", "");
    }

    default boolean useKrb() {
        if (!useExistingConnection()) {
            return DesignerDIComponent.BigDataDIComponent.getBooleanParameter(node(), "__USE_KRB__");
        }
        return false;
    }

    default boolean useKeytab() {
        if (!useExistingConnection()) {
            return DesignerDIComponent.BigDataDIComponent.getBooleanParameter(node(), "__USE_KEYTAB__");
        }
        return false;
    }

    default String getUsePrincipal() {
        return DesignerDIComponent.BigDataDIComponent.getParameter(node(), "__PRINCIPAL__", "");
    }

    default String getKeytabPath() {
        return DesignerDIComponent.BigDataDIComponent.getParameter(node(), "__KEYTAB_PATH__", "");
    }

    default String getHbaseMasterPrincipal() {
        return DesignerDIComponent.BigDataDIComponent.getParameter(node(), "__HBASE_MASTER_PRINCIPAL__", "");
    }

    default String getHbaseRegionServerPrincipal() {
        return DesignerDIComponent.BigDataDIComponent.getParameter(node(), "__HBASE_REGIONSERVER_PRINCIPAL__", "");
    }

    default boolean isSparkDistrib() {
        return "SPARK".equals(getDistribution());
    }

    default Map<String, String> getHbaseParameters() {
        Map<String, String> result = new HashMap<>();
        List<Map<String, String>> properties = BigDataDIComponent.tableParameter(node(), "__HBASE_PARAMETERS__", Collections.emptyList());
        if (!properties.isEmpty()) {
            for (Map<String, String> mapItem : properties) {
                result.put(THbaseUtils.trimDuplicateQuotes(mapItem.get("PROPERTY")),
                        THbaseUtils.trimDuplicateQuotes(mapItem.get("VALUE")));
            }
        }
        return result;
    }

    default boolean isSpecifyNamespace() {
        return DesignerDIComponent.BigDataDIComponent.getBooleanParameter(node(), "__SPECIFY_NAMESPACE__");
    }

    default String getNamespace() {
        return DesignerDIComponent.BigDataDIComponent.getParameter(node(), "__NAMESPACE__", "");
    }

    default String getTableName() {
        return DesignerDIComponent.BigDataDIComponent.getParameter(node(), "__TABLE__", "");
    }

    default boolean isUseMapRTicket() {
        return DesignerDIComponent.BigDataDIComponent.getBooleanParameter(node(), "__USE_MAPRTICKET__");
    }

    default String getMapRUserName() {
        return DesignerDIComponent.BigDataDIComponent.getParameter(node(), "__USERNAME__", "");
    }

    default String getMapRTicketCluster() {
        return DesignerDIComponent.BigDataDIComponent.getParameter(node(), "__MAPRTICKET_CLUSTER__", "");
    }

    default String getMapRTicketDuration() {
        return DesignerDIComponent.BigDataDIComponent.getParameter(node(), "__MAPRTICKET_DURATION__", "");
    }

    default boolean isSetMapRHomeDir() {
        return DesignerDIComponent.BigDataDIComponent.getBooleanParameter(node(), "__SET_MAPR_HOME_DIR__");
    }

    default String getMapRHomeDir() {
        return DesignerDIComponent.BigDataDIComponent.getParameter(node(), "__MAPR_HOME_DIR__", "");
    }

    default boolean isSetMapRHadoopLogin() {
        return DesignerDIComponent.BigDataDIComponent.getBooleanParameter(node(), "__SET_HADOOP_LOGIN__");
    }

    default String getMapRHadoopLogin() {
        return DesignerDIComponent.BigDataDIComponent.getParameter(node(), "__HADOOP_LOGIN__", "");
    }

    default boolean useExistingConnection() {
        return DesignerDIComponent.BigDataDIComponent.getBooleanParameter(node(), "__USE_EXISTING_CONNECTION__");
    }
}
