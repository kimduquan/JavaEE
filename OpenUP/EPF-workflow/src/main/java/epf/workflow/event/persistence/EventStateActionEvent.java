package epf.workflow.event.persistence;

import jakarta.nosql.mapping.Column;
import jakarta.nosql.mapping.Entity;

/**
 * @author PC
 *
 */
@Entity("Event")
public class EventStateActionEvent extends WorkflowEvent {
	
	/**
	 * 
	 */
	@Column
	private String eventState;
	
	/**
	 * 
	 */
	@Column
	private Integer onEventsDefinition;
	
	/**
	 * 
	 */
	@Column
	private String actionDefinition;

	public String getEventState() {
		return eventState;
	}

	public void setEventState(String eventState) {
		this.eventState = eventState;
	}

	public Integer getOnEventsDefinition() {
		return onEventsDefinition;
	}

	public void setOnEventsDefinition(Integer onEventsDefinition) {
		this.onEventsDefinition = onEventsDefinition;
	}

	public String getActionDefinition() {
		return actionDefinition;
	}

	public void setActionDefinition(String actionDefinition) {
		this.actionDefinition = actionDefinition;
	}
}
