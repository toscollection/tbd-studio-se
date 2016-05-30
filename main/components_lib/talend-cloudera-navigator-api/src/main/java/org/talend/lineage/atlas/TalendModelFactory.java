package org.talend.lineage.atlas;

import com.google.common.collect.ImmutableList;
import org.apache.atlas.typesystem.Referenceable;
import org.apache.atlas.typesystem.TypesDef;
import org.apache.atlas.typesystem.persistence.Id;
import org.apache.atlas.typesystem.types.*;
import org.apache.atlas.typesystem.types.utils.TypesUtil;
import org.apache.commons.lang3.tuple.Triple;
import org.talend.cloudera.navigator.api.NavigatorNode;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Map;

/**
 * This class contains a set of static methods to represent the Talend data model in Atlas
 * Notice that the goal of this class is only to build the objects to represent the model
 * It does not deal with persistence
 */
final class TalendModelFactory {

    private static final org.apache.log4j.Logger LOG = org.apache.log4j.Logger.getLogger(TalendModelFactory.class);

    private static final String DATABASE_TYPE = "DB";
    private static final String COLUMN_TYPE = "Column";
    private static final String TABLE_TYPE = "Table";
    private static final String VIEW_TYPE = "View";
    private static final String LOAD_PROCESS_TYPE = "LoadProcess";
    private static final String STORAGE_DESC_TYPE = "StorageDesc";

    static final Collection<String> MODEL_TYPES = ImmutableList.of(COLUMN_TYPE, TABLE_TYPE, LOAD_PROCESS_TYPE);

    private TalendModelFactory() {

    }

    /**
     *
     * @param schema is a Map where each entry contains the name and the type each column
     * @return The column as a Referenceable
     */
    static List<Referenceable> buildColumns(final Map<String, String> schema) {
        List<Referenceable> refs = new ArrayList<>();
        for(Map.Entry<String, String> entry : schema.entrySet()) {
            Referenceable ref = new Referenceable(COLUMN_TYPE);
            ref.set("name", entry.getKey());
            // In atlas 0.5 there is a bug and the dataType does not show correctly
            ref.set("dataType", entry.getValue());
            ref.set("comment", entry.getValue());
            refs.add(ref);
        }
        return refs;
    }

    static Referenceable buildRawStorageDescriptor(final String location, final String inputFormat,
                                                   final String outputFormat, final boolean compressed) {
        Referenceable ref = new Referenceable(STORAGE_DESC_TYPE);
        ref.set("location", location);
        ref.set("inputFormat", inputFormat);
        ref.set("outputFormat", outputFormat);
        ref.set("compressed", compressed);
        return ref;
    }

    static Referenceable buildDatabase(final String name, final String description,
                                       final String owner, final String locationUri,
                                       final String... traitNames) {
        Referenceable ref = new Referenceable(DATABASE_TYPE, traitNames);
        ref.set("name", name);
        ref.set("description", description);
        ref.set("owner", owner);
        ref.set("locationUri", locationUri);
        ref.set("createTime", System.currentTimeMillis());
        return ref;
    }

    static Referenceable buildTable(final NavigatorNode navigatorNode, final Id dbId, final Referenceable sd) {
        // FIXME if the studio wants to pass trait types it must pass them in each node
//        String[] traits = {"Dimension", "ETL"};
        Referenceable ref = new Referenceable(TABLE_TYPE);

        // We map first the generic metadata, if the node uses a reserved key,
        // it can be overwritten
        Map<String, String> metadata = navigatorNode.getMetadata();
        for(Map.Entry<String, String> meta : metadata.entrySet()) {
            ref.set(meta.getKey(), meta.getValue());
        }

        // We map then the mandatory entities
        ref.set("name", navigatorNode.getName());
        ref.set("description", navigatorNode.getName() + System.currentTimeMillis());
        ref.set("inputs", navigatorNode.getInputNodes());
        ref.set("outputs", navigatorNode.getOutputNodes());
        //TODO Reduce the model to include only the things that come from the studio
        ref.set("db", dbId);
        ref.set("sd", sd);

        Map<String, String> schema = navigatorNode.getSchema();
        List<Referenceable> columns = buildColumns(schema);
        ref.set("columns", columns);

        return ref;
    }

