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
import org.talend.designer.codegen.additionaljet.AbstractJetFileProvider;

/**
 * DOC wyang class global comment. Detailled comment
 */
public class ThalesJetFileProvider extends AbstractJetFileProvider {

    private static Logger logger = Logger.getLogger(ThalesJetFileProvider.class);

    /**
     * DOC wyang ThalesJetFileProvider constructor comment.
     */
    public ThalesJetFileProvider() {
    }

    protected File getExternalFrameLocation() {
        URL url = FileLocator.find(Activator.getDefault().getBundle(), new Path("resources"), null); //$NON-NLS-1$
        try {
            URL fileUrl = FileLocator.toFileURL(url);
            return new File(fileUrl.getPath());
        } catch (Exception e) {
            logger.error(e);
        }
        return null;
    }

}
