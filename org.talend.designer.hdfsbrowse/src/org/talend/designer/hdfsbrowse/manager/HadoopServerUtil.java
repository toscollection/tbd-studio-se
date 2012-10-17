// ============================================================================
//
// Copyright (C) 2006-2012 Talend Inc. - www.talend.com
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

import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;

import org.apache.commons.lang.StringUtils;
import org.talend.commons.ui.runtime.exception.ExceptionHandler;
import org.talend.commons.utils.data.reflection.ReflectionUtils;
import org.talend.core.repository.ConnectionStatus;
import org.talend.core.utils.TalendQuoteUtils;
import org.talend.designer.hdfsbrowse.model.ELinuxAuthority;
import org.talend.designer.hdfsbrowse.model.HDFSConnectionBean;

/**
 * DOC ycbai class global comment. Detailled comment
 */
public class HadoopServerUtil {

    public static final String EMPTY_STRING = ""; //$NON-NLS-1$

    /**
     * DOC ycbai Comment method "getDFS".
     * 
     * Provides access to the HDFS System.
     * 
     * @param connection
     * @return
     * @throws IOException
     * @throws InterruptedException
     * @throws URISyntaxException
     * @throws ClassNotFoundException
     * @throws IllegalAccessException
     * @throws InstantiationException
     */
    public static Object getDFS(HDFSConnectionBean connection) throws IOException, InterruptedException, URISyntaxException,
            InstantiationException, IllegalAccessException, ClassNotFoundException {
        assert connection != null;
        String nameNodeURI = connection.getNameNodeURI();
        assert nameNodeURI != null;
        nameNodeURI = TalendQuoteUtils.removeQuotesIfExist(nameNodeURI);
        String userName = StringUtils.trimToNull(connection.getUserName());
        if (userName != null) {
            userName = TalendQuoteUtils.removeQuotesIfExist(userName);
        }
        String principal = StringUtils.trimToNull(connection.getPrincipal());
        String group = StringUtils.trimToNull(connection.getGroup());
        boolean enableKerberos = connection.isEnableKerberos();

        Object dfs = null;
        ClassLoader oldClassLoaderLoader = Thread.currentThread().getContextClassLoader();
        try {
            ClassLoader classLoader = getClassLoader(connection);
            Thread.currentThread().setContextClassLoader(classLoader);
            Object conf = Class.forName("org.apache.hadoop.conf.Configuration", true, classLoader).newInstance();
            EHadoopConfProperties.FS_DEFAULT_URI.set(conf, nameNodeURI);
            if (enableKerberos) {
                assert principal != null;
                userName = null;
                // EHadoopConfProperties.JOB_UGI.set(conf, EMPTY_STRING);
                EHadoopConfProperties.KERBEROS_PRINCIPAL.set(conf, principal);
            }
            if (group != null) {
                assert userName != null;
                // EHadoopConfProperties.KERBEROS_PRINCIPAL.set(conf, EMPTY_STRING);
                EHadoopConfProperties.JOB_UGI.set(conf, userName + "," + group); //$NON-NLS-1$
            }
            if (userName == null) {
                dfs = getDFS(conf, classLoader);
            } else {
                dfs = getDFS(new URI(nameNodeURI), conf, userName, classLoader);
            }
        } finally {
            Thread.currentThread().setContextClassLoader(oldClassLoaderLoader);
        }

        return dfs;
    }

    private static Object getDFS(Object conf, ClassLoader classLoader) throws IOException {
        return ReflectionUtils.invokeStaticMethod("org.apache.hadoop.fs.FileSystem", classLoader, "get", new Object[] { conf });
    }

    private static Object getDFS(URI uri, Object conf, String userName, ClassLoader classLoader) throws IOException,
            InterruptedException {
        return ReflectionUtils.invokeStaticMethod("org.apache.hadoop.fs.FileSystem", classLoader, "get", new Object[] { uri,
                conf, userName });
    }

    public static boolean hasReadAuthority(Object status, String userName) throws ClassNotFoundException {
        return hasAuthority(status, userName, ELinuxAuthority.READ);
    }

    public static boolean hasWriteAuthority(Object status, String userName) throws ClassNotFoundException {
        return hasAuthority(status, userName, ELinuxAuthority.WRITE);
    }

