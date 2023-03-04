package epf.workflow.event.persistence;

import jakarta.nosql.mapping.Column;
import jakarta.nosql.mapping.Entity;

/**
 * @author PC
 *
 */
@Entity
public class EventStateEvent extends WorkflowEvent {
	
	/**
	 * 
	 */
	@Column
	private String eventState;
	
	/**
	 * 
	 */
	@Column
	private String eventDefinition;
	
	/**
	 * 
	 */
	@Column
	private Object data;

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

	public Object getData() {
		return data;
	}

	public void setData(Object data) {
		this.data = data;
	}
}
