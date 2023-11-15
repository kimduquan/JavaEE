package epf.workflow.schema.adapter;

import epf.workflow.schema.StartDefinition;
import epf.workflow.schema.util.StringOrObjectJsonAdapter;

/**
 * @author PC
 *
 */
public class StartDefinitionAdapter extends StringOrObjectJsonAdapter<StartDefinition> {

	/**
	 * 
	 */
	public StartDefinitionAdapter() {
		super(StartDefinition.class);
	}
}
