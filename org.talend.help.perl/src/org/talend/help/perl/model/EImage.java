// ============================================================================
//
// Copyright (C) 2006-2010 Talend Inc. - www.talend.com
//
// This source code is available under agreement available at
// %InstallDIR%\features\org.talend.rcp.branding.%PRODUCTNAME%\%PRODUCTNAME%license.txt
//
// You should have received a copy of the agreement
// along with this program; if not, write to Talend SA
// 9 rue Pages 92150 Suresnes, France
//
// ============================================================================
package org.talend.help.perl.model;

import org.talend.commons.ui.runtime.image.IImage;
import org.talend.help.perl.Activator;

/**
 * DOC Administrator class global comment. Detailled comment <br/>
 * 
 * $Id: talend-code-templates.xml 1 2006-09-29 17:06:40 +0000 (������, 29 ���� 2006) nrousseau $
 * 
 */
public enum EImage implements IImage {

    DEFAULT_IMAGE,
    ENTER_BACKIMAGE("/icons/e_back2.gif"), //$NON-NLS-1$
    OUT_BACKIMAGE("/icons/e_back.gif"), //$NON-NLS-1$
    DISABLE_BACKIMAGE("/icons/back.gif"), //$NON-NLS-1$
    ENTER_FORWARDIMAGE("/icons/e_forward2.gif"), //$NON-NLS-1$
    OUT_FORWARDIMAGE("/icons/e_forward.gif"), //$NON-NLS-1$
    DISABLE_FORWARDIMAGE("/icons/forward.gif"), //$NON-NLS-1$

    WARNING_ICON("/icons/warning.gif"); //$NON-NLS-1$

    private String path;

    EImage() {
        this.path = "/icons/unknow.gif"; //$NON-NLS-1$
    }

    EImage(String path) {
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
        return Activator.class;
    }

}
