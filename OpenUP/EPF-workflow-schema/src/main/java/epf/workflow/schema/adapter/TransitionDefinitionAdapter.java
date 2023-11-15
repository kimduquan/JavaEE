package epf.workflow.schema.adapter;

import epf.workflow.schema.TransitionDefinition;
import epf.workflow.schema.util.StringOrObjectJsonAdapter;

/**
 * @author PC
 *
 */
public class TransitionDefinitionAdapter extends StringOrObjectJsonAdapter<TransitionDefinition> {

	/**
	 * 
	 */
	public TransitionDefinitionAdapter() {
		super(TransitionDefinition.class);
	}
}
