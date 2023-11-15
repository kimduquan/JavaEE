package epf.workflow.schema.adapter;

import epf.workflow.schema.EndDefinition;
import epf.workflow.schema.util.BooleanOrObjectJsonAdapter;

/**
 * @author PC
 *
 */
public class EndDefinitionAdapter extends BooleanOrObjectJsonAdapter<EndDefinition> {

	/**
	 * 
	 */
	public EndDefinitionAdapter() {
		super(EndDefinition.class);
	}
}
