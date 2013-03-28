// ============================================================================
//
// Copyright (C) 2006-2013 Talend Inc. - www.talend.com
//
// This source code is available under agreement available at
// %InstallDIR%\features\org.talend.rcp.branding.%PRODUCTNAME%\%PRODUCTNAME%license.txt
//
// You should have received a copy of the agreement
// along with this program; if not, write to Talend SA
// 9 rue Pages 92150 Suresnes, France
//
// ============================================================================

package org.talend.designer.hdfsbrowse.util;

/**
 * DOC ycbai class global comment. Detailled comment
 */
public enum EHDFSRepositoryToComponent {

    DISTRIBUTION("DISTRIBUTION", "DISTRIBUTION"), //$NON-NLS-1$ //$NON-NLS-2$

    DB_VERSION("DB_VERSION", "DB_VERSION"), //$NON-NLS-1$ //$NON-NLS-2$

    HADOOP_CUSTOM_JARS("HADOOP_CUSTOM_JARS", "HADOOP_CUSTOM_JARS"), //$NON-NLS-1$ //$NON-NLS-2$

    AUTHENTICATION_MODE("AUTHENTICATION_MODE", "AUTHENTICATION_MODE"), //$NON-NLS-1$ //$NON-NLS-2$

    FS_DEFAULT_NAME("FS_DEFAULT_NAME", "FS_DEFAULT_NAME"), //$NON-NLS-1$ //$NON-NLS-2$

    USE_KRB("USE_KRB", "USE_KRB"), //$NON-NLS-1$ //$NON-NLS-2$

    NAMENODE_PRINCIPAL("NAMENODE_PRINCIPAL", "NAMENODE_PRINCIPAL"), //$NON-NLS-1$ //$NON-NLS-2$

    USERNAME("USERNAME", "USERNAME"), //$NON-NLS-1$ //$NON-NLS-2$

    GROUP("GROUP", "GROUP"), //$NON-NLS-1$ //$NON-NLS-2$

    FILENAME("FILENAME", "FILENAME"), //$NON-NLS-1$ //$NON-NLS-2$

    ROWSEPARATOR("ROWSEPARATOR", "ROWSEPARATOR"), //$NON-NLS-1$ //$NON-NLS-2$

    FIELDSEPARATOR("FIELDSEPARATOR", "FIELDSEPARATOR"), //$NON-NLS-1$ //$NON-NLS-2$

    LOCAL("LOCAL", "LOCAL"), //$NON-NLS-1$ //$NON-NLS-2$

    MAPREDUCE("MAPREDUCE", "MAPREDUCE"), //$NON-NLS-1$ //$NON-NLS-2$

    PIG_VERSION("PIG_VERSION", "PIG_VERSION"), //$NON-NLS-1$ //$NON-NLS-2PIG_VERSION

    MAPRED_JOB_TRACKER("MAPRED_JOB_TRACKER", "MAPRED_JOB_TRACKER"), //$NON-NLS-1$ //$NON-NLS-2PIG_VERSION

    FIELD_SEPARATOR_CHAR("FIELD_SEPARATOR_CHAR", "FIELD_SEPARATOR_CHAR"), //$NON-NLS-1$ //$NON-NLS-2PIG_VERSION

    LOAD("LOAD", "LOAD"), //$NON-NLS-1$ //$NON-NLS-2PIG_VERSION

    INPUT_FILENAME("INPUT_FILENAME", "INPUT_FILENAME"), //$NON-NLS-1$ //$NON-NLS-2PIG_VERSION

    ;

    public final String repositoryValue;

    public final String parameterName;

    EHDFSRepositoryToComponent(String repositoryValue, String parameterName) {
        this.repositoryValue = repositoryValue;
        this.parameterName = parameterName;
    }

    public String getRepositoryValue() {
        return repositoryValue;
    }

    public String getParameterName() {
        return parameterName;
    }

}
