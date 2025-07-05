package erp.schema.util;

import jakarta.persistence.AttributeConverter;

/**
 * @param <T>
 */
public class EnumAttributeConverter<T extends Enum<T>> implements AttributeConverter<T, String> {
	
	/**
	 * 
	 */
	private final Class<T> clazz;
	
	/**
	 * 
	 */
	private final String prefix;
	
	/**
	 * 
	 */
	private final String sep;
	
	/**
	 * 
	 */
	private final String alt;
	
	/**
	 * @param clazz
	 * @param prefix
	 * @param sep
	 * @param alt
	 */
	public EnumAttributeConverter(final Class<T> clazz, final String prefix, final String sep, final String alt) {
		this.clazz = clazz;
		this.prefix = prefix;
		this.sep = sep;
		this.alt = alt;
	}

	@Override
	public String convertToDatabaseColumn(final T attribute) {
		String name = attribute.name();
		if(prefix != null && name.startsWith(prefix)) {
			name = name.substring(prefix.length());
		}
		if(sep != null) {
			name = name.replace(sep, alt);
		}
		name = name.replace('_', '-');
		return name;
	}

	@Override
	public T convertToEntityAttribute(final String dbData) {
		String name = dbData;
		name = name.replace('-', '_');
		if(sep != null) {
			name = name.replace(alt, sep);
		}
		if(prefix != null) {
			name = prefix + name;
		}
		return Enum.valueOf(clazz, name);
	}

}
