// ============================================================================
//
// Copyright (C) 2006-2021 Talend Inc. - www.talend.com
//
// This source code is available under agreement available at
// %InstallDIR%\features\org.talend.rcp.branding.%PRODUCTNAME%\%PRODUCTNAME%license.txt
//
// You should have received a copy of the agreement
// along with this program; if not, write to Talend SA
// 9 rue Pages 92150 Suresnes, France
//
// ============================================================================
package org.talend.hadoop.distribution;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

import org.talend.hadoop.distribution.condition.BasicExpression;
import org.talend.hadoop.distribution.condition.ComponentCondition;
import org.talend.hadoop.distribution.condition.EqualityOperator;
import org.talend.hadoop.distribution.condition.SimpleComponentCondition;
import org.talend.hadoop.distribution.condition.common.HDFSLinkedNodeCondition;
import org.talend.hadoop.distribution.condition.common.SparkStreamingLinkedNodeCondition;
import org.talend.hadoop.distribution.constants.HDFSConstant;
import org.talend.hadoop.distribution.constants.ModuleGroupName;
import org.talend.hadoop.distribution.constants.SparkBatchConstant;
import org.talend.hadoop.distribution.constants.SparkStreamingConstant;
import org.talend.hadoop.distribution.utils.ModuleGroupsUtils;

public abstract class AbstractSparkDistribution extends AbstractDistribution {

	@Override
	protected Map<ComponentType, Set<DistributionModuleGroup>> buildModuleGroups() {
        Map<ComponentType, Set<DistributionModuleGroup>> result = new HashMap<>();
        result.put(ComponentType.SPARKBATCH, ModuleGroupsUtils.getModuleGroups(null, ModuleGroupName.SPARK_COMPILE.get(this.getVersion()), false));
        result.put(ComponentType.SPARKSTREAMING, ModuleGroupsUtils.getModuleGroups(null, ModuleGroupName.SPARK_COMPILE.get(this.getVersion()), false));
        return result;
    }
    
