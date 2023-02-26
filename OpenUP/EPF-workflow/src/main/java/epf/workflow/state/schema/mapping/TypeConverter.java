package epf.workflow.state.schema.mapping;

import epf.workflow.schema.mapping.EnumAttributeConverter;
import epf.workflow.state.schema.Type;

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
