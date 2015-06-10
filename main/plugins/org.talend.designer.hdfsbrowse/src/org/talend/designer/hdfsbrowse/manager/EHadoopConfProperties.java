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

package org.talend.designer.hdfsbrowse.manager;

import java.lang.reflect.InvocationTargetException;
import java.util.HashMap;
import java.util.Map;

import org.talend.core.utils.ReflectionUtils;

/**
 * DOC ycbai class global comment. Detailled comment
 */
public enum EHadoopConfProperties {
    /**
     * Property name for naming the job tracker (URI).
     */
    JOB_TRACKER_URI("mapred.job.tracker", "localhost:8021"), //$NON-NLS-1$ //$NON-NLS-2$

    RESOURCE_MANAGER("yarn.resourcemanager.address", "localhost:8032"), //$NON-NLS-1$ //$NON-NLS-2$

    RESOURCEMANAGER_SCHEDULER("yarn.resourcemanager.scheduler.address", "localhost:8030"), //$NON-NLS-1$ //$NON-NLS-2$

    JOBHISTORY("mapreduce.jobhistory.address", "localhost:10020"), //$NON-NLS-1$ //$NON-NLS-2$

    STAGING_DIR("yarn.app.mapreduce.am.staging-dir", "/user"), //$NON-NLS-1$ //$NON-NLS-2$

    /**
     * Property name for naming the default file system (URI).
     */
    FS_DEFAULT_URI("fs.default.name", "hdfs://localhost:8020/"), //$NON-NLS-1$ //$NON-NLS-2$

    FS_DEFAULT_URI_NEW("fs.defaultFS", "hdfs://localhost:8020/"), //$NON-NLS-1$ //$NON-NLS-2$

    DFS_NAMESERVICES("dfs.nameservices", ""), //$NON-NLS-1$ //$NON-NLS-2$

    DFS_HA_NAMENODES_NAMESERVICE_ID("dfs.ha.namenodes.%s", ""), //$NON-NLS-1$//$NON-NLS-2$

    YARN_RESOURCEMANAGER_HA_ENABLED("yarn.resourcemanager.ha.enabled", false), //$NON-NLS-1$

    YARN_RESOURCEMANAGER_HA_RM_IDS("yarn.resourcemanager.ha.rm-ids", ""), //$NON-NLS-1$ //$NON-NLS-2$

    YARN_RESOURCEMANAGER_ADMIN_ADDRESS_RM_ID("yarn.resourcemanager.admin.address.%s", ""), //$NON-NLS-1$ //$NON-NLS-2$

    FS_HDFS_IMPL_DISABLE_CACHE("fs.hdfs.impl.disable.cache", "false"), //$NON-NLS-1$ //$NON-NLS-2$

    HADOOP_SECURITY_KERBEROS_TICKET_CACHE_PATH("hadoop.security.kerberos.ticket.cache.path", ""), //$NON-NLS-1$ //$NON-NLS-2$

    AUTHENTICATION("hadoop.security.authentication", "simple"), //$NON-NLS-1$ //$NON-NLS-2$

    /**
     * Property name for kerberos principal.
     */
    KERBEROS_PRINCIPAL("dfs.namenode.kerberos.principal", "nn/_HOST@EXAMPLE.COM"), //$NON-NLS-1$ //$NON-NLS-2$

    RM_PRINCIPAL("yarn.resourcemanager.principal", "yarn/_HOST@EXAMPLE.COM"), //$NON-NLS-1$ //$NON-NLS-2$

    JT_PRINCIPAL("mapreduce.jobtracker.kerberos.principal", "mapred/_HOST@EXAMPLE.COM"), //$NON-NLS-1$ //$NON-NLS-2$

    JH_PRINCIPAL("mapreduce.jobhistory.principal", "mapred/_HOST@EXAMPLE.COM"), //$NON-NLS-1$ //$NON-NLS-2$

    KERBEROS_COMMAND_KINIT("hadoop.kerberos.kinit.command", "/usr/kerberos/bin/kinit"), //$NON-NLS-1$ //$NON-NLS-2$

    /**
     * Property name for job ugi.
     */
    JOB_UGI("hadoop.job.ugi", "username,supergroup"), //$NON-NLS-1$ //$NON-NLS-2$

    /**
     * Property name for the default socket factory:
     */
    SOCKET_FACTORY_DEFAULT("hadoop.rpc.socket.factory.class.default", "org.apache.hadoop.net.StandardSocketFactory"), //$NON-NLS-1$ //$NON-NLS-2$

    /**
     * Property name for the SOCKS server URI.
     */
    SOCKS_SERVER("hadoop.socks.server", "host:1080"), //$NON-NLS-1$ //$NON-NLS-2$

    ;

    private static Map<String, EHadoopConfProperties> map;

    private static synchronized void registerProperty(String name, EHadoopConfProperties prop) {
        if (EHadoopConfProperties.map == null) {
            EHadoopConfProperties.map = new HashMap<String, EHadoopConfProperties>();
        }

        EHadoopConfProperties.map.put(name, prop);
    }

    public static EHadoopConfProperties getByName(String propName) {
        return map.get(propName);
    }

    public final String name;

    public final Object defVal;

    EHadoopConfProperties(String name, Object defVal) {
        this.name = name;
        this.defVal = defVal;

        EHadoopConfProperties.registerProperty(name, this);
    }

    public String get(Object conf) throws SecurityException, IllegalArgumentException, NoSuchMethodException,
            IllegalAccessException, InvocationTargetException {
        return (String) ReflectionUtils.invokeMethod(conf, "get", new Object[] { name, defVal });
    }

    public void set(Object conf, String value) throws SecurityException, IllegalArgumentException, NoSuchMethodException,
            IllegalAccessException, InvocationTargetException {
        assert value != null;
        ReflectionUtils.invokeMethod(conf, "set", new Object[] { name, value });
    }

    public String getName() {
        return this.name;
    }

}
