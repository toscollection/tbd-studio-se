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
package org.talend.designer.hdfsbrowse.util;

import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;

import org.apache.commons.lang.StringUtils;
import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.FileStatus;
import org.apache.hadoop.fs.FileSystem;
import org.apache.hadoop.fs.permission.FsAction;
import org.apache.hadoop.fs.permission.FsPermission;
import org.talend.commons.ui.runtime.exception.ExceptionHandler;
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
     */
    public static FileSystem getDFS(HDFSConnectionBean connection) throws IOException, InterruptedException, URISyntaxException {
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

        FileSystem dfs = null;
        Configuration conf = new Configuration();
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
            dfs = getDFS(conf);
        } else {
            dfs = getDFS(new URI(nameNodeURI), conf, userName);
        }

        return dfs;
    }

    public static FileSystem getDFS(Configuration conf) throws IOException {
        return FileSystem.get(conf);
    }

    public static FileSystem getDFS(URI uri, Configuration conf, String userName) throws IOException, InterruptedException {
        return FileSystem.get(uri, conf, userName);
    }

    public static void closeHDFSConnection() throws IOException {
        FileSystem.closeAll();
    }

    public static boolean hasReadAuthority(FileStatus status, String userName) {
        return hasAuthority(status, userName, ELinuxAuthority.READ);
    }

    public static boolean hasWriteAuthority(FileStatus status, String userName) {
        return hasAuthority(status, userName, ELinuxAuthority.WRITE);
    }

    public static boolean hasExcuteAuthority(FileStatus status, String userName) {
        return hasAuthority(status, userName, ELinuxAuthority.EXCUTE);
    }

    public static boolean hasAuthority(FileStatus status, String userName, ELinuxAuthority authority) {
        boolean hasAuthority = false;
        if (status == null) {
            return hasAuthority;
        }
        if (authority == null) {
            authority = ELinuxAuthority.READ;
        }
        FsPermission permission = status.getPermission();
        if (permission == null) {
            return hasAuthority;
        }
        String owner = status.getOwner();
        FsAction userAction = permission.getUserAction();
        FsAction groupAction = permission.getGroupAction();
        FsAction otherAction = permission.getOtherAction();
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

    private static boolean hasReadAuthority(FsAction action) {
        boolean hasAuthority = false;
        if (action == null) {
            return hasAuthority;
        }
        switch (action) {
        case READ:
        case READ_WRITE:
        case READ_EXECUTE:
        case ALL:
            hasAuthority = true;
            break;
        default:
            break;
        }

        return hasAuthority;
    }

    private static boolean hasWriteAuthority(FsAction action) {
        boolean hasAuthority = false;
        if (action == null) {
            return hasAuthority;
        }
        switch (action) {
        case WRITE:
        case WRITE_EXECUTE:
        case READ_WRITE:
        case ALL:
            hasAuthority = true;
            break;
        default:
            break;
        }

        return hasAuthority;
    }

    private static boolean hasExcuteAuthority(FsAction action) {
        boolean hasAuthority = false;
        if (action == null) {
            return hasAuthority;
        }
        switch (action) {
        case EXECUTE:
        case READ_EXECUTE:
        case WRITE_EXECUTE:
        case ALL:
            hasAuthority = true;
            break;
        default:
            break;
        }

        return hasAuthority;
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
        FileSystem dfs = null;
        try {
            dfs = HadoopServerUtil.getDFS(connection);
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
                    dfs.close();
                } catch (IOException e) {
                }
            }
        }

        return connectionStatus;
    }

}
