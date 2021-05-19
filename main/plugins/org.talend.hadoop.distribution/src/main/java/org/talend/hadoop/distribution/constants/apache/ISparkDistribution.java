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
package org.talend.hadoop.distribution.constants.apache;

import java.util.Arrays;
import java.util.List;

@SuppressWarnings("nls")
public interface ISparkDistribution {
	
	public enum ESparkRuntime {

	    K8S("K8S");

	    private String runtime;

	    ESparkRuntime(String runtime) {
	        this.runtime = runtime;
	    }

	    public String getRuntime() {
	        return runtime;
	    }

	}

    static final String DISTRIBUTION_NAME = "SPARK";

    static final String DISTRIBUTION_DISPLAY_NAME = "Spark";
    
    static final List<ESparkRuntime> SPARK_RUNTIME = Arrays.asList(ESparkRuntime.values());	
}
