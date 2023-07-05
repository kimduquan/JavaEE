package epf.workflow.schema.mapping;

import java.util.logging.Level;
import java.util.logging.Logger;
import epf.json.schema.JsonSchema;
import epf.util.json.JsonUtil;
import epf.util.logging.LogManager;
import jakarta.nosql.mapping.AttributeConverter;

/**
 * @author PC
 *
 */
public class JsonSchemaAttributeConverter implements AttributeConverter<JsonSchema, Object> {
	
	/**
	 * 
	 */
	private static transient final Logger LOGGER = LogManager.getLogger(JsonSchemaAttributeConverter.class.getName()); 

	@Override
	public Object convertToDatabaseColumn(final JsonSchema attribute) {
		try {
			return JsonUtil.toString(attribute);
		} 
		catch (Exception e) {
			LOGGER.log(Level.SEVERE, "convertToDatabaseColumn", e);
			return null;
		}
	}

	@Override
	public JsonSchema convertToEntityAttribute(final Object dbData) {
		try {
			return JsonUtil.fromJson(dbData.toString(), JsonSchema.class);
		} 
		catch (Exception e) {
			LOGGER.log(Level.SEVERE, "convertToEntityAttribute", e);
			return null;
		}
	}
}
