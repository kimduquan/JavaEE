package epf.nosql.schema.util;

import org.eclipse.jnosql.mapping.AttributeConverter;
import epf.util.EnumUtil;

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
	 * 
	 */
	private final boolean caseSensitive;
	
	/**
	 * @param cls
	 * @param caseSensitive
	 */
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