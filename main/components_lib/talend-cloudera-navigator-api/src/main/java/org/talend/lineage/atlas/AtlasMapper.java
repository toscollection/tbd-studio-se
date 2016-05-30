package org.talend.lineage.atlas;

import com.google.common.collect.ImmutableMap;
import org.apache.atlas.AtlasException;
import org.apache.atlas.typesystem.Referenceable;
import org.apache.atlas.typesystem.TypesDef;
import org.apache.atlas.typesystem.json.InstanceSerialization;
import org.apache.atlas.typesystem.persistence.Id;
import org.apache.commons.lang3.tuple.Triple;
import org.codehaus.jettison.json.JSONObject;
import org.talend.cloudera.navigator.api.NavigatorNode;

import java.util.*;


/**
 * Class to map the Talend DAG into into the Atlas DAG
 * It defines the model used in the translation e.g. types / references
 *
 * Dev Note: There is a clear semantic difference between the buildMethods to
 * construct the Atlas Models in memory and the persist methods who deal with
 * persistence in the Atlas service
 */
public class AtlasMapper {

    private static final org.apache.log4j.Logger LOG = org.apache.log4j.Logger.getLogger(AtlasMapper.class);

    private StringBuilder debugStringBuilder = new StringBuilder();
    private AtlasUtils atlasUtils;

    public AtlasMapper(final String url, final String login, final String password) {
        this.atlasUtils = new AtlasUtils(url, login, password);
    }

    public void changeEndpoint(final String url, final String login, final String password) {
        this.atlasUtils = new AtlasUtils(url, login, password);
    }

    /**
     * TODO this function must return the appropriate type of the objects that are going to be persisted
     * TODO remove jobId ? do woe need a meta entity to contain the others ?
     * Note that jobId, tags and other information must come in the metadata
     * Note also that atlas does not offer a bulk create method as cloudera does, so this map in reality deals
     * with persistence too
     * @param navigatorNodes
     * @param jobId not used, please passe job info in the metadata
     */
    public void map(final List<NavigatorNode> navigatorNodes, final String jobId) {
        // Create the type definitions or ensure that the default types do exist
        //TODO method to create or get the ids of the typesDef
        //TODO we need to delete the types too ?
        Collection<String> missingTypes = atlasUtils.getMissingTypes(TalendModelFactory.MODEL_TYPES);
        if (!missingTypes.isEmpty()) {
            TypesDef typesDef = TalendModelFactory.buildTypesDefinitions();
            JSONObject typesIds = atlasUtils.persistTypes(typesDef);
        }

        // create and persist each node as a Table (vertex)
        Referenceable dbRef = TalendModelFactory.buildDatabase("Sales", "Sales Database", "John ETL", "hdfs://host:8000/apps/warehouse/sales");
        Id dbId = null;
        try {
            dbId = atlasUtils.persistInstanceWithLog(dbRef);
        } catch (Exception e) {
            e.printStackTrace();
        }
        Referenceable sd =
                TalendModelFactory.buildRawStorageDescriptor("hdfs://host:8000/apps/warehouse/sales",
                        "TextInputFormat", "TextOutputFormat", true);

        List<Referenceable> refs = TalendModelFactory.buildTables(navigatorNodes, dbId, sd);
        Map<String, Triple<Referenceable, Id, Map<String, Object>>> persistedEntities = createInstances(refs);

        // Now we create the artificial Processes to connect the references (edges)
        List<Referenceable> artificialRefs = TalendModelFactory.buildProcesses(persistedEntities);
        // We persist the artificial processes
        Map<String, Triple<Referenceable, Id, Map<String, Object>>> persistedArtificialEntities = persistInstances(artificialRefs);
        logPersistedEntities("persistedEntities:", persistedEntities);
        logPersistedEntities("persistedArtificialEntities:", persistedArtificialEntities);
    }

    public Map<String, Triple<Referenceable, Id, Map<String, Object>>>
    createInstances(Collection<Referenceable> refs) {
        Map<String, Triple<Referenceable, Id, Map<String, Object>>> persistedEntities = new HashMap<>();
        for (Referenceable ref : refs) {
            try {
                // Notice the use of synchronized to have a exact in time copy of the value
                Map<String, Object> copyOfValues = Collections.synchronizedMap(ref.getValuesMap());
                LOG.debug(copyOfValues);

                Map<String, Object> immutableCopyOfValues = ImmutableMap.copyOf(ref.getValuesMap());
                LOG.debug(immutableCopyOfValues);

//                ref.set("columns", ImmutableList.of());
                ref.set("inputs", null);
                ref.set("outputs", null);

                //TODO Finish the refactor of this method (this should be peristInstance)
                Id idRef = atlasUtils.persistInstanceWithLog(ref);
                String name = ref.getValuesMap().get("name").toString();
                persistedEntities.put(name, Triple.of(ref, idRef, immutableCopyOfValues));
                LOG.debug(InstanceSerialization.toJson(ref, true));
            } catch (AtlasException e) {
                LOG.error("Error createInstances:", e);
            } catch (Throwable e) {
                LOG.error("Error createInstances:", e);
            }
        }
        return persistedEntities;
    }

    public Map<String, Triple<Referenceable, Id, Map<String, Object>>>
    persistInstances(final Collection<Referenceable> refs) {
        Map<String, Triple<Referenceable, Id, Map<String, Object>>> persistedEntities = new HashMap<>();
        for (Referenceable ref : refs) {
            try {
                Id idRef = atlasUtils.persistInstanceWithLog(ref);
                String name = ref.getValuesMap().get("name").toString();
                persistedEntities.put(name, Triple.of(ref, idRef, ref.getValuesMap()));
            } catch (AtlasException e) {
                LOG.error("Error persistInstances:", e);
            } catch (Throwable e) {
                LOG.error("Error persistInstances:", e);
            }
        }
        return persistedEntities;
    }

    private void logPersistedEntities(final String msg,
                                      final Map<String, Triple<Referenceable, Id, Map<String, Object>>> entries) {
        LOG.debug(msg);
        for (Map.Entry<String, Triple<Referenceable, Id, Map<String, Object>>> entry : entries.entrySet()) {
            Referenceable ref = entry.getValue().getLeft();
            Id id = entry.getValue().getMiddle();
//            Map<String, Object> originalValues = entry.getValue().getRight();
            LOG.debug(ref.get("name") + " [" + ref.getTypeName() + "] : " + id._getId());
        }
    }

}
