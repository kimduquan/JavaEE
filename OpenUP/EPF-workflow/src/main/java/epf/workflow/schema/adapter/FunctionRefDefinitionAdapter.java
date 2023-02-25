package epf.workflow.schema.adapter;

import epf.workflow.adapter.StringOrObjectAdapter;
import epf.workflow.schema.FunctionRefDefinition;

/**
 * @author PC
 *
 */
public class FunctionRefDefinitionAdapter extends StringOrObjectAdapter {

	/**
	 * 
	 */
	public FunctionRefDefinitionAdapter() {
		super(FunctionRefDefinition.class);
	}
}
