// ============================================================================
//
// Copyright (C) 2006-2019 Talend Inc. - www.talend.com
//
// This source code is available under agreement available at
// %InstallDIR%\features\org.talend.rcp.branding.%PRODUCTNAME%\%PRODUCTNAME%license.txt
//
// You should have received a copy of the agreement
// along with this program; if not, write to Talend SA
// 9 rue Pages 92150 Suresnes, France
//
// ============================================================================

package org.talend.hadoop.distribution.synapse;

import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.talend.hadoop.distribution.AbstractDistribution;
import org.talend.hadoop.distribution.ComponentType;
import org.talend.hadoop.distribution.DistributionModuleGroup;
import org.talend.hadoop.distribution.EHadoopVersion;
import org.talend.hadoop.distribution.ESparkVersion;
import org.talend.hadoop.distribution.NodeComponentTypeBean;
import org.talend.hadoop.distribution.component.SparkBatchComponent;
import org.talend.hadoop.distribution.condition.ComponentCondition;
import org.talend.hadoop.distribution.constants.ModuleGroupName;
import org.talend.hadoop.distribution.constants.synapse.ISynapseDistribution;
import org.talend.hadoop.distribution.spark.SparkClassPathUtils;

@SuppressWarnings("nls")
public class SynapseDistribution extends AbstractDistribution implements ISynapseDistribution, SparkBatchComponent {

    public static final String VERSION = "SYNAPSE";

    public static final String VERSION_DISPLAY = "Azure Synapse Runtime";
    
    public final static ESparkVersion SPARK_VERSION = ESparkVersion.SPARK_3_1;

    private final static String YARN_APPLICATION_CLASSPATH = "$HADOOP_CONF_DIR,/usr/hdp/current/hadoop-client/*,/usr/hdp/current/hadoop-client/lib/*,/usr/hdp/current/hadoop-hdfs-client/*,/usr/hdp/current/hadoop-hdfs-client/lib/*,/usr/hdp/current/hadoop-yarn-client/*,/usr/hdp/current/hadoop-yarn-client/lib/*"; //$NON-NLS-1$

	public final static String DEFAULT_LIB_ROOT = "/usr/lib";
	
	protected Map<ComponentType, Set<DistributionModuleGroup>> moduleGroups;

	protected Map<NodeComponentTypeBean, Set<DistributionModuleGroup>> nodeModuleGroups;

	protected Map<ComponentType, ComponentCondition> displayConditions;

	protected Map<ComponentType, String> customVersionDisplayNames;

	public SynapseDistribution() {
		displayConditions = buildDisplayConditions();
        customVersionDisplayNames = buildCustomVersionDisplayNames();
        moduleGroups = buildModuleGroups();
        nodeModuleGroups = buildNodeModuleGroups(getDistribution(), getVersion());        
	}

	protected Map<ComponentType, ComponentCondition> buildDisplayConditions() {
		return new HashMap<>();
	}

	protected Map<ComponentType, String> buildCustomVersionDisplayNames() {
        return new HashMap<>();
    }
	
	@Override
	public String getDistribution() {
		return DISTRIBUTION_NAME;
	}

	@Override
	public String getDistributionName() {
		return DISTRIBUTION_DISPLAY_NAME;
	}

	@Override
	public String getVersion() {
		return VERSION;
	}
	
	@Override
	public boolean isSynapseDistribution() {
		return true;
	}

	@Override
	public String getVersionName(ComponentType componentType) {
		return VERSION_DISPLAY;
	}

	@Override
	public EHadoopVersion getHadoopVersion() {
		return EHadoopVersion.HADOOP_3;
	}

	@Override
	public boolean doSupportKerberos() {
		return false;
	}

	@Override
	public Set<DistributionModuleGroup> getModuleGroups(
			ComponentType componentType) {
		return moduleGroups.get(componentType);
	}

	@Override
	public Set<DistributionModuleGroup> getModuleGroups(
			ComponentType componentType, String componentName) {
		return nodeModuleGroups.get(new NodeComponentTypeBean(componentType,
				componentName));
	}

	@Override
	public boolean doSupportUseDatanodeHostname() {
		return true;
	}
	
	@Override
	public boolean doSupportImpersonation() {
		return false;
	}
	
	@Override
	public boolean doSupportCrossPlatformSubmission() {
		return false;
	}
	
	@Override
	public String getYarnApplicationClasspath() {
		return YARN_APPLICATION_CLASSPATH;
	}
	
	@Override
	public String generateSparkJarsPaths(List<String> commandLineJarsPaths, boolean isLightWeight) {
        return isLightWeight ? "" : generateSparkJarsPaths(commandLineJarsPaths);
    }

	@Override
	public String generateSparkJarsPaths(List<String> commandLineJarsPaths) {
        return SparkClassPathUtils.generateSparkJarsPaths(commandLineJarsPaths, ModuleGroupName.SPARK_BATCH.get(this.getVersion()));
    }

	
	@Override
    public Set<ESparkVersion> getSparkVersions() {
		Set<ESparkVersion> version = new HashSet<>();
        version.add(SynapseDistribution.SPARK_VERSION);
        return version;
    }

    @Override
    public boolean isExecutedThroughSparkJobServer() {
        return false;
    }

	@Override
	public boolean doSupportOldImportMode() {
		return false;
	}
	
    @Override
    public boolean doSupportSparkStandaloneMode() {
        return false;
    }

    @Override
    public boolean doSupportSparkYarnClientMode() {
        return false;
    }
    
    @Override
    public boolean doSupportSparkYarnClusterMode() {
    	return true;
    };
    
    @Override
    public boolean doSupportDynamicMemoryAllocation() {
        return true;
    }

    public boolean doSupportCheckpointing() {
        return true;
    }

    public boolean doSupportBackpressure() {
        return true;
    }

    @Override
    public short orderingWeight() {
        return 10;
    }

    @Override
    public boolean doSupportLightWeight() {
        return false;
    }
    
    @Override
    public boolean isCloudDistribution() {
        return true;
    }
   
    @Override
    public boolean doSupportAzureBlobStorage() {
        return true;
    }
    
    @Override
    public boolean doSupportAzureDataLakeStorageGen2() {
        return true;
    }

}
