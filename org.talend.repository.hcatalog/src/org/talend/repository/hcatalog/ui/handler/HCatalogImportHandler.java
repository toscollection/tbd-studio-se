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
package org.talend.repository.hcatalog.ui.handler;

import java.util.List;

import org.eclipse.core.runtime.IPath;
import org.eclipse.core.runtime.IProgressMonitor;
import org.talend.repository.items.importexport.handlers.imports.MetadataConnectionImportHandler;
import org.talend.repository.items.importexport.handlers.model.ItemRecord;
import org.talend.repository.items.importexport.manager.ResourcesManager;

/**
 * DOC ggu class global comment. Detailled comment
 */
public class HCatalogImportHandler extends MetadataConnectionImportHandler {

    /**
     * DOC ggu HCatalogImportHandler constructor comment.
     */
    public HCatalogImportHandler() {
        super();
    }

    /*
     * (non-Javadoc)
     * 
     * @see
     * org.talend.repository.items.importexport.handlers.imports.ImportExecutableBasePathHandler#calcItemRecord(org.eclipse.core.runtime
     * .IProgressMonitor, org.talend.repository.items.importexport.ui.wizard.imports.managers.ResourcesManager,
     * org.eclipse.core.runtime.IPath, boolean, java.util.List)
     */
    @Override
    public ItemRecord calcItemRecord(IProgressMonitor progressMonitor, ResourcesManager resManager, IPath resourcePath,
            boolean overwrite, List<ItemRecord> existeditems) {
        final ItemRecord itemRecord = super.calcItemRecord(progressMonitor, resManager, resourcePath, overwrite, existeditems);
        if (itemRecord != null) {
            // won't display in import dialog, will be implict to import by hadoop cluster(method
            // findRelatedItemRecord).
            itemRecord.setVisible(false);
        }
        return itemRecord;
    }

}
