package epf.workflow.state.schema;

import javax.validation.constraints.NotNull;
import epf.workflow.event.schema.EventDataFilters;
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
	private EventDataFilters eventDataFilter;
	
	public String getEventRef() {
		return eventRef;
	}
	public void setEventRef(String eventRef) {
		this.eventRef = eventRef;
	}
	public EventDataFilters getEventDataFilter() {
		return eventDataFilter;
	}
	public void setEventDataFilter(EventDataFilters eventDataFilter) {
		this.eventDataFilter = eventDataFilter;
	}
}
