// ============================================================================
//
// Copyright (C) 2006-2016 Talend Inc. - www.talend.com
//
// This source code is available under agreement available at
// %InstallDIR%\features\org.talend.rcp.branding.%PRODUCTNAME%\%PRODUCTNAME%license.txt
//
// You should have received a copy of the agreement
// along with this program; if not, write to Talend SA
// 9 rue Pages 92150 Suresnes, France
//
// ============================================================================
package org.talend.governance.atlas;

import com.google.common.base.Joiner;
import com.google.common.collect.ArrayListMultimap;
import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableMap;
import com.google.common.collect.ListMultimap;
import org.apache.atlas.AtlasServiceException;
import org.apache.atlas.TalendAtlasClient;
import org.junit.Before;
import org.junit.Test;
import org.talend.cloudera.navigator.api.NavigatorNode;
import org.talend.governance.atlas.AtlasMapper;
import scala.collection.mutable.MultiMap;


import java.util.*;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

/**
 * created by pbailly on 21 Oct 2015 Detailled comment
 *
 */
public class AtlasMapperTest {

    private static final String ENTITY_DESCRIPTION = "Talend Component";
    private static final String ENTITY_LINK = "http://www.talend.com/";

    //    @Test(expected = IllegalArgumentException.class)
    private static final String ENDPOINT_URL = "http://localhost:21000";
    private AtlasMapper mapper = new AtlasMapper(ENDPOINT_URL, null, null);
    //TODO delete and fix
    private TalendAtlasClient client = new TalendAtlasClient(ENDPOINT_URL, null, null);

    @Before
    public void setUp() {
    }

    @Test
    public void testEmpty() {
        mapper.map(new ArrayList<NavigatorNode>(), "job");
        assertNotNull(mapper);
//        List<Entity> entities =
    }

    private static Map<String, String> createGenericMetadata() {
        Map<String, String> metadata = new HashMap<>();
        metadata.put("description", ENTITY_DESCRIPTION);
        metadata.put("link", ENTITY_LINK);
        Collection<String> tags = ImmutableList.of("tag1", "tag2");
        metadata.put("jobId", "TestJob");
        metadata.put("tags", Joiner.on(", ").join(tags));

        metadata.put("user", "scott");
        metadata.put("startTime", String.valueOf(System.currentTimeMillis()));
        metadata.put("endTime", String.valueOf(System.currentTimeMillis() + 10000));
        metadata.put("query", "queryText");
        return metadata;
    }


    private void deleteTalendEntities() {
        try {
            client.deleteEntitiesByType(AtlasMapper.TABLE_TYPE,
                    AtlasMapper.COLUMN_TYPE, AtlasMapper.LOAD_PROCESS_TYPE);
        } catch (AtlasServiceException e) {
            e.printStackTrace();
        }
    }

    //TODO this is a debug method since I don't have a GUI
    public Map<String, Collection<String>> getEntitiesByType(String... types) {
        ListMultimap<String, String> m = ArrayListMultimap.create();
        for (String type : types) {
            try {
                List<String> guids = guids = client.listEntities(type);
                for (String guid : guids) {
                    m.put(type, guid);
                }
            } catch (AtlasServiceException e) {
                e.printStackTrace();
            }
        }
        return m.asMap();
    }

