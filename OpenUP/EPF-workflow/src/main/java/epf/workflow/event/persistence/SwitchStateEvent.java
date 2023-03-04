package epf.workflow.event.persistence;

import jakarta.nosql.mapping.Column;
import jakarta.nosql.mapping.Entity;

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
	private String switchState;
	
	/**
	 * 
	 */
	@Column
	private String eventConditions;

	public String getSwitchState() {
		return switchState;
	}

	public void setSwitchState(String switchState) {
		this.switchState = switchState;
	}

	public String getEventConditions() {
		return eventConditions;
	}

	public void setEventConditions(String eventConditions) {
		this.eventConditions = eventConditions;
	}
}
