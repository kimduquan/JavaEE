package epf.workflow.event.schema.mapping;

import epf.workflow.event.schema.EventDefinition;
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