	@Override
    protected Map<NodeComponentTypeBean, Set<DistributionModuleGroup>> buildNodeModuleGroups(String distribution, String version) { 
        
        Map<NodeComponentTypeBean, Set<DistributionModuleGroup>> result = new HashMap<>();
        
        // s3
        ComponentCondition s3Condition = new SparkStreamingLinkedNodeCondition(distribution, version, SparkBatchConstant.SPARK_BATCH_AZURE_SPARKCONFIGURATION_LINKEDPARAMETER).getCondition();

        result.put(new NodeComponentTypeBean(ComponentType.SPARKBATCH, SparkBatchConstant.S3_CONFIGURATION_COMPONENT), 
                ModuleGroupsUtils.getModuleGroups(distribution, version, s3Condition, ModuleGroupName.S3.get(this.getVersion()), true));
        
        result.put(new NodeComponentTypeBean(ComponentType.SPARKSTREAMING, SparkStreamingConstant.S3_CONFIGURATION_COMPONENT),
                ModuleGroupsUtils.getModuleGroups(distribution, version, s3Condition, ModuleGroupName.S3.get(this.getVersion()), true));

        
        // Azure
        ComponentCondition azureCondition = new SparkStreamingLinkedNodeCondition(distribution, version, SparkBatchConstant.SPARK_BATCH_AZURE_SPARKCONFIGURATION_LINKEDPARAMETER).getCondition();

        result.put(new NodeComponentTypeBean(ComponentType.SPARKBATCH, SparkBatchConstant.AZURE_CONFIGURATION_COMPONENT), 
                ModuleGroupsUtils.getModuleGroups(distribution, version, azureCondition, ModuleGroupName.AZURE.get(this.getVersion()), true));
        
        result.put(new NodeComponentTypeBean(ComponentType.SPARKSTREAMING, SparkStreamingConstant.AZURE_CONFIGURATION_COMPONENT),
                ModuleGroupsUtils.getModuleGroups(distribution, version, azureCondition, ModuleGroupName.AZURE.get(this.getVersion()), true));

        
        // Spark Batch BigQuery
        result.put(new NodeComponentTypeBean(ComponentType.SPARKBATCH, SparkBatchConstant.BIGQUERY_CONFIG_COMPONENT), 
                    ModuleGroupsUtils.getModuleGroups(distribution, version, (String) null, ModuleGroupName.BIGQUERY.get(this.getVersion()), true));

        // DynamoDB nodes ...
        Set<DistributionModuleGroup> dynamoDBBatchNodeModuleGroups = ModuleGroupsUtils.getModuleGroups(distribution, version, "USE_EXISTING_CONNECTION == 'false'", ModuleGroupName.DYNAMODB_BATCH.get(this.getVersion()), true );
        Set<DistributionModuleGroup> dynamoDBBatchConfigurationModuleGroups = ModuleGroupsUtils.getModuleGroups(distribution, version, (String) null, ModuleGroupName.DYNAMODB_BATCH.get(this.getVersion()), true );
        
        // ... in Spark batch
        result.put(new NodeComponentTypeBean(ComponentType.SPARKBATCH, SparkBatchConstant.DYNAMODB_INPUT_COMPONENT), dynamoDBBatchNodeModuleGroups);
        result.put(new NodeComponentTypeBean(ComponentType.SPARKBATCH, SparkBatchConstant.DYNAMODB_OUTPUT_COMPONENT), dynamoDBBatchNodeModuleGroups);
        result.put(new NodeComponentTypeBean(ComponentType.SPARKBATCH, SparkBatchConstant.DYNAMODB_CONFIGURATION_COMPONENT), dynamoDBBatchConfigurationModuleGroups);
        
        Set<DistributionModuleGroup> dynamoDBStreamingNodeModuleGroups = ModuleGroupsUtils.getModuleGroups(distribution, version, "USE_EXISTING_CONNECTION == 'false'", ModuleGroupName.DYNAMODB_STREAMING.get(this.getVersion()), true );
        Set<DistributionModuleGroup> dynamoDBStreamingConfigurationModuleGroups = ModuleGroupsUtils.getModuleGroups(distribution, version, (String) null, ModuleGroupName.DYNAMODB_STREAMING.get(this.getVersion()), true );
        
        // ... in Spark streaming
        result.put(new NodeComponentTypeBean(ComponentType.SPARKSTREAMING, SparkStreamingConstant.DYNAMODB_INPUT_COMPONENT), dynamoDBStreamingNodeModuleGroups);
        result.put(new NodeComponentTypeBean(ComponentType.SPARKSTREAMING, SparkStreamingConstant.DYNAMODB_OUTPUT_COMPONENT), dynamoDBStreamingNodeModuleGroups);
        result.put(new NodeComponentTypeBean(ComponentType.SPARKSTREAMING, SparkStreamingConstant.DYNAMODB_CONFIGURATION_COMPONENT), dynamoDBStreamingConfigurationModuleGroups);   
        
        // Spark Streaming Flume nodes
        ComponentCondition flumeCondition = new SparkStreamingLinkedNodeCondition(distribution, version, SparkStreamingConstant.FLUME_SPARKCONFIGURATION_LINKEDPARAMETER).getCondition();
        
        result.put(new NodeComponentTypeBean(ComponentType.SPARKSTREAMING, SparkStreamingConstant.FLUME_INPUT_COMPONENT),
                ModuleGroupsUtils.getModuleGroups(distribution, version, flumeCondition, ModuleGroupName.FLUME.get(this.getVersion()), true));
        
        result.put(new NodeComponentTypeBean(ComponentType.SPARKSTREAMING, SparkStreamingConstant.FLUME_OUTPUT_COMPONENT),
                ModuleGroupsUtils.getModuleGroups(distribution, version, flumeCondition, ModuleGroupName.FLUME.get(this.getVersion()), true));

        
        // Spark Batch  GCS 
        result.put(new NodeComponentTypeBean(ComponentType.SPARKBATCH, SparkBatchConstant.GCS_CONFIG_COMPONENT), 
                    ModuleGroupsUtils.getModuleGroups(distribution, version, (String) null, ModuleGroupName.GCS.get(this.getVersion()), true));
        
       
        // GraphFrames - Spark Batch DQ matching
        result.put(new NodeComponentTypeBean(ComponentType.SPARKBATCH, SparkBatchConstant.MATCH_PREDICT_COMPONENT),
                ModuleGroupsUtils.getModuleGroups(distribution, version, (ComponentCondition) null,  ModuleGroupName.GRAPHFRAMES.get(this.getVersion()), true));     
        
        
        // Spark Streaming Kafka nodes
        
        ComponentCondition kafkaCondition = new SparkStreamingLinkedNodeCondition(distribution, version, SparkStreamingConstant.KAFKA_SPARKCONFIGURATION_LINKEDPARAMETER).getCondition();
        
        result.put(new NodeComponentTypeBean(ComponentType.SPARKSTREAMING, SparkStreamingConstant.KAFKA_INPUT_COMPONENT),
                ModuleGroupsUtils.getModuleGroups(distribution, version, kafkaCondition, ModuleGroupName.KAFKA.get(this.getVersion()), true));
        
        result.put(new NodeComponentTypeBean(ComponentType.SPARKSTREAMING, SparkStreamingConstant.KAFKA_AVRO_INPUT_COMPONENT),
                ModuleGroupsUtils.getModuleGroups(distribution, version, kafkaCondition, ModuleGroupName.KAFKA.get(this.getVersion()), true));
        
        result.put(new NodeComponentTypeBean(ComponentType.SPARKSTREAMING, SparkStreamingConstant.KAFKA_OUTPUT_COMPONENT),
                ModuleGroupsUtils.getModuleGroups(distribution, version, kafkaCondition, ModuleGroupName.KAFKA.get(this.getVersion()), true));

        
        // Spark Streaming Kinesis nodes
        Set<DistributionModuleGroup> kinesisModuleGroups =  ModuleGroupsUtils.getStreamingModuleGroups(distribution, version, (ComponentCondition) null, ModuleGroupName.KINESIS.get(this.getVersion()), true );
            
        result.put(new NodeComponentTypeBean(ComponentType.SPARKSTREAMING, SparkStreamingConstant.KINESIS_INPUT_COMPONENT), kinesisModuleGroups);
        result.put(new NodeComponentTypeBean(ComponentType.SPARKSTREAMING, SparkStreamingConstant.KINESIS_INPUT_AVRO_COMPONENT), kinesisModuleGroups);
        result.put(new NodeComponentTypeBean(ComponentType.SPARKSTREAMING, SparkStreamingConstant.KINESIS_OUTPUT_COMPONENT), kinesisModuleGroups);
        
        // Spark Batch Parquet nodes
        result.put(new NodeComponentTypeBean(ComponentType.SPARKBATCH, SparkBatchConstant.PARQUET_INPUT_COMPONENT),
                    ModuleGroupsUtils.getModuleGroups(distribution, version, (ComponentCondition) null, ModuleGroupName.PARQUET.get(this.getVersion()), true));
        
        result.put(new NodeComponentTypeBean(ComponentType.SPARKBATCH, SparkBatchConstant.PARQUET_OUTPUT_COMPONENT),
                    ModuleGroupsUtils.getModuleGroups(distribution, version, (ComponentCondition) null, ModuleGroupName.PARQUET.get(this.getVersion()), true));

        // Spark Streaming Parquet nodes
        result.put(new NodeComponentTypeBean(ComponentType.SPARKSTREAMING, SparkStreamingConstant.PARQUET_INPUT_COMPONENT),
                ModuleGroupsUtils.getModuleGroups(distribution, version, (ComponentCondition) null, ModuleGroupName.PARQUET.get(this.getVersion()), true));
        result.put(new NodeComponentTypeBean(ComponentType.SPARKSTREAMING, SparkStreamingConstant.PARQUET_OUTPUT_COMPONENT),
                ModuleGroupsUtils.getModuleGroups(distribution, version, (ComponentCondition) null, ModuleGroupName.PARQUET.get(this.getVersion()), true));
        result.put(new NodeComponentTypeBean(ComponentType.SPARKSTREAMING, SparkStreamingConstant.PARQUET_STREAM_INPUT_COMPONENT),
                ModuleGroupsUtils.getModuleGroups(distribution, version, (ComponentCondition) null, ModuleGroupName.PARQUET.get(this.getVersion()), true));
        
        // Redshift nodes ...
        Set<DistributionModuleGroup> redshiftBatchNodeModuleGroups = ModuleGroupsUtils.getModuleGroups(distribution, version, "USE_EXISTING_CONNECTION == 'false'", ModuleGroupName.REDSHIFT_BATCH.get(this.getVersion()), true );
        Set<DistributionModuleGroup> redshiftBatchConfigurationModuleGroups = ModuleGroupsUtils.getModuleGroups(distribution, version, (String) null, ModuleGroupName.REDSHIFT_BATCH.get(this.getVersion()), true );
        
        // ... in Spark batch
        result.put(new NodeComponentTypeBean(ComponentType.SPARKBATCH, SparkBatchConstant.REDSHIFT_INPUT_COMPONENT), redshiftBatchNodeModuleGroups);
        result.put(new NodeComponentTypeBean(ComponentType.SPARKBATCH, SparkBatchConstant.REDSHIFT_OUTPUT_COMPONENT), redshiftBatchNodeModuleGroups);
        result.put(new NodeComponentTypeBean(ComponentType.SPARKBATCH, SparkBatchConstant.REDSHIFT_CONFIGURATION_COMPONENT), redshiftBatchConfigurationModuleGroups);
        
        Set<DistributionModuleGroup> redshiftStreamingNodeModuleGroups = ModuleGroupsUtils.getModuleGroups(distribution, version, "USE_EXISTING_CONNECTION == 'false'", ModuleGroupName.REDSHIFT_STREAMING.get(this.getVersion()), true );
        Set<DistributionModuleGroup> redshiftStreamingConfigurationModuleGroups = ModuleGroupsUtils.getModuleGroups(distribution, version, (String) null, ModuleGroupName.REDSHIFT_STREAMING.get(this.getVersion()), true );
        
        // ... in Spark streaming
        result.put(new NodeComponentTypeBean(ComponentType.SPARKSTREAMING, SparkStreamingConstant.REDSHIFT_LOOKUP_INPUT_COMPONENT), redshiftStreamingNodeModuleGroups);
        result.put(new NodeComponentTypeBean(ComponentType.SPARKSTREAMING, SparkStreamingConstant.REDSHIFT_OUTPUT_COMPONENT), redshiftStreamingNodeModuleGroups);
        result.put(new NodeComponentTypeBean(ComponentType.SPARKSTREAMING, SparkStreamingConstant.REDSHIFT_CONFIGURATION_COMPONENT), redshiftStreamingConfigurationModuleGroups);           
        
        
        // Snowflake nodes ...
        Set<DistributionModuleGroup> snowFlakeBatchNodeModuleGroups = ModuleGroupsUtils.getModuleGroups(distribution, version, "USE_EXISTING_CONNECTION == 'false'", ModuleGroupName.SNOWFLAKE.get(this.getVersion()), true );
        Set<DistributionModuleGroup> snowFlakeBatchConfigurationModuleGroups = ModuleGroupsUtils.getModuleGroups(distribution, version, (String) null, ModuleGroupName.SNOWFLAKE.get(this.getVersion()), true );
        
        // ... in Spark batch
        result.put(new NodeComponentTypeBean(ComponentType.SPARKBATCH, SparkBatchConstant.SNOWFLAKE_INPUT_COMPONENT), snowFlakeBatchNodeModuleGroups);
        result.put(new NodeComponentTypeBean(ComponentType.SPARKBATCH, SparkBatchConstant.SNOWFLAKE_OUTPUT_COMPONENT), snowFlakeBatchNodeModuleGroups);
        result.put(new NodeComponentTypeBean(ComponentType.SPARKBATCH, SparkBatchConstant.SNOWFLAKE_CONFIGURATION_COMPONENT), snowFlakeBatchConfigurationModuleGroups);
        
        // WebHDFS
        HDFSLinkedNodeCondition hdfsLinkedNodeCondition = new HDFSLinkedNodeCondition(distribution, version);
       
        Set<DistributionModuleGroup> webHDFSNodeModuleGroups = ModuleGroupsUtils.getModuleGroups(distribution, version, hdfsLinkedNodeCondition.getWebHDFSCondition(), ModuleGroupName.WEBHDFS.get(this.getVersion()), true);
        
        for(String hdfsComponent : HDFSConstant.HDFS_COMPONENTS) {
            result.put(new NodeComponentTypeBean(ComponentType.HDFS, hdfsComponent), webHDFSNodeModuleGroups);
        }
 
        // Spark Batch tSQLRow nodes
        ComponentCondition hiveContextCondition = new SimpleComponentCondition(new BasicExpression(
                "SQL_CONTEXT", EqualityOperator.EQ, "HiveContext")); 
        
        result.put(new NodeComponentTypeBean(ComponentType.SPARKBATCH, SparkBatchConstant.SPARK_SQL_ROW_COMPONENT),
                ModuleGroupsUtils.getModuleGroups(distribution, version, hiveContextCondition, ModuleGroupName.HIVE.get(this.getVersion()), true));

        // Spark Streaming tSQLRow nodes
        result.put(new NodeComponentTypeBean(ComponentType.SPARKSTREAMING, SparkStreamingConstant.SPARK_SQL_ROW_COMPONENT),
                ModuleGroupsUtils.getModuleGroups(distribution, version, hiveContextCondition, ModuleGroupName.HIVE.get(this.getVersion()), true));
        
        // delta components in Spark batch
        Set<DistributionModuleGroup> deltaBatchConfigurationModuleGroups = ModuleGroupsUtils.getModuleGroups(distribution, version, (String) null, ModuleGroupName.DELTALAKE.get(this.getVersion()), true);
        
        result.put(new NodeComponentTypeBean(ComponentType.SPARKBATCH, SparkBatchConstant.DELTALAKE_INPUT_COMPONENT), deltaBatchConfigurationModuleGroups);
        result.put(new NodeComponentTypeBean(ComponentType.SPARKBATCH, SparkBatchConstant.DELTALAKE_OUTPUT_COMPONENT), deltaBatchConfigurationModuleGroups);
       
        return result;
    }
    
}
