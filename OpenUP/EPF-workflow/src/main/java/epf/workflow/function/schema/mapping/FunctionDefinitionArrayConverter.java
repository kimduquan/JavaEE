package epf.workflow.function.schema.mapping;

import epf.workflow.function.schema.FunctionDefinition;
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
