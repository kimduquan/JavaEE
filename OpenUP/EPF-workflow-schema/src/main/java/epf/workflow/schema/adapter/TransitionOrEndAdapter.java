package epf.workflow.schema.adapter;

import epf.workflow.schema.EndDefinition;
import epf.workflow.schema.TransitionDefinition;
import epf.workflow.schema.TransitionOrEnd;
import epf.workflow.schema.util.BooleanOrObject;
import epf.workflow.schema.util.EitherOrEitherJsonAdapter;
import epf.workflow.schema.util.StringOrObject;

/**
 * @author PC
 *
 */
public class TransitionOrEndAdapter extends EitherOrEitherJsonAdapter<TransitionOrEnd, StringOrObject<TransitionDefinition>, BooleanOrObject<EndDefinition>> {

	/**
	 * 
	 */
	public TransitionOrEndAdapter() {
		super(TransitionOrEnd.TRANSITION, new TransitionDefinitionAdapter(), TransitionOrEnd.END, new EndDefinitionAdapter(), TransitionOrEnd.class);
	}
}
