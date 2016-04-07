package org.talend.governance.atlas;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableMap;
import com.google.common.collect.ImmutableSet;
import org.apache.atlas.AtlasClient;
import org.apache.atlas.AtlasException;
import org.apache.atlas.AtlasServiceException;
import org.apache.atlas.TalendAtlasClient;
import org.apache.atlas.typesystem.*;
import org.apache.atlas.typesystem.json.InstanceSerialization;
import org.apache.atlas.typesystem.persistence.Id;
import org.apache.atlas.typesystem.types.IDataType;
import org.apache.commons.lang3.tuple.Triple;
import org.codehaus.jettison.json.JSONArray;

import java.util.*;

/**
 * This is an util class that corresponds to some missing methods
 * from the org.apache.atlas.AtlasClient class.
 * This job is intended to be pushed into Apache Atlas
 */
public class AtlasUtils {

    private static String ENDPOINT_URL = "http://localhost:21000";
    private static AtlasClient client =  new TalendAtlasClient(ENDPOINT_URL, null, null);

    /**
     *
     * @param referenceable
     * @return
     * @throws AtlasException
     */
    public static Referenceable createInstance(Referenceable referenceable) throws AtlasException {
        //TODO improve safety + exception handling + this could be better
        // this one is like a modified version of the one on Quickstart
        // to return a ref with id
        String entityJSON = InstanceSerialization.toJson(referenceable, true);
        System.out.println("Submitting new entity= " + entityJSON);
        List<String> guids = null;
        try {
            guids = client.createEntity(entityJSON);
//            client.createEntity()
            String typeName = referenceable.getTypeName();
            System.out.println("created instance for type " + typeName + ", guid: " + guids);
            // return the Id for created instance with guid
//            Id id = new Id(guids.getString(guids.length()-1), referenceable.getId().getVersion(),
//                    referenceable.getTypeName());

//            Id id = new Id(guids.getString(guids.length()-1), referenceable.getId().getVersion(),
//                    referenceable.getTypeName());
            //TODO a new constructor in Referenceable ?
//            Referenceable refWithId = new Referenceable(id, referenceable.getTypeName(),
//                    referenceable.getValuesMap(), referenceable.getTraits(),
//                    getTraits(referenceable));
//            return refWithId;
            return null;
//        } catch (AtlasServiceException e) {
        } catch (Exception e) {
            e.printStackTrace();
            throw new AtlasException(e);
        }
    }

    private static Map<String, IStruct> getTraits(IReferenceableInstance instance) throws AtlasException {
        Map<String, IStruct> traits = new HashMap<>();
        for (String traitName : instance.getTraits() ) {
            traits.put(traitName, new Struct(traitName, instance.getTrait(traitName).getValuesMap()));
        }
        return traits;
    }

    /**
     * Create instances in bulk, it tries to guarantee the complete creation of the instances. If it fails it deletes
     * the partially created ones
     *
     * @param refs
     * @return
     */
    public static Object createBulkEntities(Collection<Referenceable> refs) throws AtlasServiceException {
//    public static Object createBulkInstances(Collection<? extends IInstance> instances) {
        //shouldn't the argument be Collection<? extends IInstance>
        //TODO JB a real (atomic) server side version, with a real return value for Id,
        // It must delete the entities if it cannot create them all
        // notice that current AtlasClient.createEntity(Collection...) does not guarantee this
        // It would be better to have the Ref objects and not the json as a return type

//        copyOf(Map<? extends K,? extends V> map)

        // First we persist the references without ids
//        Map<Id, Referenceable>
//        Map.Entry<String, Triple<Referenceable, Id, Map<String, Object>>> entry;

        for (Referenceable ref : refs) {
            try {
                // Notice the use of synchronized to have a exact in time copy of the value
//                Map<String, Object> copyOfValues = Collections.synchronizedMap(ref.getValuesMap());
//                copyOfValues.keySet().retainAll(ImmutableSet.of("columns", "inputs", "outputs"));
//                System.out.println(copyOfValues);

//                Map<String, Object> immutableCopyOfValues = ImmutableMap.copyOf(ref.getValuesMap());
//                System.out.println(immutableCopyOfValues);

//                ref.getValuesMap().keySet().remove("columns");
//                ref.getValuesMap().keySet().removeAll(ImmutableSet.of("columns", "inputs", "outputs"));
//                ref.getValuesMap().putAll(copyOfValues);

//                ref.set("columns", null);
                ref.set("columns", ImmutableList.of());
                ref.set("inputs", ImmutableList.of());
                ref.set("outputs", ImmutableList.of());

                Referenceable refWiwthid = createInstance(ref);
                System.out.println(refWiwthid);


            } catch (AtlasException e) {
                e.printStackTrace();
            }
            System.out.println(InstanceSerialization.toJson(ref, true));
        }

        List<String> a = client.createEntity(refs);
        System.out.println(a);
        // what should be the return type ? Map<Referenceable, Id> ?
        // or even better the Collection<Referenceable> with the IDs included ?

//        client.updateEntityAttribute(guid, attribute, value);
//        return createInstance(referenceable);
//        return table;

//            ref.getId();
//            Id id = AtlasUtils.createInstance(ref);
//            Id id = null;

        // Update links information afterwards
//        for (Map.Entry<String, Triple<NavigatorNode, Referenceable, Id>> entry : nodes.entrySet()) {
//            NavigatorNode navigatorNode = entry.getValue().getLeft();
//            Referenceable ref = entry.getValue().getMiddle();
//            String guid = entry.getValue().getRight()._getId();
//
//            List<Id> inputIds = new ArrayList<>();
//            for (String input : navigatorNode.getInputNodes()) {
//                Triple<NavigatorNode, Referenceable, Id> t = nodes.get(input);
//                inputIds.add(t.getRight());
//            }
//            ref.set("inputs", inputIds);
//
//            Collection<Triple<NavigatorNode, Referenceable, Id>> in =
//                    Collections2.transform(navigatorNode.getInputNodes(), Functions.forMap(nodes));
//
//            List<Id> outputIds = new ArrayList<>();
//            for (String output : navigatorNode.getOutputNodes()) {
//                Triple<NavigatorNode, Referenceable, Id> t = nodes.get(output);
//                inputIds.add(t.getRight());
//            }
//            ref.set("outputs", outputIds);
//
//            try {
//                Referenceable r2 = this.client.getEntity(guid);
//                System.out.format("Updating %s: %s\n", r2.getId()._getId(), r2);
//                System.out.println("---------------------------");
//                System.out.format("Updating %s: %s\n", ref.getId()._getId(), ref);
//
//                // guid ?
//                this.client.updateEntity(r2.getId()._getId(), r2);
//
////                this.client.updateEntity(guid, ref);
////                this.client.updateEntityAttribute(id._getId(), "inputs", ids2);
//            } catch (AtlasServiceException e) {
//                e.printStackTrace();
//            }
//        }

        return null;
    }

}
