package epf.shell.client;

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

/**
 * @author PC
 *
 */
public class JsonObjectDeserializer extends JsonDeserializer<JsonObject> {

	@Override
	public JsonObject deserialize(final JsonParser parser, final DeserializationContext ctxt)
			throws IOException, JsonProcessingException {
		JsonObject object = null;
		final TreeNode node = parser.readValueAsTree();
		if(node != null) {
			final String value = node.toString();
			try(StringReader reader = new StringReader(value)){
				try(JsonReader jsonReader = Json.createReader(reader)){
					object = jsonReader.readObject();
				}
			}
		}
		return object;
	}

}
