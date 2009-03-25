// ============================================================================
//
// Copyright (C) 2006-2009 Talend Inc. - www.talend.com
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

    public static final Image INSTALL_ICON = ImageDescriptor.createFromFile(StatusImageProvider.class, "/icons/install.png")
            .createImage();

    public static final Image INSTALL_UNAVAILABLE_ICON = ImageDescriptor.createFromFile(StatusImageProvider.class,
            "/icons/install_unavailable.png").createImage();

    public static final Image UPDATE_UNAVAILABLE_ICON = ImageDescriptor.createFromFile(StatusImageProvider.class,
            "/icons/update_unavailable.png").createImage();

    public static final Image UPDATE_ICON = ImageDescriptor.createFromFile(StatusImageProvider.class, "/icons/update.png")
            .createImage();

    public static final Image REMOVE_ICON = ImageDescriptor.createFromFile(StatusImageProvider.class, "/icons/remove.png")
            .createImage();

    public static final Image REMOVE_UNAVAILABLE_ICON = ImageDescriptor.createFromFile(StatusImageProvider.class,
            "/icons/remove_unavailable.png").createImage();

    public static Image getInstallImage(ComponentExtension extension) {
        if (extension.getInstalledRevision() == null) {
            return INSTALL_ICON;
        } else {
            return INSTALL_UNAVAILABLE_ICON;
        }
    }

    public static Image getUpdateImage(ComponentExtension extension) {
        // double installedVersion = new Double(extension.getInstalledRevision().getName());
        // double LatestRevision = new Double(extension.getLatestRevision().getName());
        if (extension.getInstalledRevision() != null
                && !(extension.getInstalledRevision().getName()).equals(extension.getLatestRevision().getName())) {
            return UPDATE_ICON;
        } else {
            return UPDATE_UNAVAILABLE_ICON;
        }
    }

    public static Image getRemoveImage(ComponentExtension extension) {
        if (extension.getInstalledRevision() != null) {
            return REMOVE_ICON;
        } else {
            return REMOVE_UNAVAILABLE_ICON;
        }
    }

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
