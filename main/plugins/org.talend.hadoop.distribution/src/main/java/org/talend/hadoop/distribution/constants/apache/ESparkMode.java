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

import java.util.HashMap;
import java.util.Map;

import org.talend.hadoop.distribution.i18n.Messages;
public enum ESparkMode {

    CLUSTER(Messages.getString("ESparkMode.CLUSTER"),
            "CLUSTER",
            "(DISTRIB[DISTRIBUTION, SPARK_VERSION].doSupportSparkStandaloneMode[])"),
    YARN_CLIENT(Messages.getString("ESparkMode.YARN_CLIENT"),
            "YARN_CLIENT",
            "(DISTRIB[DISTRIBUTION, SPARK_VERSION].doSupportSparkYarnClientMode[])"),
    YARN_CLUSTER(Messages.getString("ESparkMode.YARN_CLUSTER"),
            "YARN_CLUSTER",
            "(DISTRIB[DISTRIBUTION, SPARK_VERSION].doSupportSparkYarnClusterMode[])"),
    KUBERNETES(Messages.getString("ESparkMode.KUBERNETES"),
            "SPARK_K8S",
            "(DISTRIB[DISTRIBUTION, SPARK_VERSION].doSupportSparkYarnK8SMode[])"),
    CDE(Messages.getString("ESparkMode.CDE"), "CDE", "(DISTRIB[DISTRIBUTION, SPARK_VERSION].doSupportUniversalCDEMode[])"),
    DATAPROC(Messages.getString("ESparkMode.DATAPROC"),
            "DATAPROC",
            "(DISTRIB[DISTRIBUTION, SPARK_VERSION].doSupportUniversalDataprocMode[])"),
    DATABRICKS(Messages.getString("ESparkMode.DATABRICKS"),
            "DATABRICKS",
            "(DISTRIB[DISTRIBUTION, SPARK_VERSION].doSupportUniversalDBRMode[])"),
    STANDALONE(Messages.getString("ESparkMode.STANDALONE"),
            "STANDALONE",
            "(DISTRIB[DISTRIBUTION, SPARK_VERSION].doSupportUniversalStandaloneMode[])"),
    SPARK_LOCAL(Messages.getString("ESparkMode.SPARK_LOCAL"),
            "SPARK_LOCAL",
            "(DISTRIB[DISTRIBUTION, SPARK_VERSION].doSupportUniversalLocalMode[])");
	// we want spark local by default if possible so please let it last in this list

    private String runModeLabel;

    private String runModeValue;

    private String displayCondition;

    private static final Map<String, ESparkMode> sparkModeByLabel = new HashMap<String, ESparkMode>();

    static {
        for (ESparkMode m : ESparkMode.values()) {
            sparkModeByLabel.put(m.getLabel(), m);
        }
    }
    
    ESparkMode(String runModeLabel, String runModeValue, String displayCondition) {
        this.runModeLabel = runModeLabel;
        this.runModeValue = runModeValue;
        this.displayCondition = displayCondition;
    }

    public String getLabel() {
        return runModeLabel;
    }

    public String getValue() {
        return runModeValue;
    }

    public String getDisplayCondition() {
        return displayCondition;
    }

    public static ESparkMode getByLabel(String label) {
        return sparkModeByLabel.get(label);
    }

}
