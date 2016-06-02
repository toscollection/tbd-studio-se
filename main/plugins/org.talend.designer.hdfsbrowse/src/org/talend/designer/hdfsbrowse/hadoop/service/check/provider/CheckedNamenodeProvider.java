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
package org.talend.designer.hdfsbrowse.hadoop.service.check.provider;

import java.net.URI;
import java.sql.SQLException;

import org.apache.commons.lang.StringUtils;
import org.talend.commons.utils.PasswordEncryptUtil;
import org.talend.core.hadoop.HadoopClassLoaderFactory2;
import org.talend.core.utils.ReflectionUtils;
import org.talend.designer.hdfsbrowse.hadoop.service.HadoopServiceProperties;
import org.talend.designer.hdfsbrowse.hadoop.service.check.AbstractCheckedServiceProvider;

/**
 * created by ycbai on Aug 6, 2014 Detailled comment
 *
 */
public class CheckedNamenodeProvider extends AbstractCheckedServiceProvider {

    @Override
    protected Object check(HadoopServiceProperties serviceProperties, ClassLoader classLoader) throws Exception {
        Object fileSystem = null;
        try {
            Object conf = Class.forName("org.apache.hadoop.conf.Configuration", true, classLoader).newInstance(); //$NON-NLS-1$
            URI nameNodeURI = URI.create(serviceProperties.getNameNode());
            String scheme = nameNodeURI.getScheme();
            ReflectionUtils.invokeMethod(conf, "set", new Object[] { String.format("fs.%s.impl.disable.cache", scheme), "true" }); //$NON-NLS-1$//$NON-NLS-2$ //$NON-NLS-3$
            ReflectionUtils.invokeMethod(conf, "set", new Object[] { "dfs.client.retry.policy.enabled", "false" }); //$NON-NLS-1$//$NON-NLS-2$ //$NON-NLS-3$
            ReflectionUtils.invokeMethod(conf, "set", new Object[] { "ipc.client.connect.max.retries", "0" }); //$NON-NLS-1$//$NON-NLS-2$ //$NON-NLS-3$
            setHadoopProperties(conf, serviceProperties);
            String userName = StringUtils.trimToNull(serviceProperties.getUserName());
            String group = StringUtils.trimToNull(serviceProperties.getGroup());
            boolean useKrb = serviceProperties.isUseKrb();
            boolean useMaprTicket = serviceProperties.isMaprT();
            if (useKrb) {
                String nameNodePrincipal = serviceProperties.getPrincipal();
                ReflectionUtils.invokeMethod(conf, "set", new Object[] { "dfs.namenode.kerberos.principal", nameNodePrincipal }); //$NON-NLS-1$//$NON-NLS-2$
                boolean useKeytab = serviceProperties.isUseKeytab();
                if (useKeytab) {
                    String keytabPrincipal = serviceProperties.getKeytabPrincipal();
                    String keytab = serviceProperties.getKeytab();
                    ReflectionUtils.invokeStaticMethod("org.apache.hadoop.security.UserGroupInformation", classLoader, //$NON-NLS-1$
                            "loginUserFromKeytab", new String[] { keytabPrincipal, keytab }); //$NON-NLS-1$
                }
                if (useMaprTicket) {
                    System.setProperty("pname", "MapRLogin");//$NON-NLS-1$ //$NON-NLS-2$
                    System.setProperty("https.protocols", "TLSv1.2");//$NON-NLS-1$ //$NON-NLS-2$
                    System.setProperty("mapr.home.dir", "/opt/mapr");//$NON-NLS-1$ //$NON-NLS-2$
                    System.setProperty("hadoop.login", "kerberos");//$NON-NLS-1$ //$NON-NLS-2$
                    String mapRTicketCluster = serviceProperties.getMaprTCluster();
                    String mapRTicketDuration = serviceProperties.getMaprTDuration();
                    Long desiredTicketDurInSecs = 86400L;
                    if (mapRTicketDuration != null && StringUtils.isNotBlank(mapRTicketDuration)) {
                        desiredTicketDurInSecs = Long.parseLong(mapRTicketDuration);
                    }
                    try {
                        Object mapRClientConfig = ReflectionUtils.newInstance(
                                "com.mapr.login.client.MapRLoginHttpsClient", classLoader, new Object[] {}); //$NON-NLS-1$
                        ReflectionUtils.invokeMethod(mapRClientConfig,
                                "getMapRCredentialsViaKerberos", new Object[] { mapRTicketCluster, desiredTicketDurInSecs }); //$NON-NLS-1$
                    } catch (Exception e) {
                        throw new SQLException(e);
                    }
                }
            } else if (userName != null && group != null) {
                ReflectionUtils.invokeMethod(conf, "set", new Object[] { "hadoop.job.ugi", userName + "," + group }); //$NON-NLS-1$//$NON-NLS-2$ //$NON-NLS-3$
            }
            if (useMaprTicket) {
                System.setProperty("pname", "MapRLogin");//$NON-NLS-1$ //$NON-NLS-2$
                System.setProperty("https.protocols", "TLSv1.2");//$NON-NLS-1$ //$NON-NLS-2$
                System.setProperty("mapr.home.dir", "/opt/mapr");//$NON-NLS-1$ //$NON-NLS-2$
                String mapRTicketUsername = serviceProperties.getUserName();
                String mapRTicketPassword = serviceProperties.getMaprTPassword();
                String mapRTicketCluster = serviceProperties.getMaprTCluster();
                String mapRTicketDuration = serviceProperties.getMaprTDuration();
                Long desiredTicketDurInSecs = 86400L;
                if (mapRTicketDuration != null && StringUtils.isNotBlank(mapRTicketDuration)) {
                    desiredTicketDurInSecs = Long.parseLong(mapRTicketDuration);
                }
                try {
                    String decryptedPassword = PasswordEncryptUtil.encryptPassword(mapRTicketPassword);
                    Object mapRClientConfig = ReflectionUtils.newInstance(
                            "com.mapr.login.client.MapRLoginHttpsClient", classLoader, new Object[] {}); //$NON-NLS-1$
                    ReflectionUtils.invokeMethod(mapRClientConfig, "setCheckUGI", new Object[] { false }, boolean.class);//$NON-NLS-1$
                    ReflectionUtils
                            .invokeMethod(
                                    mapRClientConfig,
                                    "getMapRCredentialsViaPassword", new Object[] { mapRTicketCluster, mapRTicketUsername, decryptedPassword, desiredTicketDurInSecs }); //$NON-NLS-1$
                } catch (Exception e) {
                    throw new SQLException(e);
                }
            }

            fileSystem = ReflectionUtils.invokeStaticMethod("org.apache.hadoop.fs.FileSystem", classLoader, "get", //$NON-NLS-1$ //$NON-NLS-2$
                    new Object[] { nameNodeURI, conf });
            if (fileSystem != null) {
                Object pathObj = ReflectionUtils.newInstance("org.apache.hadoop.fs.Path", classLoader, //$NON-NLS-1$
                        new Object[] { "/" }); //$NON-NLS-1$
                ReflectionUtils.invokeMethod(fileSystem, "listStatus", new Object[] { pathObj }); //$NON-NLS-1$
            }
        } finally {
            if (fileSystem != null) {
                try {
                    ReflectionUtils.invokeMethod(fileSystem, "close", new Object[0]); //$NON-NLS-1$
                } catch (Exception e) {
                    e.printStackTrace(); // Dont care this..
                }
            }
        }

        return fileSystem;
    }

    @Override
    protected ClassLoader getClassLoader(HadoopServiceProperties serviceProperties) {
        ClassLoader loader = null;
        if (serviceProperties.isCustom()) {
            loader = HadoopClassLoaderFactory2.getHadoopCustomClassLoader(serviceProperties.getUid(),
                    serviceProperties.getCustomJars());
        } else {
            loader = HadoopClassLoaderFactory2.getHDFSClassLoader(serviceProperties.getRelativeHadoopClusterId(),
                    serviceProperties.getDistribution(), serviceProperties.getVersion(), serviceProperties.isUseKrb());
            loader = addCustomConfsJarIfNeeded(loader, serviceProperties);
        }

        return loader;
    }

}