    public static boolean hasExcuteAuthority(Object status, String userName) throws ClassNotFoundException {
        return hasAuthority(status, userName, ELinuxAuthority.EXCUTE);
    }

    public static boolean hasAuthority(Object status, String userName, ELinuxAuthority authority) throws ClassNotFoundException {
        boolean hasAuthority = false;
        if (status == null) {
            return hasAuthority;
        }
        if (authority == null) {
            authority = ELinuxAuthority.READ;
        }
        Object permission = ReflectionUtils.invokeMethod(status, "getPermission", new Object[0]);
        if (permission == null) {
            return hasAuthority;
        }
        userName = TalendQuoteUtils.addQuotesIfNotExist(userName);
        String owner = (String) ReflectionUtils.invokeMethod(status, "getOwner", new Object[0]);
        owner = TalendQuoteUtils.addQuotesIfNotExist(owner);
        Object userAction = ReflectionUtils.invokeMethod(permission, "getUserAction", new Object[0]);
        Object groupAction = ReflectionUtils.invokeMethod(permission, "getGroupAction", new Object[0]);
        Object otherAction = ReflectionUtils.invokeMethod(permission, "getOtherAction", new Object[0]);
        switch (authority) {
        case READ:
            if (owner != null && owner.equals(userName)) {
                return hasReadAuthority(userAction) || hasReadAuthority(groupAction);
            }
            return hasReadAuthority(otherAction);
        case WRITE:
            if (owner != null && owner.equals(userName)) {
                return hasWriteAuthority(userAction) || hasWriteAuthority(groupAction);
            }
            return hasWriteAuthority(otherAction);
        case EXCUTE:
            if (owner != null && owner.equals(userName)) {
                return hasExcuteAuthority(userAction) || hasExcuteAuthority(groupAction);
            }
            return hasExcuteAuthority(otherAction);
        default:
            break;
        }

        return hasAuthority;
    }

    private static boolean hasReadAuthority(Object action) throws ClassNotFoundException {
        if (action == null) {
            return false;
        }
        Object enumName = ((Enum) action).name();
        return "READ".equals(enumName) || "READ".equals(enumName) || "READ_WRITE".equals(enumName)
                || "READ_EXECUTE".equals(enumName) || "ALL".equals(enumName);
    }

    private static boolean hasWriteAuthority(Object action) {
        if (action == null) {
            return false;
        }
        Object enumName = ((Enum) action).name();
        return "WRITE".equals(enumName) || "WRITE_EXECUTE".equals(enumName) || "READ_WRITE".equals(enumName)
                || "ALL".equals(enumName);
    }

    private static boolean hasExcuteAuthority(Object action) {
        if (action == null) {
            return false;
        }
        Object enumName = ((Enum) action).name();
        return "EXECUTE".equals(enumName) || "READ_EXECUTE".equals(enumName) || "WRITE_EXECUTE".equals(enumName)
                || "ALL".equals(enumName);
    }

    /**
     * DOC ycbai Comment method "testConnection".
     * 
     * Test whether can connect to HDFS.
     * 
     * @return
     */
    public static ConnectionStatus testConnection(HDFSConnectionBean connection) {
        ConnectionStatus connectionStatus = new ConnectionStatus();
        connectionStatus.setResult(false);
        Object dfs = null;
        try {
            dfs = getDFS(connection);
            if (dfs != null) {
                connectionStatus.setResult(true);
                connectionStatus.setMessageException("Connection successful");
            } else {
                connectionStatus.setMessageException("Cannot connect to HDFS \"" + connection.getNameNodeURI()
                        + "\". Please check the connection parameters. ");
            }
        } catch (Exception e) {
            ExceptionHandler.process(e);
            connectionStatus.setMessageException(e.getMessage());
        } finally {
            if (dfs != null) {
                try {
                    ReflectionUtils.invokeMethod(dfs, "close", new Object[0]);
                } catch (Exception e) {
                }
            }
        }

        return connectionStatus;
    }

    public static ClassLoader getClassLoader(HDFSConnectionBean connection) {
        return HadoopClassLoaderFactory.getClassLoader(connection);
    }

}
