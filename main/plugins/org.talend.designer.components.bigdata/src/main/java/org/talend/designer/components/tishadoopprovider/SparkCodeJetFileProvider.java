// ============================================================================
//
// Copyright (C) 2006-2014 Talend Inc. - www.talend.com
//
// This source code is available under agreement available at
// %InstallDIR%\features\org.talend.rcp.branding.%PRODUCTNAME%\%PRODUCTNAME%license.txt
//
// You should have received a copy of the agreement
// along with this program; if not, write to Talend SA
// 9 rue Pages 92150 Suresnes, France
//
// ============================================================================
package org.talend.designer.components.tishadoopprovider;

import java.io.File;
import java.net.URL;

import org.apache.log4j.Logger;
import org.eclipse.core.runtime.FileLocator;
import org.eclipse.core.runtime.Path;
import org.talend.designer.codegen.additionaljet.AbstractJetFileProvider;

/**
 * @author rdubois
 * 
 */
public class SparkCodeJetFileProvider extends AbstractJetFileProvider {

    private static Logger logger = Logger.getLogger(SparkCodeJetFileProvider.class);

    @Override
    protected File getExternalFrameLocation() {
        try {
            URL url = FileLocator.find(TisHadoopComponentProviderActivator.getDefault().getBundle(),
                    new Path("resources/spark"), null); //$NON-NLS-1$

            URL fileUrl = FileLocator.toFileURL(url);
            return new File(fileUrl.getPath());

        } catch (Exception e) {
            logger.error(e);
        }
        return null;
    }

}
