package org.talend.cloudera.navigator.api;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import com.cloudera.nav.sdk.model.CustomIdGenerator;
import com.cloudera.nav.sdk.model.entities.Entity;

/*
 * Class to map studio components into cloudera navigator connected entities
 */
public class TalendEntityMapper {
	
	private static final String ENTITY_DESCRIPTION = "Talend Component";
	private static final String ENTITY_LINK = "http://www.talend.com/";
	public static final String CLOUDERA_NAVIGATOR_APPLICATION_NAMESPACE = "Talend";
	public static final String DATASET_MARKER="DATASET_";
	public static final String[] FILE_INPUT_OUTPUT_COMPONENT_PREFIXS= {"tFile", "tHDFS", "tParquet", "tS3", "tAvro"};
	
	private List<NavigatorNode> navigatorNodes;
	// The mapper uses a jobId to create unique entities in Cloudera navigator
	private String jobId;
	private StringBuilder debugStringBuilder;
	
	public TalendEntityMapper(List<NavigatorNode> navigatorNodes, String jobId){
		this.navigatorNodes = navigatorNodes;
		this.jobId = jobId;
		this.debugStringBuilder = new StringBuilder();
	}
	
	/*
	 * returns a list of Cloudera navigator entities ready to be written to Cloudera Navigator
	 * Calls methods to create all of the entities and to connect them together
	 * 
	 * Map Navigator nodes to the equivalent Talend entities.
	 * Processing components are mapped to TalendInputOutputEntity
	 * Input/output file components are mapped to TalendInputOutputEntity with a dataSet as input/output
	 * Input/output components (LogRow, FixedFlow, ...) are mapped to TalendInputEntity/TalendOutputEntity
	 * 
	 * Connect them to their SOURE/TARGETS and their children
	 * 
	 * ID = NameSpace + TALEND_JOB_ID + ComponentName 
	 * 
	 * Note : Due to limitations in the Cloudera navigator API/SDK we need to connect each {@link #TalendEntityChild} to 
	 * the next {@link #TalendEntity} in the flow in order to make visible in the lineage navigator
	 * 
	 */
	public List<Entity> map(){
		
		List<Entity> output = new ArrayList<Entity>();
		
		if(this.navigatorNodes.size() > 0){
			for(NavigatorNode navigatorNode : this.navigatorNodes){
				if((navigatorNode.getInputNodes().size()!=0 && navigatorNode.getOutputNodes().size()!=0) 
						|| (navigatorNode.getInputNodes().size()==0 && IsFileInputOutputComponent(navigatorNode.getName())) 
						||(navigatorNode.getOutputNodes().size()==0 && IsFileInputOutputComponent(navigatorNode.getName()))){
					// Processing || FileInput/Output Component 
					TalendInputOutputEntity parentEntity = new TalendInputOutputEntity(CLOUDERA_NAVIGATOR_APPLICATION_NAMESPACE,
							getJobId(), navigatorNode.getName());
					setEntityMetadata(parentEntity);
					// Create the children entities (schema elements)
					List<TalendEntityChild> childrenEntities = mapToChildrenEntities(navigatorNode.getSchema(), parentEntity.getName());
					// Connect the children entities to their parent
					connectchildrenToParent(parentEntity, childrenEntities);
					// Connect the children entities to their target output
					connectChildrenTotaget(navigatorNode, childrenEntities);
					// Connect the parent entity to its input/output entities
					connectParentEntity(parentEntity, navigatorNode);
					// Add parent entity to the output
					output.add(parentEntity);
					// Add children entities to output
					output.addAll(childrenEntities);
					// Add to debugString
					addToDebugString(parentEntity, childrenEntities);
				} else if(navigatorNode.getInputNodes().size()==0 && navigatorNode.getOutputNodes().size()!=0){
					// Input component
					TalendInputEntity parentEntity = new TalendInputEntity(CLOUDERA_NAVIGATOR_APPLICATION_NAMESPACE,
							getJobId(), navigatorNode.getName());
					setEntityMetadata(parentEntity);
					// Create the children entities (schema elements)
					List<TalendEntityChild> childrenEntities = mapToChildrenEntities(navigatorNode.getSchema(), parentEntity.getName());
					// Connect the children entities to their parent
					connectchildrenToParent(parentEntity, childrenEntities);
					// Connect the children entities to their target output
					connectChildrenTotaget(navigatorNode, childrenEntities);
					// Connect the parent entity to its input/output entities
					connectParentEntity(parentEntity, navigatorNode);
					// Add parent entity to the output
					output.add(parentEntity);
					// Add children entities to output
					output.addAll(childrenEntities);
					// Add to debugString
					addToDebugString(parentEntity, childrenEntities);
				} else if(navigatorNode.getInputNodes().size()!=0 && navigatorNode.getOutputNodes().size()==0){
					// Input component
					TalendOutputEntity parentEntity = new TalendOutputEntity(CLOUDERA_NAVIGATOR_APPLICATION_NAMESPACE,
							getJobId(), navigatorNode.getName());
					setEntityMetadata(parentEntity);
					// Create the children entities (schema elements)
					List<TalendEntityChild> childrenEntities = mapToChildrenEntities(navigatorNode.getSchema(), parentEntity.getName());
					// Connect the children entities to their parent
					connectchildrenToParent(parentEntity, childrenEntities);
					// Connect the children entities to their target output
					connectChildrenTotaget(navigatorNode, childrenEntities);
					// Connect the parent entity to its input/output entities
					connectParentEntity(parentEntity, navigatorNode);
					// Add parent entity to the output
					output.add(parentEntity);
					// Add children entities to output
					output.addAll(childrenEntities);
					// Add to debugString
					addToDebugString(parentEntity, childrenEntities);
				}
			}
			return output;
		} else{
			//TODO
			return null;
		}
	}
	
