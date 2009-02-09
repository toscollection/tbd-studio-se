// ============================================================================
//
// Copyright (C) 2006-2009 Talend Inc. - www.talend.com
//
// This source code is available under agreement available at
// %InstallDIR%\features\org.talend.rcp.branding.%PRODUCTNAME%\%PRODUCTNAME%license.txt
//
// You should have received a copy of the agreement
// along with this program; if not, write to Talend SA
// 9 rue Pages 92150 Suresnes, France
//
// ============================================================================
package org.talend.designer.components.ecosystem.proxy;

import org.apache.commons.lang.StringUtils;

public class DefaultHTTPSTransportClientProperties extends DefaultHTTPTransportClientProperties {

    /**
     * @see org.apache.axis.components.net.TransportClientProperties#getProxyHost()
     */
    @Override
    public String getProxyHost() {
        return StringUtils.trimToEmpty(System.getProperty("https.proxyHost"));
    }

    /**
     * @see org.apache.axis.components.net.TransportClientProperties#getNonProxyHosts()
     */
    @Override
    public String getNonProxyHosts() {

        return StringUtils.trimToEmpty(System.getProperty("https.nonProxyHosts"));
    }

    /**
     * @see org.apache.axis.components.net.TransportClientProperties#getPort()
     */
    @Override
    public String getProxyPort() {

        return StringUtils.trimToEmpty(System.getProperty("https.proxyPort"));
    }

    /**
     * @see org.apache.axis.components.net.TransportClientProperties#getUser()
     */
    @Override
    public String getProxyUser() {

        return StringUtils.trimToEmpty(System.getProperty("https.proxyUser"));
    }

    /**
     * @see org.apache.axis.components.net.TransportClientProperties#getPassword()
     */
    @Override
    public String getProxyPassword() {

        return StringUtils.trimToEmpty(System.getProperty("https.proxyPassword"));
    }
}
