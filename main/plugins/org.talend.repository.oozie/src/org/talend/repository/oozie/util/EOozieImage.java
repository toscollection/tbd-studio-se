package org.talend.repository.oozie.util;

import org.talend.commons.ui.runtime.image.IImage;

public enum EOozieImage implements IImage {

    OOZIE_RESOURCE_ICON("/icons/Oozie_icon16.png"), //$NON-NLS-1$
    OOZIE_WIZ("/icons/hadoop-logo-wiz.png"); //$NON-NLS-1$

    private String path;

    EOozieImage() {
        this.path = "/icons/unknown.gif"; //$NON-NLS-1$
    }

    EOozieImage(String path) {
        this.path = path;
    }

    /**
     * Getter for path.
     * 
     * @return the path
     */
    @Override
    public String getPath() {
        return this.path;
    }

    /**
     * Getter for clazz.
     * 
     * @return the clazz
     */
    @Override
    public Class getLocation() {
        return EOozieImage.class;
    }
}