	public void setEntityMetadata(TalendEntity talendEntity){
		talendEntity.setDescription(ENTITY_DESCRIPTION);
		talendEntity.setLink(ENTITY_LINK);
	}
	
	public List<TalendEntityChild> mapToChildrenEntities(Map<String, String> schema, String ParentEntityName){
		
		List<TalendEntityChild> output = new ArrayList<TalendEntityChild>();
		for(Entry<String, String> entry : schema.entrySet()){
			TalendEntityChild talendEntityChild = new TalendEntityChild(CLOUDERA_NAVIGATOR_APPLICATION_NAMESPACE, getJobId(),
					ParentEntityName, entry.getKey(), entry.getValue());
			setChildEntityMetadata(talendEntityChild);
			output.add(talendEntityChild);
		}
		return output;
	}
	
	public void setChildEntityMetadata(TalendEntityChild talendEntityChild){
		talendEntityChild.setDescription(ENTITY_DESCRIPTION);
		talendEntityChild.setLink(ENTITY_LINK);
	}
	
	/*
	 * Connect schema entities to the parent entity using CHILD -> PARENT relation
	 */
	public void connectchildrenToParent(TalendEntity parentEntity, List<TalendEntityChild> children){
		for(TalendEntityChild child : children){
			child.setParent(parentEntity);
		}
	}
	
	/*
	 * Connects a parent entity to its input/output using SOURCE -> TARGET & TARGET -> SOURCE relations
	 */
	public void connectParentEntity(TalendInputOutputEntity parent, NavigatorNode navigatorNode){
		// File Input components should be linked with a dataset
		if(navigatorNode.getInputNodes().size()==0 && IsFileInputOutputComponent(navigatorNode.getName())){
			String id = CustomIdGenerator.generateIdentity(DATASET_MARKER, CLOUDERA_NAVIGATOR_APPLICATION_NAMESPACE, getJobId(),
					navigatorNode.getName());
			parent.addPreviousEntity(id);
		}
		// File Output components should be linked with a dataset
		if(navigatorNode.getOutputNodes().size()==0 && IsFileInputOutputComponent(navigatorNode.getName())){
			String id = CustomIdGenerator.generateIdentity(DATASET_MARKER, CLOUDERA_NAVIGATOR_APPLICATION_NAMESPACE, getJobId(),
					navigatorNode.getName());
			parent.addNextEntity(id);
		}
		for(String input : navigatorNode.getInputNodes()){
			// generate the id of the component to connect to
			String id = CustomIdGenerator.generateIdentity(CLOUDERA_NAVIGATOR_APPLICATION_NAMESPACE, getJobId(), input);
			parent.addPreviousEntity(id);
		}
		for(String output : navigatorNode.getOutputNodes()){
			// generate the id of the component to connect to
			String id = CustomIdGenerator.generateIdentity(CLOUDERA_NAVIGATOR_APPLICATION_NAMESPACE, getJobId(), output);
			parent.addNextEntity(id);
		}
	}
	
