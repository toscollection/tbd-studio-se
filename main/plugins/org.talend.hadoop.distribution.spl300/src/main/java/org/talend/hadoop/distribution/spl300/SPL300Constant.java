// ============================================================================
//
// Copyright (C) 2006-2020 Talend Inc. - www.talend.com
//
// This source code is available under agreement available at
// %InstallDIR%\features\org.talend.rcp.branding.%PRODUCTNAME%\%PRODUCTNAME%license.txt
//
// You should have received a copy of the agreement
// along with this program; if not, write to Talend SA
// 9 rue Pages 92150 Suresnes, France
//
// ============================================================================
package org.talend.hadoop.distribution.spl300;


public enum SPL300Constant {
    SPARK_BATCH_MODULE_GROUP("SPARK_BATCH_MODULE_GROUP_SPL300"), //$NON-NLS-1$
    SPARK_BATCH_PARQUET_MODULE_GROUP("SPARK_BATCH_PARQUET_MODULE_GROUP_SPL300"),
    SPARK_BATCH_S3_MODULE_GROUP("SPARK_BATCH_S3_MODULE_GROUP_SPL300"),
    SPARK_BATCH_AZURE_MODULE_GROUP("SPARK_BATCH_AZURE_MODULE_GROUP_SPL300"),
    SPARK_BATCH_GS_MODULE_GROUP("SPARK_BATCH_GS_MODULE_GROUP_SPL300"),
    
    SPARK_STREAMING_LIBS("SPARK_STREAMING_LIBS_SPL300"), //$NON-NLS-1$
    SPARK_STREAMING_KINESIS_LIBS("SPARK_STREAMING_KINESIS_LIBS_SPL300"); //$NON-NLS-1$

    private String mModuleName;

    SPL300Constant(String moduleName) {
        this.mModuleName = moduleName;
    }

    public String getModuleName() {
        return this.mModuleName;
    }
}
