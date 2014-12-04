package org.talend.repository.oozie.update;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.talend.core.model.properties.ConnectionItem;
import org.talend.core.model.relationship.Relation;
import org.talend.core.model.relationship.RelationshipItemBuilder;
import org.talend.core.model.update.EUpdateItemType;
import org.talend.core.model.update.RepositoryUpdateManager;

/**
 * DOC plv class global comment. Detailled comment
 */
public class OozieUpdateManager {

    /**
     * DOC plv Comment method "updateHDFSConnection".
     * 
     * @param connection
     * @return
     */
    public static boolean updateOozieConnection(ConnectionItem connection) {
        return updateOozieConnection(connection, true, false);
    }

    /**
     * DOC plv Comment method "updateHDFSConnection".
     * 
     * @param connectionItem
     * @param show
     * @param onlySimpleShow
     * @return
     */
    public static boolean updateOozieConnection(ConnectionItem connectionItem, boolean show, final boolean onlySimpleShow) {
        List<Relation> relations = RelationshipItemBuilder.getInstance().getItemsRelatedTo(connectionItem.getProperty().getId(),
                RelationshipItemBuilder.LATEST_VERSION, RelationshipItemBuilder.PROPERTY_RELATION);

        RepositoryUpdateManager repositoryUpdateManager = new RepositoryUpdateManager(connectionItem, relations) {

            @Override
            public Set<EUpdateItemType> getTypes() {
                Set<EUpdateItemType> types = new HashSet<EUpdateItemType>();
                types.add(EUpdateItemType.NODE_PROPERTY);

                return types;
            }

        };
        return repositoryUpdateManager.doWork(true, false);
    }

}
