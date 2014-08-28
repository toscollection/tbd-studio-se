// ============================================================================
//
// Copyright (C) 2006-2014 Talend Inc. - www.talend.com
//
// This source code is available under agreement available at
// %InstallDIR%\features\org.talend.rcp.branding.%PRODUCTNAME%\%PRODUCTNAME%license.txt
//
// You should have received a copy of the agreement
// along with this program; if not, write to Talend SA
// 9 rue Pages 92150 Suresnes, France
//
// ============================================================================
package org.talend.designer.hdfsbrowse.hadoop.service;

import java.util.Set;

/**
 * created by ycbai on Aug 6, 2014 Detailled comment
 *
 */
public class HadoopServiceProperties {

    private String distribution;

    private String version;

    private boolean isCustom;

    private String nameNode;

    private String jobTracker;

    private String resourceManager;

    private boolean useKrb;

    private String uid;

    private Set<String> customJars;

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

    public boolean isCustom() {
        return this.isCustom;
    }

    public void setCustom(boolean isCustom) {
        this.isCustom = isCustom;
    }

    public String getNameNode() {
        return this.nameNode;
    }

    public void setNameNode(String nameNode) {
        this.nameNode = nameNode;
    }

    public String getJobTracker() {
        return this.jobTracker;
    }

    public void setJobTracker(String jobTracker) {
        this.jobTracker = jobTracker;
    }

    public String getResourceManager() {
        return this.resourceManager;
    }

    public void setResourceManager(String resourceManager) {
        this.resourceManager = resourceManager;
    }

    public boolean isUseKrb() {
        return this.useKrb;
    }

    public void setUseKrb(boolean useKrb) {
        this.useKrb = useKrb;
    }

    public Set<String> getCustomJars() {
        return this.customJars;
    }

    public void setCustomJars(Set<String> customJars) {
        this.customJars = customJars;
    }

    public String getUid() {
        return this.uid;
    }

    public void setUid(String uid) {
        this.uid = uid;
    }

}
