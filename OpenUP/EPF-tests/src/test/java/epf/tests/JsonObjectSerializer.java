package epf.tests;

import java.io.IOException;
import javax.json.JsonObject;
import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;

public class JsonObjectSerializer extends JsonSerializer<JsonObject> {

	@Override
	public void serialize(JsonObject value, JsonGenerator gen, SerializerProvider serializers) throws IOException {
		if(value != null) {
			gen.writeRaw(value.toString());
		}
		else {
			gen.writeNull();
		}
	}

}