    /**
     * This creates the artificial processes to connect the graph (edges)
     * @param persistedEntities
     * @return
     */
    static List<Referenceable> buildProcesses(
            final Map<String, Triple<Referenceable, Id, Map<String, Object>>> persistedEntities) {
        List<Referenceable> artificialRefs = new ArrayList<>();
        for (Map.Entry<String, Triple<Referenceable, Id, Map<String, Object>>> entry : persistedEntities.entrySet()) {
            Referenceable ref = entry.getValue().getLeft();
            Id id = entry.getValue().getMiddle();
            Map<String, Object> originalValues = entry.getValue().getRight();

            List<String> outputs = (List) originalValues.get("outputs");
            if (outputs != null && !outputs.isEmpty()) {
                List<Id> outputsIds = getIdsFromNodeNames(persistedEntities, outputs);
                List<String> traitsList = ref.getTraits();
                String [] traits = traitsList.toArray(new String[traitsList.size()]);

                // We build the artificial processes that connect the nodes
                Referenceable artificialRef = buildReferenceable("LoadProcess", ref.getValuesMap(), traits);
                String artificialName = artificialRef.get("name").toString();
                artificialRef.set("name", artificialName);
                artificialRef.set("description", artificialRef.get("name") + "_process");
                // We delete the columns to make the request smaller, this is not mandatory
                artificialRef.set("columns", ImmutableList.of());
                //TODO Check what happens on many inputs
                artificialRef.set("inputs", ImmutableList.of(id));
                artificialRef.set("outputs", outputsIds);
                artificialRefs.add(artificialRef);
            }
        }
        return artificialRefs;
    }

    /**
     * THis is a generic method to build references
     * @param refType
     * @param values
     * @param traitTypes
     * @return
     */
    static Referenceable buildReferenceable(final String refType, final Map<String, Object> values,
                                            final String... traitTypes) {
        Referenceable ref = new Referenceable(refType, traitTypes);
        for(Map.Entry<String, Object> entry : values.entrySet()) {
            ref.set(entry.getKey(), entry.getValue());
        }
        return ref;
    }

