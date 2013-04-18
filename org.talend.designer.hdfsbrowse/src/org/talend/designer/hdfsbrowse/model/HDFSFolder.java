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

import java.util.List;

import org.apache.log4j.Logger;
import org.eclipse.swt.graphics.Image;
import org.eclipse.ui.ISharedImages;
import org.eclipse.ui.PlatformUI;
import org.talend.commons.ui.swt.dialogs.ErrorDialogWidthDetailArea;
import org.talend.designer.hdfsbrowse.HDFSPlugin;
import org.talend.designer.hdfsbrowse.exceptions.HadoopServerException;
import org.talend.designer.hdfsbrowse.i18n.Messages;
import org.talend.designer.hdfsbrowse.manager.HadoopOperationManager;
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

    public HDFSFolder(HDFSPath parent) {
        super(parent);
    }

    protected void loadHDFSFolderChildren() throws HadoopServerException {
        HadoopOperationManager.getInstance().loadHDFSFolderChildren(connection, fileSystem, classLoader, this, getPath());
    }

    @Override
    public List<IHDFSNode> getChildren() {
        boolean checkConnection = true;
        if (!hasFetchedChildren) {
            super.getChildren().clear();
            try {
                loadHDFSFolderChildren();
                hasFetchedChildren = true;
            } catch (Exception e) {
                log.error(e);
                checkConnection = false;
                PlatformUI.getWorkbench().getDisplay().syncExec(new Runnable() {

                    @Override
                    public void run() {
                        String mainMsg = Messages.getString("AbstractHDFSBrowseController.connectionFailure.mainMsg"); //$NON-NLS-1$
                        String detailMsg = Messages.getString("AbstractHDFSBrowseController.connectionFailure.detailMsg", //$NON-NLS-1$
                                connection.getNameNodeURI());
                        new ErrorDialogWidthDetailArea(PlatformUI.getWorkbench().getDisplay().getActiveShell(),
                                HDFSPlugin.PLUGIN_ID, mainMsg, detailMsg);
                        return;
                    }
                });
            } finally {
                if (checkConnection) {
                    provider.refresh(HDFSFolder.this);
                }
            }
        }

        return super.getChildren();
    }

    @Override
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

    @Override
    public Image getImage() {
        return sharedImages.getImage(ISharedImages.IMG_OBJ_FOLDER);
    }

    @Override
    public String toString() {
        return String.format("%s (%s)", super.toString(), this.getChildren().size()); //$NON-NLS-1$
    }

    @Override
    public EHadoopFileTypes getType() {
        return EHadoopFileTypes.FOLDER;
    }

}
