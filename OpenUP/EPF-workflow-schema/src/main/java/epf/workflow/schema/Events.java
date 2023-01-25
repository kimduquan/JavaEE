package epf.workflow.schema;

import java.util.List;
import epf.workflow.schema.events.EventDefinition;
import jakarta.nosql.mapping.Column;
import jakarta.nosql.mapping.Embeddable;

/**
 * @author PC
 *
 */
@Embeddable
public class Events 
{
	/**
	 * 
	 */
	@Column
	private String refValue;
	/**
	 * 
	 */
	@Column
	private List<EventDefinition> eventDefs;

	public String getRefValue() {
		return refValue;
	}

	public void setRefValue(final String refValue) {
	    this.refValue = refValue;
	}
	
	public List<EventDefinition> getEventDefs() {
	    return eventDefs;
	}
	
	public void setEventDefs(final List<EventDefinition> eventDefs) {
	    this.eventDefs = eventDefs;
	}
}
