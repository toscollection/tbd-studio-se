package org.talend.lineage;

import java.util.List;
import java.util.Map;

/**
 *
 */
public interface ILineageCreator {

    /**
     *
     * @param name
     * @param schema
     * @param inputNodes
     * @param outputNodes
     */
    void addNodeToLineage(String name, Map<String, String> schema, Map<String, Object> metadata,
                          List<String> inputNodes, List<String> outputNodes);

    /**
     *
     * @param dieOnError
     */
    void sendToLineageProvider(Boolean dieOnError);
}
