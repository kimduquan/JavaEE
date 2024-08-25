<<<<<<< HEAD:OpenUP/EPF-tests/src/test/java/epf/tests/JsonObjectDeserializer.java
package epf.tests;
=======
package epf.tests.client;
>>>>>>> remotes/origin/micro:OpenUP/EPF-tests/src/test/java/epf/tests/client/JsonObjectDeserializer.java

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
