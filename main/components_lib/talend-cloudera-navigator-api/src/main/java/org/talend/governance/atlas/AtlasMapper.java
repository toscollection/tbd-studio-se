package org.talend.governance.atlas;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableSet;
import org.apache.atlas.AtlasClient;
import org.apache.atlas.AtlasServiceException;
import org.apache.atlas.TalendAtlasClient;
import org.apache.atlas.typesystem.Referenceable;
import org.apache.atlas.typesystem.TypesDef;
import org.apache.atlas.typesystem.persistence.Id;
import org.apache.atlas.typesystem.types.*;
import org.apache.atlas.typesystem.types.utils.TypesUtil;
import org.apache.commons.collections.CollectionUtils;
import org.talend.cloudera.navigator.api.NavigatorNode;

import java.util.*;


/**
 * Class to map the Talend DAG into into the Atlas DAG
 */
public class AtlasMapper {

    static final String STORAGE_DESC_TYPE = "StorageDesc2";
    static final String COLUMN_TYPE = "Column2"; //TODO check 2 vs no 2
    static final String TABLE_TYPE = "Table2";
    static final String LOAD_PROCESS_TYPE = "Process2";
    static final Collection<String> MODEL_TYPES = ImmutableList.of(COLUMN_TYPE, TABLE_TYPE, LOAD_PROCESS_TYPE);

    // Reserved attribute names, these will be overwritten if they come
    // in the schema and the metadata
    public static final String COLUMNS_ATTRIBUTE = "columns";

    private TalendAtlasClient client;
    private StringBuilder debugStringBuilder = new StringBuilder();

    public AtlasMapper(String url, String login, String password) {
        this.client = new TalendAtlasClient(url, login, password);
    }

    public void changeEndpoint(String url, String login, String password) {
        this.client = new TalendAtlasClient(url, login, password);
    }

    /**
     * TODO this function must return the appropriate type of the objects that are going to be persisted
     * TODO remove jobId ? do woe need a meta entity to contain the others ?
     * Note that jobId, tags and other information must come in the metadata
     * @param navigatorNodes
     * @param jobId not used, please passe job info in the metadata
     */
    public void map(List<NavigatorNode> navigatorNodes, String jobId) {
        // First create the type definition
        //TODO method to create or get the ids of the typesDef
        // we ensure that the default types do exist
        //TODO we need to delete the types too
        Collection<String> missingTypes = getMissingTypes();
        if (missingTypes.size() > 0) {
            TypesDef typesDef = buildTypesDefinitions();
            List<String> typesIds = persistTypes(typesDef);
        }

        Collection<Referenceable> refs = new ArrayList<>();
        for (NavigatorNode navigatorNode : navigatorNodes) {
            Referenceable ref = createEntity(navigatorNode);
            refs.add(ref);
        }

        try {
            AtlasUtils.createBulkEntities(refs);
        } catch (AtlasServiceException e) {
            e.printStackTrace();
            throw new RuntimeException(e);
        }

    }

    public List<String> persistTypes(TypesDef typesDef) {
//        System.out.println("typesAsJSON = " + TypesSerialization.toJson(typesDef));
        try {
            List<String> ids = this.client.createType(typesDef);
            return ids;
        } catch (AtlasServiceException e) {
            e.printStackTrace();
            throw new RuntimeException(e);
        }
    }

    private Collection<String> getMissingTypes() {
        try {
            Collection<String> allTypes = this.client.listTypes();
            Collection<String> missingTypes = CollectionUtils.subtract(MODEL_TYPES, allTypes);
            return missingTypes;
        } catch (AtlasServiceException e) {
            e.printStackTrace();
            throw new RuntimeException(e);
        }
    }

