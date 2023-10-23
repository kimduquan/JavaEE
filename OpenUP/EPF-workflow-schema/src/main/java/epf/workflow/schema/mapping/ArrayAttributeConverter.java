package epf.workflow.schema.mapping;

import java.util.Arrays;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import epf.util.json.ext.JsonUtil;
import epf.util.logging.LogManager;
import org.eclipse.jnosql.mapping.AttributeConverter;

/**
 * @author PC
 *
 * @param <T>
 */
public class ArrayAttributeConverter<T extends Object> implements AttributeConverter<T[], Object[]> {
	
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
	public Object[] convertToDatabaseColumn(final T[] attribute) {
		try {
			return JsonUtil.toList(attribute).toArray(new Object[0]);
		}
		catch(Exception ex) {
			LOGGER.log(Level.SEVERE, "convertToDatabaseColumn", ex);
			return null;
		}
	}

	@Override
	public T[] convertToEntityAttribute(final Object[] dbData) {
		try {
			final List<T> list = JsonUtil.fromLisṭ̣(Arrays.asList(dbData), cls);
			return list.toArray(array);
		} 
		catch (Exception ex) {
			LOGGER.log(Level.SEVERE, "convertToEntityAttribute", ex);
			return null;
		}
	}
}
