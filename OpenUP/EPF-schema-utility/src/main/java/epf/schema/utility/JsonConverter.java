package epf.schema.utility;

import jakarta.json.JsonObject;
import jakarta.persistence.AttributeConverter;
import jakarta.persistence.Converter;
import epf.util.json.ext.JsonUtil;

@Converter(autoApply = true)
public class JsonConverter implements AttributeConverter<JsonObject, String> {

	@Override
	public String convertToDatabaseColumn(final JsonObject attribute) {
		String value = null;
        if(attribute != null){
        	value = attribute.toString();
        }
        return value;
	}

	@Override
	public JsonObject convertToEntityAttribute(final String dbData) {
            JsonObject result = null;
            if(dbData != null){
            	result = JsonUtil.readObject(dbData);
            }
            return result;
	}

}
