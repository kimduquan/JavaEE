package epf.nosql.schema.util;

import epf.util.EnumUtil;
import jakarta.nosql.AttributeConverter;

public class EnumAttributeConverter<T extends Enum<T>> implements AttributeConverter<Enum<T>, String> {
	
	private final Class<T> cls;
	
	private final boolean caseSensitive;
	
	public EnumAttributeConverter(final Class<T> cls, final boolean caseSensitive) {
		this.cls = cls;
		this.caseSensitive = caseSensitive;
	}

	@Override
	public String convertToDatabaseColumn(final Enum<T> attribute) {
		return caseSensitive ? attribute.name() : attribute.name().toLowerCase();
	}

	@Override
	public Enum<T> convertToEntityAttribute(final String dbData) {
		if(dbData == null) {
			return null;
		}
		return EnumUtil.valueOf(cls, dbData, caseSensitive);
	}
}