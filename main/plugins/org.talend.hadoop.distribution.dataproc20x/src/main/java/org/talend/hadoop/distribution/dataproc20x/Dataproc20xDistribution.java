// ============================================================================
//
// Copyright (C) 2006-2022 Talend Inc. - www.talend.com
//
// This source code is available under agreement available at
// %InstallDIR%\features\org.talend.rcp.branding.%PRODUCTNAME%\%PRODUCTNAME%license.txt
//
// You should have received a copy of the agreement
// along with this program; if not, write to Talend SA
// 9 rue Pages 92150 Suresnes, France
//
// ============================================================================

package org.talend.hadoop.distribution.dataproc20x;

import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.talend.hadoop.distribution.ComponentType;
import org.talend.hadoop.distribution.DistributionModuleGroup;
import org.talend.hadoop.distribution.EHadoopVersion;
import org.talend.hadoop.distribution.ESparkVersion;
import org.talend.hadoop.distribution.NodeComponentTypeBean;
import org.talend.hadoop.distribution.component.HDFSComponent;
import org.talend.hadoop.distribution.component.HiveComponent;
import org.talend.hadoop.distribution.component.HiveOnSparkComponent;
import org.talend.hadoop.distribution.component.SparkBatchComponent;
import org.talend.hadoop.distribution.component.SparkStreamingComponent;
import org.talend.hadoop.distribution.condition.ComponentCondition;
import org.talend.hadoop.distribution.constants.ModuleGroupName;
import org.talend.hadoop.distribution.constants.dataproc.IGoogleDataprocDistribution;
import org.talend.hadoop.distribution.spark.SparkClassPathUtils;
import org.talend.hadoop.distribution.dtp.DTPDistribution;
import org.talend.hadoop.distribution.kafka.SparkStreamingKafkaVersion;

@SuppressWarnings("nls")
public class Dataproc20xDistribution extends DTPDistribution implements IGoogleDataprocDistribution, SparkBatchComponent, SparkStreamingComponent, 
			HiveComponent, HiveOnSparkComponent {
	
    public static final String VERSION = "DATAPROC_2_0_x";

    public static final String VERSION_DISPLAY = "Google Dataproc";
    
    public final static ESparkVersion SPARK_VERSION = ESparkVersion.SPARK_3_1;

	protected Map<ComponentType, Set<DistributionModuleGroup>> moduleGroups;

	protected Map<NodeComponentTypeBean, Set<DistributionModuleGroup>> nodeModuleGroups;

	protected Map<ComponentType, ComponentCondition> displayConditions;

	protected Map<ComponentType, String> customVersionDisplayNames;

	public Dataproc20xDistribution() {
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
    public String getVersionName(ComponentType componentType) {
        String customVersionName = customVersionDisplayNames.get(componentType);
        return customVersionName != null ? customVersionName : VERSION_DISPLAY;
    }

	 @Override
	    public boolean doSupportUseDatanodeHostname() {
	        return true;
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
	    public boolean doSupportSparkStandaloneMode() {
	        return false;
	    }

	    @Override
	    public boolean doSupportSparkYarnClientMode() {
	        return true;
	    }

	    @Override
	    public boolean doSupportDynamicMemoryAllocation() {
	        return true;
	    }

	    @Override
	    public boolean doSupportCrossPlatformSubmission() {
	        return false;
	    }

	    @Override
	    public boolean doSupportImpersonation() {
	        return false;
	    }

	    @Override
	    public boolean doSupportCheckpointing() {
	        return true;
	    }

	    @Override
	    public boolean doSupportBackpressure() {
	        return true;
	    }

	    @Override
		public String generateSparkJarsPaths(List<String> commandLineJarsPaths) {
	        return SparkClassPathUtils.generateSparkJarsPaths(commandLineJarsPaths, ModuleGroupName.SPARK_BATCH.get(this.getVersion()));
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
	    public boolean doSupportKerberos() {
	        return false;
	    }

	    @Override
	    public Set<ESparkVersion> getSparkVersions() {
	        Set<ESparkVersion> version = new HashSet<>();
	        version.add(SPARK_VERSION);
	        return version;
	    }

	    @Override
	    public boolean isCloudDistribution() {
	        return true;
	    }

	    @Override
	    public boolean useCloudLauncher() {
	        return true;
	    }

	    @Override
	    public boolean isGoogleDataprocDistribution() {
	        return true;
	    }

	    @Override
	    public boolean doSupportS3() {
	        return false;
	    }

	    @Override
	    public boolean doSupportOldImportMode() {
	        return false;
	    }

	    @Override
	    public SparkStreamingKafkaVersion getSparkStreamingKafkaVersion(ESparkVersion sparkVersion) {
	        return SparkStreamingKafkaVersion.KAFKA_0_10;
	    }

	    @Override
	    public boolean doSupportOozie() {
	        return false;
	    }

	    @Override
	    public boolean doSupportHive1() {
	        return false;
	    }

	    @Override
	    public boolean doSupportHive2() {
	        // use rest API
	        return true;
	    }

	    /**
	    * Theoretically tez is supported by Dataproc:
	    * https://github.com/GoogleCloudPlatform/dataproc-initialization-actions/tree/master/tez
	    * but practically it is not implemented yet 
	    */
	    @Override
	    public boolean doSupportTezForHive() {
	        return false;
	    }

	    /**
	     * No Hbase on Dataproc
	     */
	    @Override
	    public boolean doSupportHBaseForHive() {
	        return false;
	    }

	    @Override
	    public boolean doSupportSSL() {
	        return true;
	    }

	    @Override
	    public boolean doSupportORCFormat() {
	        return true;
	    }

	    @Override
	    public boolean doSupportAvroFormat() {
	        return true;
	    }

	    @Override
	    public boolean doSupportParquetFormat() {
	        return true;
	    }

	    @Override
	    public boolean doSupportStoreAsParquet() {
	        return true;
	    }

	    @Override
	    public boolean doSupportSparkYarnClusterMode() {
	        return false;
	    }

	    @Override
	    public boolean doSupportAvroDeflateProperties(){
	        return true;
	    }

	    @Override
	    public boolean isHiveWizardCheckEnabled() {
	        return true;
	    }
	    
	    @Override
	    public boolean doRequireMetastoreVersionOverride() {
	        return true;
	    }
	    
	    @Override
	    public String getHiveMetastoreVersionForSpark() {
	        return "3.1.2";
	    }
	    
	    @Override
	    public String getHiveMetastoreJars() {
	        return "/usr/lib/hive/lib/*";
	        
	    }
	}
