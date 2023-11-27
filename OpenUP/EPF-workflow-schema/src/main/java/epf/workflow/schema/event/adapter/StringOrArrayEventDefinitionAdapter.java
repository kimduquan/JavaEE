package epf.workflow.schema.event.adapter;

import epf.workflow.schema.event.EventDefinition;
import epf.workflow.schema.util.StringOrArrayJsonAdapter;

/**
 * 
 */
public class StringOrArrayEventDefinitionAdapter extends StringOrArrayJsonAdapter<EventDefinition> {

	/**
	 * 
	 */
	public StringOrArrayEventDefinitionAdapter() {
		super(EventDefinition.class);
	}

}
