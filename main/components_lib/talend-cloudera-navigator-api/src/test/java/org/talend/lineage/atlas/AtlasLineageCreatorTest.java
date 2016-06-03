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
package org.talend.lineage.atlas;

import org.apache.atlas.typesystem.Referenceable;
import org.apache.atlas.typesystem.persistence.Id;
import org.apache.commons.lang3.tuple.Pair;
import org.junit.Test;

import java.util.*;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

public class AtlasLineageCreatorTest {

    private static final String ENTITY_DESCRIPTION = "Talend Component";
    private static final String ENTITY_LINK = "http://www.talend.com/";

    private static final String ENDPOINT_URL = "http://localhost:21000";
    AtlasLineageCreator atlasLineageCreator = new AtlasLineageCreator(ENDPOINT_URL, null, null);

    private static Map<String, Object> createGenericMetadata() {
        final long id = System.currentTimeMillis();
        Map<String, Object> metadata = new HashMap<>();
        metadata.put("description", ENTITY_DESCRIPTION + "-" + id);
//        metadata.put("link", ENTITY_LINK);
        metadata.put("owner", "scott");
        // tags are traits in atlas (at least visually)
        metadata.put("startTime", id);
        metadata.put("endTime", id + 10000);
        metadata.put("script", "SELECT FROM WHERE");
        return metadata;
    }

    private static Map<String, Object> createInputMetadata() {
        Map<String, Object> metadata = createGenericMetadata();
        metadata.put("path", "hdfs://foo/bar");
        metadata.put("format", "CSV");
        return metadata;
    }

    private void addJobInfo(String name) {
        long currentTime = System.currentTimeMillis();
        atlasLineageCreator.addJobInfo(name + "_" + currentTime, name + "_description", name + "_purpose", "author",
                "1.0", "jobType", "framework", currentTime - 10000, currentTime, "status",
                System.currentTimeMillis(), System.currentTimeMillis());
    }

    @Test
    public void test_simple() {
        Map<String, String> schema1 = new HashMap<>();
        schema1.put("id", "int");
        schema1.put("name", "string");

        Map<String, String> schema2 = new HashMap<>();
        schema2.put("a", "string");
        schema2.put("b", "string");
        schema2.put("c", "int");

        addJobInfo("test_simple");
        Referenceable jobRef = atlasLineageCreator.getJobRef();
        assertNotNull(jobRef);

        atlasLineageCreator.addNodeToLineage("input", schema1, createInputMetadata(), new ArrayList<String>(), Arrays.asList("out"));
        atlasLineageCreator.addNodeToLineage("out", schema2, createGenericMetadata(), Arrays.asList("input"), new ArrayList<String>());

        List<Referenceable> refs = atlasLineageCreator.getRefs();
        assertNotNull(refs);
        assertEquals(refs.size(), 2);

        atlasLineageCreator.sendToLineageProvider(true);
        atlasLineageCreator.logPersistedEntities();
        testPersistence(1, 2, 1);
    }

    @Test
    public void test_hard() {
        Map<String, String> schema1 = new HashMap<>();
        schema1.put("id", "int");
        schema1.put("name", "string");

        Map<String, String> schema2 = new HashMap<>();
        schema2.put("id", "int");
        schema2.put("name", "string");
        schema2.put("value", "int");

        Map<String, String> schema3 = new HashMap<>();
        schema3.put("id", "int");

        addJobInfo("test_hard");
        atlasLineageCreator.addNodeToLineage("input", schema1, createGenericMetadata(), new ArrayList<String>(), Arrays.asList("middle1"));
        atlasLineageCreator.addNodeToLineage("middle1", schema1, createGenericMetadata(), Arrays.asList("input"), Arrays.asList("middle2", "out1"));
        atlasLineageCreator.addNodeToLineage("middle2", schema2, createGenericMetadata(), Arrays.asList("middle1"), Arrays.asList("out2"));
        atlasLineageCreator.addNodeToLineage("out1", schema3, createGenericMetadata(), Arrays.asList("middle1"), new ArrayList<String>());
        atlasLineageCreator.addNodeToLineage("out2", schema3, createGenericMetadata(), Arrays.asList("middle2"), new ArrayList<String>());

        atlasLineageCreator.sendToLineageProvider(true);
        atlasLineageCreator.logPersistedEntities();
        testPersistence(1, 5, 4);
    }

    @Test
    public void test_hardMultipleInput() {
        Map<String, String> schema1 = new HashMap<>();
        schema1.put("id", "int");
        schema1.put("name", "string");

        Map<String, String> schema2 = new HashMap<>();
        schema2.put("id", "int");
        schema2.put("name", "string");
        schema2.put("value", "int");

        Map<String, String> schema3 = new HashMap<>();
        schema3.put("id", "int");

        addJobInfo("test_hardMultipleInput");
        atlasLineageCreator.addNodeToLineage("input1", schema1, createGenericMetadata(), new ArrayList<String>(), Arrays.asList("middle1"));
        atlasLineageCreator.addNodeToLineage("input2", schema2, createGenericMetadata(), new ArrayList<String>(), Arrays.asList("middle1"));
        atlasLineageCreator.addNodeToLineage("middle1", schema1, createGenericMetadata(), Arrays.asList("input1", "input2"), Arrays.asList("middle2", "out1"));
        atlasLineageCreator.addNodeToLineage("middle2", schema2, createGenericMetadata(), Arrays.asList("middle1"), Arrays.asList("out2"));
        atlasLineageCreator.addNodeToLineage("out1", schema3, createGenericMetadata(), Arrays.asList("middle1"), new ArrayList<String>());
        atlasLineageCreator.addNodeToLineage("out2", schema3, createGenericMetadata(), Arrays.asList("middle2"), new ArrayList<String>());

        atlasLineageCreator.sendToLineageProvider(true);
        atlasLineageCreator.logPersistedEntities();
        testPersistence(1, 6, 4);
    }

    private void testPersistence(int numPersistedJobInstances, int numPersistedInstances, int numPersistedArticialInstances) {
        Map<String, Pair<Referenceable, Id>> persistedJobs = atlasLineageCreator.getPersistedJobs();
        assertNotNull(persistedJobs);
        assertEquals(persistedJobs.values().size(), numPersistedJobInstances);

        Map<String, Pair<Referenceable, Id>> persistedComponents = atlasLineageCreator.getPersistedComponents();
        assertNotNull(persistedComponents);
        assertEquals(persistedComponents.values().size(), numPersistedInstances);

        Map<String, Pair<Referenceable, Id>> persistedArtificialComponents = atlasLineageCreator.getPersistedArtificialComponents();
        assertNotNull(persistedArtificialComponents);
        assertEquals(persistedArtificialComponents.values().size(), numPersistedArticialInstances);
    }

}