    @Test
    public void test_simple() {
        List<NavigatorNode> nodes = new ArrayList<NavigatorNode>();

        Map<String, String> schema1 = new HashMap<>();
        schema1.put("id", "int");
        schema1.put("name", "string");

        Map<String, String> schema2 = new HashMap<>();
        schema1.put("a", "string");
        schema1.put("b", "string");
        schema1.put("c", "int");

        nodes.add(new NavigatorNode("input", schema1, createGenericMetadata(), new ArrayList<String>(), Arrays.asList("out")));
        nodes.add(new NavigatorNode("out", schema2, createGenericMetadata(), Arrays.asList("input"), new ArrayList<String>()));

        mapper.map(nodes, "job");

        Map<String, Collection<String>> m = getEntitiesByType(AtlasMapper.TABLE_TYPE,
                AtlasMapper.COLUMN_TYPE, AtlasMapper.LOAD_PROCESS_TYPE);
        for (Map.Entry<String,Collection<String>> entry : m.entrySet()) {
            System.out.println(entry.getKey() + " - " + entry.getValue().size());
            System.out.println(entry.getKey() + " - " + entry.getValue());
        }

//        deleteTalendEntities();

        assertNotNull(mapper);

//        metadata.put("inputs", ImmutableList.of());
//        metadata.put("outputs", ImmutableList.of());

//        String idInput = GeneratorID.generateEntityID("job", "input");
//        String idInputId = GeneratorID.generateEntityChildID("job", "input", "id");
//        String idInputName = GeneratorID.generateEntityChildID("job", "input", "name");
//        String idOut = GeneratorID.generateEntityID("job", "out");
//        String idOutId = GeneratorID.generateEntityChildID("job", "out", "id");
//        String idOutName = GeneratorID.generateEntityChildID("job", "out", "name");

//        assertEquals("\ninput (" + idInput + ") --->[" + idOut + "]\n" //
//                + "\t" + idInput + "__name(" + idInputName + ") --->[" + idOutName + "]\n" //
//                + "\t" + idInput + "__id(" + idInputId + ") --->[" + idOutId + "]\n" //
//                + "\n" //
//                + "[" + idInput + "]---> out (" + idOut + ")\n" //
//                + "\t" + idOut + "__name(" + idOutName + ") --->[" + idOut + "]\n" //
//                + "\t" + idOut + "__id(" + idOutId + ") --->[" + idOut + "]\n", tem.toString());
//
//        assertEquals(6, entities.size());
    }
//
    @Test
    public void test_hard() {
        List<NavigatorNode> nodes = new ArrayList<NavigatorNode>();

        Map<String, String> schema1 = new HashMap<String, String>();
        schema1.put("id", "int");
        schema1.put("name", "string");

        Map<String, String> schema2 = new HashMap<String, String>();
        schema2.put("id", "int");
        schema2.put("name", "string");
        schema2.put("value", "int");

        Map<String, String> schema3 = new HashMap<String, String>();
        schema3.put("id", "int");

        nodes.add(new NavigatorNode("input", schema1, new ArrayList<String>(), Arrays.asList("middle1")));
        nodes.add(new NavigatorNode("middle1", schema1, Arrays.asList("input"), Arrays.asList("middle2", "out1")));
        nodes.add(new NavigatorNode("middle2", schema2, Arrays.asList("middle1"), Arrays.asList("out2")));
        nodes.add(new NavigatorNode("out1", schema3, Arrays.asList("middle1"), new ArrayList<String>()));
        nodes.add(new NavigatorNode("out2", schema3, Arrays.asList("middle2"), new ArrayList<String>()));

        mapper.map(nodes, "job");
        assertNotNull(mapper);

//        String idInput = GeneratorID.generateEntityID("job", "input");
//        String idInputId = GeneratorID.generateEntityChildID("job", "input", "id");
//        String idInputName = GeneratorID.generateEntityChildID("job", "input", "name");
//
//        String idMiddle1 = GeneratorID.generateEntityID("job", "middle1");
//        String idMiddle1Id = GeneratorID.generateEntityChildID("job", "middle1", "id");
//        String idMiddle1Name = GeneratorID.generateEntityChildID("job", "middle1", "name");
//
//        String idMiddle2 = GeneratorID.generateEntityID("job", "middle2");
//        String idMiddle2Id = GeneratorID.generateEntityChildID("job", "middle2", "id");
//        String idMiddle2Name = GeneratorID.generateEntityChildID("job", "middle2", "name");
//        String idMiddle2Value = GeneratorID.generateEntityChildID("job", "middle2", "value");
//
//        String idOut1 = GeneratorID.generateEntityID("job", "out1");
//        String idOut1Id = GeneratorID.generateEntityChildID("job", "out1", "id");
//
//        String idOut2 = GeneratorID.generateEntityID("job", "out2");
//        String idOut2Id = GeneratorID.generateEntityChildID("job", "out2", "id");
//
////        assertEquals("\ninput (" + idInput + ") --->[" + idMiddle1 + "]\n" //
////                + "\t" + idInput + "__name(" + idInputName + ") --->[" + idMiddle1Name + "]\n" //
////                + "\t" + idInput + "__id(" + idInputId + ") --->[" + idMiddle1Id + "]\n" //
////                + "\n" //
////                + "[" + idInput + "]---> middle1 (" + idMiddle1 + ") --->[" + idMiddle2 + ", " + idOut1 + "]\n" //
////                + "\t" + idMiddle1 + "__name(" + idMiddle1Name + ") --->[" + idMiddle2Name + "]\n" //
////                + "\t" + idMiddle1 + "__id(" + idMiddle1Id + ") --->[" + idMiddle2Id + ", " + idOut1Id + "]\n" //
////                + "\n" //
////                + "[" + idMiddle1 + "]---> middle2 (" + idMiddle2 + ") --->[" + idOut2 + "]\n" //
////                + "\t" + idMiddle2 + "__name(" + idMiddle2Name + ") --->[" + idMiddle2 + "]\n" //
////                + "\t" + idMiddle2 + "__id(" + idMiddle2Id + ") --->[" + idOut2Id + "]\n" //
////                + "\t" + idMiddle2 + "__value(" + idMiddle2Value + ") --->[" + idMiddle2 + "]\n" //
////                + "\n" //
////                + "[" + idMiddle1 + "]---> out1 (" + idOut1 + ")\n" //
////                + "\t" + idOut1 + "__id(" + idOut1Id + ") --->[" + idOut1 + "]\n" //
////                + "\n" //
////                + "[" + idMiddle2 + "]---> out2 (" + idOut2 + ")\n" //
////                + "\t" + idOut2 + "__id(" + idOut2Id + ") --->[" + idOut2 + "]\n", tem.toString());
////        assertEquals(14, entities.size());
    }
//
//    @Test
//    public void test_hardMultipleInput() {
//        List<NavigatorNode> nodes = new ArrayList<NavigatorNode>();
//
//        Map<String, String> schema1 = new HashMap<String, String>();
//        schema1.put("id", "int");
//        schema1.put("name", "string");
//
//        Map<String, String> schema2 = new HashMap<String, String>();
//        schema2.put("id", "int");
//        schema2.put("name", "string");
//        schema2.put("value", "int");
//
//        Map<String, String> schema3 = new HashMap<String, String>();
//        schema3.put("id", "int");
//
//        nodes.add(new NavigatorNode("input1", schema1, new ArrayList<String>(), Arrays.asList("middle1")));
//        nodes.add(new NavigatorNode("input2", schema2, new ArrayList<String>(), Arrays.asList("middle1")));
//        nodes.add(new NavigatorNode("middle1", schema1, Arrays.asList("input1", "input2"), Arrays.asList("middle2", "out1")));
//        nodes.add(new NavigatorNode("middle2", schema2, Arrays.asList("middle1"), Arrays.asList("out2")));
//        nodes.add(new NavigatorNode("out1", schema3, Arrays.asList("middle1"), new ArrayList<String>()));
//        nodes.add(new NavigatorNode("out2", schema3, Arrays.asList("middle2"), new ArrayList<String>()));
//
//        TalendEntityMapper tem = new TalendEntityMapper(nodes, "job");
//        assertEquals("", tem.toString());
//        List<Entity> entities = tem.map();
//
//        String idInput1 = GeneratorID.generateEntityID("job", "input1");
//        String idInput1Id = GeneratorID.generateEntityChildID("job", "input1", "id");
//        String idInput1Name = GeneratorID.generateEntityChildID("job", "input1", "name");
//
//        String idInput2 = GeneratorID.generateEntityID("job", "input2");
//        String idInput2Id = GeneratorID.generateEntityChildID("job", "input2", "id");
//        String idInput2Name = GeneratorID.generateEntityChildID("job", "input2", "name");
//        String idInput2Value = GeneratorID.generateEntityChildID("job", "input2", "value");
//
//        String idMiddle1 = GeneratorID.generateEntityID("job", "middle1");
//        String idMiddle1Id = GeneratorID.generateEntityChildID("job", "middle1", "id");
//        String idMiddle1Name = GeneratorID.generateEntityChildID("job", "middle1", "name");
//
//        String idMiddle2 = GeneratorID.generateEntityID("job", "middle2");
//        String idMiddle2Id = GeneratorID.generateEntityChildID("job", "middle2", "id");
//        String idMiddle2Name = GeneratorID.generateEntityChildID("job", "middle2", "name");
//        String idMiddle2Value = GeneratorID.generateEntityChildID("job", "middle2", "value");
//
//        String idOut1 = GeneratorID.generateEntityID("job", "out1");
//        String idOut1Id = GeneratorID.generateEntityChildID("job", "out1", "id");
//
//        String idOut2 = GeneratorID.generateEntityID("job", "out2");
//        String idOut2Id = GeneratorID.generateEntityChildID("job", "out2", "id");
//
//        assertEquals("\ninput1 (" + idInput1 + ") --->[" + idMiddle1
//                + "]\n" //
//                + "\t" + idInput1 + "__name(" + idInput1Name + ") --->[" + idMiddle1Name
//                + "]\n" //
//                + "\t" + idInput1 + "__id(" + idInput1Id + ") --->[" + idMiddle1Id
//                + "]\n" //
//                + "\n" //
//                + "input2 (" + idInput2 + ") --->[" + idMiddle1
//                + "]\n" //
//                + "\t" + idInput2 + "__name(" + idInput2Name + ") --->[" + idMiddle1Name
//                + "]\n" //
//                + "\t" + idInput2 + "__id(" + idInput2Id + ") --->[" + idMiddle1Id
//                + "]\n" //
//                + "\t" + idInput2 + "__value(" + idInput2Value + ") --->[" + idInput2
//                + "]\n" //
//                + "\n" //
//                + "[" + idInput1 + ", " + idInput2 + "]---> middle1 (" + idMiddle1 + ") --->[" + idMiddle2 + ", " + idOut1
//                + "]\n" //
//                + "\t" + idMiddle1 + "__name(" + idMiddle1Name + ") --->[" + idMiddle2Name + "]\n" //
//                + "\t" + idMiddle1 + "__id(" + idMiddle1Id + ") --->[" + idMiddle2Id + ", " + idOut1Id + "]\n" //
//                + "\n" //
//                + "[" + idMiddle1 + "]---> middle2 (" + idMiddle2 + ") --->[" + idOut2 + "]\n" //
//                + "\t" + idMiddle2 + "__name(" + idMiddle2Name + ") --->[" + idMiddle2 + "]\n" //
//                + "\t" + idMiddle2 + "__id(" + idMiddle2Id + ") --->[" + idOut2Id + "]\n" //
//                + "\t" + idMiddle2 + "__value(" + idMiddle2Value + ") --->[" + idMiddle2 + "]\n" //
//                + "\n" //
//                + "[" + idMiddle1 + "]---> out1 (" + idOut1 + ")\n" //
//                + "\t" + idOut1 + "__id(" + idOut1Id + ") --->[" + idOut1 + "]\n" //
//                + "\n" //
//                + "[" + idMiddle2 + "]---> out2 (" + idOut2 + ")\n" //
//                + "\t" + idOut2 + "__id(" + idOut2Id + ") --->[" + idOut2 + "]\n", tem.toString());
//        assertEquals(18, entities.size());
//    }
//
//    /**
//     * if the name of an extremum component start with "tFile", we need to connect it to the associated dataset
//     */
//    @Test
//    public void test_file() {
//        List<NavigatorNode> nodes = new ArrayList<NavigatorNode>();
//
//        Map<String, String> schema1 = new HashMap<String, String>();
//        schema1.put("id", "int");
//        schema1.put("name", "string");
//
//        nodes.add(new NavigatorNode("tFile_input", schema1, new ArrayList<String>(), Arrays.asList("tFile_out")));
//        nodes.add(new NavigatorNode("tFile_out", schema1, Arrays.asList("tFile_input"), new ArrayList<String>()));
//
//        TalendEntityMapper tem = new TalendEntityMapper(nodes, "job");
//        assertEquals("", tem.toString());
//        List<Entity> entities = tem.map();
//        String datasetInput = GeneratorID.generateDatasetID("job", "tFile_input");
//        String idInput = GeneratorID.generateEntityID("job", "tFile_input");
//        String idInputId = GeneratorID.generateEntityChildID("job", "tFile_input", "id");
//        String idInputName = GeneratorID.generateEntityChildID("job", "tFile_input", "name");
//        String datasetOut = GeneratorID.generateDatasetID("job", "tFile_out");
//        String idOut = GeneratorID.generateEntityID("job", "tFile_out");
//        String idOutId = GeneratorID.generateEntityChildID("job", "tFile_out", "id");
//        String idOutName = GeneratorID.generateEntityChildID("job", "tFile_out", "name");
//
//        assertEquals("\n[" + datasetInput + "]---> tFile_input (" + idInput + ") --->[" + idOut + "]\n" //
//                + "\t" + idInput + "__name(" + idInputName + ") --->[" + idOutName + "]\n" //
//                + "\t" + idInput + "__id(" + idInputId + ") --->[" + idOutId + "]\n" //
//                + "\n" //
//                + "[" + idInput + "]---> tFile_out (" + idOut + ") --->[" + datasetOut + "]\n" //
//                + "\t" + idOut + "__name(" + idOutName + ") --->[" + datasetOut + "]\n" //
//                + "\t" + idOut + "__id(" + idOutId + ") --->[" + datasetOut + "]\n", tem.toString());
//
//        assertEquals(6, entities.size());
//    }
}
