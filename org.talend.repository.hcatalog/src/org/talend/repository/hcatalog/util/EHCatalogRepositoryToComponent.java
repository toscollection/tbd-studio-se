// ============================================================================
//
// Copyright (C) 2006-2012 Talend Inc. - www.talend.com
//
// This source code is available under agreement available at
// %InstallDIR%\features\org.talend.rcp.branding.%PRODUCTNAME%\%PRODUCTNAME%license.txt
//
// You should have received a copy of the agreement
// along with this program; if not, write to Talend SA
// 9 rue Pages 92150 Suresnes, France
//
// ============================================================================

package org.talend.repository.hcatalog.util;

/**
 * DOC ycbai class global comment. Detailled comment
 */
public enum EHCatalogRepositoryToComponent {

    DISTRIBUTION("DISTRIBUTION", "DISTRIBUTION"), //$NON-NLS-1$ //$NON-NLS-2$

    HCAT_VERSION("HCAT_VERSION", "HCAT_VERSION"), //$NON-NLS-1$ //$NON-NLS-2$

    TEMPLETON_HOST("TEMPLETON_HOST", "TEMPLETON_HOST"), //$NON-NLS-1$ //$NON-NLS-2$

    TEMPLETON_PORT("TEMPLETON_PORT", "TEMPLETON_PORT"), //$NON-NLS-1$ //$NON-NLS-2$

    USERNAME("USERNAME", "USERNAME"), //$NON-NLS-1$ //$NON-NLS-2$

    DATABASE_NAME("DATABASE_NAME", "DATABASE_NAME"), //$NON-NLS-1$ //$NON-NLS-2$

    TABLE_NAME("TABLE_NAME", "TABLE_NAME"), //$NON-NLS-1$ //$NON-NLS-2$

    PARTITION_NAME("PARTITION_NAME", "PARTITION_NAME"), //$NON-NLS-1$ //$NON-NLS-2$

    USE_KRB("USE_KRB", "USE_KRB"), //$NON-NLS-1$ //$NON-NLS-2$

    KRB_PRINC("KRB_PRINC", "KRB_PRINC"), //$NON-NLS-1$ //$NON-NLS-2$

    KRB_REALM("KRB_REALM", "KRB_REALM"), //$NON-NLS-1$ //$NON-NLS-2$

    NAMENODE_PRINCIPAL("NAMENODE_PRINCIPAL", "NAMENODE_PRINCIPAL"), //$NON-NLS-1$ //$NON-NLS-2$

    ;

    public final String repositoryValue;

    public final String parameterName;

    EHCatalogRepositoryToComponent(String repositoryValue, String parameterName) {
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
