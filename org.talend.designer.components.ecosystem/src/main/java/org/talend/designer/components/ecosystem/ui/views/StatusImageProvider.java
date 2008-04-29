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

import org.eclipse.swt.graphics.Image;
import org.talend.commons.ui.image.ImageProvider;
import org.talend.commons.ui.swt.tableviewer.behavior.IColumnImageProvider;
import org.talend.core.ui.images.ECoreImage;
import org.talend.designer.components.ecosystem.model.ComponentExtension;

/**
 * Image provider for component status.
 */
public class StatusImageProvider implements IColumnImageProvider<ComponentExtension> {

    public Image getImage(ComponentExtension extension) {
        if (extension.getInstalledLocation() == null) {
            return ImageProvider.getImage(ECoreImage.MODULE_ERROR_ICON);
        } else if (!extension.getLatestRevision().getName().equals(extension.getInstalledRevision().getName())) {
            return ImageProvider.getImage(ECoreImage.MODULE_WARNING_ICON);
        } else {
            return ImageProvider.getImage(ECoreImage.MODULE_INSTALLED_ICON);
        }

    }
}