    /**
     *
     * @return The type definition for the model
     */
    static TypesDef buildTypesDefinitions() {
        HierarchicalTypeDefinition<TraitType> dimTraitDef = TypesUtil.createTraitTypeDef("Dimension", null);
        HierarchicalTypeDefinition<TraitType> factTraitDef = TypesUtil.createTraitTypeDef("Fact", null);
        HierarchicalTypeDefinition<TraitType> piiTraitDef = TypesUtil.createTraitTypeDef("PII", null);
        HierarchicalTypeDefinition<TraitType> metricTraitDef = TypesUtil.createTraitTypeDef("Metric", null);
        HierarchicalTypeDefinition<TraitType> etlTraitDef = TypesUtil.createTraitTypeDef("ETL", null);
        HierarchicalTypeDefinition<TraitType> jdbcTraitDef = TypesUtil.createTraitTypeDef("JdbcAccess", null);

        HierarchicalTypeDefinition<ClassType> dbClsDef = TypesUtil
                .createClassTypeDef(DATABASE_TYPE, null,
                        TypesUtil.createUniqueRequiredAttrDef("name", DataTypes.STRING_TYPE),
                        TypesUtil.createOptionalAttrDef("description", DataTypes.STRING_TYPE),
                        TypesUtil.createOptionalAttrDef("locationUri", DataTypes.STRING_TYPE),
                        TypesUtil.createOptionalAttrDef("owner", DataTypes.STRING_TYPE),
                        TypesUtil.createOptionalAttrDef("createTime", DataTypes.LONG_TYPE));
        HierarchicalTypeDefinition<ClassType> storageDescClsDef = TypesUtil
                .createClassTypeDef(STORAGE_DESC_TYPE, null,
                        TypesUtil.createOptionalAttrDef("location", DataTypes.STRING_TYPE),
                        TypesUtil.createOptionalAttrDef("inputFormat", DataTypes.STRING_TYPE),
                        TypesUtil.createOptionalAttrDef("outputFormat", DataTypes.STRING_TYPE),
                        TypesUtil.createRequiredAttrDef("compressed", DataTypes.STRING_TYPE)
                );
        HierarchicalTypeDefinition<ClassType> columnClsDef = TypesUtil
                .createClassTypeDef(COLUMN_TYPE, null,
                        TypesUtil.createOptionalAttrDef("name", DataTypes.STRING_TYPE),
                        TypesUtil.createOptionalAttrDef("dataType", DataTypes.STRING_TYPE),
                        TypesUtil.createOptionalAttrDef("comment", DataTypes.STRING_TYPE)
                );
        HierarchicalTypeDefinition<ClassType> tblClsDef = TypesUtil
                .createClassTypeDef(TABLE_TYPE, ImmutableList.of("DataSet"),
                        new AttributeDefinition("db", DATABASE_TYPE, Multiplicity.REQUIRED, false, null),
                        new AttributeDefinition("sd", STORAGE_DESC_TYPE, Multiplicity.REQUIRED, true, null),
                        TypesUtil.createOptionalAttrDef("owner", DataTypes.STRING_TYPE),
                        TypesUtil.createOptionalAttrDef("createTime", DataTypes.LONG_TYPE),
                        TypesUtil.createOptionalAttrDef("lastAccessTime", DataTypes.LONG_TYPE),
                        TypesUtil.createOptionalAttrDef("retention", DataTypes.LONG_TYPE),
                        TypesUtil.createOptionalAttrDef("viewOriginalText", DataTypes.STRING_TYPE),
                        TypesUtil.createOptionalAttrDef("viewExpandedText", DataTypes.STRING_TYPE),
                        TypesUtil.createOptionalAttrDef("tableType", DataTypes.STRING_TYPE),
                        TypesUtil.createOptionalAttrDef("temporary", DataTypes.BOOLEAN_TYPE),
                        new AttributeDefinition("columns", DataTypes.arrayTypeName(COLUMN_TYPE),
                                Multiplicity.COLLECTION, true, null));
        HierarchicalTypeDefinition<ClassType> loadProcessClsDef = TypesUtil
                .createClassTypeDef(LOAD_PROCESS_TYPE, ImmutableList.of("Process"),
                        TypesUtil.createOptionalAttrDef("userName", DataTypes.STRING_TYPE),
                        TypesUtil.createOptionalAttrDef("startTime", DataTypes.LONG_TYPE),
                        TypesUtil.createOptionalAttrDef("endTime", DataTypes.LONG_TYPE),
                        TypesUtil.createRequiredAttrDef("queryText", DataTypes.STRING_TYPE),
                        TypesUtil.createRequiredAttrDef("queryPlan", DataTypes.STRING_TYPE),
                        TypesUtil.createRequiredAttrDef("queryId", DataTypes.STRING_TYPE),
                        TypesUtil.createRequiredAttrDef("queryGraph", DataTypes.STRING_TYPE),
                        new AttributeDefinition("columns", DataTypes.arrayTypeName(COLUMN_TYPE),
                                Multiplicity.COLLECTION, true, null)
                );
        HierarchicalTypeDefinition<ClassType> viewClsDef = TypesUtil
                .createClassTypeDef(VIEW_TYPE, null,
                        TypesUtil.createOptionalAttrDef("name", DataTypes.STRING_TYPE),
                        TypesUtil.createRequiredAttrDef("db", DATABASE_TYPE),
                        new AttributeDefinition("inputTables", DataTypes.arrayTypeName(TABLE_TYPE),
                                Multiplicity.COLLECTION, false, null));

        return TypeUtils.getTypesDef(ImmutableList.<EnumTypeDefinition>of(), ImmutableList.<StructTypeDefinition>of(),
                ImmutableList.of(dimTraitDef, factTraitDef, piiTraitDef, metricTraitDef, etlTraitDef, jdbcTraitDef),
                ImmutableList.of(dbClsDef, storageDescClsDef, columnClsDef, tblClsDef, loadProcessClsDef, viewClsDef));
    }

    static List<Referenceable> buildTables(final List<NavigatorNode> navigatorNodes, final Id dbId,
                                           final Referenceable sd) {
        List<Referenceable> refs = new ArrayList<>();
        for (NavigatorNode navigatorNode : navigatorNodes) {
            Referenceable ref = buildTable(navigatorNode, dbId, sd);
            refs.add(ref);
        }
        return refs;
    }

    /**
     *
     * @param persistedEntities
     * @param nodeNames
     * @return the list of the Ids corresponding to the given node Names
     */
    static List<Id> getIdsFromNodeNames(final Map<String, Triple<Referenceable, Id, Map<String, Object>>> persistedEntities,
                                        final List<String> nodeNames) {
        List<Id> persistedIds = new ArrayList<>();
        for (String nodeName : nodeNames) {
            Triple<Referenceable, Id, Map<String, Object>> persistedNode = persistedEntities.get(nodeName);
            if (persistedNode != null) {
                persistedIds.add(persistedNode.getMiddle());
            } else {
                LOG.error("SOMETHING WENT WRONG WITH: " + nodeName);
                throw new RuntimeException("Impossible to get property for " + nodeName);
            }
        }
        return persistedIds;
    }
}
