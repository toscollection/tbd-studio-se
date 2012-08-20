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
package org.talend.repository.hdfs.server;

import java.io.IOException;
import java.net.URISyntaxException;

import org.apache.hadoop.fs.FileSystem;
import org.talend.designer.hdfsbrowse.util.HadoopServerUtil;
import org.talend.repository.hdfs.util.HDFSModelUtil;
import org.talend.repository.model.hdfs.HDFSConnection;

/**
 * DOC ycbai class global comment. Detailled comment
 */
public class HadoopServerManager {

    private static HadoopServerManager instance = null;

    private static FileSystem dfs = null;

    private HadoopServerManager() {
    }

    public static HadoopServerManager getInstance() {
        if (instance == null) {
            instance = new HadoopServerManager();
        }
        return instance;
    }

    public FileSystem getDFS(HDFSConnection connection, boolean renew) throws IOException, InterruptedException,
            URISyntaxException {
        if (renew) {
            HadoopServerUtil.closeHDFSConnection();
            dfs = null;
        }
        if (dfs == null) {
            dfs = HadoopServerUtil.getDFS(HDFSModelUtil.convert2HDFSConnectionBean(connection));
        }
        return dfs;
    }

    public FileSystem getDFS(HDFSConnection connection) throws IOException, InterruptedException, URISyntaxException {
        return getDFS(connection, false);
    }

}
