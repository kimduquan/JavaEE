package epf.workflow.schema.state;

import jakarta.validation.constraints.NotNull;
import epf.workflow.schema.event.EventDataFilter;
import jakarta.nosql.Column;
import jakarta.nosql.Embeddable;
import org.eclipse.microprofile.graphql.Description;

@Embeddable
public class SwitchStateEventConditions extends SwitchStateConditions {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	@NotNull
	@Column
	@Description("References an unique event name in the defined workflow events")
	private String eventRef;
	
	@Column
	@Description("Event data filter definition")
	private EventDataFilter eventDataFilter;
	
	public String getEventRef() {
		return eventRef;
	}
	public void setEventRef(String eventRef) {
		this.eventRef = eventRef;
	}
	public EventDataFilter getEventDataFilter() {
		return eventDataFilter;
	}
	public void setEventDataFilter(EventDataFilter eventDataFilter) {
		this.eventDataFilter = eventDataFilter;
	}
}
