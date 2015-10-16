package org.talend.cloudera.navigator.api;

import com.cloudera.nav.sdk.model.CustomIdGenerator;
import com.cloudera.nav.sdk.model.SourceType;
import com.cloudera.nav.sdk.model.annotations.MClass;
import com.cloudera.nav.sdk.model.annotations.MProperty;
import com.cloudera.nav.sdk.model.annotations.MRelation;
import com.cloudera.nav.sdk.model.entities.EndPointProxy;
import com.cloudera.nav.sdk.model.entities.Entity;
import com.cloudera.nav.sdk.model.entities.EntityType;
import com.cloudera.nav.sdk.model.relations.RelationRole;

/*
 * Represents Talend components as a Cloudera Navigator entity
 */
@MClass(model = "talend")
public class TalendEntity extends Entity {
	

	private String previousEntityId;
	private String nextEntityId;
	
	@MProperty
	private String link;
	
	@MRelation(role = RelationRole.SOURCE)
	private EndPointProxy sourceProxy;
	
	@MRelation(role = RelationRole.TARGET)
	private EndPointProxy targetProxy;
	
	public TalendEntity(String namespace, String componentName) {
		setName(componentName);
	    setNamespace(namespace);
	  }
	
	@Override
	public String generateId() {
		return CustomIdGenerator.generateIdentity(getNamespace(), getName());
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
	
	public void setPreviousTalendComponent(String previousComponentId){
		setPreviousEntityId(previousComponentId);
		this.sourceProxy = new EndPointProxy(previousComponentId, SourceType.PLUGIN, EntityType.OPERATION_EXECUTION);
	}
	
	public void setnextTalendComponent(String nextComponentId){
		setNextEntityId(nextComponentId);
		this.targetProxy = new EndPointProxy(nextComponentId, SourceType.PLUGIN, EntityType.OPERATION_EXECUTION);
	}

	public EndPointProxy getSourceProxy() {
		return sourceProxy;
	}

	public void setSourceProxy(EndPointProxy sourceProxy) {
		this.sourceProxy = sourceProxy;
	}

	public EndPointProxy getTargetProxy() {
		return targetProxy;
	}

	public void setTargetProxy(EndPointProxy targetProxy) {
		this.targetProxy = targetProxy;
	}


	public String getPreviousEntityId() {
		return previousEntityId;
	}

	public void setPreviousEntityId(String previousEntityId) {
		this.previousEntityId = previousEntityId;
	}

	public String getNextEntityId() {
		return nextEntityId;
	}

	public void setNextEntityId(String nextEntityId) {
		this.nextEntityId = nextEntityId;
	}

	@Override
	public String toString() {
		return getPreviousEntityId() + "<->" + getName() + "<->" + getNextEntityId();
	}

}
