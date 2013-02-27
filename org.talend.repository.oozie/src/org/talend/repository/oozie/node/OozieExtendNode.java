package org.talend.repository.oozie.node;

import org.talend.commons.ui.runtime.image.IImage;
import org.talend.core.repository.IExtendRepositoryNode;
import org.talend.repository.model.RepositoryNode;
import org.talend.repository.oozie.util.EOozieImage;

public class OozieExtendNode implements IExtendRepositoryNode {

    @Override
    public IImage getNodeImage() {
        return EOozieImage.OOZIE_RESOURCE_ICON;
    }

    @Override
    public int getOrdinal() {
        return 52;
    }

    @Override
    public Object[] getChildren() {
        return new RepositoryNode[0];
    }

}
