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
package org.talend.hadoop.distribution.dbr551;

public enum Dbr551Constant {

    BIGDATA_LAUNCHER_LIB_DBR551("BIGDATA-LAUNCHER-LIB-DBR551"),  //$NON-NLS-1$
    HIVEONSPARK_LIB_MRREQUIRED_DBR551("HIVEONSPARK-LIB-MRREQUIRED-DBR551"),  //$NON-NLS-1$
    SPARK_AZURE_LIB_MRREQUIRED_DBR551("SPARK-AZURE-LIB-MRREQUIRED-DBR551"),  //$NON-NLS-1$
    SPARK_LIB_MRREQUIRED_DBR551("SPARK-LIB-MRREQUIRED-DBR551"),  //$NON-NLS-1$
    SPARK_STREAMING_LIB_KINESIS_DBR551("SPARK-STREAMING-LIB-KINESIS-DBR551"),  //$NON-NLS-1$
    SPARK_STREAMING_LIB_MRREQUIRED_DBR551("SPARK-STREAMING-LIB-MRREQUIRED-DBR551") //$NON-NLS-1$
    ;

    private String mModuleName;

    Dbr551Constant(String moduleName) {
        this.mModuleName = moduleName;
    }

    public String getModuleName() {
        return this.mModuleName;
    }
}


