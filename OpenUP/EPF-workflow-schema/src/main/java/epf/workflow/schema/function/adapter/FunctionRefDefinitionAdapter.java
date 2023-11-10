package epf.workflow.schema.function.adapter;

import epf.workflow.schema.function.FunctionRefDefinition;
import epf.workflow.schema.util.EitherJsonAdapter;

/**
 * @author PC
 *
 */
public class FunctionRefDefinitionAdapter extends EitherJsonAdapter<String, FunctionRefDefinition> {

	/**
	 * 
	 */
	public FunctionRefDefinitionAdapter() {
		super(String.class, FunctionRefDefinition.class);
	}
}
