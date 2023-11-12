package epf.workflow.schema.adapter;

import java.util.Arrays;
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
		super(TransitionDefinition.class, Arrays.asList());
	}
}