	/*
	 * Connects a parent entity to its input/output using SOURCE -> TARGET & TARGET -> SOURCE relations
	 */
	public void connectParentEntity(TalendInputEntity parent, NavigatorNode navigatorNode){
		for(String output : navigatorNode.getOutputNodes()){
			// generate the id of the component to connect to
			String id = CustomIdGenerator.generateIdentity(CLOUDERA_NAVIGATOR_APPLICATION_NAMESPACE, getJobId(), output);
			parent.addNextEntity(id);
		}
	}
	
	/*
	 * Connects a parent entity to its input/output using SOURCE -> TARGET & TARGET -> SOURCE relations
	 */
	public void connectParentEntity(TalendOutputEntity parent, NavigatorNode navigatorNode){
		for(String input : navigatorNode.getInputNodes()){
			// generate the id of the component to connect to
			String id = CustomIdGenerator.generateIdentity(CLOUDERA_NAVIGATOR_APPLICATION_NAMESPACE, getJobId(), input);
			parent.addPreviousEntity(id);
		}
	}
	
	/*
	 * Connect children to their target entity
	 */
	public void connectChildrenTotaget(NavigatorNode navigatorNode, List<TalendEntityChild> children){
		for(TalendEntityChild talendEntityChild : children){
			// File Output children entities should be linked with a dataset
			if(navigatorNode.getOutputNodes().size()==0 && IsFileInputOutputComponent(navigatorNode.getName())){
				String targetComponentId = CustomIdGenerator.generateIdentity(DATASET_MARKER, CLOUDERA_NAVIGATOR_APPLICATION_NAMESPACE, getJobId(),
						navigatorNode.getName());
				talendEntityChild.addTarget(targetComponentId);
			// We connect the children to the component itself
			} else if(navigatorNode.getOutputNodes().size()==0 && !IsFileInputOutputComponent(navigatorNode.getName())){
				String targetComponentId = CustomIdGenerator.generateIdentity(CLOUDERA_NAVIGATOR_APPLICATION_NAMESPACE, getJobId(),
						navigatorNode.getName());
				talendEntityChild.addTarget(targetComponentId);
			}
			for(String outputComponent : navigatorNode.getOutputNodes()){
				String targetComponentId = CustomIdGenerator.generateIdentity(CLOUDERA_NAVIGATOR_APPLICATION_NAMESPACE, getJobId(), outputComponent);
				talendEntityChild.addTarget(targetComponentId);
			}
		}
	}
	
	public void addToDebugString(TalendEntity parentEntity, List<TalendEntityChild> children){
		this.debugStringBuilder.append("\n" + parentEntity.toString() + "\n");
		for(TalendEntityChild child : children){
			this.debugStringBuilder.append("\t" + child.toString() + "\n");
		}
	}
	
	public boolean IsFileInputOutputComponent(String componentName){
		for(String prefix : FILE_INPUT_OUTPUT_COMPONENT_PREFIXS){
			if(componentName.toLowerCase().startsWith(prefix.toLowerCase())){
				return true;
			}
		}
		return false;
	}
	
	@Override
	public String toString(){
		return this.debugStringBuilder.toString();
	}

	public String getJobId() {
		return jobId;
	}
	
}
