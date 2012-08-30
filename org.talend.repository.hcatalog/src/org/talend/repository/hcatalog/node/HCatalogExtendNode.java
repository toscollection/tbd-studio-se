package org.talend.repository.hcatalog.node;

import org.talend.commons.ui.runtime.image.IImage;
import org.talend.core.repository.IExtendRepositoryNode;
import org.talend.repository.hcatalog.util.EHCatalogImage;
import org.talend.repository.model.RepositoryNode;

/**
 * DOC ycbai class global comment. Detailled comment
 */
public class HCatalogExtendNode implements IExtendRepositoryNode {

    @Override
    public IImage getNodeImage() {
        return EHCatalogImage.HCATALOG_RESOURCE_ICON;
    }

    @Override
    public int getOrdinal() {
        return 51;
    }

    @Override
    public Object[] getChildren() {
        return new RepositoryNode[0];
    }

}
