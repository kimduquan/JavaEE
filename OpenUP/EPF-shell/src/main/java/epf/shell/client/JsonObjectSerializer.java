package epf.shell.client;

import java.io.IOException;
import javax.json.JsonObject;
import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;

/**
 * @author PC
 *
 */
public class JsonObjectSerializer extends JsonSerializer<JsonObject> {

	@Override
	public void serialize(final JsonObject value, final JsonGenerator gen, final SerializerProvider serializers) throws IOException {
		if(value == null) {
			gen.writeNull();
		}
		else {
			gen.writeRaw(value.toString());
		}
	}

}
