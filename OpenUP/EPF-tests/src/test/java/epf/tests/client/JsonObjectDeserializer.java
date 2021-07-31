<<<<<<< HEAD:OpenUP/EPF-tests/src/test/java/epf/tests/JsonObjectDeserializer.java
package epf.tests;
=======
package epf.tests.client;
>>>>>>> remotes/origin/micro:OpenUP/EPF-tests/src/test/java/epf/tests/client/JsonObjectDeserializer.java

import java.io.IOException;
import java.io.StringReader;
import javax.json.Json;
import javax.json.JsonObject;
import javax.json.JsonReader;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.TreeNode;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;

public class JsonObjectDeserializer extends JsonDeserializer<JsonObject> {

	@Override
	public JsonObject deserialize(JsonParser p, DeserializationContext ctxt)
			throws IOException, JsonProcessingException {
		TreeNode node = p.readValueAsTree();
		if(node != null) {
			String value = node.toString();
			JsonObject object;
			try(StringReader reader = new StringReader(value)){
				try(JsonReader jsonReader = Json.createReader(reader)){
					object = jsonReader.readObject();
				}
			}
			return object;
		}
		return null;
	}

}
