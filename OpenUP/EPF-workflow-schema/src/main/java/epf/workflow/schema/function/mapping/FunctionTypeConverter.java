package epf.workflow.schema.function.mapping;

import epf.workflow.schema.function.FunctionType;
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
		super(FunctionType.class, true);
	}

}
