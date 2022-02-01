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
package org.talend.hadoop.distribution.constants.dataproc;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

public class DataprocRuntimeVersion {

private static List<DataprocRuntimeAndSparkVersion> availableRuntimeAndSparkVersion = initAvailableRuntimeAndSparkVersion();
	
	public static String showIfCondition = "(SPARK_MODE=='DATAPROC')";

	private static List<DataprocRuntimeAndSparkVersion> initAvailableRuntimeAndSparkVersion() {
		try {
			List<DataprocRuntimeAndSparkVersion> result = new ArrayList<DataprocRuntimeAndSparkVersion>();
			//TODO change to master
			URL url = new URL("https://raw.githubusercontent.com/Talend/tbd-studio-se/feat/TBD-12990/main/plugins/org.talend.hadoop.distribution/resources/config/default/DataprocRuntimeVersionWithSparkVersion.txt");
			BufferedReader in = new BufferedReader(new InputStreamReader(url.openStream()));
		    String inputLine;
		    while ((inputLine = in.readLine()) != null) {
		    	String[] split = inputLine.split(";");
		    	result.add(new DataprocRuntimeAndSparkVersion(split[0], split[1], split[2], split[3]));
		    }
		    in.close();
			return result;
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}
	
	public static List<DataprocRuntimeAndSparkVersion> getAvailableRuntimeAndSparkVersion() {
		return availableRuntimeAndSparkVersion;
	}
}