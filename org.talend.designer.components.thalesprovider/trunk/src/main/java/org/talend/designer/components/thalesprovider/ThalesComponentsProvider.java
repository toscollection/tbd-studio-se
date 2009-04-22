// ============================================================================
//
// Copyright (C) 2006-2008 Talend Inc. - www.talend.com
//
// This source code is available under agreement available at
// %InstallDIR%\features\org.talend.rcp.branding.%PRODUCTNAME%\%PRODUCTNAME%license.txt
//
// You should have received a copy of the agreement
// along with this program; if not, write to Talend SA
// 9 rue Pages 92150 Suresnes, France
//
// ============================================================================
package org.talend.designer.components.thalesprovider;

import java.io.File;
import java.net.URL;

import org.apache.log4j.Logger;
import org.eclipse.core.runtime.FileLocator;
import org.eclipse.core.runtime.Path;
import org.talend.core.model.components.AbstractComponentsProvider;

/**
 * DOC wyang class global comment. Detailled comment
 */
public class ThalesComponentsProvider extends AbstractComponentsProvider {

    private static Logger logger = Logger.getLogger(ThalesComponentsProvider.class);

    /**
     * DOC mhirt TisMpComponentsProvider constructor comment.
     */
    public ThalesComponentsProvider() {
    }

    protected File getExternalComponentsLocation() {
        URL url = FileLocator.find(Activator.getDefault().getBundle(), new Path("components"), null); //$NON-NLS-1$
        URL fileUrl;
        try {
            fileUrl = FileLocator.toFileURL(url);
            return new File(fileUrl.getPath());
        } catch (Exception e) {
            logger.error(e);
            e.printStackTrace();
        }
        return null;
    }

    /*
     * (non-Javadoc)
     * 
     * @see org.talend.core.model.components.AbstractComponentsProvider#getFamilyTranslation(java.lang.String)
     */
    @Override
    public String getFamilyTranslation(String text) {
        // TODO to implement when TIS provider got translations
        return null;
    }
}
