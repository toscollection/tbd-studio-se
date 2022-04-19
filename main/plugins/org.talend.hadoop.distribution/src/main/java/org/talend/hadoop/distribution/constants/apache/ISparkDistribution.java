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

import java.util.List;

import org.talend.hadoop.distribution.constants.databricks.EDatabriksCloudProvider;
import org.talend.hadoop.distribution.constants.databricks.EDatabriksSubmitMode;

public interface ISparkDistribution {

    static final String DISTRIBUTION_NAME = "SPARK";

    static final String DISTRIBUTION_DISPLAY_NAME = "Universal";
    
    public List<ESparkMode> getSparkModes();
    
    List<EDatabriksCloudProvider> getSupportCloudProviders();
    
    List<EDatabriksSubmitMode> getRunSubmitMode();
    
    public boolean doSupportUniversalDBRMode();
    
    public boolean doSupportUniversalDataprocMode();
    
    public boolean doSupportUniversalLocalMode();
    
    public boolean doSupportSparkYarnK8SMode();
    
    public boolean doSupportSparkYarnClusterMode();
    
    public boolean doSupportUniversalStandaloneMode();
    
}
