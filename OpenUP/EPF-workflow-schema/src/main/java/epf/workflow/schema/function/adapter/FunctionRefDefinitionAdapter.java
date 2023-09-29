package epf.workflow.schema.function.adapter;

import epf.workflow.schema.adapter.StringOrObjectAdapter;
import epf.workflow.schema.function.FunctionRefDefinition;

/**
 * @author PC
 *
 */
public class FunctionRefDefinitionAdapter extends StringOrObjectAdapter<FunctionRefDefinition> {

	/**
	 * 
	 */
	public FunctionRefDefinitionAdapter() {
		super(FunctionRefDefinition.class);
	}
}
