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
package org.talend.repository.hcatalog.util;

import org.talend.commons.ui.runtime.image.IImage;

/**
 * DOC ycbai class global comment. Detailled comment
 */
public enum EHCatalogImage implements IImage {

    HCATALOG_RESOURCE_ICON("/icons/hadoop-logo-16x16.png"), //$NON-NLS-1$
    HCATALOG_WIZ("/icons/hadoop-logo-wiz.png");

    private String path;

    EHCatalogImage() {
        this.path = "/icons/unknown.gif"; //$NON-NLS-1$
    }

    EHCatalogImage(String path) {
        this.path = path;
    }

    /**
     * Getter for path.
     * 
     * @return the path
     */
    public String getPath() {
        return this.path;
    }

    /**
     * Getter for clazz.
     * 
     * @return the clazz
     */
    public Class getLocation() {
        return EHCatalogImage.class;
    }
}
