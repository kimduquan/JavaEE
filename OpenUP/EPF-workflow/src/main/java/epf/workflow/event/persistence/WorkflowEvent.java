package epf.workflow.event.persistence;

import epf.workflow.event.Event;
import jakarta.nosql.mapping.Column;
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
	private String name = getClass().getSimpleName();
	
	/**
	 * 
	 */
	@Column("workflowdefinition")
	private String workflowDefinition;
	
	/**
	 * 
	 */
	@Column("eventdefinition")
	private String eventDefinition;

	public String getWorkflowDefinition() {
		return workflowDefinition;
	}

	public void setWorkflowDefinition(String workflowDefinition) {
		this.workflowDefinition = workflowDefinition;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getEventDefinition() {
		return eventDefinition;
	}

	public void setEventDefinition(String eventDefinition) {
		this.eventDefinition = eventDefinition;
	}
}
