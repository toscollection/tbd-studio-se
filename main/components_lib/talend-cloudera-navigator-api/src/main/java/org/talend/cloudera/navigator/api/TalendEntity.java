package org.talend.cloudera.navigator.api;

import com.cloudera.nav.sdk.model.CustomIdGenerator;
import com.cloudera.nav.sdk.model.SourceType;
import com.cloudera.nav.sdk.model.annotations.MClass;
import com.cloudera.nav.sdk.model.annotations.MProperty;
import com.cloudera.nav.sdk.model.entities.Entity;
import com.cloudera.nav.sdk.model.entities.EntityType;

/*
 * Represents Talend components as a Cloudera Navigator entity
 */
@MClass(model = "talend")
public class TalendEntity extends Entity {
	
	private String entityId;
	
	@MProperty
	private String link;
	
	public TalendEntity(String namespace, String jobId, String componentName) {
		this.entityId = CustomIdGenerator.generateIdentity(namespace, jobId, componentName);
		setName(componentName);
	    setNamespace(namespace);
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
}
