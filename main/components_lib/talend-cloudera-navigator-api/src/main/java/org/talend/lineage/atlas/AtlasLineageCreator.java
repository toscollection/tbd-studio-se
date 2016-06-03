package org.talend.lineage.atlas;

import org.apache.atlas.AtlasServiceException;
import org.apache.atlas.typesystem.Referenceable;
import org.apache.atlas.typesystem.TypesDef;
import org.apache.atlas.typesystem.persistence.Id;
import org.apache.commons.lang3.tuple.Pair;
import org.codehaus.jettison.json.JSONObject;
import org.talend.lineage.ILineageCreator;

import javax.annotation.Nullable;
import java.util.*;

public class AtlasLineageCreator implements ILineageCreator {
    private static final org.apache.log4j.Logger LOG =
            org.apache.log4j.Logger.getLogger(AtlasLineageCreator.class);

    @Nullable private Referenceable jobRef;
    private List<Referenceable> refs = new ArrayList<>();

    // only for testing / debugging purposes
    // the get re-initialized on each invocation of sendToLineageProvider
    private Map<String, Pair<Referenceable, Id>> persistedComponents;
    private Map<String, Pair<Referenceable, Id>> persistedArtificialComponents;
    private Map<String, Pair<Referenceable, Id>> persistedJobs;

    private final TalendAtlasClient client;

    /**
     *
     * @param url
     * @param login
     * @param password
     */
    public AtlasLineageCreator(final String url, final String login, final String password) {
        this.client = new TalendAtlasClient(url, login, password);
    }

    /**
     *
     * @param name
     * @param description
     * @param purpose
     * @param author
     * @param version
     * @param jobType
     * @param framework
     * @param creationDate
     * @param lastModificationDate
     * @param status
     * @param startTime
     * @param endTime
     */
    protected void addJobInfo(final String name, final String description, final String purpose,
                              final String author, final String version, final String jobType,
                              final String framework, final long creationDate,
                              final long lastModificationDate, final String status,
                              final long startTime, final long endTime) {
        Map<String, Object> values = new HashMap<>();
        values.put("name", name);
        values.put("description", description);
        values.put("purpose", purpose);
        values.put("author", author);
        values.put("version", version);
        values.put("jobType", jobType);
        values.put("framework", framework);
        values.put("creationDate", creationDate);
        values.put("lastModificationDate", lastModificationDate);
        values.put("status", status);
        values.put("startTime", startTime);
        values.put("endTime", endTime);
        // Notice that the job components are not put in creation, since we don't have
        // the ids of the components yet, it is sendToLineageProvider who does it
        this.jobRef = TalendModelFactory.buildTalendJob(values);
    }

    @Override
    public void addNodeToLineage(final String name, final Map<String, String> schema,
                                 final Map<String, Object> metadata, final List<String> inputNodes,
                                 final List<String> outputNodes) {
        Referenceable ref = TalendModelFactory.buildTalendComponent(name, schema,
                metadata, inputNodes, outputNodes);
        this.refs.add(ref);
    }

    @Override
    public void sendToLineageProvider(final Boolean dieOnError) {
        try {
            // First we persist the instances
            Map<String, Pair<Referenceable, Id>> persistedArtificialInstances =
                    persistWithArtificialComponents(this.refs);
            // then with the ids we persist the job info
            if (jobRef != null) {
                List<Id> instancesIds = new ArrayList<>();
                // Notice that we don't add the artificial instances to avoid
                // type + visualization problems
                for (Pair<Referenceable, Id> pair : this.persistedComponents.values()) {
                    instancesIds.add(pair.getRight());
                }
                jobRef.set("components", instancesIds);

                Id idJobRef = client.persistInstanceWithLog(jobRef);
                String jobName = (String) jobRef.get("name");
                this.persistedJobs = new HashMap<>();
                this.persistedJobs.put(jobName, Pair.of(jobRef, idJobRef));

                // we create an artificial instance to link the job with the components
                Referenceable artificialRef =
                        TalendModelFactory.buildTalendArtificialComponent(jobName,
                                Arrays.asList(idJobRef), instancesIds);
                Id idArtificialRef = client.persistInstanceWithLog(artificialRef);
            }
        } catch (Throwable e) {
            if (dieOnError) {
                throw new RuntimeException(e);
            } else {
                LOG.error(e);
            }
        }
    }

