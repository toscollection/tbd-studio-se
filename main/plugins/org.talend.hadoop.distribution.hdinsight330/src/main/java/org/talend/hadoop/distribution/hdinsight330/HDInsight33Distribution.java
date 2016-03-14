// ============================================================================
//
// Copyright (C) 2006-2016 Talend Inc. - www.talend.com
//
// This source code is available under agreement available at
// %InstallDIR%\features\org.talend.rcp.branding.%PRODUCTNAME%\%PRODUCTNAME%license.txt
//
// You should have received a copy of the agreement
// along with this program; if not, write to Talend SA
// 9 rue Pages 92150 Suresnes, France
//
// ============================================================================

package org.talend.hadoop.distribution.hdinsight330;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

import org.talend.core.hadoop.version.EHadoopDistributions;
import org.talend.hadoop.distribution.AbstractDistribution;
import org.talend.hadoop.distribution.ComponentType;
import org.talend.hadoop.distribution.DistributionModuleGroup;
import org.talend.hadoop.distribution.EHadoopVersion;
import org.talend.hadoop.distribution.ESparkVersion;
import org.talend.hadoop.distribution.NodeComponentTypeBean;
import org.talend.hadoop.distribution.component.HiveComponent;
import org.talend.hadoop.distribution.component.MRComponent;
import org.talend.hadoop.distribution.component.PigComponent;
import org.talend.hadoop.distribution.component.SparkBatchComponent;
import org.talend.hadoop.distribution.component.SparkStreamingComponent;
import org.talend.hadoop.distribution.condition.BasicExpression;
import org.talend.hadoop.distribution.condition.BooleanOperator;
import org.talend.hadoop.distribution.condition.ComponentCondition;
import org.talend.hadoop.distribution.condition.EqualityOperator;
import org.talend.hadoop.distribution.condition.MultiComponentCondition;
import org.talend.hadoop.distribution.condition.NestedComponentCondition;
import org.talend.hadoop.distribution.condition.SimpleComponentCondition;
import org.talend.hadoop.distribution.constants.Constant;
import org.talend.hadoop.distribution.constants.PigOutputConstant;
import org.talend.hadoop.distribution.constants.SparkBatchConstant;
import org.talend.hadoop.distribution.constants.SparkStreamingConstant;
import org.talend.hadoop.distribution.hdinsight330.modulegroup.HDInsight33HiveModuleGroup;
import org.talend.hadoop.distribution.hdinsight330.modulegroup.HDInsight33MapReduceModuleGroup;
import org.talend.hadoop.distribution.hdinsight330.modulegroup.HDInsight33PigModuleGroup;
import org.talend.hadoop.distribution.hdinsight330.modulegroup.HDInsight33PigOutputModuleGroup;
import org.talend.hadoop.distribution.hdinsight330.modulegroup.HDInsight33SparkBatchModuleGroup;
import org.talend.hadoop.distribution.hdinsight330.modulegroup.HDInsight33SparkStreamingModuleGroup;
import org.talend.hadoop.distribution.hdinsight330.modulegroup.node.pigoutput.HDInsight33PigOutputNodeModuleGroup;
import org.talend.hadoop.distribution.hdinsight330.modulegroup.node.sparkbatch.HDInsight33SparkBatchParquetNodeModuleGroup;
import org.talend.hadoop.distribution.hdinsight330.modulegroup.node.sparkbatch.HDInsight33SparkBatchS3NodeModuleGroup;
import org.talend.hadoop.distribution.hdinsight330.modulegroup.node.sparkstreaming.HDInsight33SparkStreamingKafkaAssemblyModuleGroup;
import org.talend.hadoop.distribution.hdinsight330.modulegroup.node.sparkstreaming.HDInsight33SparkStreamingKafkaAvroModuleGroup;
import org.talend.hadoop.distribution.hdinsight330.modulegroup.node.sparkstreaming.HDInsight33SparkStreamingKafkaClientModuleGroup;
import org.talend.hadoop.distribution.hdinsight330.modulegroup.node.sparkstreaming.HDInsight33SparkStreamingKinesisNodeModuleGroup;
import org.talend.hadoop.distribution.hdinsight330.modulegroup.node.sparkstreaming.HDInsight33SparkStreamingParquetNodeModuleGroup;
import org.talend.hadoop.distribution.hdinsight330.modulegroup.node.sparkstreaming.HDInsight33SparkStreamingS3NodeModuleGroup;

