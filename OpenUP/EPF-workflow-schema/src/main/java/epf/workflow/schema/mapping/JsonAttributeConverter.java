package epf.workflow.schema.mapping;

import java.util.logging.Level;
import java.util.logging.Logger;
import org.eclipse.jnosql.mapping.AttributeConverter;
import epf.util.json.ext.JsonUtil;
import epf.util.logging.LogManager;
import jakarta.json.JsonValue;
import jakarta.json.JsonValue.ValueType;

/**
 * 
 */
public class JsonAttributeConverter<T> implements AttributeConverter<Object, JsonValue> {
	
	/**
	 * 
	 */
	private transient static final Logger LOGGER = LogManager.getLogger(JsonAttributeConverter.class.getName());
	
	/**
	 * 
	 */
	private transient final Class<T> cls;
	
	/**
	 * @param cls
	 */
	public JsonAttributeConverter(final Class<T> cls) {
		this.cls = cls;
	}

	@Override
	public JsonValue convertToDatabaseColumn(final Object attribute) {
		try {
			return JsonUtil.toJsonValue(attribute);
		} 
		catch (Exception e) {
			LOGGER.log(Level.SEVERE, e.toString());
			return null;
		}
	}

	@Override
	public Object convertToEntityAttribute(final JsonValue dbData) {
		if(ValueType.OBJECT.equals(dbData.getValueType())) {
			try {
				return JsonUtil.asObject(cls, dbData);
			} 
			catch (Exception e) {
				LOGGER.log(Level.SEVERE, e.toString());
				return null;
			}
		}
		return JsonUtil.asValue(dbData);
	}

}
