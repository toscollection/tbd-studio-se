package org.talend.repository.hcatalog.action;

import org.eclipse.jface.wizard.IWizard;
import org.eclipse.ui.IWorkbench;
import org.talend.commons.ui.runtime.image.IImage;
import org.talend.core.model.repository.ERepositoryObjectType;
import org.talend.repository.hadoopcluster.action.common.CreateHadoopNodeAction;
import org.talend.repository.hcatalog.node.HCatalogRepositoryNodeType;
import org.talend.repository.hcatalog.ui.HCatalogWizard;
import org.talend.repository.hcatalog.util.EHCatalogImage;
import org.talend.repository.model.RepositoryNode;
import org.talend.repository.model.hcatalog.HCatalogConnectionItem;

/**
 * DOC ycbai class global comment. Detailled comment
 */
public class CreateHCatalogAction extends CreateHadoopNodeAction {

    @Override
    protected ERepositoryObjectType getNodeType() {
        return HCatalogRepositoryNodeType.HCATALOG;
    }

    @Override
    protected IImage getNodeImage() {
        return EHCatalogImage.HCATALOG_RESOURCE_ICON;
    }

    @Override
    protected IWizard getWizard(IWorkbench workbench, boolean isCreate, RepositoryNode node, String[] existingNames) {
        return new HCatalogWizard(workbench, isCreate, node, existingNames);
    }

    @Override
    public Class getClassForDoubleClick() {
        return HCatalogConnectionItem.class;
    }

}
