package epf.workflow.schema.adapter;

import epf.workflow.schema.TransitionDefinition;
import epf.workflow.schema.util.EitherAdapter;

/**
 * @author PC
 *
 */
public class TransitionDefinitionAdapter extends EitherAdapter<String, TransitionDefinition> {

	/**
	 * 
	 */
	public TransitionDefinitionAdapter() {
		super(String.class, TransitionDefinition.class);
	}
}
