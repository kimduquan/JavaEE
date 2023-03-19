package epf.workflow.schema.mapping;

import java.util.logging.Level;
import java.util.logging.Logger;
import javax.json.JsonValue;
import epf.util.json.JsonUtil;
import epf.util.logging.LogManager;
import jakarta.nosql.mapping.AttributeConverter;

/**
 * @author PC
 *
 */
public class JsonValueAttributeConverter implements AttributeConverter<JsonValue, Object> {
	
	/**
	 * 
	 */
	private static transient final Logger LOGGER = LogManager.getLogger(JsonValueAttributeConverter.class.getName()); 

	@Override
	public Object convertToDatabaseColumn(final JsonValue attribute) {
		return JsonUtil.asValue(attribute);
	}

	@Override
	public JsonValue convertToEntityAttribute(final Object dbData) {
		try {
			final Object value = ArrayAttributeConverter.convertToValue(dbData);
			return JsonUtil.toJsonValue(value);
		} 
		catch (Exception e) {
			LOGGER.log(Level.SEVERE, "convertToEntityAttribute", e);
			return null;
		}
	}
}
