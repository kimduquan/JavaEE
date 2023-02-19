package epf.workflow.schema;

import javax.validation.constraints.NotNull;

/**
 * @author PC
 *
 */
public class SwitchStateEventConditions extends SwitchStateConditions {

	/**
	 * 
	 */
	@NotNull
	private String eventRef;
	/**
	 * 
	 */
	private Object eventDataFilter;
	
	public String getEventRef() {
		return eventRef;
	}
	public void setEventRef(String eventRef) {
		this.eventRef = eventRef;
	}
	public Object getEventDataFilter() {
		return eventDataFilter;
	}
	public void setEventDataFilter(Object eventDataFilter) {
		this.eventDataFilter = eventDataFilter;
	}
}
