package epf.workflow.schema.function.mapping;

import epf.nosql.schema.util.EnumAttributeConverter;
import epf.workflow.schema.function.FunctionType;

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
