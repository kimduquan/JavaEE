package epf.workflow.mapping;

import java.util.ArrayList;
import java.util.List;
import javax.json.JsonArray;
import epf.util.json.JsonUtil;
import jakarta.nosql.mapping.AttributeConverter;

/**
 * @author PC
 *
 */
public class ArrayAttributeConverter<T extends Object> implements AttributeConverter<T[], List<Object>> {
	
	/**
	 * 
	 */
	private final Class<T> cls;
	
	/**
	 * 
	 */
	private final T[] array;
	
	/**
	 * @param cls
	 * @param array
	 */
	public ArrayAttributeConverter(final Class<T> cls, final T[] array) {
		this.cls = cls;
		this.array = array;
	}

	@Override
	public List<Object> convertToDatabaseColumn(final T[] attribute) {
		try {
			final JsonArray jsonArray = JsonUtil.toJsonArray(attribute);
			return JsonUtil.asList(jsonArray);
		}
		catch(Exception ex) {
			return null;
		}
	}
	
	@Override
	public T[] convertToEntityAttribute(final List<Object> dbData) {
		List<T> list = new ArrayList<>();
		try {
			list = JsonUtil.fromLisṭ̣(dbData, cls);
		} 
		catch (Exception e) {
			return null;
		}
		return list.toArray(array);
	}
}
