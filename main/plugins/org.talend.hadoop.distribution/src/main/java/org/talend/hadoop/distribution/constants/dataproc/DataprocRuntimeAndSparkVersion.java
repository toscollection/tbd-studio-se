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

public class DataprocRuntimeAndSparkVersion {

	private String runtimeVersion;

	private String sparkVersion;

	public DataprocRuntimeAndSparkVersion(String runtimeVersion, String sparkVersion) {
		this.runtimeVersion = runtimeVersion;
		this.sparkVersion = sparkVersion;
	}

	public String getRuntimeVersion() {
		return this.runtimeVersion;
	}

	public String getSparkVersion() {
		return this.sparkVersion;
	}
}