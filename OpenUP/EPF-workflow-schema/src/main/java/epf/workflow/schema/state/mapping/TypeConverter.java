package epf.workflow.schema.state.mapping;

import epf.workflow.schema.mapping.EnumAttributeConverter;
import epf.workflow.schema.state.Type;

/**
 * @author PC
 *
 */
public class TypeConverter extends EnumAttributeConverter<Type> {

	/**
	 * 
	 */
	public TypeConverter() {
		super(Type.class);
	}
}
