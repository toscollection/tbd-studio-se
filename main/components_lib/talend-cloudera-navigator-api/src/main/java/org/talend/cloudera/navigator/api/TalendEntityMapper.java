package org.talend.cloudera.navigator.api;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import com.cloudera.nav.sdk.model.entities.Entity;

/*
 * Class to map studio components into cloudera navigator connected entities
 * The entities will be connected to input and output datatsets/HDFS folders
 */
public class TalendEntityMapper {
	
	private static final String ENTITY_NAME_PREFIX = "TLD ";
	private static final String ENTITY_DESCRIPTION = "Talend Component";
	private static final String ENTITY_LINK = "http://www.talend.com/";
	private static final String CLOUDERA_NAVIGATOR_APPLICATION_NAMESPACE="Talend";
	
	private Map<String, Map<String, String>> studioComponents;
	private String flowInputId;
	private String flowOutputId;
	private String talendJobId;
	
	public TalendEntityMapper(String flowInputId, String flowOutputId, Map<String, Map<String, String>> studioComponents){
		this.studioComponents = studioComponents;
		this.flowInputId = flowInputId;
		this.flowOutputId = flowOutputId;
		// Use timestamp in the entities names to create unique entities
		this.talendJobId = "_" + System.currentTimeMillis();
	}
	
	/*
	 * returns a list of Cloudera navigator entities ready to be written to Cloudera Navigator
	 * Calls methods to create all of the entities and to connect them together
	 * 
	 * Order is very important here as we need to connect the entities in the same order 
	 * used in Talend studio job.
	 * 
	 * Each Talend studio component is mapped into a {@link #TalendEntity}
	 * Each Talend studio schema element (column name + type) of each component is mapped into a {@link #TalendEntityChild}
	 * 
	 * Each {@link #TalendEntity} can have multiple {@link #TalendEntityChild} to reflect the Talend studio component's schema
	 * 
	 * {@link #TalendEntity} are linked together to form the flow
	 * 
	 * Special attention to the first and the last {@link #TalendEntity} as these need to be connected to flow's input/output
	 * 
	 * Note : Due to limitations in the Cloudera navigator API/SDK we need to connect each {@link #TalendEntityChild} to 
	 * the next {@link #TalendEntity} in the flow in order to make visible in the lineage navigator
	 * 
	 */
	public List<Entity> map(){
		
		if(this.studioComponents.keySet().size() > 0){
			// output list to hold all of the entities
			List<Entity> output = new ArrayList<Entity>();
			
			// list of parent entities (to preserve order)
			List<TalendEntity> orderedParentEntities = new ArrayList<TalendEntity>();
			
			// list of children lists (to preserve order)
			List<List<TalendEntityChild>> orderedChildrenLists = new ArrayList<List<TalendEntityChild>>();
			
			for(Entry<String, Map<String, String>> entry : studioComponents.entrySet()){
				// Map each studio component into a parent entity
				TalendEntity parentEntity = mapToParentEntity(entry.getKey());
				// Map each studio component schema elements into children entities
				List<TalendEntityChild> childrenEntities = mapToChildrenEntities(entry.getKey(), entry.getValue());
				// Connect children entities to their parent entity
				connectchildrenToParent(parentEntity, childrenEntities);
				// Add parent to ordered list of parent entities for later processing
				orderedParentEntities.add(parentEntity);
				// Add the children list for for later processing
				orderedChildrenLists.add(childrenEntities);
			}
			
			// Connect Parents to each other
			connectParents(orderedParentEntities);
			// Connect children to their target (next entity in the flow)
			connectChildrenTotaget(orderedParentEntities, orderedChildrenLists);
			
			// Connect flow's input & output
			setFlowInput(orderedParentEntities.get(0));
			setFlowOutput(orderedParentEntities.get(orderedParentEntities.size() - 1 ), orderedChildrenLists.get(orderedChildrenLists.size() - 1));
			
			// Add all parents & children to the output list
			output.addAll(orderedParentEntities);
			for(List<TalendEntityChild> childrenList : orderedChildrenLists){
				output.addAll(childrenList);
			}
			
			return output;
		} else{
			//TODO
			return null;
		}
	}
	
	public TalendEntity mapToParentEntity(String studioComponentName){
		TalendEntity talendEntity = new TalendEntity(CLOUDERA_NAVIGATOR_APPLICATION_NAMESPACE, studioComponentName + this.talendJobId);
		talendEntity.setName(ENTITY_NAME_PREFIX + studioComponentName + this.talendJobId);
		talendEntity.setDescription(ENTITY_DESCRIPTION);
		talendEntity.setLink(ENTITY_LINK);
		return talendEntity;
	}
	
	public List<TalendEntityChild> mapToChildrenEntities(String parent, Map<String, String> studioComponentSchema){
		List<TalendEntityChild> output = new ArrayList<TalendEntityChild>();
		for(Entry<String, String> entry : studioComponentSchema.entrySet()){
			TalendEntityChild talendChildEntity = new TalendEntityChild(CLOUDERA_NAVIGATOR_APPLICATION_NAMESPACE,
					entry.getKey() + "_" + parent  + this.talendJobId, entry.getValue());
			output.add(talendChildEntity);
		}
		return output;
	}
	
	/*
	 * Connect schema entities to the parent entity using CHILD -> PARENT relation
	 */
	public void connectchildrenToParent(TalendEntity parent, List<TalendEntityChild> children){
		for(TalendEntityChild child : children){
			child.setParent(parent);
		}
	}
	
	/*
	 * Connects parents to each other using SOURCE -> TARGET & TARGET -> SOURCE relations
	 */
	public void connectParents(List<TalendEntity> parents){
		// Connect 0 to 1 & n to n-1
		if(parents.size() > 1){
			parents.get(0).setnextTalendComponent(parents.get(1).generateId());
			parents.get(parents.size() - 1).setPreviousTalendComponent(parents.get(parents.size() - 2).generateId());
		}
		// Connect i to j / 0 < i,j < n
		for(int i=1; i < parents.size() - 1; i++){
			parents.get(i).setPreviousTalendComponent(parents.get(i-1).generateId());
			parents.get(i).setnextTalendComponent(parents.get(i+1).generateId());
		}
	}
	
	/*
	 * Connect children to their target entity
	 */
	public void connectChildrenTotaget(List<TalendEntity> parents, List<List<TalendEntityChild>> childrenLists){
		for(int i=0; i < parents.size() - 1; i++){
			for(TalendEntityChild child : childrenLists.get(i)){
				child.setTarget(parents.get(i+1).generateId());
			}
		}
	}
	
	/*
	 * Set the flow's input (Dataset/HDFS dir)
	 */
	public void setFlowInput(TalendEntity inputEntity){
		inputEntity.setPreviousTalendComponent(getFlowInputId());
	}
	
	/*
	 * Set the flow's output (Dataset/HDFS dir)
	 */
	public void setFlowOutput(TalendEntity outputEntity, List<TalendEntityChild> outputChildren){
		outputEntity.setnextTalendComponent(getFlowOutputId());
		for(TalendEntityChild child : outputChildren){
			child.setTarget(getFlowOutputId());
		}
	}

	public String getFlowInputId() {
		return flowInputId;
	}

	public void setFlowInputId(String flowInputId) {
		this.flowInputId = flowInputId;
	}

	public String getFlowOutputId() {
		return flowOutputId;
	}

	public void setFlowOutputId(String flowOutputId) {
		this.flowOutputId = flowOutputId;
	}

	public Map<String, Map<String, String>> getStudioComponents() {
		return studioComponents;
	}
	
}
