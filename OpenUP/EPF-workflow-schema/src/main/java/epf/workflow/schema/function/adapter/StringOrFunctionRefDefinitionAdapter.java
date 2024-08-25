package epf.workflow.schema.function.adapter;

import epf.workflow.schema.function.FunctionRefDefinition;
import epf.workflow.schema.util.StringOrObjectJsonAdapter;

/**
 * @author PC
 *
 */
public class StringOrFunctionRefDefinitionAdapter extends StringOrObjectJsonAdapter<FunctionRefDefinition> {

	/**
	 * 
	 */
	public StringOrFunctionRefDefinitionAdapter() {
		super(FunctionRefDefinition.class);
	}
}
