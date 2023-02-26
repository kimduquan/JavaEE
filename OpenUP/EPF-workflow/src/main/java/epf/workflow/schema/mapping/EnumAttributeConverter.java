package epf.workflow.schema.mapping;

import jakarta.nosql.mapping.AttributeConverter;

/**
 * @author PC
 *
 */
public class EnumAttributeConverter<T extends Enum<T>> implements AttributeConverter<Enum<T>, String> {
	
	/**
	 * 
	 */
	private final Class<T> cls;
	
	/**
	 * @param cls
	 */
	public EnumAttributeConverter(final Class<T> cls) {
		this.cls = cls;
	}

	@Override
	public String convertToDatabaseColumn(final Enum<T> attribute) {
		return attribute.name();
	}

	@Override
	public Enum<T> convertToEntityAttribute(final String dbData) {
		if(dbData == null) {
			return null;
		}
		return Enum.valueOf(cls, dbData);
	}
}