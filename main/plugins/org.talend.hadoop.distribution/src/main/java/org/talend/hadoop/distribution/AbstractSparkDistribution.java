// ============================================================================
//
// Copyright (C) 2006-2021 Talend Inc. - www.talend.com
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

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.talend.core.model.process.INode;
import org.talend.hadoop.distribution.condition.common.HDFSLinkedNodeCondition;
import org.talend.hadoop.distribution.constants.ModuleGroupName;
import org.talend.hadoop.distribution.utils.ModuleGroupsUtils;

public abstract class AbstractSparkDistribution extends AbstractDistribution {

	@Override
	protected Map<ComponentType, Set<DistributionModuleGroup>> buildModuleGroups() {
        Map<ComponentType, Set<DistributionModuleGroup>> result = super.buildModuleGroups();
        HDFSLinkedNodeCondition hDFSLinkedNodeCondition = new HDFSLinkedNodeCondition(this.getDistribution(), this.getVersion());
        Set<DistributionModuleGroup> hdfsDistributionModuleGroup = ModuleGroupsUtils.getModuleGroups(hDFSLinkedNodeCondition.getWebHDFSCondition(), ModuleGroupName.WEBHDFS.get(this.getVersion()), false);
        hdfsDistributionModuleGroup.addAll(ModuleGroupsUtils.getModuleGroups(null, ModuleGroupName.SPARK_COMPILE.get(this.getVersion()), false));
        result.put(ComponentType.HDFS, hdfsDistributionModuleGroup);
        result.put(ComponentType.HIVE, ModuleGroupsUtils.getModuleGroups(null, ModuleGroupName.HIVE.get(this.getVersion()), false));
        result.put(ComponentType.HBASE, ModuleGroupsUtils.getModuleGroups(null, ModuleGroupName.HBASE.get(this.getVersion()), false));
        result.put(ComponentType.IMPALA, ModuleGroupsUtils.getModuleGroups(null, ModuleGroupName.IMPALA.get(this.getVersion()), false));
        result.put(ComponentType.SQOOP, ModuleGroupsUtils.getModuleGroups(null, ModuleGroupName.SQOOP.get(this.getVersion()), false));
        result.put(ComponentType.SPARKBATCH, ModuleGroupsUtils.getModuleGroups(null, ModuleGroupName.SPARK_COMPILE.get(this.getVersion()), false));
        result.put(ComponentType.SPARKSTREAMING, ModuleGroupsUtils.getModuleGroups(null, ModuleGroupName.SPARK_COMPILE.get(this.getVersion()), false));
        return result;
    }
	
	@Override
	protected Map<NodeComponentTypeBean, Set<DistributionModuleGroup>> buildNodeModuleGroups( String distribution, String version) { 
        
        Map<NodeComponentTypeBean, Set<DistributionModuleGroup>> result = super.buildNodeModuleGroups(distribution, version);
        
        return result;
	}
	
	@Override
	public String getSparkClasspath(List<? extends INode> nodes) {
		String hadoopHome = "${HADOOP_HOME}";
		String classpathSeparator = ",";
		String classpath = "/opt/cloudera/parcels/CDH/lib/spark/jars/*"//CDH&CDP base classpath
							+ classpathSeparator + hadoopHome + "/../spark2/jars/*" //HDP base classpath
							+ classpathSeparator + "/usr/lib/spark/jars/*" + classpathSeparator + "/usr/lib/hadoop-lzo/lib/*"; //EMR base classpath
		if (doesJobContain(nodes, "hive")) {
			classpath += classpathSeparator + "/opt/cloudera/parcels/CDH/lib/spark/hive/*" //CDH&CDP hive lib for spark
					+ classpathSeparator + "/usr/lib/hive/lib/*" + classpathSeparator + "/usr/lib/tez/*"; //Hive for EMR
		}
		if (doesJobContain(nodes, "hbase")) {
			classpath += classpathSeparator + "/opt/cloudera/parcels/CDH/lib/hbase/lib/*"; //CDH&CDP hbase lib for spark
			classpath += classpathSeparator + hadoopHome + "/../hbase/lib/*"; //HDP hbase lib for spark
			classpath += classpathSeparator + "/usr/lib/hbase/lib/*"; //EMR
		}
		if (doesJobContain(nodes, "sqoop")) {
			classpath += classpathSeparator + "/opt/cloudera/parcels/CDH/lib/sqoop/*"; //CDH&CDP hbase lib for spark
			classpath += classpathSeparator + hadoopHome + "/../sqoop/*"; //HDP hbase lib for spark
			classpath += classpathSeparator + "/usr/lib/sqoop/lib/*"; //EMR
		}
		return classpath;
	}
	
	private boolean doesJobContain(List<? extends INode> nodes, String nodeName) {
		return nodes.stream().anyMatch(x -> x.getComponent().getName().toLowerCase().contains(nodeName.toLowerCase()));
	}
	
	@Override
	public List<String> getSupportedHBaseVersion() {
    	List<String> hBaseVersions = new ArrayList<String>();
    	if (this.doSupportHBase2x()) {
    		hBaseVersions.add("HBase2");
    	}
    	if (this.doSupportHBase1x()) {
    		hBaseVersions.add("HBase1");
    	}
    	return hBaseVersions;
    }
	
	@Override
    public String getSqoopPackageName() {
        return ESqoopPackageName.COM_CLOUDERA_SQOOP.toString();
    }
	
	@Override
    public boolean doSupportEmbeddedMode() {
        return false;
    }
}
