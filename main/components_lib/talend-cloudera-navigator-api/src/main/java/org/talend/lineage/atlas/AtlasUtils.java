package org.talend.lineage.atlas;

import org.apache.atlas.AtlasClient;
import org.apache.atlas.AtlasServiceException;
import org.apache.atlas.typesystem.*;
import org.apache.atlas.typesystem.json.InstanceSerialization;
import org.apache.atlas.typesystem.json.TypesSerialization;
import org.apache.atlas.typesystem.persistence.Id;
import org.apache.commons.collections.CollectionUtils;
import org.codehaus.jettison.json.JSONObject;

import java.util.Collection;


/**
 * This is an util class to encapsulate all the communication with Atlas.
 * It enhances the AtlasClient as well as provides some missing util methods.
 * Some of these methods are replacements for existing methods in more recent
 * versions, or may end up going upstream (to the Apache Atlas project).
 */
public class AtlasUtils {

    private static final org.apache.log4j.Logger LOG = org.apache.log4j.Logger.getLogger(AtlasUtils.class);

    private final AtlasClient client;

    public AtlasUtils(final String url, final String login, final String password) {
        client = new AtlasClient(url);
    }

    public Id persistInstance(final Referenceable ref) throws Exception {
        String entityJSON = InstanceSerialization.toJson(ref, true);
        JSONObject jsonObject = client.createEntity(entityJSON);
        String guid = jsonObject.getString(AtlasClient.GUID);
        return new Id(guid, ref.getId().getVersion(), ref.getTypeName());
    }

    /**
     * This method is just a wrapper to log the invocation of AtlasUtil.createInstance
     * It only exists for debuggin purposes and can be replaced with the direct call
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
     *
     * @param typesDef object to persit
     * @return the Id corresponding to the created types
     */
    public JSONObject persistTypes(final TypesDef typesDef) {
        String typeAsJson = TypesSerialization.toJson(typesDef);
        try {
            return this.client.createType(typeAsJson);
        } catch (AtlasServiceException e) {
            LOG.error("Error on persistTypes:", e);
            throw new RuntimeException(e);
        }
    }

    /**
     *
     * @param the collection of type names we want to check if they exist in Atlas
     * @return the collection of types that don't exist in Atlas
     */
    public Collection<String> getMissingTypes(final Collection<String> types) {
        try {
            Collection<String> allTypes = this.client.listTypes();
            return CollectionUtils.subtract(types, allTypes);
        } catch (AtlasServiceException e) {
            LOG.error("Error on getMissingTypes:", e);
            throw new RuntimeException(e);
        }
    }
}
