package epf.workflow.schema.adapter;

import java.util.Arrays;
import epf.workflow.schema.EndDefinition;
import epf.workflow.schema.util.EitherJsonAdapter;

/**
 * @author PC
 *
 */
public class EndDefinitionAdapter extends EitherJsonAdapter<Boolean, EndDefinition> {

	/**
	 * 
	 */
	public EndDefinitionAdapter() {
		super(Boolean.class, EndDefinition.class, Arrays.asList(ContinueAsAdapter.class));
	}
}
