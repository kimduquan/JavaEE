package epf.workflow.event.persistence;

import epf.naming.Naming;
import jakarta.nosql.mapping.Column;
import jakarta.nosql.mapping.Entity;

/**
 * @author PC
 *
 */
@Entity(Naming.Workflow.EVENT)
public class EventStateEvent extends WorkflowEvent {
	
	/**
	 * 
	 */
	@Column("eventstate")
	private String eventState;
	
	/**
	 * 
	 */
	@Column("eventdefinition")
	private String eventDefinition;

	public String getEventState() {
		return eventState;
	}

	public void setEventState(String eventState) {
		this.eventState = eventState;
	}

	public String getEventDefinition() {
		return eventDefinition;
	}

	public void setEventDefinition(String eventDefinition) {
		this.eventDefinition = eventDefinition;
	}
}
