package epf.workflow.schema.event.mapping;

import epf.workflow.schema.event.EventDefinition;
import epf.workflow.schema.mapping.ArrayAttributeConverter;

/**
 * @author PC
 *
 */
public class EventDefinitionArrayConverter extends ArrayAttributeConverter<EventDefinition> {

	/**
	 * 
	 */
	public EventDefinitionArrayConverter() {
		super(EventDefinition.class, new EventDefinition[0]);
	}
}
