package epf.workflow.schema.adapter;

import epf.nosql.schema.BooleanOrObject;
import epf.nosql.schema.StringOrObject;
import epf.workflow.schema.EndDefinition;
import epf.workflow.schema.TransitionDefinition;
import epf.workflow.schema.TransitionOrEnd;
import epf.workflow.schema.util.EitherOrEitherJsonAdapter;

/**
 * @author PC
 *
 */
public class TransitionOrEndAdapter extends EitherOrEitherJsonAdapter<TransitionOrEnd, StringOrObject<TransitionDefinition>, BooleanOrObject<EndDefinition>> {

	/**
	 * 
	 */
	public TransitionOrEndAdapter() {
		super(TransitionOrEnd.TRANSITION, new StringOrTransitionDefinitionAdapter(), TransitionOrEnd.END, new BooleanOrEndDefinitionAdapter(), TransitionOrEnd.class);
	}
}
