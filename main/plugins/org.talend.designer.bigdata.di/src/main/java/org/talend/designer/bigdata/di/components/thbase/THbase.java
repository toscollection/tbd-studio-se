package org.talend.designer.bigdata.di.components.thbase;

import org.talend.core.model.process.ElementParameterParser;
import org.talend.core.model.process.INode;
import org.talend.designer.bigdata.di.components.DesignerDIComponent;
import org.talend.designer.codegen.config.CodeGeneratorArgument;
import org.talend.hadoop.distribution.component.HBaseComponent;
import org.talend.hadoop.distribution.DistributionFactory;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public abstract class THbase implements DesignerDIComponent.BigDataDIComponent,
        DesignerDIComponent.WithDieOnErrorOption {

    protected CodeGeneratorArgument codeGeneratorArgument;
    protected INode node;
    protected INode connNode;

    public THbase(CodeGeneratorArgument codeGeneratorArgument) {
        this.codeGeneratorArgument = codeGeneratorArgument;
        this.node = (INode) codeGeneratorArgument().getArgument();
        this.connNode = setTargetNode();
    }
    @Override
    public CodeGeneratorArgument codeGeneratorArgument() {
        return this.codeGeneratorArgument;
    }

    private INode setTargetNode(){
        if (isUseExistingConnection()){
            return ElementParameterParser.getLinkedNodeValue(node, "__CONNECTION__");
        }
        return node;
    }

    public INode getNode(){
        return this.node;
    }
    @Override
    public boolean getDieOnError() {
        return WithDieOnErrorOption.super.getDieOnError();
    }

    public HBaseComponent getHbaseDistrib() {
        try {
            return (HBaseComponent) DistributionFactory.buildDistribution(getDistribution(), getHbaseVersion());
        } catch (Exception e) {
            return null;
        }
    }

    public String getHbaseVersion(){
        return BigDataDIComponent.getParameter(connNode,"__HBASE_VERSION__","");
    }
    public String getDistribution(){
        return BigDataDIComponent.getParameter(connNode,"__DISTRIBUTION__","");
    }
    public String getZookeeperUrl() {
        return BigDataDIComponent.getParameter(node,"__ZOOKEEPER_QUORUM__","");
    }
    public String getZookeeperPort() {
        return BigDataDIComponent.getParameter(node,"__ZOOKEEPER_CLIENT_PORT__","");
    }
    public boolean isHBase2x(){
        if (getHbaseDistrib() != null) {
            return getHbaseDistrib().doSupportHBase2x();
        }
        return BigDataDIComponent.getParameter(node, "__HBASE_API_VERSION__","").equals("HBASE_2");
    }
    public boolean isCustom() {
        if (getDistribution() != null){
            return getHbaseDistrib() instanceof org.talend.hadoop.distribution.custom.CustomDistribution;
        }
        return false;
    }
    public boolean isHadoop2(){
        return BigDataDIComponent.getBooleanParameter(node, "__HADOOP_CUSTOM_VERSION__");
    }
    public boolean isZNodeParentSet(){
        return BigDataDIComponent.getBooleanParameter(node, "__SET_ZNODE_PARENT__");
    }
    public String getZNodeParent() {
        return BigDataDIComponent.getParameter(node,"__ZNODE_PARENT__","");
    }
    public boolean isUseExistingConnection(){
        return BigDataDIComponent.getBooleanParameter(node, "__USE_EXISTING_CONNECTION__");
    }
    public String getExistingConnection(){
        return BigDataDIComponent.getParameter(node, "__CONNECTION__", "");
    }
    public boolean useKrb(){
        if (!isUseExistingConnection()){
            return BigDataDIComponent.getBooleanParameter(node,"__USE_KRB__");
        }
        return false;
    }
    public boolean useKeytab(){
        if (!isUseExistingConnection()){
            return BigDataDIComponent.getBooleanParameter(node,"__USE_KEYTAB__");
        }
        return false;
    }
    public String getUsePrincipal(){
        return BigDataDIComponent.getParameter(node,"__PRINCIPAL__","");
    }
    public String getKeytabPath(){
        return BigDataDIComponent.getParameter(node,"__KEYTAB_PATH__", "");
    }
    public String getHbaseMasterPrincipal(){
        return BigDataDIComponent.getParameter(node,"__HBASE_MASTER_PRINCIPAL__","");
    }
    public String getHbaseRegionServerPrincipal(){
        return BigDataDIComponent.getParameter(node,"__HBASE_REGIONSERVER_PRINCIPAL__","");
    }
    public boolean isSparkDistrib(){
        return "SPARK".equals(getDistribution());
    }
    public Map<String,String> getHbaseParameters(){
        Map<String, String> result = new HashMap<>();

        List<Map<String,String>> properties = ElementParameterParser.getTableValue(node,"__HBASE_PARAMETERS__");

        for (Map<String, String> mapItem: properties) {
            result.put(trimString(mapItem.get("PROPERTY")),trimString(mapItem.get("VALUE")));
        }
        return  result;
    }

    private String trimString(String inputString){
        if (inputString.length()<2) return "";

        StringBuilder sb = new StringBuilder();
        sb.append(inputString);
        if ( inputString.charAt(0) == '\"' &&  inputString.charAt(1) == '\"'){
            sb.deleteCharAt(0);
        }
        if ( inputString.charAt(inputString.length()-1) == '\"' && inputString.charAt(inputString.length()-2) == '\"'){
            sb.deleteCharAt(inputString.length()-1);
        }
        return sb.toString();
    }
    public boolean isSpecifyNamespace(){
        return BigDataDIComponent.getBooleanParameter(node,"__SPECIFY_NAMESPACE__");
    }
    public String getNamespace(){
            return BigDataDIComponent.getParameter(node, "__NAMESPACE__", "");
    }
    public String getTableName(){
        return BigDataDIComponent.getParameter(node, "__TABLE__", "");
    }

    public boolean isConfigureFromClassPath(){
        return BigDataDIComponent.getBooleanParameter(node,"__CONFIGURATIONS_FROM_CLASSPATH__");
    }
    public boolean isUseMapRTicket(){
        return BigDataDIComponent.getBooleanParameter(node,"__USE_MAPRTICKET__");
    }
    public String getMapRUserName(){
        return BigDataDIComponent.getParameter(node,"__USERNAME__","");
    }
    public String getMapRTicketCluster(){
        return BigDataDIComponent.getParameter(node,"__MAPRTICKET_CLUSTER__","");
    }
    public String getMapRTicketDuration(){
        return BigDataDIComponent.getParameter(node,"__MAPRTICKET_DURATION__","");
    }
    public boolean isSetMapRHomeDir(){
        return BigDataDIComponent.getBooleanParameter(node,"__SET_MAPR_HOME_DIR__");
    }
    public String getMapRHomeDir(){
        return BigDataDIComponent.getParameter(node,"__MAPR_HOME_DIR__","");
    }
    public boolean isSetMapRHadoopLogin(){
        return BigDataDIComponent.getBooleanParameter(node,"__SET_HADOOP_LOGIN__");
    }
    public String getMapRHadoopLogin(){
        return BigDataDIComponent.getParameter(node,"__HADOOP_LOGIN__","");
    }
}
