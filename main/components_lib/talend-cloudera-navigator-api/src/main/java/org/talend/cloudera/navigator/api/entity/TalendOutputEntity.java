package org.talend.cloudera.navigator.api.entity;

import java.util.ArrayList;
import java.util.List;

import org.talend.cloudera.navigator.api.util.GeneratorID;

import com.cloudera.nav.sdk.model.SourceType;
import com.cloudera.nav.sdk.model.annotations.MClass;
import com.cloudera.nav.sdk.model.annotations.MRelation;
import com.cloudera.nav.sdk.model.entities.EndPointProxy;
import com.cloudera.nav.sdk.model.entities.EntityType;
import com.cloudera.nav.sdk.model.relations.RelationRole;

/*
 * Cloudera navigator entity to represent a Talend output component
 */
@MClass(model = "talend")
public class TalendOutputEntity extends TalendEntity {

    private List<String> previousEntitiesId;

    @MRelation(role = RelationRole.SOURCE)
    private List<EndPointProxy> sourceProxies;

    public TalendOutputEntity(String namespace, String jobId, String componentName) {
        super(namespace, jobId, componentName);
        sourceProxies = new ArrayList<EndPointProxy>();
        previousEntitiesId = new ArrayList<String>();
    }

    public void addPreviousEntity(String entityId) {
        this.previousEntitiesId.add(entityId);
        EndPointProxy endpointProxy = new EndPointProxy(entityId, SourceType.PLUGIN, EntityType.OPERATION_EXECUTION);
        this.sourceProxies.add(endpointProxy);
    }

    public List<String> getPreviousEntitiesId() {
        return previousEntitiesId;
    }

    public List<EndPointProxy> getSourceProxies() {
        return sourceProxies;
    }

    @Override
    public String toString() {
        return this.previousEntitiesId + "---> " + getName() + " (" + getEntityId() + ")";
    }

    /**
     * Connects a parent entity to its input using TARGET -> SOURCE relations
     */
    @Override
    public void connectToEntity(String componentName, String jobId, List<String> inputs, List<String> outputs) {
        for (String input : inputs) {
            // generate the id of the component to connect to
            String id = GeneratorID.generateNodeID(jobId, input);
            this.addPreviousEntity(id);
        }
    }
}
