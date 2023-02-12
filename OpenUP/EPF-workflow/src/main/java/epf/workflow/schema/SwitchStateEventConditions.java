package epf.workflow.schema;

/**
 * @author PC
 *
 */
public class SwitchStateEventConditions extends SwitchStateConditions {

	/**
	 * 
	 */
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
