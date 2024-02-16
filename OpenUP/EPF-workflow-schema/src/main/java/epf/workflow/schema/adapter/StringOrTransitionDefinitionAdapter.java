package epf.workflow.schema.adapter;

import epf.workflow.schema.TransitionDefinition;
import epf.workflow.schema.util.StringOrObjectJsonAdapter;

/**
 * @author PC
 *
 */
public class StringOrTransitionDefinitionAdapter extends StringOrObjectJsonAdapter<TransitionDefinition> {

	/**
	 * 
	 */
	public StringOrTransitionDefinitionAdapter() {
		super(TransitionDefinition.class);
	}
}