public class HDInsight33Distribution extends AbstractDistribution implements MRComponent, PigComponent, HiveComponent,
        SparkBatchComponent, SparkStreamingComponent {

    public final static String VERSION = "MICROSOFT_HD_INSIGHT_3_3"; //$NON-NLS-1$

    private final static String YARN_APPLICATION_CLASSPATH = "$HADOOP_CONF_DIR,$HADOOP_COMMON_HOME/*,$HADOOP_COMMON_HOME/lib/*,$HADOOP_HDFS_HOME/*,$HADOOP_HDFS_HOME/lib/*,$HADOOP_MAPRED_HOME/*,$HADOOP_MAPRED_HOME/lib/*,$YARN_HOME/*,$YARN_HOME/lib/*,$HADOOP_YARN_HOME/*,$HADOOP_YARN_HOME/lib/*,$HADOOP_COMMON_HOME/share/hadoop/common/*,$HADOOP_COMMON_HOME/share/hadoop/common/lib/*,$HADOOP_HDFS_HOME/share/hadoop/hdfs/*,$HADOOP_HDFS_HOME/share/hadoop/hdfs/lib/*,$HADOOP_YARN_HOME/share/hadoop/yarn/*,$HADOOP_YARN_HOME/share/hadoop/yarn/lib/*"; //$NON-NLS-1$

    private static Map<ComponentType, Set<DistributionModuleGroup>> moduleGroups;

    private static Map<NodeComponentTypeBean, Set<DistributionModuleGroup>> nodeModuleGroups;

    private static Map<ComponentType, ComponentCondition> displayConditions = new HashMap<>();

    static {

        // Used to add a module group import for the components that have a HADOOP_DISTRIBUTION parameter, aka. the
        // components that have the distribution list.
        moduleGroups = new HashMap<>();
        moduleGroups.put(ComponentType.MAPREDUCE, HDInsight33MapReduceModuleGroup.getModuleGroups());
        moduleGroups.put(ComponentType.PIG, HDInsight33PigModuleGroup.getModuleGroups());
        moduleGroups.put(ComponentType.PIGOUTPUT, HDInsight33PigOutputModuleGroup.getModuleGroups());
        moduleGroups.put(ComponentType.HIVE, HDInsight33HiveModuleGroup.getModuleGroups());
        moduleGroups.put(ComponentType.SPARKBATCH, HDInsight33SparkBatchModuleGroup.getModuleGroups());
        moduleGroups.put(ComponentType.SPARKSTREAMING, HDInsight33SparkStreamingModuleGroup.getModuleGroups());

        // Used to add a module group import for a specific node. The given node must have a HADOOP_LIBRARIES parameter.
        nodeModuleGroups = new HashMap<>();

        nodeModuleGroups.put(new NodeComponentTypeBean(ComponentType.PIG, PigOutputConstant.PIGSTORE_COMPONENT),
                HDInsight33PigOutputNodeModuleGroup.getModuleGroups());

        nodeModuleGroups.put(new NodeComponentTypeBean(ComponentType.SPARKBATCH, SparkBatchConstant.PARQUET_INPUT_COMPONENT),
                HDInsight33SparkBatchParquetNodeModuleGroup.getModuleGroups());
        nodeModuleGroups.put(new NodeComponentTypeBean(ComponentType.SPARKBATCH, SparkBatchConstant.PARQUET_OUTPUT_COMPONENT),
                HDInsight33SparkBatchParquetNodeModuleGroup.getModuleGroups());
        nodeModuleGroups.put(new NodeComponentTypeBean(ComponentType.SPARKBATCH, SparkBatchConstant.S3_CONFIGURATION_COMPONENT),
                HDInsight33SparkBatchS3NodeModuleGroup.getModuleGroups());
        nodeModuleGroups.put(new NodeComponentTypeBean(ComponentType.SPARKSTREAMING,
                SparkStreamingConstant.PARQUET_INPUT_COMPONENT), HDInsight33SparkStreamingParquetNodeModuleGroup
                .getModuleGroups());
        nodeModuleGroups.put(new NodeComponentTypeBean(ComponentType.SPARKSTREAMING,
                SparkStreamingConstant.PARQUET_OUTPUT_COMPONENT), HDInsight33SparkStreamingParquetNodeModuleGroup
                .getModuleGroups());
        nodeModuleGroups.put(new NodeComponentTypeBean(ComponentType.SPARKSTREAMING,
                SparkStreamingConstant.PARQUET_STREAM_INPUT_COMPONENT), HDInsight33SparkStreamingParquetNodeModuleGroup
                .getModuleGroups());
        nodeModuleGroups.put(new NodeComponentTypeBean(ComponentType.SPARKSTREAMING,
                SparkStreamingConstant.S3_CONFIGURATION_COMPONENT), HDInsight33SparkStreamingS3NodeModuleGroup.getModuleGroups());

        Set<DistributionModuleGroup> kinesisNodeModuleGroups = HDInsight33SparkStreamingKinesisNodeModuleGroup.getModuleGroups();
        nodeModuleGroups.put(new NodeComponentTypeBean(ComponentType.SPARKSTREAMING,
                SparkStreamingConstant.KINESIS_INPUT_COMPONENT), kinesisNodeModuleGroups);
        nodeModuleGroups.put(new NodeComponentTypeBean(ComponentType.SPARKSTREAMING,
                SparkStreamingConstant.KINESIS_INPUT_AVRO_COMPONENT), kinesisNodeModuleGroups);
        nodeModuleGroups.put(new NodeComponentTypeBean(ComponentType.SPARKSTREAMING,
                SparkStreamingConstant.KINESIS_OUTPUT_COMPONENT), kinesisNodeModuleGroups);

        Set<DistributionModuleGroup> kafkaAssemblyModuleGroups = HDInsight33SparkStreamingKafkaAssemblyModuleGroup
                .getModuleGroups();
        Set<DistributionModuleGroup> kafkaAvroModuleGroups = HDInsight33SparkStreamingKafkaAvroModuleGroup.getModuleGroups();
        nodeModuleGroups.put(
                new NodeComponentTypeBean(ComponentType.SPARKSTREAMING, SparkStreamingConstant.KAFKA_INPUT_COMPONENT),
                kafkaAssemblyModuleGroups);
        nodeModuleGroups.put(new NodeComponentTypeBean(ComponentType.SPARKSTREAMING,
                SparkStreamingConstant.KAFKA_AVRO_INPUT_COMPONENT), kafkaAssemblyModuleGroups);
        nodeModuleGroups.put(new NodeComponentTypeBean(ComponentType.SPARKSTREAMING,
                SparkStreamingConstant.KAFKA_AVRO_INPUT_COMPONENT), kafkaAvroModuleGroups);
        nodeModuleGroups
                .put(new NodeComponentTypeBean(ComponentType.SPARKSTREAMING, SparkStreamingConstant.KAFKA_OUTPUT_COMPONENT),
                        HDInsight33SparkStreamingKafkaClientModuleGroup.getModuleGroups());

        ComponentCondition c1 = new NestedComponentCondition(new MultiComponentCondition(new SimpleComponentCondition(
                new BasicExpression(Constant.PIG_STORE_PARAMETER, EqualityOperator.NOT_EQ, Constant.PIG_HBASESTORAGE_PARAMETER)),
                BooleanOperator.AND, new SimpleComponentCondition(new BasicExpression(Constant.PIG_STORE_PARAMETER,
                        EqualityOperator.NOT_EQ, Constant.PIG_HCATSTORER_PARAMETER))));
        displayConditions.put(ComponentType.PIGOUTPUT, c1);
    }

    @Override
    public boolean doSupportOldImportMode() {
        return false;
    }

    @Override
    public String getDistribution() {
        return EHadoopDistributions.MICROSOFT_HD_INSIGHT.getName();
    }

    @Override
    public String getDistributionName() {
        return EHadoopDistributions.MICROSOFT_HD_INSIGHT.getDisplayName();
    }

    @Override
    public String getVersion() {
        return VERSION;
    }

    @Override
    public String getVersionName(ComponentType componentType) {
        return "Microsoft HD Insight 3.3"; //$NON-NLS-1$
    }

    @Override
    public EHadoopVersion getHadoopVersion() {
        return EHadoopVersion.HADOOP_2;
    }

    @Override
    public boolean doSupportKerberos() {
        return false;
    }

    @Override
    public Set<DistributionModuleGroup> getModuleGroups(ComponentType componentType) {
        return moduleGroups.get(componentType);
    }

    @Override
    public Set<DistributionModuleGroup> getModuleGroups(ComponentType componentType, String componentName) {
        return nodeModuleGroups.get(new NodeComponentTypeBean(componentType, componentName));
    }

    @Override
    public boolean isExecutedThroughWebHCat() {
        return true;
    }

    @Override
    public boolean doSupportUseDatanodeHostname() {
        return false;
    }

    @Override
    public boolean doSupportCrossPlatformSubmission() {
        return false;
    }

    @Override
    public String getYarnApplicationClasspath() {
        return YARN_APPLICATION_CLASSPATH;
    }

    @Override
    public boolean doSupportHCatalog() {
        return true;
    }

    @Override
    public boolean pigVersionPriorTo_0_12() {
        return false;
    }

    @Override
    public boolean doSupportHBase() {
        return false;
    }

    @Override
    public boolean doSupportImpersonation() {
        return false;
    }

    @Override
    public boolean doSupportEmbeddedMode() {
        return false;
    }

    @Override
    public boolean doSupportStandaloneMode() {
        return false;
    }

    @Override
    public boolean doSupportHive1() {
        return false;
    }

    @Override
    public boolean doSupportHive2() {
        return false;
    }

    @Override
    public boolean doSupportTezForHive() {
        return false;
    }

    @Override
    public boolean doSupportHBaseForHive() {
        return false;
    }

    @Override
    public boolean doSupportSSL() {
        return false;
    }

    @Override
    public boolean doSupportORCFormat() {
        return true;
    }

    @Override
    public boolean doSupportAvroFormat() {
        return true;
    }

    @Override
    public boolean doSupportParquetFormat() {
        return true;
    }

    @Override
    public boolean doSupportStoreAsParquet() {
        return false;
    }

    @Override
    public boolean doSupportDynamicMemoryAllocation() {
        return false;
    }

    @Override
    public boolean isExecutedThroughSparkJobServer() {
        return true;
    }

    @Override
    public boolean doSupportCheckpointing() {
        return false;
    }

    @Override
    public ComponentCondition getDisplayCondition(ComponentType componentType) {
        return displayConditions.get(componentType);
    }

    @Override
    public ESparkVersion getSparkVersion() {
        return ESparkVersion.SPARK_1_5;
    }

    @Override
    public boolean doSupportSparkStandaloneMode() {
        return true;
    }

    @Override
    public boolean doSupportSparkYarnClientMode() {
        return false;
    }

    @Override
    public boolean doSupportBackpressure() {
        return false;
    }

}