    /**
     *
     * @return
     */
    private static TypesDef buildTypesDefinitions() {
        ImmutableList<EnumTypeDefinition> enums = ImmutableList.<EnumTypeDefinition>of();
        ImmutableList<StructTypeDefinition> structs = ImmutableList.<StructTypeDefinition>of();

        HierarchicalTypeDefinition<TraitType> dimTraitDef = TypesUtil.createTraitTypeDef("Dimension",  "Dimension Trait", null);
        HierarchicalTypeDefinition<TraitType> factTraitDef = TypesUtil.createTraitTypeDef("Fact", "Fact Trait", null);
        HierarchicalTypeDefinition<TraitType> piiTraitDef = TypesUtil.createTraitTypeDef("PII", "PII Trait", null);
        HierarchicalTypeDefinition<TraitType> metricTraitDef = TypesUtil.createTraitTypeDef("Metric", "Metric Trait", null);
        HierarchicalTypeDefinition<TraitType> etlTraitDef = TypesUtil.createTraitTypeDef("ETL", "ETL Trait", null);
        HierarchicalTypeDefinition<TraitType> jdbcTraitDef = TypesUtil.createTraitTypeDef("JdbcAccess", "JdbcAccess Trait", null);
        HierarchicalTypeDefinition<TraitType> logTraitDef = TypesUtil.createTraitTypeDef("Log Data", "LogData Trait",  null);

        ImmutableList<HierarchicalTypeDefinition<TraitType>> traits =
                ImmutableList.<HierarchicalTypeDefinition<TraitType>>of();
//                ImmutableList.of(dimTraitDef, factTraitDef, piiTraitDef, metricTraitDef,
//                        etlTraitDef, jdbcTraitDef, logTraitDef);

        HierarchicalTypeDefinition<ClassType> storageDescClsDef = TypesUtil
                .createClassTypeDef(STORAGE_DESC_TYPE, STORAGE_DESC_TYPE, null,
                        TypesUtil.createOptionalAttrDef("location", DataTypes.STRING_TYPE),
                        TypesUtil.createOptionalAttrDef("inputFormat", DataTypes.STRING_TYPE),
                        TypesUtil.createOptionalAttrDef("outputFormat", DataTypes.STRING_TYPE),
                        TypesUtil.createRequiredAttrDef("compressed", DataTypes.STRING_TYPE)
                );
        HierarchicalTypeDefinition<ClassType> columnClsDef = TypesUtil
                .createClassTypeDef(COLUMN_TYPE, COLUMN_TYPE, null,
                        TypesUtil.createOptionalAttrDef("name", DataTypes.STRING_TYPE),
                        TypesUtil.createOptionalAttrDef("dataType", DataTypes.STRING_TYPE),
                        TypesUtil.createOptionalAttrDef("comment", DataTypes.STRING_TYPE)
                );
        HierarchicalTypeDefinition<ClassType> tblClsDef = TypesUtil
                .createClassTypeDef(TABLE_TYPE, TABLE_TYPE, ImmutableSet.of("DataSet"),
                        TypesUtil.createOptionalAttrDef("owner", DataTypes.STRING_TYPE),
                        TypesUtil.createOptionalAttrDef("createTime", DataTypes.LONG_TYPE),
                        TypesUtil.createOptionalAttrDef("lastAccessTime", DataTypes.LONG_TYPE),
                        TypesUtil.createOptionalAttrDef("retention", DataTypes.LONG_TYPE),
                        TypesUtil.createOptionalAttrDef("viewOriginalText", DataTypes.STRING_TYPE),
                        TypesUtil.createOptionalAttrDef("viewExpandedText", DataTypes.STRING_TYPE),
                        TypesUtil.createOptionalAttrDef("tableType", DataTypes.STRING_TYPE),
                        TypesUtil.createOptionalAttrDef("temporary", DataTypes.BOOLEAN_TYPE),
//                        new AttributeDefinition(COLUMNS_ATTRIBUTE, DataTypes.arrayTypeName(COLUMN_TYPE),
//                                Multiplicity.COLLECTION, true, null)
                        new AttributeDefinition(COLUMNS_ATTRIBUTE, DataTypes.arrayTypeName("Column2"),
                                Multiplicity.COLLECTION, true, null)
                );
        HierarchicalTypeDefinition<ClassType> loadProcessClsDef = TypesUtil
                .createClassTypeDef(LOAD_PROCESS_TYPE, LOAD_PROCESS_TYPE, ImmutableSet.of("Process"),
                        TypesUtil.createOptionalAttrDef("userName", DataTypes.STRING_TYPE),
                        TypesUtil.createOptionalAttrDef("startTime", DataTypes.LONG_TYPE),
                        TypesUtil.createOptionalAttrDef("endTime", DataTypes.LONG_TYPE),
                        TypesUtil.createRequiredAttrDef("queryText", DataTypes.STRING_TYPE),
                        TypesUtil.createRequiredAttrDef("queryPlan", DataTypes.STRING_TYPE),
                        TypesUtil.createRequiredAttrDef("queryId", DataTypes.STRING_TYPE),
                        TypesUtil.createRequiredAttrDef("queryGraph", DataTypes.STRING_TYPE),
                        new AttributeDefinition(COLUMNS_ATTRIBUTE, DataTypes.arrayTypeName(COLUMN_TYPE),
                                Multiplicity.COLLECTION, true, null)
                );
        ImmutableList<HierarchicalTypeDefinition<ClassType>> classes =
                ImmutableList.of(storageDescClsDef, columnClsDef, tblClsDef, loadProcessClsDef);
//                ImmutableList.of(columnClsDef, tblClsDef, loadProcessClsDef);

        return TypesUtil.getTypesDef(enums, structs, traits, classes);
    }

    /**
     * Builds and Atlas Referenceable from a NavigatorNode
     * TODO end doc
     * @param navigatorNode
     * @return
     */
    private Referenceable createEntity(NavigatorNode navigatorNode) {
        // Note that Trait Types are not decidable by the studio,
        // We just add them to all entities
//        Referenceable process = new Referenceable(LOAD_PROCESS_TYPE, "Dimension", "ETL");
//        Referenceable process = new Referenceable(TABLE_TYPE, "Dimension", "ETL");
        Referenceable process = new Referenceable("Table", "Dimension", "ETL");

        // We map first the generic metadata, remember that if
        // navigator uses a reserved key, it can be overwritten
        Map<String, String> metadata = navigatorNode.getMetadata();
        for(Map.Entry<String, String> meta : metadata.entrySet()) {
            process.set(meta.getKey(), meta.getValue());
        }

        // We map then the mandatory entities
        process.set("name", navigatorNode.getName());
        process.set("inputs", navigatorNode.getInputNodes());
        process.set("outputs", navigatorNode.getOutputNodes());

        Map<String, String> schema = navigatorNode.getSchema();
        List<Referenceable> columns = new ArrayList<>();
        for(Map.Entry<String, String> entry : schema.entrySet()) {
            Referenceable column = new Referenceable(COLUMN_TYPE); //, traits ... s"PII" or "Metric"
            column.set("name", entry.getKey());
            column.set("dataType", entry.getValue());
//            column.set("comment", comment);

            columns.add(column);

        }
        process.set("columns", columns);

        return process;
    }

    //TODO Add this to have the whole discussed figure
    Referenceable rawStorageDescriptor(String location, String inputFormat, String outputFormat, boolean compressed)
            throws Exception {
        Referenceable referenceable = new Referenceable(STORAGE_DESC_TYPE);
        referenceable.set("location", location);
        referenceable.set("inputFormat", inputFormat);
        referenceable.set("outputFormat", outputFormat);
        referenceable.set("compressed", compressed);
        return referenceable;
    }

}
