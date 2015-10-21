package org.talend.cloudera.navigator.api.entity;

import org.talend.cloudera.navigator.api.GeneratorID;

import com.cloudera.nav.sdk.model.SourceType;
import com.cloudera.nav.sdk.model.annotations.MClass;
import com.cloudera.nav.sdk.model.annotations.MRelation;
import com.cloudera.nav.sdk.model.entities.Dataset;
import com.cloudera.nav.sdk.model.entities.Entity;
import com.cloudera.nav.sdk.model.entities.EntityType;
import com.cloudera.nav.sdk.model.entities.HdfsEntity;
import com.cloudera.nav.sdk.model.relations.RelationRole;
import com.google.common.base.Preconditions;

@MClass(model = "talend_dataset")
public class TalendDataset extends Dataset {

    private static org.apache.log4j.Logger LOG = org.apache.log4j.Logger.getLogger(TalendDataset.class);

    @MRelation(role = RelationRole.PHYSICAL)
    private Entity dataContainer;

    private String componentName;

    private String generatedId;

    public TalendDataset(String name, String componentName, String jobId) {
        super();
        setSourceType(SourceType.PLUGIN);
        setNamespace(GeneratorID.CLOUDERA_NAVIGATOR_APPLICATION_NAMESPACE);
        setName(name);
        this.componentName = componentName;

        this.generatedId = GeneratorID.generateDatasetID(jobId, componentName);
        LOG.debug("Dataset:" + componentName + " " + jobId + ": " + generatedId); //$NON-NLS-1$ //$NON-NLS-2$ //$NON-NLS-3$
    }

    @Override
    public String generateId() {
        return generatedId;
    }

    public String getComponentName() {
        return this.componentName;
    }

    public Entity getDataContainer() {
        return dataContainer;
    }

    public void setDataContainer(HdfsEntity hdfsDir) {
        Preconditions.checkArgument(hdfsDir.getEntityType() == EntityType.DIRECTORY);
        this.dataContainer = hdfsDir;
    }
}
