package epf.workflow.schema.adapter;

import epf.workflow.schema.StartDefinition;
import epf.workflow.schema.util.EitherAdapter;

/**
 * @author PC
 *
 */
public class StartDefinitionAdapter extends EitherAdapter<String, StartDefinition> {

	/**
	 * 
	 */
	public StartDefinitionAdapter() {
		super(String.class, StartDefinition.class);
	}
}
