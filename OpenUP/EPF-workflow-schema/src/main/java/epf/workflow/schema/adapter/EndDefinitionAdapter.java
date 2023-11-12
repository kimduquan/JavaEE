package epf.workflow.schema.adapter;

import java.util.Arrays;
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
		super(EndDefinition.class, Arrays.asList(ContinueAsAdapter.class));
	}
}
