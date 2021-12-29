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
package org.talend.hadoop.distribution.synapse;

import java.util.Collections;
import java.util.HashMap;
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
import org.talend.hadoop.distribution.component.SparkStreamingComponent;
import org.talend.hadoop.distribution.condition.ComponentCondition;
import org.talend.hadoop.distribution.constants.synapse.ISynapseDistribution;
import org.talend.hadoop.distribution.kafka.SparkStreamingKafkaVersion;

public class SynapseDistribution extends AbstractDistribution implements ISynapseDistribution, SparkBatchComponent,
        SparkStreamingComponent {

	public static final String VERSION = "SYNAPSE";

    public static final String VERSION_DISPLAY = "Azure Synapse Runtime for Apache Spark 3.0";
    
    public final static ESparkVersion SPARK_VERSION = ESparkVersion.SPARK_3_0;

    private final static String YARN_APPLICATION_CLASSPATH = "$HADOOP_CONF_DIR,$HADOOP_COMMON_HOME/*,$HADOOP_COMMON_HOME/lib/*,$HADOOP_HDFS_HOME/*,$HADOOP_HDFS_HOME/lib/*,$HADOOP_MAPRED_HOME/*,$HADOOP_MAPRED_HOME/lib/*,$YARN_HOME/*,$YARN_HOME/lib/*,$HADOOP_YARN_HOME/*,$HADOOP_YARN_HOME/lib/*,$HADOOP_COMMON_HOME/share/hadoop/common/*,$HADOOP_COMMON_HOME/share/hadoop/common/lib/*,$HADOOP_HDFS_HOME/share/hadoop/hdfs/*,$HADOOP_HDFS_HOME/share/hadoop/hdfs/lib/*,$HADOOP_YARN_HOME/share/hadoop/yarn/*,$HADOOP_YARN_HOME/share/hadoop/yarn/lib/*" ; //$NON-NLS-1$

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
    public String getVersion() {
        return VERSION;
    }

    @Override
    public EHadoopVersion getHadoopVersion() {
        return EHadoopVersion.HADOOP_3;
    }

    @Override
    public String getDistributionName() {
        return DISTRIBUTION_DISPLAY_NAME;
    }

    @Override
    public String getVersionName(ComponentType componentType) {
        return VERSION_DISPLAY;
    }

    @Override
    public Set<DistributionModuleGroup> getModuleGroups(ComponentType componentType) {
        return moduleGroups.get(componentType);
    }

    @Override
    public Set<DistributionModuleGroup> getModuleGroups(ComponentType componentType, String componentName) {
        return nodeModuleGroups.get(new NodeComponentTypeBean(componentType, componentName));
    }

    @Override
    public Set<ESparkVersion> getSparkVersions() {
    	return Collections.singleton(ESparkVersion.SPARK_3_0);
    }

    @Override
    public boolean doSupportSparkStandaloneMode() {
        return true;
    }

    @Override
    public boolean doSupportS3() {
        return true;
    }

    @Override
    public boolean doSupportSparkYarnClientMode() {
        return false;
    }

    @Override
    public boolean doSupportAzureDataLakeStorage() {
        return true;
    }

    @Override
    public boolean doSupportKerberos() {
        return false;
    }

    @Override
    public boolean doSupportS3V4() {
        return true;
    }

    @Override
    public boolean doSupportAzureDataLakeStorageGen2() {
        return true;
    }

    @Override
    public boolean doSupportDynamicMemoryAllocation() {
        return true;
    }

    @Override
    public boolean doSupportCheckpointing() {
        return true;
    }

    @Override
    public boolean doSupportCrossPlatformSubmission() {
        return true;
    }

    @Override
    public boolean doSupportBackpressure() {
        return true;
    }
    
    @Override
    public boolean doSupportImpersonation() {
        return true;
    }

    @Override
    public boolean doSupportUseDatanodeHostname() {
        return true;
    }

    @Override
    public boolean isCloudDistribution() {
        return true;
    }

    @Override
    public boolean isSynapseDistribution() {
        return true;
    }

    @Override
    public boolean useCloudLauncher() {
        return true;
    }

	@Override
	public boolean doRequireMetastoreVersionOverride() {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public String getHiveMetastoreVersionForSpark() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String getHiveMetastoreJars() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public boolean doSupportSparkYarnClusterMode() {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean isExecutedThroughSparkJobServer() {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean isExecutedThroughLivy() {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean isExecutedThroughKnox() {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public String generateSparkJarsPaths(List<String> commandLineJarsPaths) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String generateSparkJarsPaths(List<String> commandLineJarsPaths, boolean isLightWeight) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public boolean doImportDynamoDBDependencies() {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean doImportSparkHiveContextDependencies() {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean useS3AProperties() {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean doSupportAssumeRole() {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public String getLightWeightClasspath() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public boolean isExecutedThroughWebHCat() {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean doSupportClouderaNavigator() {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public String getYarnApplicationClasspath() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public boolean doSupportCustomMRApplicationCP() {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public String getCustomMRApplicationCP() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public boolean doSupportAtlas() {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public int getClouderaNavigatorAPIVersion() {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public boolean doSupportBasicAtlasAuthentification() {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean doSupportAvroDeflateProperties() {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean useOldAWSAPI() {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public String getParquetPrefixPackageName() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String getWinUtilsName() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public boolean isHadoop3() {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean isHadoop2() {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean isHadoop1() {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean doSupportMapRTicket() {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean doSupportMaprTicketV52API() {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean doSupportGroup() {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public ComponentCondition getDisplayCondition(ComponentType componentType) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public boolean doSupportOldImportMode() {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public String getDefaultConfig(String... keys) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public boolean doSupportHDFSEncryption() {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean isGoogleDataprocDistribution() {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean isAltusDistribution() {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean isDatabricksDistribution() {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean isCDPDistribution() {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean isQuboleDistribution() {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean doSupportOozie() {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean doSupportCreateServiceConnection() {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public List<String> getNecessaryServiceName() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public boolean isHortonworksDistribution() {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean isActivated() {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean isDynamicDistribution() {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean doSupportWebHDFS() {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public SparkStreamingKafkaVersion getSparkStreamingKafkaVersion(ESparkVersion sparkVersion) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public boolean doSupportKerberizedKafka() {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean doRequireElasticsearchSparkPatch() {
		// TODO Auto-generated method stub
		return false;
	}

}
