package org.talend.cloudera.navigator.api;

import java.util.ArrayList;
import java.util.List;

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
 * Represents Talend components as a Cloudera Navigator entities
 */
@MClass(model = "talend")
public class TalendEntity extends Entity {
	
	private List<String> previousEntitiesId;
	private List<String> nextEntitiesId;
	private String entityId;
	
	@MProperty
	private String link;
	
	@MRelation(role = RelationRole.SOURCE)
	private List<EndPointProxy> sourceProxies;
	
	@MRelation(role = RelationRole.TARGET)
	private List<EndPointProxy> targetProxies;
	
	public TalendEntity(String namespace, String jobId, String componentName) {
		this.entityId = CustomIdGenerator.generateIdentity(namespace, jobId, componentName);
		setName(componentName);
	    setNamespace(namespace);
	    sourceProxies = new ArrayList<EndPointProxy>();
	    previousEntitiesId = new ArrayList<String>();
	    targetProxies = new ArrayList<EndPointProxy>();
	    nextEntitiesId = new ArrayList<String>();
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
	
	public void addNextEntity(String entityId){
		this.nextEntitiesId.add(entityId);
		EndPointProxy endpointProxy = new EndPointProxy(entityId, SourceType.PLUGIN, EntityType.OPERATION_EXECUTION);
		this.targetProxies.add(endpointProxy);
	}
	
	public void addPreviousEntity(String entityId){
		this.previousEntitiesId.add(entityId);
		EndPointProxy endpointProxy = new EndPointProxy(entityId, SourceType.PLUGIN, EntityType.OPERATION_EXECUTION);
		this.sourceProxies.add(endpointProxy);
	}
	
	@Override
	public String toString() {
		return this.previousEntitiesId + "<->" + getName() + "<->" + this.nextEntitiesId;
	}

	public String getEntityId() {
		return entityId;
	}

	public List<EndPointProxy> getSourceProxies() {
		return sourceProxies;
	}

	public void setSourceProxies(List<EndPointProxy> sourceProxies) {
		this.sourceProxies = sourceProxies;
	}

	public List<EndPointProxy> getTargetProxies() {
		return targetProxies;
	}

	public void setTargetProxies(List<EndPointProxy> targetProxies) {
		this.targetProxies = targetProxies;
	}

}
