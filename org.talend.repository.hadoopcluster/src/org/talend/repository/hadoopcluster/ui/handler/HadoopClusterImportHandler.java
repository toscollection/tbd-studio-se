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
package org.talend.repository.hadoopcluster.ui.handler;

import java.util.ArrayList;
import java.util.List;

import org.eclipse.core.runtime.IProgressMonitor;
import org.talend.core.GlobalServiceRegister;
import org.talend.core.hadoop.IHadoopClusterService;
import org.talend.core.model.properties.Item;
import org.talend.repository.items.importexport.handlers.imports.ImportRepTypeHandler;
import org.talend.repository.items.importexport.handlers.model.ItemRecord;
import org.talend.repository.items.importexport.manager.ResourcesManager;

/**
 * DOC ggu class global comment. Detailled comment
 */
public class HadoopClusterImportHandler extends ImportRepTypeHandler {

    public HadoopClusterImportHandler() {
        super();
    }

    /*
     * (non-Javadoc)
     * 
     * @see
     * org.talend.repository.items.importexport.handlers.imports.ImportRepTypeHandler#findRelatedItemRecord(org.eclipse
     * .core.runtime.IProgressMonitor,
     * org.talend.repository.items.importexport.ui.wizard.imports.managers.ResourcesManager,
     * org.talend.repository.items.importexport.ui.wizard.imports.models.ItemRecord,
     * org.talend.repository.items.importexport.ui.wizard.imports.models.ItemRecord[])
     */
    @Override
    public List<ItemRecord> findRelatedItemRecord(IProgressMonitor monitor, ResourcesManager resManager,
            ItemRecord selectedItemRecord, ItemRecord[] allImportItemRecords) {

        List<ItemRecord> relatedItemRecords = new ArrayList<ItemRecord>();
        relatedItemRecords.addAll(super.findRelatedItemRecord(monitor, resManager, selectedItemRecord, allImportItemRecords));

        if (GlobalServiceRegister.getDefault().isServiceRegistered(IHadoopClusterService.class)) {
            IHadoopClusterService hadoopClusterService = (IHadoopClusterService) GlobalServiceRegister.getDefault().getService(
                    IHadoopClusterService.class);
            final Item item = selectedItemRecord.getItem();
            if (hadoopClusterService != null && hadoopClusterService.isHadoopClusterItem(item)) {
                resolveItem(resManager, selectedItemRecord);

                List<String> subitemIds = hadoopClusterService.getSubitemIdsOfHadoopCluster(item);
                if (!subitemIds.isEmpty() && allImportItemRecords != null) {
                    for (ItemRecord ir : allImportItemRecords) {
                        if (ir.getProperty() != null && subitemIds.contains(ir.getProperty().getId())) {
                            relatedItemRecords.add(ir);
                        }
                    }
                }
            }
        }
        return relatedItemRecords;
    }

}
