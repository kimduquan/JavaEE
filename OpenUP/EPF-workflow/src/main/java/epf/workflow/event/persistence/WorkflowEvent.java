package epf.workflow.event.persistence;

import jakarta.nosql.mapping.Column;
import jakarta.nosql.mapping.Id;
import jakarta.nosql.mapping.MappedSuperclass;

/**
 * @author PC
 *
 */
@MappedSuperclass
public class WorkflowEvent {

	/**
	 * 
	 */
	@Id
	private String id;
	
	/**
	 * 
	 */
	@Column
	private String source;
	
	/**
	 * 
	 */
	@Column
	private String type;
	
	/**
	 * 
	 */
	@Column
	private String workflowDefinition;
	
	/**
	 * 
	 */
	@Column
	private String workflowInstance;

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getSource() {
		return source;
	}

	public void setSource(String source) {
		this.source = source;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getWorkflowDefinition() {
		return workflowDefinition;
	}

	public void setWorkflowDefinition(String workflowDefinition) {
		this.workflowDefinition = workflowDefinition;
	}

	public String getWorkflowInstance() {
		return workflowInstance;
	}

	public void setWorkflowInstance(String workflowInstance) {
		this.workflowInstance = workflowInstance;
	}
}
