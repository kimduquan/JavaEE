package epf.workflow.schema.function.adapter;

import epf.workflow.schema.function.FunctionRefDefinition;
import epf.workflow.schema.util.StringOrObjectJsonAdapter;

/**
 * @author PC
 *
 */
public class FunctionRefDefinitionAdapter extends StringOrObjectJsonAdapter<FunctionRefDefinition> {

	/**
	 * 
	 */
	public FunctionRefDefinitionAdapter() {
		super(FunctionRefDefinition.class);
	}
}
