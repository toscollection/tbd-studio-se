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

import java.io.BufferedInputStream;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.net.URI;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;

import org.apache.commons.lang.StringUtils;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.NullProgressMonitor;
import org.talend.commons.exception.ExceptionHandler;
import org.talend.core.classloader.DynamicClassLoader;
import org.talend.core.hadoop.version.EHadoopVersion4Drivers;
import org.talend.core.model.general.ModuleNeeded;
import org.talend.core.model.general.ModuleNeeded.ELibraryInstallStatus;
import org.talend.core.repository.ConnectionStatus;
import org.talend.core.utils.ReflectionUtils;
import org.talend.core.utils.TalendQuoteUtils;
import org.talend.designer.hdfsbrowse.exceptions.HadoopServerException;
import org.talend.designer.hdfsbrowse.model.ELinuxAuthority;
import org.talend.designer.hdfsbrowse.model.HDFSConnectionBean;
import org.talend.librariesmanager.model.ModulesNeededProvider;

/**
 * DOC ycbai class global comment. Detailled comment
 */
public class HadoopServerUtil {

    public static final String EMPTY_STRING = ""; //$NON-NLS-1$

    public static final int TIMEOUT = 20; // the max time(second) which achieve DFS connection use.

    /**
     * DOC ycbai Comment method "getDFS".
     * 
     * Provides access to the HDFS System.
     * 
     * @param connection
     * @return
     * @throws HadoopServerException
     */
    public static Object getDFS(HDFSConnectionBean connection) throws HadoopServerException {
        if (connection == null) {
            return null;
        }
        String nameNodeURI = connection.getNameNodeURI();
        if (nameNodeURI == null) {
            return null;
        }

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

            Callable<Object> dfsCallable = null;
            if (userName == null || group != null) {
                dfsCallable = getDFS(conf, classLoader);
            } else {
                dfsCallable = getDFS(new URI(nameNodeURI), conf, userName, classLoader);
            }

            ExecutorService executor = Executors.newSingleThreadExecutor();
            Future<Object> future = executor.submit(dfsCallable);
            try {
                dfs = future.get(TIMEOUT, TimeUnit.SECONDS);
            } catch (TimeoutException e) {
                future.cancel(true);
            }
        } catch (Exception e) {
            throw new HadoopServerException(e);
        } finally {
            Thread.currentThread().setContextClassLoader(oldClassLoaderLoader);
        }

