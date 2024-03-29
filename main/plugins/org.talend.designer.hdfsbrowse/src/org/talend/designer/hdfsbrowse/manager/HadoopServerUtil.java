// ============================================================================
//
// Copyright (C) 2006-2021 Talend Inc. - www.talend.com
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
import java.lang.reflect.InvocationTargetException;
import java.net.URI;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

import org.apache.commons.beanutils.BeanUtils;
import org.apache.commons.lang.ArrayUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang.exception.ExceptionUtils;
import org.eclipse.core.runtime.IConfigurationElement;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.NullProgressMonitor;
import org.talend.commons.exception.ExceptionHandler;
import org.talend.core.classloader.ClassLoaderFactory;
import org.talend.core.classloader.DynamicClassLoader;
import org.talend.core.database.conn.ConnParameterKeys;
import org.talend.core.hadoop.HadoopClassLoaderUtil;
import org.talend.core.hadoop.IHadoopClusterService;
import org.talend.core.hadoop.conf.EHadoopConfProperties;
import org.talend.core.hadoop.repository.HadoopRepositoryUtil;
import org.talend.core.model.general.ModuleNeeded;
import org.talend.core.model.general.ModuleNeeded.ELibraryInstallStatus;
import org.talend.core.repository.model.connection.ConnectionStatus;
import org.talend.core.utils.ReflectionUtils;
import org.talend.core.utils.TalendQuoteUtils;
import org.talend.designer.core.model.utils.emf.talendfile.ContextType;
import org.talend.designer.hdfsbrowse.exceptions.HadoopServerException;
import org.talend.designer.hdfsbrowse.hadoop.service.HadoopServiceProperties;
import org.talend.designer.hdfsbrowse.hadoop.service.check.provider.CheckedKnoxNamenodeProvider;
import org.talend.designer.hdfsbrowse.model.EHadoopAdditionalJars;
import org.talend.designer.hdfsbrowse.model.EHadoopAdditionalJarsMapping;
import org.talend.designer.hdfsbrowse.model.ELinuxAuthority;
import org.talend.designer.hdfsbrowse.model.HDFSConnectionBean;
import org.talend.librariesmanager.model.ModulesNeededProvider;

/**
 * DOC ycbai class global comment. Detailled comment
 */
public class HadoopServerUtil {

    public static final String EMPTY_STRING = ""; //$NON-NLS-1$

    public static final String ROOT_PATH = "/"; //$NON-NLS-1$

    public static final String GROUP_SEPARATOR = ","; //$NON-NLS-1$

    // public static final int TIMEOUT = 20; // the max time(second) which achieve DFS connection use.

    public static Object getDFS(HDFSConnectionBean connection) throws HadoopServerException {
        return getDFS(connection, null);
    }

