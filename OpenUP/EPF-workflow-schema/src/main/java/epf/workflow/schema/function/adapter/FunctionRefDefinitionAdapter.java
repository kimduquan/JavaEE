package epf.workflow.schema.function.adapter;

import epf.workflow.schema.function.FunctionRefDefinition;
import epf.workflow.schema.util.EitherAdapter;

/**
 * @author PC
 *
 */
public class FunctionRefDefinitionAdapter extends EitherAdapter<String, FunctionRefDefinition> {

	/**
	 * 
	 */
	public FunctionRefDefinitionAdapter() {
		super(String.class, FunctionRefDefinition.class);
	}
}
