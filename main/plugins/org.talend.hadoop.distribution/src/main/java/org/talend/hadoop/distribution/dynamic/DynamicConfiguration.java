// ============================================================================
//
// Copyright (C) 2006-2016 Talend Inc. - www.talend.com
//
// This source code is available under agreement available at
// %InstallDIR%\features\org.talend.rcp.branding.%PRODUCTNAME%\%PRODUCTNAME%license.txt
//
// You should have received a copy of the agreement
// along with this program; if not, write to Talend SA
// 9 rue Pages 92150 Suresnes, France
//
// ============================================================================
package org.talend.hadoop.distribution.dynamic;


/**
 * DOC cmeng  class global comment. Detailled comment
 */
public class DynamicConfiguration {

    private String distribution;

    private String version;

    private String remoteRepositoryUrl;

    public String getDistribution() {
        return this.distribution;
    }

    public void setDistribution(String distribution) {
        this.distribution = distribution;
    }

    public String getVersion() {
        return this.version;
    }

    public void setVersion(String version) {
        this.version = version;
    }

    public String getRemoteRepositoryUrl() {
        return this.remoteRepositoryUrl;
    }

    public void setRemoteRepositoryUrl(String remoteRepositoryUrl) {
        this.remoteRepositoryUrl = remoteRepositoryUrl;
    }

}
