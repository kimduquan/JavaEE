package epf.workflow.event.persistence;

import jakarta.nosql.mapping.Column;
import jakarta.nosql.Entity;

/**
 * @author PC
 *
 */
@Entity
public class SwitchStateEvent extends WorkflowEvent {
	
	/**
	 * 
	 */
	@Column
	private String eventConditions;

	public String getEventConditions() {
		return eventConditions;
	}

	public void setEventConditions(String eventConditions) {
		this.eventConditions = eventConditions;
	}
}
