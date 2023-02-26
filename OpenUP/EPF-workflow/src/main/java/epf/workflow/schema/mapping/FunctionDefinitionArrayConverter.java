package epf.workflow.schema.mapping;

import epf.workflow.schema.FunctionDefinition;

/**
 * @author PC
 *
 */
public class FunctionDefinitionArrayConverter extends ArrayAttributeConverter<FunctionDefinition> {

	/**
	 * 
	 */
	public FunctionDefinitionArrayConverter() {
		super(FunctionDefinition.class, new FunctionDefinition[0]);
	}
}
