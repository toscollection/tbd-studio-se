package org.talend.hadoop.distribution.constants.databricks;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

public class DatabricksRuntimeVersion {
	
	private static List<DatabricksRuntimeAndSparkVersion> availableRuntimeAndSparkVersion = initAvailableRuntimeAndSparkVersion();
	
	public static String showIfCondition = "(SPARK_MODE=='DATABRICKS' AND DATABRICKS_USE_TRANSIENT_CLUSTER=='true')";

	private static List<DatabricksRuntimeAndSparkVersion> initAvailableRuntimeAndSparkVersion() {
		try {
			List<DatabricksRuntimeAndSparkVersion> result = new ArrayList<DatabricksRuntimeAndSparkVersion>();
			//TODO change to master
			URL url = new URL("https://raw.githubusercontent.com/Talend/tbd-studio-se/feat/TBD-12989/main/plugins/org.talend.hadoop.distribution/resources/config/default/DBRRuntimeVersionWithSparkVersion.txt");
			BufferedReader in = new BufferedReader(new InputStreamReader(url.openStream()));
		    String inputLine;
		    while ((inputLine = in.readLine()) != null) {
		    	String[] split = inputLine.split(";");
		    	result.add(new DatabricksRuntimeAndSparkVersion(split[0], split[1]));
		    }
		    in.close();
			return result;
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}
	
	public static List<DatabricksRuntimeAndSparkVersion> getAvailableRuntimeAndSparkVersion() {
		return availableRuntimeAndSparkVersion;
	}
}
