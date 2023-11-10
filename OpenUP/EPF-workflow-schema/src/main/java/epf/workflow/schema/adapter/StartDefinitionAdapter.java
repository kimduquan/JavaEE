package epf.workflow.schema.adapter;

import epf.workflow.schema.StartDefinition;
import epf.workflow.schema.util.EitherJsonAdapter;

/**
 * @author PC
 *
 */
public class StartDefinitionAdapter extends EitherJsonAdapter<String, StartDefinition> {

	/**
	 * 
	 */
	public StartDefinitionAdapter() {
		super(String.class, StartDefinition.class);
	}
}
