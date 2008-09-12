// ============================================================================
//
// Copyright (C) 2006-2007 Talend Inc. - www.talend.com
//
// This source code is available under agreement available at
// %InstallDIR%\features\org.talend.rcp.branding.%PRODUCTNAME%\%PRODUCTNAME%license.txt
//
// You should have received a copy of the agreement
// along with this program; if not, write to Talend SA
// 9 rue Pages 92150 Suresnes, France
//
// ============================================================================
package org.talend.designer.components.ecosystem.ui.views;

import org.eclipse.jface.resource.ImageDescriptor;
import org.eclipse.swt.graphics.Image;
import org.talend.commons.ui.image.ImageProvider;
import org.talend.commons.ui.swt.tableviewer.behavior.IColumnImageProvider;
import org.talend.core.ui.images.ECoreImage;
import org.talend.designer.components.ecosystem.model.ComponentExtension;

/**
 * Image provider for component status.
 */
public class StatusImageProvider implements IColumnImageProvider<ComponentExtension> {

    private static final Image NOT_INSTALLED_ICON = ImageDescriptor.createFromFile(StatusImageProvider.class,
            "/icons/not_installed.gif").createImage();

    private static final Image DOWNLOAD_ICON = ImageDescriptor.createFromFile(StatusImageProvider.class, "/icons/download.gif")
            .createImage();

    public Image getImage(ComponentExtension extension) {
        if (extension.getInstalledLocation() == null) {
            return NOT_INSTALLED_ICON;
        } else if (!extension.getLatestRevision().getName().equals(extension.getInstalledRevision().getName())) {
            return NOT_INSTALLED_ICON;
        } else {
            return ImageProvider.getImage(ECoreImage.MODULE_INSTALLED_ICON);
        }
    }

    /**
     * 
     * DOC YeXiaowei Comment method "getDownloadImage".
     * 
     * @return
     */
    public static Image getDownloadImage() {
        return DOWNLOAD_ICON;
    }
}
