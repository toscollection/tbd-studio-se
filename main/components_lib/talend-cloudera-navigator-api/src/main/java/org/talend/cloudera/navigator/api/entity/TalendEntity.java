package org.talend.cloudera.navigator.api.entity;

import java.util.List;

import org.talend.cloudera.navigator.api.util.GeneratorID;

import com.cloudera.nav.sdk.model.SourceType;
import com.cloudera.nav.sdk.model.annotations.MClass;
import com.cloudera.nav.sdk.model.annotations.MProperty;
import com.cloudera.nav.sdk.model.entities.Entity;
import com.cloudera.nav.sdk.model.entities.EntityType;

/*
 * Represents Talend components as a Cloudera Navigator entity
 */
@MClass(model = "talend")
public abstract class TalendEntity extends Entity {

    private String entityId;

    private final String jobId;

    @MProperty
    private String link;

    public TalendEntity(String jobId, String componentName) {
        this.entityId = GeneratorID.generateEntityID(jobId, componentName);
        setName(componentName);
        setNamespace(GeneratorID.CLOUDERA_NAVIGATOR_APPLICATION_NAMESPACE);
        this.jobId = jobId;
    }

    @Override
    public String generateId() {
        return getEntityId();
    }

    @Override
    public EntityType getEntityType() {
        return EntityType.OPERATION_EXECUTION;
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

    public String getEntityId() {
        return entityId;
    }

    public String getJobId() {
        return this.jobId;
    }

    /**
     * Connects a the talend entity to its input/output using relations
     */
    public abstract void connectToEntity(List<String> inputs, List<String> outputs);
}
