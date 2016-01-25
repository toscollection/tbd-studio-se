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
package org.talend.designer.hdfsbrowse.model;

import java.util.HashMap;
import java.util.Map;

/**
 * DOC ycbai class global comment. Detailled comment
 */
public class HDFSConnectionBean {

    private String distribution;

    private String dfVersion;

    private String nameNodeURI;

    private String jobtrackerURI;

    private boolean enableKerberos;

    private String principal;

    private String userName;

    private String group;

    private String rowSeparator;

    private String fieldSeparator;

    private String relativeHadoopClusterId;

    private boolean useCustomVersion;

    private Map<String, Object> additionalProperties = new HashMap<String, Object>();

    public String getDistribution() {
        return this.distribution;
    }

    public void setDistribution(String distribution) {
        this.distribution = distribution;
    }

    public String getDfVersion() {
        return this.dfVersion;
    }

    public void setDfVersion(String dfVersion) {
        this.dfVersion = dfVersion;
    }

    public String getNameNodeURI() {
        return this.nameNodeURI;
    }

    public void setNameNodeURI(String nameNodeURI) {
        this.nameNodeURI = nameNodeURI;
    }

    public String getJobtrackerURI() {
        return this.jobtrackerURI;
    }

    public void setJobtrackerURI(String jobtrackerURI) {
        this.jobtrackerURI = jobtrackerURI;
    }

    public boolean isEnableKerberos() {
        return this.enableKerberos;
    }

    public void setEnableKerberos(boolean enableKerberos) {
        this.enableKerberos = enableKerberos;
    }

    public String getPrincipal() {
        return this.principal;
    }

    public void setPrincipal(String principal) {
        this.principal = principal;
    }

    public String getUserName() {
        return this.userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getGroup() {
        return this.group;
    }

    public void setGroup(String group) {
        this.group = group;
    }

    public String getRowSeparator() {
        return this.rowSeparator;
    }

    public void setRowSeparator(String rowSeparator) {
        this.rowSeparator = rowSeparator;
    }

    public String getFieldSeparator() {
        return this.fieldSeparator;
    }

    public void setFieldSeparator(String fieldSeparator) {
        this.fieldSeparator = fieldSeparator;
    }

    public String getRelativeHadoopClusterId() {
        return this.relativeHadoopClusterId;
    }

    public void setRelativeHadoopClusterId(String relativeHadoopClusterId) {
        this.relativeHadoopClusterId = relativeHadoopClusterId;
    }

    public Map<String, Object> getAdditionalProperties() {
        return this.additionalProperties;
    }

    public boolean isUseCustomVersion() {
        return this.useCustomVersion;
    }

    public void setUseCustomVersion(boolean useCustomVersion) {
        this.useCustomVersion = useCustomVersion;
    }

}
