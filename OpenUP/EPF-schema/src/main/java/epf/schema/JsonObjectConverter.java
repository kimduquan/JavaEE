package epf.schema;

import java.io.StringReader;
import javax.json.Json;
import javax.json.JsonObject;
import javax.json.JsonReader;
import javax.persistence.AttributeConverter;
import javax.persistence.Converter;

@Converter(autoApply = true)
public class JsonObjectConverter implements AttributeConverter<JsonObject, String> {

	@Override
	public String convertToDatabaseColumn(JsonObject attribute) {
            if(attribute != null){
		return attribute.toString();
            }
            return null;
	}

	@Override
	public JsonObject convertToEntityAttribute(String dbData) {
            JsonObject result = null;
            if(dbData != null){
                try(StringReader strReader = new StringReader(dbData)){
                        try(JsonReader reader = Json.createReader(strReader)){
                                result = reader.readObject();
                        }
                }
            }
            return result;
	}

}
