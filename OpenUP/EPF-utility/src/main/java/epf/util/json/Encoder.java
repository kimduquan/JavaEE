/**
 * 
 */
package epf.util.json;

import java.io.StringReader;
import javax.json.Json;
import javax.json.JsonObject;
import javax.json.JsonReader;
import javax.json.bind.Jsonb;

/**
 * @author PC
 *
 */
public class Encoder {

	/**
	 * @param jsonb
	 * @param object
	 * @return
	 * @throws EncodeException
	 */
	public String encode(final Jsonb jsonb, final Object object) throws Exception {
		final String json = jsonb.toJson(object);
		try(StringReader reader = new StringReader(json)){
			try(JsonReader jsonReader = Json.createReader(reader)){
				final JsonObject jsonObject = jsonReader.readObject();
				final JsonObject encodedJsonObject = Json
						.createObjectBuilder(jsonObject)
						.add("class", object.getClass().getName())
						.build();
				return encodedJsonObject.toString();
			}
		}
	}
}
