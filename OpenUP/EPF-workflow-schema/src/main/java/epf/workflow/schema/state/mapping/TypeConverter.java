package epf.workflow.schema.state.mapping;

import epf.nosql.schema.util.EnumAttributeConverter;
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
		super(Type.class, true);
	}
}
