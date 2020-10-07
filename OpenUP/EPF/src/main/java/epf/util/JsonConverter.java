package epf.util;

import java.io.StringReader;
import javax.json.Json;
import javax.json.JsonObject;
import javax.json.JsonReader;
import javax.persistence.AttributeConverter;
import javax.persistence.Converter;

@Converter(autoApply = true)
public class JsonConverter implements AttributeConverter<JsonObject, String> {

	@Override
	public String convertToDatabaseColumn(JsonObject attribute) {
		return attribute.toString();
	}

	@Override
	public JsonObject convertToEntityAttribute(String dbData) {
		JsonObject result = null;
		try(StringReader strReader = new StringReader(dbData)){
			try(JsonReader reader = Json.createReader(strReader)){
				result = reader.readObject();
			}
		}
		return result;
	}

}
