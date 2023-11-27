package epf.workflow.schema.adapter;

import epf.workflow.schema.EndDefinition;
import epf.workflow.schema.util.BooleanOrObjectJsonAdapter;

/**
 * @author PC
 *
 */
public class BooleanOrEndDefinitionAdapter extends BooleanOrObjectJsonAdapter<EndDefinition> {

	/**
	 * 
	 */
	public BooleanOrEndDefinitionAdapter() {
		super(EndDefinition.class);
	}
}