    /**
     * DOC ycbai Comment method "getDFS".
     *
     * Provides access to the HDFS System.
     *
     * @param connection
     * @return
     * @throws HadoopServerException
     */
    public static Object getDFS(HDFSConnectionBean connection, ClassLoader currentClassLoader) throws HadoopServerException {
        if (connection == null) {
            return null;
        }
        String nameNodeURI = connection.getNameNodeURI();
        if (nameNodeURI == null) {
            return null;
        }

        Object dfs = null;
        ClassLoader classLoader = currentClassLoader;
        ClassLoader oldClassLoaderLoader = Thread.currentThread().getContextClassLoader();
        try {
            if (classLoader == null) {
                classLoader = getClassLoader(connection);
            }
            Thread.currentThread().setContextClassLoader(classLoader);
            Object conf = getConfiguration(connection, classLoader);
            // add the needed jars automatically
            List<String> classIds = EHadoopAdditionalJars.getBaseLoaderClassIds();
            for (String classId : classIds) {
                Object obj = ReflectionUtils.invokeMethod(conf, "get", new Object[] { classId }, String.class, String.class);//$NON-NLS-1$
                if (obj != null && obj instanceof String) {
                    String[] addedJars = EHadoopAdditionalJarsMapping.getAddedJarsByClassName(obj.toString());
                    if (classLoader instanceof DynamicClassLoader) {
                        classLoader = DynamicClassLoader.updateBaseLoader((DynamicClassLoader) classLoader, addedJars);
                        Thread.currentThread().setContextClassLoader(classLoader);
                    }
                }
            }
            ReflectionUtils.invokeStaticMethod("org.apache.hadoop.security.UserGroupInformation", classLoader, //$NON-NLS-1$
                    "setConfiguration", new Object[] { conf }); //$NON-NLS-1$
            boolean enableKerberos = connection.isEnableKerberos();
            String userName = StringUtils.trimToNull(connection.getUserName());
            if (userName != null) {
                userName = TalendQuoteUtils.removeQuotesIfExist(userName);
            }
            if (enableKerberos) {
                userName = null;
            }
            String group = StringUtils.trimToNull(connection.getGroup());
            if (userName == null || group != null) {
                dfs = ReflectionUtils.invokeStaticMethod("org.apache.hadoop.fs.FileSystem", classLoader, "get", //$NON-NLS-1$ //$NON-NLS-2$
                        new Object[] { conf });
            } else {
                dfs = ReflectionUtils.invokeStaticMethod("org.apache.hadoop.fs.FileSystem", classLoader, "get", new Object[] { //$NON-NLS-1$ //$NON-NLS-2$
                        new URI(EHadoopConfProperties.FS_DEFAULT_URI.get(conf)), conf, userName });

            }
        } catch (Exception e) {
            throw new HadoopServerException(e);
        } finally {
            Thread.currentThread().setContextClassLoader(oldClassLoaderLoader);
        }

        return dfs;
    }

    public static Object getConfiguration(HDFSConnectionBean connection) throws HadoopServerException {
        return getConfiguration(connection, null);
    }

