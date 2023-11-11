package epf.workflow.schema.adapter;

import java.util.Arrays;
import epf.workflow.schema.TransitionDefinition;
import epf.workflow.schema.util.EitherJsonAdapter;

/**
 * @author PC
 *
 */
public class TransitionDefinitionAdapter extends EitherJsonAdapter<String, TransitionDefinition> {

	/**
	 * 
	 */
	public TransitionDefinitionAdapter() {
		super(String.class, TransitionDefinition.class, Arrays.asList());
	}
}
