package epf.workflow.schema;

import javax.validation.constraints.NotNull;
import jakarta.nosql.mapping.Column;
import jakarta.nosql.mapping.Embeddable;

/**
 * @author PC
 *
 */
@Embeddable
public class SwitchStateEventConditions extends SwitchStateConditions {

	/**
	 * 
	 */
	@NotNull
	@Column
	private String eventRef;
	/**
	 * 
	 */
	@Column
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
