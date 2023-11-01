package epf.workflow.schema.adapter;

import epf.util.json.ext.adapter.StringOrObjectAdapter;
import epf.workflow.schema.StartDefinition;

/**
 * @author PC
 *
 */
public class StartDefinitionAdapter extends StringOrObjectAdapter<StartDefinition> {

	/**
	 * 
	 */
	public StartDefinitionAdapter() {
		super(StartDefinition.class);
	}
}
