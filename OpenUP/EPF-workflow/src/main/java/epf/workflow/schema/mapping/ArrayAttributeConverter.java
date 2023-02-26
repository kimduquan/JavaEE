package epf.workflow.schema.mapping;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.json.JsonArray;
import epf.util.json.JsonUtil;
import epf.util.logging.LogManager;
import jakarta.nosql.document.Document;
import jakarta.nosql.mapping.AttributeConverter;

/**
 * @author PC
 *
 * @param <T>
 */
public class ArrayAttributeConverter<T extends Object> implements AttributeConverter<T[], List<Object>> {
	
	/**
	 * 
	 */
	private transient static final Logger LOGGER = LogManager.getLogger(ArrayAttributeConverter.class.getName());
	
	/**
	 * 
	 */
	private final T[] array;
	
	/**
	 * 
	 */
	private transient final Class<T> cls;
	
	/**
	 * @param cls
	 * @param array
	 */
	public ArrayAttributeConverter(final Class<T> cls, final T[] array) {
		this.array = array;
		this.cls = cls;
	}

	@Override
	public List<Object> convertToDatabaseColumn(final T[] attribute) {
		try {
			final JsonArray jsonArray = JsonUtil.toJsonArray(attribute);
			return JsonUtil.asList(jsonArray);
		}
		catch(Exception ex) {
			LOGGER.log(Level.SEVERE, "convertToDatabaseColumn", ex);
			return null;
		}
	}

	@Override
	public T[] convertToEntityAttribute(final List<Object> dbData) {
		try {
			final List<Object> list = convertToList(dbData);
			return JsonUtil.fromLisṭ̣(list, cls).toArray(array);
		} 
		catch (Exception ex) {
			LOGGER.log(Level.SEVERE, "convertToEntityAttribute", ex);
			return null;
		}
	}
	
	private static Map<String, Object> convertToMap(final List<?> list){
		final Map<String, Object> map = new HashMap<>();
		boolean isMap = true;
		for(Object object : list) {
			if(object instanceof Document) {
				final Document document = (Document) object;
				final Object value = document.get();
				if(value instanceof List) {
					final List<?> asList = (List<?>)value;
					if(asList.isEmpty()) {
						map.put(document.getName(), value);
					}
					else {
						final Map<String, Object> newMap = convertToMap(asList);
						if(newMap != null) {
							map.put(document.getName(), newMap);
						}
						else {
							final List<Object> newList = convertToList(asList);
							map.put(document.getName(), newList);
						}
					}
				}
				else if(value != null) {
					map.put(document.getName(), value);
				}
			}
			else {
				isMap = false;
				break;
			}
		}
		return isMap ? map : null;
	}
	
	/**
	 * @param dbData
	 * @return
	 */
	public static final List<Object> convertToList(final List<?> dbData) {
		final List<Object> list = new ArrayList<>();
		for(Object object : dbData) {
			if(object instanceof List) {
				final List<?> asList = (List<?>) object;
				if(asList.isEmpty()) {
					list.add(new HashMap<String, Object>());
				}
				else {
					final Map<String, Object> map = convertToMap((List<?>)object);
					if(map != null) {
						list.add(map);
					}
					else {
						final List<Object> newList = convertToList(asList);
						list.add(newList);
					}
				}
			}
		}
		return list;
	}
}