        return dfs;
    }

    private static Callable<Object> getDFS(final Object conf, final ClassLoader classLoader) {
        return new Callable<Object>() {

            @Override
            public Object call() throws Exception {
                return ReflectionUtils.invokeStaticMethod("org.apache.hadoop.fs.FileSystem", classLoader, "get",
                        new Object[] { conf });
            }
        };

    }

    private static Callable<Object> getDFS(final URI uri, final Object conf, final String userName, final ClassLoader classLoader) {
        return new Callable<Object>() {

            @Override
            public Object call() throws Exception {
                return ReflectionUtils.invokeStaticMethod("org.apache.hadoop.fs.FileSystem", classLoader, "get", new Object[] {
                        uri, conf, userName });
            }
        };

    }

    public static boolean hasReadAuthority(Object status, String userName, String group) throws HadoopServerException {
        return hasAuthority(status, userName, group, ELinuxAuthority.READ);
    }

    public static boolean hasWriteAuthority(Object status, String userName, String group) throws HadoopServerException {
        return hasAuthority(status, userName, group, ELinuxAuthority.WRITE);
    }

    public static boolean hasExcuteAuthority(Object status, String userName, String group) throws HadoopServerException {
        return hasAuthority(status, userName, group, ELinuxAuthority.EXCUTE);
    }

    public static boolean hasAuthority(Object status, String userName, String group, ELinuxAuthority authority)
            throws HadoopServerException {
        boolean hasAuthority = false;
        if (status == null) {
            return hasAuthority;
        }
        if (authority == null) {
            authority = ELinuxAuthority.READ;
        }

        try {
            Object permission = ReflectionUtils.invokeMethod(status, "getPermission", new Object[0]);
            if (permission == null) {
                return hasAuthority;
            }
            if (StringUtils.isNotBlank(userName)) {
                userName = TalendQuoteUtils.addQuotesIfNotExist(userName);
            }
            if (StringUtils.isNotBlank(group)) {
                group = TalendQuoteUtils.addQuotesIfNotExist(group);
            }
            String fileOwner = (String) ReflectionUtils.invokeMethod(status, "getOwner", new Object[0]);
            fileOwner = TalendQuoteUtils.addQuotesIfNotExist(fileOwner);
            String fileGroup = (String) ReflectionUtils.invokeMethod(status, "getGroup", new Object[0]);
            fileGroup = TalendQuoteUtils.addQuotesIfNotExist(fileGroup);
            Object userAction = ReflectionUtils.invokeMethod(permission, "getUserAction", new Object[0]);
            Object groupAction = ReflectionUtils.invokeMethod(permission, "getGroupAction", new Object[0]);
            Object otherAction = ReflectionUtils.invokeMethod(permission, "getOtherAction", new Object[0]);
            switch (authority) {
            case READ:
                if (fileOwner != null && fileOwner.equals(userName)) {
                    return hasReadAuthority(userAction);
                }
                if (fileGroup != null && fileGroup.equals(group)) {
                    return hasReadAuthority(groupAction);
                }
                return hasReadAuthority(otherAction);
            case WRITE:
                if (fileOwner != null && fileOwner.equals(userName)) {
                    return hasWriteAuthority(userAction);
                }
                if (fileGroup != null && fileGroup.equals(group)) {
                    return hasWriteAuthority(groupAction);
                }
                return hasWriteAuthority(otherAction);
            case EXCUTE:
                if (fileOwner != null && fileOwner.equals(userName)) {
                    return hasExcuteAuthority(userAction);
                }
                if (fileGroup != null && fileGroup.equals(group)) {
                    return hasExcuteAuthority(groupAction);
                }
                return hasExcuteAuthority(otherAction);
            default:
                break;
            }
        } catch (Exception e) {
            throw new HadoopServerException(e);
        }

        return hasAuthority;
    }

    private static boolean hasReadAuthority(Object action) {
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
        String errorMsg = "Cannot connect to HDFS \"" + connection.getNameNodeURI()
                + "\". Please check the connection parameters. ";
        Object dfs = null;
        try {
            dfs = getDFS(connection);
            if (dfs != null) {
                connectionStatus.setResult(true);
                connectionStatus.setMessageException("Connection successful");
            } else {
                connectionStatus.setMessageException(errorMsg);
            }
        } catch (Exception e) {
            Set<String> jars = getMissingJars(connection);
            String missJarMsg = "";
            if (jars.size() > 0) {
                missJarMsg = "Missing jars:" + jars.toString() + "; " + "Need to check them in modules view.";
            }
            ExceptionHandler.process(e);
            connectionStatus.setMessageException(errorMsg + "\n" + missJarMsg);
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

    /**
     * DOC Talend Comment method "getMissingJars".
     * 
     * @param connection
     * @return
     */
    public static Set<String> getMissingJars(HDFSConnectionBean connection) {
        Set<String> set = new HashSet<String>();
        Set<String> jars = new HashSet<String>();
        ClassLoader classLoader = HadoopClassLoaderFactory.getClassLoader(connection.getDistribution(),
                connection.getDfVersion(), false, false);
        if (classLoader instanceof DynamicClassLoader) {
            set.addAll(((DynamicClassLoader) classLoader).getLibraries());
        }
        List jarsNeed = ModulesNeededProvider.getModulesNeeded();
        for (Object jar : jarsNeed) {
            if (jar instanceof ModuleNeeded) {
                String jarName = ((ModuleNeeded) jar).getModuleName();
                if (set.contains(jarName) && ((ModuleNeeded) jar).getStatus().equals(ELibraryInstallStatus.NOT_INSTALLED)) {
                    jars.add(jarName);
                }
            }
        }
        return jars;
    }

    public static ClassLoader getClassLoader(HDFSConnectionBean connection) {
        return HadoopClassLoaderFactory.getClassLoader(connection);
    }

    public static void upload(File localFile, String dfsPath, Object fileSystem, ClassLoader classLoader)
            throws HadoopServerException {
        upload(localFile, dfsPath, fileSystem, classLoader, null);
    }

    /**
     * DOC ycbai Comment method "upload".
     * 
     * Upload a local file to the distributed file system
     * 
     * @param localFile the file which you want to upload from local
     * @param dfsPath the path on file system which you want to upload.
     * @param fileSystem
     * @param classLoader
     * @param monitor
     * @throws OozieJobDeployException
     */
    public static void upload(File localFile, String dfsPath, Object fileSystem, ClassLoader classLoader, IProgressMonitor monitor)
            throws HadoopServerException {
        if (classLoader == null) {
            classLoader = HadoopServerUtil.class.getClassLoader();
        }
        if (monitor == null) {
            monitor = new NullProgressMonitor();
        }
        monitor.setTaskName("Upload file " + dfsPath);

        BufferedInputStream istream = null;
        DataOutputStream ostream = null;
        Object destPath = null;
        try {
            destPath = ReflectionUtils.newInstance("org.apache.hadoop.fs.Path", classLoader, new Object[] { dfsPath }); //$NON-NLS-1$
            if (localFile.isDirectory()) {
                ReflectionUtils.invokeMethod(fileSystem, "mkdirs", new Object[] { destPath }); //$NON-NLS-1$
                monitor.worked(1);
                for (File child : localFile.listFiles()) {
                    if (monitor.isCanceled()) {
                        return;
                    }
                    upload(child, dfsPath + "/" + child.getName(), fileSystem, classLoader, monitor); //$NON-NLS-1$
                }
            } else if (localFile.isFile()) {
                istream = new BufferedInputStream(new FileInputStream(localFile));
                ostream = (DataOutputStream) ReflectionUtils.invokeMethod(fileSystem, "create", new Object[] { destPath }); //$NON-NLS-1$

                int bytes;
                final int taskSize = 1024;
                byte[] buffer = new byte[taskSize];

                while ((bytes = istream.read(buffer)) >= 0) {
                    if (monitor.isCanceled()) {
                        return;
                    }
                    ostream.write(buffer, 0, bytes);
                    monitor.worked(1);
                }
            }
        } catch (Exception e) {
            throw new HadoopServerException(String.format("Unable to upload file %s to %s", localFile, destPath), e); //$NON-NLS-1$
        } finally {
            try {
                if (istream != null) {
                    istream.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
                // nothing we can do here
            }
            try {
                if (ostream != null) {
                    ostream.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
                // nothing we can do here
            }
        }
    }

    public static boolean isSupportSecurity(EHadoopVersion4Drivers version4Drivers) {
        if (version4Drivers != null) {
            switch (version4Drivers) {
            case HDP_1_0:
            case HDP_1_2:
            case APACHE_1_0_0:
            case CLOUDERA_CDH4:
            case APACHE_1_0_3_EMR:
                return true;
            default:
                return false;
            }
        }
        return false;
    }

    public static boolean isSupportGroup(EHadoopVersion4Drivers version4Drivers) {
        if (version4Drivers != null) {
            switch (version4Drivers) {
            case APACHE_0_20_2:
            case MAPR1:
            case MAPR2:
            case MAPR212:
            case MAPR_EMR:
                return true;
            default:
                return false;
            }
        }
        return false;
    }
}
