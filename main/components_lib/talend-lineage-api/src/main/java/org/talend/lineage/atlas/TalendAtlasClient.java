package org.talend.lineage.atlas;

import java.util.Collection;
import java.util.List;

import org.apache.atlas.AtlasClient;
import org.apache.atlas.AtlasServiceException;
import org.apache.atlas.typesystem.Referenceable;
import org.apache.atlas.typesystem.TypesDef;
import org.apache.atlas.typesystem.json.InstanceSerialization;
import org.apache.atlas.typesystem.json.TypesSerialization;
import org.apache.atlas.typesystem.persistence.Id;
import org.apache.commons.collections.CollectionUtils;

/**
 * This is a client access class for all the communication with Atlas. It encapsulates the original AtlasClient and
 * provides some missing util methods. Some of these methods are replacements for existing methods in more recent
 * versions, or may end up going upstream (to the Apache Atlas project).
 *
 * It exists to offer a stable foundation in case API changes in Apache Atlas
 */
final class TalendAtlasClient {

    private static final org.apache.log4j.Logger LOG = org.apache.log4j.Logger.getLogger(TalendAtlasClient.class);

    private final AtlasClient                    client;

    TalendAtlasClient(final String url, final String login, final String password) {
        client = new AtlasClient(new String[] { url }, new String[] { login, password });
    }

    /**
     *
     * @param referenceable
     * @return
     * @throws Exception
     */
    private Id persistInstance(final Referenceable referenceable) throws Exception {
        String typeName = referenceable.getTypeName();

        String entityJSON = InstanceSerialization.toJson(referenceable, true);
        System.out.println("Submitting new entity= " + entityJSON);
        List<String> guids = this.client.createEntity(entityJSON);
        System.out.println("created instance for type " + typeName + ", guid: " + guids);

        // return the Id for created instance with guid
        return new Id(guids.get(guids.size() - 1), referenceable.getId().getVersion(), referenceable.getTypeName());
    }

    /**
     * This method is just a wrapper to log the invocation of AtlasUtil.createInstance It only exists for debugging
     * purposes and can be replaced with the direct call
     *
     * @param ref
     * @return
     * @throws Exception
     */
    public Id persistInstanceWithLog(final Referenceable ref) throws Exception {
        String entityJSON = InstanceSerialization.toJson(ref, true);
        LOG.debug("Submitting new entity= " + entityJSON);
        Id idRef = persistInstance(ref);
        LOG.debug("created instance for type " + ref.getTypeName() + ", guid: " + idRef);
        return idRef;
    }

    /**
     * @param typesDef object to persist
     * @return the Id corresponding to the created types
     */
    public List<String> persistTypes(final TypesDef typesDef) throws AtlasServiceException {
        String typeAsJson = TypesSerialization.toJson(typesDef);
        return this.client.createType(typeAsJson);
    }

    /**
     * @param types collection of type names we want to check if they exist in Atlas
     * @return the collection of types that don't exist in Atlas
     */
    public Collection<String> getMissingTypes(final Collection<String> types) throws AtlasServiceException {
        Collection<String> allTypes = this.client.listTypes();
        return CollectionUtils.subtract(types, allTypes);
    }

}