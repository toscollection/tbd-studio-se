package org.talend.cloudera.navigator.api;

import com.cloudera.nav.sdk.model.CustomIdGenerator;
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
	
	private String targetEntityId;
	
	@MProperty
	private String link;
	
	@MRelation(role = RelationRole.PARENT)
	private TalendEntity parent;
	
	@MRelation(role = RelationRole.TARGET)
	private EndPointProxy target;
	
	public TalendEntityChild(String namespace, String componentName, String type) {
		setName(componentName);
	    setNamespace(namespace);
	    setDataType(type);
	  }
	
	@Override
	public String generateId() {
		return CustomIdGenerator.generateIdentity(getNamespace(), getName());
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

	public EndPointProxy getTarget() {
		return target;
	}

	public void setTarget(String targetId) {
		setTargetEntityId(targetId);
		this.target = new EndPointProxy(targetId, SourceType.PLUGIN, EntityType.OPERATION_EXECUTION);
	}

	@Override
	public String toString() {
		return getName() + "-ooo-" + getParentEntityId() + " -***-" + getTargetEntityId();
	}

	public String getParentEntityId() {
		return parentEntityId;
	}

	public void setParentEntityId(String parentEntityId) {
		this.parentEntityId = parentEntityId;
	}

	public String getTargetEntityId() {
		return targetEntityId;
	}

	public void setTargetEntityId(String targetEntityId) {
		this.targetEntityId = targetEntityId;
	}
	
}
