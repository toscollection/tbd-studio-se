package org.talend.cloudera.navigator.api.entity;

import java.util.ArrayList;
import java.util.List;

import org.talend.cloudera.navigator.api.util.GeneratorID;

import com.cloudera.nav.sdk.model.SourceType;
import com.cloudera.nav.sdk.model.annotations.MClass;
import com.cloudera.nav.sdk.model.annotations.MProperty;
import com.cloudera.nav.sdk.model.annotations.MRelation;
import com.cloudera.nav.sdk.model.entities.DatasetField;
import com.cloudera.nav.sdk.model.entities.EndPointProxy;
import com.cloudera.nav.sdk.model.entities.EntityType;
import com.cloudera.nav.sdk.model.relations.RelationRole;

/*
 * Represents Talend schema element (column) as a Cloudera Navigator entity
 */
@MClass(model = "talend")
public class TalendEntityChild extends DatasetField {

    private String parentEntityId;

    private List<String> targetEntitiesId;

    private String entityId;

    @MProperty
    private String link;

    @MRelation(role = RelationRole.PARENT)
    private TalendEntity parent;

    @MRelation(role = RelationRole.TARGET)
    private List<EndPointProxy> targets;

    public TalendEntityChild(String jobId, String parent, String name, String type) {
        this.entityId = GeneratorID.generateEntityChildID(jobId, parent, name);
        setName(name);
        setNamespace(GeneratorID.CLOUDERA_NAVIGATOR_APPLICATION_NAMESPACE);
        setDataType(type);
        this.targetEntitiesId = new ArrayList<String>();
        this.targets = new ArrayList<EndPointProxy>();
    }

    @Override
    public String generateId() {
        return getEntityId();
    }

    @Override
    public EntityType getEntityType() {
        return EntityType.FIELD;
    }

    @Override
    public SourceType getSourceType() {
        return SourceType.PLUGIN;
    }

    public String getLink() {
        return link;
    }

    public void setLink(String link) {
        this.link = link;
    }

    public TalendEntity getParent() {
        return parent;
    }

    public void setParent(TalendEntity parent) {
        setParentEntityId(parent.generateId());
        this.parent = parent;
    }

    public void addTarget(String targetId) {
        this.targetEntitiesId.add(targetId);
        EndPointProxy endpointProxy = new EndPointProxy(targetId, SourceType.PLUGIN, EntityType.OPERATION_EXECUTION);
        this.targets.add(endpointProxy);
    }

    @Override
    public String toString() {
        return getParentEntityId() + "__" + getName() + " --->" + targetEntitiesId;
    }

    public String getParentEntityId() {
        return parentEntityId;
    }

    public void setParentEntityId(String parentEntityId) {
        this.parentEntityId = parentEntityId;
    }

    public String getEntityId() {
        return entityId;
    }

    public List<String> getTargetEntitiesId() {
        return targetEntitiesId;
    }

    public void setTargetEntitiesId(List<String> targetEntitiesId) {
        this.targetEntitiesId = targetEntitiesId;
    }

    public List<EndPointProxy> getTargets() {
        return targets;
    }
}
