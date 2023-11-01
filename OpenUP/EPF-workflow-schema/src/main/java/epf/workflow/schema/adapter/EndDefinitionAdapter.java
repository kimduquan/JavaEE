package epf.workflow.schema.adapter;

import epf.util.json.ext.adapter.BoolOrObjectAdapter;
import epf.workflow.schema.EndDefinition;

/**
 * @author PC
 *
 */
public class EndDefinitionAdapter extends BoolOrObjectAdapter<EndDefinition> {

	/**
	 * 
	 */
	public EndDefinitionAdapter() {
		super(EndDefinition.class);
	}
}
