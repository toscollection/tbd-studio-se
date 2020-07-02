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
    SPARK_BATCH_MODULE_GROUP("SPARK_BATCH_MODULE_GROUP"), //$NON-NLS-1$
    SPARK_STREAMING_MODULE_GROUP("SPARK_STREAMING_MODULE_GROUP"), //$NON-NLS-1$
    SPARK_STREAMING_KINESIS_MODULE_GROUP("SPARK_STREAMING_KINESIS_MODULE_GROUP"); //$NON-NLS-1$

    private String mModuleName;

    SPL300Constant(String moduleName) {
        this.mModuleName = moduleName;
    }

    public String getModuleName() {
        return this.mModuleName;
    }
}
