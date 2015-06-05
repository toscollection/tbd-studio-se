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
package org.talend.repository.hadoopcluster.conf.model;

/**
 * created by ycbai on 2015年6月4日 Detailled comment
 *
 */
public class HadoopConfsConnection {

    private String connURL;

    private String username;

    private String password;

    public String getConnURL() {
        return this.connURL;
    }

    public void setConnURL(String connURL) {
        this.connURL = connURL;
    }

    public String getUsername() {
        return this.username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return this.password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

}
