package epf.workflow.schema.mapping;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import jakarta.json.JsonArray;
import epf.util.json.ext.JsonUtil;
import epf.util.logging.LogManager;
import org.eclipse.jnosql.communication.document.Document;
import org.eclipse.jnosql.mapping.AttributeConverter;

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
	
	public static Map<String, Object> convertToMap(final List<?> list){
		final Map<String, Object> map = new HashMap<>();
		boolean isMap = true;
		for(Object object : list) {
			if(object instanceof Document) {
				final Document document = (Document) object;
				final Object value = document.get();
				if(value instanceof List) {
					final List<?> asList = (List<?>)value;
					if(asList.isEmpty()) {
						map.put(document.name(), value);
					}
					else {
						final Map<String, Object> newMap = convertToMap(asList);
						if(newMap != null) {
							map.put(document.name(), newMap);
						}
						else {
							final List<Object> newList = convertToList(asList);
							map.put(document.name(), newList);
						}
					}
				}
				else if(value != null) {
					map.put(document.name(), value);
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
	public static final Object convertToValue(final Object dbData) {
		Object object = null;
		if(dbData instanceof List) {
			final List<?> asList = (List<?>) dbData;
			if(asList.isEmpty()) {
				object = new HashMap<String, Object>();
			}
			else {
				final Map<String, Object> map = convertToMap((List<?>)dbData);
				if(map != null) {
					object = map;
				}
				else {
					final List<Object> newList = convertToList(asList);
					object = newList;
				}
			}
		}
		else if(dbData instanceof String || dbData instanceof Number || dbData instanceof Boolean) {
			object = dbData;
		}
		else {
			object= dbData;
		}
		return object;
	}
	
	/**
	 * @param dbData
	 * @return
	 */
	public static final List<Object> convertToList(final List<?> dbData) {
		final List<Object> list = new ArrayList<>();
		for(Object object : dbData) {
			final Object value = convertToValue(object);
			list.add(value);
		}
		return list;
	}
}
