package epf.tests.client;

import java.io.IOException;
import javax.json.JsonObject;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.TreeNode;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;
import epf.util.json.JsonUtil;

public class JsonObjectDeserializer extends JsonDeserializer<JsonObject> {

	@Override
	public JsonObject deserialize(JsonParser p, DeserializationContext ctxt)
			throws IOException, JsonProcessingException {
		TreeNode node = p.readValueAsTree();
		if(node != null) {
			String value = node.toString();
			JsonObject object;
			object = JsonUtil.readObject(value);
			return object;
		}
		return null;
	}

}
