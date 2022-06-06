package epf.schema.utility;

import javax.json.JsonObject;
import javax.persistence.AttributeConverter;
import javax.persistence.Converter;

import epf.util.json.JsonUtil;

/**
 * @author PC
 *
 */
@Converter(autoApply = true)
public class JsonConverter implements AttributeConverter<JsonObject, String> {

	/**
	 *
	 */
	@Override
	public String convertToDatabaseColumn(final JsonObject attribute) {
		String value = null;
        if(attribute != null){
        	value = attribute.toString();
        }
        return value;
	}

	/**
	 *
	 */
	@Override
	public JsonObject convertToEntityAttribute(final String dbData) {
            JsonObject result = null;
            if(dbData != null){
            	result = JsonUtil.readObject(dbData);
            }
            return result;
	}

}
