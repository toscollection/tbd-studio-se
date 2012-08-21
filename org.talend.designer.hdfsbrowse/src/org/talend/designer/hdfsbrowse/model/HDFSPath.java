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

package org.talend.designer.hdfsbrowse.model;

import java.io.IOException;

import org.apache.hadoop.fs.FileStatus;
import org.apache.hadoop.fs.FileSystem;
import org.apache.hadoop.fs.Path;
import org.apache.log4j.Logger;
import org.talend.core.utils.TalendQuoteUtils;
import org.talend.designer.hdfsbrowse.ui.provider.FileSelectorTreeViewerProvider;
import org.talend.designer.hdfsbrowse.util.HadoopServerUtil;

/**
 * DOC ycbai class global comment. Detailled comment
 * 
 * DFS Path handling for DFS
 * 
 */
public abstract class HDFSPath extends HDFSNode {

    protected static Logger log = Logger.getLogger(HDFSPath.class.getName());

    protected FileSelectorTreeViewerProvider provider;

    protected HDFSConnectionBean connection;

    private FileSystem dfs = null;

    public HDFSPath(FileSelectorTreeViewerProvider provider, HDFSConnectionBean connection) {
        this.provider = provider;
        this.connection = connection;
    }

    protected HDFSPath(HDFSPath parent, Path path) {
        this.setParent(parent);
        this.setPath(path);
        this.provider = parent.provider;
        this.connection = parent.connection;
        this.dfs = parent.dfs;
    }

    protected void dispose() {
        // Free the DFS connection
    }

    @Override
    public String toString() {
        if (PATH_SEPARATOR.equals(getPath())) { //$NON-NLS-1$
            return connection.getNameNodeURI();
        } else {
            return getPath().getName();
        }
    }

    protected String getPathString() {
        if (getPath() != null) {
            return getPath().toString();
        }
        return ""; //$NON-NLS-1$
    }

    protected String getPathName() {
        return getPathName(getPath());
    }

    protected String getPathName(Path path) {
        if (path != null && path.getName() != null) {
            return path.getName();
        }
        return ""; //$NON-NLS-1$
    }

    protected String getRelativePath(String absPath) {
        String nameNodeURI = TalendQuoteUtils.removeQuotesIfExist(connection.getNameNodeURI());
        if (absPath.startsWith(nameNodeURI)) {
            absPath = absPath.substring(absPath.indexOf(nameNodeURI) + nameNodeURI.length());
        }
        if (!absPath.startsWith(PATH_SEPARATOR)) {
            absPath = PATH_SEPARATOR + absPath;
        }

        return absPath;
    }

    protected String trimFileExtention(String fileName) {
        if (fileName.indexOf(".") != -1) { //$NON-NLS-1$
            fileName = fileName.substring(0, fileName.lastIndexOf(".")); //$NON-NLS-1$
        }
        return fileName;
    }

    public abstract void refresh();

    /**
     * Refresh the UI element for this content
     */
    public void doRefresh() {
        provider.refresh(this);
    }

    protected FileSystem getDFS() throws IOException {
        if (this.dfs == null) {
            try {
                this.dfs = HadoopServerUtil.getDFS(connection);
            } catch (Exception e) {
                // ExceptionHandler.process(e);
            }
        }
        return this.dfs;
    }

    protected boolean canAccess(FileStatus status) {
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
