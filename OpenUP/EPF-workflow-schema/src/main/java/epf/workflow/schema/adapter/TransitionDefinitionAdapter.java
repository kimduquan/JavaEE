package epf.workflow.schema.adapter;

import epf.workflow.schema.TransitionDefinition;

/**
 * @author PC
 *
 */
public class TransitionDefinitionAdapter extends StringOrObjectAdapter<TransitionDefinition> {

	/**
	 * 
	 */
	public TransitionDefinitionAdapter() {
		super(TransitionDefinition.class);
	}
}
