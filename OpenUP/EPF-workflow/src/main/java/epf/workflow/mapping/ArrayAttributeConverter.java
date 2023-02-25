package epf.workflow.mapping;

import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.json.JsonArray;
import epf.util.json.JsonUtil;
import epf.util.logging.LogManager;
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
			return JsonUtil.fromLisṭ̣(dbData, cls).toArray(array);
		} 
		catch (Exception ex) {
			LOGGER.log(Level.SEVERE, "convertToEntityAttribute", ex);
			return null;
		}
	}
}
