package epf.workflow.schema.mapping;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import epf.util.json.ext.JsonUtil;
import epf.util.logging.LogManager;
import jakarta.json.JsonValue;
import jakarta.json.bind.Jsonb;
import jakarta.json.bind.JsonbBuilder;
import org.eclipse.jnosql.mapping.AttributeConverter;

/**
 * @author PC
 *
 * @param <T>
 */
public class ArrayAttributeConverter<T extends Object> implements AttributeConverter<T[], JsonValue> {
	
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
	public JsonValue convertToDatabaseColumn(final T[] attribute) {
		try {
			return JsonUtil.toJsonArray(attribute);
		}
		catch(Exception ex) {
			LOGGER.log(Level.SEVERE, "convertToDatabaseColumn", ex);
			return null;
		}
	}

	@Override
	public T[] convertToEntityAttribute(final JsonValue dbData) {
		try {
			final List<T> list = new ArrayList<>();
			final Iterator<JsonValue> it = dbData.asJsonArray().iterator();
			try(Jsonb jsonb = JsonbBuilder.create()){
				while(it.hasNext()) {
					final JsonValue value = it.next();
					final T object = jsonb.fromJson(value.toString(), cls);
					list.add(object);
				}
			}
			final T[] arr = list.toArray(array);
			return arr;
		} 
		catch (Exception ex) {
			LOGGER.log(Level.SEVERE, "convertToEntityAttribute", ex);
			return null;
		}
	}
}
