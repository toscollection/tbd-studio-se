package org.talend.lineage.common;

import java.util.Map;

/**
 * 
 * Default implementations of methods from ILineageCreator that are not supported by a given provider
 *
 */
public abstract class AbstractLineageCreator {

    public void addDataset(Map<String, String> schema, String componentName, String fileSystemPath, String fileFormat) {
        // Nothing
    }

    public void addJobInfo(Map<String, Object> jobMetadata) {
        // Nothing
    }

}
