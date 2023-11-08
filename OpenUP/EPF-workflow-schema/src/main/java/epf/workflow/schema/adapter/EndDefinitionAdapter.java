package epf.workflow.schema.adapter;

import epf.workflow.schema.EndDefinition;
import epf.workflow.schema.util.EitherAdapter;

/**
 * @author PC
 *
 */
public class EndDefinitionAdapter extends EitherAdapter<Boolean, EndDefinition> {

	/**
	 * 
	 */
	public EndDefinitionAdapter() {
		super(Boolean.class, EndDefinition.class);
	}
}
