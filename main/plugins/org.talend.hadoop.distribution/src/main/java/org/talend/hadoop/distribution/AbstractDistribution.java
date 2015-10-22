// ============================================================================
//
// Copyright (C) 2006-2015 Talend Inc. - www.talend.com
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

import java.util.HashSet;
import java.util.Set;

import org.talend.hadoop.distribution.condition.ComponentCondition;

/**
 * Base class that describes a Distribution.
 *
 */
public abstract class AbstractDistribution {

    public abstract EHadoopVersion getHadoopVersion();

    public boolean isHadoop1() {
        return getHadoopVersion() == EHadoopVersion.HADOOP_1;
    }

    public boolean isHadoop2() {
        return getHadoopVersion() == EHadoopVersion.HADOOP_2;
    }

    public abstract boolean doSupportKerberos();

    public boolean doSupportGroup() {
        return false;
    }

    public boolean isExecutedThroughWebHCat() {
        return false;
    }

    public boolean doSupportClouderaNavigator() {
        return false;
    }

    public String getYarnApplicationClasspath() {
        // Not used in Hadoop 1
        return ""; //$NON-NLS-1$
    }

    public boolean doSupportHive1Standalone() {
        return true;
    }

    public boolean doSupportMapRDB() {
        return false;
    }

    // Only used if SparkComponent is implemented
    public ESparkVersion getSparkVersion() {
        return ESparkVersion.SPARK_1_3;
    }

    // Only used if SparkComponent is implemented
    public boolean isSpark14() {
        return getSparkVersion() == ESparkVersion.SPARK_1_4;
    }

    public boolean doSupportTezForPig() {
        return false;
    }

    public ComponentCondition getDisplayCondition(ComponentType componentType) {
        return null;
    }

    public Set<DistributionModuleGroup> getModuleGroups(ComponentType componentType, String componentName) {
        return new HashSet<>();
    }
}
