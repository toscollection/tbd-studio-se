package org.talend.repository.oozie.action;

import org.talend.core.model.repository.ERepositoryObjectType;
import org.talend.repository.hadoopcluster.action.common.EditHadoopNodePropertiesAction;
import org.talend.repository.oozie.node.model.OozieRepositoryNodeType;

public class EditOoziePropertiesAction extends EditHadoopNodePropertiesAction {

    @Override
    protected ERepositoryObjectType getNodeType() {
        return OozieRepositoryNodeType.OOZIE;
    }

}
