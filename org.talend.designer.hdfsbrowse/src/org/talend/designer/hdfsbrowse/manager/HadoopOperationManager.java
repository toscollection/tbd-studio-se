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

import java.io.InputStream;

import org.talend.core.repository.ConnectionStatus;
import org.talend.core.utils.ReflectionUtils;
import org.talend.core.utils.TalendQuoteUtils;
import org.talend.cwm.relational.RelationalFactory;
import org.talend.cwm.relational.TdTable;
import org.talend.designer.hdfsbrowse.exceptions.HadoopServerException;
import org.talend.designer.hdfsbrowse.model.HDFSConnectionBean;
import org.talend.designer.hdfsbrowse.model.HDFSFile;
import org.talend.designer.hdfsbrowse.model.HDFSFolder;
import org.talend.designer.hdfsbrowse.model.HDFSPath;

/**
 * DOC ycbai class global comment. Detailled comment
 */
public class HadoopOperationManager {

    protected static final String PATH_SEPARATOR = "/"; //$NON-NLS-1$

    protected static final String COLON = ":"; //$NON-NLS-1$

    private static HadoopOperationManager instance = new HadoopOperationManager();

    private HadoopOperationManager() {
    }

    public static HadoopOperationManager getInstance() {
        return instance;
    }

    public void loadHDFSFolderChildren(HDFSConnectionBean connection, Object fileSystem, ClassLoader classLoader,
            HDFSPath parent, String path) throws HadoopServerException {
        if (connection == null || fileSystem == null || classLoader == null || parent == null || path == null) {
            return;
        }

        try {
            Object pathObj = ReflectionUtils.newInstance("org.apache.hadoop.fs.Path", classLoader, new Object[] { path });
            Object[] statusList = (Object[]) ReflectionUtils.invokeMethod(fileSystem, "listStatus", new Object[] { pathObj });
            if (statusList == null) {
                return;
            }
            for (Object status : statusList) {
                if (!canAccess(connection, status)) {
                    continue;
                }
                HDFSPath content = null;
                Object statusPath = ReflectionUtils.invokeMethod(status, "getPath", new Object[0]);
                String pathName = (String) ReflectionUtils.invokeMethod(statusPath, "getName", new Object[0]);
                // String absolutePath = ((URI) ReflectionUtils.invokeMethod(statusPath, "toUri", new
                // Object[0])).toString();
                // Get path from toString method since convert to URI will escape some special characters. Need test...
                String absolutePath = (String) ReflectionUtils.invokeMethod(statusPath, "toString", new Object[0]);
                String relativePath = getRelativePath(connection, absolutePath);
                if ((Boolean) ReflectionUtils.invokeMethod(status, "isDir", new Object[0])) {
                    content = new HDFSFolder(parent);
                } else {
                    content = new HDFSFile(parent);
                    content.setTable(createTable(trimFileExtention(pathName)));
                }
                content.setPath(relativePath);
                content.setValue(pathName);
                parent.addChild(content);
            }
        } catch (Exception e) {
            throw new HadoopServerException(e);
        }
    }

    public long getFileSize(Object fileSystem, ClassLoader classLoader, String filePath) throws HadoopServerException {
        long size = 0;
        try {
            Object pathObj = ReflectionUtils.newInstance("org.apache.hadoop.fs.Path", classLoader, new Object[] { filePath });
            Object fileStatus = ReflectionUtils.invokeMethod(fileSystem, "getFileStatus", new Object[] { pathObj });
            size = (Long) ReflectionUtils.invokeMethod(fileStatus, "getLen", new Object[0]);
        } catch (Exception e) {
            throw new HadoopServerException(e);
        }

        return size;
    }

    public InputStream getFileContent(Object fileSystem, ClassLoader classLoader, String filePath) throws HadoopServerException {
        InputStream stream = null;
        if (fileSystem == null) {
            return null;
        }

        try {
            Object pathObj = ReflectionUtils.newInstance("org.apache.hadoop.fs.Path", classLoader, new Object[] { filePath });
            stream = (InputStream) ReflectionUtils.invokeMethod(fileSystem, "open", new Object[] { pathObj });
        } catch (Exception e) {
            throw new HadoopServerException(e);
        }

        return stream;
    }

    public InputStream getFileContent(HDFSConnectionBean connection, String filePath) throws HadoopServerException {
        Object fileSystem = getDFS(connection);
        if (fileSystem == null) {
            return null;
        }
        ClassLoader classLoader = getClassLoader(connection);
        return getFileContent(fileSystem, classLoader, filePath);
    }

    public ConnectionStatus testConnection(HDFSConnectionBean connection) {
        return HadoopServerUtil.testConnection(connection);
    }

    public Object getDFS(HDFSConnectionBean connectionBean) throws HadoopServerException {
        return HadoopServerUtil.getDFS(connectionBean);
    }

    public ClassLoader getClassLoader(HDFSConnectionBean connectionBean) {
        return HadoopServerUtil.getClassLoader(connectionBean);
    }

    private String trimFileExtention(String fileName) {
        if (fileName.indexOf(".") != -1) { //$NON-NLS-1$
            fileName = fileName.substring(0, fileName.lastIndexOf(".")); //$NON-NLS-1$
        }
        return fileName;
    }

    private String getRelativePath(HDFSConnectionBean connection, String absPath) {
        String nameNodeURI = TalendQuoteUtils.removeQuotesIfExist(connection.getNameNodeURI());
        if (absPath.startsWith(nameNodeURI)) {
            absPath = absPath.substring(absPath.indexOf(nameNodeURI) + nameNodeURI.length());
        } else if (nameNodeURI.endsWith(HadoopParameterUtil.DEFAULT_NAMENODE_PORT)) {
            String nameNodeURIWithoutPort = nameNodeURI.substring(0,
                    nameNodeURI.lastIndexOf(HadoopParameterUtil.DEFAULT_NAMENODE_PORT) - 1);
            if (absPath.startsWith(nameNodeURIWithoutPort)) {
                absPath = absPath.substring(absPath.indexOf(nameNodeURIWithoutPort) + nameNodeURIWithoutPort.length());
            }
        }
        if (absPath.indexOf(COLON) != -1) {
            absPath = absPath.substring(absPath.lastIndexOf(COLON) + 1);
        }
        if (!absPath.startsWith(PATH_SEPARATOR)) {
            absPath = PATH_SEPARATOR + absPath;
        }

        return absPath;
    }

    private TdTable createTable(String tableName) {
        TdTable table = RelationalFactory.eINSTANCE.createTdTable();
        table.setName(tableName);
        table.setLabel(tableName);

        return table;
    }

    private boolean canAccess(HDFSConnectionBean connection, Object status) throws HadoopServerException {
        if (status == null) {
            return false;
        }
        String userName = null;
        if (connection != null) {
            userName = connection.getUserName();
        }
        return HadoopServerUtil.hasReadAuthority(status, userName);
    }

}
