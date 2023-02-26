package epf.workflow.function.schema.adapter;

import epf.workflow.function.schema.FunctionRefDefinition;
import epf.workflow.schema.adapter.StringOrObjectAdapter;

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
