package org.talend.cloudera.navigator.api;

import java.util.ArrayList;
import java.util.List;

import com.cloudera.nav.sdk.model.SourceType;
import com.cloudera.nav.sdk.model.annotations.MClass;
import com.cloudera.nav.sdk.model.annotations.MRelation;
import com.cloudera.nav.sdk.model.entities.EndPointProxy;
import com.cloudera.nav.sdk.model.entities.EntityType;
import com.cloudera.nav.sdk.model.relations.RelationRole;

@MClass(model = "talend")
public class TalendInputOutputEntity extends TalendEntity{
	
	private List<String> previousEntitiesId;
	private List<String> nextEntitiesId;
	
	@MRelation(role = RelationRole.SOURCE)
	private List<EndPointProxy> sourceProxies;
	
	@MRelation(role = RelationRole.TARGET)
	private List<EndPointProxy> targetProxies;

	public TalendInputOutputEntity(String namespace, String jobId, String componentName) {
		super(namespace, jobId, componentName);
		sourceProxies = new ArrayList<EndPointProxy>();
	    previousEntitiesId = new ArrayList<String>();
	    targetProxies = new ArrayList<EndPointProxy>();
	    nextEntitiesId = new ArrayList<String>();
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
	
	@Override
	public String toString() {
		return this.previousEntitiesId + "---> " + getName() + " (" + getEntityId() + ")" + " --->" + this.nextEntitiesId;
	}
}
