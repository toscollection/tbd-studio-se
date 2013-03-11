// ============================================================================
//
// Copyright (C) 2006-2013 Talend Inc. - www.talend.com
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
    JOB_TRACKER_URI("mapred.job.tracker", "localhost:50020"),

    /**
     * Property name for naming the default file system (URI).
     */
    FS_DEFAULT_URI("fs.defaultFS", "hdfs://localhost:50040/"),

    /**
     * Property name for kerberos principal.
     */
    KERBEROS_PRINCIPAL("dfs.namenode.kerberos.principal", "nn/_HOST@EXAMPLE.COM"),

    /**
     * Property name for job ugi.
     */
    JOB_UGI("hadoop.job.ugi", "username,supergroup"),

    /**
     * Property name for the default socket factory:
     */
    SOCKET_FACTORY_DEFAULT("hadoop.rpc.socket.factory.class.default", "org.apache.hadoop.net.StandardSocketFactory"),

    /**
     * Property name for the SOCKS server URI.
     */
    SOCKS_SERVER("hadoop.socks.server", "host:1080"),

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

    public final String defVal;

    EHadoopConfProperties(String name, String defVal) {
        this.name = name;
        this.defVal = defVal;

        EHadoopConfProperties.registerProperty(name, this);
    }

    public String get(Object conf) throws SecurityException, IllegalArgumentException, NoSuchMethodException,
            IllegalAccessException, InvocationTargetException {
        return (String) ReflectionUtils.invokeMethod(conf, "get", new Object[] { name });
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
