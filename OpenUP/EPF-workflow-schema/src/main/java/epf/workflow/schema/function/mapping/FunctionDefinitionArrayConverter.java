package epf.workflow.schema.function.mapping;

import epf.workflow.schema.function.FunctionDefinition;
import epf.workflow.schema.mapping.ArrayAttributeConverter;

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
