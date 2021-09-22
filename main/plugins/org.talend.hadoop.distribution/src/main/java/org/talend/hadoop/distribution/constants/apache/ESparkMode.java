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
package org.talend.hadoop.distribution.constants.apache;

import org.talend.hadoop.distribution.i18n.Messages;
public enum ESparkMode {

	YARN_CLUSTER(Messages.getString("ESparkMode.YARN_CLUSTER"), "YARN_CLUSTER"),
	KUBERNETES(Messages.getString("ESparkMode.KUBERNETES"), "SPARK_K8S"),
    SPARK_LOCAL(Messages.getString("ESparkMode.SPARK_LOCAL"), "SPARK_LOCAL");

    private String runModeLabel;

    private String runModeValue;

    ESparkMode(String runModeLabel, String runModeValue) {
        this.runModeLabel = runModeLabel;
        this.runModeValue = runModeValue;
    }

    public String getLabel() {
        return runModeLabel;
    }

    public String getValue() {
        return runModeValue;
    }
}