    /**
     * Persists the refs and with the obtained Ids it creates:
     * <ul>
     *     <li>the corresponding types for the Talend Model (if they don't exist)</li>
     *     <li>the artificial components to connect the refs (they are persisted too)</li>
     * </ul>
     * @param refs
     * @return the instance corresponding to the artificial nodes
     */
    private Map<String, Pair<Referenceable, Id>>
    persistWithArtificialComponents(final List<Referenceable> refs) throws Exception {
        findOrCreateTypes(TalendModelFactory.TALEND_CLASS_TYPES);

        Map<String, Pair<Referenceable, Id>> persistedInstances = persist(refs);
        // Now we create the artificial components to connect the references (edges)
        List<Referenceable> artificialRefs =
                TalendModelFactory.buildTalendArtificialComponents(persistedInstances);
        // We persist the artificial processes
        Map<String, Pair<Referenceable, Id>> persistedArtificialInstances = persist(artificialRefs);
        // This is done for debugging/test purposes
        this.persistedComponents = persistedInstances;
        this.persistedArtificialComponents = persistedArtificialInstances;
        return persistedArtificialInstances;
    }

    /**
     * Persists the refs in Atlas, it assumes that the refs have different names (identity)
     * @param refs
     * @return
     */
    private Map<String, Pair<Referenceable, Id>> persist(final List<Referenceable> refs)
            throws Exception {
        Map<String, Pair<Referenceable, Id>> persistedEntities = new HashMap<>();
        for (final Referenceable ref : refs) {
            // We persisted with the modified Ref if it was modified
            Id idRef = client.persistInstanceWithLog(ref);
            String name = ref.getValuesMap().get("name").toString();
            persistedEntities.put(name, Pair.of(ref, idRef));
        }
        return persistedEntities;
    }

    /**
     * Creates the type definitions or ensure that the default types exist
     * @param types
     */
    private void findOrCreateTypes(final Collection<String> types) throws AtlasServiceException {
        Collection<String> missingTypes = client.getMissingTypes(types);
        if (!missingTypes.isEmpty()) {
            // notice that we force the creation of the types if they don't exist
            TypesDef typesDef = TalendModelFactory.buildTypesDef(missingTypes);
            JSONObject typesIds = client.persistTypes(typesDef);
        }
    }

    // Methods below are only for debugging / unit testing purposes

    protected void logPersistedEntities() {
        logPersistedEntities("persistedJobs:", this.persistedJobs);
        logPersistedEntities("persistedEntities:", this.persistedComponents);
        logPersistedEntities("persistedArtificialEntities:", this.persistedArtificialComponents);
    }

    private void logPersistedEntities(final String msg,
                                      @Nullable final Map<String, Pair<Referenceable, Id>> entries) {
        LOG.debug(msg);
        if (entries != null) {
            for (Map.Entry<String, Pair<Referenceable, Id>> entry : entries.entrySet()) {
                Referenceable ref = entry.getValue().getLeft();
                Id id = entry.getValue().getRight();
                String name = (String) ref.get("name");
                String typeName = ref.getTypeName();
                LOG.debug(name + " [" + typeName + "] : " + id._getId());
                if ("tArtificialComponent".equals(typeName)) {
                    List<String> inputs = (List<String>) ref.get("inputs");
                    List<String> outputs = (List<String>) ref.get("outputs");
                    LOG.debug(name + " inputs  : ["+ inputs + "]");
                    LOG.debug(name + " outputs : ["+ outputs + "]");
                }
            }
        }
    }

    protected List<Referenceable> getRefs() {
        return refs;
    }

    @Nullable
    protected Referenceable getJobRef() {
        return jobRef;
    }

    protected Map<String, Pair<Referenceable, Id>> getPersistedComponents() {
        return persistedComponents;
    }

    protected Map<String, Pair<Referenceable, Id>> getPersistedArtificialComponents() {
        return persistedArtificialComponents;
    }

    protected Map<String, Pair<Referenceable, Id>> getPersistedJobs() {
        return persistedJobs;
    }
}
