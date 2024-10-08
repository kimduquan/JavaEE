package epf.workflow.schema.state;

import jakarta.validation.constraints.NotNull;
import epf.workflow.schema.event.EventDataFilter;
import jakarta.nosql.Column;
import org.eclipse.jnosql.mapping.Embeddable;

/**
 * @author PC
 *
 */
@Embeddable
public class SwitchStateEventConditions extends SwitchStateConditions {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
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
