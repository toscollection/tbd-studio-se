package com.talend.tuj.generator.processors;

import com.talend.tuj.generator.elements.IElement;
import com.talend.tuj.generator.elements.JobElement;
import com.talend.tuj.generator.utils.*;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class SparkStandaloneToYARNProcessor extends IProcessor {
    @Override
    public boolean shouldBeProcessed(IElement component) {
        if(component.isOfClass(ComponentClass.Job)){
            JobElement jobComponent = (JobElement)component;
            return (jobComponent.isJobOfFramework(JobFramework.SPARK) || jobComponent.isJobOfFramework(JobFramework.SPARK_STREAMING)) &&
                jobComponent.isOfType(NodeType.JOBCONFIG) &&
                jobComponent.getParameter("SPARK_MODE").isPresent() &&
                jobComponent.getParameter("SPARK_MODE").get().equals("CLUSTER");
        }
        return false;
    }

    @Override
    public void process(IElement component) {
        JobElement jobConfig = (JobElement)component;
        Map<String, String> newContextParameters = new HashMap<>();
        newContextParameters.put("scheduler","null");
        newContextParameters.put("staging_directory","null");

        jobConfig.getParentJob().addContextParameters(newContextParameters);
        jobConfig.getParentJob().getTuj().ifPresent(
                tuj -> tuj.getStarterJob().addContextParameters(newContextParameters)
        );

        Map<String, String> newSparkParameters = new HashMap<>();

        newSparkParameters.put("SPARK_MODE","YARN_CLIENT");
        newSparkParameters.put("RESOURCE_MANAGER","context.resourcemanager");
        newSparkParameters.put("SET_SCHEDULER_ADDRESS","true");
        newSparkParameters.put("RESOURCEMANAGER_SCHEDULER_ADDRESS","context.scheduler");
        newSparkParameters.put("SET_JOBHISTORY_ADDRESS","true");
        newSparkParameters.put("JOBHISTORY_ADDRESS","context.jobhistory");
        newSparkParameters.put("SET_STAGING_DIRECTORY","true");
        newSparkParameters.put("STAGING_DIRECTORY","context.staging_directory");
        newSparkParameters.put("DEFINE_HADOOP_HOME_DIR","false");
        newSparkParameters.put("DEFINE_SPARK_DRIVER_HOST","false");
        newSparkParameters.put("USERNAME","context.username");

        jobConfig.replaceParameters(newSparkParameters);

        List<JobElement> tHDFSConfigurations = jobConfig.getParentJob().getComponents().stream().filter(
                c -> c.getAttribute("componentName").orElse("").equals("tHDFSConfiguration")
        ).collect(Collectors.toList());
        if(tHDFSConfigurations.size() == 1){
            // Add username to tHDFSConfiguration components as it is required with YARN.
            tHDFSConfigurations.forEach(
                    c -> c.replaceParameter("USERNAME", "context.username")
            );
        } else if(tHDFSConfigurations.size() == 0){
            // Add tHDFSConfiguration component as it is required with YARN but not with Spark Standalone.
            createtHDFSComponent(jobConfig.getParentJob());
        }
    }

    private void createtHDFSComponent(Job job){
        if(!job.getFramework().equals(JobFramework.SPARK) && !job.getFramework().equals(JobFramework.SPARK_STREAMING)) return;

        JobElement jobConf = job.getJobConfiguration();

        Document root = job.getItem();
        Element tHDFSConfiguration = root.createElement("node");
        tHDFSConfiguration.setAttribute("componentName","tHDFSConfiguration");
        tHDFSConfiguration.setAttribute("componentVersion","0.101");
        tHDFSConfiguration.setAttribute("offsetLabelX","0");
        tHDFSConfiguration.setAttribute("offsetLabelY","0");
        tHDFSConfiguration.setAttribute("posX","16");
        tHDFSConfiguration.setAttribute("posY","16");
        tHDFSConfiguration.setAttribute("xmi:id", JobID.generateJobID());

        tHDFSConfiguration.appendChild(createtHDFSConfigurationParameter(root, "TEXT", "UNIQUE_NAME", "tHDFSConfiguration_1", false));
        tHDFSConfiguration.appendChild(createtHDFSConfigurationParameter(root, "CLOSED_LIST", "DISTRIBUTION", jobConf.getParameter("DISTRIBUTION").orElse("CLOUDERA"), true));
        tHDFSConfiguration.appendChild(createtHDFSConfigurationParameter(root, "CLOSED_LIST", "DB_VERSION", jobConf.getParameter("SPARK_VERSION").orElse("Cloudera_CDH5_13_0_dynamic_20180226115614083_0100"), true));
        tHDFSConfiguration.appendChild(createtHDFSConfigurationParameter(root, "HADOOP_DISTRIBUTION", "HDFS", "", true));
        tHDFSConfiguration.appendChild(createtHDFSConfigurationParameter(root, "HADOOP_JARS_DIALOG", "HADOOP_CUSTOM_JARS", "", false));
        tHDFSConfiguration.appendChild(createtHDFSConfigurationParameter(root, "TEXT", "FS_DEFAULT_NAME", "context.namenode", true));
        tHDFSConfiguration.appendChild(createtHDFSConfigurationParameter(root, "CHECK", "USE_KRB", "false", true));
        tHDFSConfiguration.appendChild(createtHDFSConfigurationParameter(root, "TEXT", "NAMENODE_PRINCIPAL", "\"nn/_HOST@EXAMPLE.COM\"", false));
        tHDFSConfiguration.appendChild(createtHDFSConfigurationParameter(root, "CHECK", "USE_KEYTAB", "false", false));
        tHDFSConfiguration.appendChild(createtHDFSConfigurationParameter(root, "TEXT", "PRINCIPAL", "\"hdfs\"", false));
        tHDFSConfiguration.appendChild(createtHDFSConfigurationParameter(root, "FILE", "KEYTAB_PATH", "\"/tmp/hdfs.headless.keytab\"", false));
        tHDFSConfiguration.appendChild(createtHDFSConfigurationParameter(root, "TEXT", "USERNAME", "context.username", true));
        tHDFSConfiguration.appendChild(createtHDFSConfigurationParameter(root, "TEXT", "GROUP", "\"supergroup\"", false));
        tHDFSConfiguration.appendChild(createtHDFSConfigurationParameter(root, "CHECK", "USE_DATANODE_HOSTNAME", "true", true));
        tHDFSConfiguration.appendChild(createtHDFSConfigurationParameter(root, "TEXT", "TEMP_FOLDER", "\"/tmp\"", true));
        tHDFSConfiguration.appendChild(createtHDFSConfigurationParameter(root, "CHECK", "USE_HDFS_ENCRYPTION", "false", true));
        tHDFSConfiguration.appendChild(createtHDFSConfigurationParameter(root, "TEXT", "HDFS_ENCRYPTION_KEY_PROVIDER", "\"kms://http@localhost:16000/kms\"", false));
        tHDFSConfiguration.appendChild(createtHDFSConfigurationParameter(root, "TABLE", "HADOOP_ADVANCED_PROPERTIES", "", true));
        tHDFSConfiguration.appendChild(createtHDFSConfigurationParameter(root, "TEXT", "CONNECTION_FORMAT", "row", true));

        Node insertRoot = root.getElementsByTagName("talendfile:ProcessType").item(0);

        List<JobElement> components = job.getComponents();
        insertRoot.insertBefore(tHDFSConfiguration, components.get(components.size()-1).getRawNode());

        Element subjob = root.createElement("subjob");
        subjob.setAttribute("xmi:id", JobID.generateJobID());
        subjob.appendChild(createtHDFSConfigurationParameter(root, "TEXT", "UNIQUE_NAME", "tHDFSConfiguration_1", false));
        subjob.appendChild(createtHDFSConfigurationParameter(root, "COLOR", "SUBJOB_TITLE_COLOR", "92;131;150", false));
        subjob.appendChild(createtHDFSConfigurationParameter(root, "COLOR", "SUBJOB_COLOR", "207;226;236", true));

        components = job.getSubJobs();
        insertRoot.insertBefore(subjob, components.get(components.size()-1).getRawNode());
    }

    private Element createtHDFSConfigurationParameter(Document root, String field, String name, String value, boolean show){
        Element element = root.createElement("elementParameter");
        element.setAttribute("field", field);
        element.setAttribute("name", name);
        if (!show) element.setAttribute("show", "false");
        if (!field.equals("TABLE")) element.setAttribute("value", value);
        element.setAttribute("xmi:id", JobID.generateJobID());

        return element;
    }
}
