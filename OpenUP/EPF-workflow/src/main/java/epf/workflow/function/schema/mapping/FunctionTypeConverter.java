package epf.workflow.function.schema.mapping;

import epf.workflow.function.schema.FunctionType;
import epf.workflow.schema.mapping.EnumAttributeConverter;

/**
 * @author PC
 *
 */
public class FunctionTypeConverter extends EnumAttributeConverter<FunctionType> {

	/**
	 * 
	 */
	public FunctionTypeConverter() {
		super(FunctionType.class);
	}

}
