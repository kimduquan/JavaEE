package epf.workflow;

import jakarta.nosql.Column;
import jakarta.nosql.Entity;
import jakarta.nosql.Id;

/**
 * @author PC
 *
 */
@Entity
public class WorkflowInstance {
	
	/**
	 * 
	 */
	@Column
	private String workflowDefinition;
	
	/**
	 * 
	 */
	@Id
	private String id;
	
	/**
	 * 
	 */
	@Column
	private WorkflowState state;

	public String getWorkflowDefinition() {
		return workflowDefinition;
	}

	public void setWorkflowDefinition(String workflowDefinition) {
		this.workflowDefinition = workflowDefinition;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public WorkflowState getState() {
		return state;
	}

	public void setState(WorkflowState state) {
		this.state = state;
	}
}
