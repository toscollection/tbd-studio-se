package org.talend.designer.hdfsbrowse.hadoop.service.check.provider;

import java.sql.SQLException;

import org.talend.commons.utils.PasswordEncryptUtil;
import org.talend.core.hadoop.HadoopClassLoaderFactory2;
import org.talend.core.utils.ReflectionUtils;
import org.talend.designer.hdfsbrowse.hadoop.service.HadoopServiceProperties;
import org.talend.designer.hdfsbrowse.hadoop.service.check.AbstractCheckedServiceProvider;

/**
 * 
 * created by ycbai on Aug 8, 2014 Detailled comment
 *
 */
public class CheckedResourceManagerProvider extends AbstractCheckedServiceProvider {

    @Override
    protected Object check(HadoopServiceProperties serviceProperties, ClassLoader classLoader) throws Exception {
        Object configuration = Class.forName("org.apache.hadoop.conf.Configuration", true, classLoader).newInstance(); //$NON-NLS-1$
        Object conf = ReflectionUtils
                .newInstance("org.apache.hadoop.mapred.JobConf", classLoader, new Object[] { configuration }); //$NON-NLS-1$
        ReflectionUtils.invokeMethod(conf, "set", new Object[] { "mapreduce.framework.name", "yarn" }); //$NON-NLS-1$//$NON-NLS-2$ //$NON-NLS-3$
        ReflectionUtils.invokeMethod(conf, "set", //$NON-NLS-1$
                new Object[] { "yarn.resourcemanager.address", serviceProperties.getResourceManager() }); //$NON-NLS-1$
        ReflectionUtils.invokeMethod(conf, "set", new Object[] { "ipc.client.connect.timeout", "2000" }); //$NON-NLS-1$//$NON-NLS-2$ //$NON-NLS-3$
        ReflectionUtils.invokeMethod(conf, "set", new Object[] { "ipc.client.connection.maxidletime", "2000" }); //$NON-NLS-1$//$NON-NLS-2$ //$NON-NLS-3$
        ReflectionUtils.invokeMethod(conf, "set", new Object[] { "ipc.client.connect.max.retries", "1" }); //$NON-NLS-1$//$NON-NLS-2$ //$NON-NLS-3$
        ReflectionUtils.invokeMethod(conf, "set", new Object[] { "yarn.resourcemanager.connect.max-wait.ms", "2000" }); //$NON-NLS-1$//$NON-NLS-2$ //$NON-NLS-3$
        ReflectionUtils.invokeMethod(conf, "set", new Object[] { "yarn.resourcemanager.connect.retry-interval.ms", "2000" }); //$NON-NLS-1$//$NON-NLS-2$ //$NON-NLS-3$
        setHadoopProperties(conf, serviceProperties);
        boolean useKrb = serviceProperties.isUseKrb();
        boolean useMaprTicket = serviceProperties.isMaprT();
        if (useKrb) {
            String yarnPrincipal = serviceProperties.getJtOrRmPrincipal();
            ReflectionUtils.invokeMethod(conf, "set", new Object[] { "yarn.resourcemanager.principal", yarnPrincipal }); //$NON-NLS-1$//$NON-NLS-2$
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
                Object mapRTicketDuration = serviceProperties.getMaprTDuration();
                try {
                    ReflectionUtils.invokeStaticMethod("com.mapr.login.client.MapRLoginHttpsClient", classLoader, //$NON-NLS-1$
                            "getMapRCredentialsViaKerberos", new Object[] { mapRTicketCluster, mapRTicketDuration }); //$NON-NLS-1$
                } catch (Exception e) {
                    throw new SQLException(e);
                }
            }
        }

        if (useMaprTicket) {
            System.setProperty("pname", "MapRLogin");//$NON-NLS-1$ //$NON-NLS-2$
            System.setProperty("https.protocols", "TLSv1.2");//$NON-NLS-1$ //$NON-NLS-2$
            System.setProperty("mapr.home.dir", "/opt/mapr");//$NON-NLS-1$ //$NON-NLS-2$
            String mapRTicketUsername = serviceProperties.getUserName();
            String mapRTicketPassword = serviceProperties.getMaprTPassword();
            String mapRTicketCluster = serviceProperties.getMaprTCluster();
            Object mapRTicketDuration = serviceProperties.getMaprTDuration();
            try {
                String decryptedPassword = PasswordEncryptUtil.encryptPassword(mapRTicketPassword);
                ReflectionUtils
                        .invokeStaticMethod(
                                "com.mapr.login.client.MapRLoginHttpsClient", classLoader, "setCheckUGI", new Object[] { false }, Boolean.class);//$NON-NLS-1$//$NON-NLS-2$
                ReflectionUtils
                        .invokeStaticMethod(
                                "com.mapr.login.client.MapRLoginHttpsClient",//$NON-NLS-1$
                                classLoader,
                                "getMapRCredentialsViaPassword", new Object[] { mapRTicketCluster, mapRTicketUsername, decryptedPassword, mapRTicketDuration }); //$NON-NLS-1$
            } catch (Exception e) {
                throw new SQLException(e);
            }
        }

        Object jc = ReflectionUtils.newInstance("org.apache.hadoop.mapred.JobClient", classLoader, new Object[] { conf }); //$NON-NLS-1$

        return ReflectionUtils.invokeMethod(jc, "getClusterStatus", new Object[0]); //$NON-NLS-1$
    }

    @Override
    protected ClassLoader getClassLoader(HadoopServiceProperties serviceProperties) {
        ClassLoader loader = null;
        if (serviceProperties.isCustom()) {
            loader = HadoopClassLoaderFactory2.getHadoopCustomClassLoader(serviceProperties.getUid(),
                    serviceProperties.getCustomJars());
        } else {
            loader = HadoopClassLoaderFactory2.getMRClassLoader(serviceProperties.getRelativeHadoopClusterId(),
                    serviceProperties.getDistribution(), serviceProperties.getVersion(), serviceProperties.isUseKrb());
            loader = addCustomConfsJarIfNeeded(loader, serviceProperties);
        }

        return loader;
    }

}