    public static Object getConfiguration(HDFSConnectionBean connection, ClassLoader currentClassLoader)
            throws HadoopServerException {
        Object conf = null;

        String userName = StringUtils.trimToNull(connection.getUserName());
        String namenodePrincipal = StringUtils.trimToNull(connection.getPrincipal());
        String group = StringUtils.trimToNull(connection.getGroup());
        boolean enableKerberos = connection.isEnableKerberos();
        boolean useKeytab = connection.isUseKeytab();
        String keytabPrincipal = StringUtils.trimToNull(connection.getKeytabPrincipal());
        String keytab = StringUtils.trimToNull(connection.getKeytab());

        String nameNodeURI = connection.getNameNodeURI();
        nameNodeURI = TalendQuoteUtils.removeQuotesIfExist(nameNodeURI);
        if (userName != null) {
            userName = TalendQuoteUtils.removeQuotesIfExist(userName);
        }
        if (namenodePrincipal != null) {
            namenodePrincipal = TalendQuoteUtils.removeQuotesIfExist(namenodePrincipal);
        }
        if (group != null) {
            group = TalendQuoteUtils.removeQuotesIfExist(group);
        }
        if (keytabPrincipal != null) {
            keytabPrincipal = TalendQuoteUtils.removeQuotesIfExist(keytabPrincipal);
        }
        if (keytab != null) {
            keytab = TalendQuoteUtils.removeQuotesIfExist(keytab);
        }

        if (HadoopClassLoaderUtil.isWebHDFS(nameNodeURI)) {
            IHadoopClusterService hadoopClusterService = HadoopRepositoryUtil.getHadoopClusterService();
            String hcId = connection.getRelativeHadoopClusterId();
            if (StringUtils.isNotBlank(hcId) && hadoopClusterService != null) {
                Map<String, String> parameters = hadoopClusterService.getHadoopDbParameters(hcId);
                if (parameters.size() > 0) {
                    ContextType contextType = hadoopClusterService.getHadoopClusterContextType(hcId);
                    if (contextType != null) {
                        connection.setParentContextType(contextType);
                    }
                    boolean isUseSSL = Boolean.parseBoolean(parameters.get(ConnParameterKeys.CONN_PARA_KEY_USE_WEBHDFS_SSL));
                    String trustStorePath = connection
                            .getRealValue(parameters.get(ConnParameterKeys.CONN_PARA_KEY_WEBHDFS_SSL_TRUST_STORE_PATH), true);
                    String trustStorePassword = connection
                            .getRealValue(parameters.get(ConnParameterKeys.CONN_PARA_KEY_WEBHDFS_SSL_TRUST_STORE_PASSWORD), true);
                    HadoopRepositoryUtil.setSSLSystemProperty(isUseSSL, nameNodeURI, trustStorePath, trustStorePassword);
                }
            }
        }
        ClassLoader classLoader = currentClassLoader;
        ClassLoader oldClassLoaderLoader = Thread.currentThread().getContextClassLoader();
        try {
            if (classLoader == null) {
                classLoader = getClassLoader(connection);
            }
            Thread.currentThread().setContextClassLoader(classLoader);
            conf = Class.forName("org.apache.hadoop.conf.Configuration", true, classLoader).newInstance(); //$NON-NLS-1$
            EHadoopConfProperties.FS_DEFAULT_URI.set(conf, nameNodeURI);
            if (enableKerberos) {
                assert namenodePrincipal != null;
                userName = null;
                EHadoopConfProperties.KERBEROS_PRINCIPAL.set(conf, namenodePrincipal);
                EHadoopConfProperties.AUTHENTICATION.set(conf, "KERBEROS"); //$NON-NLS-1$
            }
            if (group != null) {
                assert userName != null;
                EHadoopConfProperties.JOB_UGI.set(conf, userName + GROUP_SEPARATOR + group);
            }

            if (useKeytab) {
                assert keytabPrincipal != null;
                assert keytab != null;
                ReflectionUtils.invokeStaticMethod("org.apache.hadoop.security.UserGroupInformation", classLoader, //$NON-NLS-1$
                        "loginUserFromKeytab", new String[] { keytabPrincipal, keytab }); //$NON-NLS-1$
            }

            Map<String, Object> configurations = connection.getConfigurations();
            Iterator<Entry<String, Object>> configsIterator = configurations.entrySet().iterator();
            while (configsIterator.hasNext()) {
                Entry<String, Object> configEntry = configsIterator.next();
                String key = configEntry.getKey();
                Object value = configEntry.getValue();
                if (key == null) {
                    continue;
                }
                ReflectionUtils
                        .invokeMethod(conf, "set", new Object[] { key, String.valueOf(value) }, String.class, String.class); //$NON-NLS-1$
            }
            ReflectionUtils.invokeMethod(conf, "set", new Object[] { "dfs.client.use.datanode.hostname", "true" }, String.class, //$NON-NLS-1$ //$NON-NLS-2$ //$NON-NLS-3$
                    String.class);
        } catch (Exception e) {
            throw new HadoopServerException(e);
        } finally {
            Thread.currentThread().setContextClassLoader(oldClassLoaderLoader);
        }

        return conf;
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
            if (StringUtils.trimToNull(userName) != null) {
                userName = TalendQuoteUtils.removeQuotesIfExist(userName);
            }
            String[] groups = new String[0];
            if (StringUtils.trimToNull(group) != null) {
                group = TalendQuoteUtils.removeQuotesIfExist(group);
                groups = group.split(GROUP_SEPARATOR);
            }
            String fileOwner = (String) ReflectionUtils.invokeMethod(status, "getOwner", new Object[0]);
            fileOwner = TalendQuoteUtils.removeQuotesIfExist(fileOwner);
            String fileGroup = (String) ReflectionUtils.invokeMethod(status, "getGroup", new Object[0]);
            fileGroup = TalendQuoteUtils.removeQuotesIfExist(fileGroup);
            Object userAction = ReflectionUtils.invokeMethod(permission, "getUserAction", new Object[0]);
            Object groupAction = ReflectionUtils.invokeMethod(permission, "getGroupAction", new Object[0]);
            Object otherAction = ReflectionUtils.invokeMethod(permission, "getOtherAction", new Object[0]);
            switch (authority) {
            case READ:
                if (fileOwner != null && fileOwner.equals(userName)) {
                    return hasReadAuthority(userAction);
                }
                if (fileGroup != null && ArrayUtils.contains(groups, fileGroup)) {
                    return hasReadAuthority(groupAction);
                }
                return hasReadAuthority(otherAction);
            case WRITE:
                if (fileOwner != null && fileOwner.equals(userName)) {
                    return hasWriteAuthority(userAction);
                }
                if (fileGroup != null && ArrayUtils.contains(groups, fileGroup)) {
                    return hasWriteAuthority(groupAction);
                }
                return hasWriteAuthority(otherAction);
            case EXCUTE:
                if (fileOwner != null && fileOwner.equals(userName)) {
                    return hasExcuteAuthority(userAction);
                }
                if (fileGroup != null && ArrayUtils.contains(groups, fileGroup)) {
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

    public static String extractUsername(HDFSConnectionBean connection, ClassLoader classLoader) throws HadoopServerException {
        String username = null;
        try {
            Object ugi = ReflectionUtils.invokeStaticMethod("org.apache.hadoop.security.UserGroupInformation", classLoader, //$NON-NLS-1$
                    "getCurrentUser", new String[0]); //$NON-NLS-1$
            if (ugi != null) {
                username = (String) ReflectionUtils.invokeMethod(ugi, "getShortUserName", new Object[0]); //$NON-NLS-1$
            }
        } catch (Exception e) {
            throw new HadoopServerException(e);
        }

        return username;
    }

    /**
     * It's meanless here since the groups retrieved are from local OS instead of HDFS. <br>
     * refer to https://hadoop.apache.org/docs/r2.8.0/hadoop-project-dist/hadoop-common/GroupsMapping.html
     */
    @Deprecated
    public static String extractGroups(HDFSConnectionBean connection, ClassLoader classLoader) throws HadoopServerException {
        StringBuffer groupBuf = new StringBuffer();
        try {
            Object ugi = ReflectionUtils.invokeStaticMethod("org.apache.hadoop.security.UserGroupInformation", classLoader, //$NON-NLS-1$
                    "getCurrentUser", new String[0]); //$NON-NLS-1$
            if (ugi != null) {
                String[] groups = (String[]) ReflectionUtils.invokeMethod(ugi, "getGroupNames", new Object[0]); //$NON-NLS-1$
                if (groups != null && groups.length > 0) {
                    boolean isFirstOne = true;
                    for (String group : groups) {
                        if (isFirstOne) {
                            isFirstOne = false;
                        } else {
                            groupBuf.append(GROUP_SEPARATOR);
                        }
                        groupBuf.append(group);
                    }
                }
            }
        } catch (Exception e) {
            ExceptionHandler.process(e);
        }

        return groupBuf.toString();
    }
    
    /**
     * DOC ycbai Comment method "testConnection".
     *
     * Test whether can connect to HDFS.
     *
     * @return
     */
    public static ConnectionStatus testKnoxConnection(HDFSConnectionBean connection) {
        ConnectionStatus connectionStatus = new ConnectionStatus();
        connectionStatus.setResult(false);
        String errorMsg = "Cannot connect to HDFS \"" + connection.getNameNodeURI()
                + "\". Please check the connection parameters. ";

        try {
            
            HadoopServiceProperties serviceProperties = new HadoopServiceProperties();
            BeanUtils.copyProperties(serviceProperties, connection);
            serviceProperties.setVersion(connection.getDfVersion());

            serviceProperties.setKnoxURL( connection.getParameters().get(ConnParameterKeys.CONN_PARA_KEY_KNOX_URL));
            serviceProperties.setKnoxUser( connection.getParameters().get(ConnParameterKeys.CONN_PARA_KEY_KNOX_USER));
            serviceProperties.setKnoxPassword( connection.getParameters().get(ConnParameterKeys.CONN_PARA_KEY_KNOX_PASSWORD));
            serviceProperties.setKnoxDirectory( connection.getParameters().get(ConnParameterKeys.CONN_PARA_KEY_KNOX_DIRECTORY));
            serviceProperties.setKnoxTimeout( connection.getParameters().get(ConnParameterKeys.CONN_PARA_KEY_KNOX_TIMEOUT));
            
            CheckedKnoxNamenodeProvider hadoopKnox = new CheckedKnoxNamenodeProvider();
            connectionStatus.setResult(hadoopKnox.checkService(serviceProperties, 1000));
            connectionStatus.setMessageException("Connection successful"); 
            
        } catch (HadoopServerException | InvocationTargetException | IllegalAccessException e) {
            connectionStatus.setMessageException(ExceptionUtils.getFullStackTrace(e));
        }       

        return connectionStatus;
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
        ClassLoader oldClassLoaderLoader = Thread.currentThread().getContextClassLoader();
        try {
            ClassLoader classLoader = getClassLoader(connection);
            Thread.currentThread().setContextClassLoader(classLoader);
            dfs = getDFS(connection, classLoader);
            if (dfs != null) {
                // Check if we can access the HDFS file content.
                Object pathObj = ReflectionUtils
                        .newInstance("org.apache.hadoop.fs.Path", classLoader, new Object[] { ROOT_PATH });
                ReflectionUtils.invokeMethod(dfs, "listStatus", new Object[] { pathObj });

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
                connectionStatus.setMessageException(missJarMsg);
            } else {
                connectionStatus.setMessageException(ExceptionUtils.getFullStackTrace(e));
            }
            ExceptionHandler.process(e);
        } finally {
            if (dfs != null) {
                try {
                    ReflectionUtils.invokeMethod(dfs, "close", new Object[0]);
                } catch (Exception e) {
                }
            }
            Thread.currentThread().setContextClassLoader(oldClassLoaderLoader);
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
        Set<String> jars = new HashSet<String>();
        Set<String> set = getJarsNeeded(connection);
        Set<ModuleNeeded> modulesNeeded = ModulesNeededProvider.getModulesNeeded();
        for (ModuleNeeded module : modulesNeeded) {
            String id = module.getId();
            if (id != null && set.contains(id) && module.getStatus().equals(ELibraryInstallStatus.NOT_INSTALLED)) {
                jars.add(module.getModuleName());
            }
        }
        return jars;
    }

    private static Set<String> getJarsNeeded(HDFSConnectionBean connection) {
        String index = "HDFS" + ":" + connection.getDistribution() + ":" + connection.getDfVersion(); //$NON-NLS-1$ //$NON-NLS-2$ //$NON-NLS-3$
        if (connection.isEnableKerberos()) {
            index += "?USE_KRB"; //$NON-NLS-1$
        }
        return getJarsNeeded(index);
    }

    private static Set<String> getJarsNeeded(String index) {
        Set<String> jars = new HashSet<String>();
        IConfigurationElement configElement = ClassLoaderFactory.findIndex(index);
        if (configElement == null) {
            return jars;
        }
        String parentIndex = configElement.getAttribute(ClassLoaderFactory.PARENT_ATTR);
        if (StringUtils.isNotEmpty(parentIndex)) {
            Set<String> parentJars = getJarsNeeded(parentIndex);
            if (parentJars != null && !parentJars.isEmpty()) {
                jars.addAll(parentJars);
            }
        }
        String[] jarsNeeded = ClassLoaderFactory.getLibs(index);
        if (jarsNeeded != null && 0 < jarsNeeded.length) {
            jars.addAll(Arrays.asList(jarsNeeded));
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
}
