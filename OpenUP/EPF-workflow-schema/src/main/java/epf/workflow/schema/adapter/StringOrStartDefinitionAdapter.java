package epf.workflow.schema.adapter;

import epf.workflow.schema.StartDefinition;
import epf.workflow.schema.util.StringOrObjectJsonAdapter;

/**
 * @author PC
 *
 */
public class StringOrStartDefinitionAdapter extends StringOrObjectJsonAdapter<StartDefinition> {

	/**
	 * 
	 */
	public StringOrStartDefinitionAdapter() {
		super(StartDefinition.class);
	}
}
