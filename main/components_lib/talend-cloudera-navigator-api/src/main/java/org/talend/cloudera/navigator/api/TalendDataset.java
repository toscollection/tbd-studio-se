package org.talend.cloudera.navigator.api;

import com.cloudera.nav.sdk.model.DatasetIdGenerator;
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

    @MRelation(role = RelationRole.PHYSICAL)
    private Entity dataContainer;

    public TalendDataset() {
        super();
        setSourceType(SourceType.PLUGIN);
        setNamespace("Talend");
    }

    @Override
    public String generateId() {
        return DatasetIdGenerator.datasetId(getDataContainer().getIdentity(), getNamespace(), getName());
    }

    public Entity getDataContainer() {
        return dataContainer;
    }

    public void setDataContainer(HdfsEntity hdfsDir) {
        Preconditions.checkArgument(hdfsDir.getEntityType() == EntityType.DIRECTORY);
        this.dataContainer = hdfsDir;
    }
}
