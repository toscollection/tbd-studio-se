/**
 * Licensed to the Apache Software Foundation (ASF) under one or more contributor license agreements. See the NOTICE
 * file distributed with this work for additional information regarding copyright ownership. The ASF licenses this file
 * to you under the Apache License, Version 2.0 (the "License"); you may not use this file except in compliance with the
 * License. You may obtain a copy of the License at
 * 
 * http://www.apache.org/licenses/LICENSE-2.0
 * 
 * Unless required by applicable law or agreed to in writing, software distributed under the License is distributed on
 * an "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the License for the
 * specific language governing permissions and limitations under the License.
 */

package org.talend.designer.hdfsbrowse.model;

import java.io.IOException;
import java.util.List;

import org.apache.hadoop.fs.FileStatus;
import org.apache.hadoop.fs.FileSystem;
import org.apache.hadoop.fs.Path;
import org.apache.log4j.Logger;
import org.eclipse.swt.graphics.Image;
import org.eclipse.ui.ISharedImages;
import org.talend.designer.hdfsbrowse.ui.provider.FileSelectorTreeViewerProvider;

/**
 * DOC ycbai class global comment. Detailled comment
 * 
 * Local representation of a folder in the DFS.
 * 
 */
public class HDFSFolder extends HDFSPath {

    private static Logger log = Logger.getLogger(HDFSFolder.class);

    public HDFSFolder(FileSelectorTreeViewerProvider provider, HDFSConnectionBean connection) {
        super(provider, connection);
    }

    public HDFSFolder(HDFSPath parent, Path path) {
        super(parent, path);
    }

    protected void loadHDFSFolderChildren() throws IOException {
        FileSystem dfs = getDFS();
        if (dfs == null) {
            return;
        }
        for (FileStatus status : dfs.listStatus(getPath())) {
            if (!canAccess(status)) {
                continue;
            }
            HDFSPath content = null;
            Path statusPath = status.getPath();
            String pathName = getPathName(statusPath);
            if (status.isDir()) {
                content = new HDFSFolder(this, status.getPath());
            } else {
                content = new HDFSFile(this, status.getPath());
                content.setTable(createTable(trimFileExtention(pathName)));
            }
            content.setValue(pathName);
            addChild(content);
        }
    }

    public List<IHDFSNode> getChildren() {
        if (!hasFetchedChildren) {
            super.getChildren().clear();
            try {
                loadHDFSFolderChildren();
                hasFetchedChildren = true;
            } catch (IOException ioe) {
                log.error(ioe);
            } finally {
                provider.refresh(HDFSFolder.this);
            }
        }

        return super.getChildren();
    }

    public boolean hasChildren() {
        return true;
    }

    /*
     * (non-Javadoc)
     * 
     * @see org.talend.repository.hdfs.model.HDFSContent#refresh()
     */
    @Override
    public void refresh() {
        this.doRefresh();
    }

    public Image getImage() {
        return sharedImages.getImage(ISharedImages.IMG_OBJ_FOLDER);
    }

    @Override
    public String toString() {
        return String.format("%s (%s)", super.toString(), this.getChildren().size());
    }

    @Override
    public EHadoopFileTypes getType() {
        return EHadoopFileTypes.FOLDER;
    }

}
