// ============================================================================
//
// Copyright (C) 2006-2015 Talend Inc. - www.talend.com
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

/**
 * DOC ycbai class global comment. Detailled comment
 */
public class HDFSConnectionBean {

    private String distribution;

    private String dfVersion;

    private String dfDrivers;

    private String nameNodeURI;

    private boolean enableKerberos;

    private String principal;

    private String userName;

    private String group;

    private String rowSeparator;

    private String fieldSeparator;

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

    public String getDfDrivers() {
        return this.dfDrivers;
    }

    public void setDfDrivers(String dfDrivers) {
        this.dfDrivers = dfDrivers;
    }

    public String getNameNodeURI() {
        return this.nameNodeURI;
    }

    public void setNameNodeURI(String nameNodeURI) {
        this.nameNodeURI = nameNodeURI;
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

}
