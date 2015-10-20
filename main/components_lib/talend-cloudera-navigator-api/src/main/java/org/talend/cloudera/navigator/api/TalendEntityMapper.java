package org.talend.cloudera.navigator.api;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import com.cloudera.nav.sdk.model.CustomIdGenerator;
import com.cloudera.nav.sdk.model.entities.Entity;

/*
 * Class to map studio components into cloudera navigator connected entities
 */
public class TalendEntityMapper {

    private static final String ENTITY_DESCRIPTION = "Talend Component";

    private static org.apache.log4j.Logger LOG = org.apache.log4j.Logger.getLogger(TalendEntityMapper.class);

    private static final String ENTITY_LINK = "http://www.talend.com/";

    public static final String CLOUDERA_NAVIGATOR_APPLICATION_NAMESPACE = "Talend";

    public static final String DATASET_MARKER = "DATASET_";

    private List<NavigatorNode> navigatorNodes;

    // The mapper uses a jobId to create unique entities in Cloudera navigator
    private String jobId;

    public TalendEntityMapper(List<NavigatorNode> navigatorNodes, String jobId) {
        this.navigatorNodes = navigatorNodes;
        this.jobId = jobId;
    }

    /*
     * returns a list of Cloudera navigator entities ready to be written to Cloudera Navigator Calls methods to create
     * all of the entities and to connect them together
     * 
     * Each Talend studio component is mapped into a {@link #TalendEntity} Each Talend studio schema element (column
     * name + type) of each component is mapped into a {@link #TalendEntityChild}
     * 
     * Each {@link #TalendEntity} can have multiple {@link #TalendEntityChild} to reflect the Talend studio component's
     * schema Each {@link #TalendEntity} can have multiple input/output links to other {@link #TalendEntity}
     * 
     * Note : Due to limitations in the Cloudera navigator API/SDK we need to connect each {@link #TalendEntityChild} to
     * the next {@link #TalendEntity} in the flow in order to make visible in the lineage navigator
     */
    public List<Entity> map() {

        List<Entity> output = new ArrayList<Entity>();

        if (this.navigatorNodes.size() > 0) {
            for (NavigatorNode navigatorNode : this.navigatorNodes) {
                // Create the parent entity
                TalendEntity parentEntity = mapToParentEntity(navigatorNode.getName());
                // Connect the parent entity to its input/output entities
                connectParentEntity(parentEntity, navigatorNode.getName(), navigatorNode.getInputNodes(),
                        navigatorNode.getOutputNodes());
                // Create the children entities (schema elements)
                List<TalendEntityChild> childrenEntities = mapToChildrenEntities(navigatorNode.getSchema(),
                        parentEntity.getName());
                // Connect the children entities to their parent
                connectchildrenToParent(parentEntity, childrenEntities);
                // Connect the children entities to their target output
                connectChildrenTotaget(navigatorNode.getOutputNodes(), navigatorNode.getName(), childrenEntities);
                // Add parent entity to the output
                output.add(parentEntity);
                // Add children entities to output
                output.addAll(childrenEntities);
            }
            return output;
        } else {
            // TODO
            return null;
        }
    }

    public TalendEntity mapToParentEntity(String componentName) {

        // Create the parent entity representing a navigator node
        // Important : each Talend Entity is identified by NameSpace + ComponentName + TALEND_JOB_ID
        TalendEntity talendEntity = new TalendEntity(CLOUDERA_NAVIGATOR_APPLICATION_NAMESPACE, getJobId(), componentName);
        talendEntity.setDescription(ENTITY_DESCRIPTION);
        talendEntity.setLink(ENTITY_LINK);

        return talendEntity;
    }

    public List<TalendEntityChild> mapToChildrenEntities(Map<String, String> schema, String ParentEntityName) {

        List<TalendEntityChild> output = new ArrayList<TalendEntityChild>();
        for (Entry<String, String> entry : schema.entrySet()) {
            TalendEntityChild talendEntityChild = new TalendEntityChild(CLOUDERA_NAVIGATOR_APPLICATION_NAMESPACE, getJobId(),
                    ParentEntityName, entry.getKey(), entry.getValue());
            output.add(talendEntityChild);
        }
        return output;
    }

    /*
     * Connect schema entities to the parent entity using CHILD -> PARENT relation
     */
    public void connectchildrenToParent(TalendEntity parentEntity, List<TalendEntityChild> children) {
        for (TalendEntityChild child : children) {
            child.setParent(parentEntity);
        }
    }

    /*
     * Connects a parent entity to its input/output using SOURCE -> TARGET & TARGET -> SOURCE relations
     */
    public void connectParentEntity(TalendEntity parent, String componentName, List<String> inputComponents,
            List<String> outputComponents) {
        // The Talend studio component is an Input component and is the first component in a subprocess
        if (inputComponents.size() == 0) {
            // generate the id of the component to connect to
            String id = CustomIdGenerator.generateIdentity(DATASET_MARKER, CLOUDERA_NAVIGATOR_APPLICATION_NAMESPACE, getJobId(),
                    componentName);
            parent.addPreviousEntity(id);
        } else {
            for (String input : inputComponents) {
                // generate the id of the component to connect to
                String id = CustomIdGenerator.generateIdentity(CLOUDERA_NAVIGATOR_APPLICATION_NAMESPACE, getJobId(), input);
                parent.addPreviousEntity(id);
            }
        }
        if (outputComponents.size() == 0) {
            // generate the id of the component to connect to
            String id = CustomIdGenerator.generateIdentity(DATASET_MARKER, CLOUDERA_NAVIGATOR_APPLICATION_NAMESPACE, getJobId(),
                    componentName);
            parent.addNextEntity(id);
        } else {
            for (String output : outputComponents) {
                // generate the id of the component to connect to
                String id = CustomIdGenerator.generateIdentity(CLOUDERA_NAVIGATOR_APPLICATION_NAMESPACE, getJobId(), output);
                parent.addNextEntity(id);
            }
        }
        LOG.debug(componentName + ": " + parent); //$NON-NLS-1$
    }

    /*
     * Connect children to their target entity
     */
    public void connectChildrenTotaget(List<String> outputComponents, String nodeName, List<TalendEntityChild> children) {
        for (TalendEntityChild talendEntityChild : children) {
            if (outputComponents.size() == 0) {
                String targetComponentId = CustomIdGenerator.generateIdentity(DATASET_MARKER,
                        CLOUDERA_NAVIGATOR_APPLICATION_NAMESPACE, getJobId(), nodeName);
                talendEntityChild.addTarget(targetComponentId);
            } else {
                for (String outputComponent : outputComponents) {
                    String targetComponentId = CustomIdGenerator.generateIdentity(CLOUDERA_NAVIGATOR_APPLICATION_NAMESPACE,
                            getJobId(), outputComponent);
                    talendEntityChild.addTarget(targetComponentId);
                }
            }
        }
    }

    public String getJobId() {
        return jobId;
    }

}
