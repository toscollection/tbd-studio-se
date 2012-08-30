package org.talend.repository.hcatalog.tester;

import org.talend.core.model.repository.ERepositoryObjectType;
import org.talend.repository.hcatalog.node.HCatalogRepositoryNodeType;
import org.talend.repository.metadata.tester.CoMetadataNodeTester;

public class HCatalogMetadataNodeTester extends CoMetadataNodeTester {

    private static final String IS_HCATALOG_CONNECTION = "isHCatalogConnection"; //$NON-NLS-1$

    protected ERepositoryObjectType findType(String property) {
        if (property != null) {
            if (IS_HCATALOG_CONNECTION.equals(property)) {
                return HCatalogRepositoryNodeType.HCATALOG;
            }
        }
        return null;
    }

}
