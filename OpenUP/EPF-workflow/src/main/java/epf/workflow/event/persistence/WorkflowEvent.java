package epf.workflow.event.persistence;

import epf.workflow.event.Event;
import epf.workflow.event.mapping.UUIDAttributeConverter;
import jakarta.nosql.mapping.Column;
import jakarta.nosql.mapping.Convert;
import jakarta.nosql.mapping.MappedSuperclass;

/**
 * @author PC
 *
 */
@MappedSuperclass
public class WorkflowEvent extends Event {
	
	/**
	 * 
	 */
	@Column
	private String workflowDefinition;
	
	/**
	 * 
	 */
	@Column
	@Convert(UUIDAttributeConverter.class)
	private String workflowInstance;

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
