package org.talend.hadoop.distribution.constants.databricks;

import java.util.Arrays;
import java.util.List;

public class DatabricksRuntimeVersion {
	
	private static List<DatabricksRuntimeAndSparkVersion> availableRuntimeAndSparkVersion = initAvailableRuntimeAndSparkVersion();
	
	public static String showIfCondition = "(SPARK_MODE=='DATABRICKS' AND DATABRICKS_USE_TRANSIENT_CLUSTER=='true')";

	private static List<DatabricksRuntimeAndSparkVersion> initAvailableRuntimeAndSparkVersion() {
		return Arrays.asList(new DatabricksRuntimeAndSparkVersion("9.1.x-scala2.12", "SPARK_3_1_x"));
	}
	
	public static List<DatabricksRuntimeAndSparkVersion> getAvailableRuntimeAndSparkVersion() {
		return availableRuntimeAndSparkVersion;
	}
}
