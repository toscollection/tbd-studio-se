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
package org.talend.hadoop.distribution.cdh5x;


/**
 * DOC cmeng  class global comment. Detailled comment
 */
public enum CDH5xConstant {

    HDFS_MODULE_GROUP("HDFS-LIB_CDH5_{0}"), //$NON-NLS-1$
    HDFS_MODULE_GROUP_COMMON("HDFS-COMMON-LIB_CDH5_{0}"), //$NON-NLS-1$
    HDFS_MODULE_GROUP_SPARK1_6("HDFS-SPARK1_6-LIB_CDH5_{0}"), //$NON-NLS-1$
    HDFS_MODULE_GROUP_SPARK2_1("HDFS-SPARK2_1-LIB_CDH5_{0}"), //$NON-NLS-1$
    MAPREDUCE_MODULE_GROUP("MAPREDUCE-LIB_CDH5_{0}"), //$NON-NLS-1$
    HBASE_MODULE_GROUP("HBASE-LIB_CDH5_{0}"), //$NON-NLS-1$
    PIG_MODULE_GROUP("PIG-LIB_CDH5_{0}"), //$NON-NLS-1$
    HIVE_MODULE_GROUP("HIVE-LIB_CDH5_{0}"), //$NON-NLS-1$
    SQOOP_MODULE_GROUP("SQOOP-LIB_CDH5_{0}"), //$NON-NLS-1$
    SPARK_MODULE_GROUP("SPARK-LIB_CDH5_{0}"), //$NON-NLS-1$
    SPARK2_MODULE_GROUP("SPARK-LIB_SPARK2_CDH5_{0}"), //$NON-NLS-1$
    HIVE_HBASE_MODULE_GROUP("HIVE-HBASE-LIB_CDH5_{0}"), //$NON-NLS-1$
    MAPREDUCE_PARQUET_MODULE_GROUP("MAPREDUCE-PARQUET-LIB_CDH5_{0}"), //$NON-NLS-1$
    MAPREDUCE_PARQUET_MRREQUIRED_MODULE_GROUP("MAPREDUCE-PARQUET-LIB-MRREQUIRED_CDH5_{0}"), //$NON-NLS-1$
    PIG_AVRO_MODULE_GROUP("PIG-AVRO-LIB_CDH5_{0}"), //$NON-NLS-1$
    PIG_HBASE_MODULE_GROUP("PIG-HBASE-LIB_CDH5_{0}"), //$NON-NLS-1$
    PIG_HCATALOG_MODULE_GROUP("PIG-HCATALOG-LIB_CDH5_{0}"), //$NON-NLS-1$
    PIG_PARQUET_MODULE_GROUP("PIG-PARQUET-LIB_CDH5_{0}"), //$NON-NLS-1$
    PIG_RCFILE_MODULE_GROUP("PIG-RCFILE-LIB_CDH5_{0}"), //$NON-NLS-1$
    PIG_SEQUENCEFILE_MODULE_GROUP("PIG-SEQUENCEFILE-LIB_CDH5_{0}"), //$NON-NLS-1$
    SPARK_MRREQUIRED_MODULE_GROUP("SPARK-LIB-MRREQUIRED_CDH5_{0}"), //$NON-NLS-1$
    SPARK_DYNAMODB_MRREQUIRED_MODULE_GROUP("SPARK-DYNAMODB-LIB-MRREQUIRED_CDH5_{0}"), //$NON-NLS-1$
    SPARK_PARQUET_MRREQUIRED_MODULE_GROUP("SPARK-PARQUET-LIB-MRREQUIRED_CDH5_{0}"), //$NON-NLS-1$
    SPARK_KINESIS_MRREQUIRED_MODULE_GROUP("SPARK-KINESIS-LIB-MRREQUIRED_CDH5_{0}"), //$NON-NLS-1$
    SPARK2_KINESIS_MRREQUIRED_MODULE_GROUP("SPARK2-KINESIS-LIB-MRREQUIRED_CDH5_{0}"), //$NON-NLS-1$
    SPARK_KAFKA_ASSEMBLY_MRREQUIRED_MODULE_GROUP("SPARK-KAFKA-ASSEMBLY-LIB-MRREQUIRED_CDH5_{0}"), //$NON-NLS-1$
    SPARK2_KAFKA_ASSEMBLY_MRREQUIRED_MODULE_GROUP("SPARK2-KAFKA-ASSEMBLY-LIB-MRREQUIRED_CDH5_{0}"), //$NON-NLS-1$
    SPARK_KAFKA_AVRO_MRREQUIRED_MODULE_GROUP("SPARK-KAFKA-AVRO-LIB-MRREQUIRED_CDH5_{0}"), //$NON-NLS-1$
    SPARK_KAFKA_CLIENT_MRREQUIRED_MODULE_GROUP("SPARK-KAFKA-CLIENT-LIB-MRREQUIRED_CDH5_{0}"), //$NON-NLS-1$
    SPARK2_KAFKA_CLIENT_MRREQUIRED_MODULE_GROUP("SPARK2-KAFKA-CLIENT-LIB-MRREQUIRED_CDH5_{0}"), //$NON-NLS-1$
    SPARK_S3_MRREQUIRED_MODULE_GROUP("SPARK-S3-LIB-MRREQUIRED_CDH5_{0}"), //$NON-NLS-1$
    PIG_S3_MODULE_GROUP("PIG-S3-LIB_CDH5_{0}"), //$NON-NLS-1$
    GRAPHFRAMES_MRREQUIRED_MODULE_GROUP("SPARK-GRAPHFRAMES-LIB-MRREQUIRED_CDH5_{0}"), //$NON-NLS-1$
    SPARK_HIVE_MRREQUIRED_MODULE_GROUP("SPARK-HIVE-LIB-MRREQUIRED_CDH5_{0}"), //$NON-NLS-1$
    TALEND_CLOUDERA_CDH_5_X_NAVIGATOR("TALEND_CLOUDERA_CDH5_NAVIGATOR_{0}"), //$NON-NLS-1$
    SPARK_FLUME_MRREQUIRED_MODULE_GROUP("SPARK-FLUME-LIB-MRREQUIRED_CDH5_{0}"), //$NON-NLS-1$
    SQOOP_PARQUET_MODULE_GROUP("SQOOP-PARQUET-LIB_CDH5_{0}"); //$NON-NLS-1$

    private String prefix;

    private CDH5xConstant(String prefix) {
        this.prefix = prefix;
    }

    public String getPrefix() {
        return this.prefix;
    }

    public String getModuleName(String... ids) {
        String moduleName = this.prefix;
        for (int i = 0; i < ids.length; ++i) {
            String id = ids[i];
            String key = "\\{" + i + "\\}"; //$NON-NLS-1$ //$NON-NLS-2$
            moduleName = moduleName.replaceAll(key, id);
        }
        return moduleName;
    }
}
