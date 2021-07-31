<<<<<<< HEAD:OpenUP/EPF-tests/src/test/java/epf/tests/JsonObjectSerializer.java
package epf.tests;
=======
package epf.tests.client;
>>>>>>> remotes/origin/micro:OpenUP/EPF-tests/src/test/java/epf/tests/client/JsonObjectSerializer.java

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
